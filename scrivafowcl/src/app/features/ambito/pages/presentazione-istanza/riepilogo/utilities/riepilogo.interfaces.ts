/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Observable } from 'rxjs';
import { CategoriaAllegato } from '../../../../models';

/**
 * Interfaccia con l'insieme di chiamata per il check delle categorie prima del download degli allegati.
 */
export interface ICheckCategorieAllegatiReq {
  categorieObbligatorie: Observable<CategoriaAllegato[]>;
  categorieAdempimento: Observable<CategoriaAllegato[]>;
}

/**
 * Interfaccia con l'insieme di risposte per il check delle categorie prima del download degli allegati.
 */
export interface ICheckCategorieAllegatiRes {
  categorieObbligatorie: CategoriaAllegato[];
  categorieAdempimento: CategoriaAllegato[];
}

/**
 * Interfaccia con l'insieme di chiamata per il check per lo scarico modulo.
 */
export interface ICheckScaricaModuloReq {
  checkDichiarazioniObbligatorie: Observable<boolean>;
  checkIstanza: Observable<any>;
}

/**
 * Interfaccia con l'insieme di risposte per il check per lo scarico modulo.
 */
export interface ICheckScaricaModuloRes {
  checkDichiarazioniObbligatorie: boolean;
  checkIstanza: any;
}

/**
 * Interfaccia di comodo usata per il check sui dati delle dichiarazioni.
 */
export interface IDichiarazioneFormio {
  check: boolean;
  cod_dichiarazione: string;
  des_dichiarazione: string;
  obbligatorio: boolean;
}