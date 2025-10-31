/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { AfterViewInit, Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ColumnMode } from '@swimlane/ngx-datatable';
import { Istanza, IstanzaCompetenza } from 'src/app/shared/models';
import { IstanzaResponsabile } from 'src/app/shared/models/istanza/istanza-responsabile.model';
import { AdvancedActionResponsabileComponent } from '../advanced-action-responsabile/advanced-action-responsabile.component';
import { TipoResponsabile } from 'src/app/shared/models/aut-competente/tipo-responsabile.model';
import { MessageService } from 'src/app/shared/services';

@Component({
  selector: 'advanced-action-responsabili',
  templateUrl: './advanced-action-responsabili.component.html',
  styleUrls: ['./advanced-action-responsabili.component.scss'],
})
export class AdvancedActionResponsabili implements OnInit, AfterViewInit {
  
  @Input() istanza: Istanza;
  @Input() responsabili: IstanzaResponsabile[];
  @Input() tipiResponsabile: TipoResponsabile[] = [];
  @Input() istanzaCompetenzaList: IstanzaCompetenza[] =[];
  @Input() insertEnabled: boolean = true;
  @Input() isRiepilogoModal: boolean = false;

  /** Output che definisce l'evento passato verso alto */
  @Output() emit = new EventEmitter<any>();
   

  /** ViewChild per i template della tabella */
  @ViewChild('azioniTemplate') azioniTemplate: TemplateRef<any>;
  @ViewChild('azioniVisibileTemplate') azioniVisibileTemplate: TemplateRef<any>;
  
  
  ALERTANCHOR:string = 'alertAnchorAdvancedActionResponsabili';
  ERRORVIOLATIONLIST:string = 'E075';
  ColumnMode = ColumnMode;
  responsabiliColumns = [];
  
  /**
   * Costruttore.
   */
  constructor(
    private messageService: MessageService,
    private modalService: NgbModal,
    ) {}

  /**
   * NgOnInit.
   */
  ngOnInit() {
    
  }

  /**
   * Propago evento verso alto verso componente che contiene il breadcrumb
   * @param ev stringa che dice quale evento deve essere emesso
   */
  clickEmit(ev:string) {
    this.emit.next(ev);
  }
  
  /**
   * setto le colonne della tabella dei Responsabili
   */
  ngAfterViewInit() {
    setTimeout(() => {
      this.responsabiliColumns = [
        {
          name: 'Tipologia Riferimento',
          prop: 'tipo_responsabile.des_tipo_responsabile',
          sortable: false,
        },
        {
          name: 'Nominativo',
          minWidth: 160,
          prop: 'nominativo_responsabile',
          sortable: false,
        },
        {
          name: 'Recapito',
          prop: 'recapito_responsabile',
          sortable: false,
        },
        {
          name: 'Visibile su portale',
          sortable: false,
          minWidth: 90,
          maxWidth: 120,
          resizable: false,
          cellTemplate: this.azioniVisibileTemplate,
        },
        {
          name: 'Azioni',
          sortable: false,
          minWidth: 90,
          maxWidth: 120,
          resizable: false,
          cellTemplate: this.azioniTemplate,
        }
      ];
    });
  }

  /**
   * Emetto verso alto aggiunta di un responsabile
   * @param r IstanzaResponsabile
   */
  private _onEmitAddResponsabile(r:IstanzaResponsabile) {
    const ev = {
      action: 'onAddResponsabile',
      args: r
    };
    this.emit.next(ev);
  }

   /**
   * Emetto verso alto la lista dei responsabili
   */
  private _updateResponsabili() {
    let resp = [...this.responsabili];
    const ev = {
      action: 'onUpdateResponsabili',
      args: resp
    };
    this.emit.next(ev);
  }

  /**
   * Aggiorno elenco lista dei responsabili da Edit
   * @param istanzaResponsabile IstanzaResponsabile
   * @param noCheck boolean che controlla se esiste una violazione di coppia TIPOREP/NOMINATIVO
   * @returns 
   */
  onEditResponsabile(istanzaResponsabile: IstanzaResponsabile, noCheck: boolean = false) {
    if(!noCheck && this._checkViolationListIstanzaResponsabile(istanzaResponsabile)) {
      this.messageService.showMessage(this.ERRORVIOLATIONLIST, this.ALERTANCHOR, true);
      return;
    } 
    this.responsabili = this.responsabili.map(r => r.id_istanza_responsabile !== istanzaResponsabile.id_istanza_responsabile ? r : istanzaResponsabile);
    this._updateResponsabili();
  }
  
  /**
   * Aggiorno elenco lista dei responsabili da ADD
   * @param istanzaResponsabile IstanzaResponsabile
   * @returns void
   */
  onNewResponsabile(istanzaResponsabile: IstanzaResponsabile) {
    if(this._checkViolationListIstanzaResponsabile(istanzaResponsabile)) {
      this.messageService.showMessage(this.ERRORVIOLATIONLIST, this.ALERTANCHOR, true);
      return;
    }
    this._onEmitAddResponsabile(istanzaResponsabile);
    this.responsabili.push(istanzaResponsabile);
    this._updateResponsabili();
  }

  /**
   * Click su OnDeleteResponsabile
   * @param istanzaResponsabile IstanzaResponsabile
   */
  clickOnDeleteResponsabile(istanzaResponsabile: IstanzaResponsabile) {
    this.messageService.showConfirmation({
      title: 'Conferma eliminazione',
      codMess: 'A002',
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
            this.onDeleteResponsabile(istanzaResponsabile);
          },
        },
      ],
    });
  }

  /**
   * Aggiorno elenco lista dei responsabili da DELETE
   * @param istanzaResponsabile IstanzaResponsabile
   */
  onDeleteResponsabile(istanzaResponsabile: IstanzaResponsabile) {
    const i = this.responsabili.findIndex(
      (r) => r.id_istanza_responsabile === istanzaResponsabile.id_istanza_responsabile
    );
    this.responsabili.splice(i, 1);
    this.responsabili = [...this.responsabili];
    this._updateResponsabili();
  }

  /**
   * Toogle del flg_riservato su IstanzaResponsabile in input
   * @param istanzaResponsabile IstanzaResponsabile
   */
  onFlagRiservatoToogle(istanzaResponsabile: IstanzaResponsabile) {
    istanzaResponsabile.flg_riservato = !istanzaResponsabile.flg_riservato;
    this.onEditResponsabile(istanzaResponsabile, true);
  }

  /**
   * Click per editare IstanzaResponsabile in input che lavora su modale
   * @param istanzaResponsabile IstanzaResponsabile
   */
  clickOnEditResponsabile(istanzaResponsabile: IstanzaResponsabile) {
    const istanzaResponsabileBeforeChange = {...istanzaResponsabile};
    const modalRef = this.modalService.open(AdvancedActionResponsabileComponent, {
      centered: true,
      scrollable: true,
      backdrop: 'static',
      size: this.isRiepilogoModal?'lg':'xl',
    });
    modalRef.componentInstance.idIstanza = this.istanza.id_istanza;
    modalRef.componentInstance.tipiResponsabile = this.tipiResponsabile;
    modalRef.componentInstance.istanzaResponsabile = {...istanzaResponsabile};
    modalRef.componentInstance.istanzaCompetenzaList = this.istanzaCompetenzaList;
    modalRef.result
      .then((r) => {
        if(r) {
          // in caso di modifica dei campi la cui coppia deve essere unica
          // occorre controllare il rispetto di questa condizione
          // se non ho modificato questi campi salviamo le altre informazioni
          const noCheck:boolean = (istanzaResponsabileBeforeChange.nominativo_responsabile!=r.nominativo_responsabile ||
          istanzaResponsabileBeforeChange.tipo_responsabile.cod_tipo_responsabile!=r.tipo_responsabile.cod_tipo_responsabile)?false:true;
          this.onEditResponsabile(r,noCheck);
        }
      })
      .catch(e => {
        
      });
  }

  /**
   * Controlla se esiste una violazione di coppia TIPOREP/NOMINATIVO tra IstanzaResponsabile in input e lista corrente
   * @param istanzaResponsabile IstanzaResponsabile
   * @returns boolean
   */
  private _checkViolationListIstanzaResponsabile(istanzaResponsabile: IstanzaResponsabile): boolean {
    return this.responsabili.some(
      i=>i.nominativo_responsabile === istanzaResponsabile.nominativo_responsabile && i.tipo_responsabile.cod_tipo_responsabile === istanzaResponsabile.tipo_responsabile.cod_tipo_responsabile
    );
  }
}
