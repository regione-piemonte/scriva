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
import { Injectable } from '@angular/core';
import { AbstractControl, FormControl, FormGroup } from '@angular/forms';
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import * as moment from 'moment';
import { Moment } from 'moment';
import {
  DynamicObjAny,
  DynamicObjString,
} from '../../../core/interfaces/scriva.interfaces';
import {
  IMappaErroriFormECodici,
  IScrivaDatepickerConfig,
} from '../../components/form-inputs/form-input/utilities/form-input.interfaces';
import {
  ScrivaCssHandlerTypes,
  ScrivaDatesFormat,
} from '../../enums/scriva-utilities/scriva-utilities.enums';
import {
  IHandleBlobRead,
  IRulesQueryParams,
} from '../../interfaces/scriva-utilities.interfaces';
import { ScrivaCss } from '../../types/scriva-utilities/scriva-utilities.types';
import { MessageService } from '../message/message.service';
import {
  arrotondaDifetto,
  arrotondaEccesso,
  base64ToByteArray,
  checkDateFormat,
  convertDateByType,
  convertMomentToISOString,
  convertMomentToNgbDateStruct,
  convertMomentToServerDate,
  convertMomentToType,
  convertMomentToViewDate,
  convertNgbDateStructToDateString,
  convertNgbDateStructToMoment,
  convertNgbDateStructToServerDate,
  convertNgbDateStructToType,
  convertNgbDateStructToViewDate,
  convertServerDateToMoment,
  convertServerDateToNgbDateStruct,
  convertServerDateToType,
  convertServerDateToViewDate,
  convertStringDateToMoment,
  convertViewDateToServerDate,
  convertViewDateToType,
  countFormErrors,
  createQueryParams,
  createQueryParamsUrl,
  extractServerDate,
  formatDateFromString,
  formatImportoITA,
  formHasErrors,
  formioStringNumberGreaterThanZeroExtended,
  generateRandomId,
  importoITAToJsFloat,
  isDataValiditaAttiva,
  isEmptyObject,
  isEmptyObjectDeep,
  isFunction,
  objectInList,
  readBlob,
  scrivaCssHandler,
  scrivaExecute,
  trimEverything,
} from './scriva-utilities.functions';
import { ScrivaServerError } from 'src/app/core/classes/scriva.classes';

@Injectable({
  providedIn: 'root',
})
export class ScrivaUtilitiesService {
  /**
   * Costruttore.
   */
  constructor(private _messages: MessageService) {}

  /**
   * Funzione di comodo che rimuove ogni spazio bianco all'interno di una stringa.
   * Se non viene definita una stringa, verrà ritornata stringa vuota.
   * @param str string da pulire da spazi bianchi.
   * @returns string con la stringa senza spazi bianchi.
   */
  trimEverything(str: string): string {
    // Richiamo la funzione di utility
    return trimEverything(str);
    // #
  }

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
  generateRandomId(complexity = 2): string {
    // Richiamo la funzione di generazione
    return generateRandomId(complexity);
  }

  /**
   * Funzione di utility che permette di verificare se la variabile in input è una funzione.
   * @param functionToCheck Input da verificare
   * @returns boolean che indica se il parametro è una funzione (true) o non lo è (false).
   */
  isFunction(functionToCheck: any) {
    // Richiamo la funzione di utility
    return isFunction(functionToCheck);
  }

  /**
   * Funzione che esegue la funzione passata come parametro.
   * @param f (v?: any, ...p: any[]) => any da eseguire.
   * @param v any con il valore da passare come parametro alla funzione.
   * @param p Array di any che definisce n possibili parametri passati alla funzione.
   * @returns any come risultato dell'operazione.
   */
  scrivaExecute(
    f: (v?: any, ...p: any[]) => any,
    v: any,
    ...p: any[]
  ): any | undefined {
    // Richiamo la funzione di utility
    return scrivaExecute(f, v, ...p);
  }

  /**
   * Funzione che gestisce i dati del css, manipolando le informazioni per generare un oggetto compatibile con le direttive NgClass o NgStyle.
   * @param cssType ScrivaCssHandlerTypes il tipo di stile per gestire l'output.
   * @param css string o ScrivaCss che definisce le informazioni dello stile.
   * @returns string o ScrivaCss con la configurazione css.
   */
  scrivaCssHandler(cssType: ScrivaCssHandlerTypes, css?: ScrivaCss): ScrivaCss {
    // Richiamo la funzione di utility
    return scrivaCssHandler(cssType, css);
  }

  /**
   * Funzione di comodo che verifica se un oggetto possiede la proprietà per le date validità.
   * Nello specifico, verrà controllato se esiste la proprietà "data_fine_validita" ed il suo valore.
   * @param o any contenente le informazioni per le date di validità.
   * @returns boolean che definisce se l'oggetto è attualmente valido: non esiste data_fine_validita, oppure la data fine validita è successiva ad "oggi".
   */
  isDataValiditaAttiva(o: any): boolean {
    // Richiamo la funzione di controllo
    return isDataValiditaAttiva(o);
  }

  /**
   * Formatta un importo mettendo una virgola a separare i decimali ed il punto per separare le migliaia.
   * La formattazione troncherà i numeri decimali.
   * @param importo number | string da formattare.
   * @param decimal number con il numero di decimali da mandatenere.
   * @param decimaliNonSignificativi boolean che definisce se, dalla gestione dei decimali, bisogna completare i decimali con gli 0 mancanti (non significativi). Per default è false.
   * @returns string con la formattazione applicata.
   */
  formatImportoITA(
    importo: number | string,
    decimals?: number,
    decimaliNonSignificativi: boolean = false
  ): string {
    // Richiamo la funzione di comodo
    return formatImportoITA(importo, decimals, decimaliNonSignificativi);
    // #
  }

  /**
   * Formatta un importo italiano in un numero compatibile con javascript.
   * @param importo string da formattare.
   * @returns number con il numero correttamente convertito.
   */
  importoITAToJsFloat(importo: string): number {
    // Richiamo la funzione di comodo
    return importoITAToJsFloat(importo);
    // #
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
   * #################################
   * FUNZIONI PER LA GESTIONE DEI FORM
   * #################################
   */

  /**
   * Funzione di comodo recupera tutti i messaggi di errore, dato un FormGroup e una mappatura: [{ erroreForm: string, erroreCodice: string }].
   * Gli errori saranno ordinati in base alla struttura della mappatura.
   * @param formGroup FormGroup dalla quale estrarre gli errori.
   * @param mappatura Array di MappaErroriFormECodici contente le chiavi per il recupero degli errori.
   * @returns Array di string contenente tutti i messaggi d'errore.
   */
  getAllErrorMessagesFromForm(
    formGroup: FormGroup,
    mappatura: IMappaErroriFormECodici[]
  ): string[] {
    // Verifico che gli input siano definiti
    if (!formGroup || !mappatura) return [];

    // Recupero tutti gli errori dalla form
    const erroriForm = this.getAllFormErrors(formGroup);
    // Ritorno la lista degli errori
    return this.estraiMessaggiErroreDaOggettoErrori(erroriForm, mappatura);
  }

  /**
   * Funzione di comodo recupera tutti i messaggi di errore, dato un FormControl e una mappatura: [{ erroreForm: string, erroreCodice: string }].
   * Gli errori saranno ordinati in base alla struttura della mappatura.
   * @param formControl FormGroup | FormControl dalla quale estrarre gli errori.
   * @param mappatura Array di MappaErroriFormECodici contente le chiavi per il recupero degli errori.
   * @returns Array di string contenente tutti i messaggi d'errore.
   */
  getErrorMessagesFromForm(
    formControl: FormGroup | FormControl,
    mappatura: IMappaErroriFormECodici[]
  ): string[] {
    // Verifico che gli input siano definiti
    if (!formControl || !mappatura) {
      return [];
    }

    // Recupero tutti gli errori dalla form
    const erroriForm = formControl.errors || {};
    // Ritorno la lista degli errori
    return this.estraiMessaggiErroreDaOggettoErrori(erroriForm, mappatura);
  }

  /**
   * Funzione di comodo recupera tutti i messaggi di errore, dato un oggetto di errori chiave/valore e una mappatura: [{ erroreForm: string, erroreCodice: string }].
   * Gli errori saranno ordinati in base alla struttura della mappatura.
   * @param errori Oggetto dinamico DynamicObjString chiave/valore contenente gli errori.
   * @param mappatura Array di MappaErroriFormECodici contente le chiavi per il recupero degli errori.
   * @returns Array di string contenente tutti i messaggi d'errore.
   */
  private estraiMessaggiErroreDaOggettoErrori(
    errori: DynamicObjString = {},
    mappatura: IMappaErroriFormECodici[] = []
  ): string[] {
    // Definisco l'array di messaggi vuoto
    const messaggiErrore = [];

    // Ciclo la mappatura degli errori
    for (let i = 0; i < mappatura.length; i++) {
      // Estraggo la mappatura
      const mappa = mappatura[i];

      // Verifico se esiste il messaggio definito per la form all'interno dell'oggetto di errori
      if (errori[mappa.erroreForm]) {
        // Recupero il codice di errore
        const errorceCodice = mappa.erroreCodice;
        // Se esiste una label per il messaggio d'errore, l'aggiungo
        const labelCampo = mappa.nomeCampo?.label || '';
        // Recupero il messaggio
        const msg: string =
          this._messages.getDescrizioneMessaggio(errorceCodice);
        // Compongo il messaggio d'errore
        const errore = labelCampo ? `${labelCampo} | ${msg}` : msg;

        // Aggiungo l'errore alla lista
        messaggiErrore.push(errore);
      }
    }

    // Ritorno la lista degli errori
    return messaggiErrore;
  }

  /**
   * Funzione di comodo che estrae tutti gli errori dall'oggetto FormGroup e dagli oggetti figli FormControl.
   * @param form FormGroup dalla quale estrarre gli errori.
   * @returns { [key:string] : string } contenente tutti gli errori con chiave (nome errore) e valore.
   */
  getAllFormErrors(form: FormGroup): { [key: string]: string } {
    // Definisco una lista di errori vuota
    let errors = {};

    // Estraggo gli errori dal form group
    const errFormGroup = this.getFormGroupErrors(form);
    // Estraggo gli errori dal form group
    const errFormControl = this.getFormControlsErrors(form);

    // Concateno gli errori
    errors = { ...errFormGroup, ...errFormControl };

    // Ritorno la lista di errori
    return errors;
  }

  /**
   * Funzione di comodo che estrae tutti gli errori dall'oggetto FormGroup.
   * @param form FormGroup dalla quale estrarre gli errori.
   * @returns { [key:string] : string } contenente tutti gli errori con chiave (nome errore) e valore.
   */
  getFormGroupErrors(form: FormGroup): { [key: string]: string } {
    // Ritorno la lista di errori
    return this.getFormErrors(form.errors);
  }

  /**
   * Funzione di comodo che estrae tutti gli errori dall'oggetto FormGroup, per i suoi relativi figli FormController.
   * @param form FormGroup dalla quale estrarre gli errori.
   * @returns { [key:string] : string } contenente tutti gli errori con chiave (nome errore) e valore.
   */
  getFormControlsErrors(form: FormGroup): { [key: string]: string } {
    // Definisco una lista di errori vuota
    let errors = {};

    // Recupero gli errori sul FormGroup
    const formControls = form.controls;
    // Verifico che esistano degli errori
    if (formControls) {
      // Ciclo gli errori e per ogni errore creo una entry nell'array
      for (const control in formControls) {
        // Verifico se il controller è invalid
        if (formControls[control].invalid) {
          // Estraggo la lista degli errori
          const erroriControl = this.getFormErrors(
            formControls[control].errors
          );
          // Concateno gli errori
          errors = { ...errors, ...erroriControl };
        }
      }
    }

    // Ritorno la lista di errori
    return errors;
  }

  /**
   * Funzione che itera all'interno dell'oggetto errore e genera un oggetto di oggetti errori.
   * @param errorObj { [key:string] : string } contenente i dati dell'errore.
   * @returns DynamicObjString contenente tutti gli errori con chiave (nome errore) e valore.
   */
  private getFormErrors(errorObj: { [key: string]: string }): DynamicObjString {
    // Definisco una oggetto di errori vuoto
    let errors = {};

    // Verifico che esistano degli errori
    if (errorObj) {
      // Ciclo gli errori e per ogni errore creo una entry nell'array
      for (const tipoErrore in errorObj) {
        // Verifico che all'interno dell'oggetto principale non ci sia già l'errore
        if (!errors[tipoErrore]) {
          // Aggiungo l'errore all'oggetto
          errors[tipoErrore] = errorObj[tipoErrore];
        }
      }
    }

    // Ritorno la lista di errori
    return errors;
  }

  /**
   * Funzione che verifica se per un FormContrl esiste uno specifico validatore.
   * @param formGroup FormGroup.
   * @param formControlName string che identifica un FormControl.
   * @param validator string che identifica il validatore da verificare.
   * @returns boolean true se esiste un validatore con il nome ricercato, false se non esiste.
   */
  hasValidator(
    formGroup: FormGroup,
    formControlName: string,
    validator: string
  ): boolean {
    // Tento di recuperare i validatori
    const formControl = formGroup.get(formControlName);
    // Verifico che esistano i validatori
    if (formControl.validator) {
      // Tento di estrarre i validatori
      const validators = formControl.validator({} as AbstractControl);
      // Ritorno la verifica sui validatori e sul contenuto
      return validators && validators.hasOwnProperty(validator);
    }

    // Fallback
    return false;
  }
    /**
   * Funzione di supporto che estrae il codice d'errore da un errore generato dal server.
   * Se esiste la definizione viene ritornato il messaggio relativo, altrimenti undefined.
   * @param e ScrivaServerError per il recupero delle informazioni.
   * @returns string | undefined con il messaggio d'errore se viene trovato.
   */
    getMessageFromScrivaServerError(e: ScrivaServerError): string {
      // Verifico l'input
      if (!e) {
        // Niente dati
        return;
      }
  
      // Cerco di estrarre il codice d'errore
      const { code } = e.error || {};
      // Verifico se esiste il codice
      if (code) {
        // Recupero il messaggio dal servizio
        return this._messages.getDescrizioneMessaggio(code);
        // #
      } else {
        // Niente messaggio
        return undefined;
      }
    }

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
  formatDateFromString = (
    date: string,
    startFormat: string,
    targetFormat: string
  ): string => {
    // Richiamo la funzione di utility
    return formatDateFromString(date, startFormat, targetFormat);
  };

  /**
   * Funzione che effettua il convert da una data string, compatibile con l'oggetto Date di javascrpit, in un formato Moment.
   * @param date string da convertire.
   * @returns Moment convertito.
   */
  convertStringDateToMoment(date: string): Moment {
    // Richiamo la funzione di utility
    return convertStringDateToMoment(date);
  }

  /**
   * Funzione di comodo che converte una data nel tipo di formato definito.
   * Se i formati non vengono specificati, il ritorno sarà undefined.
   * @param date any da convertire.
   * @param inputFormat ScrivaDatesFormat che definisce la tipologia della data in input.
   * @param outputFormat ScrivaDatesFormat che definisce la tipologia della data in output.
   * @returns Moment | NgbDateStruct | string in base al formato scelto per la configurazione.
   */
  convertDateByType(
    date: any,
    inputFormat: ScrivaDatesFormat,
    outputFormat: ScrivaDatesFormat
  ): Moment | NgbDateStruct | string {
    // Richiamo la funzione di utility
    return convertDateByType(date, inputFormat, outputFormat);
  }

  /**
   * Funzione di comodo che converte una data di tipo Moment in uno specifico formato di output.
   * @param date Moment con la data da convertire.
   * @param outputFormat ScrivaDatesFormat con la data che si vuole ottenere in output.
   * @returs NgbDateStruct | string con la data formattata come impostato da output.
   */
  convertMomentToType(
    date: Moment,
    outputFormat: ScrivaDatesFormat
  ): NgbDateStruct | string {
    // Richiamo la funzione di utility
    return convertMomentToType(date, outputFormat);
  }

  /**
   * Funzione di comodo che converte una data di tipo NgbDateStruct in uno specifico formato di output.
   * @param date NgbDateStruct con la data da convertire.
   * @param outputFormat ScrivaDatesFormat con la data che si vuole ottenere in output.
   * @returs Moment | string con la data formattata come impostato da output.
   */
  convertNgbDateStructToType(
    date: NgbDateStruct,
    outputFormat: ScrivaDatesFormat
  ): Moment | string {
    // Richiamo la funzione di utility
    return convertNgbDateStructToType(date, outputFormat);
  }

  /**
   * Funzione di comodo che converte una data di tipo server date in uno specifico formato di output.
   * @param date string in formato server date con la data da convertire.
   * @param outputFormat ScrivaDatesFormat con la data che si vuole ottenere in output.
   * @returs Moment | NgbDateStruct | string con la data formattata come impostato da output.
   */
  convertServerDateToType(
    date: string,
    outputFormat: ScrivaDatesFormat
  ): Moment | NgbDateStruct | string {
    // Richiamo la funzione di utility
    return convertServerDateToType(date, outputFormat);
  }

  /**
   * Funzione di comodo che converte una data di tipo Moment in uno specifico formato di output.
   * @param date stringa in formato ViewDate con la data da convertire.
   * @param outputFormat ScrivaDatesFormat con la data che si vuole ottenere in output.
   * @returs Moment | NgbDateStruct | string con la data formattata come impostato da output.
   */
  convertViewDateToType(
    date: string,
    outputFormat: ScrivaDatesFormat
  ): Moment | NgbDateStruct | string {
    // Richiamo la funzione di utility
    return convertViewDateToType(date, outputFormat);
  }

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
  convertMomentToISOString(dateMoment: Moment): string {
    // Richiamo la funzione di utility
    return convertMomentToISOString(dateMoment);
  }

  /**
   * Funzione di parsing data da un formato moment al formato view date.
   * @param dateMoment Moment da convertire.
   * @returns string data convertita.
   */
  convertMomentToViewDate(dateMoment: Moment): string {
    // Richiamo la funzione di utility
    return convertMomentToViewDate(dateMoment);
  }

  /**
   * Funzione di parsing data da un formato moment al formato server date.
   * @param dateMoment Moment da convertire.
   * @returns string data convertita.
   */
  convertMomentToServerDate(dateMoment: Moment): string {
    // Richiamo la funzione di utility
    return convertMomentToServerDate(dateMoment);
  }

  /**
   * Funzione di convert da un Moment ad un NgbDateStruct.
   * @param dateMoment Moment da convertire
   * @returns NgbDateStruct convertita.
   */
  convertMomentToNgbDateStruc(dateMoment: Moment): NgbDateStruct {
    // Richiamo la funzione di utility
    return convertMomentToNgbDateStruct(dateMoment);
  }

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
  convertServerDateToViewDate(date: string | NgbDateStruct): string {
    // Richiamo la funzione di utility
    return convertServerDateToViewDate(date);
  }

  /**
   * Funzione che effettua il convert da una data string con formato server ad una data NgbDateStruct.
   * @param date string da convertire.
   * @returns NgbDateStruct convertita.
   */
  convertServerDateToNgbDateStruct(date: string): NgbDateStruct {
    // Richiamo la funzione di utility
    return convertServerDateToNgbDateStruct(date);
  }

  /**
   * Funzione che effettua il convert da una data string con formato server ad un Moment.
   * @param date string da convertire.
   * @returns Moment convertito.
   */
  convertServerDateToMoment(date: string): Moment {
    // Richiamo la funzione di utility
    return convertServerDateToMoment(date);
  }

  /**
   * Funzione di parsing data da un formato in input al formato server.
   * @param date string | NgbDateStruct data da convertire.
   * @returns string data convertita.
   */
  convertViewDateToServerDate(date: string | NgbDateStruct): string {
    // Richiamo la funzione di utility
    return convertViewDateToServerDate(date);
  }

  /**
   * #############
   * NGBDATESTRUCT
   * #############
   */

  /**
   * Funzione che formatta l'input in data string.
   * @param date NgbDateStruct da convertire.
   * @returns Moment data formattata.
   */
  convertNgbDateStructToMoment(date: NgbDateStruct): Moment {
    // Richiamo la funzione di utility
    return convertNgbDateStructToMoment(date);
  }

  /**
   * Funzione che formatta l'input in data string.
   * Se la data è già una stringa, viene ritornata senza parsing.
   * @param date string | NgbDateStruct da convertire.
   * @param formato string che definisce il formato di destinazione.
   * @returns string data formattata.
   */
  convertNgbDateStructToDateString(
    date: string | NgbDateStruct,
    formato: string
  ): string {
    // Richiamo la funzione di utility
    return convertNgbDateStructToDateString(date, formato);
  }

  /**
   * Funzione che formatta l'input in data con formato "view".
   * Se la data è già una stringa, viene ritornata senza parsing.
   * @param date string | NgbDateStruct da convertire.
   * @returns string data formattata.
   */
  convertNgbDateStructToViewDate(date: string | NgbDateStruct): string {
    // Richiamo la funzione di utility
    return convertNgbDateStructToViewDate(date);
  }

  /**
   * Funzione che formatta l'input in data con formato "server".
   * Se la data è già una stringa, viene ritornata senza parsing.
   * @param date string | NgbDateStruct da convertire.
   * @returns string data formattata.
   */
  convertNgbDateStructToServerDate(date: string | NgbDateStruct): string {
    // Richiamo la funzione di utility
    return convertNgbDateStructToServerDate(date);
  }

  /**
   * #######
   * UTILITY
   * #######
   */

  /**
   * Funzione che converte una data stringa in moment e verifica se il suo formato risulta valido.
   * @param date string con la data da verificare.
   * @param format string con il formato della data da verificare.
   * @param allow29thFeb boolean che permette di definire se accettare come data il 29 febbrario. Per default è: true.
   * @returns boolean con il risultato del check.
   */
  checkDateFormat(
    date: string,
    format: string,
    allow29thFeb: boolean = true
  ): boolean {
    // Richiamo la funzione di utility
    return checkDateFormat(date, format, allow29thFeb);
  }

  /**
   * ################################################################
   * FUNZIONI DI UTILITY PER LA GESTIONE DEL CALENDARIO NGBDATEPICKER
   * ################################################################
   */

  /**
   * Funzione che modifica per referenza l'oggetto di configurazione del calendario in input.
   * Le date, se impostate, verranno assegnate: data min -> 100 anni nel passato; data max -> 100 anni nel futuro.
   * Entrambe a partire da oggi.
   * @param calendarioConfig IScrivaDatepickerConfig da configurare.
   * @param setMin boolean che definisce se inizializzare la data minima del calendario. Il default è true.
   * @param setMax boolean che definisce se inizializzare la data massima del calendario. Il default è true.
   */
  setupCalendarioMinMaxConfig(
    calendarioConfig: IScrivaDatepickerConfig,
    setMin = true,
    setMax = true
  ) {
    // Verifico che l'oggetto esista
    if (!calendarioConfig) return;

    // Definisco la data minima, partendo da oggi
    const startCalendar = moment().subtract(100, 'years');
    // Definizione della data massima, partendo da oggi
    const endCalendar = moment().add(100, 'years');

    // Se richiesto, imposto la data minima
    if (setMin) {
      // Configurazione data minima per il calendario
      calendarioConfig.minDate = {
        year: startCalendar.year(),
        month: startCalendar.month() + 1,
        day: startCalendar.date(),
      };
    }

    // Se richiesto, imposto la data massima
    if (setMax) {
      // Configurazione data minima per il calendario
      calendarioConfig.maxDate = {
        year: endCalendar.year(),
        month: endCalendar.month() + 1,
        day: endCalendar.date(),
      };
    }
  }

  /**
   * ############################
   * FUNZIONI DI CONVERSIONE DATI
   * ############################
   */

  /**
   * Funzione per la conversione di una stringa in formato Base64 ad un array di Uint8Array.
   * @param b64Data string che definisce un Base64 da convertire.
   * @param sliceSize number che definisce la porzione di byte per la conversione da Base64 ad array di byte.
   * @returns Array di Uint8Array risultato della conversione.
   */
  base64ToByteArray(b64Data: string, sliceSize: number = 512): Uint8Array[] {
    // Richiamo la funzione di utility
    return base64ToByteArray(b64Data, sliceSize);
  }

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
  isEmptyObject(o: any): boolean {
    // Richiamo la funzione di utility
    return isEmptyObject(o);
  }

  /**
   * Funzione che verifica se un oggetto è vuoto controllando anche i suoi parametri.
   * Se uno di essi è un oggetto valorizzato, allora controlla che anch'esso sia vuoto.
   * @param o any da verificare.
   * @returns boolean che definisce se l'oggetto è vuoto o undefined.
   */
  isEmptyObjectDeep(o: any): boolean {
    // Richiamo la funzione di utility
    return isEmptyObjectDeep(o);
  }

  /**
   * Funzione che tenta di estrarre da una stringa una data per il server.
   * La stringa DEVE contenere al suo interno la seguente struttura: YYYY-MM-DD.
   * @param date string contenente le informazioni per la data.
   * @return string con la data del server estratta.
   */
  extractServerDate(date: string): string {
    // Richiamo la funzione di utility
    return extractServerDate(date);
  }

  /**
   * #####################
   * GESTIONE LETTURA BLOB
   * #####################
   */

  /**
   * Funzione di comodo che partendo da un blob dati, genera uno specifico oggetto di alert.
   * La funzione eseguirà le funzioni agganciate agli eventi definiti tramite input.
   * @param configs IHandleBlobData che definisce le configurazioni per la generazione dell'alert.
   */
  readBlob(configs: IHandleBlobRead) {
    // Richiamo la funzione di utility
    return readBlob(configs);
  }

  /**
   * #####
   * VARIE
   * #####
   */

  /**
   * Funzione che verifica se all'interno di una lista di oggetti esiste già un oggetto, data la proprietà sulla quale verificare se i dati combaciano.
   * @param elemento any con l'oggetto per la verifica.
   * @param lista any[] con la lista da verificare.
   * @param proprieta string con la proprità da usare per il confronto dati.
   * @param strict boolean che definisce se il confronto tra le proprietà deve essere restrittivo. Per default è: true.
   * @returns boolean con il risultato del controllo. Se ritorna undefined, vuol dire che i parametri non sono stati definiti correttamente.
   */
  objectInList(
    elemento: any,
    lista: any[],
    proprieta: string,
    strict: boolean = true
  ): boolean {
    // Richiamo e ritorno la funzione
    return objectInList(elemento, lista, proprieta, strict);
  }

  /**
   * Funzione che verifica la quantità di errori all'interno di un FormGroup o un FormControl.
   * @param form FormGroup | AbstractControl con l'oggetto da verificare.
   * @param errorName string con il nome dell'errore da verificare.
   * @returns number con la quantità di errori generati.
   */
  countFormErrors(
    form: FormGroup | AbstractControl,
    errorName: string
  ): number {
    // Richiamo e ritorno la funzione
    return countFormErrors(form, errorName);
  }

  /**
   * Funzione che verifica la quantità di errori all'interno di un FormGroup o un FormControl.
   * @param form FormGroup | AbstractControl con l'oggetto da verificare.
   * @param errorName string con il nome del campo (credo) da verificare.
   * @returns boolean con un flag che indica se il form ha almeno un errore del tipo "errorName".
   */
  formHasErrors(form: FormGroup | AbstractControl, errorName: string): boolean {
    // Richiamo la funzione di count errori
    return formHasErrors(form, errorName);
    // #
  }

  
  /**
   * Funzione di arrotondamento per eccesso di un numero.
   * Se non definiti, i decimali sono 0.
   * @param n number con il numero d'arrotondare.
   * @param d number con i decimali da arrotondare.
   */
  arrotondaEccesso(n: number, d: number = 0): number {
    // Richiamo la funzione di utility
    return arrotondaEccesso(n, d);
  }

  /**
   * Funzione di arrotondamento per eccesso di un numero.
   * Se non definiti, i decimali sono 0.
   * @param n number | string con il numero d'arrotondare.
   * @param d number con i decimali da arrotondare.
   */
  arrotondaDifetto(n: number | string, d: number = 0): number {
    // Richiamo la funzione di utility
    return arrotondaDifetto(n, d);
  }

  
  /**
   * ########################
   * FORMIO GENERIC UTILITIES
   * ########################
   */

  // #region "FORMIO GENERIC UTILITIES"

  /**
   * Funzione di comodo per le logiche di verifica di FormIo.
   * La funzione prende in input un valore "numero stringa" e verifica che il suo valore sia maggiore di 0.
   * La funzione gestirà il ritorno in modalità "FormIo", ossia, se il numero è > 0 verrà ritornato un valore booleano, mentre se andrà in errore sarà ritornata una stringa per gestire due casi:
   * - Numero <= 0 ==> Stringa di errore: "numero0oMinore";
   * - Numero non convertito correttamente, per errata formattazione ===> "erroreDiConversione";
   * @param formioNumber string con il numero da formattare e verificare, con il risultato maggiore di 0.
   * @returns boolean che definisce se il numero in input è > 0. Se il numero è 0 o minore, verrà ritornato l'errore "numero0oMinore", se si è verificato un errore di conversione verrà ritornato l'errore "erroreDiConversione".
   */
  formioStringNumberGreaterThanZeroExtended(
    formioNumber: string
  ): boolean | string {
    // Richiamo la funzione di utility
    return formioStringNumberGreaterThanZeroExtended(formioNumber);
    // #
  }

  /**
   * Funzione di comodo per le logiche di verifica di FormIo.
   * La funzione si basa sulle logiche della funzione "formioStringNumberGreaterThanZeroExtended", ma ritornerà solo valori boolean.
   * @param formioNumber string con il numero da formattare e verificare, con il risultato maggiore di 0.
   * @returns boolean che definisce se il numero in input è > 0.
   */
  formioStringNumberGreaterThanZero(formioNumber: string): boolean {
    // Richiamo la funzione di check e verifico il tipo del risultato
    const check: boolean | string =
      formioStringNumberGreaterThanZeroExtended(formioNumber);
    // Ritorno un boolean sulla base del tipo del risultato, se è boolean allora la funzione di check è andata bene
    return typeof check === 'boolean';
    // #
  }

  // #endregion "FORMIO GENERIC UTILITIES"
}

