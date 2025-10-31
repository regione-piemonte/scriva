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
 * Enums che definisce i codici per la gestione di scrittura/lettura o sola lettura.
 */
export enum AbilitazioniFormio {
  scrittura = 'ENABLED',
  lettura = 'DISABLED',
}

/**
 * Enums che definisce le chiavi degli eventi custom definiti a livello di sviluppi e logiche.
 */
export enum FormioCustomEvents {
  visualizzaHelper = 'helpBtnClick',
  salvaDatiSezione = 'saveSectionData',
}

/**
 * Enum con l'indicazione per la tipologia di alert di FormIo.
 */
export enum FormIoAlertTypes {
  error = 'danger',
}
