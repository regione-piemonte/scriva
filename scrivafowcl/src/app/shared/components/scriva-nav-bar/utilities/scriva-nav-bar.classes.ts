/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ISNBTab } from './scriva-nav-bar.interfaces';

/**
 * Classe di supporto che definisce la struttura dell'oggetto di configurazione delle tab di scriva.
 */
export class SNBTab implements ISNBTab {
  /** String identificativo della tab. */
  id: string;
  /** String che definisce la label visualizzata per la tab. */
  label: string;
  /** String con il title (attributo HTML) della tab. */
  title?: string;
  /** Boolean che definisce se la tab è da considerare come selezionata inizialmente. */
  selected?: boolean;

  constructor(c: ISNBTab) {
    this.id = c.id;
    this.label = c.label ?? '';
    this.title = c.title ?? '';
    this.selected = c.selected ?? false;
  }
}
