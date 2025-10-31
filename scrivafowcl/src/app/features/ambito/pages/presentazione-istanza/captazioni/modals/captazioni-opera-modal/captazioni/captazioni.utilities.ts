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
  importoITAToJsFloat,
  interiDecimaliNumero,
  interiNumero,
  isDefinedArray,
  isDefinedObject,
  isDefinedString,
  maxLengthString,
  numeroITAMaggioreDiZero,
} from '../../../../../../../../shared/services/scriva-utilities/scriva-utilities.functions';
import { TypeSiNo } from '../../../../../../../../shared/types/formio/scriva-formio.types';
import {
  ICaptazioneRilascioAValle,
  ICaptazioniEsercizioDellaCaptazione,
  ICaptEDCQtaRisorsaCaptata,
  ICaptEDCStatoEsercizio,
  ICaptRAVModulazione,
  ICheckValoreMinMax,
} from './captazioni.interfaces';

/**
 * Classe che definisce una serie di logiche di supporto per la gestione dei dati dell'opera.
 */
export class CaptazioniUtilities {
  /**
   * Costruttore.
   */
  constructor(
    protected isBackOffice: boolean,
    protected isFrontOffice: boolean
  ) {}

  /**
   * #############################
   * FUNZIONI GENERICHE DI UTILITY
   * #############################
   */

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * La funzione è generica per il tipo di campo: portata.
   * @param portata string con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  protected checkPortata(portata: string): boolean {
    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = interiDecimaliNumero(portata, 7, 4);

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param portataMedia string con l'input da verificare.
   * @param portataMassima string con l'input da verificare.
   * @param required boolean che definisce se la portata media è required. Per default è: false.
   * @return boolean con il risultato del check. Viene ritornato undefined se la portata massima non è definita.
   */
  protected checkPortataMedia(
    portataMedia: string,
    portataMassima: string,
    required: boolean = false
  ): boolean {
    // Verifico la portata media
    const definedPMed = isDefinedString(portataMedia);
    const validPMed = definedPMed && this.checkPortata(portataMedia);
    if (!validPMed) {
      // Verifico se è richiesta
      return required ? false : true;
    }
    // Verifico la portata massima
    const definedPMax = isDefinedString(portataMassima);
    const validPMax = definedPMax && this.checkPortata(portataMassima);
    if (!validPMax) {
      // Manca la portata massima
      return undefined;
    }

    // Le informazioni sono stringhe, devo sostituire il separatore decimale
    const portataMediaNum = importoITAToJsFloat(portataMedia);
    const portataMassimaNum = importoITAToJsFloat(portataMassima);
    // Verifico se la portata media è required, ma ha valore 0
    if (required && portataMediaNum <= 0) {
      // Validazione fallita, ritorno un "codice errore"
      return false;
      // #
    }

    // Verifico che siano numeri validi
    const existPMed = portataMediaNum != undefined;
    const existPMax = portataMassimaNum != undefined;
    const existPortate = existPMed && existPMax;
    // Devono esistere entrambe le portate per la verifica
    // La portata media deve essere minore/uguale alla massima
    if (!existPortate || portataMediaNum <= portataMassimaNum) {
      // Validazione completa
      return true;
      // #
    } else {
      // Validazione fallita, ritorno un "codice errore"
      return false;
    }
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param minimoConfig ICheckValoreMinMax con l'input da verificare.
   * @param massimoConfig ICheckValoreMinMax con l'input da verificare.
   * @param required boolean che definisce se la portata media è required. Per default è: false.
   * @return boolean con il risultato del check. Viene ritornato undefined se la portata massima non è definita.
   */
  protected checkMinimoEMassimo(
    minimoConfig: ICheckValoreMinMax,
    massimoConfig: ICheckValoreMinMax
  ): boolean {
    // Estraggo le informazioni per la verifica dei campi
    const valoreMinimo = minimoConfig.valore;
    const interiMin = minimoConfig.interi ?? 10;
    const decimaliMin = minimoConfig.decimali ?? 0;
    const requiredMin = minimoConfig.required;
    // Se non definito, il minimo è consentito sia 0
    const allowMinZero = minimoConfig.allowMinZero ?? true;
    const valoreMassimo = massimoConfig.valore;
    const interiMax = massimoConfig.interi ?? 10;
    const decimaliMax = massimoConfig.decimali ?? 0;
    const allowMaxZero = massimoConfig.allowMaxZero ?? false;

    // Verifico il valore minimo
    const definedVMin = isDefinedString(valoreMinimo);
    const validVMin =
      definedVMin && interiDecimaliNumero(valoreMinimo, interiMin, decimaliMin);
    if (!validVMin) {
      // Verifico se è richiesta
      return requiredMin ? false : true;
      // #
    }
    // Verifico che il numero sia maggiore di zero
    let greatThanZero: boolean = false;
    greatThanZero = numeroITAMaggioreDiZero(valoreMinimo);
    // Verifico se il valore minimo può essere 0
    if (!allowMinZero && !greatThanZero) {
      // Ritorno errore
      return false;
      // #
    }

    // Verifico il valore massimo
    const definedVMax = isDefinedString(valoreMassimo);
    const validVMax =
      definedVMax &&
      interiDecimaliNumero(valoreMassimo, interiMax, decimaliMax);
    if (!validVMax) {
      // Valore massimo non valido
      return undefined;
    }

    // Le informazioni sono stringhe, devo sostituire il separatore decimale
    const minimoNum = importoITAToJsFloat(valoreMinimo);
    const massimoNum = importoITAToJsFloat(valoreMassimo);
    // Verifico che siano numeri validi
    const existVMin = minimoNum != undefined;
    let existVMax = massimoNum != undefined;
    // Verifico se per il valore massimo, 0 è da ignorare come valore
    if (allowMaxZero) {
      // Aggiungo la condizione
      existVMax = existVMax && massimoNum != 0;
      //
    }
    // Definisco il flag di verifica
    const existValues = existVMin && existVMax;
    // Devono esistere entrambe le portate per la verifica
    // La portata media deve essere minore/uguale alla massima
    if (!existValues || minimoNum <= massimoNum) {
      // Validazione completa
      return true;
      // #
    } else {
      // Validazione fallita, ritorno un "codice errore"
      return false;
    }
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * La funzione è generica per il tipo di campo: portata.
   * @param volume string con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  protected checkVolume(volume: string): boolean {
    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = interiDecimaliNumero(volume, 9, 5);

    // Ritorno il risultato
    return isValid;
  }

  /**
   * ###################
   * DATI IDENTIFICATIVI
   * ###################
   */

  // #region "DATI IDENTIFICATIVI"

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param codiceRoc string con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  protected checkCodiceRoc(codiceRoc: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(codiceRoc)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = maxLengthString(codiceRoc, 10);

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param codiceRilievo string con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  protected checkCodiceRilievo(codiceRilievo: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(codiceRilievo)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = maxLengthString(codiceRilievo, 14);

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param codiceSii string con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  protected checkCodiceSii(codiceSii: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(codiceSii)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = maxLengthString(codiceSii, 10);

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param comune string con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  protected checkComune(comune: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(comune)) {
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
   * @param localita string con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  protected checkLocalita(localita: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(localita)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = maxLengthString(localita, 100);

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param catasComune string con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  protected checkCodiceCatastale(catasComune: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(catasComune)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = maxLengthString(catasComune, 4);

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param catasSezione string con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  protected checkSezioneCatastale(catasSezione: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(catasSezione)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = maxLengthString(catasSezione, 2);

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param catasFoglio string con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  protected checkFoglioCatastale(catasFoglio: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(catasFoglio)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = interiNumero(catasFoglio, 5);

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param catasParticella string con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  protected checkParticellaCatastale(catasParticella: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(catasParticella)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = maxLengthString(catasParticella, 10);

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param quotaPianoC string con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  protected checkQuotaPianoCampagna(quotaPianoC: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(quotaPianoC)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = interiNumero(quotaPianoC, 4);

    // Ritorno il risultato
    return isValid;
  }

  // #endregion "DATI IDENTIFICATIVI"

  /**
   * ##########################
   * ESERCIZIO DELLA CAPTAZIONE
   * ##########################
   */

  // #region "ESERCIZIO DELLA CAPTAZIONE"

  /**
   * Funzione che verifica la validità delle informazioni per la sezione dell'opera.
   * @param esercizioDellaCaptazione IPozzoEsercizioDellaCaptazione con le informazioni da verificare.
   * @returns boolean con il risultato del controllo.
   */
  validEsercizioDellaCaptazione(
    esercizioDellaCaptazione: ICaptazioniEsercizioDellaCaptazione
  ): boolean {
    // Verifico l'input
    if (!esercizioDellaCaptazione) {
      // Manca la configurazione
      return false;
    }

    // Estraggo i parametri da verificare
    const stato_esercizio: ICaptEDCStatoEsercizio =
      esercizioDellaCaptazione.stato_esercizio;
    const portata_max_deriv: string =
      esercizioDellaCaptazione.portata_max_deriv;
    const portata_med_deriv: string =
      esercizioDellaCaptazione.portata_med_deriv;
    const volume_max: string = esercizioDellaCaptazione.volume_max_concessione;
    const qta_risorsa_captata: ICaptEDCQtaRisorsaCaptata[] =
      esercizioDellaCaptazione.qta_risorsa_captata;

    const isStatEseValid: boolean = this.checkStatoEsercizio(stato_esercizio);
    const isPortMaxValid: boolean =
      this.checkPortataMassimaEDC(portata_max_deriv);
    const isPortMedValid: boolean = this.checkPortataMediaEDC(
      portata_med_deriv,
      portata_max_deriv
    );
    const isVolumeMaxValid: boolean = this.checkVolumeMassimoEDC(volume_max);
    const isQtaRisCaptValid: boolean =
      this.checkQuantitaRisorseCaptata(qta_risorsa_captata);

    // Definisco un array contenenti tutti i check per la sezione
    const allChecks: boolean[] = [
      isStatEseValid,
      isPortMaxValid,
      isPortMedValid,
      isVolumeMaxValid,
      isQtaRisCaptValid,
    ];
    // Verifico e ritorno se tutti i controlli sono OK
    return allChecks.every((check) => check);
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param codiceEsercizio ICaptEDCStatoEsercizio con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  protected checkStatoEsercizio(
    codiceEsercizio: ICaptEDCStatoEsercizio
  ): boolean {
    // Verifico se è definito
    if (!isDefinedObject(codiceEsercizio)) {
      // Oggetto non definito, verifico se sono nel BO che lo richiede
      return this.isBackOffice ? false : true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = true;

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param portataMassima string con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  protected checkPortataMassimaEDC(portataMassima: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(portataMassima)) {
      // E' obbligatorio
      return false;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = true;
    isValid = this.checkPortata(portataMassima);

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param portataMedia string con l'input da verificare.
   * @param portataMedia string con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  protected checkPortataMediaEDC(
    portataMedia: string,
    portataMassima: string
  ): boolean {
    // Definisco la condizione di validità
    let isValid: boolean = false;

    // Effettuo la verifica per la portata media
    const validPMed = this.checkPortataMedia(
      portataMedia,
      portataMassima,
      true
    );
    // La condizione è valida (true oppure undefined che indica la mancanza della portata massima)
    isValid = validPMed === true || validPMed === undefined;

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param volumeMassimo string con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  protected checkVolumeMassimoEDC(volumeMassimo: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(volumeMassimo)) {
      // E' obbligatorio
      return false;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = true;
    isValid = this.checkVolume(volumeMassimo);

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param qtaRisorsaCaptata ICaptEDCQtaRisorsaCaptata[] con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  protected checkQuantitaRisorseCaptata(
    qtaRisorsaCaptata: ICaptEDCQtaRisorsaCaptata[]
  ): boolean {
    // Verifico se è definito
    if (!isDefinedArray(qtaRisorsaCaptata)) {
      // E' obbligatorio
      return false;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = true;

    // Ritorno il risultato
    return isValid;
  }

  // #endregion "ESERCIZIO DELLA CAPTAZIONE"

  /**
   * ############################
   * RILASCIO A VALLE DELLA PRESA
   * ############################
   */

  // #region "RILASCIO A VALLE DELLA PRESA"

  /**
   * Funzione che verifica la validità delle informazioni per la sezione dell'opera.
   * @param rilascioAValle IPresaRilascioAValle con le informazioni da verificare.
   * @returns boolean con il risultato del controllo.
   */
  validRilascioAValle(rilascioAValle: ICaptazioneRilascioAValle): boolean {
    // Verifico l'input
    if (!rilascioAValle) {
      // Manca la configurazione
      return false;
    }

    // Estraggo i parametri da verificare
    const flg_dispositivi: TypeSiNo = rilascioAValle.flg_dispositivi;
    const rilascio_imposto: any = rilascioAValle.rilascio_imposto;
    const flg_de: TypeSiNo = rilascioAValle.flg_de;
    const modulazione: ICaptRAVModulazione = rilascioAValle.modulazione;
    const flg_deroga_de: TypeSiNo = rilascioAValle.flg_deroga_de;
    const ulteriori_obblighi: string = rilascioAValle.ulteriori_obblighi;

    const isFlgDispValid: boolean = this.checkFlgDispositivi(flg_dispositivi);
    const isRilasImpValid: boolean =
      this.checkRilascioImposto(rilascio_imposto);
    const isFlgDeValid: boolean = this.checkFlgDe(flg_de, flg_dispositivi);
    const isModulazioneValid: boolean = this.checkModulazione(
      modulazione,
      flg_de
    );
    const isFlgDerogaDeValid: boolean = this.checkFlgDerogaDe(flg_deroga_de);
    const isUltObbValid: boolean =
      this.checkUlterioriObblighi(ulteriori_obblighi);

    // Definisco un array contenenti tutti i check per la sezione
    const allChecks: boolean[] = [
      isFlgDispValid,
      isRilasImpValid,
      isFlgDeValid,
      isModulazioneValid,
      isFlgDerogaDeValid,
      isUltObbValid,
    ];
    // Verifico e ritorno se tutti i controlli sono OK
    return allChecks.every((check) => check);
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param flgDispositivi TypeSiNo con l'input da verificare.
   * @returns boolean con il risultato del check.
   */
  protected checkFlgDispositivi(flgDispositivi: TypeSiNo): boolean {
    // Verifico se è definito
    if (!isDefinedString(flgDispositivi)) {
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
   * @param rilascioImposto any[] con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  protected checkRilascioImposto(rilascioImposto: any[]): boolean {
    // Verifico se è definito
    if (!isDefinedArray(rilascioImposto)) {
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
   * @param flgDe TypeSiNo con l'input da verificare.
   * @param flgDispositivi TypeSiNo con l'input per le verifiche incrociate.
   * @returns boolean con il risultato del check.
   */
  protected checkFlgDe(flgDe: TypeSiNo, flgDispositivi: TypeSiNo): boolean {
    // Verifico se il flag dispostivi è "si"
    const isFlgDispSi: boolean = flgDispositivi === 'si';
    // Verifico se il flag DE è definito
    const isFlgDeDefined: boolean = isDefinedString(flgDe);

    // Verifico se il flag dispositivo è definito
    if (isFlgDispSi) {
      // Il flag dispositivi è si, verifico che esista il campo
      if (isFlgDeDefined) {
        // E' obbligatorio ed esiste
        return true;
        // #
      } else {
        // E' obbligatorio, ma non esiste
        return false;
        // #
      }
    } else {
      // Non è definito il flg dispositivo
      return true;
    }
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param modulazione IPRAVModulazione con l'input da verificare.
   * @param flgDe TypeSiNo con l'input per le verifiche incrociate.
   * @returns boolean con il risultato del check.
   */
  protected checkModulazione(
    modulazione: ICaptRAVModulazione,
    flgDe: TypeSiNo
  ): boolean {
    // Verifico se il flag dispostivi è "si"
    const isFlgDispSi: boolean = flgDe === 'si';
    // Verifico se il flag DE è definito
    const isModulazioneDefined: boolean = isDefinedObject(modulazione);

    // Verifico se il flag dispositivo è definito
    if (isFlgDispSi) {
      // Il flag dispositivi è si, verifico che esista il campo
      if (isModulazioneDefined) {
        // E' obbligatorio ed esiste
        return true;
        // #
      } else {
        // E' obbligatorio, ma non esiste
        return false;
        // #
      }
    } else {
      // Non è definito il flg dispositivo
      return true;
    }
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param flgDerogaDe TypeSiNo con l'input da verificare.
   * @returns boolean con il risultato del check.
   */
  protected checkFlgDerogaDe(flgDerogaDe: TypeSiNo): boolean {
    // Verifico se è definito
    if (!isDefinedString(flgDerogaDe)) {
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
   * @param ulterioriObblighi string con l'input da verificare.
   * @returns boolean con il risultato del check.
   */
  protected checkUlterioriObblighi(ulterioriObblighi: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(ulterioriObblighi)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = maxLengthString(ulterioriObblighi, 4000);

    // Ritorno il risultato
    return isValid;
  }

  // #endregion "RILASCIO A VALLE DELLA PRESA"
}
