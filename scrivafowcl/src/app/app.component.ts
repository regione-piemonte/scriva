/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, HostListener, OnInit } from '@angular/core';
import { ChildActivationEnd, Router } from '@angular/router';
import { clone } from 'lodash';
import { NgxSpinnerService } from 'ngx-spinner';
import { Subscription } from 'rxjs';
import { filter, switchMap, tap } from 'rxjs/operators';
import { version } from '../../package.json';
import { AutoUnsubscribe } from './core/components';
import { NotificaApplicativa } from './features/notifiche/models/notifica-applicativa';
import { NOTIFICHE_CONSTS } from './features/notifiche/pages/notifiche/utilities/notifiche.consts.js';
import { NotificheService } from './features/notifiche/services/notifiche.service';
import { CommonConsts } from './shared/consts/common-consts.consts.js';
import { ScrivaComponenteApp } from './shared/enums/scriva-utilities/scriva-utilities.enums.js';
import {
  Ambito,
  Compilante,
  Help,
  IFunzionarioAutorizzato,
} from './shared/models';
import {
  AmbitiService,
  AuthStoreService,
  HelpService,
  MessageService,
} from './shared/services';
import { AuthService } from './shared/services/auth/auth.service.js';
import { GeecoService } from './shared/services/geeco/geeco.service.js';
import { GeecoReturnParams } from './shared/services/geeco/utilities/geeco.enums.js';
import { IGeecoReturnParams } from './shared/services/geeco/utilities/geeco.interfaces.js';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent extends AutoUnsubscribe implements OnInit {
  /** Oggetto di costanti condivise nell'applicazione. */
  C_C = new CommonConsts();
  /** Costanti di comodo per la gestione del componente. */
  N_C = NOTIFICHE_CONSTS;

  modeList = ['over', 'push', 'slide'];
  positionList = ['left', 'right', 'top', 'bottom'];
  sidebarMode: string;
  sidebarPosition = this.positionList[0];
  SIDEBAR_BREAKPOINT = 800; // in px. check also $sidebar-breakpoint in scss file

  opened = false;
  openedNotifiche = false;
  closeOnClickOutside = false;
  closeOnClickBackdrop = true;
  showBackdrop = false;
  animate = false;
  trapFocus = false;
  autoFocus = false;
  autoCollapseHeight = 500;
  autoCollapseWidth = 500;
  ariaLabel = 'Lateral Navigation Bar';
  dock = false;

  ambiti: Ambito[] = [];
  activeLinkFromUrl = null;

  iconBasePath = 'assets/icon-sidebar-';

  routeSubscription;
  selected = 'accreditamento';
  redirectPpay = false;
  redirectGeeco = false;
  ppayQueryParams;

  /** IGeecoReturnParams che mappa i possibili query params di rientro alla chiusura di Geeco. */
  geecoQueryParams: IGeecoReturnParams;

  targetUrl: string;
  componente: ScrivaComponenteApp = ScrivaComponenteApp.frontOffice;
  appTitle = 'Scrivania del richiedente';

  compilante: Compilante;
  funzionario: IFunzionarioAutorizzato;
  loadContent = true;
  errorMessage = '';

  notificheApplicative: NotificaApplicativa[];
  notificheMostrate: number;
  private _subscription: Subscription;
  notificheConfigurazioniService: any;

  /**
   * Costruttore.
   */
  constructor(
    private ambitiService: AmbitiService,
    private authService: AuthService,
    private authStoreService: AuthStoreService,
    private _geeco: GeecoService,
    private helpService: HelpService,
    private messageService: MessageService,
    private notificheService: NotificheService,
    private router: Router,
    private _spinner: NgxSpinnerService
  ) {
    super();
    console.log('Version: ', version);
  }

  /** NgOnInit. */
  ngOnInit() {
    // Imposto il valore del componente applicativo
    this.componente = this.authStoreService.setComponenteByHostOrSession();

    if (this.authStoreService.isBoComponent()) {
      this.appTitle = 'Scrivania del funzionario';
    }
    /* MOCK */
    // this.componente = 'BO';
    // this.appTitle = 'Scrivania del funzionario';
    /* ---- */
    console.log('Accesso effettuato su componente', this.componente);

    // Lancio l'inizializzazione delle funzioni di controllo sulla navigazione
    this.initNavigationChecks();

    // Per gestire correttamente l'animazione si mette un timeout per gestire la digest visuale di Angular
    setTimeout(() => {
      this.animate = true;
    });

    // Lancio il set delle informazioni per la sidebar
    this.setSidebarProperties();

    // Rimango in ascolto degli eventi di navigazine applicativa
    this.initRouteSubscription();
    // Inizializzo le informazioni del compilante scriva
    this.initCompilanteScriva();

    // Inizializzo le notifiche
    this.notificheService.initNotifiche();
  }

  /**
   * ###########################################################
   * INIT DELLE FUNZIONI DI CHECK PER LA NAVIGAZIONE APPLICATIVA
   * ###########################################################
   */

  /**
   * Funzione di init che definisce le logiche di controllo e navigazione automatica dell'applicativo.
   */
  private initNavigationChecks() {
    // Invoco le funzioni di check specifiche per la navigazione
    this.initNavigationAmbito();
    this.initNavigationPPay();
    this.initNavigationGeeco();
  }

  /**
   * Funzione di init check per la navigazione dell'ambito.
   */
  private initNavigationAmbito() {
    // Verifico l'hash della location per la navigazione
    if (window.location.hash) {
      // Recupero il target URL che potrebbe contenere informazioni sull'ambito
      this.targetUrl = window.location.hash.substring(2);
      // Verifico se l'url contiene "ambito"
      if (this.targetUrl.includes('ambito')) {
        // Lo contiene, recupero parte dell'url per il target applicativo
        this.targetUrl = this.targetUrl.substring(0, 10);
      }
    }
  }

  /**
   * Funzione di init check per la navigazione per PPay.
   */
  private initNavigationPPay() {
    // Verifico se per l'href è presente la chiave 'idPagamento'
    if (window.location.href.indexOf('idPagamento') > -1) {
      // Esiste, recupero i parametri definiti all'interno dell'url
      const urlParams = new URLSearchParams(window.location.search);
      // Creo la confgurazione dati per PPay
      this.ppayQueryParams = {
        idPagamento: urlParams.get('idPagamento'),
        descEsito: urlParams.get('descEsito'),
        codEsito: urlParams.get('codEsito'),
        source: urlParams.get('source'),
      };
      // Imposto il flag di redirect a PPay
      this.redirectPpay = true;
    }
  }

  /**
   * Funzione di init check per la navigazione Geeco.
   */
  private initNavigationGeeco() {
    // Verifico se per l'href è prente la chiave 'geeco'
    if (window.location.href.indexOf('geeco') > -1) {
      // Esiste, definisco la url per il redirect di Geeco
      const urlReturnGeeco = window.location.href;
      // Recupero i parametri dall'url
      const params = urlReturnGeeco.split('?');

      // Recupero i parametri definiti all'interno dell'url
      const urlParams = new URLSearchParams(params[1]);
      const idIstanzaUrl: string = urlParams.get(GeecoReturnParams.idIstanza);
      const idIstanza: number = this.urlParamNumber(idIstanzaUrl);
      const idTipoQuadroUrl: string = urlParams.get(
        GeecoReturnParams.idTipoQuadro
      );
      const idTipoQuadro: number = this.urlParamNumber(idTipoQuadroUrl);
      const codQuadro: string = urlParams.get(GeecoReturnParams.codQuadro);

      // Definisco i parametri per geeco
      this.geecoQueryParams = { idIstanza, codQuadro, idTipoQuadro };

      // Imposto il flag di redirect
      this.redirectGeeco = true;
    }
  }

  /**
   * Funzione di supporto che gestisce la corretta formattazione di un query params in numero.
   * @param param string con il parametro da convertire.
   * @returns number con il valore numero del parametro.
   */
  private urlParamNumber(param: string): number {
    // Verifico l'input
    if (param == undefined) {
      // Non è definito
      return undefined;
    }

    // Il valore esiste tento di convertirlo
    return Number(param);
  }

  /**
   * #################################################
   * INIT PER IMPOSTARE IL LISTENER AL CAMBIO DI ROUTE
   * #################################################
   */

  /**
   * Funzione di init per la subscription agli eventi di cambio route applicativa.
   */
  private initRouteSubscription() {
    // Lancio lo spinner di caricamento
    this._spinner.show();

    // Registro un ascoltatore per il cambi di route applicativa
    this.routeSubscription = this.router.events
      // Estraggo solo gli eventi di attivazione completata per i child route dei componenti padri
      .pipe(filter((event) => event instanceof ChildActivationEnd))
      .subscribe({
        next: (event: ChildActivationEnd) => {
          // Verifico se nelle configurazioni esiste un path specifico
          if (event.snapshot.routeConfig?.path) {
            // Recupero il segmento url dalla configurazione
            const urlsegment = event.snapshot.url[1] || event.snapshot.url[0];
            // Imposto il path di navigazione a livello di componente
            this.selected = urlsegment.path.toLowerCase();
          }

          // Chiudo lo spinner di caricamento
          this._spinner.hide();
        },
        error: (e: any) => {
          // Gestisco gli errori
          this._showError(e);
          // Chiudo lo spinner di caricamento
          this._spinner.hide();
        },
      });
  }

  /**
   * ########################################################
   * FUNZIONI DI SCARICO E SALVATAGGIO DATI COMPILANTE SCRIVA
   * ########################################################
   */

  /**
   * Funzione d'inizializzazione dei dati del compilante all'intero dell'applicazione SCRIVA.
   */
  private initCompilanteScriva() {
    // Lancio lo spinner di caricamento
    this._spinner.show();

    // Lancio la chiamata allo scarico dati del compilante per l'applicazione.
    this.authService
      .getCompilanteBySession()
      .pipe(
        tap((compilante: Compilante) => {
          // Salvo all'interno del servizio in sessione il cf del compilante
          this.authStoreService.setLoggedUserCf(compilante.cf_compilante);

          // A seconda del tipo di componente applicativo, scarico delle specifiche informazioni del soggetto
          if (this.componente === ScrivaComponenteApp.backOffice) {
            // Recupero i dati come funzionario
            this._getFunzionario();
            // #
          } else {
            // Recuperi i dati del compilante
            this._getCompilante(compilante);
            // #
          }
        }),
        switchMap(() => {
          // A seguito dello scarico dati del compilante, recupero i dati per ambiti
          return this.ambitiService.getAmbiti();
        })
      )
      .subscribe({
        next: (ambiti: Ambito[]) => {
          // Se esiste almeno un ambito
          if (ambiti?.length > 0) {
            // Imposto localmente gli ambiti per la sessione applicativa
            this.ambiti = ambiti;
          }
          // Chido lo spinner
          this._spinner.hide();
        },
        error: (e: any) => {
          // Gestisco gli errori
          this._showError(e);
          // Chido lo spinner
          this._spinner.hide();
        },
      });
  }

  private _getCompilante(compilante: Compilante) {
    this._spinner.hide();
    this.compilante = compilante;
    this.authStoreService.setCompilante(compilante);

    if (this.redirectPpay) {
      this.router.navigate(['/pagamento-istanza'], {
        queryParams: this.ppayQueryParams,
      });
      this.ppayQueryParams = null;
      return;
    }

    // Verifico la navigazione per geeco
    if (this.redirectGeeco) {
      // Gestisco il giro per geeco
      this.navigateToGeeco(this.geecoQueryParams);
      // Interrompo il flusso della funzione
      return;
    }

    if (compilante.gestUID) {
      if (this.targetUrl) {
        this.router.navigate([this.targetUrl]);
      } else {
        this.router.navigate(['home']);
      }
    } else {
      this.router.navigate(['accreditamento']);
    }
  }

  private _getFunzionario() {
    this._spinner.show();
    this.authService.getFunzionarioBySession().subscribe(
      (funzionario: IFunzionarioAutorizzato) => {
        this._spinner.hide();
        this.funzionario = funzionario;
        this.authStoreService.setFunzionario(this.funzionario);

        if (this.redirectGeeco && this.geecoQueryParams) {
          // Gestisco il giro per geeco
          this.navigateToGeeco(this.geecoQueryParams);
          // Interrompo il flusso della funzione
          return;
        }

        if (this.targetUrl) {
          if (this.redirectGeeco) {
            this.router.navigate(['/progetto-geeco-istanza'], {
              queryParams: this.geecoQueryParams,
            });
            this.geecoQueryParams = null;
            return;
          } else {
            this.router.navigate([this.targetUrl]);
          }
        } else {
          this.router.navigate(['home']);
        }
      },
      (err) => {
        this._showError(err);
        this._spinner.hide();
      }
    );
  }

  /**
   * Funzione di comodo che gestisce i dati e naviga a seguito il rientro da Geeco.
   * @param geecoQueryParams IGeecoReturnParams con i parametri da gestire ritornati da geeco.
   */
  private navigateToGeeco(geecoQueryParams: IGeecoReturnParams) {
    // Creo una copia dell'oggetto in input
    geecoQueryParams = clone(geecoQueryParams);
    // Recupero dai query param di geeco l'id istanza
    const { idIstanza } = geecoQueryParams;
    // Invoco il redirect per la gestione di geeco
    this._geeco.navigateGeecoToScriva(idIstanza, geecoQueryParams);

    // Resetto la configurazione del componente
    this.geecoQueryParams = null;
  }

  private _showError(err) {
    this.loadContent = false;
    const errorCode = err.error?.code || 'E100';
    this.errorMessage =
      this.messageService.messaggi?.find(
        (msg) => msg.cod_messaggio === errorCode
      )?.des_testo_messaggio ||
      'Errore inaspettato nella gestione della richiesta. Riprova a breve.';
  }

  onHelpClicked() {
    let chiaveHelp: string = '';
    const codMaschera = this.helpService.getCodMaschera();
    const codContesto = this.helpService.getCodContesto();
    if (codContesto != undefined) {
      chiaveHelp = this.componente + codContesto + codMaschera;
    } else {
      chiaveHelp = this.componente + codMaschera;
      const codAmbito = this.selected.toUpperCase();

      if (codAmbito === 'AMB' || codAmbito === 'AE') {
        if (codMaschera === '.MSCR003D' || codMaschera === '.MSCR002D') {
          chiaveHelp = chiaveHelp + '.' + codAmbito;
          if (codMaschera === '.MSCR003D') {
            chiaveHelp = chiaveHelp + '_preferiti';
          } else {
            chiaveHelp = chiaveHelp + '_tutti';
          }
        }
      }
    }

    console.log('chiaveHelp: ', chiaveHelp);

    // Mappo i dettagli con le relative sostituzioni in caso di codice maschera vinca per ricavare il corretto messaggio di helper per la maschera  (è possibile aggiungere nuove voci alla mappa se servisse)
    // SCRIVA-1545, solo caso VINCA al momento
    const mappaSostituzioni = {
      '.MSCR020F': '.QDR_DETT_OGGETTO',
      '.MSCR019F': '.QDR_DETT_OGGETTO',
    };

    // Verifico se `codMaschera` contiene uno dei dettagli e sostituisco se necessario - SCRIVA-1545, solo caso VINCA al momento
    for (const [chiave, nuovaChiave] of Object.entries(mappaSostituzioni)) {
      if (codMaschera.includes(chiave)) {
        chiaveHelp = chiaveHelp.replace(chiave, nuovaChiave);
        break; // Uscita dal ciclo dopo la prima sostituzione
      }
    }

    // Tento di scaricare gli helper collegati alla chiave
    this.helpService.getHelpByChiave(chiaveHelp).subscribe(
      (res: Help[]) => {
        // Lancio la funzione di gestione per l'apertura dell'helper
        this.apriHelperTestata(res);
        // #
      },
      (err) => {
        if (err.error?.code) {
          this.messageService.showMessage(err.error.code, 'main', false);
        } else {
          this.messageService.showMessage('E100', 'main', true);
        }
      }
    );
  }

  /**
   * Funzione che gestisce l'apertura di un helper da una lista di helper scaricati.
   * @param helpers Help[] con la lista degli helper scaricati.
   */
  private apriHelperTestata(helpers: Help[]) {
    // Recupero l'helper cercando per il codice "maschera": 'MSC'
    const helper: Help = helpers.find((help: Help) => {
      // Verifico e recupero per il codice dell'helper maschera
      return help.livello_help.cod_livello_help === 'MSC';
    });

    // Definisco un fallback in caso in cui non ci sia l'helper
    const fallback: string = 'Help non trovato...';
    // Recupero la descrizione dell'helper
    const modalContent: string = helper?.des_testo_help ?? fallback;
    // Apro la modale con l'helper
    this.messageService.showConfirmation({
      title: 'HELP',
      codMess: null,
      content: modalContent,
      buttons: [],
    });
  }

  /**
   * Funzione agganciata alla voce di apertura del profilo utente.
   */
  apriProfilo() {
    // Lancio la navigazione dell'applicazione verso la pagina del profilo
    this.router.navigate(['/profilo']);
  }

  /**
   * Funzione che richiama il logout applicativo.
   */
  logout() {
    // Richiamo la funzione del servizio
    this.authService.logout(this.componente);
  }

  /**
   *
   * @param elementName
   */
  goToElement(elementName: string) {
    const element = document.getElementById(elementName);
    element.setAttribute('tabindex', '0');
    element.scrollIntoView();
    element.focus();
    element.removeAttribute('tabindex');
  }

  // side menu
  @HostListener('window:resize', [])
  onResize() {
    this.setSidebarProperties();
  }

  setSidebarProperties() {
    this.dock = window.innerWidth > this.SIDEBAR_BREAKPOINT ? this.dock : false;
    this.sidebarMode =
      window.innerWidth > this.SIDEBAR_BREAKPOINT
        ? this.modeList[1]
        : this.modeList[0];
    this.opened = window.innerWidth > this.SIDEBAR_BREAKPOINT ? true : false;
    this.showBackdrop =
      window.innerWidth > this.SIDEBAR_BREAKPOINT ? false : true;
  }

  toggleOpen() {
    this.opened = !this.opened;
  }

  toggleExpansion() {
    this.dock = !this.dock;
  }

  selectItem(name: string) {
    if (this.sidebarMode === this.modeList[0]) {
      this.opened = false;
    }
    this.router.navigate(['/' + name], {});
  }

  /**
   * Funzione di toggle per apertura e chiusura della sidebare.
   */
  toggleSidebarNotifiche() {
    // Toggle di visibilità per la sidebar
    this.openedNotifiche = !this.openedNotifiche;
    // Se la sidebar è chiusa
    if (this.openedNotifiche) {
      // Nascondo la scrollbar della pagina
      document.body.classList.add('hidescrollbar');
    } else {
      // Visualizzo la scrollbar della pagina
      document.body.classList.remove('hidescrollbar');
    }
  }

  /**
   * Funzione collegata al template.
   * Rimane in ascolto dell'evento di click sul dettaglio di una notifica.
   */
  onDettaglioNotifica() {
    // Richiamo il toggle della sidebar per chiuderla
    this.toggleSidebarNotifiche();
  }

  /**
   * Funzione collegata al template.
   * Rimane in ascolto dell'evento di click sul dettaglio procedimento di una notifica.
   */
  onVaiAllaPratica() {
    // Richiamo il toggle della sidebar per chiuderla
    this.toggleSidebarNotifiche();
  }

  /**
   * Funzione collegata al template.
   * Rimane in ascolto dell'evento di click sul dettaglio procedimento di una notifica.
   */
  onVaiATutteNotifiche() {
    // Richiamo il toggle della sidebar per chiuderla
    this.toggleSidebarNotifiche();
  }
}
