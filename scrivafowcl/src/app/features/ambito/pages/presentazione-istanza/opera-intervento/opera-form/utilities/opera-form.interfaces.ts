import { OggettoIstanza, Opera } from '../../../../../models';
import { GestioniDatiProgetto } from './opera-form.enums';

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
 * Interfaccia che definisce le proprietà per la gestione dei controlli dati per l'opera.
 */
export interface IValidaInfoDettaglio {
  /** any[] con la lista degli elementi gestiti dalla tabella dati. */
  tableSource: any[];
  /** any[] con la lista degli elementi selezionati dalla tabella. Dovrebbe avere 2 stati: array con almeno un elemento e array vuoto. Questo dovuto alla funzione di seleziona/deseleziona tutti. */
  selectedFromTable: any[];
}

/**
 * Interfaccia che definisce le informazioni ritornate nel momento in cui l'utente "submitta" i dati del progetto.
 */
export interface IOperaFormPayload {
  action: GestioniDatiProgetto;
  opera: Opera | OggettoIstanza;
  codScriva: string;
  elemDiscontinuita: IElementoDiscontinuita;
  valutazioneIncidenza: 'si' | 'no' | string;
}

/**
 * Interfaccia che definisce le informazioni per un elemento di discontinuità.
 */
export interface IElementoDiscontinuita {
  id_oggetto_istanza: number;
  flg_valore: boolean;
  des_note: string;
}
