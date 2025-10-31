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
import { DropDownItem, Icon } from '@shared/models';

@Component({
  selector: 'app-dropdown',
  templateUrl: './dropdown.component.html',
  styleUrls: ['./dropdown.component.scss']
})
export class DropDownComponent implements OnInit {
  @Input() useAvatar = false;
  @Input() avatarContent = '';
  @Input() togglerIcon: Icon = {
    name: 'eng-more-items',
    type: 'eng',
    cssClass: 'cursor-pointer align-top',
    fill: '#005095',
    size: 'small'
  };

  @Input() items: DropDownItem[];
  @Input() content: any;

  show = false;

  ngOnInit(): void {}

  isHidden(item: DropDownItem, row: any): boolean {
    if (!item.isHidden) {
      return false;
    }

    return item.isHidden(row);
  }
}
