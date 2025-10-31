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
import { OggettiSearchConst } from '../../components/oggetti-search/utilities/oggetti-search.consts';
import { ConfigElement } from '../../models';

// #region "FUNZIONI CONDIVISE"

/**
 * Funzione di comodo che converte una proprietà stringa o boolean in boolean.
 * Le proprietà di configurazione sono a doppio tipo, la funzione forza alla condizione boolean.
 * @param proprieta string | boolean da convertire.
 * @returns boolean con il risultato della tipizzazione.
 */
const configurazioneToBoolean = (proprieta: string | boolean): boolean => {
  // Verifico che esista la configurazione
  if (proprieta == undefined) {
    // Manca la configurazione
    return false;
    // #
  }

  // Verifico il tipo della proprietà
  if (typeof proprieta === 'boolean') {
    // E' un boolean, lo ritorno direttamente
    return proprieta;
    // #
  } else if (typeof proprieta === 'string') {
    // Definisco la visibilità con alcune condizioni specifiche
    const isPStringBool = proprieta === 'true';
    const isPStringNumber = proprieta === '1';
    // Se una delle due condizioni è verà, il campo è visibile
    return isPStringBool || isPStringNumber;
    // #
  }

  // La condizione non è vera o ha un valore non gestito
  return false;
  // #
};

/**
 * Funzione che verifica e gestisce l'informazione inerente alla visibilità di un criterio.
 * @param configurazione ConfigElement con le informazioni per la verifica.
 * @returns boolean con il risultato della verifica.
 */
const isCriterioRicercaVisibile = (configurazione: ConfigElement): boolean => {
  // Verifico l'input
  if (!configurazione) {
    // Manca la configurazione
    return false;
    // #
  }

  // Recupero il flag di visibilità
  const visibile: string | boolean = configurazione.visibile;
  // Verifico con la tipizzazione e ritorno il valore boolean
  return configurazioneToBoolean(visibile);
  // #
};

// #endregion "FUNZIONI CONDIVISE"

// #region "VISUALIZZA CAMPO RICERCA"

@Pipe({ name: 'mostraCriterioRicerca' })
export class MostraCriterioRicercaOggettiSearchPipe implements PipeTransform {
  /**
   * Costruttore del Pipe.
   */
  constructor() {}

  /**
   * Funzione che verifica le configurazioni per la visualizzazione di un criterio di ricerca.
   * @param configurazione ConfigElement con le configurazioni per il criterio di ricerca.
   * @returns boolean con la condizione di visualizzazione.
   */
  transform(configurazione: ConfigElement): boolean {
    // Lancio la funzione di verifica puntuale
    return isCriterioRicercaVisibile(configurazione);
    // #
  }
}

// #endregion "VISUALIZZA CAMPO RICERCA"

// #region "VISUALIZZA CAMPO RICERCA GENERICO"

@Pipe({ name: 'mostraCriterioRicercaGenerico' })
export class MostraCriterioRicercaGenericoOggettiSearchPipe
  implements PipeTransform
{
  /** OggettiSearchConst con le constanti usate per il componente. */
  OGG_SEARCH_CONST = new OggettiSearchConst();

  /**
   * Costruttore del Pipe.
   */
  constructor() {}

  /**
   * Funzione che verifica le configurazioni per la visualizzazione di un criterio di ricerca.
   * La funzione va ad escludere una lista specifica di campi che vengono gestiti in maniera specifica sul componente.
   * @param configurazione ConfigElement con le configurazioni per il criterio di ricerca.
   * @param criterio string con il nome del criterio di ricerca.
   * @returns boolean con la condizione di visualizzazione.
   */
  transform(configurazione: ConfigElement, criterio: string): boolean {
    // Lancio la funzione di verifica puntuale
    const isCRVisibile: boolean = isCriterioRicercaVisibile(configurazione);

    // Definisco la lista dei criteri di ricerca specifici da escludere
    const crSpecifici: string[] = [
      this.OGG_SEARCH_CONST.PROVINCIA,
      this.OGG_SEARCH_CONST.ID_COMUNE,
      this.OGG_SEARCH_CONST.CF_SOGGETTO,
    ];
    // Verifico se la configurazione è uno dei criteri specifici
    const isCriterioSpecifico: boolean = crSpecifici.some(
      (crSpecifico: string) => {
        // Verifico il nome del criterio
        return crSpecifico === criterio;
        // #
      }
    );

    // La condizione di visibilità vale per la configurazione e se il criterio non è specifico
    return isCRVisibile && !isCriterioSpecifico;
    // #
  }
}

// #endregion "VISUALIZZA CAMPO RICERCA GENERICO"

// #region "LABEL CAMPO DI RICERCA"

@Pipe({ name: 'labelCriterioRicerca' })
export class LabelCriterioRicercaOggettiSearchPipe implements PipeTransform {
  /**
   * Costruttore del Pipe.
   */
  constructor() {}

  /**
   * Funzione che verifica le configurazioni per la visualizzazione della label del criterio di ricerca.
   * Se la configurazione prevede il campo come "obbligatorio", davanti alla label sarà aggiunto un "*".
   * @param configurazione ConfigElement con le configurazioni per il criterio di ricerca.
   * @returns string con la condizione di visualizzazione.
   */
  transform(configurazione: ConfigElement): string {
    // Verifico l'input
    if (!configurazione) {
      // Manca la configurazione
      return '';
      // #
    }

    // Recupero dalla configurazione le informazioni di obbligatorietà e la label
    const label: string = configurazione.label ?? '';
    let obbligatorio: boolean;
    obbligatorio = configurazioneToBoolean(configurazione.obbligatorio);

    // Costruisco la label completa
    const labelCR: string = `${obbligatorio ? '*' : ''} ${label}`;
    // Ritorno la label generata
    return labelCR;
    // #
  }
}

// #endregion "VISUALIZZA CAMPO RICERCA GENERICO"
