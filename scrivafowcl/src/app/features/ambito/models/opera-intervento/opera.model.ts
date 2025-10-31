/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ProcedimentoCollegato } from './procedimento-collegato.model';
import { StatoOggetto } from './stato-oggetto.model';
import { TipologiaOggetto } from './tipologia-oggetto.model';
import { UbicazioneOggetto } from './ubicazione-oggetto.model';

// OggettoUbicazioneExtendedDTO + pratiche_collegate
export interface Opera {
  id_oggetto?: number;
  gestUID?: string;
  tipologia_oggetto?: TipologiaOggetto;
  stato_oggetto?: StatoOggetto;
  cod_scriva?: string;
  cod_oggetto_fonte?: string;
  data_aggiornamento?: string;
  den_oggetto?: string;
  des_oggetto?: string;
  coordinata_x?: number;
  coordinata_y?: number;
  id_masterdata_origine?: number;
  id_masterdata?: number;
  id_istanza_aggiornamento?: number;
  ubicazione_oggetto?: UbicazioneOggetto[];
  procedimento_collegata?: ProcedimentoCollegato[];
  pratica_collegata?: ProcedimentoCollegato[];
  id_istanza?: number;
  valutazioneIncidenza?: string;
}
