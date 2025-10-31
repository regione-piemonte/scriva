/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
// tslint:disable: directive-selector
import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[popverHost]',
})
export class PopoverHostDirective {
  constructor(public viewContainerRef: ViewContainerRef) {}
}
