/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Pipe, PipeTransform } from '@angular/core';
import { DichiarazioniOrientamentoVIAConsts } from '../../../services/orientamento-istanza/orientamento-istanza-dichiarazioni/utilities/dichiarazioni/dichiarazioni-orientamento-via.consts';
import { DichiarazioneACVIA } from '../../../pages/presentazione-istanza/dichiarazioni-via/utilities/dichiarazioni-via.consts';

/**
 * Pipe.
 */
@Pipe({ name: 'dichiarazioniVIARegionePiemonte' })
export class DichiarazioniOrientamentoVIARegionePiemontePipe
  implements PipeTransform
{
  /** DichiarazioniVIAConsts con le costanti per la pipe. */
  private DVIA_C = new DichiarazioniOrientamentoVIAConsts();

  /**
   * Costruttore del Pipe.
   */
  constructor() {}

  /**
   * Funzione che gestisce la composizione delle dichiarazioni VIA con autorità competente: Regione Piemonte.
   * @param codiceTipoAdempimento string con l'indicazione per la dichiarazione.
   * @param codiceAutoritaCompetente string con l'indicazione per la dichiarazione.
   * @param linkAutoritaCompetente string con l'indicazione per la dichiarazione.
   * @returns string con la struttura HTML formattata per le dichiarazioni di via.
   */
  transform(
    codiceTipoAdempimento: string,
    codiceAutoritaCompetente: string,
    linkAutoritaCompetente: string
  ): string {
    // Ci sono le informazioni, cerco di generare una descrizione
    return this.DVIA_C.linkDichiarazioniVIARegionePiemonte(
      codiceTipoAdempimento,
      codiceAutoritaCompetente,
      linkAutoritaCompetente
    );
  }
}

/**
 * Pipe.
 */
@Pipe({ name: 'dichiarazioniVIAACSpecifica' })
export class DichiarazioniOrientamentoVIAACSpecificaPipe
  implements PipeTransform
{
  /** DichiarazioniVIAConsts con le costanti per la pipe. */
  private DVIA_C = new DichiarazioniOrientamentoVIAConsts();

  /**
   * Costruttore del Pipe.
   */
  constructor() {}

  /**
   * Funzione che gestisce la composizione delle dichiarazioni VIA con autorità competente: Regione Piemonte.
   * @param codiceTipoAdempimentoRegionePiemonte string con l'indicazione per la dichiarazione.
   * @param codiceAutoritaCompetenteRegionePiemonte string con l'indicazione per la dichiarazione.
   * @param linkAutoritaCompetenteRegionePiemonte string con l'indicazione per la dichiarazione.
   * @param codiceTipoAdempimentoACSpec string con l'indicazione per la dichiarazione.
   * @param codiceAutoritaCompetenteSpec string con l'indicazione per la dichiarazione.
   * @param linkAutoritaCompetenteSpec string con l'indicazione per la dichiarazione.
   * @param descAutoritaCompetenteSpec string con l'indicazione per la dichiarazione.
   * @returns string con la struttura HTML formattata per le dichiarazioni di via.
   */
  transform(
    codiceTipoAdempimentoRegionePiemonte: string,
    codiceAutoritaCompetenteRegionePiemonte: string,
    linkAutoritaCompetenteRegionePiemonte: string,
    codiceTipoAdempimentoACSpec: string,
    codiceAutoritaCompetenteSpec: string,
    linkAutoritaCompetenteSpec: string,
    descAutoritaCompetenteSpec: string
  ): string {
    // Ci sono le informazioni, cerco di generare una descrizione
    return this.DVIA_C.linkDichiarazioniVIAACSpecifica(
      codiceTipoAdempimentoRegionePiemonte,
      codiceAutoritaCompetenteRegionePiemonte,
      linkAutoritaCompetenteRegionePiemonte,
      codiceTipoAdempimentoACSpec,
      codiceAutoritaCompetenteSpec,
      linkAutoritaCompetenteSpec,
      descAutoritaCompetenteSpec
    );
  }
}

/**
 * Pipe.
 */
@Pipe({ name: 'dichiarazioneVIAHTML' })
export class DichiarazioneVIAHTMLPipe implements PipeTransform {
  /**
   * Costruttore del Pipe.
   */
  constructor() {}

  /**
   * Funzione la stringa HTML da visualizzare per una dichiarazione VIA.
   * @param dichiarazioneVIA DichiarazioneACVIA con l'oggetto che gestisce la dichiarazione.
   * @param params any con i parametri di configurazione per la generazione della stringa.
   * @param codiceAutoritaCompetente string con il codice dell'autorità competente.
   * @returns string con la struttura HTML formattata per la dichiarazione di VIA.
   */
  transform(
    dichiarazioneVIA: DichiarazioneACVIA,
    params?: any,
    codiceAutoritaCompetente?: string
  ): string {
    // Verifico l'input
    if (!dichiarazioneVIA) {
      // Manca la configurazione
      return '';
      // #
    }

    // Richiamo la funzione per gestire l'HTML della dichiarazione
    return dichiarazioneVIA.htmlDichiarazione(params, codiceAutoritaCompetente);
  }
}
