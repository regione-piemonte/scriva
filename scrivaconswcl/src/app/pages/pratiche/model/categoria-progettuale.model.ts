/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
export interface CategoriaProgettuale {
  categoria_oggetto: CategoriaOggetto;
  data_inizio_validita: string;
  flg_cat_modifica_oggetto: boolean;
  flg_cat_nuovo_oggetto: boolean;
  flg_cat_principale: boolean;
  gestUID: string;
  id_oggetto_istanza: number;
  ordinamento_istanza_competenza: number;
}

interface CategoriaOggetto {
  cod_categoria_oggetto: string;
  data_inizio_validita: string;
  des_categoria_oggetto: string;
  des_categoria_oggetto_estesa: string;
  id_categoria_oggetto: number;
  ind_visibile: string;
  ordinamento_categoria_oggetto: number;
}
