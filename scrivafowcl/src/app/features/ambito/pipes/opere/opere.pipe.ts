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
import {
  OggettoIstanza,
  OggettoIstanzaLike,
  Opera,
  UbicazioneOggetto,
  UbicazioneOggettoIstanza,
} from '../../models';

// #region "UTILITIES"

/** UbicazioneOggettoMap come type che include più tipi al suo interno per la gestione della mappatura dati. */
type UbicazioneOggettoMap =
  | UbicazioneOggetto
  | UbicazioneOggettoIstanza

/**
 * Funzione generica che genera l'informazione per la colonna dei comuni per la tabella delle opere.
 * Questa funzione è pensata per gestire sia oggetti Opera che oggetti OggettoIstanza
 * @param ubicazioni UbicazioneOggettoMap con le informazioni per la gestione dati.
 * @returns string con la descrizione per i comuni della colonna.
 */
const colonnaComuniOperaOggettoIstanza = (
  ubicazioni: UbicazioneOggettoMap[]
): string => {
  // Verifico che esistano effettivamente ubicazioni
  let descrizioni = ubicazioni?.map((u: UbicazioneOggettoMap) => {
    // Recupero le informazioni per la composizione della descrizione
    const comune = `${u?.comune?.denom_comune}`;
    const siglaProvincia = `${u?.comune?.provincia?.sigla_provincia}`;
    // Compongo la descrizione completa
    const descrizione = `${comune} (${siglaProvincia})`;
    // Ritorno la descrizione
    return descrizione;
    // #
  });

  // Effettuo una join tra tutte le descrizioni
  const listaComuni: string = descrizioni?.join(', ') ?? '';
  // Ritorno le informazioni concatenate
  return listaComuni;
};

/**
 * Funzione generica che genera l'informazione per la colonna dell'indirizzo per la tabella delle opere/oggetti-istanza.
 * @param ubicazione UbicazioneOggetto con le informazioni per la generazione della descrizione per l'indirizzo.
 * @returns string con la descrizione composta.
 */
const colonnaIndirizzoUbicazione = (ubicazione: UbicazioneOggetto): string => {
  // Definisco il contenitore per la descrizione
  let descrizione: string = '';

  // Estraggo dall'ubicazione le informazioni per la descrizione
  const indirizzo: string = ubicazione?.den_indirizzo ?? '';
  const civico: string = ubicazione?.num_civico ?? '';

  // Compongo la stringa con la descrizione
  descrizione = `${indirizzo} ${civico}`;
  descrizione = descrizione.trim();

  // Ritonro la descrizione composta
  return descrizione;
};

// #endregion "UTILITIES"

// #region "OPERA"

/**
 * Pipe dedicata alla opere.
 */
@Pipe({ name: 'opereComuniOpera' })
export class OpereComuniOperaPipe implements PipeTransform {
  /**
   * Costruttore del Pipe.
   */
  constructor() {}

  /**
   * Funzione che gestisce le informazioni di una riga per la tabella "Ricerca Opere".
   * @param riga Opera con le informazioni della riga di tabella passata dalla libreria ngx-datatable.
   * @returns string con la descrizione formattata.
   */
  transform(riga: Opera): string {
    // Verifico l'input
    if (!riga || !riga.ubicazione_oggetto) {
      // Manca la configurazione minima
      return '';
    }

    // Recupero le ubicazioni per la gestione dei comuni
    const ubicazione: UbicazioneOggetto[] = riga.ubicazione_oggetto;
    // Ci sono le informazioni, cerco di generare una descrizione
    return this.colonnaComuni(ubicazione);
  }

  /**
   * Funzione che genera l'informazione per la colonna dei comuni per la tabella delle opere.
   * @param ubicazioni UbicazioneOggettoMap con le informazioni per la gestione dati.
   * @returns string con la descrizione per i comuni della colonna.
   */
  private colonnaComuni(ubicazioni: UbicazioneOggettoMap[]): string {
    // Richiamo la funzione generica per la gestione delle casistica
    return colonnaComuniOperaOggettoIstanza(ubicazioni);
  }
}

/**
 * Pipe dedicata alla opere.
 */
@Pipe({ name: 'opereCodiceScrivaOpera' })
export class OpereCodiceScrivaOperaPipe implements PipeTransform {
  /**
   * Costruttore del Pipe.
   */
  constructor() {}

  /**
   * Funzione che gestisce le informazioni di una riga per la tabella "Associa Opere".
   * @param riga Opera con le informazioni della riga di tabella passata dalla libreria ngx-datatable.
   * @returns string con la descrizione formattata dei comuni.
   */
  transform(riga: Opera): string {
    // Verifico l'input
    if (!riga) {
      // Manca la configurazione minima
      return '';
    }

    // Ci sono le informazioni, cerco di generare una descrizione
    return this.colonnaCodiceScriva(riga);
  }

  /**
   * Funzione che recupera il codice scriva date le informazioni in input.
   * @param opera Opera con l'opera per il recupero del codice scriva.
   * @returns string con il codice scriva cercato.
   */
  private colonnaCodiceScriva(opera: Opera): string {
    // Estraggo la proprietà dall'oggetto
    const codiceScriva = opera?.cod_scriva ?? '';
    // Ritorno il dato
    return codiceScriva;
  }
}

/**
 * Pipe dedicata alla opere.
 */
@Pipe({ name: 'opereCodiceRilievoOpera' })
export class OpereCodiceRilievoOperaPipe implements PipeTransform {
  /**
   * Costruttore del Pipe.
   */
  constructor() {}

  /**
   * Funzione che gestisce le informazioni di una riga per la tabella "Associa Opere".
   * @param riga Opera con le informazioni della riga di tabella passata dalla libreria ngx-datatable.
   * @returns string con la descrizione formattata dei comuni.
   */
  transform(riga: Opera): string {
    // Verifico l'input
    if (!riga) {
      // Manca la configurazione minima
      return '';
    }

    // Ci sono le informazioni, cerco di generare una descrizione
    return this.colonnaCodiceRilievo(riga);
  }

  /**
   * Funzione che recupera il codice scriva date le informazioni in input.
   * Il codice rilievo è un codice scriva che non comincia con la sequenza "SCRV", poiché questa è la sequenza assegnata ad un'opera non revisionata da regione.
   * @param opera Opera con l'opera per il recupero del codice scriva.
   * @returns string con il codice rilievo dell'opera.
   */
  private colonnaCodiceRilievo(opera: Opera): string {
    // Estraggo la proprietà dall'oggetto
    const codiceRilievo = opera?.cod_scriva ?? '';

    // Definisco il codice con la sequenza di caratteri che identifica un'opera non ancora visionata da regione
    const OPERA_NON_REV: string = 'SCRV';
    // Verifico se il codice scriva inizia con il codice
    const isOperNotRev: boolean = codiceRilievo.startsWith(OPERA_NON_REV);
    // Verifico il risultato
    if (isOperNotRev) {
      // Il codice rilievo non è stato definito, c'è la struttura non revisionata, ritorno stringa vuota
      return '';
      // #
    }

    // Ritorno il codice rilievo
    return codiceRilievo;
  }
}

// #endregion "OPERA"

// #region "OGGETTO ISTANZA"

/**
 * Pipe dedicata alla opere.
 */
@Pipe({ name: 'opereComuniOggettoIstanza' })
export class OpereComuniOggettoIstanzaPipe implements PipeTransform {
  /**
   * Costruttore del Pipe.
   */
  constructor() {}

  /**
   * Funzione che gestisce le informazioni di una riga per la tabella "Ricerca Opere".
   * @param riga OggettoIstanza con le informazioni della riga di tabella passata dalla libreria ngx-datatable.
   * @returns string con la descrizione formattata.
   */
  transform(riga: OggettoIstanza): string {
    // Verifico l'input
    if (!riga || !riga.ubicazione_oggetto) {
      // Manca la configurazione minima
      return '';
    }

    // Recupero le ubicazioni per la gestione dei comuni
    const ubicazione: UbicazioneOggettoIstanza[] = riga.ubicazione_oggetto;
    // Ci sono le informazioni, cerco di generare una descrizione
    return this.colonnaComuni(ubicazione);
  }

  /**
   * Funzione che genera l'informazione per la colonna dei comuni per la tabella delle opere.
   * @param ubicazioni UbicazioneOggettoMap con le informazioni per la gestione dati.
   * @returns string con la descrizione per i comuni della colonna.
   */
  private colonnaComuni(ubicazioni: UbicazioneOggettoMap[]): string {
    // Richiamo la funzione generica per la gestione delle casistica
    return colonnaComuniOperaOggettoIstanza(ubicazioni);
  }
}

/**
 * Pipe dedicata alla opere.
 */
@Pipe({ name: 'opereLocalitaOggettoIstanza' })
export class OpereLocalitaOggettoIstanzaPipe implements PipeTransform {
  /**
   * Costruttore del Pipe.
   */
  constructor() {}

  /**
   * Funzione che gestisce le informazioni di una riga per la tabella "Associa Opere".
   * @param riga OggettoIstanza con le informazioni della riga di tabella passata dalla libreria ngx-datatable.
   * @returns string con la descrizione formattata.
   */
  transform(riga: OggettoIstanza): string {
    // Verifico l'input
    if (!riga || !riga.ubicazione_oggetto) {
      // Manca la configurazione minima
      return '';
    }

    // Ci sono le informazioni, cerco di generare una descrizione
    return this.colonnaLocalita(riga.ubicazione_oggetto);
  }

  /**
   * Funzione che genera l'informazione per la colonna delle localita per la tabella delle opere.
   * @notes La logica è stata ereditata, non so perché si faccia quel tipo di ricerca.
   * @param ubicazioni UbicazioneOggettoIstanza con le informazioni per la gestione dati.
   * @returns string con la descrizione per le localita della colonna.
   */
  private colonnaLocalita(ubicazioni: UbicazioneOggettoIstanza[]): string {
    // Cerco all'interno delle ubicazioni il primo oggetto con una descrizione
    let ubicazioneConDes: UbicazioneOggettoIstanza;
    ubicazioneConDes = ubicazioni?.find((u: UbicazioneOggettoIstanza) => {
      // Verifico se l'oggetto ciclato ha una descrizione per la località
      return u?.des_localita != undefined;
      // #
    });

    // Ritorno la descrizione della località oppure, se non è stato trovato nessun oggetto, stringa vuota
    return ubicazioneConDes?.des_localita ?? '';
  }
}

/**
 * Pipe dedicata alla opere.
 */
@Pipe({ name: 'opereCodiceScrivaOggettoIstanza' })
export class OpereCodiceScrivaOggettoIstanzaPipe implements PipeTransform {
  /**
   * Costruttore del Pipe.
   */
  constructor() {}

  /**
   * Funzione che gestisce le informazioni di una riga per la tabella "Associa Opere".
   * @param riga OggettoIstanza con le informazioni della riga di tabella passata dalla libreria ngx-datatable.
   * @param opere Opera[] con la lista oggetti delle opere per la ricerca del codice scriva.
   * @returns string con la descrizione formattata dei comuni.
   */
  transform(riga: OggettoIstanza, opere: Opera[]): string {
    // Verifico l'input
    if (!opere || !riga || riga.id_oggetto == undefined) {
      // Manca la configurazione minima
      return '';
    }

    // Ci sono le informazioni, cerco di generare una descrizione
    return this.colonnaCodiceScriva(opere, riga.id_oggetto);
  }

  /**
   * Funzione che recupera il codice scriva date le informazioni in input.
   * @param opere Opera[] con la lista oggetti delle opere per la ricerca del codice scriva.
   * @param idOggetto number con l'id oggetto istanza di riferimento per il recupero del codice scriva.
   * @returns string con il codice scriva cercato.
   */
  private colonnaCodiceScriva(opere: Opera[], idOggetto: number): string {
    // Verifico l'input
    opere = opere ?? [];

    // Tento di recuperare l'opera dalla lista
    const operaPerScriva = opere.find((o: Opera) => {
      // Verifico l'id oggetto è il medesimo
      return o?.id_oggetto === idOggetto;
      // #
    });

    // Se ho trovato l'opera ritorno il codice scriva, altrimenti stringa vuota
    return operaPerScriva?.cod_scriva ?? '';
  }
}

// #endregion "OGGETTO ISTANZA"

// #region "PIPE CONDIVISE TRA OPERE/OGGETTI-ISTANZA"

/**
 * Pipe dedicata alla opere.
 */
@Pipe({ name: 'opereIndirizzoUbicazione' })
export class OpereIndirizzoUbicazionePipe implements PipeTransform {
  /**
   * Costruttore del Pipe.
   */
  constructor() {}

  /**
   * Funzione che gestisce le informazioni per la generazione dati per l'indirizzo ubicazione di un'opera o di un oggetto-istanza.
   * @param riga Opera | OggettoIstanzaLike con le informazioni della riga di tabella passata dalla libreria ngx-datatable.
   * @returns string con la descrizione formattata.
   */
  transform(riga: Opera | OggettoIstanzaLike): string {
    // Verifico l'input
    if (!riga || !riga.ubicazione_oggetto) {
      // Manca la configurazione minima
      return '';
    }

    // Recupero le ubicazioni per la gestione dei comuni
    const ubicazione: UbicazioneOggettoIstanza[] = riga.ubicazione_oggetto;
    // Ci sono le informazioni, cerco di generare una descrizione
    return this.indirizzoUbicazione(ubicazione);
  }

  /**
   * Funzione che genera l'informazione per la colonna dell'indirizzo ubicazione per la relativa colonna.
   * @param ubicazioni UbicazioneOggettoMap con le informazioni per la gestione dati.
   * @returns string con la descrizione per l'indirizzo dell'ubicazione.
   */
  private indirizzoUbicazione(ubicazioni: UbicazioneOggettoMap[]): string {
    // Gestisco l'input per sicurezza
    ubicazioni = ubicazioni ?? [];
    // La gestione dell'indirizzo per l'ubicazione è stata richiesta solo per il primo (e tecnicamente unico) elemento della lista
    const ubicazione: UbicazioneOggettoMap = ubicazioni[0];
    // Richiamo la funzione generica per la gestione delle casistica
    return colonnaIndirizzoUbicazione(ubicazione);
  }
}

// #endregion "PIPE CONDIVISE TRA OPERE/OGGETTI-ISTANZA"
