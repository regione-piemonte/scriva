import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import { ScrivaErrorsMap } from '../../../../../shared/classes/scriva-input-errors/scriva-errors-maps.class';

/**
 * Form validator personalizzato.
 * Il validatore verificherà che il form control non contenga valori da considerare nulli.
 * @returns (formControl: FormControl) => ValidationErrors | null come funzione per la gestione degli errori
 */
export const nullAsStringValidator = (
  invalidStrings?: string[]
): ValidatorFn => {
  // Come struttura di un validatore personalizzato, viene ritornata una funzione che gestisce le logiche di controllo
  return (formControl: FormControl): ValidationErrors | null => {
    // Verifico che il formControl esista
    if (!formControl) {
      // Non esiste, ritorno null
      return null;
      // #
    }

    // Cerco di recuperare il valore del form control
    const value: any = formControl.value;
    // Definisco l'array per la verifica delle parole invalide, con la definizione di un default
    const defaultInvalidStrings: string[] = ['null', 'Seleziona', ''];
    const invalidChecks: string[] = invalidStrings ?? defaultInvalidStrings;

    // Estraggo la condifizione di check sul tipo stringa
    const isValueString: boolean = typeof value === 'string';
    // Verifico se il valore è una stringa
    if (isValueString) {
      // Cerco la parola all'interno della lista di quelle non valide
      const isInvalid: boolean = invalidChecks.some((ic: string) => {
        // Verifico il valore con la parola invalida
        return ic === value;
        // #
      });

      // Controllo la validità del valore
      if (isInvalid) {
        // Recupero le costanti di errori
        const EK = new ScrivaErrorsMap();
        // Ritorno un errore
        return EK.MAP_FORM_CONTROL_REQUIRED;
        // #
      }
      // #
    }

    // Nessun errore
    return null;
    // #
  };
};
