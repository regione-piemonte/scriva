import { environment } from 'src/environments/environment';
import { TipoInfoAdempimento } from '../../../../../../../shared/services/configurazioni/utilities/configurazioni.enums';

/**
 * Classe con le costanti associate al componente omonimo.
 */
export class DichiarazioniOrientamentoVIAConsts {
  // #### REGIONE PIEMONTE ####
  /** string con la definizione del placeholder FormIo per la gestione del codice adempimento specifica per: REGIONE PIEMONTE. */
  private readonly PH_FORMIO_COD_TIPO_ADEMPI: string =
    '{{form.imports.codTipoAdempimento}}';
  /** string con la definizione del placeholder FormIo per la gestione del codice autorità competente specifica per: REGIONE PIEMONTE. */
  private readonly PH_FORMIO_COD_AC: string =
    '{{form.imports.codAutCompetente}}';
  /** string con la definizione del placeholder FormIo per la gestione del link all'autorità competente specifica per: REGIONE PIEMONTE. */
  private readonly PH_FORMIO_LINK_AC: string = '{{form.imports.acWeb}}';
  // #### ALTRA AC SPECIFICA ####
  /** string con la definizione del placeholder FormIo per la gestione del codice adempimento specifica per: AUTORITA' COMPETENTE SPECIFICA. */
  private readonly PH_FORMIO_COD_TIPO_ADEMPI_AC_SPEC: string =
    '{{form.imports.autoritaCompetenteSpecifica?.codTipoAdempimento}}';
  /** string con la definizione del placeholder FormIo per la gestione del codice autorità competente specifica per: AUTORITA' COMPETENTE SPECIFICA. */
  private readonly PH_FORMIO_COD_AC_SPEC: string =
    '{{form.imports.autoritaCompetenteSpecifica?.codAutCompetente}}';
  /** string con la definizione del placeholder FormIo per la gestione del link all'autorità competente specifica per: AUTORITA' COMPETENTE SPECIFICA. */
  private readonly PH_FORMIO_LINK_AC_SPEC: string =
    '{{form.imports.autoritaCompetenteSpecifica.acWeb}}';
  /** string con la definizione del placeholder FormIo per la gestione della descrizione dell'autorità competente specifica per: AUTORITA' COMPETENTE SPECIFICA. */
  private readonly PH_FORMIO_DESC_AC_SPEC: string =
    '{{ form.imports.autoritaCompetenteSpecifica?.autoritaCompetente?.des_competenza_territorio }}';

  // #### REGIONE PIEMONTE ####
  /**string con la definizione del path del documento del GDPR */
  private readonly GDPR_DOCURL: string = environment.GDPR_docUrl;
  /** string con la definizione del placeholder per la gestione del codice adempimento specifica per: REGIONE PIEMONTE. */
  private readonly PH_COD_TIPO_ADEMPI_RP: string = '{{ COD_TIPO_ADEMPI }}';
  /** string con la definizione del placeholder per la gestione del codice autorità competente specifica per: REGIONE PIEMONTE. */
  private readonly PH_COD_AC_RP: string = '{{ COD_AC }}';
  /** string con la definizione del placeholder per la gestione del link all'autorità competente specifica per: REGIONE PIEMONTE. */
  private readonly PH_LINK_AC_RP: string = '{{ LINK_AC }}';
  // #### ALTRA AC SPECIFICA ####
  /** string con la definizione del placeholder per la gestione del codice adempimento specifica per: AUTORITA' COMPETENTE SPECIFICA. */
  private readonly PH_COD_TIPO_ADEMPI_AC_SPEC: string =
    '{{ COD_TIPO_ADEMPI_AC_SPEC }}';
  /** string con la definizione del placeholder per la gestione del codice autorità competente specifica per: AUTORITA' COMPETENTE SPECIFICA. */
  private readonly PH_COD_AC_SPEC: string = '{{ COD_AC_SPEC }}';
  /** string con la definizione del placeholder per la gestione del link all'autorità competente specifica per: AUTORITA' COMPETENTE SPECIFICA. */
  private readonly PH_LINK_AC_SPEC: string = '{{ LINK_AC_SPEC }}';
  /** string con la definizione del placeholder per la gestione della descrizione dell'autorità competente specifica per: AUTORITA' COMPETENTE SPECIFICA. */
  private readonly PH_DESC_AC_SPEC: string = '{{ DESC_AC_SPEC }}';

  /** string con la struttura HTML completa con la descrizione per le dichiarazioni privacy di VIA per: regione piemonte. */
  private readonly HTML_DICH_VIA_REGIONE_PIEMONTE: string = `<span>di aver preso visione dell'<a href="${this.GDPR_DOCURL}${this.PH_COD_TIPO_ADEMPI_RP}_${this.PH_COD_AC_RP}_GDPR.pdf" target="_blank">Informativa sul trattamento dei dati personali</a>, aggiornata all'art. 13 del GDPR 2016/679, consultabile anche sul <a href="${this.PH_LINK_AC_RP}" target="_blank">sito web istituzionale della Regione Piemonte</a></span>`;
  /** string con la struttura HTML completa con la descrizione per le dichiarazioni privacy di VIA per: non regione piemonte. */
  private readonly HTML_DICH_VIA_AC_SPECIFICA: string = `<span>di aver preso visione dell'<a href="${this.GDPR_DOCURL}${this.PH_COD_TIPO_ADEMPI_RP}_${this.PH_COD_AC_RP}_GDPR_ESTESA.pdf" target="_blank">Informativa sul trattamento dei dati personali</a>, aggiornata all'art. 13 del GDPR 2016/679, consultabile anche sul <a href="${this.PH_LINK_AC_RP}" target="_blank">sito web istituzionale della Regione Piemonte</a> e di aver preso visione dell'<a href="${this.GDPR_DOCURL}${this.PH_COD_TIPO_ADEMPI_AC_SPEC}_${this.PH_COD_AC_SPEC}_GDPR.pdf" target="_blank">Informativa sul trattamento dei dati personali</a>, aggiornata all'art. 13 del GDPR 2016/679, consultabile anche sul <a href="${this.PH_LINK_AC_SPEC}" target="_blank">sito web istituzionale della ${this.PH_DESC_AC_SPEC}</a></span>`;

  /** string che definisce la chiave di placeholder per la descrizione dichiarazione VIA per: non regione piemonte. */
  private readonly DES_DICHIARAZIONE_VIA_NRP_PLACEHOLDER: string =
    '{{ AUTORITA_COMPETENTE }}';

  /** string con la descrizione testuale per le dichiarazioni privacy di VIA per: regione piemonte. */
  private readonly DES_DICH_VIA_REGIONE_PIEMONTE: string = `di aver preso visione dell'Informativa sul trattamento dei dati personali, aggiornata all'art. 13 del GDPR 2016/679, consultabile anche sul sito web istituzionale della Regione Piemonte`;
  /** string con la descrizione testuale per le dichiarazioni privacy di VIA per: non regione piemonte. */
  private readonly DES_DICH_VIA_NON_REGIONE_PIEMONTE: string = `${this.DES_DICH_VIA_REGIONE_PIEMONTE} ${this.PH_LINK_AC_RP} e di aver preso visione dell'Informativa sul trattamento dei dati personali, aggiornata all'art. 13 del GDPR 2016/679, consultabile anche sul sito web istituzionale della ${this.DES_DICHIARAZIONE_VIA_NRP_PLACEHOLDER}`;

  /** string con la chiave costante necessaria per lo scarico delle informazioni per i dati delle autorità competenti riferite alle competenze del territorio. */
  public readonly INFO_VIA_DICH_PUB: TipoInfoAdempimento =
    TipoInfoAdempimento.acWebPub;

  /**
   * Costruttore.
   */
  constructor() {}

  /**
   * ###################################
   * LINK DICHIARAZIONE REGIONE PIEMONTE
   * ###################################
   */

  // #region "LINK DICHIARAZIONE REGIONE PIEMONTE"

  /**
   * Funzione che genera la struttura HTML completa con link per le dichiarazioni VIA di Regione Piemonte.
   * @param codiceTipoAdempimento string con l'indicazione per la dichiarazione.
   * @param codiceAutoritaCompetente string con l'indicazione per la dichiarazione.
   * @param linkAutoritaCompetente string con l'indicazione per la dichiarazione.
   * @returns string con la dichiarazione compilata con le informazioni in input.
   */
  linkDichiarazioniVIARegionePiemonte(
    codiceTipoAdempimento: string,
    codiceAutoritaCompetente: string,
    linkAutoritaCompetente: string
  ): string {
    // Verifico l'input
    codiceTipoAdempimento = codiceTipoAdempimento ?? '';
    codiceAutoritaCompetente = codiceAutoritaCompetente ?? '';
    linkAutoritaCompetente = linkAutoritaCompetente ?? '';
    // Recupero i placholder per la gestione della descrizione
    const phCodTipoAdempi = this.PH_COD_TIPO_ADEMPI_RP;
    const phCodAC = this.PH_COD_AC_RP;
    const phLinkAC = this.PH_LINK_AC_RP;
    // Recupero la descrizione con link per regione piemonte
    let dichiarazione = this.HTML_DICH_VIA_REGIONE_PIEMONTE;

    // Vado ad effettuare i replace specifici e ritorno la dichiarazione aggiornata
    dichiarazione = dichiarazione
      .replace(phCodTipoAdempi, codiceTipoAdempimento)
      .replace(phCodAC, codiceAutoritaCompetente)
      .replace(phLinkAC, linkAutoritaCompetente);

    // Ritorno la dichiarazione aggiornata
    return dichiarazione;
  }

  /**
   * Funzione che genera la struttura HTML completa con link per le dichiarazioni VIA di Regione Piemonte.
   * La funzione gestisce in maniera specifica la costruzione della dichiarazione per la struttura del FormIo del quadro orientamento.
   * @returns string con la dichiarazione compilata con le informazioni in input.
   */
  linkDichiarazioniVIARegionePiemonteFormIo(): string {
    // Verifico l'input
    const codiceTipoAdempimento = this.PH_FORMIO_COD_TIPO_ADEMPI;
    const codiceAutoritaCompetente = this.PH_FORMIO_COD_AC;
    const linkAutoritaCompetente = this.PH_FORMIO_LINK_AC;

    // Richiamo la funzione con la gestione dei placeholder
    return this.linkDichiarazioniVIARegionePiemonte(
      codiceTipoAdempimento,
      codiceAutoritaCompetente,
      linkAutoritaCompetente
    );
  }

  // #endregion "LINK DICHIARAZIONE REGIONE PIEMONTE"

  /**
   * ###############################
   * LINK DICHIARAZIONE AC SPECIFICA
   * ###############################
   */

  // #region "LINK DICHIARAZIONE AC SPECIFICA"

  /**
   * Funzione che genera la struttura HTML completa con link per le dichiarazioni VIA di Regione Piemonte.
   * @param codiceTipoAdempimento string con l'indicazione per la dichiarazione.
   * @param codiceAutoritaCompetente string con l'indicazione per la dichiarazione.
   * @param linkAutoritaCompetente string con l'indicazione per la dichiarazione.
   * @param codiceTipoAdempimentoACSpec string con l'indicazione per la dichiarazione.
   * @param codiceAutoritaCompetenteSpec string con l'indicazione per la dichiarazione.
   * @param linkAutoritaCompetenteSpec string con l'indicazione per la dichiarazione.
   * @param descAutoritaCompetenteSpec string con l'indicazione per la dichiarazione.
   * @returns string con la dichiarazione compilata con le informazioni in input.
   */
  linkDichiarazioniVIAACSpecifica(
    codiceTipoAdempimento: string,
    codiceAutoritaCompetente: string,
    linkAutoritaCompetente: string,
    codiceTipoAdempimentoACSpec: string,
    codiceAutoritaCompetenteSpec: string,
    linkAutoritaCompetenteSpec: string,
    descAutoritaCompetenteSpec: string
  ): string {
    // Verifico l'input
    codiceTipoAdempimento = codiceTipoAdempimento ?? '';
    codiceAutoritaCompetente = codiceAutoritaCompetente ?? '';
    linkAutoritaCompetente = linkAutoritaCompetente ?? '';
    codiceTipoAdempimentoACSpec = codiceTipoAdempimentoACSpec ?? '';
    codiceAutoritaCompetenteSpec = codiceAutoritaCompetenteSpec ?? '';
    linkAutoritaCompetenteSpec = linkAutoritaCompetenteSpec ?? '';
    descAutoritaCompetenteSpec = descAutoritaCompetenteSpec ?? '';
    // Recupero i placholder per la gestione della descrizione
    const phCodTipoAdempi = this.PH_COD_TIPO_ADEMPI_RP;
    const phCodAC = this.PH_COD_AC_RP;
    const phLinkAC = this.PH_LINK_AC_RP;
    const phCodTipoAdempiACSpec = this.PH_COD_TIPO_ADEMPI_AC_SPEC;
    const phCodACSpec = this.PH_COD_AC_SPEC;
    const phLinkACSpec = this.PH_LINK_AC_SPEC;
    const phDescACSpec = this.PH_DESC_AC_SPEC;
    // Recupero la descrizione con link per ac specifica
    let dichiarazione = this.HTML_DICH_VIA_AC_SPECIFICA;

    // Vado ad effettuare i replace specifici e ritorno la dichiarazione aggiornata
    dichiarazione = dichiarazione
      .replace(phCodTipoAdempi, codiceTipoAdempimento)
      .replace(phCodAC, codiceAutoritaCompetente)
      .replace(phLinkAC, linkAutoritaCompetente)
      .replace(phCodTipoAdempiACSpec, codiceTipoAdempimentoACSpec)
      .replace(phCodACSpec, codiceAutoritaCompetenteSpec)
      .replace(phLinkACSpec, linkAutoritaCompetenteSpec)
      .replace(phDescACSpec, descAutoritaCompetenteSpec);

    // Ritorno la dichiarazione aggiornata
    return dichiarazione;
  }

  /**
   * Funzione che genera la struttura HTML completa con link per le dichiarazioni VIA di Regione Piemonte.
   * La funzione gestisce in maniera specifica la costruzione della dichiarazione per la struttura del FormIo del quadro orientamento.
   * @returns string con la dichiarazione compilata con le informazioni in input.
   */
  linkDichiarazioniVIAACSpecificaFormIo(): string {
    // Verifico l'input
    const codiceTipoAdempimento = this.PH_FORMIO_COD_TIPO_ADEMPI;
    const codiceAutoritaCompetente = this.PH_FORMIO_COD_AC;
    const linkAutoritaCompetente = this.PH_FORMIO_LINK_AC;
    const codiceTipoAdempimentoACSpec = this.PH_FORMIO_COD_TIPO_ADEMPI_AC_SPEC;
    const codiceAutoritaCompetenteSpec = this.PH_FORMIO_COD_AC_SPEC;
    const linkAutoritaCompetenteSpec = this.PH_FORMIO_LINK_AC_SPEC;
    const descAutoritaCompetenteSpec = this.PH_FORMIO_DESC_AC_SPEC;

    // Richiamo la funzione con la gestione dei placeholder
    return this.linkDichiarazioniVIAACSpecifica(
      codiceTipoAdempimento,
      codiceAutoritaCompetente,
      linkAutoritaCompetente,
      codiceTipoAdempimentoACSpec,
      codiceAutoritaCompetenteSpec,
      linkAutoritaCompetenteSpec,
      descAutoritaCompetenteSpec
    );
  }

  // #endregion "LINK DICHIARAZIONE AC SPECIFICA"

  /**
   * ##############################
   * DICHIARAZIONE REGIONE PIEMONTE
   * ##############################
   */

  // #region "DICHIARAZIONE REGIONE PIEMONTE"

  /**
   * Funzione che genera la descrizione completa per le dichiarazioni VIA di Regione Piemonte.
   * @param linkAutoritaCompetente string con l'indicazione per la dichiarazione.
   * @returns string con la dichiarazione compilata con le informazioni in input.
   */
  dichiarazioniVIARegionePiemonte(linkAutoritaCompetente: string): string {
    // Verifico l'input
    linkAutoritaCompetente = linkAutoritaCompetente ?? '';
    // Recupero la descrizione per regione piemonte
    let dichiarazione = this.DES_DICH_VIA_REGIONE_PIEMONTE;

    // Vado a concatenare la descrizione con il link a regione piemonte
    dichiarazione = `${dichiarazione} ${linkAutoritaCompetente}`;

    // Ritorno la dichiarazione aggiornata
    return dichiarazione;
  }

  // #endregion "DICHIARAZIONE REGIONE PIEMONTE"

  /**
   * ##########################
   * DICHIARAZIONE AC SPECIFICA
   * ##########################
   */

  // #region "DICHIARAZIONE AC SPECIFICA"

  /**
   * Funzione che genera la struttura HTML completa con link per le dichiarazioni VIA di Regione Piemonte.
   * @param linkAutoritaCompetenteRegionePiemonte string con l'indicazione per la dichiarazione.
   * @param linkAutoritaCompetenteSpec string con l'indicazione per la dichiarazione.
   * @param descAutoritaCompetenteSpec string con l'indicazione per la dichiarazione.
   * @returns string con la dichiarazione compilata con le informazioni in input.
   */
  dichiarazioniVIAACSpecifica(
    linkAutoritaCompetenteRegionePiemonte: string,
    linkAutoritaCompetenteSpec: string,
    descAutoritaCompetenteSpec: string
  ): string {
    // Verifico l'input
    linkAutoritaCompetenteSpec = linkAutoritaCompetenteSpec ?? '';
    descAutoritaCompetenteSpec = descAutoritaCompetenteSpec ?? '';
    // Recupero i placholder per il link a regione piemonte
    const phLinkACRP = this.PH_LINK_AC_RP;
    // Recupero i placholder per la gestione della descrizione
    const phDescACSpec = this.DES_DICHIARAZIONE_VIA_NRP_PLACEHOLDER;
    // Recupero la descrizione per ac specifica
    let dichiarazione = this.DES_DICH_VIA_NON_REGIONE_PIEMONTE;

    // Vado ad effettuare i replace specifici e ritorno la dichiarazione aggiornata
    dichiarazione = dichiarazione
      .replace(phLinkACRP, linkAutoritaCompetenteRegionePiemonte)
      .replace(phDescACSpec, descAutoritaCompetenteSpec);
    // Vado a concatenare la descrizione con il link all'autorità competente
    dichiarazione = `${dichiarazione} ${linkAutoritaCompetenteSpec}`;

    // Ritorno la dichiarazione aggiornata
    return dichiarazione;
  }

  // #endregion "DICHIARAZIONE AC SPECIFICA"
}
