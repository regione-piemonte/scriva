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
  isDefinedObject,
  isDefinedString,
  maxLengthString,
} from '../../../../../../../../shared/services/scriva-utilities/scriva-utilities.functions';
import { TypeSiNo } from '../../../../../../../../shared/types/formio/scriva-formio.types';
import { CaptazioniUtilities } from '../captazioni/captazioni.utilities';
import {
  IPDISponda,
  IPDISpondaScala,
  IPDITipoPresa,
  IPDITipoSbarramento,
  IPDITipoScala,
  IPresaDatiIdentificativi,
  IPresaDatiInfrastrutturali,
} from './presa.interfaces';

/**
 * Classe che definisce una serie di logiche di supporto per la gestione dei dati dell'opera.
 * @todo E' stato commesso un errore di nomenclatura, questa "presa" fa riferimento ad "presa-acque-superficiali". Sarebbe da rifattorizzare il codice ed i FormIo.
 */
export class PresaUtilities extends CaptazioniUtilities {
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
   * @param datiIdentificativi IPresaDatiIdentificativi con le informazioni da verificare.
   * @returns boolean con il risultato del controllo.
   */
  validDatiIdentificativi(
    datiIdentificativi: IPresaDatiIdentificativi
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
    const tipo_presa: IPDITipoPresa = datiIdentificativi?.tipo_presa;
    const denominazione: string = datiIdentificativi?.denominazione;
    const denomin_cidrsup: string = datiIdentificativi?.denomin_cidrsup;
    const comune: string = datiIdentificativi?.comune;
    const localita: string = datiIdentificativi?.localita;
    const catas_comune: string = datiIdentificativi?.catas_comune;
    const catas_sezione: string = datiIdentificativi?.catas_sezione;
    const catas_foglio: string = datiIdentificativi?.catas_foglio;
    const catas_particella: string = datiIdentificativi?.catas_particella;
    const sponda: IPDISponda = datiIdentificativi?.sponda;
    const progressiva_asta: string = datiIdentificativi?.progressiva_asta;
    const flg_galleria_filtr: TypeSiNo = datiIdentificativi?.flg_galleria_filtr;
    const flg_capt_subalveo: TypeSiNo = datiIdentificativi?.flg_capt_subalveo;
    const codice_roc_bck: string = datiIdentificativi?.codice_roc_bck;
    const flg_da_canale: TypeSiNo = datiIdentificativi?.flg_da_canale;

    const isCodRocValid: boolean = this.checkCodiceRoc(codice_roc);
    const isCodRilievoValid: boolean = this.checkCodiceRilievo(codice_rilievo);
    const isCodSiiValid: boolean = this.checkCodiceSii(codice_sii);
    const isTipoPresaValid: boolean = this.checkTipoPresa(tipo_presa);
    const isDenomValid: boolean = this.checkDenominazione(denominazione);
    const isDenomCidValid: boolean =
      this.checkDenominazioneCidrsup(denomin_cidrsup);
    const isComuneValid: boolean = this.checkComune(comune);
    const isLocalitaValid: boolean = this.checkLocalita(localita);
    const isCodCatasValid: boolean = this.checkCodiceCatastale(catas_comune);
    const isSezioneValid: boolean = this.checkSezioneCatastale(catas_sezione);
    const isFoglioValid: boolean = this.checkFoglioCatastale(catas_foglio);
    const isParticellaValid: boolean =
      this.checkParticellaCatastale(catas_particella);
    const isSpondaValid: boolean = this.checksponda(sponda);
    const isProgAstaValid: boolean =
      this.checkProgressivaAsta(progressiva_asta);
    const isFlgGallFiltrValid: boolean =
      this.checkFlgGalleriaFiltrante(flg_galleria_filtr);
    const isFlgCaptSubalvValid: boolean =
      this.checkFlgCaptSubalveo(flg_capt_subalveo);
    const isCodRocBckValid: boolean = this.checkCodiceRocBck(codice_roc_bck);
    const isFlgCanaleValid: boolean = this.checkFlgDaCanale(flg_da_canale);

    // Definisco un array contenenti tutti i check per la sezione
    const allChecks: boolean[] = [
      isCodRocValid,
      isCodRilievoValid,
      isCodSiiValid,
      isTipoPresaValid,
      isDenomValid,
      isDenomCidValid,
      isComuneValid,
      isLocalitaValid,
      isCodCatasValid,
      isSezioneValid,
      isFoglioValid,
      isParticellaValid,
      isSpondaValid,
      isProgAstaValid,
      isFlgGallFiltrValid,
      isFlgCaptSubalvValid,
      isCodRocBckValid,
      isFlgCanaleValid,
    ];
    // Verifico e ritorno se tutti i controlli sono OK
    return allChecks.every((check) => check);
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param tipoPresa IPDITipoPresa con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkTipoPresa(tipoPresa: IPDITipoPresa): boolean {
    // Verifico se è definito
    if (!isDefinedObject(tipoPresa)) {
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
   * @param denominCidrsup string con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkDenominazioneCidrsup(denominCidrsup: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(denominCidrsup)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = maxLengthString(denominCidrsup, 100);

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param sponda IPDISponda con l'input da verificare.
   * @returns boolean con il risultato del check.
   */
  private checksponda(sponda: IPDISponda): boolean {
    // Verifico se è definito
    if (!isDefinedObject(sponda)) {
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
   * @param progressivaAsta string con l'input da verificare.
   * @returns boolean con il risultato del check.
   */
  private checkProgressivaAsta(progressivaAsta: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(progressivaAsta)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = interiDecimaliNumero(progressivaAsta, 3, 2);

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param flgGalleriaFiltr TypeSiNo con l'input da verificare.
   * @returns boolean con il risultato del check.
   */
  private checkFlgGalleriaFiltrante(flgGalleriaFiltr: TypeSiNo): boolean {
    // Verifico se è definito
    if (!isDefinedString(flgGalleriaFiltr)) {
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
   * @param flgCaptSubalveo TypeSiNo con l'input da verificare.
   * @returns boolean con il risultato del check.
   */
  private checkFlgCaptSubalveo(flgCaptSubalveo: TypeSiNo): boolean {
    // Verifico se è definito
    if (!isDefinedString(flgCaptSubalveo)) {
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

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param flgDaCanale TypeSiNo con l'input da verificare.
   * @returns boolean con il risultato del check.
   */
  private checkFlgDaCanale(flgDaCanale: TypeSiNo): boolean {
    // Verifico se è definito
    if (!isDefinedString(flgDaCanale)) {
      // E' obbligatorio
      return false;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = true;

    // Ritorno il risultato
    return isValid;
  }

  // #endregion "DATI IDENTIFICATIVI"

  /**
   * #####################
   * DATI INFRASTRUTTURALI
   * #####################
   */

  // #region "DATI INFRASTRUTTURALI"

  /**
   * Funzione che verifica la validità delle informazioni per la sezione dell'opera.
   * @param datiInfrastrutturali IPresaDatiInfrastrutturali con le informazioni da verificare.
   * @returns boolean con il risultato del controllo.
   */
  validDatiInfrastrutturali(
    datiInfrastrutturali: IPresaDatiInfrastrutturali
  ): boolean {
    // Verifico l'input
    if (!datiInfrastrutturali) {
      // Manca la configurazione
      return false;
    }

    // Estraggo i parametri da verificare
    const tipo_sbarr: IPDITipoSbarramento = datiInfrastrutturali.tipo_sbarr;
    const altezza: string = datiInfrastrutturali.altezza;
    const volume_max_invaso: string = datiInfrastrutturali.volume_max_invaso;
    const flg_scala: TypeSiNo = datiInfrastrutturali.flg_scala;
    const tipo_scala: IPDITipoScala = datiInfrastrutturali.tipo_scala;
    const sponda_scala: IPDISpondaScala = datiInfrastrutturali.sponda_scala;
    const flg_obblighi_itt: TypeSiNo = datiInfrastrutturali.flg_obblighi_itt;
    const specie_ittiche: string = datiInfrastrutturali.specie_ittiche;
    const flg_sbarramento: TypeSiNo = datiInfrastrutturali.flg_sbarramento;

    // Converto il flag sbarramento in booleano
    const isFlagSbarramentoNo: boolean = flg_sbarramento === 'no';
    // Verifico il valore specifico del flag sbarramento
    if (isFlagSbarramentoNo) {
      // Il flag è 'no' quindi tutti gli altri campi sono nascosti su FormIo e non devo verificarli
      return true;
      // #
    }

    const isTipoSbarrValid: boolean = this.checkTipoSbarramento(tipo_sbarr);
    const isAltezzaValid: boolean = this.checkAltezza(altezza);
    const isVolume_max_invasoValid: boolean =
      this.checkVolumeMaxInvaso(volume_max_invaso);
    const isFlgScalaValid: boolean = this.checkFlgScala(flg_scala);
    const isTipoScalaValid: boolean = this.checkTipoScala(
      tipo_scala,
      flg_scala
    );
    const isSpondaScalaValid: boolean = this.checkSpondaScala(
      sponda_scala,
      flg_scala
    );
    const isFlgObblIttValid: boolean =
      this.checkFlgObblighiItt(flg_obblighi_itt);
    const isSpecItticheValid: boolean = this.checkSpecieIttiche(specie_ittiche);

    // Definisco un array contenenti tutti i check per la sezione
    const allChecks: boolean[] = [
      isTipoSbarrValid,
      isAltezzaValid,
      isVolume_max_invasoValid,
      isFlgScalaValid,
      isTipoScalaValid,
      isSpondaScalaValid,
      isFlgObblIttValid,
      isSpecItticheValid,
    ];
    // Verifico e ritorno se tutti i controlli sono OK
    return allChecks.every((check) => check);
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param tipoSbarr IPDITipoSbarramento con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkTipoSbarramento(tipoSbarr: IPDITipoSbarramento): boolean {
    // Verifico se è definito
    if (!isDefinedObject(tipoSbarr)) {
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
   * @param altezza string con l'input da verificare.
   * @returns boolean con il risultato del check.
   */
  private checkAltezza(altezza: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(altezza)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = interiDecimaliNumero(altezza, 4, 2);

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param volumeMaxInvaso string con l'input da verificare.
   * @returns boolean con il risultato del check.
   */
  private checkVolumeMaxInvaso(volumeMaxInvaso: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(volumeMaxInvaso)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = interiDecimaliNumero(volumeMaxInvaso, 10, 2);

    // Ritorno il risultato
    return isValid;
  }

  /**
   * Funzione che verifica la validità del campo della sezione dell'opera.
   * @param flgScala TypeSiNo con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkFlgScala(flgScala: TypeSiNo): boolean {
    // Verifico se è definito
    if (!isDefinedString(flgScala)) {
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
   * @param tipoScala IPDITipoScala con l'input da verificare.
   * @param flgScala TypeSiNo con l'input per le verifiche incrociate.
   * @returns boolean con il risultato del check.
   */
  private checkTipoScala(
    tipoScala: IPDITipoScala,
    flgScala: TypeSiNo
  ): boolean {
    // Verifico se il flag dispostivi è "si"
    const isFlgScalaSi: boolean = flgScala === 'si';
    // Verifico se il flag DE è definito
    const isTipoScalaDefined: boolean = isDefinedObject(tipoScala);

    // Verifico se il flag dispositivo è definito
    if (isFlgScalaSi) {
      // Il flag dispositivi è si, verifico che esista il campo
      if (isTipoScalaDefined) {
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
   * @param spondaScala IPDISpondaScala con l'input da verificare.
   * @param flgScala TypeSiNo con l'input per le verifiche incrociate.
   * @returns boolean con il risultato del check.
   */
  private checkSpondaScala(
    spondaScala: IPDISpondaScala,
    flgScala: TypeSiNo
  ): boolean {
    // Verifico se il flag dispostivi è "si"
    const isFlgScalaSi: boolean = flgScala === 'si';
    // Verifico se il flag DE è definito
    const isSpondaScalaDefined: boolean = isDefinedObject(spondaScala);

    // Verifico se il flag dispositivo è definito
    if (isFlgScalaSi) {
      // Il flag dispositivi è si, verifico che esista il campo
      if (isSpondaScalaDefined) {
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
   * @param flgObblighiItt TypeSiNo con l'input da verificare.
   * @return boolean con il risultato del check.
   */
  private checkFlgObblighiItt(flgObblighiItt: TypeSiNo): boolean {
    // Verifico se è definito
    if (!isDefinedString(flgObblighiItt)) {
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
   * @param specieIttiche string con l'input da verificare.
   * @returns boolean con il risultato del check.
   */
  private checkSpecieIttiche(specieIttiche: string): boolean {
    // Verifico se è definito
    if (!isDefinedString(specieIttiche)) {
      // Non è obbligatorio
      return true;
    }

    // Esiste, verifico le condizioni per il campo
    let isValid: boolean = false;
    isValid = maxLengthString(specieIttiche, 200);

    // Ritorno il risultato
    return isValid;
  }

  // #endregion "DATI INFRASTRUTTURALI"
}
