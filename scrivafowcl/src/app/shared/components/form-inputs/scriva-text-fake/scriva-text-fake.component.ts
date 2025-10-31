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
} from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { FormInputsService } from '../../../services/form-inputs/form-inputs.service';
import { ScrivaUtilitiesService } from '../../../services/scriva-utilities/scriva-utilities.service';
import { FormInputComponent } from '../form-input/form-input.component';
import { ScrivaFormInputText } from '../form-input/utilities/form-input.classes';

@Component({
  selector: 'scriva-text-fake',
  templateUrl: './scriva-text-fake.component.html',
  styleUrls: ['./scriva-text-fake.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => ScrivaTextFakeComponent),
      multi: true,
    },
  ],
})
export class ScrivaTextFakeComponent
  extends FormInputComponent<ScrivaFormInputText>
  implements OnInit, ControlValueAccessor
{
  /** ViewChild che definisce il collegamento tra l'input del DOM e il componente. */
  @ViewChild('scrivaTextFake') scrivaTextFake: ElementRef;

  /** Boolean che permette di definire se l'input da gestire è un number. */
  @Input() isNumber = false;
  /** number che definisce quanti decimali visualizzare per il valore SE isNumber è true. */
  @Input('decimali') decimals: number = 2;
  /** boolean che definisce se visualizzare gli 0 come decimali non significativi SE isNumber è true. */
  @Input() decimaliNonSignificativi: boolean = true;

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

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  /**
   * Getter per value.
   */
  get value(): any {
    // Verifico se la gestione del value è number
    if (this.isNumber && !isNaN(Number(this._value))) {
      // Riconverto l'input in number
      return Number(this._value);
    }

    // Ritorno il suo normale valore
    return this._value;
  }

  /**
   * Setter per value.
   */
  set value(v: any) {
    // Verifico se la gestione del value è number
    if (this.isValueNumber(v)) {
      // Riconverto l'input in number
      this._value = this.formatImportoITA(v);
      // #
    } else {
      // Valore normale
      this._value = v;
    }
  }

  /**
   * Getter di comodo che verifica se il valore di _value è un number anche per configurazione del componente.
   * @returns boolean con il risultato del check.
   */
  private isValueNumber(value: any): boolean {
    // Verifico configurazione e conversione
    return this.isNumber && !isNaN(Number(value));
  }

  /**
   * #####################
   * FUNZIONI DI UTILITIES
   * #####################
   */

  /**
   * Formatta un importo mettendo una virgola a separare i decimali ed il punto per separare le migliaia.
   * La formattazione troncherà i numeri decimali.
   * @param importo number | string da formattare.
   * @returns string con la formattazione applicata.
   */
  formatImportoITA(importo: number | string): string {
    // Recupero le configurazioni dall'input
    const decimals = this.decimals ?? 2;
    const decimaliNonSignificativi = this.decimaliNonSignificativi ?? true;

    // Richiamo la funzione di comodo
    return this._scrivaUtilities.formatImportoITA(
      importo,
      decimals,
      decimaliNonSignificativi
    );
    // #
  }
}
