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
import { IScrivaIcon } from './utilities/scriva-icon.interfaces';
import { CommonConsts } from '../../consts/common-consts.consts';

@Component({
  selector: 'scriva-icon',
  templateUrl: './scriva-icon.component.html',
  styleUrls: ['./scriva-icon.component.scss'],
})
export class ScrivaIconComponent implements OnInit {
  /** Oggetto contenente i valori costanti dell'applicazione. */
  C_C = new CommonConsts();
  /** Input IScrivaIcon con la configurazione per la gestione dell'icona. */
  @Input('icon') iconConfig: IScrivaIcon;

  /**
   * Costruttore.
   */
  constructor() {}

  /**
   * NgOnInit.
   */
  ngOnInit() {}

  /**
   * ###############
   * GETTER & SETTER
   * ###############
   */

  /**
   * Getter di comodo che genera una stringa con riferimento alle icone di assets del progetto.
   * @returns string con il path per il caricamento dell'icona definita nella cartella di assets.
   */
  get iconAsset(): string {
    // Definisco il path base
    const basePath = 'assets/';
    // Concateno il path con il nome dell'icona
    const icon = `${basePath}${this.iconConfig?.icon ?? ''}`;

    // Ritorno l'icona
    return icon;
  }
}
