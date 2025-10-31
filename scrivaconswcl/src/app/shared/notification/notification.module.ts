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
import { NotificationComponent } from './components/notification/notification.component';
import { SharedComponentsModule } from '../components/shared-components.module';
import { TranslateModule } from '@eng-ds/translate';
import { NotificationContainerComponent } from './components/notification-container/notification-container.component';
import { NgbToastModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [NotificationComponent, NotificationContainerComponent],
  imports: [
    CommonModule,
    SharedComponentsModule,
    TranslateModule,
    NgbToastModule
  ],
  exports: [NotificationContainerComponent, NotificationComponent]
})
export class NotificationModule {}
