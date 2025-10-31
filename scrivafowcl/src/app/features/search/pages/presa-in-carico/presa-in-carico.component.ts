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
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ColumnMode } from '@swimlane/ngx-datatable';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin, of } from 'rxjs';
import { distinctUntilChanged, filter, map, switchMap, takeUntil, tap } from 'rxjs/operators';

import { AutoUnsubscribe } from 'src/app/core/components';
import { SoggettoIstanza } from 'src/app/features/ambito/models';
import { AmbitoService } from 'src/app/features/ambito/services';
import { InfoProtocolloAttoModalComponent } from 'src/app/shared/components';
import { CompetenzaTerritorio, IFunzionarioAutorizzato, Help, Istanza, IstanzaCompetenza, NotaIstanza } from 'src/app/shared/models';
import { AdempimentoService, AuthStoreService, HelpService, IstanzaService, MessageService } from 'src/app/shared/services';
import { TipoEventoEnum } from 'src/app/shared/utils';

@Component({
  selector: 'app-presa-in-carico',
  templateUrl: './presa-in-carico.component.html',
  styleUrls: ['./presa-in-carico.component.scss']
})
export class PresaInCaricoComponent extends AutoUnsubscribe implements OnInit, AfterViewInit {

  @ViewChild('dataTemplate') dataTemplate: TemplateRef<any>;
  @ViewChild('flgRiservataTemplate') flgRiservataTemplate: TemplateRef<any>;
  @ViewChild('azioniTemplate') azioniTemplate: TemplateRef<any>;

  codMaschera = '.MSCR011D';
  componente: string;
  funzionario: IFunzionarioAutorizzato;

  idIstanza: number;
  istanza: Istanza;
  denTitolareIstanza: string;

  helpList: Help[];
  tipoEventoEnum = TipoEventoEnum;

  istanzaCompetenzaList: IstanzaCompetenza[];
  acList: CompetenzaTerritorio[];
  selectedAC: CompetenzaTerritorio;

  infoForm: FormGroup;
  noteIstanza: NotaIstanza[] = [];
  ColumnMode = ColumnMode;
  noteTableColumns = [];

  enableBtnProtocolla = false;
  showBtnConferma = false;

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private istanzaService: IstanzaService,
    private ambitoService: AmbitoService,
    private adempimentoService: AdempimentoService,
    private authStoreService: AuthStoreService,
    private helpService: HelpService,
    private messageService: MessageService,
    private spinner: NgxSpinnerService,
    private modalService: NgbModal,
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
    this.funzionario = this.authStoreService.getFunzionarioAutorizzato();
  }

  ngOnInit() {
    this.helpService.setCodMaschera(this.codMaschera);
    this.buildForm();
    this.loadData();
  }

 ngAfterViewInit() {
    this.noteTableColumns = [
      { name: 'Nota', minWidth: 200, prop: 'des_nota' },
      { name: 'Data inserimento', minWidth: 100, cellTemplate: this.dataTemplate, prop: 'data_nota' },
      { name: 'Nota riservata', minWidth: 100, cellTemplate: this.flgRiservataTemplate, prop: 'ind_visibile' },
      {
        name: 'Azioni',
        sortable: false,
        minWidth: 90,
        maxWidth: 120,
        resizable: false,
        cellTemplate: this.azioniTemplate,
      }
    ];
  }

  buildForm() {
    this.infoForm = this.fb.group({
      ac: [null, Validators.required],
      notaIstanza: [null, Validators.maxLength(1000)]
    });

    this.infoForm.get('ac').valueChanges
      .pipe(
        takeUntil(this.destroy$),
        distinctUntilChanged(),
        filter(value => !!value)
      )
      .subscribe(ac => {
        this.selectedAC = ac;
        this.enableBtnProtocolla = !this.selectedAC.id_componente_gestore_processo;
      });
  }

  loadData() { 
    this.spinner.show();
    const getIstanza = this.istanzaService.getIstanzaById(this.idIstanza);
    const getHelpList = this.helpService.getHelpByChiave(this.componente + this.codMaschera);
    const getSoggettiIstanza = this.ambitoService.getSoggettiIstanzaByIstanza(this.idIstanza);
    const getAC = this.adempimentoService.getAutoritaCompetenteByIstanza(this.idIstanza)
      .pipe(
        map(list => list.filter(istComp => istComp.flg_autorita_principale)),
        tap(list => this.istanzaCompetenzaList = list),
        map(list => list.map(elem => elem.competenza_territorio))
      );
    forkJoin([getIstanza, getHelpList, getSoggettiIstanza, getAC]).subscribe(
      res => {
        this.istanza = res[0];
        this.helpList = res[1];
        this.getTitolare(res[2]);
        this.filterAC(res[3]);
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

  getTitolare(soggettiIstanza: SoggettoIstanza[]) {
    const titolareIstanza = soggettiIstanza.find(soggIst => !soggIst.id_soggetto_padre);
    this.denTitolareIstanza = titolareIstanza.den_soggetto || titolareIstanza.cognome + titolareIstanza.nome;
  }

  filterAC(list: CompetenzaTerritorio[]) {
    this.acList = list.filter(ac => this.funzionario.funzionario_competenza?.some(el => el.competenza_territorio?.id_competenza_territorio === ac.id_competenza_territorio));
    if (this.acList.length === 1) {
      this.infoForm.get('ac').setValue(this.acList[0]);
    }
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
      des_nota: this.infoForm.get('notaIstanza').value,
    };

    this.spinner.show();
    this.istanzaService.saveNotaIstanza(nuovaNota).subscribe(
      res => {
        this.noteIstanza.push(res);
        this.noteIstanza = [...this.noteIstanza];
        this.infoForm.get('notaIstanza').reset();
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

  onInserisciDatiProtocollazione() {
    const modalRef = this.modalService.open(InfoProtocolloAttoModalComponent, {
      centered: true,
      scrollable: true,
      backdrop: 'static',
      size: 'md',
    });
    modalRef.componentInstance.type = 'P';
    modalRef.componentInstance.tempValues = {
      data: this.istanza.data_protocollo_istanza,
      numero: this.istanza.num_protocollo_istanza
    };

    modalRef.result.then( ({data, numero}) => {
      this.istanza.data_protocollo_istanza = data;
      this.istanza.num_protocollo_istanza = numero;
    })
    .catch(err => {});
  }

  checkDatiProtocollazione(): boolean {
    let check = true;
    if (this.enableBtnProtocolla) {
      check = !!this.istanza.data_protocollo_istanza && !!this.istanza.num_protocollo_istanza;
    }
    return check;
  }

  onConferma() {
    if (!this.infoForm.valid || !this.checkDatiProtocollazione()) {
      this.messageService.showMessage('E001', 'dettaglioContainer', true);
      return;
    }

    const istComp = this.istanzaCompetenzaList.find(element => element.competenza_territorio.id_competenza_territorio === this.selectedAC.id_competenza_territorio);
    istComp.flg_autorita_assegnata_bo = true;
    const flgGestoreConfigurato = !!this.selectedAC.id_componente_gestore_processo;
    const codEvento = flgGestoreConfigurato ? this.tipoEventoEnum.ASSEGNATA : this.tipoEventoEnum.IN_CARICO;

    this.spinner.show();
    this.adempimentoService.saveAutoritaCompetente(istComp)
      .pipe(
        switchMap(() => {
          if (flgGestoreConfigurato) {
            return of(null);
          } else {
            this.istanza.data_protocollo_istanza += ' 02:00:00';
            return this.istanzaService.salvaIstanza(this.istanza, !this.istanza.cod_pratica);
          }
        }),
        switchMap(() => this.istanzaService.generaEvento(this.idIstanza, codEvento))
      )
      .subscribe(
        res => {
          this.spinner.hide();
          this.messageService.showConfirmation({
            title: '',
            codMess: flgGestoreConfigurato ? 'P017' : 'P005',
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
          if (err.error?.code) {
            this.messageService.showMessage(err.error.code, 'dettaglioContainer', false);
          } else {
            this.messageService.showMessage('E100', 'dettaglioContainer', true);
          }
        }
      );
  }

  onIndietro() {
    this.goToSearchPage();
  }

  goToSearchPage() {
    this.router.navigate(['/ricerca']);
  }

  compareAC(a1: CompetenzaTerritorio, a2: CompetenzaTerritorio) {
    return a1 && a2 && a1.id_competenza_territorio === a2.id_competenza_territorio;
  }
}
