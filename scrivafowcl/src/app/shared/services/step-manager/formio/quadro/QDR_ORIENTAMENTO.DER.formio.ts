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
export const QDR_ORIENTAMENTO_DER_DEBUG = {
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
        "key": "columnsTipoConcessione",
        "type": "columns",
        "input": false,
        "label": "ColumnsTipoConcessione",
        "columns": [
          {
            "pull": 0,
            "push": 0,
            "size": "md",
            "width": 10,
            "offset": 0,
            "components": [
              {
                "key": "QDR_DER_DATIGEN.tipo_concessione",
                "type": "radio",
                "input": true,
                "label": "<strong>Indica il tipo di concessione:</strong>",
                "inline": false,
                "values": [
                  {
                    "label": "Nuova concessione di derivazione",
                    "value": "ordinaria",
                    "shortcut": ""
                  },
                  {
                    "label": "Nuova concessione in via di sanatoria",
                    "value": "sanatoria",
                    "shortcut": ""
                  }
                ],
                "validate": {
                  "required": true
                },
                "tableView": false,
                "attributes": {
                  "style": "font-weight: bold;"
                },
                "validateWhenHidden": false,
                "optionsLabelPosition": "right"
              }
            ],
            "currentWidth": 10
          },
          {
            "pull": 0,
            "push": 0,
            "size": "md",
            "width": 2,
            "offset": 0,
            "components": [],
            "currentWidth": 2
          }
        ],
        "tableView": false
      },
      {
        "key": "columnsVerificaImpattoAmbientale",
        "type": "columns",
        "input": false,
        "label": "ColumnsVerificaImpattoAmbientale",
        "columns": [
          {
            "pull": 0,
            "push": 0,
            "size": "md",
            "width": 10,
            "offset": 0,
            "components": [
              {
                "key": "verifica_impatto_ambientale",
                "type": "radio",
                "input": true,
                "label": "<strong>E’ necessario presentare domanda di avvio alla verifica di impatto ambientale?</strong>",
                "inline": true,
                "values": [
                  {
                    "label": "Si",
                    "value": "si",
                    "shortcut": ""
                  },
                  {
                    "label": "No",
                    "value": "no",
                    "shortcut": ""
                  }
                ],
                "validate": {
                  "required": true
                },
                "tableView": false,
                "validateWhenHidden": false,
                "optionsLabelPosition": "right"
              }
            ],
            "currentWidth": 10
          },
          {
            "pull": 0,
            "push": 0,
            "size": "md",
            "width": 2,
            "offset": 0,
            "components": [],
            "currentWidth": 2
          }
        ],
        "tableView": false
      },
      {
        "key": "JS_FIELD_SET.verifica_impatto_ambientale",
        "type": "fieldset",
        "input": false,
        "label": "Field Set",
        "tableView": false,
        "components": [
          {
            "key": "columnsValutazioneImpattoAmbientale",
            "type": "columns",
            "input": false,
            "label": "ColumnsValutazioneImpattoAmbientale",
            "columns": [
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 10,
                "offset": 0,
                "components": [
                  {
                    "key": "valutazione_impatto_ambientale",
                    "type": "radio",
                    "input": true,
                    "label": "<strong>E’ necessario presentare domanda di avvio alla valutazione di impatto ambientale?</strong>",
                    "inline": true,
                    "values": [
                      {
                        "label": "Si",
                        "value": "si",
                        "shortcut": ""
                      },
                      {
                        "label": "No",
                        "value": "no",
                        "shortcut": ""
                      }
                    ],
                    "validate": {
                      "required": true
                    },
                    "tableView": false,
                    "validateWhenHidden": false,
                    "optionsLabelPosition": "right"
                  }
                ],
                "currentWidth": 10
              },
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 2,
                "offset": 0,
                "components": [],
                "currentWidth": 2
              }
            ],
            "tableView": false
          },
          {
            "key": "JS_FIELD_SET.valutazione_impatto_ambientale",
            "type": "fieldset",
            "input": false,
            "label": "Field Set",
            "tableView": false,
            "components": [
              {
                "key": "questionWell",
                "type": "well",
                "input": false,
                "label": "well-label",
                "tableView": false,
                "components": [
                  {
                    "key": "QDR_ALLEGATO.procedura_semplificata",
                    "type": "radio",
                    "input": true,
                    "label": "<b>Il procedimento segue una procedura semplificata?</b>",
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
                    "inline": true,
                    "values": [
                      {
                        "label": "Si",
                        "value": "true",
                        "shortcut": ""
                      },
                      {
                        "label": "No",
                        "value": "false",
                        "shortcut": ""
                      }
                    ],
                    "validate": {
                      "required": true,
                      "customMessage": "Rispondi alla domanda per continuare"
                    },
                    "tableView": false,
                    "persistent": false,
                    "validateWhenHidden": false,
                    "hideOnChildrenHidden": false,
                    "optionsLabelPosition": "right"
                  },
                  {
                    "key": "opzione_domanda_1",
                    "type": "button",
                    "event": "helpBtnClick",
                    "input": true,
                    "label": "<i class=\"fa fa-question-circle fa-lg\" aria-hidden=\"true\"></i>",
                    "action": "event",
                    "tableView": false,
                    "customClass": "help-btn",
                    "showValidations": false,
                    "hideOnChildrenHidden": false
                  }
                ],
                "customClass": "formio-well"
              },
              {
                "key": "JS_FIELD_SET.procedura_semplificata",
                "type": "fieldset",
                "input": false,
                "label": "Field Set",
                "tableView": false,
                "components": [
                  {
                    "key": "columnsTipologiaProceduraSemplificata",
                    "type": "columns",
                    "input": false,
                    "label": "ColumnsTipologiaProceduraSemplificata",
                    "columns": [
                      {
                        "pull": 0,
                        "push": 0,
                        "size": "md",
                        "width": 10,
                        "offset": 0,
                        "components": [
                          {
                            "label": "Indicare la tipologia di procedura semplificata",
                            "optionsLabelPosition": "right",
                            "inline": false,
                            "tableView": false,
                            "values": [
                              {
                                "label": "Procedura semplificata con opere fisse in alveo e sulle sponde",
                                "value": "true",
                                "shortcut": ""
                              },
                              {
                                "label": "Procedura semplificata senza opere fisse in alveo e sulle sponde",
                                "value": "false",
                                "shortcut": ""
                              }
                            ],
                            "persistent": false,
                            "validate": {
                              "required": true,
                              "customMessage": "Rispondi alla domanda per continuare"
                            },
                            "validateWhenHidden": false,
                            "key": "QDR_ALLEGATO.flg_opere_fisse",
                            "conditional": {
                              "show": true,
                              "when": "QDR_ALLEGATO.procedura_semplificata",
                              "eq": "true"
                            },
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
                            "type": "radio",
                            "input": true,
                            "hideOnChildrenHidden": false
                          }
                        ],
                        "currentWidth": 10
                      },
                      {
                        "pull": 0,
                        "push": 0,
                        "size": "md",
                        "width": 2,
                        "offset": 0,
                        "components": [],
                        "currentWidth": 2
                      }
                    ],
                    "tableView": false
                  },
                  {
                    "key": "QDR_ALLEGATO.uso_esclusivo_domestico",
                    "type": "checkbox",
                    "input": true,
                    "label": "Ad uso esclusivo domestico",
                    "tableView": false,
                    "conditional": {
                      "eq": "false",
                      "show": true,
                      "when": "QDR_ALLEGATO.flg_opere_fisse"
                    },
                    "customClass": "formio-fieldset-check-input",
                    "defaultValue": false,
                    "validateWhenHidden": false
                  },
                  {
                    "key": "QDR_ALLEGATO.uso_geotermico_max20l_s",
                    "type": "checkbox",
                    "input": true,
                    "label": "Pozzi per impianti geotermici con portata massima fino a 20 l/s",
                    "conditional": {
                      "show": true,
                      "when": "QDR_ALLEGATO.procedura_semplificata",
                      "eq": "true"
                    },
                    "tableView": false,
                    "customClass": "formio-fieldset-check-input",
                    "defaultValue": false,
                    "validateWhenHidden": false
                  }
                ]
              }
            ],
            "conditional": {
              "eq": "no",
              "show": true,
              "when": "valutazione_impatto_ambientale"
            }
          }
        ],
        "conditional": {
          "eq": "no",
          "show": true,
          "when": "verifica_impatto_ambientale"
        }
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
        "defaultValue": "di aver preso visione dell'Informativa sul trattamento dei dati personali (http://www.regione.piemonte.it/ambiente/valutazioni_ambientali/dwd/Informativa_privacy_PIVIA.pdf), aggiornata all'art. 13 del GDPR 2016/679, consultabile sul Sito Web Istituzionale (https://www.regione.piemonte.it/web/temi/ambiente-territorio/ambiente/valutazioni-ambientali/valutazione-impatto-ambientale-via) e sulla Home Page del servizio (http://www.sistemapiemonte.it/cms/privati/ambiente-e-energia/servizi/804-valutazioni-e-adempimenti-ambientali)."
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
        "key": "dichiarazioni[0].check",
        "type": "checkbox",
        "input": true,
        "label": "di aver preso visione dell'<a href=\"http://www.regione.piemonte.it/ambiente/valutazioni_ambientali/dwd/Informativa_privacy_PIVIA.pdf\" target=\"_blank\">Informativa sul trattamento dei dati personali</a>, aggiornata all'art. 13 del GDPR 2016/679, consultabile sul <a href=\"https://www.regione.piemonte.it/web/temi/ambiente-territorio/ambiente/valutazioni-ambientali/valutazione-impatto-ambientale-via\" target=\"_blank\">Sito Web Istituzionale</a> e sulla <a href=\"http://www.sistemapiemonte.it/cms/privati/ambiente-e-energia/servizi/804-valutazioni-e-adempimenti-ambientali\" target=\"_blank\">Home Page del servizio</a>",
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
        "tableView": false,
        "persistent": false,
        "defaultValue": false
      },
      {
        "key": "JS_ALERT_BOX.verifica_impatto_ambientale",
        "tag": "div",
        "type": "htmlelement",
        "attrs": [
          {
            "attr": "",
            "value": ""
          }
        ],
        "input": false,
        "label": "JS_ALERT_BOX.verifica_impatto_ambientale",
        "content": "<!-- RIFERIMENTO MESSAGGIO: A052 -->\n<i class=\"icon icon-big fa fa-exclamation-circle\" aria-hidden=\"true\"></i>\n<span>\n  <p class=\"text-only\">\n    <b>Attenzione:</b> procedimento collegato a VIA. Per proseguire seleziona dalla home page il procedimento di VIA per avviare un procedimento unico.\n  </p>\n</span>",
        "className": "alert-box warning",
        "tableView": false,
        "conditional": {
          "eq": "si",
          "show": true,
          "when": "verifica_impatto_ambientale"
        },
        "refreshOnChange": false
      },
      {
        "key": "JS_ALERT_BOX.valutazione_impatto_ambientale",
        "tag": "div",
        "type": "htmlelement",
        "attrs": [
          {
            "attr": "",
            "value": ""
          }
        ],
        "input": false,
        "label": "JS_ALERT_BOX.valutazione_impatto_ambientale",
        "content": "<!-- RIFERIMENTO MESSAGGIO: A052 -->\n<i class=\"icon icon-big fa fa-exclamation-circle\" aria-hidden=\"true\"></i>\n<span>\n  <p class=\"text-only\">\n    <b>Attenzione:</b> procedimento collegato a VIA. Per proseguire seleziona dalla home page il procedimento di VIA per avviare un procedimento unico.\n  </p>\n</span>",
        "className": "alert-box warning",
        "tableView": false,
        "conditional": {
          "eq": "si",
          "show": true,
          "when": "valutazione_impatto_ambientale"
        },
        "refreshOnChange": false
      }
    ]
  }
};
