/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**
 * Modello dei dati che descrive la ricerca delle notifiche
 */
export interface ISearchNotificheRequest {
  cod_stato_notifica?: string;
  data_fine?: string;
  data_inizio?: string;
  id_adempimento?: number[];
  id_notifica_applicativa?: number;
  num_istanza?: string;
}
