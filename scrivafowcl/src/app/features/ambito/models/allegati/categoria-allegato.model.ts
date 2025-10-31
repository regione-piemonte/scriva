/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export interface CategoriaAllegato {
  cod_categoria_allegato: string;
  des_categoria_allegato: string;
  flg_attivo: boolean;
  id_categoria_allegato: number;
  ordinamento_categoria_allegato: number;
  flg_obbligo?: boolean; // not in DTO
}
