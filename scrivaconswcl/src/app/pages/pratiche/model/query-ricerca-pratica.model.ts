/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
export interface QueryRicercaPratica {
  id_ambito: number;
  id_tipo_adempimento: number;
  id_adempimento: number;
  id_competenza_territorio: number;
  anno_presentazione_progetto: string;
  codice_pratica: string;
  denominazione_oggetto: string;
  sigla_provincia_oggetto: string;
  cod_istat_comune_oggetto: string;
}
