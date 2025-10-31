/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ScrivaCodesMesseges } from '../../../../core/enums/scriva-codes-messages.enums';
import { IFormUpdatePropagation } from '../../../../shared/interfaces/scriva-utilities.interfaces';
import { Compilante } from '../../../../shared/models';
import { MessageService } from '../../../../shared/services';
import { ICallbackDataModal } from '../../../../shared/services/scriva-modals/utilities/scriva-modal.interfaces';
import { IPDUAction } from '../../components/profilo-dato-utente/utilities/profilo-dato-utente.interfaces';
import { ProfiloContattiModalService } from '../../services/profilo-contatti/profilo-contatti-modal.service';
import { ProfiloContattiService } from '../../services/profilo-contatti/profilo-contatti.service';
import { PROFILO_CONSTS } from '../profilo/utilities/profilo.consts';
import { PROFILO_CONTATTI_CONSTS } from './utilities/profilo-contatti.consts';
import { PCActions } from './utilities/profilo-contatti.enums';

@Component({
  selector: 'scriva-profilo-contatti',
  templateUrl: './profilo-contatti.component.html', // <img src="assets/icon-table-edit.svg" height="24px" alt="Modifica"/>
  styleUrls: ['./profilo-contatti.component.scss'],
})
export class ProfiloContattiComponent implements OnInit {
  /** Costante contenente le informazioni costanti del componente. */
  P_C = PROFILO_CONSTS;
  PC_C = PROFILO_CONTATTI_CONSTS;

  /** FormGroup contenente le informazioni del profilo utente. */
  pcForm: FormGroup;

  /**
   * Costruttore.
   */
  constructor(
    private _formBuilder: FormBuilder,
    private _message: MessageService,
    private _profiloContatti: ProfiloContattiService,
    private _profiloContattiModal: ProfiloContattiModalService
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
    // Lancio l'inizializzazione del form
    this.initForm();
  }

  /**
   * Funzione di comodo per l'init del form del componente.
   */
  private initForm() {
    // Genero la configurazione dei campi
    const fields = this._profiloContatti.generateControlsConfig();
    const validators = { validators: [] };

    // Istanzio il form con le informazioni
    this.pcForm = this._formBuilder.group(fields, validators);

    // Inizializzo le informazioni del form
    this._profiloContatti.setFormInfo(this.pcForm);
    // Definisco l'accessibilità del form
    this._profiloContatti.setAccessibilitaForm(this.pcForm);
  }

  /**
   * #################################
   * FUNZIONI DI GESTIONE DELLE AZIONI
   * #################################
   */

  /**
   * Funzione collegata al click di una action.
   * @param action IPDUAction con la configurazione dell'action premuta.
   */
  onActionClick(action: IPDUAction) {
    // Verifico l'input
    if (!action) {
      // Nessuna configurazione
      return;
    }

    // Verifico quale è l'identificatiov dell'action
    switch (action.id) {
      case PCActions.modificaEmail:
        // Apro la modale per la modifica dell'email
        this.modificaEmail();
        break;
      case PCActions.terminiCodizioniUso:
        // Apro la modale per la modifica dell'email
        this.vediTerminiCondizioniUso();
        break;
    }
  }

  /**
   * Funzione di supporto che raccoglie le informazioni profilo per la modifica dell'indirizzo email.
   */
  private modificaEmail() {
    // Definisco le callback per la chiusura della modale
    const callbacks: ICallbackDataModal = {
      onConfirm: (compilante: Compilante) => {
        // Richiamo la chiusura
        this.onModificaEmail(compilante);
      },
    };
    // Richiamo l'apertura della modale
    this._profiloContattiModal.modificaEmail(callbacks);
  }

  /**
   * Funzione invocata alla chiusura, con conferma, della modale della modifica email.
   * @param compilante Compilante contente le informazioni aggiornate del compilante salvato.
   */
  private onModificaEmail(compilante: Compilante) {
    // Aggiorno l'email del profilo
    this.aggiornaEmailModificata(compilante);
    // La modifica per l'email è stata completata, visualizzo il success
    const code = ScrivaCodesMesseges.P001;
    this.showAlert(code);
  }

  /**
   * Funzione di comodo che aggiorna il valore dell'email nel form, dato il compilante restituito dal salvataggio dati.
   * @param compilante Compilante contente le informazioni aggiornate del compilante salvato.
   */
  private aggiornaEmailModificata(compilante: Compilante) {
    // Definisco le chiavi per i form controls
    const keyEmail = this.P_C.FC_EMAIL;
    // Recupero dall'oggetto le informazioni
    const email = compilante?.des_email_compilante ?? '';
    // Definisco l'opzione per la propagazione delle informazioni
    const o: IFormUpdatePropagation = { emitEvent: false };
    // Assegno ai form control i rispettivi dati
    this.pcForm.get(keyEmail).setValue(email, o);
  }

  /**
   * Funzione di supporto che gestisce la visione dei termini e le condizioni uso.
   */
  private vediTerminiCondizioniUso() {}

  /**
   * #####################################
   * FUNZIONI DI UTILITY PER IL COMPONENTE
   * #####################################
   */

  /**
   * Funzione di supporto per la gestione degli errori nel componente, dato il codice errore.
   * @param code string con il codice del messaggio da visualizzare.
   */
  private showAlert(code: string) {
    // Definisco le configurazioni per la visualizzazione dell'alert
    const target = this.PC_C.ALERT_ANCHOR;
    const autoFade = true;
    // Visualizzo il messaggio
    this._message.showMessage(code, target, autoFade);
  }
}
