/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { BaseComponent } from '@app/core';
import { Component, OnInit } from '@angular/core';
import { I18nService } from '@eng-ds/translate';
import { LoadingService } from '@theme/layouts/loading.services';
import { ModalService } from '@shared/modal/modal.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Adempimenti, TipoAdempimento } from '@pages/ambiti/model';
import { catchError, takeUntil } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { InfoScriva } from '@app/pages/home/enums/info-scriva';
import { of } from 'rxjs';
import { AmbitiService } from '@pages/ambiti/services/ambiti.service';
import { HelpService } from '@app/shared/services/help/help.service';

@Component({
  selector: 'app-adempimenti-list',
  templateUrl: './adempimenti-list.component.html',
  styleUrls: ['./adempimenti-list.component.scss']
})
export class AdempimentiListComponent extends BaseComponent implements OnInit {
  codMaschera: string = 'PUBWEB.MSCR001P';

  tipiAdempimenti: Adempimenti[];
  titolo: String;
  codTipoAdempimento: string;

  constructor(
    private i18n: I18nService,
    private loadingService: LoadingService,
    private router: Router,
    private route: ActivatedRoute,
    private modalService: ModalService,
    private ambitiService: AmbitiService,
    private helpService: HelpService
  ) {
    super();
  }

  ngOnInit(): void {
    this.helpService.setCodMaschera(this.codMaschera);
    this.route.data.pipe().subscribe((data: any) => {
      this.tipiAdempimenti = data.tipiAdempimento;
      this.titolo = data.tipiAdempimento[0].ambito.des_ambito;
      this.getAdempimentiConfig();
      this.loadingService.hide();
    });
  }

  getAdempimentiConfig(): void {
    for (let i = 0; i < this.tipiAdempimenti.length; i++) {
      this.codTipoAdempimento = this.tipiAdempimenti[i].cod_tipo_adempimento;
      this.ambitiService
        .getAdempimentiConfig(
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
            this.tipiAdempimenti[i].mostra_avvisi = true;
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
