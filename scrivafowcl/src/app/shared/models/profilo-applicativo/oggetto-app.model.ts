/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { TipoEvento } from '../evento/tipo-evento.model';
import { TipoOggettoApp } from './tipo-oggetto-app.model';

export interface OggettoApp {
  id_oggetto_app: number;
  cod_oggetto_app?: string;
  des_oggetto_app: string;
  tipo_oggetto_app: TipoOggettoApp;
  tipo_evento?: TipoEvento
}
