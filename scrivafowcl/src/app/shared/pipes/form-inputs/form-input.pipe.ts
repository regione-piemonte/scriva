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
import { FormControl, FormGroup } from '@angular/forms';
import { IMappaErroriFormECodici } from '../../components/form-inputs/form-input/utilities/form-input.interfaces';
import { ScrivaUtilitiesService } from '../../services/scriva-utilities/scriva-utilities.service';
/**
 * Pipe dedicata alla gestione degli errori sulle form.
 * Fornito un FormGroup o un FormControl e una mappatura degli errori, la pipe restituirà un messaggio d'errore.
 * Il messaggio d'errore avrà la priorità definita come posizionale all'interno dell'array della mappatura.
 */
@Pipe({ name: 'scrivaFormError' })
export class ScrivaFormErrorPipe implements PipeTransform {
  /**
   * Costruttore del Pipe.
   * @param _scrivaUtilities ScrivaUtilityService
   */
  constructor(private _scrivaUtilities: ScrivaUtilitiesService) {}

  /**
   * Funzione di gestione, verifica e recupero degli errori per un formControl.
   * @param form FormGroup | FormControl per la verifica e recupero degli errori.
   * @param mappatura Array di MappaErroriFormECodici contenente la mappatura degli errori.
   * @param forceRefresh Parametro any usato per forzare il refresh della pipe. E' un workaround per forzare l'aggiornamento della pipe.
   * @returns string che contiene il primo messaggio d'errore da far visualizzare all'utente.
   */
  transform(
    form: FormGroup | FormControl,
    mappatura: IMappaErroriFormECodici[],
    forceRefresh?: any
  ): string | undefined {
    // Verifico che gli input siano definiti
    if (!form || !mappatura || mappatura.length === 0) {
      return;
    }

    // Recupero tutti i messaggi d'errore per il form control
    const messaggiErrore = this._scrivaUtilities.getErrorMessagesFromForm(
      form,
      mappatura
    );

    // Verifico se non ci sono errori
    if (!messaggiErrore && messaggiErrore.length === 0) {
      return;
    }

    // Ci sono messaggi d'errore, recupero il primo e lo ritorno
    return messaggiErrore[0];
  }
}

/**
 * Pipe dedicata alla manipolazione dei dati per ottenere un output leggibile per il componente scriva-typeahead.
 */
@Pipe({ name: 'scrivaTypeaheadMap' })
export class ScrivaTypeaheadMapPipe implements PipeTransform {
  /**
   * Costruttore del Pipe.
   */
  constructor() {}

  /**
   * Funzione che manipola l'output da visualizzare nella input del componente scriva-typeahead.
   * @param popupTip any contenente l'oggetto selezionato dall'utente nel popup dei suggerimenti.
   * @param mapping Funzione che definisce le logiche per il mapping dell'oggetto 'popupTip'.
   * @returns string come valore da visualizzare nella input.
   */
  transform(popupTip: any, mapping: (v: any) => string): string {
    // Verifico che gli input siano definiti
    if (!popupTip || !mapping) {
      return '';
    }

    // Eseguo la funzione passando come parametro l'oggetto popupTip
    return mapping(popupTip);
  }
}

/**
 * Pipe dedicata alla verifica dei dati per la gestione della colorazione degli elementi della lista secondo la validazione tramite data_fine_validita.
 */
@Pipe({ name: 'scrivaTypeaheadDataValidita' })
export class ScrivaTypeaheadDataValiditaPipe implements PipeTransform {
  /**
   * Costruttore del Pipe.
   */
  constructor(private _scrivaUtilities: ScrivaUtilitiesService) {}

  /**
   * Funzione che restituisce una classe CSS per la gestione degli elementi con data fine validità scaduta.
   * @param e any che definisce l'oggetto selezionato della lista.
   * @returns string come classe css d'assegnare all'elemento della lista.
   */
  transform(e: any): string {
    // Definisco una costante con il nome dalla classe di stile
    const cssClass = 'dropdown-element';

    // Verifico che gli input siano definiti
    if (!e) {
      return '';
    }

    // Verifico la data fine validità mediante funzione di utility
    if (!this._scrivaUtilities.isDataValiditaAttiva(e)) {
      // Ritorno la classe di stile
      return cssClass;
      // #
    } else {
      // Nessuna classe di stile
      return '';
    }
  }
}
