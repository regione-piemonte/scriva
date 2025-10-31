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
export const QDR_DER_ALTRIUSI_DEBUG = {
    "label": "Altri usi",
    "jsonForm": {
      "display": "form",
      "components": [
        {
          "key": "title",
          "type": "htmlelement",
          "attrs": [
            {
              "attr": "",
              "value": ""
            }
          ],
          "input": false,
          "label": "title",
          "content": "Compila i dati degli usi dell’acqua non collegabili ad una Unità Locale Operativa, se sono presenti.",
          "tableView": false,
          "refreshOnChange": false
        },
        {
          "key": "string_campo_obbligatorio",
          "tag": "small",
          "type": "htmlelement",
          "attrs": [
            {
              "attr": "",
              "value": ""
            }
          ],
          "input": false,
          "label": "string_campo_obbligatorio",
          "content": "*Campo obbligatorio",
          "className": "text-muted",
          "tableView": false,
          "customClass": "form-text s-mb--20",
          "refreshOnChange": false
        },
        {
          "key": "usoAgricolo",
          "type": "panel",
          "input": false,
          "label": "Panel",
          "title": "Uso agricolo",
          "collapsed": true,
          "tableView": false,
          "components": [
            {
              "key": "USO_AGRICOLO.flgUsoAgricolo",
              "type": "radio",
              "input": true,
              "label": "E' presente l'uso agricolo?",
              "inline": true,
              "values": [
                {
                  "label": "Sì",
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
              "tableView": true,
              "defaultValue": "no",
              "validateWhenHidden": false,
              "optionsLabelPosition": "right"
            },
            {
              "key": "JS_MODAL.USO_AGRICOLO.flgUsoAgricolo",
              "type": "textfield",
              "input": true,
              "label": "Conferma presenza uso agricolo [NASCOSTO]",
              "logic": [
                {
                  "name": "JS_MODAL_TRIGGER.USO_AGRICOLO.flgUsoAgricolo",
                  "actions": [
                    {
                      "name": "JS_MODAL_CONFERMA.USO_AGRICOLO.flgUsoAgricolo",
                      "type": "customAction",
                      "customAction": "try {\n  const componenteTrigger = 'USO_AGRICOLO.flgUsoAgricolo';\n  const componenteConferma = 'JS_MODAL.USO_AGRICOLO.flgUsoAgricolo';\n  const formioModals = options.SCRIVA.formioModals;\n  const formioUpdate = options.SCRIVA.formioUpdate;\n  const formioUpdateComponent = options.SCRIVA.formioUpdateComponent;\n\n  const onConfirm = () => {\n    return formioUpdateComponent(data, componenteConferma, 'no');\n  }\n  const onCancel = () => {\n    return formioUpdateComponent(data, componenteTrigger, 'si');\n  }\n  \n  const config = {\n    title: 'Conferma',\n    codeOrBody: 'A064',\n    callbacks: {\n      onConfirm: () => {\n        data = onConfirm();\n        formioUpdate(data);\n      },\n      onCancel: () => {\n        data = onCancel();\n        formioUpdate(data);\n      },\n      onClose: () => {\n        data = onCancel();\n        formioUpdate(data);\n      }\n    }\n  }\n  \n  formioModals.conferma(config);\n  \n} catch (e) {}"
                    }
                  ],
                  "trigger": {
                    "type": "javascript",
                    "simple": {},
                    "javascript": "try {\n  const check1 = data.USO_AGRICOLO.flgUsoAgricolo == 'no';\n  const check2 = data.JS_MODAL.USO_AGRICOLO.flgUsoAgricolo == 'si';\n  result = check1 && check2;\n} catch (e) {\n  return false;\n}"
                  }
                },
                {
                  "name": "JS_MODAL_SET_TRIGGER.USO_AGRICOLO.flgUsoAgricolo",
                  "actions": [
                    {
                      "name": "JS_MODAL_SET_TRIGGER_SI.USO_AGRICOLO.flgUsoAgricolo",
                      "type": "value",
                      "value": "value = 'si';"
                    }
                  ],
                  "trigger": {
                    "type": "simple",
                    "simple": {
                      "eq": "si",
                      "show": true,
                      "when": "USO_AGRICOLO.flgUsoAgricolo"
                    }
                  }
                }
              ],
              "redrawOn": "data",
              "tableView": true,
              "applyMaskOn": "change",
              "customClass": "display-none",
              "defaultValue": "init",
              "validateWhenHidden": false
            },
            {
              "key": "esercizio",
              "type": "panel",
              "input": false,
              "label": "Panel",
              "title": "* Esercizio",
              "collapsed": true,
              "tableView": false,
              "components": [
                {
                  "key": "USO_AGRICOLO.uso_effettivo",
                  "type": "editgrid",
                  "input": true,
                  "label": "<strong>Uso effettivo</strong>",
                  "saveRow": "CONFERMA",
                  "removeRow": "ANNULLA",
                  "rowDrafts": false,
                  "tableView": false,
                  "templates": {
                    "row": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">\r\n        {{ isVisibleInRow(component) ? getView(component, row[component.key]) : ''}}\r\n      </div>\r\n    {% } %}\r\n  {% }) %}\r\n  {% if (!instance.options.readOnly && !instance.disabled) { %}\r\n    <div class=\"col\">\r\n      <div class=\"btn-group pull-left\">\r\n        <button class=\"btn btn-default btn-light btn-sm editRow\"><i class=\"{{ iconClass('edit') }}\"></i></button>\r\n        {% if (!instance.hasRemoveButtons || instance.hasRemoveButtons()) { %}\r\n          <button class=\"btn btn-danger btn-sm removeRow\"><i class=\"{{ iconClass('trash') }}\"></i></button>\r\n        {% } %}\r\n      </div>\r\n    </div>\r\n  {% } %}\r\n</div>",
                    "header": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">{{ t(component.label) }}</div>\r\n    {% } %}\r\n  {% }) %}\r\n  <div class=\"col\">Azioni</div>\r\n</div>"
                  },
                  "addAnother": "AGGIUNGI USO",
                  "components": [
                    {
                      "key": "uso_effettivo",
                      "data": {
                        "custom": "values = [\r\n  { des_tipo_uso: 'abbeveraggio bestiame per volumi inferiori a 1000 metri cubi/anno', fk_uso_legge: 3, id_tipo_uso: 14},\r\n  { des_tipo_uso: 'altri usi non identificati', fk_uso_legge: 3, id_tipo_uso: 15},\r\n  { des_tipo_uso: 'antibrina', fk_uso_legge: 3, id_tipo_uso: 13},\r\n  { des_tipo_uso: 'irrigazione', fk_uso_legge: 3, id_tipo_uso: 12},\r\n  { des_tipo_uso: 'pulizia stalla o struttura equivalente', fk_uso_legge: 3, id_tipo_uso: 35},\r\n  { des_tipo_uso: 'trattamento fitosanitari', fk_uso_legge: 3, id_tipo_uso: 34}\r\n  ]"
                      },
                      "type": "select",
                      "input": true,
                      "label": "Uso effettivo",
                      "widget": "choicesjs",
                      "dataSrc": "custom",
                      "template": "<span>{{ item.des_tipo_uso }}</span>",
                      "validate": {
                        "required": true
                      },
                      "tableView": true,
                      "validateWhenHidden": false
                    }
                  ],
                  "displayAsTable": false,
                  "validateWhenHidden": false
                },
                {
                  "key": "USO_AGRICOLO.qta_risorsa_utilizzata",
                  "type": "editgrid",
                  "input": true,
                  "label": "<strong>Quantità di risorsa utilizzata</strong>",
                  "saveRow": "CONFERMA",
                  "removeRow": "ANNULLA",
                  "rowDrafts": false,
                  "tableView": false,
                  "templates": {
                    "row": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">\r\n        {{ isVisibleInRow(component) ? getView(component, row[component.key]) : ''}}\r\n      </div>\r\n    {% } %}\r\n  {% }) %}\r\n  {% if (!instance.options.readOnly && !instance.disabled) { %}\r\n    <div class=\"col\">\r\n      <div class=\"btn-group pull-left\">\r\n        <button class=\"btn btn-default btn-light btn-sm editRow\"><i class=\"{{ iconClass('edit') }}\"></i></button>\r\n        {% if (!instance.hasRemoveButtons || instance.hasRemoveButtons()) { %}\r\n          <button class=\"btn btn-danger btn-sm removeRow\"><i class=\"{{ iconClass('trash') }}\"></i></button>\r\n        {% } %}\r\n      </div>\r\n    </div>\r\n  {% } %}\r\n</div>",
                    "header": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">{{ t(component.label) }}</div>\r\n    {% } %}\r\n  {% }) %}\r\n  <div class=\"col\">Azioni</div>\r\n</div>\r\n"
                  },
                  "addAnother": "AGGIUNGI PERIODO",
                  "components": [
                    {
                      "key": "columns2",
                      "type": "columns",
                      "input": false,
                      "label": "Columns_2",
                      "columns": [
                        {
                          "pull": 0,
                          "push": 0,
                          "size": "md",
                          "width": 4,
                          "offset": 0,
                          "components": [
                            {
                              "label": "Portata massima (l/s)",
                              "applyMaskOn": "change",
                              "tableView": true,
                              "validate": {
                                "required": true,
                                "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                                "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                                "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                              },
                              "validateWhenHidden": false,
                              "key": "portata_max_utilizz",
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
                              "type": "textfield",
                              "input": true,
                              "hideOnChildrenHidden": false
                            }
                          ],
                          "currentWidth": 4
                        },
                        {
                          "pull": 0,
                          "push": 0,
                          "size": "md",
                          "width": 4,
                          "offset": 0,
                          "components": [
                            {
                              "label": "Portata media (l/s)",
                              "applyMaskOn": "change",
                              "tableView": true,
                              "validate": {
                                "required": true,
                                "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                                "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, inferiore o uguale alla portata massima, con un massimo di 7 cifre intere e 4 cifre decimali.",
                                "custom": "try {\r\n  // Definisco un oggetto con le informazioni da passare per la verifica\r\n  const params = {\r\n    portataMedia: input,\r\n    portataMassima: row.portata_max_utilizz,\r\n    checkZero: true,\r\n  };\r\n  // Recupero la funzione di comodo per la verifica\r\n  const qdrDERCheckPortataMedia = options.SCRIVA.formioQuadri.qdrDERCheckPortataMedia;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const check = qdrDERCheckPortataMedia(params);\r\n  // Verifico il risultato dell'elaborazione sul check\r\n  if (check) {\r\n    // Validazione completa\r\n    return true;\r\n    // #\r\n  } else {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"portataMediaInvalida\";\r\n  }\r\n} catch (e) {}"
                              },
                              "validateWhenHidden": false,
                              "key": "portata_med_utilizz",
                              "type": "textfield",
                              "input": true,
                              "hideOnChildrenHidden": false
                            }
                          ],
                          "currentWidth": 4
                        },
                        {
                          "size": "md",
                          "width": 4,
                          "components": [
                            {
                              "label": "Volume massimo (m<sup>3</sup>)",
                              "applyMaskOn": "change",
                              "tableView": true,
                              "validate": {
                                "required": true,
                                "pattern": "(^\\d{1,9}$)|(^\\d{1,9}[,]\\d{1,5}$)",
                                "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 9 cifre intere e 5 cifre decimali.",
                                "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                              },
                              "validateWhenHidden": false,
                              "key": "volume_max_utilizz",
                              "type": "textfield",
                              "input": true,
                              "hideOnChildrenHidden": false
                            }
                          ],
                          "currentWidth": 4
                        }
                      ],
                      "tableView": false
                    },
                    {
                      "key": "columnsEc2",
                      "type": "columns",
                      "input": false,
                      "label": "columns_1",
                      "columns": [
                        {
                          "pull": 0,
                          "push": 0,
                          "size": "md",
                          "width": 3,
                          "offset": 0,
                          "components": [
                            {
                              "key": "inizio_periodo",
                              "type": "textfield",
                              "input": true,
                              "label": "Dal (gg/mm)",
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
                                "custom": "const d = moment(input,'DD/MM');\n\nvalid = d.isValid() ? true : 'invalid';\n",
                                "pattern": "(^([0-2]{0,1}[0-9]|3[0,1])[/]([1-9]|0[1-9]|1[0-2])$)",
                                "required": true,
                                "customMessage": "Data non valida"
                              },
                              "tableView": true,
                              "validateOn": "blur",
                              "applyMaskOn": "change",
                              "validateWhenHidden": false,
                              "hideOnChildrenHidden": false
                            }
                          ],
                          "currentWidth": 3
                        },
                        {
                          "pull": 0,
                          "push": 0,
                          "size": "md",
                          "width": 3,
                          "offset": 0,
                          "components": [
                            {
                              "key": "fine_periodo",
                              "type": "textfield",
                              "input": true,
                              "label": "Al (gg/mm)",
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
                                "custom": "const d = moment(input,'DD/MM');\n\nvalid = d.isValid() ? true : 'invalid';\n",
                                "pattern": "(^([0-2]{0,1}[0-9]|3[0,1])[/]([1-9]|0[1-9]|1[0-2])$)",
                                "required": true,
                                "customMessage": "Data non valida"
                              },
                              "tableView": true,
                              "validateOn": "blur",
                              "applyMaskOn": "change",
                              "validateWhenHidden": false,
                              "hideOnChildrenHidden": false
                            }
                          ],
                          "currentWidth": 3
                        }
                      ],
                      "tableView": false
                    },
                    {
                      "key": "columns",
                      "type": "columns",
                      "input": false,
                      "label": "Columns",
                      "columns": [
                        {
                          "pull": 0,
                          "push": 0,
                          "size": "md",
                          "width": 6,
                          "offset": 0,
                          "components": [
                            {
                              "label": "Superficie irrigabile (ha)",
                              "applyMaskOn": "change",
                              "tableView": true,
                              "validate": {
                                "required": true,
                                "pattern": "(^\\d{1,6}$)|(^\\d{1,6}[,]\\d{1,5}$)",
                                "customMessage": "Indicare un valore maggiore di 0, con un massimo di 6 cifre intere e 5 cifre decimali",
                                "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                              },
                              "validateWhenHidden": false,
                              "key": "sup_irrigabile",
                              "type": "textfield",
                              "input": true
                            }
                          ],
                          "currentWidth": 6
                        },
                        {
                          "pull": 0,
                          "push": 0,
                          "size": "md",
                          "width": 6,
                          "offset": 0,
                          "components": [
                            {
                              "label": "Superficie massima irrigata (ha)",
                              "applyMaskOn": "change",
                              "tableView": true,
                              "validate": {
                                "required": true,
                                "pattern": "(^\\d{1,6}$)|(^\\d{1,6}[,]\\d{1,5}$)",
                                "customMessage": "Indicare un valore maggiore di 0, con un massimo di 6 cifre intere e 5 cifre decimali",
                                "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                              },
                              "validateWhenHidden": false,
                              "key": "sup_irrigata",
                              "type": "textfield",
                              "input": true
                            }
                          ],
                          "currentWidth": 6
                        }
                      ],
                      "tableView": false
                    }
                  ],
                  "displayAsTable": false,
                  "validateWhenHidden": false
                },
                {
                  "key": "USO_AGRICOLO.colt_irrigua",
                  "type": "editgrid",
                  "input": true,
                  "label": "<strong>Colture</strong>",
                  "saveRow": "CONFERMA",
                  "removeRow": "ANNULLA",
                  "rowDrafts": false,
                  "tableView": false,
                  "templates": {
                    "row": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">\r\n        {{ isVisibleInRow(component) ? getView(component, row[component.key]) : ''}}\r\n      </div>\r\n    {% } %}\r\n  {% }) %}\r\n  {% if (!instance.options.readOnly && !instance.disabled) { %}\r\n    <div class=\"col\">\r\n      <div class=\"btn-group pull-left\">\r\n        <button class=\"btn btn-default btn-light btn-sm editRow\"><i class=\"{{ iconClass('edit') }}\"></i></button>\r\n        {% if (!instance.hasRemoveButtons || instance.hasRemoveButtons()) { %}\r\n          <button class=\"btn btn-danger btn-sm removeRow\"><i class=\"{{ iconClass('trash') }}\"></i></button>\r\n        {% } %}\r\n      </div>\r\n    </div>\r\n  {% } %}\r\n</div>",
                    "header": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">{{ t(component.label) }}</div>\r\n    {% } %}\r\n  {% }) %}\r\n  <div class=\"col\">Azioni</div>\r\n</div>\r\n"
                  },
                  "addAnother": "AGGIUNGI RIGA",
                  "components": [
                    {
                      "key": "coltura_old",
                      "type": "textfield",
                      "input": true,
                      "label": "Coltura (testo)",
                      "hidden": true,
                      "validate": {
                        "maxLength": 100,
                        "customMessage": "Inserire un massimo di 100 caratteri"
                      },
                      "tableView": false,
                      "applyMaskOn": "change",
                      "validateWhenHidden": false
                    },
                    {
                      "key": "coltura",
                      "data": {
                        "custom": "values = [\r\n  { des_coltura: 'ACACIA', id_coltura: 4},\r\n  { des_coltura: 'ACONITO', id_coltura: 5},\r\n  { des_coltura: 'ACTINIDIA', id_coltura: 6},\r\n  { des_coltura: 'ALTRA ARBORICOLTURA DA LEGNO', id_coltura: 274},\r\n  { des_coltura: 'ALTRE LEGUMINOSE DA GRANELLA', id_coltura: 13},\r\n  { des_coltura: 'ALTRI SEMINATIVI', id_coltura: 275}\r\n  ]"
                      },
                      "type": "select",
                      "input": true,
                      "label": "Coltura",
                      "widget": "choicesjs",
                      "dataSrc": "custom",
                      "template": "<span>{{ item.des_coltura }}</span>",
                      "validate": {
                        "required": true
                      },
                      "tableView": true,
                      "validateWhenHidden": false
                    },
                    {
                      "key": "columns1",
                      "type": "columns",
                      "input": false,
                      "label": "Columns",
                      "columns": [
                        {
                          "pull": 0,
                          "push": 0,
                          "size": "md",
                          "width": 6,
                          "offset": 0,
                          "components": [
                            {
                              "key": "metodo_irriguo",
                              "data": {
                                "custom": "values = [\r\n  { des_metodo_irriguo: 'Scorrimento', id_metodo_irriguo: 1},\r\n  { des_metodo_irriguo: 'Aspersione', id_metodo_irriguo: 2},\r\n  { des_metodo_irriguo: 'Microirrigazione', id_metodo_irriguo: 3},\r\n  { des_metodo_irriguo: 'Sommersione', id_metodo_irriguo: 4}\r\n  ]"
                              },
                              "type": "select",
                              "input": true,
                              "label": "Metodo irriguo",
                              "widget": "choicesjs",
                              "dataSrc": "custom",
                              "template": "<span>{{ item.des_metodo_irriguo }}</span>  ",
                              "validate": {
                                "required": true
                              },
                              "tableView": true,
                              "validateWhenHidden": false
                            }
                          ],
                          "currentWidth": 6
                        },
                        {
                          "pull": 0,
                          "push": 0,
                          "size": "md",
                          "width": 6,
                          "offset": 0,
                          "components": [
                            {
                              "label": "Superficie irrigata (ha)",
                              "applyMaskOn": "change",
                              "tableView": true,
                              "validate": {
                                "required": true,
                                "pattern": "(^\\d{1,6}$)|(^\\d{1,6}[,]\\d{1,5}$)",
                                "customMessage": "Indicare un valore maggiore di 0, con un massimo di 6 cifre intere e 5 cifre decimali",
                                "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                              },
                              "validateWhenHidden": false,
                              "key": "sup_irrigata",
                              "type": "textfield",
                              "input": true
                            }
                          ],
                          "currentWidth": 6
                        }
                      ],
                      "tableView": false
                    }
                  ],
                  "displayAsTable": false,
                  "validateWhenHidden": false
                },
                {
                  "key": "columns5",
                  "type": "columns",
                  "input": false,
                  "label": "Columns",
                  "columns": [
                    {
                      "pull": 0,
                      "push": 0,
                      "size": "md",
                      "width": 6,
                      "offset": 0,
                      "components": [
                        {
                          "label": "Portata media (l/s)",
                          "applyMaskOn": "change",
                          "tableView": true,
                          "validate": {
                            "required": true,
                            "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                            "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                            "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                          },
                          "validateWhenHidden": false,
                          "key": "USO_AGRICOLO.portata_media",
                          "type": "textfield",
                          "input": true
                        }
                      ],
                      "currentWidth": 6
                    },
                    {
                      "pull": 0,
                      "push": 0,
                      "size": "md",
                      "width": 6,
                      "offset": 0,
                      "components": [
                        {
                          "label": "Volume massimo annuo (m<sup>3</sup>)",
                          "applyMaskOn": "change",
                          "tableView": true,
                          "validate": {
                            "required": true,
                            "pattern": "(^\\d{1,11}$)|(^\\d{1,11}[,]\\d{1,2}$)",
                            "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 11 cifre intere e 2 cifre decimali.",
                            "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                          },
                          "validateWhenHidden": false,
                          "key": "USO_AGRICOLO.volume_max_annuo",
                          "type": "textfield",
                          "input": true
                        }
                      ],
                      "currentWidth": 6
                    }
                  ],
                  "tableView": false
                }
              ],
              "collapsible": true,
              "customConditional": "try {\r\n  // Recupero il valore della input modale\r\n  const inputUtente = data.USO_AGRICOLO.flgUsoAgricolo;\r\n  const inputModale = data.JS_MODAL.USO_AGRICOLO.flgUsoAgricolo;\r\n  // Se l'input ha un valore specifico o quello di init devo nascondere l'elemento\r\n  const caseOnInit = inputUtente === 'si' && inputModale === 'init';\r\n  const caseOnUse = inputModale === 'si';\r\n  // Definisco le condizioni di visualizzazione\r\n  show = caseOnInit || caseOnUse;\r\n} catch(e) {\r\n  show = true;\r\n}\r\n"
            },
            {
              "key": "panel",
              "type": "panel",
              "input": false,
              "label": "Panel",
              "title": "* Dati tecnici",
              "collapsed": true,
              "tableView": false,
              "components": [
                {
                  "key": "columns",
                  "type": "columns",
                  "input": false,
                  "label": "Columns",
                  "columns": [
                    {
                      "pull": 0,
                      "push": 0,
                      "size": "md",
                      "width": 6,
                      "offset": 0,
                      "components": [
                        {
                          "label": "Superficie irrigabile (ha)",
                          "applyMaskOn": "change",
                          "tableView": true,
                          "validate": {
                            "required": true,
                            "pattern": "(^\\d{1,6}$)|(^\\d{1,6}[,]\\d{1,5}$)",
                            "customMessage": "Indicare un valore maggiore di 0, con un massimo di 6 cifre intere e 5 cifre decimali",
                            "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                          },
                          "validateWhenHidden": false,
                          "key": "USO_AGRICOLO.sup_irrigabile",
                          "type": "textfield",
                          "input": true
                        }
                      ],
                      "currentWidth": 6
                    },
                    {
                      "pull": 0,
                      "push": 0,
                      "size": "md",
                      "width": 6,
                      "offset": 0,
                      "components": [
                        {
                          "key": "USO_AGRICOLO.flg_bocca_nontassata",
                          "type": "radio",
                          "input": true,
                          "label": "Bocca non tassata",
                          "inline": true,
                          "values": [
                            {
                              "label": "si",
                              "value": "si",
                              "shortcut": ""
                            },
                            {
                              "label": "no",
                              "value": "no",
                              "shortcut": ""
                            }
                          ],
                          "validate": {
                            "required": true
                          },
                          "tableView": false,
                          "customConditional": "show = options.SCRIVA.isBackOffice;",
                          "validateWhenHidden": false,
                          "optionsLabelPosition": "right"
                        }
                      ],
                      "currentWidth": 6
                    }
                  ],
                  "tableView": false
                },
                {
                  "key": "columns1",
                  "type": "columns",
                  "input": false,
                  "label": "Columns",
                  "columns": [
                    {
                      "pull": 0,
                      "push": 0,
                      "size": "md",
                      "width": 6,
                      "offset": 0,
                      "components": [
                        {
                          "key": "USO_AGRICOLO.sup_irrigata",
                          "type": "textfield",
                          "input": true,
                          "label": "Superficie irrigata (ha)",
                          "disabled": true,
                          "validate": {
                            "pattern": "(^\\d{1,6}$)|(^\\d{1,6}[,]\\d{1,5}$)",
                            "customMessage": "Indicare un valore maggiore di 0, con un massimo di 6 cifre intere e 5 cifre decimali"
                          },
                          "tableView": true,
                          "applyMaskOn": "change",
                          "defaultValue": "0",
                          "calculateValue": "// Recupero i dati per l'edit grid della coltura\nconst colture = data.USO_AGRICOLO.colt_irrigua;\n// Calcolo e ritorno il totale\nconst tot = options.SCRIVA.formioQuadri.qdrAltriUsiDERCalcoloSuperficieIrrigata(colture);\n// Ritorno il valore calcolato\nreturn tot;",
                          "validateWhenHidden": false
                        }
                      ],
                      "currentWidth": 6
                    },
                    {
                      "pull": 0,
                      "push": 0,
                      "size": "md",
                      "width": 6,
                      "offset": 0,
                      "components": [],
                      "currentWidth": 6
                    }
                  ],
                  "tableView": false
                }
              ],
              "collapsible": true,
              "customConditional": "try {\r\n  // Recupero il valore della input modale\r\n  const inputUtente = data.USO_AGRICOLO.flgUsoAgricolo;\r\n  const inputModale = data.JS_MODAL.USO_AGRICOLO.flgUsoAgricolo;\r\n  // Se l'input ha un valore specifico o quello di init devo nascondere l'elemento\r\n  const caseOnInit = inputUtente === 'si' && inputModale === 'init';\r\n  const caseOnUse = inputModale === 'si';\r\n  // Definisco le condizioni di visualizzazione\r\n  show = caseOnInit || caseOnUse;\r\n} catch(e) {\r\n  show = true;\r\n}\r\n"
            }
          ],
          "collapsible": true
        },
        {
          "key": "usoCivile",
          "type": "panel",
          "input": false,
          "label": "Panel",
          "title": "Uso civile",
          "collapsed": true,
          "tableView": false,
          "components": [
            {
              "key": "USO_CIVILE.flgUsoCivile",
              "type": "radio",
              "input": true,
              "label": "E' presente l'uso civile?",
              "inline": true,
              "values": [
                {
                  "label": "Sì",
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
              "tableView": true,
              "defaultValue": "no",
              "validateWhenHidden": false,
              "optionsLabelPosition": "right"
            },
            {
              "key": "JS_MODAL.USO_CIVILE.flgUsoCivile",
              "type": "textfield",
              "input": true,
              "label": "Conferma presenza uso civile [NASCOSTO]",
              "logic": [
                {
                  "name": "JS_MODAL_TRIGGER.USO_CIVILE.flgUsoCivile",
                  "actions": [
                    {
                      "name": "JS_MODAL_CONFERMA.USO_CIVILE.flgUsoCivile",
                      "type": "customAction",
                      "customAction": "try {\n  const componenteTrigger = 'USO_CIVILE.flgUsoCivile';\n  const componenteConferma = 'JS_MODAL.USO_CIVILE.flgUsoCivile';\n  const formioModals = options.SCRIVA.formioModals;\n  const formioUpdate = options.SCRIVA.formioUpdate;\n  const formioUpdateComponent = options.SCRIVA.formioUpdateComponent;\n\n  const onConfirm = () => {\n    return formioUpdateComponent(data, componenteConferma, 'no');\n  }\n  const onCancel = () => {\n    return formioUpdateComponent(data, componenteTrigger, 'si');\n  }\n  \n  const config = {\n    title: 'Conferma',\n    codeOrBody: 'A064',\n    callbacks: {\n      onConfirm: () => {\n        data = onConfirm();\n        formioUpdate(data);\n      },\n      onCancel: () => {\n        data = onCancel();\n        formioUpdate(data);\n      },\n      onClose: () => {\n        data = onCancel();\n        formioUpdate(data);\n      }\n    }\n  }\n  \n  formioModals.conferma(config);\n  \n} catch (e) {}"
                    }
                  ],
                  "trigger": {
                    "type": "javascript",
                    "simple": {},
                    "javascript": "try {\n  const check1 = data.USO_CIVILE.flgUsoCivile == 'no';\n  const check2 = data.JS_MODAL.USO_CIVILE.flgUsoCivile == 'si';\n  result = check1 && check2;\n} catch (e) {\n  return false;\n}"
                  }
                },
                {
                  "name": "JS_MODAL_SET_TRIGGER.USO_CIVILE.flgUsoCivile",
                  "actions": [
                    {
                      "name": "JS_MODAL_SET_TRIGGER_SI.USO_CIVILE.flgUsoCivile",
                      "type": "value",
                      "value": "value = 'si';"
                    }
                  ],
                  "trigger": {
                    "type": "simple",
                    "simple": {
                      "eq": "si",
                      "show": true,
                      "when": "USO_CIVILE.flgUsoCivile"
                    }
                  }
                }
              ],
              "redrawOn": "data",
              "tableView": true,
              "applyMaskOn": "change",
              "customClass": "display-none",
              "defaultValue": "init",
              "validateWhenHidden": false
            },
            {
              "key": "esercizio1",
              "type": "panel",
              "input": false,
              "label": "Panel",
              "title": "* Esercizio",
              "collapsed": true,
              "tableView": false,
              "components": [
                {
                  "key": "USO_CIVILE.qta_risorsa_utilizzata",
                  "type": "editgrid",
                  "input": true,
                  "label": "<strong>Quantità di risorsa utilizzata</strong>",
                  "saveRow": "CONFERMA",
                  "removeRow": "ANNULLA",
                  "rowDrafts": false,
                  "tableView": false,
                  "templates": {
                    "row": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">\r\n        {{ isVisibleInRow(component) ? getView(component, row[component.key]) : ''}}\r\n      </div>\r\n    {% } %}\r\n  {% }) %}\r\n  {% if (!instance.options.readOnly && !instance.disabled) { %}\r\n    <div class=\"col\">\r\n      <div class=\"btn-group pull-left\">\r\n        <button class=\"btn btn-default btn-light btn-sm editRow\"><i class=\"{{ iconClass('edit') }}\"></i></button>\r\n        {% if (!instance.hasRemoveButtons || instance.hasRemoveButtons()) { %}\r\n          <button class=\"btn btn-danger btn-sm removeRow\"><i class=\"{{ iconClass('trash') }}\"></i></button>\r\n        {% } %}\r\n      </div>\r\n    </div>\r\n  {% } %}\r\n</div>",
                    "header": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">{{ t(component.label) }}</div>\r\n    {% } %}\r\n  {% }) %}\r\n  <div class=\"col\">Azioni</div>\r\n</div>\r\n"
                  },
                  "addAnother": "AGGIUNGI PERIODO",
                  "components": [
                    {
                      "key": "columnsEc2",
                      "type": "columns",
                      "input": false,
                      "label": "columns_1",
                      "columns": [
                        {
                          "pull": 0,
                          "push": 0,
                          "size": "md",
                          "width": 6,
                          "offset": 0,
                          "components": [
                            {
                              "key": "tipo_uso",
                              "data": {
                                "custom": "values = [\r\n  { des_tipo_uso: 'alimentazione laghetto', fk_uso_legge: 5, id_tipo_uso: 40},\r\n  { des_tipo_uso: 'alimentazione lavatoio', fk_uso_legge: 5, id_tipo_uso: 41},\r\n  { des_tipo_uso: 'alimentazione piscina privata', fk_uso_legge: 5, id_tipo_uso: 39},\r\n  { des_tipo_uso: 'igienico-sanitario', fk_uso_legge: 5, id_tipo_uso: 38},\r\n  { des_tipo_uso: 'irrigazione aree verdi private', fk_uso_legge: 5, id_tipo_uso: 37},\r\n  { des_tipo_uso: 'irrigazione di aree verdi pubbliche', fk_uso_legge: 5, id_tipo_uso: 31},\r\n  { des_tipo_uso: 'lavaggio mezzi', fk_uso_legge: 5, id_tipo_uso: 36},\r\n  { des_tipo_uso: 'lavaggio strade e piazzali', fk_uso_legge: 5, id_tipo_uso: 29},\r\n  { des_tipo_uso: 'pompe di calore', fk_uso_legge: 5, id_tipo_uso: 42},\r\n  { des_tipo_uso: 'scorte antincendio', fk_uso_legge: 5, id_tipo_uso: 32},\r\n  { des_tipo_uso: 'spurgo fognature', fk_uso_legge: 5, id_tipo_uso: 30},\r\n  { des_tipo_uso: 'altri usi non identificati', fk_uso_legge: 5, id_tipo_uso: 33}\r\n  ]"
                              },
                              "type": "select",
                              "input": true,
                              "label": "Uso effettivo",
                              "widget": "choicesjs",
                              "dataSrc": "custom",
                              "template": "<span>{{ item.des_tipo_uso }}</span>",
                              "validate": {
                                "required": true
                              },
                              "tableView": true,
                              "validateWhenHidden": false
                            }
                          ],
                          "currentWidth": 6
                        },
                        {
                          "pull": 0,
                          "push": 0,
                          "size": "md",
                          "width": 2,
                          "offset": 0,
                          "components": [
                            {
                              "key": "inizio_periodo",
                              "type": "textfield",
                              "input": true,
                              "label": "Dal (gg/mm)",
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
                                "custom": "const d = moment(input,'DD/MM');\n\nvalid = d.isValid() ? true : 'invalid';\n",
                                "pattern": "(^([0-2]{0,1}[0-9]|3[0,1])[/]([1-9]|0[1-9]|1[0-2])$)",
                                "required": true,
                                "customMessage": "Data non valida"
                              },
                              "tableView": true,
                              "validateOn": "blur",
                              "applyMaskOn": "change",
                              "validateWhenHidden": false,
                              "hideOnChildrenHidden": false
                            }
                          ],
                          "currentWidth": 2
                        },
                        {
                          "size": "md",
                          "width": 2,
                          "components": [
                            {
                              "key": "fine_periodo",
                              "type": "textfield",
                              "input": true,
                              "label": "Al (gg/mm)",
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
                                "custom": "const d = moment(input,'DD/MM');\n\nvalid = d.isValid() ? true : 'invalid';\n",
                                "pattern": "(^([0-2]{0,1}[0-9]|3[0,1])[/]([1-9]|0[1-9]|1[0-2])$)",
                                "required": true,
                                "customMessage": "Data non valida"
                              },
                              "tableView": true,
                              "validateOn": "blur",
                              "applyMaskOn": "change",
                              "validateWhenHidden": false,
                              "hideOnChildrenHidden": false
                            }
                          ],
                          "currentWidth": 2
                        }
                      ],
                      "tableView": false
                    },
                    {
                      "key": "columns2",
                      "type": "columns",
                      "input": false,
                      "label": "Columns_2",
                      "columns": [
                        {
                          "pull": 0,
                          "push": 0,
                          "size": "md",
                          "width": 4,
                          "offset": 0,
                          "components": [
                            {
                              "label": "Portata massima (l/s)",
                              "applyMaskOn": "change",
                              "tableView": true,
                              "validate": {
                                "required": true,
                                "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                                "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                                "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                              },
                              "validateWhenHidden": false,
                              "key": "portata_max_utilizz",
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
                              "type": "textfield",
                              "input": true,
                              "hideOnChildrenHidden": false
                            }
                          ],
                          "currentWidth": 4
                        },
                        {
                          "pull": 0,
                          "push": 0,
                          "size": "md",
                          "width": 4,
                          "offset": 0,
                          "components": [
                            {
                              "label": "Portata media (l/s)",
                              "applyMaskOn": "change",
                              "tableView": true,
                              "validate": {
                                "required": true,
                                "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                                "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, inferiore o uguale alla portata massima, con un massimo di 7 cifre intere e 4 cifre decimali.",
                                "custom": "try {\r\n  // Definisco un oggetto con le informazioni da passare per la verifica\r\n  const params = {\r\n    portataMedia: input,\r\n    portataMassima: row.portata_max_utilizz,\r\n    checkZero: true,\r\n  };\r\n  // Recupero la funzione di comodo per la verifica\r\n  const qdrDERCheckPortataMedia = options.SCRIVA.formioQuadri.qdrDERCheckPortataMedia;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const check = qdrDERCheckPortataMedia(params);\r\n  // Verifico il risultato dell'elaborazione sul check\r\n  if (check) {\r\n    // Validazione completa\r\n    return true;\r\n    // #\r\n  } else {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"portataMediaInvalida\";\r\n  }\r\n} catch (e) {}"
                              },
                              "validateWhenHidden": false,
                              "key": "portata_med_utilizz",
                              "type": "textfield",
                              "input": true,
                              "hideOnChildrenHidden": false
                            }
                          ],
                          "currentWidth": 4
                        },
                        {
                          "size": "md",
                          "width": 4,
                          "components": [
                            {
                              "label": "Volume massimo (m<sup>3</sup>)",
                              "applyMaskOn": "change",
                              "tableView": true,
                              "validate": {
                                "required": true,
                                "pattern": "(^\\d{1,9}$)|(^\\d{1,9}[,]\\d{1,5}$)",
                                "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 9 cifre intere e 5 cifre decimali.",
                                "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                              },
                              "validateWhenHidden": false,
                              "key": "volume_max_utilizz",
                              "type": "textfield",
                              "input": true,
                              "hideOnChildrenHidden": false
                            }
                          ],
                          "currentWidth": 4
                        }
                      ],
                      "tableView": false
                    }
                  ],
                  "displayAsTable": false,
                  "validateWhenHidden": false
                },
                {
                  "key": "columns6",
                  "type": "columns",
                  "input": false,
                  "label": "Columns",
                  "columns": [
                    {
                      "pull": 0,
                      "push": 0,
                      "size": "md",
                      "width": 6,
                      "offset": 0,
                      "components": [
                        {
                          "label": "Portata media (l/s)",
                          "applyMaskOn": "change",
                          "tableView": true,
                          "validate": {
                            "required": true,
                            "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                            "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                            "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                          },
                          "validateWhenHidden": false,
                          "key": "USO_CIVILE.portata_media",
                          "type": "textfield",
                          "input": true
                        }
                      ],
                      "currentWidth": 6
                    },
                    {
                      "pull": 0,
                      "push": 0,
                      "size": "md",
                      "width": 6,
                      "offset": 0,
                      "components": [
                        {
                          "label": "Volume massimo annuo (m<sup>3</sup>)",
                          "applyMaskOn": "change",
                          "tableView": true,
                          "validate": {
                            "required": true,
                            "pattern": "(^\\d{1,11}$)|(^\\d{1,11}[,]\\d{1,2}$)",
                            "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 11 cifre intere e 2 cifre decimali.",
                            "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                          },
                          "validateWhenHidden": false,
                          "key": "USO_CIVILE.volume_max_annuo",
                          "type": "textfield",
                          "input": true
                        }
                      ],
                      "currentWidth": 6
                    }
                  ],
                  "tableView": false
                }
              ],
              "collapsible": true,
              "customConditional": "try {\r\n  // Recupero il valore della input modale\r\n  const inputUtente = data.USO_CIVILE.flgUsoCivile;\r\n  const inputModale = data.JS_MODAL.USO_CIVILE.flgUsoCivile;\r\n  // Se l'input ha un valore specifico o quello di init devo nascondere l'elemento\r\n  const caseOnInit = inputUtente === 'si' && inputModale === 'init';\r\n  const caseOnUse = inputModale === 'si';\r\n  // Definisco le condizioni di visualizzazione\r\n  show = caseOnInit || caseOnUse;\r\n} catch(e) {\r\n  show = true;\r\n}\r\n"
            }
          ],
          "collapsible": true
        },
        {
          "key": "usoDomestico",
          "type": "panel",
          "input": false,
          "label": "Panel",
          "title": "Uso domestico",
          "collapsed": true,
          "tableView": false,
          "components": [
            {
              "key": "USO_DOMESTICO.flgUsoDomestico",
              "type": "radio",
              "input": true,
              "label": "E' presente l'uso domestico?",
              "inline": true,
              "values": [
                {
                  "label": "Sì",
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
              "tableView": true,
              "defaultValue": "no",
              "validateWhenHidden": false,
              "optionsLabelPosition": "right"
            },
            {
              "key": "JS_MODAL.USO_DOMESTICO.flgUsoDomestico",
              "type": "textfield",
              "input": true,
              "label": "Conferma presenza uso domestico [NASCOSTO]",
              "logic": [
                {
                  "name": "JS_MODAL_TRIGGER.USO_DOMESTICO.flgUsoDomestico",
                  "actions": [
                    {
                      "name": "JS_MODAL_CONFERMA.USO_DOMESTICO.flgUsoDomestico",
                      "type": "customAction",
                      "customAction": "try {\n  const componenteTrigger = 'USO_DOMESTICO.flgUsoDomestico';\n  const componenteConferma = 'JS_MODAL.USO_DOMESTICO.flgUsoDomestico';\n  const formioModals = options.SCRIVA.formioModals;\n  const formioUpdate = options.SCRIVA.formioUpdate;\n  const formioUpdateComponent = options.SCRIVA.formioUpdateComponent;\n\n  const onConfirm = () => {\n    return formioUpdateComponent(data, componenteConferma, 'no');\n  }\n  const onCancel = () => {\n    return formioUpdateComponent(data, componenteTrigger, 'si');\n  }\n  \n  const config = {\n    title: 'Conferma',\n    codeOrBody: 'A064',\n    callbacks: {\n      onConfirm: () => {\n        data = onConfirm();\n        formioUpdate(data);\n      },\n      onCancel: () => {\n        data = onCancel();\n        formioUpdate(data);\n      },\n      onClose: () => {\n        data = onCancel();\n        formioUpdate(data);\n      }\n    }\n  }\n  \n  formioModals.conferma(config);\n  \n} catch (e) {}"
                    }
                  ],
                  "trigger": {
                    "type": "javascript",
                    "simple": {},
                    "javascript": "try {\n  const check1 = data.USO_DOMESTICO.flgUsoDomestico == 'no';\n  const check2 = data.JS_MODAL.USO_DOMESTICO.flgUsoDomestico == 'si';\n  result = check1 && check2;\n} catch (e) {\n  return false;\n}"
                  }
                },
                {
                  "name": "JS_MODAL_SET_TRIGGER.USO_DOMESTICO.flgUsoDomestico",
                  "actions": [
                    {
                      "name": "JS_MODAL_SET_TRIGGER_SI.USO_DOMESTICO.flgUsoDomestico",
                      "type": "value",
                      "value": "value = 'si';"
                    }
                  ],
                  "trigger": {
                    "type": "simple",
                    "simple": {
                      "eq": "si",
                      "show": true,
                      "when": "USO_DOMESTICO.flgUsoDomestico"
                    }
                  }
                }
              ],
              "redrawOn": "data",
              "tableView": true,
              "applyMaskOn": "change",
              "customClass": "display-none",
              "defaultValue": "init",
              "validateWhenHidden": false
            },
            {
              "key": "esercizio",
              "type": "panel",
              "input": false,
              "label": "Panel",
              "title": "* Esercizio",
              "collapsed": true,
              "tableView": false,
              "components": [
                {
                  "key": "USO_DOMESTICO.tipo_uso",
                  "data": {
                    "custom": "values = [\r\n  { des_tipo_uso: 'domestico di acque superficiali', fk_uso_legge: 10, id_tipo_uso: 28}\r\n  ]"
                  },
                  "type": "select",
                  "input": true,
                  "label": "Uso effettivo",
                  "widget": "choicesjs",
                  "dataSrc": "custom",
                  "disabled": true,
                  "template": "<span>{{ item.des_tipo_uso }}</span>",
                  "tableView": true,
                  "defaultValue": {
                    "id_tipo_uso": 28,
                    "des_tipo_uso": "domestico di acque superficiali",
                    "fk_uso_legge": 10
                  },
                  "validateWhenHidden": false
                },
                {
                  "key": "USO_DOMESTICO.qta_risorsa_utilizzata",
                  "type": "editgrid",
                  "input": true,
                  "label": "<strong>Quantità di risorsa utilizzata</strong>",
                  "saveRow": "CONFERMA",
                  "removeRow": "ANNULLA",
                  "rowDrafts": false,
                  "tableView": false,
                  "templates": {
                    "row": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">\r\n        {{ isVisibleInRow(component) ? getView(component, row[component.key]) : ''}}\r\n      </div>\r\n    {% } %}\r\n  {% }) %}\r\n  {% if (!instance.options.readOnly && !instance.disabled) { %}\r\n    <div class=\"col\">\r\n      <div class=\"btn-group pull-left\">\r\n        <button class=\"btn btn-default btn-light btn-sm editRow\"><i class=\"{{ iconClass('edit') }}\"></i></button>\r\n        {% if (!instance.hasRemoveButtons || instance.hasRemoveButtons()) { %}\r\n          <button class=\"btn btn-danger btn-sm removeRow\"><i class=\"{{ iconClass('trash') }}\"></i></button>\r\n        {% } %}\r\n      </div>\r\n    </div>\r\n  {% } %}\r\n</div>",
                    "header": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">{{ t(component.label) }}</div>\r\n    {% } %}\r\n  {% }) %}\r\n  <div class=\"col\">Azioni</div>\r\n</div>\r\n"
                  },
                  "addAnother": "AGGIUNGI PERIODO",
                  "components": [
                    {
                      "key": "columnsEc2",
                      "type": "columns",
                      "input": false,
                      "label": "columns_1",
                      "columns": [
                        {
                          "pull": 0,
                          "push": 0,
                          "size": "md",
                          "width": 3,
                          "offset": 0,
                          "components": [
                            {
                              "key": "inizio_periodo",
                              "type": "textfield",
                              "input": true,
                              "label": "Dal (gg/mm)",
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
                                "custom": "const d = moment(input,'DD/MM');\n\nvalid = d.isValid() ? true : 'invalid';\n",
                                "pattern": "(^([0-2]{0,1}[0-9]|3[0,1])[/]([1-9]|0[1-9]|1[0-2])$)",
                                "required": true,
                                "customMessage": "Data non valida"
                              },
                              "tableView": true,
                              "validateOn": "blur",
                              "applyMaskOn": "change",
                              "validateWhenHidden": false,
                              "hideOnChildrenHidden": false
                            }
                          ],
                          "currentWidth": 3
                        },
                        {
                          "pull": 0,
                          "push": 0,
                          "size": "md",
                          "width": 3,
                          "offset": 0,
                          "components": [
                            {
                              "key": "fine_periodo",
                              "type": "textfield",
                              "input": true,
                              "label": "Al (gg/mm)",
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
                                "custom": "const d = moment(input,'DD/MM');\n\nvalid = d.isValid() ? true : 'invalid';\n",
                                "pattern": "(^([0-2]{0,1}[0-9]|3[0,1])[/]([1-9]|0[1-9]|1[0-2])$)",
                                "required": true,
                                "customMessage": "Data non valida"
                              },
                              "tableView": true,
                              "validateOn": "blur",
                              "applyMaskOn": "change",
                              "validateWhenHidden": false,
                              "hideOnChildrenHidden": false
                            }
                          ],
                          "currentWidth": 3
                        }
                      ],
                      "tableView": false
                    },
                    {
                      "key": "columns2",
                      "type": "columns",
                      "input": false,
                      "label": "Columns_2",
                      "columns": [
                        {
                          "pull": 0,
                          "push": 0,
                          "size": "md",
                          "width": 4,
                          "offset": 0,
                          "components": [
                            {
                              "label": "Portata massima (l/s)",
                              "applyMaskOn": "change",
                              "tableView": true,
                              "validate": {
                                "required": true,
                                "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                                "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                                "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                              },
                              "validateWhenHidden": false,
                              "key": "portata_max_utilizz",
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
                              "type": "textfield",
                              "input": true,
                              "hideOnChildrenHidden": false
                            }
                          ],
                          "currentWidth": 4
                        },
                        {
                          "pull": 0,
                          "push": 0,
                          "size": "md",
                          "width": 4,
                          "offset": 0,
                          "components": [
                            {
                              "label": "Portata media (l/s)",
                              "applyMaskOn": "change",
                              "tableView": true,
                              "validate": {
                                "required": true,
                                "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                                "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, inferiore o uguale alla portata massima, con un massimo di 7 cifre intere e 4 cifre decimali.",
                                "custom": "try {\r\n  // Definisco un oggetto con le informazioni da passare per la verifica\r\n  const params = {\r\n    portataMedia: input,\r\n    portataMassima: row.portata_max_utilizz,\r\n    checkZero: true,\r\n  };\r\n  // Recupero la funzione di comodo per la verifica\r\n  const qdrDERCheckPortataMedia = options.SCRIVA.formioQuadri.qdrDERCheckPortataMedia;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const check = qdrDERCheckPortataMedia(params);\r\n  // Verifico il risultato dell'elaborazione sul check\r\n  if (check) {\r\n    // Validazione completa\r\n    return true;\r\n    // #\r\n  } else {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"portataMediaInvalida\";\r\n  }\r\n} catch (e) {}"
                              },
                              "validateWhenHidden": false,
                              "key": "portata_med_utilizz",
                              "type": "textfield",
                              "input": true,
                              "hideOnChildrenHidden": false
                            }
                          ],
                          "currentWidth": 4
                        },
                        {
                          "size": "md",
                          "width": 4,
                          "components": [
                            {
                              "label": "Volume massimo (m<sup>3</sup>)",
                              "applyMaskOn": "change",
                              "tableView": true,
                              "validate": {
                                "required": true,
                                "pattern": "(^\\d{1,9}$)|(^\\d{1,9}[,]\\d{1,5}$)",
                                "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 9 cifre intere e 5 cifre decimali.",
                                "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                              },
                              "validateWhenHidden": false,
                              "key": "volume_max_utilizz",
                              "type": "textfield",
                              "input": true,
                              "hideOnChildrenHidden": false
                            }
                          ],
                          "currentWidth": 4
                        }
                      ],
                      "tableView": false
                    }
                  ],
                  "displayAsTable": false,
                  "validateWhenHidden": false
                },
                {
                  "key": "columns5",
                  "type": "columns",
                  "input": false,
                  "label": "Columns",
                  "columns": [
                    {
                      "pull": 0,
                      "push": 0,
                      "size": "md",
                      "width": 6,
                      "offset": 0,
                      "components": [
                        {
                          "label": "Portata media (l/s)",
                          "applyMaskOn": "change",
                          "tableView": true,
                          "validate": {
                            "required": true,
                            "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                            "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                            "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                          },
                          "validateWhenHidden": false,
                          "key": "USO_DOMESTICO.portata_media",
                          "type": "textfield",
                          "input": true
                        }
                      ],
                      "currentWidth": 6
                    },
                    {
                      "pull": 0,
                      "push": 0,
                      "size": "md",
                      "width": 6,
                      "offset": 0,
                      "components": [
                        {
                          "label": "Volume massimo annuo (m<sup>3</sup>)",
                          "applyMaskOn": "change",
                          "tableView": true,
                          "validate": {
                            "required": true,
                            "pattern": "(^\\d{1,11}$)|(^\\d{1,11}[,]\\d{1,2}$)",
                            "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 11 cifre intere e 2 cifre decimali.",
                            "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                          },
                          "validateWhenHidden": false,
                          "key": "USO_DOMESTICO.volume_max_annuo",
                          "type": "textfield",
                          "input": true
                        }
                      ],
                      "currentWidth": 6
                    }
                  ],
                  "tableView": false
                }
              ],
              "collapsible": true,
              "customConditional": "try {\r\n  // Recupero il valore della input modale\r\n  const inputUtente = data.USO_DOMESTICO.flgUsoDomestico;\r\n  const inputModale = data.JS_MODAL.USO_DOMESTICO.flgUsoDomestico;\r\n  // Se l'input ha un valore specifico o quello di init devo nascondere l'elemento\r\n  const caseOnInit = inputUtente === 'si' && inputModale === 'init';\r\n  const caseOnUse = inputModale === 'si';\r\n  // Definisco le condizioni di visualizzazione\r\n  show = caseOnInit || caseOnUse;\r\n} catch(e) {\r\n  show = true;\r\n}\r\n"
            }
          ],
          "collapsible": true
        },
        {
          "key": "usoPotabile",
          "type": "panel",
          "input": false,
          "label": "Panel",
          "title": "Uso potabile",
          "collapsed": true,
          "tableView": false,
          "components": [
            {
              "key": "USO_POTABILE.flgUsoPotabile",
              "type": "radio",
              "input": true,
              "label": "E' presente l'uso potabile?",
              "inline": true,
              "values": [
                {
                  "label": "Sì",
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
              "tableView": true,
              "defaultValue": "no",
              "validateWhenHidden": false,
              "optionsLabelPosition": "right"
            },
            {
              "key": "JS_MODAL.USO_POTABILE.flgUsoPotabile",
              "type": "textfield",
              "input": true,
              "label": "Conferma presenza uso potabile [NASCOSTO]",
              "logic": [
                {
                  "name": "JS_MODAL_TRIGGER.USO_POTABILE.flgUsoPotabile",
                  "actions": [
                    {
                      "name": "JS_MODAL_CONFERMA.USO_POTABILE.flgUsoPotabile",
                      "type": "customAction",
                      "customAction": "try {\n  const componenteTrigger = 'USO_POTABILE.flgUsoPotabile';\n  const componenteConferma = 'JS_MODAL.USO_POTABILE.flgUsoPotabile';\n  const formioModals = options.SCRIVA.formioModals;\n  const formioUpdate = options.SCRIVA.formioUpdate;\n  const formioUpdateComponent = options.SCRIVA.formioUpdateComponent;\n\n  const onConfirm = () => {\n    return formioUpdateComponent(data, componenteConferma, 'no');\n  }\n  const onCancel = () => {\n    return formioUpdateComponent(data, componenteTrigger, 'si');\n  }\n  \n  const config = {\n    title: 'Conferma',\n    codeOrBody: 'A064',\n    callbacks: {\n      onConfirm: () => {\n        data = onConfirm();\n        formioUpdate(data);\n      },\n      onCancel: () => {\n        data = onCancel();\n        formioUpdate(data);\n      },\n      onClose: () => {\n        data = onCancel();\n        formioUpdate(data);\n      }\n    }\n  }\n  \n  formioModals.conferma(config);\n  \n} catch (e) {}"
                    }
                  ],
                  "trigger": {
                    "type": "javascript",
                    "simple": {},
                    "javascript": "try {\n  const check1 = data.USO_POTABILE.flgUsoPotabile == 'no';\n  const check2 = data.JS_MODAL.USO_POTABILE.flgUsoPotabile == 'si';\n  result = check1 && check2;\n} catch (e) {\n  return false;\n}"
                  }
                },
                {
                  "name": "JS_MODAL_SET_TRIGGER.USO_POTABILE.flgUsoPotabile",
                  "actions": [
                    {
                      "name": "JS_MODAL_SET_TRIGGER_SI.USO_POTABILE.flgUsoPotabile",
                      "type": "value",
                      "value": "value = 'si';"
                    }
                  ],
                  "trigger": {
                    "type": "simple",
                    "simple": {
                      "eq": "si",
                      "show": true,
                      "when": "USO_POTABILE.flgUsoPotabile"
                    }
                  }
                }
              ],
              "redrawOn": "data",
              "tableView": true,
              "applyMaskOn": "change",
              "customClass": "display-none",
              "defaultValue": "init",
              "validateWhenHidden": false
            },
            {
              "key": "esercizio",
              "type": "panel",
              "input": false,
              "label": "Panel",
              "title": "* Esercizio",
              "collapsed": true,
              "tableView": false,
              "components": [
                {
                  "key": "USO_POTABILE.tipo_uso",
                  "data": {
                    "custom": "values = [\r\n  { des_tipo_uso: 'approvvigionamento idrico alle persone', fk_uso_legge: 4, id_tipo_uso: 11}\r\n  ]"
                  },
                  "type": "select",
                  "input": true,
                  "label": "Uso effettivo",
                  "widget": "choicesjs",
                  "dataSrc": "custom",
                  "disabled": true,
                  "template": "<span>{{ item.des_tipo_uso }}</span>",
                  "tableView": true,
                  "defaultValue": {
                    "id_tipo_uso": 11,
                    "des_tipo_uso": "approvvigionamento idrico alle persone",
                    "fk_uso_legge": 4
                  },
                  "validateWhenHidden": false
                },
                {
                  "key": "USO_POTABILE.qta_risorsa_utilizzata",
                  "type": "editgrid",
                  "input": true,
                  "label": "<strong>Quantità di risorsa utilizzata</strong>",
                  "saveRow": "CONFERMA",
                  "removeRow": "ANNULLA",
                  "rowDrafts": false,
                  "tableView": false,
                  "templates": {
                    "row": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">\r\n        {{ isVisibleInRow(component) ? getView(component, row[component.key]) : ''}}\r\n      </div>\r\n    {% } %}\r\n  {% }) %}\r\n  {% if (!instance.options.readOnly && !instance.disabled) { %}\r\n    <div class=\"col\">\r\n      <div class=\"btn-group pull-left\">\r\n        <button class=\"btn btn-default btn-light btn-sm editRow\"><i class=\"{{ iconClass('edit') }}\"></i></button>\r\n        {% if (!instance.hasRemoveButtons || instance.hasRemoveButtons()) { %}\r\n          <button class=\"btn btn-danger btn-sm removeRow\"><i class=\"{{ iconClass('trash') }}\"></i></button>\r\n        {% } %}\r\n      </div>\r\n    </div>\r\n  {% } %}\r\n</div>",
                    "header": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">{{ t(component.label) }}</div>\r\n    {% } %}\r\n  {% }) %}\r\n  <div class=\"col\">Azioni</div>\r\n</div>\r\n"
                  },
                  "addAnother": "AGGIUNGI PERIODO",
                  "components": [
                    {
                      "key": "columnsEc2",
                      "type": "columns",
                      "input": false,
                      "label": "columns_1",
                      "columns": [
                        {
                          "pull": 0,
                          "push": 0,
                          "size": "md",
                          "width": 6,
                          "offset": 0,
                          "components": [
                            {
                              "label": "N. abitanti serviti",
                              "applyMaskOn": "change",
                              "tableView": true,
                              "validate": {
                                "required": true,
                                "pattern": "(^\\d{1,7}$)",
                                "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere.",
                                "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                              },
                              "validateWhenHidden": false,
                              "key": "abitanti_equiv",
                              "type": "textfield",
                              "input": true
                            }
                          ],
                          "currentWidth": 6
                        },
                        {
                          "pull": 0,
                          "push": 0,
                          "size": "md",
                          "width": 3,
                          "offset": 0,
                          "components": [
                            {
                              "key": "inizio_periodo",
                              "type": "textfield",
                              "input": true,
                              "label": "Dal (gg/mm)",
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
                                "custom": "const d = moment(input,'DD/MM');\n\nvalid = d.isValid() ? true : 'invalid';\n",
                                "pattern": "(^([0-2]{0,1}[0-9]|3[0,1])[/]([1-9]|0[1-9]|1[0-2])$)",
                                "required": true,
                                "customMessage": "Data non valida"
                              },
                              "tableView": true,
                              "validateOn": "blur",
                              "applyMaskOn": "change",
                              "validateWhenHidden": false,
                              "hideOnChildrenHidden": false
                            }
                          ],
                          "currentWidth": 3
                        },
                        {
                          "size": "md",
                          "width": 3,
                          "components": [
                            {
                              "key": "fine_periodo",
                              "type": "textfield",
                              "input": true,
                              "label": "Al (gg/mm)",
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
                                "custom": "const d = moment(input,'DD/MM');\n\nvalid = d.isValid() ? true : 'invalid';\n",
                                "pattern": "(^([0-2]{0,1}[0-9]|3[0,1])[/]([1-9]|0[1-9]|1[0-2])$)",
                                "required": true,
                                "customMessage": "Data non valida"
                              },
                              "tableView": true,
                              "validateOn": "blur",
                              "applyMaskOn": "change",
                              "validateWhenHidden": false,
                              "hideOnChildrenHidden": false
                            }
                          ],
                          "currentWidth": 3
                        }
                      ],
                      "tableView": false
                    },
                    {
                      "key": "columns2",
                      "type": "columns",
                      "input": false,
                      "label": "Columns_2",
                      "columns": [
                        {
                          "pull": 0,
                          "push": 0,
                          "size": "md",
                          "width": 4,
                          "offset": 0,
                          "components": [
                            {
                              "label": "Portata massima (l/s)",
                              "applyMaskOn": "change",
                              "tableView": true,
                              "validate": {
                                "required": true,
                                "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                                "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                                "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                              },
                              "validateWhenHidden": false,
                              "key": "portata_max_utilizz",
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
                              "type": "textfield",
                              "input": true,
                              "hideOnChildrenHidden": false
                            }
                          ],
                          "currentWidth": 4
                        },
                        {
                          "pull": 0,
                          "push": 0,
                          "size": "md",
                          "width": 4,
                          "offset": 0,
                          "components": [
                            {
                              "label": "Portata media (l/s)",
                              "applyMaskOn": "change",
                              "tableView": true,
                              "validate": {
                                "required": true,
                                "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                                "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, inferiore o uguale alla portata massima, con un massimo di 7 cifre intere e 4 cifre decimali.",
                                "custom": "try {\r\n  // Definisco un oggetto con le informazioni da passare per la verifica\r\n  const params = {\r\n    portataMedia: input,\r\n    portataMassima: row.portata_max_utilizz,\r\n    checkZero: true,\r\n  };\r\n  // Recupero la funzione di comodo per la verifica\r\n  const qdrDERCheckPortataMedia = options.SCRIVA.formioQuadri.qdrDERCheckPortataMedia;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const check = qdrDERCheckPortataMedia(params);\r\n  // Verifico il risultato dell'elaborazione sul check\r\n  if (check) {\r\n    // Validazione completa\r\n    return true;\r\n    // #\r\n  } else {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"portataMediaInvalida\";\r\n  }\r\n} catch (e) {}"
                              },
                              "validateWhenHidden": false,
                              "key": "portata_med_utilizz",
                              "type": "textfield",
                              "input": true,
                              "hideOnChildrenHidden": false
                            }
                          ],
                          "currentWidth": 4
                        },
                        {
                          "size": "md",
                          "width": 4,
                          "components": [
                            {
                              "label": "Volume massimo (m<sup>3</sup>)",
                              "applyMaskOn": "change",
                              "tableView": true,
                              "validate": {
                                "required": true,
                                "pattern": "(^\\d{1,9}$)|(^\\d{1,9}[,]\\d{1,5}$)",
                                "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 9 cifre intere e 5 cifre decimali.",
                                "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                              },
                              "validateWhenHidden": false,
                              "key": "volume_max_utilizz",
                              "type": "textfield",
                              "input": true,
                              "hideOnChildrenHidden": false
                            }
                          ],
                          "currentWidth": 4
                        }
                      ],
                      "tableView": false
                    }
                  ],
                  "displayAsTable": false,
                  "validateWhenHidden": false
                },
                {
                  "key": "columns5",
                  "type": "columns",
                  "input": false,
                  "label": "Columns",
                  "columns": [
                    {
                      "pull": 0,
                      "push": 0,
                      "size": "md",
                      "width": 6,
                      "offset": 0,
                      "components": [
                        {
                          "label": "Portata media (l/s)",
                          "applyMaskOn": "change",
                          "tableView": true,
                          "validate": {
                            "required": true,
                            "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                            "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                            "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                          },
                          "validateWhenHidden": false,
                          "key": "USO_POTABILE.portata_media",
                          "type": "textfield",
                          "input": true
                        }
                      ],
                      "currentWidth": 6
                    },
                    {
                      "pull": 0,
                      "push": 0,
                      "size": "md",
                      "width": 6,
                      "offset": 0,
                      "components": [
                        {
                          "label": "Volume massimo annuo (m<sup>3</sup>)",
                          "applyMaskOn": "change",
                          "tableView": true,
                          "validate": {
                            "required": true,
                            "pattern": "(^\\d{1,11}$)|(^\\d{1,11}[,]\\d{1,2}$)",
                            "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 11 cifre intere e 2 cifre decimali.",
                            "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                          },
                          "validateWhenHidden": false,
                          "key": "USO_POTABILE.volume_max_annuo",
                          "type": "textfield",
                          "input": true
                        }
                      ],
                      "currentWidth": 6
                    }
                  ],
                  "tableView": false
                }
              ],
              "collapsible": true,
              "customConditional": "try {\r\n  // Recupero il valore della input modale\r\n  const inputUtente = data.USO_POTABILE.flgUsoPotabile;\r\n  const inputModale = data.JS_MODAL.USO_POTABILE.flgUsoPotabile;\r\n  // Se l'input ha un valore specifico o quello di init devo nascondere l'elemento\r\n  const caseOnInit = inputUtente === 'si' && inputModale === 'init';\r\n  const caseOnUse = inputModale === 'si';\r\n  // Definisco le condizioni di visualizzazione\r\n  show = caseOnInit || caseOnUse;\r\n} catch(e) {\r\n  show = true;\r\n}\r\n"
            }
          ],
          "collapsible": true
        }
      ]
    }
};

