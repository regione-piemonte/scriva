import { Observable } from 'rxjs';
import { ConfigAdempimento } from '../../../../../../shared/models';

/**
 * Interfaccia che definisce l'oggetto da passare per la generazione della descrizione di una valutazione.
 */
export interface IDichiarazioneACPubVALSpecifica {
  descrizioneComune: string;
  link: string;
}

/**
 * Interfaccia con le informazioni per la generazione dati per la dichiarazione sui dati personali (GDPR) di: Regione Piemonte.
 */
export interface IDatiGDPR_AC_VAL {
  webLink?: string;
}

/**
 * Interfaccia con le informazioni per la generazione dati per la dichiarazione sui dati personali (GDPR) di: Regione Piemonte.
 */
export interface IDatiACRegionePiemonteVAL extends IDatiGDPR_AC_VAL {}

/**
 * Interfaccia con le informazioni per la generazione dati per la dichiarazione sui dati personali (GDPR) di: altra AC NON Regione Piemonte.
 */
export interface IDatiACSpecificaVAL extends IDatiGDPR_AC_VAL {
  comune: string;
}

/**
 * Interfaccia con la richiesta dati per lo scarico delle informazioni per i link dell'ac principale per le informazioni istanza.
 */
export interface ILinkAcWebVALReq {
  linkAcWebGDPRRegione: Observable<ConfigAdempimento>;
  linkAcWebGDPRAltraAC?: Observable<ConfigAdempimento>;
  linkAcWebPub: Observable<ConfigAdempimento>;
}

/**
 * Interfaccia con la risposta dati per lo scarico delle informazioni per i link dell'ac principale per le informazioni istanza.
 */
export interface ILinkAcWebVALRes {
  linkAcWebGDPRRegione: ConfigAdempimento;
  linkAcWebGDPRAltraAC?: ConfigAdempimento;
  linkAcWebPub: ConfigAdempimento;
}
