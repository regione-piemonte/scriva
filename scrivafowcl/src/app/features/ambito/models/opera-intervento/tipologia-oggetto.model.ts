/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { NaturaOggetto } from './natura-oggetto.model';

// TipologiaOggettoExtendedDTO
export interface TipologiaOggetto {
  id_tipologia_oggetto?: number;
  cod_tipologia_oggetto?: string;
  des_tipologia_oggetto?: string;
  natura_oggetto?: NaturaOggetto;
}
