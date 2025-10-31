/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Component, OnDestroy, OnInit } from '@angular/core';
import { MenuService } from '@core/services';
import { Adempimenti, TipoAdempimento } from '@pages/ambiti/model';
import { BaseComponent } from '@core/components';
import { catchError, takeUntil } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { LoadingService } from '@theme/layouts/loading.services';
import { Router } from '@angular/router';
import { NotificationService } from '@shared/notification/notification.service';
import { I18nService } from '@eng-ds/translate';
import { AlertService } from '@shared/alert/alert.service';
import { HomeService } from '@pages/home/services/home.service';
import { HelpService } from '@app/shared/services/help/help.service';

@Component({
  selector: 'app-service-home',
  templateUrl: './service-home.component.html',
  styleUrls: ['./service-home.component.scss']
})
export class ServiceHomeComponent
  extends BaseComponent
  implements OnInit, OnDestroy
{
  codMaschera: string = 'PUBWEB.MSCR001P';

  mappedResponse: Map<string, { adempimenti: Adempimenti[]; config: any }> =
    new Map<string, { adempimenti: Adempimenti[]; config: any }>();
  codTipoAdempimento: string;
  visualizzaAvvisi = false;

  ambitoColor = {
    ambiente: '#03B1D1',
    'attività-estrattive': '#5D008E'
  };

  constructor(
    private menu: MenuService,
    private loadingService: LoadingService,
    private router: Router,
    private notificationService: NotificationService,
    private i18n: I18nService,
    private alertService: AlertService,
    private homeService: HomeService,
    private helpService: HelpService
  ) {
    super();
    menu.hideMenu();
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
        this.mapAdempimenti(data);
        this.loadingService.hide();
      });
  }

  mapAdempimenti(data: Adempimenti[]) {
    data.forEach((a) => {
      let ambito = this.mappedResponse.get(a.ambito.des_ambito);

      if (!ambito) {
        const key = a.ambito.des_ambito.toLowerCase().replace(' ', '-');
        ambito = {} as any;
        ambito.adempimenti = [];
        ambito.config = {
          title: a.ambito.des_ambito,
          color: this.ambitoColor[key],
          icon: 'assets/images/eng-ambito-' + key + '.png'
        };
      }

      const adempimenti = [...ambito.adempimenti];
      adempimenti.push(a);
      ambito.adempimenti = [...adempimenti];
      this.mappedResponse.set(a.ambito.des_ambito, ambito);
    });
    // console.log(this.mappedResponse);
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

  ngOnDestroy() {
    this.menu.showMenu();
    super.ngOnDestroy();
  }
}
