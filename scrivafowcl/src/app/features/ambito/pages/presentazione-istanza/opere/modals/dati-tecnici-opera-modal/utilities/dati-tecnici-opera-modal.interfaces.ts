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
import { Help, Quadro, Adempimento } from '../../../../../../../../shared/models';
import { Opera, OggettoIstanza } from '../../../../../../models';
import { ScrivaComponenteApp } from '../../../../../../../../shared/enums/scriva-utilities/scriva-utilities.enums';

/**
 * Interfaccia che definisce le proprietà per il componente della modale dei dati tecnici per un'opera da gestire in modale.
 */
export interface IDTOperaModalParams {
  attoreGestioneIstanza: string;
  componente: string;
  opera: Opera;
  oggettoIstanza: OggettoIstanza;
  readOnly: boolean;
  quadro: Quadro;
  adempimento: Adempimento;
  sourceData: any;
  showModalSalva?: boolean;
}

/**
 * Interfaccia che definisce le chiamate per lo scarico delle strutture dati che non hanno legami tra loro.
 */
export interface IDTOperModalReq {
  help: Observable<Help[]>;
}

/**
 * Interfaccia che definisce le risposte per lo scarico delle strutture dati che non hanno legami tra loro.
 */
export interface IDTOperModalRes {
  help: Help[];
}

/**
 * Interfaccia che definisce le informazioni per il salvataggio dei dati tecnici di una sezione.
 */
export interface IDTOperaSalvataggio {
  datiTecnici: any;
}

/**
 * Interfaccia che definisce le informazioni come parametri extra di gestione del formio per i dati tecnici dell'opera.
 */
export interface IFormIoDTOperaParams {
  componenteQuadro: ScrivaComponenteApp;
  oggetto: Opera;
  oggettoIstanza: OggettoIstanza;
}

/**
 * Interfaccia che definisce le callback da utilizzare per la gestione dei dati tecnici delle opere.
 */
export interface IDTOperaModalCallbacks {
  /** (modalResponse: IDTOperaSalvataggio) => any; con la funzione che gestisce il flusso logico alla chiusura della modale. */
  onModaleChiusa: (modalResponse?: IDTOperaSalvataggio) => any;
}