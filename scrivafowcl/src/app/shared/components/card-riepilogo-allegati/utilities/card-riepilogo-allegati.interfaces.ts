/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { TableColumn } from '@swimlane/ngx-datatable';
import { ScrivaServerError } from '../../../../core/classes/scriva.classes';
import { Allegato } from '../../../../features/ambito/models';

/**
 * Interfaccia che definisce le configurazioni per la classe omonima.
 */
export interface ICRATableConfigs {
  /** string che definisce il nome della colonna. */
  columnLabel?: string;
  /** number che definisce la dimensione per la colonna. */
  colWidth?: number;
  /** number che definisce la dimensione minima per la colonna. */
  colMinWidth?: number;
  /** number che definisce la dimensione massima per la colonna. */
  colMaxWidth?: number;
  /** boolean che definisce la possibilità di sort della colonna. */
  colSortable?: boolean;
}

/**
 * Interfaccia che estende l'interfaccia TableColumn come configurazione delle colonne delle tablle ngx-datatable, e aggiunge delle informazioni specifiche per una tabella.
 */
export interface ICRATableColumn extends TableColumn {
  /** Configurazione specifica per gestire la trasformazione dati. */
  getter?: {
    transform?: (value: Allegato) => string;
  };
  /** Configurazione specifica per gestire la formattazione delle date. */
  formatDate?: {
    transform?: (value: Allegato) => string;
  };
}

/**
 * Interfaccia che definisce il risultato dell'operazione di pubblicazione/annullamento allegati.
 */
export interface ICRAPubblicazioneAllegati {
  /** Partial<Allegato>[] con la lista di allegati che si è cercato di pubblicare/annullare. */
  allegati: Partial<Allegato>[];
  /** (Partial<Allegato> | ScrivaServerError)[] con le informazioni del risultato per la pubblicazione/annullamento. */
  risultati: (Partial<Allegato> | ScrivaServerError)[];
}
