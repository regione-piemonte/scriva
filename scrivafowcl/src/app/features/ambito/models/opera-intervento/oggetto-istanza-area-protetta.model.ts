/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { CompetenzaTerritorio } from 'src/app/shared/models';
import { OggettoIstanza } from './oggetto-istanza.model';

export interface OggettoIstanzaAreaProtetta {
  id_oggetto_area_protetta?: number;
  oggetto_istanza?: OggettoIstanza;
  competenza_territorio: CompetenzaTerritorio;
  tipo_area_protetta: TipoAreaProtetta;
  cod_amministrativo: string;
  cod_gestore_fonte: string;
  des_area_protetta: string;
  flg_ricade: boolean;
  to_be_validated?: boolean; // not in DTO
  flg_fuori_geometrie?: boolean; // not in DTO
  gestAttoreIn?: string;
  gestAttoreUpd?: string;
  gestUID: string;
  id_competenza_territorio?: number;
  id_oggetto_istanza?: number;
  id_tipo_area_protetta?: number;
}
interface TipoAreaProtetta {
  id_tipo_area_protetta: number;
  cod_tipo_area_protetta: string;
  des_tipo_area_protetta: string;
}
