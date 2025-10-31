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
import { IFormioVocabulary } from './formio-i18n.interfaces';

/**
 * Dichiarazione di un tipo specifico di variabile che permette di definire un oggetto composto come proprietà dai valori dell'enumeratore FormIoI18NVocabularies.
 */
export type TFormIoI18NVocabulariesConfigs = {
  [key in FormIoI18NVocabularies]?: IFormioVocabulary;
};
