/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { TipoAbilitazione } from '../abilitazione/tipo-abilitazione.model';
import { Compilante } from '../compilante/compilante.model';
import { ProfiloApp } from '../profilo-applicativo/profilo-app.model';

export interface IstanzaAttore {
  id_istanza_attore?: number;
  id_istanza: number;
  tipo_abilitazione: TipoAbilitazione;
  compilante?: Compilante;
  profilo_app?: ProfiloApp;
  cf_attore: string;
  cf_abilitante_delegante: string;
  data_inizio?: string;
  data_revoca?: string;
  data_delega_con_firma?: Date;
}
