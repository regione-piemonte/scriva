/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Nazione } from './nazione.model';

export interface Regione {
  cod_regione?:	string;
  data_fine_validita?:	Date;
  data_inizio_validita?:	Date;
  denom_regione?:	string;
  id_nazione?: number;
  id_regione?: number;
  nazione?: Nazione;
}
