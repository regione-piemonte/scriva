/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject, forkJoin, of } from 'rxjs';
import * as _ from 'lodash';
import { catchError, map, switchMap, tap } from 'rxjs/operators';
import {
  AdempimentoService,
  AppConfigService,
  AuthStoreService,
  ConfigurazioniScrivaService,
  IstanzaService,
  MessageService,
  StepManagerService,
} from 'src/app/shared/services';
import { Allegato, IntegraAllegato, TipoAllegato } from '../models';
import { StatoIstanzaEnum, TipoEventoEnum } from 'src/app/shared/utils';
import {
  ConfigAdempimento,
  DataQuadro,
  Istanza,
  OggettoApp,
} from 'src/app/shared/models';
import { IntegrazioneIstanza } from 'src/app/shared/models/istanza/integrazione-istanza.model';
import { IntegrazioneIstanzaStatus } from 'src/app/shared/models/istanza/integrazione-istanza-status.model';
import { AllegatiService } from './allegati.service';
import { DatePipe } from '@angular/common';
import { NgxSpinnerService } from 'ngx-spinner';
import { CodTipologiaAllegato } from 'src/app/shared/enums/cod-tipologia-allegato.enums';
import { Router } from '@angular/router';
import { TipoInfoAdempimento } from 'src/app/shared/services/configurazioni/utilities/configurazioni.enums';
import { AdempimentoStatoIstanza } from 'src/app/shared/models/adempimento/adempimento-stato-istanza.model';

@Injectable({ providedIn: 'root' })
export class AllegatiIntegrazioniService {
  private beUrl = '';
  private CODTIPOQUADRO: string = 'QDR_ALLEGATO';
  private CODTIPOQUADRORIEPILOGO: string = 'QDR_RIEPILOGO';
  private IDSHOWERRORS: string = 'cardIntegrazioneAllegati';
  private CHIAVEADEMPIELAB: string = 'ELAB_PROGETTUALI';
  private CHIAVEADEMPIINTEGR: string = 'INTEGRAZIONI';

  isFrontOffice: boolean = false;

  tipiIntegrazione: IntegraAllegato[] = [
    {
      id_tipo_integra_allegato: 1,
      cod_tipo_integra_allegato: 'P',
      des_tipo_integra_allegato: 'Integrazione di perfezionamento',
    },
    {
      id_tipo_integra_allegato: 2,
      cod_tipo_integra_allegato: 'I',
      des_tipo_integra_allegato: 'Integrazione post avvio',
    },
  ];

  private _istanze: { idIstanza: number; istanza: Istanza }[] = [];
  private _tipoIntegrazione: {
    idIstanza: number;
    tipoIntegrazione: IntegraAllegato;
  }[] = [];
  private _integrazioneIstanza: {
    idIstanza: number;
    integrazioneIstanza: IntegrazioneIstanza;
  }[] = [];
  private _statoPartenzaIntegrazione: {
    idIstanza: number;
    statoPartenzaIntegrazione: string;
  }[] = [];
  private _JsIntegrazioni: { idIstanza: number; jsIntegrazioni: string }[] = [];

  private _status$: Subject<IntegrazioneIstanzaStatus> =
    new Subject<IntegrazioneIstanzaStatus>();

  /** Subject<Istanza> che propaga le informazioni dell'istanza aggiornata a seguito dell'annullamento integrazione documenti. */
  onAnnullaIntegrazione$ = new Subject<Istanza>();

  constructor(
    private adempimentoService: AdempimentoService,
    private http: HttpClient,
    private config: AppConfigService,
    private configurazioniService: ConfigurazioniScrivaService,
    private datePipe: DatePipe,
    private istanzaService: IstanzaService,
    private allegatiService: AllegatiService,
    private messageService: MessageService,
    private router: Router,
    private spinner: NgxSpinnerService,
    private stepManagerService: StepManagerService,
    private authStoreService: AuthStoreService
  ) {
    this.beUrl = this.config.getBEUrl();
    if (this.authStoreService.getComponente() === 'BO') {
      this.isFrontOffice = false;
    } else {
      this.isFrontOffice = true;
    }
  }

  static isIntegrazioneStatus(status: StatoIstanzaEnum): boolean {
    return (
      status === StatoIstanzaEnum.PERF_ALLEGATI ||
      status === StatoIstanzaEnum.RIC_INTEGR ||
      status === StatoIstanzaEnum.RIC_INTEGR_SUCC ||
      status === StatoIstanzaEnum.DA_SCARICARE_ELENCO ||
      status === StatoIstanzaEnum.DA_SCARICARE_LETTERA ||
      status === StatoIstanzaEnum.DA_FIRMARE_LETTERA ||
      status === StatoIstanzaEnum.DA_CONFERMARE_INTEG
    );
  }

  private _getTipoIntegrazione(
    statoIstanza: StatoIstanzaEnum,
    istanza: Istanza
  ): IntegraAllegato {
    const statoIstanzaPartenza: StatoIstanzaEnum =
      this.getStatoPartenzaIntegrazione(istanza.id_istanza);

    if (statoIstanza === StatoIstanzaEnum.PERF_ALLEGATI) {
      return this.tipiIntegrazione[0];
    }
    if (
      statoIstanza === StatoIstanzaEnum.RIC_INTEGR ||
      statoIstanza === StatoIstanzaEnum.RIC_INTEGR_SUCC
    ) {
      return this.tipiIntegrazione[1];
    }

    // se lo stato attuale non e' tra questi non sono in integrazione
    if (!AllegatiIntegrazioniService.isIntegrazioneStatus(statoIstanza)) {
      return null;
    }

    if (statoIstanzaPartenza === StatoIstanzaEnum.PERF_ALLEGATI) {
      return this.tipiIntegrazione[0];
    }

    if (
      statoIstanzaPartenza === StatoIstanzaEnum.RIC_INTEGR_SUCC ||
      statoIstanzaPartenza === StatoIstanzaEnum.RIC_INTEGR
    ) {
      return this.tipiIntegrazione[1];
    }

    return null;
  }

  /**
   * Funzione che recupera obervable dello status per istanza corrente
   */
  getStatus(): Observable<any> {
    return this._status$.asObservable();
  }

  private _isPerfezionamentoOrIntegrazione(istanza: Istanza): boolean {
    return (
      !!this.istanzaService.getIstanzaOggettoApp(
        istanza,
        'alg_perfezionamento'
      ) ||
      !!this.istanzaService.getIstanzaOggettoApp(istanza, 'alg_integrazione') ||
      !!this.istanzaService.getIstanzaOggettoApp(istanza, 'INTEGRA_ALLEG_RICH')
    );
  }

  public checkIntegrazioneAndInit(
    istanza: Istanza
  ): Observable<IntegrazioneIstanza> {
    // SCRIVA-1319 con subtask SCRIVA-1337 SCRIVA-1380
    if (!this._isPerfezionamentoOrIntegrazione(istanza)) {
      return of(null);
    }

    this.setJsonDataIntegrazioneFromIstanza(istanza, istanza.id_istanza);
    const tipoIntegrazione = this._getTipoIntegrazione(
      istanza.stato_istanza.codice_stato_istanza as StatoIstanzaEnum,
      istanza
    );
    if (tipoIntegrazione) {
      this.setIstanza(istanza, istanza.id_istanza);
      this.setTipoIntegrazione(tipoIntegrazione, istanza.id_istanza);
      return this._postIntegrazione(istanza);
    }

    this._reset();
    return of(null);
  }

  /**
   * Ad ora non utilizzato
   * @param istanza
   * @returns
   */
  public apiGetIntegrazioni(
    istanza: Istanza,
    codTipoIntegrazione: string = 'I'
  ): Observable<IntegrazioneIstanza[]> {
    return this.http.get<IntegrazioneIstanza[]>(
      `${this.beUrl}/integrazioni/?id_istanza=${istanza.id_istanza}&cod_tipo_integrazione=${codTipoIntegrazione}`
    );
  }

  callbackPostAllegati(
    allegato: Allegato,
    idIstanza: number
  ): Observable<IntegrazioneIstanza> {
    // SCRIVA-1319 con subtask SCRIVA-1337 SCRIVA-1380
    const istanza: Istanza = this.getIstanza(idIstanza);
    if (!this._isPerfezionamentoOrIntegrazione(istanza)) {
      return of(null);
    }

    const integrazioneIstanzaForPut =
      this._getIntegrazioneIstanzaForPutWhenAddAllegato(idIstanza, allegato);
    if (integrazioneIstanzaForPut) {
      return this._putIntegrazione(integrazioneIstanzaForPut, idIstanza);
    }
    return of(null);
  }

  callbackMultiplePostAllegati(
    allegati: Allegato[],
    idIstanza: number
  ): Observable<IntegrazioneIstanza> {
    const istanza: Istanza = this.getIstanza(idIstanza);
    if (!this._isPerfezionamentoOrIntegrazione(istanza)) {
      return of(null);
    }
    let integrazioneIstanzaForPut =
      this._getIntegrazioneIstanzaForPutWhenAddAllegato(idIstanza, allegati);
    if (integrazioneIstanzaForPut) {
      return this._putIntegrazione(integrazioneIstanzaForPut, idIstanza);
    }
    return of();
  }

  _getIntegrazioneIstanzaForPutWhenAddAllegato(
    idIstanza: number,
    allegato: Allegato | Allegato[],
    flg_allegato_rif_protocollo: boolean = false
  ): IntegrazioneIstanza {
    const integrazioneIstanza = this.getIntegrazioneIstanza(idIstanza);
    const tipoIntegrazione = this.getTipoIntegrazione(idIstanza);

    if (
      !tipoIntegrazione ||
      !integrazioneIstanza ||
      integrazioneIstanza?.id_istanza !== idIstanza
    ) {
      return null;
    }
    let integrazioneIstanzaForPut: IntegrazioneIstanza = {
      ...integrazioneIstanza,
    };
    if (!integrazioneIstanzaForPut.allegato_integrazione) {
      integrazioneIstanzaForPut.allegato_integrazione = [];
    }
    if (Array.isArray(allegato)) {
      allegato.forEach((item) => {
        integrazioneIstanzaForPut.allegato_integrazione.push({
          id_allegato_istanza: item.id_allegato_istanza,
          flg_allegato_rif_protocollo: flg_allegato_rif_protocollo,
        });
      });
    } else {
      integrazioneIstanzaForPut.allegato_integrazione.push({
        id_allegato_istanza: allegato.id_allegato_istanza,
        flg_allegato_rif_protocollo: flg_allegato_rif_protocollo,
      });
    }
    return integrazioneIstanzaForPut;
  }

  _getIntegrazioneIstanzaForPutWhenDeleteAllegato(
    idIstanza: number,
    allegato: Allegato,
    flg_allegato_rif_protocollo: boolean = false
  ): IntegrazioneIstanza {
    const integrazioneIstanza = this.getIntegrazioneIstanza(idIstanza);
    const tipoIntegrazione = this.getTipoIntegrazione(idIstanza);
    if (
      !tipoIntegrazione ||
      !integrazioneIstanza ||
      integrazioneIstanza?.id_istanza !== allegato.id_istanza
    ) {
      return null;
    }
    let integrazioneIstanzaForPut: IntegrazioneIstanza = {
      ...integrazioneIstanza,
    };
    if (!integrazioneIstanzaForPut.allegato_integrazione) {
      return null;
    }
    const toRemoveIndexOfObject =
      integrazioneIstanzaForPut.allegato_integrazione.findIndex((object) => {
        return object.id_allegato_istanza === allegato.id_allegato_istanza;
      });
    if (toRemoveIndexOfObject > -1) {
      integrazioneIstanzaForPut.allegato_integrazione.splice(
        toRemoveIndexOfObject,
        1
      );
    }
    return integrazioneIstanzaForPut;
  }

  callbackDeleteAllegati(
    allegato: Allegato,
    idIstanza: number
  ): Observable<IntegrazioneIstanza> {
    const integrazioneIstanzaForPut =
      this._getIntegrazioneIstanzaForPutWhenDeleteAllegato(idIstanza, allegato);
    if (integrazioneIstanzaForPut) {
      return this._putIntegrazione(integrazioneIstanzaForPut, idIstanza);
    }
    return of(null);
  }

  private _putIntegrazione(
    integrazioneIstanza: Partial<IntegrazioneIstanza>,
    idIstanza: number
  ): Observable<IntegrazioneIstanza> {
    return this.apiPut(integrazioneIstanza).pipe(
      map((res: IntegrazioneIstanza[]) => {
        return this._responseIntegrazione(res, idIstanza);
      })
    );
  }

  public apiPut(
    integrazioneIstanza: Partial<IntegrazioneIstanza>
  ): Observable<IntegrazioneIstanza[]> {
    return this.http.put<IntegrazioneIstanza[]>(
      `${this.beUrl}/integrazioni/`,
      integrazioneIstanza
    );
  }

  private _postIntegrazione(istanza: Istanza): Observable<IntegrazioneIstanza> {
    return this.http
      .post(`${this.beUrl}/integrazioni/`, {
        id_istanza: istanza.id_istanza,
        tipo_integrazione: {
          cod_tipo_integrazione: this.getTipoIntegrazione(istanza.id_istanza)
            .cod_tipo_integra_allegato,
        },
      })
      .pipe(
        map((res: IntegrazioneIstanza[]) => {
          return this._responseIntegrazione(res, istanza.id_istanza);
        })
      );
  }

  private _responseIntegrazione(
    res: IntegrazioneIstanza[],
    idIstanza: number
  ): IntegrazioneIstanza {
    this.setIntegrazioneIstanza(res[0], idIstanza);
    this._emitStatus(idIstanza);
    return res[0];
  }

  private _reset() {}

  setTipoIntegrazione(v: IntegraAllegato, idIstanza: number) {
    const index = this._tipoIntegrazione.findIndex((object) => {
      return object.idIstanza === idIstanza;
    });
    if (index > -1) {
      this._tipoIntegrazione[index] = {
        idIstanza: idIstanza,
        tipoIntegrazione: v,
      };
    } else {
      this._tipoIntegrazione.push({
        idIstanza: idIstanza,
        tipoIntegrazione: v,
      });
    }
  }

  getTipoIntegrazione(idIstanza: number): IntegraAllegato {
    return this._tipoIntegrazione.find((i) => i.idIstanza === idIstanza)
      ?.tipoIntegrazione;
  }

  setIntegrazioneIstanza(v: IntegrazioneIstanza, idIstanza: number) {
    const index = this._integrazioneIstanza.findIndex((object) => {
      return object.idIstanza === idIstanza;
    });
    if (index > -1) {
      this._integrazioneIstanza[index] = {
        idIstanza: idIstanza,
        integrazioneIstanza: v,
      };
    } else {
      this._integrazioneIstanza.push({
        idIstanza: idIstanza,
        integrazioneIstanza: v,
      });
    }
  }

  getIntegrazioneIstanza(idIstanza: number): IntegrazioneIstanza {
    return this._integrazioneIstanza.find((i) => i.idIstanza === idIstanza)
      ?.integrazioneIstanza;
  }

  setIstanza(v: Istanza, idIstanza: number) {
    const index = this._istanze.findIndex((object) => {
      return object.idIstanza === idIstanza;
    });
    if (index > -1) {
      this._istanze[index] = {
        idIstanza: idIstanza,
        istanza: v,
      };
    } else {
      this._istanze.push({
        idIstanza: idIstanza,
        istanza: v,
      });
    }
  }

  getIstanza(idIstanza: number): Istanza {
    return this._istanze.find((i) => i.idIstanza === idIstanza)?.istanza;
  }

  setStatoPartenzaIntegrazione(v: StatoIstanzaEnum, idIstanza: number) {
    const index = this._statoPartenzaIntegrazione.findIndex((object) => {
      return object.idIstanza === idIstanza;
    });
    if (index > -1) {
      this._statoPartenzaIntegrazione[index] = {
        idIstanza: idIstanza,
        statoPartenzaIntegrazione: v,
      };
    } else {
      this._statoPartenzaIntegrazione.push({
        idIstanza: idIstanza,
        statoPartenzaIntegrazione: v,
      });
    }
  }

  setJsonDataIntegrazioneFromIstanza(istanza: Istanza, idIstanza: number) {
    if (
      JSON.parse(istanza.json_data)[this.CODTIPOQUADRO]?.js_integrazioni
        ?.stato_integrazione
    ) {
      this.setStatoPartenzaIntegrazione(
        JSON.parse(istanza.json_data)[this.CODTIPOQUADRO].js_integrazioni
          .stato_integrazione as StatoIstanzaEnum,
        idIstanza
      );
    }
    if (JSON.parse(istanza.json_data)[this.CODTIPOQUADRO]) {
      this.setJsIntegrazioni(
        JSON.parse(istanza.json_data)[this.CODTIPOQUADRO].js_integrazioni,
        idIstanza
      );
    }
  }

  getStatoPartenzaIntegrazione(idIstanza: number): StatoIstanzaEnum {
    return this._statoPartenzaIntegrazione.find(
      (i) => i.idIstanza === idIstanza
    )?.statoPartenzaIntegrazione as StatoIstanzaEnum;
  }

  setJsIntegrazioni(v: any, idIstanza: number) {
    const index = this._JsIntegrazioni.findIndex((object) => {
      return object.idIstanza === idIstanza;
    });
    if (index > -1) {
      this._JsIntegrazioni[index] = {
        idIstanza: idIstanza,
        jsIntegrazioni: v,
      };
    } else {
      this._JsIntegrazioni.push({
        idIstanza: idIstanza,
        jsIntegrazioni: v,
      });
    }
  }

  getJsIntegrazioni(idIstanza: number): any {
    return this._JsIntegrazioni.find((i) => i.idIstanza === idIstanza)
      ?.jsIntegrazioni;
  }

  _getDisabledAndReadonly(idIstanza: number) {
    const istanza: Istanza = this.getIstanza(idIstanza);
    const integrazioneIstanza: IntegrazioneIstanza =
      this.getIntegrazioneIstanza(idIstanza);

    let disabled = {
      btn_prosegui_integr_disabled: true,
      btn_download_lett_accomp_disabled: true,
      btn_upload_lett_accomp_disabled: true,
      btn_download_elen_integ_disabled: true,
      pan_lettera_accompagnamento_disabled: true,
      btn_integra_allegati_disabled: true,
      btn_perfeziona_allegati_disabled: true,
      btn_integra_allegati_succ_disabled: true,
      btn_annulla_pubblica_disabled: true,
    };

    const statoIstanza: StatoIstanzaEnum = istanza.stato_istanza
      .codice_stato_istanza as StatoIstanzaEnum;
    const firstStep =
      statoIstanza === StatoIstanzaEnum.PERF_ALLEGATI ||
      statoIstanza === StatoIstanzaEnum.RIC_INTEGR ||
      statoIstanza === StatoIstanzaEnum.RIC_INTEGR_SUCC
        ? true
        : false;
    const secondStep =
      statoIstanza === StatoIstanzaEnum.DA_SCARICARE_ELENCO ? true : false;
    const thirdStep =
      statoIstanza === StatoIstanzaEnum.DA_SCARICARE_LETTERA ? true : false;
    const fourthStep =
      statoIstanza === StatoIstanzaEnum.DA_FIRMARE_LETTERA ? true : false;
    const fifthStep =
      statoIstanza === StatoIstanzaEnum.DA_CONFERMARE_INTEG ? true : false;

    // SCRIVA-1319 con subtask SCRIVA-1337
    let readonlyAllegati = false;

    if (
      firstStep ||
      secondStep ||
      secondStep ||
      thirdStep ||
      fourthStep ||
      fifthStep
    ) {
      readonlyAllegati = firstStep ? false : true;
      if (integrazioneIstanza.allegato_integrazione?.length > 0) {
        if (firstStep) {
          disabled.btn_prosegui_integr_disabled = false;
        }

        // se lo stato è il secondo
        if (secondStep) {
          disabled.btn_download_elen_integ_disabled = false;
          disabled.btn_annulla_pubblica_disabled = false;
        }

        if (thirdStep) {
          disabled.btn_download_lett_accomp_disabled = false;
          disabled.btn_annulla_pubblica_disabled = false;
        }

        if (fourthStep) {
          disabled.btn_upload_lett_accomp_disabled = false;
          disabled.btn_annulla_pubblica_disabled = false;
        }

        if (fifthStep) {
          if (
            this.getStatoPartenzaIntegrazione(idIstanza) ===
            StatoIstanzaEnum.PERF_ALLEGATI
          ) {
            disabled.btn_perfeziona_allegati_disabled = false;
          }

          if (
            this.getStatoPartenzaIntegrazione(idIstanza) ===
            StatoIstanzaEnum.RIC_INTEGR
          ) {
            disabled.btn_integra_allegati_disabled = false;
          }

          if (
            this.getStatoPartenzaIntegrazione(idIstanza) ===
            StatoIstanzaEnum.RIC_INTEGR_SUCC
          ) {
            disabled.btn_integra_allegati_succ_disabled = false;
          }

          disabled.btn_annulla_pubblica_disabled = false;
        }
      }
    }
    return { disabled, readonlyAllegati };
  }

  _emitStatus(idIstanza: number) {
    const statusIstanzaIntegrazione: IntegrazioneIstanzaStatus = {
      idIstanza: idIstanza,
      integrazioneIstanza: this.getIntegrazioneIstanza(idIstanza),
      tipoIntegrazione: this.getTipoIntegrazione(idIstanza),
      tipoAdempimentoOggApp: this.istanzaService.getOggAppPulsanti(),
      ...this._getDisabledAndReadonly(idIstanza),
      istanza: this.getIstanza(idIstanza),
      jsIntegrazioni: this.getJsIntegrazioni(idIstanza),
    };
    this._status$.next(statusIstanzaIntegrazione);
  }

  private _getCodTipoEventoByOggettoApp(
    oggettoApp: OggettoApp,
    def: TipoEventoEnum
  ): TipoEventoEnum {
    return oggettoApp.tipo_evento?.cod_tipo_evento
      ? (oggettoApp.tipo_evento.cod_tipo_evento as TipoEventoEnum)
      : def;
  }

  /**
   * Controllo se ci sono allegati obbligaori su onProsegui
   * @param idIstanza number
   * @returns Observable<boolean>
   */
  private checkOnProsegui(idIstanza: number): Observable<boolean> {
    return this.allegatiService.checkCategorieObbligatorie(idIstanza).pipe(
      map((ret: any) => {
        if (ret.length == 0) {
          return true;
        }
        this._showErrorCategorieObbligatorie();
        return false;
      })
    );
  }

  /**
   * Mostro errore delle categorie obbligaorie
   */
  private _showErrorCategorieObbligatorie() {
    this.messageService.showMessage('E080', this.IDSHOWERRORS, false);
  }

  public onProsegui(istanza: Istanza, oggettoApp: OggettoApp) {
    const idIstanza = istanza.id_istanza;
    this.spinner.show();
    this.checkOnProsegui(idIstanza).subscribe({
      next: (ret: boolean) => {
        if (ret) {
          // Procedo con tutte le chiamate del prosegui
          this._onProseguiGo(istanza, oggettoApp);
        }
      },
      error: (e: any) => {
        this._showError(e, idIstanza);
      },
    });
  }

  private _onProseguiGo(istanza: Istanza, oggettoApp: OggettoApp) {
    const idIstanza = istanza.id_istanza;
    this.spinner.show();
    const dataQuadro = {
      js_integrazioni: {
        stato_integrazione: istanza.stato_istanza.codice_stato_istanza,
      },
    };
    this._saveDataQuadro(idIstanza, dataQuadro, true)
      .pipe(
        switchMap((ret: any) => {
          return this._generaEvento(
            idIstanza,
            this._getCodTipoEventoByOggettoApp(
              oggettoApp,
              TipoEventoEnum.INS_ALL_INTEGR
            )
          );
        }),
        switchMap((ret: any) => {
          return this._reloadIstanza(idIstanza);
        })
      )
      .subscribe({
        next: (istanza: Istanza) => {
          this._onActionCompleted(idIstanza);
        },
        error: (e: any) => {
          this._showError(e, idIstanza);
        },
      });
  }

  private _onActionCompleted(idIstanza: number) {
    this.spinner.hide();
    // ricarico tutte le variabili di stato necessarie ed emetto info aggiornate
    this._emitStatus(idIstanza);
  }

  public onDownloadElenIntegrazioni(istanza: Istanza, oggettoApp: OggettoApp) {
    const idIstanza = istanza.id_istanza;
    this.spinner.show();
    let allegatoElencoAllegati = null;
    const chiave =
      this.getStatoPartenzaIntegrazione(idIstanza) ==
      StatoIstanzaEnum.PERF_ALLEGATI
        ? this.CHIAVEADEMPIELAB
        : this.CHIAVEADEMPIINTEGR;
    let codTipologiaAllegato: CodTipologiaAllegato = null;
    this.configurazioniService
      .getConfigurazioniByInfoAndChiave(
        istanza.adempimento.cod_adempimento,
        TipoInfoAdempimento.elencoDoc,
        chiave
      )
      .pipe(
        switchMap((configAdempimenti: ConfigAdempimento[]) => {
          codTipologiaAllegato = configAdempimenti[0]
            .valore as CodTipologiaAllegato;
          if (codTipologiaAllegato === CodTipologiaAllegato.elencoAllegati) {
            return this._removeAllegatoByCodTipologiaAllegato(
              idIstanza,
              codTipologiaAllegato
            );
          }
          return of(null);
        }),
        switchMap((ret: any) => {
          return this._generateElenco(idIstanza, codTipologiaAllegato);
        }),
        switchMap((ret: any) => {
          return this._saveDataQuadroRiepilogo(idIstanza);
        }),
        switchMap((ret: any) => {
          return this._getElenco(idIstanza, codTipologiaAllegato);
        }),
        switchMap((allegato: Allegato) => {
          allegatoElencoAllegati = allegato;
          const dataQuadro = {
            js_integrazioni: {
              info_lettera_accompagnamento: {
                elenco_integrazioni: {
                  codice_allegato: allegato?.cod_allegato,
                  nome_file: allegato?.nome_allegato,
                },
              },
            },
          };
          return this._saveDataQuadro(idIstanza, dataQuadro);
        }),
        switchMap((dataQuadro: DataQuadro) => {
          const integrazioneIstanzaForPut =
            this._getIntegrazioneIstanzaForPutWhenAddAllegato(
              idIstanza,
              allegatoElencoAllegati
            );
          if (integrazioneIstanzaForPut) {
            return this.apiPut(integrazioneIstanzaForPut);
          }
          return of(null);
        }),
        switchMap((res: IntegrazioneIstanza[]) => {
          if (res) {
            // aggiorno la integrazione istanza corrente in base a risposta
            this.setIntegrazioneIstanza(res[0], idIstanza);
          }
          return this.allegatiService.getAllegatoByUuid(
            allegatoElencoAllegati.uuid_index
          );
        }),
        switchMap((ret: any) => {
          this.downloadFile(allegatoElencoAllegati.nome_allegato, ret);
          return this._generaEvento(
            idIstanza,
            this._getCodTipoEventoByOggettoApp(
              oggettoApp,
              TipoEventoEnum.SCARICA_ELEN_INTEG
            )
          );
        }),
        switchMap((ret: any) => {
          return this._reloadIstanza(idIstanza);
        })
      )
      .subscribe({
        next: (istanza: Istanza) => {
          this._onActionCompleted(idIstanza);
        },
        error: (e: any) => {
          this._showError(e, idIstanza);
        },
      });
  }

  public onDownloadLettAccomp(
    istanza: Istanza,
    oggettoApp: OggettoApp,
    testo_lettera_integrazioni: string = null
  ) {
    const dataQuadro = testo_lettera_integrazioni
      ? {
          js_integrazioni: {
            info_lettera_accompagnamento: {
              testo_lettera_integrazioni: testo_lettera_integrazioni,
            },
          },
        }
      : {
          js_integrazioni: {
            info_lettera_accompagnamento: {},
          },
        };
    const idIstanza = istanza.id_istanza;
    this.spinner.show();
    this._saveDataQuadro(idIstanza, dataQuadro)
      .pipe(
        switchMap((dataQuadro: DataQuadro) => {
          return this.istanzaService.downloadMuduloIstanzaByCodTipologiaAllegato(
            idIstanza,
            CodTipologiaAllegato.lettTrasm
          );
        }),
        switchMap(
          // ret è un response body con il blob del file
          (ret: any) => {
            const nome_file = this.getFilenameByHeader(ret.headers);
            this.downloadFile(nome_file, ret.body);
            return this._generaEvento(
              idIstanza,
              this._getCodTipoEventoByOggettoApp(
                oggettoApp,
                TipoEventoEnum.SCARICATA_LETTERA
              )
            );
          }
        ),
        switchMap((ret: any) => {
          return this._reloadIstanza(idIstanza);
        })
      )
      .subscribe({
        next: (istanza: Istanza) => {
          this._onActionCompleted(idIstanza);
        },
        error: (e: any) => {
          this._showError(e, idIstanza);
        },
      });
  }

  public onUploadLettAccomp(
    istanza: Istanza,
    oggettoApp: OggettoApp,
    files: any
  ) {
    const idIstanza = istanza.id_istanza;
    const file = files[0];
    const tipoIntegraAllegato: IntegraAllegato =
      this.getTipoIntegrazione(idIstanza);
    const statoIstanzaPartenza = this.getStatoPartenzaIntegrazione(idIstanza);

    const dataLettera: Partial<Allegato> = {
      id_istanza: idIstanza,
      nome_allegato: file.name,
      tipo_integra_allegato: tipoIntegraAllegato,
      tipologia_allegato: {
        cod_tipologia_allegato: CodTipologiaAllegato.lettTrasm,
      },
    };
    this.spinner.show();

    const codAdempimento = istanza.adempimento.cod_adempimento;
    const tipoAllegato = CodTipologiaAllegato.lettTrasm;

    let lettAccomp: Allegato = null;

    this._getConfigAllegatoByCodTipologia(codAdempimento, tipoAllegato)
      .pipe(
        switchMap((tipoAllegato: TipoAllegato) => {
          dataLettera.flg_riservato = tipoAllegato.flg_riservato;
          return this.adempimentoService.getStatiIstanzaAdempimento(
            istanza.adempimento.id_adempimento
          );
        }),
        switchMap((ret: AdempimentoStatoIstanza[]) => {
          let currentAdempimentoStatoIstanza = ret.filter(
            (item) =>
              item.stato_istanza.codice_stato_istanza === statoIstanzaPartenza
          );
          if (currentAdempimentoStatoIstanza?.length > 0) {
            dataLettera.classe_allegato =
              currentAdempimentoStatoIstanza[0].classe_allegato;
          }
          return this.allegatiService.postAllegati(
            JSON.stringify(dataLettera),
            file
          );
        }),
        switchMap((allegato: Allegato) => {
          if (allegato.ind_firma === 1 || allegato.ind_firma === 3) {
            this.messageService.showMessage('I009', this.IDSHOWERRORS, false);
          }
          lettAccomp = allegato;
          const dataQuadro = {
            js_integrazioni: {
              info_lettera_accompagnamento: {
                lettera_accompagnamento: {
                  codice_allegato: allegato?.cod_allegato,
                  nome_file: allegato?.nome_allegato,
                },
              },
            },
          };
          return this._saveDataQuadro(idIstanza, dataQuadro);
        }),
        switchMap((dataQuadro: DataQuadro) => {
          const integrazioneIstanzaForPut =
            this._getIntegrazioneIstanzaForPutWhenAddAllegato(
              idIstanza,
              lettAccomp,
              true
            );
          if (integrazioneIstanzaForPut) {
            return this.apiPut(integrazioneIstanzaForPut);
          }
          return of(null);
        }),
        switchMap((res: IntegrazioneIstanza[]) => {
          if (res) {
            // aggiorno la integrazione istanza corrente in base a risposta
            this.setIntegrazioneIstanza(res[0], idIstanza);
          }
          return this._saveDataQuadroRiepilogo(idIstanza);
        }),
        switchMap((ret: any) => {
          return this._generaEvento(
            idIstanza,
            this._getCodTipoEventoByOggettoApp(
              oggettoApp,
              TipoEventoEnum.FIRMATA_LETTERA
            )
          );
        }),
        switchMap((ret: any) => {
          return this._reloadIstanza(idIstanza);
        })
      )
      .subscribe({
        next: (istanza: Istanza) => {
          this.spinner.hide();
          this._onActionCompleted(idIstanza);
        },
        error: (e: any) => {
          this.spinner.hide();
          this._showError(e, idIstanza);
        },
      });
  }

  public onIntegraAllegati(istanza: Istanza, oggettoApp: OggettoApp) {
    this._showConfirm(() => {
      this._onConferma(istanza, oggettoApp, TipoEventoEnum.INT_ALL);
    });
  }

  public onIntegraAllegatiSucc(istanza: Istanza, oggettoApp: OggettoApp) {
    this._showConfirm(() => {
      this._onConferma(istanza, oggettoApp, TipoEventoEnum.INT_ALL);
    });
  }

  public onPerfezionaAllegati(istanza: Istanza, oggettoApp: OggettoApp) {
    this._showConfirm(() => {
      this._onConferma(istanza, oggettoApp, TipoEventoEnum.COMPL_PERF_ALLEGATI);
    });
  }

  private _onConferma(
    istanza: Istanza,
    oggettoApp: OggettoApp,
    defTipoEvento: TipoEventoEnum
  ) {
    let dataInvio = this._convertDateForm4BE(new Date().toISOString());
    let integrazioneIstanza: IntegrazioneIstanza = this.getIntegrazioneIstanza(
      istanza.id_istanza
    );
    integrazioneIstanza.data_invio = dataInvio;
    const idIstanza = istanza.id_istanza;
    this.spinner.show();
    // SCRIVA-1197
    const tipoRichiesta =
      this.getStatoPartenzaIntegrazione(istanza.id_istanza) ===
      StatoIstanzaEnum.PERF_ALLEGATI
        ? 'PERFEZIONAMENTO'
        : 'INTEGRAZIONE';
    this.apiPut(integrazioneIstanza)
      .pipe(
        switchMap((res: IntegrazioneIstanza[]) => {
          if (res) {
            // aggiorno la integrazione istanza corrente in base a risposta
            this.setIntegrazioneIstanza(res[0], idIstanza);
          }
          // SCRIVA-1349 gestUID passato alla generaEvento SCRIVA-1197
          return this._generaEvento(
            idIstanza,
            this._getCodTipoEventoByOggettoApp(oggettoApp, defTipoEvento),
            res[0].gestUID,
            tipoRichiesta
          );
        }),
        switchMap((ret: any) => {
          const dataQuadro = { js_integrazioni: null };
          return this._saveDataQuadro(idIstanza, dataQuadro);
        })
      )
      .subscribe({
        next: (ret: any) => {
          this.messageService.showMessage('P008', 'divBtnConferma', false);
          // torna alla ricerca
          setTimeout(() => {
            this.router.navigate(['/ricerca']);
          }, 1500);
        },
        error: (e: any) => {
          this._showError(e, idIstanza);
        },
      });
  }

  private _showConfirm(callback, codMess: string = 'A031') {
    this.messageService.showConfirmation({
      title: 'Conferma integrazioni',
      codMess: codMess,
      buttons: [
        {
          label: 'ANNULLA',
          type: 'btn-link',
          callback: () => {},
        },
        {
          label: 'CONFERMA',
          type: 'btn-primary',
          callback: () => {
            callback();
          },
        },
      ],
    });
  }

  private _showError(error: any, idIstanza: number) {
    // Chiudo lo spinner
    this.spinner.hide();
    if (error.error?.code) {
      if (error.error.code === 'E015') {
        this.messageService.showMessage(
          error.error.code,
          this.IDSHOWERRORS,
          false,
          null,
          error.error.title
        );
      } else {
        const istanza: Istanza = this.getIstanza(idIstanza);
        this.messageService.showMessage(
          error.error.code,
          this.IDSHOWERRORS,
          false,
          {
            ph: '{PH_STATO_ISTANZA_EVENTO}',
            swap: istanza.stato_istanza.descrizione_stato_istanza
              ? istanza.stato_istanza.descrizione_stato_istanza
              : '',
          }
        );
      }
    } else {
      this.messageService.showMessage('E100', this.IDSHOWERRORS, true);
    }
  }

  public onAnnulla(istanza: Istanza) {
    this._showConfirm(() => {
      this._onAnnulla(istanza);
    }, 'A049');
  }

  /**
   * Richiamo api che ritorna la configurazione di un allegato
   * @param codAdempimento
   * @param codTipologiaAllegato
   */
  private _getConfigAllegatoByCodTipologia(
    codAdempimento: string,
    codTipologiaAllegato: string
  ): Observable<TipoAllegato> {
    return this.allegatiService
      .getConfigAllegatoByCodTipologia(codAdempimento, codTipologiaAllegato)
      .pipe(
        map((tipiAllegato: TipoAllegato[]) => {
          //ritorno solo il primo elemento se esiste
          return tipiAllegato[0];
        })
      );
  }

  /**
   * Cancella fisicamente la lettera
   * @param idIstanza idIstanza number
   * @returns
   */
  private _removeLettera(idIstanza: number) {
    const jsIntegrazioni = this.getJsIntegrazioni(idIstanza);
    const codAllegato =
      jsIntegrazioni?.info_lettera_accompagnamento?.lettera_accompagnamento
        ?.codice_allegato;
    return this._removeFileByCodAllegato(idIstanza, codAllegato);
  }

  /**
   * Cancella fisicamente il file elenco
   * @param idIstanza idIstanza number
   * @returns
   */
  private _removeElenco(idIstanza: number) {
    // SCRIVA-1454
    const jsIntegrazioni = this.getJsIntegrazioni(idIstanza);
    const codAllegato =
      jsIntegrazioni?.info_lettera_accompagnamento?.elenco_integrazioni
        ?.codice_allegato;
    return this._removeFileByCodAllegato(idIstanza, codAllegato);
  }

  /**
   * Cancella fisicamente un file
   * @param idIstanza number
   * @param codAllegato string
   * @returns
   */
  private _removeFileByCodAllegato(idIstanza: number, codAllegato: string) {
    return this.allegatiService.getAllAllegatiIstanza(idIstanza).pipe(
      switchMap((allegati: Allegato[]) => {
        const removed = allegati.filter(
          (elem) => elem.cod_allegato === codAllegato
        );
        if (removed?.length > 0) {
          return this.allegatiService.deleteAllegatoByUuid(
            removed[0].uuid_index
          );
        }
        return of(null);
      })
    );
  }

  private _onAnnulla(istanza: Istanza) {
    const idIstanza = istanza.id_istanza;
    const jsIntegrazioni = this.getJsIntegrazioni(idIstanza);
    const dataQuadro = { js_integrazioni: null };
    // SCRIVA-1454
    const callRemoveElenco = jsIntegrazioni?.info_lettera_accompagnamento
      ?.elenco_integrazioni?.codice_allegato
      ? this._removeElenco(idIstanza)
      : of(null);

    const callRemoveLettera = jsIntegrazioni?.info_lettera_accompagnamento
      ?.lettera_accompagnamento?.codice_allegato
      ? this._removeLettera(idIstanza)
      : of(null);

    this.spinner.show();
    forkJoin(callRemoveElenco, callRemoveLettera)
      .pipe(
        switchMap((resp: any) => {
          const integrazioneIstanza = this.getIntegrazioneIstanza(idIstanza);
          let allegato_integrazione = [];
          // in questo caso devo rimuovere da integrazione corrente sia la lettera che elenco
          const toRemove = [
            jsIntegrazioni?.info_lettera_accompagnamento?.elenco_integrazioni
              ?.codice_allegato,
            jsIntegrazioni?.info_lettera_accompagnamento
              ?.lettera_accompagnamento?.codice_allegato,
          ];
          integrazioneIstanza.allegato_integrazione.forEach((element) => {
            if (toRemove.indexOf(element.cod_allegato) === -1) {
              allegato_integrazione.push(element);
            }
          });
          integrazioneIstanza.allegato_integrazione = allegato_integrazione;
          return this.apiPut(integrazioneIstanza);
        }),
        switchMap((res: IntegrazioneIstanza[]) => {
          if (res) {
            // aggiorno la integrazione istanza corrente in base a risposta
            this.setIntegrazioneIstanza(res[0], idIstanza);
          }
          return this._saveDataQuadro(idIstanza, dataQuadro);
        }),
        switchMap((ret: any) => {
          let tipoEvento;
          if (
            this.getStatoPartenzaIntegrazione(idIstanza) ==
            StatoIstanzaEnum.PERF_ALLEGATI
          ) {
            tipoEvento = TipoEventoEnum.ANNULLA_PERFALLEGATI;
          } else if (
            this.getStatoPartenzaIntegrazione(idIstanza) ==
            StatoIstanzaEnum.RIC_INTEGR
          ) {
            tipoEvento = TipoEventoEnum.ANNULLA_RICH_INTEGR;
          } else if (
            this.getStatoPartenzaIntegrazione(idIstanza) ==
            StatoIstanzaEnum.RIC_INTEGR_SUCC
          ) {
            tipoEvento = TipoEventoEnum.ANNULLA_RIC_INT_SUCC;
          }
          return this._generaEvento(idIstanza, tipoEvento);
        }),
        switchMap((ret: any) => {
          return this._reloadIstanza(idIstanza);
        })
      )
      .subscribe({
        next: (istanza: Istanza) => {
          this._onActionCompleted(idIstanza);
          // A seguito dell'aggiornamento dati dell'istanza, emetto un evento specifico x l'annulla
          this.onAnnullaIntegrazione$.next(istanza);
          // #
        },
        error: (e: any) => {
          this._showError(e, istanza.id_istanza);
        },
      });
  }

  public onDeleteLettera(istanza: Istanza) {
    const idIstanza = istanza.id_istanza;
    const jsIntegrazioni = this.getJsIntegrazioni(idIstanza);
    const dataQuadro = { js_integrazioni: jsIntegrazioni };
    this.spinner.show();
    this._removeLettera(idIstanza)
      .pipe(
        switchMap((allegatoResp: any) => {
          const integrazioneIstanza = this.getIntegrazioneIstanza(idIstanza);
          let allegato_integrazione = [];
          // in questo caso devo rimuovere da integrazione corrente solo la lettera
          const toRemove = [
            jsIntegrazioni?.info_lettera_accompagnamento
              ?.lettera_accompagnamento?.codice_allegato,
          ];
          integrazioneIstanza.allegato_integrazione.forEach((element) => {
            if (toRemove.indexOf(element.cod_allegato) === -1) {
              allegato_integrazione.push(element);
            }
          });
          integrazioneIstanza.allegato_integrazione = allegato_integrazione;
          return this.apiPut(integrazioneIstanza);
        }),
        switchMap((res: IntegrazioneIstanza[]) => {
          if (res) {
            // aggiorno la integrazione istanza corrente in base a risposta
            this.setIntegrazioneIstanza(res[0], idIstanza);
          }
          // data quadro senza lettera accompagnamento nel json
          delete dataQuadro.js_integrazioni.info_lettera_accompagnamento
            .lettera_accompagnamento;
          return this._saveDataQuadro(idIstanza, dataQuadro);
        }),
        switchMap((ret: any) => {
          return this._generaEvento(
            idIstanza,
            TipoEventoEnum.SCARICATA_LETTERA
          );
        }),
        switchMap((ret: any) => {
          return this._reloadIstanza(idIstanza);
        })
      )
      .subscribe({
        next: (istanza: Istanza) => {
          this._onActionCompleted(idIstanza);
        },
        error: (e: any) => {
          this._showError(e, idIstanza);
        },
      });
  }

  private _generaEvento(
    idIstanza: number,
    codTipoEvento: TipoEventoEnum,
    uid_richiesta: string = '',
    tipo_richiesta: string = ''
  ) {
    return this.istanzaService.generaEvento(
      idIstanza,
      codTipoEvento,
      null,
      null,
      null,
      null,
      uid_richiesta,
      tipo_richiesta
    );
  }

  private _saveDataQuadroRiepilogo(idIstanza: number): Observable<DataQuadro> {
    return this.allegatiService.getAllAllegatiIstanza(idIstanza).pipe(
      switchMap((allegatiFullList: Allegato[]) => {
        const istanza: Istanza = this.getIstanza(idIstanza);
        // TODO duplicate from allegati
        const docSYS = allegatiFullList.filter(
          (doc) =>
            doc.tipologia_allegato.categoria_allegato.cod_categoria_allegato ===
              'SYS' &&
            doc.tipologia_allegato.cod_tipologia_allegato.includes('ELENCO_')
        );
        let qdr_riepilogo = JSON.parse(istanza.json_data)[
          this.CODTIPOQUADRORIEPILOGO
        ];
        let documentiDiSistema = [];
        docSYS.forEach((doc) => {
          const dataFullSplit = doc.data_upload.split(' ');
          const dataSplit = dataFullSplit[0].split('-');
          const dataUpload =
            dataSplit[2] + '/' + dataSplit[1] + '/' + dataSplit[0];
          const timeUpload = dataFullSplit[1];
          documentiDiSistema.push({
            nomeDocumento: doc.nome_allegato,
            dataDocumento: dataUpload + ' ' + timeUpload,
            codAllegato: doc.cod_allegato,
            codTipologia: doc.tipologia_allegato.cod_tipologia_allegato,
          });
        });
        qdr_riepilogo[this.CODTIPOQUADRO].documentiDiSistema =
          documentiDiSistema;
        const requestDataRiepilogo = {
          id_istanza: idIstanza,
          cod_tipo_quadro: this.CODTIPOQUADRORIEPILOGO,
          json_data_quadro: JSON.stringify(qdr_riepilogo),
        };
        return this.stepManagerService.salvaJsonDataQuadro(
          requestDataRiepilogo,
          true
        );
      })
    );
  }

  private _saveDataQuadro(
    idIstanza: number,
    dataQuadro: any,
    remove: boolean = false
  ): Observable<DataQuadro> {
    if (!remove) {
      // recupero il vecchio js_integrazioni
      let js_integrazioni: any = this.getJsIntegrazioni(idIstanza);
      // faccio un merge
      if (js_integrazioni) {
        js_integrazioni = {
          js_integrazioni: js_integrazioni,
        };
        dataQuadro = _.merge(js_integrazioni, dataQuadro);
      }
    }

    const requestDataQuadro = {
      id_istanza: idIstanza,
      cod_tipo_quadro: this.CODTIPOQUADRO,
      json_data_quadro: JSON.stringify(dataQuadro),
    };
    return this.stepManagerService.salvaJsonDataQuadro(requestDataQuadro, true);
  }

  private _removeElencoAllegati(idIstanza: number): Observable<Allegato> {
    return this._removeAllegatoByCodTipologiaAllegato(
      idIstanza,
      CodTipologiaAllegato.elencoAllegati
    );
  }

  private _removeAllegato(
    idIstanza: number,
    allegato: Partial<Allegato>
  ): Observable<Allegato> {
    const request: Partial<Allegato> = {
      ...allegato,
      flg_cancellato: true,
      data_cancellazione: this._convertDateForm4BE(
        new Date().toISOString().slice(0, 10)
      ),
    };
    return this.allegatiService.updateAllegati(request);
  }

  private _removeAllegatoByCodAllegato(
    idIstanza: number,
    codAllegato: string
  ): Observable<Allegato[]> {
    return this.allegatiService.getAllAllegatiIstanza(idIstanza).pipe(
      switchMap((allegati: Allegato[]) => {
        const removed = allegati.filter(
          (elem) => elem.cod_allegato === codAllegato
        );
        if (removed?.length > 0) {
          return this._removeAllegato(idIstanza, removed[0]);
        }
        return of(null);
      })
    );
  }

  private _removeAllegatoByCodTipologiaAllegato(
    idIstanza: number,
    codTipologiaAllegato: CodTipologiaAllegato
  ): Observable<Allegato> {
    return this.allegatiService.getAllAllegatiIstanza(idIstanza).pipe(
      switchMap((allegati: Allegato[]) => {
        const elencoAllegati = allegati.filter(
          (elem) =>
            elem.tipologia_allegato.cod_tipologia_allegato ===
            codTipologiaAllegato
        );
        if (elencoAllegati?.length > 0) {
          return this._removeAllegato(idIstanza, elencoAllegati[0]);
        }
        return of(null);
      })
    );
  }

  private _generateElenco(
    idIstanza: number,
    codTipologiaAllegato: CodTipologiaAllegato
  ) {
    return this.istanzaService.downloadMuduloIstanzaByCodTipologiaAllegato(
      idIstanza,
      codTipologiaAllegato,
      true,
      false
    );
  }

  _getElenco(idIstanza, cod_tipologia_allegato): Observable<Allegato> {
    return this.allegatiService.getAllAllegatiIstanza(idIstanza).pipe(
      switchMap((allegati: Allegato[]) => {
        // devo prendere sempre ultimo elenco
        // SCRIVA-1319 con subtask SCRIVA-1338
        let elencoAllegati = allegati
          .filter(
            (elem) =>
              elem.tipologia_allegato.cod_tipologia_allegato ===
              cod_tipologia_allegato
          )
          .sort((a: Allegato, b: Allegato) =>
            ('' + b.data_upload).localeCompare(a.data_upload)
          );
        if (elencoAllegati?.length > 0) {
          return of(elencoAllegati[0]);
        }
        return of(null);
      })
    );
  }

  private _reloadIstanza(idIstanza: number): Observable<Istanza> {
    return this.istanzaService.getIstanzaById(idIstanza).pipe(
      tap((istanza: Istanza) => {
        this.setIstanza(istanza, istanza.id_istanza);
        this.setJsonDataIntegrazioneFromIstanza(istanza, istanza.id_istanza);
      })
    );
  }

  // TODO migrare
  /**
   * utility che converte la data del form in data per il BE
   * @param date stringa
   * @returns stringa in formato yyyy-MM-dd HH:mm:ss per salvataggio a BE
   */
  private _convertDateForm4BE(
    date: string,
    format: string = 'yyyy-MM-dd HH:mm:ss'
  ): string {
    return this.datePipe.transform(date, format);
  }

  downloadFile(nome_file: string, response: any) {
    const nameArr = nome_file.split('.');
    const blob = new Blob([response], {
      type: 'application/' + nameArr[nameArr.length - 1],
    });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = nome_file;
    a.click();
    window.URL.revokeObjectURL(url);
    a.remove();
  }

  getFilenameByHeader(headers: any) {
    const contentDispositionHeader = headers.get('Content-Disposition');
    let fileName = contentDispositionHeader.split('filename="')[1];
    fileName = fileName.slice(0, -1);
    return fileName;
  }

  /**
   * Recupero se disabiltare azione in input per allegato in input ed integrazione corrente
   * implementazione degli alg_perfezionamento e alg_integrazione
   * @param idIstanza number
   * @param allegato Allegato
   * @param action 'delete'| 'deletelogic'|'edit'
   * @returns boolean
   */
  disableAllegato(
    idIstanza: number,
    allegato: Allegato,
    action: 'delete' | 'deletelogic' | 'edit'
  ): boolean {
    const integrazioneIstanza: IntegrazioneIstanza =
      this.getIntegrazioneIstanza(idIstanza);
    // caso di non integrazione che non dovrebbe mai verificarsi
    if (!integrazioneIstanza) {
      return false;
    }

    if (action === 'deletelogic') {
      if (integrazioneIstanza.tipo_integrazione?.cod_tipo_integrazione === 'P') {
        if (
          !integrazioneIstanza.allegato_integrazione?.find(
            (a) => a.id_allegato_istanza === allegato.id_allegato_istanza
          )
        ) {
          return false;
        }
      }
      return true;
    }

    // in caso di perfezionamento abilito edit e delete per integrazione corrente
    if (
      allegato.tipo_integra_allegato?.cod_tipo_integra_allegato === 'P' ||
      allegato.tipo_integra_allegato?.cod_tipo_integra_allegato === 'I'
    ) {
      // se perfezionamento ed è presente tra gli allegati correnti
      if (
        integrazioneIstanza.allegato_integrazione?.find(
          (a) => a.id_allegato_istanza === allegato.id_allegato_istanza
        )
      ) {
        return false;
      }
      return true;
    }
    // disabilito se non sono allegati integrazione o perfezionamento in ogni caso
    return true;
  }
}
