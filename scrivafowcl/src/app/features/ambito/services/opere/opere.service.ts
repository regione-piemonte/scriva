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
import { differenceBy } from 'lodash';
import { Observable, of, throwError } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
import { ScrivaServerError } from '../../../../core/classes/scriva.classes';
import { DataQuadro } from '../../../../shared/models';
import { StepManagerService } from '../../../../shared/services';
import { FormioService } from '../../../../shared/services/formio/formio.service';
import {
  OggettoIstanza,
  OggettoIstanzaLike,
  Opera,
  UbicazioneOggetto,
  UbicazioneOggettoIstanza,
} from '../../models';
import { IndLivelliOpere } from '../../pages/presentazione-istanza/opere/utilities/opere.enums';
import { IAssociaOggettiIstanza } from './utilities/opere-modal.interfaces';
import {
  IOpereSaveJsonDataRes,
  IParamsDataQuadro,
  IParamsDataRiepilogo,
} from './utilities/opere.interfaces';

@Injectable({ providedIn: 'root' })
export class OpereService {
  /**
   * Costruttore.
   */
  constructor(
    private _formio: FormioService,
    private _stepManager: StepManagerService
  ) {}

  /**
   * ###################
   * FUNZIONI DI UTILITY
   * ###################
   */

  // #region "FUNZIONI DI UTILITY"

  /**
   * Funzione gestione dati per le tipologie opere dalla configurazione stringa dal DB.
   * La configurazione in input prevederà dei valori concatenati tramite carattere "|".
   * @param configString string con le configurazioni da estrarre.
   * @returns string[] con la lista di tipologie opere mappata.
   */
  tipologieOpereByConfig(configString: string): string[] {
    // Verifico l'input
    configString = configString ?? '';

    // Effetto lo split dei valori
    let tipoOpereConfigs: string[];
    tipoOpereConfigs = configString.split('|');
    // Effettuo un'operazione di remap dei dati, togliendo eventuali spazi vuoti
    return tipoOpereConfigs.map((tcc: string) => {
      // Effettuo la trim sul valore
      return tcc?.trim();
      // #
    });
  }

  /**
   * Funzione di comodo che gestisce un errore dal servizo come 404 not found per una lista di elementi.
   * Se l'errore è un 404, permette la continuzione delle logiche, ritornando un array vuoto.
   * Se è un altro tipo di errore, viene generata e ritornata un'eccezione come Observable.
   * @param error ScrivaServerError con l'errore generato dalla chiamata.
   * @returns Observable<any[]> con i dati gestiti. Altrimenti un observable con l'errore in input.
   */
  allowNotFoundArray<T>(error: ScrivaServerError): Observable<T[]> {
    // Verifico se non sono stati trovati elementi
    if (error?.status == 404) {
      // Non blocco la chiamata, ma ritorno array vuoto
      return of([]);
      // #
    } else {
      // E' un altro tipo di errore, ritorno l'eccezione
      return throwError(error);
    }
  }

  /**
   * Funzione di filtro che effettua delle logiche di selezione per gli oggetti in input.
   * Partendo da una fonte di oggetti scaricati per istanza, si filtrano tutti quegli oggetti compatibili con le tipologie opere del componente.
   * @param opereOggettiIstanza Opera[] | OggettoIstanza[] con la lista di elementi da filtrare.
   * @param tipologiaOpere string[] con la configurazione delle opere del quadro.
   * @returns Opera[] | OggettoIstanza[] con la lista di elementi filtrati.
   */
  filterOpereOggettiIstanzaByQuadro(
    opereOggettiIstanza: Opera[] | OggettoIstanza[],
    tipologieOpere: string[]
  ): Opera[] | OggettoIstanza[] {
    // Recupero le tipologie di captazione
    let tipiOpereQuadro: string[];
    tipiOpereQuadro = tipologieOpere ?? [];

    // Forzo il parse per l'array in input per una lista di any
    const list: any[] = opereOggettiIstanza ?? [];

    // Filtro le opere scaricate per istanza, verificando se all'interno delle tipologie delle opere configurate nel componente sono presenti
    let opereIstanzaByQuadro: Opera[] | OggettoIstanza[];
    opereIstanzaByQuadro = list?.filter((element: Opera | OggettoIstanza) => {
      // Verifico se l'opera dall'istanza è contenuta nella lista per le opere del quadro
      return tipiOpereQuadro.some((tipoOperaQuadro: string) => {
        // Recupero dal tipo opera istanza il codice da verificare
        let tipoOperaIstanza: string;
        tipoOperaIstanza = element?.tipologia_oggetto?.cod_tipologia_oggetto;
        // Ritorno il match tra i codici che identificano le tipologie di opere
        return tipoOperaIstanza === tipoOperaQuadro;
        // #
      });
    });

    // Ritorno la lista di elementi filtrati
    return opereIstanzaByQuadro;
  }

  /**
   * Funzione di filtro che effettua delle logiche di selezione per gli oggetti in input.
   * Partendo da una fonte di oggetti scaricati per istanza, si filtrano tutti gli oggetti per il loro "ind_livello".
   * @param oggettiIstanza OggettoIstanzaLike[] con la lista di elementi da filtrare.
   * @param livello number con l'indicazione di filtro per i dati.
   * @returns OggettoIstanzaLike[] con la lista di elementi filtrati.
   */
  filterOggettiIstanzaByLivello(
    oggettiIstanza: OggettoIstanzaLike[],
    livello: number
  ): OggettoIstanzaLike[] {
    // Forzo il parse per l'array in input per una lista di any
    const list: any[] = oggettiIstanza ?? [];

    // Filtro le opere scaricate per istanza, verificando se all'interno delle tipologie delle opere configurate nel componente sono presenti
    let oggettiIstanzaByLivello: OggettoIstanzaLike[];
    oggettiIstanzaByLivello = list?.filter((element: OggettoIstanzaLike) => {
      // Verifico se l'opera dall'istanza è contenuta nella lista per le opere del quadro
      return element?.ind_livello == livello;
      // #
    });

    // Ritorno la lista di elementi filtrati
    return oggettiIstanzaByLivello;
  }

  /**
   * Funzione che va ad estrarre le informazioni dei dati tecnici di un'opera dato l'oggetto dati tecnici estratto dal json data.
   * @param datiTecnici any con l'oggetto contenente i dati tecnici di un'opera.
   * @param opera Opera con l'oggetto che definisce le informazioni dell'opera.
   * @param oggettoIstanza OggettoIstanza con le informazioni dell'oggetto istanza di riferimento per il recupero dati.
   * @returns any con la struttura dati di una specifica sezione dei dati tecnici. Se non definiti, ritorno undefined.
   * @jira SCRIVA-1003
   */
  estraiDatiTecniciOpera(
    datiTecnici: any,
    opera: Opera,
    oggettoIstanza: OggettoIstanza
  ) {
    // Richiamo il servizio definito per il servizio formio
    return this._formio.estraiDatiTecniciOpera(
      datiTecnici,
      opera,
      oggettoIstanza
    );
  }

  // #endregion "FUNZIONI DI UTILITY"

  // #region "OPERE E OGGETTI ISTANZA"

  /**
   * Funzione che gestisce l'aggiornamento degli oggettiIstanza rispetto alle opere definite in input.
   * @param configs IAssociaOggettiIstanza con le informazioni di configurazione per l'associazione oggetti istanza.
   * @returns OggettoIstanza[] che definisce tutti i nuovi oggetti istanza da salvare.
   */
  associaOggettiIstanza(configs: IAssociaOggettiIstanza): OggettoIstanza[] {
    // Verifico l'input ed eventualmente assegno un default
    if (!configs) {
      // Nessuna configurazione
      return [];
    }

    // Estraggo dall'oggetto di configurazione i dati per l'associazione
    let { istanza, oggettiIstanza, opere, idOggettoIstanzaPadre } = configs;
    // Vedo a definire dei valori di default per la gestione degli array
    oggettiIstanza = oggettiIstanza ?? [];
    opere = opere ?? [];
    idOggettoIstanzaPadre = idOggettoIstanzaPadre ?? null;
    // Recupero l'id istanza dall'oggetto
    const idIstanza = istanza?.id_istanza;

    // Se la lista oggettiIstanza è vuota, utilizzo la lista in input per la popolazione dei dati
    if (oggettiIstanza.length === 0) {
      // Lancio la funzione di supporto per la creazione/salvataggio degli oggetti istanza
      return this.creaOggettiIstanzaDaAssociazioni(
        opere,
        idIstanza,
        idOggettoIstanzaPadre
      );
      // #
    } else {
      // Definisco la proprietà dell'oggetto come discrimanate per la differenza tra array
      const property = 'id_oggetto';
      // [opere per oggetti istanza] Recupero i possibili oggetti "opera" dalla lista opere che NON si trovano già nella lista oggettiIstanza
      const operePerOI = differenceBy(opere, oggettiIstanza, property);

      // Se ci sono delle opere non presenti (per id_oggetto) nell'array oggettiIstanza, vado a generare le informazioni dedicate
      if (operePerOI.length > 0) {
        // Ritorno la lista di elementi generati
        return this.creaOggettiIstanzaDaAssociazioni(
          operePerOI,
          idIstanza,
          idOggettoIstanzaPadre
        );
        // #
      } else {
        // Tutte le opere sono già definite per id_oggetto all'interno di oggettiIstanza
        return [];
      }
    }
  }

  /**
   * Funzione dedicata alla generazione di OggettoIstanza, partendo da una lista Opera[].
   * @param opere Opera[] come base dati per la generazione delle informazioni OggettoIstanza.
   * @param idIstanza number che definisce l'id istanza di riferimento per gli oggetti OggettoIstanza.
   * @returns OggettoIstanza[] come lista di oggetti generati dalla funzione.
   */
  creaOggettiIstanzaDaAssociazioni(
    opere: Opera[],
    idIstanza: number,
    idOggettoIstanzaPadre: number
  ): OggettoIstanza[] {
    // Verifico l'input
    if (!opere || opere.length === 0) {
      // Non ci sono dati per la generazione
      return [];
    }

    // Rimappo le informazioni dell'array opere "convertendole" in un array di tipo OggettoIstanza
    const oggettiIstanza: OggettoIstanza[] = opere.map((opera: Opera) => {
      // Genero un OggettoIstanza
      let oggettoIstanza: OggettoIstanza;
      oggettoIstanza = this.convertOperaToOggettoIstanza(
        opera,
        idIstanza,
        idOggettoIstanzaPadre
      );
      // Ritorno l'oggetto generato
      return oggettoIstanza;
    });

    // Ritorno la lista generata
    return oggettiIstanza;
  }

  /**
   * Funzione di comodo, che genera un oggetto OggettoIstanza partendo da un oggetto Opera.
   * @param opera Opera come base dati per la generazione dell'oggetto OggettoIstanza.
   * @param idIstanza number che definisce l'id istanza di riferimento per l'oggetto OggettoIstanza.
   * @returns OggettoIstanza generato dalla conversione.
   */
  private convertOperaToOggettoIstanza(
    opera: Opera,
    idIstanza: number,
    idOggettoIstanzaPadre: number
  ): OggettoIstanza {
    // Verifico l'input
    if (!opera) {
      // Nessun oggetto di configurazione
      return undefined;
    }

    // Creo un OggettoIstanza partendo dai dati opera
    const oggettoIstanza: OggettoIstanza = {
      id_oggetto: opera.id_oggetto,
      id_istanza: idIstanza,
      tipologia_oggetto: opera.tipologia_oggetto,
      cod_oggetto_fonte: opera.cod_oggetto_fonte,
      den_oggetto: opera.den_oggetto,
      des_oggetto: opera.des_oggetto,
      coordinata_x: opera.coordinata_x,
      coordinata_y: opera.coordinata_y,
      id_masterdata_origine: opera.id_masterdata_origine,
      id_masterdata: opera.id_masterdata,
      // flg_principale: true,
      // Sono con ind_livello 2 il livello 1 viene salvato nel quadro dati generali
      ind_livello: IndLivelliOpere.secondo,
      id_oggetto_istanza_padre: idOggettoIstanzaPadre,
      ubicazione_oggetto: [],
    };

    // Recupero dall'opera l'array "ubicazione_oggetto"
    const ubicazioniOgg: UbicazioneOggetto[] = opera.ubicazione_oggetto;
    // Verifico se esistono ubicazioni oggetti
    if (ubicazioniOgg && ubicazioniOgg.length > 0) {
      // Esistono ubicazioni oggetti, effettuo la conversione del dato per l'oggetto istanza
      oggettoIstanza.ubicazione_oggetto = ubicazioniOgg.map(
        (ubOgg: UbicazioneOggetto) => {
          // Lancio e ritorno la conversione dell'oggetto
          return this.convertUbicazioneOggettoToUbicazioneOggettoIstanza(ubOgg);
        }
      );
    }

    // Conversione dell'oggetto completata, ritorno l'oggetto
    return oggettoIstanza;
  }

  /**
   * Funzione di conversione dati da un oggetto UbicazioneOggetto a UbicazioneOggettoIstanza.
   * @param ubicazioneOgg UbicazioneOggetto da convertire.
   * @returns UbicazioneOggettoIstanza generato.
   */
  private convertUbicazioneOggettoToUbicazioneOggettoIstanza(
    ubicazioneOgg: UbicazioneOggetto
  ): UbicazioneOggettoIstanza {
    // Verifico l'input
    if (!ubicazioneOgg) {
      // Non è stata passata nessuna configurazione
      return undefined;
    }

    // Definisco l'oggetto UbicazioneOggettoIstanza
    const ubicazioneOggIst: UbicazioneOggettoIstanza = {
      comune: ubicazioneOgg.comune,
      den_indirizzo: ubicazioneOgg.den_indirizzo,
      num_civico: ubicazioneOgg.num_civico,
      des_localita: ubicazioneOgg.des_localita,
      ind_geo_provenienza: ubicazioneOgg.ind_geo_provenienza,
    };

    // Ritorno l'oggetto generato
    return ubicazioneOggIst;
  }

  // #endregion "OPERE E OGGETTI ISTANZA"

  // #region "GENERAZIONE CHIAVE IDENTIFICAZIONE DATI TECNICI"

  /**
   * Funzione di comodo che genera in maniera univoca la chiave identificativa dei dati tecnici.
   * La chiave è composta da un prefisso e da un codice estratto dall'oggetto Opera.
   * @param prefisso string con il prefisso della chiave.
   * @param opera Opera con le informazioni per il recupero del codice per la chiave.
   * @returns string con la chiave generata.
   */
  keyDatiTecniciOpera(prefisso: string, opera: Opera): string {
    // Recupero il codice per il tipo dell'opera
    const codeTipoOpera: string =
      opera?.tipologia_oggetto?.cod_tipologia_oggetto;
    // Genero e ritorno la chiave
    return this.keyDatiTecniciTipoOpera(prefisso, codeTipoOpera);
  }

  /**
   * Funzione di comodo che genera in maniera univoca la chiave identificativa dei dati tecnici.
   * La chiave è composta da un prefisso e da un codice estratto dall'oggetto Opera.
   * @param prefisso string con il prefisso della chiave.
   * @param opera Opera con le informazioni per il recupero del codice per la chiave.
   * @returns string con la chiave generata.
   */
  keyDatiTecniciTipoOpera(prefisso: string, tipoOpera: string): string {
    // Genero e ritorno la chiave
    return `${prefisso}${tipoOpera}`.trim();
  }

  // #endregion "GENERAZIONE CHIAVE IDENTIFICAZIONE DATI TECNICI"

  // #region "SALVATAGGIO INFORMAZIONI JSON DATA"

  /**
   * Funzione che gestisce il salvataggio dei json data per il quadro dell'opera e l'aggiornamento del json data per i dati di riepilogo.
   * @param paramsDataQuadro IParamsDataQuadro con oggetto di configurazione per la chiamata al salvataggio dati per i dati del quadro.
   * @param paramsDataRiepilogo IParamsDataRiepilogo con oggetto di configurazione per la chiamata al salvataggio dati per i dati del riepilogo.
   * @returns Observable<DataQuadro> con il risultato dell'operazione per l'ultima operazione di salvataggio effettuata, ossia il riepilogo.
   * @refactor Richiesto refactor del codice lato BE-FO. Sarebbe meglio che facesse il servizio in un'unica transazione.
   */
  saveJsonDataQuadro(
    paramsDataQuadro: IParamsDataQuadro,
    paramsDataRiepilogo: IParamsDataRiepilogo
  ): Observable<IOpereSaveJsonDataRes> {
    // Estraggo dai parametri le informazioni per il salvataggio dati
    const jsonDataQuadro: DataQuadro = paramsDataQuadro?.dataQuadro;
    const isPut: boolean = paramsDataQuadro?.isPut ?? false;
    const jsonDataRiepilogo: DataQuadro = paramsDataRiepilogo?.dataQuadro;

    // Lancio e ritorno i servizi di salvataggio
    return this._stepManager.salvaJsonDataQuadro(jsonDataQuadro, isPut).pipe(
      switchMap((dataQuadroDER: DataQuadro) => {
        // Salvate le informazioni del quadro, aggiorno il riepilogo
        return this._stepManager
          .salvaJsonDataQuadro(jsonDataRiepilogo, true)
          .pipe(
            map((dataQuadroRIE: DataQuadro) => {
              // Metto insieme le informazioni e le ritorno
              const response: IOpereSaveJsonDataRes = {
                quadro: dataQuadroDER,
                riepilogo: dataQuadroRIE,
              };
              // Ritorno la risposta
              return response;
              // #
            })
          );
        // #
      })
    );
  }

  // #endregion "SALVATAGGIO INFORMAZIONI JSON DATA"
}
