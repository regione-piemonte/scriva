/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { FormGroup } from '@angular/forms';

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
