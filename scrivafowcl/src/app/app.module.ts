/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SidebarModule } from 'ng-sidebar';
import { NgxSpinnerModule } from 'ngx-spinner';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CoreModule } from './core/core.module';
import { AccreditamentoModule } from './features/accreditamento/accreditamento.module';
import { AccreditamentoService } from './features/accreditamento/services';
import { NotificheModule } from './features/notifiche/notifiche.module';
import { ProfiloContattiModificaEmailModalComponent } from './features/profilo/modals/profilo-contatti-modifica-email/profilo-contatti-modifica-email.component';
import { ProfiloModule } from './features/profilo/profilo.module';
import { AppConfigService, AuthStoreService } from './shared/services';
import { AuthService } from './shared/services/auth/auth.service';
import { ScrivaModalService } from './shared/services/scriva-modals/scriva-modals.service';
import { SharedModule } from './shared/shared.module';

// to fix the annoying warning in console for components that use form.io try this:
// https://github.com/formio/angular/issues/133
@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserAnimationsModule,
    NgxSpinnerModule,
    BrowserModule,
    CoreModule.forRoot(),
    SidebarModule.forRoot(),
    HttpClientModule,
    NgbModule,
    RouterModule,
    AppRoutingModule,
    NotificheModule,
    SharedModule,
    AccreditamentoModule,
    ProfiloModule,
  ],
  providers: [
    AppConfigService,
    AuthService,
    AuthStoreService,
    AccreditamentoService,
    ScrivaModalService,
  ],
  bootstrap: [AppComponent],
  entryComponents: [ProfiloContattiModificaEmailModalComponent],
})
export class AppModule {}
