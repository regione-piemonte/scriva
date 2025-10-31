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
import { DynamicObjAny } from '../../../../core/interfaces/scriva.interfaces';

/**
 * Interfaccia personalizzata come supporto per le classi che gestiscono modali.
 */
export interface ICallbackDataModal {
  /** Funzione che viene invocata quando il modale viene chiuso in modalità close (success). */
  onConfirm?: (...args: any) => any;
  /** Funzione che viene invocata quando il modale viene chiuso in modalità dismiss (close) e sono stati forniti parametri. */
  onClose?: (...args: any) => any;
  /** Funzione che viene invocata quando il modale viene chiuso in modalità dismiss (cancel) e non sono stati forniti parametri. */
  onCancel?: () => any;
}

/**
 * Interfaccia personalizzata che estende le callback per la modale e definisce una serie di parametri di configurazione comuni.
 */
export interface ICommonParamsModal extends ICallbackDataModal {
  title?: string;
  body?: string;
  buttonCancel?: string;
  buttonConfirm?: string;
  onConfirm?: (...args: any[]) => any;
  onClose?: (...args: any[]) => any;
  onCancel?: (...args: any[]) => any;
  confirmPayload?: any;
  closePayload?: any;
  cancelPayload?: any;
}

/**
 * Interfaccia di configurazione per i parametri delle modali tramite servizio scriva-modal.service.ts
 */
export interface IParamsModalConfigs {
  /** ICommonParamsModal che definisce una specifica sezioni di parametri utilizzati automaticamente dalla componente di utility: scriva-utility-modal.component.ts. */
  dataModal?: ICommonParamsModal;
  /** Per questo oggetto è possibile definire un qualunque parametro, che deve risultare poi mappato all'interno del componente della modale come variabile. */
  [key: string]: any;
}

/**
 * Interfaccia di comodo che gestisce la validazione delle callback della modale.
 * Se la callback di confirm, close o cancel esistono, verranno valorizzati i relativi flag boolean a true.
 */
export interface ICheckCallbacksDataModal {
  confirm: boolean;
  close: boolean;
  cancel: boolean;
}

/**
 * Interfaccia di configurazione per le modali tramite servizio scriva-modal.service.ts
 */
export interface IApriModalConfigsForClass {
  /** Istanza del componente che si vuole aprire. Deve essere definito nelle entryComponents del modulo. */
  component: any;
  /** NgbModalOptions contenente le informazioni di configurazione per le opzioni del modale, se non ci sono informazioni, definire un oggetto vuoto. */
  options: NgbModalOptions;
  /** DynamicObjAny che deve riflettere i nomi delle @Input() del componente e i propri valori. */
  params?: DynamicObjAny;
  /** CallbackDataModal contenente la logica delle callback del modale. */
  callbacks?: ICallbackDataModal;
  /** NgbModal con l'istanza del servizio per l'apertura della modale. Obbligatorio se si tenta di aprire una modale al di fuori del modulo shared. */
  ngbModal?: NgbModal;
}
