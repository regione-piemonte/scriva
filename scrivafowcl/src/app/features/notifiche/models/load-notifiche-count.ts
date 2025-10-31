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
 * Modello dei dati che descrive i contatrori delle notifiche.
 */
export interface ILoadNotificheCount {
  cancellate: number;
  lette: number;
  non_lette: number;
  tutte: number;
}
