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
import { FormGroup } from '@angular/forms';
import { IMappaErroriFormECodici } from '../../components/form-inputs/form-input/utilities/form-input.interfaces';

/**
 * Servizio di utility con funzionalità di comodo per i componenti di form-inputs.
 */
@Injectable({
  providedIn: 'root',
})
export class FormInputsService {
  /**
   * Costruttore.
   */
  constructor() {}

  /**
   * Funzione che verifica se esistono errori sul FormGroup.
   * @param formGroup FormGruop da verificare.
   * @param mappaErroriFG IMappaErroriFormECodici[] contenente la lista dei possibili errori.
   * @returns boolean che definsce se all'interno del FormGroup esistono gli errori presenti nel mapping.
   */
  getFormErrors(
    formGroup: FormGroup,
    mappaErroriFG: IMappaErroriFormECodici[]
  ) {
    // Verifico l'input
    if (!formGroup || !mappaErroriFG || mappaErroriFG.length === 0) {
      // Ritorno false
      return false;
    }

    // Verifico se il form ha generato un errore
    for (let i = 0; i < mappaErroriFG.length; i++) {
      // Recupero l'oggetto all'indice
      const errore = mappaErroriFG[i];
      // Verifico se nel form esiste l'errore estratto
      if (formGroup.errors && formGroup.errors[errore.erroreForm]) {
        // Errore trovato, blocco il ciclo e ritorno true
        return true;
      }
    }

    // Non ho trovato niente, ritorno false
    return false;
  }
}
