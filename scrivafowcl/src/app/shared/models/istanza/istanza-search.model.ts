/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ProfiloApp, TipoAdempimentoOggApp } from '..';

export interface IstanzaSearch {
  id_istanza: number;
  attore_gestione_istanza: string;
  attore_modifica_fo?: string;
  attore_modifica_bo?: string;
  cod_ambito: string;
  cod_adempimento: string;
  cod_istanza: string;
  cod_pratica?: string;
  cod_stato_istanza: string;
  comune?: string;
  data_inserimento_istanza: string;
  data_modifica_istanza: string;
  data_inserimento_pratica?: string;
  data_modifica_pratica?: string;
  // data_presentazione_istanza?: string;
  den_oggetto?: string;
  den_soggetto: string;
  des_adempimento: string;
  des_stato_istanza: string;
  des_estesa_stato_istanza?: string;
  label_stato?: string;
  des_stato_sintesi_pagamento?: string;
  gestuid_istanza: string;
  tipi_adempimento_ogg_app?: TipoAdempimentoOggApp[];
  profili_app?: ProfiloApp[];
}
