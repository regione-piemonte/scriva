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

/**
 * Componente di supporto che definisce le funzionalità comuni alle modali dell'applicazione.
 * L'utilizzo del generic "T" definisce il modello dell'oggetto "dataModal", in maniera che il componente che estende questo abbia accessibilità anche al modello dati del parametro.
 */
@Component({
  selector: 'scriva-modal-utilities',
  template: ``,
  styleUrls: [],
})
export class ScrivaModalUtilitiesComponent<T> {
  /** Oggetto contenente i parametri per la modal. */
  @Input() dataModal: T;

  /**
   * Costruttore.
   */
  constructor(public activeModal: NgbActiveModal) {}

  /**
   * ##################################
   * FUNZIONI PER GESTIONE DELLA MODALE
   * ##################################
   */

  /**
   * Funzione che effettua la dismiss (e quindi la reject) della modale.
   */
  modalDismiss() {
    // Dismiss della modale
    this.activeModal.dismiss();
  }

  /**
   * Funzione che effettua la dismiss (e quindi la reject) della modale.
   * @param data any che definisce il dato di ritorno della modale.
   */
  modalClose(data?: any) {
    // Dismiss della modale
    this.activeModal.dismiss(data);
  }

  /**
   * Funzione che effettua la close (e quindi la resolved) della modale.
   * @param data any che definisce il dato di ritorno della modale.
   */
  modalConfirm(data?: any) {
    // Close della modale
    this.activeModal.close(data);
  }
}
