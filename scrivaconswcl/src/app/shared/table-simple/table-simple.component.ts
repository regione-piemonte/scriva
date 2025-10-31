/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
/* tslint:disable:semicolon */
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-table-simple',
  templateUrl: './table-simple.component.html',
  styleUrls: ['./table-simple.component.scss']
})
export class TableSimpleComponent implements OnInit {
  @Input() title: string;
  @Input() columns = [];
  @Input() data;

  ngOnInit() {}

  constructor() {}
}
