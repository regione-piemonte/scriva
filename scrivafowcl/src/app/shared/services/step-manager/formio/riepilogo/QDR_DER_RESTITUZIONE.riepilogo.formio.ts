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
export const QDR_DER_RESTITUZIONE_RIEPILOGO_DEBUG = {
  "display": "form",
  "components": [{
          "key": "key_restituzioni",
          "type": "htmlelement",
          "attrs": [{
                  "attr": "",
                  "value": ""
              }
          ],
          "input": false,
          "label": "HTML",
          "content": "<h2><strong>RESTITUZIONI</strong></h2>",
          "tableView": false,
          "refreshOnChange": false
      }, {
          "key": "html_restituzioni",
          "type": "htmlelement",
          "attrs": [{
                  "attr": "",
                  "value": ""
              }
          ],
          "input": false,
          "label": "html_restituzioni",
          "content": "Sono state inserite {{data.JS_DATI_RESTITUZIONE?.length}} restituzioni.",
          "tableView": false,
          "refreshOnChange": false,
          "customConditional": "show = data.JS_DATI_RESTITUZIONE?.length > 0;"
      }, {
          "key": "html_restituzioni_non_inserite",
          "type": "htmlelement",
          "attrs": [{
                  "attr": "",
                  "value": ""
              }
          ],
          "input": false,
          "label": "html_restituzioni_non_inserite",
          "content": "Non sono state inserite restituzioni.",
          "tableView": false,
          "refreshOnChange": false,
          "customConditional": "show = data.JS_DATI_RESTITUZIONE == undefined || data.JS_DATI_RESTITUZIONE?.length == 0;"
      }
  ]
}
