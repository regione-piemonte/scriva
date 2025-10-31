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
import { Adempimento, ConfigAdempimento, DataQuadro, Istanza, IstanzaCompetenza } from 'src/app/shared/models';
import { AdempimentoService, ConfigurazioniScrivaService, StepManagerService } from 'src/app/shared/services';
import { TipoInfoAdempimento } from 'src/app/shared/services/configurazioni/utilities/configurazioni.enums';
import { AdvancedActionsChiavi } from '../enums/advanced-actions.enums';
import { map } from 'rxjs/operators';
import { DatePipe } from '@angular/common';
import { AllegatiService } from '../../ambito/services';
import { IstanzaResponsabile } from 'src/app/shared/models/istanza/istanza-responsabile.model';
import { AllegatiIntegrazioniService } from '../../ambito/services/allegati-integrazioni.service';
import { IntegrazioneIstanza } from 'src/app/shared/models/istanza/integrazione-istanza.model';


@Injectable({ providedIn: 'root' })
export class AdvancedActionService {
  infoAzTipoQuadro:TipoInfoAdempimento = TipoInfoAdempimento.azTipoQuadro;
  
  constructor(
    private allegatiIntegrazioniService: AllegatiIntegrazioniService,
    private allegatiService: AllegatiService,
    private adempimentoService: AdempimentoService,
    private configurazioniService: ConfigurazioniScrivaService,
    private datePipe: DatePipe,
    private stepManagerService: StepManagerService,
  ) {
  }

  getJsonData(istanza:Istanza, chiave: AdvancedActionsChiavi):Observable<Partial<DataQuadro>> {
    return this._getCodQuadroJsonData(
      istanza.adempimento,
      chiave
    ).pipe(
      map((response:ConfigAdempimento[]) => {
        const codTipoQuadro = response[0].valore || '';
        return {
          cod_tipo_quadro : codTipoQuadro,
          json_data_quadro: this._getJsonDataByCodQuadro(istanza,codTipoQuadro)
        };
      })     
    );
  }

  setJsonData(istanza: Istanza, dataQuadro:Partial<DataQuadro>,saveWithPut: boolean = false) {
    const requestDataQuadro:DataQuadro = {
      id_istanza: istanza.id_istanza,
      id_template_quadro: -1, // salva unicamente in base a cod_tipo_quadro recuperato in lettura da adempi_config
      json_data_quadro: this._stringJsonData(dataQuadro.json_data_quadro),
      cod_tipo_quadro: dataQuadro.cod_tipo_quadro 
    }
    return this.stepManagerService.salvaJsonDataQuadro(requestDataQuadro, saveWithPut);
  }

  /**
   * Recupera la lista IstanzaCompetenza filtrate per azione avanzata BO
   * con flg_autorita_principale o flg_autorita_assegnata_bo
   * @param idIstanza 
   * @returns Observable<IstanzaCompetenza[]>
   */
  getIstanzaCompetenzaList(idIstanza):Observable<IstanzaCompetenza[]> {
    return this.adempimentoService
    .getAutoritaCompetenteByIstanza(idIstanza)
    .pipe(
      map((list) =>
        list.filter((istComp) => istComp.flg_autorita_principale || istComp.flg_autorita_assegnata_bo)
      )
    );  
  }


  /**
   * Recupera la lista Allgeati filtrate per cod_tipologia_allegato ELENCO_INTEGR  
   * con flg_autorita_principale o flg_autorita_assegnata_bo
   * @param idIstanza 
   * @returns Observable<Allegato[]>
   */
  getIntegrazioni(istanza:Istanza):Observable<IntegrazioneIstanza[]> {
    return this.allegatiIntegrazioniService.apiGetIntegrazioni(istanza);  
  }

  /**
   *  Salva la integrazioneIstanza corrente
   * @param integrazioneIstanza IntegrazioneIstanza
   * @returns Observable<IntegrazioneIstanza[]>
   */
  setIntegrazione(integrazioneIstanza:IntegrazioneIstanza):Observable<IntegrazioneIstanza[]> {
    return this.allegatiIntegrazioniService.apiPut(integrazioneIstanza);
  }

  /**
   * Recupera allegato da servzio Allegati
   * @param uuid_index 
   * @returns 
   */
  getAllegatoByUuid(uuid_index: string ) {
    return this.allegatiService.getAllegatoByUuid(uuid_index);
  }

  
  /**
   * Ripulisco la lista dagli id_istanza_responsabile finti utilizzati per 
   * IstanzaResponsabile in memoria ma non ancora salvati
   * @returns IstanzaResponsabile[]
   */
  getResponsabiliForSave(responsabili: IstanzaResponsabile[], responsabiliAdded: IstanzaResponsabile[]):IstanzaResponsabile[] {
    let resp = [...responsabili];
    // Rimuovo id_istanza_responsabile finti creati solo per tenere in memoria le informazioni
    resp = resp.map(
      r=> {
        if(responsabiliAdded.find(i=>i.id_istanza_responsabile===r.id_istanza_responsabile)) {
          delete r.id_istanza_responsabile;
        }
        // la label non è modificabile lasciamo il compito al BE
        delete r.label_responsabile;
        return r;
      } 
    );
    return resp;
  }

  private _getCodQuadroJsonData(adempimento: Adempimento, chiave:AdvancedActionsChiavi):Observable<ConfigAdempimento[]>{
    return this.configurazioniService
        .getConfigurazioniByInfoAndChiave(
          adempimento.cod_adempimento,
          this.infoAzTipoQuadro,
          chiave
        );
  }

  /** metodi per la gestione del json data, da migrare */
  private _getJsonDataByCodQuadro(istanza: Istanza, codTipoQuadro: string) {
    return JSON.parse(istanza.json_data)[codTipoQuadro];
  }

  private _stringJsonData(jsonDataObj: any): string {
    return JSON.stringify(jsonDataObj);
  }

  /** metodi per la gestione delle date, da migrare */
  /**
   * utility che converte la data Be in data per il form
   * @param date stringa in formato yyyy-MM-dd HH:mm:ss
   * @returns stringa in formato yyyy-MM-dd per campi data
   */
  convertDateBE4Form(date:string): string {
    return date?date.split(' ')[0]:null;
  } 
  
  /**
   * utility che converte la data del form in data per il BE
   * @param date stringa in formato yyyy-MM-dd
   * @returns stringa in formato yyyy-MM-dd HH:mm:ss per salvataggio a BE
   */
  convertDateForm4BE(date:string, format: string = 'yyyy-MM-dd HH:mm:ss'): string {
    return this.datePipe.transform(
      date,
      format
    );
  } 
 
}
