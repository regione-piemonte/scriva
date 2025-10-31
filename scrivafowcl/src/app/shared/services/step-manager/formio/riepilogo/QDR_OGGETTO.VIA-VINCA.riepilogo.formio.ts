/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**
 * Definisco un oggetto con le informazioni del formio per il debug.
 * @develop Usare "calculateValue" come proprietà per testare e debuggare i parametri delle funzioni di FormIo.
 */
export const QDR_OGGETTO_VIA_VINCA_RIEPILOGO_DEBUG = {
  "display": "form",
  "components": [
    {
      "label": "<h2><strong>ISTANZA PRESENTATA PER</strong></h2>",
      "customClass": "table-custom-class",
      "disableAddingRemovingRows": true,
      "tableView": false,
      "templates": {
        "header": "<div class=\"row\">\n {% (component.components || []).forEach(function(component) { %}\n {% if (component.type === 'textfield') { %}\n <div class=\"col-sm-3\"><strong>{{ component.label }}</strong></div>\n {% } %}\n {% if (component.type === 'checkbox' && component.key !== 'valuzatione_incidenza') { %}\n <div class=\"col-sm-3\"><strong>{{ component.label }}</strong></div>\n {% } %}\n {% if (component.type === 'checkbox' && component.key === 'valuzatione_incidenza' && submission.data.valutazioneIncidenza === 'si') { %}\n <div class=\"col-sm-3\"><strong>{{ component.label }}</strong></div>\n {% } %}\n {% }) %}\n</div>",
        "row": "<div class=\"row\">\n {% instance.eachComponent(function(component) { %}\n {% if (component.type === 'textfield') { %}\n {% if (component.label === 'Comuni') { %}\n {% if (submission.data.oggettiIstanza[rowIndex].ubicazione_oggetto.length > 1) { %}\n <div class=\"col-sm-3\" title=\"{{ submission.data.oggettiIstanza[rowIndex].ubicazione_oggetto.map(o => o.comune).map(c => c.denom_comune).join(', ') }}\">\n {{ component.getView(component.dataValue) }}, ...\n </div>\n {% } else { %}\n <div class=\"col-sm-3\">\n {{ component.getView(component.dataValue) }}\n </div>\n {% } %}\n {% } else { %}\n <div class=\"col-sm-3\">\n {{ component.getView(component.dataValue) }}\n </div>\n {% } %}\n {% } %}\n {% if (component.type === 'checkbox') { %}\n {% if (component.key === 'georeferenziazione') { %}\n {% if (submission.data.indGeoMode === 'N') { %}\n <div class=\"col-sm-3\">non richiesta</div>\n {% } else if (submission.data.indGeoMode === 'O') { %}\n {% if (submission.data.oggettiIstanza[rowIndex].ind_geo_stato === 'G') { %}\n <div class=\"col-sm-3\">\n <i class=\"fa fa-check-circle-o\" style=\"color: green\" aria-hidden=\"true\"></i>\n </div>\n {% } else { %}\n <div class=\"col-sm-3\">non eseguita</div>\n {% } %}\n {% } else if (submission.data.indGeoMode === 'M') { %}\n {% if (submission.data.oggettiIstanza[rowIndex].ind_geo_stato === 'G') { %}\n <div class=\"col-sm-3\">\n <i class=\"fa fa-check-circle-o\" style=\"color: green\" aria-hidden=\"true\"></i>\n </div>\n {% } else { %}\n <div class=\"col-sm-3\">da effettuare</div>\n {% } %}\n {% } else if (submission.data.indGeoMode === 'A') { %}\n {% if (submission.data.oggettiIstanza[rowIndex].ind_geo_stato === 'G') { %}\n <div class=\"col-sm-3\">\n <i class=\"fa fa-check-circle-o\" style=\"color: green\" aria-hidden=\"true\"></i>\n </div>\n {% } else { %}\n <div class=\"col-sm-3\">non eseguita</div>\n {% } %}\n {% } else if (submission.data.indGeoMode === 'P') { %}\n {% if (submission.data.oggettiIstanza[rowIndex].ind_geo_stato === 'G') { %}\n <div class=\"col-sm-3\">\n <i class=\"fa fa-check-circle-o\" style=\"color: green\" aria-hidden=\"true\"></i>\n </div>\n {% } else { %}\n <div class=\"col-sm-3\">da effettuare</div>\n {% } %}\n {% } %}\n {% } %}\n {% if (component.key === 'valuzatione_incidenza' && submission.data.valutazioneIncidenza === 'si') { %}\n  <div class=\"col-sm-3\">\n <i class=\"fa fa-check-circle-o\" style=\"color: green\" aria-hidden=\"true\"></i>\n </div>\n {% } %}\n {% } %}\n {% }, rowIndex) %}\n</div>"
      },
      "validateWhenHidden": false,
      "rowDrafts": false,
      "key": "oggettiIstanza",
      "type": "editgrid",
      "displayAsTable": false,
      "input": true,
      "components": [
        {
          "key": "den_oggetto",
          "type": "textfield",
          "input": true,
          "label": "Denominazione",
          "tableView": true
        },
        {
          "key": "ubicazione_oggetto[0].comune.denom_comune",
          "type": "textfield",
          "input": true,
          "label": "Comuni",
          "tableView": true
        },
        {
          "key": "georeferenziazione",
          "type": "checkbox",
          "input": true,
          "label": "Georeferenziazione",
          "hideLabel": true,
          "tableView": true,
          "defaultValue": false
        },
        {
          "key": "valuzatione_incidenza",
          "type": "checkbox",
          "input": true,
          "label": "Valutazione di Incidenza",
          "hideLabel": true,
          "tableView": true,
          "defaultValue": false
        }
      ]
    },
    {
      "key": "errorMessage",
      "type": "htmlelement",
      "input": false,
      "label": "HTML",
      "content": "<i class=\"icon icon-big fa fa-times-circle-o fa-lg\" style=\"color: #f41622;\" aria-hidden=\"true\"></i> <b>Attenzione!</b> La georeferenziazione non è stata ancora eseguita.",
      "tableView": false,
      "refreshOnChange": false,
      "customConditional": "show = ( (data.indGeoMode === 'M' || data.indGeoMode === 'P') && data.oggettiIstanza.some(oggetto => oggetto.ind_geo_stato !== 'G') );"
    },
    {
      "key": "warningMessage",
      "type": "htmlelement",
      "input": false,
      "label": "HTML",
      "content": "<i class=\"icon icon-big fa fa-exclamation-circle fa-lg\" style=\"color: #f8e60d;\" aria-hidden=\"true\"></i> <b>Attenzione!</b> L'assenza della georeferenziazione non permette di usufruire di alcune compilazioni e controlli automatici.",
      "tableView": false,
      "refreshOnChange": false,
      "customConditional": "show = ( data.indGeoMode === 'A' && data.oggettiIstanza.some(oggetto => oggetto.ind_geo_stato !== 'G') );"
    }
  ]
}