/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { CommonConsts } from '../../../consts/common-consts.consts';
import { ScrivaUtilitiesService } from '../../../services/scriva-utilities/scriva-utilities.service';
import {
  ScrivaFormInputCommon,
  ScrivaFormInputCss,
} from '../form-input/utilities/form-input.classes';
import { ScrivaLabelPosition } from '../form-input/utilities/form-input.enums';
import { ConfigurazioneElementoAppVo } from 'src/app/core/vo/configurazioni-elementi-app-vo';
import { ConfigurazioniElementiAppService } from 'src/app/core/services/configurazioni-elementi-app.service';

@Component({
  selector: 'scriva-input-label',
  templateUrl: './scriva-input-label.component.html',
  styleUrls: ['./scriva-input-label.component.scss'],
})
export class ScrivaInputLabelComponent<T> implements OnInit {
  /** Oggetto di costanti comuni all'applicazione. */
  C_C = new CommonConsts();

  /** Input che definisce le configurazioni per gli stili della input. */
  @Input() cssConfig: ScrivaFormInputCss;
  /** Input che definisce le configurazioni per i dati della input. */
  @Input() dataConfig: T | any;

  /** Input che definisce la posizione della label. */
  @Input() position = ScrivaLabelPosition.top;

  /** String che definisce il nome del FormControl a cui è assegnato il componente. */
  @Input('idFormControl') idFC: string;
  /** FormGroup a cui fa riferimento il componente. */
  @Input() formGroup: FormGroup;
  /** String che definisce l'id del pradre come id del DOM. */
  @Input() idDOM: string;

  /** Boolean che disabilita il controllo sul FormControl required. */
  @Input('disableRequiredCheck') disableRequired = true;

  constructor(
    private _scrivaUtilities: ScrivaUtilitiesService,
    private _configurazioniEA: ConfigurazioniElementiAppService
  ) {}

  ngOnInit() {
    // Verifico che esistano i dati input
    if (!this.idFC) {
      throw new Error('idFormControl not defined');
    }
    if (!this.formGroup) {
      throw new Error('formGroup not defined');
    }
  }

  /**
   * Getter che verifica se il form control è obbligatorio.
   */
  get require() {
    // Verifico se è abilitato il check di controllo
    if (this.disableRequired) {
      return false;
    }

    // Variabili di comodo
    const fg = this.formGroup;
    const idFC = this.idFC;
    const req = 'required';

    // Verifico se esistono dei validatori ed esiste il required
    return this._scrivaUtilities.hasValidator(fg, idFC, req);
  }

  /**
   * Getter per lo status della proprietà: label; unito con la direzione.
   */
  get checkLabel() {
    // Definisco le condizioni per label
    const checkLT = this.dataConfig?.label !== undefined;
    const checkLL = this.dataConfig?.labelLeft !== undefined;
    const checkLR = this.dataConfig?.labelRight !== undefined;
    const checkLB = this.dataConfig?.labelBottom !== undefined;

    // Verifico la posizione
    switch (this.position) {
      case ScrivaLabelPosition.top:
        return checkLT;
      case ScrivaLabelPosition.left:
        return checkLL;
      case ScrivaLabelPosition.right:
        return checkLR;
      case ScrivaLabelPosition.bottom:
        return checkLB;
      default:
        return false;
    }
  }

  /**
   * Getter per il dato della proprietà: label; a seconda della posizione.
   */
  get dataLabel() {
    // Recupero la configurazione per l'input, forzando la formattazione
    const dataConfig: ScrivaFormInputCommon = this.dataConfig;
    // Recupero l'eventuale configurazione per l'elemento applicativo della label
    const keyLabel: string = dataConfig.labelDBConfigKey;
    // Cerco all'interno delle configurazioni del servizio l'eventuale configurazione per la label
    const labelCEA: ConfigurazioneElementoAppVo =
      this._configurazioniEA.getConfigurazioneElementoApp(keyLabel);

    // Verificare che effettivamente sia stata restituita una configurazione
    if (labelCEA) {
      // L'oggetto di configurazione esiste, ritorno il valore
      return labelCEA.valore;
      // #
    }
    // Verifico la posizione
    switch (this.position) {
      case ScrivaLabelPosition.top:
        return this.dataConfig.label;
      case ScrivaLabelPosition.left:
        return this.dataConfig.labelLeft;
      case ScrivaLabelPosition.right:
        return this.dataConfig.labelRight;
      case ScrivaLabelPosition.bottom:
        return this.dataConfig.labelBottom;
      default:
        return false;
    }
  }

  /**
   * Getter per il dato della proprietà: hide; a seconda della posizione.
   */
  get hideLabel() {
    // Verifico la posizione
    switch (this.position) {
      case ScrivaLabelPosition.top:
        return this.dataConfig.hideLabel;
      case ScrivaLabelPosition.left:
        return this.dataConfig.hideLabelLeft;
      case ScrivaLabelPosition.right:
        return this.dataConfig.hideLabelRight;
      case ScrivaLabelPosition.bottom:
        return this.dataConfig.hideLabelBottom;
      default:
        return false;
    }
  }

  /**
   * Getter per lo status della proprietà: extraLabelRight.
   */
  get checkExtraLabelRight() {
    return this.dataConfig?.extraLabelRight !== undefined;
  }

  /**
   * Getter per lo status della proprietà: extraLabelSub.
   */
  get checkExtraLabelSub() {
    return this.dataConfig?.extraLabelSub !== undefined;
  }

  /**
   * Getter per la posizione della label: top.
   */
  get isLabelTop() {
    return this.position === ScrivaLabelPosition.top;
  }

  /**
   * Getter per la posizione della label: bottom.
   */
  get isLabelBottom() {
    return this.position === ScrivaLabelPosition.bottom;
  }

  /**
   * Getter per la posizione della label: left.
   */
  get isLabelLeft() {
    return this.position === ScrivaLabelPosition.left;
  }

  /**
   * Getter per la posizione della label: right.
   */
  get isLabelRight() {
    return this.position === ScrivaLabelPosition.right;
  }
}
