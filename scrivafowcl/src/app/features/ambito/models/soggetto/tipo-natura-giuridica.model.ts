/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export interface TipoNaturaGiuridica {
  id_tipo_natura_giuridica: number;
  cod_tipo_natura_giuridica: string;
  des_tipo_natura_giuridica: string;
  sigla_tipo_natura_giuridica: string;
  ordinamento_tipo_natura_giu?: number;
  flg_pubblico?: boolean;
}
