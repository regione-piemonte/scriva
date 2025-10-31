/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IFormioConfirmModalParams } from './utilities/formio-confirm-modal.interfaces';

/**
 * Modal di conferma.
 */
@Component({
  selector: 'formio-confirm-modal',
  templateUrl: './formio-confirm-modal.component.html',
  styleUrls: ['./formio-confirm-modal.component.scss'],
})
export class FormioConfirmModalComponent {
  /** Input IFormioConfirmModalParams con le informazioni per la gestione della modale di conferma. */
  @Input() dataModal: IFormioConfirmModalParams;

  /**
   * Costruttore.
   */
  constructor(private _activeModal: NgbActiveModal) {}

  /**
   * ######################
   * FUNZIONI PER LA MODALE
   * ######################
   */

  /**
   * Funzione che effettua la close (e quindi la resolved) della modale.
   */
  modalConfirm(payload?: any) {
    // Close della modale
    this._activeModal.close(payload);
  }

  /**
   * Funzione che effettua la dismiss (e quindi la reject) della modale.
   */
  modalCancel(payload?: any) {
    // Dismiss della modale
    this._activeModal.dismiss(payload);
  }

  /**
   * Funzione che effettua la dismiss (e quindi la reject) della modale.
   */
  modalClose(payload?: any) {
    // Dismiss della modale
    this._activeModal.dismiss(payload);
  }

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  /**
   * Getter di comodo per il title dalla modale.
   */
  get modalTitle(): string {
    // Recupero il title dalla configurazione
    return this.dataModal?.title;
  }
}
