/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { formatDate } from '@angular/common';
import {
  AfterViewInit,
  Component,
  Inject,
  LOCALE_ID,
  OnInit
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BaseComponent } from '@app/core';
import { I18nService } from '@app/core/translate/public-api';
import { AllegatoInsert, Pratica } from '@app/pages/pratiche/model';
import { PraticheService } from '@app/pages/pratiche/services/pratiche.service';
import { LocalPagedDataSource } from '@app/shared/table/models/local-paged-data-source';
import { TablePage } from '@app/shared/table/models/table-page';
import { TableColumn } from '@swimlane/ngx-datatable';
import { Nota } from '../../interfaces/nota';
import { Subscription } from 'rxjs';
import { LocalPagedDataSourceWithEmit } from '@app/shared/table/models/local-paged-data-source-with-emit';

@Component({
  selector: 'app-istruttoria-pratica',
  templateUrl: './istruttoria-pratica.component.html',
  styleUrls: ['./istruttoria-pratica.component.scss']
})
export class IstruttoriaPraticaComponent
  extends BaseComponent
  implements OnInit, AfterViewInit
{
  pratica: Pratica;
  columns: TableColumn[] = [];
  columnsAttiProcedimento: TableColumn[] = [];
  dataSource: LocalPagedDataSource<Nota>;
  dataSourceAttiProcedimento: LocalPagedDataSourceWithEmit<any>;

  private _onLocalRowsEvalueted$: Subscription;
  showAttiProcedimento: boolean = true;

  initTable: boolean = false;
  titleRete2000: string = '';
  columnsRete2000: string[] = [];
  dataRete2000: any[][] = [];

  titleAreeProtette: string = '';
  columnsAreeProtette: string[];
  dataAreeProtette: any[][] = [];

  pipeFormatDate = {
    transform: (value: string) =>
      value ? formatDate(value, 'dd/MM/yyyy', this.locale) : ''
  };

  constructor(
    private praticheService: PraticheService,
    private i18n: I18nService,
    private route: ActivatedRoute,
    @Inject(LOCALE_ID) public locale: string
  ) {
    super();
  }

  ngOnInit(): void {
    this.pratica = this.route.snapshot.data.pratica;
    this.initDataTable();

    this._initHref();

    this.dataSource = new LocalPagedDataSource({
      observable: this.praticheService.getNotes.bind(
        this.praticheService,
        this.pratica.id_istanza
      ),
      tablePage: new TablePage()
    });

    this.dataSourceAttiProcedimento = new LocalPagedDataSourceWithEmit({
      observable: this.praticheService.getAttiFinali.bind(
        this.praticheService,
        this.pratica.id_istanza
      ),
      tablePage: new TablePage()
    });

    this._onLocalRowsEvalueted$ =
      this.dataSourceAttiProcedimento.onLocalRowsEvalueted$.subscribe({
        next: (rows: AllegatoInsert[]) => {
          // Verifico se la chiamata ha generato informazioni e gestisco il flag di visualizzazione
          this.showAttiProcedimento = rows?.length > 0;
        },
        error: (e: any) => {
          // TODO: gestire errori
        }
      });
  }

  ngAfterViewInit(): void {
    this._initTable();
  }

  initDataTable(): void {

    if (this.pratica?.oggetto_istanza[0].siti_natura_2000) {
      this.initTable = true;

      this.titleRete2000 = this.i18n.translate(
        'PRACTICE.RETE_NATURA_2000.TITLE'
      );

      this.columnsRete2000 = [
        this.i18n.translate('PRACTICE.RETE_NATURA_2000.COD'),
        this.i18n.translate('PRACTICE.RETE_NATURA_2000.DEN'),
        this.i18n.translate('PRACTICE.RETE_NATURA_2000.TIPO'),
        this.i18n.translate('PRACTICE.RETE_NATURA_2000.ENTE')
      ];

      this.pratica.oggetto_istanza[0].siti_natura_2000.forEach((reteN) => {
        const valoriRiga: any[] = [
          reteN.cod_amministrativo,
          reteN.des_sito_natura_2000,
          reteN.tipo_natura_2000.des_tipo_natura_2000,
          reteN.competenza_territorio.des_competenza_territorio
        ];
        this.dataRete2000.push(valoriRiga);
      });
    }

    if (this.pratica?.oggetto_istanza[0].aree_protette) {
      this.initTable = true;
      
      this.titleAreeProtette = this.i18n.translate(
        'PRACTICE.AREE_PROTETTE.TITLE'
      );

      this.columnsAreeProtette = [
        this.i18n.translate('PRACTICE.AREE_PROTETTE.COD'),
        this.i18n.translate('PRACTICE.AREE_PROTETTE.DEN'),
        this.i18n.translate('PRACTICE.AREE_PROTETTE.TIPO'),
        this.i18n.translate('PRACTICE.AREE_PROTETTE.ENTE')
      ];

      this.pratica.oggetto_istanza[0].aree_protette.forEach((aree) => {
        const valoriRiga: any[] = [
          aree.cod_amministrativo,
          aree.des_area_protetta,
          aree.tipo_area_protetta.des_tipo_area_protetta,
          aree.competenza_territorio.des_competenza_territorio
        ];
        this.dataAreeProtette.push(valoriRiga);
      });
    }
  }

  private _initHref() {
    if (!this.pratica?.procedimento_statale?.url_portale_esterno) {
      return;
    }

    if (
      this.pratica?.procedimento_statale?.url_portale_esterno?.startsWith(
        'http'
      )
    ) {
      this.pratica.procedimento_statale.url_portale_esternoLOCAL =
        this.pratica?.procedimento_statale?.url_portale_esterno;
      return;
    }
    this.pratica.procedimento_statale.url_portale_esternoLOCAL =
      // '//' +
      this.pratica?.procedimento_statale?.url_portale_esterno;
  }

  private _initTable(): void {
    this.columns = [
      {
        prop: 'des_nota',
        name: this.i18n.translate('PRACTICE.NOTES.TEXT'),
        cellClass: 'align-middle',
        sortable: false
      },
      {
        prop: 'data_nota',
        name: this.i18n.translate('PRACTICE.NOTES.DATE'),
        cellClass: 'align-middle',
        pipe: this.pipeFormatDate,
        sortable: false
      }
    ];

    this.columnsAttiProcedimento = [
      {
        prop: 'nome_allegato',
        name: this.i18n.translate('PRACTICE.FINAL_ACTS.NAME'),
        cellClass: 'align-middle',
        sortable: false
      },
      {
        prop: 'titolo_allegato',
        name: this.i18n.translate('PRACTICE.FINAL_ACTS.TITLE'),
        cellClass: 'align-middle',
        sortable: false
      },
      {
        prop: 'num_atto',
        name: this.i18n.translate('PRACTICE.FINAL_ACTS.NUMBER'),
        cellClass: 'align-middle',
        sortable: false
      },
      {
        prop: 'data_atto',
        name: this.i18n.translate('PRACTICE.FINAL_ACTS.DATE'),
        cellClass: 'align-middle',
        pipe: this.pipeFormatDate,
        sortable: false
      }
    ];
  }

  ngOnDestroy() {
    try {
      // Unsubscribe degli observable
      if (this._onLocalRowsEvalueted$) {
        this._onLocalRowsEvalueted$.unsubscribe();
      }
      if (this.dataSourceAttiProcedimento.onLocalRowsEvalueted$) {
        this.dataSourceAttiProcedimento.onLocalRowsEvalueted$.unsubscribe();
      }
    } catch (e) {}
  }
}
