/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {
  Adempimento,
  CompetenzaTerritorio,
  IstanzaCompetenza,
  Preferenza,
  TipoAdempimento,
} from '../models';
import { AdempimentoStatoIstanza } from '../models/adempimento/adempimento-stato-istanza.model';
import { AppConfigService } from './app-config.service';

@Injectable({
  providedIn: 'root',
})
export class AdempimentoService {
  private beUrl = '';

  constructor(private http: HttpClient, private config: AppConfigService) {
    this.beUrl = this.config.getBEUrl();
  }

  /*
   * API tipi-adempimento
   */
  // restituisce tipo-adempimento sulla base del codice
  getTipoAdempimentoByCode(
    codTipoAdempimento: string
  ): Observable<TipoAdempimento> {
    return this.http
      .get<TipoAdempimento[]>(
        `${this.beUrl}/tipi-adempimento?codice=${codTipoAdempimento}`
      )
      .pipe(map((tipiAdempimento) => tipiAdempimento[0]));
  }

  // restituisce collezione di tutti i tipi adempimento
  getTipiAdempimento(): Observable<TipoAdempimento[]> {
    return this.http.get<TipoAdempimento[]>(`${this.beUrl}/tipi-adempimento`);
  }

  // restituisce collezione di tutti i tipi adempimento con i preferiti dal compilante
  getTipiAdempimentoByCompilante(
    idCompilante: number
  ): Observable<TipoAdempimento[]> {
    return this.http.get<TipoAdempimento[]>(
      `${this.beUrl}/tipi-adempimento?id_compilante=${idCompilante}`
    );
  }

  // restituisce collezione di tipo-adempimento su base idAmbito
  getTipiAdempimentoByAmbito(idAmbito: number): Observable<TipoAdempimento[]> {
    return this.http.get<TipoAdempimento[]>(
      `${this.beUrl}/tipi-adempimento?id_ambito=${idAmbito}`
    );
  }

  salvaPreferenza(preferenza: Preferenza): Observable<Preferenza> {
    return this.http.post<Preferenza>(
      `${this.beUrl}/compilanti/preferenze`,
      preferenza
    );
  }

  cancellaPreferenza(idPreferenza: number) {
    return this.http.delete(
      `${this.beUrl}/compilanti/preferenze/${idPreferenza}`
    );
  }

  /*
   * API adempimenti
   */
  // restituisce collezione di tutti gli adempimenti
  getAdempimenti(): Observable<Adempimento[]> {
    return this.http.get<Adempimento[]>(`${this.beUrl}/adempimenti`);
  }

  // restituisce collezione di adempimenti su base idAmbito
  // never used
  getAdempimentiByAmbito(idAmbito: number): Observable<Adempimento[]> {
    return this.http.get<Adempimento[]>(
      `${this.beUrl}/adempimenti?id_ambito=${idAmbito}`
    );
  }

  // restituisce collezione di adempimenti su base idTipoAdempimento
  getAdempimentiByTipo(idTipoAdempimento: number): Observable<Adempimento[]> {
    return this.http.get<Adempimento[]>(
      `${this.beUrl}/adempimenti?id_tipo_adempimento=${idTipoAdempimento}`
    );
  }

  getAdempimentoById(idAdempimento: number): Observable<Adempimento> {
    return this.http
      .get<Adempimento>(`${this.beUrl}/adempimenti/${idAdempimento}`)
      .pipe(map((res) => res[0]));
  }

  getAdempimentoByCode(codAdempimento: string): Observable<Adempimento> {
    return this.http
      .get<Adempimento>(`${this.beUrl}/adempimenti?codice=${codAdempimento}`)
      .pipe(map((res) => res[0]));
  }

  /*
   * API esiti procedimenti per adempimenti (per liste combo)
   */
  getEsitiProcedimentiById(
    idAdempimento: number,
    attivi: boolean = true
  ): Observable<any> {
    return this.http.get<any>(
      `${this.beUrl}/adempimenti/${idAdempimento}/esiti-procedimento?attivi=${attivi}`
    );
  }

  getStatoProceduraStatale(): Observable<any> {
    return this.http.get<any>(`${this.beUrl}/stati-procedimento-statale`);
  }

  /*
   * API competenze-territorio (autorità competenti)
   * should be moved to a dedicate service...
   */
  getCompetenzeTerritorioByAdempimento(
    idAdempimento: number
  ): Observable<CompetenzaTerritorio[]> {
    return this.http.get<CompetenzaTerritorio[]>(
      `${this.beUrl}/competenze-territorio?id_adempimento=${idAdempimento}`
    );
  }

  getAutoritaCompetenteByIstanza(
    idIstanza: number
  ): Observable<IstanzaCompetenza[]> {
    return this.http.get<IstanzaCompetenza[]>(
      `${this.beUrl}/competenze-territorio?id_istanza=${idIstanza}`
    );
  }

  /**
   * Funzione che scarica la lista delle autorità competenti di un'istanza e ritorna l'autorità competente principale.
   * @param idIstanza number con l'id istanza per lo scarico dati.
   * @returns Observable<IstanzaCompetenza> con l'autorità compentete principale trovata, altrimenti undefined.
   */
  getAutoritaCompetentePrincipaleByIstanza(
    idIstanza: number
  ): Observable<IstanzaCompetenza> {
    // Richiamo la funzione di scarico dei dati
    return this.getAutoritaCompetenteByIstanza(idIstanza).pipe(
      map((autoritaCompetenti: IstanzaCompetenza[]) => {
        // Verifico il risultato per sicurezza
        autoritaCompetenti = autoritaCompetenti ?? [];
        // Cerco all'interno dell'array l'autorità competente principale
        return autoritaCompetenti.find((ac: IstanzaCompetenza) => {
          // Verifico il flag per l'autorità principale
          return ac?.flg_autorita_principale;
        });
        // #
      })
    );
  }

  /**
   * Recupera gli stati adempimento in base a idAdempimento
   * @param idAdempimento number
   * @returns AdempimentoStatoIstanza[]
   */
  getStatiIstanzaAdempimento(
    idAdempimento: number
  ): Observable<AdempimentoStatoIstanza[]> {
    return this.http.get<AdempimentoStatoIstanza[]>(
      `${this.beUrl}/adempimenti/${idAdempimento}/stati-istanza`
    );
  }

  saveAutoritaCompetente(
    payload: IstanzaCompetenza
  ): Observable<IstanzaCompetenza> {
    if (!payload.gestUID) {
      return this.http.post<IstanzaCompetenza>(
        `${this.beUrl}/competenze-territorio`,
        payload
      );
    } else {
      return this.http.put<IstanzaCompetenza>(
        `${this.beUrl}/competenze-territorio`,
        payload
      );
    }
  }

  checkCompetenzeOggetto(
    idIstanza: number,
    tipoSelezione: string = 'N',
    ac3: boolean = false
  ): Observable<CompetenzaTerritorio[]> {
    return this.http.get<CompetenzaTerritorio[]>(
      `${this.beUrl}/competenze-territorio/_check-ac?id_istanza=${idIstanza}&tipo_selezione=${tipoSelezione}&ac3=${ac3}`
    );
  }

  calcolaAutoritaCompetenti(
    idIstanza: number,
    compList: string
  ): Observable<IstanzaCompetenza[]> {
    return this.http.post<IstanzaCompetenza[]>(
      `${this.beUrl}/competenze-territorio/ac-secondarie?id_istanza=${idIstanza}&lvl=${compList}`,
      null
    );
  }

  deleteAutoritaCompetentiSecondarie(idIstanza: number) {
    return this.http.delete(
      `${this.beUrl}/competenze-territorio/ac-secondarie?id_istanza=${idIstanza}`
    );
  }
}
