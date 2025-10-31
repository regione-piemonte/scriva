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
export const QDR_DER_RESTITUZIONE_DEBUG = {
    "label": "Restituzione",
    "RESTITUZIONE": {
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
          "content": "<strong>Restituzione {{options.SCRIVA.oggetto.cod_scriva}}</strong>",
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
          "collapsed": true,
          "tableView": false,
          "components": [
            {
              "key": "title_di_1",
              "type": "htmlelement",
              "input": false,
              "label": "title_di_1",
              "content": "<strong>Dati identificativi</strong>",
              "tableView": false,
              "refreshOnChange": false
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
              "key": "columns_di_1",
              "type": "columns",
              "input": false,
              "label": "columns_di_1",
              "columns": [
                {
                  "pull": 0,
                  "push": 0,
                  "size": "md",
                  "width": 6,
                  "offset": 0,
                  "components": [
                    {
                      "key": "DATI_IDENTIFICATIVI.dati_identificativi.denomin_cidrsup",
                      "type": "textfield",
                      "input": true,
                      "label": "Denominazione corpo idrico",
                      "validate": {
                        "maxLength": 100,
                        "customMessage": "Il campo può contenere un massimo di 100 caratteri"
                      },
                      "tableView": true,
                      "applyMaskOn": "change",
                      "customDefaultValue": "value = options.SCRIVA.oggettoIstanza.den_oggetto;",
                      "validateWhenHidden": false,
                      "hideOnChildrenHidden": false
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
                      "key": "DATI_IDENTIFICATIVI.dati_identificativi.denomin_canarest",
                      "type": "textfield",
                      "input": true,
                      "label": "Denominazione canale di restituzione",
                      "validate": {
                        "maxLength": 100,
                        "customMessage": "Il campo può contenere un massimo di 100 caratteri"
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
            },
            {
              "key": "title_di_2",
              "type": "htmlelement",
              "attrs": [
                {
                  "attr": "",
                  "value": ""
                }
              ],
              "input": false,
              "label": "title_di_2",
              "content": "<strong>Localizzazione</strong>",
              "tableView": false,
              "refreshOnChange": false
            },
            {
              "key": "columns_di_2",
              "type": "columns",
              "input": false,
              "label": "columns_di_2",
              "columns": [
                {
                  "pull": 0,
                  "push": 0,
                  "size": "md",
                  "width": 4,
                  "offset": 0,
                  "components": [
                    {
                      "key": "JS_READ_ONLY.DATI_IDENTIFICATIVI.comune",
                      "type": "textfield",
                      "input": true,
                      "label": "Comune",
                      "disabled": true,
                      "validate": {
                        "required": true
                      },
                      "tableView": false,
                      "applyMaskOn": "change",
                      "customDefaultValue": "value = options.SCRIVA.oggettoIstanza.ubicazione_oggetto[0].comune.denom_comune;",
                      "validateWhenHidden": false,
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
                      "key": "DATI_IDENTIFICATIVI.localizzazione.localita",
                      "type": "textfield",
                      "input": true,
                      "label": "Località",
                      "validate": {
                        "maxLength": 100,
                        "customMessage": "Il campo può contenere un massimo di 100 caratteri"
                      },
                      "tableView": true,
                      "applyMaskOn": "change",
                      "customDefaultValue": "value = options.SCRIVA.oggettoIstanza.ubicazione_oggetto[0].des_localita;",
                      "validateWhenHidden": false,
                      "hideOnChildrenHidden": false
                    }
                  ],
                  "currentWidth": 4
                }
              ],
              "tableView": false
            },
            {
              "key": "title_di_3",
              "type": "htmlelement",
              "input": false,
              "label": "title_di_3",
              "content": "Dati catastali:",
              "tableView": false,
              "refreshOnChange": false
            },
            {
              "key": "columns_di_3",
              "type": "columns",
              "input": false,
              "label": "columns_di_3",
              "columns": [
                {
                  "pull": 0,
                  "push": 0,
                  "size": "md",
                  "width": 3,
                  "offset": 0,
                  "components": [
                    {
                      "key": "JS_READ_ONLY.DATI_IDENTIFICATIVI.cod_catastale",
                      "type": "textfield",
                      "input": true,
                      "label": "Cod. Catastale",
                      "disabled": true,
                      "tableView": true,
                      "applyMaskOn": "change",
                      "customDefaultValue": "let v = null;\n\ntry {\n  const datoCatastale = options.SCRIVA.oggettoIstanza.ubicazione_oggetto[0].dati_catastali[0];\n  v = datoCatastale.cod_belfiore  \n} catch (e) {}\n\nvalue = v;",
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
                      "key": "JS_READ_ONLY.DATI_IDENTIFICATIVI.sezione",
                      "type": "textfield",
                      "input": true,
                      "label": "Sezione",
                      "disabled": true,
                      "tableView": true,
                      "applyMaskOn": "change",
                      "customDefaultValue": "let v = null;\n\ntry {\n  const datoCatastale = options.SCRIVA.oggettoIstanza.ubicazione_oggetto[0].dati_catastali[0];\n  v = datoCatastale.sezione  \n} catch (e) {}\n\nvalue = v;",
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
                      "key": "JS_READ_ONLY.DATI_IDENTIFICATIVI.foglio",
                      "type": "textfield",
                      "input": true,
                      "label": "Foglio",
                      "disabled": true,
                      "tableView": true,
                      "applyMaskOn": "change",
                      "customDefaultValue": "let v = null;\n\ntry {\n  const datoCatastale = options.SCRIVA.oggettoIstanza.ubicazione_oggetto[0].dati_catastali[0];\n  v = datoCatastale.foglio  \n} catch (e) {}\n\nvalue = v;",
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
                      "key": "JS_READ_ONLY.DATI_IDENTIFICATIVI.particella",
                      "type": "textfield",
                      "input": true,
                      "label": "Particella",
                      "disabled": true,
                      "tableView": true,
                      "applyMaskOn": "change",
                      "customDefaultValue": "let v = null;\n\ntry {\n  const datoCatastale = options.SCRIVA.oggettoIstanza.ubicazione_oggetto[0].dati_catastali[0];\n  v = datoCatastale.particella;\n} catch(e){}\n\nvalue = v;",
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
              "key": "title_di_4",
              "type": "htmlelement",
              "input": false,
              "label": "title_di_4",
              "content": "<strong>Altri elementi identificativi</strong>",
              "tableView": false,
              "refreshOnChange": false
            },
            {
              "key": "columns_di_4",
              "type": "columns",
              "input": false,
              "label": "columns_di_4",
              "columns": [
                {
                  "pull": 0,
                  "push": 0,
                  "size": "md",
                  "width": 4,
                  "offset": 0,
                  "components": [
                    {
                      "key": "DATI_IDENTIFICATIVI.altri_elementi_identificativi.sponda",
                      "data": {
                        "custom": "values = [\n  { id_sponda: 1, des_sponda: 'Destra' },\n  { id_sponda: 2, des_sponda: 'Sinistra' },\n]"
                      },
                      "type": "select",
                      "input": true,
                      "label": "Sponda su cui insiste l'opera",
                      "widget": "choicesjs",
                      "dataSrc": "custom",
                      "template": "<span>{{ item.des_sponda }}</span>",
                      "validate": {
                        "required": true,
                        "customMessage": "Campo obbligatorio"
                      },
                      "tableView": true,
                      "placeholder": "Seleziona",
                      "searchEnabled": false,
                      "validateWhenHidden": false,
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
                      "key": "DATI_IDENTIFICATIVI.altri_elementi_identificativi.progr_asta",
                      "type": "textfield",
                      "input": true,
                      "label": "Progressiva sull’asta (km)",
                      "logic": [
                        {
                          "name": "verificaComponenteAppFO",
                          "actions": [
                            {
                              "name": "disabilitaPerFO",
                              "type": "property",
                              "state": true,
                              "property": {
                                "type": "boolean",
                                "label": "Disabled",
                                "value": "disabled"
                              }
                            }
                          ],
                          "trigger": {
                            "type": "javascript",
                            "javascript": "/* result = options.SCRIVA.isFrontOffice === true; */ return false;"
                          }
                        },
                        {
                          "name": "verificaComponenteAppBO",
                          "actions": [
                            {
                              "name": "obbligatorioPerBO",
                              "type": "property",
                              "state": true,
                              "property": {
                                "type": "boolean",
                                "label": "Required",
                                "value": "validate.required"
                              }
                            }
                          ],
                          "trigger": {
                            "type": "javascript",
                            "javascript": "/*result = options.SCRIVA.isBackOffice === true;*/ return false;"
                          }
                        }
                      ],
                      "validate": {
                        "pattern": "(^\\d{1,3}$)|(^\\d{1,3}[,]\\d{1,2}$)",
                        "customMessage": "Indicare un massimo di 3 cifre intere e 2 cifre decimali"
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
                  "components": [
                    {
                      "key": "DATI_IDENTIFICATIVI.altri_elementi_identificativi.restituzione_bck",
                      "type": "textfield",
                      "input": true,
                      "label": "Restituzione alternativa codice rilievo",
                      "logic": [
                        {
                          "name": "verificaComponenteAppFO",
                          "actions": [
                            {
                              "name": "disabilitaPerFO",
                              "type": "property",
                              "state": true,
                              "property": {
                                "type": "boolean",
                                "label": "Disabled",
                                "value": "disabled"
                              }
                            }
                          ],
                          "trigger": {
                            "type": "javascript",
                            "javascript": "/* result = options.SCRIVA.isFrontOffice === true; */ return false;"
                          }
                        }
                      ],
                      "validate": {
                        "maxLength": 14,
                        "customMessage": "Il campo può contenere un massimo di 14 caratteri"
                      },
                      "tableView": true,
                      "applyMaskOn": "change",
                      "validateWhenHidden": false,
                      "hideOnChildrenHidden": false
                    }
                  ],
                  "currentWidth": 4
                }
              ],
              "tableView": false
            }
          ],
          "collapsible": true
        },
        {
          "key": "ESERCIZIO_DELLA_CAPTAZIONE",
          "type": "panel",
          "input": false,
          "label": "Esercizio della captazione",
          "title": "<strong>* Esercizio della restituzione</strong>",
          "collapsed": true,
          "tableView": false,
          "components": [
            {
              "key": "title_ec_1",
              "type": "htmlelement",
              "attrs": [
                {
                  "attr": "",
                  "value": ""
                }
              ],
              "input": false,
              "label": "title_ec_1",
              "content": "<strong>Esercizio della restituzione</strong>",
              "tableView": false,
              "refreshOnChange": false
            },
            {
              "key": "columns_ec_1",
              "type": "columns",
              "input": false,
              "label": "columns_ec_1",
              "columns": [
                {
                  "pull": 0,
                  "push": 0,
                  "size": "md",
                  "width": 4,
                  "offset": 0,
                  "components": [
                    {
                      "label": "Portata massima restituita (l/s)",
                      "applyMaskOn": "change",
                      "tableView": true,
                      "validate": {
                        "required": true,
                        "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                        "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                        "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                      },
                      "validateWhenHidden": false,
                      "key": "ESERCIZIO_DELLA_RESTITUZIONE.esercizio_restituzione.portata_max",
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
                      "label": "Portata media annua restituita (l/s)",
                      "applyMaskOn": "change",
                      "tableView": true,
                      "validate": {
                        "required": true,
                        "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                        "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, inferiore o uguale alla portata massima, con un massimo di 7 cifre intere e 4 cifre decimali.",
                        "custom": "try {\r\n  // Definisco un oggetto con le informazioni da passare per la verifica\r\n  const params = {\r\n    portataMedia: input,\r\n    portataMassima: data.ESERCIZIO_DELLA_RESTITUZIONE.esercizio_restituzione.portata_max,\r\n    checkZero: true,\r\n  };\r\n  // Recupero la funzione di comodo per la verifica\r\n  const qdrDERCheckPortataMedia = options.SCRIVA.formioQuadri.qdrDERCheckPortataMedia;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const check = qdrDERCheckPortataMedia(params);\r\n  // Verifico il risultato dell'elaborazione sul check\r\n  if (check) {\r\n    // Validazione completa\r\n    return true;\r\n    // #\r\n  } else {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"portataMediaInvalida\";\r\n  }\r\n} catch (e) {}"
                      },
                      "validateWhenHidden": false,
                      "key": "ESERCIZIO_DELLA_RESTITUZIONE.esercizio_restituzione.portata_med",
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
                      "label": "Volume massimo annuo restituito (m<sup>3</sup>)",
                      "applyMaskOn": "change",
                      "tableView": true,
                      "validate": {
                        "required": true,
                        "pattern": "(^\\d{1,10}$)|(^\\d{1,10}[,]\\d{1,2}$)",
                        "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 10 cifre intere e 2 cifre decimali.",
                        "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                      },
                      "validateWhenHidden": false,
                      "key": "ESERCIZIO_DELLA_RESTITUZIONE.esercizio_restituzione.volume_max",
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
              "key": "ESERCIZIO_DELLA_RESTITUZIONE.qta_risorsa_restituita",
              "type": "editgrid",
              "input": true,
              "label": "<strong>Quantità di risorsa restituita dall'opera di recapito finale</strong>",
              "saveRow": "CONFERMA",
              "validate": {
                "customMessage": "Inserire almeno un periodo"
              },
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
                  "key": "columns_ec_2",
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
                          "key": "inizio_periodo",
                          "type": "textfield",
                          "input": true,
                          "label": "Dal (gg/mm)",
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
                          "key": "fine_periodo",
                          "type": "textfield",
                          "input": true,
                          "label": "Al (gg/mm)",
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
                    }
                  ],
                  "tableView": false
                },
                {
                  "key": "columns_ec_3",
                  "type": "columns",
                  "input": false,
                  "label": "columns_ec_3",
                  "columns": [
                    {
                      "pull": 0,
                      "push": 0,
                      "size": "md",
                      "width": 4,
                      "offset": 0,
                      "components": [
                        {
                          "label": "Portata massima restituita (l/s)",
                          "applyMaskOn": "change",
                          "tableView": true,
                          "validate": {
                            "required": true,
                            "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                            "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                            "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                          },
                          "validateWhenHidden": false,
                          "key": "portata_max",
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
                          "label": "Portata media annua restituita (l/s)",
                          "applyMaskOn": "change",
                          "tableView": true,
                          "validate": {
                            "required": true,
                            "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                            "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, inferiore o uguale alla portata massima, con un massimo di 7 cifre intere e 4 cifre decimali.",
                            "custom": "try {\r\n  // Definisco un oggetto con le informazioni da passare per la verifica\r\n  const params = {\r\n    portataMedia: input,\r\n    portataMassima: row.portata_max,\r\n    checkZero: true,\r\n  };\r\n  // Recupero la funzione di comodo per la verifica\r\n  const qdrDERCheckPortataMedia = options.SCRIVA.formioQuadri.qdrDERCheckPortataMedia;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const check = qdrDERCheckPortataMedia(params);\r\n  // Verifico il risultato dell'elaborazione sul check\r\n  if (check) {\r\n    // Validazione completa\r\n    return true;\r\n    // #\r\n  } else {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"portataMediaInvalida\";\r\n  }\r\n} catch (e) {}"
                          },
                          "validateWhenHidden": false,
                          "key": "portata_med",
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
                          "label": "Volume massimo annuo restituito (m<sup>3</sup>)",
                          "applyMaskOn": "change",
                          "tableView": true,
                          "validate": {
                            "required": true,
                            "pattern": "(^\\d{1,10}$)|(^\\d{1,10}[,]\\d{1,2}$)",
                            "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 10 cifre intere e 2 cifre decimali.",
                            "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                          },
                          "validateWhenHidden": false,
                          "key": "volume_max",
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
            }
          ],
          "collapsible": true
        }
      ]
    },
    "componentName": "RestituzioniComponent"
};