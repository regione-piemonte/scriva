/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export interface RuoloCompilante {
  id_ruolo_compilante: number;
  cod_ruolo_compilante: string;
  des_ruolo_compilante: string;
  flg_modulo_delega?: boolean;
  flg_modulo_procura?: boolean;
}
