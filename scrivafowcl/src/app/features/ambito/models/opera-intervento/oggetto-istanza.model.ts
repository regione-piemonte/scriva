/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { uniq } from 'lodash';
import { SoggettoIstanza } from '..';
import { OggettoIstanzaAreaProtetta } from './oggetto-istanza-area-protetta.model';
import { OggettoIstanzaNatura2000 } from './oggetto-istanza-natura2000.model';
import { OggettoIstanzaVincoloAutorizza } from './oggetto-istanza-vincolo-autorizza.model';
import { TipologiaOggetto } from './tipologia-oggetto.model';
import {
  GeometriaUbicazione,
  ParametriGeometriaUbicazione,
  UbicazioneOggettoIstanza,
} from './ubicazione-oggetto-istanza.model';

/**
 * Type custom che permette di gestire alcune delle logiche riferite alla struttura base dell'interfaccia OggettoIstanza.
 * ATTENZIONE: questa tipizzazione è da usare con tutte quelle interfacce che hanno ALMENO TUTTE LE PROPRIETA' di OggettoIstanza.
 */
export type OggettoIstanzaLike = OggettoIstanza | OggettoIstanzaGeometrieFE;

// OggettoIstanzaExtendedDTO
export interface OggettoIstanza {
  id_oggetto_istanza?: number;
  gestUID?: string;
  id_oggetto?: number;
  id_istanza: number;
  tipologia_oggetto?: TipologiaOggetto;
  ind_geo_stato?: string;
  cod_oggetto_fonte?: string;
  cod_utenza?: string;
  den_oggetto?: string;
  des_oggetto?: string;
  coordinata_x?: number;
  coordinata_y?: number;
  // flg_principale?: boolean;
  ind_livello?: number;
  id_oggetto_istanza_padre?: number;
  note_atto_precedente?: string;
  flg_geo_modificato?: boolean;
  id_masterdata_origine?: number;
  id_masterdata?: number;
  ubicazione_oggetto?: UbicazioneOggettoIstanza[];
  siti_natura_2000?: OggettoIstanzaNatura2000[];
  aree_protette?: OggettoIstanzaAreaProtetta[];
  // dati_catastali?: -> dentro ubicazione
  vincoli?: OggettoIstanzaVincoloAutorizza[];
  data_scadenza_procedimento?: string;
  valutazioneIncidenza?: string;
}

/**
 * Interfaccia che racchiude le informazioni di un OggettoIstanza ed aggiunge le informazioni
 */
export interface OggettoIstanzaGeometrie extends OggettoIstanza {
  geometrie_oggetto?: GeometriaUbicazione[];
}

/**
 * Classe con la struttura dell'oggetto FE gestito per l'omonima interfaccia dell'oggetto ritornato dal server.
 */
export class OggettoIstanzaGeometrieFE {
  // #region "VARIABILI"

  id_oggetto_istanza?: number;
  gestUID?: string;
  id_oggetto?: number;
  id_istanza: number;
  tipologia_oggetto?: TipologiaOggetto;
  ind_geo_stato?: string;
  cod_oggetto_fonte?: string;
  cod_utenza?: string;
  den_oggetto?: string;
  des_oggetto?: string;
  coordinata_x?: number;
  coordinata_y?: number;
  ind_livello?: number;
  id_oggetto_istanza_padre?: number;
  note_atto_precedente?: string;
  flg_geo_modificato?: boolean;
  id_masterdata_origine?: number;
  id_masterdata?: number;
  ubicazione_oggetto?: UbicazioneOggettoIstanza[];
  siti_natura_2000?: OggettoIstanzaNatura2000[];
  aree_protette?: OggettoIstanzaAreaProtetta[];
  vincoli?: OggettoIstanzaVincoloAutorizza[];
  data_scadenza_procedimento?: string;
  valutazioneIncidenza?: string;

  geometrie_oggetto?: GeometriaUbicazione[];

  /** SoggettoIstanza con la lista dei soggetti estratti tramite id_soggetto dei dati tecnici interni alle ubicazioni oggetti. */
  soggettiGeometrie?: SoggettoIstanza[] = [];

  // #endregion "VARIABILI"

  /**
   * Costruttore.
   */
  constructor(oggIstGeom?: OggettoIstanzaGeometrie) {
    // #region "ASSEGNAMENTO VARIABILI"

    this.id_oggetto_istanza = oggIstGeom?.id_oggetto_istanza;
    this.gestUID = oggIstGeom?.gestUID;
    this.id_oggetto = oggIstGeom?.id_oggetto;
    this.id_istanza = oggIstGeom?.id_istanza;
    this.tipologia_oggetto = oggIstGeom?.tipologia_oggetto;
    this.ind_geo_stato = oggIstGeom?.ind_geo_stato;
    this.cod_oggetto_fonte = oggIstGeom?.cod_oggetto_fonte;
    this.cod_utenza = oggIstGeom?.cod_utenza;
    this.den_oggetto = oggIstGeom?.den_oggetto;
    this.des_oggetto = oggIstGeom?.des_oggetto;
    this.coordinata_x = oggIstGeom?.coordinata_x;
    this.coordinata_y = oggIstGeom?.coordinata_y;
    this.ind_livello = oggIstGeom?.ind_livello;
    this.id_oggetto_istanza_padre = oggIstGeom?.id_oggetto_istanza_padre;
    this.note_atto_precedente = oggIstGeom?.note_atto_precedente;
    this.flg_geo_modificato = oggIstGeom?.flg_geo_modificato;
    this.id_masterdata_origine = oggIstGeom?.id_masterdata_origine;
    this.id_masterdata = oggIstGeom?.id_masterdata;
    this.ubicazione_oggetto = oggIstGeom?.ubicazione_oggetto;
    this.siti_natura_2000 = oggIstGeom?.siti_natura_2000;
    this.aree_protette = oggIstGeom?.aree_protette;
    this.vincoli = oggIstGeom?.vincoli;
    this.data_scadenza_procedimento = oggIstGeom?.data_scadenza_procedimento;
    this.valutazioneIncidenza = oggIstGeom?.valutazioneIncidenza;

    this.geometrie_oggetto = oggIstGeom?.geometrie_oggetto;

    // #endregion "ASSEGNAMENTO VARIABILI"
  }

  /**
   * Funzione che estrae dalla struttura dati delle geomestrie tutti gli id soggetto presenti all'interno dei dati.
   * @returns number[] con la lista degli id per i soggetti presenti dentro le geometrie.
   */
  private allIdSoggettiGeometrie(): number[] {
    // Definisco un array di appoggio
    const idSoggettiGeometrie: number[] = [];

    // Itero la lista di geometrie oggetto che contiene i dati di geolocalizzazione
    this.geometrie_oggetto?.forEach((geometriaOggetto: GeometriaUbicazione) => {
      // Estraggo l'oggetto contenente le configurazioni per la geometria
      const parametriGeometria: ParametriGeometriaUbicazione =
        geometriaOggetto?.geo_feature;
      // Estraggo dai parametri l'id soggetto
      const idSoggettoProperties: string =
        parametriGeometria?.properties?.id_soggetto;
      // Converto l'id soggetto properties in un number
      const idSoggetto: number = Number(idSoggettoProperties);

      // Verifico che l'id soggetto esista
      if (!isNaN(idSoggetto)) {
        // Esiste un id soggetto, lo aggiungo alla lista
        idSoggettiGeometrie.push(idSoggetto);
        // #
      }
      // #
    });

    // Ritorno la lista degli id soggetti estratti x le geometrie
    return idSoggettiGeometrie;
    // #
  }

  /**
   * Getter che recupera la lista di tutti gli id soggetto per ogni geometria presente.
   * @returns number[] con la lista degli id soggetto unici per le geometrie.
   */
  get idSoggettiGeometrie(): number[] {
    // Recupero la lista di tutti gli id soggetti per le geometrie
    const allIdSoggGeom: number[] = this.allIdSoggettiGeometrie();
    // Vado a rimuovere tutti i doppioni all'interno dell'array
    const idSoggGeom: number[] = uniq(allIdSoggGeom);

    // Ritorno la lista di id soggetti geometrie univoci
    return idSoggGeom;
    // #
  }
}
