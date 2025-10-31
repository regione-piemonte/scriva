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
export const QDR_DER_INFRACC_RIEPILOGO_DEBUG = {
  "display": "form",
  "components": [{
          "key": "title",
          "type": "htmlelement",
          "input": false,
          "label": "HTML",
          "content": "<h2><strong>INFRASTRUTTURE ACCESSORIE</strong></h2>",
          "tableView": false,
          "refreshOnChange": false
      }, {
          "key": "html_stazione_di_pompaggio",
          "type": "htmlelement",
          "input": false,
          "label": "html_stazione_di_pompaggio",
          "content": "Sono state inserite {{data.JS_DATI_STAZIONE_DI_POMPAGGIO?.length}} opere di tipo Stazione di pompaggio.",
          "tableView": false,
          "refreshOnChange": false,
          "customConditional": "show = data.JS_DATI_STAZIONE_DI_POMPAGGIO?.length > 0;"
      }, {
          "key": "html_stazione_di_pompaggio_non_inserite",
          "type": "htmlelement",
          "input": false,
          "label": "html_stazione_di_pompaggio_non_inserite",
          "content": "Non sono state inserite opere di tipo Stazione di pompaggio.",
          "tableView": false,
          "refreshOnChange": false,
          "customConditional": "show = data.JS_DATI_STAZIONE_DI_POMPAGGIO == undefined || data.JS_DATI_STAZIONE_DI_POMPAGGIO?.length == 0;"
      }, {
          "key": "html_serbatorio_di_accumulo",
          "type": "htmlelement",
          "input": false,
          "label": "html_serbatorio_di_accumulo",
          "content": "Sono state inserite {{data.JS_DATI_SERBATOIO_DI_ACCUMULO?.length}} opere di tipo Serbatoio di accumulo.",
          "tableView": false,
          "refreshOnChange": false,
          "customConditional": "show = data.JS_DATI_SERBATOIO_DI_ACCUMULO?.length > 0;"
      }, {
          "key": "html_serbatorio_di_accumulo_non_inserite",
          "type": "htmlelement",
          "input": false,
          "label": "html_serbatorio_di_accumulo_non_inserite",
          "content": "Non sono state inserite opere di tipo Serbatoio di accumulo.",
          "tableView": false,
          "refreshOnChange": false,
          "customConditional": "show = data.JS_DATI_SERBATOIO_DI_ACCUMULO == undefined || data.JS_DATI_SERBATOIO_DI_ACCUMULO?.length == 0;"
      }, {
          "key": "html_misuratore_di_portata",
          "type": "htmlelement",
          "input": false,
          "label": "html_misuratore_di_portata",
          "content": "Sono state inserite {{data.JS_DATI_MISURATORE_DI_PORTATA?.length}} opere di tipo Misuratore.",
          "tableView": false,
          "refreshOnChange": false,
          "customConditional": "show = data.JS_DATI_MISURATORE_DI_PORTATA?.length > 0;"
      }, {
          "key": "html_misuratore_di_portata_non_inserite",
          "type": "htmlelement",
          "input": false,
          "label": "html_misuratore_di_portata_non_inserite",
          "content": "Non sono state inserite opere di tipo Misuratore.",
          "tableView": false,
          "refreshOnChange": false,
          "customConditional": "show = data.JS_DATI_MISURATORE_DI_PORTATA == undefined || data.JS_DATI_MISURATORE_DI_PORTATA?.length == 0;"
      }
  ]
}
