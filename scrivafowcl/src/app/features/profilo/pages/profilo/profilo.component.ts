/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { ScrivaServerError } from '../../../../core/classes/scriva.classes';
import { ScrivaCodesMesseges } from '../../../../core/enums/scriva-codes-messages.enums';
import { MessageService } from '../../../../shared/services';
import { IPreferenzaNotifica } from '../../models/preferenze-notifiche.model';
import { ProfiloService } from '../../services/profilo/profilo.service';
import { PROFILO_CONSTS } from './utilities/profilo.consts';

@Component({
  selector: 'scriva-profilo',
  templateUrl: './profilo.component.html',
  styleUrls: ['./profilo.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ProfiloComponent implements OnInit {
  /** Costante contenente le informazioni costanti del componente. */
  P_C = PROFILO_CONSTS;

  /** String che definisce l'id della tab premuto. */
  tabIdSelected: string = '';

  /**
   * Costruttore.
   */
  constructor(
    private _message: MessageService,
    private _profilo: ProfiloService,
    private _spinner: NgxSpinnerService
  ) {
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
   * ################################
   * FUNZIONI DI SETUP DEL COMPONENTE
   * ################################
   */

  /**
   * Funzione di setup del componente, lanciato dal costruttore.
   */
  private setupComponente() {
    // Lancio il setup per la navbar
    this.setupDefaultTab();
    // Lancio lo scarico delle informazioni per le preferenze notifiche
    this.setupPreferenzeNotifiche();
  }

  /**
   * Funzione che effettua il setup della nav bar con la tab di default.
   */
  private setupDefaultTab() {
    // Recupero dalle costanti l'id di default
    const defaultTab: string = this.P_C.TAB_ID_ANAGRAFICA;
    // Definisco l'id della tab di default come selezionato
    this.tabIdSelected = defaultTab;
  }

  /**
   * Funzione che scarica le preferenze notifiche del compilante.
   */
  private setupPreferenzeNotifiche() {
    // Faccio partire lo spinner di caricamento
    this._spinner.show();

    // Recupero dal servizio di autenticazione le informazioni del compilante
    const compilante = this._profilo.compilante;
    // Recuper l'id del compilante dall'oggetto Compilante
    const idCompilante = compilante?.id_compilante;

    // Invoco il servizio per lo scarico delle preferenze
    this._profilo.getPreferenzeNotifiche(idCompilante).subscribe({
      next: (preferenzeNotifiche: IPreferenzaNotifica[]) => {
        // Interrompo lo spinner
        this._spinner.hide();
      },
      error: (e: ScrivaServerError) => {
        // Interrompo lo spinner
        this._spinner.hide();
        // Visualizzo l'errore tramite alert
        this.onServiceError(e);
      },
    });
  }

  /**
   * ###############################
   * FUNZIONI DI INIT DEL COMPONENTE
   * ###############################
   */

  /**
   * Funzione di init del componente, lanciato dall'NgOnInit.
   */
  private initComponente() {}

  /**
   * ####################
   * EVENTI DELLA NAV BAR
   * ####################
   */

  /**
   * Funzione collegata al click sulla tab definita per il componente.
   * @param idTab string con l'id della tab premuta.
   */
  onTabIdClick(idTab: string) {
    // Salvo localmente l'id della tab premuto
    this.tabIdSelected = idTab;
  }

  /**
   * #####################################
   * FUNZIONI DI UTILITY PER IL COMPONENTE
   * #####################################
   */

  /**
   * Funzione di supporto per la gestione della segnalazione dell'errore da parte dei servizi.
   * @param e ScrivaServerError con il dettaglio d'errore generato.
   */
  private onServiceError(e?: ScrivaServerError) {
    // Si è verificato un errore, gestisco la segnalazione utente
    const code = e?.error?.code ?? ScrivaCodesMesseges.E100;
    // Visualizzo il messaggio
    this.showAlert(code);
  }

  /**
   * Funzione di supporto per la gestione degli errori nel componente, dato il codice errore.
   * @param code string con il codice del messaggio da visualizzare.
   */
  private showAlert(code: string) {
    // Definisco le configurazioni per la visualizzazione dell'alert
    const target = this.P_C.ALERT_ANCHOR;
    const autoFade = true;
    // Visualizzo il messaggio
    this._message.showMessage(code, target, autoFade);
  }
}
