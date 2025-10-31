/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { IAlertConfig } from '../../../interfaces/scriva-utilities.interfaces';

/**
 * Interfaccia che rappresenta l'oggetto di risposta del servizio di gestione delle geometrie Geeco
 */
export interface IGeoGeeco {
  url: string;
}

/**
 * Interfaccia che rappresenta l'oggetto di configurazione per la request delle geometrie Geeco
 */
export interface IGeoGeecoConfigs {
  /** number con l'id istanza per aprire geeco. E' OBBLIGATORIO! */
  id_istanza: number;
  id_oggetto_istanza?: number[];
  chiave_config?: string;
  cod_tipologia_oggetto?: string;
  id_soggetto?: number;
  /** string che definisce tutti i query params passati dallo strato di FE che ci si aspetta che ritorni nel momento in cui geeco viene chiuso. Deve cominciare con "&". */
  quit_url_params?: string;
}

/**
 * Interfaccia dedicata che definisce tutte le informazioni di configurazione per gestire l'apertura di Geeco con una gestione di flusso standard.
 */
export interface IGeecoOpen {
  /** IGeoGeecoConfigs con la configurazione per la connessione a geeco. */
  geoGeeco: IGeoGeecoConfigs;
  /** string che definisce la modalità di accesso dell'attore per la gestione dell'istanza. Come informazione deve essere passata a geeco per la gestione della modalità di utilizzo dati (WRITE, READ). */
  attoreGestioneIstanza: string;
  /** IAlertConfig con la configurazione per gestire eventuali errori durante la connessione a Geeco. */
  errorConfigs: IAlertConfig;
}

/**
 * Interfaccia che rappresenta i parametri di rientro da geeco una richiamato l'url alla chiusura della libreria.
 */
export interface IGeecoReturnParams extends IGeecoQuitUrlParams {
  idIstanza: number;
}

/**
 * Interfaccia che rappresenta la gestione dei parametri da utilizzare per quello che riguarda la logica di ritorno di Geeco per la parte di captazione.
 */
export interface IGeecoQuitUrlParams {
  codQuadro?: string;
  idTipoQuadro?: number;
}
