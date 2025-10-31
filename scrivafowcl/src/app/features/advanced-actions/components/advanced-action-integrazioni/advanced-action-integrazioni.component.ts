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
import { Istanza} from 'src/app/shared/models';
import { AdvancedActionIntegrazioneComponent } from '../advanced-action-integrazione/advanced-action-integrazione.component';
import { AdvancedActionService } from '../../services/advanced-action.service';
import { IntegrazioneIstanza } from 'src/app/shared/models/istanza/integrazione-istanza.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'src/app/shared/services';

@Component({
  selector: 'advanced-action-integrazioni',
  templateUrl: './advanced-action-integrazioni.component.html',
  styleUrls: ['./advanced-action-integrazioni.component.scss'],
})
export class AdvancedActionIntegrazioniComponent implements OnInit, AfterViewInit {
  
  @Input() istanza: Istanza;
  @Input() integrazioni: IntegrazioneIstanza[];
  @Input() insertEnabled: boolean = true;
  @Input() isRiepilogoModal: boolean = false;

  /** Output che definisce l'evento passato verso alto */
  @Output() emit = new EventEmitter<any>();
   

  /** ViewChild per i template della tabella */
  @ViewChild('azioniTemplate') azioniTemplate: TemplateRef<any>;
  
  ALERTANCHOR:string = 'alertAnchorAdvancedActionResponsabili';
  ColumnMode = ColumnMode;
  integrazioniColumns = [];
  
  pipeFormatDate = {
    transform: (value: string) =>
      value ? this.advancedActionService.convertDateBE4Form(value) : ''
  };

  pipeNome = {
    transform: (allegato_integrazione: any = []) =>
      allegato_integrazione && allegato_integrazione?.find(i=>i.flg_allegato_rif_protocollo) ? allegato_integrazione?.find(i=>i.flg_allegato_rif_protocollo)?.nome_allegato : ''
  };


  /**
   * Costruttore.
   */
  constructor(
    private advancedActionService: AdvancedActionService,
    private messageService: MessageService,
    private modalService: NgbModal,
    private spinner: NgxSpinnerService,
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
      this.integrazioniColumns = [
        {
          name: 'Nome file',
          prop: 'allegato_integrazione',
          sortable: false,
          pipe: this.pipeNome 
        },
        {
          name: 'Data Integrazione',
          minWidth: 160,
          prop: 'data_invio',
          pipe: this.pipeFormatDate ,
          sortable: false,
        },
        {
          name: 'Numero Protocollo',
          prop: 'num_protocollo',
          sortable: false,
        },
        {
          name: 'Data Protocollo',
          minWidth: 160,
          prop: 'data_protocollo',
          pipe: this.pipeFormatDate ,
          sortable: false,
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
   * Emetto verso alto la lista delle integrazioni
   */
  private _updateIntegrazioni() {
    let integrazioni = [...this.integrazioni];
    const ev = {
      action: 'onUpdateIntegrazioni',
      args: integrazioni
    };
    this.emit.next(ev);
  }

  /**
   * Aggiorno elenco lista dei responsabili da Edit
   * @param integrazione Allegato
   * @returns 
   */
  onEditIntegrazione(integrazione: IntegrazioneIstanza) {
    this.integrazioni = this.integrazioni.map(i => i.id_integrazione_istanza !== integrazione.id_integrazione_istanza ? i : integrazione);
    this._updateIntegrazioni();
  }
  
  /**
   * Click per editare integraione in input che lavora su modale
   * @param integrazione 
   */
  clickOnEditIntegrazione(integrazione: IntegrazioneIstanza) {
    const modalRef = this.modalService.open(AdvancedActionIntegrazioneComponent, {
      centered: true,
      scrollable: true,
      backdrop: 'static',
      size: this.isRiepilogoModal?'lg':'xl',
    });
    modalRef.componentInstance.integrazione = integrazione;
    modalRef.result
      .then((r) => {
        if(r) {
          this.onEditIntegrazione(r);
        }
      })
      .catch(e => {
        
      });
  }
 
  clickOnDownloadIntegrazione(integrazione: IntegrazioneIstanza) {
    this.spinner.show();
    let allegatoIntegrazione = integrazione.allegato_integrazione?.find(i=>i.flg_allegato_rif_protocollo);
    let fileName = allegatoIntegrazione.nome_allegato;
    let uuid_index = allegatoIntegrazione.uuid_index;
    this.advancedActionService.getAllegatoByUuid(uuid_index).subscribe(
      (response) => {
        const nameArr = fileName.split('.');
        const blob = new Blob([response], {
          type: 'application/' + nameArr[nameArr.length - 1],
        });
        const url = window.URL.createObjectURL(blob);
    
        const a = document.createElement('a');
        a.href = url;
        a.download = fileName;
        a.click();
        window.URL.revokeObjectURL(url);
        a.remove();
        this.spinner.hide();
      },
      (err) => {
        if (err.error?.code) {
          this.messageService.showMessage(
            err.error.code,
            'containerUploadDocumenti',
            false
          );
        } else {
          this.messageService.showMessage(
            'E100',
            'containerUploadDocumenti',
            true
          );
        }
      }
    );
  }
}
