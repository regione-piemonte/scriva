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
 * Interfaccia di supporto per l'oggetto di costanti del componente profilo-notifiche.component.ts
 */
export interface IProfiloNotificheConsts {
  ALERT_ANCHOR: string;

  BTN_CONFERMA_PREFERENZE_NOTIFICHE: ScrivaButtonConfig;
}

/**
 * Costanti di utilities per il componente profilo-notifiche.components.ts.
 */
export const PROFILO_NOTIFICHE_CONSTS: IProfiloNotificheConsts = {
  ALERT_ANCHOR: 'ALERT_PROFILO_NOTIFICHE',

  BTN_CONFERMA_PREFERENZE_NOTIFICHE: { label: 'CONFERMA' },
};
