/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { TranslateModule } from '@eng-ds/translate';
import { ThemeModule } from '@theme/theme.module';
import { SharedModule } from '@shared/shared.module';
import { CommonModule } from '@angular/common';
import { TipoAndempimentiResolver } from '@app/pages/ambiti/services/tipo-adempimenti.resolve';
import { AmbitiService } from '@pages/ambiti/services/ambiti.service';
import { AmbitiComponent } from '@pages/ambiti/ambiti.component';
import { AmbitiRoutingModule } from '@pages/ambiti/ambiti-routing.module';

@NgModule({
  declarations: [AmbitiComponent],
  exports: [AmbitiComponent],
  imports: [
    CommonModule,
    SharedModule,
    ThemeModule,
    TranslateModule,
    AmbitiRoutingModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [
    AmbitiService,
    TipoAndempimentiResolver
  ]
})
export class AmbitiModule {
}
