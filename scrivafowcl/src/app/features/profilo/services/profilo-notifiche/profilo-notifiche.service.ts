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
import { IPreferenzaNotifica } from '../../models/preferenze-notifiche.model';
import { PROFILO_NOTIFICHE_CONSTS } from '../../pages/profilo-notifiche/utilities/profilo-notifiche.consts';
import { PROFILO_CONSTS } from '../../pages/profilo/utilities/profilo.consts';
import { ProfiloService } from '../profilo/profilo.service';

@Injectable({ providedIn: 'root' })
export class ProfiloNotificheService {
  /** Costante contenente le informazioni costanti del componente. */
  private P_C = PROFILO_CONSTS;
  private PN_C = PROFILO_NOTIFICHE_CONSTS;

  /**
   * Costruttore.
   */
  constructor(private _profilo: ProfiloService) {}

  /**
   * Funzione che si occupa di aggiornare le informazioni per le preferenze notifiche.
   * @param preferenzeNotifiche IPreferenzaNotifica[] con la lista delle preferenze d'aggiornare.
   * @returns Observable<IPreferenzaNotifica[]> con l'array aggiornato dal server.
   */
  aggiornaPreferenzeNotifiche(preferenzeNotifiche: IPreferenzaNotifica[]) {
    // Verifico l'input
    preferenzeNotifiche = preferenzeNotifiche ?? [];
    // Recupero dal servizio di autenticazione il compilante
    const compilante = this._profilo.compilante;
    // Aggiorno all'interno dell'array il dato del compilante (potrebbe mancare in caso in cui non ci siano mai state configurazioni precedenti)
    preferenzeNotifiche = preferenzeNotifiche.map((pn: IPreferenzaNotifica) => {
      // Aggiorniamo la proprietà del compilante
      pn.compilante = compilante;
      // Ritorno l'oggetto aggiornato
      return pn;
    });

    // Ritorno la chiamata al servizio di update delle notifiche
    return this._profilo.putPreferenzeNotifiche(preferenzeNotifiche);
  }
}
