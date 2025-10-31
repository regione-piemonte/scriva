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
import { ConfigElement, ICriteriRicercaQdrOggetto } from '../../../models';
import { UsiUloDerivazioniConsts } from '../../../pages/presentazione-istanza/usi-ulo-derivazioni/utilities/usi-ulo-derivazioni.consts';
import { IConfigsElementCfAzienda } from './utilities/usi-ulo-derivazioni.interfaces';

@Injectable()
export class UsiUloDerivazioniService {
  /** UsiUloDerivazioniConsts con le informazioni costanti per il componente. */
  public OPERE_CONSTS = new UsiUloDerivazioniConsts();

  /**
   * Costruttore.
   */
  constructor() {}

  /**
   * Funzione che recupera la configurazione per i campi del form ricerca oggetti.
   * Utilizza la configurazione del campo del codice fiscale per la gestione delle informazioni.
   * @returns IConfigsElementCfAzienda con le configurazioni generate.
   */
  initConfigInputCFAzienda(
    criteriRicerca: ICriteriRicercaQdrOggetto
  ): IConfigsElementCfAzienda {
    // Recupero la configurazione per il codice fiscale
    const cfSoggetto: ConfigElement = criteriRicerca?.cf_soggetto;
    // Inizializzo con la configurazione
    return this.initConfigInputCFAziendaByConfig(cfSoggetto);
  }

  /**
   * Funzione che inizializza le informazioni per la configurazione per la gestione delle input relative al codice fiscale azienda.
   * Le configurazioni si baseranno sull'oggetto di input.
   * @param cfSoggetto ConfigElement con le informazioni per la configurazione delle input.
   * @returns IConfigsElementCfAzienda con le configurazioni generate.
   */
  initConfigInputCFAziendaByConfig(
    cfSoggetto: ConfigElement
  ): IConfigsElementCfAzienda {
    // Verifico l'input
    if (!cfSoggetto) {
      // Manca la configurazione
      return this.initConfigInputCFAziendaDefault();
      // #
    }

    // Associo localmente la configurazione "rimuovendo" il riferimento all'oggetto
    const cfAziendaConfig: ConfigElement = { ...cfSoggetto };
    // Partendo dai dati del cfAziendale, gestisco anche quella della descrzione
    const denAziendaConfig: ConfigElement = { ...cfAziendaConfig };

    // Definisco la variabile che conterrà la label corretta per la denominazione azienda
    let labelDenominazioneAzienda: string = `${this.OPERE_CONSTS.DENOMINAZIONE}`;
    // Recupero la label per la descrizione azienda
    let labelDesAziendale: string = denAziendaConfig?.label ?? '';
    // Forzo un tolower case per una verifica successiva
    labelDesAziendale = labelDesAziendale.toLowerCase();
    // Verifico se all'interno della descrizione è effettivamente presente la parola "azienda"
    const azienda: string = this.OPERE_CONSTS.AZIENDA;
    const labelContainsAzienda: boolean = labelDesAziendale.includes(azienda);
    // Verifico la condizione
    if (labelContainsAzienda) {
      // C'è la parola, concateno la descrizione
      labelDenominazioneAzienda = `${labelDenominazioneAzienda} ${azienda}`;
      // #
    }
    // Assegno la nuova label all'oggetto di configurazione
    denAziendaConfig.label = labelDenominazioneAzienda;
    // #

    // Ritorno le configurazioni
    return { cfAziendaConfig, denAziendaConfig };
  }

  /**
   * Funzione che inizializza le informazioni per la configurazione per la gestione delle input relative al codice fiscale azienda.
   * Le configurazioni si baseranno su oggetti gestiti FE.
   * @param cfSoggetto ConfigElement con le informazioni per la configurazione delle input.
   * @returns IConfigsElementCfAzienda con le configurazioni generate.
   */
  initConfigInputCFAziendaDefault(): IConfigsElementCfAzienda {
    // Definisco un oggetto di default per la gestione dell'input del codice fiscale
    const cfAziendaConfig: ConfigElement = {
      label: this.OPERE_CONSTS.CODICE_FISCALE,
    };
    // Partendo dai dati del cfAziendale, gestisco anche quella della descrzione
    const denAziendaConfig: ConfigElement = {
      label: this.OPERE_CONSTS.DENOMINAZIONE,
    };

    // Ritorno le configurazioni
    return { cfAziendaConfig, denAziendaConfig };
  }
}
