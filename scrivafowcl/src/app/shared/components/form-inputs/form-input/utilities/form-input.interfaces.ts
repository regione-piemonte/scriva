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
import {
  DynamicObjAny,
  DynamicObjString,
} from '../../../../../core/interfaces/scriva.interfaces';
import { TScrivaFormInputChoiceOrientation } from './form-input.types';
import { ScrivaAppendixPosition } from './form-input.enums';

/**
 * Interfaccia di comodo che definisce le proprietà per la gestion del css per i componenti scriva-form-input.
 */
export interface IScrivaFormInputCss {
  customForm?: string | any;
  customFormCheck?: string;
  customInput?: string;
  customLabel?: string;
  customError?: string;
  showErrorFG?: boolean;
  showErrorFC?: boolean;
  labelColLeft?: string | any;
  labelColRight?: string | any;
  appendix?: IScrivaAppendix;
}
/**
 * Interfaccia che definisce le proprietà per la gestione dell'appendice per le input di SCRIVA.
 */
export interface IScrivaAppendix {
  text: string;
  position?: ScrivaAppendixPosition;
}

/**
 * Interfaccia personalizzata che definisce la struttura per l'aggiunta del nome del campo a seguito dell'estrazione degli errori.
 */
export interface IMappaNomeCampo {
  label: string;
  formControlName: string;
}

/**
 * Interfaccia personalizzata che definisce la struttura per il recupero dei messaggi d'errore, partendo dagli errori di una form.
 */
export interface IMappaErroriFormECodici {
  /** erroreForm è la proprietà dell'oggetto errors della form. */
  erroreForm: string;
  /** erroreCodice è il codice fornitoci da CSI e presente nella documentazione degli errori. */
  erroreCodice: string;
  /** nomeCampo è un oggetto che permette d'inserire, nel messaggio d'errore, il nome del campo in errore. Valido solo per i FormControl. */
  nomeCampo?: IMappaNomeCampo;
}

/**
 * Interfaccia che definisce il tipo di oggetto accettato dai componenti di selezione mediante checkbox e radio.
 */
export interface IScrivaChoiceData extends DynamicObjAny {
  id: string;
  value: any;
  label?: string;
  disabled?: boolean;
}

/**
 * Interfaccia che definisce il tipo di oggetto accettato dal componente: scriva-checkbox; come source.
 */
export interface IScrivaCheckboxData extends IScrivaChoiceData {
  check: boolean;
}

/**
 * Interfaccia che definisce il tipo di oggetto accettato dal componente: scriva-radio; come source.
 */
export interface IScrivaRadioData extends IScrivaChoiceData {}

/**
 * Interfaccia comune a tutte le componenti form-inputs.
 */
export interface IScrivaFormInputCommon {
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
  /** String che definisce la label dell'input, posizionata a destra come mini descrizione. */
  extraLabelSub?: string;
  /** String che definisce il placeholder da visualizzare all'interno della input. */
  placeholder?: string;
  /** Number che definisce la lunghezza massima del campo. */
  maxLength?: number;
  /** Boolean che definisce se l'input deve contenere solo numeri. */
  onlyNumber?: boolean;
  /** Boolean che definisce se l'input deve contenere anche i decimali, se "onlyNumber" è true. */
  useDecimal?: boolean;
  /** Boolean che definisce se l'input può contenere il segno (-), se "onlyNumber" è true. */
  useSign?: boolean;
  /** String che definisce il tooltip da visualizzare sulla label del componente. */
  title?: string;

  /** (value: any) => any; che permette di definire la logica di sanitizzazione delle input. */
  sanitizeLogic?: (value: any) => any;
}

/**
 * Intefaccia che definisce le configurazioni DATI utilizzate dai componenti form-inputs: text.
 */
export interface IScrivaFormInputText extends IScrivaFormInputCommon {}

/**
 * Intefaccia che definisce le configurazioni DATI utilizzate dai componenti form-inputs: email.
 */
export interface IScrivaFormInputEmail extends IScrivaFormInputCommon {}

/**
 * Intefaccia che definisce le configurazioni DATI utilizzate dai componenti form-inputs: typeahead.
 */
export interface IScrivaFormInputTypeahead extends IScrivaFormInputCommon {
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
  /** Boolean che definisce se la ricerca è triggerata dal click */
  showOnClick?: boolean;
}

/**
 * Intefaccia che definisce le configurazioni DATI utilizzate dai componenti form-inputs: number.
 */
export interface IScrivaFormInputNumber extends IScrivaFormInputCommon {
  /** Number che definisce il minimo impostabile per la input. */
  min?: number;
  /** Number che definisce il massimo impostabile per la input. */
  max?: number;
  /** Number che definisce il massimo impostabile per la input. */
  step?: number;
  /** Number che definisce il numero di decimali da gestire per la input. */
  decimals?: number;
  /** boolean che permette di mantenere i decimali 0 (non significativi) se la quantità di decimali non raggiungesse il numero di decimali impostati. */
  decimaliNonSignificativi?: boolean;
  /** boolean che permette di attivare il separatore delle migliaia. */
  allowThousandsSeparator?: boolean;
}

/**
 * Intefaccia che definisce le configurazioni DATI utilizzate dai componenti form-inputs: selezione (checkbox, radio).
 */
export interface IScrivaFormInputChoice extends IScrivaFormInputCommon {
  /** String che definisce l'orientamento dei selettori. */
  orientation?: TScrivaFormInputChoiceOrientation;
}

/**
 * Interfaccia che definisce le configurazioni DATI utilizzate dai componenti form-inputs: datepicker.
 */
export interface IScrivaFormInputDatepicker extends IScrivaFormInputCommon {
  /** NgbDateStruct che definisce la data minima del calendario. */
  minDate: NgbDateStruct;
  /** NgbDateStruct che definisce la data massima del calendario. */
  maxDate: NgbDateStruct;
}

/**
 * Interfaccia personalizzata che definisce i parametri di configurazione per i date picker di bootstrap.
 */
export interface IScrivaDatepickerConfig {
  minDate?: NgbDateStruct;
  maxDate?: NgbDateStruct;
}

/**
 * Intefaccia che definisce le configurazioni DATI utilizzate dai componenti form-inputs: select.
 */
export interface IScrivaFormInputSelect extends IScrivaFormInputCommon {
  /** Boolean che definisce se la selecte deve comportarsi come una multi-select. */
  multiple: boolean;
  /** Boolean che definisce se aggiungere un elemento vuoto all'interno della select. */
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
}


/**
 * Interfaccia personalizzata che definisce la struttura per l'aggiunta del nome del campo a seguito dell'estrazione degli errori.
 */
export interface MappaNomeCampo {
  /** string che definisce la label semplice d'associare ad un form control. */
  label?: string;
  /** Funzione che permette di gestire della logica custom per la visualizzazione del messaggio d'errore dinamico. Se definita insieme alla proprietà "label" questa proprietà avrà meno priorità. */
  erroreDinamico?: (e: any, msg: string) => string;
}



