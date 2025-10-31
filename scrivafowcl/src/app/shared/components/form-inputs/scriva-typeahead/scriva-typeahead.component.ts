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
  Component,
  ElementRef,
  forwardRef,
  Input,
  OnInit,
  ViewChild,
  ViewEncapsulation,
} from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { NgbTypeaheadSelectItemEvent } from '@ng-bootstrap/ng-bootstrap';
import { fromEvent, merge, Observable, of, OperatorFunction, Subject, throwError } from 'rxjs';
import {
  catchError,
  debounceTime,
  distinctUntilChanged,
  filter,
  map,
  switchMap,
} from 'rxjs/operators';
import { FormInputsService } from '../../../services/form-inputs/form-inputs.service';
import { LoggerService } from '../../../services/logger.service';
import { ScrivaUtilitiesService } from '../../../services/scriva-utilities/scriva-utilities.service';
import { FormInputComponent } from '../form-input/form-input.component';
import { ScrivaFormInputTypeahead } from '../form-input/utilities/form-input.classes';
import { ITypeAheadDataValidazioneTmpl } from './utilities/scriva-typeahead.interfaces';

@Component({
  selector: 'scriva-typeahead',
  templateUrl: './scriva-typeahead.component.html',
  styleUrls: ['./scriva-typeahead.component.scss'],
  encapsulation: ViewEncapsulation.None,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => ScrivaTypeaheadComponent),
      multi: true,
    },
  ],
})
export class ScrivaTypeaheadComponent
  extends FormInputComponent<ScrivaFormInputTypeahead>
  implements OnInit, ControlValueAccessor
{
  /** Boolean che definisce se l'input può essere valorizzata con stringhe che definisce l'utente, senza selezionare un valore dal popup di consigli. */
  @Input('allowUserInput') editable = false;
  /** Boolean che permette di andare automaticamente a ricercare il valore tramite chiamata typeaheadSearch. Di default è false. */
  @Input('autoCompleteOnBlur') autoCompleteBlur = false;

  /** ViewChild che collega l'HTML delle input con il componente. */
  @ViewChild('inputTypeahead') inputTypeahead: ElementRef;

  /** ScrivaFormInputTypeahead che definisce le configurazioni per i dati della input. */
  dataConfig: ScrivaFormInputTypeahead;
  /** Funzione che viene eseguita nel momento in cui il valore della input cambia. Viene usata per lanciare la ricerca dei consigli. */
  typeaheadSearch: OperatorFunction<string, readonly any[]>;
  /** Funzione che viene eseguita per la formattazione dei valori ritornati dalla funzione "typeaheadSearch". */
  typeaheadMap: (v: any) => string;

  click$ = new Subject<void>();

  constructor(
    formInputs: FormInputsService,
    private _logger: LoggerService,
    scrivaUtilities: ScrivaUtilitiesService
  ) {
    // Richiamo il super
    super(formInputs, scrivaUtilities);
  }

  ngOnInit() {
    // Verifico che esistano i dati input richiamando il super
    super.ngOnInit();
    // Verifico che esistano i dati input
    if (!this.idFC) {
      throw new Error('idFormControl not defined');
    }
    if (!this.formGroup) {
      throw new Error('formGroup not defined');
    }
    if (!this.dataConfig.typeaheadSearch) {
      throw new Error('typeaheadSearch not defined');
    }
    if (!this.dataConfig.typeaheadMap) {
      throw new Error('typeaheadMap not defined');
    }

    // Lancio il setup del componente
    this.setupComponente();
  }

  /**
   * Funzione di supporto per il setup del componente.
   */
  private setupComponente() {
    // Assegno localmente la funzione di typingahead map
    this.typeaheadMap = this.dataConfig.typeaheadMap;
    // Definisco la funzione di search alla digitazione nella input
   
this.typeaheadSearch = (text$: Observable<string>) => {
  // Verifica se la proprietà showOnClick è true
  const click$ = this.dataConfig.showOnClick
    ? this.click$.pipe(
        // Trasforma l'evento click in una stringa vuota per avviare la ricerca
        map(() => '')
      )
    : of(); // Se showOnClick è false, ritorna un osservabile vuoto

  return merge(text$, click$).pipe(
    // Aspetto [debounceTime] millisecondi prima di far partire la ricerca
    debounceTime(this.dataConfig.debounceTime),
    // Faccio partire la ricerca solo se l'input è effettivamente cambiato
    distinctUntilChanged(),
    // Se la dimensione dell'input è >= a [searchOnLength] allora lancio la ricerca
    filter((term: string) => this.dataConfig.showOnClick || term.length >= this.dataConfig.searchOnLength),
    // Lancio la funzione di ricerca passando come parametro [term] alla funzione [typeaheadSearch]
    switchMap((term: string) => this.dataConfig.typeaheadSearch(term)),
    // Gestisco possibili errori di scarico dati
    catchError((err) => {
      // Loggo l'errore
      this._logger.error('typeaheadSearch', err);
      // Ritorno l'errore
      return throwError(err);
    })
  );
};
  }

  /**
   * Funzione di supporto che gestisce la conversione dei dati se la configurazione per la data validità è attiva.
   * @param list any[] che definisce la lista di elementi scaricati.
   * @returns any[] con la lista di elementi gestiti in base al flag di configurazione per data validità.
   */
  private dataValiditaConverter(list: any[]): any[] {
    // Verifico la configurazione per la data validità
    if (this.dataConfig.dataValidita) {
      // Rimappo la lista di oggetti nello specifico formato ITypeAheadDataValidazioneTmpl
      return list.map((e: any) => {
        // Definisco l'oggetto ITypeAheadDataValidazioneTmpl
        const newE: ITypeAheadDataValidazioneTmpl = {
          data: e,
          dataConfig: this.dataConfig,
          typeaheadMap: this.typeaheadMap,
        };
        // Ritorno l'oggetto specifico
        return newE;
      });
      // #
    } else {
      // Ritorno la lista per come è stata definita
      return list;
    }
  }

  /**
   * Funzione agganciata all'evento di selezione di un item dalla lista dei suggerimenti.
   * Questa funzione viene eseguita prima di valorizzare _value con il valore scelto.
   * @param selected NgbTypeaheadSelectItemEvent that contains the information of selected item.
   */
  onSelectItem(selected: NgbTypeaheadSelectItemEvent) {
    // Emetto l'evento di change del dato
    this.onChange(selected.item);
  }

  /**
   * Funzione di auto complete quando l'utente esce dalla input senza selezionare un suggerimento.
   */
  autoCompleteOnBlur() {
    // Definisco le casistiche di auto search
    const notEditable = !this.editable;
    const autoComplete = this.autoCompleteBlur;
    const valueUndefined = this.value === undefined;
    // Recupero il valore della input direttamente tramite viewChild, perché il modello bindato sulla input viene valorizzato solo nel momento in cui si seleziona un suggerimento dal popup
    const inputValue = this.inputTypeahead?.nativeElement?.value;

    // Verifico le casistiche per l'autocomplete
    if (notEditable && autoComplete && valueUndefined && inputValue) {
      // Definisco una funzione per la gestione degli errori
      const error = (error: any) => {
        this._logger.error('autoCompleteOnBlur', error);
      };

      // Lancio la funzione di typeaheadSearch
      this.dataConfig.typeaheadSearch(inputValue).subscribe({
        next: (r: any[]) => {
          this.autoCompleteOnBlurNext(r, inputValue);
        },
        error,
      });
    }
  }

  /**
   * Funzione agganciata al next della funzione di scarico dati.
   * @param results Array di any con i valori ritornati dalla chiamata di scarico dati.
   */
  private autoCompleteOnBlurNext(results: any[], inputValue: string) {
    // Verifico se esiste un solo risultato
    if (results && results.length === 1) {
      // Recupero l'oggetto dal risultato
      const result = results[0];
      // Recupero il valore per verificare la compatibilità del dato
      const resValueCompare = this.dataConfig.typeaheadMap(result);
      // Verifico se il valore nella input è contenuto nel valore restituito (CASE SENSITIVE!!!)
      const contains = resValueCompare
        .toUpperCase()
        .includes(inputValue.toUpperCase());

      // Verifico se la stringa è contenuta
      if (contains) {
        // Oggetto trovato, assegno le informazioni
        this.value = result;
        // Emetto l'evento di change
        this.onChange(result);
      }
    }

    // Applico la trasformazione del testo
    this.transformText();
  }

  onClick() {
    // Emetto l'evento di click
    this.click$.next();
  }
}
