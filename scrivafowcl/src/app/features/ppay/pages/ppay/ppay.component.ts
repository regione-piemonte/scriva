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
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';

import { AutoUnsubscribe } from 'src/app/core/components';
import { MessageService, PagamentiService } from 'src/app/shared/services';
import { EsitoPagamento } from '../../models/esito-pagamento.model';

@Component({
  selector: 'app-ppay',
  templateUrl: './ppay.component.html',
  styleUrls: ['./ppay.component.scss']
})
export class PpayComponent extends AutoUnsubscribe implements OnInit {

  esitoPagamento: EsitoPagamento;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private pagamentiService: PagamentiService,
    private messageService: MessageService,
    private spinner: NgxSpinnerService
  ) {
    super();
  }

  ngOnInit() {
    this.spinner.show();
    this.esitoPagamento = {
      idPagamento: this.route.snapshot.queryParamMap.get('idPagamento'),
      descEsito: this.route.snapshot.queryParamMap.get('descEsito'),
      codEsito: this.route.snapshot.queryParamMap.get('codEsito'),
      source: this.route.snapshot.queryParamMap.get('source'),
    };

    switch (this.esitoPagamento.descEsito) {
      case 'SUCCESSO':
        this.pagamentiService.setPagamentoErrorCode('P004');
        break;
      case 'FALLITO':
        this.pagamentiService.setPagamentoErrorCode('E034');
        break;
      case 'ANNULLATO':
        this.pagamentiService.setPagamentoErrorCode('I016');
        break;
      default:
        break;
    }

    this.pagamentiService.sendPagamentoResult(this.esitoPagamento).subscribe(
      res => {
        const codAdempimento = res.adempimento.cod_adempimento;
        const codAmbito = res.adempimento.tipo_adempimento.ambito.cod_ambito;

        this.router.navigate([`../ambito/${codAmbito}/istanza/${codAdempimento}`], {
          relativeTo: this.route,
          state: {
            idIstanza: res.id_istanza,
            attoreGestioneIstanza: 'WRITE',
            pagamentoFlag: true
          }
        });
      }, err => {
        this.messageService.showMessage('E100', 'emptyDiv', false, null, 'Si è verificato un errore. Tra qualche secondo verrai reindirizzato alla pagina principale.');
        const timer = setTimeout( () => {
          this.router.navigate(['/home']);
        }, 5000);
      }
    );
  }

}
