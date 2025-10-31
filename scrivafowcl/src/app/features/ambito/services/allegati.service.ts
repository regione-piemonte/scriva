/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AppConfigService } from 'src/app/shared/services';
import {
  Allegato,
  CategoriaAllegato,
  EstensioneAllegato,
  TipoAllegato,
} from '../models';
import { AllegatiSearchRequest } from '../models/allegati/allegati-search-request';

@Injectable({ providedIn: 'root' })
export class AllegatiService {
  private beUrl = '';

  static maxLengths: {
    key: string;
    max: number;
  }[] = [
    { key: 'numProtocollo', max: 20 }, // num_protocollo_allegato
    { key: 'numAtto', max: 20 }, // num_atto
    { key: 'titolo', max: 1000 }, // titolo allegato
    { key: 'autore', max: 300 }, // autore allegato
  ];

  constructor(protected http: HttpClient, protected config: AppConfigService) {
    this.beUrl = this.config.getBEUrl();
  }

  getAcceptedFileTypesByCodAdempimento(
    codAdempimento: string
  ): Observable<EstensioneAllegato[]> {
    return this.http.get<EstensioneAllegato[]>(
      `${this.beUrl}/estensioni-allegato/code-adempimento/${codAdempimento}`
    );
  }

  getAllAllegatiIstanza(
    idIstanza: number,
    sys: boolean = true
  ): Observable<Allegato[]> {
    const allegatiIstanzaSearch = {
      id_istanza: idIstanza,
      sys: sys,
    };
    return this.getAllAllegatiIstanzaSearch(allegatiIstanzaSearch);
  }

  getAllegatiIstanzaSearch(
    idIstanza: number,
    allegatiIstanzaSearch: Partial<AllegatiSearchRequest> = {}
  ): Observable<Allegato[]> {
    const allegatiSearchRequest = {
      id_istanza: idIstanza,
      flg_canc_logica: false,
      sys: true,
      ...allegatiIstanzaSearch,
    };
    return this.getAllAllegatiIstanzaSearch(allegatiSearchRequest);
  }

  getAllAllegatiIstanzaSearch(
    allegatiIstanzaSearch: AllegatiSearchRequest
  ): Observable<Allegato[]> {
    return this.http.post<Allegato[]>(
      `${this.beUrl}/allegati/_search?limit=9999`,
      allegatiIstanzaSearch
    );
  }

  getAllTipiAllegatoByCodAdempimento(
    codAdempimento: string
  ): Observable<TipoAllegato[]> {
    return this.http.get<TipoAllegato[]>(
      `${this.beUrl}/tipologie-allegato/code-adempimento/${codAdempimento}`
    );
  }

  getCategorieAllegatoByCodAdempimento(
    codAdempimento: string
  ): Observable<CategoriaAllegato[]> {
    return this.http.get<CategoriaAllegato[]>(
      `${this.beUrl}/categorie-allegato/code-adempimento/${codAdempimento}`
    );
  }

  getTipologieAllegatoByCodAdempimentoCodCategoria(
    codAdempimento: string,
    codCategoria: string
  ): Observable<TipoAllegato[]> {
    return this.http.get<TipoAllegato[]>(
      `${this.beUrl}/tipologie-allegato/code-adempimento/${codAdempimento}/code-categoria/${codCategoria}`
    );
  }

  getAllegatiObbligatoriPerVincoli(
    idIstanza: number
  ): Observable<TipoAllegato[]> {
    return this.http.get<TipoAllegato[]>(
      `${this.beUrl}/vincoli-autorizzazioni/configurazioni-allegati/id-istanza/${idIstanza}`
    );
  }

  getConfigAllegatoByCodTipologia(
    codAdempimento: string,
    codTipologiaAllegato: string
  ): Observable<TipoAllegato[]> {
    return this.http.get<TipoAllegato[]>(
      `${this.beUrl}/tipologie-allegato/code-adempimento/${codAdempimento}/code-tipologia-allegato/${codTipologiaAllegato}`
    );    
  }

  postAllegati(data: string, file): Observable<Allegato> {
    const formData = new FormData();
    formData.append('file', file, file.name);
    formData.append('allegatoIstanza', data);
    return this.http
      .post<Allegato[]>(`${this.beUrl}/allegati`, formData)
      .pipe(map((res) => res[0]));
  }

  updateAllegati(request: Allegato | Partial<Allegato>) {
    return this.http.put<any>(`${this.beUrl}/allegati`, request);
  }

  getAllegatoByUuid(uuidIndex): any {
    return this.http.get(`${this.beUrl}/allegati/${uuidIndex}`, {
      responseType: 'blob',
    });
  }

  getAllegatoByCode(codAllegato: string) {
    return this.http.get(
      `${this.beUrl}/allegati/_download?cod_allegato=${codAllegato}`,
      { responseType: 'blob' }
    );
  }

  deleteAllegatoByUuid(uuidIndex): any {
    return this.http.delete(`${this.beUrl}/allegati/${uuidIndex}`);
  }

  exportCSV(idIstanza: number): any {
    return this.http.get(
      `${this.beUrl}/allegati/_download?id_istanza=${idIstanza}&output_format=CSV`,
      { responseType: 'blob', observe: 'response' }
    );
  }

  exportZIP(idIstanza: number): any {
    return this.http.get(
      `${this.beUrl}/allegati/_download?id_istanza=${idIstanza}&output_format=ZIP`,
      { responseType: 'arraybuffer', observe: 'response' }
    );
  }

  downloadAllegatiSelezionati(id_istanza: number, list: string[]) {
    return this._downloadSelected(id_istanza, {
      list,
      output_format: 'zip',
      filename: `documenti_istanza_${id_istanza}.zip`,
    });
  }

  private _downloadSelected(
    id_istanza: number,
    options: {
      output_format?: string;
      list: string[];
      filename?: string;
    }
  ) {
    const api = `${this.beUrl}/allegati/_download`;
    let params = new HttpParams();
    if (id_istanza) {
      params = params.set('id_istanza', id_istanza.toString());
    }
    if (options.output_format) {
      params = params.set('output_format', options.output_format);
    } else {
      params = params.set('output_format', '');
    }
    return this.http
      .post(
        api,
        { list: options.list },
        {
          params,
          observe: 'response',
          responseType: 'blob',
        }
      )
      .pipe(
        map((response) => {
          const contentDispositionHeader = response.headers.get(
            'Content-Disposition'
          );
          let fileName = contentDispositionHeader.split('filename="')[1];
          fileName = fileName.slice(0, -1);
          const blob = new Blob([response.body], { type: response.body.type });
          this.saveAs(blob, fileName);
        })
      );
  }

  str2bytes(str) {
    var bytes = new Uint8Array(str.length);
    for (var i = 0; i < str.length; i++) {
      bytes[i] = str.charCodeAt(i);
    }
    return bytes;
  }

  createCSV(
    rows: any[],
    columns: any[],
    fileName?: string,
    formatter?: Record<string, (value: any) => string>
  ) {
    // Adding just the fields of the row that must been printed
    // (e.g. fields in the columns)
    const items = [];
    for (const row of rows) {
      const rowToExport = {};
      for (const column of columns) {
        if (column.getter?.transform) {
          rowToExport[column.prop] = column.getter.transform(row);
        } else if (column.formatDate?.transform) {
          rowToExport[column.prop] = column.formatDate.transform(row);
        } else {
          rowToExport[column.prop] = row[column.prop];
        }
      }
      items.push(rowToExport);
    }

    // Creating the formatter Record
    // if the column has a pipe, then added to formatter
    // else we have a standard function for null values
    if (!formatter) {
      formatter = {};

      for (const column of columns) {
        column?.pipe?.transform
          ? (formatter[column.prop] = column.pipe.transform)
          : (formatter[column.prop] = (value) => {
              if (value) {
                return value;
              }
              return '';
            });
      }
    }

    let csv = '';

    // Loop the array of objects
    for (let row = 0; row < items.length; row++) {
      const keysAmount = Object.keys(items[row]).length;
      let keysCounter = 0;

      // If this is the first row, generate the headings
      if (row === 0) {
        // Loop each property of the object
        for (const key of Object.keys(items[row])) {
          // This is to not add a comma at the last cell
          // The '\r\n' adds a new line
          csv +=
            columns.find((c) => c.prop === key).name +
            (keysCounter + 1 < keysAmount ? ';' : '\r\n');
          keysCounter++;
        }

        keysCounter = 0;
      }

      for (const key of Object.keys(items[row])) {
        if (formatter && formatter[key]) {
          csv +=
            formatter[key](items[row][key]) +
            (keysCounter + 1 < keysAmount ? ';' : '\r\n');
        } else {
          csv +=
            items[row][key] + (keysCounter + 1 < keysAmount ? ';' : '\r\n');
        }

        keysCounter++;
      }

      keysCounter = 0;
    }
    const name = fileName ? fileName : 'report';
    const blob = new Blob(['\ufeff', csv], { type: 'text/csv' });
    this.saveAs(blob, `${name}.csv`);
  }

  public saveAs(blob: Blob, filename: string) {
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = filename;
    a.click();
    window.URL.revokeObjectURL(url);
    a.remove();
  }

  checkCategorieObbligatorie(
    idIstanza: number
  ): Observable<CategoriaAllegato[]> {
    return this.http.get<CategoriaAllegato[]>(
      `${this.beUrl}/allegati/_mandatory-categoria?id_istanza=${idIstanza}`
    );
  }

  checkCategorieAdempimento(
    idIstanza: number
  ): Observable<CategoriaAllegato[]> {
    return this.http.get<CategoriaAllegato[]>(
      `${this.beUrl}/allegati/categorie-adempimento?id_istanza=${idIstanza}`
    );
  }

  checkDelega(idIstanza: number) {
    return this.http.get(
      `${this.beUrl}/allegati/_mandatory-delega?id_istanza=${idIstanza}`
    );
  }

  generaDocumentoDiSistema(idIstanza: number): Observable<any> {
    // previously generaPdfAllegati(idIstanza)
    // old endpoint: /allegati/_download?id_istanza=${idIstanza}&output_format=PDF
    return this.http.get(
      `${this.beUrl}/istanze/${idIstanza}/_download?archivia=true`,
      { responseType: 'blob' }
    );
  }
}
