/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export interface GruppoSoggetto {
  id_gruppo_soggetto?: number;
  gestUID?: string;
  cod_gruppo_soggetto?: string;
  des_gruppo_soggetto: string;
  flg_creazione_automatica: boolean;
}
