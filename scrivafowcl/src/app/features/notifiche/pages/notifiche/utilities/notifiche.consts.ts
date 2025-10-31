/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ScrivaIconTextButton } from '../../../../../shared/classes/scriva-utilities/scriva-utilities.classes';
import { IStatoNotifica } from '../../../components/notifiche-search/utilities/notifiche-search.interfaces';
import { NotificaCardTypeView } from '../../../components/notifica-card/notifiche-card/notifica-card.enums';
import { CodStatiNotifiche } from '../../../enums/notifiche.enums';

/** Costanti di comodo per la gestione delle costanti di configurazione. */
const LABEL_FILTRI: string = 'Filtri';
const LABEL_ORDINA_DATA: string = 'Ordinamento per data';

/**
 * Interfaccia di supporto per l'oggetto di costanti del componente notifiche.component.ts
 */
export interface INotificheConsts {
  PAGE_TITLE: string;
  ALERT_ANCHOR: string;

  NOTIFICA_CARD_TYPE: NotificaCardTypeView;

  LABEL_MARK_ALL_AS_READ: string;

  BUTTON_FILTRI: ScrivaIconTextButton;
  BUTTON_ORDINA_DATA: ScrivaIconTextButton;
  BUTTON_OPZIONI: ScrivaIconTextButton;

  LIST_STATI_NOTIFICA: IStatoNotifica[];
}

/**
 * Costanti di utilities per il componente notifiche.components.ts.
 */
export const NOTIFICHE_CONSTS: INotificheConsts = {
  PAGE_TITLE: 'Notifiche',
  ALERT_ANCHOR: 'ALERT_NOTIFICHE',

  NOTIFICA_CARD_TYPE: NotificaCardTypeView.LIST,

  LABEL_MARK_ALL_AS_READ: 'Segna tutte come lette',

  BUTTON_FILTRI: new ScrivaIconTextButton({
    data: {
      label: LABEL_FILTRI,
      icon: {
        icon: 'fa fa-bars scriva-blue',
        alt: LABEL_FILTRI,
        customCss: {
          'font-size': '24px',
        },
      },
    },
    css: {
      customButton: {},
      customLabel: 'n-filtri-label',
    },
  }),
  BUTTON_ORDINA_DATA: new ScrivaIconTextButton({
    data: {
      label: LABEL_ORDINA_DATA,
      icon: {
        icon: 'fa fa-arrows-v fa-lg scriva-blue',
        alt: LABEL_ORDINA_DATA,
      },
    },
    css: {
      customButton: {},
      customLabel: 'n-filtri-label',
      labelLeft: true,
    },
  }),
  BUTTON_OPZIONI: new ScrivaIconTextButton({
    data: {
      label: undefined,
      icon: {
        icon: 'fa fa-cog scriva-blue',
        alt: LABEL_FILTRI,
        customCss: {
          'font-size': '24px',
        },
      },
    },
    css: {},
  }),

  LIST_STATI_NOTIFICA: [
    {
      id: 0,
      cod_stato_notifica: CodStatiNotifiche.lette,
      des_stato_notifica: 'Letto',
    },
    {
      id: 1,
      cod_stato_notifica: CodStatiNotifiche.nonLette,
      des_stato_notifica: 'Non letto',
    },
  ],
};
