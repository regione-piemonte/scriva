/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import {
  ModuleWithProviders,
  NgModule,
  Optional,
  SkipSelf
} from '@angular/core';
import { Logger } from './logger.service';
import { LoggerConfig } from './models/logger.model';

export function loadLoggerService(config: LoggerConfig) {
  return new Logger(config);
}

@NgModule()
export class LoggerModule {
  constructor(@Optional() @SkipSelf() parentModule: LoggerModule) {
    if (parentModule) {
      throw new Error(
        'LoggerModule is already loaded. Import it in the AppModule/CoreModule only'
      );
    }
  }

  static forRoot(config?: LoggerConfig): ModuleWithProviders<LoggerModule> {
    return {
      ngModule: LoggerModule,
      providers: [
        { provide: LoggerConfig, useValue: config },
        {
          provide: Logger,
          useFactory: loadLoggerService,
          deps: [LoggerConfig]
        }
      ]
    };
  }
}
