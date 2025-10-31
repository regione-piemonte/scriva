/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Provincia } from './provincia.model';

export interface Comune {
  cap_comune?: string;
  cod_belfiore_comune?: string;
  cod_istat_comune?: string;
  data_fine_validita?: Date;
  data_inizio_validita?: Date;
  denom_comune?: string;
  dt_id_comune?: number;
  dt_id_comune_next?: number;
  dt_id_comune_prev?:	number;
  id_comune?:	number;
  id_provincia?: number;
  provincia?: Provincia;
}
