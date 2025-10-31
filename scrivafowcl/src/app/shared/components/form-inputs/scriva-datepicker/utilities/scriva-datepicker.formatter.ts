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
  NgbDateParserFormatter,
  NgbDateStruct,
} from '@ng-bootstrap/ng-bootstrap';
import * as moment from 'moment';
import { CommonConsts } from '../../../../consts/common-consts.consts';
import {
  convertMomentToNgbDateStruct,
  convertMomentToViewDate,
  convertNgbDateStructToMoment,
} from '../../../../services/scriva-utilities/scriva-utilities.functions';

@Injectable()
export class NgbDateCustomParserFormatter extends NgbDateParserFormatter {
  /** Oggetto di costanti comuni all'applicazione. */
  private C_C = new CommonConsts();

  /** String che definisce il formato della data per le verifiche. Deve essere compatibile con la libreria MomentJs. */
  private _momentFormat: string;

  /**
   * Costruttore del parser.
   */
  constructor() {
    super();

    // Salvo, se esiste, il formato moment custom, altrimenti imposto un default
    this._momentFormat = this.C_C.DATE_FORMAT_VIEW;
  }

  /**
   * Funzione estesa da NgbDateParserFormatter.
   * La funzione cerchera di effettuare una conversione da una stringa in input, in un oggetto NgbDateStruct per il funzionamento del datepicker.
   * @param value string con la data da convertire per il calendario.
   */
  parse(value: string): NgbDateStruct | null {
    // Verifico l'input
    if (!value) {
      // L'input non è valido
      return null;
    }
    // Tento di convertire il valore in input in un moment
    const mValue = moment(value, this._momentFormat);
    // Verifico che il moment sia una data valida
    if (!mValue.isValid()) {
      // L'input non è formattato correttamente
      return null;
    }

    // Il moment è valido, compongo un oggetto NgbDateStruct
    const ngbValue = convertMomentToNgbDateStruct(mValue);
    // Ritorno l'oggetto convertito
    return ngbValue;
  }

  /**
   * Funzione estesa da NgbDateParserFormatter.
   * La funzione cerchera di effettuare una formattazione da un oggetto NgbDateStruct in input, in una stringa per il funzionamento del datepicker.
   * @param date NgbDateStruct con la data da convertire per il calendario.
   */
  format(date: NgbDateStruct | null): string {
    // Verifico l'input
    if (!date) {
      // L'input non è valido
      return '';
    }

    // Converto l'oggetto NgbDateStruct in un moment
    const mDate = convertNgbDateStructToMoment(date);
    // Verifico se il moment generato è una data valida
    if (!mDate.isValid()) {
      // La data non è valida
      return '';
    }

    // La data è valida, converto il moment in stringa
    const sDate = convertMomentToViewDate(mDate);
    // Ritorno la data in formato stringa
    return sDate;
  }
}
