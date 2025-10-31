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
export const QDR_DER_ALTRIUSI_RIEPILOGO_DEBUG = {
  "display": "form",
  "components": [
    {
      "key": "title",
      "type": "htmlelement",
      "input": false,
      "label": "HTML",
      "content": "<h2><strong>ALTRI USI</strong></h2>",
      "tableView": false,
      "refreshOnChange": false
    },
    {
      "key": "html_altri_usi",
      "type": "htmlelement",
      "input": false,
      "label": "html_altri_usi",
      "content": "Sono stati inseriti i seguenti usi dell'acqua:",
      "tableView": false,
      "refreshOnChange": false
    },
    {
      "label": "html_nessun_uso",
      "attrs": [
        {
          "attr": "",
          "value": ""
        }
      ],
      "content": "Nessun uso definito",
      "refreshOnChange": false,
      "key": "html_nessun_uso",
      "customConditional": "const flgUsoAgricolo = data.USO_AGRICOLO.flgUsoAgricolo;\r\nconst flgUsoAgricoloNO = flgUsoAgricolo == undefined || flgUsoAgricolo == 'no';\r\n\r\nconst flgUsoCivile = data.USO_CIVILE.flgUsoCivile;\r\nconst flgUsoCivileNO = flgUsoCivile == undefined || flgUsoCivile == 'no';\r\n\r\nconst flgUsoDomestico = data.USO_DOMESTICO.flgUsoDomestico;\r\nconst flgUsoDomesticoNO = flgUsoDomestico == undefined || flgUsoDomestico == 'no';\r\n\r\nconst flgUsoPotabile = data.USO_POTABILE.flgUsoPotabile;\r\nconst flgUsoPotabileNO = flgUsoPotabile == undefined || flgUsoPotabile == 'no';\r\n\r\nshow = flgUsoAgricoloNO && flgUsoCivileNO && flgUsoDomesticoNO && flgUsoPotabileNO;",
      "type": "htmlelement",
      "input": false,
      "tableView": false
    },
    {
      "label": "html_uso_agricolo",
      "attrs": [
        {
          "attr": "",
          "value": ""
        }
      ],
      "content": "Uso agricolo",
      "refreshOnChange": false,
      "key": "html_altri_usi1",
      "customConditional": "const flgUsoAgricolo = data.USO_AGRICOLO.flgUsoAgricolo;\nconst flgUsoAgricoloSI = flgUsoAgricolo == 'si';\nshow = flgUsoAgricoloSI;",
      "type": "htmlelement",
      "input": false,
      "tableView": false
    },
    {
      "label": "html_uso_civile",
      "attrs": [
        {
          "attr": "",
          "value": ""
        }
      ],
      "content": "Uso civile",
      "refreshOnChange": false,
      "key": "html_uso_civile",
      "customConditional": "const flgUsoCivile = data.USO_CIVILE.flgUsoCivile;\nconst flgUsoCivileSI = flgUsoCivile == 'si';\nshow = flgUsoCivileSI;",
      "type": "htmlelement",
      "input": false,
      "tableView": false
    },
    {
      "label": "html_uso_domestico",
      "attrs": [
        {
          "attr": "",
          "value": ""
        }
      ],
      "content": "Uso domestico",
      "refreshOnChange": false,
      "key": "html_uso_domestico",
      "customConditional": "const flgUsoDomestico = data.USO_DOMESTICO.flgUsoDomestico;\nconst flgUsoDomesticoSI = flgUsoDomestico == 'si';\nshow = flgUsoDomesticoSI;",
      "type": "htmlelement",
      "input": false,
      "tableView": false
    },
    {
      "label": "html_uso_potabile",
      "attrs": [
        {
          "attr": "",
          "value": ""
        }
      ],
      "content": "Uso potabile",
      "refreshOnChange": false,
      "key": "html_uso_potabile",
      "customConditional": "const flgUsoPotabile = data.USO_POTABILE.flgUsoPotabile;\nconst flgUsoPotabileSI = flgUsoPotabile == 'si';\nshow = flgUsoPotabileSI;",
      "type": "htmlelement",
      "input": false,
      "tableView": false
    }
  ]
}