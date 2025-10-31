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
import { cloneDeep } from 'lodash';
import {
  IQdrConfig_QdrDerDatiGen,
  IQuadroDatiGeneraliDER,
} from '../../../../../../shared/components/formio/utilities/children/formio-dati-generali.interfaces';
import { LoggerService } from '../../../../../../shared/services/logger.service';
import { IOrientamentoDerivazioni } from './utilities/derivazioni-bozza.interfaces';

@Injectable()
export class DerivazioniBozzaService {
  /**
   * Costruttore.
   */
  constructor(private _logger: LoggerService) {}

  /**
   * ##########################
   * GESTIONE DATI ORIENTAMENTO
   * ##########################
   */

  // #region "GESTIONE DATI ORIENTAMENTO"

  /**
   * Funzione che gestisce le informazioni per l'orientamento.
   * Questa funzione raccoglie tutte le possibili modifiche d'applicare ai dati dell'orientamento.
   * @param orientamentoData IOrientamentoDerivazioni con l'oggetto dati generato per l'orientamento.
   * @returns IOrientamentoDerivazioni con le informazioni manipolate per adempimento.
   */
  completaDatiBozzaOrientamento(
    orientamentoData: IOrientamentoDerivazioni
  ): IOrientamentoDerivazioni {
    // Creo una variabile apposita per la gestione delle modifiche
    let orientamentoUpdated: IOrientamentoDerivazioni =
      cloneDeep(orientamentoData);

    // Lancio la funzione di gestione dei dati per la bozza (modifica per riferimento)
    this.aggiornaDatiBozzaOrientamento(orientamentoUpdated);

    // Ritorno l'oggetto aggiornato
    return orientamentoUpdated;
  }

  /**
   * Funzione che gestisce le informazioni per l'orientamento.
   * Questa funzione aggiorna le informazioni creando la struttura per gestire correttamente i dati per la bozza dell'istanza.
   * La funzione modificherà i dati per riferimento.
   * @param orientamentoData IOrientamentoDerivazioni con i dati dell'orientamento da gestire.
   */
  aggiornaDatiBozzaOrientamento(orientamentoData: IOrientamentoDerivazioni) {
    // Verifico l'input
    if (!orientamentoData) {
      // Manca la configurazione
      return;
      // #
    }

    // Gestisco tutto tramite un try catch perché la struttura dati NON E' STABILE, quindi per sicurezza gestisco le logiche
    try {
      // Tento di recuperare le informazioni relative ai dati dell'allegato
      const qdrAllegato: any = { ...orientamentoData.QDR_ALLEGATO };

      // Verifico la presenza delle informazioni per il quadro dei dati generali
      if (!orientamentoData.QDR_DER_DATIGEN) {
        // Manca l'oggetto per i dati generali, lo creo
        orientamentoData.QDR_DER_DATIGEN = {};
        // #
      }

      // Effettuo una destrutturazione e unione delle informazioni del quadro dei dati generali con le informazioni del quadro allegato
      // NOTA BENE: i dati del quadro allegato sono dichiarati dopo per andare a sovrascrivere eventuali proprietà con stesso nome
      orientamentoData.QDR_DER_DATIGEN = {
        ...orientamentoData.QDR_DER_DATIGEN,
        ...qdrAllegato,
      };
      // #
    } catch (e) {
      // Scrivo un log per informare del fallimento della logica
      const t: string = `aggiornaDatiBozzaOrientamento | Update bozza data failed`;
      const b: any = { error: e };
      this._logger.warning(t, b);
      // #
    }
  }

  // #endregion "GESTIONE DATI ORIENTAMENTO"

  /**
   * ######################
   * GESTIONE DATI GENERALI
   * ######################
   */

  // #region "GESTIONE DATI GENERALI"

  /**
   * Funzione che gestisce le informazioni per i dati generali.
   * Questa funzione raccoglie tutte le possibili modifiche d'applicare ai dati generali.
   * @param datiGeneraliFormIo IQuadroDatiGeneraliDER con l'oggetto dati generato per i dati generali.
   * @param jsonDataIstanza any con le informazioni del json_data.
   * @returns IQuadroDatiGeneraliDER con le informazioni manipolate per adempimento.
   */
  completaDatiGeneraliBozza(
    datiGeneraliFormIo: IQuadroDatiGeneraliDER,
    jsonDataIstanza: any
  ): IQuadroDatiGeneraliDER {
    // Creo una variabile apposita per la gestione delle modifiche
    let datiGeneraliUpdated: IQuadroDatiGeneraliDER =
      cloneDeep(datiGeneraliFormIo);

    // Lancio la funzione di gestione dei dati per la bozza (modifica per riferimento)
    this.aggiornaDatiBozzaGenerali(datiGeneraliUpdated, jsonDataIstanza);

    // Ritorno l'oggetto aggiornato
    return datiGeneraliUpdated;
  }

  /**
   * Funzione che gestisce le informazioni per i dati generali.
   * Questa funzione aggiorna le informazioni creando la struttura per gestire correttamente i dati per la bozza dell'istanza.
   * La funzione modificherà i dati per riferimento.
   * @param orientamentoData IOrientamentoDerivazioni con i dati generali da gestire.
   * @param jsonDataIstanza any con le informazioni del json_data.
   */
  aggiornaDatiBozzaGenerali(
    datiGeneraliFormIo: IQuadroDatiGeneraliDER,
    jsonDataIstanza: any
  ) {
    // Verifico l'input
    if (!datiGeneraliFormIo || !jsonDataIstanza) {
      // Manca la configurazione
      return;
      // #
    }

    // Gestisco tutto tramite un try catch perché la struttura dati NON E' STABILE, quindi per sicurezza gestisco le logiche
    try {
      // Tento di recuperare le informazioni relative ai dati nel quadro config per i dati generali
      const qdrConfigDatiGen: IQdrConfig_QdrDerDatiGen = {
        ...jsonDataIstanza.QDR_CONFIG.QDR_DER_DATIGEN,
      };

      // Verifico se esiste già la proprietà per la gestione delle informazioni per i dati istanza
      if (datiGeneraliFormIo.JS_DATI_ISTANZA) {
        // Esiste già una configurazione con le informazioni, non tocco le informazioni presenti
        return;
      }

      // La configurazione non esiste ancora, recupero le informazioni specifiche dal quadro config/quadro der dati gen impostati nell'orientamento
      const flg_opere_fisse: boolean = qdrConfigDatiGen.flg_opere_fisse;
      const procedura_semplificata: boolean =
        qdrConfigDatiGen.procedura_semplificata;
      const uso_esclusivo_domestico: boolean =
        qdrConfigDatiGen.uso_esclusivo_domestico;
      const uso_geotermico_max20l_s: boolean =
        qdrConfigDatiGen.uso_geotermico_max20l_s;
      const tipo_concessione: string = qdrConfigDatiGen.tipo_concessione;
      // Per la proprietà dei dati JS istanza, vado a creare un oggetto con le informazioni dell'orientamento estratte
      datiGeneraliFormIo.JS_DATI_ISTANZA = {
        flg_opere_fisse,
        procedura_semplificata,
        uso_esclusivo_domestico,
        uso_geotermico_max20l_s,
        tipo_concessione
      };
      // #
    } catch (e) {
      // Scrivo un log per informare del fallimento della logica
      const t: string = `aggiornaDatiBozzaGenerali | Update bozza data failed`;
      const b: any = { error: e };
      this._logger.warning(t, b);
      // #
    }
  }

  // #endregion "GESTIONE DATI GENERALI"
}
