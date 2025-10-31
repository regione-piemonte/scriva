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
import { ExtendedComponentSchema } from 'angular-formio';
import { FormioForm } from 'angular-formio/formio.common';
import { identificativoComponenteFormIo } from './formio-utilities.functions';
import { FormioFormBuilderTypes } from './utilities/formio.types';

/**
 * Servizio di utility per il componente scriva che gestisce i formio applicativi.
 */
@Injectable({ providedIn: 'root' })
export class FormioUtilitiesService {
  /**
   * Costruttore.
   */
  constructor() {}

  /**
   * Funzione che ricerca e ritorna un oggetto ExtendedComponentSchema come componente del formio.
   * @param formioForm FormioForm contenente le configurazioni del formio.
   * @param key string che definisce il nome della chiave per il recupero del formio.
   * @returns ExtendedComponentSchema come componente definito per il formio. Se non esiste, ritorna undefined.
   */
  getFormioComponent(
    formioForm: FormioForm,
    key: string
  ): ExtendedComponentSchema {
    // Verifico l'input
    if (!formioForm) {
      // Non esiste la configurazione
      return undefined;
    }

    // Estraggo la lista dei componenti dal formio
    const { components } = formioForm;
    // Verifico esistano componenti
    if (!components || components.length === 0) {
      // Nessun componente
      return undefined;
    }

    // Cerco all'interno dei componenti per chiave
    const theComponent = components.find((c: ExtendedComponentSchema) => {
      // Effettuo un confronto tra chiavi
      return c.key === key;
    });

    // Ritorno il componente trovato, oppure undefined
    return theComponent;
  }

  /**
   * Funzione di comodo che recupera l'identificativo di un componente formIo.
   * @param componenteFormIo FormioFormBuilderTypes con la struttura FormIo del componente che dovrebbe contenere le informazioni per il recupero dell'identificativo.
   * @returns string con l'identificativo trovato, altrimenti stringa vuota.
   */
  identificativoComponenteFormIo(
    componenteFormIo: FormioFormBuilderTypes
  ): string {
    // Richiamo la funzione di utility
    return identificativoComponenteFormIo(componenteFormIo);
  }
}
