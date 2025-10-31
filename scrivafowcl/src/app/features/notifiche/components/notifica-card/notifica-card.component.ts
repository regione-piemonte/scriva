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
import { NotificaApplicativa } from '../../models/notifica-applicativa';
import { NOTIFICHE_CARD_CONSTS } from './notifiche-card/notifica-card.consts';
import {
  CssCardNotifica,
  NotificaCardTypeView,
} from './notifiche-card/notifica-card.enums';
import { AuthStoreService } from 'src/app/shared/services';
import { NotificheService } from '../../services/notifiche.service';

@Component({
  selector: 'scriva-notifica-card',
  templateUrl: './notifica-card.component.html',
  styleUrls: ['./notifica-card.component.scss'],
})
export class NotificaCardComponent implements OnInit {
  /** Input NotificaApplicativa contenente le informazioni della notifica da visualizzare. */
  @Input() notifica: NotificaApplicativa;
  /** Input NotificaCardTypeView che definisce la tipologia di layout visuale da gestire per la card. */
  @Input('notificaCardTypeView') viewType: NotificaCardTypeView =
    NotificaCardTypeView.HOME;
  /** Input boolean che flagga la carta come ultima della lista. Per default è: false. */
  @Input('ultimaCard') ultimaCard: boolean = false;

  /** Output NotificaApplicativa che definisce l'evento di avvenuta cancellazione della notifica. */
  @Output('onNotificaCancellata') onNotificaCancellata$ =
    new EventEmitter<NotificaApplicativa>();
  /** Output NotificaApplicativa che definisce l'evento di avvenuta lettura della notifica. */
  @Output('onNotificaLetta') onNotificaLetta$ =
    new EventEmitter<NotificaApplicativa>();
  /** Output NotificaApplicativa che definisce l'evento di avvenuta lettura della notifica. */
  @Output('onDettaglioNotifica') onDettaglioNotifica$ =
    new EventEmitter<NotificaApplicativa>();
  /** Output NotificaApplicativa che definisce l'evento di avvenuta lettura della notifica. */
  @Output('onVaiAlProcedimento') onVaiAlProcedimento$ =
    new EventEmitter<NotificaApplicativa>();

  /** String che contiene la classe di stile per la visualizzazione della card della notifica. */
  private _cssCard: string;

  /** Boolean che definisce se per questa notifica card è attiva la visualizzazione per singola card. */
  isViewSingleCard: boolean = true;
  /** Boolean che definisce se per questa notifica card è attiva la navigazione verso il procedimento. */
  allowVaiAProcedimento: boolean = true;

  NC_C = NOTIFICHE_CARD_CONSTS;

  /** descrizione nella scrivania. */
  private SCRIVANIABO: string = 'Scrivania del Funzionario';
  private SCRIVANIAFO: string = 'Scrivania del Richiedente';
  _descrizioneScrivania: string;

  /**
   * Costruttore.
   */
  constructor(
    private authStoreService: AuthStoreService,
    private _notifiche: NotificheService
  ) {}

  /**
   * NgOnInit.
   */
  ngOnInit(): void {
    // Richiamo la funzione per l'init del componente
    this.initComponente();
  }

  /**
   * ###############################
   * FUNZIONI DI INIT DEL COMPONENTE
   * ###############################
   */

  /**
   * Funzione di init del componente.
   */
  private initComponente() {
    // Verifico la gestione per la visualizzazione a card singola
    this.initViewSingleCard();
    // Inizializzo la logica per visualizzare il pulsante per andare al procedimento
    this.initVaiAProcedimentoAbilitato();
    // Inizializzo la classe di stile per la card
    this._cssCard = this.initClasseCssCard();

    if (this.authStoreService.isBoComponent()) {
      this.descrizioneScrivania = this.SCRIVANIABO;
    } else {
      this.descrizioneScrivania = this.SCRIVANIAFO;
    }
  }

  /**
   * Funzione di init per la gestione della visualizzazione della card.
   */
  private initViewSingleCard() {
    // Verifico le situazioni in cui la card è gestita in modalità singola
    if (this.isViewList || this.isViewSidebar) {
      // Non è da gestire come card singola
      this.isViewSingleCard = false;
    }
  }

  /**
   * Funzione di init che verifica se, per la notifica corrente, c'è la possibilità di visualizzare il pulsante per accedere al dettaglio del procedimento.
   */
  private initVaiAProcedimentoAbilitato() {
    // Imposto a false la configurazione per accedere al procedimento
    this.allowVaiAProcedimento = false;
    // Verifico le condizioni per abilitare la navigazione
    const notViewList = !this.isViewList;
    const idIOK = this.idIstanza != undefined;
    const codAdOK = this.codAdempimento != undefined;
    const codAmOK = this.codAmbito != undefined;
    const datiProcedimentoOK = idIOK && codAdOK && codAmOK;

    // Se non siamo in visualizzazione lista e ci sono tutti i dati del procedimento
    if (notViewList && datiProcedimentoOK) {
      // Abilito la possibilità di navigazione verso il procedimento
      this.allowVaiAProcedimento = true;
    }
  }

  /**
   * Funzione di init che definisce quale classe di stile è da definire per la card.
   * La funzione deciderà in base alla tipologia di view in input.
   * @returns string che definisce la classe di stile da caricare.
   */
  private initClasseCssCard(): string {
    // Verifico le possibili view del componente
    if (this.isViewHome) {
      // Ritorno la class di stile associata
      return CssCardNotifica.home;
    }
    if (this.isViewList) {
      // Ritorno la class di stile associata
      return CssCardNotifica.list;
    }
    if (this.isViewDetail) {
      // Ritorno la class di stile associata
      return CssCardNotifica.detail;
    }
    if (this.isViewSidebar) {
      // Ritorno la class di stile associata
      return CssCardNotifica.sidebar;
    }

    // Default
    return '';
  }

  /**
   * #############################################
   * FUNZIONI COLLEGATE AL TEMPLATE DEL COMPONENTE
   * #############################################
   */

  /**
   * Funzione invocata al click sulla card.
   * Per la sezione HOME o SIDEBAR, è possibile definire una notifica come "letta" al click sulla singola card.
   * @param notifica NotificaApplicativa da contrassegnare.
   */
  onCardClick(notifica: NotificaApplicativa) {
    // Verifico se la sezione accetta la lettura sul click
    if (this.isViewHome || this.isViewSidebar) {
      // Effettuo la chiamata della card come letta
      this.segnaComeLetta(notifica);
    }
  }

  /**
   * Funzione che informa il componente padre che la notifica è stata contrassegnata come: "letta".
   * @param notifica NotificaApplicativa da contrassegnare.
   */
  segnaComeLetta(notifica: NotificaApplicativa) {
    // Richiamo l'even emitter per la segnalazione
    this.onNotificaLetta$.emit(notifica);
  }

  /**
   * Funzione che informa il componente padre che la notifica è stata contrassegnata come: "cancellata".
   * @param notifica NotificaApplicativa da contrassegnare.
   */
  segnaComeCancellata(notifica: NotificaApplicativa) {
    // Richiamo l'even emitter per la segnalazione
    this.onNotificaCancellata$.emit(notifica);
  }

  /**
   * Funzione che informa il componente padre che è stata richiesta la navigazione verso: dettaglio notifica.
   * @param notifica NotificaApplicativa con i dati di dettaglio della notifica per la navigazione.
   */
  vaiADettaglioNotifica(notifica: NotificaApplicativa) {
    // Richiamo l'even emitter per la navigazione
    this.onDettaglioNotifica$.emit(notifica);
  }

  /**
   * Funzione che informa il componente padre che è stata richiesta la navigazione verso: dettaglio notifica.
   * @param notifica NotificaApplicativa con i dati di dettaglio della notifica per la navigazione.
   */
  vaiAProcedimento(notifica: NotificaApplicativa) {
    // Richiamo l'even emitter per la navigazione
    this.onVaiAlProcedimento$.emit(notifica);
  }

  /**
   * Funzione collegata al template ed al componente di una card.
   * La funzione effettuerà la navigazione dell'applicazione verso la pagina: procedimento.
   * @param notificaCard NotificaApplicativa passata come riferimento per l'evento.
   */
  navigateToPratica(notificaCard: NotificaApplicativa) {
    // Effettuo la navigazione verso la pagina di dettaglio procedimento
    this._notifiche.navigateToPratica(notificaCard);
  }

  /**
   * ###############
   * GETTER & SETTER
   * ###############
   */

  /**
   * Getter che verifica se la view type è impostata su: HOME.
   * @returns boolean che definisce se la view è del tipo verificato.
   */
  get isViewHome(): boolean {
    // Ritorno la verifica sul view type
    return this.viewType === NotificaCardTypeView.HOME;
  }

  /**
   * Getter che verifica se la view type è impostata su: LIST.
   * @returns boolean che definisce se la view è del tipo verificato.
   */
  get isViewList(): boolean {
    // Ritorno la verifica sul view type
    return this.viewType === NotificaCardTypeView.LIST;
  }

  /**
   * Getter che verifica se la view type è impostata su: DETAIL.
   * @returns boolean che definisce se la view è del tipo verificato.
   */
  get isViewDetail(): boolean {
    // Ritorno la verifica sul view type
    return this.viewType === NotificaCardTypeView.DETAIL;
  }

  /**
   * Getter che verifica se la view type è impostata su: SIDEBAR.
   * @returns boolean che definisce se la view è del tipo verificato.
   */
  get isViewSidebar(): boolean {
    // Ritorno la verifica sul view type
    return this.viewType === NotificaCardTypeView.SIDEBAR;
  }

  /**
   * Getter di comodo per il recupero dell'id istanza della notifica.
   * @returns number con l'id istanza della notifica.
   */
  get idIstanza(): number {
    // Tento di recuperare e ritornare il parametro della notifica
    return this.notifica?.istanza?.id_istanza;
  }

  /**
   * Getter di comodo per il recupero del codice adempimento della notifica.
   * @returns string con il codice adempimento della notifica.
   */
  get codAdempimento(): string {
    // Tento di recuperare e ritornare il parametro della notifica
    return this.notifica?.istanza?.adempimento?.cod_adempimento;
  }

  /**
   * Getter di comodo per il recupero del codice ambito della notifica.
   * @returns string con il codice ambito della notifica.
   */
  get codAmbito(): string {
    // Tento di recuperare e ritornare il parametro della notifica
    return this.notifica?.istanza?.adempimento?.tipo_adempimento?.ambito
      ?.cod_ambito;
  }

  /**
   * Getter di comodo che verifica il tipo di view del componente e ritorna una specifica classe di stile.
   * @returns string contenente la classe di stile per gestire la casistica di view della card.
   */
  get cssCard(): string {
    // Ritorno la classe di stile
    return this._cssCard;
  }

  /**
   * Getter di comodo che verifica se la notifica è letta.
   * @returns boolean che definisce il risultato del check.
   */
  get isNotificaLetta(): boolean {
    // Verifico se la notifica è da leggere
    return this.notifica?.data_lettura != undefined;
  }

  /**
   * Getter di comodo che verifica se la notifica è cancellata.
   * @returns boolean che definisce il risultato del check.
   */
  get isNotificaCancellata(): boolean {
    // Verifico se la notifica è da leggere
    return this.notifica?.data_cancellazione != undefined;
  }

  /**
   * Getter di comodo che verifica se la notifica è da leggere.
   * @returns boolean che definisce il risultato del check.
   */
  get isNotificaDaLeggere(): boolean {
    // Verifico se la notifica ha una data cancellazione
    if (this.isNotificaCancellata) {
      // Esiste una data cancellazione, non può essere da leggere
      return false;
    }
    // Verifico se la notifica è già stata letta
    if (this.isNotificaLetta) {
      // La notifica è già stata letta
      return false;
    }

    // La notifica è da leggere
    return true;
  }

  /**
   * Getter di comodo che verifica se la notifica è da leggere.
   * Se è da leggere, ritorna una stringa che definisce lo stile css d'applicare.
   * @returns string contenente la classe di stile per gestire la casistica di lettura.
   */
  get cssCardDaLeggere(): string {
    // Verifico se la notifica è da leggere
    const toRead = !this.isNotificaLetta;
    // A seconda della condizione ritorno la classe
    return toRead ? CssCardNotifica.daLeggere : '';
  }

  /**
   * Getter di comodo che verifica se la notifica è l'ultima di una lista, tramite la configurazione di input.
   * @returns string contenente la classe di stile per gestire la casistica di ultima card.
   */
  get cssCardFinale(): string {
    // Verifico se la notifica è da leggere
    const isLast = this.ultimaCard;
    // A seconda della condizione ritorno la classe
    return isLast ? CssCardNotifica.ultimaCard : '';
  }

  /**
   * Getter di comodo per il recupero della descrizione adempimento dalla notifica.
   * @returns string con la descrizione estratta.
   */
  get descrizioneAdempimento(): string {
    // Recupero dalla notifica la descrizione adempimento
    return this.notifica?.istanza?.adempimento?.des_adempimento ?? '';
  }

  /**
   * Getter di comodo che verifica se il pulsante "segna come letta" è da visualizzare.
   * @returns boolean che definisce se il pulsante è da visualizzare.
   */
  get checkSegnaComeLetta(): boolean {
    // Verifico che la view sia lista
    return this.isViewList;
  }

  /**
   * Getter di comodo per il recupero della descrizione della scrivania.
   * @returns string con la descrizione.
   */
  get descrizioneScrivania(): string {
    return this._descrizioneScrivania;
  }

  /**
   * Setter di comodo per il recupero della descrizione della scrivania.
   * @returns string con la descrizione.
   */
  set descrizioneScrivania(v: string) {
    // Recupero dalla notifica la descrizione adempimento
    this._descrizioneScrivania = v;
  }
}
