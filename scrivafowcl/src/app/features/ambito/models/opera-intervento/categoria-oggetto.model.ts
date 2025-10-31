/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { TipoCompetenza } from 'src/app/shared/models';
// import { TipologiaOggetto } from './tipologia-oggetto.model';

export interface CategoriaOggetto {
  id_categoria_oggetto: number;
  cod_categoria_oggetto: string;
  des_categoria_oggetto: string;
  des_categoria_oggetto_estesa: string;
  ordinamento_categoria_oggetto: string;
  data_inizio_validita: Date;
  ind_visibile: string;
  // tipologia_oggetto: TipologiaOggetto;
  tipi_competenza: TipoCompetenza[];
  des_altra_categoria_oggetto: string;
}
