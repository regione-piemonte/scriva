/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BaseComponent, ValueMapperService } from '@app/core';
import { I18nService } from '@app/core/translate/public-api';
import { LoadingService } from '@app/theme/layouts/loading.services';
import { GeoJsonObject } from 'geojson';
import * as L from 'leaflet';
import { latLng, Map, MapOptions, tileLayer, ZoomAnimEvent } from 'leaflet';
import 'leaflet.control.layers.tree';
import 'proj4leaflet';
import { toWgs84 } from 'reproject';
import { STATIC_WMS_MAP } from '../../const/static-layers';
import { crs_32632 } from '../../const/static-layers';
import {
  MacroServizioMappa,
  ServizioMappa
} from '../../interfaces/istanza-mappa';

@Component({
  selector: 'app-mappa-pratica',
  templateUrl: './mappa-pratica.component.html',
  styleUrls: ['./mappa-pratica.component.scss']
})
export class MappaPraticaComponent extends BaseComponent implements OnInit {
  breadcrumbs = [];

  map: Map;
  zoom: number;
  layers: L.Layer[] = [];

  /** string con l'url del layer per il caricamento della mappa da usare per la configurazione. Versione: Open street maps. */
  urlLayerOSM: string = `https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png`;
  /** string con l'url del layer per il caricamento della mappa da usare per la configurazione. Versione: Geomap Reteunitaria Piemonte. */
  urlLayerGMR: string = `https://geomap.reteunitaria.piemonte.it/mapproxy/wmts/regp_sfondo_bdtre_wmts/grid_32632_nw/{z}/{x}/{y}.png`;

  /** string con l'identificativo per l'attribution per la mappa. Versione: Open street maps. */
  attributionOSM: string = `&copy; <a href="http://www.openstreetmap.org/copyright" target="_blank">OpenStreetMap</a> contributors`;
  /** string con l'identificativo per l'attribution per la mappa. Versione: Geomap Reteunitaria Piemonte. */
  attributionGMR: string = `Csi Piemonte`;

  /**
   * SCRIVA-1255
   * Richiesto cambio layer da OMS a BDTRe. Riferimento tileLayer preso da file js condiviso sulla jira
   */
  options: MapOptions = {
    // layers: [tileLayer(this.urlLayerOSM, { attribution: this.attributionOSM })],
    layers: [tileLayer(this.urlLayerGMR, { attribution: this.attributionGMR })],
    zoom: 12,
    center: latLng(45.0677551, 7.6824892),
    crs: crs_32632,
  };

  overlayesTree: any;

  constructor(
    private route: ActivatedRoute,
    private loadingService: LoadingService,
    private mapper: ValueMapperService,
    private i18n: I18nService
  ) {
    super();
    this.loadingService.hide();
  }

  ngOnInit() {
    this._initBreadcrumbs();
  }

  onMapReady(map: Map) {
    this.map = map;
    this.getMapDetail();
  }

  onMapZoomEnd(e: ZoomAnimEvent): void {
    this.zoom = e.target.getZoom();
  }

  getMapDetail() {
    const bounds = new L.LatLngBounds(
      L.latLng(45.0677551, 7.6824892),
      L.latLng(45.0677551, 7.6824892)
    );
    this.route.data.subscribe((data: any) => {
      const response = data.mappa;
      this.overlayesTree = {
        label: this.i18n.translate('PRACTICE.DETAIL.MAP.OVERLAY_TREE.TITLE'),
        children: []
      };
      response.service.forEach((level1: MacroServizioMappa) => {
        const levels2 = [];
        level1.level.forEach((level2: ServizioMappa) => {
          switch (level2.type) {
            case 'geojson':
              const geoLayer = L.geoJSON();
              level2.geojson.forEach((gj) => {
                gj = toWgs84(gj, 'EPSG:32632');
                geoLayer.addData(gj as GeoJsonObject);
              });
              levels2.push({
                label: level2.label,
                layer: geoLayer
              });
              if (level2.enable) {
                this.layers.push(geoLayer);
                bounds.extend(geoLayer.getBounds());
              }
              break;
            case 'wms':
              const wmsLayer = L.tileLayer.wms(level2.params.url, {
                layers: level2.params.layer,
                format: level2.params.format
              });
              levels2.push({
                label: level2.label,
                layer: wmsLayer
              });
              if (level2.enable) {
                this.layers.push(wmsLayer);
              }
              break;
          }
        });
        this.overlayesTree.children.push({
          label: level1.label,
          selectAllCheckbox: true,
          children: levels2,
          collapsed: true
        });
      });
      if (bounds.isValid()) {
        // Valid, fit bounds
        this.map.fitBounds(bounds);
      }
      this.overlayesTree.children.push(STATIC_WMS_MAP);
      L.control.layers.tree(null, this.overlayesTree).addTo(this.map);
    });
  }

  private _initBreadcrumbs() {
    const pratica = this.route.snapshot.data.pratica;
    const snapshot = this.route.snapshot.parent.params;
    const ambito = this.mapper.getAmbito(snapshot['id_ambito']);

    const fromSearch = this.route.snapshot.queryParams['fromSearch'];

    if (fromSearch) {
      this.breadcrumbs = [
        {
          label: 'PRACTICE.SEARCH.TITLE',
          href: '/procedimenti/ricerca'
        },
        {
          label: `${pratica.cod_pratica.replace('/', ' - ')}`,
          href: `/procedimenti/${ambito.cod_ambito}/${snapshot['id_tipo_adempimento']}/${pratica.id_istanza}`
        },
        {
          label: 'PRACTICE.DETAIL.MAP.TITLE',
          href: null
        }
      ];
      return;
    }

    this.breadcrumbs = [
      {
        label: `${ambito.des_ambito}`,
        href: `/ambito/${ambito.cod_ambito}`
      },
      {
        label: `${snapshot['id_tipo_adempimento']}`,
        href: `/procedimenti/${ambito.cod_ambito}/${snapshot['id_tipo_adempimento']}`
      },
      {
        label: `${pratica.cod_pratica.replace('/', ' - ')}`,
        href: `/procedimenti/${ambito.cod_ambito}/${snapshot['id_tipo_adempimento']}/${pratica.id_istanza}`
      },
      {
        label: 'PRACTICE.DETAIL.MAP.TITLE',
        href: null
      }
    ];
  }
}
