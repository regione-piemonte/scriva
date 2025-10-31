/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export interface IFunzionario {
  id_funzionario: number;
  gestUID?: string;
  cf_funzionario: string;
  nome_funzionario: string;
  cognome_funzionario: string;
  num_telefono_funzionario: string;
  num_cellulare?: string;
  des_email_funzionario: string;
  data_inizio_validita: Date;
  data_fine_validita?: Date;
}
