/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { TipoAdempimento } from '../adempimento/tipo-adempimento.model';
import { ProfiloApp } from './profilo-app.model';

export interface TipoAdempiProfilo {
  profilo_app?: ProfiloApp;
  id_profilo_app?: number;
  id_tipo_adempi_profilo?: number;
  id_tipo_adempimento?: number;
  flg_sola_lettura: boolean;
}
