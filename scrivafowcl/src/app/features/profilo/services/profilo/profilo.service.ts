/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { clone, cloneDeep } from 'lodash';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Compilante, IFunzionario } from '../../../../shared/models';
import {
  AppConfigService,
  AuthStoreService,
} from '../../../../shared/services';
import { ScrivaUtilitiesService } from '../../../../shared/services/scriva-utilities/scriva-utilities.service';
import { IPreferenzaNotifica } from '../../models/preferenze-notifiche.model';
import { IDatiProfilo } from '../../pages/profilo/utilities/profilo.interfaces';

@Injectable({ providedIn: 'root' })
export class ProfiloService {
  /** String che definsce il path per il servizio: /compilanti. */
  private PATH_COMPILANTI = '/compilanti';
  /** String che definsce il path per il servizio: /preferenze-notifiche. */
  private PATH_PREFERENZE_NOTIFICHE = '/preferenze-notifiche';

  /** IPreferenzaNotifica[] contenente le configurazioni per le preferenze di notifica dell'utente. */
  private _preferenzeNotifiche: IPreferenzaNotifica[];

  /**
   * Costruttore.
   */
  constructor(
    private _authStore: AuthStoreService,
    private _config: AppConfigService,
    private _http: HttpClient,
    private _scrivaUtilities: ScrivaUtilitiesService
  ) {}

  /**
   * ###############################
   * RECUPERO INFORMAZIONI NOTIFICHE
   * ###############################
   */

  /**
   * Funzione che invoca il servizio per il recupero delle preferenze notifiche dell'utente.
   * @param idCompilante number con l'id del compilante per il recupero delle configurazioni.
   * @returns Observable<IPreferenzaNotifica[]> con le informazioni per le preferenze.
   */
  getPreferenzeNotifiche(
    idCompilante: number
  ): Observable<IPreferenzaNotifica[]> {
    // Creo l'url per l'invocazione dell'api
    const url = this._config.appUrl(
      this.PATH_COMPILANTI,
      this.PATH_PREFERENZE_NOTIFICHE
    );
    // Definisco i query params
    const params = this._scrivaUtilities.createQueryParams({
      id_compilante: idCompilante,
    });

    // Richiamo il servizio
    return this._http.get<IPreferenzaNotifica[]>(url, { params }).pipe(
      tap((preferenzeNotifiche: IPreferenzaNotifica[]) => {
        // Salvo locamente le informazioni delle preferenze notifiche
        this._preferenzeNotifiche = preferenzeNotifiche;
      })
    );
  }

  /**
   * Funzione che si occupa di aggiornare le informazioni per le preferenze notifiche.
   * @param preferenzeNotifiche IPreferenzaNotifica[] con la lista delle preferenze d'aggiornare.
   * @returns Observable<IPreferenzaNotifica[]> con l'array aggiornato dal server.
   */
  putPreferenzeNotifiche(preferenzeNotifiche: IPreferenzaNotifica[]) {
    // Creo l'url per l'invocazione dell'api
    const url = this._config.appUrl(
      this.PATH_COMPILANTI,
      this.PATH_PREFERENZE_NOTIFICHE
    );

    // Richiamo il servizio d'aggiornamento dati
    return this._http.put<IPreferenzaNotifica[]>(url, preferenzeNotifiche).pipe(
      tap((preferenzeNotifiche: IPreferenzaNotifica[]) => {
        // Salvo localmente le informazion
        this._preferenzeNotifiche = preferenzeNotifiche;
      })
    );
  }

  /**
   * ########################################
   * RECUPERO INFORMAZIONI PER PROFILO UTENTE
   * ########################################
   */

  /**
   * Funzione di supporto per il set dati all'interno di un form.
   * La funzione recupererà i dati del: compilante; per valorizzare il form.
   * @returns IDatiProfilo con i dati recuperati per il set dell'anagrafica.
   */
  getProfiloCompilante(): IDatiProfilo {
    // Recupero il dato per la popolazione
    const compilante: Compilante = this.compilante;
    // Definisco l'oggetto di configurazione per i dati profilo
    const datiProfilo: IDatiProfilo = {
      codiceFiscale: compilante?.cf_compilante,
      cognome: compilante?.cognome_compilante,
      nome: compilante?.nome_compilante,
      email: compilante?.des_email_compilante,
      telefono: undefined, // Ancora non esiste nel modello dati
    };

    // Imposto le informazioni per i campi del form
    return datiProfilo;
  }

  /**
   * Funzione di supporto per il set dati all'interno di un form.
   * La funzione recupererà i dati del: funzionario; per valorizzare il form.
   * @returns IDatiProfilo con i dati recuperati per il set dell'anagrafica.
   */
  getProfiloFunzionario(): IDatiProfilo {
    // Recupero il dato per la popolazione
    const funzionario: IFunzionario = this.funzionario;
    // Definisco l'oggetto di configurazione per i dati profilo
    const datiProfilo: IDatiProfilo = {
      codiceFiscale: funzionario?.cf_funzionario,
      cognome: funzionario?.cognome_funzionario,
      nome: funzionario?.nome_funzionario,
      email: funzionario?.des_email_funzionario,
      telefono: undefined, // Ancora non esiste nel modello dati
    };

    // Imposto le informazioni per i campi del form
    return datiProfilo;
  }

  /**
   * ###############
   * GETTER & SETTER
   * ###############
   */

  /**
   * Getter per i dati del compilante.
   * @returns Compilante con i dati definiti per la sessione.
   */
  get compilante(): Compilante {
    // Rirotno i dati del compilante
    return this._authStore.getCompilante();
  }

  /**
   * Getter per i dati del funzionario.
   * @returns Funzionario con i dati definiti per la sessione.
   */
  get funzionario(): IFunzionario {
    // Rirotno i dati del funzionario
    return this._authStore.getFunzionario();
  }

  /**
   * Getter di comodo per il recupero dati del profilo dell'utente connesso, in base al tipo componente in uso.
   * @returns IDatiProfilo con le informazioni per la sezione del proflo.
   */
  get profilo(): IDatiProfilo {
    // Verifico il tipo di componente applicativo
    if (this._authStore.isFrontOffice) {
      // Ritorno i dati del profilo per compilante
      return this.getProfiloCompilante();
      // #
    } else {
      // Ritorno i dati del profilo per funzionario
      return this.getProfiloFunzionario();
      // #
    }
  }

  /**
   * Getter per le preferenze notifiche scaricate per il compilante.
   * @returns IPreferenzaNotifica[] con le preferenze di notifica compilante.
   */
  get preferenzeNotifiche(): IPreferenzaNotifica[] {
    // Ritorno l'oggetto presente nel servizio
    return cloneDeep(this._preferenzeNotifiche);
  }
}
