/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { cloneDeep } from 'lodash';
import { IScrivaIcon } from '../../../../shared/components/scriva-icon/utilities/scriva-icon.interfaces';
import { ScrivaUtilitiesService } from '../../../../shared/services/scriva-utilities/scriva-utilities.service';
import { ICanaleNotifica, IPreferenzaNotifica } from '../../models/preferenze-notifiche.model';
import { CanalePreferenzeIcon } from './utilities/preferenza-notifica.classes';

@Component({
  selector: 'scriva-preferenza-notifica',
  templateUrl: './preferenza-notifica.component.html',
  styleUrls: ['./preferenza-notifica.component.scss'],
})
export class PreferenzaNotificaComponent implements OnInit, OnChanges {
  /** CanalePreferenzeIcon contenente la configurazione per le icone in base al canale di notifica. */
  private canalePreferenzeIcon = new CanalePreferenzeIcon();

  /** Input IPreferenzaNotifica contenente le configurazioni per le preferenze di notifica dell'utente. */
  @Input() preferenzaNotifica: IPreferenzaNotifica;

  /** Output EventEmitter<boolean> che definisce l'evento triggherato al cambio della checkbox. */
  @Output('checkboxChanged') checkboxChanged$ = new EventEmitter<boolean>();
  /** Output EventEmitter<IPreferenzaNotifica> che definisce l'evento triggherato al cambio della preferenza. */
  @Output('preferenzaChanged') preferenzaChanged$ =
    new EventEmitter<IPreferenzaNotifica>();

  /** String che definisce un id del DOM univoco per questo componente. */
  idPreferenzaNotifica: string;
  /** Boolean che contiene il valore per l'ngModel della checkbox. */
  checkbox = false;
  /** IScrivaIcon che definisce l'oggetto di configurazione per l'icona della preferenza notifica. */
  canaleIcon: IScrivaIcon;

  /**
   * Costruttore.
   */
  constructor(private _scrivaUtilities: ScrivaUtilitiesService) {
    // Lancio il setup del componente
    this.setupComponente();
  }

  /**
   * NgOnInit.
   */
  ngOnInit() {
    // Lancio l'init del componente
    this.initComponente();
  }

  /**
   * NgOnChanges.
   * @param changes
   */
  ngOnChanges(changes: SimpleChanges): void {
    // Recupero il possibile oggetto di changes: preferenzaNotifica
    const pnChanges = changes.preferenzaNotifica;
    // Verifico se è stato aggiornato: preferenzaNotifica
    if (pnChanges && !pnChanges.firstChange) {
      // Lancio l'aggiornamento delle preferenze utente
      this.initPreferenzaNotifica(pnChanges.currentValue);
    }
  }

  /**
   * ################################
   * FUNZIONI DI SETUP DEL COMPONENTE
   * ################################
   */

  /**
   * Funzione di setup del componente, lanciato dal costruttore.
   */
  private setupComponente() {}

  /**
   * ###############################
   * FUNZIONI DI INIT DEL COMPONENTE
   * ###############################
   */

  /**
   * Funzione di init del componente, lanciato dall'NgOnInit.
   */
  private initComponente() {
    // Lancio la generazione dell'id del dom custom per questa componente
    this.generateIdDOM(this.preferenzaNotifica);
    // Gestisco il valore d'assegnare al modello della checkbox
    this.initCheckbox(this.preferenzaNotifica);
    // Lancio l'init delle preferenze utente
    this.initPreferenzaNotifica(this.preferenzaNotifica);
  }

  /**
   * Funzione di init per il valore del modello della checkbox.
   * @param preferenzeNotifica IPreferenzaNotifica con la configurazione iniziale per la checkbox.
   */
  private initCheckbox(preferenzeNotifica: IPreferenzaNotifica) {
    // Recupero e assegno il valore per la checkbox
    this.checkbox = preferenzeNotifica.flgAbilitato;
  }

  /**
   * Funzione di comodo che inizializza le informazioni per l'oggetto della preferenza notifica.
   * @param preferenzaNotifica IPreferenzaNotifica che definisce l'oggetto di configurazione da gestire.
   */
  private initPreferenzaNotifica(preferenzaNotifica: IPreferenzaNotifica) {
    // Recupero dall'oggetto in input il codice
    const codeCanale = preferenzaNotifica?.canaleNotifica?.codCanaleNotifica;
    // Tento di recuperare dalla mappatura delle icone quella specifica per codice
    this.canaleIcon = this.canalePreferenzeIcon[codeCanale];

    // Verifico se è stata recuperata una configurazione, altrimenti recupero il default
    if (!this.canaleIcon) {
      // Recupero la configurazione di default
      this.canaleIcon = this.canalePreferenzeIcon.DEFAULT;
    }
  }

  /**
   * ##################################
   * FUNZIONI DI UTILITY DEL COMPONENTE
   * ##################################*
   */

  /**
   * Funzione che genera un id univoco che verrà assegnato alla input.
   * @param preferenzaNotifica IPreferenzaNotifica che definisce l'oggetto di configurazione, come base per la generazione dell'id del DOM.
   */
  private generateIdDOM(preferenzaNotifica: IPreferenzaNotifica) {
    // Genero un id random
    const idRandom = this._scrivaUtilities.generateRandomId();
    // Recupero il codice delle preferenza notifica
    const codeCN = preferenzaNotifica?.canaleNotifica?.codCanaleNotifica;
    // Unisco l'id del form control con l'id randomico
    this.idPreferenzaNotifica = `${codeCN}_${idRandom}`;
  }

  /**
   * #############################################
   * FUNZIONI COLLEGATE AL TEMPLATE DEL COMPONENTE
   * #############################################
   */

  /**
   * Funzione collegata al cambio di valore della checkbox.
   */
  checkboxChange(checkbox: boolean) {
    // Emetto l'evento per informare il nuovo valore della checkbox
    const newCheck = checkbox;
    // Emetto gli eventi per il cambio del checkbox
    this.checkboxChanged$.emit(newCheck);

    // Creo una copia dell'oggetto in input
    const preferenzaNotifica = cloneDeep(this.preferenzaNotifica);
    // Aggiorno il flag dell'abilitazione
    preferenzaNotifica.flgAbilitato = newCheck;
    // Emetto l'evento per il cambio della configurazione
    this.preferenzaChanged$.emit(preferenzaNotifica);
  }

  /**
   * ###############
   * GETTER & SETTER
   * ###############
   */

  /**
   * Getter di comodo per il recupero del ICanaleNotifica dall'oggetto IPreferenzaNotifica.
   * @returns ICanaleNotifica con le informazioni della preferenza.
   */
  get canaleNotifica(): ICanaleNotifica {
    // Estraggo la proprietà dalla preferenza
    return this.preferenzaNotifica?.canaleNotifica;
  }
}
