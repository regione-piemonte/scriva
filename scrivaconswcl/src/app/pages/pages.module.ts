/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { NgModule } from '@angular/core';
import { TranslateModule } from '@eng-ds/translate';
import { NotAuthorizedComponent } from '@pages/not-authorized/not-authorized.component';
import { SharedModule } from '../shared/shared.module';
import { ThemeModule } from '../theme/theme.module';
import { ContattiComponent } from './contatti/contatti.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { CancellaOsservazionetAttivaPipe } from './osservazioni/pipes/osservazioni-list/osservazioni-list.pipe';
import { PagesMenuService } from './pages-menu.service';
import { PagesRoutingModule } from './pages-routing.module';
import { PagesComponent } from './pages.component';
import { ServiceHomeComponent } from './service-home/service-home.component';

@NgModule({
  imports: [
    PagesRoutingModule,
    ThemeModule,
    SharedModule,
    TranslateModule.forChild()
  ],
  declarations: [
    // COMPONENTI
    PagesComponent,
    NotFoundComponent,
    NotAuthorizedComponent,
    ServiceHomeComponent,
    ContattiComponent
  ],
  providers: [PagesMenuService]
})
export class PagesModule {}
