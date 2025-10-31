/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

/**
 * Servizio collegato strettamente alla componente delle scriva-select.
 */
@Injectable({
  providedIn: 'root',
})
export class ScrivaSelectService {
  /** Subject che permette l'emissione dell'evento: refreshData. */
  refreshDOM$ = new Subject<any>();

  /**
   * Costruttore.
   */
  constructor() {}

  /**
   * Funzione impiegata per la gestione del bug grafico che avviene durante il ciclo di vita del componente: scriva-select.
   * In alcuni momenti del ciclo di vita dell'applicazione, le select si resettano graficamente al primo valore della lista.
   * Non vengono però emessi eventi, quindi la struttura dati rimane invariata, causando questo bug.
   */
  refreshDOM() {
    // Emetto l'evento di refresh
    this.refreshDOM$.next(true);
  }
}
