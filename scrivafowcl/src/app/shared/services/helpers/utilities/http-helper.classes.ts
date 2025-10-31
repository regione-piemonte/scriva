/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ScrivaSortTypes } from './http-helper.enums';
import {
  IRicercaPaginataResponse,
  IScrivaTablePagination,
  IRicercaIncrementaleResponse,
} from './http-helper.interfaces';


/**
 * Classe che definisce le configurazioni DATI utilizzate dal componente risca-table per la configurazione della paginazione.
 */
export class ScrivaPagination {
  /** Number che definisce il totale di elementi. */
  total?: number;
  /** Boolean che definisce se visualizzare il totale degli elementi. */
  showTotal?: boolean;
  /** String che definisce la label parlante per il totale degli elementi. */
  label?: string;
  /** Number che definisce il numero massimo di elementi da visualizzare per pagina. */
  elementsForPage?: number;
  /** Pagina corrente da mostrare. Si parte da 1. */
  currentPage?: number;
  /** Campo di ordinamento dei record */
  sortBy?: string;
  /** Verso di ordinamento dei record */
  sortDirection?: ScrivaSortTypes;
  /** Numero massimo di pagine che si possono visualizzare */
  maxPages?: number;

  constructor(c?: IScrivaTablePagination) {
    this.total = c?.total ?? 0;
    this.showTotal = c?.showTotal ?? true;
    this.label = c?.label ?? 'Risultati di ricerca';
    this.elementsForPage = c?.elementsForPage ?? 10;
    this.currentPage = c?.currentPage ?? 1;
    this.sortBy = c?.sortBy;
    this.sortDirection = c?.sortDirection;
    this.maxPages = c?.maxPages ?? 10;
  }
}

/**
 * Oggetto che rappresenta i dati e la paginazione insieme
 */
export class RicercaPaginataResponse<T> {
  sources: T;
  paging: ScrivaPagination;

  constructor(c?: IRicercaPaginataResponse<T>) {
    this.sources = c?.sources;
    this.paging = c?.paging;
  }
}

/**
 * Oggetto che rappresenta i dati e la paginazione insieme, ma del tipo che segnala solo che ci sono altri elementi.
 */
export class RicercaIncrementaleResponse<T> {
  sources: T;
  hasMoreItems: boolean;

  constructor(c?: IRicercaIncrementaleResponse<T>) {
    this.sources = c?.sources;
    this.hasMoreItems = c?.hasMoreItems;
  }
}