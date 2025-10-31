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

import { Compilante, Help, Preferenza, TipoAdempimento } from 'src/app/shared/models';
import { AdempimentoService, AuthStoreService, HelpService, MessageService } from 'src/app/shared/services';

@Component({
  selector: 'app-activities',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss'],
})
export class MainComponent extends AutoUnsubscribe implements OnInit {
  compilante: Compilante;
  componente: string;
  isFrontOffice: boolean;
  tipiAdempimentoList: TipoAdempimento[] = [];

  helpList: Help[];
  codMaschera = '.MSCR002D';

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private authStoreService: AuthStoreService,
    private adempimentoService: AdempimentoService,
    private messageService: MessageService,
    private spinner: NgxSpinnerService,
    private ms: MessageService,
    private helpService: HelpService
  ) {
    super();
    this.componente = this.authStoreService.getComponente();
    if (this.componente === 'BO') {
      this.isFrontOffice = false;
    } else {
      this.isFrontOffice = true;
    }

    if (this.isFrontOffice) {
      this.compilante = this.authStoreService.getCompilante();
    }
    
  }

  ngOnInit() {   
    this.helpService.setCodMaschera(this.codMaschera);
    this.helpService.setCodContesto(undefined);
    this.getHelpList();    
    let getTipiAdempimento$;
    if (this.isFrontOffice) {
      getTipiAdempimento$ =
        this.adempimentoService.getTipiAdempimentoByCompilante(
          this.compilante.id_compilante
        );
    } else {
      getTipiAdempimento$ = this.adempimentoService.getTipiAdempimento();
    }

    this.spinner.show();
    getTipiAdempimento$.subscribe(
      (res) => {        
        this.tipiAdempimentoList = res;
        this.sortTipiAdempimento();
        this.spinner.hide();
      },
      (err) => {
        this.messageService.showMessage('E100', 'pageContainer', true);
      }
    );
  }

  sortTipiAdempimento() {
    const tipiPreferiti = this.tipiAdempimentoList
      .filter((tipo) => tipo.preferito)
      ?.sort(
        (tipoA, tipoB) =>
          tipoA.ordinamento_tipo_adempimento -
          tipoB.ordinamento_tipo_adempimento
      );
    const tipiNonPreferiti = this.tipiAdempimentoList
      .filter((tipo) => !tipo.preferito)
      ?.sort(
        (tipoA, tipoB) =>
          tipoA.ordinamento_tipo_adempimento -
          tipoB.ordinamento_tipo_adempimento
      );

    this.tipiAdempimentoList = [...tipiPreferiti, ...tipiNonPreferiti];
  }

  goToWizard(tipo: TipoAdempimento) {
    this.router.navigate(
      [
        `../ambito/${tipo.ambito.cod_ambito}/orientamento/${tipo.cod_tipo_adempimento}`,
      ],
      {
        relativeTo: this.route.parent,
        state: {
          clearIdIstanza: true,
          startingPage: 'adempimenti',
        },
      }
    );
  }

  toggleFavourite(tipoAdemp: TipoAdempimento) {
    // SCRIVA-1534 Sul BO NON VA GESTITA LA PREFERENZA, se l'utente seleziona la stellina nella card non deve succedere nulla (non chiedono di nascondere l'icona -.-)
    if (!this.isFrontOffice) {
      return;
    }
    this.spinner.show();
    if (!tipoAdemp.preferito) {
      const pref: Preferenza = {
        compilante: this.compilante,
        tipo_adempimento: tipoAdemp,
      };
      this.adempimentoService.salvaPreferenza(pref).subscribe(
        (res) => {
          this.tipiAdempimentoList.forEach((elem) => {
            if (elem.id_tipo_adempimento === tipoAdemp.id_tipo_adempimento) {
              elem.preferito = res.id_preferenza;
            }
          });
          this.sortTipiAdempimento();
          this.spinner.hide();
        },
        (err) => {
          this.messageService.showMessage('E100', 'pageContainer', true);
        }
      );
    } else {
      this.adempimentoService.cancellaPreferenza(tipoAdemp.preferito).subscribe(
        (res) => {
          this.tipiAdempimentoList.forEach((elem) => {
            if (elem.id_tipo_adempimento === tipoAdemp.id_tipo_adempimento) {
              elem.preferito = 0;
            }
          });
          this.sortTipiAdempimento();
          this.spinner.hide();
        },
        (err) => {
          this.messageService.showMessage('E100', 'pageContainer', true);
        }
      );
    }
  }

  cercaIstanze(event) {
    this.router.navigate(['../ricerca'], {
      relativeTo: this.route.parent,
      state: {
        status: event?.status,
        flgDaPagare: event?.flgDaPagare,
        tipoAdempimento: event?.tipoAdempimento?.cod_tipo_adempimento,
      },
    });
  }

  getHelpList() {
    this.helpService.getHelpByChiave('info_card').subscribe(
      (res) => {        
        this.helpList = res.filter((help) =>
          help.chiave_help.startsWith(this.componente)
        );        
      },
      (err) => {
        if (err.error?.code) {
          this.ms.showMessage(err.error.code, 'pageContainer', false);
        } else {
          this.ms.showMessage('E100', 'pageContainer', true);
        }
        console.log(
          '# Error retrieving help array ',
          this.componente + 'MSCR004D'
        );
      }
    );
  }

  showInfoHelp(tipoAdempimento: TipoAdempimento) {
    const valoreCampo =
      tipoAdempimento.ambito.cod_ambito +
      '.' +
      tipoAdempimento.cod_tipo_adempimento;
    const modalContent =
      this.helpList.find((help) => help.valore_campo_help === valoreCampo)
        ?.des_testo_help || 'Help non trovato...';

    this.ms.showConfirmation({
      title: `${tipoAdempimento.ambito.des_ambito} - ${tipoAdempimento.des_tipo_adempimento}`,
      codMess: null,
      content: modalContent,
      buttons: [
        {
          label: 'CHIUDI',
          type: 'btn-primary single',
          callback: () => {},
        },
      ],
    });
  }
}
