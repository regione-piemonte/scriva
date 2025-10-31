/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export interface Referente {
  id_referente_istanza?: number;
  gestUID?: string;
  id_istanza: number;
  cognome_referente: string;
  nome_referente: string;
  num_tel_referente?: string;
  num_cellulare_referente?: string;
  des_email_referente: string;
  des_pec_referente?: string;
  des_mansione_referente?: string;
}
