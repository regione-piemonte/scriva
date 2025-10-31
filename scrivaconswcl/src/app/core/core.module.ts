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
  APP_INITIALIZER,
  LOCALE_ID,
  ModuleWithProviders,
  NgModule,
  Optional,
  SkipSelf
} from '@angular/core';
import { NbRoleProvider, NbSecurityModule } from '@nebular/security';
import { NbSimpleRoleProvider } from './auth/role-provider.class';
import { AuthGuard } from './services/auth-guard.service';
import { TranslateModule } from '@eng-ds/translate';
import {
  HomeGuard,
  MenuService,
  UtilityService,
  ValueMapperService
} from './services';
import { SecurityService } from './services/security.service';
import { acl } from '@app/core/auth/access-control-list';
import { LoggerModule } from '@eng-ds/logger';
import { CommonModule } from '@angular/common';
import { ApiClientModule } from '@eng-ds/api-client';
import { environment } from '@env/environment';
import { throwIfAlreadyLoaded } from '@core/module-import-guard';
import { IT } from '@assets/i18n/it';
import {
  ErrorInterceptor,
  LoggerInterceptor
} from '@core/backend/interceptors';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NotificationModule } from '@shared/notification/notification.module';
import { AlertModule } from '@shared/alert/alert.module';
import { AuthInterceptor } from './backend/interceptors/auth-interceptor.service';

export const CORE_PROVIDERS = [
  NbSecurityModule.forRoot(acl).providers,
  {
    provide: NbRoleProvider,
    useClass: NbSimpleRoleProvider
  },
  AuthGuard,
  HomeGuard,
  MenuService,
  SecurityService,
  UtilityService,
  {
    provide: HTTP_INTERCEPTORS,
    useClass: LoggerInterceptor,
    multi: true
  },
  {
    provide: HTTP_INTERCEPTORS,
    useClass: ErrorInterceptor,
    multi: true
  },
  {
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  },
  { provide: LOCALE_ID, useValue: 'it' }
];

@NgModule({
  imports: [
    CommonModule,
    LoggerModule,
    ApiClientModule,
    TranslateModule,
    NotificationModule,
    AlertModule
  ],
  exports: [],
  declarations: []
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    throwIfAlreadyLoaded(parentModule, 'CoreModule');
  }

  static forRoot(): ModuleWithProviders<CoreModule> {
    return {
      ngModule: CoreModule,
      providers: [
        ...CORE_PROVIDERS,
        ...LoggerModule.forRoot(environment.logger).providers,
        ...ApiClientModule.forRoot({
          backend: environment.backend
        }).providers,
        ...TranslateModule.forRoot({
          langs: [
            { code: 'it', isDefault: true, label: 'Italiano', translations: IT }
          ]
        }).providers,

        ValueMapperService,
        {
          provide: APP_INITIALIZER,
          useFactory: (mapper: ValueMapperService) => () => {
            return mapper.getConfig();
          },
          deps: [ValueMapperService],
          multi: true
        },
        {
          provide: APP_INITIALIZER,
          useFactory: (security: SecurityService) => () => {
            return security.initUserInfo();
          },
          deps: [SecurityService],
          multi: true
        }
      ]
    };
  }
}
