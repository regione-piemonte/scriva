/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { CategoriaOggetto } from './categoria-oggetto.model';

export interface OggettoIstanzaCategoria {
  gestUID?: string;
  id_oggetto_istanza_categoria?: number;
  id_oggetto_istanza: number;
  categoria_oggetto: CategoriaOggetto;
  data_inizio_validita?: Date;
  data_fine_validita?: Date;
  ordinamento_istanza_competenza?: number;
  flg_cat_nuovo_oggetto: boolean;
  flg_cat_modifica_oggetto: boolean;
  flg_cat_principale: boolean;
}
