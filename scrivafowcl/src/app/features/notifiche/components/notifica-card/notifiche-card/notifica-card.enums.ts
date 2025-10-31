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
 * Tipi di visualizzazione della card con la singola notifica
 */
export enum NotificaCardTypeView {
  HOME = 'home',
  LIST = 'list',
  DETAIL = 'detail',
  SIDEBAR = 'sidebar',
}

/**
 * Enum che definisce la class CSS associata ad una specifica view del componente.
 */
export enum CssCardNotifica {
  home = 'card-of-home',
  list = 'card-of-list',
  detail = 'card-of-detail',
  sidebar = 'card-of-sidebar',
  daLeggere = 'card-to-read',
  ultimaCard = 'card-of-sidebar-last',
}
