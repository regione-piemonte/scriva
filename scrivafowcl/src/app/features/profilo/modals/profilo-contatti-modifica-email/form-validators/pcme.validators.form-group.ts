/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { FormGroup, ValidationErrors } from '@angular/forms';
import { generaObjErrore } from '../../../../../shared/services/scriva-utilities/scriva-utilities.validators';
import { PCMEErrorsKeys } from '../utilities/profilo-contatti-modifica-email.errors-key';

/**
 * Interfaccia di comodo che permette di definire all'interno di un oggetto una serie di informazioni da far gestire alla funzione di controllo sul form.
 */
export interface IPCMEFormConfigs {
  formSubmitted: boolean;
}

/**
 * Form validator personalizzato per un form group.
 * Il validatore verificherà che le input riferite ad email e ripeti email, contengano lo stesso valore.
 * @param emailFormControlName string nome del form control riferito al campo.
 * @param emailRipetiFormControlName string nome del form control riferito al campo.
 * @param formSubmitted boolean che identifica lo stato di submit del form.
 * @returns ValidationErrors contenente le informazioni sull'errore.
 */
export const stessaEmail = (
  emailFormControlName: string,
  emailRipetiFormControlName: string,
  formConfigs: IPCMEFormConfigs
) => {
  // Come struttura di un validatore personalizzato, viene ritornata una funzione che gestisce le logiche di controllo
  return (formGroup: FormGroup): ValidationErrors | null => {
    // Verifico che l'oggetto formGroup esista
    if (!formGroup) {
      return;
    }
    // Definisco la costante contenente le chiavi d'errore per i forms validators
    const PCME_EK = PCMEErrorsKeys;

    // Estraggo dal form group i form control dai parametri
    const emailFC = formGroup.get(emailFormControlName);
    const emailRipetiFC = formGroup.get(emailRipetiFormControlName);
    // Estraggo dai form control i valori per email e ripeti email
    const email = emailFC?.value;
    const emailRipeti = emailRipetiFC?.value;

    // Verifico che sia stato inserito un valore per email e ripeti email
    if (!email && !emailRipeti) {
      // Entrambi i valori non sono definiti, niente errore
      return null;
      // #
    }

    // Verifico se una delle due email è stata compilata e l'altra no
    const checkEmail = email != null && emailRipeti != ''; // Input valida
    const checkEmailR = emailRipeti != null && emailRipeti != ''; // Input valida
    // Casi che non generano errori
    const case1 = email === emailRipeti;
    const case2 = checkEmail && !checkEmailR && !formConfigs?.formSubmitted;
    const case3 = !checkEmail && checkEmailR && !formConfigs?.formSubmitted;

    // Verifico se email e ripetiEmail coincidono
    if (case1 || case2 || case3) {
      // Entrambe le email sono uguali, passa il controllo
      return null;
      // #
    } else {
      // Una delle due email non è valida o le input non sono uguali
      const key = PCME_EK.RIPETI_EMAIL;
      // Genero e ritorno l'errore al form group
      return generaObjErrore(key);
    }
  };
};
