/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { StatoIstanza } from './stato-istanza.model';

export interface IstanzaStato {
  id_istanza: number;
  data_cambio_stato?: string;
  stato_istanza: StatoIstanza;
}
