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
import { CommonModule } from '@angular/common';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';

import { SearchRoutingModule } from './search-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';

import { MainComponent } from './pages/main/main.component';
import { GestioneAbilitazioniComponent } from './pages/gestione-abilitazioni/gestione-abilitazioni.component';
import { RevocaDelegaComponent } from './pages/revoca-delega/revoca-delega.component';
import { ConclusioneProcedimentoComponent } from './pages/conclusione-procedimento/conclusione-procedimento.component';
import { PresaInCaricoComponent } from './pages/presa-in-carico/presa-in-carico.component';

import { SearchService, SearchStoreService } from './services';

@NgModule({
  declarations: [
    MainComponent,
    GestioneAbilitazioniComponent,
    RevocaDelegaComponent,
    ConclusioneProcedimentoComponent,
    PresaInCaricoComponent
  ],
  imports: [
    CommonModule,
    NgxDatatableModule,
    SearchRoutingModule,
    SharedModule,
  ],
  providers: [SearchService, SearchStoreService],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class SearchModule {}
