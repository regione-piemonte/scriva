/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Comune } from 'src/app/shared/models';

export interface UbicazioneOggettoIstanza {
  id_ubicazione_oggetto_istanza?: number;
  gestUID?: string;
  id_oggetto_istanza?: number;
  comune?: Comune;
  den_indirizzo?: string;
  num_civico?: string;
  des_localita?: string;
  ind_geo_provenienza?: string;
  dati_catastali?: Catasto[];
}

/**
 * Interfaccia che raccoglie i dati dell'ubicazione oggetto istanza e aggiunge le informazioni delle geometrie.
 */
export interface GeometriaUbicazione {
  geo_feature?: ParametriGeometriaUbicazione;
  json_geo_feature?: string;
}

/**
 * Interfaccia che definisce le informazioni per i parametri di una geometria ubicazione.
 */
export interface ParametriGeometriaUbicazione {
  id?: number;
  type?: string;
  geometry?: GeometryPGU;
  properties?: PropertiesPGU;
}

/**
 * Interfaccia che definisce le informazioni per il parametro "geometry" una geometria ubicazione.
 */
export interface GeometryPGU {
  type?: string;
  coordinates?: number[][];
}

/**
 * Interfaccia che definisce le informazioni per il parametro "properties" una geometria ubicazione.
 */
export interface PropertiesPGU {
  [key: string]: any;
  Attore?: string;
  'Id istanza'?: string;
  'Tipo opera'?: string;
  id_soggetto?: string;
  'Codice univoco'?: string;
  Identificativo?: string;
  map_den_oggetto?: string;
  map_des_oggetto?: string;
  'Aggiorna oggetto'?: string;
  map_id_tipologia?: string;
  'Descrizione geometria'?: string;
  'Componente applicativa'?: string;
  'Gestione oggetto istanza'?: string;
  'Identificativo oggetto padre'?: string;
}

export interface Catasto {
  // CatastoUbicazioneOggettoIstanzaDTO
  gestUID?: string;
  id_catasto_ubica_oggetto_ist?: number;
  id_ubica_oggetto_istanza?: number;
  sezione: string;
  foglio: number;
  particella: string;
  cod_istat_comune?: string;
  ind_fonte_dato: string;
  cod_belfiore: string;
  to_be_validated?: boolean; // not in DTO
  flg_fuori_geometrie?: boolean; // not in DTO
}
