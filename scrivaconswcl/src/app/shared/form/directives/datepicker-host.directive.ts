/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[appDatepickerHost]'
})
export class DatepickerHostDirective {
  constructor(public viewContainerRef: ViewContainerRef) {}
}
