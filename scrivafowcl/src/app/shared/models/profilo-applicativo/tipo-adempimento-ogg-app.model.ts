/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { StatoIstanza } from '../istanza/stato-istanza.model';
import { OggettoApp } from './oggetto-app.model';
import { TipoAdempiProfilo } from './tipo-adempi-profilo.model';

export interface TipoAdempimentoOggApp {
  tipo_adempi_profilo: TipoAdempiProfilo;
  stato_istanza: StatoIstanza;
  flg_clona_istanza: boolean;
  id_istanza?: number;
  oggetto_app?: OggettoApp;
}
