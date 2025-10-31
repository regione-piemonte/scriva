/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { AdvancedActionsChiavi, AdvancedActionsMaschere } from './../../../advanced-actions/enums/advanced-actions.enums';
import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ColumnMode } from '@swimlane/ngx-datatable';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin, of } from 'rxjs';
import {
  catchError,
  distinctUntilChanged,
  filter,
  map,
  switchMap,
  takeUntil,
  tap,
} from 'rxjs/operators';

import { AbstractAdvancedAction } from 'src/app/features/advanced-actions/pages/abstract-advanced-action';
import { AmbitoService } from 'src/app/features/ambito/services';
import {
  CompetenzaTerritorio,
  IFunzionarioAutorizzato,
  Help,
  Istanza,
  IstanzaCompetenza,
  NotaIstanza,
} from 'src/app/shared/models';
import {
  AdempimentoService,
  AuthStoreService,
  HelpService,
  IstanzaService,
  MessageService,
} from 'src/app/shared/services';
import { ScrivaNoteService } from 'src/app/shared/services/scriva-note/scriva-note.service';
import { AdvancedActionService } from '../../services/advanced-action.service';

@Component({
  selector: 'app-presa-in-carico',
  templateUrl: './presa-in-carico.component.html',
  styleUrls: ['./presa-in-carico.component.scss'],
})
export class PresaInCaricoComponent
  extends AbstractAdvancedAction
  implements OnInit
{
  @ViewChild('dataTemplate') dataTemplate: TemplateRef<any>;
  @ViewChild('flgRiservataTemplate') flgRiservataTemplate: TemplateRef<any>;
  @ViewChild('azioniTemplate') azioniTemplate: TemplateRef<any>;

  chiave:AdvancedActionsChiavi = AdvancedActionsChiavi.PRENDI_IN_CARICO;
  codMaschera: AdvancedActionsMaschere = AdvancedActionsMaschere.PRENDI_IN_CARICO;
  componente: string;
  funzionario: IFunzionarioAutorizzato;

  idIstanza: number;
  istanza: Istanza;
  denTitolareIstanza: string;

  helpList: Help[];

  istanzaCompetenzaList: IstanzaCompetenza[];
  acList: CompetenzaTerritorio[];
  selectedAC: CompetenzaTerritorio;

  infoForm: FormGroup;
  noteIstanza: NotaIstanza[] = [];
  ColumnMode = ColumnMode;
  noteTableColumns = [];
  flgGestoreConfigurato: any;

  enableBtnProtocolla = false;
  showBtnConferma = false;

  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public fb: FormBuilder,
    public _istanza: IstanzaService,
    public ambitoService: AmbitoService,
    public adempimentoService: AdempimentoService,
    public authStoreService: AuthStoreService,
    public helpService: HelpService,
    public _message: MessageService,
    public _spinner: NgxSpinnerService,
    public modalService: NgbModal,
    public scrivaNoteService: ScrivaNoteService,
    public advancedActionService: AdvancedActionService
  ) {
    super(
      router,
      fb,
      _istanza,
      adempimentoService,
      ambitoService,
      authStoreService,
      helpService,
      _message,
      route,
      _spinner,
      scrivaNoteService,
      advancedActionService
    );


    this.componente = this.authStoreService.getComponente(); // should always be BO
    this.funzionario = this.authStoreService.getFunzionarioAutorizzato();

    this.scrivaBreadCrumbItems = [
      {
        emitAction: 'goToSearchPage',
        label: 'Ricerca',
        ariaLabel: 'ricerca',
      },
      {
        label: 'Prendi in carico',
      },
    ];
  }

  ngOnInit() {
    super.ngOnInit();
    this.buildForm();
    this.loadData();
  }

  buildForm() {
    this.infoForm = this.fb.group({
      ac: [null, Validators.required],
      notaIstanza: [null, Validators.maxLength(500)],
    });

    this.infoForm
      .get('ac')
      .valueChanges.pipe(
        takeUntil(this.destroy$),
        distinctUntilChanged(),
        filter((value) => !!value)
      )
      .subscribe((ac) => {
        this.selectedAC = ac;
        this.enableBtnProtocolla =
          !this.selectedAC.id_componente_gestore_processo;
      });
  }

  loadData() {
    this._spinner.show();

    let calls = [...this.loadDataObservables];
    const getAC = this.adempimentoService
      .getAutoritaCompetenteByIstanza(this.idIstanza)
      .pipe(
        map((list) =>
          list.filter((istComp) => istComp.flg_autorita_principale)
        ),
        tap((list) => (this.istanzaCompetenzaList = list)),
        map((list) => list.map((elem) => elem.competenza_territorio))
      );

    calls.push(getAC);

    forkJoin(calls).subscribe(
      (res: any[]) => {
        this.loadDataSuccess(res);
        this._spinner.hide();
      },
      (err) => {
        this.loadDataError(err);
      }
    );
  }

  loadDataSuccess(res: any[]) {
    super.loadDataSuccess(res);
    this.filterAC(res[3]);
  }

  filterAC(list: CompetenzaTerritorio[]) {
    this.acList = list.filter((ac) =>
      this.funzionario.funzionario_competenza?.some(
        (el) =>
          el.competenza_territorio?.id_competenza_territorio ===
          ac.id_competenza_territorio
      )
    );
    if (this.acList.length === 1) {
      this.infoForm.get('ac').setValue(this.acList[0]);
    }
  }

  onConferma() {
    if (!this.infoForm.valid) {
      this._message.showMessage('E001', 'dettaglioContainer', true);
      return;
    }

    const istComp = this.istanzaCompetenzaList.find(
      (element) =>
        element.competenza_territorio.id_competenza_territorio ===
        this.selectedAC.id_competenza_territorio
    );
    istComp.flg_autorita_assegnata_bo = true;
    this.flgGestoreConfigurato =
      !!this.selectedAC.id_componente_gestore_processo;
    const codEvento = this.flgGestoreConfigurato
      ? this.tipoEventoEnum.ASSEGNATA
      : this.tipoEventoEnum.IN_CARICO;
    
    if(this.istanza.data_protocollo_istanza) {
      this.istanza.data_protocollo_istanza += ' 02:00:00';  
    }
    
    const codMess = this.flgGestoreConfigurato?'P017':'P005';  

    super.saveNoteIstanza()
    .pipe(catchError(err => of([])))
    .pipe(
      switchMap(() => { 
        return this.adempimentoService.saveAutoritaCompetente(istComp)
      })
    )
    .subscribe(
      {
        next: (res: any) => {
          this._spinner.hide();
          if (this.flgGestoreConfigurato) {
            super.generaEvento(codEvento, codMess);
          }else {
            super.saveIstanza(codEvento, codMess);
          }
        },
        error: (err: any) => {
          // non Gestisco gli errori
          // Chiudo lo spinner di caricamento
          this._spinner.hide();
          if (err.error?.code) {
            this._message.showMessage(
              err.error.code,
              'dettaglioContainer',
              false
            );
          } else {
            this._message.showMessage('E100', 'dettaglioContainer', true);
          }
        },
      }
    );
  }

  
  compareAC(a1: CompetenzaTerritorio, a2: CompetenzaTerritorio) {
    return (
      a1 && a2 && a1.id_competenza_territorio === a2.id_competenza_territorio
    );
  }
}
