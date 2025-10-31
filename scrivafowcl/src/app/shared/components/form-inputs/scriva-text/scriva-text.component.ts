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
  AfterViewInit,
  Component,
  ElementRef,
  forwardRef,
  OnInit,
  ViewChild,
} from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { FormInputsService } from '../../../services/form-inputs/form-inputs.service';
import { ScrivaUtilitiesService } from '../../../services/scriva-utilities/scriva-utilities.service';
import { FormInputComponent } from '../form-input/form-input.component';
import { ScrivaFormInputText } from '../form-input/utilities/form-input.classes';

@Component({
  selector: 'scriva-text',
  templateUrl: './scriva-text.component.html',
  styleUrls: ['./scriva-text.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => ScrivaTextComponent),
      multi: true,
    },
  ],
})
export class ScrivaTextComponent
  extends FormInputComponent<ScrivaFormInputText>
  implements OnInit, ControlValueAccessor, AfterViewInit
{
  /** ViewChild che definisce il collegamento tra l'input del DOM e il componente. */
  @ViewChild('scrivaText') scrivaText: ElementRef;

  /** ScrivaFormInputText che definisce le configurazioni per i dati della input. */
  dataConfig: ScrivaFormInputText;

  /**
   * Costruttore.
   */
  constructor(
    formInputs: FormInputsService,
    scrivaUtilities: ScrivaUtilitiesService
  ) {
    // Richiamo il super
    super(formInputs, scrivaUtilities);
  }

  ngOnInit() {
    // Verifico che esistano i dati input richiamando il super
    super.ngOnInit();
  }

  ngAfterViewInit() {}

  /**
   * Funzione agganciata all'evento blur della input.
   */
  onInputBlur() {
    // Verifico che la modalità di aggiornamento sia blur
    if (!this.inputUpdOnBlur) {
      // Non configurato, non gestisco il valore
      return;
    }

    // Lancio la funzione per verifica se ci sono logiche di sanitizzazione
    this.sanitizeValue();
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

    // Lancio la funzione per verifica se ci sono logiche di sanitizzazione
    this.sanitizeValue();
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
