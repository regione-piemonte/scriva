/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ScrivaTipiPersona } from '../../../../../../../shared/enums/scriva-utilities/scriva-utilities.enums';
import { RuoloCompilante } from '../../../../../../../shared/models';
import { Soggetto } from '../../../../../models';

/**
 * Interfaccia usata per la gestione delle funzioni del componente.
 */
export interface IDatiCompilanteAccessibili {
  ultimoTipoPersonaCercato: ScrivaTipiPersona;
  cfSoggettoInLinea: string;
  cfSoggettoForm: string;
  ruoloCompilante: RuoloCompilante;
}

/**
 * Interfaccia usata per la gestione dei dati emessi quando il form è stato submittato senza errori.
 */
export interface ISoggettoFormSubmitData {
  soggetto: Soggetto;
  formRawValue: any;
  firmatario?: Soggetto;
  buttonIndex?: number;
}

/**
 * Interfaccia usata per passare i parametri alle chiamate di recupero del richiedente, sia per PF che per PG.
 * La informazioni sono differenti a seconda del tipo di chiamata che vuole fare.
 */
export interface IRecuperaRichiedente {
  /** string con il codice fiscale del soggetto da ricercare. Usato sia per PF che per PG. */
  cfSoggetto: string;
  /** string con il codice adempimento per la ricerca. Usato sia per PF che per PG. */
  codAdempimento: string;
  /** string con il codice fiscale del soggetto da ricercare. Usato per PG. */
  cfImpresa?: string;
}