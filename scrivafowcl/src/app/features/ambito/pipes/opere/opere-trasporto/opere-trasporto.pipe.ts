/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Pipe, PipeTransform } from '@angular/core';
import { OggettoIstanza } from '../../../models';
import { OpereTrasportoService } from '../../../services/opere/opere-trasporto/opere-trasporto.service';

// #region "OPERA"

/**
 * Pipe dedicata alla opere.
 */
@Pipe({ name: 'opereTrasportoAccessoDatiTecnici' })
export class OpereTrasportoAccessoDatiTecniciOperaPipe
  implements PipeTransform
{
  /**
   * Costruttore del Pipe.
   */
  constructor(private _opereTrasporto: OpereTrasportoService) {}

  /**
   * Funzione che verifica le informazioni di un OggettoIstanza per le opere di trasporto e verifica se è editabile.
   * @param oggettoIstanza OggettoIstanza con le informazioni della riga di tabella passata dalla libreria ngx-datatable.
   * @param verificaConAccesso boolean che definisce se il check per l'accesso dei dati deve prendere in considerazione i codici delle opere per cui è garantito l'accesso. Se false, verrà controllata la lista con le tipologie oggette da escludere.
   * @returns boolean con il check per l'accesso ai dati tecnici.
   */
  transform(
    oggettoIstanza: OggettoIstanza,
    verificaConAccesso: boolean = true
  ): boolean {
    // Verifico l'input
    if (!oggettoIstanza || !oggettoIstanza.id_oggetto_istanza) {
      // Manca la configurazione minima
      return false;
    }

    // Recupero la tipologia dell'oggetto istanza
    const tipoOggIst: string =
      oggettoIstanza.tipologia_oggetto.cod_tipologia_oggetto;

    // Richiamo la funzione per verificare l'accesso ai dati tecnici per le opere di trasporto
    return this._opereTrasporto.accediDatiTecniciOpereTrasportoByTipoOggettoIstanza(
      tipoOggIst,
      verificaConAccesso
    );
    // #
  }
}
