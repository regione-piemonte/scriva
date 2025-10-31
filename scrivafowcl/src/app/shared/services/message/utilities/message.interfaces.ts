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
 * Interfaccia che definisce la struttura per la gestione dei placholder del servizio di messaggistica.
 */
export interface IMsgPlacholder {
  ph: string;
  swap: string;
}