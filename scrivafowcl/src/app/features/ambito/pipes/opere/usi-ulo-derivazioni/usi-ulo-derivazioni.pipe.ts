/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Pipe, PipeTransform } from '@angular/core';
import {
  OggettoIstanzaGeometrieFE,
  OggettoIstanzaLike,
  SoggettoIstanza,
  UbicazioneOggettoIstanza,
} from '../../../models';
import { generaDenominazioneAzienda } from '../../../pages/presentazione-istanza/usi-ulo-derivazioni/utilities/usi-ulo-derivazioni.functions';

// #region "OGGETTO ISTANZA"

/**
 * Pipe dedicata alla opere.
 */
@Pipe({ name: 'opereCFAziendaOggettoIstanza' })
export class OpereCFAziendaOggettoIstanzaPipe implements PipeTransform {
  /**
   * Costruttore del Pipe.
   */
  constructor() {}

  /**
   * Funzione che gestisce le informazioni di una riga per la tabella degli oggetti-istanza.
   * @param riga OggettoIstanzaGeometrieFE con le informazioni della riga di tabella passata dalla libreria ngx-datatable.
   * @returns string con il codice fiscale.
   */
  transform(riga: OggettoIstanzaGeometrieFE): string {
    // Definisco un contenitore per il soggetto
    let soggetto: SoggettoIstanza;

    // Uso un try catch di sicurezza
    try {
      // Recupero dalla lista dei soggetti il primo (e tecnicamente unico, come da scambio con @Alessandro Verner) soggetto
      soggetto = riga?.soggettiGeometrie[0];
      // #
    } catch (e) {}

    // Ci sono le informazioni, cerco di generare una descrizione
    return soggetto?.cf_soggetto;
  }
}

/**
 * Pipe dedicata alla opere.
 */
@Pipe({ name: 'opereDenominazioneAziendaOggettoIstanza' })
export class OpereDenominazioneAziendaOggettoIstanzaPipe
  implements PipeTransform
{
  /**
   * Costruttore del Pipe.
   */
  constructor() {}

  /**
   * Funzione che gestisce le informazioni di una riga per la tabella degli oggetti-istanza.
   * @param riga OggettoIstanzaGeometrieFE con le informazioni della riga di tabella passata dalla libreria ngx-datatable.
   * @returns string con la descrizione formattata.
   */
  transform(riga: OggettoIstanzaGeometrieFE): string {
    // Definisco un contenitore per il soggetto
    let soggetto: SoggettoIstanza;

    // Uso un try catch di sicurezza
    try {
      // Recupero dalla lista dei soggetti il primo (e tecnicamente unico, come da scambio con @Alessandro Verner) soggetto
      soggetto = riga?.soggettiGeometrie[0];
      // #
    } catch (e) {}

    // Ci sono le informazioni, cerco di generare una descrizione
    return generaDenominazioneAzienda(soggetto);
  }
}

// #region "OGGETTO ISTANZA"
