/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { RuoloCompilante } from 'src/app/shared/models';
import { RecapitoAlternativo } from '..';
import { GruppoSoggetto } from './gruppo-soggetto.model';
import { RuoloSoggetto } from './ruolo-soggetto.model';
import { Soggetto } from './soggetto.model';

export interface SoggettoIstanza extends Soggetto {
  gestUID?: string;
  id_soggetto_istanza?: number;
  id_soggetto_padre?: number;
  soggetto: Soggetto;
  id_istanza: number;
  ruolo_soggetto?: RuoloSoggetto;
  ruolo_compilante: RuoloCompilante;
  flg_capogruppo?: boolean;
  recapito_alternativo?: RecapitoAlternativo[];
  gruppo_soggetto?: GruppoSoggetto;
}
