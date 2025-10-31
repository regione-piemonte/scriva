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
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';
import { cloneDeep } from 'lodash';
import { IPreferenzaNotifica } from '../../models/preferenze-notifiche.model';

@Component({
  selector: 'scriva-preferenze-notifiche',
  templateUrl: './preferenze-notifiche.component.html',
  styleUrls: ['./preferenze-notifiche.component.scss'],
})
export class PreferenzeNotificheComponent implements OnInit, OnChanges {
  /** Input IPreferenzaNotifica[] contenente le configurazioni per le preferenze di notifica dell'utente. */
  @Input() preferenzeNotifiche: IPreferenzaNotifica[];

  /** Output EventEmitter<IPreferenzaNotifica[]> che definisce l'evento triggherato al cambio della preferenze. */
  @Output('preferenzeChanged') preferenzeChanged$ = new EventEmitter<
    IPreferenzaNotifica[]
  >();

  /** Input IPreferenzaNotifica[] contenente le configurazioni per le preferenze di notifica dell'utente. */
  private preferenzeNotificheUpd: IPreferenzaNotifica[];

  /**
   * Costruttore.
   */
  constructor() {
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
    // Recupero il possibile oggetto di changes: preferenzeNotifiche
    const pnChanges = changes.preferenzeNotifiche;
    // Verifico se è stato aggiornato: preferenzeNotifiche
    if (pnChanges && !pnChanges.firstChange) {
      // Lancio l'aggiornamento delle preferenze utente
      this.initPreferenzeNotifiche(pnChanges.currentValue);
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
    // Lancio il setup delle preferenze utente
    this.initPreferenzeNotifiche(this.preferenzeNotifiche);
  }

  /**
   * Funzione di comodo che inizializza le informazioni per l'array delle preferenze notifiche.
   * @param preferenzeNotifiche IPreferenzaNotifica[] che definisce l'array di configurazioni da gestire.
   */
  private initPreferenzeNotifiche(preferenzeNotifiche: IPreferenzaNotifica[]) {
    // Al setup, le preferenze update sono uguale alle init
    this.preferenzeNotificheUpd = cloneDeep(preferenzeNotifiche);
  }

  /**
   * ##############################
   * FUNZIONI COLLEGATE AL TEMPLATE
   * ##############################
   */

  /**
   * Funzione collegata all'evento di cambio del valore della checkbox.
   * @param preferenzaNotifica IPreferenzaNotifica con l'oggetto modificato a seguito dell'evento di cambio del checkbox.
   */
  onPreferenzaNotificaChanged(preferenzaNotifica: IPreferenzaNotifica) {
    // Creo una copia dell'array delle preferenze
    const preferenzeNotifiche = this.preferenzeNotificheUpd;

    // Vado ad aggiornare, all'interno delle preferenze, l'oggetto modificato
    const iPN = preferenzeNotifiche.findIndex((pn: IPreferenzaNotifica) => {
      // Recupero gli id per il confronto
      const idPNElem = pn.idCompilantePreferenzaCanale;
      const idPNMod = preferenzaNotifica.idCompilantePreferenzaCanale;

      // Effettuo una comparazione tra gli id degli oggetti
      return idPNElem === idPNMod;
    });

    // Verifico se è stato trovato l'oggetto all'interno dell'array
    if (iPN > -1) {
      // Oggetto trovato lo vado a sostituire
      preferenzeNotifiche.splice(iPN, 1, preferenzaNotifica);
      // Elementi modificati, emetto in output l'array
      this.preferenzeChanged$.emit(preferenzeNotifiche);
    }
  }

  /**
   * ###############
   * GETTER & SETTER
   * ###############
   */

  /**
   * Getter di comodo che verifica se esiste la lista di preferenze notifiche e possiede almeno 1 elemento.
   * @returns boolean con il risultato del controllo.
   */
  get checkPreferenzeNotifiche(): boolean {
    // Verifico esista l'array e che abbia almeno un elemento
    return this.preferenzeNotifiche?.length > 0;
  }
}
