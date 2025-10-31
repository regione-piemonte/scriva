import { ScrivaServerError } from "src/app/core/classes/scriva.classes";
import { IMappaErroriFormECodici } from "../../form-input/utilities/form-input.interfaces";
import { ScrivaFormInputCss } from "../../form-input/utilities/form-input.classes";


/**
 * Interfaccia che definisce l'oggetto restituito dal form.
 */
export interface ITestInputFormData {
  anni: IScrivaAnnoSelect;
}

/**
 * Interfaccia per la gestione dei dati relativi alle select per la selezione anni
 */
export interface IScrivaAnnoSelect {
  anno: number;
}

/**
 * Interfaccia che definisce le configurazioni per gestire le informazioni sulla funzione: onFormErrors.
 */
export interface IRFCFormErrorsConfigs {
  formErrors?: IMappaErroriFormECodici[];
  serverError?: ScrivaServerError;
}

/**
 * Interfaccia di comodo che racchiude le configurazioni per la gestione delle input form dell'applicazione.
 */
export interface ScrivaComponentConfig<T> {
  css: ScrivaFormInputCss;
  data: T;
  source?: any | any[];
}