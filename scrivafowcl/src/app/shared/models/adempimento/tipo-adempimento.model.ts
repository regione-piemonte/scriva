/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Ambito } from '..';

export interface TipoAdempimento {
  ambito: Ambito;
  cod_tipo_adempimento: string;
  des_tipo_adempimento: string;
  des_estesa_tipo_adempimento?: string;
  id_tipo_adempimento: number;
  ordinamento_tipo_adempimento?: number;
  preferito?: number;
}
