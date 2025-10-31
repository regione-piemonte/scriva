/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Istanza } from 'src/app/shared/models/istanza/istanza.model';

/**
 * Modello dei dati che descrive la notifica applicativa
 */
export interface NotificaApplicativa {
  gestUID: string;
  cf_destinatario?: string;
  data_cancellazione?: string;
  data_inserimento: string;
  data_lettura?: string;
  des_messaggio?: string;
  des_oggetto?: string;
  id_componente_app_push?: number;
  istanza?: Partial<Istanza>;
  id_notifica: number;
  /* Chiave primaria del modello */
  id_notifica_applicativa: number;
}
