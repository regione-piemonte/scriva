/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { TipologiaAllegato } from './tipologia-allegato.model';

export interface Allegato {
  id_allegato_istanza: number;
  id_allegato_istanza_padre?: number;
  id_istanza: number;
  id_classe_allegato?: number;
  id_istanza_osservazione?: number;
  id_funzionario?: number;
  id_istanza_attore?: number;
  uuid_index: string;

  tipologia_allegato: TipologiaAllegato;
  tipo_integra_allegato?: IntegraAllegato;
  data_integrazione?: string;

  cod_allegato: string;
  nome_allegato: string;
  dimensione_upload: number;
  data_upload: string;
  data_pubblicazione?: string;
  data_cancellazione?: string;
  note: string;

  num_protocollo_allegato?: string;
  data_protocollo_allegato?: string;
  num_atto?: string;
  data_atto?: string;
  titolo_allegato?: string;
  autore_allegato?: string;
  data_invio_esterno?: string;

  flg_riservato: boolean;
  flg_cancellato: boolean;
  flg_da_pubblicare?: boolean;
  ind_firma: number;
  url_doc?: string;

  classe_allegato?: ClasseAllegato;
  // non nel modello
  treeId?: string
}

export interface IntegraAllegato {
  id_tipo_integra_allegato?: number;
  cod_tipo_integra_allegato?: string; // 'P' or 'I'
  des_tipo_integra_allegato?: string;
}

export interface TreeElement {
  treeId: string;
  columns: any;
  level?: number;
  treeStatus?: string;
  treeParentId?: string;
  allegato?: Allegato;
  actions?: IRowAllegatoAction;
}

/**
 * Interfaccia che rappresenta i flag di gestione per le azioni di una riga della tabella, identificando le azioni che si possono compiere sull'allegato.
 */
export interface IRowAllegatoAction {
  editVisible?: boolean;
  deleteVisible?: boolean;
  deletelogicVisible?: boolean;
  edit?: boolean;
  delete?: boolean;
  deletelogic?: boolean;
}

export interface ClasseAllegato {
  cod_classe_allegato?: string;
  des_classe_allegato?: string;
  flg_attivo?: boolean;
  id_classe_allegato?: number;
  ordinamento_classe_allegato?: number;
}