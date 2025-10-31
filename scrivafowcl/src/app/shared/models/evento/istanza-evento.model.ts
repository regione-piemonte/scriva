/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ComponenteApp } from '../profilo-applicativo/componente-app.model';
import { TipoEvento } from './tipo-evento.model';

export interface IstanzaEvento {
  gestUID?: string;
  id_istanza_evento: number;
  id_istanza: number;
  data_evento: string;
  id_istanza_attore?: number;
  id_funzionario?: number;
  tipo_evento: TipoEvento;
  componente_app: ComponenteApp;
}
