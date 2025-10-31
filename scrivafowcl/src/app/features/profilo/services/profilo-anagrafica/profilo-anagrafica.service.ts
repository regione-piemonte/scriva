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
import { FormControl, FormGroup } from '@angular/forms';
import { DynamicObjAny } from '../../../../core/interfaces/scriva.interfaces';
import { IFormUpdatePropagation } from '../../../../shared/interfaces/scriva-utilities.interfaces';
import { PROFILO_CONSTS } from '../../pages/profilo/utilities/profilo.consts';
import { IDatiProfilo } from '../../pages/profilo/utilities/profilo.interfaces';
import { ProfiloService } from '../profilo/profilo.service';

@Injectable({ providedIn: 'root' })
export class ProfiloAnagraficaService {
  /** Costante contenente le informazioni costanti del componente. */
  private P_C = PROFILO_CONSTS;

  /**
   * Costruttore.
   */
  constructor(private _profilo: ProfiloService) {}

  /**
   * ################################################
   * FUNZIONI PER LA GESTIONE DEL FORM DEL COMPONENTE
   * ################################################
   */

  /**
   * Funzione di init di supporto per la generazione delle configurazioni per il form group del componente.
   * @returns DynamicObjAny contenente le configurazioni dei campi del form.
   */
  generateControlsConfig(): DynamicObjAny {
    // Definisco un oggetto composto da chiavi definite come costanti per la configurazione del form del componente
    const controlsConfig: DynamicObjAny = {};
    // Definisco chiave e valore specifico per ogni elemento del form
    // # Codice fiscale
    controlsConfig[this.P_C.FC_CODICE_FISCALE] = new FormControl(
      { value: null, disabled: false },
      { validators: [] }
    );
    // # Cognome
    controlsConfig[this.P_C.FC_COGNOME] = new FormControl(
      { value: null, disabled: false },
      { validators: [] }
    );
    // # Nome
    controlsConfig[this.P_C.FC_NOME] = new FormControl(
      { value: null, disabled: false },
      { validators: [] }
    );

    // Ritorno l'insieme di configurazioni
    return controlsConfig;
  }

  /**
   * Funzione che gestisce il set delle informazioni del form del componente.
   * La funzione gestirà le informazioni aggiornando il form per referenza.
   * @param f FormGroup con la struttura del form da valorizzare.
   */
  setFormInfo(f: FormGroup) {
    // Verifico l'input
    if (!f) {
      // Nessuna base per l'aggiornamento
      return;
    }

    // Recupero i dati del profilo
    const profilo = this._profilo.profilo;
    // Richiamo il set dati per il form
    this.setFormConDatiProfilo(f, profilo);
  }

  /**
   * Funzione di supporto che dato in input un oggetto di configurazione, assegna ai campi del form i rispettivi valori.
   * @param f FormGrup con la struttura del form da valorizzare.
   * @param datiProfilo IDatiProfilo come configurazione per il set dati.
   * @param propagation IFormUpdatePropagation che definisce le opzioni di configurazione per la propagazione degli eventi.
   */
  setFormConDatiProfilo(
    f: FormGroup,
    datiProfilo: IDatiProfilo,
    propagation?: IFormUpdatePropagation
  ) {
    // Verifico l'input
    if (!f || !datiProfilo) {
      // Niente dati
      return;
    }

    // Definisco le chiavi per i form controls
    const keyCF = this.P_C.FC_CODICE_FISCALE;
    const keyC = this.P_C.FC_COGNOME;
    const keyN = this.P_C.FC_NOME;
    // Recupero dall'oggetto le informazioni
    const codiceFiscale = datiProfilo?.codiceFiscale ?? '';
    const cognome = datiProfilo?.cognome ?? '';
    const nome = datiProfilo?.nome ?? '';
    // Definisco l'opzione per la propagazione delle informazioni
    const o: IFormUpdatePropagation = propagation ?? { emitEvent: false };

    // Assegno ai form control i rispettivi dati
    f.get(keyCF).setValue(codiceFiscale, o);
    f.get(keyC).setValue(cognome, o);
    f.get(keyN).setValue(nome, o);
  }

  /**
   * Funzione di supporto che imposta l'accessibilità al form in base al tipo di componente applicativo.
   * La funzione agisce per riferimento.
   */
  setAccessibilitaForm(f: FormGroup) {
    // Verifico l'input
    if (!f) {
      // Niente dati
      return;
    }

    // Il form del profilo anagrafica risulterà sempre bloccato
    f.disable();
  }
}
