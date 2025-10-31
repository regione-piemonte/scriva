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

/**
 * Interfaccia di supporto per l'oggetto di costanti del componente notifiche-search.component.ts
 */
export interface INotificheSearchConsts {
  ALERT_ANCHOR: string;

  BTN_RESETTA_FILTRI: IScrivaButtonConfig;
  BTN_CONFERMA_FILTRI: IScrivaButtonConfig;

  FC_STATO_NOTIFICA: string;
  FC_PROCEDIMENTO: string;
  FC_DATA_DA: string;
  FC_DATA_A: string;
  FC_NUMERO_ISTANZA: string;

  LABEL_STATO_NOTIFICA: string;
  LABEL_PROCEDIMENTO: string;
  LABEL_DATA_DA: string;
  LABEL_DATA_A: string;
  LABEL_NUMERO_ISTANZA: string;

  DEFAULT_LABEL_STATO_NOTIFICA: string;
  DEFAULT_LABEL_PROCEDIMENTO: string;
  DEFAULT_LABEL_NUMERO_ISTANZA: string;

  PROPERTY_STATO_NOTIFICA: string;
  PROPERTY_PROCEDIMENTO: string;
}

/**
 * Costanti di utilities per il componente notifiche-search.components.ts.
 */
export const NOTIFICHE_SEARCH_CONSTS: INotificheSearchConsts = {
  ALERT_ANCHOR: 'ALERT_NOTIFICHE_SEARCH',

  BTN_RESETTA_FILTRI: { label: 'AZZERA FILTRI' },
  BTN_CONFERMA_FILTRI: { label: 'CONFERMA' },

  // Nomi dei form control del form group per i filtri di ricerca notifiche
  FC_STATO_NOTIFICA: 'statoNotifica',
  FC_PROCEDIMENTO: 'procedimento',
  FC_DATA_DA: 'dataDa',
  FC_DATA_A: 'dataA',
  FC_NUMERO_ISTANZA: 'numeroIstanza',

  LABEL_STATO_NOTIFICA: 'Stato',
  LABEL_PROCEDIMENTO: 'Procedimento',
  LABEL_DATA_DA: 'Dal',
  LABEL_DATA_A: 'Al',
  LABEL_NUMERO_ISTANZA: 'Numero istanza',

  DEFAULT_LABEL_STATO_NOTIFICA: 'Seleziona stato',
  DEFAULT_LABEL_PROCEDIMENTO: 'Seleziona procedimento',
  DEFAULT_LABEL_NUMERO_ISTANZA: 'Inserisci numero istanza',

  PROPERTY_STATO_NOTIFICA: 'des_stato_notifica',
  PROPERTY_PROCEDIMENTO: 'des_adempimento',
};
