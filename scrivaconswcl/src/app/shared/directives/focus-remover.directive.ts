/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Directive, HostListener, ElementRef } from '@angular/core';

/**
 * This directive removes focus from the selectors after clicking on them
 */
@Directive({
  selector: '[appFocusRemover]'
})
export class FocusRemoverDirective {
  constructor(private elRef: ElementRef) {}

  @HostListener('click') onClick() {
    this.elRef.nativeElement.blur();
  }
}
