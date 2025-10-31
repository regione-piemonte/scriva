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
import { ScrivaComponenteApp } from '../../enums/scriva-utilities/scriva-utilities.enums';
import { Adempimento, Help, TipoAdempimento } from '../../models';
import { AppConfigService } from '../app-config.service';
import {
  IChiaveHelpParams,
  IChiaveHelpPrimitiveParams,
} from './utilities/help.interfaces';

@Injectable({ providedIn: 'root' })
export class HelpService {
  private beUrl = '';
  private codMaschera: string;
  private codContesto: string;

  constructor(private http: HttpClient, private config: AppConfigService) {
    this.beUrl = this.config.getBEUrl();
  }

  /**
   * Funzione di GET che combina le informazioni in input e scarica per una maschera specifica gli oggetti di help.
   * @param config IChiaveHelpParams con i parametri di configurazione.
   * @returns Observable<Help[]> con la lista di help scaricati.
   */
  getHelpMascheraByParams(config: IChiaveHelpParams): Observable<Help[]> {
    // Combino le informazioni per creare la chiave di ricerca
    const chiave: string = this.generaChiaveHelp(config);
    // Richiamo la funzione per lo scarico dati
    return this.getHelpByChiave(chiave);
  }

  /**
   * Funzione di GET che combina le informazioni in input e scarica per una maschera specifica gli oggetti di help.
   * @param config IChiaveHelpPrimitiveParams con i parametri di configurazione.
   * @returns Observable<Help[]> con la lista di help scaricati.
   */
  getHelpMascheraByParamsDomain(
    config: IChiaveHelpPrimitiveParams
  ): Observable<Help[]> {
    // Combino le informazioni per creare la chiave di ricerca
    const chiave: string = this.generaChiaveHelpPrimitives(config);
    // Richiamo la funzione per lo scarico dati
    return this.getHelpByChiave(chiave);
  }

  /**
   * Funzione che genera una chiave per gli help completa partendo dalla configurazione specifica.
   * @param config IChiaveHelpPrimitiveParams con le informazioni per generare la chiave.
   * @returns string con la chiave generata.
   */
  generaChiaveHelp(config: IChiaveHelpParams): string {
    // Combino le informazioni per creare la chiave di ricerca
    const configPrimitive: IChiaveHelpPrimitiveParams = {
      componente: config?.componente,
      codTipoAdempimento:
        config?.adempimento?.tipo_adempimento?.cod_tipo_adempimento,
      codAdempimento: config?.adempimento?.cod_adempimento,
      codQuadro: config?.quadro?.cod_quadro,
    };
    // Richiamo e ritorno la funzione di generazione della chiave
    return this.generaChiaveHelpPrimitives(configPrimitive);
  }

  /**
   * Funzione che genera una chiave per gli help completa partendo dalla configurazione specifica.
   * @param config IChiaveHelpPrimitiveParams con le informazioni per generare la chiave.
   * @returns string con la chiave generata.
   */
  generaChiaveHelpPrimitives(config: IChiaveHelpPrimitiveParams): string {
    // Estraggo dall'input le informazioni per il recupero degli help
    const componente: ScrivaComponenteApp = config?.componente;
    const codTipoAdempi: string = config?.codTipoAdempimento;
    const codAdempi: string = config?.codAdempimento;
    const codQuadro: string = config?.codQuadro;

    // Combino le informazioni per creare la chiave di ricerca
    const chiave: string = `${componente}.${codTipoAdempi}.${codAdempi}.${codQuadro}`;

    // Ritorno la chiave
    return chiave;
  }

  getHelpByChiave(chiave: string): Observable<Help[]> {
    return this.http.get<Help[]>(`${this.beUrl}/help/chiave/${chiave}`);
  }

  getCodMaschera(): string {
    return this.codMaschera;
  }

  setCodMaschera(codMasc: string) {
    this.codMaschera = codMasc;
  }

  setCodContesto(codCont: string) {
    this.codContesto = codCont;
  }

  getCodContesto(): string {
    return this.codContesto;
  }

  /**
   * Funzione di setup che recupera le informazioni dall'oggetto adempimento dell'istanza per il quadro e definire le informazioni per l'helper.
   * @param adempimento Adempimento con le informazioni dell'adempimento dell'istanza.
   */
  setCodContestoFromAdempimento(adempimento: Adempimento) {
    // Verifico l'input
    if (!adempimento) {
      // Manca la configurazione
      return;
    }

    // Estraggo il codice adempimento e il codice tipo adempimento
    const tipoAdempimento: TipoAdempimento = adempimento?.tipo_adempimento;
    const codTipoAdempi: string = tipoAdempimento?.cod_tipo_adempimento;
    const codAdempi: string = adempimento.cod_adempimento;

    // Dichiaro il codice del contesto per l'helper
    const codContesto: string = `.${codTipoAdempi}.${codAdempi}`;
    // Imposto il codice del contesto nell'helper
    this.setCodContesto(codContesto);
  }
}
