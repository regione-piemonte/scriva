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
  AfterViewInit,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import * as _ from 'lodash';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin, Observable, of } from 'rxjs';
import {
  debounceTime,
  distinctUntilChanged,
  filter,
  map,
  switchMap,
  takeUntil,
  tap,
} from 'rxjs/operators';
import { AutoUnsubscribe } from 'src/app/core/components';
import {
  AnagraficaSoggettoPF,
  AnagraficaSoggettoPG,
  ConfigRuoloAdempimento,
  FormSoggettoPF,
  FormSoggettoPG,
  RuoloSoggetto,
  Soggetto,
  TipoNaturaGiuridica,
  TipoSoggetto,
} from 'src/app/features/ambito/models';
import {
  AmbitoService,
  SoggettoStoreService,
} from 'src/app/features/ambito/services';
import { ControlloCf, NumeroEnum } from 'src/app/features/ambito/utils';
import { ErrorValidatorService } from 'src/app/shared/form-error-validator';
import {
  Adempimento,
  Compilante,
  Comune,
  Help,
  Nazione,
  NominatimLocation,
  Provincia,
  Regione,
  RuoloCompilante,
  TipoAdempimento,
} from 'src/app/shared/models';
import {
  HelpService,
  LocationService,
  MessageService,
  NominatimService,
} from 'src/app/shared/services';
import { RegexUtil } from 'src/app/shared/utils';
import {
  ScrivaCodiciRuoloCompilante,
  ScrivaComponenteApp,
  ScrivaTipiPersona,
} from '../../../../../../shared/enums/scriva-utilities/scriva-utilities.enums';
import { IAlertConfig } from '../../../../../../shared/interfaces/scriva-utilities.interfaces';
import { SoggettiService } from '../../../../../../shared/services/soggetti/soggetti.service';
import {
  IDatiCompilanteAccessibili,
  IRecuperaRichiedente,
  ISoggettoFormSubmitData,
} from './utilities/soggetto-form.interfaces';
import { StatiRicercaRichiedente } from './utilities/soggetto-form.enums';

/**
 * Interfaccia di comodo per la gestione dei dati di configurazione a seguito del cambio del compilante.
 */
interface IChangeCompilanteDataReq {
  ruoliSoggetto: Observable<RuoloSoggetto[]>;
  adempimentoConfig: Observable<ConfigRuoloAdempimento>;
}

/**
 * Interfaccia di comodo per la gestione dei dati di configurazione a seguito del cambio del compilante.
 */
interface IChangeCompilanteDataRes {
  ruoliSoggetto: RuoloSoggetto[];
  adempimentoConfig: ConfigRuoloAdempimento;
}

/**
 * Interfaccia di comodo che definisce i dati generati dalla conversione dei form soggetto/compilante ad oggetti Soggetto.
 */
interface IDatiSoggettoDaForm {
  soggetto: Soggetto;
  firmatario?: Soggetto;
}

@Component({
  selector: 'app-soggetto-form',
  templateUrl: './soggetto-form.component.html',
  styleUrls: ['./soggetto-form.component.scss'],
})
export class SoggettoFormComponent
  extends AutoUnsubscribe
  implements OnInit, AfterViewInit
{
  @Output() confermaInserisciSoggetto$ =
    new EventEmitter<ISoggettoFormSubmitData>();
  @Output() annullaInserisciSoggetto$ = new EventEmitter();

  @Input() data;
  @Input() componente;
  @Input() formSoggettoRow: {
    record: FormSoggettoPF | FormSoggettoPG;
    index: number;
  } = null;
  @Input() soggetto: Partial<Soggetto> = null;
  @Input() soggettoInAnagrafica: boolean;
  @Input() ultimoTipoPersonaCercato:
    | ScrivaTipiPersona.PF
    | ScrivaTipiPersona.PG
    | ScrivaTipiPersona.PB;
  @Input() ultimoCFCercato: string;

  @Input() adempimentoConfigList: ConfigRuoloAdempimento[] = [];

  @Input() compilante: Compilante;
  @Input() tipoAdempimento: TipoAdempimento;
  @Input() adempimento: Adempimento;

  @Input() tipiSoggetto: TipoSoggetto[] = [];
  @Input() ruoliSoggetto: RuoloSoggetto[] = [];
  @Input() ruoliCompilante: RuoloCompilante[] = [];
  @Input() tipiNaturaGiuridica: TipoNaturaGiuridica[] = [];

  @Input() nazioni: Nazione[];
  @Input() nazioniAttive: Nazione[];
  @Input() flagRecapitoAlt: boolean;

  @Input() showYesButton: false;
  @Input() yesButtonLabel = 'CONFERMA';
  @Input() showNoButton: false;
  @Input() noButtonLabel = 'Annulla';

  @Input() notaInserimento: string;
  @Input() segnalazioneInserimento: IAlertConfig;

  @Input() codQuadro: string;

  @Input() onlyShow: boolean;

  notaInserimentoRichiedente: string;

  tipologiaPersona = ScrivaTipiPersona;
  numerazione = NumeroEnum;
  today = new Date();

  formInserisciSoggetto: FormGroup;

  provinceAttive: Provincia[] = [];
  provinceFullList: Provincia[] = [];

  // regioniNascita: Regione[];
  // provinceNascita: Provincia[];
  comuniNascita: Comune[];
  firstLoad_nascita = true;

  // regioniResidenza: Regione[];
  // provinceResidenza: Provincia[];
  comuniResidenza: Comune[];
  firstLoad_residenza = true;
  searchResultsResidenza: NominatimLocation[];

  // regioniResidenzaRecAlt: Regione[];
  // provinceResidenzaRecAlt: Provincia[];
  comuniResidenzaRecAlt: Comune[];
  firstLoad_residenzaAlt = true;
  searchResultsResidenzaAlt: NominatimLocation[];

  // regioniSedeLegale: Regione[];
  // provinceSedeLegale: Provincia[];
  comuniSedeLegale: Comune[];
  firstLoad_sedeLegale = true;
  searchResultsSedeLegale: NominatimLocation[];

  // regioniSedeLegaleRecAlt: Regione[];
  // provinceSedeLegaleRecAlt: Provincia[];
  comuniSedeLegaleRecAlt: Comune[];
  firstLoad_sedeLegaleAlt = true;
  searchResultsSedeLegaleAlt: NominatimLocation[];

  adempimentoConfig: ConfigRuoloAdempimento;
  idRuoloCompilante: number;

  showRecAltResidenzaSoggetto = false;
  showRecAltResidenzaRichiedente = false;
  showRecAltSedeLegale = false;

  loadingAddress = false;

  statoRicercaRichiedente = StatiRicercaRichiedente.nonGestito;

  searchAddressResidenza = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(1000),
      distinctUntilChanged(),
      tap(() => (this.loadingAddress = true)),
      switchMap((input) => {
        return this.getAddressResidenza(input).pipe(
          tap((list) => (this.searchResultsResidenza = list)),
          map((list) => {
            const resultStrings = [];
            if (list) {
              list.forEach((result) => resultStrings.push(result.display_name));
            }
            return resultStrings;
          })
        );
      }),
      tap(() => (this.loadingAddress = false))
    );

  searchAddressResidenzaAlt = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(1000),
      distinctUntilChanged(),
      tap(() => (this.loadingAddress = true)),
      switchMap((input) => {
        return this.getAddressResidenzaAlt(input).pipe(
          tap((list) => (this.searchResultsResidenzaAlt = list)),
          map((list) => {
            const resultStrings = [];
            if (list) {
              list.forEach((result) => resultStrings.push(result.display_name));
            }
            return resultStrings;
          })
        );
      }),
      tap(() => (this.loadingAddress = false))
    );

  searchAddressSedeLegale = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(1000),
      distinctUntilChanged(),
      tap(() => (this.loadingAddress = true)),
      switchMap((input) => {
        return this.getAddressSedeLegale(input).pipe(
          tap((list) => (this.searchResultsSedeLegale = list)),
          map((list) => {
            const resultStrings = [];
            if (list) {
              list.forEach((result) => resultStrings.push(result.display_name));
            }
            return resultStrings;
          })
        );
      }),
      tap(() => (this.loadingAddress = false))
    );

  searchAddressSedeLegaleAlt = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(1000),
      distinctUntilChanged(),
      tap(() => (this.loadingAddress = true)),
      switchMap((input) => {
        return this.getAddressSedeLegaleAlt(input).pipe(
          tap((list) => (this.searchResultsSedeLegaleAlt = list)),
          map((list) => {
            const resultStrings = [];
            if (list) {
              list.forEach((result) => resultStrings.push(result.display_name));
            }
            return resultStrings;
          })
        );
      }),
      tap(() => (this.loadingAddress = false))
    );

  constructor(
    private ms: MessageService,
    private fb: FormBuilder,
    private ambitoService: AmbitoService,
    private spinner: NgxSpinnerService,
    private locationService: LocationService,
    private nominatimService: NominatimService,
    private errorValidatorService: ErrorValidatorService,
    private helpService: HelpService,
    public activeModal: NgbActiveModal,
    private soggettoStoreService: SoggettoStoreService,
    private _soggetti: SoggettiService
  ) {
    super();
    this.locationService.getProvince().subscribe(
      (res) => {
        this.provinceFullList = res;
        this.provinceAttive = this.provinceFullList.filter(
          (prov) => !prov.data_fine_validita
        );
      },
      (err) => {
        if (err.error?.code) {
          this.ms.showMessage(err.error.code, 'formInserisciSoggetto', true);
        } else {
          this.ms.showMessage('E100', 'formInserisciSoggetto', true);
        }
      }
    );
  }

  ngAfterViewInit() {
    if (this.formSoggettoRow) {
      this.setCheckBoxesRecapitoAlternativo(
        this.formSoggettoRow.record as FormSoggettoPG
      );
    }
  }

  ngOnInit() {
    // Verifico se ci sono da visualizzare notifiche da parte del componente padre per la ricerca
    this.initSegnalazioneInserimentoSoggetto();
    // Verifico se è stato passato un oggetto come riga di tabella dei soggetti
    this.initSoggettoRow();
  }

  /**
   * Funzione che verifica le configurazioni in input e gestisce l'eventuale segnalazione per l'inserimento soggetto.
   */
  private initSegnalazioneInserimentoSoggetto() {
    // Verifico se è stata passata una configurazione
    if (this.segnalazioneInserimento) {
      // Estraggo dalla configurazione i parametri
      const { code, idDOM, autoFade } = this.segnalazioneInserimento;
      // Esiste un oggetto, visualizzo un messaggio
      this.onServiziError(code, idDOM, autoFade);
      // #
    }
  }

  /**
   * Funzione che gestisce le logiche di init nella casistica in cui viene passato un soggetto tramite riga di tabella presente nel componente padre.
   */
  private initSoggettoRow() {
    // Verifico se esiste la configurazione per la riga di tabella con i dati del soggetto
    const existFSR = this.formSoggettoRow != undefined;
    // Controllo il flag
    if (existFSR) {
      // Esistono dati, vado a recuperare le informazioni per gestire il flusso
      const datiRow = this.formSoggettoRow.record;
      const fsrCodTipoSoggetto =
        datiRow.anagraficaSoggetto?.tipoSoggetto?.cod_tipo_soggetto;

      // Verifico se il soggetto passato è una persona fisica
      if (fsrCodTipoSoggetto === this.tipologiaPersona.PF) {
        // E' una persona fisica, effettuo il parse dei dati della riga come FormSoggettoPF
        const formSoggetto: FormSoggettoPF = datiRow as FormSoggettoPF;
        // Lancio la funzione di gestione per la persona fisica
        this.initSoggettoRowPF(formSoggetto);
        // #
      } else {
        // E' una persona giuridica, effettuo il parse dei dati della riga come FormSoggettoPG
        const formSoggetto: FormSoggettoPG = datiRow as FormSoggettoPG;
        // Lancio la funzione di gestione per la persona fisica
        this.initSoggettoRowPG(formSoggetto);
        // #
      }
    } else {
      this._buildFormInserisciSoggetto(
        this.soggetto,
        this.ultimoCFCercato,
        this.ultimoTipoPersonaCercato
      );
    }
  }

  /**
   * Funzione specifica d'inizializzazione dati quando il soggetto viene passato tramite riga di tabella (dal componente padre) ed il tipo soggetto è persona fisica.
   * @param datiRow FormSoggettoPF con le informazioni per gestire i dati del form.
   */
  private initSoggettoRowPF(datiRow: FormSoggettoPF) {
    // E' una persona fisica, effettuo il parse dei dati della riga come FormSoggettoPF
    const formSoggetto: FormSoggettoPF = datiRow;
    // Lancio la build del form soggetto tramite i dati della riga di tabella
    this._buildFormInserisciSoggettoPFFromTable(formSoggetto);
    // Effettuo il set per il ruolo compilante PF
    this.setRuoliCompilantePF();
    // Una volta settato il ruolo, lancio le logiche come se fosse stato modificato il ruolo compilante
    this.verificaEGestisciDatiCompilante(true);
    // this.setCheckBoxesRecapitoAlternativo(this.formSoggettoRow.record as FormSoggettoPF);
  }

  /**
   * Funzione specifica d'inizializzazione dati quando il soggetto viene passato tramite riga di tabella (dal componente padre) ed il tipo soggetto è persona giuridica.
   * @param datiRow FormSoggettoPG con le informazioni per gestire i dati del form.
   */
  initSoggettoRowPG(datiRow: FormSoggettoPG) {
    // E' una persona fisica, effettuo il parse dei dati della riga come FormSoggettoPF
    const formSoggetto: FormSoggettoPG = datiRow;
    // Lancio la build del form soggetto tramite i dati della riga di tabella
    this._buildFormInserisciSoggettoPGFromTable(formSoggetto);
    // Lancio le logiche come se fosse stato modifica il ruolo compilante
    this.verificaEGestisciDatiCompilante(true);
    // this.setCheckBoxesRecapitoAlternativo(this.formSoggettoRow.record as FormSoggettoPG);
  }

  setRuoliCompilantePF() {
    // Resetto la lista dei ruoli compilante
    this.ruoliCompilante = [];
    // Verifico se il soggetto ricerca è lo stesso dell'attore in linea
    const soggettoAsAttoreInLinea =
      this.ultimoCFCercato === this.compilante?.cf_compilante;

    // Richiamo la funzione per ottenere i ruoli compilante per PF
    this.ruoliCompilante = this._soggetti.ruoliCompilantePF(
      this.adempimentoConfigList,
      soggettoAsAttoreInLinea
    );
  }

  /**
   * ####################################
   * FUNZIONI PER LA BUILD DEL FORM GROUP
   * ####################################
   */

  /**
   *
   * @param soggetto
   * @param cfCercato
   * @param tipoSoggettoCercato
   */
  private _buildFormInserisciSoggetto(
    soggetto: Partial<Soggetto> = null,
    cfCercato: string = null,
    tipoSoggettoCercato: string = null
  ) {
    // se il soggetto è Giuridico creo tutto
    // - compilante !=== richiedente
    // - compilante ==== richiedente
    if (tipoSoggettoCercato !== this.tipologiaPersona.PF) {
      this.formInserisciSoggetto = this.fb.group({
        anagraficaSoggetto: this._buildFormSoggetto(
          cfCercato,
          tipoSoggettoCercato,
          soggetto
        ),
        ruoloCompilante: [null, Validators.required],
      });
    } else {
      this.formInserisciSoggetto = this.fb.group({
        anagraficaSoggetto: this._buildFormSoggetto(
          cfCercato,
          tipoSoggettoCercato,
          soggetto
        ),
      });

      // Definisco le informazioni per la gestione del ruolo compilante
      let ruoloCompilante: RuoloCompilante = null;
      // Verifico se il soggetto e l'attore in linea sono lo stesso
      if (cfCercato == this.compilante?.cf_compilante) {
        // Estraggo dalla lista di configurazione degli adempimenti il ruolo compilante di default
        ruoloCompilante = this.adempimentoConfigList.find(
          (config: ConfigRuoloAdempimento) => {
            // Ritoron il valore del flag di defaul
            return config.flg_ruolo_default === true;
            // #
          }
        )?.ruolo_compilante;
      }
      // Gestisco il campo del ruolo compilante
      this.updateRuoloCompilante(ruoloCompilante, cfCercato);
    }
  }

  /**
   * Funzione che raccoglie e gestisce le informazioni per la generazione del form dati.
   * Il form dati verrà costruito sulle logiche definite per un soggetto con tipo soggetto: PF.
   * @param formSoggetto FormSoggettoPF con l'oggetto per generare il form.
   */
  private _buildFormInserisciSoggettoPFFromTable(formSoggetto: FormSoggettoPF) {
    // Genero i dati per la persona fisica
    const datiPF = this._formToSoggettiPF(formSoggetto);
    // Estraggo le informazioni
    const { soggetto, firmatario } = datiPF || {};

    // Aggiorno i dati all'interno del servizio di snapshot dei soggetti
    this.setDatiSoggettiInSnapshot(soggetto, firmatario);
    // Effettuo il setup dell'anagrafica del soggetto
    this.setupAnagraficaSoggetto(soggetto);

    // Se il soggetto passato è lo stesso del soggetto compilante NON avrà e NON deve avere la possibilità di gestire il ruolo compilante
    if (soggetto?.cf_soggetto !== this.compilante?.cf_compilante) {
      // Definisco le informazioni per la gestione del ruolo compilante
      const ruoloCompilante: RuoloCompilante = formSoggetto.ruoloCompilante;
      const cfSoggetto: string = soggetto.cf_soggetto;
      // Gestisco il campo del ruolo compilante
      this.updateRuoloCompilante(ruoloCompilante, cfSoggetto);
    }

    // Recupero le informazioni per la gestione dell'anagrafica richiedente
    const ruoloRichiedente: RuoloSoggetto =
      formSoggetto.anagraficaRichiedente?.ruoloSoggetto;
    // Lancio il setup dell'anagrafica del richiedente
    this.setupAnagraficaRichiedente(firmatario, ruoloRichiedente);

    // Definisco le variabili per la gestione del recapito alternativo residenza
    let fgSogg: FormGroup;
    fgSogg = this.anagraficaSoggettoFG as FormGroup;
    let anagSogg: AnagraficaSoggettoPF;
    anagSogg = formSoggetto.anagraficaSoggetto;

    // Lancio al funzione per gestire i dati del recapito alternativo collegato all'anagrafica SOGGETTO
    this.gestisciRecapitoAlternativoResidenza(fgSogg, anagSogg, true);

    // Definisco le variabili per la gestione del recapito alternativo residenza
    let fgR: FormGroup;
    fgR = this.anagraficaRichiedenteFG as FormGroup;
    let anagRic: AnagraficaSoggettoPF;
    anagRic = formSoggetto.anagraficaRichiedente;

    // Lancio al funzione per gestire i dati del recapito alternativo collegato all'anagrafica RICHIEDENTE
    this.gestisciRecapitoAlternativoResidenza(fgR, anagRic, false);
    // #
  }

  /**
   * Funzione che raccoglie e gestisce le informazioni per la generazione del form dati.
   * Il form dati verrà costruito sulle logiche definite per un soggetto con tipo soggetto: PG.
   * @param formSoggetto FormSoggettoPG con l'oggetto per generare il form.
   */
  private _buildFormInserisciSoggettoPGFromTable(formSoggetto: FormSoggettoPG) {
    // Genero i dati per la persona giuridica
    const datiPG = this._formToSoggettiPG(formSoggetto);
    // Estraggo le informazioni
    const { soggetto, firmatario } = datiPG || {};

    // Aggiorno i dati all'interno del servizio di snapshot dei soggetti
    this.setDatiSoggettiInSnapshot(soggetto, firmatario);
    // Effettuo il setup dell'anagrafica del soggetto
    this.setupAnagraficaSoggetto(soggetto);

    // Recupero le informazioni per la gestione del ruolo compilante
    const ruoloCompilante: RuoloCompilante = formSoggetto.ruoloCompilante;
    const cfSoggetto: string = soggetto.cf_soggetto;
    // Gestisco il campo del ruolo compilante
    this.updateRuoloCompilante(ruoloCompilante, cfSoggetto);

    // Recupero le informazioni per la gestione dell'anagrafica richiedente
    const ruoloRichiedente: RuoloSoggetto =
      formSoggetto.anagraficaRichiedente?.ruoloSoggetto;
    // Lancio il setup dell'anagrafica del richiedente
    this.setupAnagraficaRichiedente(firmatario, ruoloRichiedente);

    // Definisco le variabili per la gestione del recapito alternativo sede legale
    let fgSogg: FormGroup;
    fgSogg = this.formInserisciSoggetto.get('anagraficaSoggetto') as FormGroup;
    let anagSogg: AnagraficaSoggettoPG;
    anagSogg = formSoggetto.anagraficaSoggetto;

    // Lancio al funzione per gestire i dati del recapito alternativo collegato all'anagrafica SOGGETTO
    this.gestisciRecapitoAlternativoSedeLegale(fgSogg, anagSogg);

    // Definisco le variabili per la gestione del recapito alternativo residenza
    let fgR: FormGroup;
    fgR = this.formInserisciSoggetto.get('anagraficaRichiedente') as FormGroup;
    let anagRic: AnagraficaSoggettoPF;
    anagRic = formSoggetto.anagraficaRichiedente;

    // Lancio al funzione per gestire i dati del recapito alternativo collegato all'anagrafica SOGGETTO
    this.gestisciRecapitoAlternativoResidenza(fgR, anagRic, false);
    // #
  }

  private _buildFormSoggetto(
    cfCercato: string,
    tipoSoggettoCercato: string,
    soggetto: Partial<Soggetto> = null,
    isRichiedente: boolean = false,
    ruoloSoggetto: RuoloSoggetto = null
  ) {
    if (tipoSoggettoCercato === ScrivaTipiPersona.PF) {
      return this._formPersFis(
        soggetto,
        cfCercato,
        tipoSoggettoCercato,
        isRichiedente,
        ruoloSoggetto
      );
    } else {
      return this._formPersGiur(soggetto, cfCercato, tipoSoggettoCercato);
    }
  }

  /**
   * Funzione di comodo che gestisce le logiche di set dati all'interno del servizio di snapshot dei soggetti.
   * @param soggetto Soggetto con i dati da definire all'interno del servizio di snapshot.
   * @param firmatario Soggetto con i dati da definire all'interno del servizio di snapshot.
   */
  private setDatiSoggettiInSnapshot(soggetto: Soggetto, firmatario?: Soggetto) {
    // Setto all'interno del servizio i dati per i soggetti
    this.soggettoStoreService.soggettoSnapshot = _.clone(soggetto);
    this.soggettoStoreService.richiedenteSnapshot = _.clone(firmatario);
  }

  /**
   * Funzione di comodo che gestisce i dati del soggetto e crea una nuova istanza del form group del soggetto (form principale).
   * @param soggetto Soggetto con le informazioni da inserire dentro il form.
   */
  private setupAnagraficaSoggetto(soggetto: Soggetto) {
    // Estraggo dall'oggetto le informazioni minime
    const cfSoggetto = soggetto?.cf_soggetto;
    const codTipoSoggetto = soggetto?.tipo_soggetto?.cod_tipo_soggetto;
    // Creo un oggetto anagrafica compatibile con il form
    const anagraficaSoggetto = this._buildFormSoggetto(
      cfSoggetto,
      codTipoSoggetto,
      soggetto
    );
    // Creo un nuovo form group e inserisco i dati dell'anagrafica
    this.formInserisciSoggetto = this.fb.group({ anagraficaSoggetto });
  }

  /**
   * Funzione di comodo che gestisce i dati del richiedente e gestisce le strutture dati all'interno del form del soggetto (form principale).
   * @param richiedente Soggetto con le informazioni da inserire dentro il form.
   * @param ruoloRichiedente RuoloSoggetto che definisce i dati del ruolo soggetto del richiedente.
   */
  private setupAnagraficaRichiedente(
    richiedente: Soggetto,
    ruoloRichiedente: RuoloSoggetto
  ) {
    // Verifico se esiste il richiedente
    if (!richiedente) {
      // Non c'è la configurazione, blocco il flusso
      return;
    }

    // Estraggo le informazioni dal richiedente e definisco le variabili per la costruzione della struttura per il form
    const cfRichiedente: string = richiedente?.cf_soggetto;
    const codTipoSoggettoRichiedente: string =
      richiedente?.tipo_soggetto?.cod_tipo_soggetto;
    const isRichiedente: boolean = true;
    // Definisco la variabile che conterrà la struttura del sub form group che gestirà i dati del richiedente
    let formRichiedente: FormGroup;
    formRichiedente = this._buildFormSoggetto(
      cfRichiedente,
      codTipoSoggettoRichiedente,
      richiedente,
      isRichiedente,
      ruoloRichiedente
    );

    // Lancio al funzione per gestire l'anagrafica richiedente
    this.updateControlAnagraficaRichiedente(formRichiedente);
  }

  /**
   * Funzione che gestisce la struttura del form riferita al recapito alternativo per la sede legale.
   * Questa funzione gestisce solo ed esclusivamente dei soggetti di tipo PG (persona giuridica).
   * @param formGroup FormGroup con l'oggetto del form sulla quale gestire i dati del recapito alternativo.
   * @param anagrafica AnagraficaSoggettoPF con i dati dell'anagrafica del soggetto (PG) da gestire nel form.
   */
  private gestisciRecapitoAlternativoSedeLegale(
    formGroup: FormGroup,
    anagrafica: AnagraficaSoggettoPG
  ) {
    // Verifico l'input
    if (!formGroup || !anagrafica) {
      // Mancano le configurazioni
      return;
    }

    // Definisco le chiavi per la gestione del form
    const recapitoAlternativoSedeLegaleKey = 'recapitoAlternativoSedeLegale';
    const statoSedeLegaleAltKey = 'statoSedeLegaleAlt';

    // Verifico che esista il recapito alternativo sede legale all'interno dell'anagrafica in input
    if (anagrafica.recapitoAlternativoSedeLegale) {
      // Assegno localmente dall'input le informazioni del form group
      const anagraficaFormGroup = formGroup;

      // Aggiungo il controllo all'oggetto del form anagrafica per il recapito sede legale
      anagraficaFormGroup.addControl(
        recapitoAlternativoSedeLegaleKey,
        this.buildRecapitoAlternativoSedeLegale(anagrafica)
      );

      // Lancio manualmente la funzione di modifica del recapito alternativo della residenza
      this.onChangeRecAltSedeLegale(
        anagraficaFormGroup.get(recapitoAlternativoSedeLegaleKey) as FormGroup,
        anagrafica
      );

      // Mostro la sezione del recapito alternativo sede legale
      this.showRecAltSedeLegale = true;
      anagraficaFormGroup
        .get(recapitoAlternativoSedeLegaleKey)
        .get(statoSedeLegaleAltKey)
        .setValue(anagrafica.recapitoAlternativoSedeLegale.statoSedeLegaleAlt);
    }
  }

  /**
   * Funzione che gestisce la struttura del form riferita al recapito alternativo residenza.
   * A seconda che si stia gestendo il soggetto o il compilante, verrà visualizzata la sezione specifica.
   * @param formGroup FormGroup con l'oggetto del form sulla quale gestire i dati del recapito alternativo.
   * @param anagrafica AnagraficaSoggettoPF con i dati dell'anagrafica del soggetto (PF) o del richiedente (PG) da gestire nel form.
   * @param isSoggetto boolean che definisce il flusso della logica, se impostare le informazioni per un soggetto o per un richiedente.
   */
  private gestisciRecapitoAlternativoResidenza(
    formGroup: FormGroup,
    anagrafica: AnagraficaSoggettoPF,
    isSoggetto: boolean
  ) {
    // Verifico l'input
    if (!formGroup || !anagrafica) {
      // Mancano le configurazioni
      return;
    }

    // Definisco le chiavi per la gestione del form
    const recapitoAlternativoResidenzaKey = 'recapitoAlternativoResidenza';
    const statoResidenzaAltKey = 'statoResidenzaAlt';

    // Verifico che esista il recapito alternativo residenza all'interno dell'anagrafica in input
    if (anagrafica.recapitoAlternativoResidenza) {
      // Assegno localmente dall'input le informazioni del form group
      const anagraficaFormGroup = formGroup;

      // Aggiungo il controllo all'oggetto del form anagrafica per il recapito alternativo
      anagraficaFormGroup.addControl(
        recapitoAlternativoResidenzaKey,
        this.buildRecapitoAlternativoResidenza(anagrafica)
      );

      // Lancio manualmente la funzione di modifica del recapito alternativo della residenza
      this.onChangeRecAltResidenza(
        anagraficaFormGroup.get(recapitoAlternativoResidenzaKey) as FormGroup,
        anagrafica
      );

      // Mostro la sezione del recapito alternativo residenza a seconda della configurazione
      if (isSoggetto) {
        // Stiamo gestendo i dati del soggetto, mostro la sua sezione
        this.showRecAltResidenzaSoggetto = true;
      } else {
        // Stiamo gestendo i dati del richiedente, mostro la sua sezione
        this.showRecAltResidenzaRichiedente = true;
      }

      // Reupero il form control del recapito alternativo residenza, recupero lo stato residenza e imposto il valore
      anagraficaFormGroup
        .get(recapitoAlternativoResidenzaKey)
        .get(statoResidenzaAltKey)
        .setValue(anagrafica.recapitoAlternativoResidenza.statoResidenzaAlt);
    }
  }

  /**
   * ############################################
   * FUNZIONI CHE GENERANO GLI OGGETTI FORM GROUP
   * ############################################
   */

  private _formPersFis(
    soggetto: Partial<Soggetto>,
    cfCercato: string = null,
    tipoSoggettoCercato: string = null,
    isRichiedente: boolean,
    ruoloSoggetto: RuoloSoggetto
  ) {
    const formGroup = this.fb.group({
      id_soggetto: soggetto?.id_soggetto,
      gestUID: soggetto?.gestUID,
      id_masterdata: soggetto?.id_masterdata,
      id_masterdata_origine: soggetto?.id_masterdata_origine,
      cf: [
        {
          value: cfCercato,
          disabled: isRichiedente
            ? null
            : soggetto?.cf_soggetto || cfCercato
            ? true
            : null,
        },
        {
          validators: [
            Validators.required,
            Validators.pattern(RegexUtil.CF),
            Validators.maxLength(16),
            this.cfValidator,
          ],
        },
      ],
      cognome: [
        soggetto?.cognome || '',
        {
          disabled: true,
          validators: [
            Validators.required,
            Validators.maxLength(50),
            Validators.pattern(RegexUtil.Name_Surname),
          ],
        },
      ],
      nome: [
        soggetto?.nome || '',
        {
          disabled: true,
          validators: [
            Validators.required,
            Validators.maxLength(50),
            Validators.pattern(RegexUtil.Name_Surname),
          ],
        },
      ],
      statoNascita: [null, Validators.required],
      // regioneNascita: [null, Validators.required],
      provinciaNascita: [null, Validators.required],
      comuneNascita: [null, Validators.required],
      cittaEsteraNascita: soggetto?.citta_estera_nascita || '',
      dataNascita: [
        soggetto?.data_nascita_soggetto?.split('T')[0] || '',
        {
          validators: [
            Validators.required,
            this.errorValidatorService.minimumAgeValidator(18),
          ],
        },
      ],
      statoResidenza: [null, Validators.required],
      // regioneResidenza: [null, Validators.required],
      provinciaResidenza: [null, Validators.required],
      comuneResidenza: [null, Validators.required],
      cittaEsteraResidenza: soggetto?.citta_estera_residenza || '',
      indirizzoResidenza: [
        soggetto?.indirizzo_soggetto || '',
        {
          validators: [Validators.required, Validators.maxLength(100)],
        },
      ],
      civicoResidenza: [
        soggetto?.num_civico_indirizzo || '',
        {
          validators: [Validators.required, Validators.maxLength(30)],
        },
      ],
      capResidenza: [
        soggetto?.cap_residenza || '',
        {
          validators: [Validators.required, Validators.maxLength(10)],
        },
      ],
      localitaResidenza: soggetto?.des_localita || '',
      email: [
        soggetto?.des_email || '',
        {
          validators: [
            Validators.required,
            Validators.pattern(RegexUtil.Email),
            Validators.maxLength(100),
          ],
        },
      ],
      pec: [
        soggetto?.des_pec || '',
        {
          validators: [
            Validators.pattern(RegexUtil.Email),
            Validators.maxLength(100),
          ],
        },
      ],
      telefono: [
        soggetto?.num_telefono || '',
        {
          validators: [
            Validators.required,
            Validators.pattern(RegexUtil.Phone),
            Validators.maxLength(25),
          ],
        },
      ],
      cellulare: [
        soggetto?.num_cellulare || '',
        {
          validators: [
            Validators.pattern(RegexUtil.Phone),
            Validators.maxLength(25),
          ],
        },
      ],
      flg_capogruppo: [null],
    });

    formGroup.addControl(
      'tipoSoggetto',
      new FormControl(
        {
          value:
            this.tipiSoggetto.find(
              (ts) => ts.cod_tipo_soggetto === tipoSoggettoCercato
            ) || '',
          disabled: true,
        },
        Validators.required
      )
    );

    if (isRichiedente) {
      if (ruoloSoggetto) {
        formGroup.addControl(
          'ruoloSoggetto',
          new FormControl(ruoloSoggetto, Validators.required)
        );
      } else if (this.ruoliSoggetto && this.ruoliSoggetto.length === 1) {
        formGroup.addControl(
          'ruoloSoggetto',
          new FormControl(this.ruoliSoggetto[0], Validators.required)
        );
      } else {
        formGroup.addControl(
          'ruoloSoggetto',
          new FormControl('', Validators.required)
        );
      }
    }

    //Verfico se il componente è in sola lettura
    if (this.onlyShow) {
      // Disabilito il form
      formGroup.disable({ emitEvent: false });
    }

    this.onChangeFormPersFis(formGroup, soggetto);

    return formGroup;
  }

  onChangeFormPersFis(form: FormGroup, soggetto: Partial<Soggetto>) {
    form
      .get('statoNascita')
      .valueChanges.pipe(filter((stato) => !!stato))
      .subscribe((stato: Nazione) => {
        form.get('provinciaNascita').reset();
        form.get('cittaEsteraNascita').reset();

        if (stato.cod_istat_nazione === '100') {
          form.get('cittaEsteraNascita').clearValidators();
          form.get('cittaEsteraNascita').updateValueAndValidity();

          form.get('provinciaNascita').setValidators(Validators.required);
          form.get('provinciaNascita').updateValueAndValidity();
          form.get('comuneNascita').setValidators(Validators.required);
          form.get('comuneNascita').updateValueAndValidity();
        } else {
          form.get('cittaEsteraNascita').setValidators(Validators.required);
          form.get('cittaEsteraNascita').updateValueAndValidity();

          form.get('provinciaNascita').clearValidators();
          form.get('provinciaNascita').updateValueAndValidity();
          form.get('comuneNascita').reset();
          form.get('comuneNascita').clearValidators();
          form.get('comuneNascita').updateValueAndValidity();
        }

        if (this.firstLoad_nascita && soggetto) {
          form
            .get('provinciaNascita')
            .setValue(soggetto.comune_nascita?.provincia || null);
          if (!form.get('provinciaNascita').value) {
            this.firstLoad_nascita = false;
          }
        }
      });

    form
      .get('provinciaNascita')
      .valueChanges.pipe(
        takeUntil(this.destroy$),
        filter((provincia) => !!provincia)
      )
      .subscribe((provincia: Provincia) => {
        form.get('comuneNascita').reset();
        this.locationService
          .getComuniByProvincia(provincia.cod_provincia)
          .subscribe(
            (res) => {
              this.comuniNascita = res;
              if (this.firstLoad_nascita) {
                form
                  .get('comuneNascita')
                  .setValue(soggetto?.comune_nascita || null);
                this.firstLoad_nascita = false;
              }
            },
            (err) => {
              if (err.error?.code) {
                this.ms.showMessage(
                  err.error.code,
                  'formInserisciSoggetto',
                  true
                );
              } else {
                this.ms.showMessage('E100', 'formInserisciSoggetto', true);
              }
            }
          );
      });

    form
      .get('statoResidenza')
      .valueChanges.pipe(filter((stato) => !!stato))
      .subscribe((stato: Nazione) => {
        form.get('provinciaResidenza').reset();
        form.get('cittaEsteraResidenza').reset();
        /* LF SCRIVA-1367
         * Modificata la logica di reset dei campi relativi all'indirizzo del soggetto.
         * Aggiunto controllo sul valore della property 'pristine' dell'AbstractControl che si sta modificando.
         * Nel caso sia 'true' non viene effettuato alcun reset perchè l'onChange è scatenato dal popolamento
         *  iniziale dei dati
         */
        const statoResidenzaControl = form.get('statoResidenza') as AbstractControl;
        this.resetDerivatiComuneSedeResidenza(statoResidenzaControl, form);

        if (stato.cod_istat_nazione === '100') {
          this.onStatoResidenzaItalia(form);
        } else {
          this.onStatoResidenzaAltraNazione(form);
        }

        if (this.firstLoad_residenza && soggetto) {
          form
            .get('provinciaResidenza')
            .setValue(soggetto.comune_residenza?.provincia || null);
          if (!form.get('provinciaResidenza').value) {
            this.firstLoad_residenza = false;
          }
        }
      });

    form
      .get('provinciaResidenza')
      .valueChanges.pipe(filter((provincia) => !!provincia))
      .subscribe((provincia: Provincia) => {
        form.get('comuneResidenza').reset();
        /* LF SCRIVA-1367
         * Modificata la logica di reset dei campi relativi all'indirizzo del soggetto.
         * Aggiunto controllo sul valore della property 'pristine' dell'AbstractControl che si sta modificando.
         * Nel caso sia 'true' non viene effettuato alcun reset perchè l'onChange è scatenato dal popolamento
         * iniziale dei dati
        */
        const provinciaResidenzaControl = form.get('provinciaResidenza') as AbstractControl;
        this.resetDerivatiComuneSedeResidenza(provinciaResidenzaControl, form);

        this.locationService
          .getComuniByProvincia(provincia.cod_provincia)
          .subscribe(
            (res) => {
              this.comuniResidenza = res.filter(
                (comune) => !comune.data_fine_validita
              );
              if (this.firstLoad_residenza) {
                form
                  .get('comuneResidenza')
                  .setValue(soggetto?.comune_residenza || null);
                this.firstLoad_residenza = false;
              }
            },
            (err) => {
              if (err.error?.code) {
                this.ms.showMessage(
                  err.error.code,
                  'formInserisciSoggetto',
                  true
                );
              } else {
                this.ms.showMessage('E100', 'formInserisciSoggetto', true);
              }
            }
          );
      });

    form.get('comuneResidenza').valueChanges.subscribe({
      next: (comune: Comune) => {
        /* LF SCRIVA-1367
         * Modificata la logica di reset dei campi relativi all'indirizzo del soggetto.
         * Aggiunto controllo sul valore della property 'pristine' dell'AbstractControl che si sta modificando.
         * Nel caso sia 'true' non viene effettuato alcun reset perchè l'onChange è scatenato dal popolamento
         * iniziale dei dati
         */
        const comuneResidenzaControl = form.get('comuneResidenza') as AbstractControl;
        this.resetDerivatiComuneSedeResidenza(comuneResidenzaControl, form);
      },
    });

    form
      .get('indirizzoResidenza')
      .valueChanges.pipe(filter((indirizzo) => !!indirizzo))
      .subscribe((indirizzo) => {
        if (this.searchResultsResidenza?.length > 0 && indirizzo.length > 4) {
          const location = this.searchResultsResidenza.find(
            (l) => l.display_name === indirizzo
          );
          if (location) {
            form.get('civicoResidenza').setValue(location.address.house_number);
            form.get('capResidenza').setValue(location.address.postcode);
            form.get('indirizzoResidenza').setValue(location.address.road);
          }
        }
      });

    // manual set to trigger valueChanges()
    form.get('statoNascita').setValue(soggetto?.nazione_nascita, { eventEmit: false });
    form.get('cittaEsteraNascita').setValue(soggetto?.citta_estera_nascita);
    this._popolaNascita(form, soggetto);

    form.get('statoResidenza').setValue(soggetto?.nazione_residenza);
    form.get('cittaEsteraResidenza').setValue(soggetto?.citta_estera_residenza);
    this._popolaResidenza(form, soggetto);

    if (!soggetto?.nazione_nascita) {
      const italia = this.nazioni.find(
        (nazione) => nazione.cod_istat_nazione === '100'
      );
      if (italia) {
        form.get('statoNascita').setValue(italia);
      }
    }

    if (!soggetto?.nazione_residenza) {
      const italia = this.nazioni.find(
        (nazione) => nazione.cod_istat_nazione === '100'
      );
      if (italia) {
        form.get('statoResidenza').setValue(italia);
      }
    }
  }

  /**
   * Popoliamo la parte di form con provincia e comune di residenza
   * in base a soggetto in ingresso
   * @param form FormGroup
   * @param soggetto Partial<Soggetto>
   */
  private _popolaResidenza(form: FormGroup, soggetto: Partial<Soggetto>) {
    console.log(soggetto);
    if (soggetto?.comune_residenza?.provincia?.cod_provincia) {
      form
        .get('provinciaResidenza')
        .setValue(soggetto.comune_residenza?.provincia || null);
      this.locationService
        .getComuniByProvincia(
          soggetto.comune_residenza?.provincia?.cod_provincia
        )
        .subscribe(
          (res) => {
            this.comuniResidenza = res;
            form
              .get('comuneResidenza')
              .setValue(soggetto?.comune_residenza || null);
          },
          (err) => {
            form.get('provinciaResidenza').enable();
            form.get('comuneResidenza').enable();
          }
        );
    }
  }

  /**
   * Popoliamo la parte di form con provincia e comune
   * in base a soggetto in ingresso
   * @param form FormGroup
   * @param soggetto Partial<Soggetto>
   */
  private _popolaNascita(form: FormGroup, soggetto: Partial<Soggetto>) {
    if (soggetto?.comune_nascita?.provincia?.cod_provincia) {
      form
        .get('provinciaNascita')
        .setValue(soggetto.comune_nascita?.provincia || null);
      this.locationService
        .getComuniByProvincia(soggetto.comune_nascita?.provincia?.cod_provincia)
        .subscribe(
          (res) => {
            this.comuniNascita = res;
            form
              .get('comuneNascita')
              .setValue(soggetto?.comune_nascita || null);
          },
          (err) => {
            form.get('provinciaNascita').enable();
            form.get('comuneNascita').enable();
          }
        );
    }
  }

  private _formPersGiur(
    soggetto: Partial<Soggetto>,
    cfCercato: string = null,
    tipoSoggettoCercato: string = null
  ) {
    const formGroup = this.fb.group({
      id_soggetto: soggetto?.id_soggetto,
      gestUID: soggetto?.gestUID,
      id_masterdata: soggetto?.id_masterdata,
      id_masterdata_origine: soggetto?.id_masterdata_origine,
      ragioneSociale: [
        soggetto?.den_soggetto || '',
        {
          validators: [Validators.required, Validators.maxLength(250)],
        },
      ],
      naturaGiuridica: [
        soggetto?.tipo_natura_giuridica || '',
        {
          validators: [Validators.required],
        },
      ],
      cf: [
        {
          value: cfCercato || soggetto?.cf_soggetto,
          disabled: true,
        },
        {
          validators: [
            Validators.required,
            Validators.pattern(RegexUtil.CF),
            Validators.maxLength(16),
          ],
        },
      ],
      pIva: [
        soggetto?.partita_iva_soggetto || '',
        {
          validators: [
            Validators.required,
            Validators.maxLength(11),
            // Validators.pattern(RegexUtil.PIVA),
            this.cfValidator,
          ],
        },
      ],
      tipoSoggetto: [
        {
          value:
            this.tipiSoggetto.find(
              (ts) => ts.cod_tipo_soggetto === tipoSoggettoCercato
            ) || '',
          disabled: true,
        },
        {
          validators: [Validators.required],
        },
      ],
      statoSedeLegale: [null, Validators.required],
      // regioneSedeLegale: [null, Validators.required],
      provinciaSedeLegale: [null, Validators.required],
      comuneSedeLegale: [null, Validators.required],
      cittaEsteraSedeLegale: soggetto?.citta_estera_sede_legale || '',
      indirizzoSedeLegale: [
        soggetto?.indirizzo_soggetto || '',
        {
          validators: [Validators.required, Validators.maxLength(100)],
        },
      ],
      civicoSedeLegale: [
        soggetto?.num_civico_indirizzo || '',
        {
          validators: [Validators.required, Validators.maxLength(30)],
        },
      ],
      capSedeLegale: [
        soggetto?.cap_sede_legale || '',
        {
          validators: [Validators.required, Validators.maxLength(10)],
        },
      ],
      localitaSedeLegale: soggetto?.des_localita || '',
      emailSedeLegale: [
        soggetto?.des_email || '',
        {
          validators: [
            Validators.required,
            Validators.pattern(RegexUtil.Email),
            Validators.maxLength(100),
          ],
        },
      ],
      pecSedeLegale: [
        soggetto?.des_pec || '',
        {
          validators: [
            Validators.required,
            Validators.pattern(RegexUtil.Email),
            Validators.maxLength(100),
          ],
        },
      ],
      telefonoSedeLegale: [
        soggetto?.num_telefono || '',
        {
          validators: [
            Validators.pattern(RegexUtil.Phone),
            Validators.maxLength(25),
          ],
        },
      ],
      cellulareSedeLegale: [
        soggetto?.num_cellulare || '',
        {
          validators: [
            Validators.pattern(RegexUtil.Phone),
            Validators.maxLength(25),
          ],
        },
      ],
      provinciaCciaa: [
        soggetto?.den_provincia_cciaa || '',
        {
          validators: [Validators.maxLength(20)],
        },
      ],
      annoCciaa: [
        soggetto?.den_anno_cciaa || '',
        {
          validators: [
            Validators.min(1700),
            Validators.max(new Date().getFullYear()),
          ],
        },
      ],
      numeroCciaa: [
        soggetto?.den_numero_cciaa || '',
        {
          validators: [Validators.maxLength(20)],
        },
      ],
      flg_capogruppo: [null],
    });

    //Verfico se il componente è in sola lettura
    if (this.onlyShow) {
      // Disabilito il form
      formGroup.disable({ emitEvent: false });
    }

    this.onChangeFormPersGiur(formGroup, soggetto);

    return formGroup;
  }
  
 /* LF SCRIVA-1367
  * Modificata la logica di reset dei campi relativi all'indirizzo del soggetto.
  * Aggiunto controllo sul valore della property 'pristine' dell'AbstractControl che si sta modificando.
  * Nel caso sia 'true' non viene effettuato alcun reset perchè l'onChange è scatenato dal popolamento
  *  iniziale dei dati
  */  
  private resetDerivatiComuneSedeLegale(changedControl: AbstractControl, form: FormGroup) {
    if (!changedControl.pristine) {
      form.get('localitaSedeLegale').reset();
      form.get('indirizzoSedeLegale').reset();
      form.get('civicoSedeLegale').reset();
      form.get('capSedeLegale').reset();
    }
  }

 /* LF SCRIVA-1367
  * Modificata la logica di reset dei campi relativi all'indirizzo del soggetto.
  * Aggiunto controllo sul valore della property 'pristine' dell'AbstractControl che si sta modificando.
  * Nel caso sia 'true' non viene effettuato alcun reset perchè l'onChange è scatenato dal popolamento
  *  iniziale dei dati
  */  
  private resetDerivatiComuneSedeResidenza(changedControl: AbstractControl, form: FormGroup) {
    if (!changedControl.pristine) {
      form.get('localitaResidenza').reset();
      form.get('indirizzoResidenza').reset();
      form.get('civicoResidenza').reset();
      form.get('capResidenza').reset();
    }
  }

  onChangeFormPersGiur(form: FormGroup, soggetto: Partial<Soggetto>) {
    form
      .get('statoSedeLegale')
      .valueChanges.pipe(filter((stato) => !!stato))
      .subscribe((stato: Nazione) => {
        this.onChangeStatoSedeLegale(form, stato);

        if (this.firstLoad_sedeLegale && soggetto) {
          form
            .get('provinciaSedeLegale')
            .setValue(soggetto.comune_sede_legale?.provincia || null);
          if (!form.get('provinciaSedeLegale').value) {
            this.firstLoad_sedeLegale = false;
          }
        }
      });

    form
      .get('provinciaSedeLegale')
      .valueChanges.pipe(filter((provincia) => !!provincia))
      .subscribe((provincia: Provincia) => {
        form.get('comuneSedeLegale').reset();
        /* LF SCRIVA-1367
         * Modificata la logica di reset dei campi relativi all'indirizzo del soggetto.
         * Aggiunto controllo sul valore della property 'pristine' dell'AbstractControl che si sta modificando.
         * Nel caso sia 'true' non viene effettuato alcun reset perchè l'onChange è scatenato dal popolamento
         * iniziale dei dati
         */
        const provinciaSedeLegaleControl = form.get('provinciaSedeLegale') as AbstractControl;
        this.resetDerivatiComuneSedeLegale(provinciaSedeLegaleControl, form);

        this.locationService
          .getComuniByProvincia(provincia.cod_provincia)
          .subscribe(
            (res) => {
              this.comuniSedeLegale = res.filter(
                (comune) => !comune.data_fine_validita
              );
              if (this.firstLoad_sedeLegale) {
                form
                  .get('comuneSedeLegale')
                  .setValue(soggetto?.comune_sede_legale || null);
                this.firstLoad_sedeLegale = false;
              }
            },
            (err) => {
              if (err.error?.code) {
                this.ms.showMessage(
                  err.error.code,
                  'formInserisciSoggetto',
                  true
                );
              } else {
                this.ms.showMessage('E100', 'formInserisciSoggetto', true);
              }
            }
          );
      });

    form.get('comuneSedeLegale').valueChanges.subscribe({
      next: (comune: Comune) => {
        /* LF SCRIVA-1367
         * Modificata la logica di reset dei campi relativi all'indirizzo del soggetto.
         * Aggiunto controllo sul valore della property 'pristine' dell'AbstractControl che si sta modificando.
         * Nel caso sia 'true' non viene effettuato alcun reset perchè l'onChange è scatenato dal popolamento
         * iniziale dei dati
         */
        const comuneSedeLegaleControl = form.get('comuneSedeLegale') as AbstractControl;
        this.resetDerivatiComuneSedeLegale(comuneSedeLegaleControl, form);},
    });

    form
      .get('indirizzoSedeLegale')
      .valueChanges.pipe(filter((indirizzo) => !!indirizzo))
      .subscribe((indirizzo) => {
        if (this.searchResultsSedeLegale?.length > 0 && indirizzo.length > 4) {
          const location = this.searchResultsSedeLegale.find(
            (l) => l.display_name === indirizzo
          );
          if (location) {
            form
              .get('civicoSedeLegale')
              .setValue(location.address.house_number);
            form.get('capSedeLegale').setValue(location.address.postcode);
            form.get('indirizzoSedeLegale').setValue(location.address.road);
          }
        }
      });

    // manual set to trigger valueChanges()
    form.get('statoSedeLegale').setValue(soggetto?.nazione_sede_legale);
    form
      .get('cittaEsteraSedeLegale')
      .setValue(soggetto?.citta_estera_sede_legale);

    if (!soggetto?.nazione_sede_legale) {
      const italia = this.nazioni.find(
        (nazione) => nazione.cod_istat_nazione === '100'
      );
      if (italia) {
        form.get('statoSedeLegale').setValue(italia);
      }
    }
  }

  private onChangeStatoSedeLegale(form: FormGroup, stato: Nazione) {
    console.log('soggetto is:', this.soggetto);
    
    form.get('provinciaSedeLegale').reset();
    form.get('cittaEsteraSedeLegale').reset();

    /* LF SCRIVA-1367
     * Modificata la logica di reset dei campi relativi all'indirizzo del soggetto.
     * Aggiunto controllo sul valore della property 'pristine' dell'AbstractControl che si sta modificando.
     * Nel caso sia 'true' non viene effettuato alcun reset perchè l'onChange è scatenato dal popolamento
     * iniziale dei dati
     */  
    const statoSedeLegaleControl: AbstractControl = form.get('statoSedeLegale') as AbstractControl;
    this.resetDerivatiComuneSedeLegale(statoSedeLegaleControl, form);
    if (stato.cod_istat_nazione === '100') {
      this.onStatoSedeLegaleItalia(form);
    } else {
      this.onStatoSedeLegaleAltraNazione(form);
    }
  }

  private onStatoSedeLegaleItalia(form: FormGroup) {
    form.get('cittaEsteraSedeLegale').clearValidators();
    form.get('cittaEsteraSedeLegale').updateValueAndValidity();
    form.get('provinciaSedeLegale').setValidators(Validators.required);
    form.get('provinciaSedeLegale').updateValueAndValidity();
    form.get('comuneSedeLegale').setValidators(Validators.required);
    form.get('comuneSedeLegale').updateValueAndValidity();
  }

  private onStatoSedeLegaleAltraNazione(form: FormGroup) {
    form.get('cittaEsteraSedeLegale').setValidators(Validators.required);
    form.get('cittaEsteraSedeLegale').updateValueAndValidity();
    form.get('provinciaSedeLegale').clearValidators();
    form.get('provinciaSedeLegale').updateValueAndValidity();
    form.get('comuneSedeLegale').reset();
    form.get('comuneSedeLegale').clearValidators();
    form.get('comuneSedeLegale').updateValueAndValidity();
  }

  private onStatoResidenzaItalia(form: FormGroup) {
    form.get('cittaEsteraResidenza').clearValidators();
    form.get('cittaEsteraResidenza').updateValueAndValidity();
    form.get('provinciaResidenza').setValidators(Validators.required);
    form.get('provinciaResidenza').updateValueAndValidity();
    form.get('comuneResidenza').setValidators(Validators.required);
    form.get('comuneResidenza').updateValueAndValidity();
  }

  private onStatoResidenzaAltraNazione(form: FormGroup) {
    form.get('cittaEsteraResidenza').setValidators(Validators.required);
    form.get('cittaEsteraResidenza').updateValueAndValidity();
    form.get('provinciaResidenza').clearValidators();
    form.get('provinciaResidenza').updateValueAndValidity();
    form.get('comuneResidenza').reset();
    form.get('comuneResidenza').clearValidators();
    form.get('comuneResidenza').updateValueAndValidity();
  }

  setCheckBoxesRecapitoAlternativo(
    formSoggetto: FormSoggettoPF | FormSoggettoPG
  ) {
    if (Object.keys(formSoggetto).length === 2) {
      const form: FormSoggettoPF = formSoggetto as FormSoggettoPF;

      if (form.anagraficaSoggetto.recapitoAlternativoResidenza) {
        const checkBox = document.getElementById(
          'cbRecapitoAltPF'
        ) as HTMLInputElement;
        checkBox.checked = true;
      }
    } else {
      const form: FormSoggettoPG = formSoggetto as FormSoggettoPG;

      if (form.anagraficaSoggetto.recapitoAlternativoSedeLegale) {
        const checkBox = document.getElementById(
          'cbRecapitoAltPG'
        ) as HTMLInputElement;
        checkBox.checked = true;
      }

      if (form.anagraficaRichiedente.recapitoAlternativoResidenza) {
        const checkBox = document.getElementById(
          'cbRecapitoAltPF'
        ) as HTMLInputElement;
        checkBox.checked = true;
      }
    }
  }

  /**
   * #####################################
   * FUNZIONI PER INSERIMENTO DEL SOGGETTO
   * #####################################
   */

  /**
   * Funzione principale per la gestione dell'inserimento del soggetto nel flusso di gestione dell'opera.
   */
  inserisciSoggetto() {
    // Lancio la funzione di "tutti i campi toccati" per lanciare le varie logiche di controllo e validazione
    this.formInserisciSoggetto.markAllAsTouched();

    // Verifico se il form è valido
    if (this.formInserisciSoggetto.valid) {
      // Il form del soggetto è valido, lancio le logiche di gestione
      this.richiediInserisciSoggetto();
      // #
    } else {
      if (this.formHasError(this.formInserisciSoggetto, 'required') > 0) {
        this.ms.showMessage('E001', 'formInserisciSoggetto', true);
      } else if (this.formHasError(this.formInserisciSoggetto, 'pattern') > 0) {
        this.ms.showMessage('E004', 'formInserisciSoggetto', true);
      }
    }
  }

  /**
   * Funzione che compone le informazioni per poi emettere la richiesta d'inserimento soggetto.
   */
  private richiediInserisciSoggetto() {
    // Imposto una variabile che conterrà il risultato di una funzione asincrona per la gestione delle casistiche d'emissione del soggetto
    let dataSubmit: ISoggettoFormSubmitData;

    // Verifico se il tipo persona cercato è: persona fisica
    if (this.ultimoTPCercatoPF) {
      // Definisco l'oggetto d'emissione gestendo la persona fisica
      dataSubmit = this.datiSubmitInserisciSoggettoPF();
      // #
    } else {
      // Definisco l'oggetto d'emissione gestendo la persona giuridica
      dataSubmit = this.datiSubmitInserisciSoggettoPG();
    }

    // Effettuo l'emit dei dati del soggetto
    this.confermaInserisciSoggetto$.emit(dataSubmit);
  }

  /**
   * Funzione che compone le informazioni per poi emettere la richiesta d'inserimento soggetto.
   * La funzione verificherà e gestirà i dati per il submit del form di una persona fisica.
   * @returns ISoggettoFormSubmitData con le informazioni da emettere.
   */
  private datiSubmitInserisciSoggettoPF(): ISoggettoFormSubmitData {
    // Definisco l'oggetto che conterrà tutte le informazioni per l'inserimento dati
    let submitForm: ISoggettoFormSubmitData = {
      soggetto: undefined,
      formRawValue: undefined,
      firmatario: undefined,
    };

    // Definisco le variabili per la gestione dell'oggetto di submit
    let formRawValue: any;

    // Recupero i dati dal form del soggetto
    const formSoggetto: FormSoggettoPF =
      this.formInserisciSoggetto.getRawValue();
    // Genero i dati per la persona fisica
    const datiPF = this._formToSoggettiPF(formSoggetto);
    // Estraggo le informazioni
    const { soggetto, firmatario } = datiPF || {};

    // L'oggetto è usato anche per formRawValue
    formRawValue = formSoggetto;

    // Le informazioni sono già pronte, modifico l'oggetto con i dati locali
    submitForm.soggetto = soggetto;
    submitForm.formRawValue = formRawValue;
    submitForm.firmatario = firmatario;

    // Ritorno l'oggetto per l'emit
    return submitForm;
  }

  /**
   * Funzione che compone le informazioni per poi emettere la richiesta d'inserimento soggetto.
   * La funzione verificherà e gestirà i dati per il submit del form di una persona giuridica.
   * @returns ISoggettoFormSubmitData con le informazioni da emettere.
   */
  private datiSubmitInserisciSoggettoPG(): ISoggettoFormSubmitData {
    // Definisco l'oggetto che conterrà tutte le informazioni per l'inserimento dati
    let submitForm: ISoggettoFormSubmitData = {
      soggetto: undefined,
      formRawValue: undefined,
      firmatario: undefined,
    };

    // Recupero i dati dal form del soggetto
    const formSoggetto: FormSoggettoPG =
      this.formInserisciSoggetto.getRawValue();

    // Genero i Soggetti tramite funzione persona giuridica
    const datiPG: IDatiSoggettoDaForm = this._formToSoggettiPG(formSoggetto);
    // Estraggo le informazioni
    const { soggetto, firmatario } = datiPG || {};

    // Assegno le informazioni per il submit
    submitForm.soggetto = soggetto;
    submitForm.formRawValue = formSoggetto;
    submitForm.firmatario = firmatario;

    // Ritorno l'oggetto per l'emit
    return submitForm;
  }

  /**
   * ###########################################################
   * FUNZIONE DI CONVERSIONE DA SOGGETTO A ANGRAFICA RICHIEDENTE
   * ###########################################################
   */

  /**
   * Funzione che gestisce un oggetto FormSoggettoPF | FormSoggettoPG impostando le informazioni per "anagraficaRichiedente", partendo dalle informazioni del soggetto in input.
   * @param form FormSoggettoPF | FormSoggettoPG con l'oggetto che definisce i dati del form.
   * @param soggetto Soggetto con le informazioni del soggetto da definire per l'anagrafica del richiedente.
   * @returns FormSoggettoPF | FormSoggettoPG con le informazioni aggiornate.
   */
  private soggettoToAnagraficaRichiedente(
    form: FormSoggettoPF | FormSoggettoPG,
    soggetto: Soggetto
  ): FormSoggettoPF | FormSoggettoPG {
    // Verifico l'input
    if (!form || !soggetto) {
      // Non ci sono le configurazioni
      return form;
    }

    // Definisco una variabile per la gestione del ruolo soggetto
    let ruoloSoggetto: RuoloSoggetto;
    // Verifico se esiste il ruolo compilante
    if (form.ruoloCompilante) {
      // Assegno i dati
      ruoloSoggetto = {
        id_ruolo_soggetto: form.ruoloCompilante.id_ruolo_compilante,
        cod_ruolo_soggetto: form.ruoloCompilante.cod_ruolo_compilante,
        des_ruolo_soggetto: form.ruoloCompilante.des_ruolo_compilante,
      };
    }

    // Definisco tutte le informazioni per l'anagrafica richiedente
    form.anagraficaRichiedente = {
      id_soggetto: soggetto.id_soggetto,
      gestUID: soggetto.gestUID,
      id_masterdata: soggetto.id_masterdata,
      id_masterdata_origine: soggetto.id_masterdata_origine,
      cf: soggetto.cf_soggetto,
      tipoSoggetto: soggetto.tipo_soggetto,
      cognome: soggetto.cognome,
      nome: soggetto.nome,
      statoNascita: soggetto.nazione_nascita,
      provinciaNascita:
        soggetto.comune_nascita?.provincia?.denom_provincia ?? '',
      comuneNascita: soggetto.comune_nascita,
      dataNascita: soggetto.data_nascita_soggetto,
      cittaEsteraNascita: soggetto.citta_estera_nascita,
      statoResidenza: soggetto.nazione_residenza,
      provinciaResidenza:
        soggetto.comune_residenza?.provincia?.denom_provincia ?? '',
      comuneResidenza: soggetto.comune_residenza,
      cittaEsteraResidenza: soggetto.citta_estera_residenza,
      indirizzoResidenza: soggetto.indirizzo_soggetto,
      civicoResidenza: soggetto.num_civico_indirizzo,
      capResidenza: soggetto.cap_residenza,
      localitaResidenza: soggetto.des_localita,
      email: soggetto.des_email,
      pec: soggetto.des_pec,
      telefono: soggetto.num_telefono,
      cellulare: soggetto.num_cellulare,
      ruoloSoggetto: ruoloSoggetto,
      recapitoAlternativoResidenza: undefined,
      flg_capogruppo: undefined,
    };

    // Ritorno l'oggetto del form aggiornato
    return form;
  }

  /**
   * #############################
   * #############################
   * #############################
   */

  // general button click event
  buttonClicked(i, buttonId: string) {
    if (i < 1 || buttonId === 'BTN_CLOSE') {
      this.activeModal.close({
        buttonIndex: i,
      });
    } else {
      this.formInserisciSoggetto.markAllAsTouched();
      if (this.formInserisciSoggetto.valid) {
        const formRawValue = this.formInserisciSoggetto.getRawValue();
        if (!formRawValue.ruoloCompilante) {
          formRawValue.ruoloCompilante =
            this.formSoggettoRow.record.ruoloCompilante;
        }
        if (this.ultimoTPCercatoPF) {
          // Genero i dati per la persona fisica
          const datiPF = this._formToSoggettiPF(formRawValue);
          // Estraggo le informazioni
          const { soggetto, firmatario } = datiPF || {};

          // Definisco l'oggetto per il ritorno della modale
          const returnData: ISoggettoFormSubmitData = {
            formRawValue,
            soggetto,
            firmatario,
            buttonIndex: i,
          };

          this.activeModal.close(returnData);
          // #
        } else {
          // Genero i Soggetti tramite funzione persona giuridica
          const datiPG: IDatiSoggettoDaForm =
            this._formToSoggettiPG(formRawValue);
          // Estraggo le informazioni
          const { soggetto, firmatario } = datiPG || {};

          // Definisco l'oggetto per il ritorno della modale
          const returnData: ISoggettoFormSubmitData = {
            formRawValue,
            soggetto,
            firmatario,
            buttonIndex: i,
          };

          this.activeModal.close(returnData);
          // #
        }
      } else {
        // Verifico qual è l'errore sul form
        const isRequired =
          this.formHasError(this.formInserisciSoggetto, 'required') > 0;
        const isErrorPattern =
          this.formHasError(this.formInserisciSoggetto, 'pattern') > 0;
        // Verifico se "required" perchè quest'errore ha la priorità a livello ordine di controlli
        if (isRequired) {
          this.ms.showMessage('E001', 'formInserisciSoggetto', true);
        } else if (isErrorPattern) {
          // L'errore per il pattern è il secondo per importanza
          this.ms.showMessage('E004', 'formInserisciSoggetto', true);
        } else {
          // Per tutte le altre casistiche visualizziamo l'errore di pattern perchè nessuno ha mai gestito tutte le altre possibili casistiche.
          // Per esclusione l'errore non può essere "required". Se capitasse, la gestione del "required" probabilmente è definita con un custom error e non con il required di Angular!
          this.ms.showMessage('E004', 'formInserisciSoggetto', true);
        }
      }
    }
  }

  annullaInserisciSoggetto() {
    // ask user then delete
    this.ms.showConfirmation({
      title: 'Attenzione',
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
            this.annullaInserisciSoggetto$.emit();
          },
        },
      ],
    });
  }

  /**
   * ########################################
   * SCARICO SOGGETTO RICHIEDENTE ADEMPIMENTO
   * ########################################
   */

  /**
   * Funzione di wrapper per le logiche di scarico del soggetto adempimento.
   * @param config IRecuperaRichiedente contenente le informazioni di configurazione per la get richiedente.
   * @returns Observable<Soggetto> con i dati scaricati.
   */
  getRichiedenteAdepimento(config: IRecuperaRichiedente): Observable<Soggetto> {
    // Estraggo dall'oggetto in input le informazioni
    const { cfSoggetto, cfImpresa, codAdempimento } = config || {};

    // Verifico qual è l'ultimo tipo di soggetto ricercato
    if (this.ultimoTPCercatoPF) {
      // Definisco manualmente il codice tipo soggetto
      const codTipoSoggetto = ScrivaTipiPersona.PF;
      // Richiamo la funzione per PF
      return this.ambitoService.getSoggettoAdempimento(
        cfSoggetto,
        codTipoSoggetto,
        codAdempimento
      );
      // #
    } else {
      // Richiamo la funzione per PG
      return this.ambitoService.getRichiedenteAdepimento(
        cfImpresa,
        cfSoggetto,
        codAdempimento
      );
    }
  }

  /**
   * ####################################################
   * FUNZIONI PER LA GESTIONE DEL CAMBIO RUOLO COMPILANTE
   * ####################################################
   */

  /**
   * Funzione di verifica che controlla lo stato del componente e, a seconda delle configurazioni, ritorna un booleano.
   * Il booleano permette l'accesso alla sezione dei dati del compilante.
   * @param config IDatiCompilanteAccessibili contenente le informazioni per il controllo delle casistiche.
   * @returns boolean con il risultato che definisce l'accesso ai dati.
   */
  private datiCompilanteAccessibili(
    config: IDatiCompilanteAccessibili
  ): boolean {
    // Verifico la configurazione
    if (!config) {
      // Configurazione assente
      return false;
    }

    // Estraggo dall'oggetto di configurazione i dati per la verifica
    const {
      ultimoTipoPersonaCercato,
      cfSoggettoInLinea,
      cfSoggettoForm,
      ruoloCompilante,
    } = config;

    // Verifico se il tipo dell'ultima persona cercato non è PF (persona fisica)
    if (ultimoTipoPersonaCercato !== ScrivaTipiPersona.PF) {
      // Accesso consentito
      return true;
    }

    // Verifico se il soggetto in linea (compilante) è lo stesso impostato come soggetto della form (tramite codice fiscale)
    if (cfSoggettoInLinea === cfSoggettoForm) {
      // Stesso soggetto, non faccio vedere i dati
      return false;
    }

    // Recupero il codice del compilante passato dalla configurazione
    const codRuoloComp = ruoloCompilante?.cod_ruolo_compilante;
    // Verifico quale tipo di ruolo del compilante è stato passato
    switch (codRuoloComp) {
      // 1) Configurazioni che permettono l'accesso
      case ScrivaCodiciRuoloCompilante.DCPF:
      case ScrivaCodiciRuoloCompilante.DSPF_FirmatarioAltroDelegato:
        return true;
      // 2) Configurazioni che NON permettono l'accesso
      case ScrivaCodiciRuoloCompilante.DSPF_FirmatarioLegaleRappresentanteProponente:
        return false;
      // Per default non è possibile l'accesso ai dati
      default:
        return false;
    }
  }

  /**
   * Funzione invocata al cambio di ruolo del soggetto compilante.
   * @param apiOnly boolean come flag per gestire lo spinner di caricamento dati qualora il cambio compilante sia gestito tramite richiesta al server.
   */
  onChangeRuoloCompilante(apiOnly = false) {
    // Resetto la possibile nota di ricerca richiedente
    this.notaInserimentoRichiedente = '';
    // Lancio la funzione di reset per l'anagrafica richiedente
    this.removeControlAnagraficaRichiedente();
    // Resetto lo stato di ricerca del richiedente
    this.statoRicercaRichiedente = StatiRicercaRichiedente.nonGestito;

    // Lancio la funzione di verifica e gestione del compilante
    this.verificaEGestisciDatiCompilante(apiOnly);
  }

  /**
   * Funzione che verifica e gestisce la modifica dei dati del ruolo compilante.
   * La verifica consiste nel controllare se l'utente può accedere ai dati del compilante.
   * @param apiOnly boolean come flag per gestire lo spinner di caricamento dati qualora il cambio compilante sia gestito tramite richiesta al server.
   */
  private verificaEGestisciDatiCompilante(apiOnly = false) {
    // Lancio le logiche per l'aggiornamento del form riguardo il ruolo del compilante
    let ruoloCompilante: RuoloCompilante = this.aggiornaFormRuoloCompilante();

    // Creo l'oggetto di configurazione per la verifica e accesso ai dati del compilante
    const config: IDatiCompilanteAccessibili = {
      ultimoTipoPersonaCercato: this.ultimoTipoPersonaCercato,
      cfSoggettoInLinea: this.compilante?.cf_compilante,
      cfSoggettoForm: this.cfAnagraficaSoggetto,
      ruoloCompilante,
    };

    // Verifico se il tipo soggetto dell'ultima ricerca è una persona fisica
    if (this.datiCompilanteAccessibili(config)) {
      // Lancio la funzione per l'aggiornamento delle struttura dati che coinvolgono il ruolo compilante
      this.aggiornaDatiRuoloCompilante(ruoloCompilante, apiOnly);
    }
  }

  /**
   * Funzione di comodo che gestisce le logiche di aggiornamento del form dati per il ruolo del compilante.
   * La funzione viene eseguita quando avviene un aggiornamento del "Ruolo compilante" del soggetto.
   */
  private aggiornaFormRuoloCompilante(): RuoloCompilante {
    // Definisco un contenitore per il compilante
    let ruoloCompilante: RuoloCompilante;

    // Verifico se all'interno del form è presente il form control del compilante
    if (this.ruoloCompilanteFC) {
      // Form control esistente, recupero il valore
      ruoloCompilante = this.ruoloCompilante;
      // #
    } else {
      // Non esiste il form control, recupero il ruolo dall'oggetto passato come riga di tabella dei soggetti
      ruoloCompilante = this.formSoggettoRow.record.ruoloCompilante;
    }

    // Verifico se esiste il ruolo compilante (quando il soggetto è lo stesso dell'attore in linea non esiste il ruolo)
    if (!ruoloCompilante) {
      // Non esiste la configurazione
      return;
    }

    // Assegno localmente l'id del ruolo compilante
    this.idRuoloCompilante = ruoloCompilante.id_ruolo_compilante;
    // Verifico se l'id del ruolo compilante è 1 (che cosa sarà? Bisogna chiedere ad un BEndista!)
    if (this.idRuoloCompilante !== 1) {
      // Recupero il form group innestato dell'anagrafica richiedente
      const anagraficaFormGroup = this.anagraficaRichiedenteFG as FormGroup;

      // Verifico se esiste il recapito alternativo per la residenza (recuperando il form control)
      if (anagraficaFormGroup?.get('recapitoAlternativoResidenza')) {
        // Esiste, vado a rimuoverlo come form control
        anagraficaFormGroup.removeControl('recapitoAlternativoResidenza');
        // Nascondo la sezione del recapito alternativo di residenza del richiedente
        this.showRecAltResidenzaRichiedente = false;
      }
    }

    // Ritorno il ruolo compilante
    return ruoloCompilante;
  }

  /**
   * Funzione che scarica le informazioni relative al ruolo compilante, aggiornando la pagina e le strutture dati in base a quanto ritornato.
   * @param ruoloCompilante RuoloCompilante con l'oggetto del ruolo compilante modificato.
   * @param apiOnly boolean come flag per gestire lo spinner di caricamento dati qualora il cambio compilante sia gestito tramite richiesta al server.
   */
  private aggiornaDatiRuoloCompilante(
    ruoloCompilante: RuoloCompilante,
    apiOnly = false
  ) {
    // Verifico l'input
    if (!ruoloCompilante) {
      // Nessuna configurazione
      return;
    }

    // Resetto il flag per la gestione dello stato ricerca richiedente
    this.statoRicercaRichiedente = StatiRicercaRichiedente.nonGestito;

    // Estraggo dall'oggetto del ruolo compilante le informazioni per le chiamate
    const idRC = ruoloCompilante.id_ruolo_compilante;
    const idA = this.adempimento.id_adempimento;
    // Definisco le richieste per i dati
    let rsReq: Observable<RuoloSoggetto[]> =
      this.ambitoService.getRuoliSoggettoFromRuoloCompilante(idRC, idA);
    let acReq: Observable<ConfigRuoloAdempimento> =
      this.ambitoService.getConfigurazioneRuoloCompilanteByAdempimento(
        idRC,
        idA
      );

    // Definisco l'oggetto di richiesta dati per il cambio del ruolo compilante
    const req: IChangeCompilanteDataReq = {
      ruoliSoggetto: rsReq,
      adempimentoConfig: acReq,
    };

    // Attivo lo spinner per la chiamata
    this.spinner.show();
    // Effettuo una richiesta multipla
    forkJoin(req)
      .pipe(
        takeUntil(this.destroy$),
        tap((result: IChangeCompilanteDataRes) => {
          // Recupero dalla response i ruoli soggetto
          this.ruoliSoggetto = result.ruoliSoggetto;
          // Recupero dalla response le configurazioni per gli adempimenti
          this.adempimentoConfig = result.adempimentoConfig;
          // #
        }),
        switchMap((result: IChangeCompilanteDataRes) => {
          // Se sono in modifica non devo fare aggiornamenti mi servono solo i dati api per il corretto funzionamento del componente
          if (apiOnly) {
            // Chiudo le logiche e ritorno true
            return of(true);
          }

          // Recupero le informazioni per verificare il flusso delle logiche
          const popolaRichiedente = this.popolaRichiedente;
          const notBOComponent = this.componente !== 'BO';
          // Verifico le condizioni
          if (popolaRichiedente && notBOComponent) {
            // Creo l'oggetto con le informazioni per lo scarico dati
            const config: IRecuperaRichiedente = {
              cfSoggetto: this.compilante.cf_compilante,
              cfImpresa: this.cfAnagraficaSoggetto,
              codAdempimento: this.adempimento.cod_adempimento,
            };
            // Lancio e ritorno la richiesta per il richiedente adempimento
            return this.aggiornaRCConRichiedenteAdempimento(config);
            // #
          } else {
            // Lancio la funzione di aggiornamento del form senza il passaggio dei dati del richiedente
            this.resetRichiedenteAdempimento();
            // Ritorno la risposta per completare il chiamante
            return of(true);
            // #
          }
        })
      )
      .subscribe(
        (result: boolean) => {
          // Gestione completata
          this.spinner.hide();
          // #
        },
        (error: any) => {
          // Recupero le informazioni per verificare il flusso d'errore
          const popolaRichiedente = this.popolaRichiedente;
          // Lancio la funzione specifica che gestisce le logiche dati per
          this.gestisciRichiedenteNonTrovato(error, popolaRichiedente, true);
          // Gestione completata
          this.spinner.hide();
          // #
        }
      );
  }

  /**
   * Funzione che gestisce il flusso di aggiornamento dati a seguito del cambio del ruolo compilante.
   * Una volta scaricati i dati, verranno aggiornate le strutture in pagina.
   * @param config IRecuperaRichiedente contenente le informazioni di configurazione per la get richiedente.
   * @returns Observable<boolean> con il risultato dell'operazione. Altrimenti verrà generata eccezione.
   */
  private aggiornaRCConRichiedenteAdempimento(
    config: IRecuperaRichiedente
  ): Observable<boolean> {
    // Resetto il possibile messaggio per "richiedente non trovato" di una ricerca precedente
    this.notaInserimentoRichiedente = '';

    // Richiamo la get per il richiedente adempimento
    return this.getRichiedenteAdepimento(config).pipe(
      tap((richiedenteAdempimento: Soggetto) => {
        // Definisco tutte la variabili necessarie alla gestione dell'aggiornamento del form dati
        let cfRic: string = '';
        const tipoRic: string = ScrivaTipiPersona.PF;
        let ric: Soggetto;
        const isRic: boolean = true;

        // Verifico se è stato ritornato un soggetto richiedente per l'adempimento
        if (richiedenteAdempimento) {
          // Il soggetto esiste, aggiorno le informazioni
          cfRic = richiedenteAdempimento.cf_soggetto;
          ric = richiedenteAdempimento;
          // Richiedente trovato, aggiorno lo stato
          this.statoRicercaRichiedente = StatiRicercaRichiedente.trovato;
          // #
        } else {
          // Emetto un messaggio per questa casistica
          this.ms.showMessage('I001', 'formInserisciSoggetto', true);

          // Richiedente trovato, aggiorno lo stato
          this.statoRicercaRichiedente = StatiRicercaRichiedente.daCompilante;

          // Soggetto non trovato, recupero i dati del compilante
          cfRic = this.compilante?.cf_compilante;
          ric = {
            cf_soggetto: this.compilante.cf_compilante,
            nome: this.compilante.nome_compilante,
            cognome: this.compilante.cognome_compilante,
            tipo_soggetto: undefined,
          };
        }

        // Genero un oggetto per aggiornare l'anagrafica del richiedente
        let anagRicFG: FormGroup;
        anagRicFG = this._buildFormSoggetto(cfRic, tipoRic, ric, isRic);
        // Aggiorno il form control del richiedente
        this.updateControlAnagraficaRichiedente(anagRicFG);

        // creo una copia del soggetto e la inserisco come snapshot nel servizio
        this.soggettoStoreService.richiedenteSnapshot = _.clone(
          richiedenteAdempimento
        );
        // #
      }),
      switchMap(() => of(true))
    );
  }

  /**
   * Funzione che gestisce il flusso logico di reset per i dati del richiedente adempimento.
   * La funzione, di base, andrà ad aggiornare le strutture dati del form del soggetto/anagrafica.
   */
  private resetRichiedenteAdempimento() {
    // Definisco tutte la variabili necessarie alla gestione dell'aggiornamento del form dati
    const cfRic: string = null;
    const tipoRic: string = ScrivaTipiPersona.PF;
    const ric: Soggetto = null;
    const isRic: boolean = true;

    // Genero un oggetto per aggiornare l'anagrafica del richiedente
    let anagRicFG: FormGroup;
    anagRicFG = this._buildFormSoggetto(cfRic, tipoRic, ric, isRic);
    // Aggiorno il form control del richiedente
    this.updateControlAnagraficaRichiedente(anagRicFG);
  }

  /**
   * ###############################
   * FUNZIONI DI RICERCA RICHIEDENTE
   * ###############################
   */

  /**
   * Funzione collegata al pulsante di ricerca dei dati di un richiedente.
   */
  onCercaRichiedente() {
    // Verifico che il campo del codice fiscale richiedente sia valido
    if (!this.cfAnagraficaRichiedenteFC?.valid) {
      // Il form control non è valido
      return;
    }

    // Resetto la possibile nota di ricerca richiedente
    this.notaInserimentoRichiedente = '';
    // Resetto il flag per la gestione dello stato ricerca richiedente
    this.statoRicercaRichiedente = StatiRicercaRichiedente.nonGestito;
    // Pulisco le informazioni dell'anagrafica
    this.pulisciAnagraficaRichidente();

    // Recupero la configurazione impostata per l'adempimento riguardante la possibilità di gestire i dati del richiedente
    const popolaRichiedente = this.popolaRichiedente;
    // Recupero dal form il codice fiscale per l'anagrafica richiedente
    const cfRichiedente = this.cfAnagraficaRichiedente;

    // Verifico se la configurazione non permetterebbe la gestione dei dati del richiedente
    if (!popolaRichiedente && cfRichiedente) {
      // Avvio lo spinner
      this.spinner.show();
      // Creo l'oggetto con le informazioni per lo scarico dati
      const config: IRecuperaRichiedente = {
        cfSoggetto: this.cfAnagraficaRichiedente,
        cfImpresa: this.cfAnagraficaSoggetto,
        codAdempimento: this.adempimento.cod_adempimento,
      };

      // Lancio la ricerca con gestione del dato
      this.cercaEGestisciRichiedente(config).subscribe({
        next: (existSoggetto: boolean) => {
          // Blocco lo spinner
          this.spinner.hide();
        },
        error: (error: any) => {
          // Lancio la funzione specifica che gestisce le logiche dati per
          this.gestisciRichiedenteNonTrovato(error, popolaRichiedente, false);
          // Gestione completata
          this.spinner.hide();
        },
      });
    }
  }

  /**
   * Funzione che verifica l'esistenza dell'anagrafica del richiedente e, ad eccezione per il codice fiscale, vengono puliti tutti i campi.
   */
  private pulisciAnagraficaRichidente() {
    // Recupero il cf della ricerca
    const cf = this.cfAnagraficaRichiedente;

    // Ho i dati minimi del compilante, imposto all'interno dell'anagrafica le informazioni
    const ts = ScrivaTipiPersona.PF;
    // Creo un oggetto parziale come Soggetto
    const soggetto: Partial<Soggetto> = { cf_soggetto: cf };

    // Genero l'oggetto FormGroup per gestire la struttura del form soggetto
    const fgAnagRic = this._buildFormSoggetto(cf, ts, soggetto, true);
    // Effettuo il set dati per l'anagrafica richiedente
    this.updateControlAnagraficaRichiedente(fgAnagRic);
  }

  /**
   * Funzione che effettua la ricerca e la gestione del dato di ritorno per il richiedente.
   * @param config IRecuperaRichiedente contenente le informazioni di configurazione per la get richiedente.
   */
  private cercaEGestisciRichiedente(
    config: IRecuperaRichiedente
  ): Observable<boolean> {
    // Resetto il possibile messaggio per "richiedente non trovato" di una ricerca precedente
    this.notaInserimentoRichiedente = '';

    // Richiamo il servizio per la ricerca del richiedente adempimento
    return this.getRichiedenteAdepimento(config).pipe(
      map((soggetto: Soggetto) => {
        // Verifico se è stato trovato un soggetto
        if (soggetto) {
          // Soggetto trovato, definisco i dati per aggiornare il form
          const cf = soggetto.cf_soggetto;
          const ts = ScrivaTipiPersona.PF;
          // Genero l'oggetto FormGroup per gestire la struttura del form soggetto
          const fgAnagRic = this._buildFormSoggetto(cf, ts, soggetto, true);
          // Effettuo il set dati per l'anagrafica richiedente
          this.updateControlAnagraficaRichiedente(fgAnagRic);
          // Soggetto trovato, modifico il flag
          this.statoRicercaRichiedente = StatiRicercaRichiedente.trovato;
          // #
        }

        // Aggiorno nel servizio del soggetto la snapshot per il richiedente
        this.soggettoStoreService.richiedenteSnapshot = _.clone(soggetto);

        // Ritorno il check di esistenza sul soggetto
        return soggetto != undefined;
        // #
      })
    );
  }

  /**
   * Funzioine che gestisce il flusso specifico quando la ricerca del richiedente NON trova il soggetto in anagrafica.
   * @param httpError any con l'oggetto generato dalla chiamata dall'http client di Angular.
   * @param popolaRichiedente boolean che definisce se il flusso di gestione è stato gestito con il flag del popola richiedente.
   */
  private gestisciRichiedenteNonTrovato(
    httpError: any,
    popolaRichiedente: boolean,
    autoPopola: boolean
  ) {
    // Per la ricerca richiedente in errore bisogna intercettare lo stato 404 (non si usa lo strettamente uguale perché lo status è stringa, quindi va bene così)
    const isError404 = httpError.status == 404;

    // Verifico se l'errore è 404 (soggetto non trovato)
    if (isError404) {
      // Gestisco la messaggistica
      this.segnalaRichiedenteNonTrovato(popolaRichiedente);
      // Imposto il valore che indica che il richiedente NON è stato trovato
      this.statoRicercaRichiedente = StatiRicercaRichiedente.nonTrovato;

      // Verifico se c'è da autopopolare i dati con il compilante
      if (autoPopola) {
        // Tento di recuperare i dati del soggetto in linea
        const {
          cf_compilante: cf,
          nome_compilante: nome,
          cognome_compilante: cognome,
        } = this.compilante;

        // Verifico che tutti i dati siano presenti
        if (cf && nome && cognome) {
          // Ho i dati minimi del compilante, imposto all'interno dell'anagrafica le informazioni
          const ts = ScrivaTipiPersona.PF;
          // Creo un oggetto parziale come Soggetto
          const soggetto: Partial<Soggetto> = {
            cf_soggetto: cf,
            nome: nome,
            cognome: cognome,
          };

          // Genero l'oggetto FormGroup per gestire la struttura del form soggetto
          const fgAnagRic = this._buildFormSoggetto(cf, ts, soggetto, true);
          // Effettuo il set dati per l'anagrafica richiedente
          this.updateControlAnagraficaRichiedente(fgAnagRic);

          // Imposto il valore che indica che il richiedente è stato impostato dai dati del compilante
          this.statoRicercaRichiedente =
            StatiRicercaRichiedente.nonTrovatoUsatoCompilante;
        }
      }
      // #
    } else {
      // Gestisco l'errore con la funzione di default
      this.onCercaSoggettiError(httpError);
    }
  }

  /**
   * #############################################################
   * FUNZIONI DI GESTIONE PER IL RECAPITO ALTERNATIVO DI RESIDENZA
   * #############################################################
   */

  onRecapitoAlternativoResidenza(event, fromAnagraficaSoggetto: boolean) {
    let anagraficaFormGroup: FormGroup;
    if (fromAnagraficaSoggetto) {
      anagraficaFormGroup = this.formInserisciSoggetto.get(
        'anagraficaSoggetto'
      ) as FormGroup;
    } else {
      anagraficaFormGroup = this.formInserisciSoggetto.get(
        'anagraficaRichiedente'
      ) as FormGroup;
    }

    if (event.target.checked) {
      const recAltResidenza = this.buildRecapitoAlternativoResidenza(null);
      anagraficaFormGroup.addControl(
        'recapitoAlternativoResidenza',
        recAltResidenza
      );
      this.onChangeRecAltResidenza(
        anagraficaFormGroup.get('recapitoAlternativoResidenza') as FormGroup,
        null
      );
      anagraficaFormGroup
        .get('recapitoAlternativoResidenza')
        .get('statoResidenzaAlt')
        .setValue(
          this.nazioni.find((nazione) => nazione.cod_istat_nazione === '100')
        );
      if (fromAnagraficaSoggetto) {
        this.showRecAltResidenzaSoggetto = true;
      } else {
        this.showRecAltResidenzaRichiedente = true;
      }
    } else {
      const form = anagraficaFormGroup.get(
        'recapitoAlternativoResidenza'
      ) as FormGroup;
      const isDataPresent = Object.values(form.getRawValue()).some(
        (value) => !!value
      );
      if (isDataPresent) {
        event.preventDefault();
        this.ms.showConfirmation({
          title: 'Attenzione',
          codMess: 'A027',
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
                anagraficaFormGroup.removeControl(
                  'recapitoAlternativoResidenza'
                );
                event.target.checked = false;
                if (fromAnagraficaSoggetto) {
                  this.showRecAltResidenzaSoggetto = false;
                } else {
                  this.showRecAltResidenzaRichiedente = false;
                }
              },
            },
          ],
        });
      } else {
        anagraficaFormGroup.removeControl('recapitoAlternativoResidenza');
        if (fromAnagraficaSoggetto) {
          this.showRecAltResidenzaSoggetto = false;
        } else {
          this.showRecAltResidenzaRichiedente = false;
        }
      }
    }
  }

  onRecapitoAlternativoSedeLegale(event) {
    const anagraficaFormGroup = this.formInserisciSoggetto.get(
      'anagraficaSoggetto'
    ) as FormGroup;

    if (event.target.checked) {
      const recAltSedeLegale = this.buildRecapitoAlternativoSedeLegale(null);
      anagraficaFormGroup.addControl(
        'recapitoAlternativoSedeLegale',
        recAltSedeLegale
      );
      this.onChangeRecAltSedeLegale(
        anagraficaFormGroup.get('recapitoAlternativoSedeLegale') as FormGroup,
        null
      );
      anagraficaFormGroup
        .get('recapitoAlternativoSedeLegale')
        .get('statoSedeLegaleAlt')
        .setValue(
          this.nazioni.find((nazione) => nazione.cod_istat_nazione === '100')
        );
      this.showRecAltSedeLegale = true;
    } else {
      const form = anagraficaFormGroup.get(
        'recapitoAlternativoSedeLegale'
      ) as FormGroup;
      const isDataPresent = Object.values(form.getRawValue()).some(
        (value) => !!value
      );
      if (isDataPresent) {
        event.preventDefault();
        this.ms.showConfirmation({
          title: 'Attenzione',
          codMess: 'A027',
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
                anagraficaFormGroup.removeControl(
                  'recapitoAlternativoSedeLegale'
                );
                event.target.checked = false;
                this.showRecAltSedeLegale = false;
              },
            },
          ],
        });
      } else {
        anagraficaFormGroup.removeControl('recapitoAlternativoSedeLegale');
        this.showRecAltSedeLegale = false;
      }
    }
  }

  buildRecapitoAlternativoResidenza(
    anagraficaSoggettoPF: AnagraficaSoggettoPF
  ) {
    const recAltResidenza = this.fb.group({
      idRecapitoAlternativo:
        anagraficaSoggettoPF?.recapitoAlternativoResidenza
          ?.idRecapitoAlternativo,
      gestUID: anagraficaSoggettoPF?.recapitoAlternativoResidenza?.gestUID,
      codRecapitoAlternativo:
        anagraficaSoggettoPF?.recapitoAlternativoResidenza
          ?.codRecapitoAlternativo,
      statoResidenzaAlt: null,
      // regioneResidenzaAlt: null,
      provinciaResidenzaAlt: null,
      comuneResidenzaAlt: null,
      cittaEsteraResidenzaAlt:
        anagraficaSoggettoPF?.recapitoAlternativoResidenza
          ?.cittaEsteraResidenzaAlt,
      indirizzoResidenzaAlt: [
        anagraficaSoggettoPF?.recapitoAlternativoResidenza
          ?.indirizzoResidenzaAlt,
        { validators: [Validators.maxLength(100)] },
      ],
      civicoResidenzaAlt: [
        anagraficaSoggettoPF?.recapitoAlternativoResidenza?.civicoResidenzaAlt,
        { validators: [Validators.maxLength(30)] },
      ],
      capResidenzaAlt: [
        anagraficaSoggettoPF?.recapitoAlternativoResidenza?.capResidenzaAlt,
        { validators: [Validators.maxLength(10)] },
      ],
      localitaResidenzaAlt:
        anagraficaSoggettoPF?.recapitoAlternativoResidenza
          ?.localitaResidenzaAlt,
      emailAlt: [
        anagraficaSoggettoPF?.recapitoAlternativoResidenza?.emailAlt,
        {
          validators: [
            Validators.pattern(RegexUtil.Email),
            Validators.maxLength(100),
          ],
        },
      ],
      pecAlt: [
        anagraficaSoggettoPF?.recapitoAlternativoResidenza?.pecAlt,
        {
          validators: [
            Validators.pattern(RegexUtil.Email),
            Validators.maxLength(100),
          ],
        },
      ],
      telefonoAlt: [
        anagraficaSoggettoPF?.recapitoAlternativoResidenza?.telefonoAlt,
        {
          validators: [
            Validators.pattern(RegexUtil.Phone),
            Validators.maxLength(25),
          ],
        },
      ],
      cellulareAlt: [
        anagraficaSoggettoPF?.recapitoAlternativoResidenza?.cellulareAlt,
        {
          validators: [
            Validators.pattern(RegexUtil.Phone),
            Validators.maxLength(25),
          ],
        },
      ],
    });

    return recAltResidenza;
  }

  onChangeRecAltResidenza(
    form: FormGroup,
    anagraficaSoggettoPF: AnagraficaSoggettoPF
  ) {
    form
      .get('statoResidenzaAlt')
      .valueChanges.pipe(
        takeUntil(this.destroy$),
        filter((stato) => !!stato)
      )
      .subscribe((stato: Nazione) => {
        form.get('provinciaResidenzaAlt').reset();

        if (stato.cod_istat_nazione === '100') {
          form.get('cittaEsteraResidenzaAlt').reset();
          form.get('cittaEsteraResidenzaAlt').clearValidators();
          form.get('cittaEsteraResidenzaAlt').updateValueAndValidity();

          form.get('provinciaResidenzaAlt').setValidators(Validators.required);
          form.get('provinciaResidenzaAlt').updateValueAndValidity();
          form.get('comuneResidenzaAlt').setValidators(Validators.required);
          form.get('comuneResidenzaAlt').updateValueAndValidity();
        } else {
          form
            .get('cittaEsteraResidenzaAlt')
            .setValidators(Validators.required);
          form.get('cittaEsteraResidenzaAlt').updateValueAndValidity();

          form.get('provinciaResidenzaAlt').clearValidators();
          form.get('provinciaResidenzaAlt').updateValueAndValidity();
          form.get('comuneResidenzaAlt').reset();
          form.get('comuneResidenzaAlt').clearValidators();
          form.get('comuneResidenzaAlt').updateValueAndValidity();
        }

        if (
          this.firstLoad_residenzaAlt &&
          anagraficaSoggettoPF?.recapitoAlternativoResidenza
        ) {
          form
            .get('provinciaResidenzaAlt')
            .setValue(
              anagraficaSoggettoPF.recapitoAlternativoResidenza
                .comuneResidenzaAlt?.provincia || null
            );
          if (!form.get('provinciaResidenzaAlt').value) {
            this.firstLoad_residenzaAlt = false;
          }
        }
      });

    form
      .get('provinciaResidenzaAlt')
      .valueChanges.pipe(
        takeUntil(this.destroy$),
        filter((provincia) => !!provincia)
      )
      .subscribe((provincia: Provincia) => {
        form.get('comuneResidenzaAlt').reset();
        this.locationService
          .getComuniByProvincia(provincia.cod_provincia)
          .subscribe(
            (res) => {
              this.comuniResidenzaRecAlt = res.filter(
                (comune) => !comune.data_fine_validita
              );
              if (this.firstLoad_residenzaAlt) {
                form
                  .get('comuneResidenzaAlt')
                  .setValue(
                    anagraficaSoggettoPF?.recapitoAlternativoResidenza
                      ?.comuneResidenzaAlt || null
                  );
                this.firstLoad_residenzaAlt = false;
              }
            },
            (err) => {
              if (err.error?.code) {
                this.ms.showMessage(
                  err.error.code,
                  'formInserisciSoggetto',
                  true
                );
              } else {
                this.ms.showMessage('E100', 'formInserisciSoggetto', true);
              }
            }
          );
      });

    form
      .get('indirizzoResidenzaAlt')
      .valueChanges.pipe(
        takeUntil(this.destroy$),
        filter((indirizzo) => !!indirizzo)
      )
      .subscribe((indirizzo) => {
        if (
          this.searchResultsResidenzaAlt?.length > 0 &&
          indirizzo.length > 4
        ) {
          const location = this.searchResultsResidenzaAlt.find(
            (l) => l.display_name === indirizzo
          );
          if (location) {
            form
              .get('civicoResidenzaAlt')
              .setValue(location.address.house_number);
            form.get('capResidenzaAlt').setValue(location.address.postcode);
            form.get('indirizzoResidenzaAlt').setValue(location.address.road);
          }
        }
      });
  }

  buildRecapitoAlternativoSedeLegale(
    anagraficaSoggettoPG: AnagraficaSoggettoPG
  ) {
    const recAltSedeLegale = this.fb.group({
      idRecapitoAlternativo:
        anagraficaSoggettoPG?.recapitoAlternativoSedeLegale
          ?.idRecapitoAlternativo,
      gestUID: anagraficaSoggettoPG?.recapitoAlternativoSedeLegale?.gestUID,
      codRecapitoAlternativo:
        anagraficaSoggettoPG?.recapitoAlternativoSedeLegale
          ?.codRecapitoAlternativo,
      statoSedeLegaleAlt: null,
      // regioneSedeLegaleAlt: null,
      provinciaSedeLegaleAlt: null,
      comuneSedeLegaleAlt: null,
      cittaEsteraSedeLegaleAlt:
        anagraficaSoggettoPG?.recapitoAlternativoSedeLegale
          ?.cittaEsteraSedeLegaleAlt,
      indirizzoSedeLegaleAlt: [
        anagraficaSoggettoPG?.recapitoAlternativoSedeLegale
          ?.indirizzoSedeLegaleAlt,
        { validators: [Validators.maxLength(100)] },
      ],
      civicoSedeLegaleAlt: [
        anagraficaSoggettoPG?.recapitoAlternativoSedeLegale
          ?.civicoSedeLegaleAlt,
        { validators: [Validators.maxLength(30)] },
      ],
      capSedeLegaleAlt: [
        anagraficaSoggettoPG?.recapitoAlternativoSedeLegale?.capSedeLegaleAlt,
        { validators: [Validators.maxLength(10)] },
      ],
      localitaSedeLegaleAlt:
        anagraficaSoggettoPG?.recapitoAlternativoSedeLegale
          ?.localitaSedeLegaleAlt,
      emailSedeLegaleAlt: [
        anagraficaSoggettoPG?.recapitoAlternativoSedeLegale?.emailSedeLegaleAlt,
        {
          validators: [
            Validators.pattern(RegexUtil.Email),
            Validators.maxLength(100),
          ],
        },
      ],
      pecSedeLegaleAlt: [
        anagraficaSoggettoPG?.recapitoAlternativoSedeLegale?.pecSedeLegaleAlt,
        {
          validators: [
            Validators.pattern(RegexUtil.Email),
            Validators.maxLength(100),
          ],
        },
      ],
      telefonoSedeLegaleAlt: [
        anagraficaSoggettoPG?.recapitoAlternativoSedeLegale
          ?.telefonoSedeLegaleAlt,
        {
          validators: [
            Validators.pattern(RegexUtil.Phone),
            Validators.maxLength(25),
          ],
        },
      ],
      cellulareSedeLegaleAlt: [
        anagraficaSoggettoPG?.recapitoAlternativoSedeLegale
          ?.cellulareSedeLegaleAlt,
        {
          validators: [
            Validators.pattern(RegexUtil.Phone),
            Validators.maxLength(25),
          ],
        },
      ],
    });

    return recAltSedeLegale;
  }

  onChangeRecAltSedeLegale(
    form: FormGroup,
    anagraficaSoggettoPG: AnagraficaSoggettoPG
  ) {
    form
      .get('statoSedeLegaleAlt')
      .valueChanges.pipe(
        takeUntil(this.destroy$),
        filter((stato) => !!stato)
      )
      .subscribe((stato: Nazione) => {
        form.get('provinciaSedeLegaleAlt').reset();

        if (stato.cod_istat_nazione === '100') {
          form.get('cittaEsteraSedeLegaleAlt').reset();
          form.get('cittaEsteraSedeLegaleAlt').clearValidators();
          form.get('cittaEsteraSedeLegaleAlt').updateValueAndValidity();

          form.get('provinciaSedeLegaleAlt').setValidators(Validators.required);
          form.get('provinciaSedeLegaleAlt').updateValueAndValidity();
          form.get('comuneSedeLegaleAlt').setValidators(Validators.required);
          form.get('comuneSedeLegaleAlt').updateValueAndValidity();
        } else {
          form
            .get('cittaEsteraSedeLegaleAlt')
            .setValidators(Validators.required);
          form.get('cittaEsteraSedeLegaleAlt').updateValueAndValidity();

          form.get('provinciaSedeLegaleAlt').clearValidators();
          form.get('provinciaSedeLegaleAlt').updateValueAndValidity();
          form.get('comuneSedeLegaleAlt').reset();
          form.get('comuneSedeLegaleAlt').clearValidators();
          form.get('comuneSedeLegaleAlt').updateValueAndValidity();
        }

        if (
          this.firstLoad_sedeLegaleAlt &&
          anagraficaSoggettoPG?.recapitoAlternativoSedeLegale
        ) {
          form
            .get('provinciaSedeLegaleAlt')
            .setValue(
              anagraficaSoggettoPG.recapitoAlternativoSedeLegale
                .comuneSedeLegaleAlt?.provincia || null
            );
          if (!form.get('provinciaSedeLegaleAlt').value) {
            this.firstLoad_sedeLegaleAlt = false;
          }
        }
      });

    form
      .get('provinciaSedeLegaleAlt')
      .valueChanges.pipe(
        takeUntil(this.destroy$),
        filter((provincia) => !!provincia)
      )
      .subscribe((provincia: Provincia) => {
        form.get('comuneSedeLegaleAlt').reset();
        this.locationService
          .getComuniByProvincia(provincia.cod_provincia)
          .subscribe(
            (res) => {
              this.comuniSedeLegaleRecAlt = res.filter(
                (comune) => !comune.data_fine_validita
              );
              if (this.firstLoad_sedeLegaleAlt) {
                form
                  .get('comuneSedeLegaleAlt')
                  .setValue(
                    anagraficaSoggettoPG?.recapitoAlternativoSedeLegale
                      ?.comuneSedeLegaleAlt || null
                  );
                this.firstLoad_sedeLegaleAlt = false;
              }
            },
            (err) => {
              if (err.error?.code) {
                this.ms.showMessage(
                  err.error.code,
                  'formInserisciSoggetto',
                  true
                );
              } else {
                this.ms.showMessage('E100', 'formInserisciSoggetto', true);
              }
            }
          );
      });

    form
      .get('indirizzoSedeLegaleAlt')
      .valueChanges.pipe(
        takeUntil(this.destroy$),
        filter((indirizzo) => !!indirizzo)
      )
      .subscribe((indirizzo) => {
        if (
          this.searchResultsSedeLegaleAlt?.length > 0 &&
          indirizzo.length > 4
        ) {
          const location = this.searchResultsSedeLegaleAlt.find(
            (l) => l.display_name === indirizzo
          );
          if (location) {
            form
              .get('civicoSedeLegaleAlt')
              .setValue(location.address.house_number);
            form.get('capSedeLegaleAlt').setValue(location.address.postcode);
            form.get('indirizzoSedeLegaleAlt').setValue(location.address.road);
          }
        }
      });
  }

  filterTipiNaturaGiuridica(): TipoNaturaGiuridica[] {
    let filteredList: TipoNaturaGiuridica[] = [];
    const tipoSoggetto: TipoSoggetto = this.formInserisciSoggetto
      .get('anagraficaSoggetto')
      .get('tipoSoggetto').value;

    // SCRIVA-1202 => Togliere logica di filtro flg_pubblico
    // if (tipoSoggetto.cod_tipo_soggetto === this.tipologiaPersona.PG) {
    //   filteredList = this.tipiNaturaGiuridica.filter(
    //     (tipo) => tipo.flg_pubblico === false
    //   );
    // }
    // if (tipoSoggetto.cod_tipo_soggetto === this.tipologiaPersona.PB) {
    //   filteredList = this.tipiNaturaGiuridica.filter(
    //     (tipo) => tipo.flg_pubblico === true
    //   );
    // }

    // Creo una copia dell'array senza filtri
    filteredList = [...this.tipiNaturaGiuridica];

    return filteredList;
  }

  getAddressResidenza(street: string) {
    let city;
    const anagraficaFormGroup =
      (this.formInserisciSoggetto.get('anagraficaRichiedente') as FormGroup) ||
      (this.anagraficaSoggettoFG as FormGroup);
    const country = anagraficaFormGroup
      .get('statoResidenza')
      .value.denom_nazione.toLowerCase();
    if (country === 'italia') {
      city = anagraficaFormGroup.get('comuneResidenza').value.denom_comune;
    } else {
      city = anagraficaFormGroup.get('cittaEsteraResidenza').value;
    }
    return this.nominatimService.searchAddress(country, city, street);
  }

  getAddressResidenzaAlt(street: string) {
    let city;
    const anagraficaFormGroup =
      (this.formInserisciSoggetto.get('anagraficaRichiedente') as FormGroup) ||
      (this.anagraficaSoggettoFG as FormGroup);
    const country = anagraficaFormGroup
      .get('recapitoAlternativoResidenza')
      .get('statoResidenzaAlt')
      .value.denom_nazione.toLowerCase();
    if (country === 'italia') {
      city = anagraficaFormGroup
        .get('recapitoAlternativoResidenza')
        .get('comuneResidenzaAlt').value.denom_comune;
    } else {
      city = anagraficaFormGroup
        .get('recapitoAlternativoResidenza')
        .get('cittaEsteraResidenzaAlt').value;
    }
    return this.nominatimService.searchAddress(country, city, street);
  }

  getAddressSedeLegale(street: string) {
    let city;
    const anagraficaFormGroup = this.formInserisciSoggetto.get(
      'anagraficaSoggetto'
    ) as FormGroup;
    const country = anagraficaFormGroup
      .get('statoSedeLegale')
      .value.denom_nazione.toLowerCase();
    if (country === 'italia') {
      city = anagraficaFormGroup.get('comuneSedeLegale').value.denom_comune;
    } else {
      city = anagraficaFormGroup.get('cittaEsteraSedeLegale').value;
    }
    return this.nominatimService.searchAddress(country, city, street);
  }

  getAddressSedeLegaleAlt(street: string) {
    let city;
    const anagraficaFormGroup = this.formInserisciSoggetto.get(
      'anagraficaSoggetto'
    ) as FormGroup;
    const country = anagraficaFormGroup
      .get('recapitoAlternativoSedeLegale')
      .get('statoSedeLegaleAlt')
      .value.denom_nazione.toLowerCase();
    if (country === 'italia') {
      city = anagraficaFormGroup
        .get('recapitoAlternativoSedeLegale')
        .get('comuneSedeLegaleAlt').value.denom_comune;
    } else {
      city = anagraficaFormGroup
        .get('recapitoAlternativoSedeLegale')
        .get('cittaEsteraSedeLegaleAlt').value;
    }
    return this.nominatimService.searchAddress(country, city, street);
  }

  // Utility per cercare errori all'interno dei controlli della form
  formHasError(form: FormGroup | AbstractControl, errorName: string) {
    let result = 0;
    if (!form.valid) {
      if (
        form?.errors &&
        Object.keys(form?.errors).find((item) => item === errorName)
      ) {
        result++;
      } else {
        if (form instanceof FormGroup) {
          Object.keys(form?.controls).forEach((key) => {
            result += this.formHasError(form?.controls[key], errorName);
          });
        }
      }
    }
    return result;
  }

  cfValidator(control: AbstractControl): ValidationErrors | null {
    let failedValidation = null;

    if (control.value) {
      failedValidation = ControlloCf.validateCF(control.value);
    }

    if (failedValidation) {
      if (control.errors) {
        control.errors['_controlloCf'] = true;
      } else {
        control.setErrors({ _controlloCf: true });
      }
      return { _controlloCf: true };
    } else {
      return null;
    }
  }

  compareNazione(n1: Nazione, n2: Nazione) {
    return n1 && n2 && n1.id_nazione === n2.id_nazione;
  }

  compareRegione(r1: Regione, r2: Regione) {
    return r1 && r2 && r1.id_regione === r2.id_regione;
  }

  compareProvincia(p1: Provincia, p2: Provincia) {
    return p1 && p2 && p1.id_provincia === p2.id_provincia;
  }

  compareComune(c1: Comune, c2: Comune) {
    return c1 && c2 && c1.id_comune === c2.id_comune;
  }

  compareRuoloCompilante(rc1: RuoloCompilante, rc2: RuoloCompilante) {
    return rc1 && rc2 && rc1.cod_ruolo_compilante === rc2.cod_ruolo_compilante;
  }

  compareTipoSoggetto(ts1: TipoSoggetto, ts2: TipoSoggetto) {
    return ts1 && ts2 && ts1.cod_tipo_soggetto === ts2.cod_tipo_soggetto;
  }

  compareRuoloSoggetto(rs1: RuoloSoggetto, rs2: RuoloSoggetto) {
    return rs1 && rs2 && rs1.cod_ruolo_soggetto === rs2.cod_ruolo_soggetto;
  }

  compareTipoNaturaGiuridica(
    tng1: TipoNaturaGiuridica,
    tng2: TipoNaturaGiuridica
  ) {
    return (
      tng1 &&
      tng2 &&
      tng1.cod_tipo_natura_giuridica === tng2.cod_tipo_natura_giuridica
    );
  }

  /**
   * ############################################
   * FUNZIONI CHE GESTISCONO GLI HELPER IN PAGINA
   * ############################################
   */

  /**
   * Funzione collegata al click sull'helper collegato a: ruolo compilante.
   * Verrà aperto l'helper con descrizione contestuale alla sezione a cui è collegato.
   */
  helperRuoloCompilanteClick() {
    // Definisco la chiave specifica per l'helper
    const chiave = 'ruolo_ricoperto';
    // Richiamo la funzione per generare la chiave completa
    const chiaveCompleta = this.generaChiaveHelper(undefined, chiave);
    // Richiamo l'apertura dell'helper
    this.apriHelper(chiaveCompleta);
  }

  /**
   * ################################################
   * FUNZIONI PER GENERARE SOGGETTI DAI DATI DEL FORM
   * ################################################
   */

  /**
   * Funzione di conversione dai dati del form soggetto come PF ad un oggetto Soggetto.
   * @param formSoggetto FormSoggettoPF da convertire.
   * @returns Soggetto convertito.
   */
  private formPFToSoggetto(formSoggetto: FormSoggettoPF): Soggetto {
    // La conversione/assegnazione manuale era già così, la mantengo
    return {
      id_soggetto: formSoggetto?.anagraficaSoggetto?.id_soggetto,
      gestUID: formSoggetto?.anagraficaSoggetto?.gestUID,
      id_masterdata: formSoggetto?.anagraficaSoggetto?.id_masterdata,
      id_masterdata_origine:
        formSoggetto?.anagraficaSoggetto?.id_masterdata_origine,
      cognome: formSoggetto?.anagraficaSoggetto?.cognome,
      nome: formSoggetto?.anagraficaSoggetto?.nome,
      nazione_nascita: formSoggetto?.anagraficaSoggetto?.statoNascita,
      nazione_residenza: formSoggetto?.anagraficaSoggetto?.statoResidenza,
      comune_nascita: formSoggetto?.anagraficaSoggetto?.comuneNascita,
      comune_residenza: formSoggetto?.anagraficaSoggetto?.comuneResidenza,
      citta_estera_nascita:
        formSoggetto?.anagraficaSoggetto?.cittaEsteraNascita,
      citta_estera_residenza:
        formSoggetto?.anagraficaSoggetto?.cittaEsteraResidenza,
      data_nascita_soggetto:
        formSoggetto?.anagraficaSoggetto?.dataNascita?.split('T')[0] ?? '',
      tipo_soggetto: formSoggetto?.anagraficaSoggetto?.tipoSoggetto,
      cf_soggetto: formSoggetto?.anagraficaSoggetto?.cf,
      num_telefono: formSoggetto?.anagraficaSoggetto?.telefono,
      num_cellulare: formSoggetto?.anagraficaSoggetto?.cellulare,
      des_email: formSoggetto?.anagraficaSoggetto?.email,
      des_pec: formSoggetto?.anagraficaSoggetto?.pec,
      indirizzo_soggetto: formSoggetto?.anagraficaSoggetto?.indirizzoResidenza,
      num_civico_indirizzo: formSoggetto?.anagraficaSoggetto?.civicoResidenza,
      cap_residenza: formSoggetto?.anagraficaSoggetto?.capResidenza,
      des_localita: formSoggetto?.anagraficaSoggetto?.localitaResidenza,
    };
  }

  /**
   * Funzione di conversione dai dati del form soggetto come PG ad un oggetto Soggetto.
   * @param formSoggetto FormSoggettoPG da convertire.
   * @returns Soggetto convertito.
   */
  private formPGToSoggetto(formSoggetto: FormSoggettoPG): Soggetto {
    // La conversione/assegnazione manuale era già così, la mantengo
    return {
      id_soggetto: formSoggetto?.anagraficaSoggetto?.id_soggetto,
      gestUID: formSoggetto?.anagraficaSoggetto?.gestUID,
      id_masterdata: formSoggetto?.anagraficaSoggetto?.id_masterdata,
      id_masterdata_origine:
        formSoggetto?.anagraficaSoggetto?.id_masterdata_origine,
      tipo_natura_giuridica: formSoggetto?.anagraficaSoggetto?.naturaGiuridica,
      den_soggetto: formSoggetto?.anagraficaSoggetto?.ragioneSociale,
      den_provincia_cciaa: formSoggetto?.anagraficaSoggetto?.provinciaCciaa,
      den_anno_cciaa: formSoggetto?.anagraficaSoggetto?.annoCciaa,
      den_numero_cciaa: formSoggetto?.anagraficaSoggetto?.numeroCciaa,
      comune_sede_legale: formSoggetto?.anagraficaSoggetto?.comuneSedeLegale,
      nazione_sede_legale: formSoggetto?.anagraficaSoggetto?.statoSedeLegale,
      citta_estera_sede_legale:
        formSoggetto?.anagraficaSoggetto?.cittaEsteraSedeLegale,
      partita_iva_soggetto: formSoggetto?.anagraficaSoggetto?.pIva,
      tipo_soggetto: formSoggetto?.anagraficaSoggetto?.tipoSoggetto,
      cf_soggetto: formSoggetto?.anagraficaSoggetto?.cf,
      num_telefono: formSoggetto?.anagraficaSoggetto?.telefonoSedeLegale,
      num_cellulare: formSoggetto?.anagraficaSoggetto?.cellulareSedeLegale,
      des_email: formSoggetto?.anagraficaSoggetto?.emailSedeLegale,
      des_pec: formSoggetto?.anagraficaSoggetto?.pecSedeLegale,
      indirizzo_soggetto: formSoggetto?.anagraficaSoggetto?.indirizzoSedeLegale,
      num_civico_indirizzo: formSoggetto?.anagraficaSoggetto?.civicoSedeLegale,
      cap_sede_legale: formSoggetto?.anagraficaSoggetto?.capSedeLegale,
      des_localita: formSoggetto?.anagraficaSoggetto?.localitaSedeLegale,
    };
  }

  /**
   * Funzione di conversione dai dati del form soggetto come PG ad un oggetto Soggetto.
   * @param formSoggetto FormSoggettoPG da convertire.
   * @returns Soggetto convertito.
   */
  private formSoggettoToFirmatario(
    formSoggetto: FormSoggettoPF | FormSoggettoPG
  ): Soggetto {
    // Per il firmatario la sua anagrafica è facoltativa, verifico esista
    if (!formSoggetto?.anagraficaRichiedente) {
      // Non esiste l'oggetto, ritorno undefined
      return undefined;
    }

    // La conversione/assegnazione manuale era già così, la mantengo
    return {
      id_soggetto: formSoggetto.anagraficaRichiedente.id_soggetto,
      gestUID: formSoggetto.anagraficaRichiedente.gestUID,
      id_masterdata: formSoggetto.anagraficaRichiedente.id_masterdata,
      id_masterdata_origine:
        formSoggetto.anagraficaRichiedente.id_masterdata_origine,
      cognome: formSoggetto.anagraficaRichiedente.cognome,
      nome: formSoggetto.anagraficaRichiedente.nome,
      nazione_nascita: formSoggetto.anagraficaRichiedente.statoNascita,
      nazione_residenza: formSoggetto.anagraficaRichiedente.statoResidenza,
      comune_nascita: formSoggetto.anagraficaRichiedente.comuneNascita,
      comune_residenza: formSoggetto.anagraficaRichiedente.comuneResidenza,
      citta_estera_nascita:
        formSoggetto.anagraficaRichiedente.cittaEsteraNascita,
      citta_estera_residenza:
        formSoggetto.anagraficaRichiedente.cittaEsteraResidenza,
      data_nascita_soggetto:
        formSoggetto.anagraficaRichiedente?.dataNascita?.split('T')[0] ?? '',
      tipo_soggetto: formSoggetto.anagraficaRichiedente.tipoSoggetto,
      cf_soggetto: formSoggetto.anagraficaRichiedente.cf,
      num_telefono: formSoggetto.anagraficaRichiedente.telefono,
      num_cellulare: formSoggetto.anagraficaRichiedente.cellulare,
      des_email: formSoggetto.anagraficaRichiedente.email,
      des_pec: formSoggetto.anagraficaRichiedente.pec,
      indirizzo_soggetto: formSoggetto.anagraficaRichiedente.indirizzoResidenza,
      num_civico_indirizzo: formSoggetto.anagraficaRichiedente.civicoResidenza,
      cap_residenza: formSoggetto.anagraficaRichiedente.capResidenza,
      des_localita: formSoggetto.anagraficaRichiedente.localitaResidenza,
    };
  }

  /**
   * Funzione di conversione dati per il form soggetto PF a dati soggetto.
   * @param formSoggetto FormSoggettoPF per la generazione dei dati.
   * @returns IDatiSoggettoDaForm con il risultato della conversione.
   */
  private _formToSoggettiPF(formSoggetto: FormSoggettoPF): IDatiSoggettoDaForm {
    // Lancio la funzione per generare i dati del soggetto
    const soggetto: Soggetto = this.formPFToSoggetto(formSoggetto);
    // Lancio la funzione per generare i dati del firmatario
    const firmatario: Soggetto = this.formSoggettoToFirmatario(formSoggetto);

    // Ritorno l'inseieme delle informazioni
    return { soggetto, firmatario };
  }

  /**
   * Funzione di conversione dati per il form soggetto PG a dati soggetto.
   * @param formSoggetto FormSoggettoPF per la generazione dei dati.
   * @returns IDatiSoggettoDaForm con il risultato della conversione.
   */
  private _formToSoggettiPG(formSoggetto: FormSoggettoPG): IDatiSoggettoDaForm {
    // Lancio la funzione per generare i dati del soggetto
    const soggetto: Soggetto = this.formPGToSoggetto(formSoggetto);
    // Lancio la funzione per generare i dati del firmatario
    const firmatario: Soggetto = this.formSoggettoToFirmatario(formSoggetto);

    return { soggetto, firmatario };
  }

  /**
   * ###################
   * FUNZIONI DI UTILITY
   * ###################
   */

  /**
   * Funzione di comodo per gestire le segnalazioni d'errore generate dai servizi.
   * @param code string che definisce il codice del messaggio da visualizzare.
   * @param elementId string con l'id del DOM dell'elemento a cui "attaccare" il messaggio.
   * @param autoFade boolean come flag per la gestione della scomparsa automatica.
   */
  private onServiziError(code: string, elementId: string, autoFade: boolean) {
    // Mostro il messaggio con il codice passato in input
    this.ms.showMessage(code, elementId, autoFade);
  }

  /**
   * Funzione di comodo per gestire le segnalazioni d'errore generate dai servizi.
   * Questa funzione è dedicata ai servizi di ricerca soggetti.
   * @param httpError any con l'oggetto generato dalla chiamata dall'http client di Angular.
   * @param autoFade boolean come flag per la gestione della scomparsa automatica.
   */
  private onCercaSoggettiError(httpError: any, autoFade?: boolean) {
    // Definisco l'id del DOM per la form cerca soggetti
    const idDOMCercaSoggetti = 'formCercaSoggetti';

    // Verifico se l'oggetto di errore ha il codice
    if (httpError.error?.code) {
      // Gestisco il flag per autoFade
      autoFade = autoFade ?? false;
      // Mostro il messaggio con il codice passato in input
      this.onServiziError(httpError.error.code, idDOMCercaSoggetti, autoFade);
      // #
    } else {
      // Gestisco il flag per autoFade
      autoFade = autoFade ?? true;
      // Mostro un errore generico
      this.onServiziError('E100', idDOMCercaSoggetti, autoFade);
      // #
    }
    // #
  }

  /**
   * Funzione di comodo che gestisce le logiche di comunicazione all'utente per un compilante non trovato.
   * @param popolaRichiedente boolean che definisce se il flusso di gestione è stato gestito con il flag del popola richiedente.
   */
  private segnalaRichiedenteNonTrovato(popolaRichiedente: boolean) {
    // Definisco le informazioni per la gestione specifica dell'errore
    const code = 'I001';
    const idDOM = 'containerCercaRichiedente';
    const autoFade404 = true;
    // Richiamo la funzione di gestione per gli errori
    this.onServiziError(code, idDOM, autoFade404);

    // Verifico il flag sul popola richiedente
    if (popolaRichiedente) {
      // Messaggio con popola richiedente
      this.notaInserimentoRichiedente =
        'Alcuni dati obbligatori del dichiarante non sono presenti a sistema. Si prega di completarli.';
    } else {
      // Messaggio senza popola richiedente
      this.notaInserimentoRichiedente =
        "Il dichiarante non è stato trovato: puoi procedere con l'inserimento oppure modifica i criteri di ricerca.";
    }
  }

  /**
   * Funzione di comodo che genera la chiave per l'apertura degli helper.
   * @param preChiave string con la parte di chiave da inserire in "testata".
   * @param postChiave string con la parte di chiave da inserire in "coda".
   * @returns string con la chiave per l'helper combinata secondo gli input.
   */
  private generaChiaveHelper(preChiave?: string, postChiave?: string): string {
    // Definisco le parti per creare la chiave dell'helper
    const componente = this.componente ?? 'COMPONENTE_NON_DEFINITO';
    const codTipoAdempimento =
      this.tipoAdempimento?.cod_tipo_adempimento ??
      'COD_TIPO_ADEMPIMENTO_NON_DEFINITO';
    const codAdempimento =
      this.adempimento?.cod_adempimento ?? 'COD_ADEMPIMENTO_NON_TROVATO';
    const codQuadro = this.codQuadro ?? 'COD_QUADRO_NON_TROVATO';

    // Definisco la chiave "base"
    let chiave = `${componente}.${codTipoAdempimento}.${codAdempimento}.${codQuadro}`;

    // Verifico se c'è da inserire un parte prima della chiave
    if (preChiave) {
      // Esiste una "testata", concatena la chiave
      chiave = `${preChiave}.${chiave}`;
      // #
    }
    // Verifico se c'è da inserire un parte dopo la chiave
    if (postChiave) {
      // Esiste una "testata", concatena la chiave
      chiave = `${chiave}.${postChiave}`;
      // #
    }

    // Ritorno la chiave generata
    return chiave;
  }

  /**
   * Funzione che gestisce l'apertura di un helper data una chiave.
   * @param chiaveHelp string che definisce la chiave da utilizzare per lo scarico della descrizione da visualizzare.
   */
  apriHelper(chiaveHelp: string) {
    // Richiamo il servizio per i dati dell'helper
    this.helpService.getHelpByChiave(chiaveHelp).subscribe({
      next: (helps: Help[]) => {
        // Recupero dal primo oggetto della lista la descrizione, gestendo un default in caso non siano stati trovati i dati
        const modalContent = helps[0]?.des_testo_help || 'Help non trovato...';
        // Apro la modale con cui visualizzerò i dati
        this.ms.showConfirmation({
          title: 'HELP',
          codMess: null,
          content: modalContent,
          buttons: [],
        });
        // #
      },
      error: (err) => {
        // Definisco le informazioni per gestire l'errore
        let code = 'E100';
        const idDOM = 'searchForm';
        let autoFade = true;

        // Verifico se esiste un codice specifico da far visualizzare per l'errore
        if (err.error?.code) {
          // C'è una configurazione specifica, aggiorno i dati per il messaggio
          code = err.error.code;
          autoFade = false;
          // #
        }

        // Richiamo la funzione per visualizzare l'errore in pagina
        this.onServiziError(code, idDOM, autoFade);
        // Scrivo un log per la gestione dell'errore in console
        console.log('# Error retrieving help array ', chiaveHelp);
      },
    });
  }

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  /**
   * Getter di comodo che ritorna il check sul tipo di componente applicativo.
   * @returns boolean con il risultato del check.
   */
  get isComponenteBO(): boolean {
    // Verifico se il componente in pagina è "BO"
    return this.componente === ScrivaComponenteApp.backOffice;
  }

  /**
   * Getter di comodo che ritorna il check sul tipo di componente applicativo.
   * @returns boolean con il risultato del check.
   */
  get isComponenteFO(): boolean {
    // Verifico se il componente in pagina è "FO"
    return this.componente === ScrivaComponenteApp.frontOffice;
  }

  /**
   * Getter di comodo che verifica l'ultimo tipo persona cercato.
   * La condizione è se l'ultimo tipo cercato è: PF.
   * @returns boolean con il risultato del check.
   */
  get ultimoTPCercatoPF(): boolean {
    // Verifico e ritorno il risultato del check
    return this.ultimoTipoPersonaCercato === ScrivaTipiPersona.PF;
  }

  /**
   * Getter di comodo per il recupero dei dati dal form in pagina.
   * @returns AbstractControl con le informazioni del form.
   */
  get anagraficaSoggettoFG(): AbstractControl {
    // Definisco la chiave di accesso all'elemento del form
    const KEY_AS: string = 'anagraficaSoggetto';
    // Recupero dal form i dati
    return this.formInserisciSoggetto?.get(KEY_AS);
  }

  /**
   * Getter di comodo per il recupero dei dati dal form in pagina.
   * @returns string con le informazioni del form.
   */
  get cfAnagraficaSoggetto(): string {
    // Definisco la chiave di accesso all'elemento del form
    const KEY_CF: string = 'cf';
    // Recupero dal form i dati
    return this.anagraficaSoggettoFG?.get(KEY_CF)?.value;
  }

  /**
   * Getter di comodo per il recupero dei dati dal form in pagina.
   * @returns AbstractControl con le informazioni del form.
   */
  get anagraficaRichiedenteFG(): AbstractControl {
    // Definisco la chiave di accesso all'elemento del form
    const KEY_AR: string = 'anagraficaRichiedente';
    // Recupero dal form i dati
    return this.formInserisciSoggetto?.get(KEY_AR);
  }

  /**
   * Getter di comodo per il recupero del form control dell'anagrafica richiedente.
   * @returns AbstractControl con il form control.
   */
  get cfAnagraficaRichiedenteFC(): AbstractControl {
    // Definisco la chiave di accesso all'elemento del form
    const KEY_CF: string = 'cf';
    // Recupero dal form i dati
    return this.anagraficaRichiedenteFG?.get(KEY_CF);
  }

  /**
   * Getter di comodo per il recupero dei dati dal form in pagina.
   * @returns string con le informazioni del form.
   */
  get cfAnagraficaRichiedente(): string {
    // Recupero dal form i dati
    return this.cfAnagraficaRichiedenteFC?.value;
  }

  /**
   * Getter di comodo per il recupero dei dati dal form in pagina.
   * @returns AbstractControl con le informazioni del form.
   */
  get ruoloCompilanteFC(): AbstractControl {
    // Definisco la chiave di accesso all'elemento del form
    const KEY_RC: string = 'ruoloCompilante';
    // Recupero dal form i dati
    return this.formInserisciSoggetto?.get(KEY_RC);
  }

  /**
   * Getter di comodo per il recupero dei dati dal form in pagina.
   * @returns RuoloCompilante con le informazioni del form.
   */
  get ruoloCompilante(): RuoloCompilante {
    // Recupero dal form i dati
    return this.ruoloCompilanteFC?.value;
  }

  /**
   * Setter di comodo per il recupero dei dati dal form in pagina.
   * @param ruoloCompilante RuoloCompilante con le informazioni del form.
   */
  set ruoloCompilante(ruoloCompilante: RuoloCompilante) {
    // Imposto i dati nel form
    this.ruoloCompilanteFC?.setValue(ruoloCompilante);
  }

  /**
   * Setter di comodo per la gestione del form control, innestato dentro il form group del soggetto.
   * @param fgConfig FormGroup con le informazioni per il set del dato.
   */
  setControlAnagraficaRichiedente(fgConfig: FormGroup) {
    // Definisco la chiave di accesso all'elemento del form
    const KEY_AR: string = 'anagraficaRichiedente';
    // Recupero dal form i dati
    this.formInserisciSoggetto?.setControl(KEY_AR, fgConfig);
  }

  /**
   * Adder di comodo per la gestione del form control, innestato dentro il form group del soggetto.
   * @param fgConfig FormGroup con le informazioni per il set del dato.
   */
  addControlAnagraficaRichiedente(fgConfig: FormGroup) {
    // Definisco la chiave di accesso all'elemento del form
    const KEY_AR: string = 'anagraficaRichiedente';
    // Recupero dal form i dati
    this.formInserisciSoggetto?.addControl(KEY_AR, fgConfig);
  }

  /**
   * Remover di comodo per la gestione del form control, innestato dentro il form group del soggetto.
   */
  removeControlAnagraficaRichiedente() {
    // Definisco la chiave di accesso all'elemento del form
    const KEY_AR: string = 'anagraficaRichiedente';
    // Recupero dal form i dati
    this.formInserisciSoggetto?.removeControl(KEY_AR);
  }

  /**
   * Setter di comodo per la gestione del form control, innestato dentro il form group del soggetto.
   * La funzione verificherà se all'interno del form del soggetto esiste il FormGroup e, a seconda della condizione, setterà o aggiungerà la nuova configurazione.
   * @param fgConfig FormGroup con le informazioni per il set del dato.
   */
  updateControlAnagraficaRichiedente(fgConfig: FormGroup) {
    // Tento di recuperare il form group per l'anagrafica richiedente dal form del soggetto
    let fgAnagRichidente: AbstractControl;
    fgAnagRichidente = this.anagraficaRichiedenteFG;
    // Verifico se il form group esiste all'interno del form del soggetto
    if (fgAnagRichidente) {
      // Il form group già esiste, faccio un set del tipo di form control
      this.setControlAnagraficaRichiedente(fgConfig);
      // #
    } else {
      // Il form group non esiste nel form group del soggetto, lo aggiungo
      this.addControlAnagraficaRichiedente(fgConfig);
      // #
    }
  }

  /**
   * Setter di comodo per la gestione del form control, innestato dentro il form group del soggetto.
   * @param fcConfig AbstractControl con le informazioni per il set del dato.
   */
  setControlRuoloCompilante(fcConfig: AbstractControl) {
    // Definisco la chiave di accesso all'elemento del form
    const KEY_RC: string = 'ruoloCompilante';
    // Recupero dal form i dati
    this.formInserisciSoggetto?.setControl(KEY_RC, fcConfig);
  }

  /**
   * Adder di comodo per la gestione del form control, innestato dentro il form group del soggetto.
   * @param fcConfig AbstractControl con le informazioni per il set del dato.
   */
  addControlRuoloCompilante(fcConfig: AbstractControl) {
    // Definisco la chiave di accesso all'elemento del form
    const KEY_RC: string = 'ruoloCompilante';
    // Recupero dal form i dati
    this.formInserisciSoggetto?.addControl(KEY_RC, fcConfig);
  }

  /**
   * Remover di comodo per la gestione del form control, innestato dentro il form group del soggetto.
   */
  removeControlRuoloCompilante() {
    // Definisco la chiave di accesso all'elemento del form
    const KEY_RC: string = 'ruoloCompilante';
    // Recupero dal form i dati
    this.formInserisciSoggetto?.removeControl(KEY_RC);
  }

  /**
   * Setter di comodo per la gestione del dato per il form control, innestato dentro il form group del soggetto.
   * La funzione verificherà se all'interno del form del soggetto esiste il FormControl e, a seconda della condizione, setterà una nuova configurazione.
   * @param ruoloCompilante RuoloCompilante con le informazioni per il set del dato.
   * @param cfSoggetto string che definisce il codice fiscale del soggetto selezionato.
   */
  updateRuoloCompilante(ruoloCompilante: RuoloCompilante, cfSoggetto?: string) {
    // Tento di recuperare il form group per l'anagrafica richiedente dal form del soggetto
    let fcRuoloCompilante: AbstractControl;
    fcRuoloCompilante = this.ruoloCompilanteFC;

    // Verifico se il form group esiste all'interno del form del soggetto
    if (fcRuoloCompilante) {
      // Il form group già esiste, assegno l'oggetto al form control
      this.ruoloCompilante = ruoloCompilante;
      // Lancio l'aggiornamento delle logiche per i campi
      fcRuoloCompilante.updateValueAndValidity();
      // #
    } else {
      // Gestisco il validatore di obbligatorietà in base al soggetto
      let validators: ValidatorFn[] = [];
      // Gestisco il codice fiscale del soggetto
      const cf = cfSoggetto ?? this.ultimoCFCercato;
      const cfAttoreInLinea = this.compilante?.cf_compilante;
      // Verifico se l'attore in linea non è lo stesso del soggetto selezionato
      if (cfAttoreInLinea != cf) {
        // Essendo soggetti diversi il ruolo compilante è obbligatorio
        validators = [Validators.required];
        // #
      }

      // Definisco un form control e aggiungo il dato
      let fcConfig: FormControl;
      fcConfig = new FormControl(ruoloCompilante, validators);
      // Il form group non esiste nel form group del soggetto, lo aggiungo
      this.addControlRuoloCompilante(fcConfig);
      // #
    }
  }

  /**
   * Getter che ritorna il valore del flag: flg_popola_richiedente; partendo dall'oggetto di configurazione: adempimentoConfig
   * @returns boolean con il valore del flag.
   */
  get popolaRichiedente(): boolean {
    // Tento di ritornare il flag, se non esiste ritorno false
    return this.adempimentoConfig?.flg_popola_richiedente ?? false;
  }

  /**
   * Getter di comodo che verifica lo stato di ricerca per il richiedente.
   * @returns boolean con il risultato del check.
   */
  get isRichiedenteNonGestito(): boolean {
    // Verifico lo stato e ritorno il check
    return this.statoRicercaRichiedente == StatiRicercaRichiedente.nonGestito;
  }

  /**
   * Getter di comodo che verifica lo stato di ricerca per il richiedente.
   * @returns boolean con il risultato del check.
   */
  get isRichiedenteTrovato(): boolean {
    // Verifico lo stato e ritorno il check
    return this.statoRicercaRichiedente == StatiRicercaRichiedente.trovato;
  }

  /**
   * Getter di comodo che verifica lo stato di ricerca per il richiedente.
   * @returns boolean con il risultato del check.
   */
  get isRichiedenteNonTrovato(): boolean {
    // Verifico lo stato e ritorno il check
    return this.statoRicercaRichiedente == StatiRicercaRichiedente.nonTrovato;
  }

  /**
   * Getter di comodo che verifica lo stato di ricerca per il richiedente.
   * @returns boolean con il risultato del check.
   */
  get isRichiedenteNonTrovatoUsatoCompilante(): boolean {
    // Verifico lo stato e ritorno il check
    return (
      this.statoRicercaRichiedente ==
      StatiRicercaRichiedente.nonTrovatoUsatoCompilante
    );
  }

  /**
   * Getter di comodo che verifica lo stato di ricerca per il richiedente.
   * @returns boolean con il risultato del check.
   */
  get isRichiedenteDaCompilante(): boolean {
    // Verifico lo stato e ritorno il check
    return this.statoRicercaRichiedente == StatiRicercaRichiedente.daCompilante;
  }
}
