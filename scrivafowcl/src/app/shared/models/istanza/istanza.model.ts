/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Adempimento } from '../adempimento/adempimento.model';
import { EsitoProcedimento } from './esito-procedimento.model';
import { StatoProcedimento } from './stato-procedimento.model';
import { StatoIstanza } from './stato-istanza.model';
import { IstanzaResponsabile } from './istanza-responsabile.model';
import { ProfiloApp } from '../profilo-applicativo/profilo-app.model';
import { TipoAdempimentoOggApp } from '../profilo-applicativo/tipo-adempimento-ogg-app.model';

export interface Istanza {
  gestUID?: string;
  id_istanza?: number;
  adempimento?: Adempimento;
  cod_istanza?: string;
  cod_pratica?: string;
  anno_registro?: number;

  data_inserimento_istanza?: string;
  data_modifica_istanza?: string;
  data_inserimento_pratica?: string;
  data_invio_istanza?: string;
  data_modifica_pratica?: string;
  data_pubblicazione?: string;
  data_inizio_osservazioni?: string;
  data_fine_osservazioni?: string;
  data_conclusione_procedimento?: string;
  data_scadenza_procedimento?: string;
  data_protocollo_istanza?: string;
  num_protocollo_istanza?: string;
  des_stato_sintesi_pagamento?: string;

  esito_procedimento?: EsitoProcedimento;
  esito_procedimento_statale?: EsitoProcedimento;
  stato_procedimento_statale?: StatoProcedimento;
  cod_esito_procedimento_statale?: string;
  des_esito_procedimento_statale?: string;
  des_stato_procedimento_statale?: string;

  id_funzionario?: number;
  id_template?: number;
  stato_istanza?: StatoIstanza;
  json_data?: string;
  responsabili_istanza?: IstanzaResponsabile[];

  profili_app?: ProfiloApp[];
  tipi_adempimento_ogg_app?: TipoAdempimentoOggApp[];
}