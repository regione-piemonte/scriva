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
import { FormioVincaShareService } from './formio-vinca-share.service';
import { FormioUtilitiesService } from '../formio-utilities.service';

/**
 * Servizio di utility per il componente scriva che gestisce i formio applicativi
 */
@Injectable({ providedIn: 'root' })
export class FormioVincaAppService extends FormioVincaShareService {
  /** String costante che definisce la KEY WORD applicativa specifica per gestire in maniera automatica le logiche per il formio: VINCA APP */
  private KEY_FORMIO_VINCA_APP = 'VINCA_APP';

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
  setupFormioVincaAppData(customForm: FormioForm, formioData: any): any {
    // Verifico l'input
    if (!customForm || !formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Cerco all'interno della struttura di formio, la specifica chiave identificativa
    const checkVincaApp = this.existVincaAppComponent(customForm);
    // Verifico sto lavorando su un formio VINCA APP
    if (checkVincaApp) {
      // E' il formio specifico, lancio le funzioni di setup specifiche
      formioData = this.setupJsCategorieProgettuali(formioData);
    }

    // Ritorno l'oggetto
    return formioData;
  }

  /**
   * Funzione che verifica se all'interno dell'alberatura di un formio esiste la chiave per VINCA APP.
   * @param formioForm FormioForm contenente le configurazioni del formio.
   */
  private existVincaAppComponent(formioForm: FormioForm): boolean {
    // Verifico l'input
    if (!formioForm) {
      // Non esiste la configurazione
      return false;
    }

    // Variabile di comodo per la chiave di ricerca
    const key = this.KEY_FORMIO_VINCA_APP;
    // Richiamo la funzione per il recupero del componente per chiave
    const vincaAppComp = this.getFormioComponent(formioForm, key);

    // Ritorno se è presente un componente con la chiave specifica
    return vincaAppComp != undefined;
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
  formioVincaAppHandler(formioData: any): any {
    // Verifico l'input
    if (!formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Definisco la chiave che può contenere la logica di gestione del data di formio in maniera custom
    const formioCustomFn = this.KEY_FORMIO_VINCA_APP;
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

    // Lancio la sanitizzazione delle informazioni per JS_CATEGORIE_PROGETTUALI
    data = this.handleJsCategorieProgettuali(data);

    // Ritorno l'oggetto manipolato
    return data;
  }
}
