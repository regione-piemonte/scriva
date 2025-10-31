/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Regione } from './regione.model';

export interface Provincia {
  cod_provincia?:	string;
  data_fine_validita?: Date;
  data_inizio_validita?: Date;
  denom_provincia?:	string;
  id_provincia?: number;
  id_regione?: number;
  regione?: Regione;
  sigla_provincia?: string;
}
