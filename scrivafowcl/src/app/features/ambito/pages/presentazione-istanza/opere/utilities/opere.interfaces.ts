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
import {
  ConfigAdempimento,
  Help,
  Provincia,
} from '../../../../../../shared/models';
import { FormioFormBuilderTypes } from '../../../../../../shared/services/formio/utilities/formio.types';
import { OggettoIstanza, Opera, SoggettoIstanza } from '../../../../models';
import { SezioneObbligatoriaDatiTecniciMancanti } from './opere.classes';

/**
 * Interfaccia che definisce le chiamate per lo scarico dati per le informazioni associate all'adempimento.
 */
export interface IAdempimentoOperaLinkReq {
  province: Observable<Provincia[]>;
  configsAdempimento: Observable<ConfigAdempimento[]>;
  helpList: Observable<Help[]>;
}

/**
 * Interfaccia che definisce le risposte scaricate come dati per le informazioni associate all'adempimento.
 */
export interface IAdempimentoOperaLinkRes {
  province: Provincia[];
  configsAdempimento: ConfigAdempimento[];
  helpList: Help[];
}

/**
 * Interfaccia che definisce le chiamate per lo scarico dati per le informazioni associate all'istanza.
 */
export interface IOperaInstanzaDerivatiReq {
  soggettiIstanza: Observable<SoggettoIstanza[]>;
  opereIstanza: Observable<Opera[]>;
  oggettiIstanza: Observable<OggettoIstanza[]>;
}

/**
 * Interfaccia che definisce le risposte allo scarico dati per le informazioni associate all'istanza.
 */
export interface IOperaInstanzaDerivatiRes {
  soggettiIstanza: SoggettoIstanza[];
  opereIstanza: Opera[];
  oggettiIstanza: OggettoIstanza[];
}

/**
 * Interfaccia che raccoglie TUTTI i dati tecnici di tutti gli oggetti istanza dell'istanza.
 */
export interface IListeDatiTecniciOggettiIstanze {
  [key: string]: IDatiTecniciOggettoIstanza[];
}

/**
 * Interfaccia con i dati tecnici dell'oggetto istanza, gestiti tramite formio, hanno alcune informazioni comuni a tutti.
 */
export interface IDatiTecniciOggettoIstanza {
  id_oggetto_istanza: number;
  identificativo: string;
  [key: string]: any;
}

/**
 * Interfaccia che definisce le informazioni da tracciare per la mancanza di dati tecnici collegati ad un OggettoIstanza.
 */
export interface IOggettoIstanzaSenzaDatiTecnici {
  tipo: string;
  codScriva: string;
}

/**
 * Interfaccia che definisce le informazioni da tracciare per la mancanza dei dati di una sezione dei dati tecnici.
 */
export interface ISezioneObbligatoriaDatiTecniciMancanti {
  opera?: Opera;
  oggettoIstanza?: OggettoIstanza;
  sezioneFormIo?: FormioFormBuilderTypes;
}

/**
 * Interfaccia che definisce le informazioni da tracciare per la mancanza dei dati.
 */
export interface IAggregatoreDatiTecniciMancanti {
  datiSegnalazioni?: SezioneObbligatoriaDatiTecniciMancanti[];
}
