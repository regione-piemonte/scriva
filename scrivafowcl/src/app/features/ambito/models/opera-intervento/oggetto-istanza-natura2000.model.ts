/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { CompetenzaTerritorio } from 'src/app/shared/models';
import { OggettoIstanza } from './oggetto-istanza.model';

export interface OggettoIstanzaNatura2000 {
  id_oggetto_natura_2000?: number;
  oggetto_istanza?: OggettoIstanza;
  competenza_territorio: CompetenzaTerritorio;
  tipo_natura_2000: TipoNatura2000;
  cod_amministrativo: string;
  cod_gestore_fonte: string;
  des_sito_natura_2000: string;
  num_distanza?: number;
  flg_ricade?: boolean;
  to_be_validated?: boolean; // not in DTO
  flg_fuori_geometrie?: boolean; // not in DTO
  des_elemento_discontinuita?: string;
  flg_elemento_discontinuita?: string; // not in DB
}

interface TipoNatura2000 {
  id_tipo_natura_2000: number;
  cod_tipo_natura_2000: string;
  des_tipo_natura_2000: string;
}
