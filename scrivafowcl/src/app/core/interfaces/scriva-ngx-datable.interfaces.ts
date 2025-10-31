/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/** Interfacce di riferimento per la libreria: ngx-datatable.
 * @see https://swimlane.gitbook.io/ngx-datatable/api
 */

/**
 * Definizione applicativa dell'oggetto ritornato dall'output event (select) per il plugin: ngx-datatable.
 * Interface Ngx Data Table Select.
 * @inheritdoc https://swimlane.gitbook.io/ngx-datatable/api/table/outputs#select
 */
export interface ISNgxDTSelect<T> {
  selected: T;
}
