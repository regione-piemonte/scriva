/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Ambito } from '@app/pages/ambiti/model';
import { CompetenzaTerritorio } from '@app/pages/pratiche/model';
import { StatoIstanza } from '../enums/stato-istanza';

export interface Osservazione {
  cf_osservazione_ins: string;
  data_osservazione: string;
  data_pubblicazione: string;
  gestUID: string;
  id_istanza_osservazione: number;
  istanza: IstanzaOss;
  stato_osservazione: StatoOsservazione;
  den_oggetto?: string;
}

export interface IstanzaOss {
  ambito?: Ambito;
  adempimento: AdempimentoOss;
  cod_istanza: string;
  cod_pratica: string;
  competenza_territorio: CompetenzaTerritorio;
  data_fine_osservazione: string;
  data_inizio_osservazione: string;
  data_inserimento_istanza: string;
  data_modifica_istanza: string;
  data_protocollo: string;
  data_pubblicazione: string;
  des_stato_sintesi_pagamento: string; // StatoPagamento?
  flg_osservazione: boolean;
  id_istanza: number;
  numero_protocollo: string;
  // soggetto_istanza: SoggettoIstanza;
  stato_istanza: StatoIstanza;
}

export interface AdempimentoOss {
  cod_adempimento: string;
  cod_tipo_adempimento: string;
  des_adempimento: string;
  des_tipo_adempimento: string;
}

export interface StatoOsservazione {
  cod_stato_osservazione: StatoIstanza;
  des_stato_osservazione: string;
}

// export interface SoggettoIstanza {

// }
