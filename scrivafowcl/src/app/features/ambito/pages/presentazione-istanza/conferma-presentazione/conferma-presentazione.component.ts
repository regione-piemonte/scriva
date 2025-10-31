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

import { AutoUnsubscribe } from 'src/app/core/components';
import { Istanza, TipoAdempimento } from 'src/app/shared/models';
import { HelpService, IstanzaService, MessageService } from 'src/app/shared/services';

@Component({
  selector: 'app-conferma-presentazione',
  templateUrl: './conferma-presentazione.component.html',
  styleUrls: ['./conferma-presentazione.component.scss']
})
export class ConfermaPresentazioneComponent extends AutoUnsubscribe implements OnInit {

  codMaschera = '.MSCR011F';
  istanza: Istanza;
  tipoAdempimento: TipoAdempimento;
  infoText = 'Istanza presentata correttamente.';

  constructor(
    private router: Router,
    private istanzaService: IstanzaService,
    private messageService: MessageService,
    private route: ActivatedRoute,
    private helpService: HelpService
  ) {
    super();
  }

  ngOnInit() {
    const idIstanza = this.istanzaService.getIdIstanza();
    if (!idIstanza) {
      console.log('# Error retrieving istanza object: idIstanza not found.');
      return;
    }

    this.helpService.setCodMaschera(this.codMaschera);
    this.istanzaService.getIstanzaById(idIstanza).subscribe(
      res => {
        this.istanza = res;
        this.tipoAdempimento = this.istanza.adempimento.tipo_adempimento;
        this.buildMessage();
      }, err => {
        if (err.error?.code) {
          this.messageService.showMessage(err.error.code, 'confermaPageContainer', true);
        } else {
          this.messageService.showMessage('E100', 'confermaPageContainer', true);
        }
      }
    );
  }

  buildMessage() {
    // verifico se è un caso SUAP e cambio eventualemnte il messaggio
    const jsonData = JSON.parse(this.istanza.json_data);
    const flgSuape = jsonData?.QDR_CONFIG?.FLG_INVIO_A_SUAP_SUE;
    const mess = flgSuape ? this.messageService.messaggi.find(msg => msg.cod_messaggio === 'P024') : this.messageService.messaggi.find(msg => msg.cod_messaggio === 'P012');

    this.infoText = mess.des_testo_messaggio.replace('{PH_COD_ISTANZA}', this.istanza.cod_istanza);
  }

  goToHome() {
    this.istanzaService.setIdIstanza(null);
    this.router.navigate(['/home']);
  }

  goToWizard() {
    this.istanzaService.setIdIstanza(null);
    this.router.navigate([`../../orientamento/${this.tipoAdempimento.cod_tipo_adempimento}`], {
      relativeTo: this.route,
    });
  }
}
