/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ComponenteApp, OggettoApp } from '..';

export interface ProfiloApp {
  id_profilo_app: number;
  cod_profilo_app: string;
  des_profilo_app: string;
  componente_app: ComponenteApp;
  flg_profilo_iride: boolean;
  flg_competenza: boolean;
  oggetti_app?: OggettoApp[]; // presente solo in IstanzaSearch e nel body di getIstanzaById()
}
