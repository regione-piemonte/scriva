/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { VincoloAutorizza } from './vincolo-autorizza.model';

export interface IstanzaVincoloAut {
  gestUID?: string;
  id_istanza_vincolo_aut?: number;
  id_istanza: number;
  des_vincolo_calcolato: string;
  des_ente_utente: string;
  des_email_pec_ente_utente: string;
  vincolo_autorizza: VincoloAutorizza;
}
