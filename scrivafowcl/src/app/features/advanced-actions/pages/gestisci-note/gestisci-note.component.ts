/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { NotaIstanza } from 'src/app/shared/models';
import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { AdempimentoService, AuthStoreService, HelpService, IstanzaService, MessageService } from 'src/app/shared/services';
import { AmbitoService } from 'src/app/features/ambito/services';
import { AbstractAdvancedAction } from '../abstract-advanced-action';
import { forkJoin, of } from 'rxjs';
import { AdvancedActionsChiavi, AdvancedActionsMaschere } from '../../enums/advanced-actions.enums';
import { catchError } from 'rxjs/operators';
import { ScrivaNoteService } from 'src/app/shared/services/scriva-note/scriva-note.service';
import { AdvancedActionService } from '../../services/advanced-action.service';

@Component({
  selector: 'app-gestisci-note',
  templateUrl: './gestisci-note.component.html',
  styleUrls: ['./gestisci-note.component.scss']
})
export class GestisciNoteComponent
  extends AbstractAdvancedAction
  implements OnInit
{
  chiave:AdvancedActionsChiavi = AdvancedActionsChiavi.GESTISCI_NOTE;
  codMaschera: AdvancedActionsMaschere = AdvancedActionsMaschere.GESTISCI_NOTE;
  
  noteIstanza: NotaIstanza[] = [];

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

  }

  ngOnInit() {
    super.ngOnInit();
    this.loadData();
  }

  loadData() {
    this._spinner.show();
    // le chiamate che devo prendere come base sono quelle del parent per tutte le azioni avanzate
    let calls = [...this.loadDataObservables];
    let getNote = this._istanza.getNotePubblicate(this.idIstanza).pipe(catchError(err => of([])));
    calls.push(getNote);

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
    this.noteIstanza = res[3];
  }

}
