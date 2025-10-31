/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import {
  ChangeDetectorRef,
  Component,
  Inject,
  Input,
  LOCALE_ID,
  OnInit
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BaseComponent } from '@app/core';
import {
  CompetenzaTerritorio,
  Comune,
  Pratica,
  UbicazioneOggetto
} from '@app/pages/pratiche/model';
import { PraticheService } from '@app/pages/pratiche/services/pratiche.service';
import { LoadingService } from '@app/theme/layouts/loading.services';
import { takeUntil } from 'rxjs/operators';
import { CategoriaProgettuale } from '@pages/pratiche/model/categoria-progettuale.model';
import { I18nService } from '@app/core/translate/lib/services/i18n.service';

@Component({
  selector: 'app-riepilogo-pratica',
  templateUrl: './riepilogo-pratica.component.html',
  styleUrls: ['./riepilogo-pratica.component.scss']
})
export class RiepilogoPraticaComponent extends BaseComponent implements OnInit {
  pratica: Pratica;
  competenzaPratica: CompetenzaTerritorio[];
  competenzaStatale: CompetenzaTerritorio[];
  flagValutazioneIncidenza = false;
  flagInfraStrategica = false;
  categorie: CategoriaProgettuale[] = [];
  proponente: string;

  titleResponsabili: string = '';
  dataResponsabili: any[][] = [];
  columnsResponsabili: string[] = [];

  constructor(
    private loadingService: LoadingService,
    private route: ActivatedRoute,
    private praticheService: PraticheService,
    private cdr: ChangeDetectorRef,
    private i18n: I18nService,
    @Inject(LOCALE_ID) public locale: string
  ) {
    super();
    this.loadingService.hide();
  }

  ngOnInit(): void {
    this.getDetail();
    this.initDataTable();
  }

  getDetail(): void {
    this.pratica = this.route.snapshot.data.pratica;

    // handle proponente
    if (
      this.pratica.soggetto_istanza &&
      this.pratica.soggetto_istanza.length > 0
    ) {
      if (
        this.pratica.soggetto_istanza[0].tipo_soggetto.cod_tipo_soggetto ===
        'PF'
      ) {
        this.proponente = this.pratica.soggetto_istanza[0].cognome + ' ' + this.pratica.soggetto_istanza[0].nome;
      } else {
        this.proponente = this.pratica.soggetto_istanza[0].denominazione;
      }
    }
    if (
      this.pratica.oggetto_istanza &&
      this.pratica.oggetto_istanza.length > 0
    ) {
      this.getCategorieProgettuali();
      this.getValutazioneIncidenza();
      this.getInfrastrutturaStrategica();
    }
    this.competenzaPratica = this.getCompetenzaPratica();
    this.competenzaStatale = this.getCompetenzaStatale();

    this.cdr.detectChanges();
  }

  getCategorieProgettuali() {
    this.praticheService
      .getCategorieProgettuali(
        this.pratica.oggetto_istanza[0].id_oggetto_istanza
      )
      .pipe(takeUntil(this.destroy$))
      .subscribe((value: CategoriaProgettuale[]) => {
        this.categorie = value;
      });
  }

  getValutazioneIncidenza() {
    this.praticheService
      .getValutazioneIncidenza(this.pratica.id_istanza)
      .pipe(takeUntil(this.destroy$))
      .subscribe((value) => {
        if (value) {
          this.flagValutazioneIncidenza = true;
        }
      });
  }

  getInfrastrutturaStrategica() {
    this.praticheService
      .getInfrastrutturaStrategica(this.pratica.id_istanza)
      .pipe(takeUntil(this.destroy$))
      .subscribe((value) => {
        if (value) {
          this.flagInfraStrategica = true;
        }
      });
  }

  getCompetenzaPratica(): CompetenzaTerritorio[] {
    // pesco le autorità assegnate da BO
    const competenzaAssegnataDaBO = this.pratica.competenza_territorio.filter(
      (competenza) => competenza.flg_autorita_assegnata_bo
    );

    if (competenzaAssegnataDaBO.length === 0) {
      // se non ci sono autorità assegnate da BO metto quelle principali
      // che non sono a procedimento statale
      const competenzaPrincipale = this.pratica.competenza_territorio.filter(
        (competenza) =>
          competenza.flg_autorita_principale &&
          competenza.ind_assegnata_da_sistema !== '2'
      );
      if (competenzaPrincipale.length === 0) {
        return this.pratica.competenza_territorio;
      }
      return competenzaPrincipale;
    }

    return competenzaAssegnataDaBO;
  }

  getCompetenzaStatale(): CompetenzaTerritorio[] {
    // pesco le autorità competenti che sono a procedimento a statale
    // ma che non sono state assegnate da BO
    const competenzaStatale = this.pratica.competenza_territorio.filter(
      (competenza) =>
        competenza.ind_assegnata_da_sistema === '2' &&
        !competenza.flg_autorita_assegnata_bo
    );

    return competenzaStatale;
  }

  initDataTable(): void {
    if (this.pratica?.responsabili_istanza) {
      this.titleResponsabili = this.i18n.translate(
        'PRACTICE.RIF_PROCEDIMENTO.TITLE'
      );

      this.columnsResponsabili = [
        this.i18n.translate('PRACTICE.RIF_PROCEDIMENTO.TIPOLOGIA'),
        this.i18n.translate('PRACTICE.RIF_PROCEDIMENTO.DENOMINAZIONE'),
        this.i18n.translate('PRACTICE.RIF_PROCEDIMENTO.RECAPITO')
      ];

      this.pratica.responsabili_istanza.forEach((resp) => {
        const valoriRiga: any[] = [
          resp.label_responsabile,
          resp.nominativo_responsabile,
          resp.recapito_responsabile
        ];
        this.dataResponsabili.push(valoriRiga);
      });
    }
  }

  trackUbicazione(index: number, item: UbicazioneOggetto) {
    return item.id_oggetto_istanza;
  }

  trackComune(index: number, item: Comune) {
    return item.id_comune;
  }

  preventSet() {
    return false;
  }
}
