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
  Inject,
  OnDestroy,
  OnInit,
  TemplateRef,
  ViewChild,
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { cloneDeep } from 'lodash';
import { NgxSpinnerService } from 'ngx-spinner';
import { Observable } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { AmbitoService } from 'src/app/features/ambito/services';
import { CONFIG } from 'src/app/shared/config.injectiontoken';
import {
  Istanza,
  Quadro,
  StepConfig,
  TemplateQuadro,
} from 'src/app/shared/models';
import {
  AdempimentoService,
  AuthStoreService,
  ConfigurazioniScrivaService,
  HelpService,
  IstanzaService,
  LocationService,
  MessageService,
  StepManagerService,
} from 'src/app/shared/services';
import { ScrivaServerError } from '../../../../../core/classes/scriva.classes';
import { ScrivaCodesMesseges } from '../../../../../core/enums/scriva-codes-messages.enums';
import { IAlertConfig } from '../../../../../shared/interfaces/scriva-utilities.interfaces';
import { FormioService } from '../../../../../shared/services/formio/formio.service';
import { GeecoService } from '../../../../../shared/services/geeco/geeco.service';
import {
  IGeecoOpen,
  IGeecoQuitUrlParams,
  IGeoGeeco,
  IGeoGeecoConfigs,
} from '../../../../../shared/services/geeco/utilities/geeco.interfaces';
import { LoggerService } from '../../../../../shared/services/logger.service';
import {
  IFormOggettiSearchData,
  OggettiSearchFormData,
} from '../../../components/oggetti-search/utilities/oggetti-search.interfaces';
import {
  ICriteriRicercaQdrOggetto,
  OggettoIstanza,
  OggettoIstanzaGeometrieFE,
  OggettoIstanzaLike,
  Opera,
  SoggettoIstanza,
} from '../../../models';
import { OpereService } from '../../../services/opere/opere.service';
import { UsiUloDerivazioniVerificheStepService } from '../../../services/opere/usi-ulo-derivazioni/usi-ulo-derivazioni-verifiche-step.service';
import { UsiUloDerivazioniService } from '../../../services/opere/usi-ulo-derivazioni/usi-ulo-derivazioni.service';
import { IConfigsElementCfAzienda } from '../../../services/opere/usi-ulo-derivazioni/utilities/usi-ulo-derivazioni.interfaces';
import {
  IDTOperaModalCallbacks,
  IDTOperaModalParams,
  IDTOperaSalvataggio,
} from '../opere/modals/dati-tecnici-opera-modal/utilities/dati-tecnici-opera-modal.interfaces';
import { OpereComponent } from '../opere/opere.component';
import { IndLivelliOpere } from '../opere/utilities/opere.enums';
import { IListeDatiTecniciOggettiIstanze } from '../opere/utilities/opere.interfaces';
import { UsiUloDerivazioniModalComponent } from './modals/usi-ulo-derivazioni-modal/usi-ulo-derivazioni-modal.component';
import { UsiUloDerivazioniConsts } from './utilities/usi-ulo-derivazioni.consts';
import { generaDenominazioneAzienda } from './utilities/usi-ulo-derivazioni.functions';
import { ConfigElementUsiUloDER } from './utilities/usi-ulo-derivazioni.interfaces';
import { PresentazioneIstanzaService } from '../../../services/presentazione-istanza/presentazione-istanza.service';

@Component({
  selector: 'app-usi-ulo-derivazioni',
  templateUrl: './usi-ulo-derivazioni.component.html',
  styleUrls: ['../opere/opere.component.scss'],
  providers: [UsiUloDerivazioniVerificheStepService, UsiUloDerivazioniService],
})
export class UsiUloDerivazioniComponent
  extends OpereComponent
  implements OnInit, OnDestroy, AfterViewInit
{
  /** UsiUloDerivazioniConsts con le informazioni costanti per il componente. */
  public OPERE_CONSTS = new UsiUloDerivazioniConsts();

  /** string costante che definisce il contenitore per agganciare i messaggi di segnalazione. */
  readonly ALERT_TARGET_MODALE: string = 'usi-ulo-derivazioni-content';

  /**
   * string che definisce il nome del componente di riferimento.
   * @override
   */
  protected componentName: string = 'UsiUloDerivazioniComponent';

  /**
   * any per gestire il componente da usare per la modale di dettaglio delle opere.
   * @override
   */
  protected componenteModale: any = UsiUloDerivazioniModalComponent;

  /**
   * OggettoIstanzaGeometrieFE[] con la lista degli oggetti istanza scaricati per il quadro.
   * @override
   */
  protected _oggettiIstanza: OggettoIstanzaGeometrieFE[] = [];

  /** ViewChild con il riferimento al template html per: indirizzo ubicazione. */
  @ViewChild('indirizzoUbicazioneTemplate')
  indirizzoUbicazioneTemplate: TemplateRef<any>;

  /** ViewChild con il riferimento al template html per: codice fiscale azienda. */
  @ViewChild('codiceFiscaleAziendaTemplate')
  codiceFiscaleAziendaTemplate: TemplateRef<any>;
  /** ViewChild con il riferimento al template html per: denominazione azienda. */
  @ViewChild('denominazioneAziendaTemplate')
  denominazioneAziendaTemplate: TemplateRef<any>;

  /** Observable<SoggettoIstanza[]> come funzione per la definizione delle informazioni dei soggetti. */
  setSoggetti$: Observable<SoggettoIstanza[]>;
  /** (soggetto?: SoggettoIstanza) => string come funzione per la definizione della descrizione all'interno della select dei soggetti. */
  descrizioneSoggetti: (soggetto?: SoggettoIstanza) => string;
  /** (soggetto?: any) => number come funzione per il recupero dell'id soggetto gestito dalla select. */
  getIdSoggetto: (soggetto: any) => number;

  /** SoggettoIstanza che l'utente ha selezionato all'interno del form di ricerca. */
  soggettoIstanzaRicerca: SoggettoIstanza;
  /** ConfigElementUsiUloDER con le informazioni relative all'input del codice fiscale azienda. Questa variabile rappresenta la configurazione scaricata dal DB. */
  cfAziendaConfig: ConfigElementUsiUloDER;
  /** ConfigElementUsiUloDER con le informazioni relative all'input della descrizione azienda. Questa variabile è generata partendo da: cfAziendale. */
  denAziendaConfig: ConfigElementUsiUloDER;

  /**
   * Costruttore.
   */
  constructor(
    private _usiUloDer: UsiUloDerivazioniService,
    @Inject(CONFIG) protected injConfig: StepConfig,
    ambito: AmbitoService,
    adempimento: AdempimentoService,
    auth: AuthStoreService,
    configurazioni: ConfigurazioniScrivaService,
    formio: FormioService,
    geeco: GeecoService,
    help: HelpService,
    istanza: IstanzaService,
    location: LocationService,
    logger: LoggerService,
    message: MessageService,
    modal: NgbModal,
    opere: OpereService,
    opereVerificheStep: UsiUloDerivazioniVerificheStepService,
    route: ActivatedRoute,
    stepManager: StepManagerService,
    spinner: NgxSpinnerService,
    presentazioneIstanza:PresentazioneIstanzaService
  ) {
    // Richiamo il super passando tutti i servizi
    super(
      injConfig,
      ambito,
      adempimento,
      configurazioni,
      formio,
      geeco,
      location,
      logger,
      modal,
      opere,
      opereVerificheStep,
      route,
      presentazioneIstanza,
      message,
      help,
      istanza,
      auth,
      stepManager,
      spinner
    );
  }

  /**
   * ngOnInit.
   */
  ngOnInit() {
    // Richiamo la funzione del super
    super.ngOnInit();

    // Richiamo la funzione per la gestione dei soggetti
    this.initGestioneSoggetti();
  }

  /**
   * ngAfterViewInit.
   */
  ngAfterViewInit() {
    // Richiamo la funzione del super
    super.ngAfterViewInit();
  }

  /**
   * ngOnDestroy.
   */
  ngOnDestroy() {
    // Richiamo il destroy della classe padre
    super.ngOnDestroy();
  }

  /**
   * ################
   * FUNZIONI DI INIT
   * ################
   */

  /**
   * Funzione di init che definisce le logiche di gestione per il popolamento della select dei soggetti e per l'output da visualizzare dentro la select.
   */
  private initGestioneSoggetti() {
    // Definisco la funzione per lo scarico dei soggetti
    this.setSoggetti$ = this.scaricaSoggettiUsi();

    // Definisco la funzione per lo scarico dei soggetti
    this.descrizioneSoggetti = (soggetto: SoggettoIstanza) => {
      // Definisco il contenitore per la descrizione da visualizzare
      let desSoggetto: string = '';

      // Estraggo le informazioni di cui ho bisogno per la generazione della descrizione
      const cf: string = soggetto?.cf_soggetto ?? '';
      // Genero la descrizione azienda
      const desAziendale: string = this.generaDenAzienda(soggetto);
      // Compongo le informazioni
      desSoggetto = `${cf} (${desAziendale})`;

      // Ritorno la descrizione composta
      return desSoggetto;
      // #
    };

    // Definisco la funzione per il recupero dell'id soggetto
    this.getIdSoggetto = (soggettoIstanza: SoggettoIstanza) => {
      // Ritorno l'id del soggetto istanza
      return soggettoIstanza?.soggetto?.id_soggetto;
      // #
    };
  }

  /**
   * #################
   * FUNZIONI OVERRIDE
   * #################
   */

  /**
   * Funzione che scarica e setta i dati degli oggetti istanza all'interno del componente.
   * Nel caso di gestione con override, si può utilizzare la definizione della funzione con un generic.
   * @param idIstanza number con l'id istanza per lo scarico dati.
   * @returns Observable<OggettoIstanza[] | T[]> con la lista di elementi scaricati.
   */
  protected initOggettiIstanza$<T>(
    idIstanza: number
  ): Observable<OggettoIstanza[] | T[]> {
    // Lancio la chiamata al servizio e scarico i dati
    return this._ambito.getOggettiIstanzaESoggettiByIstanza(idIstanza).pipe(
      catchError((e: ScrivaServerError) => {
        // Gestisco l'errore e permetto il 404
        return this._opere.allowNotFoundArray(e);
        // #
      }),
      tap((oggettiIstanzaFE: OggettoIstanzaGeometrieFE[]) => {
        // Richiamo la funzione di gestione del risultato
        this.onInitOggettiIstanza(oggettiIstanzaFE);
        // #
      })
    );
  }

  /**
   * Funzione di supporto invocata nel momento in cui i dati sono stati scaricati per il dato contestuale.
   * La funzione gestisce le logiche di assegnamento delle informazioni per i dati del componente.
   * @param oggettiIstanza OggettoIstanzaGeometrieFE[] con la lista di elementi scaricati.
   * @override
   */
  protected onInitOggettiIstanza(oggettiIstanza: OggettoIstanzaGeometrieFE[]) {
    // Effettuo il filtro sulla lista degli oggetti istanza forzando la tipizzazione del risultato della funzione
    let oggIstQdr: OggettoIstanzaGeometrieFE[] = <OggettoIstanzaGeometrieFE[]>(
      this.filterOpereOggettiIstanzaByQuadro(oggettiIstanza)
    );

    // Definisco il livello per il recupero dati
    const indLivello = IndLivelliOpere.secondo;
    // Nelle restituzioni sono con ind_livello 2 il livello 1 viene salvato nel quadro dati generali
    this.oggettiIstanza = this.filterOggettiIstanzaByLivello(
      oggIstQdr,
      indLivello
    );
  }

  /**
   * Funzione di filtro che effettua delle logiche di selezione per gli oggetti in input.
   * Partendo da una fonte di oggetti scaricati per istanza, si filtrano tutti quegli oggetti compatibili con le tipologie opere del componente.
   * @param opereOggettiIstanza OggettoIstanzaGeometrieFE[] con la lista di elementi da filtrare.
   * @returns OggettoIstanzaGeometrieFE[] con la lista di elementi filtrati.
   * @override
   */
  protected filterOpereOggettiIstanzaByQuadro(
    opereOggettiIstanza: OggettoIstanzaGeometrieFE[]
  ): OggettoIstanzaGeometrieFE[] {
    // Modifico la tipizzazione per la gestione della configurazione
    const opereOggettiIstanzaConfigs: OggettoIstanza[] = <OggettoIstanza[]>(
      opereOggettiIstanza
    );

    // Richiamo e ritorno le logiche della funzione del servizio
    let opereOggettiIstanzaFilter: Opera[] | OggettoIstanza[];
    opereOggettiIstanzaFilter = this._opere.filterOpereOggettiIstanzaByQuadro(
      opereOggettiIstanzaConfigs,
      this.tipologieOpere
    );

    // Gestisco manualmente il parse del tipo di ritorno
    return <OggettoIstanzaGeometrieFE[]>opereOggettiIstanzaFilter;
  }

  /**
   * Funzione di filtro che effettua delle logiche di selezione per gli oggetti in input.
   * Partendo da una fonte di oggetti scaricati per istanza, si filtrano tutti gli oggetti per il loro "ind_livello".
   * @param oggettiIstanza OggettoIstanzaGeometrieFE[] con la lista di elementi da filtrare.
   * @param livello number con l'indicazione di filtro per i dati.
   * @returns OggettoIstanzaGeometrieFE[] con la lista di elementi filtrati.
   * @override
   */
  protected filterOggettiIstanzaByLivello(
    oggettiIstanza: OggettoIstanzaGeometrieFE[],
    livello: number
  ): OggettoIstanzaGeometrieFE[] {
    // Richiamo e ritorno le logiche della funzione del servizio
    const oggettiIstanzaFilter: OggettoIstanzaLike[] =
      this._opere.filterOggettiIstanzaByLivello(oggettiIstanza, livello);

    // Gestisco manualmente il parse del tipo di ritorno
    return <OggettoIstanzaGeometrieFE[]>oggettiIstanzaFilter;
  }

  /**
   * ######################################################
   * FUNZIONI COLLEGATE ALL'INSERIMENTO DATI SU MAPPA GEECO
   * ######################################################
   */

  // #region "GEECO"

  /**
   * Funzione collegata al pulsante di inserimento punti geometria per geeco.
   * @override Modifica della logica con l'aggiunta della gestione dell'id soggetto selezionato dalla ricerca.
   */
  onInserisciSuMappa() {
    // Definisco le configurazioni specifiche per Geeco
    const geecoQuitUrlParams: IGeecoQuitUrlParams = {
      codQuadro: this.codQuadro,
    };
    // Creo l'oggetto completo per la generazione dell'url, mandando come quit_url_params il codice del quadro per il rientro nell'applicazione
    const geoGeeco: IGeoGeecoConfigs = {
      id_istanza: this.istanza.id_istanza,
      id_oggetto_istanza: [],
      chiave_config: `${this.codQuadro}.GEECO`,
      quit_url_params: this._geeco.createGeecoQuitUrlParams(geecoQuitUrlParams),
      /**
       * @author Ismaele Bottelli
       * @date 08/05/2025
       * @note Per la ricerca delle opere dentro <app-oggetti-search> si usa id_soggetto, ma per l'associazione con geeco si usa id_soggetto_istanza.
       *       Per il momento il collega Verner Alessandro ha detto che bisognerebbe modificare il nome di questa proprietà in id_soggetto_istanza.
       *       Il problema è che non abbiamo tempo/BEndisti per farlo, quindi verrà aperta una migliorativa.       * 
       */
      id_soggetto: this.idSoggettoIstanzaRicerca,
    };
    const attoreGestioneIstanza: string = this.attoreGestioneIstanza;
    const errorConfigs: IAlertConfig = {
      code: ScrivaCodesMesseges.E100,
      idDOM: this.OPERE_CONSTS.ALERT_TARGET_OPERA,
      autoFade: this.autoFade,
    };
    // Vado a generare le informazioni per l'apertura di geeco
    const configs: IGeecoOpen = {
      geoGeeco,
      attoreGestioneIstanza,
      errorConfigs,
    };

    // Faccio partire lo spinner di caricamento
    this.spinner.show();

    // Lancio la chiamata di apertura di geeco
    this._geeco.openGeeco(configs).subscribe({
      next: (connection: IGeoGeeco) => {
        // Chiudo partire lo spinner di caricamento
        this.spinner.hide();
        // #
      },
      error: (e: ScrivaServerError) => {
        // Chiudo partire lo spinner di caricamento
        this.spinner.hide();
        // #
      },
    });
  }

  // #endregion "GEECO"

  // #region "GESTIONE TABELLE"

  /**
   * Funzione di set che va a definire la struttura della tabella per la ricerca delle opere.
   * @notes Gli oggetti gestiti da questa tabella sono: Opera.
   * @override Override del set della struttura della tabella per la ricerca opere.
   */
  protected setTableRicercaOpere() {
    // Definisco la configurazione per la tabella
    this.searchResultsColumns = [
      {
        name: '',
        sortable: false,
        canAutoResize: false,
        draggable: false,
        resizeable: false,
        width: 55,
        headerTemplate: this.checkboxHeaderTemplate,
        cellTemplate: this.checkboxColTemplate,
        cellClass: 'checkbox-cell',
      },
      { name: 'Codice SCRIVA', cellTemplate: this.codiceScrivaOperaTemplate },
      { name: 'Denominazione', prop: 'den_oggetto' },
      { name: 'Indirizzo', cellTemplate: this.indirizzoUbicazioneTemplate },
      {
        name: 'Comune',
        cellTemplate: this.comuneProvinciaOperaTemplate,
        sortable: false,
      },
    ];
  }

  /**
   * Funzione di set che va a definire la struttura della tabella di associazione delle opere.
   * @notes Gli oggetti gestiti da questa tabella sono: OggettoIstanza.
   * @override Override del set della struttura della tabella per l'associazione opere.
   */
  protected setTableAssociazioneOpere() {
    // Definisco la configurazione per la tabella
    this.associazioniColumns = [
      { name: 'Codice SCRIVA', cellTemplate: this.codiceScrivaOggIstTemplate },
      { name: 'Denominazione', prop: 'den_oggetto' },
      { name: 'Indirizzo', cellTemplate: this.indirizzoUbicazioneTemplate },
      {
        name: 'Comune',
        cellTemplate: this.comuneProvinciaOggIstTemplate,
        sortable: false,
      },
      {
        name: 'Codice fiscale',
        cellTemplate: this.codiceFiscaleAziendaTemplate,
      },
      {
        name: 'Den. sede legale',
        cellTemplate: this.denominazioneAziendaTemplate,
      },
      {
        name: 'Azioni',
        sortable: false,
        minWidth: 90,
        maxWidth: 120,
        resizeable: false,
        cellTemplate: this.azioniTemplate,
      },
    ];
  }

  // #endregion "GESTIONE TABELLE"

  // #region "INIT DATI ISTANZA X TEMPLATE QUADRO"

  /**
   * Funzione di init specifica che imposta i dati all'interno del componente a seguito dello scarico dati del quadro x istanza.
   * La funzione imposta i dati specifici con dovuti controlli per l'istanza.
   * @param templateQuadro TemplateQuadro con l'insieme delle informazioni scaricate per i dati del quadro ed istanza.
   * @param codQuadro string con il codice quadro per l'assegnazione dati. Se non è definito, per default, verrà usato il dato definito a livello di componente.
   * @param codTipoQuadro string con il codice tipo quadro per l'assegnazione. Se non è definito, per default, verrà usato il dato definito a livello di componente.
   * @override Dalla funzione originale, è necessario recuperare e gestire delle configurazioni specifiche.
   *           Effettuo un'ovverride con l'aggiunta dell'inizializzazione delle informazioni necessarie.
   */
  protected initDatiIstanzaByTemplateQuadro(
    templateQuadro: TemplateQuadro,
    codQuadro?: string,
    codTipoQuadro?: string
  ) {
    // Verifico l'input
    codQuadro = codQuadro ?? this.codQuadro;
    codTipoQuadro = codTipoQuadro ?? this.codTipoQuadro;

    // Recupero dall'input le informazioni di cofigurazione
    const istanza: Istanza = templateQuadro?.istanza;

    // ### ISTANZA - Effettuo l'assegnamento delle informazioni x istanza
    this.istanza = istanza;
    // Si utilizzano logiche di parse JSON, uso un try catch
    try {
      // Effettuo il parse dei dati del json_data dell'istanza
      const jsonData: any = JSON.parse(istanza.json_data);
      // Recupero i dati per il quadro riepilogo
      this.qdr_riepilogo = jsonData?.QDR_RIEPILOGO;
      // Recupero la configurazione specifica del quadro (ex.: QDR_DER_CAPTAZIONE)
      this.configQuadro = jsonData?.QDR_CONFIG[codQuadro];
      // Per i dati tecnici, avremo codice tipo quadro diverso da cod quadro, tento di accedere "internamente"
      this.datiTecnici = jsonData[codTipoQuadro][codQuadro];

      // Verifico se esistono effettivamente i dati tecnici
      if (this.datiTecnici) {
        // Esistono dei dati tecnici, il salvataggio deve essere in modifica
        this.saveWithPut = true;
      }
      // #
    } catch (e) {
      // Loggo l'errore generato dal try catch
      const t = `Exception: initDatiIstanzaByTemplateQuadro [ISTANZA] - codQuadro: ${codQuadro} | codTipoQuadro: ${codTipoQuadro}`;
      const b = { templateQuadro, exception: e };
      this._logger.error(t, b);
      // #
    }

    // Inizializzo le informazioni per l'input del codice fiscale azienda
    this.initConfigInputCFAzienda();
    // #
  }

  /**
   * Funzione che recupera la configurazione per i campi del form ricerca oggetti.
   * Utilizza la configurazione del campo del codice fiscale per la gestione delle informazioni.
   */
  private initConfigInputCFAzienda() {
    // Recupero dalla configurazione i campi di ricerca
    let criteriRicerca: ICriteriRicercaQdrOggetto = {
      ...this.configQuadro?.criteri_ricerca,
    };

    // Inizializzo con la configurazione
    const configs: IConfigsElementCfAzienda =
      this._usiUloDer.initConfigInputCFAzienda(criteriRicerca);

    // Imposto localmente le configurazioni per la gestione del CF azienda
    this.cfAziendaConfig = configs.cfAziendaConfig;
    this.denAziendaConfig = configs.denAziendaConfig;
  }

  // #endregion "INIT DATI ISTANZA X TEMPLATE QUADRO"

  /**
   * #################
   * GESTIONE SOGGETTI
   * #################
   */

  // #region "GESTIONE SOGGETTI"

  /**
   * Funzione che scarica i filtra le informazioni per i soggetti da visualizzare dentro la form di ricerca per gli oggetti istanza.
   */
  private scaricaSoggettiUsi(): Observable<SoggettoIstanza[]> {
    // Richiamo il servizio di scarico per i soggetti
    return this._ambito.getSoggettiIstanzaByIstanza(this.idIstanza).pipe(
      map((soggetti: SoggettoIstanza[]) => {
        // Filtro i soggetti e rimuovo dalla lista tutti i soggetti senza id_soggetto_padre
        let soggettiFilter: SoggettoIstanza[];
        soggettiFilter = soggetti?.filter((soggettoIstanza: SoggettoIstanza) => {
          // Il soggetto è da tenere nella lista se non ha id_soggetto_padre
          return soggettoIstanza?.id_soggetto_padre == undefined;
          // #
        });
        // Ritorno la lista di elementi filtrati
        return soggettiFilter;
        // #
      })
    );
  }

  /**
   * Funzione che genera la denominazione azienda dato un soggetto.
   * La denominazione prevede la verifica sul tipo soggetto e, a seconda di questa informazione, generarà una denominazione specifica.
   * @param soggetto SoggettoIstanza con le informazioni per la generazione della denominazione.
   * @returns string con la denominazione generata.
   */
  private generaDenAzienda(soggetto: SoggettoIstanza): string {
    // Ritorno la denominazione composta tramite funzione
    return generaDenominazioneAzienda(soggetto);
    // #
  }

  // #endregion "GESTIONE SOGGETTI"

  /**
   * ###################
   * GESTIONE CF AZIENZA
   * ###################
   */

  // #region "GESTIONE CF AZIENZA"

  /**
   * Funzione collegata all'evento che emette l'oggetto di ricerca utilizzato per lo scarico dei dati delle opere.
   * La funzione deve recuperare l'informazione relativa al soggetto che l'utente ha scelto per la ricerca.
   * La funzionalità di "INSERISCI SU MAPPA" si abiliterà solo dopo una ricerca attiva.
   * @param params IFormOggettiSearchData con l'oggetto contenente le informazioni usate per la ricerca delle opere.
   */
  gestisciParametriRicerca(params: IFormOggettiSearchData) {
    // Verifico l'input
    if (!params) {
      // Mancano le informazioni
      return;
      // #
    }

    // Estraggo l'informazione per i dati effettivi dentro il form
    const formData: OggettiSearchFormData = params.formParams;
    // Recupero i dati del soggetto
    const soggetto: SoggettoIstanza = formData.cf_soggetto;

    // Verifico se esiste il soggetto
    if (soggetto) {
      // Salvo localmente le informazioni
      this.soggettoIstanzaRicerca = cloneDeep(soggetto);
      // #
    }
  }

  /**
   * Funzione che intercetta l'evento di form di ricerca modificata.
   * @param formData OggettiSearchFormData con l'oggetto modificato nel form.
   */
  ricercaFormChanged(formData: OggettiSearchFormData) {
    // I dati del form sono stati modificati, resetto le informazioni
    this.soggettoIstanzaRicerca = undefined;
    // #
  }

  // #endregion "GESTIONE CF AZIENZA"

  /**
   * ###########################
   * FUNZIONI DI GESTIONE MODALE
   * ###########################
   */

  // #region "GESTIONE DELLA MODALE"

  /**
   * Funzione che genera le configurazioni con i parametri da passare alla modale dei dati tecnici.
   * @param opera Opera con il riferimento all'oggetto per la gestione dei dati tecnici.
   * @param oggettoIstanza OggettoIstanza con il riferimento all'oggetto per la gestione dei dati tecnici.
   * @param readOnly boolean con l'indicazione di attivare la gestione dei dati in sola lettura.
   */
  protected paramsModaleDatiTecnici(
    opera: Opera,
    oggettoIstanza: OggettoIstanza,
    readOnly: boolean
  ): IDTOperaModalParams {
    // Definisco l'oggetto per i parametri da passare alla modale
    const modalConfig: IDTOperaModalParams = {
      adempimento: this.adempimento,
      attoreGestioneIstanza: this.attoreGestioneIstanza,
      componente: this.componente,
      oggettoIstanza: oggettoIstanza,
      opera: opera,
      quadro: this.quadro,
      readOnly: readOnly,
      sourceData: this.sourceDataForModal,
    };

    // Ritorno la configurazione
    return modalConfig;
  }

  /**
   * Funzione che definisce le callback che verranno invocate al salvataggio o alla chiusura della modale dei dati tecnici.
   * Questa funzione è pensata per l'override, in maniera tale che qualunque classe estenda questo componente, può definire una propria logica di salvataggio.
   * Per default, questa funzione permette il salvataggio di tutto il json data prodotto dalla modale dei dati tecnici.
   * @param opera Opera con il riferimento all'oggetto Opera per la gestione dei dati tecnici.
   * @param oggettoIstanza OggettoIstanza con il riferimento all'oggetto Opera per la gestione dei dati tecnici.
   * @returns IDTOperaModalCallbacks con le callback per il savataggio dati.
   */
  protected datiTecniciCallbacks(
    opera: Opera,
    oggettoIstanza: OggettoIstanza
  ): IDTOperaModalCallbacks {
    // Definisco l'oggetto con le callback per il salvataggio
    const callbacks: IDTOperaModalCallbacks = {
      onModaleChiusa: (modalResponse?: IDTOperaSalvataggio) => {
        // Vado a resettare la variabile che contiene i dati tecnici per la modale
        this.sourceDataForModal = null;

        // Verifico se è stato ritornato qualche dato dalla modale
        if (modalResponse) {
          // Lancio la funzione di salvataggio dati tecnici
          this.saveDatiTecniciOpera(modalResponse, opera, oggettoIstanza);
          // #
        }
      },
    };

    // Ritorno le callback
    return callbacks;
  }

  // #endregion "GESTIONE DELLA MODALE"

  /**
   * ################################
   * FUNZIONI VERIFICA VALIDITA' DATI
   * ################################
   */

  // #region "VERIFICA SEZIONI OBBLIGATORIE DATI TECNICI SONO COMPILATI"

  /**
   * Funzione che gestisce i controlli per verificare che ogni sezione definita come obbligatoria dei dati tecnici sia effettivamente compilata.
   * @param segnalaRisultato boolean con un flag che definisce le logiche di segnalazione all'utente di eventuali errori.
   * @returns boolean con il risultato della verifica. [true] se la verifica è passata, [false] se ci sono errori.
   * @override
   */
  protected verificaPresenzaDatiTecniciObbligatori(
    segnalaRisultato: boolean
  ): boolean {
    // Recupero le informazioni del componente
    const datiTecnici: IListeDatiTecniciOggettiIstanze = this.datiTecnici;
    const tipologieOpere: string[] = this.tipologieOpere;
    const oggettiIstanza: OggettoIstanza[] = this.oggettiIstanza;
    const opere: Opera[] = this.opere;
    const quadro: Quadro = this.quadro;
    // Richiamo la verifica definita nel servizio e ritorno il risultato
    return this._opereVerificheStep.verificaPresenzaDatiTecniciObbligatori(
      datiTecnici,
      tipologieOpere,
      oggettiIstanza,
      opere,
      quadro,
      segnalaRisultato
    );
  }

  // #endregion "VERIFICA SEZIONI OBBLIGATORIE DATI TECNICI SONO COMPILATI"

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  // #region "GETTER E SETTER"

  /**
   * Getter che recupera la lista degli oggetti-istanza scaricati per l'istanza.
   * @returns OggettoIstanzaGeometrieFE[] con le informazioni recuperate.
   */
  get oggettiIstanza(): OggettoIstanzaGeometrieFE[] {
    // Ritorno la lista degli oggetti istanza
    return this._oggettiIstanza;
    // #
  }

  /**
   * Setter che assegna una lista degli oggetti-istanza scaricati per l'istanza.
   * @params OggettoIstanzaGeometrieFE[] con le informazioni d'assegnare.
   */
  set oggettiIstanza(oggettiIstaza: OggettoIstanzaGeometrieFE[]) {
    // Ritorno la lista degli oggetti istanza
    this._oggettiIstanza = oggettiIstaza;
    // #
  }

  /**
   * Getter che recupera l'id soggetto dal soggetto ricercato dall'utente.
   * @returns number con l'informazione estratta.
   */
  get idSoggettoIstanzaRicerca(): number {
    // Recupero il soggetto che l'utente ha cercato
    const soggettoIstanza: SoggettoIstanza = this.soggettoIstanzaRicerca;
    // Cerco di estrarre l'id soggetto istanza
    return soggettoIstanza?.id_soggetto_istanza;
    // #
  }

  /**
   * Getter che recupera il codice fiscale azienda dal soggetto ricercato dall'utente.
   * @returns string con l'informazione estratta.
   */
  get cfAzienda(): string {
    // Recupero il soggetto che l'utente ha cercato
    const soggetto: SoggettoIstanza = this.soggettoIstanzaRicerca;
    // Cerco di estrarre il codice fiscale
    return soggetto?.cf_soggetto ?? '';
    // #
  }

  /**
   * Getter che recupera la denominazione azienda dal soggetto ricercato dall'utente.
   * La funzione è basata sulla stessa funzione di estrazione passata in input al componente di ricerca.
   * @returns string con l'informazione estratta.
   */
  get denAzienda(): string {
    // Recupero il soggetto che l'utente ha cercato
    const soggetto: SoggettoIstanza = this.soggettoIstanzaRicerca;
    // Genero la denominazione azienda
    const desAziendale: string = this.generaDenAzienda(soggetto);
    // Cerco di estrarre il codice fiscale
    return desAziendale ?? '';
    // #
  }

  /**
   * Getter che recupera la condizione di disabilitazione per il pulsante "INSERISCI SU MAPPA".
   * @returns boolean con il risultato del check delle condizioni.
   */
  get disabledInserisciSuMappa(): boolean {
    // Gestisco le condizioni di disabilitazione del pulsante di geeco
    const isAGILock =
      this.attoreGestioneIstanza === this.gestioneEnum.WRITE_LOCK;
    // Verifico se l'utente ha ricercato un soggetto
    const soggettoNonCercato = !this.soggettoIstanzaRicerca;

    // Ritorno l'insieme delle condizioni
    return isAGILock || soggettoNonCercato;
  }

  // #endregion "GETTER E SETTER"
}
