/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Allegato } from 'src/app/features/ambito/models';
import {
  EsitoProcedimento,
  Istanza,
  IstanzaEvento,
  IstanzaStato,
  NotaIstanza,
  OggettoApp,
  ProfiloApp,
  TipoAdempimentoOggApp,
} from '../../models';
import { AttoreGestioneIstanzaEnum } from '../../utils';
import { AppConfigService } from '../app-config.service';
import { ScrivaUtilitiesService } from '../scriva-utilities/scriva-utilities.service';
import { PrefissiGestioneIstanza } from './utilities/istanza.enums';

@Injectable({ providedIn: 'root' })
export class IstanzaService {
  private beUrl = '';

  private idIstanza: number;
  /**
   * string che rappresenta la modalità di accesso ai dati dell'istanza. La stringa è composta da codici specifici.
   * @author Ismaele BOttelli
   * @notes Per i casi di gestione specifici con inclusione/esclusione di particolari quadri possono essere concatenati da |.
   *        I codice concatenati avranno la struttura:
   *          - "QDR_<CODICE_QUADRO_1>|QDR_<CODICE_QUADRO_2>" => per permettere la scrittura di soli specifici quadri;
   *          - "LOCK_QDR_<CODICE_QUADRO_1>|LOCK_QDR_<CODICE_QUADRO_2>" => per permettere scrittura di tutti i quadri, eccetto quelli nella configurazione;
   *        Per il momento non è noto se ci saranno casi misti.
   */
  private attoreGestioneIstanza: string;
  private oggAppPulsanti: TipoAdempimentoOggApp[];
  private profiliApp: ProfiloApp[];

  private _messageSuccessBO: boolean = false;

  constructor(
    private http: HttpClient,
    private config: AppConfigService,
    private _scrivaUtilities: ScrivaUtilitiesService
  ) {
    this.beUrl = this.config.getBEUrl();
  }

  /*
   * Session-stored info
   */
  setIdIstanza(id: number) {
    this.idIstanza = id;
  }

  getIdIstanza(): number {
    return this.idIstanza;
  }

  setAttoreGestioneIstanza(value: string) {
    this.attoreGestioneIstanza = value;
  }

  getAttoreGestioneIstanza() {
    return this.attoreGestioneIstanza;
  }

  setOggAppPulsanti(oggApp: TipoAdempimentoOggApp[]) {
    this.oggAppPulsanti = oggApp;
  }

  getOggAppPulsanti(): TipoAdempimentoOggApp[] {
    return this.oggAppPulsanti;
  }

  setProfiliApp(profili: ProfiloApp[]) {
    this.profiliApp = profili;
  }

  getProfiliApp(): ProfiloApp[] {
    return this.profiliApp;
  }

  getOggApp() {
    return this.profiliApp[0].oggetti_app;
  }

  get messageSuccessBO(): boolean {
    return this._messageSuccessBO;
  }

  /**
   * Setter di comodo per capire se visualizzare o meno il messaggio di successo nel riepilogo BO
   * @returns boolean
   */
  set messageSuccessBO(v: boolean) {
    this._messageSuccessBO = v;
  }

  /**
   * API Istanza
   * @param idIstanza  number
   * @param setAttoreGestioneIstanza boolean di default a false ( utile per settare AttoreGestioneIstanza)
   * @returns Observable<Istanza>
   */
  getIstanzaById(
    idIstanza: number,
    setAttoreGestioneIstanza: boolean = false
  ): Observable<Istanza> {
    return this.http
      .get<Istanza[]>(`${this.beUrl}/istanze/id/${idIstanza}`, {
        observe: 'response',
      })
      .pipe(
        map((res: HttpResponse<Istanza[]>) => {
          if (setAttoreGestioneIstanza && res.headers.get('Attore-Gestione')) {
            this.setAttoreGestioneIstanza(res.headers.get('Attore-Gestione'));
          }
          const profiliStringified = res.headers.get('ProfiliApp');
          if (profiliStringified?.length > 0) {
            this.setProfiliApp(JSON.parse(profiliStringified));
          }
          const tipoAdempiStringified = res.headers.get(
            'TipoAdempimentoOggApp'
          );
          if (tipoAdempiStringified?.length > 0) {
            // this.setOggAppPulsanti(JSON.parse(tipoAdempiStringified));
          }

          // const oggettiApplicativi = this.getProfiliOggettoAppIstanza(res.body[0]);
          // console.log('OGGETTI APPLICATIVI: ', oggettiApplicativi);

          return res.body[0];
        })
      );
  }

  salvaIstanza(istanza: Istanza, creaPratica = false): Observable<Istanza> {
    if (istanza.gestUID) {
      return this.http.put<Istanza>(
        `${this.beUrl}/istanze?flg_crea_pratica=${creaPratica}`,
        istanza
      );
    } else {
      return this.http.post<Istanza>(`${this.beUrl}/istanze`, istanza);
    }
  }

  deleteIstanza(uidIstanza: string) {
    return this.http.delete(`${this.beUrl}/istanze/${uidIstanza}`);
  }

  checkIstanza(idIstanza: number, codAdempimento: string) {
    return this.http.get(
      `${this.beUrl}/istanze/check-istanza/id-istanza/${idIstanza}/adempimento/${codAdempimento}`
    );
  }

  /**
   * Funzione che permette di effettuare una chiamata al server per aggiornare i dati di un'istanza.
   * Per questo tipo di funzione viene invocata la gestione mediante metodo "patch".
   * Il metodo richiede sempre un body, per la gestione specifica della pubblicazione, il body sarà sempre null.
   * @param idIstanza number che definisce l'id istanza sulla quale si andrà ad aggiornare i dati.
   * @param dataPubblicazione string con la data di pubblicazione dell'istanza. Il formato della data deve essere YYYY-MM-DD. La funzione sostituirà poi i separatori con quelli specifici richiesti per l'API.
   * @returns Observable<Istanza> con il risultato dell'operazione.
   */
  pubblicaIstanza(
    idIstanza: number,
    dataPubblicazione?: string
  ): Observable<Istanza> {
    // Definisco l'url
    const url = `${this.beUrl}/istanze/${idIstanza}/_pubblica`;
    // Definisco una variabile che gestira la richiesta all'API
    let req: Observable<Istanza>;

    // Verifico se è definita una data pubblicazione (!== undefined; null; '')
    if (dataPubblicazione) {
      // Il servizio necessita di una formattazione specifica, quindi sostituisco i caratteri con quello specifico
      const sostituisci = '-';
      const separatore = '/';
      const pattern = new RegExp(sostituisci, 'g');
      const data_pubblicazione = dataPubblicazione.replace(pattern, separatore);

      // È definita una data pubblicazione creo i params
      const paramsObj = { data_pubblicazione };
      const params = this._scrivaUtilities.createQueryParams(paramsObj);
      // Definisco la chiamata da effettuare al servizio e l'assegno alla variabile locale
      req = this.http.patch<Istanza>(url, null, { params });
      // #
    } else {
      // Niente data pubblicazione, definisco la chiamata senza parametri
      req = this.http.patch<Istanza>(url, null);
      // #
    }

    // Ritorno la chiamata tramite variabile
    return req;
  }

  /**
   * Funzione che imposta per un'istanza la data pubblicazione.
   * @param idIstanza number che definisce l'id istanza sulla quale si andrà ad aggiornare i dati.
   * @param dataPubblicazione string con la data di pubblicazione dell'istanza.
   * @returns Observable<Istanza> con il risultato dell'operazione.
   */
  dataPubblicazione(
    idIstanza: number,
    dataPubblicazione: string
  ): Observable<Istanza> {
    // Richiamo la funzione di aggiornamento istanza
    return this.pubblicaIstanza(idIstanza, dataPubblicazione);
  }

  /**
   * Funzione che verifica la presenza degli oggetti applicativi e ne restituisce l'oggetto
   * @param istanza
   * @returns se presenti, ritorna il merge di profili_app e tipi_adempimento_ogg_app
   */

  getProfiliOggettoAppIstanza(istanza: Istanza) {
    const elemProfili_app = istanza?.profili_app ?? [];
    const elemTipi_adempimento_ogg_app =
      istanza?.tipi_adempimento_ogg_app ?? [];

    const objAppIstanza = {
      profili_app: [...elemProfili_app],
      tipi_adempimento_ogg_app: [...elemTipi_adempimento_ogg_app],
    };

    // let arrayAppistanza = [];
    // return concat(
    //   arrayAppistanza, ...istanza?.profili_app,
    //   ...istanza?.tipi_adempimento_ogg_app
    // );

    return objAppIstanza;
  }

  /**
   * Restituisco se presente OggettoApp in base a codOggettoApp in input
   * @param istanza Istanza
   * @param codOggettoApp string
   * @returns OggettoApp
   */
  getIstanzaOggettoApp(istanza: Istanza, codOggettoApp: string): OggettoApp {
    let oggettiApp: OggettoApp[] = [];
    if (
      istanza?.tipi_adempimento_ogg_app &&
      Array.isArray(istanza.tipi_adempimento_ogg_app)
    ) {
      oggettiApp = oggettiApp.concat(
        istanza.tipi_adempimento_ogg_app.map((i) => i.oggetto_app)
      );
    }
    if (istanza?.profili_app && Array.isArray(istanza.profili_app)) {
      istanza.profili_app.forEach((p) => {
        oggettiApp = oggettiApp.concat(p.oggetti_app);
      });
    }
    return oggettiApp.find((o) => o?.cod_oggetto_app === codOggettoApp);
  }

  /*
   * API Moduli e allegati
   */
  downloadElencoAllegati(idIstanza: number) {
    return this.http.get(`${this.beUrl}/istanze/pdf-allegati/${idIstanza}`, {
      responseType: 'blob',
    });
  }

  downloadMuduloIstanza(idIstanza: number) {
    return this.http.get(
      `${this.beUrl}/istanze/${idIstanza}/_download?cod_tipologia_allegato=MOD_IST`,
      { responseType: 'blob', observe: 'response' }
    );
  }

  /**
   * Scarica un modulo istanza in base a codTipologiaAllegato
   * @param idIstanza
   * @param codTipologiaAllegato
   * @param archivia con true salva allegato
   * @returns
   */
  downloadMuduloIstanzaByCodTipologiaAllegato(
    idIstanza: number,
    codTipologiaAllegato: string,
    archivia: boolean = false,
    cancella: boolean = true
  ) {
    // SCRIVA-1319 con subtask SCRIVA-1336
    return this.http.get(
      `${this.beUrl}/istanze/${idIstanza}/_download?cod_tipologia_allegato=${codTipologiaAllegato}` +
        '' +
        (archivia ? '&archivia=true' : '') +
        (!cancella ? '&cancella=false' : ''),
      { responseType: 'blob', observe: 'response' }
    );
  }

  uploadMuduloIstanza(idIstanza: number, file): Observable<Allegato> {
    const formData = new FormData();
    formData.append('file', file, file.name);

    return this.http.post<any>(
      `${this.beUrl}/istanze/pdf-modulo-firmato-istanza/${idIstanza}`,
      formData
    );
  }

  deleteMuduloIstanza(uuidModulo: string) {
    return this.http.delete(`${this.beUrl}/allegati/${uuidModulo}`);
  }

  downloadModuloDelega(idIstanza: number) {
    return this.http.post(
      `${this.beUrl}/istanze/doc-modulo-delega-istanza/${idIstanza}`,
      null,
      { responseType: 'blob', observe: 'response' }
    );
  }

  /*
   * API Stato istanza ed eventi
   */
  getStoricoStati(idIstanza: number): Observable<IstanzaStato[]> {
    return this.http.get<IstanzaStato[]>(
      `${this.beUrl}/istanze/${idIstanza}/stati`
    );
  }

  cambioStatoIstanza(uidIstanza: string, idStato: number): Observable<Istanza> {
    return this.http
      .put<Istanza>(
        `${this.beUrl}/istanze/stato-istanza/uid-istanza/${uidIstanza}/id-stato-istanza/${idStato}`,
        null
      )
      .pipe(map((res) => res[0]));
  }

  /* 
    TODO refactor e spostare su eventi service
  */
  generaEvento(
    idIstanza: number,
    codTipoEvento: string,
    codCanaleNotifica: string = '',
    uidRichiesta: string = '',
    rifCanaleNotifica: string = '',
    dataIntegrazione: string = '',
    uid_richiesta: string = '',
    tipo_richiesta: string = ''
  ): Observable<HttpResponse<IstanzaEvento[]>> {
    // SCRIVA-1349 aggiunto uid_richiesta
    let params = this._generaEventoParams(
      idIstanza,
      codTipoEvento,
      codCanaleNotifica,
      uidRichiesta,
      rifCanaleNotifica,
      dataIntegrazione,
      uid_richiesta,
      tipo_richiesta
    );
    return this.http.post<IstanzaEvento[]>(`${this.beUrl}/eventi`, null, {
      observe: 'response',
      params: params,
    });
  }

  /**
   * Genero HttpParams per la chiamata di generazione evento
   * @param idIstanza
   * @param codTipoEvento
   * @param codCanaleNotifica
   * @param uidRichiesta
   * @param rifCanaleNotifica
   * @param dataIntegrazione
   * @param uid_richiesta
   * @returns HttpParams
   */
  _generaEventoParams(
    idIstanza: number,
    codTipoEvento: string,
    codCanaleNotifica: string = '',
    uidRichiesta: string = '',
    rifCanaleNotifica: string = '',
    dataIntegrazione: string = '',
    uid_richiesta: string = '',
    tipo_richiesta: string = ''
  ): HttpParams {
    let params = new HttpParams();
    [
      { k: 'id_istanza', v: idIstanza },
      { k: 'cod_tipo_evento', v: codTipoEvento },
      { k: 'cod_canale_notifica', v: codCanaleNotifica },
      { k: 'uid_richiesta', v: uidRichiesta },
      { k: 'rif_canale_notifica', v: rifCanaleNotifica },
      { k: 'data_integrazione', v: dataIntegrazione },
      { k: 'uid_richiesta', v: uid_richiesta },
      { k: 'tipo_richiesta', v: tipo_richiesta },
    ].map((i) => (params = this._generaEventoParam(params, i.k, i.v)));
    return params;
  }

  /**
   * Aggiungo un parametro ai params in input
   * @param params
   * @param k chiave
   * @param v valore
   * @returns HttpParams
   */
  _generaEventoParam(params: HttpParams, k: string, v: any): HttpParams {
    return v ? params.append(k, v.toString()) : params;
  }

  /*
   * API Note istanza
   */
  saveNotaIstanza(nota: NotaIstanza): Observable<NotaIstanza> {
    const endpoint = `${this.beUrl}/note-pubblicate`;
    if (nota.gestUID) {
      return this.http.put<NotaIstanza>(endpoint, nota);
    } else {
      return this.http.post<NotaIstanza>(endpoint, nota);
    }
  }

  deleteNotaIstanza(gestUID: string) {
    return this.http.delete(`${this.beUrl}/note-pubblicate/${gestUID}`);
  }

  getNotePubblicate(idIstanza: number): Observable<NotaIstanza[]> {
    return this.http.get<NotaIstanza[]>(
      `${this.beUrl}/note-pubblicate?id_istanza=${idIstanza}`
    );
  }

  /*
   * API Esiti Procedimento
   */
  getEsitiProcedimento(): Observable<EsitoProcedimento[]> {
    return this.http.get<EsitoProcedimento[]>(
      `${this.beUrl}/esiti-procedimento`
    );
  }

  /*
   * API Responsabili
   */
  getResponsabili(idIstanza: number): Observable<any> {
    return this.http.get<any>(
      `${this.beUrl}/istanze/${idIstanza}/responsabili`
    );
  }

  getTipiResponsabili(idIstanza: number): Observable<any> {
    return this.http.get<any>(
      `${this.beUrl}/tipo-responsabile/?id_istanza=${idIstanza}`
    );
  }

  /**
   * Funzione che gestisce le condizioni di lettura/scritture per i dati di un quadro istanza.
   * @param codiceQuadro string con il codice quadro di riferimento per la verifica.
   * @returns AttoreGestioneIstanzaEnum | string con la modalità di accesso alle informazioni.
   */
  accessoDatiIstanza(codiceQuadro: string): AttoreGestioneIstanzaEnum | string {
    // Recupero dal valore in sessione la modalità di gestione dell'istanza
    let gestioneIstanza: string = this.getAttoreGestioneIstanza();
    // Pulisco la stringa di gestione istanza da possibili caratteri vuoti
    gestioneIstanza = this._scrivaUtilities.trimEverything(gestioneIstanza);

    // Verifico se è stato definita in sessione una modalità
    if (!gestioneIstanza || !codiceQuadro) {
      // Non è stata definita una modalità
      return gestioneIstanza;
      // #
    }

    // Verifico se il tipo di gestione dell'istanza è impostato per la gestione specifica dei quadri
    const prefixQuadro: string = PrefissiGestioneIstanza.quadro;
    const isQuadro: boolean = gestioneIstanza.startsWith(prefixQuadro);
    // Verifico se devo gestire l'accesso istanza con logica di soli quadri specifici abilitati => ex: 'QDR_QUADRO'
    if (isQuadro) {
      // Sono in questa casistica, richiamo e ritorno la gestione specifica
      return this.accessoDatiQuadro(gestioneIstanza, codiceQuadro);
      // #
    }

    // Verifico se il tipo di gestione dell'istanza è impostato per la gestione specifica dei quadri
    const prefixQuadroLock: string = PrefissiGestioneIstanza.quadroLock;
    const isQuadroLock: boolean = gestioneIstanza.startsWith(prefixQuadroLock);
    // Verifico se devo gestire l'accesso istanza con logica con tutti i quadri abilitati, ma con l'esclusione di specifici quadri => ex: 'LOCK_QDR_QUADRO'
    if (isQuadroLock) {
      // Sono in questa casistica, richiamo e ritorno la gestione specifica
      return this.accessoDatiQuadroLock(gestioneIstanza, codiceQuadro);
      // #
    }

    // Non rientro in alcuna casistica specifica, ritorno la configurazione diretta
    return gestioneIstanza;
  }

  /**
   * Funzione che gestisce le condizioni di lettura/scritture per i dati di un quadro istanza.
   * La funzione abilita la scrittura dei dati in maniera esclusiva a specifici quadri.
   * Inoltre questa funzione è strettamente legata al flusso della funzione: accessoDatiIstanza; evitando alcuni controlli sul dato.
   * @param gestioneIstanza string con la modalità di gestione dell'istanza da gestire tramite verifiche.
   * @param codiceQuadro string con il codice quadro di riferimento per la verifica.
   * @returns AttoreGestioneIstanzaEnum | string con la modalità di accesso alle informazioni.
   */
  private accessoDatiQuadro(
    gestioneIstanza: string,
    codiceQuadro: string
  ): AttoreGestioneIstanzaEnum | string {
    // Vista la natura di come viene composta la modalità di gestione, cerco di creare un array di modalità
    const gestioniIstanza: string[] = gestioneIstanza.split(`|`);
    // Verifico se nella lista di possibili gestione istanza è presente il codice quadro esatto rispetto all'input
    const isQdrCensito = gestioniIstanza.some((codiceQuadroIstanza: string) => {
      // Verifico se il codice quadro in input è lo stesso definito come gestione istanza
      return codiceQuadroIstanza === codiceQuadro;
      // #
    });

    // Verifico se ho trovato una corrispondenza
    if (isQdrCensito) {
      // Questo quadro specifico è abilitato
      return AttoreGestioneIstanzaEnum.WRITE;
      // #
    } else {
      // Non fa parte dei quadri specifici abilitati, è quindi in sola lettura
      return AttoreGestioneIstanzaEnum.READ_ONLY;
      // #
    }
  }

  /**
   * Funzione che gestisce le condizioni di lettura/scritture per i dati di un quadro istanza.
   * La funzione disabilita la scrittura dei dati in maniera esclusiva a specifici quadri.
   * Inoltre questa funzione è strettamente legata al flusso della funzione: accessoDatiIstanza; evitando alcuni controlli sul dato.
   * @param gestioneIstanza string con la modalità di gestione dell'istanza da gestire tramite verifiche.
   * @param codiceQuadro string con il codice quadro di riferimento per la verifica.
   * @returns AttoreGestioneIstanzaEnum | string con la modalità di accesso alle informazioni.
   */
  private accessoDatiQuadroLock(
    gestioneIstanza: string,
    codiceQuadro: string
  ): AttoreGestioneIstanzaEnum | string {
    // Vista la natura di come viene composta la modalità di gestione, cerco di creare un array di modalità
    const gestioniIstanza: string[] = gestioneIstanza.split(`|`);

    // Verifico se nella lista di possibili gestione istanza è presente il codice quadro esatto rispetto all'input
    const isQdrCensito = gestioniIstanza.some(
      (codiceQuadroLockIstanza: string) => {
        // Devo aggiungere il prefisso per verificare il codice
        const prefixQuadroLock: string = PrefissiGestioneIstanza.lock;
        const codiceQuadroLock: string = `${prefixQuadroLock}_${codiceQuadro}`;
        // Verifico se il codice quadro in input è lo stesso definito come gestione istanza
        return codiceQuadroLockIstanza === codiceQuadroLock;
        // #
      }
    );

    // Verifico se ho trovato una corrispondenza
    if (isQdrCensito) {
      // Questo quadro specifico è disabilitato
      return AttoreGestioneIstanzaEnum.READ_ONLY;
      // #
    } else {
      // Non fa parte dei quadri specifici disabilitati, è quindi in scrittura
      return AttoreGestioneIstanzaEnum.WRITE;
      // #
    }
  }
}
