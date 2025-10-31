import { ValidatorFn, AsyncValidatorFn } from '@angular/forms';
import { ScrivaFormConfigs } from './scriva-form-builer.types';

/**
 * Interfaccia che definisce le informazioni minime per la configurazione automatica di un form group.
 */
export interface IScrivaFormConfigs {
  /** ScrivaFormConfigs che definisce la configurazione per i campi del form group. */
  formConfigs: ScrivaFormConfigs;
  /** ValidatorFn | ValidatorFn[] che definisce la funzioni di validazione del form group. */
  formValidators: ValidatorFn | ValidatorFn[];
  /** AsyncValidatorFn | AsyncValidatorFn[] che definisce la funzioni asincrone di validazione del form group. */
  formAsyncValidators?: AsyncValidatorFn | AsyncValidatorFn[];
}
