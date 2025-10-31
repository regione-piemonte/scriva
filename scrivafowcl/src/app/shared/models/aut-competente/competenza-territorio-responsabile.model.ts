/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { TipoResponsabile } from "./tipo-responsabile.model";

export interface CompetenzaTerritorioResponsabile {
  id_competenza_responsabile: number;
  id_competenza_territorio: number;
  tipo_responsabile: TipoResponsabile;
  label_responsabile: string;
  nominativo_responsabile: string;
  recapito_responsabile: string;
  flg_riservato: boolean;
}
