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
 * Interfaccia di supporto che definisce la struttura dell'oggetto dati per il profilo utente.
 */
export interface IDatiProfilo {
  codiceFiscale?: string;
  cognome?: string;
  nome?: string;
  email?: string;
  telefono?: string;
}
