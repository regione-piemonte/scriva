import { CdkStep } from '@angular/cdk/stepper';
/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */

import { Observable } from 'rxjs';
import { IPositionStepClicked } from '../../stepper/stepper.component';

export interface ICambiaStep {
  // oggetto con indefinite proprietà di tipo any
  [key: string]: any;
  idQuadro: number;
  stepClickedPosition?: IPositionStepClicked;
  stepArrayLength?: number;
  clickedIndex?: number;
  currentIndex?: number;
}

export interface RequestDataQuadro {
  id_istanza: number;
  id_template_quadro: number;
  json_data_quadro: any;
}

export interface RequestSaveBodyQuadro {
  requestDataQuadro: RequestDataQuadro;
  requestDataRiepilogo: RequestDataQuadro;
}

export interface IConfigsSaveQuadro {
  idIstanza?: number;
  idTemplateQuadro?: number;
  datiRiepilogo?: any;
  datiQuadro?: any;
}

export interface IPrepareDatiRiepilogo {
  datiRiepilogo?: any;
  datiQuadro?: any;
  codQuadro?: string;
  codTipoQuadro?: string;
}

export interface IConfigsSaveDataQuadro {
  [key: string]: any;
  showSpinner?: boolean;
  isPutDatiQuadro?: boolean;
  isPutDatiRiepilogo?: boolean;
  datiRiepilogo?: any;
  datiQuadro?: any;
}

export interface ISalvaJsonDataReq {
  datiQuadro: Observable<any>;
  datiRiepilogo: Observable<any>;
}

export interface ISalvaJsonDataRes {
  datiQuadro: any;
  datiRiepilogo: any;
}
