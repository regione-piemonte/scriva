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
export const QDR_DER_USIULO_RIEPILOGO_DEBUG = {
  "display": "form",
  "components": [{
          "key": "title",
          "type": "htmlelement",
          "input": false,
          "label": "HTML",
          "content": "<h2><strong>USI IN STABILIMENTO</strong></h2>",
          "tableView": false,
          "refreshOnChange": false
      }, {
          "key": "html_unita_locale_operativa",
          "type": "htmlelement",
          "input": false,
          "label": "html_unita_locale_operativa",
          "content": "Sono state inseriti {{data.JS_DATI_UNITA_LOCALE_OPERATIVA?.length}} stabilimenti con la descrizione degli usi dell'acqua.",
          "tableView": false,
          "refreshOnChange": false,
          "customConditional": "show = data.JS_DATI_UNITA_LOCALE_OPERATIVA?.length > 0;"
      }, {
          "key": "html_unita_locale_operativa_non_inserite",
          "type": "htmlelement",
          "attrs": [{
                  "attr": "",
                  "value": ""
              }
          ],
          "input": false,
          "label": "html_unita_locale_operativa_non_inserite",
          "content": "Non sono state inseriti stabilimenti con la descrizione degli usi dell'acqua.",
          "tableView": false,
          "refreshOnChange": false,
          "customConditional": "show = data.JS_DATI_UNITA_LOCALE_OPERATIVA == undefined || data.JS_DATI_UNITA_LOCALE_OPERATIVA?.length == 0;"
      }
  ]
}

