/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { BehaviorSubject, Observable, of, throwError } from 'rxjs';
import {
  catchError,
  distinctUntilChanged,
  filter,
  takeUntil,
} from 'rxjs/operators';
import { AutoUnsubscribe } from 'src/app/core/components';
import { Comune, Help, Provincia } from 'src/app/shared/models';
import { LocationService, MessageService } from 'src/app/shared/services';
import { ScrivaServerError } from '../../../../core/classes/scriva.classes';
import { ScrivaCodesPlaceholders } from '../../../../core/consts/scriva-codes-placeholders.consts';
import { ScrivaCodesMesseges } from '../../../../core/enums/scriva-codes-messages.enums';
import { CommonConsts } from '../../../../shared/consts/common-consts.consts';
import { IServiziErrorConfig } from '../../../../shared/interfaces/scriva-utilities.interfaces';
import { LoggerService } from '../../../../shared/services/logger.service';
import { IMsgPlacholder } from '../../../../shared/services/message/utilities/message.interfaces';
import { ActionsOggettiSearchEnum } from '../../enums/actions-oggetti-search.enum';
import {
  ConfigElement,
  ICriteriRicercaQdrOggetto,
  Opera,
  QdrOggettoConfig,
  Soggetto,
} from '../../models';
import { OggettiSearchRequest } from '../../models/oggetti-search/oggetti-search-request';
import { AmbitoService } from '../../services';
import { OggettiSearchConst } from './utilities/oggetti-search.consts';
import {
  IFormOggettiSearchData,
  OggettiSearchFormData,
} from './utilities/oggetti-search.interfaces';
import { nullAsStringValidator } from './utilities/oggetti-search.validators';

@Component({
  selector: 'app-oggetti-search',
  templateUrl: './oggetti-search.component.html',
  styleUrls: ['./oggetti-search.component.scss'],
})
export class OggettiSearchComponent extends AutoUnsubscribe implements OnInit {
  /** CommonConsts con le costanti dell'applicazione comuni. */
  COMMON_CONST = new CommonConsts();
  /** OggettiSearchConst con le constanti usate per il componente. */
  OGG_SEARCH_CONST = new OggettiSearchConst();

  @Input() actions$: BehaviorSubject<ActionsOggettiSearchEnum>;
  // parametro che indica se il form deve essere abilitato o meno allo start della componente
  @Input() enabled: boolean;
  // Array di Help
  @Input() helpList: Help[] = [];
  // idIstanza id numerico della istanza
  @Input() idIstanza: number;
  // Array di id dei Soggetti di tipo numerico
  @Input() idSoggetti: number[] = [];
  // Array di Province
  @Input() province: Provincia[];
  // Config del quadro
  @Input() qdrOggettoConfig: QdrOggettoConfig;
  // codice di tipologia oggetto
  @Input() configTipologiaOggetto: string;
  // stringa di testo helper in pagina
  @Input() helpText: string = '';

  /** Observable<any[]> come funzione per la definizione delle informazioni dei soggetti. */
  @Input() setSoggetti$: Observable<any[]>;
  /** (params?: any) => string come funzione per la definizione della descrizione all'interno della select dei soggetti. */
  @Input() descrizioneSoggetti: (params?: any) => string;
  /** (soggetto?: any) => number come funzione per il recupero dell'id soggetto gestito dalla select. */
  @Input() getIdSoggetto: (soggetto: any) => number;

  /** Input ScrivaCodesMessages che definisce il messaggio di success da visualizzare quando la ricerca è completata con successo. Se non definito, non appare la segnalazione. */
  @Input('codiceRicercaSuccesso') successSearchCode: ScrivaCodesMesseges;

  // Emitter che manda le Opere verso la componenente chiamante
  @Output() oggettiSearchEmitter = new EventEmitter<Opera[]>();

  /** EventEmitter<OggettiSearchFormData> con il valore della form cambiata. */
  @Output() ricercaFormChanged$ = new EventEmitter<OggettiSearchFormData>();
  /** EventEmitter<IFormOggettiSearchData> con l'evento con l'oggetto che definisce i parametri di ricerca utilizzati. */
  @Output() paramsRicercaOpere$ = new EventEmitter<IFormOggettiSearchData>();
  /** EventEmitter<any> con l'evento di modifica del valore del soggetto. */
  @Output() soggettoChanged$ = new EventEmitter<any>();

  /* Elenco di Stringhe utili nella componente */
  // id della Card di Ricerca
  private CARD_SEARCH_FORM = 'searchFormCard';
  // codice del tipo help BNR
  private CODE_TIPO_BNR = 'BNR';
  // codice del tipo help MDL
  private CODE_TIPO_MDL = 'MDL';
  // testo default della modale di help in caso di help non trovato
  private DEFAULT_HELP_MODAL_TEXT = 'Help non trovato...';
  // titolo default della modale di help
  private DEFAULT_HELP_MODAL_TITLE = 'HELP';
  // testo default dell'helper in caso di help non trovato
  private DEFAULT_HELP_TEXT =
    "Errore: il testo di questo paragrafo non è stato trovato. Contattare l'assistenza.";
  // error code nella ricerca dei comuni
  private ERROR_CODE_COMUNI_GENERIC = ScrivaCodesMesseges.E100;
  // error code in caso di form non valido
  private ERROR_CODE_INVALID_FORM = ScrivaCodesMesseges.E002;
  // error code in caso di ricerca con il 404
  private ERROR_CODE_SEARCH404 = ScrivaCodesMesseges.I001;
  // error code in caso di ricerca senza il 404
  private ERROR_CODE_SEARCH_GENERIC = ScrivaCodesMesseges.E100;
  // chiave per il testo della modale
  private KEY_INFO_QUADRO = 'info_quadro';
  // chiave per help in pagina con ricerca oggetto a no
  private KEY_RICERCA_OGGETTO_NO = 'ricerca_oggetto_no';
  // chiave per help in pagina con ricerca oggetto a si
  private KEY_RICERCA_OGGETTO_SI = 'ricerca_oggetto_si';

  // label di default per il comune se non configurato
  private LABEL_COMUNE_DEFAULT = 'Comune';
  // label di default per la provincia se non configurata
  private LABEL_PROVINCIA_DEFAULT = 'Provincia';

  /* Elenco di Stringhe utili nella componente */

  // stringa di testo della modale di helper
  helpModalText: string;
  // Lista di ricerca per i comuni
  ricercaComuniList: Comune[] = [];
  // Form di ricerca
  ricercaForm: FormGroup;

  /** ICriteriRicercaQdrOggetto con l'oggetto di configurazione per il form di ricerca. */
  criteriRicerca: ICriteriRicercaQdrOggetto;

  /** (params?: any) => Observable<any[]> come funzione per la definizione delle informazioni dei soggetti. */
  setSoggettiLogic$: Observable<any[]>;
  /** (params?: any) => string come funzione per la definizione della descrizione all'interno della select dei soggetti. */
  descrizioneSoggettiLogic: (params?: any) => string;
  /** (soggetto?: any) => number come funzione per il recupero dell'id soggetto gestito dalla select. */
  getIdSoggettoLogic: (soggetto: any) => number;

  emptyValue: any = null;

  constructor(
    private ambitoService: AmbitoService,
    private _formBuilder: FormBuilder,
    private locationService: LocationService,
    private _message: MessageService,
    private _spinner: NgxSpinnerService,
    private _logger: LoggerService
  ) {
    super();
  }

  ngOnInit() {
    // Lancio l'init per la configurazione dell'helper
    this.initHelper();

    // Configuro il form in modo opportuno
    this.initForm();
    // Gestisco i criteri di ricerca anche in base alla configurazione del form
    this.initCampiRicerca(this.ricercaForm);

    // Lancio la funzione di configurazione dei campi del form a seguito dei criteri di ricerca
    this.initConfigurazioniForm(this.criteriRicerca);

    // Lancio l'inizializzazione per la gestione della lista soggetti
    this.initSetSoggetti();

    // sottoscrivo le azioni che provenngono da esterno della componente
    this.getActions()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (action: ActionsOggettiSearchEnum) => {
          if (action === ActionsOggettiSearchEnum.ENABLE)
            this.makeActionEnabled();
          else if (action === ActionsOggettiSearchEnum.RESETDISABLE)
            this.makeActionResetDisabled();
          else if (action === ActionsOggettiSearchEnum.RESETENABLE)
            this.makeActionResetEnable();
          else if (action === ActionsOggettiSearchEnum.UPDATEVALUEANDVALIDITY)
            this.makeActionUpdateValueAndValidity();
        },
      });
  }

  /**
   * Funzione che inizializza le configurazioni per i campi di ricerca del form.
   * I criteri di ricerca devono avere corrispondenza all'interno dell'oggetto del form.
   * @param form FormGroup con la configurazione del form per la verifica dei criteri di ricerca richiesti.
   */
  private initCampiRicerca(form: FormGroup) {
    // Recupero dalla configurazione i campi di ricerca
    let criteriRicerca: ICriteriRicercaQdrOggetto = {
      ...this.qdrOggettoConfig.criteri_ricerca,
    };
    // Gestisco le configurazioni con la funzione (modifica per referenza)
    this.gestisciCampiRicerca(criteriRicerca);

    // Verifico se c'è l'oggetto del form per la verifica
    if (!form) {
      // Manca la configurazione
      return;
      // #
    }

    // Itero i criteri di ricerca generati
    for (let [criterioRicerca, value] of Object.entries(criteriRicerca)) {
      // Verifico se il criterio di ricerca è stato configurato nel form
      const fc: AbstractControl = form.get(criterioRicerca);
      // Verifico se esiste il form control per il criterio iterato
      if (!fc) {
        // Loggo un warning e rimuovo il criterio come property
        const t = `initCampiRicerca | criterio mancante`;
        const b = { criterio: criteriRicerca, criteriRicerca };
        this._logger.warning(t, b);
        // Rimuovo il criterio
        delete criteriRicerca[criterioRicerca];
        // #
      }
      // #
    }
  }

  /**
   * Funzione che recupera le azioni del BehaviorSubject in ingressp come Observable
   */
  getActions(): Observable<ActionsOggettiSearchEnum> {
    return this.actions$.asObservable();
  }

  /**
   * Funzione che inizializza il form
   */
  private initForm() {
    // Recupero dalla classe di configurazione le proprietà per i nomi dei campi di input
    const provincia: string = this.provinciaFCN;
    const comune: string = this.comuneFCN;
    const denominazioneOggetto: string = this.denominazioneOggettoFCN;
    const codiceScriva: string = this.codiceScrivaFCN;
    const annoPresentazione: string = this.annoPresentazioneFCN;
    const codiceFiscaleSoggetto: string = this.codiceFiscaleSoggettoFCN;
    // Creo un oggetto contenente la definizione dei vari campi, utilizzando una dichiarazione a proprietà dinamiche
    const formControls: any = {};

    // Creo le configurazioni per i form control usando le proprietà dinamiche
    formControls[provincia] = [null];
    formControls[comune] = [{ value: null, disabled: true }];
    formControls[denominazioneOggetto] = null;
    formControls[codiceScriva] = null;
    formControls[annoPresentazione] = [null];
    formControls[codiceFiscaleSoggetto] = [{ value: null, disabled: false }];

    // La struttura del form si basa sulle proprietà dell'oggetto usato per la ricerca: OggettiSearchRequest
    this.ricercaForm = this._formBuilder.group(formControls);
  }

  /**
   * Funzione che gestisce le configurazioni del form per quanto riguarda l'abilitazione e la gestione dei validatori.
   * Va richiamato a seguito delle funzioni:
   * - initForm();
   * - initCampiRicerca();
   * Poiché le configurazioni dei campi devono essere gestite in maniera consona rispetto alle configurazioni del form.
   * @param criteriRicerca ICriteriRicercaQdrOggetto con la configurazione dei criteri di ricerca.
   */
  private initConfigurazioniForm(criteriRicerca: ICriteriRicercaQdrOggetto) {
    // Resetto il form in ogni caso
    this.ricercaForm.reset();

    // verifico il parametro in input per form abilitato o disabilitato
    if (!this.enabled) {
      // se il form non è abiliato lo disabilito
      this.ricercaForm.disable();
      // #
    } else {
      // se il form è abilitato abilito
      this.ricercaForm.enable();
      // Disabilito il campo relativo al comune
      this.ricercaForm.get(this.comuneFCN).disable();
    }

    // Setto i validatori in base a configurazione
    this.initValidatoriForm(criteriRicerca);

    // Setto il ValueChange della provincia
    this.initFormListeners();
  }

  /**
   * Funzione che inizializza i validatori per il form dati.
   * La gestione dei validatori è gestita in maniera generica sulla base della configurazione iterata.
   * Alcuni campi del form specifici hanno dei validatori gestiti in maniera puntale.
   * @param criteriRicerca ICriteriRicercaQdrOggetto con le configurazioni per i campi del form.
   */
  private initValidatoriForm(criteriRicerca: ICriteriRicercaQdrOggetto) {
    // Verifico l'input
    if (!criteriRicerca) {
      // Manca la configurazione
      return;
      // #
    }

    // Definisco la lista dei criteri di ricerca che hanno una gestione dei validatori specifica
    const criteriSpecifici: string[] = [
      this.annoPresentazioneFCN,
      this.codiceFiscaleSoggettoFCN,
    ];

    // Itero tutti i criteri di ricerca
    for (let [key, value] of Object.entries(criteriRicerca)) {
      // Per comodità e chiarezza, tipizzo le informazioni ciclate
      const criterio: string = key;
      const configurazione: ConfigElement = value;

      // Verifico che il criterio non sia a gestione "manuale"
      const isCriterioSpecifico: boolean = criteriSpecifici.some(
        (criterioSpecifico: string) => {
          // Verifico il criterio iterato con il criterio specifico
          return criterio === criterioSpecifico;
          // #
        }
      );

      // Gestisco le logiche comuni solo se il criterio non è specifico
      if (!isCriterioSpecifico) {
        // Verifico se esiste la configurazione di obbligatorietà
        if (configurazione?.obbligatorio) {
          // Esiste la configurazione di obbligatorietà, aggiungo il controllo al campo del form x criterio
          this.ricercaForm?.get(criterio)?.setValidators(Validators.required);
          // Per qualche motivo il codice vecchio lanciava anche una update value and validity
          this.ricercaForm?.get(criterio)?.updateValueAndValidity();
          // #
        }
      }
    }

    // Lancio la gestione dei criteri di ricerca che hanno necessità di logiche specifiche
    this.initValidatioriFormSpecifici(criteriRicerca);
  }

  /**
   * Funzione che gestisce i validatori del form per tutti quei campi che necessitano di una gestione specifica.
   * @param criteriRicerca ICriteriRicercaQdrOggetto con le configurazioni per i campi del form.
   */
  private initValidatioriFormSpecifici(
    criteriRicerca: ICriteriRicercaQdrOggetto
  ) {
    // Verifico l'input
    if (!criteriRicerca) {
      // Manca la configurazione
      return;
      // #
    }

    // Richiamo le singole funzioni di gestione dei validatori per i campi
    // #### ANNO PRESENTAZIONE
    const criterioAnnoPresentazione: ConfigElement =
      criteriRicerca?.anno_presentazione;
    this.initValidatoriFormAnnoPresentazione(criterioAnnoPresentazione);

    // #### CF SOGGETTO
    const criterioCF: ConfigElement = criteriRicerca?.cf_soggetto;
    this.initValidatoriFormCodiceFiscale(criterioCF);
    // #
  }

  /**
   * Funzione che gestisce i validatori del form per tutti quei campi che necessitano di una gestione specifica.
   * @param annoPresentazioneConfig ConfigElement con le configurazioni per i campi del form.
   */
  private initValidatoriFormAnnoPresentazione(
    annoPresentazioneConfig: ConfigElement
  ) {
    // Verifico che esista il criterio di ricerca
    if (!annoPresentazioneConfig) {
      // Manca la configurazione
      return;
      // #
    }

    // Recupero il nome per il form control name del campo
    const campoForm: string = this.annoPresentazioneFCN;
    // Definisco un array che conterrà le informazioni per i validatori
    const validatoreMaxDate = Validators.max(new Date().getFullYear());
    const validatori: ValidatorFn[] = [validatoreMaxDate];

    // Verifico se all'interno della configurazione specifica c'è la necessità di obbligatorietà
    if (annoPresentazioneConfig.obbligatorio) {
      // Aggiungo validatore di obbligatorietà
      validatori.push(Validators.required);
      // #
    }

    // L'anno di presentazione, se definito come configurazione, è una data, aggiungo il validatore per la data massimathis.ricercaForm
    this.ricercaForm?.get(campoForm)?.setValidators(validatori);
    this.ricercaForm?.get(campoForm)?.updateValueAndValidity();
    // #
  }

  /**
   * Funzione che gestisce i validatori del form per tutti quei campi che necessitano di una gestione specifica.
   * @param codiceFiscaleConfig ConfigElement con le configurazioni per i campi del form.
   */
  private initValidatoriFormCodiceFiscale(codiceFiscaleConfig: ConfigElement) {
    // Verifico che esista il criterio di ricerca
    if (!codiceFiscaleConfig) {
      // Manca la configurazione
      return;
      // #
    }

    // Recupero il nome per il form control name del campo
    const campoForm: string = this.codiceFiscaleSoggettoFCN;
    const validatori: ValidatorFn[] = [];

    // Verifico se all'interno della configurazione specifica c'è la necessità di obbligatorietà
    if (codiceFiscaleConfig.obbligatorio) {
      // Aggiungo validatore di obbligatorietà
      validatori.push(Validators.required);
      validatori.push(nullAsStringValidator());
      // #
    }

    // L'anno di presentazione, se definito come configurazione, è una data, aggiungo il validatore per la data massimathis.ricercaForm
    this.ricercaForm?.get(campoForm)?.setValidators(validatori);
    this.ricercaForm?.get(campoForm)?.updateValueAndValidity();
    // #
  }

  /**
   * Funzione di init che imposta le logiche di gestione per il set della lista dei soggetti per la select dei soggetti.
   */
  private initSetSoggetti() {
    // Verifico se è presente la configurazione per la generazione della lista dei soggetti
    if (this.setSoggetti$) {
      // La funzione esiste, la imposto come variabile per lo scarico dati
      this.setSoggettiLogic$ = this.setSoggetti$;
      // #
    } else {
      // Non c'è una funzione, ne imposto una di default
      this.setSoggettiLogic$ = of([]);
      // #
    }

    // Verifico se è presente la configurazione per l'output da visualizzare dentro la select dei soggetti
    if (this.descrizioneSoggetti) {
      // La funzione esiste, la imposto come variabile per lo scarico dati
      this.descrizioneSoggettiLogic = this.descrizioneSoggetti;
      // #
    } else {
      // Non c'è una funzione, ne creo una di default considerando un oggetto "Soggetto"
      this.descrizioneSoggettiLogic = (soggetto: Soggetto) => {
        // Ritorno la deniminanazione
        return soggetto?.den_soggetto ?? '';
        // #
      };
      // #
    }

    // Verifico se è presente la configurazione per il recupero degli id soggetti dai dati della select
    if (this.descrizioneSoggetti) {
      // La funzione esiste, la imposto come variabile per il recupero dell'id
      this.getIdSoggettoLogic = this.getIdSoggetto;
      // #
    } else {
      // Non c'è una funzione, ne creo una di default considerando un oggetto "Soggetto"
      this.getIdSoggettoLogic = (soggetto: Soggetto) => {
        // Ritorno l'id soggetto
        return soggetto?.id_soggetto;
        // #
      };
      // #
    }
  }

  /**
   * Funzione che setta le azioni nel valueChanges del field Provincia
   */
  initFormListeners() {
    this.ricercaForm.valueChanges.subscribe(
      (formData: OggettiSearchFormData) => {
        // Emetto un evento per informare il componente padre che il form ha cambiato di valore
        this.ricercaFormChanged$.emit(formData);
        // #
      }
    );

    this.ricercaForm
      .get(this.provinciaFCN)
      .valueChanges.pipe(
        distinctUntilChanged(),
        filter((prov) => !!prov),
        takeUntil(this.destroy$)
      )
      .subscribe((prov: Provincia) => {
        // senza provincia non devo chiamare il BE
        if (prov?.cod_provincia) {
          // starto lo spinner
          this._spinner.show();
          // recupero i comuni in base alla provincia
          this.locationService
            .getComuniByProvincia(prov.cod_provincia)
            .subscribe({
              next: (ricercaComuniList: Comune[]) => {
                // Stoppo lo spinner
                this._spinner.hide();
                // Setto i comuni
                this.ricercaComuniList = ricercaComuniList;
                // Abilito il campo per il comune
                this.ricercaForm.get(this.comuneFCN).enable();
                // #
              },
              error: (e: ScrivaServerError) => {
                // Stoppo lo spinner
                this._spinner.hide();
                // Gestisco la segnalazione dell'errore
                this.onServiziError(e, this.ERROR_CODE_COMUNI_GENERIC);
                // #
              },
            });
        } else {
          // SCRIVA-1048
          this.ricercaForm.get(this.comuneFCN).disable();
        }
        this.ricercaForm.get(this.comuneFCN).setValue(null);
      });
  }

  /**
   * Funzione che inizializza help-box
   */
  private initHelper() {
    if (this.helpList?.length > 0) {
      // Recupero dai messaggi di help le chiavi che occorrono per helper e modale che sia apre al click
      const helpTextKey: string = this.qdrOggettoConfig.ricerca_oggetto
        ? this.KEY_RICERCA_OGGETTO_SI
        : this.KEY_RICERCA_OGGETTO_NO;
      const help: Help = this.helpList?.find(
        (help) =>
          help.tipo_help.cod_tipo_help === this.CODE_TIPO_BNR &&
          help.chiave_help.includes(helpTextKey)
      );
      const helpModal: Help = this.helpList?.find(
        (help) =>
          help.tipo_help.cod_tipo_help === this.CODE_TIPO_MDL &&
          help.chiave_help.includes(this.KEY_INFO_QUADRO)
      );
      this.helpText = help?.des_testo_help || this.DEFAULT_HELP_TEXT;
      this.helpModalText =
        helpModal?.des_testo_help || this.DEFAULT_HELP_MODAL_TEXT;
    } else {
      this.helpText = this.helpText || this.DEFAULT_HELP_TEXT;
    }
  }

  /**
   * Funzione che gestisce la validazione dei campi di ricerca in base alla configurazione di questi ultimi.
   * @param criteriRicerca ICriteriRicercaQdrOggetto con i criteri di ricerca per la ricerca dati.
   */
  private gestisciCampiRicerca(criteriRicerca: ICriteriRicercaQdrOggetto) {
    // Effettuo una destrutturazione dell'oggetto e assegno localmente le informazioni
    let criteriRicercaForm: ICriteriRicercaQdrOggetto = { ...criteriRicerca };

    // La gestione del campo di ricerca per comune e provincia ha una logica dedicata, gestisco le informazioni
    this.gestisciCRComuneEProvincia(criteriRicercaForm);

    // Assegno la configurazione dei criteri di ricerca alla variabile del componente
    this.criteriRicerca = criteriRicercaForm;
  }

  /**
   * Funzione che gestisce la validazione dei campi di ricerca in base alla configurazione di questi ultimi.
   * La funzione gestisce in maniera specifica i campi di provincia e comune, poiché devono essere sempre presenti.
   * L'unico modo per nascondere i campi e tramite configurazione su DB, dove si definiscono le configurazioni, ma non visibili.
   * @param criteriRicerca ICriteriRicercaQdrOggetto con i criteri di ricerca per la ricerca dati.
   * @author Ismaele Bottelli
   * @date 26/02/2025
   * @notes Rifattorizzo il codice. Questa è una logica "obbligatoria" impostata per VIA e VINCA dal collega che ha creato il componente.
   *        Riporto quindi anche questa gestione di obbligatorietà.
   */
  private gestisciCRComuneEProvincia(
    criteriRicerca: ICriteriRicercaQdrOggetto
  ) {
    // La provincia, se non configurata, deve adattarsi al comune
    let provincia: ConfigElement;
    let comune: ConfigElement;
    // Estraggo le informazioni delle configurazioni di ricerca
    let configProvincia: ConfigElement = criteriRicerca.provincia;
    let configComune: ConfigElement = criteriRicerca.id_comune;

    // Verifico se esiste la configurazione del comune
    if (configComune) {
      // La configurazione è da forzare anche su provincia
      comune = { ...configComune };
      provincia = { ...configProvincia, ...configComune };
      // Gestisco in maniera specifica la label per la provincia
      const labelProvincia: string =
        configProvincia?.label ?? this.LABEL_PROVINCIA_DEFAULT;
      provincia.label = labelProvincia;
      // #
    } else if (!configComune && configProvincia) {
      // Non esiste il comune, ma esiste la provincia, genero il comune dalla provincia più una label di default
      comune = { ...configProvincia };
      provincia = { ...configProvincia };
      // Assegno la label del FE
      comune.label = this.LABEL_COMUNE_DEFAULT;
      // #
    } else if (!configComune && !configProvincia) {
      // Nessuna delle due configurazioni esiste, genero entrambe
      comune = {
        label: this.LABEL_COMUNE_DEFAULT,
        visibile: true,
        obbligatorio: true,
      };
      provincia = { ...comune };
      provincia.label = this.LABEL_PROVINCIA_DEFAULT;
      // #
    }

    // Modifico per riferimento le configurazioni dei criteri di ricerca
    criteriRicerca.id_comune = comune;
    criteriRicerca.provincia = provincia;
  }

  /**
   * Funzione collegata all'evento di "change" della select dei soggetti.
   */
  soggettoChanged() {
    // Recupero il soggetto nel form
    const soggetto: any = this.ricercaForm?.get(
      this.codiceFiscaleSoggettoFCN
    )?.value;
    // Emetto l'evento di modifica del soggetto
    this.soggettoChanged$.emit(soggetto);
    // #
  }

  /**
   * Funzione che gestisce azione di abilitazione del form
   */
  makeActionEnabled() {
    this.ricercaForm.enable();
  }

  /**
   * Funzione che gestisce azione di reset e disabilita del form
   */
  makeActionResetDisabled() {
    this.ricercaForm.reset();
    this.ricercaForm.disable();
  }

  /**
   * Funzione che gestisce azione di reset e abilita del form
   */
  makeActionResetEnable() {
    this.ricercaForm.reset();
    this.ricercaForm.enable();

    // Anche se c'è l'abilitazione, il comune è disabilitato fino a che la provincia non viene selezionata
    const existComune: boolean = !!this.ricercaForm?.get(this.comuneFCN);
    const existProvincia: boolean = !!this.ricercaForm?.get(this.provinciaFCN);
    // Verifico se esistono entrambe le configurazioni
    if (existProvincia && existComune) {
      // Esiste l'input del comune e della provincia, lo disabilito
      this.ricercaForm.get(this.comuneFCN).disable();
      // #
    }
  }

  /**
   * Funzione che gestisce azione di aggiornamento value e validita'
   */
  makeActionUpdateValueAndValidity() {
    // chiamo il metodo che esegue azione di aggiornamento value e validita'
    this.ricercaForm?.updateValueAndValidity();
  }

  /**
   * Funzione che viene eseguita al click su messagggio di help
   */
  onHelpClicked() {
    // Apro la modale tramite il servizio opportuno
    this._message.showConfirmation({
      title: this.DEFAULT_HELP_MODAL_TITLE,
      codMess: null,
      content: this.helpModalText,
      buttons: [],
    });
  }

  /**
   * Funzione che viene eseguita per la ricerca degli oggetti istanza.
   * Le informazioni del form verranno estratte e verificate per generare parametri di ricerca validi.
   */
  oggettiSearchFormSubmit() {
    // In caso di form non valido mostro un messaggio di errore ed eseguo un return
    if (!this.ricercaForm.valid) {
      // Definisco le informazioni per la visualizzazione del messaggi
      const codMess: string = this.ERROR_CODE_INVALID_FORM;
      const DOMId: string = this.CARD_SEARCH_FORM;
      const autoFade: boolean = true;
      // Visualizzo il messaggio
      this._message.showMessage(codMess, DOMId, autoFade);
      // Interrompo il flusso
      return;
    }

    // Recupero i valori all'interno della form
    const oggettiSearchRequest: OggettiSearchFormData =
      this.generaParametriRicercaOpereDaForm();
    // Lancio la funzione di ricerca
    this.ricercaOpere(oggettiSearchRequest);
    // #
  }

  /**
   * Funzione che gestisce la generazione dell'oggetto di ricerca partendo dai dati inseriti dall'utente sul form.
   * @returns OggettiSearchRequest con i parametri di ricerca generati dai dati della form.
   */
  private generaParametriRicercaOpereDaForm(): OggettiSearchRequest {
    // Recupero i valori all'interno della form
    let formValues: OggettiSearchFormData = this.ricercaForm.getRawValue();
    // Estraggo le proprietà dall'oggetto con i dati del form
    const { anno_presentazione, cod_scriva, den_oggetto, id_comune } =
      formValues ?? {};

    // Definisco l'oggetto per la ricerca delle informazioni tramite servizio, definendo subito l'id_comune (è obbligatiori)
    const oggettiSearchRequest: OggettiSearchRequest = {};
    // Definisco subito la proprietà con l'id istanza di riferimento per la ricerca
    oggettiSearchRequest.id_istanza = this.idIstanza;
    // Aggiungo gli altri parametri di ricerca in maniera "statica"
    oggettiSearchRequest.anno_presentazione = anno_presentazione;
    oggettiSearchRequest.cod_scriva = cod_scriva;
    oggettiSearchRequest.den_oggetto = den_oggetto;

    // SCRIVA-1048 bisogna mandare solo l'id del comune, verifico le informazioni
    const existIdComune: boolean = !isNaN(id_comune);
    // Verifico le condizioni per aggiungere l'id_comune come filtro di ricerca
    if (existIdComune) {
      // Aggiungo il parametro di ricerca
      oggettiSearchRequest.id_comune = id_comune;
      // #
    }

    // Recupero le informazioni per la ricerca sui soggetti
    const idSoggetti: number[] = this.idSoggettiPerRicerca;
    // Verifico se esistono gli id per la ricerca
    if (idSoggetti) {
      // Esistono, aggiungo il parametro di ricerca
      oggettiSearchRequest.id_soggetti = idSoggetti;
      // #
    }

    // In caso che sia presente il codice di tipologia oggetto tra i parametri in input della componente lo passo alla ricerca
    if (this.configTipologiaOggetto) {
      // Aggiungo il codice tipologia oggetto alla ricerca
      oggettiSearchRequest.cod_tipologia_oggetto = this.configTipologiaOggetto;
      // #
    }

    // Ritorno l'oggetto con i parametri di ricerca
    return oggettiSearchRequest;
  }

  /**
   * Funzione che gestisce la parte grafica e logica per la ricerca delle opere date i filtri di ricerca.
   * @param oggettiSearchRequest OggettiSearchRequest con i parametri di ricerca.
   */
  private ricercaOpere(oggettiSearchRequest: OggettiSearchRequest) {
    // starto lo spinner
    this._spinner.show();

    // recupero le opere in base ai filtri di Ricerca
    this.ambitoService
      .getOpereByFilters(oggettiSearchRequest)
      .pipe(
        catchError((e: ScrivaServerError) => {
          // Recupero il codice d'errore e lo formatto forzando una stringa
          const status: string = `${e?.error?.status}`.trim();
          // Verifico se l'errore è un 404
          if (status == '404') {
            // Nel caso di 404 la ricerca può essere considerata "valida", emetto l'evento per la segnalazione di ricerca effettuata
            this.segnalaRicercaEffettuata(oggettiSearchRequest);
            // #
          }
          // Lascio proseguire la gestione dell'errore
          return throwError(e);
          // #
        })
      )
      .subscribe({
        next: (response: Opera[]) => {
          // Blocco lo spinner
          this._spinner.hide();

          // Recupero ed emetto i parametri di ricerca
          this.segnalaRicercaEffettuata(oggettiSearchRequest);
          // Gestisco il flusso di completamento
          this.onRicercaOpereSuccess(response);
          // #
        },
        error: (e: ScrivaServerError) => {
          // Blocco lo spinner
          this._spinner.hide();
          // Gestisco la segnalazione dell'errore
          this.onServiziError(e, undefined, true);
        },
      });
  }

  /**
   * Funzione che gestisce il recupero delle informazioni per la ricerca effettuata ed emette un evento per il componente padre.
   * @param oggettiSearchRequest OggettiSearchRequest con i parametri di ricerca.
   */
  private segnalaRicercaEffettuata(oggettiSearchRequest: OggettiSearchRequest) {
    // Recupero le informazioni usate per la ricerca dati
    const formParams: OggettiSearchFormData = this.ricercaForm.getRawValue();
    const searchParams: OggettiSearchRequest = oggettiSearchRequest;
    // Definisco l'oggetto con gli oggetti usati per la ricerca
    const datiRicerca: IFormOggettiSearchData = {
      formParams,
      searchParams,
    };
    // Emetto l'oggetto che è stato utilizzato per la ricerca
    this.paramsRicercaOpere$.emit(datiRicerca);
  }

  /**
   * Funzione che gestisce il flusso dati quando la ricerca è completata.
   * @param opere Opera[] con la lista delle opere trovate dalla ricerca.
   */
  private onRicercaOpereSuccess(opere: Opera[]) {
    // Emetto le opere verso la componente che include la ricerca
    this.oggettiSearchEmitter.emit(opere);

    // Verifico se è stato definito un codice per quando sono stati trovate opere dalla ricerca
    if (this.successSearchCode) {
      // Esiste il codice, gestisco la segnalazione
      const code = this.successSearchCode;
      const target = this.CARD_SEARCH_FORM;
      const autoFade = true;
      let placeholder: IMsgPlacholder;

      // Verifico se il codice è I030
      if (code === ScrivaCodesMesseges.I030) {
        // Recupero il placholder del codice
        const ph = ScrivaCodesPlaceholders[code][0];
        // Aggiungo il dato del placholder
        placeholder = { ph, swap: `${opere?.length ?? ''}` };
        // #
      }

      // Visualizzo il messaggio d'errore
      this._message.showMessage(code, target, autoFade, placeholder);
      // #
    }
  }

  /**
   * ####################################
   * FUNZIONI DI COMODO PER IL COMPONENTE
   * ####################################
   */

  /**
   * Funzione di supporto che gestisce gli errori generati dalla chiamata ai servizi.
   * @param e ScrivaServerError con l'oggetto d'errore generato dalla chiamata.
   * @param defaultCode ScrivaCodesMesseges con il possibile default da gestire in caso di errore.
   * @param check404 boolean che permette di attivare la gestione specifica di un errore 404, principalmente per le ricerca Opere.
   */
  protected onServiziError(
    e?: ScrivaServerError,
    defaultCode?: ScrivaCodesMesseges,
    check404: boolean = false
  ) {
    // Definisco la configurazione per la gestione dell'errore
    const errorConfigs: IServiziErrorConfig = {
      target: this.CARD_SEARCH_FORM,
      autoFade: false,
      defaultCode: defaultCode ?? ScrivaCodesMesseges.E100,
      e,
    };

    // Richiamo la funzione di gestione degli errori con configurazion
    this.onServiziErrorConfigs(errorConfigs, check404);
  }

  /**
   * Funzione di comodo che gestisce i possibili errori generati dal server.
   * @param configs IServiciErrorConfig che definisce la configurazione per la visualizzazione degli errori.
   * @param check404 boolean che permette di attivare la gestione specifica di un errore 404, principalmente per le ricerca Opere.
   */
  protected onServiziErrorConfigs(
    configs: IServiziErrorConfig,
    check404: boolean = false
  ) {
    // Verifico l'input
    if (!configs) {
      // Nessuna configurazione, blocco
      return;
    }

    // Estraggo le configurazioni dall'input
    const { e, defaultCode, target } = configs;
    let autoFade: boolean = false;

    // Estraggo il possibile codice d'errore dall'oggetto ritornato dal server
    let errCode: string = e?.error?.code;
    // Verifico se esite un codice di errore specifico
    if (!errCode && check404) {
      // Non è stato ritornato un codice specifico, verifico lo status dell'errore è 404 (stringa o numero)
      if (e?.status == 404) {
        // Imposto il codice errore come non trovato
        errCode = this.ERROR_CODE_SEARCH404;
        // #
      } else {
        // Imposto il codice errore generico
        errCode = this.ERROR_CODE_SEARCH_GENERIC;
        // #
      }
      // Attivo l'autofade
      autoFade = true;
      // #
    } else {
      // Imposto il codice errore generico
      errCode = defaultCode ?? this.ERROR_CODE_SEARCH_GENERIC;
    }

    // Visualizzo il messaggio d'errore
    this._message.showMessage(errCode, target, autoFade);
  }

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  /**
   * Getter che recupera il nome del campo per l'identificazione del form control name.
   * @returns string con il nome del form control.
   */
  get provinciaFCN(): string {
    // Ritorno il nome dalla costante
    return this.OGG_SEARCH_CONST.PROVINCIA;
    // #
  }

  /**
   * Getter che recupera il nome del campo per l'identificazione del form control name.
   * @returns string con il nome del form control.
   */
  get comuneFCN(): string {
    // Ritorno il nome dalla costante
    return this.OGG_SEARCH_CONST.ID_COMUNE;
    // #
  }

  /**
   * Getter che recupera il nome del campo per l'identificazione del form control name.
   * @returns string con il nome del form control.
   */
  get denominazioneOggettoFCN(): string {
    // Ritorno il nome dalla costante
    return this.OGG_SEARCH_CONST.DEN_OGGETTO;
    // #
  }

  /**
   * Getter che recupera il nome del campo per l'identificazione del form control name.
   * @returns string con il nome del form control.
   */
  get codiceScrivaFCN(): string {
    // Ritorno il nome dalla costante
    return this.OGG_SEARCH_CONST.COD_SCRIVA;
    // #
  }

  /**
   * Getter che recupera il nome del campo per l'identificazione del form control name.
   * @returns string con il nome del form control.
   */
  get annoPresentazioneFCN(): string {
    // Ritorno il nome dalla costante
    return this.OGG_SEARCH_CONST.ANNO_PRESENTAZIONE;
    // #
  }

  /**
   * Getter che recupera il nome del campo per l'identificazione del form control name.
   * @returns string con il nome del form control.
   */
  get codiceFiscaleSoggettoFCN(): string {
    // Ritorno il nome dalla costante
    return this.OGG_SEARCH_CONST.CF_SOGGETTO;
    // #
  }

  /**
   * Getter che gestisce le informazioni per la ricerca dei soggetti.
   * La logica prevede di dare priorità alla configurazione dei soggetti come form.
   * Se manca la configurazione, si utilizzerà il valore della variabile di input.
   * @returns number[] con la lista degli id soggetti per la ricerca. Se mancano le informazioni, ritornerà undefined.
   */
  get idSoggettiPerRicerca(): number[] {
    // Recupero i valori all'interno della form
    let formValues: OggettiSearchFormData = this.ricercaForm.getRawValue();
    // Estraggo le proprietà dall'oggetto con i dati del form
    const cfSoggetto: any = formValues?.cf_soggetto;
    // Verifico se esiste il codice fiscale del soggetto
    if (cfSoggetto && cfSoggetto !== this.COMMON_CONST.DEFAULT_SELECT_VALUE) {
      // Esiste ritorno l'id soggetto sulla base delle logiche definite
      return [this.getIdSoggettoLogic(cfSoggetto)];
      // #
    }

    // Recupero la lista di input
    const idSoggetti: number[] = this.idSoggetti;
    // Verifico se esistono idSoggetti
    if (idSoggetti) {
      // La lista esiste, la ritorno
      return idSoggetti;
      // #
    }

    // Non esiste la gestione dei soggetti
    return undefined;
  }
}
