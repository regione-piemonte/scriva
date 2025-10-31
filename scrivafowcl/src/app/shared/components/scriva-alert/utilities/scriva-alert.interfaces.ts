/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import {
  ScrivaButtonConfig,
  ScrivaButtonCss,
} from '../../../classes/scriva-utilities/scriva-utilities.classes';
import { TScrivaAlertType } from './scriva-alert.types';

/**
 * Interfaccia che definisce la struttura completa di configurazione per i comportamenti grafici e dati per il componente risca-button.
 */
export interface IScrivaButtonAllConfig {
  cssConfig: ScrivaButtonCss;
  dataConfig: ScrivaButtonConfig;
}

/**
 * Interfaccia che rappresenta la configurazione dell'alert.
 */
export interface IScrivaAlertConfigs {
  /** string che definisce la tipologia di messaggio da gestire. */
  type?: TScrivaAlertType;
  /** string che definisce il titolo per i messaggi da visualizzare. */
  title?: string;
  /** string o array string che definisce i messaggi da visualizzare. */
  messages?: string | string[];
  /** boolean che definisce se il pannello di alert è persistente. */
  persistentMessage?: boolean;
  /** number che definisce dopo quanto nascondere il panel (in millisecondi). */
  timeoutMessage?: number;
  /** boolean che definisce se usare il layout compatto. */
  compactLayout?: boolean;
  /** string compatibile con le classi css, o oggetto compatibile con la direttiva NgStyle che definisce lo stile del container. */
  containerCss?: string | any;
  /** IScrivaButtonAllConfig che definisce le configurazioni per il pulsante di "conferma" dell'alert. */
  buttonConfirm?: IScrivaButtonAllConfig;
  /** IScrivaButtonAllConfig che definisce le configurazioni per il pulsante di "annulla" dell'alert. */
  buttonCancel?: IScrivaButtonAllConfig;
  /** boolean che definisce se mostrare o meno il pulsante X in alto a destra. */
  allowAlertClose?: boolean;
  /** any che definisce le informazioni per il pulsante di "conferma", nel momento della sua pressione. */
  payloadConfirm?: any;
  /** any che definisce le informazioni per il pulsante di "annulla", nel momento della sua pressione. */
  payloadCancel?: any;
}
