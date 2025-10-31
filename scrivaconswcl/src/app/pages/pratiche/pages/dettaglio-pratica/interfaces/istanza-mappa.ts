/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
export interface IstanzaMappa {
  service: MacroServizioMappa[];
}

export interface MacroServizioMappa {
  label: string;
  level: ServizioMappa[];
}

export interface ServizioMappa {
  label: string;
  type: 'geojson' | 'wms';
  enable: boolean;
  geojson: [
    {
      type: string;
      coordinates: [];
    }
  ];
  params: {
    url: string;
    layer: string;
    format: string;
    idMapservice: string;
    idObj: string;
  };
}
