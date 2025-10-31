/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { HelpService } from './../../shared/services/help/help.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BaseComponent } from '../../core';
import { LoadingService } from '../../theme/layouts/loading.services';
import { HomeService } from './services/home.service';
import { catchError, takeUntil } from 'rxjs/operators';
import { of } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { InfoScriva } from './enums/info-scriva';
import { Adempimenti, TipoAdempimento } from '../ambiti/model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent extends BaseComponent implements OnInit {
  codMaschera: string = 'PUBWEB.MSCR001P';

  adempimenti: Adempimenti[];
  codTipoAdempimento: string;
  visualizzaAvvisi = false;

  constructor(
    private loadingService: LoadingService,
    private router: Router,
    private homeService: HomeService,
    private helpService: HelpService
  ) {
    super();
  }

  ngOnInit(): void {
    this.getAdempimenti();
    this.helpService.setCodMaschera(this.codMaschera);
  }

  getAdempimenti(): void {
    this.homeService
      .getAdempimentiList()
      .pipe(
        catchError((e: HttpErrorResponse) => {
          this.loadingService.hide();
          return of(null);
        }),
        takeUntil(this.destroy$)
      )
      .subscribe((data) => {
        this.adempimenti = data;
        this.loadingService.hide();
        this.getAdempimentiCongif();
      });
  }

  getAdempimentiCongif(): void {
    for (let i = 0; i < this.adempimenti.length; i++) {
      this.codTipoAdempimento = this.adempimenti[i].cod_tipo_adempimento;
      this.homeService
        .getAdempimentiByCodInfo(
          this.codTipoAdempimento,
          InfoScriva.public_notice_view
        )
        .pipe(
          catchError((e: HttpErrorResponse) => {
            this.loadingService.hide();
            return of(null);
          }),
          takeUntil(this.destroy$)
        )
        .subscribe((response) => {
          if (response) {
            this.adempimenti[i].mostra_avvisi = true;
          }
          this.loadingService.hide();
        });
    }
  }

  onShowAlert(adempimento: TipoAdempimento): void {
    this.router.navigate([
      'avvisi',
      adempimento.ambito.cod_ambito,
      adempimento.cod_tipo_adempimento
    ]);
  }

  onNavigateList(adempimento: TipoAdempimento): void {
    
    this.router.navigate([
      'procedimenti',
      adempimento.ambito.cod_ambito,
      adempimento.cod_tipo_adempimento
    ]);
  }
  
}
