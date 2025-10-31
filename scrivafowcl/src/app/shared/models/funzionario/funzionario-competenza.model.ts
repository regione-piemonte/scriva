/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { CompetenzaTerritorio } from '../aut-competente/competenza-territorio.model';

export interface FunzionarioCompetenza {
  id_funzionario: number;
  competenza_territorio: CompetenzaTerritorio;
  data_inizio_validita: Date;
  data_fine_validita?: Date;
}
