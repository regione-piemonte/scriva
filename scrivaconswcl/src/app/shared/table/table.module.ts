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
import { TranslateModule } from '@eng-ds/translate';
import { FormModule } from '../form';
import { AgidPagerItemComponent } from './components/pager/pager-item/pager-item.component';
import { AgidPagerSizeComponent } from './components/pager/pager-size/pager-size.component';
import { AgidPagerComponent } from './components/pager/pager.component';
import { TableComponent } from './components/table/table.component';
import { TableTbodyComponent } from './components/tbody/table-tbody.component';
import { TableTheadComponent } from './components/thead/table-thead.component';
import { DeepObjectPipe } from './pipes/deep-object.pipe';
import { DatePipe } from './pipes/date.pipe';
import { SharedComponentsModule } from '../components/shared-components.module';

@NgModule({
  imports: [
    CommonModule,
    TranslateModule.forChild(),
    FormModule,
    SharedComponentsModule
  ],
  declarations: [
    TableComponent,
    TableTheadComponent,
    TableTbodyComponent,
    DeepObjectPipe,
    DatePipe,
    AgidPagerComponent,
    AgidPagerItemComponent,
    AgidPagerSizeComponent
  ],
  exports: [TableComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TableModule {}
