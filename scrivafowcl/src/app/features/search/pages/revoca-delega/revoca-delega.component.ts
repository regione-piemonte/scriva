/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { AfterViewInit, Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ColumnMode } from '@swimlane/ngx-datatable';
import { forkJoin } from 'rxjs';
import { NgxSpinnerService } from 'ngx-spinner';

import { Istanza, IstanzaAttore } from 'src/app/shared/models';
import { AbilitazioniService, AuthStoreService, HelpService, IstanzaService, MessageService } from 'src/app/shared/services';

import { SoggettoIstanza } from 'src/app/features/ambito/models';
import { AmbitoService } from 'src/app/features/ambito/services';

import { AutoUnsubscribe } from 'src/app/core/components';

@Component({
  selector: 'app-revoca-delega',
  templateUrl: './revoca-delega.component.html',
  styleUrls: ['./revoca-delega.component.scss']
})
export class RevocaDelegaComponent extends AutoUnsubscribe implements OnInit, AfterViewInit {

  @ViewChild('nominativoTemplate') nominativoTemplate: TemplateRef<any>;
  @ViewChild('dataRevocaTemplate') dataRevocaTemplate: TemplateRef<any>;
  @ViewChild('azioniTemplate') azioniTemplate: TemplateRef<any>;

  idIstanza: number;
  istanza: Istanza;
  denTitolareIstanza: string;

  codMaschera = '.MSCR010D';
  componente: string;

  abilitazioni: IstanzaAttore[];

  columns;
  ColumnMode = ColumnMode;
  showTable = false;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private istanzaService: IstanzaService,
    private ambitoService: AmbitoService,
    private abilitazioniService: AbilitazioniService,
    private authStoreService: AuthStoreService,
    private helpService: HelpService,
    private spinner: NgxSpinnerService,
    private messageService: MessageService,
  ) {
    super();
    const state = this.router.getCurrentNavigation().extras.state;
    if (state) {
      this.idIstanza = state['idIstanza'];
    }
  }

  ngOnInit() {
    this.helpService.setCodMaschera(this.codMaschera);
    if (this.idIstanza) {
      this.componente = this.authStoreService.getComponente();

      const getIstanza = this.istanzaService.getIstanzaById(this.idIstanza);
      const getSoggettiIstanza = this.ambitoService.getSoggettiIstanzaByIstanza(this.idIstanza);

      this.spinner.show();
      forkJoin([getIstanza, getSoggettiIstanza]).subscribe(
        res => {
          this.istanza = res[0];
          this.getTitolare(res[1]);
          this.getAbilitazioniRevocabili();
        },
        err => {
          if (err.error?.code) {
            this.messageService.showMessage(err.error.code, 'formCard', true);
          } else {
            this.messageService.showMessage('E100', 'formCard', true);
          }
        }
      );
    } else {
      this.onIndietro();
    }
  }

  ngAfterViewInit() {
    this.columns = [
      { name: 'Codice fiscale', prop: 'cf_attore' },
      { name: 'Nominativo', cellTemplate: this.nominativoTemplate },
      { name: 'Data revoca', prop: 'data_revoca', cellTemplate: this.dataRevocaTemplate },
      { name: 'Azioni', cellTemplate: this.azioniTemplate, sortable: false },
    ];
  }

  getFormattedDate(date) {
    if (date) {
      const dateSplit = date.split(' ')[0].split('-');
      return dateSplit[2] + '/' + dateSplit[1] + '/' + dateSplit[0];
    }
  }

  getTitolare(soggettiIstanza: SoggettoIstanza[]) {
    const titolareIstanza = soggettiIstanza.find(soggIst => !soggIst.id_soggetto_padre);
    this.denTitolareIstanza = titolareIstanza.den_soggetto || titolareIstanza.cognome + titolareIstanza.nome;
  }

  getAbilitazioniRevocabili() {
    this.abilitazioniService.getAbilitazioniRevocabili(this.idIstanza).subscribe(
      res => {
        this.abilitazioni = res;
        if (this.abilitazioni.length > 0) {
          this.showTable = true;
        }
        this.spinner.hide();
      },
      err => {
        if (err.status === 404) {
          this.spinner.hide();
          return;
        }

        if (err.error?.code) {
          this.messageService.showMessage(err.error.code, 'formCard', true);
        } else {
          this.messageService.showMessage('E100', 'formCard', true);
        }
      }
    );
  }

  onRevoca(abilitazione: IstanzaAttore) {
    this.messageService.showConfirmation({
      title: 'Attenzione',
      codMess: 'A018',
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
            this.rimuoviAbilitazione(abilitazione);
          },
        },
      ],
    });
  }

  rimuoviAbilitazione(abilitazione: IstanzaAttore) {
    const date = new Date();
    date.setHours( date.getHours() + 2);
    abilitazione.data_revoca = date.toISOString();
    this.saveAbilitazione(abilitazione);
  }

  saveAbilitazione(abilitazione: IstanzaAttore) {
    this.spinner.show();
    this.abilitazioniService.saveAbilitazione(abilitazione).subscribe(
      res => {
        this.getAbilitazioniRevocabili();
      }, err => {
        if (err.error?.code) {
          this.getAbilitazioniRevocabili();
          this.messageService.showMessage(err.error.code, 'formCard', true);
        } else {
          this.messageService.showMessage('E100', 'formCard', true);
        }
      }
    );
  }

  goToRicerca() {
    this.router.navigate(['/ricerca']);
  }

  onIndietro() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }

}
