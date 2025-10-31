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
import { ConfigRuoloAdempimento } from '../../../features/ambito/models';
import { RuoloCompilante } from '../../models';

@Injectable({ providedIn: 'root' })
export class SoggettiService {
  /**
   * Costruttore.
   */
  constructor() {}

  /**
   * Funzione che gestisce le logiche per definire i ruoli compilante per una persona di tipo: PF.
   * @param adempimentoConfigs ConfigRuoloAdempimento[] con le configurazioni dei ruoli adempimento contente le configurazioni per la gestione dei ruoli.
   * @param stessoTipoSoggettoAttoreInLinea boolean che definisce se il tipo persona ricercato è lo stesso dell'attore in linea.
   * @returns RuoloCompilante[] con la lista di elementi per il tipo persona.
   */
  ruoliCompilantePF(
    adempimentoConfigs: ConfigRuoloAdempimento[],
    stessoTipoSoggettoAttoreInLinea: boolean
  ): RuoloCompilante[] {
    // Verifico l'input
    if (!adempimentoConfigs) {
      // Manca la configurazione
      return [];
    }

    // Filtro i dati gestendo i flag della configurazione
    return adempimentoConfigs
      .filter((config: ConfigRuoloAdempimento) => {
        // Verifico se il check su stessoTipoSoggettoAttoreInLinea
        if (stessoTipoSoggettoAttoreInLinea) {
          // Soggetto e attore in linea sono gli stessi, ritorno il flag specifico
          return config.flg_ruolo_default === true;
          // #
        } else {
          // RISCA-1202 | Insieme a @Barbara usiamo questo flag per popolare le voci del ruolo compilante. Il flag ha un nome "errato", ma per evitare refactor per il momento lo usiamo. Il flag non è usato da nessuna parte, quindi lo accettiamo così.
          // Soggetto e attore in linea sono diversi, ritorno il flag specifico
          return config.flg_modulo_procura === true;
          // #
        }
      })
      .map((config: ConfigRuoloAdempimento) => {
        // Ritorno gli oggetti dei ruoli
        return config.ruolo_compilante;
      });
  }

  /**
   * Funzione che gestisce le logiche per definire i ruoli compilante per una persona di tipo: PG.
   * @param adempimentoConfigs ConfigRuoloAdempimento[] con le configurazioni dei ruoli adempimento contente le configurazioni per la gestione dei ruoli.
   * @returns RuoloCompilante[] con la lista di elementi per il tipo persona.
   */
  ruoliCompilantePG(
    adempimentoConfigs: ConfigRuoloAdempimento[]
  ): RuoloCompilante[] {
    // Verifico l'input
    if (!adempimentoConfigs) {
      // Manca la configurazione
      return [];
    }

    // Filtro i dati gestendo i flag della configurazione
    return adempimentoConfigs.map((config: ConfigRuoloAdempimento) => {
      // Ritorno gli oggetti dei ruoli
      return config.ruolo_compilante;
    });
  }

  /**
   * Funzione che gestisce il completamento degli oggetti di configurazione ruolo_compilante all'interno della lista in input di tipo: ConfigRuoloAdempimento[].
   * @param adempimentoConfigs ConfigRuoloAdempimento[] con la lista d'aggiornare.
   * @returns ConfigRuoloAdempimento[] con la lista aggiornata.
   */
  completaRuoliCompilantiInRuoliAdempimentiConfigs(
    adempimentoConfigs: ConfigRuoloAdempimento[]
  ): ConfigRuoloAdempimento[] {
    // Verifico l'input
    if (!adempimentoConfigs) {
      // Manca la configurazione
      return [];
    }

    // Lancio un map sull'array
    return adempimentoConfigs.map((config: ConfigRuoloAdempimento) => {
      // Verifico esista la configurazione per il ruolo compilante
      if (config.ruolo_compilante) {
        // Aggiorno i flag all'interno del ruolo compilante
        config.ruolo_compilante.flg_modulo_delega =
          config.flg_modulo_delega ?? false;
        config.ruolo_compilante.flg_modulo_procura =
          config.flg_modulo_procura ?? false;
      }
      // Ritorno l'oggetto aggiornato
      return config;
    });
  }
}
