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
import { NgModule } from '@angular/core';
import { SharedModule } from '@app/shared/shared.module';
import { OsservazioniListComponent } from './components/osservazioni-list/osservazioni-list.component';
import { OsservazioniRoutingModule } from './osservazioni-routing.module';
import {
  CancellaOsservazionetAttivaPipe,
  ModificaOsservazionetAttivaPipe
} from './pipes/osservazioni-list/osservazioni-list.pipe';

@NgModule({
  declarations: [
    // COMPONENTI
    OsservazioniListComponent,
    // PIPE
    ModificaOsservazionetAttivaPipe,
    CancellaOsservazionetAttivaPipe
  ],
  imports: [CommonModule, OsservazioniRoutingModule, SharedModule]
})
export class OsservazioniModule {}
