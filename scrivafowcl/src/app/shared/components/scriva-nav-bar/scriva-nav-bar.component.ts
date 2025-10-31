/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, EventEmitter, Input, OnInit, Output, ChangeDetectionStrategy, ChangeDetectorRef, SimpleChanges } from '@angular/core';
import { cloneDeep } from 'lodash';
import { SNBTab } from './utilities/scriva-nav-bar.classes';

@Component({
  selector: 'scriva-nav-bar',
  templateUrl: './scriva-nav-bar.component.html',
  styleUrls: ['./scriva-nav-bar.component.scss'],
})
export class ScrivaNavBarComponent implements OnInit {
  /** Input SNBTab[] che contiene la configurazione dati per la gestione delle tabs della nav bar. */
  @Input() tabs: SNBTab[];

  /** Output SNBTab con l'oggetto della tab premuta. */
  @Output('onTabClick') onTabClick$ = new EventEmitter<SNBTab>();
  /** Output SNBTab con l'id dell'oggetto della tab premuta. */
  @Output('onTabIdClick') onTabIdClick$ = new EventEmitter<string>();

  /** SNBTab[] che contiene la lista di elementi per il popolamento della nav bar. E' alimentato da: navBar. */
  snbTabs: SNBTab[];

  /** SNBTab che contiene l'oggetto attualmente attivo come tab nella nav bar. */
  private _tabSelected: SNBTab;

  /**
   * Costruttore.
   */
  constructor() {}

  /**
   * NgOnInit.
   */
  ngOnInit() {
    // Lancio la funzione di init del componente
    this.initComponente();
  }

  /**
   * ###############################
   * FUNZIONI DI INIT DEL COMPONENTE
   * ###############################
   */

  /**
   * Funzione di comodo per le funzioni di init del componente.
   */
  private initComponente() {
    // Funzione d'inizializzazione delle tabs
    this.initTabs(this.tabs);
  }

  /**
   * Funzione di inizializzazione dei dati per le tabs da visualizzare.
   * @param tabs SNBTab[] con la lista di elementi per popolare le tabs.
   */
  private initTabs(tabs: SNBTab[]) {
    // Imposto l'array in input come dato da iterare nel componente
    this.snbTabs = cloneDeep(tabs);

    // Verifico se esiste una tab selezionata di default
    const iTab = this.snbTabs.findIndex((snbTab: SNBTab) => snbTab.selected);
    // Verifico e setto le informazioni per il tab possibilmente selezionato
    this.selectTabByIndex(iTab);
  }

  /**
   * ########################################
   * FUNZIONI INVOCABILI DAL COMPONENTE PADRE
   * ########################################
   */

  /**
   * Funzione che permette di selezionare un tab definito in input.
   * @param tab SNBTab con l'oggetto per il set manuale del tab selezionato.
   * @returns SNBTab con l'oggetto selezionato se è stato trovato nella lista di tabs. Se non esiste l'oggetto, viene ritornato: undefined.
   */
  selectTabByTab(tab: SNBTab): SNBTab {
    // Verifico l'input
    if (!tab) {
      // Non esiste la configurazione, ritorno undefined
      return undefined;
    }

    // Verifico se l'oggetto esiste all'interno della lista
    const iTab = this.getTabIdByTab(tab);
    // Verifico e ritorno le informazioni per il tab possibilmente selezionato
    return this.selectTabByIndex(iTab);
  }

  /**
   * Funzione che permette di selezionare un tab, definendo una configurazione in input.
   * @param tabId string con la stringa identificativa dell'oggetto da ricercare.
   * @returns SNBTab con l'oggetto selezionato se è stato trovato nella lista di tabs. Se non esiste l'oggetto, viene ritornato: undefined.
   */
  selectTabByTabId(tabId: string): SNBTab {
    // Verifico l'input
    if (!tabId) {
      // Non esiste la configurazione, ritorno undefined
      return undefined;
    }

    // Definisco un oggetto fittizio per la ricerca all'interno dell'array
    const searchTab: SNBTab = new SNBTab({ id: tabId });

    // Verifico se l'oggetto esiste all'interno della lista
    const iTab = this.getTabIdByTab(searchTab);
    // Verifico e ritorno le informazioni per il tab possibilmente selezionato
    return this.selectTabByIndex(iTab);
  }

  /**
   * Funzione di supporto che verifica ed imposta un oggetto SNBTab, secondo l'indice posizionale in input.
   * @param iTab number con l'indice posizionale di un oggetto tab all'interno dell'array dati del componente.
   */
  private selectTabByIndex(iTab: number): SNBTab {
    // Verifico se è stato trovato
    if (iTab > -1) {
      // Imposto l'oggetto come selezionato
      this._tabSelected = this.snbTabs[iTab];
      // Trovato, ritorno l'oggetto
      return this.snbTabs[iTab];
      // #
    }

    // Nessun risultato
    return undefined;
  }

  /**
   * #############################################
   * FUNZIONI AGGANCIATE AL PULSANTE DELLA NAV BAR
   * #############################################
   */

  /**
   * Funzione agganciata all'evento di "click" del tab della navbar.
   * @param tab SNBTab con l'oggetto clickato della navbar.
   */
  onTabClick(tab: SNBTab) {
    // Salvo localmente il tab
    this._tabSelected = tab;

    // Emetto l'oggetto selezionato
    this.onTabClick$.emit(tab);
    this.onTabIdClick$.emit(tab?.id);
  }

  /**
   * #####################################
   * FUNZIONI DI UTILITY PER IL COMPONENTE
   * #####################################
   */

  /**
   * Funzione che recupera l'indice posizionale di un tab, dato un oggetto tab per la ricerca.
   * @param tab SNBTab per la ricerca all'interno dell'array.
   * @returns number con l'indice posizionale nell'array. Se non viene trovato, allora: -1.
   */
  private getTabIdByTab(tab: SNBTab): number {
    // Check sulla lista interna di elementi
    const checkTabs = this.snbTabs?.length > 0;
    // Verifico i dati minimi per il recupero dell'id
    if (!checkTabs || !tab) {
      // Ritorno -1, non esiste la lista
      return -1;
    }

    // Recupero l'indice della tab selezionata nell'array
    const i = this.snbTabs.findIndex((snbT: SNBTab) => {
      // Effettuo un match tra id
      return snbT.id === tab?.id;
    });

    // Ritorno l'indice dell'oggetto della tab attualmente selezionata
    return i;
  }

  /**
   * ###############
   * GETTER & SETTER
   * ###############
   */

  /**
   * Getter per la tab attualmente selezionata.
   * @returns SNBTab con i dati della tab selezionata.
   */
  get tabSelected(): SNBTab {
    // Ritorno l'oggetto della tab attualmente selezionata
    return cloneDeep(this._tabSelected);
  }

  /**
   * Getter per l'indice della tab attualmente selezionata.
   * @returns number con l'indece posizionale della tab all'interno dell'array di configurazione.
   */
  get tabSelectedIndex(): number {
    // Verifico che esista la lista di elementi
    if (!this.snbTabs || this.snbTabs.length === 0) {
      // Ritorno -1, non esiste la lista
      return -1;
    }

    // Ritorno l'indice dell'oggetto della tab attualmente selezionata
    return this.getTabIdByTab(this._tabSelected);
  }
}
