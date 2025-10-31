/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**
 * Costante che definisce le informazioni del vocabolario FormIo.
 * @translate https://github.com/formio/formio.js/blob/master/src/translations/en.js
 */
export const FormIo_IT = {
  unsavedRowsError: 'Si prega di salvare tutte le righe prima di procedere.',
  invalidRowsError:
    'Si prega di correggere le righe non valide prima di procedere.',
  invalidRowError: 'Riga non valida. Si prega di correggerla o eliminarla.',
  invalidOption: '{{field}} è un valore non valido.',
  invalidDay: '{{field}} non è un giorno valido.',
  alertMessageWithLabel: '{{label}}: {{message}}',
  alertMessage: '{{message}}',
  complete: 'Invio Completato',
  error: 'Si prega di correggere i seguenti errori prima di inviare.',
  errorListHotkey:
    "Premere Ctrl + Alt + X per tornare all'elenco degli errori.",
  errorsListNavigationMessage:
    "Fare clic per navigare al campo con l'errore seguente.",
  submitError:
    'Si prega di controllare il modulo e correggere tutti gli errori prima di inviare.',
  required: '{{field}} è richiesto',
  unique: '{{field}} deve essere univoco',
  array: '{{field}} deve essere un array',
  array_nonempty: '{{field}} deve essere un array non vuoto', // eslint-disable-line camelcase
  nonarray: '{{field}} non deve essere un array',
  select: '{{field}} contiene una selezione non valida',
  pattern: '{{field}} non corrisponde al modello {{pattern}}',
  minLength: '{{field}} deve avere almeno {{length}} caratteri.',
  maxLength: '{{field}} non può avere più di {{length}} caratteri.',
  minWords: '{{field}} deve avere almeno {{length}} parole.',
  maxWords: '{{field}} non può avere più di {{length}} parole.',
  min: '{{field}} non può essere inferiore a {{min}}.',
  max: '{{field}} non può essere superiore a {{max}}.',
  // maxDate:
  //   '{{field}} non dovrebbe contenere una data successiva a {{- maxDate}}',
  // minDate:
  //   '{{field}} non dovrebbe contenere una data precedente a {{- minDate}}',
  maxYear: '{{field}} non dovrebbe contenere un anno superiore a {{maxYear}}',
  minYear: '{{field}} non dovrebbe contenere un anno inferiore a {{minYear}}',
  minSelectedCount: 'È necessario selezionare almeno {{minCount}} elementi',
  maxSelectedCount: 'È possibile selezionare fino a {{maxCount}} elementi',
  invalid_email: '{{field}} deve essere un indirizzo email valido.', // eslint-disable-line camelcase
  invalid_url: '{{field}} deve essere un URL valido.', // eslint-disable-line camelcase
  invalid_regex: '{{field}} non corrisponde al modello {{regex}}.', // eslint-disable-line camelcase
  invalid_date: '{{field}} non è una data valida.', // eslint-disable-line camelcase
  invalid_day: '{{field}} non è un giorno valido.', // eslint-disable-line camelcase
  invalidValueProperty: 'Proprietà del valore non valida',
  mask: '{{field}} non corrisponde alla maschera.',
  valueIsNotAvailable: '{{field}} è un valore non valido.',
  stripe: '{{stripe}}',
  month: 'Mese',
  day: 'Giorno',
  year: 'Anno',
  january: 'Gennaio',
  february: 'Febbraio',
  march: 'Marzo',
  april: 'Aprile',
  may: 'Maggio',
  june: 'Giugno',
  july: 'Luglio',
  august: 'Agosto',
  september: 'Settembre',
  october: 'Ottobre',
  november: 'Novembre',
  december: 'Dicembre',
  next: 'Avanti',
  previous: 'Precedente',
  cancel: 'Annulla',
  submit: 'Invia Modulo',
  confirmCancel: 'Sei sicuro di voler annullare?',
  saveDraftInstanceError:
    'Impossibile salvare la bozza perché non esiste alcuna istanza di Formio.',
  saveDraftAuthError:
    'Impossibile salvare la bozza a meno che un utente non sia autenticato.',
  restoreDraftInstanceError:
    'Impossibile ripristinare la bozza perché non esiste alcuna istanza di Formio.',
  saveDraftError: 'Impossibile salvare la bozza.',
  restoreDraftError: 'Impossibile ripristinare la bozza.',
  time: 'Ora non valida',
  cancelButtonAriaLabel:
    'Pulsante Annulla. Fare clic per ripristinare il modulo',
  previousButtonAriaLabel:
    'Pulsante Precedente. Fare clic per tornare alla scheda precedente',
  nextButtonAriaLabel:
    'Pulsante Avanti. Fare clic per passare alla scheda successiva',
  submitButtonAriaLabel:
    'Pulsante Invia Modulo. Fare clic per inviare il modulo',
  reCaptchaTokenValidationError: 'ReCAPTCHA: Errore di convalida del token',
  reCaptchaTokenNotSpecifiedError:
    "ReCAPTCHA: Il token non è specificato nell'invio",
  apiKey: 'Chiave API non univoca: {{key}}',
  typeRemaining: '{{remaining}} {{type}} rimanenti.',
  typeCount: '{{count}} {{type}}',
  requiredDayField: '{{field}} è richiesto',
  requiredDayEmpty: '{{field}} è richiesto',
  requiredMonthField: '{{field}} è richiesto',
  requiredYearField: '{{field}} è richiesto',
  Submit: 'Invia',
};
