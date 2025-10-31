/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ScrivaInfoLevels } from '../../../enums/scriva-utilities/scriva-utilities.enums';
import { TScrivaAlertType } from './scriva-alert.types';
import { IScrivaButtonAllConfig, IScrivaAlertConfigs } from './scriva-alert.interfaces';

/**
 * Classe che rappresenta la configurazione dell'alert.
 */
export class ScrivaAlertConfigs {
  /** string che definisce la tipologia di messaggio da gestire. */
  type?: TScrivaAlertType = ScrivaInfoLevels.none;
  /** string che definisce il titolo per i messaggi da visualizzare. */
  title?: string = '';
  /** string o array string che definisce i messaggi da visualizzare. */
  messages?: string | string[] = [];
  /** boolean che definisce se il pannello di alert è persistente. Per default è true. */
  persistentMessage?: boolean = true;
  /** number che definisce dopo quanto nascondere il panel (in millisecondi). Per default il tempo è 5000 millisecondi. */
  timeoutMessage?: number = 7000;
  /** boolean che definisce se usare il layout compatto. Il default è false. */
  compactLayout?: boolean = false;
  /** string compatibile con le classi css, o oggetto compatibile con la direttiva NgStyle che definisce lo stile del container. */
  containerCss?: string | any;
  /** IScrivaButtonAllConfig che definisce le configurazioni per il pulsante di "conferma" dell'alert. */
  buttonConfirm?: IScrivaButtonAllConfig;
  /** IScrivaButtonAllConfig che definisce le configurazioni per il pulsante di "annulla" dell'alert. */
  buttonCancel?: IScrivaButtonAllConfig;
  /** boolean che definisce se mostrare o meno il pulsante X in alto a destra. */
  allowAlertClose?: boolean = false;
  /** any che definisce le informazioni per il pulsante di "conferma", nel momento della sua pressione. */
  payloadConfirm?: any;
  /** any che definisce le informazioni per il pulsante di "annulla", nel momento della sua pressione. */
  payloadCancel?: any;

  constructor(c?: IScrivaAlertConfigs) {
    // Verifico l'input
    if (!c) {
      return;
    }

    if (c.type) {
      this.type = c.type;
    }
    if (c.title) {
      this.title = c.title;
    }
    if (c.messages) {
      this.messages = c.messages;
    }
    if (c.persistentMessage !== undefined) {
      this.persistentMessage = c.persistentMessage;
    }
    if (c.timeoutMessage !== undefined) {
      this.timeoutMessage = c.timeoutMessage;
    }
    if (c.compactLayout !== undefined) {
      this.compactLayout = c.compactLayout;
    }
    if (c.containerCss) {
      this.containerCss = c.containerCss;
    }
    if (c.buttonConfirm) {
      this.buttonConfirm = c.buttonConfirm;
    }
    if (c.buttonCancel) {
      this.buttonCancel = c.buttonCancel;
    }
    if (c.payloadConfirm) {
      this.payloadConfirm = c.payloadConfirm;
    }
    if (c.payloadCancel) {
      this.payloadCancel = c.payloadCancel;
    }
    if (c.allowAlertClose != undefined) {
      this.allowAlertClose = c.allowAlertClose;
    }
  }
}
