/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Component, Inject, LOCALE_ID, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BaseComponent, ValueMapperService } from '@core/index';
import { LoadingService } from '@theme/layouts/loading.services';
import { Pratica } from '@pages/pratiche/model';
import { HelpService } from '@app/shared/services/help/help.service';

@Component({
  selector: 'app-dettaglio-pratica',
  templateUrl: './dettaglio-pratica.component.html',
  styleUrls: ['./dettaglio-pratica.component.scss']
})
export class DettaglioPraticaComponent extends BaseComponent implements OnInit {
  codMaschera: string = 'PUBWEB.MSCR004P';
  breadcrumbs = [];
  fromSearch = false;
  fromAvvisi = false;
  pratica: Pratica;
  id_istanza: number;
  hasGeoflag = false;
  cod_pratica : string = '';

  constructor(
    private loadingService: LoadingService,
    private route: ActivatedRoute,
    private mapper: ValueMapperService,
    private helpService: HelpService,
    @Inject(LOCALE_ID) public locale: string
  ) {
    super();
    this.loadingService.hide();
  }

  ngOnInit(): void {
    this.helpService.setCodMaschera(this.codMaschera);
    this.id_istanza = this.route.snapshot.parent.params.id_istanza;
    this.fromSearch = this.route.snapshot.parent.queryParams['fromSearch'];
    this.fromAvvisi = this.route.snapshot.parent.queryParams['fromAvvisi'];

    this.pratica = this.route.snapshot.data.pratica;
    this.cod_pratica = this.pratica.cod_pratica;
    this._checkObjectGeo();

    this._initBreadcrumbs();
  }

  private _initBreadcrumbs() {
    const snapshot = this.route.snapshot.parent.params;
    const ambito = this.mapper.getAmbitoFromAdempimento(
      snapshot['id_tipo_adempimento']
    );

    const fromSearch = this.route.snapshot.parent.queryParams['fromSearch'];
    const fromAvvisi = this.route.snapshot.parent.queryParams['fromAvvisi'];

    if (fromSearch) {
      this.breadcrumbs = [
        {
          label: 'PRACTICE.SEARCH.TITLE',
          href: '/procedimenti/ricerca',
          query: { clear: 'true' }
        },
        {
          label: 'PRACTICE.SEARCH.LIST_TITLE',
          href: '/procedimenti/ricerca'
        },
        {
          label: `${this.pratica.cod_pratica}`,
          href: null
        }
      ];
      return;
    }

    if (fromAvvisi) {
      this.breadcrumbs = [
        {
          label: `${ambito.des_ambito}`,
          href: `/ambito/${ambito.cod_ambito}`
        },
        {
          label: `Avvisi`,
          href: `/avvisi/${ambito.cod_ambito}/${snapshot['id_tipo_adempimento']}`
        },
        {
          label: `${this.pratica.cod_pratica}`,
          href: null
        }
      ];
      return;
    }

    this.breadcrumbs = [
      {
        label: `${ambito.des_ambito}`,
        href: `/ambito/${ambito.cod_ambito}`
      },
      {
        label: `${snapshot['id_tipo_adempimento']}`,
        href: `/procedimenti/${ambito.cod_ambito}/${snapshot['id_tipo_adempimento']}`
      },
      {
        label: `${this.pratica.cod_pratica}`,
        href: null
      }
    ];
  }

  private _checkObjectGeo() {
    this.pratica.oggetto_istanza.forEach((obj) => {
      if (obj.flg_georeferito) {
        this.hasGeoflag = true;
      }
    });
  }
}
