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
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { ScrivaServerError } from '../../../core/classes/scriva.classes';
import { ScrivaCodesMesseges } from '../../../core/enums/scriva-codes-messages.enums';
import { IAlertConfig } from '../../interfaces/scriva-utilities.interfaces';
import { Istanza, Quadro } from '../../models';
import { AppConfigService } from '../app-config.service';
import { IstanzaService } from '../istanza/istanza.service';
import { LoggerService } from '../logger.service';
import { MessageService } from '../message/message.service';
import {
  IGeecoOpen,
  IGeecoQuitUrlParams,
  IGeoGeeco,
  IGeoGeecoConfigs,
} from './utilities/geeco.interfaces';
import { DynamicObjAny } from 'src/app/core/interfaces/scriva.interfaces';

/**
 * Servizio che permette l'utilizzo delle funzioni di ricerca e gestione mappa di Geeco.
 */
@Injectable({ providedIn: 'root' })
export class GeecoService {
  /** String che definsce l'url path per: /geeco */
  private PATH_GEECO = '/geeco';

  /** String che definsce l'id HTML dell'elemento utilizzato per la messaggistica d'errore per Geeco. */
  private GEECO_DIV_MSG = 'emptyDiv';
  /** String che definisce l'errore standard per la comunicazione del fallimento di scarico istanza per Geeco. */
  private ERR_DES =
    'Si è verificato un errore. Tra qualche secondo verrai reindirizzato alla pagina principale.';
  /** String che defniisce il path applicativo per: /home */
  private SCRIVA_PATH_HOME = '/home';
  /** String che definisce la tipologia di gestione per la configurazione dati. */
  private ATTORE_GESTIONE_ISTANZA = 'WRITE';
  /** String con chiave per salvare ATTORE_GESTIONE_ISTANZA prima di andare su geeco  */
  private ATTORE_GESTIONE_ISTANZA_GEECO = 'attoreGestioneGeeco';

  /* String che definisce la base path URL dell'applicazione per le chiamate alle API. */
  private beUrl: string = '';

  /**
   * Costruttore.
   */
  constructor(
    private _appConfig: AppConfigService,
    private _http: HttpClient,
    private _logger: LoggerService,
    private _message: MessageService,
    private _istanza: IstanzaService,
    private _route: ActivatedRoute,
    private _router: Router,
    private _spinner: NgxSpinnerService
  ) {
    // Inizializzo il base path dell'applicazione
    this.beUrl = this._appConfig.getBEUrl();
  }

  /**
   * #######################################
   * RECUPERO DELLE CONFIGURAZIONI CON GEECO
   * #######################################
   */

  /**
   * Funzione di recupero delle informazioni di configurazione per Geeco.
   * @param idOggettoIstanza number definisce l'id oggetto istanza per lo scarico delle configurazioni di geeco.
   * @param idRuoloApplicativo number default 1 (provvisorio) che definisce l'id ruolo applicativo di riferimento per le configurazioni.
   * @returns Observable<any> con la configurazione per Geeco.
   * @deprecated La nuova funzione usata per Geeco è: postGeoGeeco. Questa sarà eliminata.
   */
  getGeecoConfig(
    idOggettoIstanza: number,
    idRuoloApplicativo: number = 1
  ): Observable<any> {
    // Definisco l'url della chiamata
    const urlGeeco = `${this.PATH_GEECO}`;
    const urlRuoloApp = `/id-ruolo-applicativo/${idRuoloApplicativo}`;
    const urlOggettoIst = `/id-oggetto-istanza/${idOggettoIstanza}`;
    // Definisco l'url finale
    const url = `${this.beUrl}${urlGeeco}${urlRuoloApp}${urlOggettoIst}`;

    // Richiamo l'API per lo scarico delle configurazioni
    return this._http.get<any>(url);
  }

  /**
   * Funzione che scarica le configurazioni per accedere a Geeco.
   * @param oggConfigGeeco IGeoGeecoConfigs che definisce l'oggetto di configurazione per le geometrie Geeco
   */
  postGeoGeeco(oggConfigGeeco: IGeoGeecoConfigs): Observable<IGeoGeeco> {
    // Definisco l'url della chiamata
    const url = `${this.beUrl}${this.PATH_GEECO}/url`;

    // Richiamo l'API per lo scarico delle informazioni
    return this._http.post<IGeoGeeco>(url, oggConfigGeeco);
  }

  /**
   * Metto in pancia ultimo attoreGestione prima di redirect su Geeco
   * @param attoreGestioneGeeco
   */
  setAttoreGestioneGeeco(attoreGestioneGeeco: string) {
    sessionStorage.setItem(
      this.ATTORE_GESTIONE_ISTANZA_GEECO,
      attoreGestioneGeeco
    );
  }

  /**
   * Recupero ultimo attoreGestione salvato
   * @returns string con attoreGestione salvato prima di andare su Geeco
   */
  private _getAttoreGestioneGeeco(): string {
    const attoreGestioneGeeco: string = sessionStorage.getItem(
      this.ATTORE_GESTIONE_ISTANZA_GEECO
    );
    sessionStorage.removeItem(this.ATTORE_GESTIONE_ISTANZA_GEECO);
    return attoreGestioneGeeco;
  }

  /**
   * ############################################
   * FUNZIONI DI APERTURA GEECO E GESTIONE FLUSSO
   * ############################################
   */

  /**
   * Funzione che gestisce un flusso standard per l'apertura di geeco per la geolocalizzazione.
   * @param configs IGeecoOpen con tutte le informazioni necessarie per l'apertura di Geeco.
   * @returns Observable<IGeoGeeco> con il risultato dell'apertura di Geeco.
   */
  openGeeco(configs: IGeecoOpen): Observable<IGeoGeeco> {
    // Estraggo dall'input le informazioni per la connessione a Geeco
    const geoGeeco: IGeoGeecoConfigs = configs?.geoGeeco;
    const attoreGestioneIstanza: string = configs?.attoreGestioneIstanza;
    const errorConfigs: IAlertConfig = configs?.errorConfigs;

    // Lancio la post per generare la connessione a Geeco
    return this.generateGeecoConnection(geoGeeco, attoreGestioneIstanza).pipe(
      catchError((e: ScrivaServerError) => {
        // Si è verificato un errore, gestisco la segnalazione utente
        this.onOpenGeecoError(e, errorConfigs);
        // Ritorno l'errore anche al chiamante
        return throwError(e);
        // #
      }),
      tap((connection: IGeoGeeco) => {
        // Gestisco il flusso di success
        this.onGeneratedGeecoConnection(connection, errorConfigs);
        // #
      })
    );
  }

  /**
   * Funzione gestisce tutte le informazioni di set per aprire poi Geeco per l'inserimento delle geometrie su mappa.
   * @param geoGeeco IGeoGeecoConfigs con le configurazioni da passare a geeco per l'apertura.
   * @param attoreGestioneIstanza string con l'indicazione dell'attore gestione istanza da impostare come attore geeco.
   * @returns Observable<IGeoGeeco> con il risultato della generazione dati per la connessione a Geeco.
   */
  generateGeecoConnection(
    geoGeeco: IGeoGeecoConfigs,
    attoreGestioneIstanza: string
  ) {
    // Metto da parte attoreGestioneIstanza corrente prima di redirect su geeco
    this.setAttoreGestioneGeeco(attoreGestioneIstanza);
    // Lancio e ritorno la chiamata all'API
    return this.postGeoGeeco(geoGeeco);
  }

  /**
   * Funzione di supporto che gestisce il flusso dati quando la generazione delle configurazioni di connessione a Geeco sono ritornate.
   * @param connection IGeoGeeco con le informazioni di connessione a Geeco.
   * @param errorConfigs IAlertConfig con le informazioni per gestire l'errore di geeco.
   */
  private onGeneratedGeecoConnection(
    connection: IGeoGeeco,
    errorConfigs: IAlertConfig
  ) {
    // Recupero l'url di connessione a Geeco
    const url = connection?.url;
    // Verifico che esista effettivamente un url per la connessione a Geeco
    if (url) {
      // Apro in una nuova finestra l'aplicativo Geeco
      window.open(url, '_self');
      // #
    } else {
      // Si è verificato un errore, gestisco la segnalazione utente
      this.onOpenGeecoError(null, errorConfigs);
    }
  }

  /**
   * Funzione di supporto per la gestione della segnalazione dell'errore per l'apertura di Geeco.
   * @param e ScrivaServerError con il dettaglio d'errore generato.
   * @param errorConfigs IAlertConfig con le informazioni per gestire l'errore di geeco.
   */
  private onOpenGeecoError(e: ScrivaServerError, errorConfigs: IAlertConfig) {
    // Si è verificato un errore, gestisco la segnalazione utente
    const code =
      e?.error?.code ?? errorConfigs?.code ?? ScrivaCodesMesseges.E100;
    const target = errorConfigs?.idDOM;
    const autoFade = errorConfigs?.autoFade ?? false;

    // Inserisco una verifica con log
    if (!target) {
      // Loggo un warning perché l'errore non si vedrà mai
      this._logger.warning('onOpenGeecoError', 'target not defined');
      // #
    }

    // Visualizzo il messaggio
    this._message.showMessage(code, target, autoFade);
  }

  /**
   * Funzione che genera i quit url params per geeco.
   * Questa funzione genera la proprietà "quit_url_params" dell'oggetto IGeoGeecoConfigs partendo da un oggetto qualunque.
   * Le informazioni devono essere variabili primitive, altrimenti si potrebbero verifcare errori nella formattazione.
   * @param params any con le informazioni da definire come quit url params.
   * @returns string con la composizione dei query parameters di ritorno da geeco.
   */
  createGeecoQuitUrlParams(params: any): string {
    // Verifico esista l'oggetto
    if (!params) {
      // Manca la configurazione
      return '';
    }

    // Definisco la stringa con tutti i parametri d'aggiungere
    let queryParams: string = '';
    // Itero le proprietà dell'oggetto e genero i query params
    for (let [key, value] of Object.entries(params)) {
      // Definisco la chiave come nome del query params and value come valore
      queryParams = `&${queryParams}${key}=${value}`;
      // #
    }

    // Ritorno tutti i query params concatenati
    return queryParams;
  }

  /**
   * ####################################################
   * FUNZIONI DI NAVIGAZIONE SCRIVA DOPO RITORNO DA GEECO
   * ####################################################
   */

  /**
   * Funzione che effettua lancia le logiche di aggiornamento dati per i dati del json dato di un'istanza.
   * @param idIstanza number che definisce l'id istanza per la quale effettuare la navigazione su Scriva.
   * @param otherParams any come oggetto contenente parametri da passare alla pagina di gestione istanza.
   */
  updateGeecoDataOnIstanza(idIstanza: number, otherParams?: any) {
    // Lancio lo spinner di caricamento
    this._spinner.show();

    // Richiamo la funzione di scarico istanza e spostamento sulla pagina
    this.navigateGeecoToScrivaReq(idIstanza, otherParams).subscribe({
      next: (istanza: Istanza) => {
        // Chiudo lo spinner di caricamento
        this._spinner.hide();
        // #
      },
      error: (e: ScrivaServerError) => {
        // Chiudo lo spinner di caricamento
        this._spinner.hide();
        // #
      },
    });
  }

  /**
   * Funzione che effettua la navigazione di ritorno da Geeco a Scriva.
   * @param idIstanza number che definisce l'id istanza per la quale effettuare la navigazione su Scriva.
   * @param otherParams any come oggetto contenente parametri da passare alla pagina di gestione istanza.
   */
  navigateGeecoToScriva(idIstanza: number, otherParams?: any) {
    // Lancio lo spinner di caricamento
    this._spinner.show();

    // Richiamo la funzione di scarico istanza e spostamento sulla pagina
    this.navigateGeecoToScrivaReq(idIstanza, otherParams).subscribe({
      next: (i: Istanza) => {
        // Chiudo lo spinner di caricamento
        this._spinner.hide();
        // #
      },
      error: (e: ScrivaServerError) => {
        // Chiudo lo spinner di caricamento
        this._spinner.hide();
        // #
      },
    });
  }

  /**
   * Funzione che scarica i dati dell'istanza ed effettua la navigazione di ritorno da Geeco a Scriva.
   * Questa funzione ritorna l'observable per la subscription.
   * @param idIstanza number che definisce l'id istanza per la quale effettuare la navigazione su Scriva.
   * @param otherParams any come oggetto contenente parametri da passare alla pagina di gestione istanza.
   * @returns Observable<Istanza> con il dato scaricato dell'istanza.
   */
  navigateGeecoToScrivaReq(
    idIstanza: number,
    otherParams?: any
  ): Observable<Istanza> {
    // Lancio il recupero dell'istanza by id
    return this._istanza.getIstanzaById(idIstanza).pipe(
      tap((istResponse: Istanza) => {
        // Gestisco le logiche a seguito della response
        this.onIstanzaRetreive(istResponse, otherParams);
        // #
      }),
      catchError((e: ScrivaServerError) => {
        // Gestisco le logiche a seguito di un errore sulla chiamata
        this.onIstanzaError(e);
        // Ritorno l'errore
        return throwError(e);
        // #
      })
    );
  }

  /**
   * Funzione richiamata nel momento in cui è ottenuta la response per l'istanza da usare per la navigazione di Geeco.
   * @param istResponse Istanza ottenuta dalla chiamata al servizio.
   * @param otherParams any come oggetto contenente parametri da passare alla pagina di gestione istanza.
   */
  private onIstanzaRetreive(istResponse: Istanza, otherParams?: any) {
    // Estraggo le informazioni dall'oggetto recuperato
    const { id_istanza } = istResponse || {};
    const { adempimento } = istResponse || {};
    const { tipo_adempimento } = adempimento || {};

    // Recupero il codice ambito dall'istanza restituita
    const codAmbito = tipo_adempimento?.ambito?.cod_ambito;
    // Recupero il codice adempimento dall'istanza restituita
    const codAdempimento = adempimento?.cod_adempimento;

    // Richiamo la navigazione interna a SCRIVA
    this.navigateToInnerRoute(
      id_istanza,
      codAmbito,
      codAdempimento,
      otherParams
    );
  }

  /**
   * Funzione richiamata nel momento in cui si è verificato un errore sulla chiamata di scarico dell'istanza per la navigazione di Geeco.
   * @param e any definisce l'oggetto di errore generato dal server.
   */
  private onIstanzaError(e: any) {
    // Definisco le configurazioni per la messaggistica di Geeco
    const errCode = ScrivaCodesMesseges.E100;
    const target = this.GEECO_DIV_MSG;
    const autoFade = false;
    const placeholders = null;
    const errorDes = this.ERR_DES;

    // Scrivo in console l'errore
    this._logger.error('ERR GEECO REDIRECT ', e);

    // Richiamo la messaggistica d'errore per Geeco
    this._message.showMessage(
      errCode,
      target,
      autoFade,
      placeholders,
      errorDes
    );

    // Definisco i millisecondi per la redirect
    const timeout = 5000;
    // Imposto un timer per il redirect alla pagina di home
    setTimeout(() => {
      // Redirect alla home
      this._router.navigate([this.SCRIVA_PATH_HOME]);
      // #
    }, timeout);
  }

  /**
   * Funzione che gestisce la navigazione specifica di Geeco a seguito del suo "ritorno" su SCRIVA.
   * @param idIstanza number id dell'istanza d'interesse per la navigazione a seguito del ritorno da Geeco.
   * @param codAmbito string come riferimento dell'ambito associato all'adempimento.
   * @param codAdempimento string codice dell'adempimento dei dati della scheda.
   * @param otherParams any come oggetto contenente parametri da passare alla pagina di gestione istanza.
   */
  navigateToInnerRoute(
    idIstanza: number,
    codAmbito: string,
    codAdempimento: string,
    otherParams: any = {}
  ) {
    // Definisco il path fisso di navigazione a seguito della callback da Geeco
    const navigateUrl = `../ambito/${codAmbito}/istanza/${codAdempimento}`;

    const attoreGestioneGeeco: string = this._getAttoreGestioneGeeco();
    // se presente AttoreGestioneGeeco in session storage lo utilizzio diversamente uso quello settato in alto come default del servizio
    const attoreGestioneIstanza = attoreGestioneGeeco
      ? attoreGestioneGeeco
      : this.ATTORE_GESTIONE_ISTANZA;

    // Tramite servizio di navigazione interna, invoco il route
    this._router.navigate([navigateUrl], {
      relativeTo: this._route,
      state: {
        // Destruturo l'oggetto per primo, se ci sono proprietà duplicate sotto, quelle sotto avranno la precedenza
        ...otherParams,
        idIstanza: idIstanza,
        attoreGestioneIstanza: attoreGestioneIstanza,
        geecoFlag: true,
      },
    });
  }

  
  /**
   * Funzione che estrae i dati specifici per la callback di uscita da Geeco.
   * La struttura è definita in maniera specifica per le captazioni.
   * @param routeState DynamicObjAny che definisce l'oggetto dello state del route di Angular.
   * @returns IGeecoQuitUrlParams con i dati estratti dai query params di callback.
   */
  retreiveGeecoQuitUrlParams(routeState: DynamicObjAny): IGeecoQuitUrlParams {
    // Verifico l'input
    if (!routeState) {
      // Configurazione assente
      return undefined;
    }

    // Estraggo le informazioni dallo state route
    const codQuadro = routeState.codQuadro?.toString();
    const idTQ = parseInt(routeState.idTipoQuadro);
    const idTipoQuadro = !isNaN(idTQ) ? idTQ : undefined;

    // Per questo specifico oggetto, andiamo a recuperare le informazioni tramite nome della proprietà
    const callbackParams: IGeecoQuitUrlParams = { codQuadro, idTipoQuadro };

    // Ritorno le informazioni estratte dall'oggetto
    return callbackParams;
  }

  /**
   * Funzione di comodo che verifica le informazioni di configurazione in input e controlla se all'interno dell'array di steps è presente un oggetto che corrisponda alle condizioni.
   * @param steps Quadro[] che definisce gli step applicativi per la verifica.
   * @param geecoQUP IGeecoQuitUrlParams contenente le informazioni ritornate dalla callback di Geeco.
   * @returns number che definisce l'indice posizionale dello step dall'array in input.
   */
  getStepIndexByGeecoQuitCallback(
    steps: Quadro[],
    geecoQUP: IGeecoQuitUrlParams
  ): number {
    // Verifico l'input
    if (!steps || !geecoQUP) {
      // Niente configurazioni
      return -1;
    }

    // Estraggo le informazioni di geeco dalla configurazione in input
    const { codQuadro, idTipoQuadro } = geecoQUP;
    // Verifico l'indice posizionale dei dati sulla base delle configurazioni in input
    const iStep = steps.findIndex((s: Quadro) => {
      // Definisco i due flag per il match tra dati, impostati di default a true e valorizzati solo se esistono le configurazioni per il match
      let isCodQ = false;
      let isIdTQ = false;

      // Verifico se esiste la configurazione per il codice quadro
      if (codQuadro) {
        // Recupero il codice quadro dello step iterato
        const { cod_quadro } = s;
        // Verifico il match
        isCodQ = cod_quadro === codQuadro;
      }
      // Verifico se esiste la configurazione per l'id tipo quadro
      if (idTipoQuadro !== undefined) {
        // Recupero l'id tipo quadro dello step iterato
        const id_tipo_quadro = s.tipo_quadro?.id_tipo_quadro;
        // Verifico il match
        isIdTQ = id_tipo_quadro === idTipoQuadro;
      }

      // Ritorno l'insieme delle condizioni
      return isCodQ || isIdTQ;
    });

    // Ritorno l'indice posizionale dello step
    return iStep;
  }
}
