/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { cloneDeep } from 'lodash';
import { ScrivaCodesMesseges } from '../../core/enums/scriva-codes-messages.enums';
import {
  ScrivaCssHandlerTypes,
  ScrivaFormatoDate,
} from '../enums/scriva-utilities/scriva-utilities.enums';
import { ModalButton, ModalConfig } from '../models';

/**
 * Oggetto di costanti contenente una serie di costanti comuni.
 */
export class CommonConsts {
  // Formattazione delle date per view e server
  readonly DATE_FORMAT_VIEW_PLACEHOLDER = 'GG/MM/AAAA';
  readonly DATE_FORMAT_VIEW = ScrivaFormatoDate.view;
  readonly DATE_FORMAT_SERVER = ScrivaFormatoDate.server;

  // Tipologia per la gestione del css per class e style
  readonly CSS_TYPE_CLASS = ScrivaCssHandlerTypes.class;
  readonly CSS_TYPE_STYLE = ScrivaCssHandlerTypes.style;

  readonly BTN_ALERT_LABEL_SI = 'Sì';
  readonly BTN_ALERT_LABEL_NO = 'No';

  readonly DATE_COMPLETE_YEAR_DASH_VIEW_FORMAT = 'dd-MM-yyyy HH:mm:ss';
  readonly DATE_COMPLETE_VIEW_FORMAT = 'dd/MM/yyyy HH:mm:ss';
  readonly DATE_VIEW_FORMAT = 'dd/MM/yyyy';
  readonly HOURS_VIEW_FORMAT = 'HH:mm:ss';

  readonly MOMENT_COMPLETE_YEAR_DASH_VIEW_FORMAT = 'YYYY-MM-DD HH:mm:ss';
  readonly MOMENT_DATE_COMPLETE_VIEW_FORMAT = 'DD/MM/YYYY HH:mm:ss';
  readonly MOMENT_DATE_VIEW_FORMAT = 'DD/MM/YYYY';
  readonly MOMENT_HOURS_VIEW_FORMAT = 'HH:mm:ss';

  readonly MEGAYTE_SIZE = 1048576;
  readonly FILE_SIZE_FORMAT = '1.0-2';

  /** ACM = Ask Confirm Modal - definizione per: titolo. */
  readonly ACM_TITLE = 'Conferma annullamento modifiche';
  /** ACM = Ask Confirm Modal - definizione per: codice messaggio. */
  readonly ACM_CODE = ScrivaCodesMesseges.A001;
  /** ACM = Ask Confirm Modal - definizione per: pulsante conferma. */
  readonly _ACM_BTN_CONFIRM = {
    label: 'CONFERMA',
    type: 'btn-primary',
    callback: () => {},
  };
  /** ACM = Ask Confirm Modal - definizione per: pulsante annulla. */
  readonly _ACM_BTN_CANCEL = {
    label: 'ANNULLA',
    type: 'btn-link',
    callback: () => {},
  };

  /** string con la configurazione di default per il valore vuoto/di default per le select applicative. */
  readonly DEFAULT_SELECT_VALUE: string = 'Seleziona';

  constructor() {}

  /**
   * Funzione che genera la configurazione standard per la richiesta di chiusura pagina/modale all'utente.
   * La configurazione genera le informazioni per l'apertura di una modale di conferma.
   * @param onConfirm () => any con la funzione eseguita come callback dalla scelta utente.
   * @param onCancel () => any con la funzione eseguita come callback dalla scelta utente.
   * @returns ModalConfig con la configurazione standard generata.
   */
  confermaAnnullamentoModalConfigs(
    onConfirm?: () => any,
    onCancel?: () => any
  ): ModalConfig {
    // Recupero le informazioni per la configurazione
    const title: string = this.ACM_TITLE;
    const codMess: string = this.ACM_CODE;

    // Richiamo la funzione comune
    return this.confermaModalConfigs(title, codMess, onConfirm, onCancel);
  }

  /**
   * Funzione che genera la configurazione standard per la richiesta di chiusura pagina/modale all'utente.
   * La configurazione genera le informazioni per l'apertura di una modale di conferma.
   * @param onConfirm () => any con la funzione eseguita come callback dalla scelta utente.
   * @param onCancel () => any con la funzione eseguita come callback dalla scelta utente.
   * @returns ModalConfig con la configurazione standard generata.
   */
  confermaModalConfigs(
    title: string,
    codMess: string,
    onConfirm?: () => any,
    onCancel?: () => any
  ): ModalConfig {
    // Default per la gestione delle callback
    const defaultCB = () => {};
    onConfirm = onConfirm ?? defaultCB;
    onCancel = onCancel ?? defaultCB;
    // Definisco i pulsanti, definendo la callback rispetto a quanto passato in input
    const btnConfirm = this.ACM_BTN_CONFIRM;
    btnConfirm.callback = () => {
      onConfirm();
    };
    const btnCancel = this.ACM_BTN_CANCEL;
    btnCancel.callback = () => {
      onCancel();
    };

    // Definisco i pulsanti per la configurazione
    const buttons = [btnCancel, btnConfirm];

    // Genero e ritorno l'oggetto di configurazione
    return { title, codMess, buttons };
  }

  /**
   * Getter per il recupero della configurazione per il pulsante di conferma modale nel contesto: ask confirm modal.
   * @returns ModalButton con l'oggetto per la gestione del pulsante CONFERMA.
   */
  get ACM_BTN_CONFIRM(): ModalButton {
    // Ritorno una copia della configurazione
    return cloneDeep(this._ACM_BTN_CONFIRM);
  }

  /**
   * Getter per il recupero della configurazione per il pulsante di annulla modale nel contesto: ask confirm modal.
   * @returns ModalButton con l'oggetto per la gestione del pulsante ANNULLA.
   */
  get ACM_BTN_CANCEL(): ModalButton {
    // Ritorno una copia della configurazione
    return cloneDeep(this._ACM_BTN_CANCEL);
  }
}
