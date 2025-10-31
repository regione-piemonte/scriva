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
import { Help } from '@app/core/interfaces/help';
import { ModalComponent } from '@app/shared/modal/models/modal.component';

@Component({
  selector: 'app-help-modal',
  templateUrl: './help-modal.component.html',
  styleUrls: ['./help-modal.component.scss']
})
export class HelpModalComponent
  extends ModalComponent
  implements OnInit
{
  @Input() helpOBJ: Help | Help[];
  @Input() placeholders: { key; value };

  desc: string;

  constructor() {
    super();
  }

  ngOnInit(): void {
    //this.desc = this.getDesc();
  }

  getDesc(): string {
    if (Array.isArray(this.helpOBJ)) {
      if (this.helpOBJ[0].placeholders) {
        return this.helpOBJ[0].des_testo_help.replace(
          this.placeholders.key,
          this.placeholders.value
        );
      } else {
        return this.combinePlaceholders(this.helpOBJ[0]?.des_testo_help);
      }
    }


    if (this.helpOBJ.placeholders) {
      return this.helpOBJ.des_testo_help.replace(
        this.placeholders.key,
        this.placeholders.value
      );
    } else {
      return this.combinePlaceholders(this.helpOBJ?.des_testo_help);
    }

  }

  combinePlaceholders(descToReplace: string) {
    if (this.placeholders) {
      let descToReturn: string = descToReplace;
      Object.entries(this.placeholders).forEach(([key, value]) => {
        if (descToReturn.includes(`{${key}}`) && value !== null) {
          let helpTextToReplace = '<ul>';
          value.forEach((v: string) => {
            helpTextToReplace = helpTextToReplace + '<li>' + v + '</li>';
          });
          helpTextToReplace = helpTextToReplace + '</ul>';
          descToReturn = descToReturn.replace(`{${key}}`, helpTextToReplace);
        }
      });
      return descToReturn;
    } else {
      return descToReplace;
    }
  }
}
