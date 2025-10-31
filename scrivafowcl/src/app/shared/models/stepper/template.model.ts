/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Adempimento } from '..';
import { Quadro } from './quadro.model';

export interface Template {
  id_template: number;
  adempimento?: Adempimento;
  cod_template?: string;
  des_template?: string;
  data_inizio_validita?: Date;
  data_cessazione?: Date;
  json_configura_template?: any;
  quadri?: Quadro[];
}
