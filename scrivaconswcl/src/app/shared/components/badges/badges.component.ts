/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-badges',
  templateUrl: './badges.component.html',
  styleUrls: ['./badges.component.scss']
})
export class BadgesComponent
  implements OnInit {

  @Input() filters: any;
  @Output() filtersChange: EventEmitter<any> = new EventEmitter();

  constructor() {

  }

  ngOnInit(): void {
  }

  closeFilter(keyFilter: any): void {
    this.filtersChange.emit(keyFilter);
  }

}
