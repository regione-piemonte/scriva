/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Comune, Nazione } from 'src/app/shared/models';

export interface RecapitoAlternativo {
  id_recapito_alternativo?: number;
  id_soggetto_istanza?: number;
  gestUID?: string;
  cod_recapito_alternativo?: string;

  comune_residenza?: Comune;
  comune_sede_legale?: Comune;
  nazione_residenza?: Nazione;
  nazione_sede_legale?: Nazione;
  citta_estera_residenza?: string;
  citta_estera_sede_legale?: string;

  indirizzo_soggetto?: string;
  num_civico_indirizzo?: string;
  cap_residenza?: string;
  cap_sede_legale?: string;
  des_localita?: string;

  num_telefono?: string;
  num_cellulare?: string;
  des_email?: string;
  des_pec?: string;

  presso?: string;
}
