/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { FormioFormBuilderTypes } from './utilities/formio.types';

/**
 * Funzione di comodo che recupera l'identificativo di un componente formIo.
 * @param componenteFormIo FormioFormBuilderTypes con la struttura FormIo del componente che dovrebbe contenere le informazioni per il recupero dell'identificativo.
 * @returns string con l'identificativo trovato, altrimenti stringa vuota.
 */
export const identificativoComponenteFormIo = (
  componenteFormIo: FormioFormBuilderTypes
): string => {
  // Recupero la proprietà "key" della sezione
  const identificativoComponente: string = componenteFormIo?.key ?? '';
  // Ritorno l'identificativo
  return identificativoComponente;
};
