/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import {
  AfterViewInit,
  Component,
  Inject,
  OnInit,
  TemplateRef,
  ViewChild,
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ColumnMode } from '@swimlane/ngx-datatable';
import { catchError, switchMap } from 'rxjs/operators';
import { forkJoin, Observable, of, throwError } from 'rxjs';
import { NgxSpinnerService } from 'ngx-spinner';

import { CONFIG } from 'src/app/shared/config.injectiontoken';
import {
  AuthStoreService,
  ConfigurazioniScrivaService,
  HelpService,
  IstanzaService,
  MessageService,
  PagamentiService,
  StepManagerService,
} from 'src/app/shared/services';
import { AttoreGestioneIstanzaEnum, RegexUtil } from 'src/app/shared/utils';
import { Help, Istanza, StepConfig } from 'src/app/shared/models';
import {
  Allegato,
  AllegatoLight,
  PagamentoIstanza,
  StatoPagamento,
  TipoAllegato,
} from '../../../models';
import { AllegatiService } from '../../../services';
import { StepperIstanzaComponent } from 'src/app/shared/components/stepper-istanza/stepper-istanza.component';
import { PresentazioneIstanzaService } from '../../../services/presentazione-istanza/presentazione-istanza.service';
import {
  IConfigsSaveDataQuadro,
  IConfigsSaveQuadro,
  IPrepareDatiRiepilogo,
  RequestSaveBodyQuadro,
} from 'src/app/shared/components/stepper-istanza/utilities/stepper-istanza.interfaces';

interface PagamentoTableElement {
  idPagamentoPrincipale?: number;
  data_inserimento: Date;
  importi_dovuti?: {
    importo_dovuto: number;
    flg_non_dovuto: boolean;
    id_tipo_pagamento: number;
  }[];
  aut_competente: string;
  stato_pagamento: StatoPagamento;
  des_pagamenti: string[];
  flg_attivazioni: boolean[];
  IUV_list: string[];
  IUBD_list: string[];
  ricevute: { uuidIndex: string; nomeAllegato: string }[];
}

@Component({
  selector: 'app-pagamenti',
  templateUrl: './pagamenti.component.html',
  styleUrls: ['./pagamenti.component.scss'],
})
export class PagamentiComponent
  extends StepperIstanzaComponent
  implements OnInit, AfterViewInit
{
  @ViewChild('dateTemplate') dateTemplate: TemplateRef<any>;
  @ViewChild('importoTemplate') importoTemplate: TemplateRef<any>;
  @ViewChild('statoTemplate') statoTemplate: TemplateRef<any>;
  @ViewChild('descrizioneTemplate') descrizioneTemplate: TemplateRef<any>;
  @ViewChild('metodoTemplate') metodoTemplate: TemplateRef<any>;
  @ViewChild('iuvTemplate') iuvTemplate: TemplateRef<any>;
  @ViewChild('iubdTemplate') iubdTemplate: TemplateRef<any>;
  @ViewChild('ricevutaTemplate') ricevutaTemplate: TemplateRef<any>;

  codMaschera = '.MSCR017F';

  gestioneEnum = AttoreGestioneIstanzaEnum;
  attoreGestioneIstanza = AttoreGestioneIstanzaEnum.WRITE as string;

  istanza: Istanza;
  // qdr_pagamenti;

  helpMaschera: Help[];
  scrivaEmail: string;
  fileExtensionsArray = [];
  acceptedFileTypes = '';
  confImportoOneriFrase = '';
  tipologiaRT: TipoAllegato;

  today = new Date();
  pagamentoForm: FormGroup;

  pagamentiIstanza: PagamentoIstanza[];
  pagamentiAltriCanali: PagamentoIstanza[] = [];
  importiInseritiManualmente: {
    idPagamentoPrincipale: number;
    importi_dovuti_index: number;
    importo: number;
  }[] = [];

  pagamentiTableSource: PagamentoTableElement[];
  columns = [];
  ColumnMode = ColumnMode;
  emptyTableMessage =
    'Ti preghiamo di caricare i pagamenti effettuati nella sezione sottostante';

  pagamentiSelectList: PagamentoIstanza[] = [];
  altriCanaliTableTitle = '<strong>Riepilogo ricevute</strong>';
  altriCanaliColumns = [
    { label: 'Data pagamento', properties: ['data_effettivo_pagamento'] },
    { label: 'Importo', properties: ['importo_pagato'] },
    {
      label: 'Descrizione',
      properties: ['riscossione_ente.des_pagamento_verso_cittadino'],
    },
    {
      label: 'Ente',
      properties: [
        'riscossione_ente.adempi_tipo_pagamento.ente_creditore.denominazione_ente_creditore',
      ],
    },
    { label: 'IUV', properties: ['iuv'] },
    { label: 'IUBD', properties: ['iubd'] },
  ];

  saveWithPut = false;

  constructor(
    private route: ActivatedRoute,
    private pagamentiService: PagamentiService,
    private allegatiService: AllegatiService,
    private configurazioniService: ConfigurazioniScrivaService,
    private fb: FormBuilder,
    presentazioneIstanzaService: PresentazioneIstanzaService,
    @Inject(CONFIG) injConfig: StepConfig,
    messageService: MessageService,
    helpService: HelpService,
    istanzaService: IstanzaService,
    authStoreService: AuthStoreService,
    stepManagerService: StepManagerService,
    spinner: NgxSpinnerService
  ) {
    super(
      presentazioneIstanzaService,
      injConfig,
      messageService,
      helpService,
      istanzaService,
      authStoreService,
      stepManagerService,
      spinner
    );
    this.setVisibilityButtonNext(true);
  }

  ngOnInit() {
    this.spinner.show();
    this.buildForm();
    this.helpService.setCodMaschera(this.codMaschera);
    this.helpService.setCodContesto(undefined);
    const codAdempimento = this.route.snapshot.paramMap.get('codAdempimento');
    this._initDataPagamento(codAdempimento);
  }

  private _initDataPagamento(codAdempimento: string) {
    const getFileExtensions =
      this.allegatiService.getAcceptedFileTypesByCodAdempimento(codAdempimento);
    const getCategorieSistemaAllegati =
      this.allegatiService.getTipologieAllegatoByCodAdempimentoCodCategoria(
        codAdempimento,
        'sys'
      );
    const getHelpMaschera = this.helpService.getHelpByChiave('MSCR017F');
    const getScrivaEmail = this.configurazioniService.getConfigurazione(
      'SCRIVA_EMAIL_ASSISTENZA_VIA'
    );
    const getImportoOneriFrase = this.configurazioniService.getConfigurazione(
      'SCRIVA_IMPORTO_ONERI_FRASE'
    );
    const getPagamentiIstanza = this.pagamentiService
      .getPagamentiIstanza(this.idIstanza)
      .pipe(
        catchError((err) => {
          if (err.status === 404) {
            return of([]);
          } else {
            return throwError(err);
          }
        })
      );

    forkJoin([
      getFileExtensions,
      getCategorieSistemaAllegati,
      getHelpMaschera,
      getScrivaEmail,
      getImportoOneriFrase,
      getPagamentiIstanza,
    ]).subscribe(
      (res) => {
        res[0].map((ext) => {
          this.fileExtensionsArray.push(ext.estensione_allegato);
          if (this.acceptedFileTypes.length > 0) {
            this.acceptedFileTypes += ', ';
          }
          this.acceptedFileTypes += '.' + ext.estensione_allegato;
        });
        this.tipologiaRT = res[1].find(
          (tipo) => tipo.tipologia_allegato.cod_tipologia_allegato === 'RT_PAG'
        );
        this.helpMaschera = res[2];
        this.scrivaEmail = res[3][0]['valore'];
        this.confImportoOneriFrase = res[4][0]['valore'];
        this.pagamentiIstanza = res[5];
        if (this.pagamentiIstanza.length === 0) {
          this.emptyTableMessage = 'Nessun pagamento è ad ora dovuto';
        }
        this.loadData();
      },
      (err) => {
        this._messageService.showMessage('E100', 'topCard', false);
      }
    );
  }

  ngAfterViewInit() {
    this._setColumns();
  }

  private _setColumns() {
    this.columns = [
      {
        name: 'Data inserim.',
        prop: 'data_inserimento',
        minWidth: 100,
        cellTemplate: this.dateTemplate,
      },
      {
        name: 'Importo',
        sortable: false,
        minWidth: 275,
        cellTemplate: this.importoTemplate,
      },
      {
        name: 'Stato pagamenti',
        prop: 'stato_pagamento.des_stato_pagamento',
        minWidth: 150,
        cellTemplate: this.statoTemplate,
      },
      {
        name: 'Descrizione',
        sortable: false,
        minWidth: 250,
        cellTemplate: this.descrizioneTemplate,
      },
      {
        name: 'Autorità competente',
        sortable: false,
        minWidth: 250,
        prop: 'aut_competente',
      },
      {
        name: 'Metodi di pagamento',
        sortable: false,
        minWidth: 150,
        cellTemplate: this.metodoTemplate,
      },
      {
        name: 'IUV',
        sortable: false,
        minWidth: 250,
        cellTemplate: this.iuvTemplate,
      },
      {
        name: 'IUBD',
        sortable: false,
        minWidth: 250,
        cellTemplate: this.iubdTemplate,
      },
      {
        name: 'Ricevuta',
        sortable: false,
        minWidth: 150,
        cellTemplate: this.ricevutaTemplate,
      },
    ];
  }

  getPagamenti() {
    this.spinner.show();
    this.pagamentiService.getPagamentiIstanza(this.idIstanza).subscribe(
      (res) => {
        this.pagamentiIstanza = res;
        this.buildTableSource();
        this.buildPagamentiSelectList();
        this.setStepCompletion();
        this.spinner.hide();
      },
      (err) => {
        this.showErrorsQuadroConCodeENoCode(err, 'topCard');
      }
    );
  }

  buildForm() {
    this.pagamentoForm = this.fb.group({
      dataPagamento: [null, Validators.required],
      importo: [
        null,
        [Validators.required, Validators.pattern(RegexUtil.Price)],
      ],
      pagamento: [null, Validators.required],
      iuv: [null, Validators.required],
      iubd: null,
    });
  }

  loadData() {
    // Gestisco la logica per il set della modalità di gestione del quadro
    this.setGestioneQuadro();

    if (this.attoreGestioneIstanza === this.gestioneEnum.WRITE) {
      this.pagamentoForm.enable();
    } else {
      this.pagamentoForm.disable();
    }

    this.istanzaService.getIstanzaById(this.idIstanza).subscribe(
      (res) => {
        this.istanza = res;
        const jsonData = JSON.parse(res.json_data);
        this.qdr_riepilogo = jsonData.QDR_RIEPILOGO;

        this.buildTableSource();
        this.buildPagamentiSelectList();
        this.checkFeedback();
        this.setStepCompletion(true);
        this.startGetRtCycle();
        this.spinner.hide();
      },
      (err) => {
        this.showErrorsQuadroConCodeENoCode(err, 'topCard');
      }
    );
  }

  checkFeedback() {
    const errorCode = this.pagamentiService.getPagamentoErrorCode();
    if (errorCode) {
      this._messageService.showMessage(errorCode, 'topCard', false);
      this.pagamentiService.setPagamentoErrorCode(null);
    }
  }

  buildTableSource() {
    this.pagamentiTableSource = [];
    // create rows for main table
    this.pagamentiIstanza
      .filter(
        (pag) =>
          pag.riscossione_ente.adempi_tipo_pagamento.ente_creditore
            .flg_aderisce_piemontepay === true
      )
      .forEach((elem) => {
        if (!elem.id_onere_padre) {
          let ricevuta = {
            uuidIndex: null,
            nomeAllegato: null,
          };
          if (elem.allegato_istanza) {
            ricevuta = {
              uuidIndex: elem.allegato_istanza.uuid_index,
              nomeAllegato: elem.allegato_istanza.nome_allegato,
            };
          }
          let descrizione = elem.riscossione_ente.des_pagamento_verso_cittadino;
          if (
            !elem.importo_dovuto &&
            elem.riscossione_ente.url_oneri_previsti
          ) {
            const denomEnte =
              elem.riscossione_ente.adempi_tipo_pagamento.ente_creditore
                .denominazione_ente_creditore;
            descrizione = this.confImportoOneriFrase.replace(
              '[PH_scriva_r_riscossione_ente.des_pagamento_verso_cittadino]',
              descrizione
            );
            descrizione = descrizione.replace(
              '[PH_scriva_r_adempi_competenza.url_oneri_previsti]',
              '<a href="' +
                elem.riscossione_ente.url_oneri_previsti +
                '" target="_blank">' +
                denomEnte +
                '</a>'
            );
          }
          const tableElement: PagamentoTableElement = {
            idPagamentoPrincipale: elem.id_pagamento_istanza,
            data_inserimento: elem.data_inserimento_pagamento,
            importi_dovuti: [
              {
                importo_dovuto: elem.importo_dovuto,
                flg_non_dovuto: false,
                id_tipo_pagamento:
                  elem.riscossione_ente.adempi_tipo_pagamento.tipo_pagamento
                    .id_tipo_pagamento,
              },
            ],
            aut_competente:
              elem.riscossione_ente.adempi_tipo_pagamento.competenza_territorio
                ?.des_competenza_territorio,
            stato_pagamento: elem.stato_pagamento,
            des_pagamenti: [descrizione],
            flg_attivazioni: [elem.riscossione_ente.flg_attiva_pagamento],
            // flg_attivazioni: [true], /* MOCK */
            IUV_list: [elem.iuv],
            IUBD_list: [elem.iubd],
            ricevute: [ricevuta],
          };
          this.pagamentiTableSource.push(tableElement);
        }
      });

    this.pagamentiTableSource.sort(
      (itemA, itemB) =>
        itemA.data_inserimento.valueOf() - itemB.data_inserimento.valueOf()
    );
    this.pagamentiTableSource.sort(
      (itemA, itemB) =>
        itemA.importi_dovuti[0].id_tipo_pagamento -
        itemB.importi_dovuti[0].id_tipo_pagamento
    );
    this.pagamentiTableSource.sort(
      (itemA, itemB) =>
        itemA.stato_pagamento.id_stato_pagamento -
        itemB.stato_pagamento.id_stato_pagamento
    );
    // todo: sort also inside group (dunno how)
    // sort by id_gruppo_pagamento

    // add data to rows (main table)
    this.pagamentiIstanza.forEach((elem) => {
      if (elem.id_onere_padre) {
        let ricevuta = {
          uuidIndex: null,
          nomeAllegato: null,
        };
        if (elem.allegato_istanza) {
          ricevuta = {
            uuidIndex: elem.allegato_istanza.uuid_index,
            nomeAllegato: elem.allegato_istanza.nome_allegato,
          };
        }

        let descrizione = elem.riscossione_ente.des_pagamento_verso_cittadino;
        if (!elem.importo_dovuto && elem.riscossione_ente.url_oneri_previsti) {
          const denomEnte =
            elem.riscossione_ente.adempi_tipo_pagamento.ente_creditore
              .denominazione_ente_creditore;
          descrizione = this.confImportoOneriFrase.replace(
            '[PH_scriva_r_riscossione_ente.des_pagamento_verso_cittadino]',
            descrizione
          );
          descrizione = descrizione.replace(
            '[PH_scriva_r_adempi_competenza.url_oneri_previsti]',
            '<a href="' +
              elem.riscossione_ente.url_oneri_previsti +
              '" target="_blank">' +
              denomEnte +
              '</a>'
          );
        }

        const index = this.pagamentiTableSource.findIndex(
          (item) => item.idPagamentoPrincipale === elem.id_onere_padre
        );
        if (index > -1) {
          if (elem.stato_pagamento.cod_stato_pagamento === '010') {
            this.pagamentiTableSource[index].stato_pagamento =
              elem.stato_pagamento;
          }
          this.pagamentiTableSource[index].importi_dovuti.push({
            importo_dovuto: elem.importo_dovuto,
            flg_non_dovuto: false,
            id_tipo_pagamento: null,
          });
          this.pagamentiTableSource[index].des_pagamenti.push(descrizione);
          this.pagamentiTableSource[index].flg_attivazioni.push(
            elem.riscossione_ente.flg_attiva_pagamento
          );
          this.pagamentiTableSource[index].IUV_list.push(elem.iuv);
          this.pagamentiTableSource[index].IUBD_list.push(elem.iubd);
          this.pagamentiTableSource[index].ricevute.push(ricevuta);
        }
      }
    });

    // altri-canali rows
    // todo: format date ( see getFormattedDate() ) ?
    this.pagamentiAltriCanali = this.pagamentiIstanza.filter(
      (pagamento) => pagamento.ind_tipo_inserimento === 'utente'
    );

    this.removeDuplicate();
  }

  removeDuplicate() {
    this.pagamentiTableSource = this.pagamentiTableSource.filter(
      (pagamento) => {
        const duplicateFlag = this.pagamentiAltriCanali.some(
          (pag) => pag.id_pagamento_istanza === pagamento.idPagamentoPrincipale
        );
        if (
          !duplicateFlag ||
          (duplicateFlag && pagamento.flg_attivazioni.length > 1)
        ) {
          return pagamento;
        }
      }
    );
  }

  buildPagamentiSelectList() {
    this.pagamentiSelectList = this.pagamentiIstanza.filter(
      (pag) =>
        pag.riscossione_ente.adempi_tipo_pagamento.ente_creditore
          .flg_aderisce_piemontepay === false &&
        pag.stato_pagamento.cod_stato_pagamento !== '040'
    );

    if (this.pagamentiSelectList.length > 0) {
      this.pagamentoForm.enable();
    } else {
      this.pagamentoForm.disable();
    }
  }

  startGetRtCycle() {
    const check = this.pagamentiIstanza.some(
      (pagamento) => pagamento.stato_pagamento.id_stato_pagamento === 2
    );
    if (!check) {
      return;
    }

    this.pagamentiService.getRT(this.idIstanza).subscribe(
      (res) => {
        if (res.status === 200) {
          this.getPagamenti();

          const timer = setTimeout(() => {
            this.startGetRtCycle();
          }, 30000);
        } else {
          const timer = setTimeout(() => {
            this.startGetRtCycle();
          }, 30000);
        }
      },
      (err) => {
        const timer = setTimeout(() => {
          this.startGetRtCycle();
        }, 30000);
      }
    );
  }

  getFormattedDate(date) {
    return date ? new Date(date).toLocaleDateString() : '';
  }

  onHelpClicked(chiave: string) {
    const modalContent =
      this.helpMaschera.find(
        (help) =>
          help.tipo_help.cod_tipo_help === 'MDL' &&
          help.chiave_help === `${this.componente}${this.codMaschera}.${chiave}`
      )?.des_testo_help || 'Help non trovato...';

    this._messageService.showConfirmation({
      title: 'HELP',
      codMess: null,
      content: modalContent,
      buttons: [],
    });
  }

  onFlagNonDovuto(idPagamentoPrincipale: number, checked: boolean) {
    if (!checked) {
      this.setFlagNonDovuto(idPagamentoPrincipale, false);
    } else {
      this._messageService.showConfirmation({
        title: 'Attenzione',
        codMess: 'A024',
        buttons: [
          {
            label: 'ANNULLA',
            type: 'btn-link',
            callback: () => {
              this.setFlagNonDovuto(idPagamentoPrincipale, false);
              const cbElement = document.getElementById(
                'cbNonDovuto-' + idPagamentoPrincipale
              );
              cbElement['checked'] = false;
            },
          },
          {
            label: 'CONFERMA',
            type: 'btn-primary',
            callback: () => {
              this.setFlagNonDovuto(idPagamentoPrincipale, true);
            },
          },
        ],
      });
    }
  }

  setFlagNonDovuto(idPagamentoPrincipale: number, checked: boolean) {
    const indexPagamento = this.pagamentiTableSource.findIndex(
      (pagamento) => pagamento.idPagamentoPrincipale === idPagamentoPrincipale
    );
    const indexImporto = this.pagamentiTableSource[
      indexPagamento
    ].importi_dovuti.findIndex((importo) => importo.id_tipo_pagamento === 1);
    this.pagamentiTableSource[indexPagamento].importi_dovuti[
      indexImporto
    ].flg_non_dovuto = checked;
    this.pagamentiIstanza.find(
      (pagamento) => pagamento.id_pagamento_istanza === idPagamentoPrincipale
    ).flg_non_dovuto = checked;
  }

  getTotalAmount(id: number) {
    let sum = 0;
    const element = this.pagamentiTableSource.find(
      (el) => el.idPagamentoPrincipale === id
    );
    if (element) {
      element.importi_dovuti.forEach((importo, index) => {
        if (!element.importi_dovuti[index].flg_non_dovuto) {
          sum += importo.importo_dovuto;
        }
      });
    }
    return sum;
  }

  setImporto(value: any, id: number, index: number) {
    const importoRegEx = new RegExp(RegexUtil.Price);
    const check = importoRegEx.test(value);
    if (!check) {
      this._messageService.showConfirmation({
        title: 'ATTENZIONE',
        codMess: null,
        content: "Il formato dell'importo non è valido.",
        buttons: [
          {
            label: 'CHIUDI',
            type: 'btn-primary single',
            callback: () => {},
          },
        ],
      });
      return;
    }

    const valueReplace = value.replace(',', '.');
    const importo = Number(valueReplace);
    this.importiInseritiManualmente.push({
      idPagamentoPrincipale: id,
      importi_dovuti_index: index,
      importo,
    });
  }

  checkAttivazione(id: number) {
    const element = this.pagamentiTableSource.find(
      (el) => el.idPagamentoPrincipale === id
    );
    const check = !element.flg_attivazioni.some((flag) => !flag);
    return check;
  }

  downloadRicevuta(ricevuta) {
    if (this.attoreGestioneIstanza !== this.gestioneEnum.WRITE) {
      return;
    }

    this.allegatiService.getAllegatoByUuid(ricevuta.uuidIndex).subscribe(
      (res) => {
        const blob = new Blob([res], { type: 'application/pdf' });
        const url = window.URL.createObjectURL(blob);
        const fileName = ricevuta.nomeAllegato;

        const a = document.createElement('a');
        a.href = url;
        a.download = fileName;
        a.click();
        window.URL.revokeObjectURL(url);
        a.remove();
        this.spinner.hide();
      },
      (err) => {
        this._messageService.showMessage('E100', 'topCard', true);
      }
    );
  }

  onCaricaRicevuta(fileInput) {
    if (!this.pagamentoForm.valid) {
      this._messageService.showMessage('E001', 'altriCanali', true);
      return;
    }
    fileInput.click();
  }

  uploadRicevuta(file) {
    if (!file || file.length < 1) {
      return;
    }

    const split = file[0].name.split('.');
    const checkExtension = this.fileExtensionsArray.some(
      (el) => el === split[split.length - 1]
    );
    if (!checkExtension) {
      this._messageService.showMessage('E019', 'altriCanali', true);
      return;
    }
    this.spinner.show();
    const data = {
      id_istanza: this.idIstanza,
      tipologia_allegato: this.tipologiaRT.tipologia_allegato,
      flg_riservato: this.tipologiaRT.flg_riservato,
      cod_allegato: 'test',
      nome_allegato: file[0].name,
      note: null,
    };

    this.allegatiService.postAllegati(JSON.stringify(data), file[0]).subscribe(
      (res) => {
        this.putPagamenti(res, true);
      },
      (err) => {
        this.showErrorsQuadroConCodeENoCode(err, 'altriCanali');
      }
    );
  }

  /**
   * Funzione per la modifica del pagamento
   * @param elemento può provenire dal
   * @param isAllegato identifica se è un allegato oppure una riga della tabella
   */
  private putPagamenti(
    elemento: PagamentoIstanza | Allegato,
    isAllegato: boolean
  ) {
    const pagamento: PagamentoIstanza = this._buildRequestModificaPagamento(
      elemento,
      isAllegato
    );
    this.pagamentiService.putPagamentiIstanza(pagamento).subscribe(
      (resp) => {
        //se sei nella modifica dell'allegato
        if (isAllegato) {
          this.buildTableSource();
          this.buildPagamentiSelectList();
          this.setStepCompletion();
          this.pagamentoForm.reset();
          this.spinner.hide();
        } else {
          //se sei nell'elimina rt altri canali
          this._deleteAllegatoByUuid(pagamento, true);
        }
      },
      (err) => {
        this.showErrorsQuadroConCodeENoCode(err, 'altriCanali');
        //se errore è nell'elimina del rt altri canali
        if (!isAllegato) {
          this._deleteAllegatoByUuid(pagamento);
        }
      }
    );
  }

  /**
   * Funzione per la costruzione della request per la modifica del pagamento
   * @param elemento valorizzato con la risposta della post degli allegati oppure dalla riga della tabella da eliminare
   * @param isAllegato boolean per indicare se è un allegato oppure una riga della tabella
   * @returns pagamento da salvare
   */
  private _buildRequestModificaPagamento(
    elemento: Allegato | PagamentoIstanza,
    isAllegato: boolean
  ): PagamentoIstanza {
    let pagamento: PagamentoIstanza | null = null;
    // se è un allegato
    if (isAllegato) {
      pagamento = this.pagamentoForm.get('pagamento').value;
      pagamento.data_effettivo_pagamento = new Date(
        this.pagamentoForm.get('dataPagamento').value
      );
      pagamento.importo_pagato = this.pagamentoForm.get('importo').value;
      pagamento.iuv = this.pagamentoForm.get('iuv').value;
      pagamento.iubd = this.pagamentoForm.get('iubd').value;
      pagamento.stato_pagamento = {
        id_stato_pagamento: 4,
        cod_stato_pagamento: '040',
        des_stato_pagamento: 'Pagato',
      };
      pagamento.allegato_istanza = this.convertAllegatoInAllegatoLight(
        elemento as Allegato
      );
      pagamento.ind_tipo_inserimento = 'utente';
    } else {
      // se è riga della tabella
      pagamento = elemento as PagamentoIstanza;
      pagamento.data_effettivo_pagamento = null;
      pagamento.importo_pagato = null;
      pagamento.iuv = null;
      pagamento.iubd = null;
      pagamento.stato_pagamento = {
        id_stato_pagamento: 1,
        cod_stato_pagamento: '010',
        des_stato_pagamento: 'Da effettuare',
      };
      pagamento.allegato_istanza = null;
      pagamento.ind_tipo_inserimento = null;
    }

    return pagamento;
  }
  /**
   * Funzione per eliminare allegato tramite uuid
   * @param pagamento
   * @param isToRefresh condizione per far aggiornare i dati in tabella al seguito dell'eliminazione
   */
  private _deleteAllegatoByUuid(
    pagamento: PagamentoIstanza,
    isToRefresh: boolean = false
  ) {
    const uuidIndex = pagamento.allegato_istanza.uuid_index;
    this.allegatiService.deleteAllegatoByUuid(uuidIndex).subscribe(
      (resp) => {
        if (isToRefresh) {
          this.buildTableSource();
          this.buildPagamentiSelectList();
          this.setStepCompletion();
          this.spinner.hide();
        }
        console.log(
          '# Il file precedentemente caricato è stato eliminato correttamente.'
        );
      },
      (error) => {
        if (isToRefresh) {
          this.showErrorsQuadroConCodeENoCode(error, 'altriCanali');
        }
        console.log(
          `Errore durante l\'eliminazione del file. Non sarà possibile caricare un altro file con lo stesso nome ${pagamento.allegato_istanza.nome_allegato}`
        );
      }
    );
  }

  downloadRtAltriCanali(event) {
    const pagamento: PagamentoIstanza = event.record;
    const ricevuta = {
      uuidIndex: pagamento.allegato_istanza.uuid_index,
      nomeAllegato: pagamento.allegato_istanza.nome_allegato,
    };

    this.downloadRicevuta(ricevuta);
  }

  deleteRtAltriCanali(event: { record: PagamentoIstanza; index: number }) {
    this.putPagamenti(event.record, false);
  }

  convertAllegatoInAllegatoLight(allegato: Allegato) {
    const allegatoLight: AllegatoLight = {
      id_allegato_istanza: allegato.id_allegato_istanza,
      id_istanza: allegato.id_istanza,
      id_tipologia_allegato: allegato.tipologia_allegato.id_tipologia_allegato,
      id_tipo_integra_allegato:
        allegato.tipo_integra_allegato?.id_tipo_integra_allegato,
      uuid_index: allegato.uuid_index,
      flg_riservato: allegato.flg_riservato,
      cod_allegato: allegato.cod_allegato,
      nome_allegato: allegato.nome_allegato,
      dimensione_upload: allegato.dimensione_upload,
      data_upload: allegato.data_upload,
      flg_cancellato: allegato.flg_cancellato,
      ind_firma: allegato.ind_firma,
      note: allegato.note,
    };
    return allegatoLight;
  }

  onPaga(id: number) {
    const pagamento = this.pagamentiIstanza.find(
      (pag) => pag.id_pagamento_istanza === id
    );

    const pagamentoTable = this.pagamentiTableSource.find(
      (pag) => pag.idPagamentoPrincipale === id
    );
    const checkIndex = pagamentoTable.importi_dovuti.findIndex(
      (elem) => !elem.importo_dovuto
    );
    if (checkIndex > 0) {
      const modalContent =
        "Uno degli importi dovuti non è stato trovato. Contattare l'assistenza.";
      this._messageService.showConfirmation({
        title: 'Errore',
        codMess: null,
        content: modalContent,
        buttons: [
          {
            label: 'CHIUDI',
            type: 'btn-primary single',
            callback: () => {},
          },
        ],
      });
      return;
    } else if (checkIndex === 0) {
      this._messageService.showConfirmation({
        title: 'Attenzione',
        codMess: 'A023',
        buttons: [
          {
            label: 'ANNULLA',
            type: 'btn-link',
            callback: () => {},
          },
          {
            label: 'CONFERMA',
            type: 'btn-primary',
            callback: () => {
              const importoManuale = this.importiInseritiManualmente.find(
                (element) => element.idPagamentoPrincipale === id
              );
              pagamento.importo_dovuto = importoManuale.importo;
              this.avviaPagamento(pagamento);
            },
          },
        ],
      });
    } else {
      // === -1
      this.avviaPagamento(pagamento);
    }
  }

  avviaPagamento(pagamento: PagamentoIstanza) {
    this.spinner.show();
    this.pagamentiService.avviaPagamento(pagamento).subscribe(
      (res) => {
        if (res['url']) {
          window.open(res['url'], '_self');
        } else {
          const swapPh = {
            ph: '{PH_SCRIVA_EMAIL_ASSISTENZA_VIA}',
            swap: this.scrivaEmail,
          };
          this._messageService.showMessage('I015', 'topCard', true, swapPh);
        }
      },
      (err) => {
        const swapPh = {
          ph: '{PH_SCRIVA_EMAIL_ASSISTENZA_VIA}',
          swap: this.scrivaEmail,
        };
        this._messageService.showMessage('I015', 'topCard', true, swapPh);
      }
    );
  }

  setStepCompletion(firstLoad = false) {
    if (firstLoad) {
      this.setStepCompletedEmit('PagamentiComponent', true);
    } else {
      this.istanzaService.getIstanzaById(this.idIstanza).subscribe(
        (res) => {
          this.istanza = res;
          this.salvaDatiQuadro();
          this.setStepCompletedEmit('PagamentiComponent', true);
        },
        (err) => {
          this.showErrorsQuadroConCodeENoCode(err, 'topCard');
        }
      );
    }
  }

  /**
   * @override
   */

  onAvanti() {
    if (this.attoreGestioneIstanza === this.gestioneEnum.WRITE) {
      this.istanzaService.getIstanzaById(this.idIstanza).subscribe(
        (res) => {
          this.istanza = res;

          this.salvaDatiQuadro(true);
          this.setStepCompletedEmit('PagamentiComponent', true);
        },
        (err) => {
          this.showErrorsQuadroConCodeENoCode(err, 'topCard');
        }
      );
    } else {
      this.setStepCompletedEmit('PagamentiComponent', true);
      this.goToStep(this.stepIndex);
    }
  }

  salvaDatiQuadro(onAvantiFlag = false) {
    const configs: IConfigsSaveDataQuadro = {
      datiQuadro: {
        sintesiPagamenti:
          this.istanza.des_stato_sintesi_pagamento?.toUpperCase(),
      },
      showSpinner: true,
      isPutDatiQuadro: this.saveWithPut,
      isPutDatiRiepilogo: true,
      datiRiepilogo: this.qdr_riepilogo,
    };

    this.saveDataQuadro(configs).subscribe(
      (res) => {
        this.saveWithPut = true;
        if (onAvantiFlag) {
          this.goToStep(this.stepIndex);
        }
      },
      (err) => {
        const elementId = onAvantiFlag ? 'altriCanali' : 'topCard';
        this.showErrorsQuadroConCodeENoCode(err, elementId);
      }
    );
  }

  /**
   *
   * @param configs
   * @returns Observable<any>
   * @override
   */
  protected saveDataQuadro(configs: IConfigsSaveDataQuadro): Observable<any> {
    // estraggo le configurazioni
    const { showSpinner, isPutDatiQuadro, isPutDatiRiepilogo, datiQuadro } =
      configs;
    if (showSpinner) {
      this.spinner.show();
    }
    const qdrRiepilogoConfigs: IPrepareDatiRiepilogo = {
      codQuadro: this.codQuadro,
      codTipoQuadro: this.codTipoQuadro,
      datiQuadro,
    };
    this.qdr_riepilogo = this.prepareDatiRiepilogo(qdrRiepilogoConfigs);
    //differisce dalla generica perché vengono azzerati i dati quadro
    //(da controllare, è un possibile bug)
    const reqSaveQuadroConfigs: IConfigsSaveQuadro = {
      idIstanza: this.idIstanza,
      idTemplateQuadro: this.idTemplateQuadro,
      datiQuadro: {},
      datiRiepilogo: this.qdr_riepilogo,
    };
    const requestData: RequestSaveBodyQuadro =
      this.buildRequestDataToSaveQuadro(reqSaveQuadroConfigs);

    return this.doForkjoinSalvaDatiQuadroERiepilogo(
      requestData,
      isPutDatiQuadro,
      isPutDatiRiepilogo,
      showSpinner
    );
  }

  ngOnDestroy(): void {
    super.ngOnDestroy();
  }
}
