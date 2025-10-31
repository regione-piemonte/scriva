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
import { Observable, Subject, forkJoin, of } from 'rxjs';
import { Istanza, IstanzaCompetenza } from '../../models';
import { catchError, map } from 'rxjs/operators';
import { AmbitoService } from 'src/app/features/ambito/services';
import { StepManagerService } from '../step-manager/step-manager.service';
import { FormioImports } from '../../models/formio/formio-imports.model';
import { FormioImportsConfig } from '../../models/formio/formio-imports-config';
import { AdempimentoService } from '../adempimento.service';
import { AllegatiService } from 'src/app/features/ambito/services';
import { ConfigurazioniScrivaService } from '../configurazioni/configurazioni-scriva.service';
import { TipoInfoAdempimento } from '../configurazioni/utilities/configurazioni.enums';
import { environment } from 'src/environments/environment';

/**
 * Servizio di utility per il componente scriva che gestisce import nel formio
 * di informazioni adempimento
 */
@Injectable({ providedIn: 'root' })
export class FormioImporterService {
  /** BehaviorSubject per notifiche applicative  */
  private imports$ = new Subject<FormioImports>();
  info: TipoInfoAdempimento = TipoInfoAdempimento.acWeb;

  /**
   * Costruttore.
   */
  constructor(
    private ambitoService: AmbitoService,
    private adempimentoService: AdempimentoService,
    private stepManagerService: StepManagerService,
    private allegatiService: AllegatiService,
    private configurazioniScrivaService: ConfigurazioniScrivaService,
  ) {}

  /**
   * Resituisco Observable con il contenuto degli imports
   * @returns Observable
   */
  getImports(): Observable<FormioImports> {
    // Restistuisco il subject
    return this.imports$.asObservable();
  }

  setImports(istanza: Istanza, importsConfig: FormioImportsConfig) {
    if(!importsConfig) {
      this._nextImports();
      return;
    }
    let calls = [];
    calls.push(importsConfig.GETJSONDATA?this._getJsonData(istanza):of(null));
    calls.push(importsConfig.GETSOGGETTI?this._getSoggetti(istanza):of(null));
    calls.push(importsConfig.GETISTANZACOMPETENZAACP?this._getIstanzaCompetenzaAcPrincipaleList(istanza):of(null));
    calls.push(importsConfig.DOCSEGR?this._getAllegatoDOCSEG(istanza.id_istanza):of(null));
    calls.push(importsConfig.DOCAMM?this._getAllegatoDOCAMM(istanza.id_istanza):of(null));
    calls.push(importsConfig.GESTIONEPRIVACY?this._getGestionePrivacy(istanza):of(null));

    forkJoin(calls).subscribe(
      res => {
        this._callbackImports(res,importsConfig.GETISTANZA?istanza:null,importsConfig.GETJSONDATA);
      },
      err => {
        this._nextImports();
      }
    );
  }

  _getJsonData(istanza: Istanza) {
    return this.stepManagerService.getJsonDataAll(istanza.id_istanza);
  }

  _getSoggetti(istanza: Istanza) {
    return this.ambitoService.getSoggettiIstanzaByIstanza(istanza.id_istanza).pipe(catchError(err => of([])));
  }

  /**
   * Recupera la lista IstanzaCompetenza filtrate
   * con flg_autorita_principale
   * @param idIstanza 
   * @returns Observable<IstanzaCompetenza[]>
   */
  _getIstanzaCompetenzaAcPrincipaleList(istanza: Istanza):Observable<IstanzaCompetenza[]> {
    return this.adempimentoService
    .getAutoritaCompetenteByIstanza(istanza.id_istanza)
    .pipe(
      map((list) =>
        list.filter((istComp) => istComp.flg_autorita_principale)
      )
    );  
  }

  _getAllegatoDOCSEG(idIstanza: number) {
    return this.allegatiService.checkCategorieObbligatorie(idIstanza)
    .pipe(
      map((resp)=>{
        //SCRIVA-1149 check categorie allegato DOC-SEGR-IND-COM
        return resp.some((allegato) => allegato.cod_categoria_allegato === "DOC-SEGR-IND-COM");
      }
      ));
    }

    _getAllegatoDOCAMM(idIstanza: number) {
      return this.allegatiService.checkCategorieObbligatorie(idIstanza)
      .pipe(
        map((resp)=>{
          //SCRIVA-1149 check categorie allegato DOC-AMM-NOPUBB 
          return resp.some((allegato) => allegato.cod_categoria_allegato === "DOC-AMM-NOPUBB");
        }
        ));
    }

    _getGestionePrivacy(istanza: Istanza){
      let parseJson = JSON.parse(istanza.json_data);      
      let codCompetenzaTerritorio = parseJson.QDR_CONFIG.ACPratica[0].cod_competenza_territorio
      console.log('codCompetenzaTerritorio: ',codCompetenzaTerritorio); // A1600A
      
      let codAdempimento = istanza.adempimento.cod_adempimento // VER o VAL
      let codTipoAdempimento = istanza.adempimento.tipo_adempimento.cod_tipo_adempimento // VIA  
      // this.info -> 'WEB_AC_GDPR'

      return this.configurazioniScrivaService.getConfigurazioniByInfoAndChiave(
        codAdempimento,
        this.info,
        codCompetenzaTerritorio
      )
      .pipe(
        map((res)=>{
          let acWeb = res[0].valore          
          let fo = {
            acWeb: acWeb,
            codTipoAdempimento: codTipoAdempimento,
            codCompetenzaTerritorio: codCompetenzaTerritorio
          }
          console.log('fo: ',fo);
          return fo // res[0].valore
        }
      ));
    }

  _callbackImports(res,istanza:Istanza, getJsonDataCodTipoQuadro: string[]=[]){
    let jsondata = this._filterJsonData(res[0],getJsonDataCodTipoQuadro);
    this._nextImports(
      {
        JSONDATA: jsondata,
        SOGGETTI: res[1],
        ISTANZA: istanza,
        ISTANZACOMPETENZAACP: res[2],
        DOCSEGR: res[3],
        DOCAMM: res[4],
        GESTIONEPRIVACY: res[5],
        GDPRDOCPATH: environment.GDPR_docUrl
      }
    );
  }

  _filterJsonData(allJsonData:any ,getJsonDataCodTipoQuadro: string[]=[]){
    if(!allJsonData)
      return null;

    if(getJsonDataCodTipoQuadro.length===1 && getJsonDataCodTipoQuadro[0]==="*") {
      return allJsonData;
    }

    let jsondata = {};
    getJsonDataCodTipoQuadro.forEach(
      item=>{
        if(item && allJsonData && allJsonData[item]){
          jsondata[item] = {...allJsonData[item]};
        }
      }
    );
    return jsondata;
  }

  _nextImports(output:any= null){
    this.imports$.next(
      output
    );
  }
}

