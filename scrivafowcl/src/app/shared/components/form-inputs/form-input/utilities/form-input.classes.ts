/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';
import { DynamicObjAny, DynamicObjString } from '../../../../../core/interfaces/scriva.interfaces';
import { SCOrientamento, ScrivaAppendixPosition } from './form-input.enums';
import {
  IScrivaAppendix,
  IScrivaFormInputChoice,
  IScrivaFormInputCommon,
  IScrivaFormInputCss,
  IScrivaFormInputDatepicker,
  IScrivaFormInputEmail,
  IScrivaFormInputNumber,
  IScrivaFormInputSelect,
  IScrivaFormInputText,
  IScrivaFormInputTypeahead,
} from './form-input.interfaces';
import { TScrivaFormInputChoiceOrientation } from './form-input.types';

/**
 * Classe che definisce le configurazioni CSS utilizzate dai componenti form-inputs.
 */
export class ScrivaFormInputCss {
  /** String or any, compatibile con le direttive NgClass e NgStyle, che definisce la classe CSS del contenitore del form-group. */
  customForm: string | any;
  /** String che definisce la classe CSS del contenitore del form-group. */
  customFormCheck: string;
  /** String or any, compatibile con le direttive NgClass e NgStyle, che definisce la classe CSS dell'input. */
  customInput: string | any;
  /** String or any, compatibile con le direttive NgClass e NgStyle, che definisce la classe CSS della label. */
  customLabel: string | any;
  /** String compatibile con le classi css, o oggetto compatibile con la direttiva NgStyle che definisce lo stile del container. */
  customError: string | any;
  /** Boolean che definisce se visualizzare eventuali messaggi d'errore per il form group. */
  showErrorFG: boolean;
  /** Boolean che definisce se visualizzare eventuali messaggi d'errore per il form control. */
  showErrorFC: boolean;

  /** String or any, compatibile con le direttive NgClass e NgStyle, che definisce la classe CSS per il contenitore della label di sinistra. */
  labelColLeft: string | any;
  /** String or any, compatibile con le direttive NgClass e NgStyle, che definisce la classe CSS per il contenitore della label di destra. */
  labelColRight: string | any;

  /** ScrivaAppendix con le informazioni da utilizzare per la gestione dell'appendice sulle input. */
  appendix?: ScrivaAppendix;

  constructor(c: IScrivaFormInputCss) {
    this.customForm = c.customForm || '';
    this.customFormCheck = c.customFormCheck || '';
    this.customInput = c.customInput || '';
    this.customLabel = c.customLabel || '';
    this.customError = c.customError || '';
    this.showErrorFG = c.showErrorFG !== undefined ? c.showErrorFG : false;
    this.showErrorFC = c.showErrorFC !== undefined ? c.showErrorFC : false;
    this.labelColLeft = c.labelColLeft || '';
    this.labelColRight = c.labelColRight || '';
    this.appendix = c.appendix ? new ScrivaAppendix(c.appendix) : undefined;
  }
}

/**
 * Classe che definisce le proprietà per la gestione dell'appendice per le input di SCRIVA.
 */
export class ScrivaAppendix {
  text: string;
  position: ScrivaAppendixPosition;

  constructor(c?: IScrivaAppendix) {
    this.text = c?.text ?? '-';
    this.position = c?.position ?? ScrivaAppendixPosition.right;
  }
}

/**
 * Classe che definisce le configurazioni DATI utilizzate dai componenti form-inputs.
 */
export class ScrivaFormInputCommon implements IScrivaFormInputCommon {
  /** String che definisce la label dell'input in posizione: sopra. */
  label?: string;
  /** String che definisce la chiave per il recupero della label dalla configurazione presente su DB. Se non definita, il valore di "label" verrà usato come default. */
  labelDBConfigKey?: string;
  /** String che definisce la label dell'input in posizione: sinistra. */
  labelLeft?: string;
  /** String che definisce la label dell'input in posizione: destra. */
  labelRight?: string;
  /** String che definisce la label dell'input in posizione: sotto. */
  labelBottom?: string;
  /** Boolean che definisce se la label dell'input deve essere nascosta (mantenendo lo spazio dell'HTML). */
  hideLabel?: boolean;
  /** Boolean che definisce se la label dell'input deve essere nascosta (mantenendo lo spazio dell'HTML). */
  hideLabelLeft?: boolean;
  /** Boolean che definisce se la label dell'input deve essere nascosta (mantenendo lo spazio dell'HTML). */
  hideLabelRight?: boolean;
  /** Boolean che definisce se la label dell'input deve essere nascosta (mantenendo lo spazio dell'HTML). */
  hideLabelBottom?: boolean;
  /** String che definisce la label dell'input, posizionata a destra come mini descrizione. */
  extraLabelRight?: string;
  /** String che definisce la label dell'input, posizionata sotto come mini descrizione. */
  extraLabelSub?: string;
  /** String che definisce il placeholder da visualizzare all'interno della input. */
  placeholder?: string;
  /** Number che definisce la lunghezza massima del campo. */
  maxLength: number;
  /** Boolean che definisce se l'input deve contenere solo numeri. */
  onlyNumber?: boolean;
  /** Boolean che definisce se l'input deve contenere anche i decimali, se "onlyNumber" è true. */
  useDecimal?: boolean;
  /** Boolean che definisce se l'input può contenere il segno (-), se "onlyNumber" è true. */
  useSign?: boolean;
  /** String che definisce il tooltip da visualizzare sulla label del componente. */
  title?: string;

  /** (value: any) => any; che permette di definire una logica di sanitizzazione della input. */
  sanitizeLogic?: (value: any) => any;

  /**
   * Costruttore.
   */
  constructor(c: IScrivaFormInputCommon) {
    this.label = c.label;
    this.labelDBConfigKey = c.labelDBConfigKey;
    this.labelLeft = c.labelLeft;
    this.labelRight = c.labelRight;
    this.labelBottom = c.labelBottom;
    this.hideLabel = c.hideLabel ?? false;
    this.hideLabelLeft = c.hideLabelLeft ?? false;
    this.hideLabelRight = c.hideLabelRight ?? false;
    this.hideLabelBottom = c.hideLabelBottom ?? false;
    this.extraLabelRight = c.extraLabelRight;
    this.extraLabelSub = c.extraLabelSub;
    this.placeholder = c.placeholder ?? '';
    this.maxLength = c.maxLength ?? 15;
    this.onlyNumber = c.onlyNumber ?? false;
    this.useDecimal = c.useDecimal ?? false;
    this.useSign = c.useSign ?? false;
    this.title = c.title ?? '';

    this.sanitizeLogic = c.sanitizeLogic ?? undefined;
  }
}

/**
 * Classe che definisce le configurazioni DATI utilizzate dai componenti form-inputs: text.
 */
export class ScrivaFormInputText
  extends ScrivaFormInputCommon
  implements IScrivaFormInputText
{
  constructor(c: IScrivaFormInputText) {
    super(c);
  }
}

/**
 * Classe che definisce le configurazioni DATI utilizzate dai componenti form-inputs: number.
 */
export class ScrivaFormInputNumber
  extends ScrivaFormInputCommon
  implements IScrivaFormInputNumber
{
  /** Number che definisce il minimo impostabile per la input. */
  min: number;
  /** Number che definisce il massimo impostabile per la input. */
  max: number;
  /** Number che definisce il massimo impostabile per la input. */
  step: number;
  /** Number che definisce il numero di decimali da gestire per la input. */
  decimals: number;
  /** boolean che permette di mantenere i decimali 0 (non significativi) se la quantità di decimali non raggiungesse il numero di decimali impostati. */
  decimaliNonSignificativi: boolean = false;
  /** boolean che permette di attivare il separatore delle migliaia. */
  allowThousandsSeparator: boolean;

  constructor(c: IScrivaFormInputNumber) {
    super(c);
    this.min = c.min;
    this.step = c.max;
    this.max = c.step;
    this.decimals = c.decimals ?? 0;
    this.decimaliNonSignificativi = c?.decimaliNonSignificativi ?? false;
    this.allowThousandsSeparator = c?.allowThousandsSeparator;
  }
}

/**
 * Classe che definisce le configurazioni DATI utilizzate dai componenti form-inputs: email.
 */
export class ScrivaFormInputEmail
  extends ScrivaFormInputCommon
  implements IScrivaFormInputEmail
{
  constructor(c: IScrivaFormInputEmail) {
    super(c);
    this.maxLength = c.maxLength || 40;
  }
}

/**
 * Classe che definisce le configurazioni DATI utilizzate dai componenti form-inputs: selezione (checkbox, radio).
 */
export class ScrivaFormInputChoice implements IScrivaFormInputChoice {
  /** String che definisce l'orientamento dell'input. */
  orientation: TScrivaFormInputChoiceOrientation;

  constructor(c: IScrivaFormInputChoice) {
    this.orientation = c.orientation || SCOrientamento.orizzontale;
  }
}

/**
 * Classe che definisce le configurazioni DATI utilizzate dai componenti form-inputs: checkbox.
 */
export class ScrivaFormInputCheckbox
  extends ScrivaFormInputChoice
  implements IScrivaFormInputChoice
{
  constructor(c: IScrivaFormInputChoice) {
    super(c);
  }
}

/**
 * Classe che definisce le configurazioni DATI utilizzate dai componenti form-inputs: radio.
 */
export class ScrivaFormInputRadio
  extends ScrivaFormInputChoice
  implements IScrivaFormInputChoice
{
  constructor(c: IScrivaFormInputChoice) {
    super(c);
  }
}

/**
 * Classe che definisce le configurazioni DATI utilizzate dai componenti form-inputs: typeahead.
 */
export class ScrivaFormInputTypeahead
  extends ScrivaFormInputCommon
  implements IScrivaFormInputTypeahead
{
  /** Number che definisce quando attivare la ricerca. */
  searchOnLength: number;
  /** Funzione utilizzata per la ricerca dei valori da visualizzare come suggerimento. Deve ritornare un Observable. */
  typeaheadSearch: (v: string) => Observable<any[]>;
  /** Funzione utilizzata per la mappatura dei risultati ritornati dalla funzione "typeaheadSearch". Verrà utilizzata anche per valorizzare l'input alla selezione dal popup. */
  typeaheadMap: (v: any) => string;
  /** Number che definisce i millisecondi di attesa prima di lanciare la funzione di ricerca. */
  debounceTime: number;
  /** Boolean che definisce se la lista di elementi deve essere gestita con il controllo di data_fine_validita e l'evidenziazione della riga. */
  dataValidita?: boolean;

  /** Boolean che definisce se la ricerca deve essere lanciata al click del mouse. */
  showOnClick?: boolean;

  constructor(c: IScrivaFormInputTypeahead) {
    super(c);
    this.searchOnLength = c.searchOnLength || 3;
    this.typeaheadSearch = c.typeaheadSearch;
    this.typeaheadMap = c.typeaheadMap;
    this.debounceTime = c.debounceTime || 300;
    this.dataValidita = c.dataValidita ?? false;
    this.showOnClick = c.showOnClick ?? false;
  }
}

/**
 * Classe che definisce le configurazioni DATI utilizzate dai componenti form-inputs: datepicker.
 */
export class ScrivaFormInputDatepicker
  extends ScrivaFormInputCommon
  implements IScrivaFormInputDatepicker
{
  /** NgbDateStruct che definisce la data minima del calendario. */
  minDate: NgbDateStruct;
  /** NgbDateStruct che definisce la data massima del calendario. */
  maxDate: NgbDateStruct;

  constructor(c: IScrivaFormInputDatepicker) {
    super(c);
    this.minDate = c.minDate;
    this.maxDate = c.maxDate;
  }
}

/**
 * Classe che definisce le configurazioni DATI utilizzate dai componenti form-inputs: select.
 */
export class ScrivaFormInputSelect
  extends ScrivaFormInputCommon
  implements IScrivaFormInputSelect
{
  /** Boolean che definisce se la selecte deve comportarsi come una multi-select. */
  multiple: boolean;
  /** Boolean che definisce se aggiungere un elemento vuoto all'inizio della select. */
  allowEmpty: boolean;
  /** String che definisce la label da visualizzare per l'elemento vuoto. */
  emptyLabel: string;
  /** Boolean che definisce se la empty label è selezionata per default. */
  emptyLabelSelected: boolean;
  /** Boolean che definisce il valore di default deve essere ignorato per i controlli. */
  ignoreDefault: boolean;
  /** Funzione che permette di manipolare l'oggetto gestito dalla select per un custom output come descrizione dell'option. */
  customOption?: (v: any) => string;
  /** Funzione che permette di manipolare la classe di stile per un'option della select. Il ritorno deve essere compatibile con la direttiva NgClass. */
  customOptionClass?: (v: any) => string[];
  /** Funzione che permette di manipolare lo stile per un'option della select. Il ritorno deve essere compatibile con la direttiva NgStyle. */
  customOptionStyle?: (v: any) => DynamicObjString;

  constructor(c: IScrivaFormInputSelect) {
    super(c);
    this.multiple = c.multiple !== undefined ? c.multiple : false;
    this.allowEmpty = c.allowEmpty !== undefined ? c.allowEmpty : false;
    this.emptyLabel = c.emptyLabel || '';
    this.emptyLabelSelected =
      c.emptyLabelSelected !== undefined ? c.emptyLabelSelected : false;
    this.ignoreDefault =
      c.ignoreDefault !== undefined ? c.ignoreDefault : false;
    this.customOption = c.customOption;
    this.customOptionClass = c.customOptionClass;
    this.customOptionStyle = c.customOptionStyle;
  }
}
/**
 * Classe che definisce le configurazioni DATI utilizzate dai componenti form-inputs: search.
 */
export class ScrivaFormInputSearch
  extends ScrivaFormInputCommon
  implements IScrivaFormInputText
{
  constructor(c: IScrivaFormInputText) {
    super(c);
  }
}
