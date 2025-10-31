/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-divider',
  template: `<hr
    [style.margin-top.rem]="marginTop"
    [style.margin-bottom.rem]="marginBottom"
    [style.margin-left.rem]="marginX"
    [style.margin-right.rem]="marginX"
    [style.border-top]="borderTop"
  /> `
})
export class DividerComponent {
  @Input() marginTop = 3;
  @Input() marginBottom = 3;
  @Input() marginX = 3;
  @Input() borderTop = '';
}
