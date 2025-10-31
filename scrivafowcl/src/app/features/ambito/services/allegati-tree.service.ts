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
import { Allegato, TreeElement } from '../models';
import { AuthStoreService } from 'src/app/shared/services';

/**
 * Porting logica tree da consweb
 */
@Injectable({ providedIn: 'root' })
export class AllegatiTreeService {
  
  constructor(
  ) {
  }
 
  private _groupByAllegati(list: Allegato[]) {
    const allegati = {};
    list.forEach((allegato) => {
      if (allegato.id_allegato_istanza_padre) {
        if (allegato.id_allegato_istanza_padre in allegati) {
          allegati[allegato.id_allegato_istanza_padre].push(allegato);
        } else {
          const padre = list.find((a) => {
            const check1 = a.id_allegato_istanza === allegato.id_allegato_istanza_padre;
            return check1;
          });
          if(!padre) {
            if (allegato.id_allegato_istanza in allegati) {
              allegati[allegato.id_allegato_istanza].push(allegato);
            } else {
              allegati[allegato.id_allegato_istanza] = [];
              allegati[allegato.id_allegato_istanza].push(allegato);
            }
          }else {
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

  private _search(list: Allegato[], id: number) {
    return list.find((el) => el.id_allegato_istanza === id);
  }

  public makeTree(response: Allegato[]) {
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

       let i=0;

       allegatiPadre.forEach((padre) => {
         i++;
         const file: Allegato = this._search(
           groupByAllegati[classe][padre],
           +padre
         );
         const currentParentRow: TreeElement = {
           treeId: padre,
           treeStatus: 'expanded',
           treeParentId: classe,
           columns: {
             isFileNode: true,
             nome_allegato: file.nome_allegato,
             categoria_allegato:
               file.tipologia_allegato.categoria_allegato
                 .des_categoria_allegato,
             tipologia_allegato: file.tipologia_allegato.des_tipologia_allegato,
             cod_allegato: file.cod_allegato,
             data_pubblicazione: file.data_pubblicazione,
             data_upload: file.data_upload,
             dimensione_upload: file.dimensione_upload,
             flg_riservato: file.flg_riservato,
             flg_da_pubblicare: file.flg_da_pubblicare,
           },
           allegato: file
         };

        rows.push(currentParentRow);
         // Inserisco tra le righe la riga relativa alla classe corrente
         let hasChild = false;
         groupByAllegati[classe][padre].forEach((allegato: Allegato) => {
           if (allegato.id_allegato_istanza !== +padre) {
             const currentAllegatoRow: TreeElement = {
               treeId: allegato.id_allegato_istanza + '',
               treeParentId: padre,
               treeStatus: 'disabled',
               columns: {
                 isFileNode: true,
                 nome_correlato: allegato.nome_allegato,
                 categoria_allegato:
                   allegato.tipologia_allegato.categoria_allegato
                     .des_categoria_allegato,
                 tipologia_allegato:
                   allegato.tipologia_allegato.des_tipologia_allegato,
                 cod_allegato: allegato.cod_allegato,
                 data_upload: allegato.data_upload,
                 data_pubblicazione: allegato.data_pubblicazione,
                 dimensione_upload: allegato.dimensione_upload,
                 flg_riservato: allegato.flg_riservato,
                 flg_da_pubblicare: allegato.flg_da_pubblicare,
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
  }
 
}
