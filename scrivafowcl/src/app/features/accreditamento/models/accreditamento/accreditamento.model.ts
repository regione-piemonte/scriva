/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export interface Accreditamento {
  gestUID?: string;
  id_compilante?: number;
  id_richiesta_accredito?: null;

  cf_accredito: string;
  cognome_accredito: string;
  nome_accredito: string;
  des_email_accredito: string;
  flg_autorizza_dati_personali: boolean;
}
