/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ScrivaButtonConfig } from '../../../../../shared/classes/scriva-utilities/scriva-utilities.classes';

/**
 * Interfaccia di supporto per l'oggetto di costanti del componente profilo-contatti-modifica-email.component.ts
 */
export interface IPCModificaEmailConsts {
  ALERT_ANCHOR: string;

  MODAL_TITLE: string;

  BTN_MODAL_ANNULLA: ScrivaButtonConfig;
  BTN_MODAL_CONFERMA: ScrivaButtonConfig;
  BTN_MODAL_CONFERMA_EMAIL: ScrivaButtonConfig;

  FC_AUTORIZZO: string;
  FC_EMAIL: string;
  FC_EMAIL_RIPETI: string;

  LABEL_AUTORIZZO: string;
  LABEL_EMAIL: string;
  LABEL_EMAIL_RIPETI: string;
  LABEL_CODICE_OTP: string;

  PLACEHOLDER_EMAIL: string;
  PLACEHOLDER_EMAIL_RIPETI: string;
  PLACEHOLDER_CODICE_OTP: string;
}

/**
 * Costanti di utilities per il componente profilo-contatti-modifica-email.components.ts.
 */
export const PROFILO_CONTATTI_MODIFICA_EMAIL_CONSTS: IPCModificaEmailConsts = {
  ALERT_ANCHOR: 'ALERT_PROFILO_CONTATTI_MODIFICA_EMAIL',

  MODAL_TITLE: 'Modifica E-mail',

  BTN_MODAL_ANNULLA: { label: 'ANNULLA' },
  BTN_MODAL_CONFERMA: { label: 'CONFERMA' },
  BTN_MODAL_CONFERMA_EMAIL: { label: 'CONFERMA E-MAIL' },

  FC_AUTORIZZO: 'autorizzo',
  FC_EMAIL: 'email',
  FC_EMAIL_RIPETI: 'emailRipeti',

  LABEL_AUTORIZZO:
    'Dichiaro di aver preso visione dell\'Informativa sul trattamento dei dati personali, aggiornata all’art. 13 del GDPR 2016/679, consultabile sulla home page di accesso al servizio',
  LABEL_EMAIL: 'E-mail (NO PEC)',
  LABEL_EMAIL_RIPETI: 'Ripeti E-mail',
  LABEL_CODICE_OTP: 'Codice di verifica',

  PLACEHOLDER_EMAIL: 'Inserisci E-mail',
  PLACEHOLDER_EMAIL_RIPETI: 'Inserisci E-mail',
  PLACEHOLDER_CODICE_OTP: 'Inserisci codice',
};
