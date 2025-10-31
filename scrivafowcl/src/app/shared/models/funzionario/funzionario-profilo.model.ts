/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { OggettoApp } from '../profilo-applicativo/oggetto-app.model';
import { ProfiloApp } from '../profilo-applicativo/profilo-app.model';
import { TipoAdempiProfilo } from '../profilo-applicativo/tipo-adempi-profilo.model';

export interface FunzionarioProfilo {
  id_funzionario: number;
  profilo_app: ProfiloApp;
  tipo_adempi_profilo?: TipoAdempiProfilo[];
  oggetto_app?: OggettoApp[];
  data_inizio_validita: Date;
  data_fine_validita?: Date;
}
