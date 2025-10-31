/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Adempimento } from 'src/app/shared/models';
import { CategoriaAllegato } from './categoria-allegato.model';

export interface TipoAllegato {
  adempimento: Adempimento;
  tipologia_allegato: TipologiaAllegato;

  flg_firma_digitale: boolean;
  flg_firma_non_valida_bloccante: boolean;
  flg_integrazione: boolean;
  flg_nota: boolean;
  flg_obbligo: boolean;
  flg_riservato: boolean;
}

export interface TipologiaAllegato {
  id_tipologia_allegato?: number;
  cod_tipologia_allegato: string;
  des_tipologia_allegato?: string;
  ordinamento_tipologia_allegato?: number;
  flg_attivo?: boolean;
  flg_atto?: boolean;
  // rif: SCRIVA-1379, SCRIVA-1380
  flg_sistema?: boolean;
  categoria_allegato?: CategoriaAllegato;
}
