/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**
 * Enum che definisce la posizione per il componente scriva-label.
 */
export enum ScrivaLabelPosition {
  top = 'top',
  left = 'left',
  right = 'right',
  bottom = 'bottom',
}

/**
 * Enum che definisce i possibili orientamenti per il componente scriva-checkbox/scriva-radio.
 */
export enum SCOrientamento {
  verticale = 'vertical',
  orizzontale = 'horizontal',
}

/**
 * Enum personalizzato per la gestione delle componenti gestite dal servizio scriva-form-builder.
 */
export enum ScrivaFormBuilderSize {
  small = 'small',
  standard = 'standard',
  x2 = 'x2',
  x3 = 'x3',
  full = 'full',
}

/**
 * Enum che riporta nel progetto i valori riferiti alla tipologia FormHooks.
 * @tutorial https://angular.io/api/forms/AbstractControl#updateOn
 */
export enum ScrivaFormHooks {
  change = 'change',
  blur = 'blur',
  submit = 'submit',
}

/**
 * Enum che definisce le posizioni per la gestione delle appendici delle input scriva.
 */
export enum ScrivaAppendixPosition {
  left = 'sinistra',
  right = 'destra',
}
