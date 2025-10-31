/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export interface EsitoProcedimento {
  id_esito_procedimento?: number;
  cod_esito_procedimento: string;
  des_esito_procedimento?: string;
  flg_positivo?: boolean;
  flg_esito_statale?: boolean;
  ind_esito?: number;
}
