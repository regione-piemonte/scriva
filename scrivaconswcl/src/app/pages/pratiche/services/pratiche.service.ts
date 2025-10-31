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
import { Observable } from 'rxjs';
import { ApiCSIPageableResponse, CSIApiFiltersRequest } from '@core/backend/model';
import { ApiClient } from '@eng-ds/api-client';
import { Pratica, QueryRicercaPratica } from '@pages/pratiche/model';
import { FasePubblicazione } from '@pages/pratiche/enum';
import { Allegato, AllegatoInsert, TreeElement } from '../model/allegato.model';
import { map } from 'rxjs/operators';
import { OsservazionePost, OsservazionePut } from '../model/osservazione.model';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { saveAs } from 'file-saver';
import { TableSort } from '@shared/table/models/table-sort';

@Injectable({
  providedIn: 'root'
})
export class PraticheService {
  constructor(private apiClient: ApiClient, private http: HttpClient) {}

  getAllegatiListMock(): any {
    return this.apiClient.request<any>('getAllegatiMock').pipe(
      map((data) => {
        return data;
      })
    );
  }

  searchPratiche(
    query: QueryRicercaPratica,
    fase_pubblicazione = FasePubblicazione.TUTTE,
    offset?: number,
    sort?: TableSort[],
    limit?: number
  ): Observable<HttpResponse<ApiCSIPageableResponse<Pratica>>> {
    // Mapper per id_tipo_adempimento
    if (query.id_tipo_adempimento) {
      query.id_tipo_adempimento = (query.id_tipo_adempimento as any).id;
    }

    // Mapper per provincia
    if (query.sigla_provincia_oggetto) {
      if ((query.sigla_provincia_oggetto as any).id) {
        query.sigla_provincia_oggetto = (
          query.sigla_provincia_oggetto as any
        ).sigla_provincia;
      }
    }

    const keys = Object.keys(query);
    keys.forEach((k: string) => {
      if (
        query[k] === null ||
        +query[k] === -1 ||
        query[k] === '' ||
        query[k] === '-1'
      ) {
        delete query[k];
      }
    });

    let params = new HttpParams();

    if (offset) {
      params = params.set('offset', offset.toString());
    }

    if (limit){
      params = params.set('limit', limit.toString());
    }

    if (Array.isArray(sort) && sort.length > 0 && sort[0]) {
      const direction = sort[0].dir === 'asc' ? '' : '-';
      params = params.set('sort', direction + sort[0].prop);
    }

    return this.apiClient.requestWithHeader<any>(
      'getPracticesList',
      {
        ...query,
        fase_pubblicazione
      },
      params
    );
  }

  getPracticesList(
    id_ambito: number,
    id_tipo_adempimento: number,
    offset?: number,
    size?: number,
    sort?: TableSort[],
    id_competenza_territorio?: number,
    fase_pubblicazione = FasePubblicazione.TUTTE,
    limit?: number
  ): Observable<HttpResponse<ApiCSIPageableResponse<Pratica>>> {

    let params = new HttpParams();

    if (offset) {
      params = params.set('offset', offset.toString());
    }

    if (limit) {
      params = params.set('limit', limit.toString())
    }

    if (sort[0]) {
      if (sort[0]?.dir === 'asc') {
        params = params.set('sort', sort[0].prop);
      } else {
        params = params.set('sort', '-' + sort[0].prop);
      }
    }

    const body = {
      id_ambito,
      id_tipo_adempimento,
      id_competenza_territorio,
      fase_pubblicazione
    };

    return this.apiClient.requestWithHeader<any>(
      'getPracticesList',
      body,
      params
    );
  }

  getPracticeDetail(id_istanza: number): Observable<Pratica> {
    return this.apiClient.request<any>('getPracticeDetail', { id_istanza });
  }

  getMapDetail(id: number): Observable<any> {
    return this.apiClient.request<any>('getMapDetail', null, null, { id });
  }

  getCategorieProgettuali(id_oggetto_istanza): Observable<any> {
    return this.apiClient.request<any>(
      'getCategorieProgettualiByIstanza',
      null,
      null,
      { id_oggetto_istanza }
    );
  }

  getValutazioneIncidenza(id_istanza): Observable<any> {
    return this.apiClient.request<any>('getVincoliIstanza', null, {
      id_istanza,
      cod_vincolo: 'VNCS'
    });
  }

  getInfrastrutturaStrategica(id_istanza): Observable<any> {
    return this.apiClient.request<any>('getVincoliIstanza', null, {
      id_istanza,
      cod_vincolo: 'L443'
    });
  }

  getAllegatiIstanza(id_istanza: number): Observable<any> {
    return this.apiClient
      .request<any>('getAllegatiList', { id_istanza })
      .pipe();
  }

  getAllegatiList(id_istanza: number): Observable<TreeElement[]> {
    return this.apiClient.request<any>('getAllegatiList', { id_istanza }).pipe(
      map((response: Allegato[]) => {
        // Ragruppo per classe allegati
        const groupByClasseAllegato = this._groupByClasseAllegato(response);
        // const groupByCategoriaAllegato = {};
        const groupByAllegati = {};
        const rows: TreeElement[] = [];

        const classiAllegato = Object.keys(groupByClasseAllegato);

        // Per ogni classe raggruppo per categoria
        classiAllegato.forEach((classe) => {
          const currentClassRow: TreeElement = {
            treeId: classe,
            treeStatus: 'expanded',
            columns: {
              alberatura:
                groupByClasseAllegato[classe][0].classe_allegato
                  .des_classe_allegato
            }
          };

          // Inserisco tra le righe la riga relativa alla classe corrente
          rows.push(currentClassRow);

          if (!(classe in groupByAllegati)) {
            groupByAllegati[classe] = {};
          }
          groupByAllegati[classe] = this._groupByAllegati(
            groupByClasseAllegato[classe]
          );

          const allegatiPadre = Object.keys(groupByAllegati[classe]);

          let i = 0;

          allegatiPadre.forEach((padre) => {
            i++;
            const file: Allegato = this._search(
              groupByAllegati[classe][padre],
              +padre
            );

            // Estraggo dati di comodo
            const tipologiaAllegato = file?.tipologia_allegato;
            const categoriaAllegato = tipologiaAllegato?.categoria_allegato;

            // Genero un oggetto con le informazioni per la riga
            const currentParentRow: TreeElement = {
              treeId: padre,
              treeStatus: 'disabled',
              treeParentId: classe,
              columns: {
                isFileNode: true,
                nome: file?.nome_allegato ?? '',
                categoria: categoriaAllegato.des_categoria_allegato ?? '',
                tipologia: tipologiaAllegato?.des_tipologia_allegato ?? '',
                codice: file?.cod_allegato,
                dataPubblicazione: file?.data_pubblicazione,
                //dataUpload: file?.data_upload,
                dimensione: file?.dimensione_upload
              },
              allegato: file
            };

            rows.push(currentParentRow);
            // Inserisco tra le righe la riga relativa alla classe corrente
            let hasChild = false;
            groupByAllegati[classe][padre].forEach((allegato: Allegato) => {
              if (allegato.id_allegato_istanza !== +padre) {
                // Estraggo dati di comodo
                const tipologiaAllegatoChild = allegato?.tipologia_allegato;
                const categoriaAllegatoChild =
                  tipologiaAllegatoChild?.categoria_allegato;

                const currentAllegatoRow: TreeElement = {
                  treeId: allegato?.id_allegato_istanza?.toString() ?? '',
                  treeParentId: padre,
                  treeStatus: 'disabled',
                  columns: {
                    isFileNode: true,
                    nome_correlato: allegato?.nome_allegato ?? '',
                    categoria:
                      categoriaAllegatoChild.des_categoria_allegato ?? '',
                    tipologia:
                      tipologiaAllegatoChild?.des_tipologia_allegato ?? '',
                    codice: allegato?.cod_allegato,
                    //dataUpload: allegato?.data_upload,
                    dataPubblicazione: allegato?.data_pubblicazione,
                    dimensione: allegato?.dimensione_upload
                  },
                  allegato: allegato
                };

                // Inserisco tra le righe la riga relativa alla classe corrente
                rows.push(currentAllegatoRow);
                hasChild = true;
              }

              if (hasChild) {
                currentParentRow.treeStatus = 'expanded';
              } else {
                currentParentRow.treeStatus = 'disabled';
              }
            });
          });
        });
        return rows;
      })
    );
  }

  downloadAllegatiZip(id_istanza: number) {
    return this._download(id_istanza, {
      output_format: 'zip',
      filename: `documenti_istanza_${id_istanza}.zip`
    });
  }

  downloadAllegatiSelezionati(id_istanza: number, list: string[]) {
    return this._downloadSelected(id_istanza, {
      list,
      output_format: 'zip',
      filename: `documenti_istanza_${id_istanza}.zip`
    });
  }

  downloadAllegatiCSV(id_istanza: number) {
    return this._download(id_istanza, {
      output_format: 'CSV',
      filename: `documenti_istanza_${id_istanza}.csv`
    });
  }

  downloadAllegato(id_istanza: number, cod_allegato: string, filename: string) {
    return this._download(id_istanza, { cod_allegato, filename });
  }

  downloadAllegatiOsservazione(id_osservazione: number, output_format = 'CSV') {
    const api = this.apiClient.getUrlByApiName('downloadAllegati');

    let params = new HttpParams();
    if (id_osservazione) {
      params = params.set('id_osservazione', id_osservazione.toString());
    }
    if (output_format) {
      params = params.set('output_format', output_format);
    }

    return this.http
      .get(api, {
        params,
        observe: 'response',
        responseType: 'blob'
      })
      .pipe(
        map((response) => {
          const contentDisposition = response.headers.get(
            'Content-Disposition'
          );
          const filename = contentDisposition.split(';')[1].split('"')[1];
          const blob = new Blob([response.body], { type: response.body.type });
          saveAs(blob, filename);
        })
      );
  }

  downloadZipAllegatiOsservazione(
    id_osservazione: number,
    output_format = 'ZIP'
  ) {
    const api = this.apiClient.getUrlByApiName('downloadAllegati');

    let params = new HttpParams();
    if (id_osservazione) {
      params = params.set('id_osservazione', id_osservazione.toString());
    }
    params = params.set('output_format', output_format);

    return this.http
      .get(api, {
        params,
        observe: 'response',
        responseType: 'blob'
      })
      .pipe(
        map((response) => {
          const contentDisposition = response.headers.get(
            'Content-Disposition'
          );
          const filename = contentDisposition.split(';')[1].split('"')[1];
          const blob = new Blob([response.body], { type: response.body.type });
          saveAs(blob, filename);
        })
      );
  }

  private _download(
    id_istanza: number,
    options: {
      output_format?: string;
      cod_allegato?: string;
      filename?: string;
    }
  ) {
    const api = this.apiClient.getUrlByApiName('downloadAllegati');

    let params = new HttpParams();
    if (id_istanza) {
      params = params.set('id_istanza', id_istanza.toString());
    }
    if (options.output_format) {
      params = params.set('output_format', options.output_format);
    } else {
      params = params.set('output_format', '');
    }
    if (options.cod_allegato) {
      params = params.set('cod_allegato', options.cod_allegato);
    }

    return this.http
      .get(api, {
        params,
        observe: 'response',
        responseType: 'blob'
      })
      .pipe(
        map((response) => {
          const blob = new Blob([response.body], { type: response.body.type });
          saveAs(blob, options.filename);
        })
      );
  }

  private _downloadSelected(
    id_istanza: number,
    options: {
      output_format?: string;
      list: string[];
      filename?: string;
    }
  ) {
    const api = this.apiClient.getUrlByApiName('downloadAllegatiSelezionati');

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
          responseType: 'blob'
        }
      )
      .pipe(
        map((response) => {
          const contentDisposition = response.headers.get(
            'Content-Disposition'
          );
          const filename = contentDisposition.split(';')[1].split('"')[1];
          const blob = new Blob([response.body], { type: response.body.type });
          saveAs(blob, filename);
        })
      );
  }

  getCaptcha(): Observable<any> {
    return this.apiClient.request('getCaptcha');
  }

  validateCaptcha(payload): Observable<any> {
    return this.apiClient.request('validateCaptcha', payload);
  }

  private _groupBy<T, K extends keyof any>(
    list: T[],
    getKey: (item: T) => K,
    setId: (id: K, item: T) => T,
    parentId?: string,
    setParentId?: (id: string, item: T) => T
  ) {
    return list.reduce((previous, currentItem) => {
      const group = getKey(currentItem);
      currentItem = setId(group, currentItem);
      if (parentId) {
        currentItem = setParentId(parentId, currentItem);
      }
      if (!previous[group]) previous[group] = [];
      previous[group].push(currentItem);
      return previous;
    }, {} as Record<K, T[]>);
  }

  private _groupByClasseAllegato(list: Allegato[]) {
    return this._groupBy(
      list,
      (i) => i.classe_allegato.cod_classe_allegato,
      (id, item) => {
        item.treeId = id;
        return item;
      }
    );
  }

  private _groupByCategoriaAllegato(list: Allegato[], parentId: string) {
    return this._groupBy(
      list,
      (i) => i.tipologia_allegato.categoria_allegato.cod_categoria_allegato,
      (id, item) => {
        item.treeId = id;
        return item;
      },
      parentId,
      (id, item) => {
        item.treeParentId = id;
        return item;
      }
    );
  }

  /**
   * Funzione che effettua il raggruppamento degli allegati.
   * Un allegato senza id_allegato_istanza_padre è da considerare l'oggetto a cui sono collegati altri allegati.
   * @param list Allegato[] con la lista di elementi da raggruppare.
   * @returns Oggetto dinamico chiave (id_allegato_istanza) e valore (allegato) con i dati raggruppati.
   */
  /*   private _groupByAllegati(list: Allegato[]) {
    // Creo un oggetto vuoto dove inserirò una coppia chiave (id_allegato) e valore (allegato)
    const allegati = {};

    // Filtro l'array in input ed estraggo solo gli oggetti che non hanno l'id_padre
    const allegatiPadre: Allegato[] =
      list.filter((a: Allegato) => {
        // NOTA DELLO SVILUPPATORE: c'è il lint che scassa le balle, quindi faccio il controllo esteso (basta in realtà == undefined)
        // Ritorno solo gli oggetti che non hanno l'id_allegato_padre
        const check1 = a.id_allegato_istanza_padre === undefined;
        const check2 = a.id_allegato_istanza_padre === null;
        return check1 || check2;
        // #
      }) ?? [];

    // Si cicla la lista di allegati padre, e si imposta il valore come proprietà all'interno dell'oggetto "allegati"
    allegatiPadre.forEach((allegatoPadre: Allegato) => {
      // Estraggo l'id dell'allegato padre e lo inserisco all'interno dell'oggetto da ritornare
      const idAP: number = allegatoPadre.id_allegato_istanza;
      // L'oggetto conterrà la lista degli allegati figli
      allegati[idAP] = [];

      // Estraggo la lista di tutti gli allegati figli
      let allegatiFigli: Allegato[];
      allegatiFigli = list.filter((a: Allegato) => {
        // Cerco gli allegati figli che hanno l'id allegato padre uguale
        return idAP === a.id_allegato_istanza_padre;
        // #
      });

      // Verifico se sono stati trovati elementi
      if (allegatiFigli?.length > 0) {
        // Ho trovato i figli, aggiorno il valore nel contenitore
        allegati[idAP] = allegatiFigli;
        // #
      }
    });

    // Ritorno l'oggetto con i dati raggruppati
    return allegati;
  } */

  private _groupByAllegati(list: Allegato[]) {
    const allegati = {};
    list.forEach((allegato) => {
      if (allegato.id_allegato_istanza_padre) {
        if (allegato.id_allegato_istanza_padre in allegati) {
          allegati[allegato.id_allegato_istanza_padre].push(allegato);
        } else {
          const padre = list.find((a) => {
            const check1 =
              a.id_allegato_istanza === allegato.id_allegato_istanza_padre;
            return check1;
          });
          if (!padre) {
            if (allegato.id_allegato_istanza in allegati) {
              allegati[allegato.id_allegato_istanza].push(allegato);
            } else {
              allegati[allegato.id_allegato_istanza] = [];
              allegati[allegato.id_allegato_istanza].push(allegato);
            }
          } else {
            allegati[padre.id_allegato_istanza] = [];
            allegati[padre.id_allegato_istanza].push(allegato);
          }
        }
      } else {
        if (allegato.id_allegato_istanza in allegati) {
          allegati[allegato.id_allegato_istanza].push(allegato);
        } else {
          allegati[allegato.id_allegato_istanza] = [];
          allegati[allegato.id_allegato_istanza].push(allegato);
        }
      }
    });
    return allegati;
  }

  private _search(list: Allegato[], id: number) {
    return list.find((el) => el.id_allegato_istanza === id);
  }

  getNotes(id_istanza): Observable<any> {
    return this.apiClient.request<any>('getNotes', null, { id_istanza });
  }

  getRiepilogoAllegati(id_osservazione: number): Observable<AllegatoInsert[]> {
    return this.apiClient.request<any>('getRiepilogoAllegati', {
      id_osservazione
    });
  }

  getCheckAllegati(id_osservazione: number): Observable<Allegato[]> {
    return this.apiClient.request<any>('getRiepilogoAllegati', {
      id_osservazione
    });
  }

  getAllegati(id_osservazione: number): Observable<Allegato[]> {
    return this.apiClient.request<any>('getRiepilogoAllegati', null, {
      id_osservazione
    });
  }

  getAttiFinali(id_istanza: number): Observable<AllegatoInsert[]> {
    return this.apiClient.request<any>('getAttiFinali', {
      id_istanza,
      cod_classe_allegato: 'PROVV_FINALE'
    });
  }

  postOsservazioni(osservazione: OsservazionePost): any {
    return this.apiClient.request('postOsservazioni', osservazione);
  }

  deleteOsservazioni(id: number): Observable<any> {
    return this.apiClient.request('deleteOsservazioni', null, null, { id });
  }

  postAllegati(metadata: any, file: any): Observable<any> {
    const api = this.apiClient.getUrlByApiName('postAllegati');
    const formData: any = new FormData();
    formData.append('allegatoIstanza', JSON.stringify(metadata));
    formData.append('file', file);
    return this.http.post(api, formData, {
      headers: {
        'Content-Disposition': `attachment; filename="${file.name}"`
      }
    });
  }

  deleteAllegato(cod_allegato: string): Observable<any> {
    return this.apiClient.request<any>('deleteAllegato', null, {
      cod_allegato
    });
  }

  sendOsservazione(osservazione: any) {
    return this.apiClient.request('putOsservazioni', osservazione);
  }

  downloadModuloOsservazioni(
    id_istanza: number,
    codTipologiaAllegato = 'MOD_OSS',
    output_format = 'doc'
  ) {
    return this._downloadModuloOssPrivacy(
      id_istanza,
      codTipologiaAllegato,
      output_format
    );
  }

  downloadModuloPrivacy(
    id_istanza: number,
    codTipologiaAllegato = 'MOD_DP_PRIVACY',
    output_format = 'doc'
  ) {
    return this._downloadModuloOssPrivacy(
      id_istanza,
      codTipologiaAllegato,
      output_format
    );
  }

  private _downloadModuloOssPrivacy(
    id_istanza: number,
    codTipologiaAllegato?: string,
    output_format?: string
  ) {
    const api = this.apiClient.getUrlByApiName('downloadModuloOsservazioni', {
      id_istanza
    });

    let params = new HttpParams();
    if (output_format) {
      params = params.set('output_format', output_format);
    }
    if (codTipologiaAllegato) {
      params = params.set('cod_tipologia_allegato', codTipologiaAllegato);
    }

    return this.http
      .get(api, {
        params,
        observe: 'response',
        responseType: 'blob'
      })
      .pipe(
        map((response) => {
          const contentDisposition = response.headers.get(
            'Content-Disposition'
          );
          const filename = contentDisposition.split(';')[1].split('"')[1];
          const blob = new Blob([response.body], { type: response.body.type });
          saveAs(blob, filename);
        })
      );
  }

  /**
   * #############################################
   * FUNZIONI DI SUPPORTO PER LE RICERCHE PRATICHE
   * #############################################
   */

  /**
   * Funzione che crea una copia delle informazioni relative ai filtri di ricerca/paginazione.
   * @param filter CSIApiFiltersRequest con le informazioni dal clonare.
   * @returns CSIApiFiltersRequest con un nuovo oggetto clonato.
   */
  cloneTableSortFromCSIApiFiltersRequest(
    filter: CSIApiFiltersRequest
  ): TableSort[] {
    // Verifico l'input
    if (!filter || !Array.isArray(filter.sort)) {
      // Non ci sono configurazioni da clonare
      return [];
    }

    // Cerco di creare una copia dell'array senza riferimenti all'oggetto filter
    let sort: TableSort[] = [...filter.sort];
    // Ridefinisco i valore per i sort della tabella, rimuovendo le referenze degli oggetti
    sort = sort.map((ts: any) => {
      // Ritorno un nuovo oggetto, destrutturando quello dell'array stesso
      return { ...ts };
    });

    // Ritorno l'array con un nuovo riferimento sganciato dall'oggetto principale
    return sort;
  }
}
