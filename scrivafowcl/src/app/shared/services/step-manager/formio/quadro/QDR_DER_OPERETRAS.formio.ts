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
export const QDR_DER_OPERATRASP_DEBUG = {
    "label": "Opere di Trasporto",
    "componentName": "OpereTrasportoComponent",
    "CONDOTTA_FORZATA": {
      "display": "form",
      "components": [
        {
          "key": "id_oggetto_istanza",
          "type": "hidden",
          "input": true,
          "label": "idOggettoIstanza - oggettoIstanza.id_oggetto_istanza",
          "tableView": true,
          "customDefaultValue": "value = options.SCRIVA.oggettoIstanza.id_oggetto_istanza;"
        },
        {
          "key": "id_oggetto",
          "type": "hidden",
          "input": true,
          "label": "idOpera/idOggetto - oggetto.id_oggetto",
          "tableView": true,
          "customDefaultValue": "value = options.SCRIVA.oggetto.id_oggetto;"
        },
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
          "content": "<strong>Condotta forzata {{options.SCRIVA.oggetto.cod_scriva}}</strong>",
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
          "key": "DATI_IDENTIFICATIVI",
          "type": "panel",
          "input": false,
          "label": "Dati identificativi",
          "title": "<strong>* Dati identificativi</strong>",
          "tableView": false,
          "components": [
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
                  "width": 4,
                  "offset": 0,
                  "components": [
                    {
                      "key": "DATI_IDENTIFICATIVI.dati_identificativi.codice_rilievo",
                      "type": "textfield",
                      "input": true,
                      "label": "Codice rilievo",
                      "disabled": true,
                      "validate": {
                        "maxLength": 14
                      },
                      "tableView": true,
                      "applyMaskOn": "change",
                      "validateWhenHidden": false
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
                  "components": [],
                  "currentWidth": 4
                },
                {
                  "size": "md",
                  "width": 4,
                  "components": [],
                  "currentWidth": 4
                }
              ],
              "tableView": false
            },
            {
              "key": "DATI_IDENTIFICATIVI.dati_identificativi.denominazione",
              "type": "textfield",
              "input": true,
              "label": "Denominazione",
              "validate": {
                "maxLength": 200,
                "customMessage": "Il campo può contenere un massimo di 200 caratteri"
              },
              "tableView": true,
              "applyMaskOn": "change",
              "customDefaultValue": "value = options.SCRIVA.oggettoIstanza.den_oggetto;",
              "validateWhenHidden": false,
              "hideOnChildrenHidden": false
            },
            {
              "key": "DATI_IDENTIFICATIVI.dati_identificativi.tipo_condotta_forz",
              "type": "radio",
              "input": true,
              "label": "Tipologia della condotta",
              "inline": false,
              "values": [
                {
                  "label": "uso energetico",
                  "value": "1",
                  "shortcut": ""
                },
                {
                  "label": "uso energetico e riqualificazione dell’energia",
                  "value": "2",
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
          "collapsible": false
        },
        {
          "key": "ESERCIZIO",
          "type": "panel",
          "input": false,
          "label": "Dati tecnici",
          "title": "<strong>Esercizio</strong>",
          "tableView": false,
          "components": [
            {
              "label": "<strong>Periodi di uso dell'acqua</strong>",
              "tableView": false,
              "templates": {
                "header": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">{{ t(component.label) }}</div>\r\n    {% } %}\r\n  {% }) %}\r\n  <div class=\"col\">Azioni</div>\r\n</div>\r\n",
                "row": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">\r\n        {{ isVisibleInRow(component) ? getView(component, row[component.key]) : ''}}\r\n      </div>\r\n    {% } %}\r\n  {% }) %}\r\n  {% if (!instance.options.readOnly && !instance.disabled) { %}\r\n    <div class=\"col\">\r\n      <div class=\"btn-group pull-left\">\r\n        <button class=\"btn btn-default btn-light btn-sm editRow\"><i class=\"{{ iconClass('edit') }}\"></i></button>\r\n        {% if (!instance.hasRemoveButtons || instance.hasRemoveButtons()) { %}\r\n          <button class=\"btn btn-danger btn-sm removeRow\"><i class=\"{{ iconClass('trash') }}\"></i></button>\r\n        {% } %}\r\n      </div>\r\n    </div>\r\n  {% } %}\r\n</div>"
              },
              "addAnother": "AGGIUNGI PERIODO",
              "saveRow": "CONFERMA",
              "removeRow": "ANNULLA",
              "validateWhenHidden": false,
              "rowDrafts": false,
              "key": "ESERCIZIO.periodiDiUsoDellacqua",
              "type": "editgrid",
              "displayAsTable": false,
              "input": true,
              "components": [
                {
                  "key": "columnsEc2",
                  "type": "columns",
                  "input": false,
                  "label": "columns_ec_2",
                  "columns": [
                    {
                      "pull": 0,
                      "push": 0,
                      "size": "md",
                      "width": 2,
                      "offset": 0,
                      "components": [
                        {
                          "key": "periodo_da",
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
                            "custom": "try {\n  // Uso la funzione di utility dalle options\n  const dateFormat = 'DD/MM';\n  const allow29thFeb = false;\n  const checkDateFormat = options.SCRIVA.utilities.checkDateFormat;\n  return checkDateFormat(input, dateFormat, allow29thFeb) ? true : 'invalidDate';\n} catch (e) {\n  // Errore, default\n  return true;\n}",
                            "pattern": "(^([0-2]{0,1}[0-9]|3[0,1])[/]([1-9]|0[1-9]|1[0-2])$)",
                            "required": true,
                            "customMessage": "Inserire nel formato giorno/mese"
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
                      "pull": 0,
                      "push": 0,
                      "size": "md",
                      "width": 2,
                      "offset": 0,
                      "components": [
                        {
                          "key": "periodo_a",
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
                            "custom": "try {\n  // Uso la funzione di utility dalle options\n  const dateFormat = 'DD/MM';\n  const allow29thFeb = false;\n  const checkDateFormat = options.SCRIVA.utilities.checkDateFormat;\n  return checkDateFormat(input, dateFormat, allow29thFeb) ? true : 'invalidDate';\n} catch (e) {\n  // Errore, default\n  return true;\n}",
                            "pattern": "(^([0-2]{0,1}[0-9]|3[0,1])[/]([1-9]|0[1-9]|1[0-2])$)",
                            "required": true,
                            "customMessage": "Inserire nel formato giorno/mese"
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
                      "width": 4,
                      "components": [
                        {
                          "label": "Portata minima (l/s)",
                          "applyMaskOn": "change",
                          "tableView": true,
                          "validate": {
                            "required": true,
                            "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                            "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                            "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}\r\n"
                          },
                          "validateWhenHidden": false,
                          "key": "portata_minima",
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
                      "size": "md",
                      "width": 4,
                      "components": [
                        {
                          "label": "Portata media (l/s)",
                          "applyMaskOn": "change",
                          "tableView": true,
                          "validate": {
                            "required": true,
                            "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                            "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                            "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}\r\n"
                          },
                          "validateWhenHidden": false,
                          "key": "portata_media",
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
                    }
                  ],
                  "tableView": false
                },
                {
                  "key": "columnsRiga2",
                  "type": "columns",
                  "input": false,
                  "label": "Columns_riga2",
                  "columns": [
                    {
                      "pull": 0,
                      "push": 0,
                      "size": "md",
                      "width": 6,
                      "offset": 0,
                      "components": [
                        {
                          "label": "Salto fiscale (m)",
                          "applyMaskOn": "change",
                          "tableView": true,
                          "validate": {
                            "required": true,
                            "pattern": "(^\\d{1,3}$)",
                            "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 3 cifre intere.",
                            "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}\r\n"
                          },
                          "validateWhenHidden": false,
                          "key": "salto_fiscale",
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
                          "label": "Potenza nominale media annua (kW)",
                          "applyMaskOn": "change",
                          "tableView": true,
                          "validate": {
                            "required": true,
                            "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                            "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                            "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                          },
                          "validateWhenHidden": false,
                          "key": "potenza_nma",
                          "type": "textfield",
                          "input": true
                        }
                      ],
                      "currentWidth": 6
                    }
                  ],
                  "tableView": false
                },
                {
                  "key": "columnsRiga3",
                  "type": "columns",
                  "input": false,
                  "label": "Columns_riga3",
                  "columns": [
                    {
                      "pull": 0,
                      "push": 0,
                      "size": "md",
                      "width": 6,
                      "offset": 0,
                      "components": [
                        {
                          "label": "Producibilità media annua (GWh)",
                          "applyMaskOn": "change",
                          "tableView": true,
                          "validate": {
                            "required": true,
                            "pattern": "(^\\d{1,12}$)|(^\\d{1,12}[,]\\d{1,2}$)",
                            "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 12 cifre intere e 2 cifre decimali.",
                            "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                          },
                          "validateWhenHidden": false,
                          "key": "producibilita_ma",
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
                          "label": "Potenza installata complessiva (kW)",
                          "applyMaskOn": "change",
                          "tableView": true,
                          "validate": {
                            "required": true,
                            "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,3}$)",
                            "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 3 cifre decimali.i",
                            "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                          },
                          "validateWhenHidden": false,
                          "key": "potenza_compl",
                          "type": "textfield",
                          "input": true
                        }
                      ],
                      "currentWidth": 6
                    }
                  ],
                  "tableView": false
                }
              ]
            },
            {
              "key": "ESERCIZIO.pompaggio",
              "type": "container",
              "input": true,
              "label": "POMPAGGIO",
              "tableView": false,
              "components": [
                {
                  "key": "title_pompaggio",
                  "type": "htmlelement",
                  "attrs": [
                    {
                      "attr": "",
                      "value": ""
                    }
                  ],
                  "input": false,
                  "label": "title_pompaggio",
                  "content": "<strong>Pompaggio</strong>",
                  "tableView": false,
                  "refreshOnChange": false
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
                          "key": "portata_max_pomp",
                          "type": "textfield",
                          "input": true,
                          "label": "Portata massima di pompaggio (l/s)",
                          "validate": {
                            "pattern": "(^\\d{1,6}$)|(^\\d{1,6}[,]\\d{1,6}$)",
                            "customMessage": "Indicare un massimo di 6 cifre intere e 6 cifre decimali"
                          },
                          "tableView": true,
                          "applyMaskOn": "change",
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
                          "label": "Portata media di pompaggio (l/s)",
                          "applyMaskOn": "change",
                          "tableView": true,
                          "validate": {
                            "pattern": "(^\\d{1,6}$)|(^\\d{1,6}[,]\\d{1,6}$)",
                            "customMessage": "Indicare un valore inferiore alla portata massima, utilizzando un massimo di 6 cifre intere e 6 cifre decimali",
                            "custom": "try {\r\n  // Definisco un oggetto con le informazioni da passare per la verifica\r\n  const params = {\r\n    portataMedia: input,\r\n    portataMassima: data.ESERCIZIO.pompaggio.portata_max_pomp,\r\n    checkZero: false,\r\n  };\r\n  // Recupero la funzione di comodo per la verifica\r\n  const qdrDERCheckPortataMedia = options.SCRIVA.formioQuadri.qdrDERCheckPortataMedia;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const check = qdrDERCheckPortataMedia(params);\r\n  // Verifico il risultato dell'elaborazione sul check\r\n  if (check) {\r\n    // Validazione completa\r\n    return true;\r\n    // #\r\n  } else {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"portataMediaInvalida\";\r\n  }\r\n} catch (e) {}"
                          },
                          "validateWhenHidden": false,
                          "key": "portata_media_pomp",
                          "type": "textfield",
                          "input": true
                        }
                      ],
                      "currentWidth": 6
                    }
                  ],
                  "tableView": false
                },
                {
                  "key": "columns5",
                  "type": "columns",
                  "input": false,
                  "label": "Columns_2",
                  "columns": [
                    {
                      "pull": 0,
                      "push": 0,
                      "size": "md",
                      "width": 6,
                      "offset": 0,
                      "components": [
                        {
                          "key": "potenza_npomp",
                          "type": "textfield",
                          "input": true,
                          "label": "Potenza nominale di pompaggio (kW)",
                          "validate": {
                            "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,3}$)",
                            "customMessage": "Indicare un massimo di 7 cifre intere e 3 cifre decimali"
                          },
                          "tableView": true,
                          "applyMaskOn": "change",
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
              "conditional": {
                "eq": "2",
                "show": true,
                "when": "DATI_IDENTIFICATIVI.dati_identificativi.tipo_condotta_forz"
              },
              "validateWhenHidden": false
            }
          ],
          "collapsible": false
        },
        {
          "key": "DATI_INFRASTRUTTURALI",
          "type": "panel",
          "input": false,
          "label": "Panel",
          "title": "<strong>Dati infrastrutturali</strong>",
          "tableView": false,
          "components": [
            {
              "key": "columns4",
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
                      "key": "DATI_INFRASTRUTTURALI.dati_infrastrutturali.lunghezza",
                      "type": "textfield",
                      "input": true,
                      "label": "Lunghezza (m)",
                      "validate": {
                        "pattern": "(^\\d{1,6}$)|(^\\d{1,6}[,]\\d{1,3}$)",
                        "customMessage": "Indicare un massimo di 6 cifre intere e 3 cifre decimali"
                      },
                      "tableView": true,
                      "applyMaskOn": "change",
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
                      "key": "DATI_INFRASTRUTTURALI.dati_infrastrutturali.diametro",
                      "type": "textfield",
                      "input": true,
                      "label": "Diametro (m)",
                      "validate": {
                        "pattern": "(^\\d{1,2}$)|(^\\d{1,2}[,]\\d{1,2}$)",
                        "customMessage": "Indicare un massimo di 2 cifre intere e 2 cifre decimali"
                      },
                      "tableView": true,
                      "applyMaskOn": "change",
                      "validateWhenHidden": false
                    }
                  ],
                  "currentWidth": 6
                }
              ],
              "tableView": false
            }
          ],
          "collapsible": false
        },
        {
          "key": "TURBINE",
          "type": "panel",
          "input": false,
          "label": "Panel",
          "title": "<strong>Turbine</strong>",
          "tableView": false,
          "components": [
            {
              "key": "TURBINE.turbine",
              "type": "editgrid",
              "input": true,
              "label": "Turbine",
              "saveRow": "CONFERMA",
              "removeRow": "ANNULLA",
              "rowDrafts": false,
              "tableView": false,
              "templates": {
                "row": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">\r\n        {{ isVisibleInRow(component) ? getView(component, row[component.key]) : ''}}\r\n      </div>\r\n    {% } %}\r\n  {% }) %}\r\n  {% if (!instance.options.readOnly && !instance.disabled) { %}\r\n    <div class=\"col\">\r\n      <div class=\"btn-group pull-left\">\r\n        <button class=\"btn btn-default btn-light btn-sm editRow\"><i class=\"{{ iconClass('edit') }}\"></i></button>\r\n        {% if (!instance.hasRemoveButtons || instance.hasRemoveButtons()) { %}\r\n          <button class=\"btn btn-danger btn-sm removeRow\"><i class=\"{{ iconClass('trash') }}\"></i></button>\r\n        {% } %}\r\n      </div>\r\n    </div>\r\n  {% } %}\r\n</div>\r\n",
                "header": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">{{ t(component.label) }}</div>\r\n    {% } %}\r\n  {% }) %}\r\n  <div class=\"col\">Azioni</div>\r\n</div>\r\n"
              },
              "addAnother": "AGGIUNGI RIGA",
              "components": [
                {
                  "key": "columns",
                  "type": "columns",
                  "input": false,
                  "label": "Columns_1",
                  "columns": [
                    {
                      "pull": 0,
                      "push": 0,
                      "size": "md",
                      "width": 4,
                      "offset": 0,
                      "components": [
                        {
                          "label": "Turbine (n.)",
                          "applyMaskOn": "change",
                          "tableView": true,
                          "validate": {
                            "required": true,
                            "pattern": "(^\\d{1,2}$)",
                            "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 2 cifre intere.",
                            "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                          },
                          "validateWhenHidden": false,
                          "key": "num_turbine",
                          "type": "textfield",
                          "input": true
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
                          "key": "tipoTurbina",
                          "data": {
                            "custom": "values = [\r\n  { des_tipo_turbina: 'Pelton', id_tipo_turbina: 1 },\r\n  { des_tipo_turbina: 'Turbo', id_tipo_turbina: 2 },\r\n  { des_tipo_turbina: 'Cross-flow', id_tipo_turbina: 3 },\r\n  { des_tipo_turbina: 'Francis', id_tipo_turbina: 4 },\r\n  { des_tipo_turbina: 'Kaplan a doppia regolazione', id_tipo_turbina: 5 },\r\n  { des_tipo_turbina: 'Kaplan a singola regolazione', id_tipo_turbina: 6 },\r\n  { des_tipo_turbina: 'Kaplan ad elica', id_tipo_turbina: 7 },\r\n  { des_tipo_turbina: 'Altra tipologia', id_tipo_turbina: 8 },\r\n]"
                          },
                          "type": "select",
                          "input": true,
                          "label": "Tipo turbina",
                          "widget": "choicesjs",
                          "dataSrc": "custom",
                          "template": "<span>{{ item.des_tipo_turbina }}</span>",
                          "validate": {
                            "required": true
                          },
                          "tableView": true,
                          "validateWhenHidden": false
                        }
                      ],
                      "currentWidth": 4
                    },
                    {
                      "size": "md",
                      "width": 4,
                      "components": [
                        {
                          "key": "potenza_inst",
                          "type": "textfield",
                          "input": true,
                          "label": "Potenza installata (kW)",
                          "validate": {
                            "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,3}$)",
                            "customMessage": "Indicare un massimo di 7 cifre intere e 3 cifre decimali"
                          },
                          "tableView": true,
                          "applyMaskOn": "change",
                          "validateWhenHidden": false
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
            }
          ],
          "collapsible": false
        }
      ]
    }
}