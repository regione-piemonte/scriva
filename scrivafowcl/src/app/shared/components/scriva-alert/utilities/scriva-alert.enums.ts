/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ScrivaInfoLevels } from '../../../enums/scriva-utilities/scriva-utilities.enums';

/**
 * Enum che definisce i possibili layout per l'alert.
 */
export enum SAlertLayouts {
  /** string che identifica il layout css: alert-container. */
  standard = 'alert-container',
  /** string che identifica il layout css: alert-container-compact. */
  compact = 'alert-container-compact',
}

/**
 * Enum che definisce le possibili classi per il layout dell'alert.
 */
export enum SAlertClasses {
  /** string che identifica una class per il container: DANGER */
  danger = 'alert-danger',
  /** string che identifica una class per il container: INFO */
  info = 'alert-info',
  /** string che identifica una class per il container: SUCCESS */
  success = 'alert-success',
  /** string che identifica una class per il container: WARNING */
  warning = 'alert-warning',
}

/**
 * Enum che definisce le possibili classi per la gestione del box dell'alert. Questa è la versione scriva e non risca.
 */
export enum SAlertBox {
  success = 'success',
  info = 'info',
  error = 'error',
  warning = 'warning',
}

/**
 * Enum che definisce le possibili classi per la gestione dell'icona dell'alert box. Questa è la versione scriva e non risca.
 */
export enum SAlertBoxIcon {
  success = 'fa-check-circle-o',
  info = 'fa-info-circle',
  error = 'fa-times-circle-o',
  warning = 'fa-exclamation-circle',
}

/**
 * Enum che definisce le possibili classi per il layout dell'alert.
 */
export enum SAlertIcons {
  /** string che identifica il path soruce per l'icona di: DANGER */
  danger = 'assets/icon-alert.svg',
  /** string che identifica il path soruce per l'icona di: INFO */
  info = 'assets/icon-info.svg',
  /** string che identifica il path soruce per l'icona di: SUCCESS */
  success = 'assets/icon-success.svg',
  /** string che identifica il path soruce per l'icona di: WARNING */
  warning = 'assets/icon-alert-info.svg',
}

/**
 * Enum che mappa i codici tipo messeggio e lo style d'associare all'alert. La mappa risulta tra: ScrivaCodTipiMessaggioUtente (valore) e RAlertClasses (chiave).
 * Verde   1	P	Messaggi feedback positivo
 * Rosso   2	E	Messaggi di errore
 * Azzurro 3	A	Messaggio di avviso legato ad un’azione
 * Azzurro 4	I	Messaggi Informativi
 * Azzurro 5	F	Messaggi di controllo formale
 * Rosso   6	C	Messaggi di errore calcolo canone
 * Azzurro 7	U	Messaggio relativo ad abilitazione utente
 */
export enum ScrivaCodTMStiliAlert {
  P = ScrivaInfoLevels.success,
  E = ScrivaInfoLevels.danger,
  A = ScrivaInfoLevels.info,
  I = ScrivaInfoLevels.info,
  F = ScrivaInfoLevels.info,
  C = ScrivaInfoLevels.danger,
  U = ScrivaInfoLevels.info,
}
