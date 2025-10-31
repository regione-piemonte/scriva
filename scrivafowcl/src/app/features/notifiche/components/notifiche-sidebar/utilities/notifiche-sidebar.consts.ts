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
import { NotificaCardTypeView } from '../../../components/notifica-card/notifiche-card/notifica-card.enums';

/** Costanti di comodo per la gestione delle costanti di configurazione. */
const LABEL_FILTRI: string = 'Filtri';
const LABEL_ORDINA_DATA: string = 'Ordinamento per data';

/**
 * Interfaccia di supporto per l'oggetto di costanti del componente notifiche-sidebar.component.ts
 */
export interface INotificheSidebarConsts {
  PAGE_TITLE: string;
  ALERT_ANCHOR: string;

  NOTIFICA_CARD_TYPE: NotificaCardTypeView;

  LABEL_MARK_ALL_AS_READ: string;

  BTN_VAI_A_LISTA_NOTIFICHE: ScrivaButtonConfig;
}

/**
 * Costanti di utilities per il componente notifiche-sidebar.components.ts.
 */
export const NOTIFICHE_SIDEBAR_CONSTS: INotificheSidebarConsts = {
  PAGE_TITLE: 'Notifiche',
  ALERT_ANCHOR: 'ALERT_NOTIFICHE_SIDEBAR',

  NOTIFICA_CARD_TYPE: NotificaCardTypeView.SIDEBAR,

  LABEL_MARK_ALL_AS_READ: 'Segna tutte come lette',

  BTN_VAI_A_LISTA_NOTIFICHE: { label: 'VEDI TUTTE' },
};
