/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {
  ScrivaButtonConfig,
  ScrivaButtonCss,
} from '../../../classes/scriva-utilities/scriva-utilities.classes';
import { CommonConsts } from '../../../consts/common-consts.consts';
import { ScrivaButtonTypes } from '../../../enums/scriva-utilities/scriva-utilities.enums';
import { generateRandomId } from '../../../services/scriva-utilities/scriva-utilities.functions';

@Component({
  selector: 'scriva-button',
  templateUrl: './scriva-button.component.html',
  styleUrls: ['./scriva-button.component.scss'],
})
export class ScrivaButtonComponent implements OnInit {
  /** Oggetto contenente i valori costanti dell'applicazione. */
  C_C = new CommonConsts();
  /** Variabile che contiene le informazioni dell'enum ScrivaButtonTypes, per l'utilizzo nel DOM. */
  scrivaButtonTypes = ScrivaButtonTypes;

  /** Input che definisce le configurazioni per gli stili della input. */
  @Input() cssConfig: ScrivaButtonCss;
  /** Input che definisce le configurazioni per i dati della input. */
  @Input() dataConfig: ScrivaButtonConfig;

  /** Output che definisce l'evento di: click. */
  @Output() onClick = new EventEmitter<any>();

  /** Boolean che definisce lo stato di disabled del pulsante. */
  disabled: boolean = false;
  /** String che definisce un id univoco per l'istanza del componente. */
  idDOM: string = 'scrivaButtonDropdown';

  /** Input setter per il disabled del button. */
  @Input() set disable(disabled: boolean) {
    // Assegno il disabled
    this.disabled = disabled;
  }

  /**
   * Costruttore
   */
  constructor() {
    // Lancio il steup del componente
    this.setupComponenteRB();
  }

  ngOnInit() {}

  /**
   * #################
   * FUNZIONI DI SETUP
   * #################
   */

  /**
   * Funzione di setup del componente.
   */
  private setupComponenteRB() {
    // Lancio la generazione dell'id da usare per il dom
    this.idDOM = `${this.idDOM}-${generateRandomId(1)}`;
  }

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  /**
   * Getter per il type del button.
   */
  get typeButton() {
    // Verifico se esiste la configurazione
    return this.cssConfig?.typeButton ?? this.scrivaButtonTypes.primary;
  }

  /**
   * Getter per la classe di stile per la svg associata al type button.
   */
  get typeButtonSvg() {
    // Richiamo il type button
    return `${this.typeButton}-svg`;
  }
}
