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
 * Enum che definisce i codici per la gestione degli stati di lettura delle notifiche.
 */
export enum CodStatiNotifiche {
  lette = 'L',
  nonLette = 'N',
  nonCancellate = 'NC',
  nonLetteNonCancellate = 'NLNC',
}


/**
 * Enum che definisce i tipi di canale delle notifiche
 */
export enum ChannelType {
  email = 'EMAIL',
  pec = 'PEC',
  sms = 'SMS',
  appIo = 'APP_IO',
  frontend = 'WEB',
}
