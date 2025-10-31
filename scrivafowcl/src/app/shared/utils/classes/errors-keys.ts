/**
 * Interfaccia che definisce la mappatura delle chiavi d'errore.
 */
interface IScrivaErrorKeys {
    /** Costante che indica la chiave per il recupero dell'errore form per campo invalido generico. */
    ERRORE_FORMATO_GENERICO: string;
    /** Costante che indica la chiave per l'errore di validazione quando il campo ha una lunghezza errata, rispetto a quello richiesto. */
    LUNGHEZZA_CAMPO_ESATTA: string;
    /** Costante che indica la chiave per l'errore di validazione quando il campo ha una lunghezza errata e risulta minore rispetto a quello richiesto. */
    LUNGHEZZA_CAMPO_MINORE: string;
    /** Costante che indica la chiave per l'errore di validazione quando il campo ha una lunghezza errata e risulta maggiore rispetto a quello richiesto. */
    LUNGHEZZA_CAMPO_MAGGIORE: string;
    /** Costante che indica la chiave per l'errore di validazione quando il campo non è valorizzato ed è richiesto. */
    DATA_INIZIO_REQUIRED: string;
    /** Costante che indica la chiave per l'errore quando un numero non è all'interno di un range di valori. */
    NUMBER_NOT_IN_RANGE: string;
    /** Costante che indica la chiave per l'errore quando i campi risultano obbligatori. */
    CAMPI_OBBLIGATORI: string;
    /** Costante che indica la chiave per l'errore quando il "Valore1" dei range canoni è maggiore del "Valore2". */
    RANGE_VALORE1_GREAT_VALORE2: string;
  }
  
  /**
   * Classe di mapping delle chiavi per gli erorri del form.
   */
  export const ScrivaErrorKeys: IScrivaErrorKeys = {
    /** Costante che indica la chiave per il recupero dell'errore form per campo invalido generico. */
    ERRORE_FORMATO_GENERICO: 'erroreFormatoGenerico',
    /** Costante che indica la chiave per l'errore di validazione quando il campo ha una lunghezza errata, rispetto a quello richiesto. */
    LUNGHEZZA_CAMPO_ESATTA: 'lunghezzaCampoEsattaInvalid',
    /** Costante che indica la chiave per l'errore di validazione quando il campo ha una lunghezza errata e risulta minore rispetto a quello richiesto. */
    LUNGHEZZA_CAMPO_MINORE: 'lunghezzaCampoMinoreInvalid',
    /** Costante che indica la chiave per l'errore di validazione quando il campo ha una lunghezza errata e risulta maggiore rispetto a quello richiesto. */
    LUNGHEZZA_CAMPO_MAGGIORE: 'lunghezzaCampoMaggioreInvalid',
    /** Costante che indica la chiave per il recupero dell'errore sul form: usi delle annualità in stati debitori . */
    DATA_INIZIO_REQUIRED: 'dataInizioRequired',
  
    /** Costante che indica la chiave per l'errore quando un numero non è all'interno di un range di valori. */
    NUMBER_NOT_IN_RANGE: 'numberNotInrange',
  
    /** Costante che indica la chiave per l'errore quando i campi risultano obbligatori. */
    CAMPI_OBBLIGATORI: 'campiOblligatoriError',
  
    /** Costante che indica la chiave per l'errore quando il "Valore1" dei range canoni è maggiore del "Valore2". */
    RANGE_VALORE1_GREAT_VALORE2: 'rangeValore1MaggValore2',
  };
  