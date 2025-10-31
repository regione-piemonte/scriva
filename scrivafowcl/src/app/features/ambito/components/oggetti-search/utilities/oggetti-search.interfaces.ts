import { OggettiSearchRequest } from '../../../models/oggetti-search/oggetti-search-request';
import { Provincia } from '../../../../../shared/models';
import { Soggetto } from '../../../models';

/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */

/**
 * Interfaccia che definisce le informazioni di ricerca delle opere.
 */
export interface IFormOggettiSearchData {
  searchParams: OggettiSearchRequest;
  formParams: OggettiSearchFormData;
}

/**
 * Interfaccia che definisce le informazioni gestite dal form dati per la ricerca degli oggetti.
 */
export interface OggettiSearchFormData {
  provincia?: Provincia[];
  id_comune?: number;
  den_oggetto?: string;
  cod_scriva?: string;
  anno_presentazione?: any;
  // La gestione del soggetto dipende dalla configurazione. Se definita in input oppure, per default, è un oggetto Soggetto
  cf_soggetto?: any | Soggetto;
}