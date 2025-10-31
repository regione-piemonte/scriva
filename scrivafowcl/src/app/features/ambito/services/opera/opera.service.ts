/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';
import {
  AdempimentoService,
  MessageService,
  VincoliAutService,
} from 'src/app/shared/services';
import { ScrivaServerError } from '../../../../core/classes/scriva.classes';
import { ScrivaCodesMesseges } from '../../../../core/enums/scriva-codes-messages.enums';
import { IScrivaServerErrorInfo } from '../../../../core/interfaces/scriva.interfaces';
import { IServiziErrorConfig } from '../../../../shared/interfaces/scriva-utilities.interfaces';
import { CompetenzaTerritorio } from '../../../../shared/models';
import {
  CatastoParticella,
  IstanzaVincoloAut,
  OggettoIstanza,
  OggettoIstanzaAreaProtetta,
  OggettoIstanzaNatura2000,
  OggettoIstanzaVincoloAutorizza,
  Opera,
  QdrOggettoConfig,
  VincoloAutorizza,
} from '../../models';
import { AmbitoService } from '../ambito/ambito.service';
import { ICheckACParams } from './utilities/opera.interfaces';

/**
 * Servizio per Opera
 */
@Injectable({ providedIn: 'root' })
export class OperaService {
  codeMessageDefaults: any = {
    cod_messaggio_comuni_1: 'A059',
    cod_messaggio_comuni_2: 'A044',
    cod_messaggio_comuni_3: 'A045',
    cod_messaggio_comuni_4: 'A046',
    cod_messaggio_post_geeco_riepilogo_1: 'A035',
    cod_messaggio_post_geeco_riepilogo_2: 'A036',
    cod_messaggio_post_geeco_riepilogo_3: 'A037',
    cod_messaggio_post_geeco_tab_1: 'A038',
    cod_messaggio_post_geeco_tab_2: 'A039',
    cod_messaggio_post_geeco_tab_3: 'A040',
  };
  private _showViaconvinca: boolean;
  private _istanzaVincoloAut: IstanzaVincoloAut[];
  private _qdrOggettoConfig: QdrOggettoConfig;
  private _oggettiIstanzaNatura2000: {
    idOggettoIstanza: number;
    items: OggettoIstanzaNatura2000[];
  }[] = [];
  private _oggettiIstanzaAreaProtetta: {
    idOggettoIstanza: number;
    items: OggettoIstanzaAreaProtetta[];
  }[] = [];

  private _oggettiIstanzaVincoloAutorizzaIdro: {
    idOggettoIstanza: number;
    items: VincoloAutorizza[];
  }[] = [];

  private _oggettiIstanzaCatastoParticella: {
    idOggettoIstanza: number;
    items: CatastoParticella[];
  }[] = [];

  constructor(
    private _adempimento: AdempimentoService,
    private _ambito: AmbitoService,
    private _message: MessageService,
    private _vincoli: VincoliAutService
  ) {}

  setVincoli(res: IstanzaVincoloAut[]) {
    this._istanzaVincoloAut = res;
  }

  setConfigOggetto(qdrOggettoConfig: QdrOggettoConfig) {
    this._qdrOggettoConfig = qdrOggettoConfig;
  }

  hasVincolo(codVincoloAutorizza: string) {
    return this._istanzaVincoloAut.find(
      (item) =>
        item.vincolo_autorizza.cod_vincolo_autorizza === codVincoloAutorizza
    );
  }

  getVincoli(): IstanzaVincoloAut[] {
    return this._istanzaVincoloAut;
  }

  setShowViaconvinca(v: boolean = false) {
    this._showViaconvinca = v;
  }

  getShowViaconvinca(): boolean {
    return this._showViaconvinca;
  }

  callSuggestOpera(operaEdit: Opera | OggettoIstanza): boolean {
    return (
      'flg_geo_modificato' in operaEdit &&
      operaEdit.flg_geo_modificato &&
      this.callSuggest()
    );
  }

  callSuggest(): boolean {
    if (this.getShowViaconvinca() && this.hasVincolo('VNCS')) {
      return true;
    }
    if (!this.getShowViaconvinca()) {
      return true;
    }
    return false;
  }

  /**
   * Recupero i messaggi gecco generici
   * @param element OggettoIstanza
   * @param fromGeeco  boolean che indica se arriviamo da geeco
   * @returns Observable<string[]>
   */
  getGeecoMessagesGeneric(
    element: OggettoIstanza,
    fromGeeco: boolean = false
  ): Observable<string[]> {
    if (fromGeeco) {
      if (!element.flg_geo_modificato) {
        return of(['P2GIS']);
      }
      return of(['P1GIS']);
    }
    return of([]);
  }

  /**
   * Recupero il messaggio in base alla chiave
   * @param key string chiave del messaggio
   * @returns stringa messaggio
   */
  getCodeMessageByKey(key: string): string {
    if (this._qdrOggettoConfig?.messaggi[key]) {
      return this._qdrOggettoConfig?.messaggi[key];
    }
    if (this.codeMessageDefaults[key]) {
      return this.codeMessageDefaults[key];
    }
    return '';
  }

  /**
   * Recupero i messaggi gecco in base ai comuni
   * @param element OggettoIstanza
   * @param fromGeeco boolean che indica se arriviamo da geeco
   * @returns Observable<string[]>
   */
  getGeecoMessagesComuni(
    element: OggettoIstanza,
    fromGeeco: boolean = false
  ): Observable<string[]> {
    if (!element.flg_geo_modificato) {
      return of([]);
    }

    let hasGeo: boolean = false;
    let hasGeod: boolean = false;
    element.ubicazione_oggetto.forEach((ubicazione) => {
      if (ubicazione.ind_geo_provenienza === 'GEO') {
        hasGeo = true;
      }
      if (ubicazione.ind_geo_provenienza === 'GEOD') {
        hasGeod = true;
      }
    });

    if (hasGeo) {
      return of([
        this.getCodeMessageByKey('cod_messaggio_post_geeco_riepilogo_1'),
      ]);
    }

    if (hasGeod) {
      return of([
        this.getCodeMessageByKey('cod_messaggio_post_geeco_riepilogo_2'),
      ]);
    }

    if (hasGeo && hasGeod) {
      return of([
        this.getCodeMessageByKey('cod_messaggio_post_geeco_riepilogo_3'),
      ]);
    }

    return of([]);
  }

  /**
   * Resetto tutti i suggerimenti
   * @param idOggettoIstanza number
   */
  public resetGeecoSuggest(idOggettoIstanza: number) {
    this._oggettiIstanzaNatura2000.splice(
      this._oggettiIstanzaNatura2000.findIndex(
        (item) => item.idOggettoIstanza === idOggettoIstanza
      ),
      1
    );
    this._oggettiIstanzaAreaProtetta.splice(
      this._oggettiIstanzaAreaProtetta.findIndex(
        (item) => item.idOggettoIstanza === idOggettoIstanza
      ),
      1
    );
    this._oggettiIstanzaCatastoParticella.splice(
      this._oggettiIstanzaNatura2000.findIndex(
        (item) => item.idOggettoIstanza === idOggettoIstanza
      ),
      1
    );
    this._oggettiIstanzaVincoloAutorizzaIdro.splice(
      this._oggettiIstanzaNatura2000.findIndex(
        (item) => item.idOggettoIstanza === idOggettoIstanza
      ),
      1
    );
    this.setConfigOggetto(null);
  }

  /**
   * Recupero le aree protette suggerite per idOggettoIstanza in input
   * @param idOggettoIstanza number
   * @returns OggettoIstanzaAreaProtetta[]
   */
  public getGeecoOggettiIstanzaAreaProtette(
    idOggettoIstanza: number
  ): OggettoIstanzaAreaProtetta[] {
    return (
      this._oggettiIstanzaAreaProtetta.find(
        (i) => i.idOggettoIstanza === idOggettoIstanza
      )?.items || []
    );
  }

  /**
   * Recupero le aree protette e le tengo da parte
   * restituiso messaggi di warning eventuali
   * @param idOggettoIstanza number
   * @param oggettiIstanza OggettoIstanza[] oggetti istanza
   * @returns Observable<string[]>
   */
  public setGeecoOggettiIstanzaAreaProtetteGetMessage(
    idOggettoIstanza: number,
    oggettiIstanza: OggettoIstanza[]
  ): Observable<string[]> {
    let call = null;
    if (this.getShowViaconvinca()) {
      call = this.hasVincolo('VNCS')
        ? this._ambito.getAreeProtetteByUbicazioni(
            null,
            idOggettoIstanza,
            false
          )
        : null;
    } else {
      call = this._ambito.getAreeProtetteByUbicazioni(null, idOggettoIstanza);
    }

    if (!call) {
      return of([]);
    }

    return call.pipe(
      catchError((err) => of([])),
      switchMap((res: OggettoIstanzaAreaProtetta[]) => {
        let suggeriti: boolean = false;
        if (
          this.isSuggestionsAndSetAreaProtetta(
            idOggettoIstanza,
            res,
            oggettiIstanza
          )
        ) {
          suggeriti = true;
        }
        const incoerenti = this._checkIncoerenti(
          idOggettoIstanza,
          oggettiIstanza,
          'aree_protette',
          this.getGeecoOggettiIstanzaAreaProtette(idOggettoIstanza)
        );
        if (suggeriti) {
          return incoerenti
            ? of([
                this.getCodeMessageByKey(
                  'cod_messaggio_post_geeco_riepilogo_3'
                ),
              ])
            : of([
                this.getCodeMessageByKey(
                  'cod_messaggio_post_geeco_riepilogo_1'
                ),
              ]);
        }
        return incoerenti
          ? of([
              this.getCodeMessageByKey('cod_messaggio_post_geeco_riepilogo_2'),
            ])
          : of([]);
      })
    );
  }

  /**
   * Verifico se ci sono degli elementi incoerenti nella lista dell'oggetto istanza in input
   * @param idOggettoIstanza number
   * @param oggettiIstanza
   * @param list string siti_natura_2000|aree_protette|dati_catastali|vincoli
   * @param suggeriti OggettoIstanzaNatura2000[]|OggettoIstanzaAreaProtetta[]|VincoloAutorizza[]|CatastoParticella[]
   * @returns boolean
   * 
   * @author Ismaele Bottelli
   * @date 16/01/2025
   * @jira SCRIVA-1587
   * @notes Refactor e correzione del codice per la gestione dei suggeriti e i messaggi di segnalazione.
   */
  private _checkIncoerenti(
    idOggettoIstanza: number,
    oggettiIstanza: OggettoIstanza[],
    list: string,
    suggeriti:
      | OggettoIstanzaNatura2000[]
      | OggettoIstanzaAreaProtetta[]
      | VincoloAutorizza[]
      | CatastoParticella[] = []
  ): boolean {
    // logica indentica a getBannerMessage dentro opera-form.component.ts

    const listaInOggettoIstanza:
      | OggettoIstanzaNatura2000[]
      | OggettoIstanzaAreaProtetta[]
      | VincoloAutorizza[]
      | CatastoParticella[] = oggettiIstanza.find(
      (associazione) => associazione.id_oggetto_istanza === idOggettoIstanza
    )[list];
    if (!listaInOggettoIstanza || listaInOggettoIstanza.length === 0) {
      return false;
    }
    //(b) => a.foglio === b.foglio && a.comune.cod_belfiore_comune === b.comune.cod_belfiore_comune && a.sezione === b.sezione && a.particella === b.particella
    //(b) => a.cod_vincolo_autorizza === b.vincolo_autorizza.cod_vincolo_autorizza
    // (sugg) => sugg.cod_amministrativo === element.cod_amministrativo
    const compareRnAP = (
      a: OggettoIstanzaNatura2000 | OggettoIstanzaAreaProtetta,
      b: OggettoIstanzaNatura2000 | OggettoIstanzaAreaProtetta
    ): boolean => a.cod_amministrativo === b.cod_amministrativo;
    const compareVincoli = (
      a: VincoloAutorizza,
      b: OggettoIstanzaVincoloAutorizza
    ): boolean =>
      a.cod_vincolo_autorizza === b.vincolo_autorizza?.cod_vincolo_autorizza;
    const compareParticelle = (
      a: CatastoParticella,
      b: CatastoParticella
    ): boolean =>
      a.foglio === b.foglio &&
      a.comune.cod_belfiore_comune === b.comune.cod_belfiore_comune &&
      a.sezione === b.sezione &&
      a.particella === b.particella;
    let compare: any = compareRnAP;
    if (list === 'vincoli') {
      compare = compareVincoli;
    }
    if (list === 'dati_catastali') {
      compare = compareParticelle;
    }
    let incoerenti: boolean = false;
    listaInOggettoIstanza.forEach((element) => {
      // Devo verificare che effettivamente ci sia una lista di suggeriti che l'utente deve poter aver riscontro
      if (suggeriti?.length > 0) {
        // Esiste almeno un suggerito da verificare rispetto ai dati delle geometrie
        const isSuggested = suggeriti.some((sugg) => compare(sugg, element));
        // Se nella lista dei suggeriti è presente un oggetto, che è anche presente come geometria impostata, allora manca il suggerito
        if (!isSuggested) {
          // Mancando il suggerito, ci sono dati "incoerenti"
          incoerenti = true;
          // #
        }
      }
    });
    return incoerenti;
  }

  /**
   * Se esiste almenoo un OggettoIstanzaAreaProtetta restitituiti dal BE che non è salvato
   * nell'oggetto istanza indivividuato tramite id in input
   * restituisco true e metto da parte i suggerimenti
   * @param idOggettoIstanza
   * @param oggettiIstanzaAreaProtetta
   * @returns boolean
   */
  private isSuggestionsAndSetAreaProtetta(
    idOggettoIstanza: number,
    oggettiIstanzaAreaProtetta: OggettoIstanzaAreaProtetta[],
    oggettiIstanza: OggettoIstanza[]
  ): boolean {
    const aree_protette =
      oggettiIstanza.find(
        (associazione) => associazione.id_oggetto_istanza === idOggettoIstanza
      ).aree_protette || [];
    const apSuggest = oggettiIstanzaAreaProtetta.filter(
      (a) =>
        !aree_protette.some(
          (b) => a.cod_amministrativo === b.cod_amministrativo
        )
    );
    this._oggettiIstanzaAreaProtetta.push({
      idOggettoIstanza: idOggettoIstanza,
      items: apSuggest,
    });
    // se non esiste nelle aree protette almeno uno restituito da Geeco non presente nella aree correnti dell'oggetto istanza
    // restituisco true
    return apSuggest.length > 0;
  }

  /**
   * Recupero le natura2000 suggerite per idOggettoIstanza in input
   * @param idOggettoIstanza number
   * @returns OggettoIstanzaNatura2000[]
   */
  public getGeecoOggettiIstanzaNatura2000(
    idOggettoIstanza: number
  ): OggettoIstanzaNatura2000[] {
    return (
      this._oggettiIstanzaNatura2000.find(
        (i) => i.idOggettoIstanza === idOggettoIstanza
      )?.items || []
    );
  }

  /**
   * Recupero i siti natura2000 e li tengo da parte
   * restituiso messaggi di warning eventuali
   * @param idOggettoIstanza number
   * @param oggettiIstanza OggettoIstanza[] oggetti istanza
   * @returns Observable<string[]>
   */
  public setGeecoOggettiIstanzaNatura2000GetMessage(
    idOggettoIstanza: number,
    oggettiIstanza: OggettoIstanza[]
  ): Observable<any[]> {
    // Definisco le chiamate
    let call = null;
    // Verifico se l'istanza è in modalità di visualizzazione "VIA con VINCA"
    if (this.getShowViaconvinca()) {
      // Se esiste effettivamente il vincolo di VIA con VINCA (Codice VNCS)
      if (this.hasVincolo('VNCS')) {
        // La chiamata è il get dati senza check comuni
        call = this._ambito.getSitiReteNatura2000ByUbicazioni(
          null,
          idOggettoIstanza,
          false
        );
      }
      // #
    } else {
      // La chiamata è il get dati con check comuni (è true nella funzione chiamante di default)
      call = this._ambito.getSitiReteNatura2000ByUbicazioni(
        null,
        idOggettoIstanza
      );
    }

    // Se non è stato intercettato nessun caso, allora non ci sono dati da scricare
    if (!call) {
      // Ritorno una lista di messaggi vuota
      return of([]);
      // #
    }

    return call.pipe(
      catchError((err) => of([])), // i 404 per me sono array vuoto
      switchMap((res: OggettoIstanzaNatura2000[]) => {
        let suggeriti: boolean = false;
        if (
          this.isSuggestionsAndSetRn2000(idOggettoIstanza, res, oggettiIstanza)
        ) {
          suggeriti = true;
        }
        const incoerenti = this._checkIncoerenti(
          idOggettoIstanza,
          oggettiIstanza,
          'siti_natura_2000',
          this.getGeecoOggettiIstanzaNatura2000(idOggettoIstanza)
        );
        if (suggeriti) {
          return incoerenti
            ? of([
                this.getCodeMessageByKey(
                  'cod_messaggio_post_geeco_riepilogo_3'
                ),
              ])
            : of([
                this.getCodeMessageByKey(
                  'cod_messaggio_post_geeco_riepilogo_1'
                ),
              ]);
        }
        return incoerenti
          ? of([
              this.getCodeMessageByKey('cod_messaggio_post_geeco_riepilogo_2'),
            ])
          : of([]);
      })
    );
  }

  /**
   * Se esiste almenoo un rn2000 restitituiti dal BE che non è salvato
   * nell'oggetto istanza indivividuato tramite id in input
   * restituisco true e metto da parte i suggerimenti
   * @param idOggettoIstanza
   * @param oggettiIstanzaNatura2000
   * @returns boolean
   */
  private isSuggestionsAndSetRn2000(
    idOggettoIstanza: number,
    oggettiIstanzaNatura2000: OggettoIstanzaNatura2000[],
    oggettiIstanza: OggettoIstanza[]
  ): boolean {
    const siti_natura_2000 =
      oggettiIstanza.find(
        (associazione) => associazione.id_oggetto_istanza === idOggettoIstanza
      ).siti_natura_2000 || [];

    const rn2000Suggest = oggettiIstanzaNatura2000?.filter(
      (a) =>
        !siti_natura_2000.some(
          (b) => a.cod_amministrativo === b.cod_amministrativo
        )
    );
    this._oggettiIstanzaNatura2000.push({
      idOggettoIstanza: idOggettoIstanza,
      items: rn2000Suggest,
    });
    // se non esiste nelle rn2000 almeno uno restituito da Geeco non presente nelle rn2000 dell'oggetto istanza
    return rn2000Suggest.length > 0;
  }

  /**
   * Recuper i vincoli idro suggeriti per idOggettoIstanza in input
   * @param idOggettoIstanza number
   * @returns VincoloAutorizza[]
   */
  public getGeecoOggettiIstanzaVincoliIdro(
    idOggettoIstanza: number
  ): VincoloAutorizza[] {
    return (
      this._oggettiIstanzaVincoloAutorizzaIdro.find(
        (i) => i.idOggettoIstanza === idOggettoIstanza
      )?.items || []
    );
  }

  /**
   * Recupero i siti vincoliidro e li tengo da parte
   * restituiso messaggi di warning eventuali
   * @param idOggettoIstanza number
   * @param oggettiIstanza OggettoIstanza[] oggetti istanza
   * @returns Observable<string[]>
   */
  public setGeecoOggettiIstanzaVincoliIdroGetMessage(
    idOggettoIstanza: number,
    oggettiIstanza: OggettoIstanza[]
  ): Observable<any[]> {
    let call = null;
    if (this.callSuggest()) {
      call = this._vincoli.getVincoliIdroByIdOggettoIstanza(idOggettoIstanza);
    }

    if (!call) {
      return of([]);
    }

    return call.pipe(
      catchError((err) => of([])),
      switchMap((res: VincoloAutorizza[]) => {
        let suggeriti: boolean = false;
        if (
          this.isSuggestionsAndSetVincolo(idOggettoIstanza, res, oggettiIstanza)
        ) {
          suggeriti = true;
        }
        const incoerenti = this._checkIncoerenti(
          idOggettoIstanza,
          oggettiIstanza,
          'vincoli',
          this.getGeecoOggettiIstanzaVincoliIdro(idOggettoIstanza)
        );
        if (suggeriti) {
          return incoerenti
            ? of([
                this.getCodeMessageByKey(
                  'cod_messaggio_post_geeco_riepilogo_3'
                ),
              ])
            : of([
                this.getCodeMessageByKey(
                  'cod_messaggio_post_geeco_riepilogo_1'
                ),
              ]);
        }
        return incoerenti
          ? of([
              this.getCodeMessageByKey('cod_messaggio_post_geeco_riepilogo_2'),
            ])
          : of([]);
      })
    );
  }

  /**
   * Se esiste almenoo un rn2000 restitituiti dal BE che non è salvato
   * nell'oggetto istanza indivividuato tramite id in input
   * restituisco true e metto da parte i suggerimenti
   * @param idOggettoIstanza
   * @param oggettiIstanzaNatura2000
   * @returns boolean
   */
  private isSuggestionsAndSetVincolo(
    idOggettoIstanza: number,
    oggettiIstanzaVincoli: VincoloAutorizza[],
    oggettiIstanza: OggettoIstanza[]
  ): boolean {
    const siti_natura_2000 =
      oggettiIstanza.find(
        (associazione) => associazione.id_oggetto_istanza === idOggettoIstanza
      ).vincoli || [];
    const vincoloSuggst = oggettiIstanzaVincoli?.filter(
      (a) =>
        !siti_natura_2000.some(
          (b) =>
            a.cod_vincolo_autorizza ===
            b.vincolo_autorizza.cod_vincolo_autorizza
        )
    );
    this._oggettiIstanzaVincoloAutorizzaIdro.push({
      idOggettoIstanza: idOggettoIstanza,
      items: vincoloSuggst,
    });
    return vincoloSuggst.length > 0;
  }

  /**
   * Recuper i vincoli idro suggeriti per idOggettoIstanza in input
   * @param idOggettoIstanza number
   * @returns VincoloAutorizza[]
   */
  public getGeecoOggettiIstanzaCatastoParticella(
    idOggettoIstanza: number
  ): CatastoParticella[] {
    return (
      this._oggettiIstanzaCatastoParticella.find(
        (i) => i.idOggettoIstanza === idOggettoIstanza
      )?.items || []
    );
  }

  /**
   * Recupero le particelle suggerite da Geeco
   * restituiso messaggi di warning eventuali
   * @param idOggettoIstanza number
   * @param oggettiIstanza OggettoIstanza[] oggetti istanza
   * @returns Observable<string[]>
   */
  public setGeecoOggettiIstanzaCatastoParticellaGetMessage(
    idOggettoIstanza: number,
    oggettiIstanza: OggettoIstanza[]
  ): Observable<any[]> {
    let call = null;
    if (this.callSuggest()) {
      call = this._ambito.getParticelleCatastaliInterseca(idOggettoIstanza);
    }

    if (!call) {
      return of([]);
    }

    return call.pipe(
      catchError((err) => of([])),
      switchMap((res: CatastoParticella[]) => {
        let suggeriti: boolean = false;
        if (
          this.isSuggestionsAndSetCatastoParticella(
            idOggettoIstanza,
            res,
            oggettiIstanza
          )
        ) {
          suggeriti = true;
        }
        const incoerenti = this._checkIncoerenti(
          idOggettoIstanza,
          oggettiIstanza,
          'dati_catastali',
          this.getGeecoOggettiIstanzaCatastoParticella(idOggettoIstanza)
        );
        if (suggeriti) {
          return incoerenti
            ? of([
                this.getCodeMessageByKey(
                  'cod_messaggio_post_geeco_riepilogo_3'
                ),
              ])
            : of([
                this.getCodeMessageByKey(
                  'cod_messaggio_post_geeco_riepilogo_1'
                ),
              ]);
        }
        return incoerenti
          ? of([
              this.getCodeMessageByKey('cod_messaggio_post_geeco_riepilogo_2'),
            ])
          : of([]);
      })
    );
  }

  /**
   * Se esiste almenoo un CatastoParticella restitituiti dal BE che non è salvato
   * nell'oggetto istanza indivividuato tramite id in input
   * restituisco true e metto da parte i suggerimenti
   * @param idOggettoIstanza
   * @param oggettiIstanzaNatura2000
   * @returns boolean
   */
  private isSuggestionsAndSetCatastoParticella(
    idOggettoIstanza: number,
    catastoParticelle: CatastoParticella[],
    oggettiIstanza: OggettoIstanza[]
  ): boolean {
    let catastoParticelleCurrent: CatastoParticella[] = [];
    oggettiIstanza
      .find(
        (associazione) => associazione.id_oggetto_istanza === idOggettoIstanza
      )
      .ubicazione_oggetto?.forEach((ubi) => {
        ubi.dati_catastali?.forEach((catasto) => {
          catasto.cod_belfiore = ubi.comune.cod_belfiore_comune;
          catastoParticelleCurrent.push({
            comune: ubi.comune,
            sezione: catasto.sezione,
            foglio: catasto.foglio.toString(),
            particella: catasto.particella,
          });
        });
      });

    const cpSuggest = catastoParticelle?.filter(
      (a) =>
        !catastoParticelleCurrent.some(
          (b) =>
            a.foglio === b.foglio &&
            a.comune.cod_belfiore_comune === b.comune.cod_belfiore_comune &&
            a.sezione === b.sezione &&
            a.particella === b.particella
        )
    );
    this._oggettiIstanzaCatastoParticella.push({
      idOggettoIstanza: idOggettoIstanza,
      items: cpSuggest,
    });
    return cpSuggest.length > 0;
  }

  /**
   * Funzione che richiama il servizio di verifica delle autorità competenti.
   * Il flusso logico prevede anche la gestione delle specifiche segnalazioni riferite ai controlli effettuati su comuni e validità dei dati.
   * @param checkACParams ICheckACParams con le informazioni per la verifica.
   * @param gestioneSegnalazione IServiziErrorConfig con le informazioni per la visualizzazione della segnalazione.
   * @returns Observable<CompetenzaTerritorio[]> con la risposta ottenuta dal servizio.
   */
  checkCompetenzeOggettoSegnalazione(
    checkACParams: ICheckACParams,
    gestioneSegnalazione: IServiziErrorConfig
  ): Observable<CompetenzaTerritorio[]> {
    // Estraggo dall'input le informazioni
    const { calcoloAC3, idIstanza, tipoSelezioneAC } = checkACParams ?? {};

    // Richiamo e ritorno il risultato dell'elaborazione del servizio
    return this._adempimento
      .checkCompetenzeOggetto(idIstanza, tipoSelezioneAC, calcoloAC3)
      .pipe(
        catchError((e: ScrivaServerError) => {
          // La gestione dell'errore è specifica, richiamo la funzione apposita
          this.gestisciSegnalazioneCheckAC(e, gestioneSegnalazione);
          // Ritorno errore per la gestione del flusso
          return throwError(e);
          // #
        })
      );
  }

  /**
   * Funzione che gestisce il flusso per la segnalazione di possibili errori generati dal servizio di check autorità competenti.
   * @param e ScrivaServerError con le informazioni relative all'errore generato.
   * @param gestioneSegnalazione IServiziErrorConfig con le informazioni per la gestione della messaggistica utente.
   */
  private gestisciSegnalazioneCheckAC(
    e: ScrivaServerError,
    gestioneSegnalazione: IServiziErrorConfig
  ) {
    // La gestione dell'errore è specifica, verifico le info dell'errore di ritorno
    const erroreServer: IScrivaServerErrorInfo = e.error;
    const codiceErrore: string = erroreServer.code;

    // Verifico se è stato ritornato effettivamente un codice d'errore dal server
    if (!codiceErrore) {
      // Mancano le informazioni specifiche, gestisco il messaggio generico
      this._message.showMessageConfigs(gestioneSegnalazione);
      // Interrompo il flusso
      return;
    }

    // Recupero dall'oggetto per la gestione della segnalazione le informazioni
    const { target } = gestioneSegnalazione;
    // Definisco delle variabili di comodo
    const ph: any = null;

    // Esiste un codice, verifico i casi specifici
    switch (codiceErrore) {
      case ScrivaCodesMesseges.E027:
      case ScrivaCodesMesseges.E030:
        // Recupero dall'oggetto di errore il testo completo da visualizzare all'utente
        const testoErrore: string = e.error.title;
        // Visualizzo il messaggio ritornato dal server, forzando il testo specifico
        this._message.showMessage(codiceErrore, target, true, ph, testoErrore);
        break;
      // #
      default:
        // Non siamo in casi specifici da gestire, gestisco il messaggio generico
        this._message.showMessage(codiceErrore, target, false);
        break;
      // #
    }
  }
}
