import { ScrivaCodesMesseges } from "src/app/core/enums/scriva-codes-messages.enums";
import { ScrivaErrorKeys } from "./errors-keys";
import {  IMappaErroriFormECodici } from "../../components/form-inputs/form-input/utilities/form-input.interfaces";

/**
 * Classe di mapping per gli errori delle form.
 */
export class RiscaErrorsMap {
    /** Definisco localmente una variabile contenente RiscaNotifyCodes. */
    private codes = ScrivaCodesMesseges;
    // Definisco la costante contenente le chiavi d'errore per i forms validators
    private ERR_KEY = ScrivaErrorKeys;
  
    /** Definisco le costanti per gli errori di Angular */
    private ANGULAR_REQUIRED = 'required';
    private ANGULAR_PATTERN = 'pattern';
    private ANGULAR_MIN = 'min';
    private ANGULAR_MAX = 'max';
    private ANGULAR_EMAIL = 'email';
    private ANGULAR_MAXLENGTH = 'maxlength';
  
    /** Mappatura errori: campo obbligatorio sul form. */
    MAP_FORM_GROUP_REQUIRED: IMappaErroriFormECodici[] = [
      {
        erroreForm: this.ANGULAR_REQUIRED,
        erroreCodice: this.codes.E001,
      },
    ];
    /** Mappatura errori: campo obbligatorio. */
    MAP_FORM_CONTROL_REQUIRED: IMappaErroriFormECodici[] = [
      {
        erroreForm: this.ANGULAR_REQUIRED,
        erroreCodice: this.codes.F008,
      },
    ];
  
    /** Mappatura errori: valore minimo. */
    MAP_MIN: IMappaErroriFormECodici[] = [
      {
        erroreForm: this.ANGULAR_MIN,
        erroreCodice: this.codes.E003,
      },
    ];
    /** Mappatura errori: valore massimo. */
    MAP_MAX: IMappaErroriFormECodici[] = [
      {
        erroreForm: this.ANGULAR_MAX,
        erroreCodice: this.codes.E003,
      },
    ];
    /** Mappatura errori: solo caratteri numerici. */
    MAP_PATTERN_NUMBER: IMappaErroriFormECodici[] = [
      {
        erroreForm: this.ANGULAR_PATTERN,
        erroreCodice: this.codes.F001,
      },
    ];
    /** Mappatura errori: validazione email. */
    MAP_PATTERN_EMAIL: IMappaErroriFormECodici[] = [
      {
        erroreForm: this.ANGULAR_EMAIL,
        erroreCodice: this.codes.F007,
      },
    ];
    /** Mappatura errori: validazione pec. */
    MAP_PATTERN_PEC: IMappaErroriFormECodici[] = [
      {
        erroreForm: this.ANGULAR_EMAIL,
        erroreCodice: this.codes.F009,
      },
    ];
    /** Mappatura errori: lunghezza del campo errata. */
    MAP_MAX_LENGTH: IMappaErroriFormECodici[] = [
      {
        erroreForm: this.ANGULAR_MAXLENGTH,
        erroreCodice: this.codes.E003,
      },
    ];
    /** Mappatura errori: errore generico. */
    MAP_GENERIC_ERROR: IMappaErroriFormECodici[] = [
      {
        erroreForm: this.ERR_KEY.ERRORE_FORMATO_GENERICO,
        erroreCodice: this.codes.E003,
      },
    ];
    /** Mappatura errori: campi obbligatori. */
    MAP_FORM_GROUP_ONE_FIELD_REQUIRED: IMappaErroriFormECodici[] = [
      {
        erroreForm: this.ERR_KEY.CAMPI_OBBLIGATORI,
        erroreCodice: this.codes.E001,
      },
    ];
  
  
 
    MAP_DATA_INIZIO_REQUIRED: IMappaErroriFormECodici[] = [
      {
        erroreForm: this.ERR_KEY.DATA_INIZIO_REQUIRED,
        erroreCodice: this.codes.E001,
      },
    ];
 
  
    constructor() {
      // Sonarqube segna come critico una funzione vuota, anche se è costruttore o hook di Angular. Il commento serve per bypassare il controllo.
    }
  }