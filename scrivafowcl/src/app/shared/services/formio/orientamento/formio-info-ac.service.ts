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
import { cloneDeep } from 'lodash';

/**
 * Interfaccia di supporto che definisce parzialmente la struttura data per la gestione della INFO AC.
 */
interface IInfoAcFormio {
  [key: string]: any;
  IndTipoCalcoloAC?: string;
  soggetto_ente?: ISoggettoEnte[];
}

/**
 * Interfaccia di supporto che definisce la struttura data per la gestione delle INFO AC per: soggetto_ente.
 */
interface ISoggettoEnte {
  check: boolean;
  obbligatorio: boolean;
  cod_dichiarazione: string;
  des_dichiarazione: string;
}

/**
 * Servizio di utility per il componente scriva che gestisce i formio applicativi
 */
@Injectable({ providedIn: 'root' })
export class FormioInfoACService {
  /** String costante che definisce la KEY WORD applicativa specifica per gestire in maniera automatica le logiche per il formio: INFO_AC */
  private KEY_FORMIO_INFO_AC = 'INFO_AC';
  /** String costante che definisce il codice specifico: "AC3". Verrà utilizzato per la gestione soggetto_ente. */
  private COD_AC3 = 'AC3';
  /** String costante che definisce il codice dichiarazione soggetto ente specifico: "dchr_soggetto_ente". Verrà utilizzato per la gestione soggetto_ente. */
  private COD_DICHIARAZIONE_SOGG_ENTE = 'dchr_soggetto_ente';

  /**
   * Costruttore.
   */
  constructor() {}

  /**
   * ##########################################################################
   * GESTIONE DELLE FUNZIONI CUSTOM PER MANIPOLAZIONE DEL FORM SUBMIT DI FORMIO
   * ##########################################################################
   */

  /**
   * Funzionalità di supporto che gestisce le possibili gestioni custom per il formio.
   * @param formioData any contenente l'oggetto "data" restituito come risultato del submit del formio.
   * @returns any contenente l'oggetto manipolato secondo le configurazioni del formio data.
   */
  formioInfoACHandler(formioData: any): any {
    // Verifico l'input
    if (!formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Definisco la chiave che può contenere la logica di gestione del data di formio in maniera custom
    const formioCustomFn = this.KEY_FORMIO_INFO_AC;
    // Variabile di comodo
    let data = formioData;

    // Verifico se esiste la proprietà con le funzioni custom
    if (data[formioCustomFn]) {
      // Lancio la funzione che esegue tutte le manipolazioni del dato per questo specifico formio
      data = this.handleFormio(data);
      // Vado a cancellare dall'oggetto finale la proprietà di gestione custom
      delete data[formioCustomFn];
    }

    // Ritorno l'oggetto
    return data;
  }

  /**
   * Funzione generica che gestisce le informazioni del formio eseguendo tutte le logiche necessarie per lo specifico formio: VINCA APP
   * @param formioData any contenente l'oggetto "data" restituito come risultato del submit del formio.
   * @returns any contenente l'oggetto manipolato secondo le configurazioni del formio data.
   */
  handleFormio(formioData: any): any {
    // Verifico l'input
    if (!formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Variabile di comodo
    let data = cloneDeep(formioData);

    // Lancio la gestione dati per il salvataggio dei dati relativi all'assegnazione dell'autorità competente (AC)
    data = this.handleAutoritaCompetente(data);

    // Ritorno l'oggetto manipolato
    return data;
  }

  private handleAutoritaCompetente(formioData: any): any {
    // Verifico l'input
    if (!formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Variabile di comodo
    let data: IInfoAcFormio = cloneDeep(formioData);

    // Recupero il codice IndTipoCalcolaAC per la verifica
    const indTipoCalcoloAC = data.IndTipoCalcoloAC;
    // Creo un array statico con un oggetto e identifico il valore di una proprietà tramite indTipoCalcolaAC
    data.soggetto_ente = [
      {
        check: indTipoCalcoloAC === this.COD_AC3,
        obbligatorio: true,
        cod_dichiarazione: this.COD_DICHIARAZIONE_SOGG_ENTE,
        // TODO: @Jessica @Marco @Antornio => Sarebbe meglio che questa informazione sia dinamica. Si può censire una proprietà a parte e fare il copia incolla della descrizione, che si può recuperare e poi assegnare.
        des_dichiarazione:
          'Si presenta l’istanza per conto del soggetto gestore del sito natura 2000 in cui ricade il Progetto/Intervento/Attività',
      },
    ];

    // Verifico se il codice non è AC3 per IndTipoCalcoloAC
    if (indTipoCalcoloAC !== this.COD_AC3) {
      // Il codice non è stato settato, lo imposto a null
      data.IndTipoCalcoloAC = null;
    }

    // Ritorno l'oggetto del formio aggiornato
    return data;
  }
}
