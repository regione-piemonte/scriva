/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { FormIoI18NVocabularies } from './formio-i18n.enums';
import { TFormIoI18NVocabulariesConfigs } from './formio-i18n.types';

/**
 * Interfaccia che rappresenta l'oggetto dati contenente le informazioni per l'internazionalizzazione di FormIo.
 * @references https://github.com/formio/angular/wiki/Translations
 */
export interface IFormIoI18N {
  language: FormIoI18NVocabularies;
  i18n: TFormIoI18NVocabulariesConfigs;
}

/**
 * Interfaccia che rappresenta la configurazione di un vocabolario per FormIo.
 */
export interface IFormioVocabulary {
  [key: string]: string;
}
