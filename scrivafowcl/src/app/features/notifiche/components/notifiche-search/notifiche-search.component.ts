/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { ScrivaServerError } from '../../../../core/classes/scriva.classes';
import { ScrivaCodesMesseges } from '../../../../core/enums/scriva-codes-messages.enums';
import { ScrivaDatesFormat } from '../../../../shared/enums/scriva-utilities/scriva-utilities.enums';
import { Adempimento } from '../../../../shared/models';
import {
  AdempimentoService,
  MessageService,
} from '../../../../shared/services';
import { ScrivaFormBuilderService } from '../../../../shared/services/form-inputs/scriva-form-builder.service';
import { dataInizioDataFineValidator } from '../../../../shared/services/scriva-utilities/scriva-utilities.validators';
import { NotificheConfigurazioniService } from '../../services/notifiche-configurazioni.service';
import { NotificheSearchService } from '../../services/notifiche-search/notifiche-search.service';
import { NOTIFICHE_SEARCH_CONSTS } from './utilities/notifiche-search.consts';
import { NotificheSearchErrorsMap } from './utilities/notifiche-search.errors-maps';
import { NSFieldsConfigClass } from './utilities/notifiche-search.fields-config';
import {
  INotificheSearchData,
  IStatoNotifica,
} from './utilities/notifiche-search.interfaces';

@Component({
  selector: 'scriva-notifiche-search',
  templateUrl: './notifiche-search.component.html',
  styleUrls: ['./notifiche-search.component.scss'],
})
export class NotificheSearchComponent implements OnInit {
  /** Oggetto di costanti per il componente. */
  NS_C = NOTIFICHE_SEARCH_CONSTS;
  NS_EM = new NotificheSearchErrorsMap();

  /** EventEmitter<INotificheSearchForm> con l'oggetto di filtro per le notifiche. */
  @Output('onConfermaFiltri') onConfermaFiltri$ =
    new EventEmitter<INotificheSearchData>();
  /** EventEmitter<any> che informa che il form è stato resettato. */
  @Output('onResetFiltri') onResetFiltri$ =
    new EventEmitter<INotificheSearchData>();

  /** FormGroup che definisce l'oggetto form contenente i filtri di ricerca. */
  nsForm: FormGroup;
  /** Boolean che tiene traccia dello stato di submit del form. */
  nsFormSubmitted = false;

  /** IStatoNotifica[] che contiene le informazioni per la select: stato notifica. */
  listaStatiNotifica: IStatoNotifica[] = [];
  /** Adempimento[] che contiene le informazioni per la select: procedimento. */
  listaProcedimenti: Adempimento[] = [];

  /** NSFieldsConfigClass che definisce le configurazioni per la costruzione delle input del form. */
  inputs: NSFieldsConfigClass;

  /**
   * Costruttore.
   */
  constructor(
    private _adempimenti: AdempimentoService,
    private _formBuilder: FormBuilder,
    private _message: MessageService,
    private _notificheConfig: NotificheConfigurazioniService,
    private _notificheSearch: NotificheSearchService,
    private _scrivaFormBuilder: ScrivaFormBuilderService
  ) {
    // Lancio il setup del componente
    this.setupComponente();
  }

  /**
   * NgOnInit.
   */
  ngOnInit(): void {
    // Lancio l'init del componente
    this.initComponente();
  }

  /**
   * ################################
   * FUNZIONI DI SETUP DEL COMPONENTE
   * ################################
   */

  /**
   * Funzione che raccoglie i setup iniziali del componente.
   */
  private setupComponente() {
    // Inizializzo i campi del form
    this.setupInputsForm();
  }

  /**
   * Funzione di setup per la configurazione delle input del componente.
   */
  private setupInputsForm() {
    // Effettuo la new della classe con le configurazioni dell'input
    this.inputs = new NSFieldsConfigClass({
      scrivaFormBuilder: this._scrivaFormBuilder,
    });
  }

  /**
   * ###############################
   * FUNZIONI DI INIT DEL COMPONENTE
   * ###############################
   */

  /**
   * Funzione che raccoglie le inizializzazioni del componente.
   */
  private initComponente() {
    // Lancio l'inizializzazione del form
    this.initForm();
    // Lancio l'inizializzazione per i campi del form
    this.initFormFields();
  }

  /**
   * Funzione di comodo per l'init del form del componente.
   */
  private initForm() {
    // Genero la configurazione dei campi
    const fields = this._notificheSearch.generateControlsConfig();
    // Variabili di comodo per i validatori globali
    const dataDa = this.NS_C.FC_DATA_DA;
    const dataA = this.NS_C.FC_DATA_A;
    const format = ScrivaDatesFormat.moment;
    const same = true;
    // Definisco la configurazione per il validatore
    const validators = {
      validators: [dataInizioDataFineValidator(dataDa, dataA, format, same)],
    };

    // Istanzio il form con le informazioni
    this.nsForm = this._formBuilder.group(fields, validators);
  }

  /**
   * Funzione che scarica e configura le informazioni da inserire all'interno del form per i filtri di ricerca.
   */
  private initFormFields() {
    // Recupero la lista degli stati delle notifiche
    this.listaStatiNotifica = this.statiNotificaList;
    // Richiamo la funzione di scarico per la lista procedimenti
    this.procedimentiList.subscribe({
      next: (procedimenti: Adempimento[]) => {
        // Assegno localmente la lista adempimenti
        this.listaProcedimenti = procedimenti;
        // #
      },
      error: (e: ScrivaServerError) => {
        // Gestisco l'errore generato dal server
        this.onServiceError(e);
        // #
      },
    });
  }

  /**
   * ##########################
   * GESTIONE PULSANTI DEL FORM
   * ##########################
   */

  /**
   * Funzione che gestisce il reset dei campi del form, resettando le impostazioni dei filtri di ricerca.
   */
  resettaFiltri() {
    // Resetto il form
    this.nsForm.reset();
    this.nsFormSubmitted = false;
    // Richiamo l'event emitter che resetta i filtri di ricerca
    this.onResetFiltri$.emit();
  }

  /**
   * Funzione che emetta la ricerca verso la componente lista che la contiene
   */
  confermaFiltri() {
    // Setto a true il submit del form
    this.nsFormSubmitted = true;

    // Verifico se il form risulta valido
    if (!this.nsForm.valid) {
      // Il form è invalido
      return;
    }

    // Il form è valido, recupero i suoi valori
    let filtri: INotificheSearchData;
    filtri = this.nsForm.getRawValue();

    // Richiamo l'event emitter per informare che il è stato prodotto l'oggetto di filtro
    this.onConfermaFiltri$.emit(filtri);
  }

  /**
   * ######################################
   * GESTIONE DEGLI ALERT PER IL COMPONENTE
   * ######################################
   */

  /**
   * Funzione di supporto per la gestione della segnalazione dell'errore da parte dei servizi.
   * @param e ScrivaServerError con il dettaglio d'errore generato.
   */
  private onServiceError(e?: ScrivaServerError) {
    // Si è verificato un errore, gestisco la segnalazione utente
    const code = e?.error?.code ?? ScrivaCodesMesseges.E100;
    // Visualizzo il messaggio
    this.showAlert(code);
  }

  /**
   * Funzione di supporto per la gestione degli errori nel componente, dato il codice errore.
   * @param code string con il codice del messaggio da visualizzare.
   */
  private showAlert(code: string) {
    // Definisco le configurazioni per la visualizzazione dell'alert
    const target = this.NS_C.ALERT_ANCHOR;
    const autoFade = true;
    // Visualizzo il messaggio
    this._message.showMessage(code, target, autoFade);
  }

  /**
   * ###############
   * GETTER & SETTER
   * ###############
   */

  /**
   * Getter di comodo per la lista degli adempimenti scaricati dal sever.
   * @returns Observable<Adempimento[]> con la risposta dal servizio.
   */
  get procedimentiList(): Observable<Adempimento[]> {
    // Richiamo la funzione per lo scarico adempimenti
    return this._adempimenti.getAdempimenti();
  }

  /**
   * Getter di comodo per le opzioni degli stati delle notifiche.
   * @returns any[] con le configurazioni per gli stati notifica.
   */
  get statiNotificaList() {
    // Richiamo il servizio che recupera la lista degli stati notifica
    return this._notificheConfig.getScrivaNotifyStatiNotifica();
  }
}
