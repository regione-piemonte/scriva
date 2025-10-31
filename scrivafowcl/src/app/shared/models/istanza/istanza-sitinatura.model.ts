/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { CompetenzaTerritorio } from "../aut-competente/competenza-territorio.model";

export interface IstanzaSitoNatura {
  des_sito_natura_2000: string;
  competenza_territorio: CompetenzaTerritorio;
  tipo_natura_2000: TipoNatura2000;
  id_istanza_sitonatura?: number;
  id_istanza?: number;
  flg_riservato: boolean;
  gestUID?: string;
  id_nota_istanza?: number;
  id_funzionario?: number;
  data_nota?: string;
  des_nota: string;
  ind_visibile?: string;
}

interface TipoNatura2000 {
  id_tipo_natura_2000: number;
  cod_tipo_natura_2000: string;
  des_tipo_natura_2000: string;
}