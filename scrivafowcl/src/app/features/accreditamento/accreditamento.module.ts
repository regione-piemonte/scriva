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
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { AccreditamentoRoutingModule } from './accreditamento-routing.module';
import { MainComponent } from './pages/main/main.component';
import { AccreditamentoService, AccreditamentoStoreService } from './services';

@NgModule({
  declarations: [MainComponent],
  imports: [CommonModule, SharedModule, AccreditamentoRoutingModule],
  providers: [AccreditamentoService, AccreditamentoStoreService],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [],
})
export class AccreditamentoModule {}
