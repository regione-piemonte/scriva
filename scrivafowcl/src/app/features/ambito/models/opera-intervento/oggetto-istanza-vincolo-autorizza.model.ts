/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { VincoloAutorizza } from '../titoli-abilitativi/vincolo-autorizza.model';
import { OggettoIstanza } from './oggetto-istanza.model';

export interface OggettoIstanzaVincoloAutorizza {
  id_oggetto_vincolo_aut?: number;
  oggetto_istanza?: OggettoIstanza;
  vincolo_autorizza: VincoloAutorizza;
  des_vincolo_calcolato: string;
  des_ente_utente: string;
  des_email_pec_ente_utente: string;
  to_be_validated?: boolean; // not in DTO
  flg_fuori_geometrie?: boolean; // not in DTO
}
