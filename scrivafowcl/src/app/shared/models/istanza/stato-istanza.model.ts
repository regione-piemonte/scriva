/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export interface StatoIstanza {
  id_stato_istanza: number;
  codice_stato_istanza?: string;
  descrizione_stato_istanza?: string;
  des_estesa_stato_istanza?: string;
  label_stato?: string;
  flg_storico_istanza?: boolean;
  flg_aggiorna_oggetto?: boolean;
  ind_visibile?: string;
}
