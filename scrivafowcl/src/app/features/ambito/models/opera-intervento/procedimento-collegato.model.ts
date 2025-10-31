/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export interface ProcedimentoCollegato {
  soggetto: {
    cognome?: string;
    nome?: string;
    den_soggetto?: string;
  };
  id_istanza: number;
  cod_istanza: string;
  cod_pratica?: string;
}
