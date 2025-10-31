/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Istanza } from '../../../../../shared/models';
import { OggettoIstanza, Opera } from '../../../models';

/**
 * Interfaccia di comodo che definisce i parametri da passare alla funzione di associazioni opere/oggettiIstanza.
 */
export interface IAssociaOggettiIstanza {
  /** OggettoIstanza[] come lista di oggetti da gestire e salvare. */
  oggettiIstanza: OggettoIstanza[];
  /** Opera[] che definisce la lista di oggetti come base dati di supporto per la compilazione della lista oggettiIstanza. */
  opere: Opera[];
  /** Istanza con le informazioni d'associare all'oggetto OggettoIstanza. */
  istanza: Istanza;
  /** Id Oggetto Istanza padre da associare all'oggetto OggettoIstanza. */
  idOggettoIstanzaPadre?: number;
}
