/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export interface Quadro {
  id_quadro: number;
  id_template_quadro?: number;
  cod_quadro: string;
  des_quadro: string;
  tipo_quadro?: TipoQuadro;
  /** formIo = 'F'; custom = 'R'; */
  flg_tipo_gestione: string;
  json_configura_quadro: any;
  json_configura_riepilogo?: any;
  ordinamento_template_quadro: number;
  flg_quadro_obbigatorio?: boolean;
  json_data_quadro?: any;
  json_vestizione_quadro?: string;
  // added manually, not in DTO:
  completed?: boolean;
  state?: { clickable; id_quadro };
}

interface TipoQuadro {
  id_tipo_quadro?: number;
  cod_tipo_quadro?: string;
  des_tipo_quadro?: string;
}
