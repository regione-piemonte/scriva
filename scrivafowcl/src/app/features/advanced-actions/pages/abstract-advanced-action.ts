/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { Observable, of, throwError } from 'rxjs';
import { catchError, switchMap, tap } from 'rxjs/operators';
import { AutoUnsubscribe } from 'src/app/core/components';
import { AmbitoService } from 'src/app/features/ambito/services';
import { ScrivaBreadCrumbItem } from 'src/app/shared/components/scriva-bread-crumb/scriva-bread-crumb.component';
import {
  DataQuadro,
  Help,
  Istanza,
  MessaggioUtente,
  NotaIstanza,
} from 'src/app/shared/models';
import {
  AdempimentoService,
  AuthStoreService,
  HelpService,
  IstanzaService,
  MessageService,
} from 'src/app/shared/services';
import { ScrivaNoteService } from 'src/app/shared/services/scriva-note/scriva-note.service';
import { TipoEventoEnum } from 'src/app/shared/utils';
import { ScrivaServerError } from '../../../core/classes/scriva.classes';
import { ScrivaCodesMesseges } from '../../../core/enums/scriva-codes-messages.enums';
import { SoggettoIstanza } from '../../ambito/models';
import { AdvancedActionsChiavi } from '../enums/advanced-actions.enums';
import { AdvancedActionService } from '../services/advanced-action.service';
export abstract class AbstractAdvancedAction
  extends AutoUnsubscribe
  implements OnInit
{
  chiave: AdvancedActionsChiavi;
  titlePage: string;
  codMaschera = '.MSCR012D';
  componente: string;
  tipoEventoEnum = TipoEventoEnum;
  // funzionario: FunzionarioAutorizzato;
  idIstanza: number;
  istanza: Istanza;
  denTitolareIstanza: string;
  helpList: Help[];
  qdr_riepilogo;
  noteIstanza: NotaIstanza[];
  modeSuccess = false;
  confirmSuccessMessagge: string = '';

  /**
   * items per il breadcrumbs
   */
  scrivaBreadCrumbItems: ScrivaBreadCrumbItem[] = [];

  /**
   * chiamate che si effettuano per il load data delle azioni avanzate
   */
  protected loadDataObservables: Observable<any>[] = [];

  /** Gestione jsondataAzioneAvanzata */
  jsondataAzioneAvanzata: Partial<DataQuadro>;
  jsondataAzioneAvanzataSaveWithPut: boolean = false;

  constructor(
    protected router: Router,
    protected fb: FormBuilder,
    protected _istanza: IstanzaService,
    protected adempimentoService: AdempimentoService,
    protected ambitoService: AmbitoService,
    protected authStoreService: AuthStoreService,
    protected helpService: HelpService,
    protected _message: MessageService,
    protected route: ActivatedRoute,
    protected _spinner: NgxSpinnerService,
    protected scrivaNoteService: ScrivaNoteService,
    protected advancedActionService: AdvancedActionService
  ) {
    super();
    const state = this.router.getCurrentNavigation().extras.state;
    if (state) {
      this.idIstanza = state['idIstanza'];
    }
    this.route.paramMap.subscribe((paramMap) => {
      if (!this.idIstanza && paramMap.get('id')) {
        this.idIstanza = +paramMap.get('id');
      }
    });

    this.componente = this.authStoreService.getComponente(); // should always be BO

    // this.funzionario = this.authStoreService.getFunzionario();
  }

  ngOnInit() {
    this.hideSuccessMode();
    this.helpService.setCodMaschera(this.codMaschera);
    // le chiamate qui sono in comune a tutte le azioni avanzate
    const getIstanza = this._istanza.getIstanzaById(this.idIstanza);
    const getSoggettiIstanza = this.ambitoService
      .getSoggettiIstanzaByIstanza(this.idIstanza)
      .pipe(catchError((err) => of([])));
    const getHelpList = this.helpService.getHelpByChiave(
      this.componente + this.codMaschera
    );
    this.loadDataObservables = [getIstanza, getSoggettiIstanza, getHelpList];
  }

  /**
   * Setto istanza, titolare e helplist in base alla risposta del server
   * @param res risultati delle chiamate
   */
  protected loadDataSuccess(res) {
    this.istanza = res[0];
    const jsonData = JSON.parse(this.istanza.json_data);
    this.titlePage = this._istanza.getIstanzaOggettoApp(
      this.istanza,
      this.chiave
    )?.des_oggetto_app
      ? this._istanza.getIstanzaOggettoApp(this.istanza, this.chiave)
          ?.des_oggetto_app
      : 'Titolo non disponibile';
    this.scrivaBreadCrumbItems = [
      {
        emitAction: 'goToSearchPage',
        label: 'Ricerca',
        ariaLabel: 'ricerca',
      },
      {
        label: this.titlePage,
      },
    ];
    //this.titlePage
    this.qdr_riepilogo = jsonData.QDR_RIEPILOGO;
    if (res[1].length > 0) {
      this.getTitolare(res[1]);
    } else {
      this.denTitolareIstanza = 'Non disponibile';
    }
    this.helpList = res[2];
  }

  /**
   * Stampo errore nel caricamento dei dati
   * @param err
   */
  protected loadDataError(err) {
    if (err.error?.code) {
      this._message.showMessage(err.error.code, 'dettaglioContainer', false);
    } else {
      this._message.showMessage('E100', 'dettaglioContainer', true);
    }
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
   * Popolo denTitolareIstanza in base ai soggetti istanza
   * @param soggettiIstanza SoggettoIstanza[]
   */
  getTitolare(soggettiIstanza: SoggettoIstanza[]) {
    const titolareIstanza = soggettiIstanza.find(
      (soggIst) => !soggIst.id_soggetto_padre
    );
    this.denTitolareIstanza =
      titolareIstanza.den_soggetto ||
      titolareIstanza.cognome + ' ' + titolareIstanza.nome;
  }

  /**
   * Apro help secondo parametro in input
   * @param chiave stringa che e' la chiave dell'help
   */
  onHelpClicked(chiave: string) {
    const modalContent =
      this.helpList.find(
        (help) =>
          help.tipo_help.cod_tipo_help === 'MDL' &&
          help.chiave_help.includes(chiave)
      )?.des_testo_help || 'Help non trovato...';

    this._message.showConfirmation({
      title: 'HELP',
      codMess: null,
      content: modalContent,
      buttons: [],
    });
  }

  /**
   * Indietro generica che punta alla search
   */
  onIndietro() {
    this.goToSearchPage();
  }

  /**
   * Vai alla pagina di ricerca
   */
  goToSearchPage() {
    this.router.navigate(['/ricerca', 'ultima']);
  }

  /**
   * saveIstanza che salva istanza e genera evento dopo il salvataggio della istanza stessa
   * @param tipoEvento singola azione avanzata
   * @param codMess stringa che defnisce il codice messaggio in caso di salvataggio andato a buon fine
   * @author Ismaele Bottelli
   * @date 08/01/2025
   * @jira SCRIVA-1583
   * @notes Rifattorizzo il codice per avere una funzione grafica separata da quella asincrona dati.
   *        Anche qua come in molte altre parti le funzioni asincrone sono state scollegate le une dalle altre, usando spinner a caso e con chiusura ancora più a caso.
   *        Sistemo come posso e vado veloce, potrebbero esserci dei problemi in caso di errori, ma non riesco al momento a verificare ogni singolo caso.
   */
  saveIstanza(tipoEvento: TipoEventoEnum, codMess: string) {
    // Lancio lo spinner di caricamento
    this._spinner.show();
    // Lancio la funzione per il salvataggio effettivo dei dati
    this.saveIstanza$(tipoEvento, codMess).subscribe({
      next: (istanza: Istanza) => {
        // Blocco lo spinner, la gestione del flusso è dentro la funzione chiamata
        this._spinner.hide();
        // #
      },
      error: (err) => {
        // Blocco lo spinner, la gestione del flusso è dentro la funzione chiamata
        this._spinner.hide();
        // #
      },
    });
  }

  /**
   * Funzione che gestisce il salvataggio effettivo dei dati per l'istanza in sessione.
   * @param tipoEvento TipoEventoEnum con l'informazione del tipo evento da gestionre.
   * @param codMess string che defnisce il codice messaggio in caso di salvataggio andato a buon fine.
   * @returns Observable<Istanza> con l'informazione dell'istanza salvata.
   * @author Ismaele Bottelli
   * @date 08/01/2025
   * @jira SCRIVA-1583
   * @notes Rifattorizzo il codice per avere una funzione grafica separata da quella asincrona dati.
   *        Anche qua come in molte altre parti le funzioni asincrone sono state scollegate le une dalle altre, usando spinner a caso e con chiusura ancora più a caso.
   *        Sistemo come posso e vado veloce, potrebbero esserci dei problemi in caso di errori, ma non riesco al momento a verificare ogni singolo caso.
   */
  saveIstanza$(
    tipoEvento: TipoEventoEnum,
    codMess: string
  ): Observable<Istanza> {
    // Il flag creaProcedimento deve essere passato solo su avviaProcedimento
    return this._istanza
      .salvaIstanza(
        this.istanza,
        tipoEvento === TipoEventoEnum.AVVIO && !this.istanza.cod_pratica
      )
      .pipe(
        tap((istanza: Istanza) => {
          // Aggiorno localmente le informazioni dell'istanza
          this.istanza = istanza;
          // Lancio la funzione di generazione evento
          this.generaEvento(tipoEvento, codMess);
          // #
        }),
        catchError((e: ScrivaServerError) => {
          // Verifico la composizione dell'errore per gestire la segnalazione
          if (e.error?.code) {
            // Errore specifico
            this._message.showMessage(
              e.error.code,
              'dettaglioContainer',
              false,
              this._getSwapReplace(e.error.code)
            );
          } else {
            // Errore generico
            this._message.showMessage('E100', 'dettaglioContainer', true);
          }
          // Ritorno l'errore per il flusso
          return throwError(e);
          // #
        })
      );
  }

  /**
   * saveIstanza che salva istanza e genera evento dopo il salvataggio della istanza stessa
   * @param tipoEvento singola azione avanzata
   * @returns Observable<Istanza> con l'istanza salvata.
   */
  saveIstanzaWithResponse(tipoEvento: TipoEventoEnum): Observable<Istanza> {
    this._spinner.show();
    // il flag creaProcedimento deve essere passato solo su avviaProcedimento
    return this._istanza.salvaIstanza(
      this.istanza,
      tipoEvento === TipoEventoEnum.AVVIO && !this.istanza.cod_pratica
    );
  }

  /**
   * generaEvento che generaEvento della singola azione avanzata
   * @param tipoEvento singola azione avanzata
   * @param codMess stringa che defnisce il codice messaggio in caso di salvataggio andato a buon fine
   */
  generaEvento(tipoEvento: TipoEventoEnum, codMess: string) {
    this._spinner.show();
    this._istanza
      .generaEvento(this.idIstanza, tipoEvento)
      .pipe(
        // Verificare se errore sun genera evento come deve comportarsi
        catchError((err) => {
          let consoleText;
          if (err.error?.code === 'E037') {
            consoleText = '# ' + err.error.title;
          } else {
            consoleText = this._message.messaggi.find(
              (mess) => mess.cod_messaggio === err.error.code
            ).des_testo_messaggio;
          }
          console.log(consoleText);
          return of(null);
        }),
        switchMap(() => {
          return this._istanza.getIstanzaById(this.idIstanza);
        })
      )
      .subscribe(
        (istanza: Istanza) => {
          this.istanza = istanza;
          this._spinner.hide();
          this.showSuccessMode(codMess);
        },
        (err) => {
          this._spinner.hide();
          if (err.error?.code) {
            this._message.showMessage(
              err.error.code,
              'dettaglioContainer',
              false,
              this._getSwapReplace(err.error.code)
            );
          } else {
            this._message.showMessage('E100', 'dettaglioContainer', true);
          }
        }
      );
  }

  /**
   * tengo allineate le note in memoria per il salvataggio sul conferma
   * @param noteIstanza NotaIstanza[]
   */
  onUpdateNote(noteIstanza: NotaIstanza[] = []) {
    this.noteIstanza = [...noteIstanza];
  }

  /**
   * Recupero le chiamate per salvare le note che
   * devo salvare in caso contrario non faccio partire nessuna chiamata
   * @returns Observable<any>
   */
  saveNoteIstanza(): Observable<any> {
    if (this.noteIstanza?.length > 0) {
      this._spinner.show();
      // recupero le chiamate con un forjoin
      return this.scrivaNoteService.getSaveNoteIstanza(this.noteIstanza);
    }
    return of(null);
  }

  /**
   * Recupero il jsondata della azioni avanzate
   */
  getJsonData() {
    this.advancedActionService
      .getJsonData(this.istanza, this.chiave)
      .subscribe({
        next: (response: Partial<DataQuadro>) => {
          this.jsondataAzioneAvanzata = response;
          this.jsondataAzioneAvanzataSaveWithPut = response.json_data_quadro
            ? true
            : false;
        },
        error: (err) => {},
      });
  }

  /**
   * Recupero il jsondata della azioni avanzate
   */
  setJsonData(json_data_quadro: any) {
    let dataquadro = { ...this.jsondataAzioneAvanzata };
    dataquadro.json_data_quadro = json_data_quadro;
    this.advancedActionService
      .setJsonData(
        this.istanza,
        dataquadro,
        this.jsondataAzioneAvanzataSaveWithPut
      )
      .subscribe({
        next: (res: any) => {},
        error: (e: any) => {
          console.log(e);
        },
      });
  }

  /**
   * Creo la sostituzione opportuna da passare al messageService
   * @param cod codice maessagio
   * @returns Array di oggetto di configurazione per il replace nel messaggio
   */
  protected _getSwapReplace(cod: string = null) {
    let swapPh = [];
    swapPh.push({
      ph: '{PH_COD_PRATICA}',
      swap: this.istanza.cod_pratica ? this.istanza.cod_pratica : '',
    });
    swapPh.push({
      ph: '{PH_STATO_ISTANZA_EVENTO}',
      swap: this.istanza.stato_istanza.codice_stato_istanza
        ? this.istanza.stato_istanza.codice_stato_istanza
        : '',
    });
    swapPh.push({
      ph: '{PH_COD_ISTANZA}',
      swap: this.istanza.cod_istanza ? this.istanza.cod_istanza : '',
    });
    return swapPh;
  }

  /**
   * Setto modeConfirmSuccess e confirmSuccessMessagge per mostrare la schermata di successo
   * e non la parte del form della azione avanzata
   * @param code codice messaggio
   */
  private showSuccessMode(code: string) {
    this.modeSuccess = true;
    const swapPh = this._getSwapReplace(code);
    const m: MessaggioUtente = swapPh
      ? this._message.getMessaggioPlaceholders(code, swapPh)
      : this._message.getMessaggio(code);
    this.confirmSuccessMessagge = m.des_testo_messaggio;
  }

  /**
   * Setto modeConfirmSuccess a false per mostrare la parte del form della azione avanzata
   * e non la la schermata di successo
   */
  private hideSuccessMode() {
    this.modeSuccess = false;
  }

  /**
   * Funzione di supporto che gestisce gli errori generati dalla chiamata ai servizi.
   * @param e ScrivaServerError con l'oggetto d'errore generato dalla chiamata.
   * @param target string con l'identificativo per un target specifico.
   * @param usePH boolean come flag per andare a gestire eventuali placholder nella messaggistica.
   */
  protected onServiziError(
    e?: ScrivaServerError,
    target?: string,
    usePH: boolean = false
  ) {
    // Definisco le informazioni per il messaggio d'errore
    const errorCode: string = e?.error?.code;
    const m: string = errorCode ? errorCode : ScrivaCodesMesseges.E100;
    const fade: boolean = !errorCode;
    target = target ?? 'dettaglioContainer';

    // Verifico se devo gestire placholder
    if (!usePH) {
      // Richiamo la visualizzazione del messaggio
      this._message.showMessage(m, target, fade);
      // #
    } else {
      // Gestisco il messaggio con placholder
      this._message.showMessage(
        errorCode,
        target,
        fade,
        this._getSwapReplace(errorCode)
      );
    }
  }
}
