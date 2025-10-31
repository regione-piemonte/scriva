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
 * Interfaccia con le informazioni che costituiscono la sezione dell'opera.
 * DATI_IDENTIFICATIVI.dati_identificativi.codice_roc
 * DATI_IDENTIFICATIVI.dati_identificativi.codice_rilievo
 * DATI_IDENTIFICATIVI.denominazione
 * JS_READ_ONLY.DATI_IDENTIFICATIVI.comune;
 * DATI_IDENTIFICATIVI.localizzazione.localita;
 * JS_READ_ONLY.DATI_IDENTIFICATIVI.cod_catastale;
 * JS_READ_ONLY.DATI_IDENTIFICATIVI.sezione;
 * JS_READ_ONLY.DATI_IDENTIFICATIVI.foglio;
 * JS_READ_ONLY.DATI_IDENTIFICATIVI.particella;
 */
export interface IFontanileDatiIdentificativi {
  codice_roc?: string;
  codice_rilievo?: string;
  denominazione?: string;
  comune?: string;
  localita?: string;
  catas_comune?: string;
  catas_sezione?: string;
  catas_foglio?: string;
  catas_particella?: string;
}
