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

export interface TipoEvento {
  id_tipo_evento: number;
  stato_istanza_evento?: StatoIstanza;
  cod_tipo_evento: string;
  des_tipo_evento: string;
  ind_visibile?: string;
  id_componente_gestore_processo?: number;
}
