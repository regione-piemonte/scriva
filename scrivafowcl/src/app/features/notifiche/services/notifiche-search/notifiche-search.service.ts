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
import { FormControl } from '@angular/forms';
import { DynamicObjAny } from '../../../../core/interfaces/scriva.interfaces';
import { NOTIFICHE_SEARCH_CONSTS } from '../../components/notifiche-search/utilities/notifiche-search.consts';

@Injectable({ providedIn: 'root' })
export class NotificheSearchService {
  /** Costante contenente le informazioni costanti del componente. */
  private NS_C = NOTIFICHE_SEARCH_CONSTS;

  /**
   * Costruttore.
   */
  constructor() {}

  /**
   * ################################################
   * FUNZIONI PER LA GESTIONE DEL FORM DEL COMPONENTE
   * ################################################
   */

  /**
   * Funzione di init di supporto per la generazione delle configurazioni per il form group del componente.
   * @returns DynamicObjAny contenente le configurazioni dei campi del form.
   */
  generateControlsConfig(): DynamicObjAny {
    // Definisco un oggetto composto da chiavi definite come costanti per la configurazione del form del componente
    const controlsConfig: DynamicObjAny = {};
    // Definisco chiave e valore specifico per ogni elemento del form
    // # Stato notifica
    controlsConfig[this.NS_C.FC_STATO_NOTIFICA] = new FormControl(
      { value: null, disabled: false },
      { validators: [] }
    );
    // # Procedimento
    controlsConfig[this.NS_C.FC_PROCEDIMENTO] = new FormControl(
      { value: null, disabled: false },
      { validators: [] }
    );
    // # Data da
    controlsConfig[this.NS_C.FC_DATA_DA] = new FormControl(
      { value: null, disabled: false },
      { validators: [] }
    );
    // # Data a
    controlsConfig[this.NS_C.FC_DATA_A] = new FormControl(
      { value: null, disabled: false },
      { validators: [] }
    );
    // # Numero istanza
    controlsConfig[this.NS_C.FC_NUMERO_ISTANZA] = new FormControl(
      { value: null, disabled: false },
      { validators: [] }
    );

    // Ritorno l'insieme di configurazioni
    return controlsConfig;
  }
}
