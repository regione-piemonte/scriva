/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ProfiloApp } from "./profilo-app.model";
import { TipoAdempimentoOggApp } from "./tipo-adempimento-ogg-app.model";

export interface OggettiApplicativi {
  profili_app?: ProfiloApp[];
  tipi_adempimento_ogg_app?: TipoAdempimentoOggApp[];
}
