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
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { NgxSpinnerService } from 'ngx-spinner';
import { ColumnMode } from '@swimlane/ngx-datatable';

import { AbilitazioniService, AuthStoreService, HelpService, IstanzaService, MessageService } from 'src/app/shared/services';
import { Compilante, Istanza, IstanzaAttore, TipoAbilitazione } from 'src/app/shared/models';
import { RegexUtil } from 'src/app/shared/utils';

import { SoggettoIstanza } from 'src/app/features/ambito/models';
import { AmbitoService } from 'src/app/features/ambito/services';

import { AutoUnsubscribe } from 'src/app/core/components';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-gestione-abilitazioni',
  templateUrl: './gestione-abilitazioni.component.html',
  styleUrls: ['./gestione-abilitazioni.component.scss']
})
export class GestioneAbilitazioniComponent extends AutoUnsubscribe implements OnInit, AfterViewInit {

  @ViewChild('nominativoTemplate') nominativoTemplate: TemplateRef<any>;
  @ViewChild('dataInizioTemplate') dataInizioTemplate: TemplateRef<any>;
  @ViewChild('dataRevocaTemplate') dataRevocaTemplate: TemplateRef<any>;
  @ViewChild('soggAbilitanteTemplate') soggAbilitanteTemplate: TemplateRef<any>;
  @ViewChild('azioniTemplate') azioniTemplate: TemplateRef<any>;

  idIstanza: number;
  istanza: Istanza;
  denTitolareIstanza: string;

  codMaschera = '.MSCR009D';
  componente: string;
  compilante: Compilante;

  tipiAbilitazione: TipoAbilitazione[] = [];
  abilitazioni: IstanzaAttore[];

  abilitazioneForm: FormGroup;

  columns;
  ColumnMode = ColumnMode;
  showTable = false;

  constructor(
    private datePipe: DatePipe,
    private router: Router,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private authStoreService: AuthStoreService,
    private istanzaService: IstanzaService,
    private ambitoService: AmbitoService,
    private abilitazioniService: AbilitazioniService,
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
    this.buildForm();
    this.helpService.setCodMaschera(this.codMaschera);
    if (this.idIstanza) {
      this.compilante = this.authStoreService.getCompilante();
      this.componente = this.authStoreService.getComponente();

      const getIstanza = this.istanzaService.getIstanzaById(this.idIstanza);
      const getTipiAbilitazione = this.abilitazioniService.getTipiAbilitazione(this.idIstanza);
      const getSoggettiIstanza = this.ambitoService.getSoggettiIstanzaByIstanza(this.idIstanza);

      this.spinner.show();
      forkJoin([getIstanza, getTipiAbilitazione, getSoggettiIstanza]).subscribe(
        res => {
          this.istanza = res[0];
          this.tipiAbilitazione = res[1];
          this.getTitolare(res[2]);
          this.getAbilitazioniConcesse();
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
      { name: 'Codice fiscale', minWidth: 175, prop: 'cf_attore' },
      { name: 'Nominativo', prop: 'compilante.nome_compilante', cellTemplate: this.nominativoTemplate },
      { name: 'Tipo abilitazione', prop: 'tipo_abilitazione.des_tipo_abilitazione' },
      { name: 'Data abilitazione', prop: 'data_inizio', cellTemplate: this.dataInizioTemplate },
      { name: 'Data revoca', prop: 'data_revoca', cellTemplate: this.dataRevocaTemplate },
      { name: 'Soggetto abilitante', cellTemplate: this.soggAbilitanteTemplate, sortable: false },
      { name: 'Azioni', maxWidth: 120, cellTemplate: this.azioniTemplate, sortable: false },
    ];
  }

  buildForm() {
    this.abilitazioneForm = this.fb.group({
      codFiscale: [null, { validators: [Validators.required, Validators.maxLength(16), Validators.pattern(RegexUtil.CF)] }],
      tipoAbilitazione: [null, Validators.required]
    });
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

  getAbilitazioniConcesse() {
    this.abilitazioniService.getAbilitazioniConcesse(this.idIstanza).subscribe(
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

  onInserisciAbilitazione() {
    if (!this.abilitazioneForm.valid) {
      this.messageService.showMessage('E001', 'formCard', true);
      return;
    }

    const nuovaAbilitazione: IstanzaAttore = {
      id_istanza: this.idIstanza,
      tipo_abilitazione: this.abilitazioneForm.get('tipoAbilitazione').value,
      cf_attore: this.abilitazioneForm.get('codFiscale').value,
      cf_abilitante_delegante: this.compilante.cf_compilante,
    };
    this.saveAbilitazione(nuovaAbilitazione);
  }

  onAnnulla() {
    this.abilitazioneForm.reset();
  }

  onRevoca(abilitazione: IstanzaAttore) {
    this.messageService.showConfirmation({
      title: 'Attenzione',
      codMess: 'A017',
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
    abilitazione.data_revoca = this.datePipe.transform(
      date,
      'yyyy-MM-dd HH:mm:ss'
    );
    this.saveAbilitazione(abilitazione);
  }

  saveAbilitazione(abilitazione: IstanzaAttore) {
    this.spinner.show();
    this.abilitazioniService.saveAbilitazione(abilitazione).subscribe(
      res => {
        this.abilitazioneForm.reset();
        this.getAbilitazioniConcesse();
      }, err => {
        this.getAbilitazioniConcesse();
        if (err.error?.code) {
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
