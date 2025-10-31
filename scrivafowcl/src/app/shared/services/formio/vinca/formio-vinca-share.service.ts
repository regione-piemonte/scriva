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
import { ExtendedComponentSchema, FormioForm } from 'angular-formio';
import { cloneDeep } from 'lodash';
import { CategoriaOggetto } from '../../../../features/ambito/models';
import { FormioUtilitiesService } from '../formio-utilities.service';
import { CodiciCategoriaOggetto } from '../utilities/formio.enums';

/**
 * Interfaccia di supporto che definisce parzialmente la struttura data per la gestione delle VINCA SCR-APP.
 */
interface IVincaFormioScrApp {
  [key: string]: any;
  JS_CATEGORIE_PROGETTUALI: IJsCategorieProgettuali;
}

/**
 * Interfaccia di supporto che definisce la struttura data per la gestione delle VINCA SCR-APP per: JS_CATEGORIE_PROGETTUALI.
 */
interface IJsCategorieProgettuali {
  elenco_categorie: CategoriaOggetto[];
  des_altra_categoria_oggetto?: string;
}

/**
 * Servizio di utility per il componente scriva che gestisce i formio applicativi
 */
@Injectable({ providedIn: 'root' })
export class FormioVincaShareService {
  /**
   * Costruttore.
   */
  constructor(protected _formioUtilities: FormioUtilitiesService) {}

  /**
   * ##############################
   * SETUP JS_CATEGORIE_PROGETTUALI
   * ##############################
   */

  /**
   * Funzione di setup che gestisce le informazioni del campo JS_CATEGORIE_PROGETTUALI.
   * Se esiste un elenco JS_CATEGORIE_PROGETTUALI e contiene un oggetto con codice "ALTRO", allora aggiorno l'oggetto del formio e preparo lo specifico campo valorizzato.
   * La modifica avviene per gestire la compatibilita' con la libreria.
   * @param formioData IVincaFormioScrApp contenente l'oggetto "data" restituito come risultato del submit del formio.
   * @returns any contenente l'oggetto manipolato secondo le configurazioni del formio data.
   */
  protected setupJsCategorieProgettuali(formioData: IVincaFormioScrApp): any {
    // Verifico l'input
    if (!formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Creo una copia dell'oggetto per gestire in maniera puntuale le modifiche
    let data = cloneDeep(formioData);

    // Estraggo l'oggetto di gestione delle categorie progettuali
    const jsCatProg: IJsCategorieProgettuali = data.JS_CATEGORIE_PROGETTUALI;
    // Aggiorno l'oggetto JS_CATEGORIE_PROGETTUALI secondo le logiche per i dati DB
    data.JS_CATEGORIE_PROGETTUALI = this.setupDesAltraCategoria(jsCatProg);

    // Ritorno il dato aggiornato
    return data;
  }

  /**
   * Funzione che gestisce le logiche per settare le informazioni generate per il formio, in un formato compatibile con la libreria e la struttura del formio stesso.
   * @param jsCatProg IJsCategorieProgettuali con le informazioni da gestire.
   * @returns IJsCategorieProgettuali formattato per il setup iniziale.
   */
  private setupDesAltraCategoria(
    jsCatProg: IJsCategorieProgettuali
  ): IJsCategorieProgettuali {
    // Verifico l'input
    if (!jsCatProg) {
      // Non esiste, ritorno l'oggetto stesso
      return jsCatProg;
    }

    // Recupero le informazioni dall'oggetto
    const elencoCat = jsCatProg.elenco_categorie;

    // Verifico che esista l'elenco dati
    if (!elencoCat || elencoCat.length === 0) {
      // Non esistono dati definisco una descrizione vuota
      jsCatProg.des_altra_categoria_oggetto = '';
      // Ritorno l'oggetto
      return jsCatProg;
    }

    // Effettuo un FIND per cercare la categoria ALTRO
    const catOggALTRO = elencoCat.find((cat: CategoriaOggetto) => {
      // Per la map devo controllare se l'oggetto ha il codice ALTRO
      const codCatALTRO = CodiciCategoriaOggetto.ALTRO;
      // Verifico il codice della categoria
      return cat.cod_categoria_oggetto === codCatALTRO;
    });

    // Definisco un nuovo oggetto di ritorno
    const jsCatProgUpd: IJsCategorieProgettuali = {
      elenco_categorie: elencoCat,
    };
    // Verifico se è stato trovato l'oggetto categoria ALTRO
    if (catOggALTRO != undefined) {
      // Aggiungo la descrizione prendendola dall'oggetto
      jsCatProgUpd.des_altra_categoria_oggetto = catOggALTRO.des_altra_categoria_oggetto;
    }

    // Ritorno un oggetto con le informazioni aggiornate
    return jsCatProgUpd;
  }

  /**
   * ###################################################
   * PREPARAZIONE PER DATO A DB JS_CATEGORIE_PROGETTUALI
   * ###################################################
   */

  /**
   * Funzione che gestisce le informazioni del campo JS_CATEGORIE_PROGETTUALI.
   * Se esiste un elenco JS_CATEGORIE_PROGETTUALI e contiene un oggetto con codice "ALTRO", allora aggiorno le informazioni dell'oggetto stesso.
   * La modifica avviene per gestire la compatibilita' con il DB.
   * @param formioData IVincaFormioScrApp contenente l'oggetto "data" restituito come risultato del submit del formio.
   * @returns any contenente l'oggetto manipolato secondo le configurazioni del formio data.
   */
  protected handleJsCategorieProgettuali(formioData: IVincaFormioScrApp): any {
    // Verifico l'input
    if (!formioData) {
      // Ritorno l'oggetto stesso
      return formioData;
    }

    // Creo una copia dell'oggetto per gestire in maniera puntuale le modifiche
    let data = cloneDeep(formioData);

    // Estraggo l'oggetto di gestione delle categorie progettuali
    const jsCatProg: IJsCategorieProgettuali = data.JS_CATEGORIE_PROGETTUALI;
    // Aggiorno l'oggetto JS_CATEGORIE_PROGETTUALI secondo le logiche per i dati DB
    data.JS_CATEGORIE_PROGETTUALI = this.handleDesAltraCategoria(jsCatProg);

    // Rimuovo la proprietà dell'oggetto gestita dal formio
    delete data.JS_CATEGORIE_PROGETTUALI.des_altra_categoria_oggetto;

    // Ritorno il dato aggiornato
    return data;
  }

  /**
   * Funzione che gestisce le logiche per formattare le informazioni generate dal formio, in un formato compatibile con il DB.
   * @param jsCatProg IJsCategorieProgettuali con le informazioni da gestire.
   * @returns IJsCategorieProgettuali formattato per il salvataggio su DB.
   */
  private handleDesAltraCategoria(
    jsCatProg: IJsCategorieProgettuali
  ): IJsCategorieProgettuali {
    // Verifico l'input
    if (!jsCatProg) {
      // Non esiste, ritorno l'oggetto stesso
      return jsCatProg;
    }

    // Recupero le informazioni dall'oggetto
    const elencoCat = jsCatProg.elenco_categorie;
    const desAltraCat = jsCatProg.des_altra_categoria_oggetto;

    // Verifico che esista l'elenco dati
    if (!elencoCat || elencoCat.length === 0) {
      // Non esistono dati, ritorno l'oggetto stesso
      return jsCatProg;
    }

    // Effettuo un MAP delle informazioni per l'elenco delle categorie
    const elencoCatUpd = elencoCat.map((cat: CategoriaOggetto) => {
      // Per la map devo controllare se l'oggetto ha il codice ALTRO
      const codCatALTRO = CodiciCategoriaOggetto.ALTRO;
      // Verifico il codice della categoria
      if (cat.cod_categoria_oggetto === codCatALTRO) {
        // E' un oggetto ALTRO, aggiorno la descrizione
        cat.des_altra_categoria_oggetto = desAltraCat;
        // #
      } else {
        // Imposto la descrizione altra categoria a stringa vuota
        cat.des_altra_categoria_oggetto = '';
      }
      // Ritorno l'oggetto aggiornato
      return cat;
    });

    // Ricreo l'oggetto per il ritorno
    const jsCatProgUpd: IJsCategorieProgettuali = {
      elenco_categorie: elencoCatUpd,
      des_altra_categoria_oggetto: desAltraCat,
    };

    // Ritorno un oggetto con le informazioni aggiornate
    return jsCatProgUpd;
  }

  /**
   * #####################
   * FUNZIONI DI UTILITIES
   * #####################
   */

  /**
   * Funzione che ricerca e ritorna un oggetto ExtendedComponentSchema come componente del formio.
   * @param formioForm FormioForm contenente le configurazioni del formio.
   * @param key string che definisce il nome della chiave per il recupero del formio.
   * @returns ExtendedComponentSchema come componente definito per il formio. Se non esiste, ritorna undefined.
   */
  getFormioComponent(
    formioForm: FormioForm,
    key: string
  ): ExtendedComponentSchema {
    // Richiamo il servizio e ritorno il componente
    return this._formioUtilities.getFormioComponent(formioForm, key);
  }
}
