/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Comune } from 'src/app/shared/models';

// UbicazioneOggettoExtendedDTO
export interface UbicazioneOggetto {
  id_ubicazione_oggetto?: number;
  gestUID?: string;
  id_oggetto?: number;
  comune?: Comune;
  den_indirizzo?: string;
  num_civico?: string;
  des_localita?: string;
  ind_geo_provenienza?: string;
}
