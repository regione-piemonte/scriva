/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { DynamicObjString } from '../../../../core/interfaces/scriva.interfaces';
import { ICRATableConfigs } from './card-riepilogo-allegati.interfaces';

/**
 * Classe che definisce le configurazioni grafiche per la gestione della tabella.
 * La configurazione è dedicata al componente: card-riepilogo-allegati.
 */
export class CRATableConfigs {
  /** string che definisce il nome della colonna. */
  columnLabel: string;
  /** number che definisce la dimensione per la colonna. */
  colWidth: number;
  /** number che definisce la dimensione minima per la colonna. */
  colMinWidth: number;
  /** number che definisce la dimensione massima per la colonna. */
  colMaxWidth: number;
  /** boolean che definisce la possibilità di sort della colonna. */
  colSortable: boolean;

  constructor(iCRATC: ICRATableConfigs) {
    this.columnLabel = iCRATC?.columnLabel;
    this.colWidth = iCRATC?.colWidth;
    // 100 è il default definito dalla ngx-datatable
    this.colMinWidth = iCRATC?.colMinWidth ?? iCRATC?.colWidth ?? 100;
    this.colMaxWidth = iCRATC?.colMaxWidth ?? iCRATC?.colWidth;
    this.colSortable = iCRATC?.colSortable ?? false;
  }

  /**
   * Getter che genera lo stile per la colonna.
   * @returns DynamicObjString con l'oggetto compatibile con la direttiva NgStyle.
   */
  get columnStyle(): DynamicObjString {
    // Genero le singole informazioni sugli stili
    const width: string = this.colWidth ? `${this.colWidth}px` : '';
    const minWidth: string = this.colMinWidth ? `${this.colMinWidth}px` : '';

    // Genero e ritorno la configurazione per la colonna
    return { width, 'min-width': minWidth };
  }

  /**
   * Getter che genera la label per la tabella secondo l'indicazione di "nuova riga".
   * @returns string con la label manipolata.
   * @author Ismaele Bottelli
   * @jira SCRIVA-1447 la collega Barbara Simone ha richiesto di spezzare la label se composta da più parole, mandando su una nuova la seconda parola.
   */
  get labelColumnBeakLine(): string {
    // Recupero la label dalla classe
    const label: string = this.columnLabel ?? '';
    // Tento di generare una label per la tabella
    try {
      // Aggiungio un break line ad ogni spazio bianco
      return label?.trim()?.replace(/\s/g, '<br>');
      // #
    } catch (e) {
      // Defaul
      return label;
    }
  }
}
