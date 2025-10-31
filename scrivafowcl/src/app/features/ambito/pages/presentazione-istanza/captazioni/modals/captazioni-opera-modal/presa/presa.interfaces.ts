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
 * DATI_IDENTIFICATIVI.dati_identificativi.tipo_presa
 * DATI_IDENTIFICATIVI.denominazione
 * DATI_IDENTIFICATIVI.dati_identificativi.denomin_cidrsup
 * JS_READ_ONLY.DATI_IDENTIFICATIVI.comune;
 * DATI_IDENTIFICATIVI.localizzazione.localita;
 * JS_READ_ONLY.DATI_IDENTIFICATIVI.cod_catastale;
 * JS_READ_ONLY.DATI_IDENTIFICATIVI.sezione;
 * JS_READ_ONLY.DATI_IDENTIFICATIVI.foglio;
 * JS_READ_ONLY.DATI_IDENTIFICATIVI.particella;
 * DATI_IDENTIFICATIVI.altri_elementi_identificativi.sponda
 * DATI_IDENTIFICATIVI.altri_elementi_identificativi.progressiva_asta
 * DATI_IDENTIFICATIVI.altri_elementi_identificativi.flg_galleria_filtr
 * DATI_IDENTIFICATIVI.altri_elementi_identificativi.flg_capt_subalveo
 * DATI_IDENTIFICATIVI.altri_elementi_identificativi.codice_roc_bck
 * DATI_IDENTIFICATIVI.altri_elementi_identificativi.flg_da_canale
 */
export interface IPresaDatiIdentificativi {
  codice_roc?: string;
  codice_rilievo?: string;
  codice_sii?: string;
  tipo_presa?: IPDITipoPresa;
  denominazione?: string;
  denomin_cidrsup?: string;
  comune?: string;
  localita?: string;
  catas_comune?: string;
  catas_sezione?: string;
  catas_foglio?: string;
  catas_particella?: string;
  sponda?: IPDISponda;
  progressiva_asta?: string;
  flg_galleria_filtr?: TypeSiNo;
  flg_capt_subalveo?: TypeSiNo;
  codice_roc_bck?: string;
  flg_da_canale?: TypeSiNo;
}

/**
 * Interfaccia per le informazioni specifiche di un dato di una sezione dell'opera.
 */
export interface IPDITipoPresa {
  id_tipo_presa: number;
  des_tipo_presa: string;
}

/**
 * Interfaccia per le informazioni specifiche di un dato di una sezione dell'opera.
 */
export interface IPDISponda {
  id_sponda: number;
  des_sponda: string;
}

/**
 * Interfaccia con le informazioni che costituiscono la sezione dell'opera.
 * DATI_INFRASTRUTTURALI.sbarramento.tipo_sbarr
 * DATI_INFRASTRUTTURALI.sbarramento.altezza
 * DATI_INFRASTRUTTURALI.sbarramento.volume_max_invaso
 * DATI_INFRASTRUTTURALI.tutela_della_fauna_ittica.flg_scala
 * DATI_INFRASTRUTTURALI.tutela_della_fauna_ittica.tipo_scala
 * DATI_INFRASTRUTTURALI.tutela_della_fauna_ittica.sponda_scala
 * DATI_INFRASTRUTTURALI.tutela_della_fauna_ittica.flg_obblighi_itt
 * DATI_INFRASTRUTTURALI.tutela_della_fauna_ittica.specie_ittiche
 */
export interface IPresaDatiInfrastrutturali {
  tipo_sbarr?: IPDITipoSbarramento;
  altezza?: string;
  volume_max_invaso?: string;
  flg_scala?: TypeSiNo;
  tipo_scala?: IPDITipoScala;
  sponda_scala?: IPDISpondaScala;
  flg_obblighi_itt?: TypeSiNo;
  specie_ittiche?: string;
  flg_sbarramento?: TypeSiNo;
}

/**
 * Interfaccia per le informazioni specifiche di un dato di una sezione dell'opera.
 */
export interface IPDITipoSbarramento {
  id_tipo_sbarr: number;
  des_tipo_sbarr: string;
}

/**
 * Interfaccia per le informazioni specifiche di un dato di una sezione dell'opera.
 */
export interface IPDITipoScala {
  id_tipo_scala: number;
  des_tipo_scala: string;
}

/**
 * Interfaccia per le informazioni specifiche di un dato di una sezione dell'opera.
 */
export interface IPDISpondaScala {
  id_sponda_scala: number;
  des_sponda_scala: string;
}
