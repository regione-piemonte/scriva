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
import { forkJoin, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { HomeService } from '../../services';

import {
  Compilante,
  IFunzionarioAutorizzato,
  Help,
  IstanzaSearch,
  Preferenza,
  TipoAdempimento,
} from 'src/app/shared/models';
import {
  AuthStoreService,
  AdempimentoService,
  MessageService,
  HelpService,
  ConfigurazioniScrivaService,
} from 'src/app/shared/services';
import { StatoIstanzaEnum } from 'src/app/shared/utils';
import { AutoUnsubscribe } from 'src/app/core/components';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent extends AutoUnsubscribe implements OnInit {
  compilante: Compilante;
  funzionario: IFunzionarioAutorizzato;
  componente: string;
  isFrontOffice: boolean;

  notificheList = null;
  scadenzeList = null;
  tipiAdempimentoList: TipoAdempimento[] = [];
  istanzeGuidateList = null;

  inBozza: { count; istanze: IstanzaSearch[] } = { count: 0, istanze: [] };
  daIntegrare: { count: number; istanze: IstanzaSearch[] } = {
    count: 0,
    istanze: [],
  };
  daPagare: { count; istanze: IstanzaSearch[] } = { count: 0, istanze: [] };

  presentate: { count: number; istanze: IstanzaSearch[] } = {
    count: 0,
    istanze: [],
  };
  inCorso: { count; istanze: IstanzaSearch[] } = { count: 0, istanze: [] };
  inCarico: { count; istanze: IstanzaSearch[] } = { count: 0, istanze: [] };

  StatoIstanzaEnum = StatoIstanzaEnum;

  alertText: string;
  showAlertBanner = false;

  codMaschera = '.MSCR001D';
  helpList: Help[];

  concludedBlock$ = new Subject();
  concludedBlocksCount = 0;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private authStoreService: AuthStoreService,
    private homeService: HomeService,
    private adempimentoService: AdempimentoService,
    private messageService: MessageService,
    private helpService: HelpService,
    private configurazioniService: ConfigurazioniScrivaService,
    private spinner: NgxSpinnerService
  ) {
    super();
    this.componente = this.authStoreService.getComponente();
    if (this.componente === 'BO') {
      this.isFrontOffice = false;
    } else {
      this.isFrontOffice = true;
    }
  }

  ngOnInit() {
    /* mock */
    this.mockNotifiche(); // -> forkJoin block #1
    this.mockScadenze(); // -> forkJoin block #1
    this.mockIstanzeGuidate(); // -> call block #4
    /* --- */

    this.spinner.show();
    this.helpService.setCodMaschera(this.codMaschera);
    this.helpService.setCodContesto(undefined);
    if (this.isFrontOffice) {
      this.compilante = this.authStoreService.getCompilante();
    } else {
      this.funzionario = this.authStoreService.getFunzionarioAutorizzato();
    }

    this.concludedBlock$.pipe(takeUntil(this.destroy$)).subscribe(() => {
      this.concludedBlocksCount++;
      if (this.concludedBlocksCount === 3) {
        // todo: change to 4 when block #4 is implemented
        this.spinner.hide();
      }
    });

    /*
     * block #1 - Notifiche, scadenze, help...
     */
    // const getHelpList = this.helpService.getHelpByChiave(this.componente + this.codMaschera);
    const getHelpList = this.helpService.getHelpByChiave('info_card');
    const getInfoMessage = this.configurazioniService.getInfoMessage(
      this.componente
    );
    // const getNotifiche = ...
    // const getScadenze = ...

    forkJoin([getHelpList, getInfoMessage]).subscribe(
      (result) => {
        this.helpList = result[0].filter((help) =>
          help.chiave_help.startsWith(this.componente)
        );

        if (result[1][0].valore !== 'NA') {
          const codMess = this.isFrontOffice ? 'I017' : 'I018';
          const mess = this.messageService.messaggi.find(
            (m) => m.cod_messaggio === codMess
          );
          this.alertText = mess.des_testo_messaggio.replace(
            `{PH_MSG_${this.componente}}`,
            result[1][0].valore
          );

          // style from .scss file doesn't apply
          const styleRules = document.createElement('style');
          styleRules.innerHTML =
            '.alert-box h3 { font-size: 1rem; line-height: 1.5; margin: 0; } .alert-box { font-size: 0.875rem; }';
          document.body.appendChild(styleRules);

          this.showAlertBanner = true;
        }

        this.concludedBlock$.next();
      },
      (err) => {
        this.messageService.showMessage('E100', 'errorAnchor', true);
        this.spinner.show();
        this.concludedBlock$.next();
      }
    );

    /*
     * block #2 - Adempimenti
     */
    if (this.isFrontOffice) {
      this.adempimentoService
        .getTipiAdempimentoByCompilante(this.compilante.id_compilante)
        .subscribe(
          (res) => {
            this.tipiAdempimentoList = res;
            this.sortTipiAdempimento();
            this.concludedBlock$.next();
          },
          (err) => {
            this.messageService.showMessage('E100', 'errorAnchor', true);
            this.spinner.show();
            this.concludedBlock$.next();
          }
        );
    } else {
      this.adempimentoService.getTipiAdempimento().subscribe(
        (res) => {
          this.tipiAdempimentoList = res;
          this.sortTipiAdempimento();
          this.concludedBlock$.next();
        },
        (err) => {
          this.messageService.showMessage('E100', 'errorAnchor', true);
          this.spinner.show();
          this.concludedBlock$.next();
        }
      );
    }

    /*
     * block #3 - Riepilogo istanze
     */
    const getInBozza = this.homeService.simpleSearch({
      stato_istanza: StatoIstanzaEnum.BOZZA,
    });

    const getDaIntegrare = this.homeService.simpleSearch({
      label_stato: 'Richiesta Integrazione',
    });

    const getDaPagare = this.homeService.simpleSearch({
      des_stato_sintesi_pagamento: 'DA EFFETTUARE',
    });

    const getPresentate = this.homeService.simpleSearch({
      stato_istanza: StatoIstanzaEnum.PRESENTATA,
    });

    const getInCorso = this.homeService.simpleSearch({
      stato_istanza: StatoIstanzaEnum.IN_CORSO,
    });

    const getInCarico = this.homeService.simpleSearch({
      stato_istanza: StatoIstanzaEnum.PRESA_IN_CARICO,
    });

    if (this.isFrontOffice) {
      forkJoin([getInBozza, getDaIntegrare, getDaPagare]).subscribe(
        (result) => {
          this.inBozza = result[0];
          this.daIntegrare = result[1];
          this.daPagare = result[2];
          this.concludedBlock$.next();
        },
        (error) => {
          this.messageService.showMessage('E100', 'errorAnchor', true);
          this.spinner.show();
          this.concludedBlock$.next();
        }
      );
    } else {
      forkJoin([getPresentate, getInCorso, getDaIntegrare]).subscribe(
        (result) => {
          this.presentate = result[0];
          this.inCorso = result[1];
          this.daIntegrare = result[2];
          this.concludedBlock$.next();
        },
        (error) => {
          this.messageService.showMessage('E100', 'errorAnchor', true);
          this.spinner.show();
          this.concludedBlock$.next();
        }
      );
    }
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

  showInfoHelp(tipoAdempimento: TipoAdempimento) {
    const valoreCampo =
      tipoAdempimento.ambito.cod_ambito +
      '.' +
      tipoAdempimento.cod_tipo_adempimento;
    const modalContent =
      this.helpList.find((help) => help.valore_campo_help === valoreCampo)
        ?.des_testo_help || 'Help non trovato...';

    this.messageService.showConfirmation({
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

  cercaIstanze(event, options = {}) {
    // SCRIVA-1464
    // se passo opzioni annullo lo status che passerebbe stato_istanza
    // caso con label_stato per esempio
    this.router.navigate(['../ricerca'], {
      relativeTo: this.route.parent,
      state: {
        status: Object.keys(options).length === 0 ? event?.status : '',
        flgDaPagare: event?.flgDaPagare,
        tipoAdempimento: event?.tipoAdempimento?.cod_tipo_adempimento,
        codAmbito: event?.tipoAdempimento?.ambito.cod_ambito,
        options: options,
      },
    });
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
          startingPage: 'home',
        },
      }
    );
  }

  goToAdempimenti() {
    this.router.navigate([`../adempimenti`], { relativeTo: this.route.parent });
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

  /* MOCK */
  mockNotifiche() {
    this.notificheList = [
      {
        titolo: 'Edilizia',
        testo:
          "L'istanza MUDE Nr. di riferimento: 0100127200004169492020 è stata accettata",
        data: '09 ott 2019 14:23',
      },
      {
        titolo: 'Notifica numero 2',
        testo:
          'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor',
        data: '09 ott 2019 14:23',
      },
      {
        titolo: 'Notifica numero 3',
        testo:
          'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor',
        data: '09 ott 2019 14:23',
      },
      {
        titolo: 'Notifica numero 4',
        testo:
          'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor',
        data: '09 ott 2019 14:23',
      },
    ];
  }

  /* MOCK */
  mockScadenze() {
    this.scadenzeList = [
      {
        titolo: 'Scadenza numero 1',
        testo:
          'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor',
        data: '09 ott 2019 14:23',
      },
      {
        titolo: 'Scadenza numero 2',
        testo:
          'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor',
        data: '09 ott 2019 14:23',
      },
      {
        titolo: 'Scadenza numero 3',
        testo:
          'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor',
        data: '09 ott 2019 14:23',
      },
      {
        titolo: 'Scadenza numero 4',
        testo:
          'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor',
        data: '09 ott 2019 14:23',
      },
    ];
  }

  /* MOCK */
  mockIstanzeGuidate() {
    this.istanzeGuidateList = [
      {
        titolo: 'TAV',
        testo:
          'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor',
        data: '18/07/19',
      },
      {
        titolo: 'Viadotti',
        testo:
          'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor',
        data: '18/07/19',
      },
      {
        titolo: 'Centri commerciali',
        testo:
          'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor',
        data: '18/07/19',
      },
      {
        titolo: 'Edifici',
        testo:
          'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor',
        data: '18/07/19',
      },
      {
        titolo: 'Parchi divertimento',
        testo:
          'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor',
        data: '18/07/19',
      },
      {
        titolo: 'Stazioni ferroviarie',
        testo:
          'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor',
        data: '18/07/19',
      },
      {
        titolo: 'Casinò',
        testo:
          'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor',
        data: '18/07/19',
      },
      {
        titolo: 'Altra opera',
        testo:
          'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor',
        data: '18/07/19',
      },
    ];
  }
}
