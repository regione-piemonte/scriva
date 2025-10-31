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
 * Interfaccia che definisce la mappatura delle chiavi d'errore.
 */
interface IScrivaErrorsKeys {
  SCRIVA_CHECKBOX_NOT_TRUE: string;
  SCRIVA_DATE_MIN_DATE_MAX: string;
}

/**
 * Classe di mapping delle chiavi per gli erorri del form.
 */
export const ScrivaErrorsKeys: IScrivaErrorsKeys = {
  SCRIVA_CHECKBOX_NOT_TRUE: 'SCRIVA_CHECKBOX_TRUE',
  SCRIVA_DATE_MIN_DATE_MAX: 'SCRIVA_DATE_MIN_DATE_MAX',
};
