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
import {
  NgbModal,
  NgbModalOptions,
  NgbModalRef,
} from '@ng-bootstrap/ng-bootstrap';
import { ScrivaUtilitiesService } from '../scriva-utilities/scriva-utilities.service';
import { ApriModalConfigs } from './utilities/scriva-modal.classes';
import {
  ICallbackDataModal,
  ICheckCallbacksDataModal,
} from './utilities/scriva-modal.interfaces';

/**
 * Servizio di utility per la gestione delle modali scriva.
 */
@Injectable({ providedIn: 'root' })
export class ScrivaModalService {
  /**
   * Costruttore
   */
  constructor(
    private _ngbModal: NgbModal,
    private _scrivaUtilities: ScrivaUtilitiesService
  ) {}

  /**
   * #############################
   * GESTIONE MODALI CONFIGURABILI
   * #############################
   */

  /**
   * Funzione che apre un modale basandosi sulla configurazione in input.
   * @param config ApriModalConfigs contenente la configurazione del modale.
   */
  apriModal(config: ApriModalConfigs) {
    // Definisco l'oggetto di configurazione per la modal di bootstrap
    const options: NgbModalOptions = config.options;
    // Definisco l'istanza del componente
    const component = config.component;
    // Definizione dell'istanza di apertura del modale
    const modal = this._ngbModal.open(component, options);

    // Gestisco i paramtri da passare alla modale
    this.generateModalParams(modal, config.params);
    // Registro le funzioni di callback alla confirm (fullyfied) e alla close (reject)
    modal.result.then(
      (confirmData: any) => {
        // Richiamo la funzione di gestione della confirm
        this.scrivaConfirmCallback(config, confirmData);
      },
      (closeData: any) => {
        // Richiamo la funzione di gestione della reject
        this.scrivaRejectCallback(config, closeData);
      }
    );
  }

  /**
   * ############################################
   * FUNZIONI DI GESTIONE PER I DATI DELLA MODALE
   * ############################################
   */

  /**
   * Funzione di supporto che genera i parametri da passare alla modale.
   * @param modal NgbModalRef con l'istanza della modale.
   * @param params any con l'insieme dei parametri da passare come @Input() alla modale.
   */
  generateModalParams(modal: NgbModalRef, params: any): any {
    // Itero le proprietà dai parametri
    for (let [input, data] of Object.entries(params || {})) {
      // Definisco i parametri da passare alla modale per @Input()
      modal.componentInstance[input] = data;
    }
  }

  /**
   * Funzione di supporto che gestisce la callback di confirm per la modale.
   * La funzione è fortemente customizzata per le logiche applicative.
   * @param config ApriModalConfigs con la configurazione della modale.
   * @param confirmData any con il risultato ritornato dalla modale.
   */
  scrivaConfirmCallback(config: ApriModalConfigs, confirmData?: any) {
    // Controllo se esiste una funzione di callback per la confirm
    const { confirm } = this.checkCallbacksDataModal(config.callbacks);
    // Richiamo la funzione di callback
    if (confirm) {
      // Richiamo la funzione di callback per la confirm
      config.callbacks.onConfirm(confirmData);
      // #
    }
  }

  /**
   * Funzione di supporto che gestisce la callback di reject per la modale.
   * La funzione è fortemente customizzata per le logiche applicative.
   * @param config ApriModalConfigs con la configurazione della modale.
   * @param rejectData any con il risultato ritornato dalla modale.
   */
  scrivaRejectCallback(config: ApriModalConfigs, rejectData?: any) {
    // Controllo se esiste una funzione di callback per la cancel
    const { cancel, close } = this.checkCallbacksDataModal(config.callbacks);
    // Richiamo la funzione di callback
    if (close && rejectData) {
      // Richiamo la funzione di callback per la close
      config.callbacks.onClose(rejectData);
      // #
    } else if (cancel) {
      // Richiamo la funzione di callback per la cancel
      config.callbacks.onCancel();
      // #
    }
  }

  /**
   * #####################################
   * FUNZIONI DI UTILITY DI CONTROLLO DATI
   * #####################################
   */

  /**
   * Funzione che verifica l'esistenza delle callback dentro l'oggetto CallbackDataModal.
   * @param callbacks CallbackDataModal contenente le configurazioni delle callback di success e/o di error.
   * @returns ICheckCallbacksDataModal che definisce lo stato di esistenza delle callbacks.
   */
  checkCallbacksDataModal(
    callbacks?: ICallbackDataModal
  ): ICheckCallbacksDataModal {
    // Definisco le condizioni
    const confirm =
      callbacks?.onConfirm &&
      this._scrivaUtilities.isFunction(callbacks.onConfirm);
    const close =
      callbacks?.onClose && this._scrivaUtilities.isFunction(callbacks.onClose);
    const cancel =
      callbacks?.onCancel &&
      this._scrivaUtilities.isFunction(callbacks.onCancel);

    // Ritorno l'oggetto di check
    return { confirm, close, cancel };
  }
}
