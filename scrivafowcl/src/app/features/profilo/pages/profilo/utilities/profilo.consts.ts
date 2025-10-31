/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { SNBTab } from '../../../../../shared/components/scriva-nav-bar/utilities/scriva-nav-bar.classes';

/**
 * Costanti con scope locale utili per la composizione delle costanti condivise.
 */
const TAB_ID_ANAGRAFICA = 'TAB_ID_ANAGRAFICA';
const TAB_ID_CONTATTI = 'TAB_ID_CONTATTI';
const TAB_ID_NOTIFICHE = 'TAB_ID_NOTIFICHE';

/**
 * Interfaccia di supporto per l'oggetto di costanti del componente profilo.component.ts
 */
export interface IProfiloConsts {
  PAGE_TITLE: string;
  ALERT_ANCHOR: string;

  TAB_ID_ANAGRAFICA: string;
  TAB_ID_CONTATTI: string;
  TAB_ID_NOTIFICHE: string;

  NAV_BAR_TABS: SNBTab[];

  FC_CODICE_FISCALE: string;
  FC_COGNOME: string;
  FC_NOME: string;
  FC_EMAIL: string;
  FC_TELEFONO: string;
  FC_NOTIFICHE_SMS: string;
  FC_NOTIFICHE_EMAIL: string;
}

/**
 * Costanti di utilities per il componente profilo.components.ts.
 */
export const PROFILO_CONSTS: IProfiloConsts = {
  PAGE_TITLE: 'Profilo',
  ALERT_ANCHOR: 'ALERT_PROFILO',

  TAB_ID_ANAGRAFICA: TAB_ID_ANAGRAFICA,
  TAB_ID_CONTATTI: TAB_ID_CONTATTI,
  TAB_ID_NOTIFICHE: TAB_ID_NOTIFICHE,

  NAV_BAR_TABS: [
    new SNBTab({
      id: TAB_ID_ANAGRAFICA,
      label: 'Anagrafica',
      selected: true,
    }),
    new SNBTab({
      id: TAB_ID_CONTATTI,
      label: 'Contatti',
    }),
    new SNBTab({
      id: TAB_ID_NOTIFICHE,
      label: 'Notifiche',
    }),
  ],

  /** Nome del campo del form grup. */
  FC_CODICE_FISCALE: 'codiceFiscale',
  FC_COGNOME: 'cognome',
  FC_NOME: 'nome',
  FC_EMAIL: 'email',
  FC_TELEFONO: 'telefono',
  FC_NOTIFICHE_SMS: "smsNotifiche",
  FC_NOTIFICHE_EMAIL: "emailNotifiche",
};
