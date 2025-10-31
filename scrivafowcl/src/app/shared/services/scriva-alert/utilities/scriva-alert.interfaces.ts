/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ScrivaAlertConfigs } from '../../../components/scriva-alert/utilities/scriva-alert.classes';
import { TScrivaAlertType } from '../../../components/scriva-alert/utilities/scriva-alert.types';
import { TScrivaDataPlaceholders } from './scriva-alert.types';

/**
 * Interfaccia che definisce la struttura per la configurazione dell'interfaccia per il ScrivaAlert tramite configurazione del messaggio da DB.
 */
export interface IAlertConfigsFromCode {
  code: string;
  codesPlaceholders?: string[];
  dataReplace?: TScrivaDataPlaceholders;
  fallbackPlaceholder?: string;
}

/**
 * Interfaccia che definisce la struttura dell'oggetto per applicare differenti controlli sull'alert configs.
 */
export interface IAlertConfigsChecks {
  [key: string]: (c: ScrivaAlertConfigs) => boolean;
}

/**
 * Interfaccia di comodo che definisce la struttura per l'aggiornamento dati di un oggetto di alert, con messsaggi.
 */
export interface IAlertConfigsUpdate {
  alert: ScrivaAlertConfigs;
  messaggi?: string[];
  tipo?: TScrivaAlertType;
}
