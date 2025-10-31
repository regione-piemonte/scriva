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
import {
  DomSanitizer,
  SafeHtml,
  SafeResourceUrl,
  SafeScript,
  SafeStyle,
  SafeUrl,
} from '@angular/platform-browser';
import {
  SanitizerTypes,
  ScrivaCssHandlerTypes,
} from '../../enums/scriva-utilities/scriva-utilities.enums';
import { scrivaCssHandler } from '../../services/scriva-utilities/scriva-utilities.functions';
import { ScrivaUtilitiesService } from '../../services/scriva-utilities/scriva-utilities.service';
import { ScrivaCss } from '../../types/scriva-utilities/scriva-utilities.types';

/**
 * Pipe che esegue una funzione in input per gestire diverse situazioni.
 */
@Pipe({ name: 'scrivaExecute' })
export class ScrivaExecutePipe implements PipeTransform {
  /**
   * Costruttore del Pipe.
   */
  constructor(private _scrivaUtilities: ScrivaUtilitiesService) {}

  /**
   * Funzione che esegue la funzione passata come parametro.
   * @param f (v?: any, ...p: any[]) => any da eseguire.
   * @param v any con il valore da passare come parametro alla funzione.
   * @param p Array di any che definisce n possibili parametri passati alla funzione.
   * @returns any come risultato dell'operazione.
   */
  transform(
    f: (v?: any, ...p: any[]) => any,
    v: any,
    ...p: any[]
  ): any | undefined {
    // Richiamo il servizio di utility per l'esecuzione della funzione
    return this._scrivaUtilities.scrivaExecute(f, v, ...p);
  }
}

/**
 * Pipe dedicata alla gestione dell'output per la gestione delle direttive NgClass e NgStyle.
 */
@Pipe({ name: 'scrivaCssHandler' })
export class ScrivaCssHandlerPipe implements PipeTransform {
  /**
   * Costruttore del Pipe.
   */
  constructor(private _scrivaUtilities: ScrivaUtilitiesService) {}

  /**
   * Funzione che ritorna la classe di stile o le configurazioni di stile a seconda dell'input.
   * @param cssConfig string o any con i dati del css.
   * @param cssType ScrivaCssHandlerTypes il tipo di stile da ritonare.
   * @returns string o oggetto contenuto in colConfig.
   */
  transform(
    cssConfig: string | any,
    cssType: ScrivaCssHandlerTypes
  ): string | any {
    // Verifico che gli input siano definiti
    if (!cssType) {
      throw new Error('ScrivaCssHandlerPipe - No cssType defined');
    }

    // Verifico e ritorno lo stile
    return this._scrivaUtilities.scrivaCssHandler(cssType, cssConfig);
  }
}

/**
 * Pipe dedicata alla gestione dell'output per la gestione delle direttive NgStyle, avendo in input differenti oggetti di configurazione da mergiare.
 */
@Pipe({ name: 'scrivaStylesHandler' })
export class ScrivaStylesHandlerPipe implements PipeTransform {
  /**
   * Costruttore del Pipe.
   */
  constructor() {}

  /**
   * Funzione che ritorna la classe di stile o le configurazioni di stile a seconda dell'input.
   * Più la configurazione ha un indice posizionale maggiore all'interno di otherConfigs, maggiore sarà la priorità dello stile.
   * @param firstConfig object che definisce il primo oggetto da mergiare.
   * @param otherConfigs Spread operator object con gli oggetti da mergiare.
   * @returns object con le proprietà mergiate.
   */
  transform(firstConfig: object, ...otherConfigs: object[]): object {
    // Verifico l'input
    firstConfig = firstConfig ?? {};
    otherConfigs = otherConfigs ?? [];
    // Creo un array di oggetti per il merge
    let allConfigs: ScrivaCss[] = [];
    // Aggiugo la prima configurazione
    allConfigs.push(firstConfig);
    // Aggiungo le altre configurazioni
    allConfigs = [...allConfigs, ...otherConfigs];

    // Definisco il contenitore per gli stili
    let styles: object = {};
    // Ciclo le configurazioni e lancio la conversione
    allConfigs = allConfigs.map((c: ScrivaCss) => {
      // Lancio la gestione dei dati, per sicurezza
      return scrivaCssHandler(ScrivaCssHandlerTypes.style, c);
    });
    // Mergio tutti gli stili insieme
    allConfigs.forEach((c: object) => {
      // Unisco gli stili
      styles = { ...styles, ...c };
    });
    // Ritorno l'oggetto mergiato
    return styles;
  }
}

/**
 * Pipe impiegata per la sanitizzazione dell'HTML.
 */
@Pipe({ name: 'sanitize' })
export class ScrivaSanitizePipe implements PipeTransform {
  /**
   * Pipe Constructor
   * @param _sanitizer: DomSanitezer
   */
  constructor(protected _sanitizer: DomSanitizer) {}

  /**
   * La funzione esegue la sanitizzazione dell'input, sulla base della configurazione.
   * @param value string con l'informazione da sanitizzare.
   * @param type SanitizerTypes con il tipo di sanitizzazione da effettuare.
   * @returns SafeHtml | SafeStyle | SafeScript | SafeUrl | SafeResourceUrl con l'input sanitizzato.
   */
  transform(
    value: string,
    type: SanitizerTypes
  ): SafeHtml | SafeStyle | SafeScript | SafeUrl | SafeResourceUrl {
    switch (type) {
      case SanitizerTypes.html:
        return this._sanitizer.bypassSecurityTrustHtml(value);
      case SanitizerTypes.style:
        return this._sanitizer.bypassSecurityTrustStyle(value);
      case SanitizerTypes.script:
        return this._sanitizer.bypassSecurityTrustScript(value);
      case SanitizerTypes.url:
        return this._sanitizer.bypassSecurityTrustUrl(value);
      case SanitizerTypes.resourceUrl:
        return this._sanitizer.bypassSecurityTrustResourceUrl(value);
      default:
        return this._sanitizer.bypassSecurityTrustHtml(value);
    }
  }
}
/**
 * Pipe impiegata per il corretto utilizzo condizionale per gestire la struttura [attr.disabled] dei campi di input.
 */
@Pipe({ name: 'attrDisabled' })
export class ScrivaAttrDisablePipe implements PipeTransform {
  /**
   * Pipe Constructor
   */
  constructor() {}

  /**
   * La funzione esegue verifica la condizione in input e ritorna un valore compatibile per la gestione della disabilitazione.
   * [attr.disabled] necessita di uno specifico ritorno per poter funzionare: flag ? '' : null.
   * @param condizione boolean con la condizione.
   * @param condizioneGlobale boolean con una condizione globale come flag di gestione extra. Default: false.
   * @returns '' | null con un valore compatibile per attivare le logiche di disabilitazione del campo.
   */
  transform(condizione: boolean, condizioneGlobale: boolean = false): '' | null {
    // Verifico e ritorno un valore compatibile
    return condizione || condizioneGlobale ? '' : null;
  }
}

/**
 * Pipe che gestisce e formatta le informazioni per ottenere un title sulla cella di una tabella con il formato: {{NOME COLONNA}}: {{VALORE CAMPO}}
 */
@Pipe({ name: 'scrivaTableTitle' })
export class ScrivaTableTitlePipe implements PipeTransform {
  /**
   * Pipe Constructor
   */
  constructor() {}

  /**
   * Funzione che compone le informazioni per ottenere un output desiderato dall'utente.
   * @param colonna string con la descrizione di testata della colonna.
   * @param valoreCampo any con il valore del campo da visualizzare.
   * @returns string con le informazioni da visualizzare per il title.
   */
  transform(colonna: string, valoreCampo: any): string {
    // Verifico l'input e gestisco un possibile default
    colonna = colonna ?? '';
    valoreCampo = valoreCampo ?? '';
    // Concateno e ritorno la stringa
    return `${colonna}: ${valoreCampo}`;
  }
}
