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
import { Icon } from 'app/shared/models/';

@Component({
  selector: 'app-tab',
  templateUrl: './tab.component.html',
  // styleUrls: ['./tab.component.scss']
})
export class TabComponent {

  @Input() tabElements: {
    title: string,
    link: string,
    icon?: Icon,
    disabled?: boolean,
  }[];
  @Input() activeElement: string;

  changeActive(active): void {
    this.activeElement = active;
  }

}
