/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Quadro } from '../../../../../shared/models';
import { IFormioFormBuilderPanel } from '../../../../../shared/services/formio/utilities/formio.interfaces';
import { OggettoIstanza, Opera } from '../../../models';
import {
  IDatiTecniciOggettoIstanza,
  IListeDatiTecniciOggettiIstanze,
} from '../../../pages/presentazione-istanza/opere/utilities/opere.interfaces';

/**
 * Interfaccia che definisce l'insieme delle informazioni necessarie per la verifica dei dati obbligatori per i dati tecnici.
 */
export interface IDatiObbligatoriDTParams {
  datiTecnici: IListeDatiTecniciOggettiIstanze;
  tipoOpera: string;
  oggettiIstanza: OggettoIstanza[];
  opere: Opera[];
  quadro: Quadro;
}

/**
 * Interfaccia che definisce l'insieme delle informazioni necessarie per la verifica dei dati obbligatori per i dati tecnici, verificando una sezione FormIo.
 */
export interface IDatiObbligatoriDTSezioneFormIoParams {
  datiObbligatoriParams: IDatiObbligatoriDTParams;
  sezioneFormIo: IFormioFormBuilderPanel;
}

/**
 * Interfaccia che definisce l'insieme delle informazioni necessarie per la verifica dei dati obbligatori per i dati tecnici, verificando i dati tecnici specifici tipo opera.
 */
export interface IDatiObbligatoriDTSezioneFormIoDTSpecificiParams
  extends IDatiObbligatoriDTSezioneFormIoParams {
  datiTecniciPerTipoOpera: IDatiTecniciOggettoIstanza;
}
