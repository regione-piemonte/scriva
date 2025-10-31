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
export const QDR_ORIENTAMENTO_VIA_DEBUG = {
  "jsonForm": {
    "display": "form",
    "components": [
      {
        "key": "disableFlag",
        "type": "hidden",
        "input": true,
        "label": "disableFlag",
        "tableView": true,
        "defaultValue": "ENABLED"
      },
      {
        "key": "dichiaro_label",
        "type": "htmlelement",
        "input": false,
        "label": "diechiaro_label",
        "content": "<b>Dichiaro:</b>",
        "tableView": false,
        "refreshOnChange": false
      },
      {
        "key": "dichiarazioni[0].cod_dichiarazione",
        "type": "hidden",
        "input": true,
        "label": "dichiarazioni[0].cod_dichiarazione",
        "tableView": false,
        "defaultValue": "dchr_gdpr"
      },
      {
        "key": "dichiarazioni[0].des_dichiarazione",
        "type": "hidden",
        "input": true,
        "label": "dichiarazioni[0].des_dichiarazione",
        "tableView": false,
        "defaultValue": "di aver preso visione dell'Informativa sul trattamento dei dati personali, aggiornata all’art. 13 del GDPR 2016/679, consultabile anche sul sito web istituzionale della Regione Piemonte;"
      },
      {
        "key": "dichiarazioni[0].obbligatorio",
        "type": "hidden",
        "input": true,
        "label": "dichiarazioni[0].obbligatorio",
        "tableView": false,
        "defaultValue": true
      },
      {
        "label": "dich-orientamento",
        "tableView": false,
        "validateWhenHidden": false,
        "key": "dich-orientamento",
        "type": "container",
        "input": false,
        "components": [
          {
            "key": "dichiarazioni[0].check",
            "type": "checkbox",
            "input": true,
            "label": "Checkbox",
            "logic": [
              {
                "name": "enableLogic",
                "actions": [
                  {
                    "name": "enableAction",
                    "type": "property",
                    "state": "true",
                    "property": {
                      "type": "boolean",
                      "label": "Disabled",
                      "value": "disabled"
                    }
                  }
                ],
                "trigger": {
                  "type": "simple",
                  "simple": {
                    "eq": "DISABLED",
                    "show": true,
                    "when": "disableFlag"
                  }
                }
              }
            ],
            "validate": {
              "required": true,
              "customMessage": "Campo obbligatorio"
            },
            "hideLabel": true,
            "tableView": false,
            "defaultValue": false
          },
          {
            "label": "DICHIARAZIONI PRIVACY - REGIONE PIEMONTE",
            "attrs": [
              {
                "attr": "",
                "value": ""
              }
            ],
            "content": "<!-- \n  @author Ismaele Bottelli\n  @date 09/12/2024\n  @jira SCRIVA-1568\n  @notes E' stata richiesta la gestione delle informative della privacy a seconda che l'autorità competente sia REGIONE PIEMONTE o un'altra autorità.\n         Il collega che ha fatto questo FormIo ha fatto delle forzature e devo lavorarci attorno. E' già stata comunicata la necessità di revisione e modifica per il quadro orientamento.\n         Per il momento l'unica alternativa trovata è stata quella di sovrascrivere lato Angular questo specifico contenuto. Bisognerà gestire meglio la casistica.\n-->\n<!-- REGIONE PIEMONTE -->\n<span>di aver preso visione dell'<a href=\"{{form.imports.GDPRDOCPATH}}{{form.imports.codTipoAdempimento}}_{{form.imports.codAutCompetente}}_GDPR.pdf\" target=\"_blank\">Informativa sul trattamento dei dati personali</a>, aggiornata all'art. 13 del GDPR 2016/679, consultabile anche sul <a href=\"{{form.imports.acWeb}}\" target=\"_blank\">sito web istituzionale della Regione Piemonte</a>;</span>\n<!-- ALTRE AUTORITA' COMPETENTI RISPETTO REGIONE PIEMONTE -->\n<span>di aver preso visione dell'<a href=\"{{form.imports.GDPRDOCPATH}}{{form.imports.codTipoAdempimento}}_{{form.imports.codAutCompetente}}_GDPR_ESTESA.pdf\" target=\"_blank\">Informativa sul trattamento dei dati personali</a>, aggiornata all'art. 13 del GDPR 2016/679, consultabile anche sul <a href=\"{{form.imports.acWeb}}\" target=\"_blank\">sito web istituzionale della Regione Piemonte</a> e<br> di aver preso visione dell'<a href=\"{{form.imports.GDPRDOCPATH}}{{form.imports.autoritaCompetenteSpecifica?.codTipoAdempimento}}_{{form.imports.autoritaCompetenteSpecifica?.codAutCompetente}}_GDPR.pdf\" target=\"_blank\">Informativa sul trattamento dei dati personali</a>, aggiornata all'art. 13 del GDPR 2016/679, consultabile anche sul <a href=\"{{form.imports.autoritaCompetenteSpecifica.acWeb}}\" target=\"_blank\">sito web istituzionale della {{ form.imports.autoritaCompetenteSpecifica?.autoritaCompetente?.des_competenza_territorio }}</a></span>",
            "refreshOnChange": false,
            "customClass": "gdpr",
            "key": "dichiarazioni[0].label",
            "type": "htmlelement",
            "input": false,
            "tableView": false
          }
        ]
      }
    ]
  }
};
