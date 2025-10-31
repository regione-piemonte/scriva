/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ScrivaCodesMesseges } from '../../../core/enums/scriva-codes-messages.enums';
import { IMappaErroriFormECodici } from '../../components/form-inputs/form-input/utilities/form-input.interfaces';
import { ScrivaErrorsKeys } from './scriva-errors-keys.class';

/**
 * Classe di mapping per gli errori delle form.
 */
export class ScrivaErrorsMap {
  /** Definisco localmente una variabile contenente RiscaNotifyCodes. */
  protected CODES = ScrivaCodesMesseges;
  /** Definisco la costante contenente le chiavi d'errore per i forms validators. */
  protected ERR_KEYS = ScrivaErrorsKeys;

  /** Definisco le costanti per gli errori di Angular */
  protected ANGULAR_REQUIRED = 'required';
  protected ANGULAR_PATTERN = 'pattern';
  protected ANGULAR_MIN = 'min';
  protected ANGULAR_EMAIL = 'email';
  protected ANGULAR_MAXLENGTH = 'maxlength';

  /** Mappatura errori: campo obbligatorio sul form. */
  MAP_FORM_GROUP_REQUIRED: IMappaErroriFormECodici[] = [
    {
      erroreForm: this.ANGULAR_REQUIRED,
      erroreCodice: this.CODES.E001,
    },
  ];
  /** Mappatura errori: campo obbligatorio. */
  MAP_FORM_CONTROL_REQUIRED: IMappaErroriFormECodici[] = [
    {
      erroreForm: this.ANGULAR_REQUIRED,
      erroreCodice: this.CODES.E001,
    },
  ];

  constructor() {}
}
