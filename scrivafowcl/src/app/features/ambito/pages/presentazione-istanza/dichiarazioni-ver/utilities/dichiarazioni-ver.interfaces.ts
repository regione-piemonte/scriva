import { Observable } from "rxjs";

import { ConfigAdempimento } from "../../../../../../shared/models";

/**
 * Interfaccia che definisce l'oggetto da passare per la generazione della descrizione di una valutazione VER.
 */
export interface IDichiarazioneACPubVERSpecifica {
  descrizioneComune: string;
  link: string;
}

/**
 * Interfaccia con le informazioni per la generazione dati per la dichiarazione sui dati personali (GDPR) di: Regione Piemonte.
 */
export interface IDatiGDPR_AC_VER {
  webLink?: string;
}

/**
 * Interfaccia con le informazioni per la generazione dati per la dichiarazione sui dati personali (GDPR) di: Regione Piemonte.
 */
export interface IDatiACRegionePiemonteVER extends IDatiGDPR_AC_VER {}

/**
 * Interfaccia con le informazioni per la generazione dati per la dichiarazione sui dati personali (GDPR) di: altra AC NON Regione Piemonte.
 */
export interface IDatiACSpecificaVER extends IDatiGDPR_AC_VER {
  comune: string;
}

/**
 * Interfaccia con la richiesta dati per lo scarico delle informazioni per i link dell'ac principale per le informazioni istanza.
 */
export interface ILinkAcWebVERReq {
  linkAcWebGDPRRegione: Observable<ConfigAdempimento>;
  linkAcWebGDPRAltraAC?: Observable<ConfigAdempimento>;
  linkAcWebPub: Observable<ConfigAdempimento>;
}

/**
 * Interfaccia con la risposta dati per lo scarico delle informazioni per i link dell'ac principale per le informazioni istanza.
 */
export interface ILinkAcWebVERRes {
  linkAcWebGDPRRegione: ConfigAdempimento;
  linkAcWebGDPRAltraAC?: ConfigAdempimento;
  linkAcWebPub: ConfigAdempimento;
}