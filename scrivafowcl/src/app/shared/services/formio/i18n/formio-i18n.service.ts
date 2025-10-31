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
import { FormIo_EN } from './dictionaries/en/formio-i18n.en';
import { FormIo_IT } from './dictionaries/it/formio-i18n.it';
import { FormIoI18NVocabularies } from './utilities/formio-i18n.enums';
import { IFormIoI18N } from './utilities/formio-i18n.interfaces';
import { TFormIoI18NVocabulariesConfigs } from './utilities/formio-i18n.types';

@Injectable({ providedIn: 'root' })
export class FormioI18NService {
  /** string con il vocabolario di default per l'applicazione. */
  private DEFAULT_VOCABULARY = FormIoI18NVocabularies.IT;

  /**
   * Costruttore.
   */
  constructor() {}

  /**
   * Funzione che genera uno specifico vocabolario per l'internazionalizzazione di FormIo.
   * @param language FormIoI18NVocabularies con il vocabolario da generare.
   * @returns IFormIoI18N con la configurazione dell'internazionalizzazione e l'attivazione di un vocabolario specifico.
   */
  getActiveVocabulary(language: FormIoI18NVocabularies): IFormIoI18N {
    // Verifico l'input
    language = language ?? this.DEFAULT_VOCABULARY;

    // Genero tutti i vocabolari disponibili per formio
    const i18n: TFormIoI18NVocabulariesConfigs = this.getAllVocabulary();
    // Genero l'oggetto di configurazione, definendo il vocabolario da utilizzare
    const i18nConfigs: IFormIoI18N = { language, i18n };

    // Ritorno la configurazione
    return i18nConfigs;
  }

  /**
   * Funzione che genera le configurazioni per tutte le internazionalizzazioni per il caricamento di FormIo.
   * @notes La funzione è stata pensata per gestire i dati. Al momento le informazioni sono solo FE, quindi per velocizzare lo sviluppo, si accede e si definiscono i dati direttamente nell'oggetto di ritorno.
   * @returns TFormIoI18NVocabulariesConfigs con la configurazione per tutti i vocabolari per FormIo.
   */
  getAllVocabulary(): TFormIoI18NVocabulariesConfigs {
    // Definisco la variabile contenitore per i dati
    const vocabularies: TFormIoI18NVocabulariesConfigs = {};

    // Estraggo i vocabolari definiti attualmente in applicazione
    const IT = FormIoI18NVocabularies.IT;
    const EN = FormIoI18NVocabularies.EN;

    // Vado a definire le configurazioni per ogni lingua
    vocabularies[IT] = FormIo_IT;
    vocabularies[EN] = FormIo_EN;

    // Ritorno la configurazione dei vocabolari
    return vocabularies;
  }
}
