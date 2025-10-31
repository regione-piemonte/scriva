/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, Input, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin, Observable, of } from 'rxjs';
import { catchError, map, switchMap } from 'rxjs/operators';
import {
  EsitoProcedimento,
  Istanza,
  IstanzaCompetenza,
} from 'src/app/shared/models';
import { TipoResponsabile } from 'src/app/shared/models/aut-competente/tipo-responsabile.model';
import { IntegrazioneIstanza } from 'src/app/shared/models/istanza/integrazione-istanza.model';
import { IstanzaResponsabile } from 'src/app/shared/models/istanza/istanza-responsabile.model';
import {
  AdempimentoService,
  ConfigurazioniScrivaService,
  IstanzaService,
  MessageService,
} from 'src/app/shared/services';
import { TipoInfoAdempimento } from 'src/app/shared/services/configurazioni/utilities/configurazioni.enums';
import { StatoIstanzaEnum } from 'src/app/shared/utils';
import { ScrivaUtilitiesService } from '../../../../shared/services/scriva-utilities/scriva-utilities.service';
import { AdvancedActionService } from '../../services/advanced-action.service';

@Component({
  selector: 'advanced-actions-riepilogo-modal',
  templateUrl: './advanced-actions-riepilogo-modal.component.html',
  styleUrls: ['./advanced-actions-riepilogo-modal.component.scss'],
})
export class AdvancedActionsRiepilogoModalComponent implements OnInit {
  @Input() istanza: Istanza;
  idIstanza: number;

  formInserisciInfoGenerali: FormGroup;

  esitoProcedimento = [];
  listEsitoProcedura = [];
  responsabili: IstanzaResponsabile[] = [];
  responsabiliAdded: IstanzaResponsabile[] = [];
  tipiResponsabile: TipoResponsabile[] = [];
  istanzaCompetenzaList: IstanzaCompetenza[];
  integrazioni: IntegrazioneIstanza[] = [];

  _isEditing: boolean = false;
  showOsservazioni: boolean = false;
  dateError: boolean = false;
  today = new Date();

  /**
   * Costruttore.
   */
  constructor(
    public activeModal: NgbActiveModal,
    private advancedActionService: AdvancedActionService,
    private adempimentoService: AdempimentoService,
    private configurazioniService: ConfigurazioniScrivaService,
    private fb: FormBuilder,
    private istanzaService: IstanzaService,
    private messageService: MessageService,
    private spinner: NgxSpinnerService,
    private _scrivaUtilities: ScrivaUtilitiesService
  ) {}

  /**
   * NgOnInit.
   */
  ngOnInit() {
    this.idIstanza = this.istanza.id_istanza;
    this._buildFormInserisciInfoGenerali();

    this.loadData();
  }

  checkDataFineInizio() {
    const dataFine = this.formInserisciInfoGenerali.get('dataFine_PO').value;
    const dataInizio =
      this.formInserisciInfoGenerali.get('dataInizio_PO').value;
    if (dataInizio && dataFine && new Date(dataFine) < new Date(dataInizio)) {
      this.dateError = true;
    } else {
      this.dateError = false;
    }
  }

  loadData() {
    this.spinner.show();
    let calls = [];
    calls.push(
      this.adempimentoService.getEsitiProcedimentiById(
        this.istanza?.adempimento.id_adempimento
      )
    );
    calls.push(
      this.istanzaService
        .getResponsabili(this.idIstanza)
        .pipe(catchError((err) => of([])))
    );
    calls.push(
      this.advancedActionService
        .getIstanzaCompetenzaList(this.idIstanza)
        .pipe(catchError((err) => of([])))
    );
    calls.push(this.istanzaService.getTipiResponsabili(this.idIstanza));
    calls.push(
      this.advancedActionService
        .getIntegrazioni(this.istanza)
        .pipe(catchError((err) => of([])))
    );
    forkJoin(calls).subscribe(
      (res) => {
        this._loadDataSuccess(res);
        this.spinner.hide();
        this.checkDataFineInizio();
      },
      (err) => {
        if (err.error?.code) {
          this.messageService.showMessage(
            err.error.code,
            'dettaglioContainer',
            false
          );
        } else {
          this.messageService.showMessage('E100', 'dettaglioContainer', true);
        }
      }
    );
  }

  _loadDataSuccess(res: any) {
    this._loadEsiti(res[0]);
    this._configAdempimenti();
    this.responsabili = res[1];
    this.istanzaCompetenzaList = res[2];
    this.tipiResponsabile = res[3];
    this.integrazioni = res[4];
    this._setValueForm();
    this.isEditing = false;
  }

  _configAdempimenti() {
    this.configurazioniService
      .getConfigurazioniByInfoAndChiave(
        this.istanza.adempimento.cod_adempimento,
        TipoInfoAdempimento.osservazioni,
        'PERIODO_OSS'
      )
      .subscribe(
        (res: any) => {
          if (res[0].valore === 'true') {
            this.showOsservazioni = true;
          }
        },
        (err) => {}
      );
  }

  _loadEsiti(listEsiti) {
    listEsiti.forEach((el) => {
      if (el.ind_esito === '1' || el.ind_esito === '3') {
        this.listEsitoProcedura.push(el.esito_procedimento);
      }
    });
  }

  /*
   * Builder form inserisci Dati istruttoria
   */
  private _buildFormInserisciInfoGenerali() {
    this.formInserisciInfoGenerali = this.fb.group({
      dataProtocollo: [null, [this.maxDateValidator]],
      numeroProtocollo: [
        '',
        {
          validators: [Validators.maxLength(50)],
        },
      ],
      dataInizio_PO: [''],
      dataFine_PO: [''],
      termineProcedimento_PO: [''],
      dataPubblicazione_PO: [''],
      esitoProcedimento_CP: [''],
      dataConclusione_CP: [''],
    });
  }

  maxDateValidator(control: AbstractControl): ValidationErrors | null {
    // Verifico che la data sia definita
    if (!control.value) {
      // Non c'è da verificare la data
      return null;
    }

    const selectedDate = new Date(control.value);
    const maxDate = new Date();
    return selectedDate <= maxDate ? null : { maxDateExceededToday: true };
  }

  _setValueForm() {
    this.formInserisciInfoGenerali.patchValue({
      dataProtocollo: this.advancedActionService.convertDateBE4Form(
        this.istanza.data_protocollo_istanza
      ),
      numeroProtocollo: this.istanza.num_protocollo_istanza,
      dataInizio_PO: this.advancedActionService.convertDateBE4Form(
        this.istanza.data_inizio_osservazioni
      ),
      dataFine_PO: this.advancedActionService.convertDateBE4Form(
        this.istanza.data_fine_osservazioni
      ),
      termineProcedimento_PO: this.advancedActionService.convertDateBE4Form(
        this.istanza.data_scadenza_procedimento
      ),
      dataPubblicazione_PO: this.advancedActionService.convertDateBE4Form(
        this.istanza.data_pubblicazione
      ),
      esitoProcedimento_CP: this.istanza.esito_procedimento,
      dataConclusione_CP: this.advancedActionService.convertDateBE4Form(
        this.istanza.data_conclusione_procedimento
      ),
    });
  }

  /**
   * Prendo dal form dei campi e li metto in formato
   * adatto a Istanza
   * @returns parziale di Istanza
   */
  private _getFieldsForm(): Partial<Istanza> {
    let fields: Partial<Istanza> = {
      data_protocollo_istanza: this.advancedActionService.convertDateForm4BE(
        this.formInserisciInfoGenerali.get('dataProtocollo').value
      ),
      num_protocollo_istanza:
        this.formInserisciInfoGenerali.get('numeroProtocollo').value,
      data_scadenza_procedimento: this.advancedActionService.convertDateForm4BE(
        this.formInserisciInfoGenerali.get('termineProcedimento_PO').value
      ),
      data_pubblicazione: this.advancedActionService.convertDateForm4BE(
        this.formInserisciInfoGenerali.get('dataPubblicazione_PO').value
      ),
      esito_procedimento: this.formInserisciInfoGenerali.get(
        'esitoProcedimento_CP'
      ).value,
      data_conclusione_procedimento:
        this.advancedActionService.convertDateForm4BE(
          this.formInserisciInfoGenerali.get('dataConclusione_CP').value
        ),
    };
    // salvo le osservazioni solo se mostro i campi
    if (this.showOsservazioni) {
      fields.data_inizio_osservazioni =
        this.advancedActionService.convertDateForm4BE(
          this.formInserisciInfoGenerali.get('dataInizio_PO').value
        );
      fields.data_fine_osservazioni =
        this.advancedActionService.convertDateForm4BE(
          this.formInserisciInfoGenerali.get('dataFine_PO').value
        );
    }
    return fields;
  }

  /**
   * Propago eventi da componenti figlie
   * @param event striga evento
   */
  emitEventChild(event: any) {
    // il nome dell'evento deve corrispondere ad un metodo dell'oggetto corrente
    // Eventi mappati: 'goToSearchPage' | 'onAnnulla' | 'onIndietro' | 'onConferma' | 'onUpdateNote'
    if (typeof event === 'string') {
      if (this[event]) {
        this[event]();
      }
    }
    // per ora onUpdateNote
    if (event?.action && this[event?.action]) {
      this[event.action](event?.args);
    }
  }

  /**
   * tengo allineati i responsabili in memoria per il salvataggio sul conferma
   * @param responsabili IstanzaResponsabile[]
   */
  onUpdateResponsabili(responsabili: IstanzaResponsabile[] = []) {
    this.responsabili = [...responsabili];
    this.isEditing = true;
  }

  /**
   * Aggiungo Responsabili in lista di quelli aggiunti e non ancora salvati
   * @param r IstanzaResponsabile
   */
  onAddResponsabile(r: IstanzaResponsabile) {
    this.responsabiliAdded.push(r);
    this.isEditing = true;
  }

  /**
   * tengo allineati i responsabili in memoria per il salvataggio sul conferma
   * @param responsabili any[]
   */
  onUpdateIntegrazioni(integrazioni: IntegrazioneIstanza[] = []) {
    this.integrazioni = [...integrazioni];
    this.isEditing = true;
  }

  compareEsitoProcedimento(e1: EsitoProcedimento, e2: EsitoProcedimento) {
    return e1 && e2 && e1.id_esito_procedimento === e2.id_esito_procedimento;
  }

  onConferma() {
    if (this.integrazioni.length > 0) {
      this.setIntegrazioni()
        .pipe(
          switchMap(() => {
            return this.setAndSaveIstanza();
          })
        )
        .subscribe({
          next: (response: Istanza) => {
            this.activeModal.close(response);
          },
          error: (err) => {},
        });
    } else {
      this.setAndSaveIstanza().subscribe({
        next: (response: Istanza) => {
          this.activeModal.close(response);
        },
        error: (err) => {},
      });
    }
  }

  setIntegrazioni(): Observable<any> {
    let calls = [];
    this.integrazioni.forEach((integrazioneIstanza: IntegrazioneIstanza) => {
      calls.push(
        this.advancedActionService.setIntegrazione(integrazioneIstanza)
      );
    });
    return forkJoin(calls);
  }

  /**
   * Funzione che aggiorna i dati dell'istanza con le informazioni presenti nel form.
   */
  private aggiornaIstanzaDaForm() {
    // Effettuo un unione dei dati dell'istanza attuali e quelli della form. La destrutturazione darà priorità ai dati del form che sostituiranno quelli dell'istanza.
    // Per i responsabili istanza richiamo la funzione di supporto tramite servizio
    this.istanza = {
      ...this.istanza,
      ...this._getFieldsForm(),
      responsabili_istanza: [
        ...this.advancedActionService.getResponsabiliForSave(
          this.responsabili,
          this.responsabiliAdded
        ),
      ],
    };
  }

  setAndSaveIstanza(): Observable<Istanza> {
    // Per la gestione della data recupero la data pubblicazione "attuale" dell'istanza
    let dtPubbStart = this.istanza.data_pubblicazione;
    dtPubbStart = this._scrivaUtilities.extractServerDate(dtPubbStart);

    // Aggiorno l'istanza con le informazioni del form
    this.aggiornaIstanzaDaForm();

    // Dopo aver aggiornato i dati dell'istanza, recupero la data pubblicazione aggiornata dal form
    let dtPubbNow = this.istanza.data_pubblicazione;
    dtPubbNow = this._scrivaUtilities.extractServerDate(dtPubbNow);

    return this.istanzaService.salvaIstanza(this.istanza).pipe(
      switchMap((istanza: Istanza) => {
        // Verifico se la data di partenza è differente rispetto a quella salvata nell'istanza
        if (dtPubbStart !== dtPubbNow) {
          // Lancio il salvataggio della data pubblicazione
          return this.saveDataPubblicazione(istanza);
          // #
        } else {
          // La data è la stessa, faccio da passacarte senza altre logiche
          return of(istanza);
        }
      })
    );
  }

  /**
   * Funzione che recupera il dato per la data pubblicazione dal form ed effettua il salvataggio.
   * La funzione salverà le informazioni per la data pubblicazione, ma ritornerà le informazioni dell'istanza passate in input.
   * @param istanza Istanza con le informazioni salvate e gestite per l'oggetto istanza.
   * @returns Observable<Istanza> con le informazioni dell'istanza passate in input.
   */
  saveDataPubblicazione(istanza: Istanza): Observable<any> {
    // Recupero le informazioni dall'oggetto istanza
    const idIstanza: number = istanza?.id_istanza;
    // Per evitare errore, cerco di estrarre la data pubblicazione dell'istanza
    const dataPubblicazione = this._scrivaUtilities.extractServerDate(
      istanza.data_pubblicazione
    );

    // Richiamo e ritorno la funzione per l'aggiornamento della data
    return this.istanzaService
      .pubblicaIstanza(idIstanza, dataPubblicazione)
      .pipe(
        map((resIstanza: Istanza) => {
          // Ritorno l'oggetto originale dell'istanza
          return istanza;
        })
      );
  }

  onClose() {
    if (this.isEditing) {
      this.messageService.showConfirmation({
        title: 'Conferma annullamento modifiche',
        codMess: 'A001',
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
              this.activeModal.close();
            },
          },
        ],
      });
    } else {
      this.activeModal.close();
    }
  }

  /**
   * getter e setter
   */
  get isEditing(): boolean {
    return this.formInserisciInfoGenerali.dirty || this._isEditing;
  }
  set isEditing(v: boolean) {
    // Recupero dalla notifica la descrizione adempimento
    this._isEditing = v;
  }

  get isPresentEndingProcess(): boolean {
    return (
      this.istanza?.stato_istanza?.codice_stato_istanza ===
      StatoIstanzaEnum.CONCLUSA
    );
  }
}
