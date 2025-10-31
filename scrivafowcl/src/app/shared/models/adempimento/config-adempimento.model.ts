/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export interface ConfigAdempimento {
  id_adempimento_config: number;
  id_adempimento: number;
  des_adempimento: string;
  id_informazione_scriva: number;
  des_informazione_scriva: string;
  chiave: string;
  valore: string;
  ordinamento: number;
}
