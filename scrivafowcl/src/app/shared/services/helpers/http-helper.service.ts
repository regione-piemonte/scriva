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
import * as moment from 'moment';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { DynamicObjAny } from '../../../core/interfaces/scriva.interfaces';
import { IRulesQueryParams } from '../../interfaces/scriva-utilities.interfaces';
import {
  createQueryParams,
  createQueryParamsUrl,
} from '../scriva-utilities/scriva-utilities.functions';
import { ScrivaUtilitiesService } from '../scriva-utilities/scriva-utilities.service';
import {
  RicercaIncrementaleResponse,
  RicercaPaginataResponse,
  ScrivaPagination,
} from './utilities/http-helper.classes';
import {
  BlobTypes,
  ResponseTypes,
  ScrivaOpenFileMethods,
  ScrivaSortTypes,
} from './utilities/http-helper.enums';
import {
  IPaginationBEReq,
  IPaginationBERes,
  IScrivaOpenFileConfigs,
  IScrivaTablePagination,
} from './utilities/http-helper.interfaces';

/**
 * Servizio dedicato alla gestione del routing applicativo.
 * Il servizio nasce dall'esigenza di gestire in maniera particolare la componentistica dell'applicazione, permettendo la gestione personalizzata del back del broswer e del pre-caricamento delle informazioni dei componenti.
 */
@Injectable({ providedIn: 'root' })
export class HttpHelperService {
  /** String costante che definisce la chiave di gestione per le ricerche. */
  private RESPONSE = 'response';
  /** String costante che definisce la chiave di gestione per le ricerche. */
  private BODY = 'body';
  /** Chiave con cui il BE restituisce nell'header le informazioni sulla paginazione. */
  public PAGINATION_INFO = 'PaginationInfo';
  /** Chiave con cui il BE restituisce nell'header le informazioni sulla paginazione incrementale. */
  public HAS_MORE_ITEMS = 'hasMoreItems';

  /**
   * Costruttore.
   */
  constructor(
    private _http: HttpClient,
    private _scrivaUtilities: ScrivaUtilitiesService
  ) {}

  /**
   * ###################################
   * FUNZIONI DI GESTIONE PER GLI HEADER
   * ###################################
   */

  /**
   * Ottiene la lista di header dalla response.
   * @param response HttpResponse che viene dal servizio da cui estrarre i dati.
   * @returns DynamicObjAny mappa chiave/valore dei valori dell'header.
   */
  getHeaders(response: HttpResponse<any>): DynamicObjAny {
    // Faccio il mapping di un oggetto header in un oggetto json
    const keys = response.headers.keys();
    const headers: DynamicObjAny = {};
    // Per ogni key vado a prendere il valore e li aggiungo all'oggetto che li conterrà
    keys.forEach((key: string) => {
      headers[key] = response.headers.get(key);
    });
    // Ritorno gli header convertiti
    return headers;
  }

  /**
   * Ottiene un particolare oggetto nell'header.
   * @param paramName key dell'oggetto da prendere nell'header.
   * @param response HttpResponse che viene dal servizio da cui estrarre i dati.
   * @returns any con il dato riferito all'header.
   */
  getHeader(paramName: string, response: HttpResponse<any>): any {
    // Controllo se esiste la response
    if (paramName && response) {
      // Prendo il parametro dall'header della response
      return response.headers.get(paramName);
    }
    // Se non esiste la response, restituisco null
    return null;
  }

  /**
   * Ottiene un oggetto generico dagli header.
   * @param paramName key dell'oggetto da prendere nell'header.
   * @param response HttpResponse che ha nell'header l'oggetto da prendere nell'header.
   * @returns l'oggetto generico corrispondente alla key data.
   */
  getObjectFromHeader(paramName: string, response: HttpResponse<any>): any {
    // Ottengo l'oggetto raw dall'header
    const itemStr = this.getHeader(paramName, response);
    // Se esiste, lo trasformo
    if (itemStr) {
      return JSON.parse(itemStr);
    }
    // Altrimenti restituisco un oggetto vuoto
    return {};
  }

  /**
   * Ottiene una oggetto RicercaPaginataResponse { item, paging } che contiene il contenuto del body della response.
   * e la paginazione restituita dal BE. Il body conterrà una lista di oggetti.
   * @param response HttpResponse che ha nell'header l'oggetto da prendere nell'header di tipo T, dove T è un array di oggetti.
   * @returns RicercaPaginataResponse { item, paging }:
   * - item contiene l'array di oggetti di tipo T nel body della response.
   * - paging contiene le informazioni sulla paginazione provenienti dal BE. In quest'ultimo caso è di interesse il numero totale di elementi trovati.
   */
  ricercaPaginataResponse<T extends any[]>(
    response: HttpResponse<T>
  ): RicercaPaginataResponse<T> {
    /** PARTE 1: prendo gli oggetti dal body */
    // Ottengo il body come oggetto di tipo T, dove T è un array di oggetti
    let body = { ...response.body! };
    // Creo un array risultato da mettere in lista
    const sources = [];
    // Body non è un array ma un oggetto, quindi per convertirlo devo parsarlo ed ottenere i singoli elementi da inserire in un array
    Object.keys(body).forEach((pra) => {
      // Prendo l'oggetto dal body
      const obj = body[pra];
      // Inserisco l'elemento nella lista risultato
      sources.push(obj);
    });

    /** PARTE 2: prendo le informazioni per la paginazione */
    // Estraggo le informazioni sulla paginazione
    let infoPaginazione: IPaginationBERes;
    infoPaginazione = this.getObjectFromHeader(this.PAGINATION_INFO, response);

    // Divido le informazioni sulla paginazione
    const { total_elements, page, total_pages, page_size } = infoPaginazione;

    // Creo l'oggetto della paginazione e lo popolo
    const paging: ScrivaPagination = {
      total: total_elements,
      currentPage: page,
      elementsForPage: page_size,
    };

    // Restituisco l'oggetto con la lista di elementi trovati e la paginazione
    return new RicercaPaginataResponse<T>({ sources: sources as T, paging });
  }

  /**
   * Ottiene una oggetto RicercaIncrementaleResponse { item, hasMoreItems } che contiene il contenuto del body della response.
   * ed un parametro che comunica se ci sono altri elementi da mostrare, restituita dal BE. Il body conterrà una lista di oggetti.
   * @param response HttpResponse che ha nell'header l'oggetto da prendere nell'header di tipo T, dove T è un array di oggetti.
   * @returns RicercaIncrementaleResponse { item, paging }:
   * - item contiene l'array di oggetti di tipo T nel body della response.
   * - paging contiene le informazioni sulla paginazione provenienti dal BE. In quest'ultimo caso è di interesse il numero totale di elementi trovati.
   */
  ricercaIncrementaleResponse<T extends any[]>(
    response: HttpResponse<T>
  ): RicercaIncrementaleResponse<T> {
    /** PARTE 1: prendo gli oggetti dal body */
    // Ottengo il body come oggetto di tipo T, dove T è un array di oggetti
    let body = { ...response.body! };
    // Creo un array risultato da mettere in lista
    const sources = [];
    // Body non è un array ma un oggetto, quindi per convertirlo devo parsarlo ed ottenere i singoli elementi da inserire in un array
    Object.keys(body).forEach((pra) => {
      // Prendo l'oggetto dal body
      const obj = body[pra];
      // Inserisco l'elemento nella lista risultato
      sources.push(obj);
    });

    /** PARTE 2: prendo le informazioni per la paginazione */
    // Estraggo le informazioni sulla paginazione
    const infoPaginazione = this.getObjectFromHeader(
      this.HAS_MORE_ITEMS,
      response
    );
    // Divido le informazioni sulla paginazione
    const { hasMoreItems } = infoPaginazione;

    // Restituisco l'oggetto con la lista di elementi trovati e la paginazione
    return new RicercaIncrementaleResponse<T>({
      sources: sources as T,
      hasMoreItems,
    });
  }

  /**
   * ############################
   * FUNZIONI DI GESTIONE RICERCA
   * ############################
   */

  /**
   * Funzione di wrapping che definisce le configurazioni specifiche per effettuare una ricerca tramite: POST.
   * @param url string che definisce l'url da richiamare.
   * @param body any contenente il body per la richiesta.
   * @param options any compatibile con le firme del servizio HttpClient di Angular, come options per la chiamata.
   * @returns Observable<RicercaPaginataResponse<T>> con la risposta alla chiamata.
   */
  searchWithPost<T extends any[]>(
    url: string,
    body?: any,
    options: any = {}
  ): Observable<RicercaPaginataResponse<T>> {
    // Aggiungo all'oggetto delle option il parametro per la gestione della ricerca
    options.observe = this.RESPONSE;
    // Lancio la chiamata per il recupero dati
    return this._http.post<T>(url, body, options).pipe(
      map((res: HttpResponse<T>) => {
        // Converto la response e genero l'oggetto per la paginazione
        return this.ricercaPaginataResponse<T>(res);
      })
    );
  }

  /**
   * Funzione di wrapping che definisce le configurazioni specifiche per effettuare una ricerca tramite: GET.
   * @param url string che definisce l'url da richiamare.
   * @param options any compatibile con le firme del servizio HttpClient di Angular, come options per la chiamata.
   * @returns Observable<RicercaPaginataResponse<T>> con la risposta alla chiamata.
   */
  searchWithGet<T extends any[]>(
    url: string,
    options: any = {}
  ): Observable<RicercaPaginataResponse<T>> {
    // Aggiungo all'oggetto delle option il parametro per la gestione della ricerca
    options.observe = this.RESPONSE;
    // Lancio la chiamata per il recupero dati
    return this._http.get<T>(url, options).pipe(
      map((res: HttpResponse<T>) => {
        // Converto la response e genero l'oggetto per la paginazione
        return this.ricercaPaginataResponse<T>(res);
      })
    );
  }

  /**
   * Funzione di wrapping che definisce le configurazioni specifiche per effettuare una ricerca tramite: POST.
   * @param url string che definisce l'url da richiamare.
   * @param body any contenente il body per la richiesta.
   * @param options any compatibile con le firme del servizio HttpClient di Angular, come options per la chiamata.
   * @returns Observable<RicercaPaginataResponse<T>> con la risposta alla chiamata.
   */
  searchWithPostIncremental<T extends any[]>(
    url: string,
    body?: any,
    options: any = {}
  ): Observable<RicercaIncrementaleResponse<T>> {
    // Aggiungo all'oggetto delle option il parametro per la gestione della ricerca
    options.observe = this.RESPONSE;
    // Lancio la chiamata per il recupero dati
    return this._http.post<T>(url, body, options).pipe(
      map((res: HttpResponse<T>) => {
        // Converto la response e genero l'oggetto per la paginazione
        return this.ricercaIncrementaleResponse<T>(res);
      })
    );
  }

  /**
   * Funzione di wrapping che definisce le configurazioni specifiche per effettuare una ricerca tramite: GET.
   * @param url string che definisce l'url da richiamare.
   * @param options any compatibile con le firme del servizio HttpClient di Angular, come options per la chiamata.
   * @returns Observable<RicercaPaginataResponse<T>> con la risposta alla chiamata.
   */
  searchWithGetIncremental<T extends any[]>(
    url: string,
    options: any = {}
  ): Observable<RicercaIncrementaleResponse<T>> {
    // Aggiungo all'oggetto delle option il parametro per la gestione della ricerca
    options.observe = this.RESPONSE;
    // Lancio la chiamata per il recupero dati
    return this._http.get<T>(url, options).pipe(
      map((res: HttpResponse<T>) => {
        // Converto la response e genero l'oggetto per la paginazione
        return this.ricercaIncrementaleResponse<T>(res);
      })
    );
  }

  /**
   * ###############################################
   * FUNZIONI DI CONFIGURAZIONI PER LE CHIAMATE HTTP
   * ###############################################
   */

  /**
   * Getter di comodo che ritorna un oggetto configurato per poter intercettare le informazioni nell'header.
   * @returns any contenente la proprietà di configurazione per intercettare i dati nell'header.
   */
  get observeResponse() {
    // Compongo un oggetto con la configurazione
    return { observe: this.RESPONSE };
  }

  /**
   * Getter di comodo che ritorna un oggetto configurato per poter intercettare le informazioni nel body.
   * @returns any contenente la proprietà di configurazione per intercettare i dati nel body.
   */
  get observeBody() {
    // Compongo un oggetto con la configurazione
    return { observe: this.BODY };
  }

  /**
   * ##########################################################
   * FUNZIONI DI SUPPORTO PER LA GENERAZIONE PER LA PAGINAZIONE
   * ##########################################################
   */

  /**
   * Funzione di supporto che crea i parametri per la paginazione da passare alle chiamate al BE.
   * @param paginazione IScrivaTablePagination che definisce l'oggetto di paginazione. Se non definito, viene generato un oggetto di default.
   * @returns HttpParams come oggetto di parametri per la richiesta http.
   */
  createPagingParams(
    paginazione: IScrivaTablePagination = {
      currentPage: 1,
      elementsForPage: 10,
      sortBy: '',
      sortDirection: ScrivaSortTypes.nessuno,
      total: 0,
    },
    params?: DynamicObjAny
  ): HttpParams {
    // Verifico l'input
    if (!paginazione) {
      // Non ritorno niente
      return null;
    }

    // Creo oggetto con parametri
    const parsedParams = this.createPagingParamsItem(paginazione);
    // Trasformo i parametri
    return this.createQueryParams({ ...params, ...parsedParams });
  }

  /**
   * Crea un oggetto con i parametri della paginazione, ma non convertiti per essere usati nelle chiamate al BE
   * @param paginazione IScrivaTablePagination con i parametri della paginazione
   * @returns un oggetto { offset: number, limit: number, sort: number }
   */
  createPagingParamsItem(
    paginazione: IScrivaTablePagination = {
      currentPage: 1,
      elementsForPage: 10,
      sortBy: '',
      sortDirection: ScrivaSortTypes.nessuno,
      total: 0,
    }
  ) {
    let sorting = '';
    // Ordinamento
    const paging = new ScrivaPagination(paginazione);
    switch (paging.sortDirection) {
      case ScrivaSortTypes.crescente: {
        sorting = '+' + paginazione.sortBy;
        break;
      }
      case ScrivaSortTypes.decrescente: {
        sorting = '-' + paginazione.sortBy;
        break;
      }
      default: {
        sorting = '';
      }
    }
    // Creo oggetto con parametri
    const parsedParams: IPaginationBEReq = {
      offset: paginazione.currentPage,
      limit: paginazione.elementsForPage,
      sort: sorting,
    };

    // Ritorno l'oggetto dei parametri
    return parsedParams;
  }

  /**
   * #########################################
   * FUNZIONI DI SUPPORTO PER LE CHIAMATE HTTP
   * #########################################
   */

  /**
   * Funzione che converte un oggetto in input in un oggetto HttpParams.
   * Le proprietà dell'oggetto saranno le chiavi, mentre il valore il valore.
   * @param objParam DynamicObjAny contenente i parametri da convertire.
   * @param rules IRulesQueryParams che definisce le regole di gestione per la gestione dei dati del query param.
   * @returns HttpParams risultato della conversione.
   */
  createQueryParams(
    objParam: DynamicObjAny,
    rules?: IRulesQueryParams
  ): HttpParams {
    // Richiamo la funzione di utility
    return createQueryParams(objParam, rules);
  }

  /**
   * Funzione che genera una stringa di dati in formato "query params" partendo da un oggetto generico.
   * @param obj DynamicObjAny che verrà utilizzato per la generazione della stringa di query params.
   * @returns string che definisce i qeury params estratti dall'oggetto in input.
   */
  createQueryParamsUrl(obj: DynamicObjAny): string {
    // Richiamo la funzione di utility
    return createQueryParamsUrl(obj);
  }

  /**
   * #####################################
   * FUNZIONI DI DOWNLOAD ED APERTURA FILE
   * #####################################
   */

  /**
   * Esegue il download di un file.
   * @param url string con l'url del servizio che ritorna il blob con il file.
   * @param responseType ResponseType opzionale, indica il tipo della risposta.
   * @returns Observable con l'HttpResponse contenente il blob scaricato.
   */
  downloadFile(
    url: string,
    responseType: ResponseTypes = ResponseTypes.blob
  ): Observable<any> {
    // Creo le options ed assegno i parametri
    const options: any = {};
    options.responseType = responseType;
    options.observe = this.RESPONSE;
    // Eseguo la chiamata
    return this._http.get<any>(url, options);
  }

  /**
   * Scarica ed apre un file generico dal BE.
   * @param url string con l'url del servizio che fa scaricare il file.
   * @param responseType ResponseType indica il tipo di file nel body della response.
   * @returns Observable con l'HttpResponse contenente il blob scaricato.
   */
  downloadAndOpenFile(
    url: string,
    responseType: ResponseTypes = ResponseTypes.blob
  ): Observable<any> {
    // Scarico il file
    return this.downloadFile(url, responseType).pipe(
      tap((response: HttpResponse<any>) => {
        // Apro il file
        this.openFileByHTTPResponse(response);
      })
    );
  }

  /**
   * Gestisce la risposta della funzione downloadFile aprendo il file scaricato in un nuovo tab.
   * @param resp la response della chiamata downloadFile.
   */
  openFileByHTTPResponse(resp: HttpResponse<any>) {
    // Costanti di comodo
    const contentDisposition = 'Content-Disposition';
    const filenamePrefix = 'filename=';
    // Gestisco l'header
    const contentDispositionHeader = resp.headers.get(contentDisposition);
    // Prendo il nome del file
    const fileName = contentDispositionHeader.split(filenamePrefix)[1];
    // Determino il type del file dall'estensione
    const mimeType = this.mimeTypeByIScrivaOpenFileConfigs(fileName);
    // Estraggo lo stream del file dal body della response
    const stream = resp.body;

    // Creo la configurazione per l'apertura del file
    const configs: IScrivaOpenFileConfigs = {
      fileName,
      stream: stream,
      mimeType,
    };

    // Apro il file
    this.openFile(configs);
  }

  /**
   * Gestisce la risposta della funzione downloadFile aprendo il file scaricato in un nuovo tab.
   * @param configs IScrivaOpenFileConfigs che definisce la configurazione per l'apertura del file.
   */
  openFile(configs: IScrivaOpenFileConfigs) {
    // La configurazione è obbligatoria
    if (!configs) {
      throw new Error('openFile - no configuration defined');
    }

    // Recupero dall'oggetto le informazioni per il file
    let { fileName, stream, openMethod, convertBase64 } = configs;
    // fileName e stream sono obbligatori
    if (!fileName || !stream) {
      throw new Error('openFile - fileName or stream undefined');
    }

    // Determino il type del file dall'estensione
    const type = this.mimeTypeByIScrivaOpenFileConfigs(fileName, configs);

    // Definisco l'array di byte per generare un Blob
    let byteArray: any[] = [];
    // Verifico se lo stream è da convertire da Base64
    if (convertBase64 == undefined || convertBase64 === true) {
      // Converto l'informazione del Base64 in un byte array
      byteArray = this._scrivaUtilities.base64ToByteArray(stream);
      // #
    } else {
      // Definisco il byte array come un array dello stream
      byteArray = [stream];
      // #
    }

    // Creo un blob a partire dallo stream in input
    const blob = new Blob(byteArray, { type });
    // Creo l'url per accedere alla risorsa scaricata
    const url = window.URL.createObjectURL(blob);

    // Creo un tag <a href="linkAllaRisorsaScaricata"> nel DOM
    const htmlLink = 'a';
    const link = document.createElement(htmlLink);

    // Assegno l'href all'<a> creato
    link.href = url;
    // Definiamo la metodologia d'apertura del file
    this.openFileMethod(link, fileName, openMethod);

    // Click programmatico sull'elemento <a> per attivarlo
    link.click();
    // Rimuovo l'oggetto creato sulla finestra
    window.URL.revokeObjectURL(url);
    // Rimuovo <a>
    link.remove();
  }

  /**
   * Funzione di comodo che gestisce la metodologia di apertura di un file.
   * La gestione avverrà mediante aggiornamento per referenza dell'oggetto in input.
   * @param link HTMLAnchorElement dell'istanza dell'oggetto del DOM per la gestione del file.
   * @param fileName string che definisce il nome del file da scaricare. Se non è definito, viene generato un nome senza estensione.
   * @param method ScrivaOpenFileMethods con il metodo d'apertura del file.
   */
  private openFileMethod(
    link: HTMLAnchorElement,
    fileName: string,
    method: ScrivaOpenFileMethods
  ) {
    // Verifico l'input
    if (!link) {
      // Niente configurazione
      return;
    }

    // Verifico e gestisco il file name
    const fn = fileName ?? `${moment().format('YYYYMMDDHHmmssSSS')}`;

    // Verifico la metodologia scelta per l'apertura
    switch (method) {
      case ScrivaOpenFileMethods.download: {
        // Richiesto il download diretto
        link.download = fn;
      }
      case ScrivaOpenFileMethods.openInTab: {
        // Apro il file in una nuova tab
        link.target = '_blank';
      }
      default: {
        // Per default viene scaricato il file direttamente
        link.download = fn;
      }
    }
  }

  /**
   * Funzione che estrae il MIMEType in base alla configurazione in input.
   * @param fileName string nome del file.
   * @param configs IScrivaOpenFileConfigs configurazioni del mimeType.
   * @returns string MIMEType.
   */
  private mimeTypeByIScrivaOpenFileConfigs(
    fileName: string,
    configs?: IScrivaOpenFileConfigs
  ): string {
    // Se non c'è una configurazione, la ricavo dal nome del file
    if (!configs) {
      return this.mimeTypeByFilename(fileName);
    }
    // Estraggo le possibili configurazioni dall'oggetto
    const { extension, mimeType } = configs;
    // Diamo priorità al mimetype se definito
    if (mimeType != undefined && mimeType != '') {
      // Ritorno direttamente il mimetype
      return mimeType;
    }
    // Controllo se esiste l'estensione
    if (extension != undefined && extension != '') {
      // Ritorno il mimetype in base all'estensione
      return this.mimeTypeByExtentionFile(extension);
    }
    // Se non si entra nelle condizioni ritorno per filename
    return this.mimeTypeByFilename(fileName);
  }

  /**
   * Ottiene il MIME type dal nome del file in base all'estensione.
   * @param fileName il nome completo del file.
   * @returns string del MIME type del file.
   */
  private mimeTypeByFilename(fileName: string): string {
    // Controllo di esistenza
    if (fileName == null || fileName.length == 0 || fileName.indexOf('.') < 0) {
      return '';
    }
    // Splitto il nome del file e lo ribalto
    const names = fileName.split('.').reverse();
    // Prendo l'estensione
    const ext = names[0].toLowerCase();
    // Se esiste lo ritorno
    return this.mimeTypeByExtentionFile(ext);
  }

  /**
   * Ottiene il MIME type dall'estensione.
   * @param ext string con l'estensione di un file (senza punto)
   * @returns BlobTypes tipo del file
   */
  private mimeTypeByExtentionFile(ext: string) {
    // Recupero dall'enum dei tipi file accedendo per proprietà
    const type = BlobTypes[ext];
    // Se esiste lo ritorno
    return type ?? '';
  }
}
