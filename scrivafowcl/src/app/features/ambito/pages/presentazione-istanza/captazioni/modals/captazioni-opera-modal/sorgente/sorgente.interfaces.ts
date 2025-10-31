/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { TypeSiNo } from '../../../../../../../../shared/types/formio/scriva-formio.types';

/**
 * Interfaccia con le informazioni che costituiscono la sezione dell'opera.
 * DATI_IDENTIFICATIVI.dati_identificativi.codice_roc
 * DATI_IDENTIFICATIVI.dati_identificativi.codice_rilievo
 * DATI_IDENTIFICATIVI.dati_identificativi.codice_sii
 * DATI_IDENTIFICATIVI.denominazione
 * JS_READ_ONLY.DATI_IDENTIFICATIVI.comune;
 * DATI_IDENTIFICATIVI.localizzazione.localita;
 * JS_READ_ONLY.DATI_IDENTIFICATIVI.cod_catastale;
 * JS_READ_ONLY.DATI_IDENTIFICATIVI.sezione;
 * JS_READ_ONLY.DATI_IDENTIFICATIVI.foglio;
 * JS_READ_ONLY.DATI_IDENTIFICATIVI.particella;
 * DATI_IDENTIFICATIVI.altri_elementi_identificativi.quota_piano_c
 */
export interface ISorgenteDatiIdentificativi {
  codice_roc?: string;
  codice_rilievo?: string;
  codice_sii?: string;
  denominazione?: string;
  comune?: string;
  localita?: string;
  catas_comune?: string;
  catas_sezione?: string;
  catas_foglio?: string;
  catas_particella?: string;
  quota_piano_c?: string;
}

/**
 * Interfaccia con le informazioni che costituiscono la sezione dell'opera.
 * DATI_SPECIFICI_DELLA_SORGENTE.dati_specifici_della_sorgente.tipo_sorg
 * DATI_SPECIFICI_DELLA_SORGENTE.dati_specifici_della_sorgente.flg_tributaria
 */
export interface ISorgenteDatiSpecifici {
  tipo_sorg?: ISDSTipoSorgente;
  flg_tributaria?: TypeSiNo;
}

/**
 * Interfaccia per le informazioni specifiche di un dato di una sezione dell'opera.
 */
export interface ISDSTipoSorgente {
  id_tipo_sorg: number;
  des_tipo_sorg: string;
}
