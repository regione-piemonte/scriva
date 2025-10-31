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
  isDefinedObject,
  isDefinedString,
  maxLengthString,
} from '../../../../../../../../shared/services/scriva-utilities/scriva-utilities.functions';
import { TypeSiNo } from '../../../../../../../../shared/types/formio/scriva-formio.types';
import { CaptazioniUtilities } from '../captazioni/captazioni.utilities';
import {
  ISDSTipoSorgente,
  ISorgenteDatiIdentificativi,
  ISorgenteDatiSpecifici,
} from './sorgente.interfaces';

/**
 * Classe che definisce una serie di logiche di supporto per la gestione dei dati dell'opera.
 */
export class SorgenteUtilities extends CaptazioniUtilities {
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
   * @param datiIdentificativi ISorgenteDatiIdentificativi con le informazioni da verificare.
   * @returns boolean con il risultato del controllo.
   */
  validDatiIdentificativi(
    datiIdentificativi: ISorgenteDatiIdentificativi
  ): boolean {
    // Verifico l'input
    if (!datiIdentificativi) {
      // Manca la configurazione
      return false;
    }

    // Estraggo i parametri da verificare
    const codice_roc: string = datiIdentificativi?.codice_roc;
    const codice_rilievo: string = datiIdentificativi?.codice_rilievo;
    const codice_sii: string = datiIdentificativi?.codice_sii;
    const denominazione: string = datiIdentificativi?.denominazione;
    const comune: string = datiIdentificativi?.comune;
    const localita: string = datiIdentificativi?.localita;
    const catas_comune: string = datiIdentificativi?.catas_comune;
    const catas_sezione: string = datiIdentificativi?.catas_sezione;
    const catas_foglio: string = datiIdentificativi?.catas_foglio;
    const catas_particella: string = datiIdentificativi?.catas_particella;
    const quota_piano_c: string = datiIdentificativi.quota_piano_c;

    const isCodRocValid: boolean = this.checkCodiceRoc(codice_roc);
    const isCodRilievoValid: boolean = this.checkCodiceRilievo(codice_rilievo);
    const isCodSiiValid: boolean = this.checkCodiceSii(codice_sii);
    const isDenomValid: boolean = this.checkDenominazione(denominazione);
    const isComuneValid: boolean = this.checkComune(comune);
    const isLocalitaValid: boolean = this.checkLocalita(localita);
    const isCodCatasValid: boolean = this.checkCodiceCatastale(catas_comune);
    const isSezioneValid: boolean = this.checkSezioneCatastale(catas_sezione);
    const isFoglioValid: boolean = this.checkFoglioCatastale(catas_foglio);
    const isParticellaValid: boolean =
      this.checkParticellaCatastale(catas_particella);
    const isQPCValid: boolean = this.checkQuotaPianoCampagna(quota_piano_c);

    // Definisco un array contenenti tutti i check per la sezione
    const allChecks: boolean[] = [
      isCodRocValid,
      isCodRilievoValid,
      isCodSiiValid,
      isDenomValid,
      isComuneValid,
      isLocalitaValid,
      isCodCatasValid,
      isSezioneValid,
      isFoglioValid,
      isParticellaValid,
      isQPCValid,
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

  /**
   * #############################
   * DATI SPECIFICI DELLA SORGENTE
   * #############################
   */

  // #region "DATI SPECIFICI DELLA SORGENTE"

  /**
   * Funzione che verifica la validità delle informazioni per la sezione dell'opera.
   * @param datiSpecifici ISorgenteDatiSpecifici con le informazioni da verificare.
   * @returns boolean con il risultato del controllo.
   */
  validDatiSpecifici(datiSpecifici: ISorgenteDatiSpecifici): boolean {
    // Verifico l'input
    if (!datiSpecifici) {
      // Manca la configurazione
      return false;
    }

    // Estraggo i parametri da verificare
    const tipo_sorg: ISDSTipoSorgente = datiSpecifici.tipo_sorg;
    const flg_tributaria: TypeSiNo = datiSpecifici.flg_tributaria;

    const isTipoSorgValid: boolean = this.checkTipoSorgente(tipo_sorg);
    const isFlgTribuValid: boolean = this.checkFlgTributaria(flg_tributaria);

    // Definisco un array contenenti tutti i check per la sezione
    const allChecks: boolean[] = [isTipoSorgValid, isFlgTribuValid];
    // Verifico e ritorno se tutti i controlli sono OK
    return allChecks.every((check) => check);
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param tipoSorgente ISDSTipoSorgente con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkTipoSorgente(tipoSorgente: ISDSTipoSorgente): boolean {
    // Verifico se è definito
    if (!isDefinedObject(tipoSorgente)) {
      // E' obbligatorio
      return false;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = true;

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param flgTributaria TypeSiNo con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkFlgTributaria(flgTributaria: TypeSiNo): boolean {
    // Verifico se è definito
    if (!isDefinedString(flgTributaria)) {
      // E' obbligatorio
      return false;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = true;

    // Ritorno il risultato
    return isValid;
  }

  // #endregion "DATI SPECIFICI DELLA SORGENTE"
}
