/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, forwardRef, Input, OnInit } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import {
  NgbDateParserFormatter,
  NgbDateStruct,
} from '@ng-bootstrap/ng-bootstrap';
import { Moment } from 'moment';
import { ScrivaDatesFormat } from '../../../enums/scriva-utilities/scriva-utilities.enums';
import { FormInputsService } from '../../../services/form-inputs/form-inputs.service';
import { ScrivaUtilitiesService } from '../../../services/scriva-utilities/scriva-utilities.service';
import { ScrivaIcons } from '../../scriva-icon/utilities/scriva-icon.classes';
import { FormInputComponent } from '../form-input/form-input.component';
import { ScrivaFormInputDatepicker } from '../form-input/utilities/form-input.classes';
import { NgbDateCustomParserFormatter } from './utilities/scriva-datepicker.formatter';

@Component({
  selector: 'scriva-datepicker',
  templateUrl: './scriva-datepicker.component.html',
  styleUrls: ['./scriva-datepicker.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => ScrivaDatepickerComponent),
      multi: true,
    },
    { provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter },
  ],
})
export class ScrivaDatepickerComponent
  extends FormInputComponent<ScrivaFormInputDatepicker>
  implements OnInit, ControlValueAccessor
{
  /** ScrivaFormInputDatepicker che definisce le configurazioni per i dati della input. */
  dataConfig: ScrivaFormInputDatepicker;
  /** ScrivaIconsMap con le icone dell'applicazione. */
  icons = ScrivaIcons;

  /** Input ScrivaOutputFormat che definisce quale sarà il formato in output per il date picker nel form group. Per default è: ScrivaOutputFormat.moment. */
  @Input() outputFormat: ScrivaDatesFormat = ScrivaDatesFormat.moment;

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
   * Funzione collegata all'evento di data selezionata.
   */
  onDateSelect() {
    // Formatto la data in un Moment e ne restituisco il valore
    const mValue = this.convertOutput(this._value);
    // Emetto il cambio del valore
    this.onChange(mValue);
  }

  /**
   * Funzione di reset dati per il calendario.
   */
  resetCalendario() {
    // Resetto il valore
    this._value = null;
    // Emetto il cambio del valore
    this.onChange(this._value);
  }

  /**
   * #####################################
   * FUNZIONI DI UTILITY PER IL COMPONENTE
   * #####################################
   */

  /**
   * Funzione di conversione della data in output secondo la configurazione in input.
   * @param value NgbDateStruct con la data selezionata dall'input.
   */
  private convertOutput(date: NgbDateStruct): Moment | NgbDateStruct | string {
    // Verifico l'input
    if (!date) {
      // Ritorno data invalida
      return null;
    }

    // Recupero il formato di output
    const output = this.outputFormat;
    // Richiamo la funzione di conversione passando la data e l'ouput desiderato
    return this._scrivaUtilities.convertNgbDateStructToType(date, output);
  }

  /**
   * ###############
   * GETTER & SETTER
   * ###############
   */

  /**
   * Getter per la risorsa dell'icona calendario.
   * @returns string con il path per la visualizzazione del calendario.
   */
  get iconCalendar() {
    // Genero il path per il calendario
    return `${this.icons.assets}${this.icons.calendar}`;
  }

  /**
   * Getter per la risorsa dell'icona calendario disabilitato.
   * @returns string con il path per la visualizzazione del calendario disabilitato.
   */
  get iconCalendarDisabled() {
    // Genero il path per il calendario
    return `${this.icons.assets}${this.icons.calendarDisabled}`;
  }
}
