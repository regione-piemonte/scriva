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

import { Ambito, Compilante, Help, Preferenza, TipoAdempimento } from 'src/app/shared/models';
import { MessageService, AdempimentoService, AmbitiService, IstanzaService, AuthStoreService, HelpService } from 'src/app/shared/services';

import { AutoUnsubscribe } from 'src/app/core/components';

@Component({
  selector: 'app-ambito-dashboard',
  templateUrl: './ambito-dashboard.component.html',
  styleUrls: ['./ambito-dashboard.component.scss'],
})
export class AmbitoDashboardComponent
  extends AutoUnsubscribe
  implements OnInit
{
  ambito: Ambito;
  tipiAdempimento: TipoAdempimento[];
  tipiAdempimentoPreferiti: TipoAdempimento[];
  compilante: Compilante;
  componente: string;
  isFrontOffice: boolean;

  selectedTab: string;

  title = '';
  codMaschera: string;
  helpList: Help[];

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private authStoreService: AuthStoreService,
    private adempimentoService: AdempimentoService,
    private ambitiService: AmbitiService,
    private istanzaService: IstanzaService,
    private helpService: HelpService,
    private spinner: NgxSpinnerService,
    private ms: MessageService
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
      // } else {
      //   this.funzionario = this.authStoreService.getFunzionario();
    }
  }

  ngOnInit() {
    this.istanzaService.setIdIstanza(null);

    this.route.paramMap.subscribe( params => {
      const codAmbito = params.get('codAmbito');
      this.tipiAdempimento = [];
      this.title = 'Adempimenti';

      this.ambitiService.getAmbitoByCode(codAmbito).subscribe(
        res => {
          this.ambito = res;
          this.title = 'Adempimenti ' + this.ambito?.des_ambito;
          this.getTipiAdempimento();
          this.getHelpList();
        }, err => {
          this.ms.showMessage('E100', 'pageContainer', true);
        }
      );
    });
  }

  getTipiAdempimento() {
    let getTipiAdempimento$;
    let initialTab;
    if (this.isFrontOffice) {
      getTipiAdempimento$ = this.adempimentoService.getTipiAdempimentoByCompilante(this.compilante.id_compilante);
      initialTab = 'fav';
    } else {
      getTipiAdempimento$ = this.adempimentoService.getTipiAdempimento();
      initialTab = 'all';
    }

    this.spinner.show();
    getTipiAdempimento$.subscribe(
      (res: TipoAdempimento[]) => {
        this.tipiAdempimento = res.filter(
          (tipo) => tipo.ambito.id_ambito === this.ambito.id_ambito
        );
        this.sortTipiAdempimento();
        this.selectTab(initialTab);
        this.spinner.hide();
      },
      (error) => {
        if (error.error?.code) {
          this.ms.showMessage(error.error.code, 'pageContainer', false);
        } else {
          this.ms.showMessage('E100', 'pageContainer', true);
        }
      }
    );
  }

  sortTipiAdempimento() {
    const tipiPreferiti = this.tipiAdempimento
      .filter((tipo) => tipo.preferito)
      ?.sort(
        (tipoA, tipoB) =>
          tipoA.ordinamento_tipo_adempimento -
          tipoB.ordinamento_tipo_adempimento
      );
    const tipiNonPreferiti = this.tipiAdempimento
      .filter((tipo) => !tipo.preferito)
      ?.sort(
        (tipoA, tipoB) =>
          tipoA.ordinamento_tipo_adempimento -
          tipoB.ordinamento_tipo_adempimento
      );

    this.tipiAdempimento = [...tipiPreferiti, ...tipiNonPreferiti];
    this.tipiAdempimentoPreferiti = tipiPreferiti;
  }

  selectTab(tab: string) {    
    this.selectedTab = tab;
    this.codMaschera = tab === 'all' ? '.MSCR002D' : '.MSCR003D';
    this.helpService.setCodMaschera(this.codMaschera);
    this.helpService.setCodContesto(undefined);
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
          this.componente + this.codMaschera
        );
      }
    );
  }

  goToFavTab() {
    this.selectTab('all');
  }

  goToWizard(tipo: TipoAdempimento) {
    this.router.navigate([`./orientamento/${tipo.cod_tipo_adempimento}`], {
      relativeTo: this.route,
      state: {
        clearIdIstanza: true,
        startingPage: `ambito/${this.ambito.cod_ambito}`,
      },
    });
  }

  cercaIstanze(event) {
    this.router.navigate(['/ricerca'], {
      //relativeTo: this.route.parent,
      state: {
        status: event?.status,
        flgDaPagare: event?.flgDaPagare,
        tipoAdempimento: event?.tipoAdempimento?.cod_tipo_adempimento,
        codAmbito: event?.tipoAdempimento.ambito.cod_ambito
      },
    });
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
          this.tipiAdempimento.forEach((elem) => {
            if (elem.id_tipo_adempimento === tipoAdemp.id_tipo_adempimento) {
              elem.preferito = res.id_preferenza;
            }
          });
          this.sortTipiAdempimento();
          this.spinner.hide();
        },
        (err) => {
          this.ms.showMessage('E100', 'pageContainer', true);
        }
      );
    } else {
      this.adempimentoService.cancellaPreferenza(tipoAdemp.preferito).subscribe(
        (res) => {
          this.tipiAdempimento.forEach((elem) => {
            if (elem.id_tipo_adempimento === tipoAdemp.id_tipo_adempimento) {
              elem.preferito = 0;
            }
          });
          this.sortTipiAdempimento();
          this.spinner.hide();
        },
        (err) => {
          this.ms.showMessage('E100', 'pageContainer', true);
        }
      );
    }
  }
}
