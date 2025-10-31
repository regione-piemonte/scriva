/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { TipoAdempimento } from './tipo-adempimento.model';

export interface Adempimento {
  cod_adempimento: string;
  des_adempimento: string;
  des_estesa_adempimento?: string;
  id_adempimento: number;
  ordinamento_adempimento: number;
  tipo_adempimento: TipoAdempimento;
}
