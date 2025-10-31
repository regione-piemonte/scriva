/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Compilante } from '../compilante/compilante.model';
import { TipoAdempimento } from './tipo-adempimento.model';

export interface Preferenza {
  id_preferenza?: number;
  compilante: Compilante;
  tipo_adempimento: TipoAdempimento;
}
