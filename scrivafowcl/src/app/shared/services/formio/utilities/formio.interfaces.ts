/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { FormioComponent } from 'angular-formio';
import { ICallbackDataModal } from '../../scriva-modals/utilities/scriva-modal.interfaces';
import { TipiComponentiFormIo } from './formio.enums';
import { FormioFormBuilderTypes } from './formio.types';

/**
 * Interfaccia che definisce le informazioni per l'apertura di una modale di conferma.
 */
export interface IFormIoConfermaModal {
  title?: string;
  codeOrBody?: string;
  callbacks?: ICallbackDataModal;
}

/**
 * Interfaccia che definisce i parametri di configurazione per le render options per i quadri formio.
 */
export interface IFormioRenderOptionsParams {
  /** boolean che definisce se l'applicazione è in modalità FrontOffice. Se false, la modalità è Backoffice. */
  isFrontOffice: boolean;
  /** (data: any) => void; con la funzione definita per aggiornare le logiche di un formio da componante angular. */
  formioUpdate?: (data: any) => void;
  /** (data: any, key: string, value: any) => any; con la funzione definita per aggiornare le informazioni di un componente formio. Se non definita, è gestita una funzione di default. */
  formioUpdateComponent?: (data: any, key: string, value: any) => any;
}

/**
 * Interfaccia che definisce i parametri di configurazione per la gestione del flusso di validazione di un FormIo.
 */
export interface IFormioValidationCheck {
  formFormIo: FormioComponent;
  callback: (valid: boolean) => any;
}

/**
 * Interfaccia che mappa le informazioni strutturali di un FormIo come struttura processata dalla libreria e/o dal builder di sviluppo.
 */
export interface IFormioFormBuilder {
  display: string;
  components: FormioFormBuilderTypes[];
}

/**
 * Interfaccia che mappa le informazioni strutturali di un FormIo come struttura processata dalla libreria e/o dal builder di sviluppo.
 * L'oggetto è specifico per un componente di tipo "pannello".
 */
export interface IFormioFormBuilderPanel {
  collapsed?: boolean;
  collapsible?: boolean;
  components?: any[];
  input?: boolean;
  key?: string;
  label?: string;
  tableView?: boolean;
  title?: string;
  type?: TipiComponentiFormIo.pannello;
}

/**
 * Interfaccia che mappa le informazioni strutturali di un FormIo come struttura processata dalla libreria e/o dal builder di sviluppo.
 * L'oggetto è specifico per un componente di tipo "nascosto".
 */
export interface IFormioFormBuilderHidden {
  defaultValue?: any;
  input?: boolean;
  key?: string;
  label?: string;
  tableView?: boolean;
  type?: TipiComponentiFormIo.nascosto;
}

/**
 * Interfaccia che mappa le informazioni strutturali di un FormIo come struttura processata dalla libreria e/o dal builder di sviluppo.
 * L'oggetto è specifico per un componente di tipo "container".
 */
export interface IFormioFormBuilderContainer {
  components?: any[];
  input?: boolean;
  key?: string;
  label?: string;
  tableView?: boolean;
  type?: TipiComponentiFormIo.container;
  validateWhenHidden?: boolean;
}

/**
 * Interfaccia che mappa le informazioni strutturali di un FormIo come struttura processata dalla libreria e/o dal builder di sviluppo.
 * L'oggetto è specifico per un componente di tipo "htmlelement".
 */
export interface IFormioFormBuilderHTMLElement {
  attrs?: any[];
  content?: string;
  customClass?: string;
  input?: boolean;
  key?: string;
  label?: string;
  refreshOnChange?: boolean;
  tableView?: boolean;
  type?: TipiComponentiFormIo.elementoHTML;
}
