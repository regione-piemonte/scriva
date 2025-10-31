/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export interface DataQuadro {
  id_istanza: number;
  id_template_quadro?: number;
  cod_tipo_quadro?: string;
  json_data_quadro: any;
}
