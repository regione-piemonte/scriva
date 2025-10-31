/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import {
  formioStringNumberGreaterThanZero,
  importoITAToJsFloat,
} from '../../scriva-utilities/scriva-utilities.functions';
import { ICheckMinimoMassimo } from './utilities/formio-quadri.interfaces';

/**
 * Funzione di check che verifica i controlli per il campo minimo massimo.
 * @param params ICheckMinimoMassimo con le informazioni per effettuare i controlli.
 * @returns boolean con i risultati dei controlli.
 */
export const qdrCheckMinimoMassimo = (params: ICheckMinimoMassimo): boolean => {
  // Recupero i parametri dall'input
  const minimoParam: string = params?.minimo;
  const massimoParam: string = params?.massimo;
  const checkZero: boolean = params?.checkZero ?? false;
  const massimoNotZero: boolean = params?.massimoNotZero ?? false;

  // Verifico se è necessario il controllo sul valore zero
  if (checkZero) {
    // Il controllo sul valore zero è necessario
    const greaterZero: boolean = formioStringNumberGreaterThanZero(minimoParam);
    // Verifico se il controllo è fallito
    if (!greaterZero) {
      // Controllo è fallito
      return false;
      // #
    }
    // #
  }

  // Le informazioni sono stringhe, devo sostituire il separatore decimale
  const minimo = importoITAToJsFloat(minimoParam);
  const massimo = importoITAToJsFloat(massimoParam);
  // Verifico che siano numeri validi
  const existPMed = minimo != undefined;
  let existPMax;
  // Verifico il controllo specifico sulla check del valore massimo x il valore 0
  if (massimoNotZero) {
    // Effettuo il controllo completo
    existPMax = massimo != undefined && massimo !== 0;
    // #
  } else {
    // Effettuo il controllo minimo
    existPMax = massimo != undefined;
    // #
  }

  const existPortate = existPMed && existPMax;
  // Devono esistere entrambe le portate per la verifica
  // La portata media deve essere minore/uguale alla massima
  if (!existPortate || minimo <= massimo) {
    // Validazione completa
    return true;
    // #
  } else {
    // Validazione fallita, ritorno un "codice errore"
    return false;
  }
};
