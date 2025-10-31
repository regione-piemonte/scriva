/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { cloneDeep } from 'lodash';
import { identificativoComponenteFormIo } from '../../../../../../shared/services/formio/formio-utilities.functions';
import { FormioFormBuilderTypes } from '../../../../../../shared/services/formio/utilities/formio.types';
import { OggettoIstanza, Opera, TipologiaOggetto } from '../../../../models';
import {
  IAggregatoreDatiTecniciMancanti,
  ISezioneObbligatoriaDatiTecniciMancanti,
} from './opere.interfaces';

/**
 * Classe che definisce le informazioni da tracciare per la mancanza dei dati di una sezione dei dati tecnici.
 */
export class SezioneObbligatoriaDatiTecniciMancanti {
  opera?: Opera;
  oggettoIstanza?: OggettoIstanza;
  sezioneFormIo?: FormioFormBuilderTypes;

  /**
   * Costruttore.
   */
  constructor(c?: ISezioneObbligatoriaDatiTecniciMancanti) {
    // Clono le informazioni per evitare problemi di riferimento sui dati
    this.opera = cloneDeep(c?.opera);
    this.oggettoIstanza = cloneDeep(c?.oggettoIstanza);
    this.sezioneFormIo = cloneDeep(c?.sezioneFormIo);
  }

  /**
   * ######################
   * FUNZIONI CON VERIFICHE
   * ######################
   */

  // #region "VERIFICA RIFERIMENTO STESSA OPERA"

  /**
   * Funzione di verifica per stesso oggetto Opera.
   * @param opera Opera con i dati per la verifica.
   */
  sameOpera(opera?: Opera): boolean {
    // Verifico l'input
    if (!opera) {
      // Manca il dato
      return undefined;
    }

    // Verifico gli id_oggetto delle opere
    return opera.id_oggetto === this.opera?.id_oggetto;
    // #
  }

  // #endregion "VERIFICA RIFERIMENTO STESSA OPERA"

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  // #region "GETTER E SETTER"

  /**
   * Getter che recupera l'informaziona della tipologia oggetto.
   * @returns string con il dato recuperato.
   */
  get desTipoOggetto(): string {
    // Recupero valore dall'oggetto della classe
    const tipoOggetto: TipologiaOggetto =
      this.oggettoIstanza?.tipologia_oggetto;
    const desTipoOggetto: string = tipoOggetto?.des_tipologia_oggetto;
    // Ritorno il valore
    return desTipoOggetto;
    // #
  }

  /**
   * Getter che recupera l'informaziona del codice scriva.
   * @returns string con il dato recuperato.
   */
  get codiceScriva(): string {
    // Recupero valore dall'oggetto della classe
    const codiceScriva: string = this.opera?.cod_scriva;
    // Ritorno il valore
    return codiceScriva;
    // #
  }

  /**
   * Getter che recupera l'informazione per la label identificativa della sezione.
   * @returns string con il dato recuperato.
   */
  get sezioneDatoTecnico(): string {
    // Recupero valore dall'oggetto della classe
    const labelSezione: string = this.sezioneFormIo?.label;
    // Ritorno il valore
    return labelSezione ?? '';
    // #
  }

  /**
   * Getter che recupera il codice identificativo della sezione FormIo.
   * @returns string con il dato recuperato.
   */
  get identificativoComponenteFormIo(): string {
    // Richiamo la funzione di utility
    return identificativoComponenteFormIo(this.sezioneFormIo);
    // #
  }

  /**
   * Getter che recupera la descrizione del tipo opera, dall'oggetto: OggettoIstanza.
   * @returns string con il dato recuperato.
   */
  get desTipoOpera(): string {
    // Ritorno la descrizione del codice tipologia oggetto
    return this.oggettoIstanza?.tipologia_oggetto?.cod_tipologia_oggetto;
  }

  // #endregion "GETTER E SETTER"
}

/**
 * Classe che definisce le informazioni gestite come aggregatore per le segnalazioni.
 * L'idea è che questa classe contenga le segnalazioni per i dati tecnici, aventi tutti la stessa Opera di riferimento.
 */
export class AggregatoreDatiTecniciMancanti {
  /** SezioneObbligatoriaDatiTecniciMancanti[] con la lista degli oggetti contenente i dati per le segnalazioni di dati tecnici mancanti. */
  datiSegnalazioni: SezioneObbligatoriaDatiTecniciMancanti[];

  /**
   * Costruttore.
   */
  constructor(c?: IAggregatoreDatiTecniciMancanti) {
    this.datiSegnalazioni = c?.datiSegnalazioni;
  }

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  // #region "GETTER E SETTER"

  /**
   * Getter che recupera l'informaziona dell'oggetto istanza del primo elemento dell'array.
   * L'idea è che gli oggetti abbiano tutti le stesse informazioni.
   * @returns SezioneObbligatoriaDatiTecniciMancanti con il dato recuperato.
   */
  get segnalazioneRiferimento(): SezioneObbligatoriaDatiTecniciMancanti {
    // Recupero e ritrno il dato del primo elemento dalla lista
    return this.datiSegnalazioni[0];
    // #
  }

  /**
   * Getter che recupera l'informaziona della tipologia oggetto.
   * @returns string con il dato recuperato.
   */
  get desTipoOggettoIstanza(): string {
    // Recupero il dato aggregato del primo elemento dalla lista
    const oggettoIstanza: OggettoIstanza =
      this.segnalazioneRiferimento?.oggettoIstanza;
    // Recupero valore dall'oggetto della classe
    const tipoOggetto: TipologiaOggetto = oggettoIstanza?.tipologia_oggetto;
    const desTipoOggetto: string = tipoOggetto?.des_tipologia_oggetto;
    // Ritorno il valore
    return desTipoOggetto ?? '';
    // #
  }

  /**
   * Getter che recupera l'informaziona del codice scriva.
   * @returns string con il dato recuperato.
   */
  get codiceScrivaOpere(): string {
    // Recupero il dato aggregato del primo elemento dalla lista
    const opera: Opera = this.segnalazioneRiferimento?.opera;
    // Recupero valore dall'oggetto della classe
    const codiceScriva: string = opera?.cod_scriva;
    // Ritorno il valore
    return codiceScriva ?? '';
    // #
  }

  /**
   * Getter che recupera l'informazione per la label identificativa della sezione.
   * @returns string con il dato recuperato.
   */
  get desSezioniFormIo(): string {
    // Recupero le informazioni per i dati delle segnalazioni
    let datiSegnalazioni: SezioneObbligatoriaDatiTecniciMancanti[];
    datiSegnalazioni = this.datiSegnalazioni ?? [];

    // Recupero il dato aggregato del primo elemento dalla lista
    let sezioniFormIo: FormioFormBuilderTypes[];
    sezioniFormIo = datiSegnalazioni?.map(
      (ds: SezioneObbligatoriaDatiTecniciMancanti) => {
        // Ritorno i dati delle sezioni formio
        return ds.sezioneFormIo;
        // #
      }
    );
    // Recupero valore dall'oggetto della classe
    const labelSezioni: string[] = sezioniFormIo?.map(
      (s: FormioFormBuilderTypes) => {
        // Recupero la label della sezione
        return s?.label ?? '';
        // #
      }
    );
    // Ritorno il valore
    return labelSezioni.join(', ');
    // #
  }

  // #endregion "GETTER E SETTER"
}
