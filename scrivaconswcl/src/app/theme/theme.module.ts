/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { CommonModule } from '@angular/common';
import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TranslateModule } from '@eng-ds/translate';
import { NbSecurityModule } from '@nebular/security';
import { HeaderComponent } from '@theme/header/header.component';
import { LoadingService } from '@theme/layouts/loading.services';
import { SharedComponentsModule } from '@shared/components/shared-components.module';
import { FooterComponent } from '@theme/footer/footer.component';
import { MenuComponent } from '@theme/menu/menu.component';
import { AppDefaultLayoutComponent } from '@theme/layouts';
import { FocusRemoverDirective } from '@shared/directives';
import { NotificationModule } from '@shared/notification/notification.module';

const NB_MODULES = [NbSecurityModule];
const COMPONENTS = [
  HeaderComponent,
  FooterComponent,
  MenuComponent,
  AppDefaultLayoutComponent
];

@NgModule({
  imports: [
    CommonModule,
    SharedComponentsModule,
    ...NB_MODULES,

    TranslateModule.forChild(),
    RouterModule,
    NotificationModule
  ],
  exports: [CommonModule, ...COMPONENTS, FocusRemoverDirective],
  declarations: [...COMPONENTS, FocusRemoverDirective, MenuComponent]
})
export class ThemeModule {
  constructor() {}

  static forRoot(): ModuleWithProviders<ThemeModule> {
    return {
      ngModule: ThemeModule,
      providers: [LoadingService]
    };
  }
}
