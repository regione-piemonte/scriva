/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Injectable } from '@angular/core';
import { OggettoIstanza } from '../../../models';
import { OpereTrasportoConsts } from '../../../pages/presentazione-istanza/opere-trasporto/utilities/opere-trasporto.consts';

@Injectable()
export class OpereTrasportoService {
  /** OpereTrasportoConsts con le informazioni costanti per il componente. */
  readonly OPERE_CONSTS = new OpereTrasportoConsts();

  /**
   * Costruttore.
   */
  constructor() {}

  /**
   * Funzione che gestisce la verifica di accesso ai dati tecnici dato un codice identificativo per la tipologia dell'oggetto istanza.
   * @param tipoOggettoIstanza string con il codice della tipologia oggetto-istanza da verificare.
   * @param verificaConAccesso boolean che definisce se il check per l'accesso dei dati deve prendere in considerazione i codici delle opere per cui è garantito l'accesso. Se false, verrà controllata la lista con le tipologie oggette da escludere.
   * @returns boolean con il check per l'accesso ai dati tecnici.
   * @author Ismaele Bottelli
   * @date 09/12/2024
   * @jira SCRIVA-1574
   * @notes Al momento della scrittura della funzione, l'analista #Alessandro_Verner ha detto che sull'analisi ha definito la gestione di accesso per tipologia.
   *        Non è però sicuro al 100%, per cui al momento sviluppo la funzione in maniera tale che sia modulare sia per la definizione di accesso che per non accesso.
   */
  accediDatiTecniciOpereTrasportoByTipoOggettoIstanza(
    tipoOggettoIstanza: string,
    verificaConAccesso: boolean
  ): boolean {
    // Verifico se la gestione è sulla lista di elementi inclusivi o esclusivi
    if (verificaConAccesso) {
      // Getisco le logiche con la lista dati inclusivi
      return this.accediDTOpereTrasportoByTipoOggettoIstanza(
        tipoOggettoIstanza
      );
      // #
    } else {
      // Getisco le logiche con la lista dati inclusivi
      return this.bloccaDTOpereTrasportoByTipoOggettoIstanza(
        tipoOggettoIstanza
      );
      // #
    }
  }

  /**
   * #########################
   * ACCEDI DT OPERE TRASPORTO
   * #########################
   */

  // #region "ACCEDI DT OPERE TRASPORTO"

  /**
   * Funzione che gestisce la verifica di accesso ai dati tecnici dato un codice identificativo per la tipologia dell'oggetto istanza.
   * La condizione è di accesso, per cui se l'input è mappato come uno dei codici di accesso, si potrà accedere ai dati tecnici.
   * @param oggettoIstanza OggettoIstanza con i dati dell'oggetto istanza da verificare.
   * @returns boolean con il check per l'accesso ai dati tecnici.
   * @author Ismaele Bottelli
   * @date 09/12/2024
   * @jira SCRIVA-1574
   */
  accediDTOpereTrasporto(oggettoIstanza: OggettoIstanza): boolean {
    // Verifico l'input
    if (!oggettoIstanza || !oggettoIstanza.id_oggetto_istanza) {
      // Manca la configurazione minima
      return false;
    }

    // Recupero la tipologia dell'oggetto istanza
    const tipoOggIst: string =
      oggettoIstanza.tipologia_oggetto.cod_tipologia_oggetto;

    // Richiamo la funzione per verificare l'accesso ai dati tecnici per le opere di trasporto
    return this.bloccaDTOpereTrasportoByTipoOggettoIstanza(tipoOggIst);
  }

  /**
   * Funzione che gestisce la verifica di accesso ai dati tecnici dato un codice identificativo per la tipologia dell'oggetto istanza.
   * La condizione è di accesso, per cui se l'input è mappato come uno dei codici di accesso, si potrà accedere ai dati tecnici.
   * @param tipoOggettoIstanza string con il codice della tipologia oggetto-istanza da verificare.
   * @returns boolean con il check per l'accesso ai dati tecnici.
   * @author Ismaele Bottelli
   * @date 09/12/2024
   * @jira SCRIVA-1574
   * @notes #Alessandro_Verner sull'analisi non ha descritto in maniera dettagliata i codici.
   *        Al momento è un accordo preso per le vie brevi, ho chiesto conferma che i codici fossero giusti per il tipo di gestione.
   */
  accediDTOpereTrasportoByTipoOggettoIstanza(
    tipoOggettoIstanza: string
  ): boolean {
    // Definisco la lista di codici oggetto-istanza che permettono l'accesso
    const accessoPer: string[] =
      this.OPERE_CONSTS.OPERE_TRASPORTO_CON_DATI_TECNICI;
    // Verifico e ritorno se il tipo oggetto istanza in input è all'interno della lista delle opere con accesso
    const conAccesso: boolean = accessoPer.some(
      (codiceAccessoOpera: string) => {
        // Verifico il match tra codici
        return codiceAccessoOpera === tipoOggettoIstanza;
        // #
      }
    );
    // Ritorno il flag con accesso
    return conAccesso;
  }

  // #endregion "ACCEDI DT OPERE TRASPORTO"

  /**
   * #########################
   * BLOCCO DT OPERE TRASPORTO
   * #########################
   */

  // #region "BLOCCO DT OPERE TRASPORTO"

  /**
   * Funzione che gestisce la verifica di accesso ai dati tecnici dato un codice identificativo per la tipologia dell'oggetto istanza.
   * La condizione è di rifiuto, per cui se l'input è mappato come uno dei codici di accesso, non si potrà accedere ai dati tecnici.
   * @param oggettoIstanza OggettoIstanza con i dati dell'oggetto istanza da verificare.
   * @returns boolean con il check per l'accesso ai dati tecnici.
   * @author Ismaele Bottelli
   * @date 09/12/2024
   * @jira SCRIVA-1574
   */
  bloccaDTOpereTrasporto(oggettoIstanza: OggettoIstanza): boolean {
    // Verifico l'input
    if (!oggettoIstanza || !oggettoIstanza.id_oggetto_istanza) {
      // Manca la configurazione minima
      return false;
    }

    // Recupero la tipologia dell'oggetto istanza
    const tipoOggIst: string =
      oggettoIstanza.tipologia_oggetto.cod_tipologia_oggetto;

    // Richiamo la funzione per verificare l'accesso ai dati tecnici per le opere di trasporto
    return this.bloccaDTOpereTrasportoByTipoOggettoIstanza(tipoOggIst);
  }

  /**
   * Funzione che gestisce la verifica di accesso ai dati tecnici dato un codice identificativo per la tipologia dell'oggetto istanza.
   * La condizione è di rifiuto, per cui se l'input è mappato come uno dei codici di accesso, non si potrà accedere ai dati tecnici.
   * @param tipoOggettoIstanza string con il codice della tipologia oggetto-istanza da verificare.
   * @returns boolean con il check per l'accesso ai dati tecnici.
   * @author Ismaele Bottelli
   * @date 09/12/2024
   * @jira SCRIVA-1574
   * @notes #Alessandro_Verner sull'analisi non ha descritto in maniera dettagliata i codici.
   *        Al momento è un accordo preso per le vie brevi, ho chiesto conferma che i codici fossero giusti per il tipo di gestione.
   */
  bloccaDTOpereTrasportoByTipoOggettoIstanza(
    tipoOggettoIstanza: string
  ): boolean {
    // Definisco la lista di codici oggetto-istanza che permettono l'accesso
    const rifiutoPer: string[] =
      this.OPERE_CONSTS.OPERE_TRASPORTO_SENZA_DATI_TECNICI;
    // Verifico e ritorno se il tipo oggetto istanza in input è all'interno della lista delle opere con accesso
    const conRifiuto: boolean = rifiutoPer.some(
      (codiceAccessoOpera: string) => {
        // Verifico il match tra codici
        return codiceAccessoOpera === tipoOggettoIstanza;
        // #
      }
    );
    // Se ho trovato il codice nella lista devo impedire l'accesso tornando false
    return conRifiuto ? false : true;
  }

  // #endregion "BLOCCO DT OPERE TRASPORTO"
}
