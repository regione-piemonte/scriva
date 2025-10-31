/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { HttpParams } from '@angular/common/http';
import { AbstractControl, FormGroup } from '@angular/forms';
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import * as moment from 'moment';
import { Moment } from 'moment';
import { DynamicObjAny } from '../../../core/interfaces/scriva.interfaces';
import { CommonConsts } from '../../consts/common-consts.consts';
import {
  ScrivaCssHandlerTypes,
  ScrivaDatesFormat,
  ScrivaFormatoDate,
} from '../../enums/scriva-utilities/scriva-utilities.enums';
import {
  IHandleBlobEvents,
  IHandleBlobRead,
  IRulesQueryParams,
} from '../../interfaces/scriva-utilities.interfaces';
import { ScrivaCss } from '../../types/scriva-utilities/scriva-utilities.types';
import { round } from 'lodash';

/**
 * Funzione che converte un generico input in un formato leggibile per gli HttpParams.
 * @param value any da convertire.
 * @returns any convertito.
 */
export const convertToHttpParamsCompatible = (value: any): any => {
  // Definisco una variabile contenitore per value
  let valueP: any;
  // Verifico la tipologia di value
  switch (typeof value) {
    case 'number':
    case 'bigint':
    case 'string':
    case 'symbol':
    case 'boolean':
      valueP = value;
      break;
    case 'object':
      valueP = JSON.stringify(value);
      break;
    default:
      valueP = '';
  }
  // Ritorno il parametro
  return valueP;
};

/**
 * Funzione che genera una stringa di dati in formato "query params" partendo da un oggetto generico.
 * @param obj DynamicObjAny che verrà utilizzato per la generazione della stringa di query params.
 * @returns string che definisce i qeury params estratti dall'oggetto in input.
 */
export const createQueryParamsUrl = (obj: DynamicObjAny): string => {
  // Verifico l'input
  if (!obj) {
    return '';
  }

  // Definsco il contenitore per i query params concatenati
  const listQParams: string[] = [];

  // Ciclo le proprietà dell'oggetto
  for (let [key, value] of Object.entries(obj)) {
    // Definisco il contenitore del parametro
    let valueP: any;

    // Verifico il value
    const isNull = value === null;
    const isUndefined = value === undefined;

    // Flag di check per la conversione
    const normalValue = !isNull && !isUndefined;

    // Verifico le condizioni
    if (normalValue) {
      // Converto il parametro
      valueP = convertToHttpParamsCompatible(value);
      // Formatto e aggiungo il query param
      listQParams.push(`${key}=${valueP}`);
    }
  }

  // Verifico che sia stato posizionato almeno un elemento nell'array
  if (listQParams.length > 0) {
    // Creo la stringa con il formato query params, concatenando i parametri con l'&
    const queryString = listQParams.join('&');
    // Generata la query string, la ritorno
    return queryString;
    // #
  } else {
    // Ritorno stringa vuota
    return '';
  }
};

/**
 * Funzione che converte un oggetto in input in un oggetto HttpParams.
 * Le proprietà dell'oggetto saranno le chiavi, mentre il valore il valore.
 * @param objParam DynamicObjAny contenente i parametri da convertire.
 * @param rules IRulesQueryParams che definisce le regole di gestione per la gestione dei dati del query param.
 * @returns HttpParams risultato della conversione.
 */
export const createQueryParams = (
  objParam: DynamicObjAny,
  rules?: IRulesQueryParams
): HttpParams => {
  // Verifico l'input
  if (!objParam) {
    return new HttpParams();
  }

  // Definisco le regole per i params
  const allowNull = rules?.allowNull || false;
  const allowUndefined = rules?.allowUndefined || false;
  const allowEmptyString = rules?.allowEmptyString || false;

  // Creo un oggetto HttpParams
  let params = new HttpParams();

  // Ciclo le proprietà dell'oggetto
  for (let [key, value] of Object.entries(objParam)) {
    // Definisco il contenitore del parametro
    let valueP: any;

    // Verifico il value
    const isNull = value === null;
    const isUndefined = value === undefined;
    const isEmptyString = value === '';

    // Flag di check per la conversione
    const cNull = allowNull && isNull;
    const cUndefined = allowUndefined && isUndefined;
    const cEmptyString = allowEmptyString && isEmptyString;
    const normalValue = !isNull && !isUndefined && !isEmptyString;

    // Verifico le condizioni
    if (cNull || cUndefined || cEmptyString || normalValue) {
      // Converto il parametro
      valueP = convertToHttpParamsCompatible(value);
      // Aggiungo il parametro all'oggetto
      params = params.append(key, valueP);
    }
  }

  // Ritorno l'oggetto
  return params;
};

/**
 * Funzione che genera una stringa alfanumerica di 10 caratteri.
 * A seconda del valore passato in input, verrà generato un id più complesso.
 * La quantità di caratteri verranno moltiplicati sulla base di complexity.
 * Quindi per complexity:
 * - 1 = random id di 10 caratteri;
 * - 2 = random id di 20 caratteri;
 * - etc...
 * @param complexity number che definisce la complessità dell'id da generare. Default: 2.
 * @returns string che definisci un id randomico.
 */
export const generateRandomId = (complexity = 2): string => {
  // Definisco la funzione interna di generazione di un id
  const randomId = (): string => Math.random().toString(36).slice(2);
  // Verifico l'input
  if (!complexity || complexity <= 0) {
    return randomId();
  }

  // Definisco il contenitore dell'id
  let id = '';
  // Itero per la complessita
  for (let i = 0; i < complexity; i++) {
    id = `${id}${randomId()}`;
  }

  // Ritorno l'id
  return id;
};

/**
 * Funzione di utility che permette di verificare se la variabile in input è una funzione.
 * @param functionToCheck Input da verificare
 * @returns boolean che indica se il parametro è una funzione (true) o non lo è (false).
 */
export const isFunction = (functionToCheck: any) => {
  return (
    functionToCheck && {}.toString.call(functionToCheck) === '[object Function]'
  );
};

/**
 * Funzione che esegue la funzione passata come parametro.
 * @param f (v?: any, ...p: any[]) => any da eseguire.
 * @param v any con il valore da passare come parametro alla funzione.
 * @param p Array di any che definisce n possibili parametri passati alla funzione.
 * @returns any come risultato dell'operazione.
 */
export const scrivaExecute = (
  f: (v?: any, ...p: any[]) => any,
  v: any,
  ...p: any[]
): any | undefined => {
  // Verifico l'input
  if (!f) {
    return false;
  }

  // Lancio la funzione
  const result = f(v, ...p);
  // Eseguo la funzione e ritorno il valore
  return result;
};

/**
 * Funzione che gestisce i dati del css, manipolando le informazioni per generare un oggetto compatibile con le direttive NgClass o NgStyle.
 * @param cssType ScrivaCssHandlerTypes il tipo di stile per gestire l'output.
 * @param css string o ScrivaCss che definisce le informazioni dello stile.
 * @returns string o ScrivaCss con la configurazione css.
 */
export const scrivaCssHandler = (
  cssType: ScrivaCssHandlerTypes,
  css?: ScrivaCss
): ScrivaCss => {
  // Verifico la configurazione del css
  const cssClass = cssType === ScrivaCssHandlerTypes.class;
  const cssStyle = cssType === ScrivaCssHandlerTypes.style;
  // Verifico che la classe di stile esista
  if (css === undefined && cssClass) {
    return '';
  }
  if (css === undefined && cssStyle) {
    return {};
  }

  // Verifico i dati del css
  const isCssString = typeof css === 'string';
  const isCssObject = typeof css === 'object';
  // Verifico la validità delle combinazioni
  const cssValid = (isCssString && cssClass) || (isCssObject && cssStyle);

  // Verifico la validità del css
  if (cssValid) {
    return css;
  }
  if (cssClass) {
    return '';
  }
  if (cssStyle) {
    return {};
  }
};

/**
 * Funzione di comodo che verifica se l'input è un oggetto Date.
 * @param d Date da verificare.
 * @returns boolean che definisce se l'input è una Date.
 */
export const isDate = (d: Date): boolean => {
  // Verifico l'input
  if (!d) {
    // Oggetto undefined
    return false;
  }

  // Verifico l'istanza dell'oggetto
  const isInstanceDate = d instanceof Date;
  const isValidDate = isInstanceDate && !isNaN(d.valueOf());

  // Ritorno se la data è valida
  return isValidDate;
};

/**
 * Funzione di comodo che verifica se un oggetto possiede la proprietà per le date validità.
 * Nello specifico, verrà controllato se esiste la proprietà "data_fine_validita" ed il suo valore.
 * @param o any contenente le informazioni per le date di validità.
 * @returns boolean che definisce se l'oggetto è attualmente valido: non esiste data_fine_validita, oppure la data fine validita è successiva ad "oggi".
 */
export const isDataValiditaAttiva = (o: any): boolean => {
  // Verifico l'oggetto
  if (!o) {
    // Non esiste l'oggetto, consideriamo non valido
    return false;
  }

  // Recupero la proprietà "data_fine_validita"
  const dataFineValidita: any = o.data_fine_validita;
  // Verifico se esiste una data fine validita
  if (!dataFineValidita) {
    // Non esiste data fine validita, consideriamo l'oggetto valido
    return true;
  }

  // Definisco una variabile di comodo per oggi
  const today = moment();

  // La data fine validita esiste, cerco di capire la sua tipologia
  const isTypeDate: boolean = isDate(dataFineValidita);
  const isMoment: boolean = moment(dataFineValidita).isValid();

  // Verifico le condizioni
  if (isTypeDate) {
    // Converto la data in moment
    const momentDFV = moment(dataFineValidita);
    // Verifico che la data di fine validità sia successiva ad oggi
    return momentDFV.isAfter(today);
    // #
  } else if (isMoment) {
    // Verifico che la data di fine validità sia successiva ad oggi
    return dataFineValidita.isAfter(today);
    // #
  }

  // Non è possibile verificare
  return false;
};

/**
 * Formatta un importo mettendo una virgola a separare i decimali ed il punto per separare le migliaia.
 * La formattazione troncherà i numeri decimali.
 * @param importo number | string da formattare.
 * @param decimal number con il numero di decimali da mandatenere.
 * @param decimaliNonSignificativi boolean che definisce se, dalla gestione dei decimali, bisogna completare i decimali con gli 0 mancanti (non significativi). Per default è false.
 * @returns string con la formattazione applicata.
 */
export const formatImportoITA = (
  importo: number | string,
  decimals?: number,
  decimaliNonSignificativi: boolean = false
): string => {
  // Controllo di esistenza
  if (importo !== 0 && (importo == undefined || importo == '')) {
    return '';
  }
  // Verifico il typing
  const isTNumber = typeof importo === 'number';
  const isTString = typeof importo === 'string';
  if (!isTNumber && !isTString) {
    // Tipo dell'input non valido
    return '';
  }

  // Trasformo l'importo in stringa, lo divido tra interi e decimali
  var parts = importo.toString().split('.');
  // Inserisco i punti delle migliaia
  parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, '.');

  // Definisco una funzione di comodo univoca per la gestione dati
  const formatIT = (parts: string[]) => parts.join(',');

  // Definisco delle condizioni specifiche e di comodo
  const soloInteri = parts.length == 1;
  const conDecimali =
    parts[1] !== undefined || parts[1] !== null || parts[1] !== '';
  const decNotDef = decimals === undefined || decimals === null;
  const noDecimals = decimals === 0;
  const someDecimals = decimals > 0;

  // Verifico se è richiesta la gestione specifica mettendo tutti i decimali non significativi
  if (decimaliNonSignificativi) {
    // Definisco quanti decimali con '0' devo piazzare
    let nonSignDecimals: number = 0;

    // Verifico la prima condizione
    if (soloInteri || !conDecimali || noDecimals) {
      // Ho solo interi, verifico se sono richiesti decimali da placeholdare
      if (someDecimals) {
        // Assegno tutti i decimali come 0 da definire
        nonSignDecimals = decimals;
        // #
      } else if (decNotDef || noDecimals) {
        // Ritorno solo la parte d'intero del numero
        return `${parts[0]}`;
        // #
      }
    } else if (conDecimali) {
      // Verifico se ci sono meno decimali di quelli richiesti (come number, ogni zero a destra non è definito)
      if (parts[1].length < decimals) {
        // Ho meno decimali di quelli richiesti, definisco gli 0 d'aggiungere
        nonSignDecimals = decimals - parts[1].length;
      }
    }

    // Verifico se ho effettivamente degli 0 d'aggiungere
    if (nonSignDecimals > 0) {
      // Verifico che esista effettivamente la parte dei decimali
      parts[1] = parts[1] ?? '';

      // Per ogni decimale mancante, aggiungo lo 0 ai decimali
      for (let i = 0; i < nonSignDecimals; i++) {
        // Aggiungo uno zero ai decimali
        parts[1] = `${parts[1]}0`;
      }

      // Ritorno l'insieme dei decimali
      return parts.join(',');
    }
  }

  // Verifico la casistica di: soliInteri o con decimali non definiti
  if (soloInteri || !conDecimali || noDecimals) {
    // Ho solo interi, verifico se sono richiesti decimali da placeholdare
    if (decNotDef || someDecimals) {
      // Metto 00 dopo la virgola
      parts.push('00');
      // Ritorno la formattazione
      return formatIT(parts);
      // #
    } else if (noDecimals) {
      // Ritorno solo la parte d'intero del numero
      return `${parts[0]}`;
      // #
    }
  }

  // Verifico che il numero abbia decimali
  if (conDecimali) {
    // se ho i decimali controllo sia di almeno 2 cifre
    if (parts[1].length < 2) {
      // se non lo è, aggiungo uno 0 alla fine
      parts[1] = parts[1] + '0';
      // #
    } else if (parts[1].length > 2 && decimals == undefined) {
      // Recupero solo i primi due decimali
      parts[1] = `${parts[1][0]}${parts[1][1]}`;
      // #
    } else if (parts[1].length > 2 && decimals != undefined) {
      // Definisco la quantità di decimali da mantenere
      let decimalsNubers: number = 0;
      // Verifico se ci sono abbastanza decimali rispetto a quelli richiesti
      if (parts[1].length >= decimals) {
        // Ci sono abbastanza decimali, definisco la quantità come l'input
        decimalsNubers = decimals;
        // #
      } else {
        // Non ci sono abbastanza elementi, mantengo quelli dell'importo
        decimalsNubers = parts[1].length;
        // #
      }

      // Verifico se c'è almeno un decimale da visualizzare
      if (decimalsNubers > 0) {
        // Effettuo una substring sui decimali e mantengo solo quelli richiesti
        parts[1] = `${parts[1].substr(0, decimalsNubers)}`;
        // #
      } else {
        // Non bisogna far vedere i decimali, rimuovo la parte dei decimali dall'array
        parts.splice(1, 1);
      }
    }
  }

  // Unisco di nuovo decimali e interi per comporre la stringa finale
  return parts.join(',');
  // #
};

/**
 * Formatta un importo italiano in un numero compatibile con javascript.
 * @param importo string da formattare.
 * @returns number con il numero correttamente convertito.
 */
export const importoITAToJsFloat = (importo: string): number => {
  // Controllo di esistenza
  if (!importo) {
    // Ritorno undefined
    return undefined;
  }

  // Definisco il contenitore principale per la stringa
  let importoItaJs = importo;
  // Definisco i caratteri da sostituire
  const regExpMigliaia = new RegExp('\\.', 'g');
  const regExpDecIT = new RegExp('\\,', 'g');
  const sepDecimaliJS = '.';

  // Effettuo una sostituzione di tutti i separatori
  importoItaJs = replaceAll(importoItaJs, regExpMigliaia, '');
  importoItaJs = replaceAll(importoItaJs, regExpDecIT, sepDecimaliJS);

  // Generata una stringa "pulita" in formato numerico JS, tento la conversione
  const importoJS: number = parseFloat(importoItaJs);

  // Verifico e ritorno l'importo se effettivamente è stato convertito
  return isNaN(importoJS) ? undefined : importoJS;
  // #
};

/**
 * ################################################################################################################
 * GESTIONE DELLE CONVERSIONI PER I TIPI DI DATA APPLICATIVE: Moment, NgbDateStruct, string (view), string (server)
 * ################################################################################################################
 */

/**
 * #########
 * UTILITIES
 * #########
 */

/**
 * Funzione di formattazione di una data, da stringa, in una data di destazione (dato i formati di inizio e fine).
 * @param date string data da formattare.
 * @param startFormat string formato della stringa in ingresso.
 * @param targetFormat string formato della stringa in uscita.
 * @returns string data formattata.
 */
export const formatDateFromString = (
  date: string,
  startFormat: string,
  targetFormat: string
): string => {
  // Dichiaro la stringa di destinazione
  let targetDate = '';

  // Verifico che gli input esistano
  if (!date || !startFormat || !targetFormat) {
    return targetDate;
  }

  // Creo un moment partendo dalla stringa d'ingresso
  const dateMoment = moment(date, startFormat);
  // Verifico che la data sia valida
  if (!dateMoment.isValid()) {
    return targetDate;
  }
  // Data valida effettuo la formattazione
  targetDate = dateMoment.format(targetFormat);

  // Ritorno la stringa formattata
  return targetDate;
};

/**
 * Funzione che effettua il convert da una data string, compatibile con l'oggetto Date di javascrpit, in un formato Moment.
 * @param date string da convertire.
 * @returns Moment convertito.
 */
export const convertStringDateToMoment = (date: string): Moment => {
  // Verifico l'input
  if (!date) {
    return null;
  }
  // Converto il date in moment
  return moment(new Date(date));
};

/**
 * Funzione di comodo che converte una data nel tipo di formato definito.
 * Se i formati non vengono specificati, il ritorno sarà undefined.
 * @param date any da convertire.
 * @param inputFormat ScrivaDatesFormat che definisce la tipologia della data in input.
 * @param outputFormat ScrivaDatesFormat che definisce la tipologia della data in output.
 * @returns Moment | NgbDateStruct | string in base al formato scelto per la configurazione.
 */
export const convertDateByType = (
  date: any,
  inputFormat: ScrivaDatesFormat,
  outputFormat: ScrivaDatesFormat
): Moment | NgbDateStruct | string => {
  // Verifico l'input
  if (!date || !inputFormat || !outputFormat) {
    // Ritorno data invalida
    return null;
  }

  // Verifico il formato di input e di output
  if (inputFormat === outputFormat) {
    // Input e output sono gli stessi, ritorno la data
    return date;
  }

  // Verifico il formato di input
  switch (inputFormat) {
    // # MOMENT
    case ScrivaDatesFormat.moment:
      // Converto la data da moment
      return convertMomentToType(date, outputFormat);
    // # VIEW DATE
    case ScrivaDatesFormat.viewFormat:
      // Converto la data da view date
      return convertViewDateToType(date, outputFormat);
    // # SERVER DATE
    case ScrivaDatesFormat.serverFormat:
      // Converto la data da server date
      return convertServerDateToType(date, outputFormat);
    // # NGBDATESTRUCT
    case ScrivaDatesFormat.ngbDateStruct:
      // Converto la data da NgbDateStruct
      return convertNgbDateStructToType(date, outputFormat);
    default:
      // Nessuna casistica trovata
      return undefined;
  }
};

/**
 * Funzione di comodo che converte una data di tipo Moment in uno specifico formato di output.
 * @param date Moment con la data da convertire.
 * @param outputFormat ScrivaDatesFormat con la data che si vuole ottenere in output.
 * @returs NgbDateStruct | string con la data formattata come impostato da output.
 */
export const convertMomentToType = (
  date: Moment,
  outputFormat: ScrivaDatesFormat
): NgbDateStruct | string => {
  // Verifico l'input
  if (!date || !outputFormat) {
    // Ritorno data invalida
    return undefined;
  }

  // Verifico la configurazione per la conversione
  switch (outputFormat) {
    // # VIEW DATE
    case ScrivaDatesFormat.viewFormat:
      // Converto la data in view date
      return convertMomentToViewDate(date);
    // # SERVER DATE
    case ScrivaDatesFormat.serverFormat:
      // Converto la data in server date
      return convertMomentToServerDate(date);
    // # NGBDATESTRUCT
    case ScrivaDatesFormat.ngbDateStruct:
      // Converto la data in NgbDateStruct
      return convertMomentToNgbDateStruct(date);
    // # DEFAULT
    default:
      // Ritorno undefined
      return undefined;
  }
};

/**
 * Funzione di comodo che converte una data di tipo NgbDateStruct in uno specifico formato di output.
 * @param date NgbDateStruct con la data da convertire.
 * @param outputFormat ScrivaDatesFormat con la data che si vuole ottenere in output.
 * @returs Moment | string con la data formattata come impostato da output.
 */
export const convertNgbDateStructToType = (
  date: NgbDateStruct,
  outputFormat: ScrivaDatesFormat
): Moment | string => {
  // Verifico l'input
  if (!date || !outputFormat) {
    // Ritorno data invalida
    return undefined;
  }

  // Verifico la configurazione per la conversione
  switch (outputFormat) {
    // # MOMENT
    case ScrivaDatesFormat.moment:
      // Converto la data in moment
      return convertNgbDateStructToMoment(date);
    // # VIEW DATE
    case ScrivaDatesFormat.viewFormat:
      // Converto la data in view date
      return convertNgbDateStructToViewDate(date);
    // # SERVER DATE
    case ScrivaDatesFormat.serverFormat:
      // Converto la data in server date
      return convertNgbDateStructToServerDate(date);
    // # DEFAULT
    default:
      // Ritorno undefined
      return undefined;
  }
};

/**
 * Funzione di comodo che converte una data di tipo server date in uno specifico formato di output.
 * @param date string in formato server date con la data da convertire.
 * @param outputFormat ScrivaDatesFormat con la data che si vuole ottenere in output.
 * @returs Moment | NgbDateStruct | string con la data formattata come impostato da output.
 */
export const convertServerDateToType = (
  date: string,
  outputFormat: ScrivaDatesFormat
): Moment | NgbDateStruct | string => {
  // Verifico l'input
  if (!date || !outputFormat) {
    // Ritorno data invalida
    return undefined;
  }

  // Verifico la configurazione per la conversione
  switch (outputFormat) {
    // # MOMENT
    case ScrivaDatesFormat.moment:
      // Converto la data in moment
      return convertServerDateToMoment(date);
    // # VIEW DATE
    case ScrivaDatesFormat.viewFormat:
      // Converto la data in view date
      return convertServerDateToViewDate(date);
    // # NGBDATESTRUCT
    case ScrivaDatesFormat.ngbDateStruct:
      // Converto la data in NgbDateStruct
      return convertServerDateToNgbDateStruct(date);
    // # DEFAULT
    default:
      // Ritorno undefined
      return undefined;
  }
};

/**
 * Funzione di comodo che converte una data di tipo Moment in uno specifico formato di output.
 * @param date stringa in formato ViewDate con la data da convertire.
 * @param outputFormat ScrivaDatesFormat con la data che si vuole ottenere in output.
 * @returs Moment | NgbDateStruct | string con la data formattata come impostato da output.
 */
export const convertViewDateToType = (
  date: string,
  outputFormat: ScrivaDatesFormat
): Moment | NgbDateStruct | string => {
  // Verifico l'input
  if (!date || !outputFormat) {
    // Ritorno data invalida
    return undefined;
  }

  // Verifico la configurazione per la conversione
  switch (outputFormat) {
    // # MOMENT
    case ScrivaDatesFormat.moment:
      // Converto la data in moment
      return convertViewDateToMoment(date);
    // # SERVER DATE
    case ScrivaDatesFormat.serverFormat:
      // Converto la data in server date
      return convertViewDateToServerDate(date);
    // # NGBDATESTRUCT
    case ScrivaDatesFormat.ngbDateStruct:
      // Converto la data in NgbDateStruct
      return convertViewDateToNgbDateStruct(date);
    // # DEFAULT
    default:
      // Ritorno undefined
      return undefined;
  }
};

/**
 * ######
 * MOMENT
 * ######
 */

/**
 * Funzione che effettua il convert da un moment ad una iso date string.
 * @param dateMoment Moment da convertire.
 * @returns string in formato Date ISO convertito.
 */
export const convertMomentToISOString = (dateMoment: Moment): string => {
  // Verifico l'input
  if (!dateMoment || !dateMoment.isValid()) {
    return '';
  }
  // Converto il moment in iso date string
  return dateMoment.toDate().toISOString();
};

/**
 * Funzione di parsing data da un formato moment al formato view date.
 * @param dateMoment Moment da convertire.
 * @returns string data convertita.
 */
export const convertMomentToViewDate = (dateMoment: Moment): string => {
  // Verifico l'input
  if (!dateMoment || !dateMoment.isValid()) {
    return '';
  }
  // Aggiorno il formato della data
  const viewFormat = ScrivaFormatoDate.view;
  // Converto il moment in iso date string
  return dateMoment.format(viewFormat);
};

/**
 * Funzione di parsing data da un formato moment al formato server date.
 * @param dateMoment Moment da convertire.
 * @returns string data convertita.
 */
export const convertMomentToServerDate = (dateMoment: Moment): string => {
  // Verifico l'input
  if (!dateMoment || !dateMoment.isValid()) {
    return '';
  }
  // Aggiorno il formato della data
  const serverFormat = ScrivaFormatoDate.server;
  // Converto il moment in iso date string
  return dateMoment.format(serverFormat);
};

/**
 * Funzione di convert da un Moment ad un NgbDateStruct.
 * @param dateMoment Moment da convertire
 * @returns NgbDateStruct convertita.
 */
export const convertMomentToNgbDateStruct = (
  dateMoment: Moment
): NgbDateStruct => {
  // Verifico l'input
  if (!dateMoment) {
    return;
  }

  // Ritorno un oggetto NgbDateStruct
  return {
    year: dateMoment.year(),
    month: dateMoment.month() + 1,
    day: dateMoment.date(),
  };
};

/**
 * ########
 * STRINGHE
 * ########
 */

/**
 * Funzione di parsing data da un formato server al formato view.
 * @param date string | NgbDateStruct data da convertire.
 * @returns string data convertita.
 */
export const convertServerDateToViewDate = (
  date: string | NgbDateStruct
): string => {
  // Verifico il tipo della data
  if (typeof date !== 'string') {
    // Parso la data da NgbDateStruct a string
    date = convertNgbDateStructToString(date, '/');
  }

  // Aggiorno il formato della data
  const viewFormat = ScrivaFormatoDate.view;
  const serverFormat = ScrivaFormatoDate.server;

  // Richiamo la funzione di formattazione
  return formatDateFromString(date, serverFormat, viewFormat);
};

/**
 * Funzione che effettua il convert da una data string con formato server ad una data NgbDateStruct.
 * @param date string da convertire.
 * @returns NgbDateStruct convertita.
 */
export const convertServerDateToNgbDateStruct = (
  date: string
): NgbDateStruct => {
  // Verifico l'input
  if (!date) {
    return null;
  }

  // Creo la classe delle costanti applicative
  const commonConsts = new CommonConsts();
  // Creo un moment
  const dateMoment = moment(date, commonConsts.DATE_FORMAT_SERVER);
  // Ritorno un'oggetto NgbDateStruct
  return {
    year: dateMoment.year(),
    month: dateMoment.month() + 1,
    day: dateMoment.date(),
  } as NgbDateStruct;
};

/**
 * Funzione che effettua il convert da una data string con formato server ad un Moment.
 * @param date string da convertire.
 * @returns Moment convertito.
 */
export const convertServerDateToMoment = (date: string): Moment => {
  // Verifico l'input
  if (!date) {
    return null;
  }

  // Creo la classe delle costanti applicative
  const commonConsts = new CommonConsts();
  // Creo un moment
  return moment(date, commonConsts.DATE_FORMAT_SERVER);
};

/**
 * Funzione di parsing data da un formato in input al formato server.
 * @param date string | NgbDateStruct data da convertire.
 * @returns string data convertita.
 */
export const convertViewDateToServerDate = (
  date: string | NgbDateStruct
): string => {
  // Verifico il tipo della data
  if (typeof date !== 'string') {
    // Parso la data da NgbDateStruct a string
    date = convertNgbDateStructToString(date, '/');
  }

  // Aggiorno il formato della data
  const viewFormat = ScrivaFormatoDate.view;
  const serverFormat = ScrivaFormatoDate.server;

  // Richiamo la funzione di formattazione
  return formatDateFromString(date, viewFormat, serverFormat);
};

/**
 * Funzione che effettua il convert da una data string con formato server ad una data NgbDateStruct.
 * @param date string da convertire.
 * @returns NgbDateStruct convertita.
 */
export const convertViewDateToNgbDateStruct = (date: string): NgbDateStruct => {
  // Verifico l'input
  if (!date) {
    return null;
  }

  // Creo la classe delle costanti applicative
  const commonConsts = new CommonConsts();
  // Creo un moment
  const dateMoment = moment(date, commonConsts.DATE_FORMAT_VIEW);
  // Ritorno un'oggetto NgbDateStruct
  return {
    year: dateMoment.year(),
    month: dateMoment.month() + 1,
    day: dateMoment.date(),
  } as NgbDateStruct;
};

/**
 * Funzione che effettua il convert da una data string con formato view ad un Moment.
 * @param date string da convertire.
 * @returns Moment convertito.
 */
export const convertViewDateToMoment = (date: string): Moment => {
  // Verifico l'input
  if (!date) {
    return null;
  }

  // Creo la classe delle costanti applicative
  const commonConsts = new CommonConsts();
  // Creo un moment
  return moment(date, commonConsts.DATE_FORMAT_VIEW);
};

/**
 * Funzione per la conversione di una stringa in formato Base64 ad un array di Uint8Array.
 * @param b64Data string che definisce un Base64 da convertire.
 * @param sliceSize number che definisce la porzione di byte per la conversione da Base64 ad array di byte.
 * @returns Array di Uint8Array risultato della conversione.
 */
export const base64ToByteArray = (
  b64Data: string,
  sliceSize: number = 512
): Uint8Array[] => {
  // Per sicurezza, metto dentro tutto all'interno di un try catch
  try {
    // Replace per la compatibilità con Internet Explorer
    b64Data = b64Data.replace(/\s/g, '');
    // Converto la stringa in caratteri "byte"
    let byteCharacters = atob(b64Data);
    // Definisco un array che conterrà i byte array dalla tokenizzazione della stringa
    let byteArrays: Uint8Array[] = [];

    // Ciclo le informazioni dei caratteri
    for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
      // Effettuo uno slice dei dati per il recupero della porzione di byte
      let slice = byteCharacters.slice(offset, offset + sliceSize);
      // Creo un array a suo volta dal pezzo di dati generato
      let byteNumbers = new Array(slice.length);

      // Ciclo i caratteri del pezzo di dati generato
      for (var i = 0; i < slice.length; i++) {
        // Definisco il codice del carattere e lo definisco come byte
        byteNumbers[i] = slice.charCodeAt(i);
      }

      // Converto l'array di byte in un carattere Uint8
      let byteArray = new Uint8Array(byteNumbers);
      // Aggiungo all'array dei bytes finale il risultato della conversione
      byteArrays.push(byteArray);
      // #
    }

    // Ritorno l'array di byte generato
    return byteArrays;
    // #
  } catch (e) {
    // Gestisco l'errore
    return [];
  }
};

/**
 * #############
 * NGBDATESTRUCT
 * #############
 */

/**
 * Funzione che formatta l'input in data: GG<separatore>MM<separatore>AAAA.
 * Se la data è già una stringa, viene ritornata senza parsing.
 * @param date string | NgbDateStruct da convertire.
 * @param separatore string che definisce il separatore della data. Il default è "-".
 * @param returnIfDateIsNull qualsiasi cosa si vuole che sia ritornata se date non è valorizzato. Il default è una stringa vuota
 * @returns string data formattata.
 */
export const convertNgbDateStructToString = (
  date: string | NgbDateStruct,
  separatore: string = '-',
  returnIfDateIsNull: any = ''
): string => {
  // Verifico che la data esista
  if (!date) {
    return returnIfDateIsNull;
  }
  // Verifico se la data non è già stringa
  if (date && typeof date === 'string') {
    return date;
  }

  // La data è un oggetto NgbDateStruct
  const dateNgb = date as NgbDateStruct;
  // Compongo la data
  const dataString = `${dateNgb.day}${separatore}${dateNgb.month}${separatore}${dateNgb.year}`;
  // Compongo il formato della data
  const dataFormat = `DD${separatore}MM${separatore}YYYY`;
  // Tramite moment creo la data e la ritorno formattata
  return moment(dataString, dataFormat).format(dataFormat);
};

/**
 * Funzione che formatta l'input in data string.
 * @param date NgbDateStruct da convertire.
 * @returns string data formattata.
 */
export const convertNgbDateStructToMoment = (date: NgbDateStruct): Moment => {
  // Verifico che la data esista
  if (!date) {
    return undefined;
  }

  // Parso l'oggetto in una stringa
  const dateS = `${date.day}/${date.month}/${date.year}`;
  // Tento di convertire la data in moment
  const dateM = moment(dateS, ScrivaFormatoDate.view);

  // Verifico se la conversione è andata a buon fine
  if (moment(dateM).isValid()) {
    // Ritorno la data convertita
    return dateM;
    // #
  } else {
    // La data aveva qualcosa che non andava, ritorno undefined
    return undefined;
  }
};

/**
 * Funzione che formatta l'input in data string.
 * Se la data è già una stringa, viene ritornata senza parsing.
 * @param date string | NgbDateStruct da convertire.
 * @param formato string che definisce il formato di destinazione.
 * @returns string data formattata.
 */
export const convertNgbDateStructToDateString = (
  date: string | NgbDateStruct,
  formato: string
): string => {
  // Verifico che la data esista
  if (!date || !formato) {
    return '';
  }
  // Verifico se la data non è già stringa
  if (date && typeof date === 'string') {
    return date;
  }

  // La data è un oggetto NgbDateStruct
  const dateNgb = date as NgbDateStruct;
  // Parso l'oggetto in un moment
  const dateM = convertNgbDateStructToMoment(dateNgb);

  // Tramite moment creo la data e la ritorno formattata
  return dateM.format(formato);
};

/**
 * Funzione che formatta l'input in data con formato "view".
 * Se la data è già una stringa, viene ritornata senza parsing.
 * @param date string | NgbDateStruct da convertire.
 * @returns string data formattata.
 */
export const convertNgbDateStructToViewDate = (
  date: string | NgbDateStruct
): string => {
  // Verifico che la data esista
  if (!date) {
    return '';
  }
  // Verifico se la data non è già stringa
  if (date && typeof date === 'string') {
    return date;
  }

  // Converto l'oggetto in un moment
  const momentDate: Moment = convertNgbDateStructToMoment(
    date as NgbDateStruct
  );
  // Verifico che il moment sia stato generato
  if (!momentDate || !momentDate.isValid()) {
    // Ritorno stringa vuota
    return '';
  }

  // Moment valido, lo converto con formattazione dedicata
  return momentDate.format(ScrivaFormatoDate.view);
};

/**
 * Funzione che formatta l'input in data con formato "server".
 * Se la data è già una stringa, viene ritornata senza parsing.
 * @param date string | NgbDateStruct da convertire.
 * @returns string data formattata.
 */
export const convertNgbDateStructToServerDate = (
  date: string | NgbDateStruct
): string => {
  // Verifico che la data esista
  if (!date) {
    return '';
  }
  // Verifico se la data non è già stringa
  if (date && typeof date === 'string') {
    return date;
  }

  // Converto l'oggetto in un moment
  const momentDate: Moment = convertNgbDateStructToMoment(
    date as NgbDateStruct
  );
  // Verifico che il moment sia stato generato
  if (!momentDate || !momentDate.isValid()) {
    // Ritorno stringa vuota
    return '';
  }

  // Moment valido, lo converto con formattazione dedicata
  return momentDate.format(ScrivaFormatoDate.server);
};

/**
 * Funzione che converte una data stringa in moment e verifica se il suo formato risulta valido.
 * @param date string con la data da verificare.
 * @param format string con il formato della data da verificare.
 * @param allow29thFeb boolean che permette di definire se accettare come data il 29 febbrario. Per default è: true.
 * @returns boolean con il risultato del check.
 */
export const checkDateFormat = (
  date: string,
  format: string,
  allow29thFeb: boolean = true
): boolean => {
  // Verifico l'input
  if (!date || !format) {
    // Ritorno false, non si può verificare
    return false;
  }

  // Converto la data in moment usando la formattazione
  const mDate = moment(date, format);
  // Verifico se la data è valida
  const isDateValid = mDate.isValid();

  // Definisco una variabile di comodo per il confronto date
  const date29Feb = '29/02';
  // Definisco una variabile di gestione per la gestione del 29 febbraio
  let isValid29Feb: boolean = true;
  // Verifico se devo controllare se la data è il 29 di febbraio
  if (!allow29thFeb) {
    // Il 29 feb non è da considerare data valida, verifico
    isValid29Feb = isDateValid && date !== date29Feb;
  }

  // Ritorno il risultato del controllo
  return isDateValid && isValid29Feb;
};

/**
 * ##################################
 * FUNZIONI DI VERIFICA OGGETTI VUOTI
 * ##################################
 */

/**
 * Funzione che verifica se un oggetto è vuoto.
 * @param o any da verificare.
 * @returns boolean che definisce se l'oggetto è vuoto o undefined.
 */
export const isEmptyObject = (o: any): boolean => {
  // Verifico l'input
  if (!o) {
    return true;
  }

  // Verifico se l'oggetto ha proprietà
  return Object.keys(o).length === 0;
};

/**
 * Funzione che verifica se un oggetto è vuoto controllando anche i suoi parametri.
 * Se uno di essi è un oggetto valorizzato, allora controlla che anch'esso sia vuoto.
 * @param o any da verificare.
 * @returns boolean che definisce se l'oggetto è vuoto o undefined.
 */
export const isEmptyObjectDeep = (o: any): boolean => {
  // Verifico l'input
  if (o === null || o === undefined || o === []) {
    return true;
  }

  // Se è una stringa valorizzata, allora la ritorno
  if (typeof o == 'string') {
    return o.length === 0;
  }

  // Ottengo l'elenco delle proprietà
  const proprieta = Object.keys(o);
  // Verifico se l'oggetto ha proprietà valorizzate. Se non ne ha è un primitivo.
  if (proprieta.length === 0) {
    return o == null;
  }

  // Controllo che ogni proprietà sia vuota ricorsivamente.
  return proprieta.every((p) => this.isEmptyObjectDeep(o[p]));
};

/**
 * Funzione che tenta di estrarre da una stringa una data per il server.
 * La stringa DEVE contenere al suo interno la seguente struttura: YYYY-MM-DD.
 * @param date string contenente le informazioni per la data.
 * @return string con la data del server estratta.
 */
export const extractServerDate = (date: string): string => {
  // Verifico l'input
  if (date == undefined) {
    // Nessuna data
    return '';
  }

  // Definisco una regex che vada a verificare il pattern: YYYY-MM-DD
  const serverDatePattern = new RegExp(/[0-9]{4}[-][0-9]{2}[-][0-9]{2}/);
  // Eseguot la regex dando in esecuzione la data
  const res: string[] = serverDatePattern.exec(date);
  // Ritorno il primo valore estratto dall'array o stringa vuota
  return res[0] ?? '';
};

/**
 * Funzione di comodo che partendo da un blob dati, genera uno specifico oggetto di alert.
 * La funzione eseguirà le funzioni agganciate agli eventi definiti tramite input.
 * @param configs IHandleBlobData che definisce le configurazioni per la generazione dell'alert.
 */
export const readBlob = (configs: IHandleBlobRead) => {
  // Estraggo le informazioni dall'input
  const { blob, eventsBehavior } = configs ?? {};
  // Verifico l'input
  if (!blob || !eventsBehavior || eventsBehavior.length === 0) {
    // Mancano le informazioni minime per la gestione dei dati
    return;
  }

  // Creo un oggetto Blob
  const blobText = new Blob([blob]);
  // Definisco il file reader per il blob dati
  const reader = new FileReader();

  // Itero la lista di eventi e comportamenti
  eventsBehavior.forEach((eBehavior: IHandleBlobEvents) => {
    // Estraggo le informazioni per l'evento
    const { event, behavior } = eBehavior;
    // Aggiungo il listener al reader file
    reader.addEventListener(event, (e: ProgressEvent<FileReader>) => {
      // Richiamo le logiche definite per l'evento
      behavior(e);
      // #
    });
  });

  // Lancio la funzione di lettura del blob tramite reader
  reader.readAsText(blobText);
  // #
};

/**
 * Funzione specifica pensata per registare il cambio di un campo ed effettuare un trim se sono presenti spazi bianchi.
 * @param form FormGroup con la referenza al form del campo.
 * @param field string con il nome del campo da trimmare.
 * @param emitEvent boolean con l'indicazione di propagazione dell'evento di change value. Per default è: true.
 */
export const trimOnChange = (
  form: FormGroup,
  field: string,
  emitEvent: boolean = true
) => {
  // Tento di sottoscrivere un ascoltatore per effettuare il trim di una stringa
  try {
    form?.get(field)?.valueChanges?.subscribe((value: string) => {
      // Tento di trimmare ed aggionrare il valore
      trimFormValue(value, form, field, emitEvent);
      // #
    });
  } catch (e) {
    // Loggo l'errore
    console.warn('trimOnChange failed', e);
  }
};

/**
 * Funzione specifica pensata per registare il cambio di un campo ed effettuare un trim se sono presenti spazi bianchi.
 * @param form FormGroup con la referenza al form del campo.
 * @param field string con il nome del campo da trimmare.
 * @param emitEvent boolean con l'indicazione di propagazione dell'evento di change value. Per default è: true.
 */
export const trimFormValue = (
  value: string,
  form: FormGroup,
  field: string,
  emitEvent: boolean = true
) => {
  // Gestisco con try catch per evitare errori
  try {
    // Verifico se esiste la stringa in input
    const isValueStr = typeof value === 'string';
    // Verifico se la stringa è da trimmare
    const hasEmptyStart = isValueStr && value[0] === ' ';
    const hasEmptyEnd = isValueStr && value[value.length - 1] === ' ';
    // Controllo le condizioni
    if (hasEmptyStart || hasEmptyEnd) {
      // Re-imposto il valore effettuando una trim
      form?.get(field)?.setValue(value.trim(), { emitEvent });
    }
    // #
  } catch (e) {
    // Loggo l'errore
    console.warn('trimOnChange failed', e);
  }
  // #
};

/**
 * Funzione che effettua una replaceAll di una stringa all'interno di un'altra stringa.
 * @param stringa string come base per la sostituzione.
 * @param trovaRegExp RegExp con la regular expression da trovare all'interno della stringa principale.
 * @param sostituisci string con la stringa da sostituire alla stringa trovata.
 * @returns string con le sostituzioni effettuate.
 */
export const replaceAll = (
  stringa: string = '',
  trovaRegExp: RegExp,
  sostituisci: string = ''
): string => {
  // Definisco il contenitore per il risultato
  let risultato = '';
  // Metto dentro tutto un try catch per evitare situazioni inattese
  try {
    // La replace all bisogna farla per forza usando replace e regex
    risultato = stringa?.replace(trovaRegExp, sostituisci) ?? '';
    // #
  } catch (e) {}

  // Ritorno il risultato
  return risultato;
};

/**
 * Funzione che verifica se all'interno di una lista di oggetti esiste già un oggetto, data la proprietà sulla quale verificare se i dati combaciano.
 * @param elemento any con l'oggetto per la verifica.
 * @param lista any[] con la lista da verificare.
 * @param proprieta string con la proprità da usare per il confronto dati.
 * @param strict boolean che definisce se il confronto tra le proprietà deve essere restrittivo. Per default è: true.
 * @returns boolean con il risultato del controllo. Se ritorna undefined, vuol dire che i parametri non sono stati definiti correttamente.
 */
export const objectInList = (
  elemento: any,
  lista: any[],
  proprieta: string,
  strict: boolean = true
): boolean => {
  // Verifico l'input
  if (!elemento || !lista || !proprieta) {
    // Non ho informazioni
    return undefined;
  }

  // Itero la lista di elementi
  const inList: boolean = lista.some((e: any) => {
    // Recupero la proprietà per il match
    const eListProperty = e[proprieta];
    const elemProperty = elemento[proprieta];
    // Ritorno il confronto
    return strict
      ? eListProperty === elemProperty
      : eListProperty == elemProperty;
    // #
  });

  // Ritorno il risultato
  return inList;
};

/**
 * Funzione che verifica se l'input è definito.
 * Con "definito" si intende che abbia un valore non undefined e non null.
 * @param v any con il valore da verificare.
 * @returns boolean con il risultato del check.
 */
export const isDefined = (v: any): boolean => {
  // Ritorno il controllo
  return v !== undefined && v !== null;
};

/**
 * Funzione che verifica se l'input è definito.
 * Con "definito" si intende che abbia un valore non undefined e non null e l'oggetto non sia senza proprietà.
 * @param v any con il valore da verificare.
 * @returns boolean con il risultato del check.
 */
export const isDefinedObject = (v: any): boolean => {
  // Ritorno il controllo
  return v !== undefined && v !== null && !isEmptyObject(v);
};

/**
 * Funzione che verifica se l'input è definito.
 * Con "definito" si intende che abbia un valore non undefined e non null e tramite input che non sia stringa vuota.
 * @param v any con il valore da verificare.
 * @param emptyStringInvalid boolean che definisce se il dato come stringa vuota è da considerarsi non definito. Per default è: true.
 * @returns boolean con il risultato del check.
 */
export const isDefinedString = (
  v: string,
  emptyStringInvalid: boolean = true
): boolean => {
  // Gestisco la casistica di stringa vuota
  const notDefinedByEmpty: boolean = emptyStringInvalid && v === '';
  // Ritorno il controllo
  return v !== undefined && v !== null && !notDefinedByEmpty;
};

/**
 * Funzione che verifica se l'input è definito.
 * Con "definito" si intende che abbia un valore non undefined e non null e tramite input che non sia lista vuota.
 * @param v any con il valore da verificare.
 * @param emptyArrayInvalid boolean che definisce se il dato come lista vuota è da considerarsi non definito. Per default è: true.
 * @returns boolean con il risultato del check.
 */
export const isDefinedArray = (
  v: any[],
  emptyArrayInvalid: boolean = true
): boolean => {
  // Gestisco la casistica di stringa vuota
  const notDefinedByEmpty: boolean = emptyArrayInvalid && v.length === 0;
  // Ritorno il controllo
  return v !== undefined && v !== null && !notDefinedByEmpty;
};

/**
 * Funzione che verifica se l'input è stringa.
 * @param v any con il valore da verificare.
 * @returns boolean con il risultato del check.
 */
export const isString = (v: any): boolean => {
  // Ritorno il controllo
  return typeof v === 'string';
};

/**
 * Funzione che verifica se l'input è stringa e la sua lunghezza è valida.
 * @param v string con il valore da verificare.
 * @param max number con il numero massimo di caratteri per la stringa.
 * @returns boolean con il risultato del check.
 */
export const maxLengthString = (v: string, max: number): boolean => {
  // Ritorno il controllo
  return isString(v) && v.length <= max;
};

/**
 * Funzione che verifica se l'input è stringa e la sua lunghezza è valida.
 * @notes Il controllo è replicato per essere uguale a quello definito sulle logiche di FormIo, usando una regex su una stringa.
 * @param v string con il valore da verificare.
 * @param interi number con il numero massimo di interi del numero.
 * @returns boolean con il risultato del check.
 */
export const interiNumero = (v: string, interi: number): boolean => {
  // Definisco la regola di regex definita su FormIo
  const formioRule: string = `(^\\d{1,${interi}}$)`;
  // Creo una regexp effettiva
  const regexp = new RegExp(formioRule);
  // Eseguo la regexp sull'input
  return isString(v) && regexp.test(v);
};

/**
 * Funzione che verifica se l'input è stringa e la sua lunghezza è valida.
 * @notes Il controllo è replicato per essere uguale a quello definito sulle logiche di FormIo, usando una regex su una stringa.
 * @param v string con il valore da verificare.
 * @param interi number con il numero massimo di interi del numero.
 * @param decimali number con il numero massimo di decimali del numero.
 * @returns boolean con il risultato del check.
 */
export const interiDecimaliNumero = (
  v: string,
  interi: number,
  decimali: number
): boolean => {
  // Definisco la regola di regex definita su FormIo
  const formioRule: string = `(^\\d{1,${interi}}$)|(^\\d{1,${interi}}[,]\\d{1,${decimali}}$)`;
  // Creo una regexp effettiva
  const regexp = new RegExp(formioRule);
  // Eseguo la regexp sull'input
  return isString(v) && regexp.test(v);
};

/**
 * Funzione di supporto che converte un numero in formato in italiano e verifica se è maggiore di 0.
 * @param v strnig con il valore da convertire e verificare.
 * @returns boolean con il risultato del check. Undefined se la conversione è fallita.
 */
export const numeroITAMaggioreDiZero = (v: string): boolean => {
  // Converto la stringa 
  const n: number = importoITAToJsFloat(v);
  // Verifico se esiste il numero
  if (n === undefined) {
    // Ritorno false
    return false;
    // #
  }

  // Effettuo la verifica
  return n > 0;
  // #
}

/**
 * Funzione che verifica la quantità di errori all'interno di un FormGroup o un FormControl.
 * @param form FormGroup | AbstractControl con l'oggetto da verificare.
 * @param errorName string con il nome del campo (credo) da verificare.
 * @returns number con la quantità di errori generati.
 */
export const countFormErrors = (
  form: FormGroup | AbstractControl,
  errorName: string
): number => {
  let result = 0;
  if (!form.valid) {
    if (
      form?.errors &&
      Object.keys(form?.errors).find((item) => item === errorName)
    ) {
      result++;
    } else {
      if (form instanceof FormGroup) {
        Object.keys(form?.controls).forEach((key) => {
          result += countFormErrors(form?.controls[key], errorName);
        });
      }
    }
  }
  return result;
};

/**
 * Funzione che verifica la quantità di errori all'interno di un FormGroup o un FormControl.
 * @param form FormGroup | AbstractControl con l'oggetto da verificare.
 * @param errorName string con il nome del campo (credo) da verificare.
 * @returns boolean con un flag che indica se il form ha almeno un errore del tipo "errorName".
 */
export const formHasErrors = (
  form: FormGroup | AbstractControl,
  errorName: string
): boolean => {
  // Richiamo la funzione di count errori
  return countFormErrors(form, errorName) > 0;
};

/**
 * Funzione di comodo che rimuove ogni spazio bianco all'interno di una stringa.
 * Se non viene definita una stringa, verrà ritornata stringa vuota.
 * @param str string da pulire da spazi bianchi.
 * @returns string con la stringa senza spazi bianchi.
 */
export const trimEverything = (str: string): string => {
  // Verifico l'input
  str = str ?? '';
  // Effettuo la replace di ogni spazio pbianco
  return str.replace(/\s/g, '');
  // #
}


/**
 * Funzione di comodo che effettua il troncamento di un numero ad una data cifra decimale. Se non definita una cifra decimale, tutti i decimali verranno rimossi.
 * ATTENZIONE: Il js è un po' strano e il truncate fatto con "toFixed" a volte può fare degli scherzi. Quindi potrebbe tornare valori inattesi.
 * @param n number da troncare.
 * @param decimals number che definisce a quale cifra decimale troncare. Per default è: 2.
 * @returns number troncato.
 */
export const mathTruncate = (n: number, decimals: number = 2): number => {
  // Verifico l'input
  if (!n) {
    // Niente numero, ritorno 0
    return 0;
  }

  // Verifico il decimal precision
  if (decimals == undefined || decimals < 0) {
    // Reimposto il decimal a 1
    decimals = 0;
  }

  // Per sicurezza wrappo tutto in un trycatch
  try {
    // LA MATEMATICA JS E I FLOAT NON VANNO MOLTO D'ACCORDO. Verifico se i decimali sono 0
    if (decimals == 0) {
      // Uso il MATH per troncare completamente i decimali senza arrotondamenti
      const truncINT = Math.trunc(n);
      // Ritorno il valore troncato
      return truncINT;
      // #
    } else {
      // Uso il MATH per calcolare il valore troncato
      const truncFLT = parseFloat(
        (Math.floor((n + Number.EPSILON) * 100) / 100).toFixed(decimals)
      );
      // Ritorno il valore troncato
      return truncFLT;
    }
    // #
  } catch (e) {
    // Come errore ritorno -1
    return -1;
  }
};



/**
 * Funzione di arrotondamento per eccesso di un numero.
 * Se non definiti, i decimali sono 0.
 * @param n number con il numero d'arrotondare.
 * @param d number con i decimali da arrotondare.
 */
export const arrotondaEccesso = (n: number, d: number = 0): number => {
  // Richiamo la funzione di lodash ed effettuo l'arrotondamento
  return round(n, d);
};

/**
 * Funzione di arrotondamento per eccesso di un numero.
 * Se non definiti, i decimali sono 0.
 * @param n number | string con il numero d'arrotondare.
 * @param d number con i decimali da arrotondare.
 */
export const arrotondaDifetto = (n: number | string, d: number = 0): number => {
  // Verifico l'input
  if (n == undefined) {
    // Ritorno undefined
    return undefined;
  }
  // Verifico il tipo dell'input
  if (typeof n === 'number') {
    // Riassegno n come stringa
    n = n.toString();
  }

  // Richiamo la funzione di lodash ed effettuo l'arrotondamento
  return Number(Number.parseFloat(n).toFixed(d));
};


/**
 * Funzione di comodo per le logiche di verifica di FormIo.
 * La funzione prende in input un valore "numero stringa" e verifica che il suo valore sia maggiore di 0.
 * La funzione gestirà il ritorno in modalità "FormIo", ossia, se il numero è > 0 verrà ritornato un valore booleano, mentre se andrà in errore sarà ritornata una stringa per gestire due casi:
 * - Numero <= 0 ==> Stringa di errore: "numero0oMinore";
 * - Numero non convertito correttamente, per errata formattazione ===> "erroreDiConversione";
 * @param formioNumber string con il numero da formattare e verificare, con il risultato maggiore di 0.
 * @returns boolean che definisce se il numero in input è > 0. Se il numero è 0 o minore, verrà ritornato l'errore "numero0oMinore", se si è verificato un errore di conversione verrà ritornato l'errore "erroreDiConversione".
 */
export const formioStringNumberGreaterThanZeroExtended = (
  formioNumber: string
): boolean | string => {
  // Converto il valore in input tramite la funzione apposita
  const numeroJS: number = importoITAToJsFloat(formioNumber);
  // Verifico se la conversione è avvenuta correttamente
  if (typeof numeroJS === 'number' && numeroJS > 0) {
    // Conversione e valore corretti
    return true;
    // #
  } else if (typeof numeroJS === 'number' && numeroJS <= 0) {
    // Conversione OK, ma valore non valido
    return 'numero0oMinore';
    // #
  } else {
    // Conversione fallita
    return 'erroreDiConversione';
    // #
  }
};

/**
 * Funzione di comodo per le logiche di verifica di FormIo.
 * La funzione si basa sulle logiche della funzione "formioStringNumberGreaterThanZeroExtended", ma ritornerà solo valori boolean.
 * @param formioNumber string con il numero da formattare e verificare, con il risultato maggiore di 0.
 * @returns boolean che definisce se il numero in input è > 0.
 */
export const formioStringNumberGreaterThanZero = (
  formioNumber: string
): boolean => {
  // Richiamo la funzione di check e verifico il tipo del risultato
  const check: boolean | string =
    formioStringNumberGreaterThanZeroExtended(formioNumber);
  // Ritorno un boolean sulla base del tipo del risultato, se è boolean allora la funzione di check è andata bene
  return typeof check === 'boolean';
  // #
};
