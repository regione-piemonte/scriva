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
  ScrivaFormInputDatepicker,
  ScrivaFormInputSelect,
  ScrivaFormInputText,
} from '../../../../../shared/components/form-inputs/form-input/utilities/form-input.classes';
import { CommonConsts } from '../../../../../shared/consts/common-consts.consts';
import { IScrivaComponentConfig } from '../../../../../shared/interfaces/scriva-utilities.interfaces';
import { ScrivaFormBuilderService } from '../../../../../shared/services/form-inputs/scriva-form-builder.service';
import { NOTIFICHE_SEARCH_CONSTS } from './notifiche-search.consts';

/**
 * Interfacci di comodo per la configurazione della classe per le configurazioni.
 */
export interface INSFieldsConfigClass {
  scrivaFormBuilder: ScrivaFormBuilderService;
}

/**
 * Classe che definisce le configurazioni per i campi del form del componente collegato.
 */
export class NSFieldsConfigClass {
  /** Costante contenente le informazioni costanti dell'applicazione. */
  C_C = new CommonConsts();
  /** Costante contenente le informazioni costanti del componente. */
  NS_C = NOTIFICHE_SEARCH_CONSTS;

  /** Oggetto di configurazione per il campo: statoNotifica. */
  statoNotificaConfig: IScrivaComponentConfig<ScrivaFormInputSelect>;
  /** Oggetto di configurazione per il campo: procedimento. */
  procedimentoConfig: IScrivaComponentConfig<ScrivaFormInputSelect>;
  /** Oggetto di configurazione per il campo: dataDa. */
  dataDaConfig: IScrivaComponentConfig<ScrivaFormInputDatepicker>;
  /** Oggetto di configurazione per il campo: dataA. */
  dataAConfig: IScrivaComponentConfig<ScrivaFormInputDatepicker>;
  /** Oggetto di configurazione per il campo: numeroIstanza. */
  numeroIstanzaConfig: IScrivaComponentConfig<ScrivaFormInputText>;

  /**
   * Costruttore.
   */
  constructor(config: INSFieldsConfigClass) {
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
    this.statoNotificaConfig = scrivaFormBuilder.genInputSelect({
      label: this.NS_C.LABEL_STATO_NOTIFICA,
      defaultLabel: this.NS_C.DEFAULT_LABEL_STATO_NOTIFICA,
      emptyLabelSelected: true,
    });

    this.procedimentoConfig = scrivaFormBuilder.genInputSelect({
      label: this.NS_C.LABEL_PROCEDIMENTO,
      defaultLabel: this.NS_C.DEFAULT_LABEL_PROCEDIMENTO,
      emptyLabelSelected: true,
    });

    this.dataDaConfig = scrivaFormBuilder.genInputDatepicker({
      label: this.NS_C.LABEL_DATA_DA,
      placeholder: this.C_C.DATE_FORMAT_VIEW_PLACEHOLDER,
      showErrorFG: true,
    });

    this.dataAConfig = scrivaFormBuilder.genInputDatepicker({
      label: this.NS_C.LABEL_DATA_A,
      placeholder: this.C_C.DATE_FORMAT_VIEW_PLACEHOLDER,
      showErrorFG: true,
    });

    this.numeroIstanzaConfig = scrivaFormBuilder.genInputText({
      label: this.NS_C.LABEL_NUMERO_ISTANZA,
      placeholder: this.NS_C.DEFAULT_LABEL_NUMERO_ISTANZA,
    });
  }
}
