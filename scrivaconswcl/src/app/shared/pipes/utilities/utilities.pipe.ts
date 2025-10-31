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

@Pipe({ name: 'spezzaParoleHTML' })
export class SpezzaParoleHTMLPipe implements PipeTransform {
  /**
   * Costruttore
   */
  constructor() {}

  /**
   * Funzione che spezza una serie di parole su più linee, usando il tag HTML <br>
   * @param label string con la frase da spezzare.
   * @returns string con la gestione della frase.
   */
  transform(label: string): string {
    // Verifico l'input
    label = label ?? '';

    // Tento di generare una label per la tabella
    try {
      // Aggiungio un break line ad ogni spazio bianco
      return label?.trim()?.replace(/\s/g, '<br>');
      // #
    } catch (e) {
      // Defaul
      return label;
    }
  }
}
