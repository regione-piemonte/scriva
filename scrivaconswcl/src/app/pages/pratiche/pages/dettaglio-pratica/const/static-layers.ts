/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import * as L from 'leaflet';
import { tileLayer } from 'leaflet';

export const crs = new (L as any).Proj.CRS(
  'EPSG:32632',
  '+proj=utm +zone=32 +ellps=WGS84 +datum=WGS84 +units=m +no_defs',
  new L.Transformation(1, -432000, -1, 6850000)
);

export const crs_32632 = new (L as any).Proj.CRS('EPSG:32632', '+proj=utm +zone=32 +ellps=WGS84 +datum=WGS84 +units=m +no_defs', {
  origin: [-10198294.6545, 15991090.6634],
  resolutions: [
      84328.16931992187,
      42164.084659960936,
      21082.042329980468,
      10541.021164990234,
      5270.510582495117,
      2635.2552912475585,
      1317.6276456237792,
      658.8138228118896,
      329.4069114059448,
      164.7034557029724,
      82.3517278514862,
      41.1758639257431,
      20.58793196287155,
      10.293965981435775,
      5.146982990717888,
      2.573491495358944,
      1.286745747679472,
      0.643372873839736
  ]
});

crs.scale = function (zoom) {
  return 1 / (234.375 / Math.pow(2, zoom));
};

const wmsUrl =
  'https://geomap.reteunitaria.piemonte.it/ws/gsareprot/rp-01/areeprotwms/wms_gsareprot_1';

export const STATIC_WMS_MAP = {
  label: 'Servizio Aree Protette e Siti Natura 2000',
  selectAllCheckbox: true,
  collapsed: true,
  children: [
    {
      label: 'Aree protette',
      layer: tileLayer.wms(wmsUrl, {
        layers: 'AreeProtette',
        format: 'image/png',
        transparent: true,
        crs
      })
    },
    {
      label: 'ZPS - Zone di Protezione Speciale',
      layer: tileLayer.wms(wmsUrl, {
        layers: 'ZPS',
        transparent: true,
        format: 'image/png',
        crs
      })
    },
    {
      label: 'SIC - Siti di importanza comunitaria',
      layer: tileLayer.wms(wmsUrl, {
        layers: 'SIC',
        format: 'image/png',
        transparent: true,
        crs
      })
    },
    {
      label: 'SIR - Siti di importanza regionale',
      layer: tileLayer.wms(wmsUrl, {
        layers: 'SIR',
        format: 'image/png',
        transparent: true,
        crs
      })
    }
  ]
};
