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
import { FormBuilder  } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin, of } from 'rxjs';
import { AdempimentoService, AuthStoreService, HelpService, IstanzaService, MessageService } from 'src/app/shared/services';

import { AmbitoService } from 'src/app/features/ambito/services';

import { AbstractAdvancedAction } from '../abstract-advanced-action';
import { AbstractAdvancedActionInterface } from '../../interfaces/abstract-advanced-action.interface';
import { AdvancedActionsChiavi, AdvancedActionsMaschere } from '../../enums/advanced-actions.enums';
import { ScrivaNoteService } from 'src/app/shared/services/scriva-note/scriva-note.service';
import { catchError } from 'rxjs/operators';
import { AdvancedActionService } from '../../services/advanced-action.service';

@Component({
  selector: 'app-integra-alleg',
  templateUrl: './integra-alleg.component.html',
  styleUrls: ['./integra-alleg.component.scss']
})
export class IntegraAllegComponent extends AbstractAdvancedAction implements OnInit, AbstractAdvancedActionInterface {

  chiave:AdvancedActionsChiavi = AdvancedActionsChiavi.RICH_INTEGR_ALLEGATI;
  codMaschera = AdvancedActionsMaschere.RICH_INTEGR_ALLEGATI;

  showBtnConferma = false;

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
    public advancedActionService: AdvancedActionService
  ) {
    super(router,
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
          advancedActionService);
    
    if (!this.idIstanza) {
      this.goToSearchPage();
      return;
    }
    
    this.scrivaBreadCrumbItems=[
      {
        emitAction: 'goToSearchPage',
        label: 'Ricerca',
        ariaLabel: 'ricerca'
      },
      {
        label: 'Richiedi Integrazioni',
      }
    ];
  }

  ngOnInit() {
    super.ngOnInit();
    this.loadData();
  }

  /**
   * metodo che recupera i dati
   */
  loadData() {
    this._spinner.show();
    let calls = [...this.loadDataObservables];
    forkJoin(calls).subscribe(
      res => {
        this.loadDataSuccess(res);
        this._spinner.hide();
      },
      err => {
        this.loadDataError(err);
      }
    );
  }

  /**
   * in caso di successo chiamo la super.loadDataSucces
   * e dopo compio le azioni spercifiche della mia azione avanzata
   * @param res 
   */
  loadDataSuccess(res:any[]) {
    super.loadDataSuccess(res);
  }

  onConferma() {
   this._spinner.show();
    super.saveNoteIstanza().pipe(catchError(err => of([]))).subscribe(
      {
        next: (res: any) => {
          this._spinner.hide();
          super.generaEvento(this.tipoEventoEnum.RICH_ALLEG,'P006');
        },
        error: (e: any) => {
          // non Gestisco gli errori
          // Chiudo lo spinner di caricamento
          this._spinner.hide();
          super.generaEvento(this.tipoEventoEnum.RICH_ALLEG,'P006');
        },
      }
    );
  }

}
