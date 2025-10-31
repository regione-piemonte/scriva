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
import { SharedModule } from 'src/app/shared/shared.module';
import { MainComponent } from './pages/main/main.component';
import { ContactsRoutingModule } from './contacts-routing.module';

@NgModule({
  declarations: [MainComponent],
  imports: [CommonModule, SharedModule, ContactsRoutingModule],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class ContactsModule {}
