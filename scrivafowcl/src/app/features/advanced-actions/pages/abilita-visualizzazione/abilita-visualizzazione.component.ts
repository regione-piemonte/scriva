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
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ValidationErrors,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import {
  AdempimentoService,
  AuthStoreService,
  ConfigurazioniScrivaService,
  HelpService,
  IstanzaService,
  MessageService,
} from 'src/app/shared/services';
import { AmbitoService } from 'src/app/features/ambito/services';
import { AbstractAdvancedAction } from '../abstract-advanced-action';
import { ScrivaNoteService } from 'src/app/shared/services/scriva-note/scriva-note.service';
import {
  AdvancedActionsChiavi,
  AdvancedActionsMaschere,
} from '../../enums/advanced-actions.enums';
import { forkJoin, of, Observable } from 'rxjs';
import { AbstractAdvancedActionInterface } from '../../interfaces/abstract-advanced-action.interface';
import { catchError, map, switchMap } from 'rxjs/operators';
import { DatePipe } from '@angular/common';
import * as moment from 'moment';
import { Adempimento, ConfigAdempimento, Istanza } from 'src/app/shared/models';
import { TipoInfoAdempimento } from 'src/app/shared/services/configurazioni/utilities/configurazioni.enums';
import { AdvancedActionService } from '../../services/advanced-action.service';
import { TipoEventoEnum } from 'src/app/shared/utils';
import { ScrivaUtilitiesService } from '../../../../shared/services/scriva-utilities/scriva-utilities.service';

@Component({
  selector: 'app-abilita-visualizzazione',
  templateUrl: './abilita-visualizzazione.component.html',
  styleUrls: ['./abilita-visualizzazione.component.scss'],
})
export class AbilitaVisualizzazioneComponent
  extends AbstractAdvancedAction
  implements OnInit, AbstractAdvancedActionInterface
{
  chiave: AdvancedActionsChiavi = AdvancedActionsChiavi.PUBBLICA;
  codMaschera = AdvancedActionsMaschere.PUBBLICA;

  tipoEventoEnum = TipoEventoEnum;
  adempimento: Adempimento;
  info: TipoInfoAdempimento = TipoInfoAdempimento.osservazioni;
  configsAdempimento: ConfigAdempimento[] = [];

  formPeriodo: FormGroup;
  today = new Date();

  showDates: boolean = false;
  dateError: boolean = false;

  constructor(
    public router: Router,
    public fb: FormBuilder,
    public _istanza: IstanzaService,
    public adempimentoService: AdempimentoService,
    public ambitoService: AmbitoService,
    public authStoreService: AuthStoreService,
    public helpService: HelpService,
    public _message: MessageService,
    public route: ActivatedRoute,
    public _spinner: NgxSpinnerService,
    public scrivaNoteService: ScrivaNoteService,
    protected configurazioniService: ConfigurazioniScrivaService,
    private datePipe: DatePipe,
    public advancedActionService: AdvancedActionService,
    private _scrivaUtilities: ScrivaUtilitiesService
  ) {
    super(
      router,
      fb,
      _istanza,
      adempimentoService,
      ambitoService,
      authStoreService,
      helpService,
      _message,
      route,
      _spinner,
      scrivaNoteService,
      advancedActionService
    );

    if (!this.idIstanza) {
      this.goToSearchPage();
      return;
    }

    this.scrivaBreadCrumbItems = [
      {
        emitAction: 'goToSearchPage',
        label: 'Ricerca',
        ariaLabel: 'ricerca',
      },
      {
        label: 'Abilita visualizzazione web procedimento',
      },
    ];
  }

  ngOnInit() {
    // se non è settata setto una idIstanza mock
    if (!this.idIstanza) {
      this.idIstanza = 1175;
    }
    super.ngOnInit();
    this.buildForm();

    this.loadData();
    // istanza && denTitolareIstanza possiamo credo caricarli su AbstractAdvancedAction
    // dovrebbe essere comune a tutte le azioni avanzate
  }

  checkDataFineInizio() {
    const dataFine = this.formPeriodo.get('dataFine').value;
    const dataInizio = this.formPeriodo.get('dataInizio').value;
    if (dataInizio && dataFine && new Date(dataFine) < new Date(dataInizio)) {
      this.dateError = true;
    } else {
      this.dateError = false;
    }
  }

  configAdempimenti() {
    this.configurazioniService
      .getConfigurazioniByInfoAndChiave(
        this.adempimento.cod_adempimento,
        this.info,
        'PERIODO_OSS'
      )
      .subscribe(
        (res: any) => {
          if (res[0].valore === 'true') {
            this.showDates = true;
          }
        },
        (err) => {
          console.error('API error:', err);
        }
      );
  }
  buildForm() {
    this.formPeriodo = this.fb.group({
      dataInizio: [''],
      dataFine: ['', [this.endDateValidator]],
      dataPubblicazione: [''],
    });
  }

  loadData() {
    this._spinner.show();
    let calls = [...this.loadDataObservables];
    forkJoin(calls).subscribe(
      (res) => {
        this.loadDataSuccess(res);
        this._spinner.hide();
        this.checkDataFineInizio();
      },
      (err) => {
        this.loadDataError(err);
      }
    );
  }

  endDateValidator(control: AbstractControl): { [key: string]: any } | null {
    const dataInizio = control.get('dataInizio');
    const dataFine = control.get('dataFine');

    if (dataInizio && dataFine && dataInizio.value && dataFine.value) {
      const startDate = new Date(dataInizio.value);
      const endDate = new Date(dataFine.value);

      if (endDate < startDate) {
        return { endDateInvalid: true };
      }
    }

    return null;
  }

  /**
   * in caso di successo chiamo la super.loadDataSucces
   * e dopo compio le azioni spercifiche della mia azione avanzata
   * @param res
   */
  loadDataSuccess(res: any[]) {
    super.loadDataSuccess(res);
    this.setValuesForm();
    this.adempimento = this.istanza.adempimento;
    this.configAdempimenti();
  }

  setValuesForm() {
    this.formPeriodo
      .get('dataPubblicazione')
      .setValue(
        this.istanza.data_pubblicazione
          ? moment(this.istanza.data_pubblicazione).format('YYYY-MM-DD')
          : moment(this.today).format('YYYY-MM-DD')
      );

    this.formPeriodo
      .get('dataInizio')
      .setValue(
        this.istanza.data_inizio_osservazioni
          ? moment(this.istanza.data_inizio_osservazioni).format('YYYY-MM-DD')
          : null
      );
    this.formPeriodo
      .get('dataFine')
      .setValue(
        this.istanza.data_fine_osservazioni
          ? moment(this.istanza.data_fine_osservazioni).format('YYYY-MM-DD')
          : null
      );
  }

  /**
   * Funzione che contiene tutte le logiche per la gestione del salvataggio Istanza e data pubblicazione.
   * La data pubblicazione viene salvata solo se è stata modificata rispetto al suo valore iniziale.
   */
  private salvaIstanzaEDataPubblicazione() {
    // // Per la gestione della data recupero la data pubblicazione "attuale" dell'istanza
    // let dtPubbStart = this.istanza.data_pubblicazione;
    // dtPubbStart = this._scrivaUtilities.extractServerDate(dtPubbStart);

    // Aggiorno l'istanza con le informazioni del form
    this.aggiornaIstanzaDaForm();
    // // Dopo aver aggiornato i dati dell'istanza, recupero la data pubblicazione aggiornata dal form
    // let dtPubbNow = this.istanza.data_pubblicazione;
    // dtPubbNow = this._scrivaUtilities.extractServerDate(dtPubbNow);

    // Faccio partire lo spinner
    this._spinner.show();

    // Richiamo la funzione di salvataggio delle note istanza come primo passo
    super
      .saveNoteIstanza()
      .pipe(catchError((err) => of([])))
      .subscribe({
        next: (res: any) => {
          // Chiudo lo spinner di caricamento
          this._spinner.hide();
          // Lancio la funzione per il proseguo del salvataggio
          this.onNoteIstanzaResponse();
          // #
        },
        error: (e: any) => {
          // non Gestisco gli errori
          // Chiudo lo spinner di caricamento
          this._spinner.hide();
          // Lancio la funzione per il proseguo del salvataggio
          this.onNoteIstanzaResponse();
          // #
        },
      });
  }

  /**
   * Funzione che gestisce le logiche di flusso e salvataggio dell'istanza dopo che la funzione di salvataggio delle note Istanza è stata conclusa.
   * @param dtPubbStart string con la data pubblicazione dell'istanza prima dell'aggiornamento dati dal form.
   * @param dtPubbNow string con la data pubblicazione dell'istanza dopo l'aggiornamento dati dal form.
   */
  private onNoteIstanzaResponse(/* dtPubbStart: string, dtPubbNow: string */) {
    // Definisco delle variabili per la gestione del flusso
    const tipoEvento = this.tipoEventoEnum.PUBBLICA;
    const codMess = 'P020';

    // Faccio partire lo spinner
    this._spinner.show();
    // Salvate le note istanza vado a salvare l'istanza utilizzando la gestione tramite evento
    super
      .saveIstanzaWithResponse(tipoEvento)
      .pipe(
        switchMap((istanza: Istanza) => {
          // Aggiorno localmente l'oggetto dell'istanza
          this.istanza = istanza;
          // Lancio il salvataggio della data pubblicazione
          return this.saveDataPubblicazione(this.istanza);
          // #
        })
      )
      .subscribe({
        next: (istanza: Istanza) => {
          // Chiudo lo spinner di caricamento
          this._spinner.hide();
          // Terminato il flusso di salvataggio, lancio la funzione generaEvento
          this.generaEvento(tipoEvento, codMess);
          // #
        },
        error: (err: any) => {
          // Chiudo lo spinner di caricamento
          this._spinner.hide();
          // Verifico se è stato generato errore con un codice specifico
          if (err.error?.code) {
            // Definisco le variabili per la gestione dell'errore
            const code = err.error.code;
            const idDOM = 'dettaglioContainer';
            const autoFade = false;
            const ph = this._getSwapReplace(err.error.code);
            // Richiamo la funzione di segnalazione dell'errore
            this._message.showMessage(code, idDOM, autoFade, ph);
            // #
          } else {
            // Visualizzo un messaggio generico
            this._message.showMessage('E100', 'dettaglioContainer', true);
            // #
          }
        },
      });
  }

  /**
   * Funzione che aggiorna i dati dell'istanza con le informazioni presenti nel form.
   */
  private aggiornaIstanzaDaForm() {
    this.istanza.data_inizio_osservazioni = this.datePipe.transform(
      this.formPeriodo.get('dataInizio').value,
      'yyyy-MM-dd HH:mm:ss'
    );
    this.istanza.data_fine_osservazioni = this.datePipe.transform(
      this.formPeriodo.get('dataFine').value,
      'yyyy-MM-dd HH:mm:ss'
    );
    this.istanza.data_pubblicazione = this.datePipe.transform(
      this.formPeriodo.get('dataPubblicazione').value,
      'yyyy-MM-dd HH:mm:ss'
    );
  }

  // saveIstanza() {
  //   this.istanza.data_inizio_osservazioni = this.datePipe.transform(
  //     this.formPeriodo.get('dataInizio').value,
  //     'yyyy-MM-dd HH:mm:ss'
  //   );
  //   this.istanza.data_fine_osservazioni = this.datePipe.transform(
  //     this.formPeriodo.get('dataFine').value,
  //     'yyyy-MM-dd HH:mm:ss'
  //   );
  //   this.istanza.data_pubblicazione = this.datePipe.transform(
  //     this.formPeriodo.get('dataPubblicazione').value,
  //     'yyyy-MM-dd HH:mm:ss'
  //   );
  //   super
  //     .saveNoteIstanza()
  //     .pipe(catchError((err) => of([])))
  //     .subscribe({
  //       next: (res: any) => {
  //         this.spinner.hide();
  //         super.saveIstanza(this.tipoEventoEnum.PUBBLICA, 'P020');
  //       },
  //       error: (e: any) => {
  //         // non Gestisco gli errori
  //         // Chiudo lo spinner di caricamento
  //         this.spinner.hide();
  //         super.saveIstanza(this.tipoEventoEnum.PUBBLICA, 'P020');
  //       },
  //     });
  // }

  /**
   * Funzione che recupera il dato per la data pubblicazione dal form ed effettua il salvataggio.
   * La funzione salverà le informazioni per la data pubblicazione, ma ritornerà le informazioni dell'istanza passate in input.
   * @param istanza Istanza con le informazioni salvate e gestite per l'oggetto istanza.
   * @returns Observable<Istanza> con le informazioni dell'istanza passate in input.
   */
  saveDataPubblicazione(istanza: Istanza): Observable<Istanza> {
    // Recupero le informazioni dall'oggetto istanza
    const idIstanza: number = istanza?.id_istanza;
    // Per evitare errore, cerco di estrarre la data pubblicazione dell'istanza
    const dataPubblicazione = this._scrivaUtilities.extractServerDate(
      istanza.data_pubblicazione
    );

    // Richiamo e ritorno la funzione per l'aggiornamento della data
    return this._istanza
      .pubblicaIstanza(idIstanza, dataPubblicazione)
      .pipe(
        map((resIstanza: Istanza) => {
          // Ritorno l'oggetto originale dell'istanza
          return istanza;
        })
      );
  }

  onAnnulla() {
    this.formPeriodo.reset();
  }

  onConferma() {
    if (!this.formPeriodo.valid) {
      this._message.showMessage('E001', 'searchFormCard', true);
      return;
    }
    // this.istanzaService.dataPubblicazione(this.idIstanza, this.istanza.data_pubblicazione);
    // Richiamo il salvataggio dei dati
    this.salvaIstanzaEDataPubblicazione();
  }
}