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
import {
  FunzionarioProfilo,
  IFunzionario,
  IFunzionarioAutorizzato,
  TipoAdempimento,
  TipoAdempiProfilo,
} from '../../models';
import { AuthStoreService } from './auth-store.service';

@Injectable({ providedIn: 'root' })
export class FunzionarioService {
  /**
   * Costruttore.
   */
  constructor(private _authStore: AuthStoreService) {}

  /**
   * ########################################
   * VERIFICA CONSULTATORE x TIPO ADEMPIMENTO
   * ########################################
   */

  // #region "VERIFICA CONSULTATORE x TIPO ADEMPIMENTO"

  /**
   * Funzione che verifica rispetto alle configurazioni del funzionario autorizzato se questo è un CONSULTATORE rispetto al tipo adempimento passato in input.
   * @param tipoAdempimento TipoAdempimento da verificare se risulta in sola consultazione.
   * @returns boolean che indica se l'utente è CONSULTATORE [true] o può inserire istanze [false].
   */
  verificaConsultatoreByTipoAdempimento(
    tipoAdempimento: TipoAdempimento
  ): boolean {
    // Verifico l'input
    if (!tipoAdempimento) {
      // Manca la configurazione, non lo considero come consultatore
      return true;
      // #
    }

    // Recupero il funzionario dalla sessione di servizio
    const datiFunzionario: IFunzionarioAutorizzato = this.funzionario;
    // Estraggo dai dati del funzionario le informazioni del profilo specifico
    const profiloFunzionario: FunzionarioProfilo =
      this.profiloFunzionario(datiFunzionario);

    // Verifico se ho trovato il profilo funzionario
    if (!profiloFunzionario) {
      // Non l'ho trovato, non lo considero come consultatore
      return true;
      // #
    }
    // Ho trovato il profilo estratto le configurazioni per i tipi adempimenti
    const tipiAdempimentiProfili: TipoAdempiProfilo[] =
      profiloFunzionario.tipo_adempi_profilo;

    // Richiamo la funzione che mi estra il flag di sola lettura e quindi di sola consultazione per gli adempimenti
    let isTipoProfiloAdempimentoSolaLettura: boolean;
    isTipoProfiloAdempimentoSolaLettura =
      this.isTipoProfiloAdempimentoSolaLettura(
        tipoAdempimento,
        tipiAdempimentiProfili
      );
    // Verifico se il risultato che ho ottenuto è undefined
    if (isTipoProfiloAdempimentoSolaLettura === undefined) {
      // Non ho trovato la configurazione, non lo considero come consultatore
      return true;
      // #
    } else {
      // Ho trovato la configurazione, ritorno il suo valore come configurazione specifica
      return isTipoProfiloAdempimentoSolaLettura;
      // #
    }
    // #
  }

  /**
   * Funzione che estrae l'oggetto FunzionarioProfilo di riferimento da un oggetto IFunzionarioAutorizzato.
   * @param datiFunzionario IFunzionarioAutorizzato dalla quale estrarre le informazioni.
   * @returns FunzionarioProfilo con le informazioni estratte.
   */
  private profiloFunzionario(
    datiFunzionario: IFunzionarioAutorizzato
  ): FunzionarioProfilo {
    // Verifico l'input
    if (!datiFunzionario) {
      // Mancano le informazioni di input
      return undefined;
      // #
    }

    // Estraggo dall'input le informazioni
    let funzionario: IFunzionario;
    funzionario = datiFunzionario.funzionario;
    let funzionario_profilo: FunzionarioProfilo[];
    funzionario_profilo = datiFunzionario.funzionario_profilo ?? [];

    // Recupero dal funzionario l'id per il recupero dell'oggetto del profilo corretto
    const idFunzionario: number = funzionario?.id_funzionario;
    // Cerco all'interno della lista dei profili per stesso id funzionario
    const profilo: FunzionarioProfilo = funzionario_profilo.find(
      (p: FunzionarioProfilo) => {
        // Verifico se l'id del funziona è il medesimo
        return p?.id_funzionario === idFunzionario;
        // #
      }
    );

    // Ritorno il profilo
    return profilo;
    // #
  }

  /**
   * Funzione che estrae il tipo profilo adempimento dato un tipo adempimento.
   * I profili adempimento contengono una serie d'informazioni sul tipo di configurazione del tipo adempimento.
   * Queste configurazioni permettono di sapere, per esempio, se l'utente può effettuare determinate azioni di scrittura/lettura sul un determinato adempimento.
   * @param tipoAdempimento TipoAdempimento da ricercare tra la lista dei profili adempimento.
   * @param profiliTipiAdempimento TipoAdempiProfilo[] con la lista dei profili per la ricerca delle configurazioni.
   * @returns TipoAdempiProfilo con la configurazione se viene trovata per il tipo adempimento richiesto.
   */
  private tipoProfiloAdempimento(
    tipoAdempimento: TipoAdempimento,
    profiliTipiAdempimento: TipoAdempiProfilo[]
  ): TipoAdempiProfilo {
    // Verifico l'input
    if (!tipoAdempimento || !profiliTipiAdempimento) {
      // Mancano le configurazioni minime
      return undefined;
      // #
    }

    // Cerco all'interno dei profili per stesso id tipo adempimento
    let profiloAdempimento: TipoAdempiProfilo;
    profiloAdempimento = profiliTipiAdempimento.find(
      (pta: TipoAdempiProfilo) => {
        // Recupero le informazioni per l'id tipo adempimento degli oggetti
        const idPTA: number = pta.id_tipo_adempimento;
        const idTA: number = tipoAdempimento.id_tipo_adempimento;
        // Verifico per stesso id tipo adempimento
        return idPTA === idTA;
        // #
      }
    );

    // Ritorno l'oggetto del profilo adempimento
    return profiloAdempimento;
    // #
  }

  /**
   * Funzione che estrae dal tipo profilo adempimento, dato un tipo adempimento, se la configurazione è di sola lettura.
   * La funzione si basa sulle logiche della funzione "tipoProfiloAdempimento" e ne legge la configurazione del flag "flg_sola_lettura".
   * @param tipoAdempimento TipoAdempimento da ricercare tra la lista dei profili adempimento.
   * @param profiliTipiAdempimento TipoAdempiProfilo[] con la lista dei profili per la ricerca delle configurazioni.
   * @returns boolean che indica se il tipo adempimento è di sola lettura per il profilo utente.
   */
  private isTipoProfiloAdempimentoSolaLettura(
    tipoAdempimento: TipoAdempimento,
    profiliTipiAdempimento: TipoAdempiProfilo[]
  ): boolean {
    // Lancio la funzione per il recupero della configurazione
    let tipoProfiloAdempimento: TipoAdempiProfilo;
    tipoProfiloAdempimento = this.tipoProfiloAdempimento(
      tipoAdempimento,
      profiliTipiAdempimento
    );

    // Verifico se esiste una configurazione
    if (tipoProfiloAdempimento) {
      // La configurazione esiste, estraggo la variabile
      const solaLettura: boolean = tipoProfiloAdempimento.flg_sola_lettura;
      // Ritorno il flag di sola lettura
      return solaLettura;
      // #
    } else {
      // Ritorno l'oggetto undefined
      return undefined;
      // #
    }
    // #
  }

  // #endregion "VERIFICA CONSULTATORE x TIPO ADEMPIMENTO"

  /**
   * ###############
   * GETTER & SETTER
   * ###############
   */

  // #region "GETTER & SETTER"

  /**
   * Getter di comodo per il recupero del funzionario autorizzato.
   * @returns IFunzionarioAutorizzato con il funzionario in sessione.
   */
  get funzionario(): IFunzionarioAutorizzato {
    // Recupero dal servizio il funzionario autorizzato
    return this._authStore.getFunzionarioAutorizzato();
    // #
  }

  // #endregion "GETTER & SETTER"
}
