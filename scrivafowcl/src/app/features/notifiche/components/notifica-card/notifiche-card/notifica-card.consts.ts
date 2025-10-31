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
import { NotificaCardTypeView } from './notifica-card.enums';

/** Costanti di comodo per la gestione delle costanti di configurazione. */

/**
 * Interfaccia di supporto per l'oggetto di costanti del componente notifiche-sidebar.component.ts
 */
export interface INotificheCardConsts {

  BTN_VAI_AL_PROCEDIMENTO: ScrivaButtonConfig;
}

/**
 * Costanti di utilities per il componente notifiche-sidebar.components.ts.
 */
export const NOTIFICHE_CARD_CONSTS: INotificheCardConsts = {

  BTN_VAI_AL_PROCEDIMENTO: { label: 'VAI AL PROCEDIMENTO' },
};
