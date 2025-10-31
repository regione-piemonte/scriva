/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { FormControl, FormGroup, ValidationErrors } from '@angular/forms';
import { Moment } from 'moment';
import { ScrivaErrorsKeys } from '../../classes/scriva-input-errors/scriva-errors-keys.class';
import { IScrivaCheckboxData } from '../../components/form-inputs/form-input/utilities/form-input.interfaces';
import { ScrivaDatesFormat } from '../../enums/scriva-utilities/scriva-utilities.enums';
import { convertDateByType } from './scriva-utilities.functions';

/**
 * ####################################
 * FUNZIONI DI UTILITY PER I VALIDATORI
 * ####################################
 */

/**
 * Funzione di supporto che genera l'oggetto di errore da ritornare al form.
 * @param key string che definisce la chiave dell'errore.
 * @param value any che definisce il value per la chiave dell'errore.
 * @returns any contenente le configurazioni degli errori.
 */
export const generaObjErrore = (
  key: string = 'NO_KEY',
  value: any = true
): any => {
  // Genero un oggetto che conterrà le informazioni dell'errore
  const o = {};
  // Aggiungo la chiave all'oggetto
  o[key] = value;
  // Ritorno l'oggetto
  return o;
};

/**
 * #############################
 * VALIDATORI CUSTOM PER LE FORM
 * #############################
 */

/**
 * Form validator specifico per gestire il valore delle scriva-checkbox.
 * Il validatore verificherà che il valore definito per il form control della checkbox sia: true.
 * @returns ValidationErrors contenente le informazioni sull'errore.
 */
export const scrivaCheckboxTrue = () => {
  // Come struttura di un validatore personalizzato, viene ritornata una funzione che gestisce le logiche di controllo
  return (formControl: FormControl): ValidationErrors | null => {
    // Verifico che il formControl esista
    if (!formControl) {
      return;
    }
    // Definisco la costante contenente le chiavi d'errore per i forms validators
    const S_EK = ScrivaErrorsKeys;

    // Recupero il valore
    const checkObj: IScrivaCheckboxData = formControl.value;
    // Verifico se esiste l'oggetto
    if (!checkObj) {
      // Non esiste l'oggetto, lo considero invalido
      const key = S_EK.SCRIVA_CHECKBOX_NOT_TRUE;
      // Genero e ritorno l'errore al form group
      return generaObjErrore(key);
    }

    // Recupero il valore della checkbox dalla configurazione
    const check: boolean = checkObj.check;
    // Verifico se il valore è false
    if (!check) {
      // Flag false, il valore è invalido
      const key = S_EK.SCRIVA_CHECKBOX_NOT_TRUE;
      // Genero e ritorno l'errore al form group
      return generaObjErrore(key);
    }

    // Rimuovo eventuali errori
    return null;
  };
};

/**
 * Verifica che dataStartFormControlName risulti precedente a dataEndFormControlName.
 * Il dato del campo deve essere definito come oggetto NgbDateStruct.
 * @param dataStartFormControlName string che definisce il nome del formControl della data che deve risultare antecendente.
 * @param dataEndFormControlName string che definisce il nome del formControl della data che deve risultare successiva.
 * @param dateFormat ScrivaDatesFormat con la formattazione della data recuperata dal form control.
 * @param lessOrEqual boolean che definisce se la data di partenza può essere anche uguale a quella di fine
 * @returns ValidationErrors con la mappatura dell'errore se il validator non soddisfa le condizioni.
 */
export const dataInizioDataFineValidator = (
  dataStartFormControlName: string,
  dataEndFormControlName: string,
  dateFormat: ScrivaDatesFormat,
  lessOrEqual: boolean = false
) => {
  return (formGroup: FormGroup): ValidationErrors | null => {
    // Verifico che il formControl esista
    if (!formGroup) {
      return;
    }
    /** Definisco la costante contenente le chiavi d'errore per i forms validators. */
    const ERR_KEY = ScrivaErrorsKeys;

    // Variabili di comodo
    const f = formGroup;
    const dsFCN = dataStartFormControlName;
    const deFCN = dataEndFormControlName;
    let ds: any;
    let de: any;

    // Recupero i valori dei form control dal FormGroup
    ds = formGroup.get(dsFCN)?.value ?? null;
    de = formGroup.get(deFCN)?.value ?? null;

    // Verifico che i campi esistano
    if (!ds || !de) {
      // Non si può fare il check
      return null;
    }

    // Definisco le variabili per la conversione delle date
    const input = dateFormat;
    const output = ScrivaDatesFormat.moment;
    // Converto le date in moment
    const dsM = convertDateByType(ds, input, output) as Moment;
    const deM = convertDateByType(de, input, output) as Moment;

    // Definisco la condizione per generare l'errore
    let validDates = true;
    // Verifico la condizione in base al flag lessOrEqual
    if (lessOrEqual) {
      // Definisco che la data di start deve essere uguale o prima di quella di end
      validDates = dsM.isSameOrBefore(deM);
      // #
    } else {
      // Definisco che la data di start deve essere prima di quella di end
      validDates = dsM.isBefore(deM);
      // #
    }

    // La data di protocollo deve essere antecedente alla data scadenza pagamento
    if (!validDates) {
      // Preparo l'oggetto di errore
      const keyErrDateMinMax = ERR_KEY.SCRIVA_DATE_MIN_DATE_MAX;
      // Ritorno l'errore generato
      return generaObjErrore(keyErrDateMinMax);
    }

    // Rimuovo eventuali errori
    return null;
  };
};
