/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { TranslateModuleConfig } from '@ngx-translate/core';
import { Language } from './language.model';

export class I18nServiceConfig {
  langs?: Language[];
  translate?: TranslateModuleConfig;
}
