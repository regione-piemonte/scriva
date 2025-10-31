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
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { AmbitoService } from 'src/app/features/ambito/services';
import { CONFIG } from 'src/app/shared/config.injectiontoken';
import { Quadro, StepConfig } from 'src/app/shared/models';
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
import { FormioService } from '../../../../../shared/services/formio/formio.service';
import { GeecoService } from '../../../../../shared/services/geeco/geeco.service';
import { LoggerService } from '../../../../../shared/services/logger.service';
import { OggettoIstanza, Opera } from '../../../models';
import { OpereTrasportoService } from '../../../services/opere/opere-trasporto/opere-trasporto.service';
import { OpereVerificheStepService } from '../../../services/opere/opere-verifiche-step.service';
import { OpereService } from '../../../services/opere/opere.service';
import {
  IDTOperaModalCallbacks,
  IDTOperaModalParams,
} from '../opere/modals/dati-tecnici-opera-modal/utilities/dati-tecnici-opera-modal.interfaces';
import { OpereComponent } from '../opere/opere.component';
import {
  IndLivelliOpere,
  TipologieOggettoIstanza,
} from '../opere/utilities/opere.enums';
import { IListeDatiTecniciOggettiIstanze } from '../opere/utilities/opere.interfaces';
import { OpereTrasportoOperaModalComponent } from './modals/opere-trasporto-opera-modal/opere-trasporto-opera-modal.component';
import { OpereTrasportoConsts } from './utilities/opere-trasporto.consts';
import { PresentazioneIstanzaService } from '../../../services/presentazione-istanza/presentazione-istanza.service';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'app-opere-trasporto',
  templateUrl: './opere-trasporto.component.html',
  styleUrls: ['../opere/opere.component.scss'],
  providers: [OpereVerificheStepService, OpereTrasportoService],
})
export class OpereTrasportoComponent
  extends OpereComponent
  implements OnInit, OnDestroy, AfterViewInit
{
  /** OpereTrasportoConsts con le informazioni costanti per il componente. */
  public OPERE_CONSTS = new OpereTrasportoConsts();

  /** string costante che definisce il contenitore per agganciare i messaggi di segnalazione. */
  readonly ALERT_TARGET_MODALE: string = 'opere-trasporto-content';

  /**
   * string che definisce il nome del componente di riferimento.
   * @override
   */
  protected componentName: string = 'OpereTrasportoComponent';

  /**
   * any per gestire il componente da usare per la modale di dettaglio delle opere.
   * @override
   */
  protected componenteModale: any = OpereTrasportoOperaModalComponent;

  /**
   * Costruttore.
   */
  constructor(
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
    private _opereTrasporto: OpereTrasportoService,
    opereVerificheStep: OpereVerificheStepService,
    route: ActivatedRoute,
    stepManager: StepManagerService,
    spinner: NgxSpinnerService,
    presentazioneIstanzaService: PresentazioneIstanzaService
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
      presentazioneIstanzaService,
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
   * #################
   * FUNZIONI OVERRIDE
   * #################
   */

  /**
   * Funzione di supporto invocata nel momento in cui i dati sono stati scaricati per il dato contestuale.
   * La funzione gestisce le logiche di assegnamento delle informazioni per i dati del componente.
   * @param oggettiIstanza OggettoIstanza[] con la lista di elementi scaricati.
   * @override
   */
  protected onInitOggettiIstanza(oggettiIstanza: OggettoIstanza[]) {
    // Effettuo il filtro sulla lista degli oggetti istanza forzando la tipizzazione del risultato della funzione
    let oggIstQdr: OggettoIstanza[] = <OggettoIstanza[]>(
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
      {
        name: 'Tipologia di opera di trasporto',
        prop: 'tipologia_oggetto.des_tipologia_oggetto',
      },
      { name: 'Denominazione', prop: 'den_oggetto' },
      { name: 'Descrizione', prop: 'des_oggetto' },
      { name: 'Codice rilievo', cellTemplate: this.codiceRilievoOperaTemplate },
      {
        name: 'Comune',
        cellTemplate: this.comuneProvinciaOperaTemplate,
        sortable: false,
      },
      { name: 'Codice SCRIVA', cellTemplate: this.codiceScrivaOperaTemplate },
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
      { name: 'Denominazione opera', prop: 'den_oggetto' },
      { name: 'Descrizione opera', prop: 'des_oggetto' },
      { name: 'Codice SCRIVA', cellTemplate: this.codiceScrivaOggIstTemplate },
      {
        name: 'Comune',
        cellTemplate: this.comuneProvinciaOggIstTemplate,
        sortable: false,
      },
      {
        name: 'Località',
        cellTemplate: this.localitaTemplate,
        sortable: false,
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

  /**
   * ###############################
   * APERTURA DETTEGLIO DATI TECNICI
   * ###############################
   */

  // #region "APRI DATI TECNICI"

  /**
   * Funzione che gestisce l'apertura della modale con le informazioni di un'opera, tramite la tabella degli oggetti istanza.
   * @param oggettoIstanzaRow OggettoIstanza con i dati dell'oggetto istanza della riga della tabella.
   * @param onlyRead boolean con il flag che gestisce la modalità di sola lettura definita dalla tabella (azioni: modifica, dettaglio).
   * @param checkIdOggettoIstanza boolean che definisce se è necessario verificare l'id oggetto istanza dell'oggetto per poterne aprire il dettaglio.
   * @override
   * @author Ismaele Bottelli
   * @date 09/12/2024
   * @jira SCRIVA-1574
   * @notes Per la gestione delle opere di trasporto, per l'apertura del dettaglio o la modifica dei dati tecnici, devo aggiungere una verifica di sicurezza.
   *        Sull'HTML c'è una pipe che nasconde i pulsanti, ma li tiene in pagina per allineare graficamente le icone, qua aggiungo "fisicamente" il controllo.
   */
  showDetails(
    oggettoIstanzaRow: OggettoIstanza,
    onlyRead: boolean,
    checkIdOggettoIstanza: boolean
  ) {
    // Verifico che l'operazione si effettivamente abilitata per l'oggetto istanza
    let isActionOK: boolean;
    isActionOK = this._opereTrasporto.accediDTOpereTrasporto(oggettoIstanzaRow);
    // Verifico se per l'opera di trasporto si può aprire i dati tecnici
    if (!isActionOK) {
      // L'operazione è bloccata
      return;
    }

    // Recupero l'id per l'oggetto istanza
    let existIdOggIst: boolean;
    existIdOggIst = oggettoIstanzaRow.id_oggetto_istanza != undefined;
    // Verifico il flag per l'oggetto istanza
    if (checkIdOggettoIstanza && !existIdOggIst) {
      // Blocco le logiche
      return;
    }

    // Dalla lista delle opere scaricate, recupero l'oggetto opera collegato all'oggetto istanza
    const opera = this.opere.find((operaElem: Opera) => {
      // Effettuo una comparazione tra id oggetto
      return operaElem.id_oggetto === oggettoIstanzaRow.id_oggetto;
      // #
    });
    // Recupero l'oggetto specifico dalla lista degli oggetti istanza scaricati
    const oggettoIstanza = this.oggettiIstanza.find((oggIstaElem) => {
      // Recupero gli id oggetti istanza per una comparazione
      const idOggIstElem = oggIstaElem.id_oggetto_istanza;
      const idOggIstRow = oggettoIstanzaRow.id_oggetto_istanza;
      // Effettuo una comparazione tra id
      return idOggIstElem === idOggIstRow;
      // #
    });

    // Verifico se esistono dati tecnici estratti dal json data
    if (this.datiTecnici) {
      // Richiamo la funzione specifica di estrazione dati tecnici per l'opera
      this.sourceDataForModal = this._opere.estraiDatiTecniciOpera(
        this.datiTecnici,
        opera,
        oggettoIstanzaRow
      );
      // #
    }

    // Definisco l'oggetto che conterrà i parametri da passare alla modale
    const modalConfig: IDTOperaModalParams = this.paramsModaleDatiTecnici(
      opera,
      oggettoIstanza,
      onlyRead
    );
    // Definisco l'oggetto con la callback per la modale ed i suoi eventi
    const modalCallbacks: IDTOperaModalCallbacks = this.datiTecniciCallbacks(
      opera,
      oggettoIstanza
    );

    // Richiamo la funzione del servizio
    this.apriModaleDatiTecnici(modalConfig, modalCallbacks);
  }

  // #endregion "APRI DATI TECNICI"

  /**
   * ###################################################
   * FUNZIONI DI VERIFICA PER PROCEDERE AL PROSSIMO STEP
   * ###################################################
   */

  // #region "CHECK STEP"

  /**
   * Funzione che gestisce i dati per poter accedere alla pagina successiva.
   * Le logiche prevedono la verifica dei dati per le opere collegate all'istanza, verificando che le informazioni siano valide.
   * @param segnalaRisultato boolean che definisce l'attivazione delle segnalazioni verso l'utente, per il salvataggio dati riuscito o fallito. Per default è: false.
   * @ovveride
   * @author Ismaele Bottelli
   * @date 11/12/2024
   * @jira SCRIVA-1574
   * @notes Il check step per passare al prossimo quadro, per queste specifiche opere, deve predevedere delle condizioni specifiche.
   *        Essendo che solo le opere CONDOTTA_FORZATA hanno dati tecnici, verificati dai check "checkDatiTecnici" e "checkMandatoryFields",
   *        se l'utente inserisce opere senza dati tecnici il check step deve permettere l'avanzamento comunque al passo successivo.
   */
  isStepValid(segnalaRisultato = false): Observable<boolean> {
    // Verifico e gestisco la logica e le segnalazioni per: l'utente ha associato almeno un OggettoIstanza all'Istanza
    const checkOggIstAssegnati: boolean =
      this.verificaSegnalaOggIstAssociati(false);

    // Verifico se ci sono oggetti-istanza collegati
    if (checkOggIstAssegnati) {
      // Esisono, verifico se gli oggetti istanza sono TUTTI senza dati tecnici
      const checkAllOggIstSenzaDT: boolean =
        this.verificaTuttiOggIstSenzaDatiTecnici();
      // Verifico se tutti gli oggetti-istanza sono effettivamente opere senza gestione dei dati tecnici
      if (checkAllOggIstSenzaDT) {
        // Lo sono, vuol dire che i prossimi controlli sono inutili e posso lasciar proseguire con lo step
        return of(true);
      }
      // #
    } else {
      // Se non ci sono oggetti-istanza associati, in questo caso possiamo lasciare andare l'utente avanti
      return of(true);
    }

    // Verifico e gestisco la logica e le segnalazioni per: gli OggettiIstanza associati all'Istanza hanno tutti dei dati tecnici associati
    const checkDatiTecnici: boolean =
      this.verificaTuttiOggettiIstanzaHannoDatiTecnici(segnalaRisultato);

    // Verifico e gestisco la logica e le segnalazioni per: tutte le informazioni dei dati tecnici hanno internamente le loro sezioni obbligatorie valorizzate
    const checkMandatoryFields: boolean =
      this.verificaPresenzaDatiTecniciObbligatori(segnalaRisultato);

    // Ritorno la validità di tutti i check dati
    return of(checkOggIstAssegnati && checkDatiTecnici && checkMandatoryFields);
  }

  // #endregion "CHECK STEP"

  // #region "VERIFICA SE TUTTI OGGETTI ISTANZA NON HANNO DATI TECNICI"

  /**
   * Funzione di verifica che prende in considerazione gli oggetti istanza inseriti dall'utente e verifica se tutti gli oggetti risultano nella lista degli oggetti-istanza senza gestione dei dati tecnici.
   * @returns boolean con il risultato del check a [true] se tutti gli oggetti-istanza sono in gestione senza dati tecnici.
   * @author Ismaele Bottelli
   * @date 11/12/2024
   * @jira SCRIVA-1574
   * @notes Il check step per passare al prossimo quadro, per queste specifiche opere, deve predevedere delle condizioni specifiche.
   *        Essendo che solo le opere CONDOTTA_FORZATA hanno dati tecnici, verificati dai check "checkDatiTecnici" e "checkMandatoryFields",
   *        se l'utente inserisce opere senza dati tecnici il check step deve permettere l'avanzamento comunque al passo successivo.
   */
  private verificaTuttiOggIstSenzaDatiTecnici(): boolean {
    // N.B.: questa funzione è pensata per essere eseguita dopo che la verifica per la quale l'utente ha già effettivamente inserito oggetti-istanza.
    // Recupero la lista degli oggetti istanza a livello componente e per sicurezza forzo ad array vuoto
    let oggettiIstanza: OggettoIstanza[] = this.oggettiIstanza ?? [];

    // Recupero la lista delle opere di trasporto che non hanno i dati tecnici
    const opereTrasportoSenzaDT: TipologieOggettoIstanza[] =
      this.OPERE_CONSTS.OPERE_TRASPORTO_SENZA_DATI_TECNICI;
    // Verifico che per ogni oggetto istanza inserito dall'utente questo sia definito come "senza dati tecnici"
    const tuttiOggIstSenzaDT: boolean = oggettiIstanza.every((oggIst: OggettoIstanza) => {
      // Verifico se l'oggetto istanza è nella lista delle opere senza dati tecnici
      const isOggIstSenzaDT: boolean = opereTrasportoSenzaDT.some(
        (tipoOggIst: string) => {
          // Effettuo un match tra il tipo oggetto istazanza senza dt e il codice dell'oggetto istanza iterato
          return tipoOggIst === oggIst.tipologia_oggetto.cod_tipologia_oggetto;
          // #
        }
      );

      // Ritorno il risultato del check
      return isOggIstSenzaDT;
      // #
    });

    // Ritorno il check di verifica per indicare che tutti gli oggetti istanza sono senza dati tecnici
    return tuttiOggIstSenzaDT;
  }

  // #endregion "VERIFICA SE TUTTI OGGETTI ISTANZA NON HANNO DATI TECNICI"

  // #region "VERIFICA TUTTI OGGETTI ISTANZA HANNO DATI TECNICI COLLEGATI"

  /**
   * Funzione che gestisce i controlli per verificare che ogni OggettoIstanza collegato all'istanza abbia collegato a se le informazioni dei dati tecnici.
   * @param segnalaRisultato boolean con un flag che definisce le logiche di segnalazione all'utente di eventuali errori.
   * @returns boolean con il risultato della verifica. [true] se la verifica è passata, [false] se ci sono errori.
   * @override
   * @author Ismaele Bottelli
   * @date 09/12/2024
   * @jira SCRIVA-1574
   * @notes Solo alcune opere hanno i dati tecnici, mentre le altre non ne hanno. La funzione quindi gestisce in maniera diversa le informazioni relative agli oggetti-istanza.
   */
  protected verificaTuttiOggettiIstanzaHannoDatiTecnici(
    segnalaRisultato: boolean
  ): boolean {
    // Recupero le informazioni del componente
    const datiTecnici: IListeDatiTecniciOggettiIstanze = this.datiTecnici;
    const oggettiIstanza: OggettoIstanza[] = this.oggettiIstanzaConDatiTecnici;
    const opere: Opera[] = this.opere;
    // Richiamo la verifica definita nel servizio e ritorno il risultato
    return this._opereVerificheStep.verificaTuttiOggettiIstanzaHannoDatiTecnici(
      datiTecnici,
      oggettiIstanza,
      opere,
      segnalaRisultato
    );
  }

  // #endregion "VERIFICA TUTTI OGGETTI ISTANZA HANNO DATI TECNICI COLLEGATI"

  // #region "VERIFICA SEZIONI OBBLIGATORIE DATI TECNICI SONO COMPILATI"

  /**
   * Funzione che gestisce i controlli per verificare che ogni sezione definita come obbligatoria dei dati tecnici sia effettivamente compilata.
   * @param segnalaRisultato boolean con un flag che definisce le logiche di segnalazione all'utente di eventuali errori.
   * @returns boolean con il risultato della verifica. [true] se la verifica è passata, [false] se ci sono errori.
   * @override
   * @author Ismaele Bottelli
   * @date 09/12/2024
   * @jira SCRIVA-1574
   * @notes Solo alcune opere hanno i dati tecnici, mentre le altre non ne hanno. La funzione quindi gestisce in maniera diversa le informazioni relative agli oggetti-istanza.
   */
  protected verificaPresenzaDatiTecniciObbligatori(
    segnalaRisultato: boolean
  ): boolean {
    // Recupero le informazioni del componente
    const datiTecnici: IListeDatiTecniciOggettiIstanze = this.datiTecnici;
    const tipologieOpere: string[] = this.tipologieOpere;
    const oggettiIstanza: OggettoIstanza[] = this.oggettiIstanzaConDatiTecnici;
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
   * Getter che recupera gli oggetti istanza che l'utente ha legato all'istanza, ma solo quelli che per le opere di trasporto hanno i dati tecnici.
   * @returns OggettoIstanza[] con gli oggetti istanza del componente filtrati per quelli che hanno i dati tecnici.
   */
  get oggettiIstanzaConDatiTecnici(): OggettoIstanza[] {
    // Recupero la lista degli oggetti istanza a livello componente
    let oggettiIstanza: OggettoIstanza[] = this.oggettiIstanza;

    // Recupero la lista delle opere di trasporto che non hanno i dati tecnici
    const opereTrasportoConDT: TipologieOggettoIstanza[] =
      this.OPERE_CONSTS.OPERE_TRASPORTO_CON_DATI_TECNICI;
    // Filtro gli oggetti istanza andando a rimuovere tutti quelli che hanno una tipologia che non ne ha di default
    oggettiIstanza = oggettiIstanza.filter((oggIst: OggettoIstanza) => {
      // Verifico se l'oggetto istanza è nella lista delle opere con dati tecnici
      const hasOggIstDT: boolean = opereTrasportoConDT.some(
        (tipoOggIst: string) => {
          // Effettuo un match tra il tipo oggetto istazanza con dt e il codice dell'oggetto istanza iterato
          return tipoOggIst === oggIst.tipologia_oggetto.cod_tipologia_oggetto;
          // #
        }
      );

      // Ritorno il risultato del check
      return hasOggIstDT;
      // #
    });

    // Ritorno la lista degli oggetti istanza che hanno effettivamente i dati tecnici
    return oggettiIstanza;
  }

  // #endregion "GETTER E SETTER"
}
