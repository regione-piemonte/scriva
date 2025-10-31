import { CodiciAutoritaCompetenti } from '../../../orientamento-istanza/utilities/orientamento-istanza.enums';
import { IDichiarazioneACVIA } from './dichiarazioni-via.interfaces';

/**
 * Classe che permette la gestione delle dichiarazioni configurabili tramite autorità competente.
 */
export class DichiarazioneACVIA {
  /** string contenete il codice dell'autorità competente di riferimento per la competenza territorio. */
  codiceCompetenzaTerritorio: string;

  /** string con la descrizione statica per la dichiarazione ambientale con riferimento AC: Regione Piemonte */
  desDichACRegionePiemonte: string;
  /** string con la descrizione statica per la dichiarazione ambientale con riferimento AC: AC specifica, diversa da Regione Piemonte. */
  desDichACSpecifica: string;

  /** string con la struttura html da visualizzare in pagina per la dichiarazione ambientale con riferimento AC: Regione Piemonte */
  htmlDichACRegionePiemonte: string;
  /** string con la strttura html da visualizzare in pagina per la dichiarazione ambientale con riferimento AC: AC specifica, diversa da Regione Piemonte. */
  htmlDichACSpecifica: string;

  /**
   * Costruttore.
   */
  constructor(c?: IDichiarazioneACVIA) {
    // Assegno localmente le informazioni
    this.codiceCompetenzaTerritorio = c?.codiceCompetenzaTerritorio;
    this.desDichACRegionePiemonte = c?.desDichACRegionePiemonte;
    this.desDichACSpecifica = c?.desDichACSpecifica;
    this.htmlDichACRegionePiemonte = c?.htmlDichACRegionePiemonte;
    this.htmlDichACSpecifica = c?.htmlDichACSpecifica;
  }

  /**
   * ###################################
   * HTML DICHIARAZIONE REGIONE PIEMONTE
   * ###################################
   */

  // #region "HTML DICHIARAZIONE REGIONE PIEMONTE"

  /**
   * Funzione che genera la struttura HTML completa con link per le dichiarazioni VIA di Regione Piemonte.
   * La funzione è pensata per essere overridata con le customizzazioni specifiche per la dichiarazione.
   * @param params any contenente tutte le informazioni per la generazione dell'html.
   * @returns string con la dichiarazione come struttura HTML leggibile dalle pagine.
   */
  htmlDichiarazioneRegionePiemonte(params: any): string {
    // Per default restituisce l'html come proprietà
    return this.htmlDichACRegionePiemonte;
  }

  // #endregion "HTML DICHIARAZIONE REGIONE PIEMONTE"

  /**
   * ###############################
   * HTML DICHIARAZIONE AC SPECIFICA
   * ###############################
   */

  // #region "HTML DICHIARAZIONE AC SPECIFICA"

  /**
   * Funzione che genera la struttura HTML completa con link per le dichiarazioni VIA di un'AC specifica.
   * La funzione è pensata per essere overridata con le customizzazioni specifiche per la dichiarazione.
   * @param params any contenente tutte le informazioni per la generazione dell'html.
   * @returns string con la dichiarazione come struttura HTML leggibile dalle pagine.
   */
  htmlDichiarazioneACSpecifica(params: any): string {
    // Per default restituisce l'html come proprietà
    return this.htmlDichACSpecifica;
  }

  // #endregion "HTML DICHIARAZIONE AC SPECIFICA"

  /**
   * ##############################
   * DICHIARAZIONE REGIONE PIEMONTE
   * ##############################
   */

  // #region "DICHIARAZIONE REGIONE PIEMONTE"

  /**
   * Funzione che genera la descrizione completa le dichiarazioni VIA di Regione Piemonte.
   * La funzione è pensata per essere overridata con le customizzazioni specifiche per la dichiarazione.
   * @param params any contenente tutte le informazioni per la generazione dell'html.
   * @returns string con la dichiarazione da definire sul json dati.
   */
  descrizioneDichiarazioneRegionePiemonte(params: any): string {
    // Per default restituisce la descrizione come proprietà
    return this.desDichACRegionePiemonte;
  }

  // #endregion "DICHIARAZIONE REGIONE PIEMONTE"

  /**
   * ##########################
   * DICHIARAZIONE AC SPECIFICA
   * ##########################
   */

  // #region "DICHIARAZIONE AC SPECIFICA"

  /**
   * Funzione che genera la descrizione completa le dichiarazioni VIA di un'AC specifica.
   * La funzione è pensata per essere overridata con le customizzazioni specifiche per la dichiarazione.
   * @param params any contenente tutte le informazioni per la generazione dell'html.
   * @returns string con la dichiarazione da definire sul json dati.
   */
  descrizioneDichiarazioneACSpecifica(params: any): string {
    // Per default restituisce la descrizione come proprietà
    return this.desDichACSpecifica;
  }

  // #endregion "DICHIARAZIONE AC SPECIFICA"

  /**
   * ######################
   * DICHIARAZIONE HTML VIA
   * ######################
   */

  // #region "DICHIARAZIONE HTML VIA"

  /**
   * Funzione che genera la struttura HTML completa per la dichiarazione VIA.
   * @param params any contenente tutte le informazioni per la generazione dell'html.
   * @param codiceCompetenzaTerritorio string con una competenza territorio specifica da gestire. Se non definito verrà usata quella della classe.
   * @returns string con la dichiarazione come struttura HTML leggibile dalle pagine.
   */
  htmlDichiarazione(params: any, codiceCompetenzaTerritorio?: string): string {
    // Verifico l'input per il codice competenza
    codiceCompetenzaTerritorio =
      codiceCompetenzaTerritorio ?? this.codiceCompetenzaTerritorio;
    // Verifico se l'AC è regione piemonte
    const isACRegionePiemonte: boolean =
      codiceCompetenzaTerritorio === CodiciAutoritaCompetenti.regionePiemonte;

    // Verifico il codice competenza territorio
    if (isACRegionePiemonte) {
      // Richiamo la funzione per la gestione della regione piemonte
      return this.htmlDichiarazioneRegionePiemonte(params);
      // #
    } else {
      // E' un'altra competenza territorio specifica, non regione piemonte
      return this.htmlDichiarazioneACSpecifica(params);
      // #
    }
  }

  // #endregion "DICHIARAZIONE HTML VIA"

  /**
   * ######################
   * DICHIARAZIONE DESC VIA
   * ######################
   */

  // #region "DICHIARAZIONE DESC VIA"

  /**
   * Funzione che genera la descrizione completa per la dichiarazione VIA.
   * @param params any contenente tutte le informazioni per la generazione dell'html.
   * @param codiceCompetenzaTerritorio string con una competenza territorio specifica da gestire. Se non definito verrà usata quella della classe.
   * @returns string con la dichiarazione da definire sul json dati.
   */
  descrizioneDichiarazione(
    params: any,
    codiceCompetenzaTerritorio?: string
  ): string {
    // Verifico l'input per il codice competenza
    codiceCompetenzaTerritorio =
      codiceCompetenzaTerritorio ?? this.codiceCompetenzaTerritorio;
    // Verifico se l'AC è regione piemonte
    const isACRegionePiemonte: boolean =
      codiceCompetenzaTerritorio === CodiciAutoritaCompetenti.regionePiemonte;

    // Verifico il codice competenza territorio
    if (isACRegionePiemonte) {
      // Richiamo la funzione per la gestione della regione piemonte
      return this.descrizioneDichiarazioneRegionePiemonte(params);
      // #
    } else {
      // E' un'altra competenza territorio specifica, non regione piemonte
      return this.descrizioneDichiarazioneACSpecifica(params);
      // #
    }
  }

  // #endregion "DICHIARAZIONE DESC VIA"
}
