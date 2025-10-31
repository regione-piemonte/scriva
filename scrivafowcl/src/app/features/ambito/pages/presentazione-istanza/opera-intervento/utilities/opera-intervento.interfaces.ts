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
import { Help, Provincia } from '../../../../../../shared/models';
import {
  IstanzaVincoloAut,
  OggettoIstanza,
  Opera,
  SoggettoIstanza,
  TipologiaOggetto,
} from '../../../../models';

/**
 * Interfaccia che definisce le chiamate per lo scarico dati per le informazioni associate all'adempimento.
 */
export interface IAdempimentoLinkReq {
  province: Observable<Provincia[]>;
  tipologieOggetto: Observable<TipologiaOggetto[]>;
  helpList: Observable<Help[]>;
}

/**
 * Interfaccia che definisce le risposte scaricate come dati per le informazioni associate all'adempimento.
 */
export interface IAdempimentoLinkRes {
  province: Provincia[];
  tipologieOggetto: TipologiaOggetto[];
  helpList: Help[];
}

/**
 * Interfaccia che definisce le chiamate per lo scarico dati per le informazioni associate all'istanza.
 */
export interface IInstanzaDerivatiReq {
  soggettiIstanza: Observable<SoggettoIstanza[]>;
  opereIstanza: Observable<Opera[]>;
  oggettiIstanza: Observable<OggettoIstanza[]>;
  vincoliIstanza: Observable<IstanzaVincoloAut[]>;
}

/**
 * Interfaccia che definisce le risposte allo scarico dati per le informazioni associate all'istanza.
 */
export interface IInstanzaDerivatiRes {
  soggettiIstanza: SoggettoIstanza[];
  opereIstanza: Opera[];
  oggettiIstanza: OggettoIstanza[];
  vincoliIstanza: IstanzaVincoloAut[];
}

/**
 * Interfaccia che definisce le chiamate per il recupero dei messaggi per una geometria.
 */
export interface IMessaggiGeometriaReq {
  msgsSitiNatura2000: Observable<string[]>;
  msgsAreeProtette: Observable<string[]>;
  msgsDatiCatastali: Observable<string[]>;
  msgsVincoli: Observable<string[]>;
  msgsComuni: Observable<string[]>;
  msgsGenerici: Observable<string[]>;
}

/**
 * Interfaccia che definisce le rispote al recupero dei messaggi per una geometria.
 */
export interface IMessaggiGeometriaRes {
  msgsSitiNatura2000: string[];
  msgsAreeProtette: string[];
  msgsDatiCatastali: string[];
  msgsVincoli: string[];
  msgsComuni: string[];
  msgsGenerici: string[];
}

/**
 * Interface con le informazioni necessarie all'aggiornamento dei dati del quadro config.
 */
export interface IAggiornaQdrConfig {
  idOggettoIstanza: number;
  flagNuovoOggetto: boolean;
}
