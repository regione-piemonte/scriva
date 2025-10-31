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
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { AdempimentoService, AuthStoreService, ConfigurazioniScrivaService, HelpService, IstanzaService, MessageService } from 'src/app/shared/services';
import { AmbitoService } from 'src/app/features/ambito/services';
import { AbstractAdvancedAction } from '../abstract-advanced-action';
import { ScrivaNoteService } from 'src/app/shared/services/scriva-note/scriva-note.service';
import { AdvancedActionService } from '../../services/advanced-action.service';
import { AbstractAdvancedActionInterface } from '../../interfaces/abstract-advanced-action.interface';
import { AdvancedActionsChiavi, AdvancedActionsMaschere } from '../../enums/advanced-actions.enums';
import { EsitoProcedimento } from 'src/app/shared/models';
import { catchError } from 'rxjs/operators';
import { forkJoin, of } from 'rxjs';
import { TipoInfoAdempimento } from 'src/app/shared/services/configurazioni/utilities/configurazioni.enums';

@Component({
  selector: 'app-rifiuta-istanza',
  templateUrl: './rifiuta-istanza.component.html',
  styleUrls: ['./rifiuta-istanza.component.scss']
})
export class RifiutaIstanzaComponent extends AbstractAdvancedAction implements OnInit, AbstractAdvancedActionInterface {
  chiave:AdvancedActionsChiavi = AdvancedActionsChiavi.RIFIUTA_ISTANZA;
  codMaschera = AdvancedActionsMaschere.RIFIUTA_ISTANZA;
  
  esiti;

  dettaglioForm: FormGroup;
  listEsitoProcedura = []; // todo associa modal

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
    public advancedActionService: AdvancedActionService,
    public configurazioniService: ConfigurazioniScrivaService,
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
        label: 'Rifiuta istanza',
      }
    ];
  }

  ngOnInit() {
    super.ngOnInit();
    this.buildForm();
    this.loadData();
  }

  buildForm() {
    this.dettaglioForm = this.fb.group({
      esitoProcedura: [null, Validators.required],
    });
  }

  /**
   * metodo che recupera i dati
   */
  loadData() {
    this._spinner.show();
    // le chiamate che devo prendere come base sono quelle del parent per utte le azioni avanzate
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
    this.getEsitiProcedimenti();
  }

  getEsitiProcedimenti() {
    let calls = [ 
      this.adempimentoService.getEsitiProcedimentiById(this.istanza?.adempimento.id_adempimento),
      this.configurazioniService.getConfigurazioniByInfoAndChiave(
        this.istanza.adempimento.cod_adempimento,
        TipoInfoAdempimento.azEsitoRifiuto,
        this.chiave
      )
    ];
    forkJoin(calls)
      .subscribe((res) => {
        const listEsiti=res[0];
        const listEsitiConfig=res[1];
        listEsitiConfig.forEach((item) => {
          const el=listEsiti.find(i=>i.esito_procedimento.cod_esito_procedimento===item.valore)
          if (el?.ind_esito === '1' || el?.ind_esito === '3') {
            this.listEsitoProcedura.push(el.esito_procedimento);
          }
        });
      });
  }
  
  saveIstanza() {
    this.istanza.esito_procedimento = this.dettaglioForm.get('esitoProcedura').value;
    super.saveNoteIstanza().pipe(catchError(err => of([]))).subscribe(
      {
        next: (res: any) => {
          this._spinner.hide();
          super.saveIstanza(this.tipoEventoEnum.RIFIUTA,'P007');
        },
        error: (e: any) => {
          // non Gestisco gli errori
          // Chiudo lo spinner di caricamento
          this._spinner.hide();
          super.saveIstanza(this.tipoEventoEnum.RIFIUTA,'P007');
        },
      }
    );
  }

  onAnnulla() {
    this.dettaglioForm.reset();
  }

  onConferma() {
    if (!this.dettaglioForm.valid) {
      this._message.showMessage('E001', 'searchFormCard', true);
      return;
    }
    this.saveIstanza();
  }
  
  compareEsito(e1: EsitoProcedimento, e2: EsitoProcedimento) {
    return e1 && e2 && e1.id_esito_procedimento === e2.id_esito_procedimento;
  }
  
}
