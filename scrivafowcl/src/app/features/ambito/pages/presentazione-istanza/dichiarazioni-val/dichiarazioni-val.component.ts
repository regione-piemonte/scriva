/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { DatePipe } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin, Observable, of } from 'rxjs';
import { catchError, map, switchMap, tap } from 'rxjs/operators';
import { CONFIG } from 'src/app/shared/config.injectiontoken';
import {
  Adempimento,
  CompetenzaTerritorio,
  ConfigAdempimento,
  Istanza,
  IstanzaCompetenza,
  StepConfig,
} from 'src/app/shared/models';
import {
  AdempimentoService,
  AuthStoreService,
  ConfigurazioniScrivaService,
  HelpService,
  IstanzaService,
  MessageService,
  StepManagerService,
} from 'src/app/shared/services';
import { TipoInfoAdempimento } from 'src/app/shared/services/configurazioni/utilities/configurazioni.enums';
import { AttoreGestioneIstanzaEnum } from 'src/app/shared/utils';
import { ScrivaServerError } from '../../../../../core/classes/scriva.classes';
import { ScrivaCodesMesseges } from '../../../../../core/enums/scriva-codes-messages.enums';
import { LoggerService } from '../../../../../shared/services/logger.service';
import { AllegatiService } from '../../../services';
import { CodiciAutoritaCompetenti } from '../../orientamento-istanza/utilities/orientamento-istanza.enums';
import { DichiarazioniOrientamentoVALConsts } from './utilities/dichiarazioni-val.consts';
import {
  IDatiACRegionePiemonteVAL,
  IDatiACSpecificaVAL,
  IDichiarazioneACPubVALSpecifica,
  ILinkAcWebVALReq,
  ILinkAcWebVALRes,
} from './utilities/dichiarazioni-val.interfaces';
import { StepperIstanzaComponent } from 'src/app/shared/components/stepper-istanza/stepper-istanza.component';
import { PresentazioneIstanzaService } from '../../../services/presentazione-istanza/presentazione-istanza.service';
import {
  IConfigsSaveQuadro,
  RequestSaveBodyQuadro,
} from 'src/app/shared/components/stepper-istanza/utilities/stepper-istanza.interfaces';

@Component({
  selector: 'app-dichiarazioni-val',
  templateUrl: 'dichiarazioni-val.component.html',
  styleUrls: ['dichiarazioni-val.component.scss'],
})
export class DichiarazioniValComponent
  extends StepperIstanzaComponent
  implements OnInit
{
  /** DichiarazioniVALConsts con le costanti del componente. */
  DVAL_C = new DichiarazioniOrientamentoVALConsts();
  /** CodiciAutoritaCompetenti con i codici costanti per le autorità competenti. */
  codiciAutoritaCompetenti = CodiciAutoritaCompetenti;

  codMaschera = '.MSCR016F';

  formDichiarazioni: FormGroup;
  adempimento: Adempimento;

  istanza: Istanza;

  qdr_dichiarazioni;
  jsonData;
  saveWithPut = false;

  gestioneEnum = AttoreGestioneIstanzaEnum;

  flagVisualizzaTexarea: boolean = false;
  flagVisualizzaSelect: boolean = false;
  info: TipoInfoAdempimento = TipoInfoAdempimento.acWeb;
  acWebPiemonte: string;
  acWebNotPiemonte: string;
  cod_tipo_adempimento: string;
  cod_competenza_territorio: string;
  comune_competenza: string;

  doc_amm_nopubb: boolean = false;
  doc_segr_ind_com: boolean = false;
  doc_segr_ind_com_tab_Arr: any[] = [];

  today = new Date();

  /** IstanzaCompetenza con le informazioni dell'autorità competente principale dell'istanza. */
  autoritaCompetentePrincipale: IstanzaCompetenza;
  /** string contenente il link di accesso per le dichiarazioni PUB rispetto all'autorità competente dell'istanza */
  acWebPub: string;

  constructor(
    private _adempimento: AdempimentoService,
    public router: Router,
    private fb: FormBuilder,
    private _configurazioniScriva: ConfigurazioniScrivaService,
    private allegatiService: AllegatiService,
    private datePipe: DatePipe,
    private _logger: LoggerService,
    presentazioneIstanzaService: PresentazioneIstanzaService,
    @Inject(CONFIG) injConfig: StepConfig,
    messageService: MessageService,
    helpService: HelpService,
    istanzaService: IstanzaService,
    authStoreService: AuthStoreService,
    stepManagerService: StepManagerService,
    spinner: NgxSpinnerService
  ) {
    super(
      presentazioneIstanzaService,
      injConfig,
      messageService,
      helpService,
      istanzaService,
      authStoreService,
      stepManagerService,
      spinner
    );
    this.setVisibilityButtonNext(true);
  }

  ngOnInit() {
    this.spinner.show();
    this.helpService.setCodMaschera(this.codMaschera);
    this.helpService.setCodContesto(undefined);
    this.changed = false;
    this.buildFormDichiarazioni();

    this.loadData();
  }

  /**
   * Funzione che prevede il caricamento di tutte le informazioni per la pagina.
   * @author Ismaele Bottelli
   * @date 09/01/2025
   * @jira SCRIVA-1569
   * @notes E' stato segnalato che la gestione dei dati per le autorità competenti deve passare dallo scarico delle informazioni tramite specifico servizio.
   *        Si modifica quindi il flusso per andare a modificare il set dei dati già impiegati nel componente, aggiungendo informazioni per la gestione delle dichiarazioni.
   */
  loadData() {
    // Gestisco la logica per il set della modalità di gestione del quadro
    this.setGestioneQuadro();

    if (this.attoreGestioneIstanza === this.gestioneEnum.WRITE) {
      this.formDichiarazioni.enable();
    } else {
      this.formDichiarazioni.disable();
    }

    this.spinner.show();
    this.istanzaService
      .getIstanzaById(this.idIstanza)
      .pipe(
        // Dopo aver scaricato i dati dell'istanza, si vanno a scaricare i dati ATTIVI delle autorità competenti
        switchMap((istanza: Istanza) => {
          // Richiamo il servizio di scarico dati delle autorità competente, ma alla fine, per mantenere il flusso funzionante, ripasso l'istanza ottenuta prima
          return this.getAutoritaCompetentiIstanza$(istanza).pipe(
            catchError((e: ScrivaServerError) => {
              // Potrebbe accadare che per qualche motivo si spacchi qualche chiamata per la gestione dell'autorità competente, intercetto l'errore e lascio proseguire il flusso
              this.onServiziError(e);
              // Lascio proseguire il flusso per l'istanza, per l'ac verrà segnalato errore
              return of(istanza);
              // #
            }),
            map((response: ILinkAcWebVALRes) => {
              // Bisogna tornare l'istanza per mantenere il flusso coerente
              return istanza;
              // #
            })
          );
        })
      )
      .subscribe({
        next: (istanza: Istanza) => {
          this.istanza = istanza;
          this.adempimento = this.istanza.adempimento;
          this.jsonData = JSON.parse(this.istanza.json_data);

          // Recupero dall'istanza l'oggetto dell'adempimento
          const adempimento: Adempimento = this.adempimento;
          // Lancio il setup per il contesto dell'helper
          this.helpService.setCodContestoFromAdempimento(adempimento);

          this.cod_tipo_adempimento =
            this.adempimento.tipo_adempimento.cod_tipo_adempimento;

          this.qdr_riepilogo = this.jsonData.QDR_RIEPILOGO;
          const qdrDichiarazioni = this.jsonData[this.codTipoQuadro];
          if (qdrDichiarazioni) {
            this.saveWithPut = true;
          }
          if (
            JSON.parse(istanza.json_data).QDR_CONFIG[this.codTipoQuadro]
              ?.solo_lettura
          ) {
            this.attoreGestioneIstanza = this.gestioneEnum.READ_ONLY as string;
          }

          // this.linkPrivacy(CodiciAutoritaCompetenti.regionePiemonte);
          // if (this.cod_competenza_territorio != CodiciAutoritaCompetenti.regionePiemonte) {
          //   // Se presente un comune di competenza territoriale diverso da Regione Piemonete
          //   // setto i validatori di dchr_info_dati_personali_2
          //   this.formDichiarazioni
          //     .get('dchr_info_dati_personali_2')
          //     .setValue(true);
          //   //.setValidators(Validators.required);
          //   this.formDichiarazioni
          //     .get('dchr_info_dati_personali_2')
          //     .updateValueAndValidity();
          //   this.linkPrivacy(this.cod_competenza_territorio);
          // }
          this.getCategoriaAllegati(qdrDichiarazioni);
          this.spinner.hide();
        },
        error: (e: ScrivaServerError) => {
          this.spinner.hide();
          this.showErrorsQuadroConCodeENoCode(e, 'containerDichiarazioni');
        },
      });
  }

  /**
   * Funzione che dato l'id istanza, prevede lo scarico e la gestione delle informazioni delle autorità competenti dell'istanza.
   * Le informazioni verranno scaricate "fresche" poiché potrebbero essere diverse rispetto a quelle del quadro orientamento.
   * La funzione prevederà inoltre tutte le logiche di set dei dati all'interno delle variabili del componente.
   * @param istanza Istanza con le informazioni dell'istanza scaricata.
   * @returns Observable<ILinkAcWebVALRes> con le informazioni scaricate.
   */
  private getAutoritaCompetentiIstanza$(
    istanza: Istanza
  ): Observable<ILinkAcWebVALRes> {
    // Estraggo le informazioni per lo scarico dati
    const idIstanza: number = istanza?.id_istanza;
    const adempimento: Adempimento = istanza?.adempimento;

    // Lancio la chiamata per lo scarico delle informazioni per l'autorità competente principale
    return this._adempimento
      .getAutoritaCompetentePrincipaleByIstanza(idIstanza)
      .pipe(
        tap((acPrincipale: IstanzaCompetenza) => {
          // Gestisco il set dei dati nel componente dall'autorità competente principale
          this.setDatiAutoritaCompetentePrincipale(acPrincipale);
          // #
        }),
        switchMap((acPrincipale: IstanzaCompetenza) => {
          // Richiamo il servizio che scarichi tutte le informazioni per i link web dell'AC dell'istanza
          return this.scaricaLinkAcWebIstanza$(acPrincipale, adempimento);
          // #
        })
      );
  }

  /**
   * Funzione che gestisce il set dei dati da un oggetto contenente le informazioni dell'autorità competente all'interno del componente.
   * @param autoritaCompetentePrincipale IstanzaCompetenza con le informazioni dell'autorità competente principale dell'istanza.
   */
  private setDatiAutoritaCompetentePrincipale(
    autoritaCompetentePrincipale: IstanzaCompetenza
  ) {
    // Assegno localmente le informazioni per l'autorità competente principale
    this.autoritaCompetentePrincipale = autoritaCompetentePrincipale;

    // Recupero l'oggetto per la gestione della competenza territorio
    let competenzaTerritorio: CompetenzaTerritorio;
    competenzaTerritorio = autoritaCompetentePrincipale.competenza_territorio;
    // Recupero le informazioni principali per la gestione dei dati
    this.cod_competenza_territorio =
      competenzaTerritorio?.cod_competenza_territorio;
    this.comune_competenza = competenzaTerritorio?.des_competenza_territorio;
    // #
  }

  /**
   * Funzione che gestisce lo scarico dei link per ogni dichiarazione che ne necessita per l'istanza.
   * @param acPrincipale IstanzaCompetenza con le informazioni dell'autorità componente principale.
   * @param adempimento Adempimento con le informazioni dell'adempimento dell'istanza.
   * @returns Observable<ILinkAcWebVALReq> con il risultato dello scarico dati per i link delle ac web istanza.
   */
  private scaricaLinkAcWebIstanza$(
    acPrincipale: IstanzaCompetenza,
    adempimento: Adempimento
  ): Observable<ILinkAcWebVALRes> {
    // Estraggo dall'input le informazioni per lo scarico dati
    const codiceTerritorioAC: string =
      acPrincipale?.competenza_territorio?.cod_competenza_territorio;
    // Estraggo il codice adempimento per lo scarico dati
    const codiceAdempimento: string = adempimento?.cod_adempimento;

    // Definisco l'oggetto con le richieste dati per i link
    const linkAcWebReq: ILinkAcWebVALReq = {
      linkAcWebGDPRRegione:
        this.getAcWebGDPRRegionePiemonteIstanzaVIA$(codiceAdempimento),
      linkAcWebPub: this.getAcWebPubIstanzaVIA$(
        acPrincipale,
        codiceAdempimento
      ),
    };

    // Verifico se il codice territorio AC è diverso da regione piemonte
    if (codiceTerritorioAC !== CodiciAutoritaCompetenti.regionePiemonte) {
      // L'AC è una specifica, non regione, aggiungo la chiamata per il link
      linkAcWebReq.linkAcWebGDPRAltraAC =
        this.getAcWebGDPRACSpecificaIstanzaVIA$(
          codiceTerritorioAC,
          codiceAdempimento
        );
      // #
    }

    // Richiamo e ritorno l'insieme delle chiamate
    return forkJoin(linkAcWebReq);
  }

  /**
   * Funzione che scarica la configurazione relativa all'autorità competente dell'istanza per il link della privacy a seconda del codice competenza del territorio passato.
   * @param codiceTerritorioAC string con il codice della competenza territorio per lo scarico del link.
   * @param codiceAdempimento string con il codice dell'adempimento di riferimento per lo scarico dati.
   * @returns Observable<ConfigAdempimento> con la configurazione contenente i dati per il link.
   * @author Ismaele Bottelli
   * @date 15/01/2025
   * @jira SCRIVA-1569
   * @notes Quando si va a scaricare i link per la dichiarazione della privacy (GDPR) bisogna SEMPRE scaricarica le informazioni di Regione Piemonte.
   *        Se poi il codice della competenza territorio è di un'altra AC rispetto a regione, si scarica il link specifico dell'ac specifica.
   */
  private getAcWebGDPRIstanzaVIA$(
    codiceTerritorioAC: string,
    codiceAdempimento: string
  ): Observable<ConfigAdempimento> {
    // console.log('cod_adempimento: ',this.adempimento.cod_adempimento)         // VIA VAL o VER
    // console.log('info: ',this.info)                                           // WEB_AC_GDPR
    // console.log('cod_competenza_territorio: ',this.cod_competenza_territorio) // A1600A:regione Piemonte - Z000A:comune Rivalta di Torino
    // console.log('cod_tipo_adempimento: ',this.cod_tipo_adempimento)           // VIA

    // Definisco le informazioni per lo scarico del link
    const info: TipoInfoAdempimento = this.info;
    const chiave: string = codiceTerritorioAC;

    // Richiamo il servizio che scarica le informazioni per link
    return this._configurazioniScriva.getConfigurazioneByInfoAndChiave(
      codiceAdempimento,
      info,
      chiave
    );
  }

  /**
   * Funzione che scarica la configurazione per il link della privacy GPDR specifica per Regione Piemonte.
   * La funzione si occuperà anche di impostare il dato una volta ottenuta risposta.
   * @param codiceAdempimento string con il codice dell'adempimento di riferimento per lo scarico dati.
   * @returns Observable<ConfigAdempimento> con la configurazione contenente i dati per il link.
   * @author Ismaele Bottelli
   * @date 15/01/2025
   * @jira SCRIVA-1569
   */
  private getAcWebGDPRRegionePiemonteIstanzaVIA$(
    codiceAdempimento: string
  ): Observable<ConfigAdempimento> {
    // Richiamo il servizio che scarica le informazioni per link
    return this.getAcWebGDPRIstanzaVIA$(
      CodiciAutoritaCompetenti.regionePiemonte,
      codiceAdempimento
    ).pipe(
      catchError((e: ScrivaServerError) => {
        // In caso di errore gestisco comunque la possibilità di continuare con la logica
        this._logger.error('getAcWebGDPRRegionePiemonteIstanzaVIA$', {
          error: e,
        });
        // Ritorno undefined come risultato
        return of(undefined);
        // #
      }),
      tap((configAdempimento: ConfigAdempimento) => {
        // Assegno il link tramite proprietà "valore" alla variabile globale
        this.acWebPiemonte = configAdempimento?.valore ?? '';
        // #
      })
    );
  }

  /**
   * Funzione che scarica la configurazione per il link della privacy GPDR per un'AC specifica diversa da Regione Piemonte.
   * La funzione si occuperà anche di impostare il dato una volta ottenuta risposta.
   * @param codiceTerritorioAC string con il codice della competenza territorio per lo scarico del link.
   * @param codiceAdempimento string con il codice dell'adempimento di riferimento per lo scarico dati.
   * @returns Observable<ConfigAdempimento> con la configurazione contenente i dati per il link.
   * @author Ismaele Bottelli
   * @date 15/01/2025
   * @jira SCRIVA-1569
   */
  private getAcWebGDPRACSpecificaIstanzaVIA$(
    codiceTerritorioAC: string,
    codiceAdempimento: string
  ): Observable<ConfigAdempimento> {
    // Richiamo il servizio che scarica le informazioni per link
    return this.getAcWebGDPRIstanzaVIA$(
      codiceTerritorioAC,
      codiceAdempimento
    ).pipe(
      catchError((e: ScrivaServerError) => {
        // In caso di errore gestisco comunque la possibilità di continuare con la logica
        this._logger.error('getAcWebGDPRACSpecificaIstanzaVIA$', { error: e });
        // Ritorno undefined come risultato
        return of(undefined);
        // #
      }),
      tap((configAdempimento: ConfigAdempimento) => {
        // Assegno il link tramite proprietà "valore" alla variabile globale
        this.acWebNotPiemonte = configAdempimento?.valore ?? '';
        // #
      })
    );
  }

  /**
   * Funzione che data l'autorità competente principale, scarica il link di puntamento relativo all'autorità competente di PUB.
   * @param autoritaCompetentePrincipale IstanzaCompetenza con le informazioni dell'autorità componente principale.
   * @returns Observable<any> con le informazioni scaricate.
   */
  private getAcWebPubIstanzaVIA$(
    autoritaCompetentePrincipale: IstanzaCompetenza,
    codAdempimento: string
  ): Observable<ConfigAdempimento> {
    // Per lo scarico dei dati delle competenze territorio, recupero le informazioni per il servizio
    const info: TipoInfoAdempimento = this.DVAL_C.INFO_VIA_DICH_PUB;
    const chiave: string =
      autoritaCompetentePrincipale?.competenza_territorio
        ?.cod_competenza_territorio;

    // Richiamo il servizio che scarichi in maniera puntuale le informazioni delle competenze territorio dell'autorità competente principale
    return this._configurazioniScriva
      .getConfigurazioneByInfoAndChiave(codAdempimento, info, chiave)
      .pipe(
        catchError((e: ScrivaServerError) => {
          // In caso di errore gestisco comunque la possibilità di continuare con la logica
          this._logger.error('getAcWebPubIstanzaVIA$', { error: e });
          // Ritorno undefined come risultato
          return of(undefined);
          // #
        }),
        tap((configAdempimento: ConfigAdempimento) => {
          // Imposto a livello globale l'informazione per il link webAcPub
          this.acWebPub = configAdempimento?.valore;
        })
      );
    // #
  }

  maxDateValidator(control: AbstractControl): ValidationErrors | null {
    const selectedDate = new Date(control.value);
    const maxDate = new Date();
    return selectedDate <= maxDate ? null : { maxDateExceededToday: true };
  }

  private disabilitaInput() {
    this.formDichiarazioni.get('dich1_num').disable();
    this.formDichiarazioni.get('dich1_data').disable();
    this.formDichiarazioni.get('dich2_num').disable();
    this.formDichiarazioni.get('dich2_data').disable();
    this.formDichiarazioni.get('dich3_num').disable();
    this.formDichiarazioni.get('dich3_data').disable();
    this.formDichiarazioni.get('dich4_num').disable();
    this.formDichiarazioni.get('dich4_data').disable();
    this.formDichiarazioni.get('dich5_num').disable();
    this.formDichiarazioni.get('dich5_data').disable();
    this.formDichiarazioni.get('dich6_num').disable();
    this.formDichiarazioni.get('dich6_data').disable();
  }

  private getCaricaQdrDichiarazioni(q: any) {
    this.formDichiarazioni.get('dchr_via_prel').setValue(q.dchr_via_prel.check);
    this.formDichiarazioni.get('dich1_num').setValue(q.dchr_via_prel.num);
    this.formDichiarazioni.get('dich1_data').setValue(q.dchr_via_prel.data);
    if (q.dchr_via_prel.check) {
      this.formDichiarazioni.get('dich1_num').enable();
      this.formDichiarazioni.get('dich1_data').enable();
      // abilito i validatori
      this.formDichiarazioni
        .get('dich1_num')
        .setValidators(Validators.required);
      this.formDichiarazioni.get('dich1_num').updateValueAndValidity();
      this.formDichiarazioni
        .get('dich1_data')
        .setValidators([Validators.required, this.maxDateValidator]);
      this.formDichiarazioni.get('dich1_data').updateValueAndValidity();
    } else {
      this.formDichiarazioni.get('dich1_num').disable();
      this.formDichiarazioni.get('dich1_data').disable();
    }

    this.formDichiarazioni.get('dchr_via_ver').setValue(q.dchr_via_ver.check);
    this.formDichiarazioni.get('dich2_num').setValue(q.dchr_via_ver.num);
    this.formDichiarazioni.get('dich2_data').setValue(q.dchr_via_ver.data);
    if (q.dchr_via_ver.check) {
      this.formDichiarazioni.get('dich2_num').enable();
      this.formDichiarazioni.get('dich2_data').enable();
      // abilito i validatori
      this.formDichiarazioni
        .get('dich2_num')
        .setValidators(Validators.required);
      this.formDichiarazioni.get('dich2_num').updateValueAndValidity();
      this.formDichiarazioni
        .get('dich2_data')
        .setValidators([Validators.required, this.maxDateValidator]);
      this.formDichiarazioni.get('dich2_data').updateValueAndValidity();
    } else {
      this.formDichiarazioni.get('dich2_num').disable();
      this.formDichiarazioni.get('dich2_data').disable();
    }

    this.formDichiarazioni.get('dchr_via_sco').setValue(q.dchr_via_sco.check);
    this.formDichiarazioni.get('dich3_num').setValue(q.dchr_via_sco.num);
    this.formDichiarazioni.get('dich3_data').setValue(q.dchr_via_sco.data);
    if (q.dchr_via_sco.check) {
      this.formDichiarazioni.get('dich3_num').enable();
      this.formDichiarazioni.get('dich3_data').enable();
      // abilito i validatori
      this.formDichiarazioni
        .get('dich3_num')
        .setValidators(Validators.required);
      this.formDichiarazioni.get('dich3_num').updateValueAndValidity();
      this.formDichiarazioni
        .get('dich3_data')
        .setValidators([Validators.required, this.maxDateValidator]);
      this.formDichiarazioni.get('dich3_data').updateValueAndValidity();
    } else {
      this.formDichiarazioni.get('dich3_num').disable();
      this.formDichiarazioni.get('dich3_data').disable();
    }

    this.formDichiarazioni
      .get('dchr_def_imp_amb')
      .setValue(q.dchr_def_imp_amb.check);
    this.formDichiarazioni.get('dich4_num').setValue(q.dchr_def_imp_amb.num);
    this.formDichiarazioni.get('dich4_data').setValue(q.dchr_def_imp_amb.data);
    if (q.dchr_def_imp_amb.check) {
      this.formDichiarazioni.get('dich4_num').enable();
      this.formDichiarazioni.get('dich4_data').enable();
      // abilito i validatori
      this.formDichiarazioni
        .get('dich4_num')
        .setValidators(Validators.required);
      this.formDichiarazioni.get('dich4_num').updateValueAndValidity();
      this.formDichiarazioni
        .get('dich4_data')
        .setValidators([Validators.required, this.maxDateValidator]);
      this.formDichiarazioni.get('dich4_data').updateValueAndValidity();
    } else {
      this.formDichiarazioni.get('dich4_num').disable();
      this.formDichiarazioni.get('dich4_data').disable();
    }

    this.formDichiarazioni
      .get('dchr_prov_autoriz')
      .setValue(q.dchr_prov_autoriz.check);
    this.formDichiarazioni.get('dich5_num').setValue(q.dchr_prov_autoriz.num);
    this.formDichiarazioni.get('dich5_data').setValue(q.dchr_prov_autoriz.data);
    if (q.dchr_prov_autoriz.check) {
      this.formDichiarazioni.get('dich5_num').enable();
      this.formDichiarazioni.get('dich5_data').enable();
      // abilito i validatori
      this.formDichiarazioni
        .get('dich5_num')
        .setValidators(Validators.required);
      this.formDichiarazioni.get('dich5_num').updateValueAndValidity();
      this.formDichiarazioni
        .get('dich5_data')
        .setValidators([Validators.required, this.maxDateValidator]);
      this.formDichiarazioni.get('dich5_data').updateValueAndValidity();
    } else {
      this.formDichiarazioni.get('dich5_num').disable();
      this.formDichiarazioni.get('dich5_data').disable();
    }

    this.formDichiarazioni.get('dchr_imp_amb').setValue(q.dchr_imp_amb.check);
    this.formDichiarazioni.get('dich6_num').setValue(q.dchr_imp_amb.num);
    this.formDichiarazioni.get('dich6_data').setValue(q.dchr_imp_amb.data);

    if (q.dchr_imp_amb.check) {
      this.formDichiarazioni.get('dich6_num').enable();
      this.formDichiarazioni.get('dich6_data').enable();
      // abilito i validatori
      this.formDichiarazioni
        .get('dich6_num')
        .setValidators(Validators.required);
      this.formDichiarazioni.get('dich6_num').updateValueAndValidity();
      this.formDichiarazioni
        .get('dich6_data')
        .setValidators([Validators.required, this.maxDateValidator]);
      this.formDichiarazioni.get('dich6_data').updateValueAndValidity();
    } else {
      this.formDichiarazioni.get('dich6_num').disable();
      this.formDichiarazioni.get('dich6_data').disable();
    }

    this.formDichiarazioni.get('dchr_dib_pub').setValue(q.dchr_dib_pub.check);
    this.formDichiarazioni
      .get('dchr_strum_urbani')
      .setValue(q.dchr_strum_urbani.radio);

    if (q.dchr_strum_urbani.radio === 'No') {
      this.flagVisualizzaSelect = true;

      if (q.dchr_strum_urbani_voci.select === 'null') {
        q.dchr_strum_urbani_voci.select = null;
      }

      this.formDichiarazioni
        .get('dchr_strum_urbani_voci')
        .setValue(q.dchr_strum_urbani_voci.select);
      this.formDichiarazioni
        .get('dchr_strum_urbani_voci')
        .setValidators(Validators.required);
      this.formDichiarazioni
        .get('dchr_strum_urbani_voci')
        .updateValueAndValidity();

      if (q.dchr_strum_urbani_voci.select === 'altro') {
        this.flagVisualizzaTexarea = true;
        this.formDichiarazioni
          .get('dchr_strum_urbani_textarea')
          .setValue(q.dchr_strum_urbani_textarea.textarea);
        // abilito i validatori
        this.formDichiarazioni
          .get('dchr_strum_urbani_textarea')
          .setValidators(Validators.required);
        this.formDichiarazioni
          .get('dchr_strum_urbani_textarea')
          .updateValueAndValidity();
      }
    }

    this.formDichiarazioni
      .get('dchr_imp_transf')
      .setValue(q.dchr_imp_transf.check);

    this.formDichiarazioni
      .get('dchr_imp_transf_textarea')
      .setValue(q.dchr_imp_transf_textarea.textarea);

    if (q.dchr_imp_transf.check) {
      this.formDichiarazioni
        .get('dchr_imp_transf_textarea')
        .setValidators(Validators.required);
      this.formDichiarazioni
        .get('dchr_imp_transf_textarea')
        .updateValueAndValidity();
    }

    this.formDichiarazioni
      .get('dchr_pub_doc_website')
      .setValue(q.dchr_pub_doc_website.check);

    if (q.dchr_doc_amm_nopubb.check) {
      this.formDichiarazioni
        .get('dchr_doc_amm_nopubb')
        .setValue(q.dchr_doc_amm_nopubb.check);
    } else {
      this.formDichiarazioni.get('dchr_doc_amm_nopubb').setValue(null);
    }

    if (q.dchr_doc_segr_ind_com.check) {
      this.formDichiarazioni
        .get('dchr_doc_segr_ind_com')
        .setValue(q.dchr_doc_segr_ind_com.check);
    } else {
      this.formDichiarazioni.get('dchr_doc_segr_ind_com').setValue(null);
    }

    this.formDichiarazioni
      .get('dchr_elab_firma')
      .setValue(q.dchr_elab_firma?.check);

    this.formDichiarazioni
      .get('dchr_info_dati_personali')
      .setValue(q.dchr_info_dati_personali?.check);

    // if (this.cod_competenza_territorio != CodiciAutoritaCompetenti.regionePiemonte) {
    //   this.formDichiarazioni
    //     .get('dchr_info_dati_personali_2')
    //     .setValue(q.dchr_info_dati_personali_2.check);
    // }

    this.formDichiarazioni
      .get('dchr_pagam_bollo')
      .setValue(q.dchr_pagam_bollo?.radio);

    this.formDichiarazioni
      .get('dchr_pagam_oneri_istruttori')
      .setValue(q.dchr_pagam_oneri_istruttori?.radio);

    this.formDichiarazioni.get('dchr_note').setValue(q.dchr_note?.textarea);

    if (this.attoreGestioneIstanza != this.gestioneEnum.WRITE) {
      this.formDichiarazioni.disable();
    }
  }

  private getCategoriaAllegati(qdrDichiarazioni: any) {
    // resetto l'array degli allegati
    this.doc_segr_ind_com_tab_Arr = [];
    this.allegatiService.getAllAllegatiIstanza(this.idIstanza, false).subscribe(
      (res) => {
        let rowsAllegati = [...res];
        if (rowsAllegati?.length > 0) {
          rowsAllegati.forEach((el: any) => {
            if (
              el.tipologia_allegato.cod_tipologia_allegato ===
              'DOC-AMM-FAC-NOPUBB'
            ) {
              this.doc_amm_nopubb = true;
              this.formDichiarazioni
                .get('dchr_doc_amm_nopubb')
                .setValidators(Validators.required);
              this.formDichiarazioni
                .get('dchr_doc_amm_nopubb')
                .updateValueAndValidity();
            }

            if (
              el.tipologia_allegato.cod_tipologia_allegato === 'SEGR-IND-COMM'
            ) {
              this.doc_segr_ind_com = true;
              this.formDichiarazioni
                .get('dchr_doc_segr_ind_com')
                .setValidators(Validators.required);
              this.formDichiarazioni
                .get('dchr_doc_segr_ind_com')
                .updateValueAndValidity();

              const doc_segr_ind_com_tab = {
                cod_allegato: el.cod_allegato,
                note: el.note,
                nome_allegato: el.nome_allegato,
              };
              this.doc_segr_ind_com_tab_Arr.push(doc_segr_ind_com_tab);
            }
          });
        }
        if (this.saveWithPut === true) {
          this.getCaricaQdrDichiarazioni(qdrDichiarazioni);
        } else {
          this.disabilitaInput();
        }
      },
      (err) => {
        this.showErrorsQuadroConCodeENoCode(err, 'containerDichiarazioni');
        if (err.error?.code && err.status === 404) {
          if (this.saveWithPut === true) {
            this.getCaricaQdrDichiarazioni(qdrDichiarazioni);
          } else {
            this.disabilitaInput();
          }
        }
      }
    );
  }

  private buildFormDichiarazioni() {
    this.formDichiarazioni = this.fb.group({
      dchr_via_prel: [null],
      dich1_num: [{ value: null, disabled: true }],
      dich1_data: [{ value: null, disabled: true }],
      dchr_via_ver: [null],
      dich2_num: [{ value: null, disabled: true }],
      dich2_data: [{ value: null, disabled: true }],
      dchr_via_sco: [null],
      dich3_num: [{ value: null, disabled: true }],
      dich3_data: [{ value: null, disabled: true }],
      dchr_def_imp_amb: [null],
      dich4_num: [{ value: null, disabled: true }],
      dich4_data: [{ value: null, disabled: true }],
      dchr_prov_autoriz: [null],
      dich5_num: [{ value: null, disabled: true }],
      dich5_data: [{ value: null, disabled: true }],
      dchr_imp_amb: [null],
      dich6_num: [{ value: null, disabled: true }],
      dich6_data: [{ value: null, disabled: true }],
      dchr_dib_pub: [null],
      dchr_strum_urbani: [null, { validators: [Validators.required] }],
      dchr_strum_urbani_voci: [null],
      dchr_strum_urbani_textarea: [null],
      dchr_imp_transf: [null],
      dchr_imp_transf_textarea: [null],
      dchr_pub_doc_website: [null, { validators: [Validators.required] }],
      dchr_doc_amm_nopubb: [null],
      dchr_doc_segr_ind_com: [null],
      dchr_elab_firma: [null, Validators.required],
      dchr_info_dati_personali: [null, { validators: [Validators.required] }],
      // dchr_info_dati_personali_2: [null],
      dchr_pagam_bollo: [null, { validators: [Validators.required] }],
      dchr_pagam_oneri_istruttori: [
        null,
        { validators: [Validators.required] },
      ],
      dchr_note: [null],
    });
  }

  setValid(formTarget) {
    const indice = formTarget.currentTarget.id;
    const valore = this.formDichiarazioni.get(indice).value;

    if (valore === false) {
      this.formDichiarazioni.get(indice).setValue(null);
    }
  }

  abilitaImpTransf() {
    if (this.formDichiarazioni?.get('dchr_imp_transf').value) {
      this.formDichiarazioni
        ?.get('dchr_imp_transf_textarea')
        .setValidators(Validators.required);
      this.formDichiarazioni
        .get('dchr_imp_transf_textarea')
        .updateValueAndValidity();
    } else {
      this.formDichiarazioni.get('dchr_imp_transf_textarea').clearValidators();
      this.formDichiarazioni
        .get('dchr_imp_transf_textarea')
        .updateValueAndValidity();
      this.formDichiarazioni.get('dchr_imp_transf_textarea').setValue(null);
    }
  }

  abilitaCheck(event: any) {
    if (event.target.value.trim().length > 0) {
      this.formDichiarazioni.get('dchr_imp_transf').setValue(true);
    } else {
      this.formDichiarazioni.get('dchr_imp_transf').setValue(false);
    }
    this.abilitaImpTransf();
  }

  abilitaCampi(formTarget, idNum, idData) {
    const indice = formTarget.currentTarget.id;
    let flagAbilita = this.formDichiarazioni.get(indice).value;

    if (flagAbilita) {
      // Se il checkbox è selezionato, abilita l'input di testo
      this.formDichiarazioni.get(idNum).enable();
      this.formDichiarazioni.get(idData).enable();
      this.formDichiarazioni.get(idData).setValidators(Validators.required);
      this.formDichiarazioni.get(idNum).setValidators(Validators.required);
      this.formDichiarazioni.get(idNum).updateValueAndValidity();
      this.formDichiarazioni.get(idData).updateValueAndValidity();
      this.formDichiarazioni.get(idData).setValue(null);
      this.formDichiarazioni.get(idNum).setValue(null);
    } else {
      // Se il checkbox non è selezionato, disabilita l'input di testo
      this.formDichiarazioni.get(idNum).setValue(null);
      this.formDichiarazioni.get(idData).setValue(null);
      this.formDichiarazioni.get(idNum).disable();
      this.formDichiarazioni.get(idData).disable();
      this.formDichiarazioni.get(idData).setValidators(null);
      this.formDichiarazioni.get(idNum).setValidators(null);
      this.formDichiarazioni.get(idNum).updateValueAndValidity();
      this.formDichiarazioni.get(idData).updateValueAndValidity();
      this.formDichiarazioni.get(idData).setValue(null);
      this.formDichiarazioni.get(idNum).setValue(null);
    }
  }

  bloccaDigitazione(event: KeyboardEvent) {
    if (event.key >= '0' && event.key <= '9') {
      event.preventDefault();
    }
  }

  soloNumeri(event: KeyboardEvent) {
    const keyCode = event.code;
    if (
      !(
        (keyCode >= 'Digit0' && keyCode <= 'Digit9') ||
        (keyCode >= 'Numpad0' && keyCode <= 'Numpad9') ||
        keyCode === 'Backspace' ||
        keyCode.startsWith('Arrow')
      )
    ) {
      event.preventDefault();
    }
  }

  displayTextarea() {
    if (
      this.formDichiarazioni?.get('dchr_strum_urbani_voci').value === 'altro'
    ) {
      this.flagVisualizzaTexarea = true;
      this.formDichiarazioni
        .get('dchr_strum_urbani_textarea')
        .setValidators(Validators.required);
      this.formDichiarazioni
        .get('dchr_strum_urbani_textarea')
        .updateValueAndValidity();
      this.formDichiarazioni.get('dchr_strum_urbani_textarea').setValue(null);
    } else {
      this.flagVisualizzaTexarea = false;
      this.formDichiarazioni.get('dchr_strum_urbani_textarea').setValue(null);
      this.formDichiarazioni
        .get('dchr_strum_urbani_textarea')
        .clearValidators();
      this.formDichiarazioni
        .get('dchr_strum_urbani_textarea')
        .updateValueAndValidity();
    }

    if (this.flagVisualizzaSelect) {
      if (
        this.formDichiarazioni.get('dchr_strum_urbani_voci').value === 'null' ||
        this.formDichiarazioni.get('dchr_strum_urbani_voci').value === null
      ) {
        this.formDichiarazioni
          .get('dchr_strum_urbani_voci')
          .setValidators(Validators.required);
        this.formDichiarazioni
          .get('dchr_strum_urbani_voci')
          .updateValueAndValidity();
        this.formDichiarazioni.get('dchr_strum_urbani_voci').setValue(null);
      } else {
        this.formDichiarazioni.get('dchr_strum_urbani_voci').clearValidators();
        this.formDichiarazioni
          .get('dchr_strum_urbani_voci')
          .updateValueAndValidity();
      }
    } else {
      this.formDichiarazioni.get('dchr_strum_urbani_voci').setValue(null);
      this.formDichiarazioni.get('dchr_strum_urbani_voci').clearValidators();
      this.formDichiarazioni
        .get('dchr_strum_urbani_voci')
        .updateValueAndValidity();
    }
    this.formDichiarazioni.get('dchr_strum_urbani_voci').value;
  }

  resettaSelectVoci() {
    this.formDichiarazioni.get('dchr_strum_urbani_voci').setValue(null);
    this.flagVisualizzaTexarea = false;
    this.formDichiarazioni.get('dchr_strum_urbani_textarea').setValue(null);
    this.formDichiarazioni.get('dchr_strum_urbani_textarea').clearValidators();
    this.formDichiarazioni
      .get('dchr_strum_urbani_textarea')
      .updateValueAndValidity();

    if (this.formDichiarazioni.get('dchr_strum_urbani').value === 'Si') {
      this.flagVisualizzaSelect = false;
      this.formDichiarazioni.get('dchr_strum_urbani_voci').clearValidators();
      this.formDichiarazioni
        .get('dchr_strum_urbani_voci')
        .updateValueAndValidity();
    } else {
      this.flagVisualizzaSelect = true;
      this.formDichiarazioni
        .get('dchr_strum_urbani_voci')
        .setValidators(Validators.required);
      this.formDichiarazioni
        .get('dchr_strum_urbani_voci')
        .updateValueAndValidity();
    }
    this.formDichiarazioni.get('dchr_strum_urbani_voci').value;
  }

  formatDate(originalDate: string) {
    return this.datePipe.transform(originalDate, 'dd-MM-yyyy');
  }

  // Metodo per ottenere il valore del radio button selezionato (Pagamento Bollo)
  getValueCodOfSelectedRadioButtonPagamBollo(form): {
    cod: string;
    des: string;
  } {
    if (form.get('dchr_pagam_bollo').value === 'Si') {
      return {
        cod: 'dchr_pagam_bollo_dovuto',
        des: "dichiara che il proponente è soggetto al pagamento dell'imposta di bollo ai sensi del DPR n. 642/1972 e che alla presente istanza è stata allegata la documentazione attestante il pagamento",
      };
    } else if (form.get('dchr_pagam_bollo').value === 'No') {
      return {
        cod: 'dchr_pagam_bollo_non_dovuto',
        des: "dichiara che il proponente è esente dal pagamento dell'imposta di bollo ai sensi del DPR n. 642/1972",
      };
    } else {
      return null; // Nessun radio button selezionato
    }
  }

  // Metodo per ottenere il valore del radio button selezionato (Pagamento Oneri Istruttoria)
  getValueCodOfSelectedRadioButtonPagamOneri(form): {
    cod: string;
    des: string;
  } {
    if (form.get('dchr_pagam_oneri_istruttori').value === 'Si') {
      return {
        cod: 'dchr_pagam_oneri_istruttori_dovuto',
        des: 'dichiara di aver pagato gli oneri istruttori di cui all’art.12, comma 1, della l.r. 13/2023 e che alla presente istanza è stata allegata la relativa documentazione prevista dalla d.g.r. n. 15-8403 del 8 aprile 2024, All. A, par. 2.2 (ricevuta di avvenuto pagamento del contributo; dichiarazione sostitutiva di atto notorio attestante il valore delle opere da realizzare e l’importo del contributo dovuto; quadro economico generale inerente il valore complessivo delle opere da realizzare; computo metrico estimativo)',
      };
    } else if (form.get('dchr_pagam_oneri_istruttori').value === 'No') {
      return {
        cod: 'dchr_pagam_oneri_istruttori_non_dovuto',
        des: 'dichiara che il proponente è un’amministrazione pubblica come definita dall’art.1, comma 2, del d.lgs. 165/2001, e che pertanto è escluso dal pagamento degli oneri istruttori ai sensi dell’art. 12, comma 4 della l.r. 13/2023',
      };
    } else {
      return null; // Nessun radio button selezionato
    }
  }
  private caricaQuadro() {
    const v = this.formDichiarazioni;

    const des1 = v.get('dchr_via_prel').value
      ? ' n.' +
        v.get('dich1_num').value +
        ' data ' +
        this.formatDate(v.get('dich1_data').value)
      : '';
    const des2 = v.get('dchr_via_ver').value
      ? ' n.' +
        v.get('dich2_num').value +
        ' data ' +
        this.formatDate(v.get('dich2_data').value)
      : '';
    const des3 = v.get('dchr_via_sco').value
      ? ' n.' +
        v.get('dich3_num').value +
        ' data ' +
        this.formatDate(v.get('dich3_data').value)
      : '';
    const des4 = v.get('dchr_def_imp_amb').value
      ? ' n.' +
        v.get('dich4_num').value +
        ' data ' +
        this.formatDate(v.get('dich4_data').value)
      : '';
    const des5 = v.get('dchr_prov_autoriz').value
      ? ' n.' +
        v.get('dich5_num').value +
        ' data ' +
        this.formatDate(v.get('dich5_data').value)
      : '';
    const des6 = v.get('dchr_imp_amb').value
      ? ' n.' +
        v.get('dich6_num').value +
        ' data ' +
        this.formatDate(v.get('dich6_data').value)
      : '';

    const comuneNotPiemonte = this.comune_competenza;
    const pagBollo = this.getValueCodOfSelectedRadioButtonPagamBollo(v);
    const pagOneriIstruttori =
      this.getValueCodOfSelectedRadioButtonPagamOneri(v);

    // Definisco l'oggetto di configurazione con i parametri per la dichiarazione GDPR
    const datiACRegionePiemonte: IDatiACRegionePiemonteVAL = {
      webLink: this.acWebPiemonte,
    };
    const datiACSpecifica: IDatiACSpecificaVAL = {
      webLink: this.acWebNotPiemonte,
      comune: comuneNotPiemonte,
    };

    let desTransf = null;
    if (
      !!v.get('dchr_imp_transf_textarea').value &&
      !!v.get('dchr_imp_transf').value
    ) {
      desTransf =
        'dichiara che il progetto può avere impatti transfrontalieri sui seguenti Stati: ' +
        v.get('dchr_imp_transf_textarea').value;
      +' e pertanto è soggetto alle procedure di cui all’art. 32 del d.lgs.152/2006';
    } else {
      desTransf =
        'dichiara che il progetto può avere impatti transfrontalieri sui seguenti Stati:';
    }

    let desStrUrbani = null;
    if (v.get('dchr_strum_urbani').value === 'Si') {
      // Si
      desStrUrbani =
        'dichiara che il progetto è conforme ai vigenti strumenti urbanistici e quindi non necessita di variante urbanistica';
    } else {
      // No
      if (!!v.get('dchr_strum_urbani_voci').value) {
        // Voci Select
        if (v.get('dchr_strum_urbani_voci').value === 'altro') {
          // altro
          if (!!v.get('dchr_strum_urbani_textarea').value) {
            desStrUrbani =
              'dichiara che il progetto non è conforme ai vigenti strumenti urbanistici e che il progetto rientra nelle casistiche per la variante semplificata ai sensi della l.r. 56/1977, articolo: ' +
              v.get('dchr_strum_urbani_textarea').value +
              '. A tal fine si allegano gli elaborati previsti dalla vigente normativa, comprensivi degli elaborati relativi alla procedura di VAS';
          } else {
            desStrUrbani =
              'dichiara che il progetto non è conforme ai vigenti strumenti urbanistici e che il progetto rientra nelle casistiche per la variante semplificata ai sensi della l.r. 56/1977, articolo';
          }
        } else {
          // Voci select (diversa da altro)
          desStrUrbani =
            'dichiara che il progetto non è conforme ai vigenti strumenti urbanistici e che il progetto rientra nelle casistiche per la variante semplificata ai sensi della l.r. 56/1977, articolo: ' +
            v.get('dchr_strum_urbani_voci').value +
            '. A tal fine si allegano gli elaborati previsti dalla vigente normativa, comprensivi degli elaborati relativi alla procedura di VAS';
        }
      }
    }

    const arDichiarazioni = [
      {
        check: !!v.get('dchr_via_prel').value,
        obbligatorio: false,
        cod_dichiarazione: 'dchr_via_prel',
        des_dichiarazione:
          'dichiara che il progetto è stato sottoposto a valutazione preliminare (d.lgs.152/2006, art. 6, comma 9) conclusasi con atto/provvedimento' +
          des1,
      },
      {
        check: !!v.get('dchr_via_ver').value,
        obbligatorio: false,
        cod_dichiarazione: 'dchr_via_ver',
        des_dichiarazione:
          'dichiara che il progetto è stato sottoposto a verifica di assoggettabilità a VIA (d.lgs.152/2006, art. 19), conclusasi con atto/provvedimento' +
          des2,
      },
      {
        check: !!v.get('dchr_via_sco').value,
        obbligatorio: false,
        cod_dichiarazione: 'dchr_via_sco',
        des_dichiarazione:
          'dichiara che il progetto è stato sottoposto a consultazione preventiva (d.lgs.152/2006, art. 20), conclusasi con atto/provvedimento' +
          des3,
      },
      {
        check: !!v.get('dchr_def_imp_amb').value,
        obbligatorio: false,
        cod_dichiarazione: 'dchr_def_imp_amb',
        des_dichiarazione:
          'dichiara che il progetto è stato sottoposto a fase di consultazione per la definizione dei contenuti dello studio di impatto ambientale (d.lgs.152/2006, art. 21), conclusasi con atto/provvedimento' +
          des4,
      },
      {
        check: !!v.get('dchr_prov_autoriz').value,
        obbligatorio: false,
        cod_dichiarazione: 'dchr_prov_autoriz',
        des_dichiarazione:
          'dichiara che il progetto è stato sottoposto a fase preliminare al provvedimento autorizzatorio unico regionale (d.lgs.152/2006, art. 26-bis), conclusasi con atto/provvedimento' +
          des5,
      },
      {
        check: !!v.get('dchr_imp_amb').value,
        obbligatorio: false,
        cod_dichiarazione: 'dchr_imp_amb',
        des_dichiarazione:
          'dichiara che il progetto è stato sottoposto a valutazione di impatto ambientale (d.lgs.152/2006, art. 27-bis), conclusasi con atto/provvedimento' +
          des6,
      },
      {
        check: !!v.get('dchr_dib_pub').value,
        obbligatorio: false,
        cod_dichiarazione: 'dchr_dib_pub',
        des_dichiarazione:
          'dichiara che il progetto è stato sottoposto a procedura di dibattito pubblico ai sensi dell’art. 22 del d.lgs. 50/2016',
      },
      {
        check: v.get('dchr_strum_urbani').value,
        obbligatorio: true,
        cod_dichiarazione: 'dchr_strum_urbani',
        des_dichiarazione: desStrUrbani,
      },
      {
        check: !!v.get('dchr_imp_transf').value,
        obbligatorio: false,
        cod_dichiarazione: 'dchr_imp_transf',
        des_dichiarazione: desTransf,
      },
      {
        check: !!v.get('dchr_pub_doc_website').value,
        obbligatorio: true,
        cod_dichiarazione: 'dchr_pub_doc_website',
        des_dichiarazione:
          // "dichiara di essere consapevole che l'Amministrazione pubblicherà la documentazione trasmessa con la presente istanza (d.lgs.152/2006, art. 27-bis, comma 2) sul sito web istituzionale Valutazioni ambientali: http://www.sistemapiemonte.it/cms/privati/ambiente-e-energia/servizi/540-valutazioni-ambientali",
          this.DVAL_C.dichiarazioneACPubVAL.descrizioneDichiarazione(
            this.paramsDichiarazioneACPubVAL,
            this.cod_competenza_territorio
          ),
      },
      {
        check: !!v.get('dchr_doc_amm_nopubb').value,
        obbligatorio: this.doc_amm_nopubb,
        cod_dichiarazione: 'dchr_doc_amm_nopubb',
        des_dichiarazione:
          'dichiara di essere consapevole che gli allegati documentali appartenenti alla categoria allegati denominata “Ulteriore documentazione amministrativa e facoltativa (non oggetto di pubblicazione)” non saranno resi accessibili al pubblico',
      },
      {
        check: !!v.get('dchr_doc_segr_ind_com').value,
        obbligatorio: this.doc_segr_ind_com,
        cod_dichiarazione: 'dchr_segr_ind',
        des_dichiarazione:
          'richiede che, per ragioni di segreto industriale o commerciale, ai sensi dell’art. 9, comma 4 del d.lgs. 152/2006, non siano resi pubblici gli elaborati inseriti nella cartella allegati denominata “Documentazione soggetta al segreto industriale o commerciale”, per le Motivazioni indicate nella seguente tabella',
      },
      {
        check: !!v.get('dchr_elab_firma').value,
        obbligatorio: true,
        cod_dichiarazione: 'dchr_elab_firma',
        des_dichiarazione:
          'dichiara di aver verificato che gli elaborati progettuali sono firmati esclusivamente in formato digitale da tutti i professionisti che li hanno redatti e che non contengono dati personali eccedenti e non pertinenti',
      },
      // {
      //   check: !!v.get('dchr_info_dati_personali').value,
      //   obbligatorio: true,
      //   cod_dichiarazione: 'dchr_info_dati_personali',
      //   des_dichiarazione: `${this.DVAL_C.dichiarazioniVIARegionePiemonte(
      //     webLink1
      //   )}`,
      //   // "dichiara di aver preso visione dell'Informativa sul trattamento dei dati personali, aggiornata all’art. 13 del GDPR 2016/679, consultabile anche sul sito" +
      //   // webLink1,
      // },
      this.dichiarazioneDatiPersonaliGDPR(
        datiACRegionePiemonte,
        datiACSpecifica
      ),
      {
        check: v.get('dchr_pagam_bollo').value,
        obbligatorio: true,
        cod_dichiarazione: pagBollo.cod,
        des_dichiarazione: pagBollo.des,
      },
      {
        check: v.get('dchr_pagam_oneri_istruttori').value,
        obbligatorio: true,
        cod_dichiarazione: pagOneriIstruttori.cod,
        des_dichiarazione: pagOneriIstruttori.des,
      },
      {
        check: !!v.get('dchr_note').value,
        obbligatorio: false,
        cod_dichiarazione: 'dchr_note',
        des_dichiarazione: v.get('dchr_note').value,
      },
    ];

    const QDR_DICHIARAZIONE = {
      dchr_via_prel: {
        check: !!v.get('dchr_via_prel').value,
        data: v.get('dich1_data').value,
        num: v.get('dich1_num').value,
      },
      dchr_via_ver: {
        check: !!v.get('dchr_via_ver').value,
        data: v.get('dich2_data').value,
        num: v.get('dich2_num').value,
      },
      dchr_via_sco: {
        check: !!v.get('dchr_via_sco').value,
        data: v.get('dich3_data').value,
        num: v.get('dich3_num').value,
      },
      dchr_def_imp_amb: {
        check: !!v.get('dchr_def_imp_amb').value,
        data: v.get('dich4_data').value,
        num: v.get('dich4_num').value,
      },
      dchr_prov_autoriz: {
        check: !!v.get('dchr_prov_autoriz').value,
        data: v.get('dich5_data').value,
        num: v.get('dich5_num').value,
      },
      dchr_imp_amb: {
        check: !!v.get('dchr_imp_amb').value,
        data: v.get('dich6_data').value,
        num: v.get('dich6_num').value,
      },
      dchr_dib_pub: {
        check: !!v.get('dchr_dib_pub').value,
      },
      dchr_strum_urbani: {
        radio: v.get('dchr_strum_urbani').value,
      },
      dchr_strum_urbani_voci: {
        select: v.get('dchr_strum_urbani_voci').value,
      },
      dchr_strum_urbani_textarea: {
        textarea: v.get('dchr_strum_urbani_textarea').value,
      },
      dchr_imp_transf: {
        check: !!v.get('dchr_imp_transf').value,
      },
      dchr_imp_transf_textarea: {
        textarea: v.get('dchr_imp_transf_textarea').value,
      },
      dchr_pub_doc_website: {
        check: !!v.get('dchr_pub_doc_website').value,
      },
      dchr_doc_amm_nopubb: {
        check: !!v.get('dchr_doc_amm_nopubb').value,
      },
      dchr_doc_segr_ind_com: {
        check: !!v.get('dchr_doc_segr_ind_com').value,
      },
      dchr_elab_firma: {
        check: !!v.get('dchr_elab_firma').value,
      },
      dchr_info_dati_personali: {
        check: !!v.get('dchr_info_dati_personali').value,
      },
      dchr_pagam_bollo: {
        radio: v.get('dchr_pagam_bollo').value,
      },
      dchr_pagam_oneri_istruttori: {
        radio: v.get('dchr_pagam_oneri_istruttori').value,
      },
      dchr_note: {
        textarea: v.get('dchr_note').value,
      },
    };

    // if (this.cod_competenza_territorio != CodiciAutoritaCompetenti.regionePiemonte) {
    //   // Se la competenza territorio è diversa da Regione Piemonte l'aggiungo all'array
    //   const datiPersonali2 = {
    //     check: !!v.get('dchr_info_dati_personali_2').value,
    //     obbligatorio: true,
    //     cod_dichiarazione: 'dchr_info_dati_personali_2',
    //     des_dichiarazione: `${this.DVAL_C.dichiarazioniVIAACSpecifica(
    //       webLink2,
    //       this.comune_competenza
    //     )}`,
    //     // "dichiara di aver preso visione dell'Informativa sul trattamento dei dati personali, aggiornata all’art. 13 del GDPR 2016/679, consultabile anche sul sito web istituzionale - " +
    //     // comuneNotPiemonte +
    //     // webLink2,
    //   };
    //   // Calcola la terzultima posizione
    //   const position = arDichiarazioni.length - 2;
    //   // Uso splice per aggiungere il nuovo oggetto in terzultima posizione
    //   arDichiarazioni.splice(position, 0, datiPersonali2);

    //   // Stessa logica l'oggetto QDR_DICHIARAZIONE
    //   QDR_DICHIARAZIONE['dchr_info_dati_personali_2'] = {
    //     check: !!v.get('dchr_info_dati_personali_2').value,
    //   };
    // }
    QDR_DICHIARAZIONE['dichiarazioni'] = arDichiarazioni;

    this.qdr_riepilogo[this.codTipoQuadro] = QDR_DICHIARAZIONE;
    this.qdr_dichiarazioni = QDR_DICHIARAZIONE;
  }

  /**
   * Funzione che gestisce l'oggetto dati per le dichiarazioni dei dati personali (GDPR).
   * A seconda dell'autorità competente attiva verrà gestita con le informazioni della regione piemonte oppure altra ac specfica.
   * @returns any con le informazioni per il salvataggio dei dati della dichiarazione.
   */
  private dichiarazioneDatiPersonaliGDPR(
    datiACRegionePiemonte: IDatiACRegionePiemonteVAL,
    datiACSpecifica: IDatiACSpecificaVAL
  ): any {
    // Recupero le informazioni per compilare i dati
    const form: FormGroup = this.formDichiarazioni;
    const codiceDichiarazione: string = 'dchr_info_dati_personali';
    const check: boolean = !!form.get(codiceDichiarazione).value;
    const obbligatorio: boolean = true;
    let des_dichiarazione: string = '';

    // Per la descrizione della dichiarazione dipende da quale sia l'autorità competente attiva
    if (this.isACPrincipaleRegionePiemonte) {
      // Estraggo i dati per AC Regione piemonte
      const linkACRP: string = datiACRegionePiemonte?.webLink ?? '';
      // La descrizione è quella della regione piemonte
      des_dichiarazione = this.DVAL_C.dichiarazioniVIARegionePiemonte(linkACRP);
      // #
    } else {
      // Estraggo i dati per AC specifica diversa da Regione Piemonte
      const linkACAltra: string = datiACSpecifica?.webLink ?? '';
      const comune: string = datiACSpecifica?.comune ?? '';
      // Estraggo i dati per AC Regione piemonte
      const linkACRP: string = datiACRegionePiemonte?.webLink ?? '';
      // La descrizione è quella di un'AC specifica NON regione piemonte
      des_dichiarazione = this.DVAL_C.dichiarazioniVIAACSpecifica(
        linkACRP,
        linkACAltra,
        comune
      );
      // #
    }

    // Creo l'oggetto con le informazioni per la dichiarazione della GDPR
    const dichiarazione = {
      check,
      obbligatorio,
      cod_dichiarazione: codiceDichiarazione,
      des_dichiarazione,
    };
    // Ritorno la configurazione
    return dichiarazione;
  }

  salvaDatiQuadro() {
    this.spinner.show();
    const configsBuild: IConfigsSaveQuadro = {
      idIstanza: this.idIstanza,
      idTemplateQuadro: this.idTemplateQuadro,
      datiQuadro: this.qdr_dichiarazioni,
      datiRiepilogo: this.qdr_riepilogo,
    };
    this.saveDataQuadro(configsBuild).subscribe(
      (res) => {
        this.spinner.hide();
        this.saveWithPut = true;
        this.setStepCompletedEmit('DichiarazioniValComponent', true);
        this.goToStep(this.stepIndex);
      },
      (err) => {
        this.spinner.hide();
        this.showErrorsQuadroConCodeENoCode(
          err,
          'formContainer',
          'containerDichiarazioni'
        );
      }
    );
  }

  /**
   * Funzione per il salvataggio del quadro e del riepilogo
   * @override
   */
  protected saveDataQuadro(configs: IConfigsSaveQuadro) {
    // Preparo le informazioni per il salvataggio dei dati
    const requestData: RequestSaveBodyQuadro =
      this.buildRequestDataToSaveQuadro(configs);

    return this.doForkjoinSalvaDatiQuadroERiepilogo(
      requestData,
      this.saveWithPut,
      true,
      true
    );
  }

  /**
   * @override
   * @returns Observable<boolean> controllo validità form
   */
  protected isStepValid():Observable<boolean> {
    if (!this.formDichiarazioni.valid) {
      Object.keys(this.formDichiarazioni.controls).forEach((controlName) => {
        this.formDichiarazioni.get(controlName).markAsTouched();
      });
      // Form NON valido
      return of(false);
    } else {
      return of(true);
    }
  }

  /**
   * @override
   */
  onAvanti() {
    if (this.attoreGestioneIstanza === this.gestioneEnum.WRITE) {
      this.isStepValid().subscribe((isValid: boolean) => {
        if (isValid) {
          this.caricaQuadro();
          this.salvaDatiQuadro();
        } else {
          // Form NON valido
          this._messageService.showMessage(
            'E001',
            'containerDichiarazioni',
            true
          );
        }
      });
    } else {
      this.goToStep(this.stepIndex);
    }
  }

  /**
   * #####################
   * GESTIONE SEGNALAZIONI
   * #####################
   */

  /**
   * Funzione di supporto che gestisce gli errori generati dalla chiamata ai servizi.
   * @param e ScrivaServerError con l'oggetto d'errore generato dalla chiamata.
   * @param target string con l'identificativo per un target specifico.
   */
  private onServiziError(e?: ScrivaServerError, target?: string) {
    // Definisco le informazioni per il messaggio d'errore
    const errorCode: string = e?.error?.code;
    const m: string = errorCode ? errorCode : ScrivaCodesMesseges.E100;
    const fade: boolean = !errorCode;
    target = target ?? 'containerDichiarazioni';
    // Richiamo la visualizzazione del messaggio
    this._messageService.showMessage(m, target, fade);
  }

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  /**
   * Getter che ritorna i parametri per la gestione della dichiarazione PUB.
   * @returns IDichiarazioneACPubVALSpecifica con i parametri.
   */
  get paramsDichiarazioneACPubVAL(): IDichiarazioneACPubVALSpecifica {
    // Creo l'oggetto con i paramtri
    const params: IDichiarazioneACPubVALSpecifica = {
      descrizioneComune: this.comune_competenza,
      link: this.acWebPub,
    };

    // Ritorno l'oggetto generato
    return params;
  }

  /**
   * Getter di comodo che verifica l'AC principale/attiva per listanza, e verifica se è una specifica.
   * @returns boolean con il risultato del controllo.
   */
  get isACPrincipaleRegionePiemonte(): boolean {
    // Verifico se l'ac è specifica
    return (
      this.cod_competenza_territorio == CodiciAutoritaCompetenti.regionePiemonte
    );
  }

  /**
   * Getter di comodo che verifica l'AC principale/attiva per listanza, e verifica se è una specifica.
   * AC specifica vuol dire "NON REGIONE PIEMONTE".
   * @returns boolean con il risultato del controllo.
   */
  get isACPrincipaleSpecifica(): boolean {
    // Verifico se l'ac è specifica
    return (
      this.cod_competenza_territorio != CodiciAutoritaCompetenti.regionePiemonte
    );
  }

  ngOnDestroy(): void {
    super.ngOnDestroy();
  }
}
