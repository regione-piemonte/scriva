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
import { TranslateModule } from '@ngx-translate/core';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { BreadcrumbsComponent } from '@shared/components/breadcrumbs/breadcrumbs.component';
import { BadgesComponent } from '@shared/components/badges/badges.component';
import { SearchBarComponent } from '@shared/components/search-bar/search-bar.component';
import { TabComponent } from '@shared/components/tab/tab.component';
import { AccordionComponent } from '@shared/components/accordion/accordion.component';
import { UtilsService } from '@shared/components/utils.service';
import { IconComponent } from '@shared/components/icon/icon.component';
import { ButtonComponent } from '@shared/components/button/button.component';
import { DividerComponent } from '@shared/components/divider/divider.component';
import { DropDownComponent } from '@shared/components/dropdown/dropdown.component';
import { AccordionHeaderComponent } from '@shared/components/accordion/accordion-header/accordion-header.component';
import { CardComponent } from '@shared/components/card/card.component';
import { CardHeaderComponent } from '@shared/components/card/card-header/card-header.component';
import { CardBodyComponent } from '@shared/components/card/card-body/card-body.component';
import { CardFooterComponent } from '@shared/components/card/card-footer/card-footer.component';
import { StepperComponent } from '@shared/components/stepper/stepper.component';
import { StepperHeaderComponent } from '@shared/components/stepper/stepper-header/stepper-header.component';
import { StepperBodyComponent } from '@shared/components/stepper/stepper-body/stepper-body.component';
import { HelperComponent } from '@shared/components/helper/helper.component';
import { BackComponent } from './back/back.component';

import { TableSimpleComponent } from '@shared/table-simple/table-simple.component';

const COMPONENTS = [
  AccordionComponent,
  BadgesComponent,
  ButtonComponent,
  IconComponent,
  DividerComponent,
  DropDownComponent,
  TabComponent,
  BreadcrumbsComponent,
  SearchBarComponent,
  CardComponent,
  CardHeaderComponent,
  CardBodyComponent,
  CardFooterComponent,
  StepperComponent,
  StepperHeaderComponent,
  StepperBodyComponent,
  HelperComponent,
  TableSimpleComponent
];

@NgModule({
  declarations: [...COMPONENTS, AccordionHeaderComponent, BackComponent],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    TranslateModule.forChild()
  ],
  exports: [...COMPONENTS, BackComponent],
  providers: [UtilsService]
})
export class SharedComponentsModule {}
