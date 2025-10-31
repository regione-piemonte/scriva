/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export interface EnteCreditore {
  id_ente_creditore: number;
  cf_ente_creditore: string;
  denominazione_ente_creditore: string;
  indirizzo_tesoreria: string;
  iban_accredito: string;
  bic_accredito: string;
  flg_aderisce_piemontepay: boolean;
  cod_catalogo_piepay?: string;
  flg_attivo?: boolean;
}
