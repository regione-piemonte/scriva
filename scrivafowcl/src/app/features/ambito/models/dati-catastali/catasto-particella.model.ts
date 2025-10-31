/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Comune } from 'src/app/shared/models';

export interface CatastoParticella {
  comune: Comune;
  sezione: string;
  foglio: string;
  particella: string;
  id_geo_particella?: number;
  aggiornato_al?: string;
  allegato?: string;
  sviluppo?: string;
  // tipo_geometria?: string;
  // coordinate?: number[][];
}
