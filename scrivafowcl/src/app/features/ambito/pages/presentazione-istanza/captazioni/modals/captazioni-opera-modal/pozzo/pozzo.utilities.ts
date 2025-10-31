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
  isDefinedArray,
  isDefinedObject,
  isDefinedString,
  maxLengthString,
} from '../../../../../../../../shared/services/scriva-utilities/scriva-utilities.functions';
import { TypeSiNo } from '../../../../../../../../shared/types/formio/scriva-formio.types';
import { CaptazioniUtilities } from '../captazioni/captazioni.utilities';
import {
  IPDSDiametroColonna,
  IPDSPresenzaCementazione,
  IPDSPresenzaDreni,
  IPDSPresenzaFiltri,
  IPDSTipoFalda,
  IPozzoDatiIdentificativi,
  IPozzoDatiStrutturali,
  IPozzoProveDiEmungimento,
} from './pozzo.interfaces';

/**
 * Classe che definisce una serie di logiche di supporto per la gestione dei dati dell'opera.
 */
export class PozzoUtilities extends CaptazioniUtilities {
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
   * @param datiIdentificativi IPozzoDatiIdentificativi con le informazioni da verificare.
   * @returns boolean con il risultato del controllo.
   */
  validDatiIdentificativi(
    datiIdentificativi: IPozzoDatiIdentificativi
  ): boolean {
    // Verifico l'input
    if (!datiIdentificativi) {
      // Manca la configurazione
      return false;
    }

    // Estraggo i parametri da verificare
    const codice_roc: string = datiIdentificativi.codice_roc;
    const codice_rilievo: string = datiIdentificativi.codice_rilievo;
    const codice_sii: string = datiIdentificativi.codice_sii;
    const denominazione: string = datiIdentificativi.denominazione;
    const comune: string = datiIdentificativi.comune;
    const localita: string = datiIdentificativi.localita;
    const catas_comune: string = datiIdentificativi.catas_comune;
    const catas_sezione: string = datiIdentificativi.catas_sezione;
    const catas_foglio: string = datiIdentificativi.catas_foglio;
    const catas_particella: string = datiIdentificativi.catas_particella;
    const profondita: string = datiIdentificativi.profondita;
    const quota_piano_c: string = datiIdentificativi.quota_piano_c;
    const quota_base_acq: string = datiIdentificativi.quota_base_acq;
    const flg_campo_pozzo: TypeSiNo =
      datiIdentificativi.flg_campo_pozzo;
    const flg_pozzo_art: TypeSiNo = datiIdentificativi.flg_pozzo_art;
    const codice_roc_bck: string = datiIdentificativi.codice_roc_bck;

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
    const isProfonditaValid: boolean = this.checkProfondita(profondita);
    const isQPCValid: boolean = this.checkQuotaPianoCampagna(quota_piano_c);
    const isQBAValid: boolean = this.checkQuotaBaseAcquifero(quota_base_acq);
    const isFlgCPValid: boolean = this.checkFlgCampoPozzo(flg_campo_pozzo);
    const isFlgPAValid: boolean = this.checkFlgPozzoArtesiano(flg_pozzo_art);
    const isCodRocBckValid: boolean = this.checkCodiceRocBck(codice_roc_bck);

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
      isProfonditaValid,
      isQPCValid,
      isQBAValid,
      isFlgCPValid,
      isFlgPAValid,
      isCodRocBckValid,
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
   * @param profondita string con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkProfondita(profondita: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(profondita)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = interiDecimaliNumero(profondita, 4, 2);

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param quotaBaseAcq string con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkQuotaBaseAcquifero(quotaBaseAcq: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(quotaBaseAcq)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = interiNumero(quotaBaseAcq, 4);

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param flgCampoPozzo TypeSiNo con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkFlgCampoPozzo(flgCampoPozzo: TypeSiNo): boolean {
    // Verifico se è definito
    if (!isDefinedString(flgCampoPozzo)) {
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
   * @param flgPozzoArt TypeSiNo con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkFlgPozzoArtesiano(flgPozzoArt: TypeSiNo): boolean {
    // Verifico se è definito
    if (!isDefinedString(flgPozzoArt)) {
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
   * @param codiceRocBck string con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkCodiceRocBck(codiceRocBck: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(codiceRocBck)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = maxLengthString(codiceRocBck, 10);

    // Ritorno il risultato
    return isValid;
  }

  // #endregion "DATI IDENTIFICATIVI"

  /**
   * ################
   * DATI STRUTTURALI
   * ################
   */

  // #region "DATI STRUTTURALI"

  /**
   * Funzione che verifica la validità delle informazioni per la sezione dell'opera.
   * @param datiStrutturali IPozzoDatiStrutturali con le informazioni da verificare.
   * @returns boolean con il risultato del controllo.
   */
  validDatiStrutturali(datiStrutturali: IPozzoDatiStrutturali): boolean {
    // Verifico l'input
    if (!datiStrutturali) {
      // Manca la configurazione
      return false;
    }

    // Estraggo i parametri da verificare
    const data_costruzione: string = datiStrutturali.data_costruzione;
    const tipo_falda: IPDSTipoFalda = datiStrutturali.tipo_falda;
    const flg_disp_strat: TypeSiNo = datiStrutturali.flg_disp_strat;
    const diametro_colonna: IPDSDiametroColonna[] =
      datiStrutturali.diametro_colonna;
    const presenza_cementazione: IPDSPresenzaCementazione[] =
      datiStrutturali.presenza_cementazione;
    const presenza_dreni: IPDSPresenzaDreni[] = datiStrutturali.presenza_dreni;
    const presenza_filtri: IPDSPresenzaFiltri[] =
      datiStrutturali.presenza_filtri;

    const isDTCostruValid: boolean =
      this.checkDataCostruzione(data_costruzione);
    const isTipoFaldaValid: boolean = this.checkTipoFalda(tipo_falda);
    const isFlgDispStratValid: boolean = this.checkFlgDispStrat(flg_disp_strat);
    const isDiamColValid: boolean = this.checkDiametroColonna(diametro_colonna);
    const isPresCementValid: boolean = this.checkPresenzaCementazione(
      presenza_cementazione
    );
    const isPresDreniValid: boolean = this.checkPresenzaDreni(presenza_dreni);
    const isPresFiltriValid: boolean =
      this.checkPresenzaFiltri(presenza_filtri);

    // Definisco un array contenenti tutti i check per la sezione
    const allChecks: boolean[] = [
      isDTCostruValid,
      isTipoFaldaValid,
      isFlgDispStratValid,
      isDiamColValid,
      isPresCementValid,
      isPresDreniValid,
      isPresFiltriValid,
    ];
    // Verifico e ritorno se tutti i controlli sono OK
    return allChecks.every((check) => check);
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param dataCostruzione string con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkDataCostruzione(dataCostruzione: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(dataCostruzione)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = true;

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param tipoFalda IPDSTipoFalda con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkTipoFalda(tipoFalda: IPDSTipoFalda): boolean {
    // Verifico se è definito
    if (!isDefinedObject(tipoFalda)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = true;

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param flgDispStart TypeSiNo con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkFlgDispStrat(flgDispStart: TypeSiNo): boolean {
    // Verifico se è definito
    if (!isDefinedString(flgDispStart)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = true;

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param diametroColonna IPDSDiametroColonna[] con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkDiametroColonna(
    diametroColonna: IPDSDiametroColonna[]
  ): boolean {
    // Verifico se è definito
    if (!isDefinedArray(diametroColonna)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = true;

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param presenzaCementazione IPDSPresenzaCementazione[] con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkPresenzaCementazione(
    presenzaCementazione: IPDSPresenzaCementazione[]
  ): boolean {
    // Verifico se è definito
    if (!isDefinedArray(presenzaCementazione)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = true;

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param presenzaDreni IPDSPresenzaDreni[] con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkPresenzaDreni(presenzaDreni: IPDSPresenzaDreni[]): boolean {
    // Verifico se è definito
    if (!isDefinedArray(presenzaDreni)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = true;

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param presenzaFiltri IPDSPresenzaFiltri[] con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkPresenzaFiltri(presenzaFiltri: IPDSPresenzaFiltri[]): boolean {
    // Verifico se è definito
    if (!isDefinedArray(presenzaFiltri)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = true;

    // Ritorno il risultato
    return isValid;
  }

  // #endregion "DATI STRUTTURALI"

  /**
   * ####################
   * PROVE DI EMUNGIMENTO
   * ####################
   */

  // #region "PROVE DI EMUNGIMENTO"

  /**
   * Funzione che verifica la validità delle informazioni per la sezione dell'opera.
   * @param datiStrutturali IPozzoProveDiEmungimento con le informazioni da verificare.
   * @returns boolean con il risultato del controllo.
   */
  validProveDiEmungimento(proveEmungimento: IPozzoProveDiEmungimento): boolean {
    // Verifico l'input
    if (!proveEmungimento) {
      // Manca la configurazione
      return false;
    }

    // Estraggo i parametri da verificare
    const flg_disp_mis_piez: TypeSiNo =
      proveEmungimento.flg_disp_mis_piez;
    const piez_assoc: string = proveEmungimento.piez_assoc;
    const emungimento_gradini: any[] = proveEmungimento.emungimento_gradini;
    const emungimento_lunga_durata: any[] =
      proveEmungimento.emungimento_lunga_durata;

    const isFlgDMPValid: boolean = this.checkFlgDispMisPiez(flg_disp_mis_piez);
    const isPiezAssocValid: boolean = this.checkPiezAssoc(piez_assoc);
    const isEmunGradiniValid: boolean =
      this.checkEmungimentoGradini(emungimento_gradini);
    const isEmunLungaDurataValid: boolean = this.checkEmungimentoLungaDurata(
      emungimento_lunga_durata
    );

    // Definisco un array contenenti tutti i check per la sezione
    const allChecks: boolean[] = [
      isFlgDMPValid,
      isPiezAssocValid,
      isEmunGradiniValid,
      isEmunLungaDurataValid,
    ];
    // Verifico e ritorno se tutti i controlli sono OK
    return allChecks.every((check) => check);
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param flgDispMisPiez TypeSiNo con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkFlgDispMisPiez(flgDispMisPiez: TypeSiNo): boolean {
    // Verifico se è definito
    if (!isDefinedString(flgDispMisPiez)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = true;

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param piezAssoc string con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkPiezAssoc(piezAssoc: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(piezAssoc)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = interiNumero(piezAssoc, 2);

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param emungimentoGradini any[] con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkEmungimentoGradini(emungimentoGradini: any[]): boolean {
    // Verifico se è definito
    if (!isDefinedArray(emungimentoGradini)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = true;

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param emungimentoLungaDurata any[] con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkEmungimentoLungaDurata(emungimentoLungaDurata: any[]): boolean {
    // Verifico se è definito
    if (!isDefinedArray(emungimentoLungaDurata)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = true;

    // Ritorno il risultato
    return isValid;
  }

  // #endregion "PROVE DI EMUNGIMENTO"
}
