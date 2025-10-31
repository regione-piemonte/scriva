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
 * Interfaccia con le informazioni per la gestione del check sul campo portata media per DER.
 */
export interface ICheckMinimoMassimo {
  minimo: string;
  massimo: string;
  checkZero: boolean;
  massimoNotZero?: boolean;
}

/**
 * Interfaccia con le informazioni per la gestione del check sul campo portata media per DER.
 */
export interface IDERCheckPortataMedia extends ICheckMinimoMassimo {
  portataMedia: string;
  portataMassima: string;
  checkZero: boolean;
}
