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
import * as moment from 'moment';
import { Moment } from 'moment';
import { IstanzaOss, Osservazione } from '../../interfaces/osservazione';

/**
 * Funzione che verifica se l'osservazione è in stato bozza.
 * @param osservazione Osservazione con l'oggetto contenente i dati dell'osservazione.
 * @returns boolean con il check di verifica.
 */
const checkIsBozza = (osservazione: Osservazione): boolean => {
  // Cerco di estrarre il codice stato osseravzione dall'oggetto
  const codStOsservazione: string =
    osservazione?.stato_osservazione?.cod_stato_osservazione ?? '';
  // Verifico se il codice stato osservazione è "bozza"
  const isObsInBozza: boolean =
    codStOsservazione.toLocaleLowerCase() === 'bozza';

  // Ritorno la verifica
  return isObsInBozza;
};

/**
 * Funzione che verifica se l'osservazione è antecedente alla data di oggi.
 * @param osseravzione Osservazione con le informazioni per la verifica sulla data di fine osservazione.
 * @returns boolean con il risultato del check.
 */
const checkDataFineOsservazione = (osservazione: Osservazione): boolean => {
  // Recupero dall'osservazione l'oggetto dell'istanza
  const istanza: IstanzaOss = osservazione?.istanza;

  /**
   * @author Ismaele Bottelli
   * @date 27/01/2025
   * @jira SCRIVA-1582
   * @notes Mantengo il vecchio codice perché è scritto male e non so se possano esserci problemi con il refactor.
   */
  // return !(
  //   !istanza.data_fine_osservazione ||
  //   new Date(
  //     new Date(istanza.data_fine_osservazione).setHours(0, 0, 0, 0)
  //   ).getTime() < new Date(new Date().setHours(0, 0, 0, 0)).getTime()
  // );

  // Recupero poi la data per la fine osservazione
  const dtFineOss: string = istanza?.data_fine_osservazione;
  // Verifico se la data fine osservazione è valida
  if (!dtFineOss) {
    // La data non è valida
    return false;
    // #
  }

  // La data esiste, provo a convertirla in un oggetto Date
  const dataFineOss: Date = new Date(dtFineOss);
  // Creo un moment per gestire meglio la verifica
  const mDataFineOss: Moment = moment(dataFineOss);
  // Creo un moment per la giornata di oggi
  const mOggi: Moment = moment();

  // Verifico che la data fine osservazione non è ancora passata
  return mOggi.isBefore(mDataFineOss);
};

@Pipe({ name: 'cancellaOsservazioneAttiva' })
export class CancellaOsservazionetAttivaPipe implements PipeTransform {
  /**
   * Costruttore.
   */
  constructor() {}

  /**
   * Funzione che verifica le informazioni per l'osservazione e ritorna un flag che indica se la funzione di "cancellazione" è attiva.
   * @param osservazione Osservazione con l'oggetto dell'osservazione da verificare.
   * @returns boolean con il flag che definisce se l'osservazione è cancellabile.
   */
  transform(osservazione: Osservazione): boolean {
    // Verifico se la data fine osservazione risulta nel futuro rispetto a oggi
    const isDtFineOssFutura: boolean = checkDataFineOsservazione(osservazione);
    // Verifico se l'osservazione è in bozza
    const isOssInBozza: boolean = checkIsBozza(osservazione);

    // Verifico se devo controllare lo stato in bozza
    return isDtFineOssFutura && isOssInBozza;
  }
}

@Pipe({ name: 'modificaOsservazioneAttiva' })
export class ModificaOsservazionetAttivaPipe implements PipeTransform {
  /**
   * Costruttore.
   */
  constructor() {}

  /**
   * Funzione che verifica le informazioni per l'osservazione e ritorna un flag che indica se la funzione di "modifica" è attiva.
   * @param osservazione Osservazione con l'oggetto dell'osservazione da verificare.
   * @returns boolean con il flag che definisce se l'osservazione è editabile.
   */
  transform(osservazione: Osservazione): boolean {
    // Verifico se la data fine osservazione risulta nel futuro rispetto a oggi
    const isDtFineOssFutura: boolean = checkDataFineOsservazione(osservazione);
    // Verifico se l'osservazione è in bozza
    const isOssInBozza: boolean = checkIsBozza(osservazione);

    // Verifico se devo controllare lo stato in bozza
    return isDtFineOssFutura && isOssInBozza;
  }
}
