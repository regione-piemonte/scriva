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
  interiDecimaliNumero,
  interiNumero,
  isDefinedString,
  maxLengthString,
  numeroITAMaggioreDiZero,
} from '../../../../../../../../shared/services/scriva-utilities/scriva-utilities.functions';
import { ICheckValoreMinMax } from '../captazioni/captazioni.interfaces';
import { CaptazioniUtilities } from '../captazioni/captazioni.utilities';
import { ITrinceaDrenanteDatiIdentificativi } from './trincea-drenante.interfaces';

/**
 * Classe che definisce una serie di logiche di supporto per la gestione dei dati dell'opera.
 */
export class TrinceaDrenanteUtilities extends CaptazioniUtilities {
  /**
   * Costruttore.
   */
  constructor(isBackOffice: boolean, isFrontOffice: boolean) {
    super(isBackOffice, isFrontOffice);
  }

  /**
   * ###################
   * DATI IDENTIFICATIVI
   * ###################
   */

  // #region "DATI IDENTIFICATIVI"

  /**
   * Funzione che verifica la validità delle informazioni per la sezione dell'opera.
   * @param datiIdentificativi ITrinceaDrenanteDatiIdentificativi con le informazioni da verificare.
   * @returns boolean con il risultato del controllo.
   */
  validDatiIdentificativi(
    datiIdentificativi: ITrinceaDrenanteDatiIdentificativi
  ): boolean {
    // Verifico l'input
    if (!datiIdentificativi) {
      // Manca la configurazione
      return false;
    }

    // Estraggo i parametri da verificare
    const codice_roc: string = datiIdentificativi?.codice_roc;
    const codice_rilievo: string = datiIdentificativi?.codice_rilievo;
    const denominazione: string = datiIdentificativi?.denominazione;
    const comune: string = datiIdentificativi?.comune;
    const localita: string = datiIdentificativi?.localita;
    const catas_comune: string = datiIdentificativi?.catas_comune;
    const catas_sezione: string = datiIdentificativi?.catas_sezione;
    const catas_foglio: string = datiIdentificativi?.catas_foglio;
    const catas_particella: string = datiIdentificativi?.catas_particella;
    const sviluppo_long: string = datiIdentificativi?.sviluppo_long;
    const inclinazione: string = datiIdentificativi?.inclinazione;
    const profondita_max: string = datiIdentificativi?.profondita_max;
    const profondita_min: string = datiIdentificativi?.profondita_min;

    const isCodRocValid: boolean = this.checkCodiceRoc(codice_roc);
    const isCodRilievoValid: boolean = this.checkCodiceRilievo(codice_rilievo);
    const isDenomValid: boolean = this.checkDenominazione(denominazione);
    const isComuneValid: boolean = this.checkComune(comune);
    const isLocalitaValid: boolean = this.checkLocalita(localita);
    const isCodCatasValid: boolean = this.checkCodiceCatastale(catas_comune);
    const isSezioneValid: boolean = this.checkSezioneCatastale(catas_sezione);
    const isFoglioValid: boolean = this.checkFoglioCatastale(catas_foglio);
    const isParticellaValid: boolean =
      this.checkParticellaCatastale(catas_particella);
    const isSvilLongValid: boolean = this.checkSviluppoLong(sviluppo_long);
    const isInclValid: boolean = this.checkInclinazione(inclinazione);
    const isProfMaxValid: boolean = this.checkProfonditaMax(profondita_max);
    const isProfMinValid: boolean = this.checkProfonditaMin(
      profondita_min,
      profondita_max
    );

    // Definisco un array contenenti tutti i check per la sezione
    const allChecks: boolean[] = [
      isCodRocValid,
      isCodRilievoValid,
      isDenomValid,
      isComuneValid,
      isLocalitaValid,
      isCodCatasValid,
      isSezioneValid,
      isFoglioValid,
      isParticellaValid,
      isSvilLongValid,
      isInclValid,
      isProfMaxValid,
      isProfMinValid,
    ];
    // Verifico e ritorno se tutti i controlli sono OK
    return allChecks.every((check) => check);
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param denominazione string con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkDenominazione(denominazione: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(denominazione)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = maxLengthString(denominazione, 100);

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param profonditaMin string con l'input da verificare.
   * @returns boolean con il risultato del check.
   */
  protected checkSviluppoLong(sviluppoLong: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(sviluppoLong)) {
      // E' obbligatorio
      return false;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = interiNumero(sviluppoLong, 4);
    // Verifico che il numero sia maggiore di zero
    let greatThanZero: boolean = false;
    greatThanZero = numeroITAMaggioreDiZero(sviluppoLong);

    // Ritorno il risultato
    return isValid && greatThanZero;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param profonditaMin string con l'input da verificare.
   * @returns boolean con il risultato del check.
   */
  protected checkInclinazione(inclinazione: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(inclinazione)) {
      // E' obbligatorio
      return false;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = interiNumero(inclinazione, 2);
    // Verifico che il numero sia maggiore di zero
    let greatThanZero: boolean = false;
    greatThanZero = numeroITAMaggioreDiZero(inclinazione);

    // Ritorno il risultato
    return isValid && greatThanZero;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param profonditaMin string con l'input da verificare.
   * @returns boolean con il risultato del check.
   */
  protected checkProfonditaMax(profonditaMax: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(profonditaMax)) {
      // E' obbligatorio
      return false;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = interiDecimaliNumero(profonditaMax, 2, 2);
    // Verifico che il numero sia maggiore di zero
    let greatThanZero: boolean = false;
    greatThanZero = numeroITAMaggioreDiZero(profonditaMax);

    // Ritorno il risultato
    return isValid && greatThanZero;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param profonditaMin string con l'input da verificare.
   * @param profonditaMax string con l'input per la verifica.
   * @returns boolean con il risultato del check.
   */
  protected checkProfonditaMin(
    profonditaMin: string,
    profonditaMax: string
  ): boolean {
    // Definisco le configurazioni
    const minConfig: ICheckValoreMinMax = {
      valore: profonditaMin,
      interi: 2,
      decimali: 2,
      required: true,
      allowMinZero: false,
    };
    const maxConfig: ICheckValoreMinMax = {
      valore: profonditaMax,
      interi: 2,
      decimali: 2,
      allowMaxZero: false,
    };

    // Lancio il controllo e ritorno il risultato
    return this.checkMinimoEMassimo(minConfig, maxConfig);
  }

  // #endregion "DATI IDENTIFICATIVI"
}
