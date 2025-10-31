/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Pratica } from '.';

export interface OsservazionePost {
  istanza: Istanza;
  stato_osservazione?: StatoOsservazione;
}

export interface Istanza {
  id_istanza: number;
}

export interface StatoOsservazione {
  cod_stato_osservazione: string;
  des_stato_osservazione?: string;
}

export interface OsservazionePut {
  cf_osservazione_ins?: string;
  data_osservazione?: string;
  data_pubblicazione?: string;
  gestUID?: string;
  id_istanza_osservazione?: number;
  istanza?: Pratica;
  stato_osservazione: StatoOsservazione;
}
