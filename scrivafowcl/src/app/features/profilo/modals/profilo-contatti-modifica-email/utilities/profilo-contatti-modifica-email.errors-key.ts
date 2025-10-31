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
 * Interfaccia che definisce la mappatura delle chiavi d'errore.
 */
interface IPCMEErrorsKeys {
  RIPETI_EMAIL: string;
}

/**
 * Classe di mapping delle chiavi per gli erorri del form.
 */
export const PCMEErrorsKeys: IPCMEErrorsKeys = {
  /** Chiave per il controllo tra email e ripetiEmail che siano uguali. */
  RIPETI_EMAIL: 'ripetiEmail',
};
