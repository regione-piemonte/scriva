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
 * Enums con i tipi di georeferenzazione possibili per il quadro oggetto/progetto.
 */
export enum TipiGeoriferimento {
  nonRichiesto_N = 'N',
  opzionale_O = 'O',
  obbligatorio_M = 'M',
  opzionaleConAvviso_A = 'A',
  nonEseguito_P = 'P',
  enum_provvisorio_val_G = 'G',
}

/**
 * Enums con i tipi 
 */
export enum CodiciVincoliAutorizza {
  viaConVinca = 'VNCS'
}