/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Injectable } from '@angular/core';
import { IStatoNotifica } from '../components/notifiche-search/utilities/notifiche-search.interfaces';
import { NOTIFICHE_CONSTS } from '../pages/notifiche/utilities/notifiche.consts';

@Injectable({ providedIn: 'root' })
export class NotificheConfigurazioniService {
  /** Costante che definisce le informazioni costanti per la pagina: notifiche. */
  private N_C = NOTIFICHE_CONSTS;

  // chiave utilizzata per recuperare tramite ConfigurazioniService.getConfigurazione(KEY) _scrivaNotifyTime
  public CONF_SCRIVA_NOTIFY_TIME = 'SCRIVA_NOTIFY_TIME';
  // valore utilizzato come valore di default se il server non restituisce nessun valore per la CONF_SCRIVA_NOTIFY_TIME
  public CONF_SCRIVA_NOTIFY_TIME_VALUE = 5*60*1000;
  // chiave utilizzata per recuperare tramite ConfigurazioniService.getConfigurazione(KEY) _scrivaNotifyNumNotificheHome
  public CONF_SCRIVA_NOTIFY_NUM_NOTIFICHE_HOME =
    'SCRIVA_NOTIFY_NUM_NOTIFICHE_HOME';
  // numero di millisecondi che fa partire il recupero delle notifiche automatico
  // setto un valore di default se il BE non ci restituisce la configurazione
  private _scrivaNotifyTime: number = this.CONF_SCRIVA_NOTIFY_TIME_VALUE;
  // numero di notifiche mostrate nella home, il valore viene recuperato dal BE tramite CONF_SCRIVA_NOTIFY_NUM_NOTIFICHE_HOME
  private _scrivaNotifyNumNotificheHome: number;
  // numero di notifiche mostrate per pagina nella lista
  private _scrivaNotifyNumNotificheListPage: number = 30;
  // numero di notifiche mostrate nella sidebar
  private _scrivaNotifyNumNotificheSidebar: number = 30;
  // campo per cui si ordina la pagina di lista delle notifiche
  private _fieldForOrderBy = 'data_inserimento';

  /** IStatoNotifica[] contenente la lista degli stati notifica per la selezione nel form di ricerca. */
  private statiNotificaLista: IStatoNotifica[] = this.N_C.LIST_STATI_NOTIFICA;

  /**
   * Costruttore
   */
  constructor() {}

  /**
   * Setter che setta intervallo di polling automatico per le notifiche
   * @param value number
   */
  setScrivaNotifyTime(value: number) {
    this._scrivaNotifyTime = value;
  }

  /**
   * Getter che recupera intervallo di polling automatico per le notifiche
   * @returns number
   */
  getScrivaNotifyTime(): number {
    return this._scrivaNotifyTime;
  }

  /**
   * Setter che setta il numero delle notifiche visualizzate in homepage
   * @param value number
   */
  setScrivaNotifyNumNotificheHome(value: number): void {
    this._scrivaNotifyNumNotificheHome = value;
  }

  /**
   * Getter che recupera il numero delle notifiche visualizzate in homepage
   * @returns number
   */
  getScrivaNotifyNumNotificheHome(): number {
    return this._scrivaNotifyNumNotificheHome;
  }

  /**
   * Getter che recupera il numero di notifiche visualizzate per la pagina della lista
   * @returns number
   */
  getScrivaNotifyNumNotificheListPage(): number {
    return this._scrivaNotifyNumNotificheListPage;
  }

  /**
   * Getter che recupera il numero di notifiche visualizzate per la sidebar
   * @returns number
   */
  getScrivaNotifyNumNotificheSidebar(): number {
    return this._scrivaNotifyNumNotificheSidebar;
  }

  /**
   * Getter che recupera il campo su cui ordinare la pagina della lista
   * @returns string
   */
  getScrivaNotifyFieldForOrderBy(): string {
    return this._fieldForOrderBy;
  }

  /**
   * Getter che recupera la combo stati Notifica
   * @returns
   */
  getScrivaNotifyStatiNotifica() {
    return this.statiNotificaLista;
  }
}
