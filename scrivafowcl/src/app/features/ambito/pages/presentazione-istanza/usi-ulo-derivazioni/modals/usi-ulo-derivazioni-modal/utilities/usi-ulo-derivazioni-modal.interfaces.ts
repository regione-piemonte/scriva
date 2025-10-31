/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ScrivaComponenteApp } from '../../../../../../../../shared/enums/scriva-utilities/scriva-utilities.enums';
import {
  OggettoIstanza,
  OggettoIstanzaLike,
  Opera,
  SoggettoIstanza,
} from '../../../../../../models';
import {
  IDTOperaModalParams,
  IFormIoDTOperaParams,
} from '../../../../opere/modals/dati-tecnici-opera-modal/utilities/dati-tecnici-opera-modal.interfaces';

/**
 * Interfaccia che definisce le proprietà per il componente della modale dei dati tecnici per un'opera da gestire in modale.
 */
export interface IDTUsiUloDERModalParams extends IDTOperaModalParams {
  oggettoIstanza: OggettoIstanzaLike;
}

/**
 * Interfaccia che definisce le informazioni come parametri extra di gestione del formio per i dati tecnici dell'opera.
 */
export interface IFormIoDTUsiUloDERParams extends IFormIoDTOperaParams {
  componenteQuadro: ScrivaComponenteApp;
  oggetto: Opera;
  oggettoIstanza: OggettoIstanza;
  soggetto: SoggettoIstanza;
}
