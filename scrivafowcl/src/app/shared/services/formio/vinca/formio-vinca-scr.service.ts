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
import { FormioForm } from 'angular-formio/formio.common';
import { cloneDeep } from 'lodash';
import { FormioUtilitiesService } from '../formio-utilities.service';
import { FormioVincaShareService } from './formio-vinca-share.service';

/**
 * Interfaccia di supporto che definisce parzialmente la struttura data per la gestione delle VINCA SCR.
 */
interface IVincaFormioScr {
  [key: string]: any;
  JS_ALTRE_AUTORIZZAZIONI: IJsAltreAutorizzazioni;
}

/**
 * Interfaccia di supporto che definisce la struttura data per la gestione delle VINCA SCR per: JS_ALTRE_AUTORIZZAZIONI.
 */
interface IJsAltreAutorizzazioni {
  elenco_altre_autorizzazioni: IAltraAutorizzazione[];
}

/**
 * Interfaccia di supporto che definisce la struttura data per la gestione delle VINCA SCR per: JS_ALTRE_AUTORIZZAZIONI.elenco_altre_autorizzazioni.
 */
interface IAltraAutorizzazione {
  des_altra_categoria: string;
  textField?: string;
}

/**
 * Servizio di utility per il componente scriva che gestisce i formio applicativi
 */
@Injectable({ providedIn: 'root' })
export class FormioVincaScrService extends FormioVincaShareService {
  /** String costante che definisce la KEY WORD applicativa specifica per gestire in maniera automatica le logiche per il formio: VINCA SCR */
  private KEY_FORMIO_VINCA_SCR = 'VINCA_SCR';

  /**
   * Costruttore.
   */
  constructor(formioUtilities: FormioUtilitiesService) {
    // Super del servizio esteso
    super(formioUtilities);
  }

  /**
   * ###########################################################
   * GESTIONE DELLE FUNZIONI CUSTOM PER IL SETUP DATI DEL FORMIO
   * ###########################################################
   */

  /**
   * Funzionalità di supporto che gestisce le possibili gestioni custom per il formio, a seconda del tipo di formio stesso.
   * Il formio può necessitare di specifiche configurazioni di setup, partendo dalla struttura dei componenti del formio stesso.
   * @param customForm FormioForm contenente le configurazioni del formio.
   * @param formioData any contenente l'oggetto "data" che verrà passato come pre-configurazione del formio.
   * @returns any contenente l'oggetto manipolato secondo le configurazioni del formio data.
   */
  setupFormioVincaScrData(customForm: FormioForm, formioData: any): any {
    // Verifico l'input
    if (!customForm || !formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Cerco all'interno della struttura di formio, la specifica chiave identificativa
    const checkVincaScr = this.existVincaScrComponent(customForm);
    // Verifico sto lavorando su un formio VINCA SCR
    if (checkVincaScr) {
      // E' il formio specifico, lancio le funzioni di setup specifiche
      formioData = this.setupJsCategorieProgettuali(formioData);
    }

    // Ritorno l'oggetto
    return formioData;
  }

  /**
   * Funzione che verifica se all'interno dell'alberatura di un formio esiste la chiave per VINCA SCR.
   * @param formioForm FormioForm contenente le configurazioni del formio.
   */
  private existVincaScrComponent(formioForm: FormioForm): boolean {
    // Verifico l'input
    if (!formioForm) {
      // Non esiste la configurazione
      return false;
    }

    // Variabile di comodo per la chiave di ricerca
    const key = this.KEY_FORMIO_VINCA_SCR;
    // Richiamo la funzione per il recupero del componente per chiave
    const vincaScrComp = this.getFormioComponent(formioForm, key);

    // Ritorno se è presente un componente con la chiave specifica
    return vincaScrComp != undefined;
  }

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
  formioVincaScrHandler(formioData: any): any {
    // Verifico l'input
    if (!formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Definisco la chiave che può contenere la logica di gestione del data di formio in maniera custom
    const formioCustomFn = this.KEY_FORMIO_VINCA_SCR;
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
   * Funzione generica che gestisce le informazioni del formio eseguendo tutte le logiche necessarie per lo specifico formio: VINCA SCR
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

    // Lancio la sanitizzazione delle informazioni per JS_CATEGORIE_PROGETTUALI
    data = this.handleJsCategorieProgettuali(data);
    // Lancio la sanitizzazione delle informazioni per JS_ALTRE_AUTORIZZAZIONI.elenco_altre_autorizzazioni[i].des_altra_categoria
    data = this.handleJsAltreAutorizzazioni(data);

    // Ritorno l'oggetto manipolato
    return data;
  }

  /**
   * Funzione che gestisce le informazioni del campo JS_ALTRE_AUTORIZZAZIONI.elenco_altre_autorizzazioni[i].des_altra_categoria.
   * Se esiste un elenco JS_ALTRE_AUTORIZZAZIONI.elenco_altre_autorizzazioni vado a pulire, per i sotto-oggetti con la proprietà "des_altra_categoria".
   * La modifica avviene per gestire la compatibilita' con il DB.
   * @param formioData IVincaFormioScr contenente l'oggetto "data" restituito come risultato del submit del formio.
   * @returns any contenente l'oggetto manipolato secondo le configurazioni del formio data.
   */
  protected handleJsAltreAutorizzazioni(formioData: IVincaFormioScr): any {
    // Verifico l'input
    if (!formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Creo una copia dell'oggetto per gestire in maniera puntuale le modifiche
    let data = cloneDeep(formioData);

    // Recupero come dato di comodo l'elenco delle autorizzazioni
    let elencoAut = data.JS_ALTRE_AUTORIZZAZIONI.elenco_altre_autorizzazioni;
    // Aggiorno l'oggetto JS_CATEGORIE_PROGETTUALI secondo le logiche per i dati DB
    const elencoAutDB = elencoAut
      .filter((aut: IAltraAutorizzazione) => {
        // Rimuovo tutti gli oggetti undefined o senza descrizione
        const existAut = aut != undefined;
        const existAutDes = existAut && aut.des_altra_categoria != undefined;
        const definedAutDes = existAutDes && aut.des_altra_categoria != '';
        // Ritorno il check
        return definedAutDes;
      })
      .map((aut: IAltraAutorizzazione) => {
        // Definisco un oggetto per il salvataggio su DB
        const autDB: IAltraAutorizzazione = {
          des_altra_categoria: aut.des_altra_categoria,
        };
        // Ritorno l'oggetto formattato per il DB
        return autDB;
      });

    // Aggiorno l'oggetto del formio
    data.JS_ALTRE_AUTORIZZAZIONI.elenco_altre_autorizzazioni = elencoAutDB;

    // Ritorno il dato aggiornato
    return data;
  }
}
