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
 * Definisco un interface personalizzato per la gestione dei placeholders.
 */
export interface DynamicObjScrivaCodesPlaceholders {
  [key: string]: string[];
}

/**
 * Enum personalizzato per la gestione dei placeholder per i code generati dal server e definiti all'interno degli oggetti d'errore intercettati dalle subscribe per le http requests.
 */
export const ScrivaCodesPlaceholders: DynamicObjScrivaCodesPlaceholders = {
  /**
   * ###################################################
   * GESTIONE PLACEHOLDER PER CODICE APPLICAZIONE PER: E
   * ###################################################
   */
  E000: [],
  E063: ['[{PH_OPERA}]'],
  E064: ['[{PH_OPERA_SEZIONI}]'],
  E086: ['[{PH_OPERA}]'],
  /**
   * ###################################################
   * GESTIONE PLACEHOLDER PER CODICE APPLICAZIONE PER: A
   * ###################################################
   */
  A000: [],
  /**
   * ###################################################
   * GESTIONE PLACEHOLDER PER CODICE APPLICAZIONE PER: I
   * ###################################################
   */
  I000: [],
  I030: ['{PH_NUM_RESULT}'],
  /**
   * ###################################################
   * GESTIONE PLACEHOLDER PER CODICE APPLICAZIONE PER: F
   * ###################################################
   */
  F000: [],
  /**
   * ###################################################
   * GESTIONE PLACEHOLDER PER CODICE APPLICAZIONE PER: C
   * ###################################################
   */
  C000: [],
  /**
   * ###################################################
   * GESTIONE PLACEHOLDER PER CODICE APPLICAZIONE PER: P
   * ###################################################
   */
  P000: [],
};
