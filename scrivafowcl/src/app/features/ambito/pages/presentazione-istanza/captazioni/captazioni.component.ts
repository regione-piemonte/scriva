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
import { iif, Observable, of, throwError } from 'rxjs';
import { catchError, mergeMap, switchMap, take, tap } from 'rxjs/operators';
import { AmbitoService } from 'src/app/features/ambito/services';
import { CONFIG } from 'src/app/shared/config.injectiontoken';
import { StepConfig } from 'src/app/shared/models';
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
import { ScrivaStatiIstanza } from '../../../../../shared/enums/scriva-utilities/scriva-utilities.enums';
import { FormioService } from '../../../../../shared/services/formio/formio.service';
import { GeecoService } from '../../../../../shared/services/geeco/geeco.service';
import { LoggerService } from '../../../../../shared/services/logger.service';
import { OggettoIstanza, Opera } from '../../../models';
import { OpereService } from '../../../services/opere/opere.service';
import { IDTOperaModalParams } from '../opere/modals/dati-tecnici-opera-modal/utilities/dati-tecnici-opera-modal.interfaces';
import { OpereComponent } from '../opere/opere.component';
import { IndLivelliOpere } from '../opere/utilities/opere.enums';
import { CaptazioniOperaModalComponent } from './modals/captazioni-opera-modal/captazioni-opera-modal.component';
import {
  IDTCaptazioniOperaModalCallbacks,
  IDTOperaSalvataggioSezione,
} from './modals/captazioni-opera-modal/utilities/captazioni-opera-modal.interfaces';
import { CaptazioniConsts } from './utilities/captazioni.consts';
import { DatiSezioneCaptazioneCondivisi } from './utilities/captazioni.enums';
import { OpereVerificheStepService } from '../../../services/opere/opere-verifiche-step.service';
import { PresentazioneIstanzaService } from '../../../services/presentazione-istanza/presentazione-istanza.service';

@Component({
  selector: 'app-captazioni',
  templateUrl: './captazioni.component.html',
  styleUrls: ['../opere/opere.component.scss'],
  providers: [OpereVerificheStepService],
})
export class CaptazioniComponent
  extends OpereComponent
  implements OnInit, OnDestroy, AfterViewInit
{
  /** CaptazioniConsts con le informazioni costanti per il componente. */
  public OPERE_CONSTS = new CaptazioniConsts();

  /** string costante che definisce il contenitore per agganciare i messaggi di segnalazione. */
  readonly ALERT_TARGET_MODALE: string = 'captazione-content';

  /**
   * string che definisce il nome del componente di riferimento.
   * @override
   */
  protected componentName: string = 'CaptazioniComponent';

  /**
   * any per gestire il componente da usare per la modale di dettaglio delle opere.
   * @override
   */
  protected componenteModale: any = CaptazioniOperaModalComponent;

  /**
   * Costruttore.
   */
  constructor(
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
    opereVerificheStep: OpereVerificheStepService,
    route: ActivatedRoute,
    stepManager: StepManagerService,
    spinner: NgxSpinnerService,
    presentazioneIstanzaService: PresentazioneIstanzaService,
    @Inject(CONFIG) protected injConfig: StepConfig,

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
      spinner,
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
   * Funzione di set che va a definire la struttura della tabella per la ricerca delle opere.
   * @notes Gli oggetti gestiti da questa tabella sono: Opera.
   * @override Override delle logiche di definizione delle colonne delle tabelle.
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
        name: 'Tipologia di captazione',
        prop: 'tipologia_oggetto.des_tipologia_oggetto',
      },
      { name: 'Denominazione', prop: 'den_oggetto' },
      { name: 'Descrizione', prop: 'des_oggetto' },
      {
        name: 'Codice ROC',
        // prop: 'cod_scriva',
        cellTemplate: this.codiceRilievoOperaTemplate,
      },
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
   * @override Override delle logiche di definizione delle colonne delle tabelle.
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

  /**
   * Funzione che genera le configurazioni con i parametri da passare alla modale dei dati tecnici.
   * @param opera Opera con il riferimento all'oggetto per la gestione dei dati tecnici.
   * @param oggettoIstanza OggettoIstanza con il riferimento all'oggetto per la gestione dei dati tecnici.
   * @param readOnly boolean con l'indicazione di attivare la gestione dei dati in sola lettura.
   * @override Override della funzione originale, con l'aggiunta delle configurazioni specifiche per questo componente.
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
      // PARAMETRI CUSTOM PER QUESTO COMPONENTE
      showModalSalva: false,
    };

    // Ritorno la configurazione
    return modalConfig;
  }

  /**
   * Funzione che definisce le callback che verranno invocate al salvataggio o alla chiusura della modale dei dati tecnici.
   * @param opera Opera con il riferimento all'oggetto Opera per la gestione dei dati tecnici.
   * @param oggettoIstanza OggettoIstanza con il riferimento all'oggetto Opera per la gestione dei dati tecnici.
   * @returns IDTCaptazioniOperaModalCallbacks con le callback per il savataggio dati.
   * @override Override che definisce un diverso tipo di oggetto di gestione dati tecnici per il salvataggio dalla modale.
   */
  protected datiTecniciCallbacks(
    opera: Opera,
    oggettoIstanza: OggettoIstanza
  ): IDTCaptazioniOperaModalCallbacks {
    // Definisco l'oggetto con le callback per il salvataggio
    const callbacks: IDTCaptazioniOperaModalCallbacks = {
      saveDatiTecniciOperaPartial: (
        datiTecniciDaSalvare: IDTOperaSalvataggioSezione
      ) => {
        // Lancio la funzione interna
        this.saveDatiTecniciOpera(datiTecniciDaSalvare, opera, oggettoIstanza);
        // #
      },
      onModaleChiusa: () => {
        // Vado a resettare la variabile che contiene i dati tecnici per la modale
        this.sourceDataForModal = null;
        // #
      },
    };

    // Ritorno le callback
    return callbacks;
  }

  /**
   * Funzione che gestisce le logiche per l'aggiornamento dei dati tecnici avvenuto all'interno della modale dei dati tecnici dell'opera.
   * @param datiTecniciDaSalvare IDTOperaSalvataggioSezione con le informazioni relative al salvataggio dati tecnici dell'opera.
   * @param opera Opera con le informazioni dell'oggetto opera passato alla modale.
   * @param oggettoIstanza OggettoIstanza con i dati dell'oggetto istanza passato alla modale.
   * @override
   */
  protected saveDatiTecniciOpera(
    datiTecniciDaSalvare: IDTOperaSalvataggioSezione,
    opera: Opera,
    oggettoIstanza: OggettoIstanza
  ) {
    // Verifico l'input
    if (!datiTecniciDaSalvare) {
      // Manca la configurazione
      return;
    }

    // Estraggo le informazioni dall'oggetto dei dati tecnici da salvare
    const sezione: string = datiTecniciDaSalvare?.sezione;

    // Verifico se i dati da salvare sono specifici per "DATI_IDENTIFICATIVI"
    if (sezione === DatiSezioneCaptazioneCondivisi.DATI_IDENTIFICATIVI) {
      // Lancio lo spinner
      this.spinner.show();

      // Lancio la funzione di aggiornamento dell'oggetto istanza
      this.editOggettoIstanza(opera, oggettoIstanza, datiTecniciDaSalvare)
        .pipe(take(1))
        .subscribe({
          next: () => {
            // Chiudo lo spinner
            this.spinner.hide();
            // Lancio la funzione di aggiornamento dati di dettaglio
            this.updateDetails(opera, oggettoIstanza, datiTecniciDaSalvare);
            // #
          },
          error: (e: ScrivaServerError) => {
            // Chiudo lo spinner
            this.spinner.hide();
            // Gestisco l'errore
            this.onServiziError(e, this.ALERT_TARGET_MODALE);
            // #
          },
        });
      // #
    } else {
      // Lancio la funzione di aggiornamento dati di dettaglio
      this.updateDetails(opera, oggettoIstanza, datiTecniciDaSalvare);
      // #
    }
  }

  /**
   * Funzione pensata per aggiornare le informazioni specifiche delle informazioni dell'oggetto Opera e dell'oggetto OggettoIstanza.
   * @param oggetto Opera con l'oggetto dell'opera d'aggiornare.
   * @param oggettoIstanza OggettoIstanza con l'oggetto dell'opera d'aggiornare.
   * @param datiTecniciDaSalvare IDTOperaSalvataggioSezione con le informazioni relative al salvataggio dati tecnici dell'opera.
   * @returns Observable<any> con il risultato del salvataggio.
   */
  editOggettoIstanza(
    oggetto: Opera,
    oggettoIstanza: OggettoIstanza,
    datiTecniciDaSalvare: IDTOperaSalvataggioSezione
  ): Observable<any> {
    // Definisco una flag per gestire l'aggiornamento dei dati
    let updateOggettoIstanzaFlg = false;

    // Estraggo dall'input le informazioni per il controllo dati
    const datiTecnici: any = datiTecniciDaSalvare?.datiTecnici;
    const datiSezione: any = datiTecniciDaSalvare?.datiSezione;
    // Definisco delle variabili di supporto per l'aggiornamento dei dati
    const localitaDT: any = datiSezione.localizzazione?.localita;
    const denominazioneDT: any = datiSezione.denominazione;
    const idOggetto: string = datiTecnici.id_oggetto.toString();

    // Verifico se la località definita è diversa rispetto a quella sulle informazioni dell'oggetto istanza
    if (oggettoIstanza.ubicazione_oggetto[0].des_localita !== localitaDT) {
      // Aggiorno il dato per l'oggetto istanza
      oggettoIstanza.ubicazione_oggetto[0].des_localita = localitaDT;
      // E' necessario salvare le informazioni
      updateOggettoIstanzaFlg = true;
    }

    // Verifico se è stata definita una denominazione per l'opera
    if (oggettoIstanza.den_oggetto !== denominazioneDT) {
      // Aggiorno il dato per l'oggetto istanza
      oggettoIstanza.den_oggetto = denominazioneDT;
      // E' necessario salvare le informazioni
      updateOggettoIstanzaFlg = true;
    }

    // Definisco un flag per vedere se l'opera è la stessa gestita per i dati tecnici
    const sameOperaDT: boolean = oggetto.id_oggetto.toString() === idOggetto;

    // Verifico se l'oggetti istanza non è da salvare, e l'opera è la stessa dei dati tecnici
    if (!updateOggettoIstanzaFlg && sameOperaDT) {
      // Non devo salvare niente
      return of(true);
      // #
    } else if (!updateOggettoIstanzaFlg && !sameOperaDT) {
      /**
       * @author Ismaele Bottelli
       * @date 08/05/2025
       * @jira SCRIVA-1642
       * @deprecated Codice deprecato.
       * @notes Con la modifica e l'utilizzo dell'id_oggetto questo pezzo di codice dovrebbe essere deprecato poiché il cod_scriva non è più usato.
       *        Il cod_scriva poteva essere modificato nel tempo, per cui la necessità di poter aggiornare l'oggetto Opera/Oggetto aggiornando solo il cod_scriva.
       *        In questo caso le condizioni non dovrebbero più permettere al flusso logico di entrare in questa condizione.
       *        Per il momento lascio attivo il codice in caso fosse necessario ripristinare il vecchio flusso, si potrebbe pensare ad un refactor per poi
       *        rimuovere questo codice inutilizzato.
       */
      // Lancio la funzione di aggiornamento dell'opera
      return this.aggiornaOperaByDT(oggetto, oggetto.cod_scriva);
      // #
    } else {
      // Lancio la funzione di aggiornamento dell'oggetto istanza e dell'opera
      return this.aggiornaOggettoIstanzaEOperaByDT(
        oggetto,
        oggettoIstanza,
        datiTecniciDaSalvare
      );
    }
  }

  /**
   * Funzione che gestisce l'aggiornamento dati per un opera a seguito della modifica dei dati tecnici.
   * @param opera Opera con le informazioni da salvare.
   * @param codiceScrivaDT string con le informazioni da salvare per l'opera.
   * @returns Observable<Opera> con le informazioni salvate.
   * @author Ismaele Bottelli
   * @date 08/05/2025
   * @jira SCRIVA-1642
   * @deprecated Codice deprecato.
   * @notes Con la modifica e l'utilizzo dell'id_oggetto questo pezzo di codice dovrebbe essere deprecato poiché il cod_scriva non è più usato.
   *        Il cod_scriva poteva essere modificato nel tempo, per cui la necessità di poter aggiornare l'oggetto Opera/Oggetto aggiornando solo il cod_scriva.
   *        In questo caso le condizioni non dovrebbero più permettere al flusso logico di entrare in questa condizione.
   *        Per il momento lascio attivo il codice in caso fosse necessario ripristinare il vecchio flusso, si potrebbe pensare ad un refactor per poi
   *        rimuovere questo codice inutilizzato.
   */
  private aggiornaOperaByDT(
    opera: Opera,
    codiceScrivaDT: string
  ): Observable<Opera> {
    // Aggiorno le informazioni con i dati tecnici
    opera.cod_scriva = codiceScrivaDT;
    // Lancio il salvataggio delle informazioni per l'opera a seguito delle modifiche tramite dati tecnici
    return this._ambito.salvaOpera(opera, this.codAdempimento).pipe(
      catchError((e: ScrivaServerError) => {
        // Gestisco la messaggistica
        this.onServiziError(e, this.ALERT_TARGET_MODALE);
        // Ritorno errore
        return throwError(e);
        // #
      }),
      // Aggiorno le informazioni per referenza dell'oggetto Opera
      tap((res) => (opera = res))
    );
  }

  /**
   * Funzione che gestisce l'aggiornamento dati per un opera a seguito della modifica dei dati tecnici.
   * @param oggetto Opera con l'oggetto dell'opera d'aggiornare.
   * @param oggettoIstanza OggettoIstanza con l'oggetto dell'opera d'aggiornare.
   * @param datiTecniciDaSalvare IDTOperaSalvataggioSezione con le informazioni relative al salvataggio dati tecnici dell'opera.
   * @returns Observable<Opera> con le informazioni salvate.
   */
  private aggiornaOggettoIstanzaEOperaByDT(
    opera: Opera,
    oggettoIstanza: OggettoIstanza,
    datiTecniciDaSalvare: IDTOperaSalvataggioSezione
  ): Observable<any> {
    // Estraggo dall'input le informazioni per il controllo dati
    const datiSezione: any = datiTecniciDaSalvare?.datiSezione;
    // Definisco delle variabili di supporto per l'aggiornamento dei dati
    const localitaDT: any = datiSezione.localizzazione?.localita;
    const denominazioneDT: any = datiSezione.denominazione;

    const salvaOI$ = this._ambito.salvaOggettoIstanza(oggettoIstanza).pipe(
      catchError((e: ScrivaServerError) => {
        // Gestisco la messaggistica
        this.onServiziError(e, this.ALERT_TARGET_MODALE);
        // Ritorno errore
        return throwError(e);
        // #
      }),
      tap((oggettoIstanzaSalvato: OggettoIstanza) => {
        // Dalla risposta ottenuta, prendo le informazioni e le aggiorno nell'oggetto istanza
        oggettoIstanza = oggettoIstanzaSalvato;
        // #
      }),
      switchMap(() => {
        // Definisco le condizioni per gestire il salvataggio dell'opera per i dati del formio
        // #1 - flg_aggiorna_oggetto è true: configurazione da DB che forza l'aggiornamento dell'opera in questo caso
        const case1 = this.istanza.stato_istanza.flg_aggiorna_oggetto;
        // #2 - L'istanza è in stato di bozza e l'id_istanza_aggiornamento dell'Opera è lo stesso id dell'istanza
        const idStatoIstanza: number =
          this.istanza.stato_istanza.id_stato_istanza;
        const istanzaInBozza = idStatoIstanza === ScrivaStatiIstanza.BOZZA;
        const stessoIdIstAggiornamento =
          opera.id_istanza_aggiornamento === this.istanza.id_istanza;
        const case2 = istanzaInBozza && stessoIdIstAggiornamento;

        // Verifico le casistiche per l'aggiornamento dell'opera
        if (case1 || case2) {
          // TODO: @Ismaele => chi ha sviluppato per qualche motivo ha usato sto codice con le "i", magari mettere un enumeratore è meglio.
          // L'opera è da aggiornare, ritorno un codice specifico
          return of('i');
          // #
        }
        /**
         * TODO: @Ismaele => Insieme ad Alessandro abbiamo visto che questa situazione potrebbe essere critica.
         * @deprecated 02/05/2025 Il codiceScriva è alimentato da => datiTecnici.identificativo;
         * @new 02/05/2025 SCRIVA-1642 => con la modifica introdotta, datiTecnici.identificativo è stato rimosso e introdotto id_oggetto direttamente. E' necessario testare bene questa parte ed eventualmente rimuovere questo blocco di commenti.
         * datiTecnici.identificativo è alimentato dal FormIo, prendendo il opera.cod_scriva di riferimento all'apertura/creazione dei dati tecnici (comunque con il riferimento all'oggetto istanza).
         * La problematica è la seguente: le opere possono partire con un cod_scriva, esempio: SCRV-12345; vengono aperti poi i dati tecnici per l'oggetto istanza e "identificativo" viene popolato con "SCRV-12345".
         * Una volta salvato dal form, opera.cod_scriva sarà uguale a identificativo (quindi l'if sotto).
         * Sulle opere però il cod_scriva, può cambiare (per esempio: regione valida l'Opera) e quindi si avrà un altro valore: "PIPPO-54321".
         * Se l'utente aprisse di nuovo il FormIo e il campo identificativo non viene sovrascritto dalle logiche di ["customDefaultValue": "value = options.SCRIVA.oggetto.cod_scriva;"]
         * al salvataggio si avrebbe "SCRV-12345" che è diverso da "PIPPO-54321".
         * Per le logiche successive, sull'opera "vince" il dato sul jsondata/formio, per cui si salverebbe l'opera riportando il codice a "SCRV-12345".
         * Essendo rischioso, si è deciso di rimuovere questa logica. Per il momento la si mantiene commentata per permettere al collega d'informarsi meglio e capire il senso di questo passaggio.
         */
        // else if (opera.cod_scriva !== codiceScriva) {
        //   // L'opera è d'aggiornare, ma solo per il codice scriva, il codice scriva dell'oggetto istanza deve essere ribaltato nell'opera
        //   return of('ii');
        //   // #
        // }
        else {
          // L'opera non deve essere aggiornata
          return of('iii');
          // #
        }
      })
    );

    return salvaOI$.pipe(
      mergeMap((value) =>
        iif(
          () => value === 'iii',
          of(true),
          of(value).pipe(
            switchMap((val) => {
              if (val === 'i') {
                opera.den_oggetto = denominazioneDT;
                opera.ubicazione_oggetto[0].des_localita = localitaDT;
                // if (opera.cod_scriva !== codiceScriva) {
                //   opera.cod_scriva = codiceScriva;
                // }
              }
              // else {
              //   // val === 'ii'
              //   opera.cod_scriva = codiceScriva;
              // }

              return this._ambito.salvaOpera(opera, this.codAdempimento).pipe(
                catchError((e: ScrivaServerError) => {
                  // Gestisco la messaggistica
                  this.onServiziError(e, this.ALERT_TARGET_MODALE);
                  // Ritorno errore
                  return throwError(e);
                  // #
                }),
                tap((resp) => (opera = resp))
              );
            })
          )
        )
      )
    );
  }

  /**
   * Funzione che effettua l'aggiornamento delle informazioni per i dati in input.
   * La funzione gestirà il set delle informazioni e poi avvierà il salvataggio dei dati tecnici.
   * @param opera Opera con l'oggetto identificativo per il salvataggio.
   * @param oggettoIstanza OggettoIstanza con l'oggetto identificativo per il salvataggio.
   * @param datiTecniciDaSalvare IDTOperaSalvataggioSezione con le informazioni dei dati tecnici per il salvattaggio.
   * @override
   */
  updateDetails(
    opera: Opera,
    oggettoIstanza: OggettoIstanza,
    datiTecniciDaSalvare: IDTOperaSalvataggioSezione
  ) {
    // Estraggo dall'input le informazioni per il salvataggio
    const sezione: string = datiTecniciDaSalvare?.sezione;
    const datiSezione: any = datiTecniciDaSalvare?.datiSezione;

    // Per le captazioni gestiscio delle logiche specifiche sulle sezioni
    if (sezione === DatiSezioneCaptazioneCondivisi.DATI_IDENTIFICATIVI) {
      // Pozzo/Presa:
      // del dati_identificativi.denominazione .codice_scriva .localita
      delete datiSezione.denominazione;
      delete datiSezione.localizzazione;
    }

    if (this.sourceDataForModal) {
      if (!this.sourceDataForModal[sezione]) {
        this.sourceDataForModal[sezione] = {};
      }
      this.sourceDataForModal[sezione] = datiSezione;
      this.sourceDataForModal.id_oggetto = opera.id_oggetto;
    } else {
      if (!this.datiTecnici) {
        this.datiTecnici = {};
      }

      let arrayName: string;
      arrayName = this._opere.keyDatiTecniciOpera(this.PREFIX_JS_DT, opera);

      if (!this.datiTecnici[arrayName]) {
        this.datiTecnici[arrayName] = [];
      }

      const dataObj: any = {};
      dataObj.id_oggetto_istanza = oggettoIstanza.id_oggetto_istanza;
      dataObj.id_oggetto = opera.id_oggetto;
      dataObj[sezione] = datiSezione;
      this.datiTecnici[arrayName].push(dataObj);
      this.sourceDataForModal = dataObj;
    }

    this.salvaDatiQuadro();
    this.onInitComponentData();
  }
}
