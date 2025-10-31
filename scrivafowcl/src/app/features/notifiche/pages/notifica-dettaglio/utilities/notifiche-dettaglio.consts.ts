/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { IScrivaButtonConfig } from '../../../../../shared/interfaces/scriva-utilities.interfaces';
import { NotificaCardTypeView } from '../../../components/notifica-card/notifiche-card/notifica-card.enums';

/**
 * Interfaccia di supporto per l'oggetto di costanti del componente notifiche-search.component.ts
 */
export interface INotificheDettaglioConsts {
  ALERT_ANCHOR: string;

  BTN_TORNA_HOME: IScrivaButtonConfig;
  BTN_TORNA_NOTIFICHE: IScrivaButtonConfig;
  BTN_PAGE_TITLE: string;
  TITLE_CONTAINER: string;

  NOTIFICA_CARD_TYPE: NotificaCardTypeView;
}

/**
 * Costanti di utilities per il componente notifiche-dettaglio.components.ts.
 */
export const NOTIFICHE_DETTAGLIO_CONSTS: INotificheDettaglioConsts = {
  ALERT_ANCHOR: 'ALERT_NOTIFICHE_SEARCH',

  BTN_TORNA_HOME: { label: 'VAI A HOME PAGE' },
  BTN_TORNA_NOTIFICHE: { label: 'VAI A NOTIFICHE' },
  BTN_PAGE_TITLE: 'Dettaglio Notifica',
  TITLE_CONTAINER: 'nd-title-container',

  NOTIFICA_CARD_TYPE: NotificaCardTypeView.DETAIL,
};
