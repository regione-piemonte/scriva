/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export interface LOCCSILocation {
  id: number;
  name: string;
  description: string;
  catalogs: string[];
  featureCollection: {
    type: string;
    features?: LOCCSIFeature[];
    bbox?: number[];
    crs?: {
      type: string;
      properties: {
        name: string;
      };
    };
  };
}

interface LOCCSIFeature {
  id: string;
  type: string;
  geometry: {
    type: string;
    coordinates: number[];
  };
  properties: {
    localita?: string;
    codice_istat?: string;
    cap?: string;
    comune?: string;
    loccsi_label?: string;
    civico_num?: string;
    nome_via?: string;
    tipo_via?: string;
  };
}
