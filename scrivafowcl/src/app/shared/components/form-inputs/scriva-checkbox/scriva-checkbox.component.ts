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
import { FormInputsService } from '../../../services/form-inputs/form-inputs.service';
import { ScrivaUtilitiesService } from '../../../services/scriva-utilities/scriva-utilities.service';
import { FormInputComponent } from '../form-input/form-input.component';
import { ScrivaFormInputCheckbox } from '../form-input/utilities/form-input.classes';
import { IScrivaCheckboxData } from '../form-input/utilities/form-input.interfaces';

@Component({
  selector: 'scriva-checkbox',
  templateUrl: './scriva-checkbox.component.html',
  styleUrls: ['./scriva-checkbox.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => ScrivaCheckboxComponent),
      multi: true,
    },
  ],
})
export class ScrivaCheckboxComponent
  extends FormInputComponent<ScrivaFormInputCheckbox>
  implements OnInit, ControlValueAccessor
{
  /** Lista di oggetti IScrivaCheckboxData che contiene il pool di dati da visualizzare come voci. */
  @Input('dataSource') source: IScrivaCheckboxData[] = [];
  /** String che definisce il nome della proprietà da visualizzare come label del radio. */
  @Input() propertyToShow: string = 'label';
  /** Boolean che pilota il cambiamento del dato ritornando solo l'ultimo oggetto che ha subito l'evento "change". Questo può risultare comodo nel caso in cui ci sia solo una checkbox. */
  @Input('onlyLastValue') lastValue = false;

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

    // Verifico che esistano i dati input del componente
    this.initComponent();
    // Gestisco la label del componente
    this.handleLabel();
    // Lancio il primo aggiornamento per allineare il FormGroup
    this.onCheckboxChange(this.source[0]);
  }

  /**
   * Funzione di init delle informazioni del componente.
   */
  private initComponent() {
    // Verifico che esistano i dati input
    if (!this.source || this.source.length === 0) {
      throw new Error('source not defined');
    }
    if (!this.propertyToShow) {
      throw new Error('propertyToShow not defined');
    }
  }

  /**
   * Funzione di init della label del componente.
   */
  private handleLabel() {
    // Aggiungo una classe di stile alla label
    this.cssConfig.customLabel = `${this.cssConfig.customLabel} form-check-label`;
  }

  /**
   * Funzione collegata al change della checkbox.
   * @param checkbox IScrivaCheckboxData con la checkbox modificata.
   */
  onCheckboxChange(checkbox: IScrivaCheckboxData) {
    // Verifico se è richiesto solo l'ultimo valore
    if (this.lastValue) {
      // Emetto l'evento di change per l'ultimo valore
      this.onChange(checkbox);
      // #
    } else {
      // Ritorno i valori
      this.onChange(this.source);
    }
  }

  /**
   * Funzione di supporto che va ad effettuare un reset di tutti i valori delle checkbox, impostando il "check" a false.
   */
  reset() {
    // Rimappo l'array dati impostando a false tutti i valori
    this.source = this.source.map((e: IScrivaCheckboxData) => {
      // Aggiorno il valore di check a false
      e.check = false;
      // Ritorno l'oggetto
      return e;
    });

    // Recupero il primo elemento e gestisco il cambio valore normalmente
    this.onCheckboxChange(this.source[0]);
  }
}
