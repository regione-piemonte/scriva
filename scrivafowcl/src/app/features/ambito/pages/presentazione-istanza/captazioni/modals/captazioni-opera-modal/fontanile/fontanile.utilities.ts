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
  isDefinedString,
  maxLengthString,
} from '../../../../../../../../shared/services/scriva-utilities/scriva-utilities.functions';
import { CaptazioniUtilities } from '../captazioni/captazioni.utilities';
import { IFontanileDatiIdentificativi } from './fontanile.interfaces';

/**
 * Classe che definisce una serie di logiche di supporto per la gestione dei dati dell'opera.
 */
export class FontanileUtilities extends CaptazioniUtilities {
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
   * @param datiIdentificativi IFontanileDatiIdentificativi con le informazioni da verificare.
   * @returns boolean con il risultato del controllo.
   */
  validDatiIdentificativi(
    datiIdentificativi: IFontanileDatiIdentificativi
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

  // #endregion "DATI IDENTIFICATIVI"
}
