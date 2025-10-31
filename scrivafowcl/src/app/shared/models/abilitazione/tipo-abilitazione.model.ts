/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ProfiloApp } from '../profilo-applicativo/profilo-app.model';

export interface TipoAbilitazione {
  id_tipo_abilitazione: number;
  cod_tipo_abilitazione: string;
  des_tipo_abilitazione: string;
  profilo_app?: ProfiloApp;
}
