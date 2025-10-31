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
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { ScrivaModalService } from '../../../../shared/services/scriva-modals/scriva-modals.service';
import { ApriModalConfigs } from '../../../../shared/services/scriva-modals/utilities/scriva-modal.classes';
import {
  IApriModalConfigsForClass,
  ICallbackDataModal,
} from '../../../../shared/services/scriva-modals/utilities/scriva-modal.interfaces';
import { ProfiloContattiModificaEmailModalComponent } from '../../modals/profilo-contatti-modifica-email/profilo-contatti-modifica-email.component';

@Injectable()
export class ProfiloContattiModalService {
  /**
   * Costruttore.
   */
  constructor(
    private _ngbModal: NgbModal,
    private _scrivaModal: ScrivaModalService
  ) {}

  /**
   * Funzione che apre la modale per la modifica dell'email del profilo.
   * @param callbacks ICallbackDataModal con le callbacks da invocare alla chiusura della modale.
   */
  modificaEmail(callbacks: ICallbackDataModal) {
    // Creo la configurazione per l'apertura della modale di modifica email
    const configs: IApriModalConfigsForClass = {
      component: ProfiloContattiModificaEmailModalComponent,
      callbacks: callbacks,
      options: {
        centered: true,
        scrollable: true,
        backdrop: 'static',
        size: 'lg',
      },
      params: undefined,
      ngbModal: this._ngbModal,
    };

    // Creo la classe con le configurazioni
    const modal = new ApriModalConfigs(configs);
    // Lancio l'apertura della modale
    this._scrivaModal.apriModal(modal);
  }
}
