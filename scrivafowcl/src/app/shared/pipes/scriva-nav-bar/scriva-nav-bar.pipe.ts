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
import { SNBTab } from '../../components/scriva-nav-bar/utilities/scriva-nav-bar.classes';
import { ISNBTabSelectedCss } from '../../components/scriva-nav-bar/utilities/scriva-nav-bar.interfaces';

/**
 * Pipe dedicata gestione per la classe di stile "selezionata" per la posizione delle tab.
 */
@Pipe({ name: 'snbTabSelected' })
export class ScrivaNavBarTabSelectedPipe implements PipeTransform {
  /**
   * Costruttore del Pipe.
   */
  constructor() {}

  /**
   * Funzione che gestisce verifica la tab selezionata e ne ritorna la classe per l'evidenziazione.
   * @param navTab SNBTab con l'oggetto iterato dall'array che compone la nav bar.
   * @param tabSelected SNBTab con l'oggetto selezionato tra le tab della nav bar.
   * @returns ISNBTabSelectedCss come oggetto contenente la struttura a classi per evidenziare la tab verificata, compatibile con NgClass.
   */
  transform(navTab: SNBTab, tabSelected: SNBTab): ISNBTabSelectedCss {
    // Verifico l'input
    if (!navTab || !tabSelected) {
      // Non ci sono dati, ritorno un oggetto senza dati
      return {};
    }

    // Definisco l'oggetto per la gestione della selezione
    const css: ISNBTabSelectedCss = {
      // Verifico gli id degli oggetti
      selected: navTab.id === tabSelected.id,
    };

    // Ritorno l'oggetto di configurazione
    return css;
  }
}
