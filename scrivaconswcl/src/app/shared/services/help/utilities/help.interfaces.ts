/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
export interface Help {
  id_help: number;
  chiave_help: string;
  valore_campo_help?: string;
  des_testo_help: string;
  note_help?: string;
  tipo_help: TipoHelp;
  livello_help: LivelloHelp;
}

interface TipoHelp {
  id_tipo_help: number;
  cod_tipo_help: string;
  des_tipo_help: string;
}

interface LivelloHelp {
  id_livello_help: number;
  cod_livello_help: string;
  des_livello_help: string;
}
