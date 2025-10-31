/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { NgModule, ModuleWithProviders } from '@angular/core';
import {
  TranslateService,
  TranslateModule as NgxTransalteModule,
  TranslatePipe,
  TranslateDirective
} from '@ngx-translate/core';

import { I18nService } from './services/i18n.service';
import { I18nServiceConfig } from './models/i18n-service.model';

export function loadI18nService(
  config: I18nServiceConfig,
  translateService: TranslateService
) {
  return new I18nService(config, translateService);
}

@NgModule({
  imports: [NgxTransalteModule],
  exports: [TranslateDirective, TranslatePipe]
})
export class TranslateModule {
  /**
   * Use this method in your root module to provide the TranslateService
   * @param config i18n config
   */
  static forRoot(
    config?: I18nServiceConfig
  ): ModuleWithProviders<TranslateModule> {
    return {
      ngModule: TranslateModule,
      providers: [
        TranslateService,
        { provide: I18nServiceConfig, useValue: config },
        // i18n service provider
        {
          provide: I18nService,
          useFactory: loadI18nService,
          deps: [I18nServiceConfig, TranslateService]
        },
        ...NgxTransalteModule.forRoot(config && config.translate).providers
      ]
    };
  }

  /**
   * Use this method in your other (non root) modules to import the directive/pipe
   * @param config i18n config
   */
  static forChild(
    config?: I18nServiceConfig
  ): ModuleWithProviders<TranslateModule> {
    return NgxTransalteModule.forChild(config && config.translate);
  }
}
