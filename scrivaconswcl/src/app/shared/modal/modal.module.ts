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
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@eng-ds/translate';
import { ModalService } from './modal.service';
import { ModalComponent } from './models/modal.component';
import { SharedComponentsModule } from '../components/shared-components.module';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { ContainerModalComponent } from './components/container-modal/container-modal.component';

@NgModule({
  declarations: [ContainerModalComponent, ModalComponent],
  imports: [
    CommonModule,
    SharedComponentsModule,
    NgbModalModule,
    TranslateModule
  ],
  exports: [
    ModalComponent, NgbModalModule
  ],
  providers: [
    ModalService
  ]
})
export class ModalModule {
}
