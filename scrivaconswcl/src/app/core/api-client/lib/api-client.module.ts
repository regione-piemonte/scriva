/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { HttpClient, HttpClientModule } from '@angular/common/http';
import {
  ModuleWithProviders,
  NgModule,
  Optional,
  SkipSelf
} from '@angular/core';
import { ApiClient } from './api-client.service';
import { ApiClientConfig } from './models/api-client.model';

export function loadApiService(
  config: ApiClientConfig,
  httpClient: HttpClient
) {
  return new ApiClient(config, httpClient);
}

@NgModule({
  imports: [HttpClientModule],
  exports: [HttpClientModule]
})
export class ApiClientModule {
  constructor(@Optional() @SkipSelf() parentModule: ApiClientModule) {
    if (parentModule) {
      throw new Error(
        'ApiClientModule is already loaded. Import it in the AppModule/CoreModule only'
      );
    }
  }

  static forRoot(
    config: Partial<ApiClientConfig>
  ): ModuleWithProviders<ApiClientModule> {
    return {
      ngModule: ApiClientModule,
      providers: [
        { provide: ApiClientConfig, useValue: new ApiClientConfig(config) },
        {
          provide: ApiClient,
          useFactory: loadApiService,
          deps: [ApiClientConfig, HttpClient]
        }
      ]
    };
  }
}
