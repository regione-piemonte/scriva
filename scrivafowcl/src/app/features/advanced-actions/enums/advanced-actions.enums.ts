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
 * Enum che definisce le azioni avanzate
 */
export enum AdvancedActions {
  ABILITAZIONI = 'abilitazioni',
  CONCLUDI_PROC = 'concludi-proc',
  RICH_INTEGR_ALLEGATI = 'integra-alleg-dichiarazioni', // nel routing si comporta in modo diverso dalle altre forse si deve implementare allo stesso modo
  PRENDI_IN_CARICO = 'prendi-in-carico',
  PRENDI_IN_CARICO_SUAP = 'prendi-in-carico-unico',
  REVOCA = 'revoca-delega',
  RICH_PERF_ALLEGATI = 'rich-perf-allegati',
  SOSPENDI = 'sospendi',
  GESTISCI_NOTE = 'gestisci-note',
  AVVIA_PRATICA = 'avvia-pratica',
  PUBBLICA = 'abilita-visualizzazione',
  RIFIUTA_ISTANZA = 'rifiuta-istanza',
  INS_DOC_ISTR = 'inserisci-doc-istruttoria'
}

/**
 * Enum che definisce le maschere azioni avanzate 
 */
export enum AdvancedActionsMaschere {
  ABILITAZIONI = '.MSCR009D',
  CONCLUDI_PROC = '.MSCR012D',
  RICH_INTEGR_ALLEGATI = '.MSCR026D', // nel routing si comporta in modo diverso dalle altre forse si deve implementare allo stesso modo
  PRENDI_IN_CARICO = '.MSCR011D',
  PRENDI_IN_CARICO_SUAP = '.MSCR030D',
  REVOCA = '.MSCR010D',
  RICH_PERF_ALLEGATI = '.MSCR023D',
  SOSPENDI = '.MSCR028D',
  GESTISCI_NOTE = '.MSCR021D',
  AVVIA_PRATICA = '.MSCR024D',
  PUBBLICA = '.MSCR025D',
  RIFIUTA_ISTANZA = '.MSCR027D',
  INS_DOC_ISTR = '.MSCR31D',
}

/**
 * Enum che definisce le chiavi azioni avanzate
 */
export enum AdvancedActionsChiavi {
  ABILITAZIONI = 'ABILITAZIONI',
  CONCLUDI_PROC = 'CONCLUDI_PROC',
  RICH_INTEGR_ALLEGATI = 'RICH_INTEGR_ALLEGATI',
  PRENDI_IN_CARICO = 'PRENDI_IN_CARICO',
  PRENDI_IN_CARICO_SUAP = 'PRENDI_IN_CARICO_SUAP',
  REVOCA = 'REVOCA',
  RICH_PERF_ALLEGATI = 'RICH_PERF_ALLEGATI',
  SOSPENDI = 'SOSPENDI',
  GESTISCI_NOTE = 'GESTISCI_NOTE',
  AVVIA_PRATICA = 'AVVIA_PRATICA',
  PUBBLICA = 'PUBBLICA',
  RIFIUTA_ISTANZA = 'RIFIUTA_ISTANZA',
  INS_DOC_ISTR = 'INS_DOC_ISTR',
}