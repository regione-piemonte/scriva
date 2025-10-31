/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { forkJoin } from 'rxjs';
import { NgxSpinnerService } from 'ngx-spinner';

import { Adempimento, Istanza } from 'src/app/shared/models';
import { IstanzaService, MessageService, StepManagerService } from 'src/app/shared/services';

import { switchMap, tap } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AdvancedActionsRiepilogoModalComponent } from '../../components/advanced-actions-riepilogo-modal/advanced-actions-riepilogo-modal.component';

@Component({
  selector: 'mock-riepilogo',
  templateUrl: './mock-riepilogo.component.html',
  styleUrls: ['./mock-riepilogo.component.scss']
})
export class MockRiepilogoComponent {
  idIstanza: number;
  istanza: Istanza;
  jsonData: any;
  adempimento: Adempimento;
  acPraticaList: any;
  quadri: any;
  moduloFirmato: any;
  qdrConclusione: any;


  links = [
    {
      path: 'abilita-visualizzazione'
    },
    {
      path: 'abilitazioni'
    },
    {
      path: 'avvia-pratica'
    },
    {
      path: 'concludi-proc'
    },
    {
      path: 'gestisci-note'
    },
    {
      path: 'prendi-in-carico'
    },
    {
      path: 'revoca-delega'
    },
    {
      path: 'rich-perf-allegati'
    },
    {
      path: 'rifiuta-istanza'
    },
    {
      path: 'sospendi'
    },
  ];

  constructor(
    private  route: ActivatedRoute,
    private messageService: MessageService,
    private stepManagerService: StepManagerService,
    private istanzaService: IstanzaService,
    private spinner: NgxSpinnerService,
    private modalService: NgbModal
  ) {
    this.route.paramMap.subscribe( paramMap => {
      this.idIstanza =  +paramMap.get('id');
    });
  }

  ngOnInit() {
  }

  openModalRiepilogo(): void {
    /* mock se non è settato */
    if(!this.idIstanza) {
      this.idIstanza = 1175; 
    }
    this.istanzaService.getIstanzaById(this.idIstanza)
      .pipe(
        tap(istanza => {
          this.istanza = istanza;
          this.jsonData = JSON.parse(istanza.json_data);
          this.adempimento = this.istanza.adempimento;
          
        }),
        switchMap(() => {
          // qui si possono aggiungere tutte le chiamate che si vogliono
          //const getACPratica = this.adempimentoService.getAutoritaCompetenteByIstanza(this.idIstanza);
          const getQuadri = this.stepManagerService.getQuadri(this.adempimento.cod_adempimento, this.istanza);
          //const getModuloFirmato = this.allegatiService.getAllAllegatiIstanza(this.idIstanza);
          //const getQdrConclusione = this.stepManagerService.getJsonDataByCodTipoQuadro(this.idIstanza, 'QDR_CONCL_PROC');
          return forkJoin([ getQuadri]);

          /* const getCodConclProc = this.configurazioniService.getConfigurazioniByInfoAndChiave(this.adempimento.cod_adempimento, 'AZ_TIPO_QUADRO', 'CONCLUDI_PROC');
          return forkJoin([getACPratica, getQuadri, getModuloFirmato, getCodConclProc]); */
        })
      )
      .subscribe(
        res => {
          this.quadri = res[0].quadri;
          
         /*  const codConclProc = res[3][0].valore; // --
          this.qdrConclusione = this.jsonData[codConclProc]; // -- */
          this._openModalRiepilogo();
          this.spinner.hide();
        },
        err => {
          if (err.error?.code) {
            this.messageService.showMessage(err.error.code, 'headerCard', false);
          } else {
            this.messageService.showMessage('E100', 'headerCard', true);
          }
        }
      );
  }

  _openModalRiepilogo() {
    /*
      qui si possono scrivere tutti i mock che servono in fase di sviluppo
    */
      const modalRef = this.modalService.open(AdvancedActionsRiepilogoModalComponent, {
        centered: true,
        scrollable: true,
        backdrop: 'static',
        size: 'xl',
      });

      /* non è detto che servono tutti */
      modalRef.componentInstance.istanza = this.istanza;
      modalRef.componentInstance.jsonData = this.jsonData;
      modalRef.componentInstance.adempimento = this.adempimento;

      modalRef.result
        .then(() => {
          console.log('result then modal')
        })
        .catch();
  }

}
