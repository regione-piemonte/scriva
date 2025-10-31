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
  OnInit,
  ViewChild,
} from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';
import { FormInputsService } from 'src/app/shared/services/form-inputs/form-inputs.service';
import { ScrivaUtilitiesService } from 'src/app/shared/services/scriva-utilities/scriva-utilities.service';
import { FormInputComponent } from '../form-input/form-input.component';
import { ScrivaFormInputSearch } from '../form-input/utilities/form-input.classes';

@Component({
  selector: 'scriva-search',
  templateUrl: './scriva-search.component.html',
  styleUrls: ['./scriva-search.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => ScrivaSearchComponent),
      multi: true,
    },
  ],
})
export class ScrivaSearchComponent
  extends FormInputComponent<ScrivaFormInputSearch>
  implements OnInit, ControlValueAccessor
{
  /** ViewChild che definisce il collegamento tra l'input del DOM e il componente. */
  @ViewChild('riscaSearch') riscaSearch: ElementRef;

  /** ScrivaFormInputSearch che definisce le configurazioni per i dati della input. */
  dataConfig: ScrivaFormInputSearch;

  /**
   * Costruttore.
   */
  constructor(
    formInputs: FormInputsService,
    riscaUtilities: ScrivaUtilitiesService
  ) {
    // Richiamo il super
    super(formInputs, riscaUtilities);
  }

  ngOnInit() {
    // Verifico che esistano i dati input richiamando il super
    super.ngOnInit();
  }

  /**
   * Funzione agganciata all'evento blur della input.
   */
  onInputBlur() {
    // Verifico che la modalità di aggiornamento sia blur
    if (!this.inputUpdOnBlur) {
      // Non configurato, non gestisco il valore
      return;
    }

    // Richiamo la gestione del valore input
    this.handleValue();
  }

  /**
   * Funzione agganciata all'evento change della input.
   */
  onInputChange() {
    // Verifico che la modalità di aggiornamento sia change
    if (!this.inputUpdOnChange) {
      // Non configurato, non gestisco il valore
      return;
    }

    // Richiamo la gestione del valore input
    this.handleValue();
  }

  /**
   * Funzione che gestisce il valore della input e la sua emissione.
   */
  private handleValue() {
    // Effettuo il trim automatico
    this.trimValue();
    // Applico la trasformazione del testo
    this.transformText();
    // Lancio il change del valore
    this.onChange(this.value);
  }

  /**
   * Funzione che gestisce l'evento di: keydown.enter.
   * @param event KeyboardEvent contenente le informazioni dell'evento della tastiera.
   * @param element HTMLElement contenente le informazioni dell'oggetto a cui è collegata la funzione.
   * @override
   */
  onKeydownEnter(event: KeyboardEvent, element?: HTMLElement) {
    // Verifico la configurazione
    if (this.keydownEnter) {
      // Triggero il blur dell'elemento
      if (element) {
        // Lancio il blur del campo
        element?.blur();
        // #
      } else {
        // Richiamo manualmente il blur
        this.onInputBlur();
      }
      // Emetto l'evento di onInvio
      this.onInvio.emit(this.value);
    }
  }
}
