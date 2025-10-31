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
 * Oggetto di richiesta per la Ricerca degli oggetti
 */
export interface OggettiSearchRequest {
  anno_presentazione?: number;
  cf_soggetto?: string;
  cod_scriva?: string;
  // TODO credo che siano enum a BE
  cod_tipo_oggetto?: string;
  // TODO credo che siano enum a BE
  cod_tipologia_oggetto?: string;
  den_oggetto?: string;
  flg_cat_ogg?: boolean;
  id_adempimento?: number;
  id_comune?: number;
  id_istanza?: number;
  id_soggetti?: number[];
}