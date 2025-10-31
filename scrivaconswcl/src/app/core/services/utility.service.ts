/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Injectable } from '@angular/core';
import { Adempimenti, Ambito } from '@app/pages/ambiti/model';
import { ApiClient } from '@eng-ds/api-client';
import { SelectOption } from '@shared/form';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { I18nService } from '@eng-ds/translate';
import { saveAs } from 'file-saver';
import { TableColumn } from '@swimlane/ngx-datatable';
import { Help } from '../interfaces/help';

@Injectable()
export class UtilityService {
  tipologieAllegato: any;
  province: any[] = [];

  constructor(private apiClient: ApiClient, private i18n: I18nService) {}

  getAmbiti(): Observable<SelectOption<number, string>[]> {
    return this.apiClient.request('getAmbiti').pipe(
      map((response: Ambito[]) => {
        const mapped: SelectOption<number, string>[] = [];
        response.forEach((value: Ambito) => {
          mapped.push({ id: value.id_ambito, denominazione: value.des_ambito });
        });
        mapped.push({
          id: -1,
          denominazione: this.i18n.translate(
            'PRACTICE.SEARCH.FIELDS.NO_VALUE.ARGOMENTO'
          )
        });
        return mapped;
      })
    );
  }

  getAmbito(codice: string): Observable<Ambito[]> {
    return this.apiClient.request('getAmbiti', null, { codice });
  }

  getTipoAdempimento(codice: string): Observable<Ambito[]> {
    return this.apiClient.request('getAdempimentiByAmbito', null, { codice });
  }

  getAdempimenti(): Observable<
    SelectOption<{ id: number; cod: string }, string>[]
  > {
    return this.apiClient.request('getAdempimenti').pipe(
      map((response: Adempimenti[]) => {
        const mapped: SelectOption<{ id: number; cod: string }, string>[] = [];
        response.forEach((value: Adempimenti) => {
          mapped.push({
            id: {
              id: value.id_tipo_adempimento,
              cod: value.cod_tipo_adempimento
            },
            denominazione: value.des_tipo_adempimento
          });
        });

        mapped.push({
          id: { id: -1, cod: '-1' },
          denominazione: this.i18n.translate(
            'PRACTICE.SEARCH.FIELDS.NO_VALUE.AMBITO'
          )
        });
        return mapped;
      })
    );
  }

  getAdempimentiByCod(
    cod_tipo_adempimento: string
  ): Observable<SelectOption<number, string>[]> {
    return this.apiClient
      .request('getAdempimentiByCod', null, { cod_tipo_adempimento })
      .pipe(
        map((response: Adempimenti[]) => {
          const mapped: SelectOption<number, string>[] = [];
          response.forEach((value: Adempimenti) => {
            mapped.push({
              id: value.id_adempimento,
              denominazione: value.des_adempimento
            });
          });

          mapped.push({
            id: -1,
            denominazione: this.i18n.translate(
              'PRACTICE.SEARCH.FIELDS.NO_VALUE.TIPO_PROCEDURA'
            )
          });

          return mapped;
        })
      );
  }

  getAdempimentiPubblicati(): Observable<any> {
    return this.apiClient.request('getAdempimentiByCod', null, null).pipe(
      map((response: any[]) => {
        return response;
      })
    );
  }

  getCompetenzeTerritorio(
    id_istanza?: number,
    id_adempimento?: number
  ): Observable<SelectOption<number, string>[]> {
    const query = {};
    if (id_istanza) {
      query[id_istanza] = id_istanza;
    }
    if (id_adempimento) {
      query[id_adempimento] = id_adempimento;
    }

    return this.apiClient.request('getCompetenzeTerritorio', null, query).pipe(
      map((response: any[]) => {
        const mapped: SelectOption<number, string>[] = [];
        response.forEach((value: any) => {
          mapped.push({
            id: value.id_competenza_territorio,
            denominazione: value.des_competenza_territorio
          });
        });

        mapped.push({
          id: -1,
          denominazione: this.i18n.translate(
            'PRACTICE.SEARCH.FIELDS.NO_VALUE.AUTORITA_COMP'
          )
        });
        return mapped;
      })
    );
  }

  getProvincia(id_provincia: any) {
    if (this.province.length === 0) {
      return;
    }

    return this.province.find((p) => p.id_provincia === id_provincia);
  }

  getProvince(
    cod_istat?: number,
    cod_istat_regione?: number,
    id_adempimento?: number
  ): Observable<SelectOption[]> {
    const query = {};
    if (cod_istat) {
      query[cod_istat] = cod_istat;
    }
    if (cod_istat_regione) {
      query[cod_istat_regione] = cod_istat_regione;
    }
    if (id_adempimento) {
      query[id_adempimento] = id_adempimento;
    }

    return this.apiClient
      .request('limitiAmministrativiProvince', null, query)
      .pipe(
        map((response: any[]) => {
          this.province = response;
          const mapped: SelectOption[] = [];
          response.forEach((value: any) => {
            mapped.push({
              id: value.id_provincia,
              denominazione: value.denom_provincia
            });
          });

          mapped.push({
            id: '-1',
            denominazione: this.i18n.translate(
              'PRACTICE.SEARCH.FIELDS.NO_VALUE.PROVINCIA'
            )
          });
          return mapped;
        })
      );
  }

  getComuni(
    siglaProvincia: string,
    cod_istat_provincia: string
  ): Observable<SelectOption<number, string>[]> {
    return this.apiClient
      .request('limitiAmministrativiComuni', null, {
        sigla_prov: siglaProvincia,
        cod_istat_provincia
      })
      .pipe(
        map((response: any[]) => {
          const mapped: SelectOption<number, string>[] = [];
          response.forEach((value: any) => {
            mapped.push({
              id: value.cod_istat_comune,
              denominazione: value.denom_comune
            });
          });

          mapped.push({
            id: -1,
            denominazione: this.i18n.translate(
              'PRACTICE.SEARCH.FIELDS.NO_VALUE.COMUNE'
            )
          });
          return mapped;
        })
      );
  }

  getStatoIstanza(): Observable<SelectOption<number, string>[]> {
    return this.apiClient
      .request('stati-istanza', null, { visibile: true })
      .pipe(
        map((response: any[]) => {
          const mapped: SelectOption<number, string>[] = [];
          response.forEach((value: any) => {
            if (!mapped.find((i) => i.denominazione === value.label_stato)) {
              mapped.push({
                id: value.label_stato,
                denominazione: value.label_stato
              });
            }
          });

          mapped.push({
            id: -1,
            denominazione: this.i18n.translate(
              'PRACTICE.SEARCH.FIELDS.NO_VALUE.STATO_PROC'
            )
          });
          return mapped;
        })
      );
  }

  getCategoriaAllegato(
    codice = 'OSS'
  ): Observable<SelectOption<string, string>[]> {
    return this.apiClient
      .request('getCategoriaAllegato', null, { codice })
      .pipe(
        map((response: any[]) => {
          const mapped: SelectOption<string, string>[] = [];
          response.forEach((value: any) => {
            mapped.push({
              id: value.id_categoria_allegato,
              denominazione: value.des_categoria_allegato
            });
          });
          return mapped;
        })
      );
  }

  getTipologieAllegatoSelect(
    codAdempimento: string,
    codCategoria: string = 'OSS'
  ): Observable<SelectOption<string, string>[]> {
    return this.apiClient
      .request('getTipologieAllegatoByCodAdCodCat', null, null, {
        codAdempimento,
        codCategoria
      })
      .pipe(
        map((response: any[]) => {
          const mapped: SelectOption<string, string>[] = [];
          this.tipologieAllegato = response;
          response.forEach((value: any) => {
            if (value.tipologia_allegato.cod_tipologia_allegato === 'MOD_OSS') {
              mapped.push({
                id: value.tipologia_allegato.id_tipologia_allegato,
                denominazione:
                  value.tipologia_allegato.des_tipologia_allegato + '*'
              });
            } else {
              mapped.push({
                id: value.tipologia_allegato.id_tipologia_allegato,
                denominazione: value.tipologia_allegato.des_tipologia_allegato
              });
            }
          });
          return mapped;
        })
      );
  }

  getCategorieProgettualiByAdempimento(id_adempimento): Observable<any> {
    return this.apiClient
      .request<any>('getCategorieProgettualiByAdempimento', null, null, {
        id_adempimento
      })
      .pipe(
        map((response: any[]) => {
          const mapped: SelectOption<number, string>[] = [];
          response.forEach((value: any) => {
            mapped.push({
              id: value.cod_categoria_oggetto,
              denominazione: value.cod_categoria_oggetto
            });
          });

          mapped.push({
            id: -1,
            denominazione: this.i18n.translate(
              'PRACTICE.SEARCH.FIELDS.NO_VALUE.CAT_OPERA'
            )
          });
          return mapped;
        })
      );
  }

  getStatiProcedimentoStatale(): Observable<any> {
    return this.apiClient.request<any>('getStatiProcedimentoStatale').pipe(
      map((response: any[]) => {
        const mapped: SelectOption<number, string>[] = [];
        response.forEach((value: any) => {
          mapped.push({
            id: value.cod_stato_proced_statale,
            denominazione: value.label_stato_proced_statale
          });
        });

        mapped.push({
          id: -1,
          denominazione: this.i18n.translate(
            'PRACTICE.SEARCH.FIELDS.NO_VALUE.STATO_PROCEDIMENTO_STATALE'
          )
        });
        return mapped;
      })
    );
  }

  getTipiAllegatiByAdempimento(codeAdempimento) {
    return this.apiClient.request('getTipiAllegatiByAdempimento', null, null, {
      codeAdempimento
    });
  }

  trackEvent(
    id_istanza: string,
    cod_tipo_evento: string,
    uuid_richiesta?: string,
    tipo_richiesta?: string
  ) {
    let payloadR = {
      id_istanza,
      cod_tipo_evento
    };

    if (uuid_richiesta && tipo_richiesta) {
      payloadR['uuid_richiesta'] = uuid_richiesta;
      payloadR['tipo_richiesta'] = tipo_richiesta;
    }

    return this.apiClient.request('trackEvent', null, payloadR);
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
        if (column?.rowProp) {
          if (column.getter?.transform) {
            rowToExport[column.rowProp] = column.getter.transform(row);
          } else if (column.pipe?.transform) {
            rowToExport[column.rowProp] = column.pipe.transform(row);
          } else {
            rowToExport[column.rowProp] = row[column.rowProp];
          }
        } else {
          if (column.getter?.transform) {
            rowToExport[column.prop] = column.getter.transform(row);
          } else if (column.pipe?.transform) {
            rowToExport[column.prop] = column.pipe.transform(row);
          } else {
            rowToExport[column.prop] = row[column.prop];
          }
        }
      }
      items.push(rowToExport);
    }

    // console.log("ITEMS Csv:  ", items);
    // console.log("COLUMNS:  ", columns);

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

          const column =
            columns.find((c) => c.prop === key) ||
            columns.find((c) => c.rowProp === key);
          if (!column) {
            console.error(`Colonna non trovata per la chiave: ${key}`);
          }

          csv += column
            ? column.name + (keysCounter + 1 < keysAmount ? ';' : '\r\n')
            : '';
          keysCounter++;
        }

        keysCounter = 0;
      }

      for (const key of Object.keys(items[row])) {
        const rawValue = formatter?.[key]
          ? formatter[key](items[row][key])
          : items[row][key];

        csv +=
          this.escapeCsvValue(rawValue) +
          (keysCounter + 1 < keysAmount ? ';' : '\r\n');
        keysCounter++;
      }
    }

    const name = fileName ? fileName : 'report';
    const blob = new Blob(['\ufeff', csv], { type: 'text/csv' });
    this.saveAs(blob, `${name}.csv`);
  }

  /**Funzione creata per evitare rotture nel CSV (escape delle virgolette, newline, ecc.) */
  escapeCsvValue(value: any): string {
    if (value == null) return '';
    const str = value.toString();
    return `"${str.replace(/"/g, '""')}"`;
  }

  /**Funzione creata adhoc per l'export della lista delle osservazioni perchè la createCSV  non và in questo caso e mi devo spicciare! */
  createCsvOss(
    rows: any[],
    columns: any[],
    fileName: string = 'report',
    formatter?: Record<string, (value: any) => string>
  ) {
    // Costanti per le colonne specifiche
    const RELEVANT_COLUMNS = [
      'des_adempimento',
      'cod_pratica',
      'den_oggetto',
      'des_stato_osservazione',
      'data_osservazione',
      'data_pubblicazione'
    ];

    // Funzione per filtrare le colonne rilevanti
    const getFilteredColumns = (columns: any[]) => {
      return columns.filter((column) =>
        RELEVANT_COLUMNS.includes(column.rowProp)
      );
    };

    // Funzione per ottenere il valore da una proprietà (supporta le proprietà annidate)
    const getValueFromRow = (row: any, prop: string) => {
      const keys = prop.split('.');
      return keys.reduce((acc, key) => (acc && acc[key]) || '', row);
    };

    // Funzione per formattare le date
    const formatDateIfNeeded = (prop: string, value: any) => {
      if (['data_osservazione', 'data_pubblicazione'].includes(prop)) {
        return this.formatDate(value);
      }
      return value;
    };

    // Funzione per applicare la formattazione personalizzata
    const applyFormatter = (formatter: any, prop: string, value: any) => {
      return formatter && formatter[prop] ? formatter[prop](value) : value;
    };

    // Preparazione dei dati per il CSV
    const filteredColumns = getFilteredColumns(columns);
    const items = rows.map((row) => {
      const rowToExport: any = {};

      filteredColumns.forEach((column) => {
        const prop = column.rowProp;

        if (prop) {
          let value = getValueFromRow(row, prop);

          // Gestione degli oggetti complessi (nell'eventualità che il valore sia un oggetto)
          if (typeof value === 'object' && value !== null) {
            const nestedValue = value[prop.split('.').pop()!];
            value = nestedValue !== undefined ? nestedValue : '';
          }

          // Formattazione della data
          value = formatDateIfNeeded(prop, value);

          // Applicazione della formattazione personalizzata
          rowToExport[prop] = applyFormatter(formatter, prop, value);
        }
      });

      return rowToExport;
    });

    // Creazione delle intestazioni del CSV
    const headers =
      filteredColumns.map((column) => `"${column.name}"`).join(';') + '\r\n';

    // Creazione delle righe del CSV
    const rowsCsv = items
      .map((row) => {
        return filteredColumns
          .map((column, index) => {
            const value = row[column.rowProp];
            const formattedValue =
              value != null
                ? `"${value.toString().replace(/"/g, '""')}"`
                : '""';
            return formattedValue;
          })
          .join(';');
      })
      .join('\r\n');

    // Unione intestazioni e righe
    const csvContent = '\ufeff' + headers + rowsCsv;

    // Creazione del Blob per il file CSV
    const blob = new Blob([csvContent], { type: 'text/csv' });
    this.saveAs(blob, `${fileName}.csv`);
  }

  formatDate(dateString: string): string {
    console.log('Formatta la data: ', dateString); // Debug

    const date = new Date(dateString);

    // Controllo se la data è invalida
    if (isNaN(date.getTime())) {
      console.error('Data non valida: ', dateString);
      return ''; // Se la data non è valida, restituisco una stringa vuota
    }

    // Uso il costruttore Intl.DateTimeFormat per formattare la data in modo flessibile
    const options: Intl.DateTimeFormatOptions = {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric'
    };

    return new Intl.DateTimeFormat('it-IT', options).format(date);
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

  getHelp(chiave: string): Observable<Help[]> {
    return this.apiClient.request('getHelp', null, { chiave });
  }
}
