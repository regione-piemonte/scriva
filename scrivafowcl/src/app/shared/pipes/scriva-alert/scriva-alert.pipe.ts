/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Pipe, PipeTransform } from '@angular/core';
import { ScrivaAlertConfigs } from '../../components/scriva-alert/utilities/scriva-alert.classes';
import { SAlertBox, SAlertBoxIcon } from '../../components/scriva-alert/utilities/scriva-alert.enums';
import { ScrivaInfoLevels } from '../../enums/scriva-utilities/scriva-utilities.enums';

/** Costante che identifica un type per il componente: DANGER */
const TYPE_DANGER = ScrivaInfoLevels.danger;
/** Costante che identifica un type per il componente: INFO */
const TYPE_INFO = ScrivaInfoLevels.info;
/** Costante che identifica un type per il componente: SUCCESS */
const TYPE_SUCCESS = ScrivaInfoLevels.success;
/** Costante che identifica un type per il componente: WARNING */
const TYPE_WARNING = ScrivaInfoLevels.warning;

/**
 * Pipe che gestisce le classi di stile per l'alert box applicativo.
 */
@Pipe({ name: 'alertBox' })
export class ScrivaAlertBoxPipe implements PipeTransform {
  /**
   * Costruttore del Pipe.
   */
  constructor() {}

  /**
   * Funzione che ritorna la classe di stile per l'alert box.
   * @param c ScrivaAlertConfig con le informazioni di configurazioni dell'alert.
   * @returns string con la classe di stile per l'alert box.
   */
  transform(c: ScrivaAlertConfigs): string {
    // Verifico che tipologia di pannello imbastire
    switch (c.type) {
      case TYPE_DANGER:
        // Alert di errore
        return SAlertBox.error;
      // #
      case TYPE_INFO:
        // Alert di info
        return SAlertBox.info;
      // #
      case TYPE_SUCCESS:
        // Alert di success
        return SAlertBox.success;
      // #
      case TYPE_WARNING:
        // Alert di warning
        return SAlertBox.warning;
      // #
      default:
        // Alert di info
        return SAlertBox.info;
    }
  }
}

/**
 * Pipe che gestisce le classi di stile per l'alert box icon applicativo.
 */
@Pipe({ name: 'alertBoxIcon' })
export class ScrivaAlertBoxIconPipe implements PipeTransform {
  /**
   * Costruttore del Pipe.
   */
  constructor() {}

  /**
   * Funzione che ritorna la classe di stile per l'alert box info.
   * @param c ScrivaAlertConfig con le informazioni di configurazioni dell'alert.
   * @returns string con la classe di stile per l'alert box info.
   */
  transform(c: ScrivaAlertConfigs): string {
    // Verifico che tipologia d'icona da visualizzare
    switch (c.type) {
      case TYPE_DANGER:
        // Alert di errore
        return SAlertBoxIcon.error;
      // #
      case TYPE_INFO:
        // Alert di info
        return SAlertBoxIcon.info;
      // #
      case TYPE_SUCCESS:
        // Alert di success
        return SAlertBoxIcon.success;
      // #
      case TYPE_WARNING:
        // Alert di warning
        return SAlertBoxIcon.warning;
      // #
      default:
        // Alert di info
        return SAlertBoxIcon.info;
    }
  }
}
