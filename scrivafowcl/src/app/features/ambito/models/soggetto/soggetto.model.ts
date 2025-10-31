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
import { TipoNaturaGiuridica } from './tipo-natura-giuridica.model';
import { TipoSoggetto } from './tipo-soggetto.model';

export interface Soggetto {
  // persona giuridica
  tipo_natura_giuridica?: TipoNaturaGiuridica;
  partita_iva_soggetto?: string;
  den_soggetto?: string;
  den_provincia_cciaa?: string;
  den_anno_cciaa?: number;
  den_numero_cciaa?: string;
  nazione_sede_legale?: Nazione;
  comune_sede_legale?: Comune;
  citta_estera_sede_legale?: string;
  cap_sede_legale?: string;

  // persona fisica
  cognome?: string;
  nome?: string;
  data_nascita_soggetto?: string;
  comune_nascita?: Comune;
  comune_residenza?: Comune;
  nazione_nascita?: Nazione;
  nazione_residenza?: Nazione;
  citta_estera_nascita?: string;
  citta_estera_residenza?: string;
  cap_residenza?: string;

  // npo e npo
  tipo_soggetto: TipoSoggetto;
  gestUID?: string;
  id_soggetto?: number;
  cf_soggetto: string;
  indirizzo_soggetto?: string;
  num_civico_indirizzo?: string;
  des_localita?: string;
  num_telefono?: string;
  num_cellulare?: string;
  data_cessazione_soggetto?: string;
  des_email?: string;
  des_pec?: string;

  id_masterdata?: number;
  id_masterdata_origine?: number;
}
