/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { CompetenzaTerritorio } from './competenza-territorio.model';

export interface IstanzaCompetenza {
  gestUID?: string;
  id_istanza: number;
  data_inizio_validita?: Date;
  flg_autorita_principale: boolean;
  flg_autorita_assegnata_bo?: boolean;
  competenza_territorio: CompetenzaTerritorio;
  ind_assegnata_da_sistema?: 	number;
}
