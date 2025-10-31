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
import { PCMEErrorsKeys } from './profilo-contatti-modifica-email.errors-key';

/**
 * Classe di mapping per gli errori delle form.
 */
export class ProfiloContattiModificaEmailErrorsMap extends ScrivaErrorsMap {
  /** PCMEKeysErrors contenente le chiavi custom per questa componente. */
  private S_EK = ScrivaErrorsKeys;
  /** PCMEKeysErrors contenente le chiavi custom per questa componente. */
  private PCME_EK = PCMEErrorsKeys;

  private FORM_CONTROL_EMAIL: IMappaErroriFormECodici = {
    erroreForm: this.ANGULAR_EMAIL,
    erroreCodice: this.CODES.E004,
  };

  /** Mappatura errori: acconsento. */
  MAP_ACCONSENTO: IMappaErroriFormECodici[] = [
    {
      erroreForm: this.S_EK.SCRIVA_CHECKBOX_NOT_TRUE,
      erroreCodice: this.CODES.E001,
    },
  ];
  /** Mappatura errori: email. */
  MAP_EMAIL: IMappaErroriFormECodici[] = [
    ...this.MAP_FORM_CONTROL_REQUIRED,
    this.FORM_CONTROL_EMAIL,
  ];
  /** Mappatura errori: emailRipeti. */
  MAP_EMAIL_RIPETI: IMappaErroriFormECodici[] = [
    ...this.MAP_FORM_CONTROL_REQUIRED,
    this.FORM_CONTROL_EMAIL,
  ];

  /** Mappatura errori: email uguale a ripetiEmail. */
  MAP_FORM_EMAIL_RIPETI_EMAIL: IMappaErroriFormECodici[] = [
    {
      erroreForm: this.PCME_EK.RIPETI_EMAIL,
      erroreCodice: this.CODES.E069,
    },
  ];

  constructor() {
    // Richiamo il super
    super();
  }
}
