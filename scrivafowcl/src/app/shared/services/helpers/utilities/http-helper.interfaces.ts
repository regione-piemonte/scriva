/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ScrivaPagination } from './http-helper.classes';
import { ScrivaOpenFileMethods, ScrivaSortTypes } from './http-helper.enums';

/**
 * Interfaccia impiegata per la classe ScrivaTablePagination come parametro del costrutture.
 */
export interface IScrivaTablePagination {
  total?: number;
  showTotal?: boolean;
  label?: string;
  elementsForPage?: number;
  currentPage?: number;
  sortBy?: string;
  sortDirection?: ScrivaSortTypes;
  maxPages?: number;
}

/**
 * Interfaccia che gestisce la configurazione per la classe RicercaPaginataResponse.
 */
export interface IRicercaPaginataResponse<T> {
  sources: T;
  paging: ScrivaPagination;
}

/**
 * Interfaccia che gestisce la configurazione per la classe RicercaIncrementaleResponse.
 */
export interface IRicercaIncrementaleResponse<T> {
  sources: T;
  hasMoreItems: boolean;
}

/**
 * Interfaccia che gestisce la configurazione per l'apertura di un file secondo possibili configurazioni.
 */
export interface IScrivaOpenFileConfigs {
  fileName: string;
  stream: any;
  extension?: string;
  mimeType?: string;
  convertBase64?: boolean;
  openMethod?: ScrivaOpenFileMethods;
}

/**
 * Intefaccia che mappa le informazioni per la paginazione di request dal BE.
 */
export interface IPaginationBEReq {
  offset: number;
  limit: number;
  sort: string;
}

/**
 * Intefaccia che mappa le informazioni per la paginazione di response dal BE.
 */
export interface IPaginationBERes {
  total_elements?: number;
  page?: number;
  total_pages?: number;
  page_size?: number;
}
