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
import { SoggettoIstanza } from '../../../../features/ambito/models';
import { generaDenominazioneAzienda } from '../../../../features/ambito/pages/presentazione-istanza/usi-ulo-derivazioni/utilities/usi-ulo-derivazioni.functions';
import {
  formatImportoITA,
  importoITAToJsFloat,
  replaceAll,
} from '../../scriva-utilities/scriva-utilities.functions';
import { qdrCheckMinimoMassimo } from './formio-quadri.functions';
import {
  ICheckMinimoMassimo,
  IDERCheckPortataMedia,
} from './utilities/formio-quadri.interfaces';

/**
 * Servizio di utility per il componente scriva che gestisce i formio applicativi.
 */
@Injectable({ providedIn: 'root' })
export class FormioQuadriService {
  /**
   * Costruttore.
   */
  constructor() {}

  /**
   * Funzione che verifica le informazioni per la sezione "MEZZI MECCANICI" del quadro dettaglio progetto per VINCA SCR.
   * @param data any con la struttura dati del formio.
   * @returns boolean con il risultato del check sui dati.
   */
  qdrVincaScrVerificaMezziMeccanici(data: any): boolean {
    // Recupero tutte le chiavi della sezione del componente
    const dataRoot =
      data.CONTAINER_MEZZI_MECCANICI.JS_DECODIFICA_PROGETTO.MEZZI_MECCANICI;
    const mezzi_aerei_imbarcazioni = dataRoot.mezzi_aerei_imbarcazioni;
    const mezzi_movimento_terra = dataRoot.mezzi_movimento_terra;
    const mezzi_pesanti = dataRoot.mezzi_pesanti;

    const allValues = Object.assign(
      {},
      mezzi_aerei_imbarcazioni,
      mezzi_movimento_terra,
      mezzi_pesanti
    );

    // Controlla se dentro la variabile "component" puoi accedere direttamente a tutti i "components" e poi tramite chiave ti tiri fuori solo i flag.
    const flgList = this.retreiveFlgFromValues(allValues);

    // Verifico che o il flag "non pertinente" o uno dei flag dei vari mezzi sia settato a true
    const flgListaDefiniti = Object.keys(flgList).length > 0;
    const flg_non_pertinente = dataRoot.flg_non_pertinente;

    if (flgListaDefiniti || flg_non_pertinente) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Funzione che verifica le informazioni per la sezione "INQUINAMENTO RIFIUTI" del quadro dettaglio progetto per VINCA SCR.
   * @param data any con la struttura dati del container dell'inquinamento rifiuti.
   * @returns boolean con il risultato del check sui dati.
   */
  qdrVincaScrVerificaInquinamentoRifiuti(data: any): boolean {
    // Recupero tutte le chiavi della sezione del componente
    const dataRoot =
      data.containerRifiuti.JS_DECODIFICA_PROGETTO.INQUINAMENTO_RIFIUTI;
    const allValues = dataRoot.fonti_inquinamento_rifiuti;

    // Controlla se dentro la variabile "component" puoi accedere direttamente a tutti i "components" e poi tramite chiave ti tiri fuori solo i flag.
    const flgList = this.retreiveFlgFromValues(allValues);

    // Verifico che o il flag "non pertinente" o uno dei flag dei vari mezzi sia settato a true
    const flgListaDefiniti = Object.keys(flgList).length > 0;
    const flg_non_pertinente = dataRoot.flg_non_pertinente;

    if (flgListaDefiniti || flg_non_pertinente) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Funzione invocata per il recupero dei componenti flag dalle sezioni formio di Vinca Scr.
   * @notes Questa logica è stata ereditata da chi ha sviluppato FormIo, non mi son messo nemmeno a capirla viste le pressioni, riporto e basta.
   * @param values any[] con la lista degli elementi da verificare.
   * @returns any con un oggetto aventi le informazioni dei campi con flag.
   */
  private retreiveFlgFromValues(values: any[]): any {
    // Controlla se dentro la variabile "component" puoi accedere direttamente a tutti i "components" e poi tramite chiave ti tiri fuori solo i flag.
    let flgList = Object.keys(values)
      //Filter Object by key "flg_"
      .filter((key) => key.includes('flg_'))
      .reduce((obj, key) => {
        return Object.assign(obj, { [key]: values[key] });
      }, {});

    //Filter flgList
    flgList = Object.fromEntries(
      Object.entries(flgList).filter(([_, v]) => v != '')
    );

    // Ritorno l'oggetto con i flag
    return flgList;
  }

  /**
   * Funzione che recupera dai dati di un soggetto le informazioni per la denominazione azienda.
   * @param soggetto SoggettoIstanza con i dati del soggetto per la generazione della denominazione.
   * @returns string con la denominazione.
   */
  qdrUsiUloDERDenominazioneSoggetto(soggetto: SoggettoIstanza): string {
    // Richiamo la funzione condivisa per la denominazione azienda del componente specifico
    return generaDenominazioneAzienda(soggetto);
    // #
  }

  /**
   * Funzione che calcola il totale di superficie irrigata, date le informazioni (come lista) della edit grid per i dati di coltura.
   * @param colture any[] con la lista dei dati per le colture.
   * @returns string come totalizzatore numerico in formato italiano.
   */
  qdrAltriUsiDERCalcoloSuperficieIrrigata(colture: any[]): string {
    // Verifico l'input forzando un valore gestibile
    const coltureData: any[] = colture ?? [];

    // Definisco un totalizzatore
    let totaleSI: number = 0;

    // Itero la lista di dati per le colture
    coltureData.forEach((c: any) => {
      // Recupero il dato
      const superficieIrrigata: string = c.sup_irrigata ?? '0';
      // Converto la stringa in un numero
      const numberSI: number = importoITAToJsFloat(superficieIrrigata) ?? 0;
      // Sommo la superficie con il totalizzatore
      totaleSI += numberSI;
      // #
    });

    // Definisco il numero di decimali presente sul FormIo per il campo originale
    const decimals: number = 5;
    // Il totale calcolato potrebbe essere errato per un problema di calcolo di numeri con virgola mobile, effetto un arrotondamento per i decimali
    totaleSI = parseFloat(totaleSI.toFixed(decimals));

    // Vado a convertire il numero calcolato in un formato importo ITA
    let totaleSI_ITA: string = formatImportoITA(totaleSI, decimals);
    // L'importo italiano ha il "." come divisore per le migliaia, vado a rimuoverlo
    const trovaPunto = new RegExp('\\.');
    totaleSI_ITA = replaceAll(totaleSI_ITA, trovaPunto, '');

    // Ritorno il valore calcolato
    return totaleSI_ITA;
  }

  /**
   * Funzione di check che verifica i controlli per il campo della portata media.
   * I controlli della portata media possono essere:
   * - Portata maggiore di 0;
   * - Poirtata minore uguale a portata massima;
   * @param params IDERCheckPortataMedia con le informazioni per effettuare i controlli sulla portata.
   * @returns boolean con i risultati dei controlli.
   */
  qdrDERCheckPortataMedia(params: IDERCheckPortataMedia): boolean {
    // Recupero i parametri dall'input
    const portataMediaParam: string = params?.portataMedia;
    const portataMassimaParam: string = params?.portataMassima;
    const checkZero: boolean = params?.checkZero;
    const massimoNotZero: boolean = params?.massimoNotZero;

    // Effettuo un parse dell'oggetto parametro per parametro
    const qdrMinimoMassimoParam: ICheckMinimoMassimo = {
      minimo: portataMediaParam,
      massimo: portataMassimaParam,
      checkZero,
      massimoNotZero,
    };

    // Richiamo e ritorno la funzione di minimo massimo
    return qdrCheckMinimoMassimo(qdrMinimoMassimoParam);
    // #
  }

  /**
   * Funzione di check che verifica i controlli per il campo minimo massimo.
   * @param params ICheckMinimoMassimo con le informazioni per effettuare i controlli.
   * @returns boolean con i risultati dei controlli.
   */
  qdrCheckMinimoMassimo(params: ICheckMinimoMassimo): boolean {
    // Richiamo e ritorno la funzione di minimo massimo
    return qdrCheckMinimoMassimo(params);
    // #
  }
}
