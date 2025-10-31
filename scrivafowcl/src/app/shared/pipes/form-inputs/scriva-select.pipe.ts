/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Pipe, PipeTransform } from '@angular/core';

/**
 * Pipe dedicata alla manipolazione dei dati per ottenere un output per la select del componente scriva-select.
 */
@Pipe({ name: 'scrivaOptionValue' })
export class ScrivaOptionValuePipe implements PipeTransform {
  /**
   * Costruttore del Pipe.
   */
  constructor() {}

  /**
   * Funzione che manipola il valore da visualizzare per l'options della select.
   * @param element any contenente l'oggetto iterato dalla select.
   * @param property string che definisce la property da visualizzare.
   * @param customOption Funzione che permette di mappare in maniera custom l'output per l'option. Nullable.
   * @returns string come valore da visualizzare nella option.
   */
  transform(
    element: any,
    property: string,
    customOption?: (v: any) => string
  ): string {
    // Verifico l'input
    if (!element) {
      return '';
    }

    // Verifico se esiste una formattazione custom
    if (customOption) {
      return customOption(element);
    }

    // Verifico se element è un oggetto
    const isElementObj = typeof element === 'object';
    // Verifico se element è un oggetto e property è definita
    if (isElementObj && property) {
      // Ritorno la proprietà dell'oggett
      return element[property];
    }
    // Se element non è un oggetto e property non è definita
    if (!isElementObj && !property) {
      // E' un primitivo, ritorno il suo valore
      return element;
    }

    // Ritorno un errore
    return '[scrivaOptionValue] case unhandled';
  }
}
