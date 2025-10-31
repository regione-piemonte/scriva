/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, OnInit } from '@angular/core';
import { AutoUnsubscribe } from 'src/app/core/components';
import { AuthStoreService, HelpService} from 'src/app/shared/services';

@Component({
  selector: 'app-contacts',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss'],
})

export class MainComponent extends AutoUnsubscribe implements OnInit {
  constructor(
    private authStoreService: AuthStoreService,
    private helpService: HelpService,
  ) {
    super();    
    this.componente = this.authStoreService.getComponente();
    if (this.componente === 'BO') {
      this.isFrontOffice = false;
    } else {
      this.isFrontOffice = true;
    }     
  }

  componente: string;
  isFrontOffice: boolean;
  codMaschera = '.MSCR029D';

  ngOnInit(){
    this.helpService.setCodMaschera(this.codMaschera);  
    this.helpService.setCodContesto(undefined);
  }
}
