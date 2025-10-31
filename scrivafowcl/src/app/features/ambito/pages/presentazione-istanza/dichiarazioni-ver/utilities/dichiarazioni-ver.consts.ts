import { DichiarazioniOrientamentoVIAConsts } from '../../../../services/orientamento-istanza/orientamento-istanza-dichiarazioni/utilities/dichiarazioni/dichiarazioni-orientamento-via.consts';
import { DichiarazioneACVIA } from '../../dichiarazioni-via/utilities/dichiarazioni-via.consts';
import { IDichiarazioneACVER } from '../../dichiarazioni-via/utilities/dichiarazioni-via.interfaces';
import { IDichiarazioneACPubVERSpecifica } from './dichiarazioni-ver.interfaces';

/**
 * Classe con le costanti associate al componente omonimo.
 */
export class DichiarazioniOrientamentoVERConsts extends DichiarazioniOrientamentoVIAConsts {
  /** DichiarazioniACPubVER come costante che serve per gestire la valutazione PUB. */
  dichiarazioneACPubVER = new DichiarazioniACPubVER();
  readonly DICHIARAZIONE_NO_NUOVA_REAL_AREE_SITI_DESCR: string = "dichiara che il progetto non è relativo ad opere o interventi di nuova realizzazione che ricadono parzialmente/totalmente all'interno di aree naturali protette come definite dalla legge 6 dicembre 1991,  n. 394, ovvero all'interno di siti della rete Natura 2000 (ai sensi del d.lgs. 152/2006, art.6, comma 7, lett.b), tale casistica non rientra nel campo di applicazione della verifica di assoggettabilità a VIA, ma della VIA);";
  readonly DICHIARAZIONE_SPEC_COND_AMB_SCREENING_TRUE: string ="chiede che siano specificate, nel provvedimento di verifica di assoggettabilità alla VIA (screening)  relativo al progetto in oggetto, le condizioni ambientali necessarie e vincolanti per evitare o prevenire quelli che potrebbero altrimenti rappresentare impatti ambientali significativi e negativi;"
  readonly DICHIARAZIONE_SPEC_COND_AMB_SCREENING_FALSE: string ="non chiede che siano specificate, nel provvedimento di verifica di assoggettabilità alla VIA (screening) relativo al progetto in oggetto, le condizioni ambientali necessarie e vincolanti per evitare o prevenire quelli che potrebbero altrimenti rappresentare impatti ambientali significativi e negativi;"



  /**
   * Costruttore.
   */
  constructor() {
    // Richiamo il super
    super();
  }
}

/**
 * Classe che permette la gestione delle dichiarazioni configurabili tramite autorità competente.
 */
export class DichiarazioniACPubVER extends DichiarazioneACVIA {
  /** string costanti con la chiave usata per la gestione dei placholder di AC specifica: descrizione. */
  readonly PH_DES_AC_SPECIFICA: string = `{{ PH_DES_AC_SPECIFICA }}`;
  /** string costanti con la chiave usata per la gestione dei placholder di AC specifica: link. */
  readonly PH_LINK_AC_SPECIFICA: string = `{{ PH_LINK_AC_SPECIFICA }}`;

  /** string con la descrizione statica per la dichiarazione ambientale con riferimento AC: Regione Piemonte */
  desDichACRegionePiemonte: string = `dichiara di essere consapevole che l'Amministrazione pubblicherà la documentazione trasmessa con la presente istanza (d.lgs.152/2006, art. 19, comma 3) sul sito web istituzionale della Regione Piemonte http://www.sistemapiemonte.it/cms/privati/ambiente-e-energia/servizi/540-valutazioni-ambientali`;
  /** string con la descrizione statica per la dichiarazione ambientale con riferimento AC: AC specifica, diversa da Regione Piemonte. */
  desDichACSpecifica: string = `dichiara di essere consapevole che l'Amministrazione pubblicherà la documentazione trasmessa con la presente istanza (d.lgs.152/2006, art. 19, comma 3) sul sito web istituzionale della ${this.PH_DES_AC_SPECIFICA} ${this.PH_LINK_AC_SPECIFICA}`;

  /** string con la struttura html da visualizzare in pagina per la dichiarazione ambientale con riferimento AC: Regione Piemonte */
  htmlDichACRegionePiemonte: string = `* dichiara di essere consapevole che l'Amministrazione pubblicherà la documentazione trasmessa con la presente istanza (d.lgs.152/2006, art. 19, comma 3) sul <a href="http://www.sistemapiemonte.it/cms/privati/ambiente-e-energia/servizi/540-valutazioni-ambientali" target="_blank">sito web istituzionale della Regione Piemonte</a>`;
  /** string con la strttura html da visualizzare in pagina per la dichiarazione ambientale con riferimento AC: AC specifica, diversa da Regione Piemonte. */
  htmlDichACSpecifica: string = `* dichiara di essere consapevole che l'Amministrazione pubblicherà la documentazione trasmessa con la presente istanza (d.lgs.152/2006, art. 19, comma 3) sul sito web istituzionale della <a href="${this.PH_LINK_AC_SPECIFICA}" target="_blank">${this.PH_DES_AC_SPECIFICA}</a>`;

  /**
   * Costruttore.
   */
  constructor(c?: IDichiarazioneACVER) {
    // Richiamo il super
    super(c);
  }

  /**
   * ###############################
   * HTML DICHIARAZIONE AC SPECIFICA
   * ###############################
   */

  // #region "HTML DICHIARAZIONE AC SPECIFICA"

  /**
   * Funzione che genera la struttura HTML completa con link per le dichiarazioni VIA di un'AC specifica.
   * La funzione è pensata per essere overridata con le customizzazioni specifiche per la dichiarazione.
   * @param params IDichiarazioneACPubVERSpecifica contenente tutte le informazioni per la generazione dell'html.
   * @returns string con la dichiarazione come struttura HTML leggibile dalle pagine.
   * @override
   */
  htmlDichiarazioneACSpecifica(
    params: IDichiarazioneACPubVERSpecifica
  ): string {
    // Estraggo le informazioni dai parametri
    const { descrizioneComune, link } = params ?? {};

    // Recupero la descrizione con html per AC specifica
    let dichiarazione = this.htmlDichACSpecifica;

    // Vado ad effettuare i replace specifici e ritorno la dichiarazione aggiornata
    dichiarazione = dichiarazione
      .replace(this.PH_DES_AC_SPECIFICA, descrizioneComune)
      .replace(this.PH_LINK_AC_SPECIFICA, link);

    // Ritorno la dichiarazione aggiornata
    return dichiarazione;
  }

  // #endregion "HTML DICHIARAZIONE AC SPECIFICA"

  /**
   * ##########################
   * DICHIARAZIONE AC SPECIFICA
   * ##########################
   */

  // #region "DICHIARAZIONE AC SPECIFICA"

  /**
   * Funzione che genera la descrizione completa le dichiarazioni VIA di un'AC specifica.
   * La funzione è pensata per essere overridata con le customizzazioni specifiche per la dichiarazione.
   * @param params IDichiarazioneACPubVERSpecifica contenente tutte le informazioni per la generazione dell'html.
   * @returns string con la dichiarazione come struttura HTML leggibile dalle pagine.
   * @override
   */
  descrizioneDichiarazioneACSpecifica(
    params: IDichiarazioneACPubVERSpecifica
  ): string {
    // Estraggo le informazioni dai parametri
    const { descrizioneComune, link } = params ?? {};

    // Recupero la descrizione con descrizione per AC specifica
    let dichiarazione = this.desDichACSpecifica;

    // Vado ad effettuare i replace specifici e ritorno la dichiarazione aggiornata
    dichiarazione = dichiarazione
      .replace(this.PH_DES_AC_SPECIFICA, descrizioneComune)
      .replace(this.PH_LINK_AC_SPECIFICA, link);

    // Ritorno la dichiarazione aggiornata
    return dichiarazione;
  }

  // #endregion "DICHIARAZIONE AC SPECIFICA"
}
