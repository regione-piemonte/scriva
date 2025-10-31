/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
export * from './lib/services/i18n.service';
export * from './lib/models/i18n-service.model';
export * from './lib/models/language.model';
export * from './lib/translate.module';

export {
  TranslateService,
  TranslateModuleConfig,
  TranslatePipe
} from '@ngx-translate/core';
