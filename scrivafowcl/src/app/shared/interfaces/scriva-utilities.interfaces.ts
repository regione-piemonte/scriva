/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ScrivaServerError } from '../../core/classes/scriva.classes';
import { ScrivaCodesMesseges } from '../../core/enums/scriva-codes-messages.enums';
import {
  ScrivaDDItemConfig,
  ScrivaFormInputCss,
  ScrivaIconConfigs,
} from '../classes/scriva-utilities/scriva-utilities.classes';
import { IScrivaIcon } from '../components/scriva-icon/utilities/scriva-icon.interfaces';
import { ScrivaButtonTypes } from '../enums/scriva-utilities/scriva-utilities.enums';
import { FormIoI18NVocabularies } from '../services/formio/i18n/utilities/formio-i18n.enums';
import { TFormIoI18NVocabulariesConfigs } from '../services/formio/i18n/utilities/formio-i18n.types';
import { ScrivaCss } from '../types/scriva-utilities/scriva-utilities.types';

/**
 * Interfaccia che definisce la configurazione per la gestione per la segnalazione degli errori.
 */
export interface IServiziErrorConfig {
  /** ScrivaServerError contenente un oggetto di informazioni sull'errore generato. */
  e?: ScrivaServerError;
  /** ScrivaCodesMesseges che definisce il codice del messaggio da utilizzare in caso non ci sia il codice all'interno dell'errore. */
  defaultCode?: ScrivaCodesMesseges;
  /** String che definisce il target per "appendere" il messaggio di segnalazione. */
  target: string;
  /** Boolean con l'indicazione di autofade per il messaggio. Se non definito, il default è: false. */
  autoFade?: boolean;
}

/**
 * Interfaccia che viene utilizzata per definire la configurazione della funzione di un FormControl: updateValueAndValidity.
 * Maggiori dettagli al link:
 * @link https://angular.io/api/forms/AbstractControl#updateValueAndValidity
 */
export interface IFormUpdatePropagation {
  onlySelf?: boolean;
  emitEvent?: boolean;
}

/**
 * Interfaccia che definisce le configurazioni dati e css per uno scriva-button.
 */
export interface IScrivaButton {
  data?: IScrivaButtonConfig;
  css?: IScrivaButtonCss;
}

/**
 * Interfaccia che definisce le configurazioni dati e css per uno scriva-icon-text-button.
 */
export interface IScrivaIconTextButton {
  data?: IScrivaIconTextButtonConfig;
  css?: IScrivaIconTextButtonCss;
}

/**
 * Interfaccia che definisce le configurazioni per il comportamento dei scriva-button.
 */
export interface IScrivaButtonConfig {
  /** String che definisce la label del pulsante. */
  label: string;
}

/**
 * Interfaccia che definisce le configurazioni per il comportamento dei scriva-icon-text-button.
 */
export interface IScrivaIconTextButtonConfig extends IScrivaButtonConfig {
  /** IScrivaIcon che definisce l'oggetto di configurazione per una scriva-icon. */
  icon: IScrivaIcon;
}

/**
 * Interfaccia che definisce le configurazioni per gli stili dei scriva-button
 */
export interface IScrivaButtonCss {
  /**
   * Per la gestione degli stili utilizzare questa struttura
   * [ngClass]="cssConfig.customButton | scrivaCssHandler:C_C.CSS_TYPE_CLASS"
   * [ngStyle]="cssConfig.customButton | scrivaCssHandler:C_C.CSS_TYPE_STYLE"
   */
  customButton?: string | any;
  /** Tipo dichiarativo derivato da ScrivaButtonTypes. */
  typeButton?: ScrivaButtonTypes;
}

/**
 * Interfaccia che definisce le configurazioni per gli stili dei scriva-icon-text-button
 */
export interface IScrivaIconTextButtonCss extends IScrivaButtonCss {
  /**
   * Per la gestione degli stili utilizzare questa struttura
   * [ngClass]="cssConfig.customButton | scrivaCssHandler:C_C.CSS_TYPE_CLASS"
   * [ngStyle]="cssConfig.customButton | scrivaCssHandler:C_C.CSS_TYPE_STYLE"
   */
  customLabel?: string | any;
  labelLeft?: boolean;
}

/**
 * Interfaccia che definisce le configurazioni per gli stili dei scriva-button
 */
export interface IScrivaDDButtonCss extends IScrivaButtonCss {
  customItems?: ScrivaCss;
}

/**
 * Interfaccia che definisce le configurazioni per il comportamento dei scriva-dropdown-button
 */
export interface IScrivaDDButtonConfig extends IScrivaButtonConfig {
  dropdownItems: ScrivaDDItemConfig[];
}

/**
 * Interfaccia che definisce le configurazioni per la lista dei pulsanti per il componente scriva-dropdown-button.
 */
export interface IScrivaDDItemConfig {
  id: string;
  label: string;
  disabled?: boolean;
  title?: string;
  data?: any;
  codeAEA?: string;
  itemCss?: ScrivaCss;
  useIcon?: boolean;
  useIconLeft?: boolean;
  useIconRight?: boolean;
  icon?: ScrivaIconConfigs;
  iconLeft?: ScrivaIconConfigs;
  iconRight?: ScrivaIconConfigs;
}

/**
 * Interfaccia che rappresenta le configurazioni per il componente scriva-icon.
 */
export interface IScrivaIconConfigs {
  /** Array di string che definisce le classi da utilizzare. */
  classes?: string[];
  /** String che definisce l'url della risorsa dell'icona. */
  icon?: string;
  /** Any che definisce gli css per l'icona, deve essere compatibile con NgStyle. */
  iconStyles?: ScrivaCss;
  /** String che definisce il title dell'icona. */
  iconTitle?: string;
  /** Boolean che definisce se l'icona è disabilitata. */
  disabled?: boolean;
  /** Boolean che definisce se utilizzare la classe di default dell'icona. */
  useDefault?: boolean;
  /** Boolean che definisce se attivare il click dell'icona. */
  enableClick?: boolean;
}

/**
 * Interfaccia di comodo che racchiude le configurazioni per la gestione delle input form dell'applicazione.
 */
export interface IScrivaComponentConfig<T> {
  css: ScrivaFormInputCss;
  data: T;
  source?: any | any[];
}

/**
 * Interfaccia di comodo che definisce le proprietà per la gestion del css per i componenti scriva-form-input.
 */
export interface IScrivaFormInputCss {
  customForm?: string | any;
  customFormCheck?: string;
  customInput?: string;
  customLabel?: string;
  customError?: string;
  showErrorFG?: boolean;
  showErrorFC?: boolean;
  labelColLeft?: string | any;
  labelColRight?: string | any;
}

/**
 * Interfaccia che definisce le regole di gestione dei parametri per la composizione di query params.
 */
export interface IRulesQueryParams {
  allowNull: boolean;
  allowUndefined: boolean;
  allowEmptyString: boolean;
}

/**
 * Interfaccia che definisce le informazioni minime di configurazione per la visualizzazione di un alert.
 */
export interface IAlertConfig {
  /** string con il codice del messaggio da visualizzare. */
  code: string;
  /** string con l'id dell'elemento del DOM alla quale attaccare l'alert. */
  idDOM: string;
  /** boolena che definisce se il messaggio di alert deve scompararire in automatico. */
  autoFade: boolean;
}

/**
 * Interfaccia specifica che definisce le configurazioni dati per associare, mediante proprietà [rowClass] della componente <ngx-datatable>, la colorazione ad una riga selezionata.
 */
export interface INgxDataTableRowSelected {
  /** La proprietà definisce la classe di stile globale per la colorazione della riga, mentre il valore definisce se la classe di stile è d'attivare effettivamente. */
  'riga-selezionata': boolean;
}

/**
 * Interfaccia che definisce la struttura per la configurazione dell'interfaccia delle logiche di lettura blob dati.
 */
export interface IHandleBlobRead {
  blob: Blob;
  eventsBehavior: IHandleBlobEvents[];
}

/**
 * Interfaccia che definisce la struttura per la configurazione dell'interfaccia delle logiche per gli eventi di lettura blob dati.
 */
export interface IHandleBlobEvents {
  /** string con l'evento compatibile con l'oggetto FileReader di javascript e da intercettare per eseguire le logiche desiderate. */
  event: string;
  /** (e: ProgressEvent<FileReader>) => any; con la funzione da eseguire alla cattura dell'evento definito. */
  behavior: (e: ProgressEvent<FileReader>) => any;
}

/**
 * Interfaccia che rappresenta le possibili configurazioni di renderizzazione di un FormIo.
 */
export interface IFormIoRenderOptions {
  SCRIVA?: any;
  language?: FormIoI18NVocabularies;
  i18n?: TFormIoI18NVocabulariesConfigs;
}
