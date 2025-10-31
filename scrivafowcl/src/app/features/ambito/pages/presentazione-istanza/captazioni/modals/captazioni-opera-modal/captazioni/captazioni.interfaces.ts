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
 * Interfaccia con le informazioni per il check su valore minimo/massimo.
 */
export interface ICheckValoreMinMax {
  valore: string;
  interi: number;
  decimali?: number;
  required?: boolean;
  allowMinZero?: boolean;
  allowMaxZero?: boolean;
}

/**
 * Interfaccia con le informazioni che costituiscono la sezione dell'opera.
 * ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.stato_esercizio
 * ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_max_deriv
 * ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_med_deriv
 * ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.volume_max
 * ESERCIZIO_DELLA_CAPTAZIONE.qta_risorsa_captata
 */
export interface ICaptazioniEsercizioDellaCaptazione {
  stato_esercizio?: ICaptEDCStatoEsercizio;
  portata_max_deriv?: string;
  portata_med_deriv?: string;
  volume_max_concessione?: string;
  qta_risorsa_captata?: ICaptEDCQtaRisorsaCaptata[];
}

/**
 * Interfaccia per le informazioni specifiche di un dato di una sezione dell'opera.
 */
export interface ICaptEDCStatoEsercizio {
  id_stato_esercizio: number;
  des_stato_esercizio: string;
}

/**
 * Interfaccia per le informazioni specifiche di un dato di una sezione dell'opera.
 */
export interface ICaptEDCQtaRisorsaCaptata {
  inizio_periodo?: string;
  fine_periodo?: string;
  portata_max_derivata?: string;
  portata_med_derivata?: string;
  volume_max_concessione?: string;
}

/**
 * Interfaccia con le informazioni che costituiscono la sezione dell'opera.
 * RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_dispositivi
 * RILASCIO_A_VALLE_DELLA_PRESA.rilascio_imposto
 * RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_de
 * RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.modulazione
 * RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_deroga_de
 * RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.ulteriori_obblighi
 */
export interface ICaptazioneRilascioAValle {
  flg_dispositivi?: TypeSiNo;
  // Non tipizzo l'oggetto, i controlli sono interni
  rilascio_imposto?: any;
  flg_de?: TypeSiNo;
  modulazione?: ICaptRAVModulazione;
  flg_deroga_de?: TypeSiNo;
  ulteriori_obblighi?: string;
}

/**
 * Interfaccia per le informazioni specifiche di un dato di una sezione dell'opera.
 */
export interface ICaptRAVModulazione {
  id_modulazione: number;
  des_modulazione: string;
}
