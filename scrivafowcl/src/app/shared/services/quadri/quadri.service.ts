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
import { IQuadroDettOggConfigs } from './utilities/quadri.interfaces';
import { CodiciTipiQuadro } from '../../enums/tipo-quadro.enums';

/**
 * Servizio di utility per il componente scriva che gestisce i dati dei quadri
 */
@Injectable({ providedIn: 'root' })
export class QuadriService {
  /** String costante che definisce la KEY WORD applicativa specifica la proprietà per l'id oggetto istanza. */
  private KEY_ID_OGGETTO_ISTANZA = 'id_oggetto_istanza';
  /** String costante che definisce la KEY WORD applicativa specifica la proprietà per l'id oggetto. */
  private KEY_ID_OGGETTO = 'id_oggetto';
  /** String costante che definisce la KEY WORD applicativa specifica per il quadro dettaglio oggetto. */
  private QDR_DETT_OGG = CodiciTipiQuadro.dettaglioOggetto;

  /**
   * Costruttore.
   */
  constructor() {}

  /**
   * #########################################################
   * FUNZIONI DI ARRICCHIMENTO DATI DIRETTAMENTE PER IL QUADRO
   * #########################################################
   */

  /**
   * Funzione che gestice, per riferimen, gli aggiornamenti per il quadro detteglio oggetto.
   * La funzioni interne specifiche definiranno le logiche di modifica dell'oggetto.
   * @param quadri any contenente tutte le informazioni dei quadri dell'istanza, come oggetto che verrà poi convertito in JSON.
   * @param configs IQuadroDettOggConfigs con i dati di configure per l'aggiornamento della struttura del quadro.
   */
  handleQuadroDettOgg(quadri: any, configs: IQuadroDettOggConfigs) {
    // Verifico l'input
    if (!quadri || !configs) {
      // Non esistono configurazioni, blocco il flusso
      return;
    }

    // Recupero dalle configurazioni i parametri di modifica
    const { idOggettoIstanza, idOggetto } = configs;

    // Verifico se le informazioni del dettaglio oggetto esistono
    const existQdrDettOgg = quadri[this.QDR_DETT_OGG] != undefined;
    // Se esiste eseguo la modifica
    if (existQdrDettOgg) {
      // Aggiorno l'oggetto definendo l'id dell'oggetto istanza
      quadri[this.QDR_DETT_OGG][this.KEY_ID_OGGETTO_ISTANZA] = idOggettoIstanza;
      if (configs.idOggetto){
      // Aggiorno l'oggetto definendo l'identificativo dell'oggetto
      quadri[this.QDR_DETT_OGG][this.KEY_ID_OGGETTO] = idOggetto;
      }
    }
  }
}
