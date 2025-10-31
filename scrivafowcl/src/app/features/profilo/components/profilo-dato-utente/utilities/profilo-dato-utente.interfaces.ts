/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { DynamicObjString } from '../../../../../core/interfaces/scriva.interfaces';
import { IScrivaIcon } from '../../../../../shared/components/scriva-icon/utilities/scriva-icon.interfaces';

/**
 * Interfaccia che rappresenta la struttura dell'oggetto che definisce la configurazione per le azioni di un dato per profilo utente.
 */
export interface IPDUAction {
  id: string;
  icon: IScrivaIcon;
  title?: string;
  style?: DynamicObjString;
  disabled?: boolean;
}
