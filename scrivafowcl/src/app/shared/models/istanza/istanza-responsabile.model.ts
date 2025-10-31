/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { TipoResponsabile } from '../aut-competente/tipo-responsabile.model';

export interface IstanzaResponsabile {
  id_istanza_responsabile?: number;
  id_istanza?: number;
  tipo_responsabile: TipoResponsabile;
  label_responsabile: string;
  nominativo_responsabile: string;
  recapito_responsabile: string;
  flg_riservato: boolean;
}
