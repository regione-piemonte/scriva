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
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';
import { DynamicObjString } from '../../../core/interfaces/scriva.interfaces';
import {
  ScrivaFormInputCheckbox,
  ScrivaFormInputCss,
  ScrivaFormInputDatepicker,
  ScrivaFormInputEmail,
  ScrivaFormInputNumber,
  ScrivaFormInputRadio,
  ScrivaFormInputSearch,
  ScrivaFormInputSelect,
  ScrivaFormInputText,
  ScrivaFormInputTypeahead,
} from '../../components/form-inputs/form-input/utilities/form-input.classes';
import {
  SCOrientamento,
  ScrivaFormBuilderSize,
} from '../../components/form-inputs/form-input/utilities/form-input.enums';
import {
  IScrivaCheckboxData,
  IScrivaRadioData,
  IScrivaDatepickerConfig,
} from '../../components/form-inputs/form-input/utilities/form-input.interfaces';
import { TScrivaFormInputChoiceOrientation } from '../../components/form-inputs/form-input/utilities/form-input.types';
import { IScrivaComponentConfig } from '../../interfaces/scriva-utilities.interfaces';
import { ScrivaUtilitiesService } from '../scriva-utilities/scriva-utilities.service';
import { ScrivaComponentConfig } from '../../components/form-inputs/test-input-generici/utilities/test-input-form.interfaces';

/**
 * Interfaccia contenente le proprietà comuni per la configurazione delle form structures.
 */
export interface IScrivaFormBuilderCommon {
  size?: ScrivaFormBuilderSize;
  label?: string;
  labelDBConfigKey?: string;
  labelLeft?: string;
  labelRight?: string;
  labelBottom?: string;
  hideLabel?: boolean;
  hideLabelLeft?: boolean;
  hideLabelRight?: boolean;
  hideLabelBottom?: boolean;
  showErrorFG?: boolean;
  showErrorFC?: boolean;
  extraLabelRight?: string;
  extraLabelSub?: string;
  placeholder?: string;
  title?: string;
}

/**
 * Interfaccia contenente le proprietà comuni per la configurazione delle form structures che gestiscono numeri.
 */
export interface IScrivaFormBuilderCommonNumber {
  onlyNumber?: boolean;
  useDecimal?: boolean;
  useSign?: boolean;
}

/**
 * Interfaccia contenente le proprietà comuni per la configurazione delle form structures che gestiscono le scelte tramite radio/checkbox.
 */
export interface IScrivaFormBuilderCommonChoice {
  orientation?: TScrivaFormInputChoiceOrientation;
}

/**
 * Interfaccia che definisce le configurazioni per il componente scriva-text.
 */
export interface IScrivaInputText
  extends IScrivaFormBuilderCommon,
    IScrivaFormBuilderCommonNumber {
  maxLength?: number;
}

/**
 * Interfaccia che definisce le configurazioni per il componente scriva-text-fake.
 */
export interface IScrivaInputTextFake extends IScrivaFormBuilderCommon {}

/**
 * Interfaccia che definisce le configurazioni per il componente scriva-datepicker.
 */
export interface IScrivaInputDatepicker extends IScrivaFormBuilderCommon {
  minDate?: NgbDateStruct;
  maxDate?: NgbDateStruct;
}

/**
 * Interfaccia che definisce le configurazioni per il componente scriva-typeahead.
 */
export interface IScrivaInputTypeahead
  extends IScrivaFormBuilderCommon,
    IScrivaFormBuilderCommonNumber {
  typeaheadSearch: (v: string) => Observable<any[]>;
  typeaheadMap: (v: any) => string;
  searchOnLength?: number;
  maxLength?: number;
  debounceTime?: number;
  showOnClick?: boolean;
}

/**
 * Interfaccia che definisce le configurazioni per il componente scriva-select.
 */
export interface IScrivaInputSelect extends IScrivaFormBuilderCommon {
  defaultLabel?: string;
  emptyLabelSelected?: boolean;
  ignoreDefault?: boolean;
  multiple?: boolean;
  customOption?: (v: any) => string;
  customOptionClass?: (v: any) => string[];
  customOptionStyle?: (v: any) => DynamicObjString;
}


/**
 * Interfaccia che definisce le configurazioni per il componente risca-search.
 */
export interface IScrivaInputSearch
  extends IScrivaFormBuilderCommon,
    IScrivaFormBuilderCommonNumber {
  maxLength?: number;
}

/**
 * Interfaccia che definisce le configurazioni per il componente scriva-email.
 */
export interface IScrivaInputEmail extends IScrivaFormBuilderCommon {
  maxLength?: number;
}

/**
 * Interfaccia che definisce le configurazioni per il componente scriva-checkbox.
 */
export interface IScrivaInputCheckbox
  extends IScrivaFormBuilderCommon,
    IScrivaFormBuilderCommonChoice {}

/**
 * Interfaccia che definisce le configurazioni per il componente scriva-radio.
 */
export interface IScrivaInputRadio
  extends IScrivaFormBuilderCommon,
    IScrivaFormBuilderCommonChoice {}

/**
 * Interfaccia che definisce le configurazioni per il componente scriva-textarea.
 */
export interface IScrivaInputTextarea extends IScrivaFormBuilderCommon {
  maxLength?: number;
}

export interface IScrivaInputNumber extends IScrivaFormBuilderCommon {
  maxLength?: number;
  useDecimal?: boolean;
  useSign?: boolean;
  min?: number;
  max?: number;
  step?: number;
  decimals?: number;
  decimaliNonSignificativi?: boolean;
  allowThousandsSeparator?: boolean;
}

/**
 * Servizio di utility con funzionalità di comodo per la generazione delle configurazioni per i campi dei form dell'applicazione.
 */
@Injectable({
  providedIn: 'root',
})
export class ScrivaFormBuilderService {
  /** Costante per la classe di stile: form */
  private SCRIVA_INPUT_SMALL = 'scriva-input-small';
  private SCRIVA_INPUT_STANDARD = 'scriva-input-standard';
  private SCRIVA_INPUT_STANDARD_X2 = 'scriva-input-standard-x2';
  private SCRIVA_INPUT_STANDARD_X3 = 'scriva-input-standard-x3';
  private SCRIVA_INPUT_STANDARD_FULL = 'scriva-input-full';
  /** Costante per la classe di stile: input */
  private SCRIVA_INPUT_CONTENT_SMALL = 'scriva-input-content-small';
  private SCRIVA_INPUT_CONTENT_STANDARD = 'scriva-input-content-standard';
  private SCRIVA_INPUT_DATEPICKER_SMALL = 'scriva-input-datepicker-sm';
  private SCRIVA_INPUT_TEXTAREA_STANDARD = 'scriva-input-textarea-standard';
  /** Costante per la classe di stile: label */
  private SCRIVA_INPUT_LABEL_SMALL = 'scriva-input-label-small';
  private SCRIVA_INPUT_LABEL_STANDARD = 'scriva-input-label-standard';
  private SCRIVA_INPUT_LABEL_RADIO_H = 'scriva-input-label-radio r-p--0';
  private SCRIVA_INPUT_LABEL_RADIO_V =
    'scriva-input-label-radio scriva-input-label-radio-v';
  private SCRIVA_INPUT_RADIO_V = 'scriva-radio-v';
  private SCRIVA_INPUT_LABEL_CHECK_H = 'scriva-input-label-checkbox r-p--0';
  private SCRIVA_INPUT_LABEL_CHECK_V =
    'scriva-input-label-checkbox scriva-input-label-checkbox-v';
  private SCRIVA_INPUT_CHECK_V = 'scriva-checkbox-v';

  /**
   * Costruttore
   */
  constructor(private _scrivaUtilities: ScrivaUtilitiesService) {}

  /**
   * ########################
   * GENERATORI DI CSS CONFIG
   * ########################
   */

  /**
   * Funzione che genera una classe ScrivaFormInputCss con dimensioni standard.
   * @param customForm string con la classe di stile per form.
   * @param customInput string con la classe di stile per input.
   * @param customLabel string con la classe di stile per label.
   * @param showErrorFG boolean come flag di attivazione per la visualizzazione degli errori sul form group.
   * @param showErrorFC boolean come flag di attivazione per la visualizzazione degli errori sul form control.
   * @returns ScrivaFormInputCss generata.
   */
  genScrivaFormInputCss(
    customForm: string,
    customInput: string,
    customLabel: string,
    showErrorFG: boolean,
    showErrorFC: boolean
  ): ScrivaFormInputCss {
    // Definisco l'oggetto per il css
    const css: ScrivaFormInputCss = new ScrivaFormInputCss({
      customForm,
      customInput,
      customLabel,
      showErrorFG,
      showErrorFC,
    });
    // Ritorno l'oggetto generato
    return css;
  }

  /**
   * Funzione che genera una classe ScrivaFormInputCss con dimensioni piccole.
   * @param showErrorFG boolean come flag di attivazione per la visualizzazione degli errori sul form group.
   * @param showErrorFC boolean come flag di attivazione per la visualizzazione degli errori sul form control.
   * @returns ScrivaFormInputCss generata.
   */
  genScrivaFormInputCssSmall(
    showErrorFG: boolean,
    showErrorFC: boolean
  ): ScrivaFormInputCss {
    // Definisco i parametri della input
    const customForm = this.SCRIVA_INPUT_SMALL;
    const customInput = this.SCRIVA_INPUT_CONTENT_SMALL;
    const customLabel = this.SCRIVA_INPUT_LABEL_SMALL;
    // Ritorno l'oggetto generato per il css
    return this.genScrivaFormInputCss(
      customForm,
      customInput,
      customLabel,
      showErrorFG,
      showErrorFC
    );
  }

  /**
   * Funzione che genera una classe ScrivaFormInputCss con dimensioni standard.
   * @param showErrorFG boolean come flag di attivazione per la visualizzazione degli errori sul form group.
   * @param showErrorFC boolean come flag di attivazione per la visualizzazione degli errori sul form control.
   * @returns ScrivaFormInputCss generata.
   */
  genScrivaFormInputCssStandard(
    showErrorFG: boolean,
    showErrorFC: boolean
  ): ScrivaFormInputCss {
    // Definisco i parametri della input
    const customForm = this.SCRIVA_INPUT_STANDARD;
    const customInput = this.SCRIVA_INPUT_CONTENT_STANDARD;
    const customLabel = this.SCRIVA_INPUT_LABEL_STANDARD;
    // Ritorno l'oggetto generato per il css
    return this.genScrivaFormInputCss(
      customForm,
      customInput,
      customLabel,
      showErrorFG,
      showErrorFC
    );
  }

  /**
   * Funzione che genera una classe ScrivaFormInputCss con dimensioni standard x2.
   * @param showErrorFG boolean come flag di attivazione per la visualizzazione degli errori sul form group.
   * @param showErrorFC boolean come flag di attivazione per la visualizzazione degli errori sul form control.
   * @returns ScrivaFormInputCss generata.
   */
  genScrivaFormInputCssX2(
    showErrorFG: boolean,
    showErrorFC: boolean
  ): ScrivaFormInputCss {
    // Definisco i parametri della input
    const customForm = this.SCRIVA_INPUT_STANDARD_X2;
    const customInput = this.SCRIVA_INPUT_CONTENT_STANDARD;
    const customLabel = this.SCRIVA_INPUT_LABEL_STANDARD;
    // Ritorno l'oggetto generato per il css
    return this.genScrivaFormInputCss(
      customForm,
      customInput,
      customLabel,
      showErrorFG,
      showErrorFC
    );
  }

  /**
   * Funzione che genera una classe ScrivaFormInputCss con dimensioni standard x2.
   * @param showErrorFG boolean come flag di attivazione per la visualizzazione degli errori sul form group.
   * @param showErrorFC boolean come flag di attivazione per la visualizzazione degli errori sul form control.
   * @returns ScrivaFormInputCss generata.
   */
  genScrivaFormInputCssX3(
    showErrorFG: boolean,
    showErrorFC: boolean
  ): ScrivaFormInputCss {
    // Definisco i parametri della input
    const customForm = this.SCRIVA_INPUT_STANDARD_X3;
    const customInput = this.SCRIVA_INPUT_CONTENT_STANDARD;
    const customLabel = this.SCRIVA_INPUT_LABEL_STANDARD;
    // Ritorno l'oggetto generato per il css
    return this.genScrivaFormInputCss(
      customForm,
      customInput,
      customLabel,
      showErrorFG,
      showErrorFC
    );
  }

  /**
   * Funzione che genera una classe ScrivaFormInputCss con dimensioni piccole.
   * @param showErrorFG boolean come flag di attivazione per la visualizzazione degli errori sul form group.
   * @param showErrorFC boolean come flag di attivazione per la visualizzazione degli errori sul form control.
   * @returns ScrivaFormInputCss generata.
   */
  genScrivaFormDatepickerCssSmall(
    showErrorFG: boolean,
    showErrorFC: boolean
  ): ScrivaFormInputCss {
    // Definisco i parametri del calendario
    const customForm = this.SCRIVA_INPUT_SMALL;
    const customInput = this.SCRIVA_INPUT_DATEPICKER_SMALL;
    const customLabel = this.SCRIVA_INPUT_LABEL_STANDARD;
    // Ritorno l'oggetto generato per il css
    return this.genScrivaFormInputCss(
      customForm,
      customInput,
      customLabel,
      showErrorFG,
      showErrorFC
    );
  }

  /**
   * Funzione che genera una classe ScrivaFormInputCss con dimensioni full.
   * @param showErrorFG boolean come flag di attivazione per la visualizzazione degli errori sul form group.
   * @param showErrorFC boolean come flag di attivazione per la visualizzazione degli errori sul form control.
   * @returns ScrivaFormInputCss generata.
   */
  genScrivaFormInputCssFull(
    showErrorFG: boolean,
    showErrorFC: boolean
  ): ScrivaFormInputCss {
    // Definisco i parametri della input
    const customForm = this.SCRIVA_INPUT_STANDARD_FULL;
    const customInput = this.SCRIVA_INPUT_CONTENT_STANDARD;
    const customLabel = this.SCRIVA_INPUT_LABEL_STANDARD;
    // Ritorno l'oggetto generato per il css
    return this.genScrivaFormInputCss(
      customForm,
      customInput,
      customLabel,
      showErrorFG,
      showErrorFC
    );
  }

  /**
   * ###############################
   * SELETTORE DIMENSIONI CSS CONFIG
   * ###############################
   */

  /**
   * Funzione di selettore dimensioni per la configurazione css.
   * @param size ScrivaFormBuilderSize con la dimensione della input.
   * @param showErrorFG boolean per visualizzare gli errori del form group. Default: false.
   * @param showErrorFC boolean per visualizzare gli errori del form control. Default: false.
   * @returns ScrivaFormInputCss con la connfigurazione.
   */
  private selettoreDimensioneCssConfig(
    size: ScrivaFormBuilderSize,
    showErrorFG: boolean,
    showErrorFC: boolean
  ): ScrivaFormInputCss {
    // Definisco un contenitore per la dimensione css
    let css: ScrivaFormInputCss;
    // Verifico la dimensione per il css (PER ORA ESISTE SOLO UNA CONFIGURAZIONE SMALL)
    switch (size) {
      case 'small':
        css = this.genScrivaFormInputCssSmall(showErrorFG, showErrorFC);
        break;
      case 'standard':
        css = this.genScrivaFormInputCssStandard(showErrorFG, showErrorFC);
        break;
      case 'x2':
        css = this.genScrivaFormInputCssX2(showErrorFG, showErrorFC);
        break;
      case 'x3':
        css = this.genScrivaFormInputCssX3(showErrorFG, showErrorFC);
        break;
      case 'full':
        css = this.genScrivaFormInputCssFull(showErrorFG, showErrorFC);
        break;
      default:
        css = this.genScrivaFormInputCssStandard(showErrorFG, showErrorFC);
    }

    // Ritorno la configurazione
    return css;
  }

  /**
   * ####################
   * GENERATORI DI CONFIG
   * ####################
   */

  /**
   * Funzione per la generazione di una input: text.
   * Se le seguenti proprietà non vengono configurate, verrà applicato un default.
   * @param c IScrivaInputText contenente le configurazioni per il componente.
   * @returns IScrivaComponentConfig con la configurazione input.
   */
  genInputText(
    c: IScrivaInputText
  ): IScrivaComponentConfig<ScrivaFormInputText> {
    // Definisco l'oggetto per il css
    const css: ScrivaFormInputCss = this.selettoreDimensioneCssConfig(
      c.size || ScrivaFormBuilderSize.standard,
      c.showErrorFG !== undefined ? c.showErrorFG : false,
      c.showErrorFC !== undefined ? c.showErrorFC : false
    );
    // Definisco l'oggetto per i dati
    const data: ScrivaFormInputText = new ScrivaFormInputText({
      label: c.label,
      labelLeft: c.labelLeft,
      labelRight: c.labelRight,
      labelBottom: c.labelBottom,
      hideLabel: c.hideLabel,
      hideLabelLeft: c.hideLabelLeft,
      hideLabelRight: c.hideLabelRight,
      hideLabelBottom: c.hideLabelBottom,
      maxLength: c.maxLength || 20,
      onlyNumber: c.onlyNumber !== undefined ? c.onlyNumber : false,
      useDecimal: c.useDecimal !== undefined ? c.useDecimal : false,
      useSign: c.useSign !== undefined ? c.useDecimal : false,
      extraLabelRight: c.extraLabelRight,
      extraLabelSub: c.extraLabelSub,
      placeholder: c.placeholder,
    });

    // Ritorno classe di stile e dati
    return { css, data };
  }

  /**
   * Funzione per la generazione di una input: text.
   * Se le seguenti proprietà non vengono configurate, verrà applicato un default.
   * @param c IScrivaInputTextFake contenente le configurazioni per il componente.
   * @returns IScrivaComponentConfig con la configurazione input.
   */
  genInputTextFake(
    c: IScrivaInputTextFake
  ): IScrivaComponentConfig<ScrivaFormInputText> {
    // Definisco l'oggetto per il css
    const css: ScrivaFormInputCss = this.selettoreDimensioneCssConfig(
      c.size || ScrivaFormBuilderSize.standard,
      c.showErrorFG !== undefined ? c.showErrorFG : false,
      c.showErrorFC !== undefined ? c.showErrorFC : false
    );

    // Definisco l'oggetto per i dati
    const data: ScrivaFormInputText = new ScrivaFormInputText({
      label: c.label,
      labelLeft: c.labelLeft,
      labelRight: c.labelRight,
      labelBottom: c.labelBottom,
      hideLabel: c.hideLabel,
      hideLabelLeft: c.hideLabelLeft,
      hideLabelRight: c.hideLabelRight,
      hideLabelBottom: c.hideLabelBottom,
      // maxLength: c.maxLength || 20,
      // onlyNumber: c.onlyNumber !== undefined ? c.onlyNumber : false,
      // useDecimal: c.useDecimal !== undefined ? c.useDecimal : false,
      // useSign: c.useSign !== undefined ? c.useDecimal : false,
      extraLabelRight: c.extraLabelRight,
      extraLabelSub: c.extraLabelSub,
      placeholder: c.placeholder,
    });

    // Ritorno classe di stile e dati
    return { css, data };
  }

  /**
   * Funzione per la generazione di una input: typeahead.
   * Se le seguenti proprietà non vengono configurate, verrà applicato un default.
   * @param c IScrivaInputTypeahead contenente le configurazioni per il componente.
   * @returns IScrivaComponentConfig con la configurazione input.
   */
  genInputTypeahead(
    c: IScrivaInputTypeahead
  ): IScrivaComponentConfig<ScrivaFormInputTypeahead> {
    // Definisco l'oggetto per il css
    const css: ScrivaFormInputCss = this.selettoreDimensioneCssConfig(
      c.size,
      c.showErrorFG !== undefined ? c.showErrorFG : false,
      c.showErrorFC !== undefined ? c.showErrorFC : false
    );

    // Definisco l'oggetto per i dati
    const data: ScrivaFormInputTypeahead = new ScrivaFormInputTypeahead({
      label: c.label || 'standard',
      labelLeft: c.labelLeft,
      labelRight: c.labelRight,
      labelBottom: c.labelBottom,
      hideLabel: c.hideLabel,
      hideLabelLeft: c.hideLabelLeft,
      hideLabelRight: c.hideLabelRight,
      hideLabelBottom: c.hideLabelBottom,
      maxLength: c.maxLength || 20,
      searchOnLength: c.searchOnLength || 3,
      typeaheadSearch: c.typeaheadSearch,
      typeaheadMap: c.typeaheadMap,
      debounceTime: c.debounceTime || 300,
      onlyNumber: c.onlyNumber !== undefined ? c.onlyNumber : false,
      useDecimal: c.useDecimal !== undefined ? c.useDecimal : false,
      useSign: c.useSign !== undefined ? c.useSign : false,
      extraLabelRight: c.extraLabelRight,
      extraLabelSub: c.extraLabelSub,
      placeholder: c.placeholder,
      showOnClick: c.showOnClick,
    });

    // Ritorno classe di stile e dati
    return { css, data };
  }

  /**
   * Funzione per la generazione di una input: email.
   * Se le seguenti proprietà non vengono configurate, verrà applicato un default.
   * @param c IScrivaInputEmail contenente le configurazioni per il componente.
   * @returns IScrivaComponentConfig con la configurazione input.
   */
  genInputEmail(
    c: IScrivaInputEmail
  ): IScrivaComponentConfig<ScrivaFormInputEmail> {
    // Definisco l'oggetto per il css
    const css: ScrivaFormInputCss = this.selettoreDimensioneCssConfig(
      c.size || ScrivaFormBuilderSize.standard,
      c.showErrorFG !== undefined ? c.showErrorFG : false,
      c.showErrorFC !== undefined ? c.showErrorFC : false
    );
    // Definisco l'oggetto per i dati
    const data: ScrivaFormInputEmail = new ScrivaFormInputEmail({
      label: c.label,
      labelLeft: c.labelLeft,
      labelRight: c.labelRight,
      labelBottom: c.labelBottom,
      hideLabel: c.hideLabel,
      hideLabelLeft: c.hideLabelLeft,
      hideLabelRight: c.hideLabelRight,
      hideLabelBottom: c.hideLabelBottom,
      maxLength: c.maxLength || 40,
      extraLabelRight: c.extraLabelRight,
      extraLabelSub: c.extraLabelSub,
      placeholder: c.placeholder,
    });

    // Ritorno classe di stile e dati
    return { css, data };
  }

  /**
   * Funzione per la generazione di una input: checkbox.
   * Se le seguenti proprietà non vengono configurate, verrà applicato un default.
   * @param c IScrivaInputCheckbox contenente le configurazioni per il componente.
   * @returns IScrivaComponentConfig con la configurazione input.
   */
  genInputCheckbox(
    c: IScrivaInputCheckbox
  ): IScrivaComponentConfig<ScrivaFormInputCheckbox> {
    // Verifico l'orientamento
    if (!c.orientation) {
      c.orientation = SCOrientamento.orizzontale;
    }
    // Definisco le classi di stile in base all'ordientamento
    let customLabel = '';
    let customInput = '';
    let customFormCheck = '';
    if (c.orientation === SCOrientamento.orizzontale) {
      customLabel = this.SCRIVA_INPUT_LABEL_CHECK_H;
    } else if (c.orientation === SCOrientamento.verticale) {
      customLabel = this.SCRIVA_INPUT_LABEL_CHECK_V;
      customInput = this.SCRIVA_INPUT_CHECK_V;
      customFormCheck = 'r-p--0';
    }

    // Definisco l'oggetto per il css
    const css: ScrivaFormInputCss = new ScrivaFormInputCss({
      customLabel,
      customInput,
      customFormCheck,
      showErrorFG: c.showErrorFG !== undefined ? c.showErrorFG : false,
      showErrorFC: c.showErrorFC !== undefined ? c.showErrorFC : false,
    });
    // Definisco l'oggetto per i dati
    const data: ScrivaFormInputCheckbox = new ScrivaFormInputCheckbox({
      orientation: c.orientation,
    });
    // Inizializzo la proprietà di source
    const source: IScrivaCheckboxData[] = [];

    // Ritorno classe di stile e dati
    return { css, data, source };
  }

  /**
   * Funzione per la generazione di una input: radio.
   * Se le seguenti proprietà non vengono configurate, verrà applicato un default.
   * @param c IScrivaInputRadio contenente le configurazioni per il componente.
   * @returns IScrivaComponentConfig con la configurazione input.
   */
  genInputRadio(
    c: IScrivaInputRadio
  ): IScrivaComponentConfig<ScrivaFormInputRadio> {
    // Verifico l'orientamento
    if (!c.orientation) c.orientation = SCOrientamento.orizzontale;
    // Definisco le classi di stile in base all'ordientamento
    let customLabel = '';
    let customInput = '';
    let customFormCheck = '';
    if (c.orientation === SCOrientamento.orizzontale) {
      customLabel = this.SCRIVA_INPUT_LABEL_RADIO_H;
    } else if (c.orientation === SCOrientamento.verticale) {
      customLabel = this.SCRIVA_INPUT_LABEL_RADIO_V;
      customInput = this.SCRIVA_INPUT_RADIO_V;
      customFormCheck = 'r-p--0';
    }

    // Definisco l'oggetto per il css
    const css: ScrivaFormInputCss = new ScrivaFormInputCss({
      customLabel,
      customInput,
      customFormCheck,
      showErrorFG: c.showErrorFG !== undefined ? c.showErrorFG : false,
      showErrorFC: c.showErrorFC !== undefined ? c.showErrorFC : false,
    });
    // Definisco l'oggetto per i dati
    const data: ScrivaFormInputRadio = new ScrivaFormInputRadio({
      orientation: c.orientation,
    });
    // Inizializzo la proprietà di source
    const source: IScrivaRadioData[] = [];

    // Ritorno classe di stile e dati
    return { css, data, source };
  }

  /**
   * Funzione per la generazione di una input: text.
   * Se le seguenti proprietà non vengono configurate, verrà applicato un default.
   * @param c IScrivaInputText contenente le configurazioni per il componente.
   * @param heigth literla 'small' | 'standard' che definisce la classe di stile per definire l'altezza della textarea.
   * @returns IScrivaComponentConfig con la configurazione input.
   */
  genInputTextarea(
    c: IScrivaInputTextarea,
    heigth: 'small' | 'standard' = 'standard'
  ): IScrivaComponentConfig<ScrivaFormInputText> {
    // Definisco l'oggetto per il css
    const css: ScrivaFormInputCss = this.selettoreDimensioneCssConfig(
      c.size || ScrivaFormBuilderSize.full,
      c.showErrorFG !== undefined ? c.showErrorFG : false,
      c.showErrorFC !== undefined ? c.showErrorFC : false
    );
    // Verifico il parametro heigth, se esiste aggiungo alla stringa della classe di stile dell'input, quella per l'altezza
    if (heigth === 'standard')
      css.customInput = `${css.customInput} ${this.SCRIVA_INPUT_TEXTAREA_STANDARD}`;

    // Definisco l'oggetto per i dati
    const data: ScrivaFormInputText = new ScrivaFormInputText({
      label: c.label,
      labelLeft: c.labelLeft,
      labelRight: c.labelRight,
      labelBottom: c.labelBottom,
      hideLabel: c.hideLabel,
      hideLabelLeft: c.hideLabelLeft,
      hideLabelRight: c.hideLabelRight,
      hideLabelBottom: c.hideLabelBottom,
      maxLength: c.maxLength || 500,
      extraLabelRight: c.extraLabelRight,
      extraLabelSub: c.extraLabelSub,
      placeholder: c.placeholder,
    });

    // Ritorno classe di stile e dati
    return { css, data };
  }

  /**
   * Funzione per la generazione di una input: number.
   * Se le seguenti proprietà non vengono configurate, verrà applicato un default.
   * @param c IScrivaInputNumber contenente le configurazioni per il componente.
   * @returns IScrivaComponentConfig con la configurazione input.
   */
  genInputNumber(
    c: IScrivaInputNumber
  ): IScrivaComponentConfig<ScrivaFormInputNumber> {
    // Definisco l'oggetto per il css
    const css: ScrivaFormInputCss = this.selettoreDimensioneCssConfig(
      c.size || ScrivaFormBuilderSize.standard,
      c.showErrorFG !== undefined ? c.showErrorFG : false,
      c.showErrorFC !== undefined ? c.showErrorFC : false
    );
    // Definisco l'oggetto per i dati
    const data: ScrivaFormInputNumber = new ScrivaFormInputNumber({
      label: c.label,
      labelLeft: c.labelLeft,
      labelRight: c.labelRight,
      labelBottom: c.labelBottom,
      hideLabel: c.hideLabel,
      hideLabelLeft: c.hideLabelLeft,
      hideLabelRight: c.hideLabelRight,
      hideLabelBottom: c.hideLabelBottom,
      maxLength: c.maxLength || 20,
      useDecimal: c.useDecimal ?? false,
      decimals: c.decimals ?? 0,
      decimaliNonSignificativi: c.decimaliNonSignificativi ?? false,
      allowThousandsSeparator: c.allowThousandsSeparator,
      useSign: c.useSign ?? false,
      min: c.min ?? null,
      max: c.max ?? null,
      step: c.step ?? null,
      extraLabelRight: c.extraLabelRight,
      extraLabelSub: c.extraLabelSub,
      placeholder: c.placeholder,
      title: c.title,
    });

    // Ritorno classe di stile e dati
    return { css, data };
  }

  /**
   * Funzione per la generazione di una input: datepicker.
   * Se le seguenti proprietà non vengono configurate, verrà applicato un default.
   * @param c IScrivaInputDatepicker contenente le configurazioni per il componente.
   * @returns ScrivaComponentConfig con la configurazione input.
   */
  genInputDatepicker(
    c: IScrivaInputDatepicker
  ): IScrivaComponentConfig<ScrivaFormInputDatepicker> {
    // Definisco un contenitore per la dimensione css
    let css: ScrivaFormInputCss;
    // Verifico la dimensione per il css (PER ORA ESISTE SOLO UNA CONFIGURAZIONE SMALL)
    switch (c.size) {
      case 'small':
        css = this.genScrivaFormDatepickerCssSmall(
          c.showErrorFG !== undefined ? c.showErrorFG : false,
          c.showErrorFC !== undefined ? c.showErrorFC : false
        );
        break;
      default:
        css = this.genScrivaFormDatepickerCssSmall(
          c.showErrorFG !== undefined ? c.showErrorFG : false,
          c.showErrorFC !== undefined ? c.showErrorFC : false
        );
    }

    // Effettuo il setup per data minima e data massima dei calendari
    const calendarMinMax: IScrivaDatepickerConfig = {};
    this._scrivaUtilities.setupCalendarioMinMaxConfig(calendarMinMax);

    // Definisco l'oggetto per i dati del calendario
    const data: ScrivaFormInputDatepicker = new ScrivaFormInputDatepicker({
      label: c.label,
      labelLeft: c.labelLeft,
      labelRight: c.labelRight,
      labelBottom: c.labelBottom,
      hideLabel: c.hideLabel,
      hideLabelLeft: c.hideLabelLeft,
      hideLabelRight: c.hideLabelRight,
      hideLabelBottom: c.hideLabelBottom,
      minDate: c.minDate || calendarMinMax.minDate,
      maxDate: c.maxDate || calendarMinMax.maxDate,
      extraLabelRight: c.extraLabelRight,
      extraLabelSub: c.extraLabelSub,
      placeholder: c.placeholder,
    });

    // Ritorno classe di stile e dati
    return { css, data };
  }

  /**
   * Funzione per la generazione di una input: select.
   * Se le seguenti proprietà non vengono configurate, verrà applicato un default.
   * @param c IScrivaInputDatepicker contenente le configurazioni per il componente.
   * @returns ScrivaComponentConfig con la configurazione input.
   */
  genInputSelect(
    c: IScrivaInputSelect
  ): IScrivaComponentConfig<ScrivaFormInputSelect> {
    // Definisco l'oggetto per il css
    const css: ScrivaFormInputCss = this.selettoreDimensioneCssConfig(
      c.size || ScrivaFormBuilderSize.standard,
      c.showErrorFG !== undefined ? c.showErrorFG : false,
      c.showErrorFC !== undefined ? c.showErrorFC : false
    );

    // Definisco le variabili di configurazione dati
    const labelSelect = c.defaultLabel !== undefined ? c.defaultLabel : '';
    const useLabelSelect = c.defaultLabel !== undefined;
    // Definisco l'oggetto per i dati
    const data: ScrivaFormInputSelect = new ScrivaFormInputSelect({
      label: c.label,
      labelLeft: c.labelLeft,
      labelRight: c.labelRight,
      labelBottom: c.labelBottom,
      hideLabel: c.hideLabel,
      hideLabelLeft: c.hideLabelLeft,
      hideLabelRight: c.hideLabelRight,
      hideLabelBottom: c.hideLabelBottom,
      multiple: c.multiple,
      allowEmpty: useLabelSelect,
      emptyLabel: labelSelect,
      emptyLabelSelected: c.emptyLabelSelected,
      ignoreDefault: c.ignoreDefault !== undefined ? c.ignoreDefault : false,
      extraLabelRight: c.extraLabelRight,
      extraLabelSub: c.extraLabelSub,
      customOption: c.customOption,
      customOptionStyle: c.customOptionStyle,
      customOptionClass: c.customOptionClass,
    });

    // Ritorno classe di stile e dati
    return { css, data };
  }

  
  /**
   * Funzione per la generazione di una input: search.
   * Se le seguenti proprietà non vengono configurate, verrà applicato un default.
   * @param c IScrivaInputSearch contenente le configurazioni per il componente.
   * @returns ScrivaComponentConfig con la configurazione input.
   */
  genInputSearch(
    c: IScrivaInputSearch
  ): ScrivaComponentConfig<ScrivaFormInputSearch> {
    // Definisco l'oggetto per il css
    const css: ScrivaFormInputCss = this.selettoreDimensioneCssConfig(
      c.size || ScrivaFormBuilderSize.standard,
      c.showErrorFG !== undefined ? c.showErrorFG : false,
      c.showErrorFC !== undefined ? c.showErrorFC : false
    );
    // Definisco l'oggetto per i dati
    const data: ScrivaFormInputSearch = new ScrivaFormInputSearch({
      label: c.label,
      labelDBConfigKey: c.labelDBConfigKey,
      labelLeft: c.labelLeft,
      labelRight: c.labelRight,
      labelBottom: c.labelBottom,
      hideLabel: c.hideLabel,
      hideLabelLeft: c.hideLabelLeft,
      hideLabelRight: c.hideLabelRight,
      hideLabelBottom: c.hideLabelBottom,
      maxLength: c.maxLength || 20,
      onlyNumber: c.onlyNumber !== undefined ? c.onlyNumber : false,
      useDecimal: c.useDecimal !== undefined ? c.useDecimal : false,
      useSign: c.useSign !== undefined ? c.useDecimal : false,
      extraLabelRight: c.extraLabelRight,
      extraLabelSub: c.extraLabelSub,
      placeholder: c.placeholder,
    });

    // Ritorno classe di stile e dati
    return { css, data };
  }

}
