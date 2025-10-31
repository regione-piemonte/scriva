/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { TipoAdempimento } from '@pages/ambiti/model/tipo-adempimento.model';
import { Ambito } from '@pages/ambiti/model/ambito.model';

export interface Adempimenti {
  cod_tipo_adempimento: string;
  des_estesa_tipo_adempimento: string;
  des_tipo_adempimento: string;
  id_ambito: number;
  id_tipo_adempimento: number;
  ordinamento_tipo_adempimento: number;
  preferito: number;
  uuid_index: string;
  cod_adempimento: string;
  des_adempimento: string;
  id_adempimento: number;
  ordinamento_adempimento: number;
  tipo_adempimento: TipoAdempimento;
  des_estesa_adempimento: string;
  mostra_avvisi?: boolean;
  ambito: Ambito;
}
