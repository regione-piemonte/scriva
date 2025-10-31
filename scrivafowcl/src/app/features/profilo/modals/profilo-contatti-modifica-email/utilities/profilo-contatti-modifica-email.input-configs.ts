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
  ScrivaFormInputCheckbox,
  ScrivaFormInputEmail,
} from '../../../../../shared/components/form-inputs/form-input/utilities/form-input.classes';
import { SCOrientamento } from '../../../../../shared/components/form-inputs/form-input/utilities/form-input.enums';
import { IScrivaCheckboxData } from '../../../../../shared/components/form-inputs/form-input/utilities/form-input.interfaces';
import { IScrivaComponentConfig } from '../../../../../shared/interfaces/scriva-utilities.interfaces';
import { ScrivaFormBuilderService } from '../../../../../shared/services/form-inputs/scriva-form-builder.service';
import { PROFILO_CONTATTI_MODIFICA_EMAIL_CONSTS } from './profilo-contatti-modifica-email.consts';

/**
 * Interfacci di comodo per la configurazione della classe per le configurazioni.
 */
export interface IPCMEFieldsConfigClass {
  scrivaFormBuilder: ScrivaFormBuilderService;
}

/**
 * Classe che definisce le configurazioni per i campi del form del componente collegato.
 */
export class PCMEFieldsConfigClass {
  /** Costante contenente le informazioni costanti del componente. */
  PCME_C = PROFILO_CONTATTI_MODIFICA_EMAIL_CONSTS;

  /** Oggetto di configurazione per il campo: autorizzo. */
  autorizzoConfig: IScrivaComponentConfig<ScrivaFormInputCheckbox>;
  /** Oggetto di configurazione per il campo: email. */
  emailConfig: IScrivaComponentConfig<ScrivaFormInputEmail>;
  /** Oggetto di configurazione per il campo: ripetiEmail. */
  ripetiEmailConfig: IScrivaComponentConfig<ScrivaFormInputEmail>;

  /**
   * Costruttore.
   */
  constructor(config: IPCMEFieldsConfigClass) {
    // Estraggo le configurazioni
    const { scrivaFormBuilder } = config;
    // Lancio il setup delle configurazioni
    this.setupFormInputs(scrivaFormBuilder);
  }

  /**
   * Funzione di setup per le input del form.
   * @param scrivaFormBuilder ScrivaFormBuilderService che definisce il servizio per la generazione delle input form di comodo.
   */
  setupFormInputs(scrivaFormBuilder: ScrivaFormBuilderService) {
    this.autorizzoConfig = scrivaFormBuilder.genInputCheckbox({
      orientation: SCOrientamento.verticale,
      showErrorFC: true,
    });
    const autorizzoSource: IScrivaCheckboxData[] = [
      {
        id: this.PCME_C.FC_AUTORIZZO,
        label: `*${this.PCME_C.LABEL_AUTORIZZO}`,
        value: false,
        check: false,
      },
    ];
    this.autorizzoConfig.source = autorizzoSource;
    // Recupero le classi di stile impostato per default
    let customCssLabel = this.autorizzoConfig.css.customLabel;
    // Aggiungo la classe di stile locale
    customCssLabel = `${customCssLabel} pcme-label-autorizzazione`;
    // Assegno le nuovi classi come custom label
    this.autorizzoConfig.css.customLabel = customCssLabel;

    this.autorizzoConfig.css.customFormCheck = 'pl-0';

    this.emailConfig = scrivaFormBuilder.genInputEmail({
      label: this.PCME_C.LABEL_EMAIL,
      placeholder: this.PCME_C.PLACEHOLDER_EMAIL,
      showErrorFC: true,
      maxLength: 150,
    });

    this.ripetiEmailConfig = scrivaFormBuilder.genInputEmail({
      label: this.PCME_C.LABEL_EMAIL_RIPETI,
      placeholder: this.PCME_C.PLACEHOLDER_EMAIL_RIPETI,
      showErrorFC: true,
      showErrorFG: true,
      maxLength: 150,
    });
  }
}
