/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Comune } from '../location/comune.model';
import { CompetenzaTerritorioResponsabile } from './competenza-territorio-responsabile.model';
import { EnteCreditore } from './ente-creditore.model';
import { TipoCompetenza } from './tipo-competenza.model';

export interface CompetenzaTerritorio {
  id_competenza_territorio: number;
  cod_competenza_territorio: string;
  des_competenza_territorio: string;
  des_competenza_territorio_estesa: string;
  indirizzo_competenza: string;
  num_civico_indirizzo: string;
  cap_competenza: string;
  data_inizio_validita: string;
  tipo_competenza: TipoCompetenza;
  ente_creditore: EnteCreditore;
  comune_competenza: Comune;
  ind_adesione_adempimento?: number;
  flg_principale?: boolean;
  id_componente_gestore_processo?: number;
  url_oneri_previsti?: string; // todo: remove this field from RiscossioneEnte model
  cod_ipa?: string;
  responsabili_competenza_territorio?: CompetenzaTerritorioResponsabile[]
}
