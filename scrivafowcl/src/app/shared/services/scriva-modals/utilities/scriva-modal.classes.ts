/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { NgbModalOptions, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {
  ICallbackDataModal,
  IParamsModalConfigs,
  IApriModalConfigsForClass,
} from './scriva-modal.interfaces';

/**
 * Interfaccia di configurazione per le modali tramite servizio scriva-modal.service.ts
 */
export class ApriModalConfigs implements IApriModalConfigsForClass {
  /** Istanza del componente che si vuole aprire. Deve essere definito nelle entryComponents del modulo. */
  component: any;
  /** NgbModalOptions contenente le informazioni di configurazione per le opzioni del modale, se non ci sono informazioni, definire un oggetto vuoto. */
  options: NgbModalOptions;
  /** IParamsModalConfigs che deve riflettere i nomi delle @Input() del componente e i propri valori. */
  params?: IParamsModalConfigs;
  /** CallbackDataModal contenente la logica delle callback del modale. */
  callbacks?: ICallbackDataModal;
  /** NgbModal con l'istanza del servizio per l'apertura della modale. Obbligatorio se si tenta di aprire una modale al di fuori del modulo shared. */
  ngbModal?: NgbModal;

  constructor(c: IApriModalConfigsForClass) {
    this.component = c.component;
    this.options = c.options;
    this.params = c.params;
    this.callbacks = c.callbacks;
    this.ngbModal = c.ngbModal;
  }
}
