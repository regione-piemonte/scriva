/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Component, Input, OnInit } from '@angular/core';
import { AccordionCard } from 'app/shared/models/';

@Component({
  selector: 'app-accordion',
  templateUrl: './accordion.component.html',
  styleUrls: ['./accordion.component.scss'],
})
export class AccordionComponent
  implements OnInit {

  @Input() card: AccordionCard;

  classes;

  constructor() {
  }

  ngOnInit(): void {
    this.classes = {
      accordion: true,
      'collapse-div': true,
      'collapse-left-icon': this.card?.header?.showLeft,
    };
  }

}
