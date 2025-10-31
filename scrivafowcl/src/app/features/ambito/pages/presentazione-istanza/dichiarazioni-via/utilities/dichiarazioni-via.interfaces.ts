/**
 * Interfaccia che definisce i parametri di configurazione per le classi di via.
 */
export class IDichiarazioneACVIA {
  /** string contenete il codice dell'autorità competente di riferimento per la competenza territorio. */
  codiceCompetenzaTerritorio?: string = '';
  /** string con la descrizione statica per la dichiarazione ambientale con riferimento AC: Regione Piemonte */
  desDichACRegionePiemonte?: string;
  /** string con la descrizione statica per la dichiarazione ambientale con riferimento AC: AC specifica, diversa da Regione Piemonte. */
  desDichACSpecifica?: string;
  /** string con la struttura html da visualizzare in pagina per la dichiarazione ambientale con riferimento AC: Regione Piemonte */
  htmlDichACRegionePiemonte: string;
  /** string con la strttura html da visualizzare in pagina per la dichiarazione ambientale con riferimento AC: AC specifica, diversa da Regione Piemonte. */
  htmlDichACSpecifica: string;
}

/**
 * Interfaccia che definisce i parametri di configurazione per le classi specifiche di VAL.
 */
export class IDichiarazioneACVAL extends IDichiarazioneACVIA {}

/**
 * Interfaccia che definisce i parametri di configurazione per le classi specifiche di VER.
 */
export class IDichiarazioneACVER extends IDichiarazioneACVIA {}
