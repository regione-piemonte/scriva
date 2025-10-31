/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export interface AllegatoLight {
  id_allegato_istanza: number;
  id_istanza: number;
  id_tipologia_allegato: number;
  id_tipo_integra_allegato?: number;
  uuid_index: string;
  flg_riservato: boolean;
  cod_allegato: string;
  nome_allegato: string;
  dimensione_upload: number;
  data_upload: string;
  flg_cancellato: boolean;
  ind_firma: number;
  note: string;
}
