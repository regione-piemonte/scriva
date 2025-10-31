/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export interface Nazione {
  cod_belfiore_nazione?: string;
  cod_istat_nazione?: string;
  cod_iso2?: string;
  data_fine_validita?: Date;
  data_inizio_validita?: Date;
  denom_nazione?: string;
  dt_id_stato?: number;
  dt_id_stato_next?: number;
  dt_id_stato_prev?: number;
  id_nazione?: number;
  id_origine?: number;
}
