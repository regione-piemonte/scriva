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
 * Enum che raccoglie i tipi di sezione per captazione.
 */
export enum DatiSezioneCaptazioneCondivisi {
  DATI_IDENTIFICATIVI = 'DATI_IDENTIFICATIVI',
  ESERCIZIO_DELLA_CAPTAZIONE = 'ESERCIZIO_DELLA_CAPTAZIONE',
}

/**
 * Enum che raccoglie i tipi di sezione per captazione.
 */
export enum DatiSezioneCaptazionePozzo {
  DATI_IDENTIFICATIVI = 'DATI_IDENTIFICATIVI',
  ESERCIZIO_DELLA_CAPTAZIONE = 'ESERCIZIO_DELLA_CAPTAZIONE',
  DATI_STRUTTURALI = 'DATI_STRUTTURALI',
  PROVE_EMUNGIMENTO = 'PROVE_EMUNGIMENTO',
  POMPE_IDRAULICHE = 'POMPE_IDRAULICHE', 
}

/**
 * Enum che raccoglie i tipi di sezioni per captazione.
 */
export enum DatiSezioneCaptazionePresaAcqueSuperficiali {
  DATI_IDENTIFICATIVI = 'DATI_IDENTIFICATIVI',
  ESERCIZIO_DELLA_CAPTAZIONE = 'ESERCIZIO_DELLA_CAPTAZIONE',
  RILASCIO_A_VALLE_DELLA_PRESA = 'RILASCIO_A_VALLE_DELLA_PRESA',
  DATI_INFRASTRUTTURALI = 'DATI_INFRASTRUTTURALI',
}
