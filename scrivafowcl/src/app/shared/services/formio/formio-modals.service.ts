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
import { NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { ScrivaCodesMesseges } from '../../../core/enums/scriva-codes-messages.enums';
import { FormioConfirmModalComponent } from '../../components/formio/modals/formio-confirm-modal/formio-confirm-modal.component';
import { IFormioConfirmModalParams } from '../../components/formio/modals/formio-confirm-modal/utilities/formio-confirm-modal.interfaces';
import { ScrivaAlertMessagesService } from '../scriva-alert/scriva-alert-messages.service';
import { ScrivaModalService } from '../scriva-modals/scriva-modals.service';
import { ApriModalConfigs } from '../scriva-modals/utilities/scriva-modal.classes';
import {
  IApriModalConfigsForClass,
  ICallbackDataModal,
} from '../scriva-modals/utilities/scriva-modal.interfaces';
import { IFormIoConfermaModal } from './utilities/formio.interfaces';

@Injectable({ providedIn: 'root' })
export class FormioModalsService {
  /**
   * Costruttore.
   */
  constructor(
    private _scrivaMessages: ScrivaAlertMessagesService,
    private _scrivaModal: ScrivaModalService
  ) {}

  /**
   * Funzione apre la modale di richiesta conferma operazione.
   * @param configis IFormIoConfermaModal con i parametri di configurazione per la modale.
   */
  conferma(configs: IFormIoConfermaModal) {
    // Verifico l'input
    const callbacks = configs?.callbacks ?? this.defaultCallbacks();
    const title = configs?.title ?? 'Conferma operazione';
    const body = this.handleMessageModal(configs?.codeOrBody);

    // Definisco le informazioni per l'apertura della modale di conferma
    const component = FormioConfirmModalComponent;
    const options: NgbModalOptions = {
      centered: true,
      scrollable: true,
      backdrop: 'static',
      size: 'md',
    };
    // Definisco i parametri da passare alla modale
    const dataModal: IFormioConfirmModalParams = { title, body };
    const params: any = { dataModal };

    // Creo la configurazione per l'apertura della modale di modifica email
    const c: IApriModalConfigsForClass = {
      component,
      callbacks,
      options,
      params,
    };

    // Creo la classe con le configurazioni
    const modal = new ApriModalConfigs(c);
    // Lancio l'apertura della modale
    this._scrivaModal.apriModal(modal);
  }

  /**
   * Funzione di comodo che genera delle callback di default per evitare errori.
   * @returns ICallbackDataModal con la configurazione di default.
   */
  private defaultCallbacks(): ICallbackDataModal {
    // Genero e ritorno un oggetto con callback di default
    const defaultCallbacks: ICallbackDataModal = {
      onConfirm: () => {},
      onCancel: () => {},
      onClose: () => {},
    };
    // Ritorno la configurazione di default
    return defaultCallbacks;
  }

  /**
   * Funzione che gestisce il body descrittivo per la modale.
   * Se l'input è un codice messaggio (ScrivaCodesMesseges like) si tenta di recuperarlo, altrimenti si definire il body come stringa effettiva.
   * @param body string | ScrivaCodesMesseges con il body del messaggio o il codice per il recuper dalla descrizione.
   * @returns string con il codice del messaggio.
   */
  private handleMessageModal(body: string | ScrivaCodesMesseges): string {
    // Definisco un default da ritornare
    const defaultBody: string = 'Confermare operazione?';
    // Verifico l'input
    if (!body) {
      // Ritorno un default
      return defaultBody;
    }

    // Verifico se la stringa è di 4 caratteri
    if (body.length === 4) {
      // Dovrebbe essere un codice, tento di recuperarlo
      let msgText: string;
      msgText = this._scrivaMessages.getDescrizioneMessaggio(body, defaultBody);

      // Ritorno il messaggio
      return msgText;
      // #
    } else {
      // E' una descrizione manuale, ritorno il testo
      return body;
    }
  }
}
