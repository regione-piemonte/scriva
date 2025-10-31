/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { IScrivaIcon } from "../../../../../shared/components/scriva-icon/utilities/scriva-icon.interfaces";

/**
 * Classe di comodo che definisce la mappatura tra codice canale notifica e la configurazione per l'icona da visualizzare in pagina.
 * La lista di codici censiti sono:
 * - 15/02/2023 => EMAIL; PEC; SERVIZIO;
 */
export class CanalePreferenzeIcon {
  /** IScrivaIcon con la configurazione per l'icona. */
  EMAIL: IScrivaIcon = {
    icon: 'fa fa-envelope-o fa-lg scriva-blue',
    alt: 'E-mail',
  };
  /** IScrivaIcon con la configurazione per l'icona. */
  PEC: IScrivaIcon = {
    icon: 'fa fa-envelope-o fa-lg scriva-blue',
    alt: 'Pec',
  };
  /** IScrivaIcon con la configurazione per l'icona. */
  SMS: IScrivaIcon = {
    icon: 'fa fa-commenting scriva-blue',
    alt: 'Servizio',
  };
  /** IScrivaIcon con la configurazione per l'icona. */
  SERVIZIO: IScrivaIcon = {
    icon: 'fa fa-credit-card-alt scriva-blue',
    alt: 'Servizio',
  };

  /** IScrivaIcon con la configurazione per l'icona. */
  DEFAULT: IScrivaIcon = {
    icon: 'fa fa-info-circle fa-lg scriva-blue',
    alt: 'Servizio',
  };

  constructor() {}
}