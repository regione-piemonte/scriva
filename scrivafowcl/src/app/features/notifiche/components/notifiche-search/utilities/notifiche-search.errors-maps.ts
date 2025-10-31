/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ScrivaErrorsKeys } from '../../../../../shared/classes/scriva-input-errors/scriva-errors-keys.class';
import { ScrivaErrorsMap } from '../../../../../shared/classes/scriva-input-errors/scriva-errors-maps.class';
import { IMappaErroriFormECodici } from '../../../../../shared/components/form-inputs/form-input/utilities/form-input.interfaces';
import { NSErrorsKeys } from './notifiche-search.errors-key';

/**
 * Classe di mapping per gli errori delle form.
 */
export class NotificheSearchErrorsMap extends ScrivaErrorsMap {
  /** ScrivaUtilitiesErrorsKeys contenente le chiavi custom per questa componente. */
  private S_EK = ScrivaErrorsKeys;
  /** NSErrorsKeys contenente le chiavi custom per questa componente. */
  private NE_EK = NSErrorsKeys;

  /** Mappatura errori: acconsento. */
  MAP_DATE_DA_DATE_A: IMappaErroriFormECodici[] = [
    {
      erroreForm: this.S_EK.SCRIVA_DATE_MIN_DATE_MAX,
      erroreCodice: this.CODES.E004,
    },
  ];

  constructor() {
    // Richiamo il super
    super();
  }
}
