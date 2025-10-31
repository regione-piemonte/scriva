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
 */
export interface IPozzoDatiIdentificativi {
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
  profondita?: string;
  quota_piano_c?: string;
  quota_base_acq?: string;
  flg_campo_pozzo?: TypeSiNo;
  flg_pozzo_art?: TypeSiNo;
  codice_roc_bck?: string;
}

/**
 * Interfaccia con le informazioni che costituiscono la sezione dell'opera.
 * DATI_STRUTTURALI.dati_strutturali.data_costruzione
 * DATI_STRUTTURALI.dati_strutturali.tipo_falda
 * DATI_STRUTTURALI.dati_strutturali.flg_disp_strat
 * DATI_STRUTTURALI.diametro_colonna
 * DATI_STRUTTURALI.presenza_cementazione
 * DATI_STRUTTURALI.presenza_dreni
 * DATI_STRUTTURALI.presenza_filtri
 */
export interface IPozzoDatiStrutturali {
  data_costruzione?: string;
  tipo_falda?: IPDSTipoFalda;
  flg_disp_strat?: TypeSiNo;
  diametro_colonna?: IPDSDiametroColonna[];
  presenza_cementazione?: IPDSPresenzaCementazione[];
  presenza_dreni?: IPDSPresenzaDreni[];
  presenza_filtri?: IPDSPresenzaFiltri[];
}

/**
 * Interfaccia per le informazioni specifiche di un dato di una sezione dell'opera.
 */
export interface IPDSTipoFalda {
  id_tipo_falda: number;
  des_tipo_falda: string;
}

/**
 * Interfaccia per le informazioni specifiche di un dato di una sezione dell'opera.
 */
export interface IPDSDiametroColonna {
  progressivo_tratto?: string;
  diametro?: string;
  profondita_da?: string;
  profondita_a?: string;
}

/**
 * Interfaccia per le informazioni specifiche di un dato di una sezione dell'opera.
 */
export interface IPDSPresenzaCementazione {
  progressivo_tratto?: string;
  mater?: IPDSMater;
  profondita_da?: string;
  profondita_a?: string;
}

/**
 * Interfaccia per le informazioni specifiche di un dato di una sezione dell'opera.
 */
export interface IPDSMater {
  id_mater?: number;
  des_mater?: string;
}

/**
 * Interfaccia per le informazioni specifiche di un dato di una sezione dell'opera.
 */
export interface IPDSPresenzaDreni {
  progressivo_tratto?: string;
  profondita_da?: string;
  profondita_a?: string;
}

/**
 * Interfaccia per le informazioni specifiche di un dato di una sezione dell'opera.
 */
export interface IPDSPresenzaFiltri {
  progressivo_tratto?: string;
  tipo_filtro?: IPDSTipoFiltro;
  apertura?: number;
  profondita_da?: string;
  profondita_a?: string;
  origine_dato?: IPDSOrigineDato;
}

/**
 * Interfaccia per le informazioni specifiche di un dato di una sezione dell'opera.
 */
export interface IPDSTipoFiltro {
  id_tipo_filtro?: number;
  des_tipo_filtro?: string;
}

/**
 * Interfaccia per le informazioni specifiche di un dato di una sezione dell'opera.
 */
export interface IPDSOrigineDato {
  id_origine_dato?: number;
  des_origine_dato?: string;
}

/**
 * Interfaccia con le informazioni che costituiscono la sezione dell'opera.
 * PROVE_EMUNGIMENTO.prove_emungimento.flg_disp_mis_piez
 * PROVE_EMUNGIMENTO.prove_emungimento.piez_assoc
 * PROVE_EMUNGIMENTO.emungimento_gradini
 * PROVE_EMUNGIMENTO.emungimento_lunga_durata
 */
export interface IPozzoProveDiEmungimento {
  flg_disp_mis_piez?: TypeSiNo;
  piez_assoc?: string;
  // Per queste due liste non sono necessari controlli specifici, non le censisco rispetto all'analisi
  emungimento_gradini?: any[];
  emungimento_lunga_durata?: any[];
}
