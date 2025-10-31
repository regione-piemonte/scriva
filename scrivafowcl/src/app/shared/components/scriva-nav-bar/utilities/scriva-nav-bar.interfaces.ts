/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**
 * Interfaccia di supporto che definisce la struttura dell'oggetto di configurazione delle tab di scriva.
 */
export interface ISNBTab {
  /** String identificativo della tab. */
  id: string;
  /** String con il title (attributo HTML) della tab. */
  title?: string;
  /** String che definisce la label visualizzata per la tab. */
  label?: string;
  /** Boolean che definisce se la tab è da considerare come selezionata inizialmente. */
  selected?: boolean;
}

/**
 * Struttura dell'oggetto gestito per l'NgClass di evidenziazione della tab della nav bar.
 */
export interface ISNBTabSelectedCss {
  selected?: boolean;
}
