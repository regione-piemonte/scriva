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
 * Enum che definisce descrittivamente l'identita di uno specifico tipo quadro collegato ad uno specifico id.
 */
export enum IdTipiQuadro {
  orientamento = 0,
  soggetto = 1,
  oggetto = 2,
  dichiarazione = 3,
  dettaglioOggetto = 4,
  allegati = 5,
  dettaglioProcedimento = 8,
  conclusioneProcedimento = 10,
  datiTecnici = 11,
  configurazioneTemplate = 50,
  istanza = 51,
  pagamento = 99,
  riepilogo = 100,
}

/**
 * Enum che definisce descrittivamente l'identita di uno specifico tipo quadro collegato ad uno specifico codice.
 */
export enum CodiciTipiQuadro {
  orientamento = 'QDR_ORIENTAMENTO',
  soggetto = 'QDR_SOGGETTO',
  oggetto = 'QDR_OGGETTO',
  dichiarazione = 'QDR_DICHIARAZIONE',
  dettaglioOggetto = 'QDR_DETT_OGGETTO',
  allegati = 'QDR_ALLEGATO',
  dettaglioProcedimento = 'QDR_DETT_PROCED',
  conclusioneProcedimento = 'QDR_CONCL_PROC',
  datiTecnici = 'QDR_DATI_TECNICI',
  configurazioneTemplate = 'QDR_CONFIG',
  istanza = 'QDR_ISTANZA',
  pagamento = 'QDR_PAGAMENTO',
  riepilogo = 'QDR_RIEPILOGO',
  altriUsi = 'QDR_DER_ALTRIUSI',
}
