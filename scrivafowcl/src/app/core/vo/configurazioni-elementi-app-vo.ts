export interface IConfigurazioneElementeAppVo {
  identificativo?: any;
  valore?: string;
}

export class ConfigurazioneElementoAppVo {
  /** any provvisorio con la proprietà identificativo. Da sistemare quando la configurazione sarà pronta. */
  identificativo?: any;
  /** string provvisorio con il valore della configurazione. Da sistemare quando il giro sarà completo. */
  valore?: string;

  /**
   * Costruttore.
   */
  constructor(public iCEAVo?: IConfigurazioneElementeAppVo) {}

  /**
   * Funzione che converte le informazioni dello stato debitorio in informazioni leggibili dal server.
   * Verranno trasformate tutte le variabili da una struttura FE friendly, in BE like.
   * @override
   */
  toServerFormat(): IConfigurazioneElementeAppVo {
    // Creo l'oggetto con le informazioni per il BE
    const be: IConfigurazioneElementeAppVo = {};

    // Ritorno l'oggetto convertito
    return be;
  }
}

/**
 * Funzione di utility che converte una lista di oggetti (interface) con i relativi oggetti FE (class).
 * @param iInteressiLegali IConfigurazioniElementiAppVo[] con la lista da convertire.
 * @returns ConfigurazioniElementiAppVo[] con la lista dati convertita.
 */
export const toListConfigurazioniElementiAppVo = (
  configurazioniEA: IConfigurazioneElementeAppVo[]
): ConfigurazioneElementoAppVo[] => {
  // Verifico l'input
  configurazioniEA = configurazioniEA ?? [];
  // Itero la lista in input e converto gli oggetti
  return configurazioniEA.map((iCEA: IConfigurazioneElementeAppVo) => {
    // Converto e ritorno l'oggetto
    return new ConfigurazioneElementoAppVo(iCEA);
    // #
  });
};
