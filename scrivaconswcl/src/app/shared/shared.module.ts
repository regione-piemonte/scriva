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
import { TranslateModule } from '@eng-ds/translate';
import { AlertModule } from './alert/alert.module';
import { SharedComponentsModule } from './components/shared-components.module';
import {
  ClickStopPropagationDirective,
  ColorStatusDirective
} from './directives';
import { FormModule } from './form';
import { ModalModule } from './modal/modal.module';
import { NotificationModule } from './notification/notification.module';
import {
  CapitalizePipe,
  DeepObjectPipe,
  FileDimensionPipe,
  TimingPipe
} from './pipes';
import { DateConstructPipe } from './pipes/date.pipe';
import { TruncateTextPipe } from './pipes/truncate-text.pipe';
import { SpezzaParoleHTMLPipe } from './pipes/utilities/utilities.pipe';
import { TableModule } from './table';

const MODULES = [
  FormModule,
  TableModule,
  NotificationModule,
  AlertModule,
  ModalModule,
  SharedComponentsModule
];

const DIRECTIVES = [ColorStatusDirective, ClickStopPropagationDirective];
const PIPES = [
  CapitalizePipe,
  TimingPipe,
  DateConstructPipe,
  DeepObjectPipe,
  TruncateTextPipe,
  FileDimensionPipe,
  SpezzaParoleHTMLPipe
];

@NgModule({
  imports: [CommonModule, TranslateModule.forChild(), ...MODULES],
  exports: [CommonModule, TranslateModule, ...MODULES, ...DIRECTIVES, ...PIPES],
  declarations: [...DIRECTIVES, ...PIPES],
  entryComponents: []
})
export class SharedModule {}
