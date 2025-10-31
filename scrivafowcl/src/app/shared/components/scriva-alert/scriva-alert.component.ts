/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { animate, style, transition, trigger } from '@angular/animations';
import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';
import { CommonConsts } from '../../consts/common-consts.consts';
import { ScrivaInfoLevels } from '../../enums/scriva-utilities/scriva-utilities.enums';
import { ScrivaAlertConfigs } from './utilities/scriva-alert.classes';
import {
  SAlertClasses,
  SAlertIcons,
  SAlertLayouts,
} from './utilities/scriva-alert.enums';

/**
 * ##################
 * ANGULAR ANIMATIONS
 * ##################
 */
const enterTransition = transition(':enter', [
  style({
    opacity: 0,
  }),
  animate(
    '0.5s ease-in',
    style({
      opacity: 1,
    })
  ),
]);

const leaveTrans = transition(':leave', [
  style({
    opacity: 1,
  }),
  animate(
    '0.5s ease-out',
    style({
      opacity: 0,
    })
  ),
]);

const fadeIn = trigger('fadeIn', [enterTransition]);
const fadeOut = trigger('fadeOut', [leaveTrans]);

@Component({
  selector: 'scriva-alert',
  templateUrl: './scriva-alert.component.html',
  styleUrls: ['./scriva-alert.component.scss'],
  animations: [fadeIn, fadeOut],
})
export class ScrivaAlertComponent implements OnInit, OnChanges {
  /** Oggetto costante contenente le informazioni comuni all'applicazione. */
  C_C = new CommonConsts();

  /** Costante che identifica un type per il componente: DANGER */
  TYPE_DANGER = ScrivaInfoLevels.danger;
  /** Costante che identifica un type per il componente: INFO */
  TYPE_INFO = ScrivaInfoLevels.info;
  /** Costante che identifica un type per il componente: SUCCESS */
  TYPE_SUCCESS = ScrivaInfoLevels.success;
  /** Costante che identifica un type per il componente: WARNING */
  TYPE_WARNING = ScrivaInfoLevels.warning;

  /** ScrivaAlertConfigs con le configurazioni dell'alert. */
  @Input('alertConfigs') configs: ScrivaAlertConfigs;
  /** Boolean con la configurazione per permettere la chiusura dell'alert tramite la X. */
  @Input() allowAlertClose: boolean = false;
  /** Boolean con la configurazione che definisce se utilizzare il layout di RISCA. */
  @Input() layoutScriva: boolean = false;

  /** EventEmitter che definisce l'evento di alert nascosto. */
  @Output() onAlertHidden = new EventEmitter<boolean>();

  /** EventEmitter che definisce l'evento di conferma dell'alert. */
  @Output() onConfirm = new EventEmitter<any>();
  /** EventEmitter che definisce l'evento di chiusura dell'alert. */
  @Output() onCancel = new EventEmitter<any>();

  /** Flag che definisce se i messaggi devono essere visibili o nascosti. */
  showMessages = true;
  /** String che definisce la classe di stile da associare al container. */
  containerClass: string;
  /** String che definisce la classe di stile da associare al container per il layout. */
  containerLayout: string;
  /** String che definisce src path per l'icona. */
  srcIcon: string;
  /** String array contenente i messaggi da visualizzare. */
  alertMessages: string[];

  constructor() {}

  ngOnInit() {
    // Verifico che sia definita una configurazione
    if (!this.configs) {
      // Blocco il flusso
      return;
    }

    // Lancio l'init del componente
    this.initComponente(this.configs);
  }

  /**
   * Hook di Angular
   * @param changes SimpleChanges
   */
  ngOnChanges(changes: SimpleChanges): void {
    // Verifico se ci sono da aggiornare i messaggi
    if (changes.configs && !changes.configs.firstChange) {
      // Aggiorno le informazioni del componente
      this.initComponente(changes.configs.currentValue);
    }
  }

  /**
   * Funzione di init per il componente. Verranno generate le configurazioni per la visualizzazione del componente.
   * @param c ScrivaAlertConfigs contenente le configurazioni per l'alert.
   */
  private initComponente(c: ScrivaAlertConfigs) {
    // Verifico che tipologia di pannello imbastire
    switch (c.type) {
      case this.TYPE_DANGER:
        // Alert di errore
        this.initAlert(c, SAlertClasses.danger, SAlertIcons.danger);
        break;
      case this.TYPE_INFO:
        // Alert di successo
        this.initAlert(c, SAlertClasses.info, SAlertIcons.info);
        break;
      case this.TYPE_SUCCESS:
        // Alert di successo
        this.initAlert(c, SAlertClasses.success, SAlertIcons.success);
        break;
      case this.TYPE_WARNING:
        // Alert di warning
        this.initAlert(c, SAlertClasses.warning, SAlertIcons.warning);
        break;
      default:
        // Alert di successo
        this.initAlert(c, SAlertClasses.success, SAlertIcons.success);
    }
  }

  /**
   * Funzione di setup del componente.
   * @param c ScrivaAlertConfigs contenente le configurazioni per l'alert.
   * @param containerClass string che definisce la classe da assegnare al container.
   * @param icon string che definisce il path all'icona.
   */
  private initAlert(
    c: ScrivaAlertConfigs,
    containerClass: string,
    icon: string
  ) {
    // Verifico il tipo di input per i messaggi, e converto tutto in array di string
    this.alertMessages = this.formatMessages(c.messages);

    // Definisco la classe del container
    this.containerClass = containerClass;
    // Definisco il path per l'icona
    this.srcIcon = icon;

    // Definisco il tipo di layout
    this.containerLayout = c.compactLayout
      ? SAlertLayouts.compact
      : SAlertLayouts.standard;

    // Gestisco la visualizzazione dell'alert
    this.gestisciVisibilitaMessaggi(c);
  }

  /**
   * Funzione di comodo per la verifica della struttura dei messaggi.
   * @param messages string | string[] contenete i messaggi.
   * @returns string[] come convert dell'input.
   */
  private formatMessages(messages: string | string[]): string[] {
    // Verifico se il messaggio è una string
    if (typeof messages === 'string') {
      // Formatto e ritorno i messaggi
      return [messages];
    }

    // Ritorno i messaggi
    return messages;
  }

  /**
   * Funzione di comodo che gestisce la visualizzazione a scomparsa dei messaggi
   * @param c ScrivaAlertConfigs contenente le configurazioni per l'alert.
   */
  private gestisciVisibilitaMessaggi(c: ScrivaAlertConfigs) {
    // Setto a true la visibilità dei messaggi
    this.showMessages = true;
    // Definisco variabili di comodo
    const isPersist = c.persistentMessage || false;
    // Definisco il timeout e aggiungo un extra per far finire l'animazione di sparizione
    let timeout = c.timeoutMessage;

    // Se non è persistente, imposto il timout di chiusura
    if (!isPersist && timeout !== undefined) {
      // Scomparsa automatica richiesta, aggiungo il tempo per far concludere l'animazione
      timeout += 550;
      // Imposto un timeout con la scomparsa
      setTimeout(() => {
        // Chiudo l'alert
        this.chiudiAlert();
        // #
      }, timeout);
    }
  }

  /**
   * ################################
   * GESTIONE DEI PULSANTI DELL'ALERT
   * ################################
   */

  /**
   * Pulsante che gestisce la "chiusura" dell'alert, quando l'utente manualmente preme sulla "X" di chiusura.
   */
  alertClose() {
    // Verifico se esistono i pulsanti di conferma/annulla dell'alert
    if (this.showAlertButtons) {
      // Devo richiamare l'evento di cancel, e non di close
      this.alertCancel();
      // #
    } else {
      // Chiudo l'alert normalmente
      this.chiudiAlert();
    }
  }

  /**
   * Bottone che lancia l'evento di chiusura quando viene premuto.
   */
  alertCancel() {
    // Emetto l'evento di "cancel" dell'alert, passando il possibile payload
    this.onCancel.emit(this.payloadCancel);
  }

  /**
   * Bottone che lancia l'evento di conferma quando viene premuto.
   */
  alertConfirm() {
    // Emetto l'evento di "confirm" dell'alert, passando il possibile payload
    this.onConfirm.emit(this.payloadConfirm);
  }

  /**
   * ##################
   * FUNZIONI DI COMODO
   * ##################
   */

  /**
   * Funzione di comodo che gestisce le logiche per la chiusura manuale dell'alert.
   */
  chiudiAlert() {
    // Nascondo l'alert
    this.showMessages = false;
    // Emetto l'evento di alert nascosto
    this.onAlertHidden.emit(true);
  }

  /**
   * ################
   * GETTER DI COMODO
   * ################
   */

  get showAlertButtons() {
    return this.buttonCancel || this.buttonConfirm;
  }

  get buttonCancel() {
    return this.configs?.buttonCancel;
  }

  get buttonConfirm() {
    return this.configs?.buttonConfirm;
  }

  get payloadConfirm() {
    return this.configs?.payloadConfirm;
  }

  get payloadCancel() {
    return this.configs?.payloadCancel;
  }

  get showCloseButton() {
    return this.allowAlertClose || (this.configs?.allowAlertClose ?? false);
  }

  /**
   * Getter di comodo che verifica se all'interno della configurazione dell'alert è presente un titolo.
   * @returns boolean con il risultato del check.
   */
  get checkTitle(): boolean {
    // Recupero il title
    const { title } = this.configs || {};
    const titleUND = title == undefined;
    const titleEMPTY = title == '';
    // Ritorno la presenza del title
    return !titleUND && !titleEMPTY;
  }
}
