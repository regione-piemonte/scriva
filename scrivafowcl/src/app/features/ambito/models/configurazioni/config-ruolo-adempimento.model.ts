/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Adempimento, RuoloCompilante } from 'src/app/shared/models';

export interface ConfigRuoloAdempimento {
  adempimento: Adempimento;
  ruolo_compilante: RuoloCompilante;
  flg_modulo_delega: boolean;
  flg_modulo_procura: boolean;
  flg_popola_richiedente: boolean;
  flg_ruolo_default: boolean;
}
