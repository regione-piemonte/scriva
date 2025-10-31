/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { AfterViewInit, Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ColumnMode } from '@swimlane/ngx-datatable';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin, of } from 'rxjs';
import { catchError, distinctUntilChanged, switchMap, takeUntil } from 'rxjs/operators';

import { AutoUnsubscribe } from 'src/app/core/components';
import { CompetenzaTerritorio, EsitoProcedimento, Help, Istanza, NotaIstanza } from 'src/app/shared/models';
import { AdempimentoService, AuthStoreService, HelpService, IstanzaService, MessageService } from 'src/app/shared/services';
import { TipoEventoEnum } from 'src/app/shared/utils';

import { AmbitoService } from 'src/app/features/ambito/services';
import { SoggettoIstanza } from 'src/app/features/ambito/models';

import { formatDate } from '@angular/common';
import { StatoProcedimento } from 'src/app/shared/models/istanza/stato-procedimento.model';
@Component({
  selector: 'app-conclusione-procedimento',
  templateUrl: './conclusione-procedimento.component.html',
  styleUrls: ['./conclusione-procedimento.component.scss']
})
export class ConclusioneProcedimentoComponent extends AutoUnsubscribe implements OnInit, AfterViewInit {
  @ViewChild('dataTemplate') dataTemplate: TemplateRef<any>;
  @ViewChild('flgRiservataTemplate') flgRiservataTemplate: TemplateRef<any>;
  @ViewChild('azioniTemplate') azioniTemplate: TemplateRef<any>;

  codMaschera = '.MSCR012D';
  componente: string;
  // funzionario: FunzionarioAutorizzato;

  idIstanza: number;
  istanza: Istanza;
  denTitolareIstanza: string;

  helpList: Help[];
  tipoEventoEnum = TipoEventoEnum;

  esitiProcedimentoStatale: EsitoProcedimento[];
  esitiProcedura: EsitoProcedimento[];
  statiProcStatale: StatoProcedimento[];
  esiti;

  dettaglioForm: FormGroup;
  noteIstanza: NotaIstanza[] = [];
  ColumnMode = ColumnMode;
  noteTableColumns = [];

  showBtnConferma = false;
  showProcStatale = false;

  today = new Date();
  idAdempimento: number;
  listEsitoProceduraStatale = []; // todo associa modal
  listEsitoProcedura = []; // todo associa modal

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private istanzaService: IstanzaService,
    private adempimentoService: AdempimentoService,
    private ambitoService: AmbitoService,
    private authStoreService: AuthStoreService,
    private helpService: HelpService,
    private messageService: MessageService,
    private spinner: NgxSpinnerService
  ) {
    super();
    const state = this.router.getCurrentNavigation().extras.state;
    if (state) {
      this.idIstanza = state['idIstanza'];
    }
    if (!this.idIstanza) {
      this.goToSearchPage();
      return;
    }
    this.componente = this.authStoreService.getComponente(); // should always be BO
    // this.funzionario = this.authStoreService.getFunzionario();
  }

  ngOnInit() {
    this.helpService.setCodMaschera(this.codMaschera);
    this.buildForm();
    this.loadData();
  }

  ngAfterViewInit() {
    this.noteTableColumns = [
      { name: 'Cognome', minWidth: 100, prop: 'funzionario.cognome_funzionario' },
      { name: 'Nome', minWidth: 100, prop: 'funzionario.nome_funzionario' },
      { name: 'Nota', minWidth: 200, prop: 'des_nota' },
      { name: 'Data inserimento', minWidth: 100, cellTemplate: this.dataTemplate, prop: 'data_nota'},
      { name: 'Nota riservata', minWidth: 100, cellTemplate: this.flgRiservataTemplate, prop: 'ind_visibile'},
      {
        name: 'Azioni',
        sortable: false,
        minWidth: 90,
        maxWidth: 120,
        resizable: false,
        cellTemplate: this.azioniTemplate
      }
    ];
  }

  buildForm() {
    this.dettaglioForm = this.fb.group({
      esitoProcedura: [null, Validators.required],
      dataConclProc: [null, Validators.required],
      esitoProcStatale: null,
      statoProcStatale: null,
      notaIstanza: [null, Validators.maxLength(1000)]
    });

    this.dettaglioForm.get('esitoProcStatale').valueChanges.pipe(takeUntil(this.destroy$), distinctUntilChanged())
      .subscribe(esito => {
        if (esito && esito !== 'null') {
          // cod_stato_proced_statale: 'CONCLUSA';
          const statoConcluso = this.statiProcStatale[0].cod_stato_proced_statale === 'CONCLUSA' ? this.statiProcStatale[0] : {};
          this.dettaglioForm.get('statoProcStatale').setValue(statoConcluso);
          this.dettaglioForm.get('statoProcStatale').disable();
        } else {
          this.dettaglioForm.get('statoProcStatale').reset();
          this.dettaglioForm.get('statoProcStatale').enable();
        }
      });
  }

  loadData() {
    this.spinner.show();
    const getIstanza = this.istanzaService.getIstanzaById(this.idIstanza);
    const getHelpList = this.helpService.getHelpByChiave(this.componente + this.codMaschera);
    const getEsitiProcedimento = this.istanzaService.getEsitiProcedimento();
    const getSoggettiIstanza = this.ambitoService.getSoggettiIstanzaByIstanza(this.idIstanza);
    const getStatoProceduraStatale = this.adempimentoService.getStatoProceduraStatale();

    forkJoin([getIstanza,getHelpList, getEsitiProcedimento, getSoggettiIstanza, getStatoProceduraStatale]).subscribe(
      res => {
        this.istanza = res[0];
        this.helpList = res[1];
        this.getTitolare(res[3]);

        this.esiti = this.getEsitiProcedimenti();
        this.statiProcStatale = res[4];

        this.checkAC();
        this.spinner.hide();
      },
      err => {
        if (err.error?.code) {
          this.messageService.showMessage(err.error.code, 'dettaglioContainer', false);
        } else {
          this.messageService.showMessage('E100', 'dettaglioContainer', true);
        }
      }
    );
  }

  getEsitiProcedimenti() {
    this.idAdempimento = this.istanza?.adempimento.id_adempimento;
    this.adempimentoService.getEsitiProcedimentiById(this.idAdempimento)
      .subscribe((listEsiti) => {
        listEsiti.forEach((el) => {
          if (el.ind_esito === '1' || el.ind_esito === '3') {
            this.listEsitoProcedura.push(el.esito_procedimento);
          }
          if (el.ind_esito === '2' || el.ind_esito === '3') {
            this.listEsitoProceduraStatale.push(el.esito_procedimento);
          }
        });
        return listEsiti;
      });
  }

  getTitolare(soggettiIstanza: SoggettoIstanza[]) {
    const titolareIstanza = soggettiIstanza.find(soggIst => !soggIst.id_soggetto_padre);
    this.denTitolareIstanza = titolareIstanza.den_soggetto || titolareIstanza.cognome + titolareIstanza.nome;
  }

  checkAC() {
    const acList: CompetenzaTerritorio[] = JSON.parse(this.istanza.json_data).QDR_CONFIG?.ACPratica;
    this.showProcStatale = acList.some(ac => ac.tipo_competenza.cod_tipo_competenza === 'NAZIONALE' && ac.flg_principale);
  }

  onHelpClicked(chiave: string) {
    const modalContent = this.helpList.find(
      help => help.tipo_help.cod_tipo_help === 'MDL' && help.chiave_help.includes(chiave)
    )?.des_testo_help || 'Help non trovato...';

    this.messageService.showConfirmation({
      title: 'HELP',
      codMess: null,
      content: modalContent,
      buttons: [],
    });
  }

  onAggiungiNota() {
    const nuovaNota: NotaIstanza = {
      id_istanza: this.idIstanza,
      des_nota: this.dettaglioForm.get('notaIstanza').value,
    };

    this.spinner.show();
    this.istanzaService.saveNotaIstanza(nuovaNota).subscribe(
      res => {
        this.noteIstanza.push(res);
        this.noteIstanza = [...this.noteIstanza];
        this.dettaglioForm.get('notaIstanza').reset();
        this.spinner.hide();
      },
      err => {
        if (err.error?.code) {
          this.messageService.showMessage(err.error.code, 'dettaglioContainer', false);
        } else {
          this.messageService.showMessage('E100', 'dettaglioContainer', true);
        }
      }
    );
  }

  onRimuoviNota(gestUID) {
    this.spinner.show();
    this.istanzaService.deleteNotaIstanza(gestUID).subscribe(
      res => {
        const i = this.noteIstanza.findIndex(nota => nota.gestUID === gestUID);
        this.noteIstanza.splice(i, 1);
        this.noteIstanza = [...this.noteIstanza];
        this.spinner.hide();
      },
      err => {
        if (err.error?.code) {
          this.messageService.showMessage(err.error.code, 'dettaglioContainer', false);
        } else {
          this.messageService.showMessage('E100', 'dettaglioContainer', true);
        }
      }
    );
  }

  setNotaRiservataFlag(idNota, checked) {
    const nota = this.noteIstanza.find(elem => elem.id_nota_istanza === idNota);
    nota.ind_visibile = checked ? 'BO' : 'FO_BO_PUBWEB';
    this.spinner.show();
    this.istanzaService.saveNotaIstanza(nota).subscribe(
      res => {
        this.noteIstanza = [...this.noteIstanza];
        this.spinner.hide();
      },
      err => {
        if (err.error?.code) {
          this.messageService.showMessage(err.error.code, 'dettaglioContainer', false);
        } else {
          this.messageService.showMessage('E100', 'dettaglioContainer', true);
        }
      }
    );
  }

  saveIstanza() {
    this.istanza.des_esito_procedimento_statale = this.dettaglioForm.get('esitoProcStatale').value?.des_esito_procedimento_statale;
    this.dettaglioForm.value.dataConclProc = formatDate( this.dettaglioForm.value.dataConclProc, 'yyyy-MM-dd H:mm:ss', 'en');
    this.istanza.data_conclusione_procedimento = this.dettaglioForm.value.dataConclProc;
    this.istanza.stato_procedimento_statale = this.dettaglioForm.get('statoProcStatale').value;
    this.istanza.esito_procedimento = this.dettaglioForm.get('esitoProcedura').value;

    this.spinner.show();
    this.istanzaService.salvaIstanza(this.istanza)
      .pipe(
        switchMap(() => {
          return this.istanzaService.generaEvento(this.idIstanza, this.tipoEventoEnum.CONCL_BO)
            .pipe(
              catchError(err => {
                let consoleText;
                if (err.error?.code === 'E037') {
                  consoleText = '# ' + err.error.title;
                } else {
                  consoleText = this.messageService.messaggi.find(mess => mess.cod_messaggio === err.error.code).des_testo_messaggio;
                }
                console.log(consoleText);
                return of(null);
              })
            );
        })
      )
      .subscribe(
        res => {
          this.spinner.hide();
          this.messageService.showConfirmation({
            title: '',
            codMess: 'P016',
            buttons: [
              {
                label: 'OK',
                type: 'btn-primary single',
                callback: () => {
                  this.goToSearchPage();
                },
              },
            ],
          });
        },
        err => {
          this.spinner.hide();
          if (err.error?.code) {
            this.messageService.showMessage(err.error.code, 'dettaglioContainer', false);
          } else {
            this.messageService.showMessage('E100', 'dettaglioContainer', true);
          }
        }
      );
  }

  onAnnulla() {
    this.dettaglioForm.reset();
  }

  onConferma() {
    if (!this.dettaglioForm.valid) {
      this.messageService.showMessage('E001', 'searchFormCard', true);
      return;
    }
    this.saveIstanza();
  }

  onIndietro() {
    this.goToSearchPage();
  }

  goToSearchPage() {
    this.router.navigate(['/ricerca']);
  }

  compareEsito(e1: EsitoProcedimento, e2: EsitoProcedimento) {
    return e1 && e2 && e1.id_esito_procedimento === e2.id_esito_procedimento;
  }
}
