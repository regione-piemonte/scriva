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
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AmbitoService } from 'src/app/features/ambito/services';
import { IstanzaCompetenza } from 'src/app/shared/models';
import { TipoResponsabile } from 'src/app/shared/models/aut-competente/tipo-responsabile.model';
import { IstanzaResponsabile } from 'src/app/shared/models/istanza/istanza-responsabile.model';
import {
  AdempimentoService,
  AuthStoreService,
  ConfigurazioniScrivaService,
  HelpService,
  IstanzaService,
  MessageService,
} from 'src/app/shared/services';
import { ScrivaNoteService } from 'src/app/shared/services/scriva-note/scriva-note.service';
import {
  AdvancedActionsChiavi,
  AdvancedActionsMaschere,
} from '../../enums/advanced-actions.enums';
import { AbstractAdvancedActionInterface } from '../../interfaces/abstract-advanced-action.interface';
import { AdvancedActionService } from '../../services/advanced-action.service';
import { AbstractAdvancedAction } from '../abstract-advanced-action';

@Component({
  selector: 'app-avvia-procedimento',
  templateUrl: './avvia-procedimento.component.html',
  styleUrls: ['./avvia-procedimento.component.scss'],
})
export class AvviaProcedimentoComponent
  extends AbstractAdvancedAction
  implements OnInit, AbstractAdvancedActionInterface
{
  chiave: AdvancedActionsChiavi = AdvancedActionsChiavi.AVVIA_PRATICA;
  codMaschera = AdvancedActionsMaschere.AVVIA_PRATICA;

  dettaglioForm: FormGroup;
  today = new Date();
  numProtocolloIstanzaMaxLen: number = 20;
  responsabili: IstanzaResponsabile[] = [];
  responsabiliAdded: IstanzaResponsabile[] = [];
  tipiResponsabile: TipoResponsabile[] = [];
  istanzaCompetenzaList: IstanzaCompetenza[];

  constructor(
    public router: Router,
    public fb: FormBuilder,
    public _istanza: IstanzaService,
    public adempimentoService: AdempimentoService,
    public ambitoService: AmbitoService,
    public authStoreService: AuthStoreService,
    public helpService: HelpService,
    public _message: MessageService,
    public route: ActivatedRoute,
    public _spinner: NgxSpinnerService,
    public scrivaNoteService: ScrivaNoteService,
    public configurazioniService: ConfigurazioniScrivaService,
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

    if (!this.idIstanza) {
      this.goToSearchPage();
      return;
    }
  }

  ngOnInit() {
    super.ngOnInit();
    this.buildForm();
    this.loadData();
    this.responsabiliAdded = [];
  }

  /**
   * Click sul pulsante conferma della azione avanzata
   */
  onConferma() {
    this.saveIstanza();
  }

  /**
   *  Setto i campi dal form  e salvo istanza con la super( contiene anche la genera evento nella callback )
   */
  saveIstanza() {
    this.istanza.anno_registro = this.dettaglioForm.get('annoRegistro').value;
    this.istanza.num_protocollo_istanza = this.dettaglioForm.get(
      'numProtocolloIstanza'
    ).value;
    this.istanza.data_protocollo_istanza =
      this.advancedActionService.convertDateForm4BE(
        this.dettaglioForm.get('dataProtocolloIstanza').value
      );
    this.istanza.data_scadenza_procedimento =
      this.advancedActionService.convertDateForm4BE(
        this.dettaglioForm.get('dataScadenzaProcedimento').value
      );
    this.istanza.responsabili_istanza = [
      ...this.advancedActionService.getResponsabiliForSave(
        this.responsabili,
        this.responsabiliAdded
      ),
    ];
    super
      .saveNoteIstanza()
      .pipe(catchError((err) => of([])))
      .subscribe({
        next: (res: any) => {
          this._spinner.hide();
          super.saveIstanza(this.tipoEventoEnum.AVVIO, 'P010');
        },
        error: (e: any) => {
          // non Gestisco gli errori
          // Chiudo lo spinner di caricamento
          this._spinner.hide();
          super.saveIstanza(this.tipoEventoEnum.AVVIO, 'P010');
        },
      });
  }

  buildForm() {
    this.dettaglioForm = this.fb.group({
      numProtocolloIstanza: [null, { validators: [Validators.required] }],
      dataProtocolloIstanza: [
        null,
        { validators: [Validators.required, this.maxDateValidator] },
      ],
      dataScadenzaProcedimento: [null],
      annoRegistro: [
        null,
        { validators: [Validators.required, Validators.pattern('(^\\d{4}$)')] },
      ],
    });
  }

  maxDateValidator(control: AbstractControl): ValidationErrors | null {
    const selectedDate = new Date(control.value);
    const maxDate = new Date();
    return selectedDate <= maxDate ? null : { maxDateExceededToday: true };
  }

  setValuesForm() {
    this.dettaglioForm
      .get('annoRegistro')
      .setValue(
        this.istanza.anno_registro
          ? this.istanza.anno_registro
          : this.today.getFullYear()
      );
    this.dettaglioForm
      .get('numProtocolloIstanza')
      .setValue(
        this.istanza.num_protocollo_istanza
          ? this.istanza.num_protocollo_istanza
          : null
      );
    this.dettaglioForm
      .get('dataProtocolloIstanza')
      .setValue(
        this.istanza.data_protocollo_istanza
          ? this.istanza.data_protocollo_istanza.split(' ')[0]
          : null
      );
    this.dettaglioForm
      .get('dataScadenzaProcedimento')
      .setValue(
        this.istanza.data_scadenza_procedimento
          ? this.istanza.data_scadenza_procedimento.split(' ')[0]
          : null
      );
  }

  /**
   * metodo che recupera i dati
   */
  loadData() {
    this._spinner.show();
    // le chiamate che devo prendere come base sono quelle del parent per utte le azioni avanzate
    let calls = [...this.loadDataObservables];
    calls.push(
      this._istanza
        .getResponsabili(this.idIstanza)
        .pipe(catchError((err) => of([])))
    );
    calls.push(
      this.advancedActionService
        .getIstanzaCompetenzaList(this.idIstanza)
        .pipe(catchError((err) => of([])))
    );
    calls.push(this._istanza.getTipiResponsabili(this.idIstanza));
    forkJoin(calls).subscribe(
      (res) => {
        this._spinner.hide();
        this.loadDataSuccess(res);
      },
      (err) => {
        this._spinner.hide();
        this.loadDataError(err);
      }
    );
  }

  /**
   * in caso di successo chiamo la super.loadDataSucces
   * e dopo compio le azioni spercifiche della mia azione avanzata
   * @param res
   */
  loadDataSuccess(res: any[]) {
    super.loadDataSuccess(res);
    this.setValuesForm();
    this.responsabili = res[3];
    this.istanzaCompetenzaList = res[4];
    this.tipiResponsabile = res[5];
  }

  /**
   * tengo allineati i responsabili in memoria per il salvataggio sul conferma
   * @param responsabili any[]
   */
  onUpdateResponsabili(responsabili: IstanzaResponsabile[] = []) {
    this.responsabili = [...responsabili];
  }

  /**
   * Aggiungo Responsabili in lista di quelli aggiunti e non ancora salvati
   * @param r IstanzaResponsabile
   */
  onAddResponsabile(r: IstanzaResponsabile) {
    this.responsabiliAdded.push(r);
  }
}
