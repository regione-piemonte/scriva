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
export const QDR_DER_USIULO_DEBUG = {
    "label": "Stabilimenti e usi",
    "componentName": "UsiUloDerivazioniComponent",
    "UNITA_LOCALE_OPERATIVA": {
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
          "label": "idOggetto - oggetto.id_oggetto",
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
          "content": "<strong>Unità Locale Operativa {{options.SCRIVA.oggetto.cod_scriva}}</strong>",
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
          "key": "DATI_ULO",
          "type": "panel",
          "input": false,
          "label": "Panel",
          "title": "* Dati identificativi",
          "collapsed": true,
          "tableView": false,
          "components": [
            {
              "key": "sottosezioneSedeLegale",
              "type": "htmlelement",
              "attrs": [
                {
                  "attr": "",
                  "value": ""
                }
              ],
              "input": false,
              "label": "SottosezioneSedeLegale",
              "content": "<strong>Sede Legale</strong>",
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
                      "key": "DATI_ULO.codiceFiscaleSedeLegale",
                      "type": "textfield",
                      "input": true,
                      "label": "Codice fiscale",
                      "disabled": true,
                      "validate": {
                        "required": true
                      },
                      "tableView": true,
                      "applyMaskOn": "change",
                      "customDefaultValue": "value = options.SCRIVA.soggetto.cf_soggetto;",
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
                      "key": "DATI_ULO.denominazioneSedeLegale",
                      "type": "textfield",
                      "input": true,
                      "label": "Denominazione",
                      "disabled": true,
                      "validate": {
                        "required": true
                      },
                      "tableView": true,
                      "applyMaskOn": "change",
                      "customDefaultValue": "// Recupero il soggetto\nconst soggetto = options.SCRIVA.soggetto;\n// Genero la descrizione\nvalue = options.SCRIVA.formioQuadri.qdrUsiUloDERDenominazioneSoggetto(soggetto);",
                      "validateWhenHidden": false
                    }
                  ],
                  "currentWidth": 6
                }
              ],
              "tableView": false
            },
            {
              "key": "sottosezioneSedeOperativa",
              "type": "htmlelement",
              "attrs": [
                {
                  "attr": "",
                  "value": ""
                }
              ],
              "input": false,
              "label": "SottosezioneSedeOperativa",
              "content": "<strong>Sede operativa</strong>",
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
                  "width": 6,
                  "offset": 0,
                  "components": [
                    {
                      "key": "DATI_ULO.codiceRilievoSedeOperativa",
                      "type": "textfield",
                      "input": true,
                      "label": "Codice rilievo",
                      "disabled": true,
                      "validate": {
                        "maxLength": 20,
                        "customMessage": "Inserire al massimo 20 caratteri."
                      },
                      "tableView": true,
                      "applyMaskOn": "change",
                      "customConditional": "show = options.SCRIVA.isBackOffice;",
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
              "tableView": false,
              "autoAdjust": true
            },
            {
              "key": "DATI_ULO.den_oggetto",
              "type": "textarea",
              "input": true,
              "label": "Denominazione",
              "validate": {
                "required": true,
                "maxLength": 500,
                "customMessage": "Inserire al massimo 500 caratteri."
              },
              "tableView": true,
              "autoExpand": true,
              "applyMaskOn": "change",
              "customDefaultValue": "// Recupero la denominazione dell'opera\nlet denOpera;\ntry { denOpera = options.SCRIVA.oggetto.den_oggetto; } catch(e) {}\n// Recupero la denominazione dell'oggetto-istanza\nlet denOggIst;\ntry { denOggIst = options.SCRIVA.oggettoIstanza.den_oggetto; } catch(e) {}\n// Imposto il valore\nif (denOpera) {\n  value = denOpera;\n} else if (denOggIst) {\n  value = denOggIst;\n} else {\n  value = '';\n}",
              "validateWhenHidden": false
            },
            {
              "key": "labelLocalizzazione",
              "type": "htmlelement",
              "attrs": [
                {
                  "attr": "",
                  "value": ""
                }
              ],
              "input": false,
              "label": "labelLocalizzazione",
              "content": "<u>Localizzazione</u>",
              "tableView": false,
              "refreshOnChange": false
            },
            {
              "key": "columns3",
              "type": "columns",
              "input": false,
              "label": "localizzazione_riga1",
              "columns": [
                {
                  "pull": 0,
                  "push": 0,
                  "size": "md",
                  "width": 6,
                  "offset": 0,
                  "components": [
                    {
                      "key": "DATI_ULO.comuneSedeOperativa",
                      "type": "textfield",
                      "input": true,
                      "label": "Comune",
                      "disabled": true,
                      "validate": {
                        "required": true
                      },
                      "tableView": true,
                      "applyMaskOn": "change",
                      "customDefaultValue": "value = options.SCRIVA.oggettoIstanza.ubicazione_oggetto[0].comune.denom_comune;",
                      "validateWhenHidden": false
                    }
                  ],
                  "currentWidth": 6
                },
                {
                  "pull": 0,
                  "push": 0,
                  "size": "md",
                  "width": 4,
                  "offset": 0,
                  "components": [
                    {
                      "key": "DATI_ULO.provinciaSedeOperativa",
                      "type": "textfield",
                      "input": true,
                      "label": "Provincia",
                      "disabled": true,
                      "validate": {
                        "required": true
                      },
                      "tableView": true,
                      "applyMaskOn": "change",
                      "customDefaultValue": "value = options.SCRIVA.oggettoIstanza.ubicazione_oggetto[0].comune.provincia.denom_provincia;",
                      "validateWhenHidden": false
                    }
                  ],
                  "currentWidth": 4
                }
              ],
              "tableView": false
            },
            {
              "key": "DATI_ULO.localitaSedeOperativa",
              "type": "textarea",
              "input": true,
              "label": "Località",
              "validate": {
                "maxLength": 250,
                "customMessage": "Inserire al massimo 250 caratteri."
              },
              "tableView": true,
              "autoExpand": false,
              "applyMaskOn": "change",
              "customDefaultValue": "value = options.SCRIVA.oggettoIstanza.ubicazione_oggetto[0].des_localita;",
              "validateWhenHidden": false
            },
            {
              "key": "riga3",
              "type": "columns",
              "input": false,
              "label": "localizzazione_riga3",
              "columns": [
                {
                  "pull": 0,
                  "push": 0,
                  "size": "md",
                  "width": 6,
                  "offset": 0,
                  "components": [
                    {
                      "key": "DATI_ULO.indirizzoSedeOperativa",
                      "type": "textfield",
                      "input": true,
                      "label": "Indirizzo",
                      "validate": {
                        "maxLength": 100,
                        "customMessage": "Inserire al massimo 100 caratteri."
                      },
                      "tableView": true,
                      "applyMaskOn": "change",
                      "customDefaultValue": "value = options.SCRIVA.oggettoIstanza.ubicazione_oggetto[0].den_indirizzo;",
                      "validateWhenHidden": false
                    }
                  ],
                  "currentWidth": 6
                },
                {
                  "pull": 0,
                  "push": 0,
                  "size": "md",
                  "width": 4,
                  "offset": 0,
                  "components": [
                    {
                      "key": "DATI_ULO.civicoSedeOperativa",
                      "type": "textfield",
                      "input": true,
                      "label": "Civico",
                      "validate": {
                        "maxLength": 30,
                        "customMessage": "Inserire al massimo 30 caratteri."
                      },
                      "tableView": true,
                      "applyMaskOn": "change",
                      "customDefaultValue": "value = options.SCRIVA.oggettoIstanza.ubicazione_oggetto[0].num_civico;",
                      "validateWhenHidden": false
                    }
                  ],
                  "currentWidth": 4
                },
                {
                  "size": "md",
                  "width": 2,
                  "components": [
                    {
                      "key": "DATI_ULO.CAPSedeOperativa",
                      "type": "textfield",
                      "input": true,
                      "label": "CAP",
                      "validate": {
                        "maxLength": 10
                      },
                      "tableView": true,
                      "applyMaskOn": "change",
                      "validateWhenHidden": false
                    }
                  ],
                  "currentWidth": 2
                }
              ],
              "tableView": false
            },
            {
              "key": "localizzazioneRiga4",
              "type": "columns",
              "input": false,
              "label": "localizzazione_riga4",
              "columns": [
                {
                  "pull": 0,
                  "push": 0,
                  "size": "md",
                  "width": 6,
                  "offset": 0,
                  "components": [
                    {
                      "key": "DATI_ULO.num_telefono",
                      "type": "textfield",
                      "input": true,
                      "label": "Telefono",
                      "validate": {
                        "maxLength": 25,
                        "customMessage": "Inserire al massimo 25 caratteri."
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
                      "key": "DATI_ULO.des_email",
                      "type": "textfield",
                      "input": true,
                      "label": "E-mail",
                      "validate": {
                        "maxLength": 100,
                        "customMessage": "Inserire al massimo 100 caratteri."
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
              "key": "DATI_ULO.annotazioni",
              "type": "textarea",
              "input": true,
              "label": "Annotazioni",
              "validate": {
                "maxLength": 4000,
                "customMessage": "Inserire al massimo 4000 caratteri."
              },
              "tableView": true,
              "autoExpand": false,
              "applyMaskOn": "change",
              "validateWhenHidden": false
            }
          ],
          "collapsible": true
        },
        {
          "key": "USO_ENERGETICO",
          "type": "panel",
          "input": false,
          "label": "Panel",
          "title": "Uso energetico",
          "collapsed": true,
          "tableView": false,
          "components": [
            {
              "key": "USO_ENERGETICO.flgUsoEnergetico",
              "type": "radio",
              "input": true,
              "label": "E' presente un uso energetico?",
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
                "custom": "// Il flag uso energetico deve essere \"si\" se flag uso riqualificazione è \"si\"\n// Ref: WP3-3.1-USR-V12.5-US009_InfoSpecificheDER_FO_BO.docx - Cap. 3.2.7.1\tScenario base – Quadro Usi in ULO - step 19\nconst flgUsoRiq = data.USO_RIQUALIFICAZIONE.flgUsoRiqualificazione;\nconst isFlgUsoRiqSI = flgUsoRiq.toLocaleLowerCase() === 'si';\n// Verifico se il flag uso riqualificazione è \"si\"\nif (isFlgUsoRiqSI) {\n  // Il campo è valido allora se il suo valore è \"si\" a sua volta\n  const flgUsoEne = input;\n  const isFlgUsoEneSI = flgUsoEne.toLocaleLowerCase() === 'si';\n  // Ritorno il controllo\n  return isFlgUsoEneSI;\n  // #\n} else {\n  // Ritorno true\n  return true;\n}",
                "required": true,
                "customMessage": "Il campo è obbligatorio, se è presente un uso di riqualificazione, bisogna definire un uso energetico"
              },
              "tableView": true,
              "defaultValue": "no",
              "validateWhenHidden": false,
              "optionsLabelPosition": "right"
            },
            {
              "key": "JS_MODAL.USO_ENERGETICO.flgUsoEnergetico",
              "type": "textfield",
              "input": true,
              "label": "Conferma gestione uso energetico [NASCOSTO]",
              "logic": [
                {
                  "name": "JS_MODAL_TRIGGER.USO_ENERGETICO.flgUsoEnergetico",
                  "actions": [
                    {
                      "name": "JS_MODAL_CONFERMA.USO_ENERGETICO.flgUsoEnergetico",
                      "type": "customAction",
                      "customAction": "try {\n  const componenteTrigger = 'USO_ENERGETICO.flgUsoEnergetico';\n  const componenteConferma = 'JS_MODAL.USO_ENERGETICO.flgUsoEnergetico';\n  const formioModals = options.SCRIVA.formioModals;\n  const formioUpdate = options.SCRIVA.formioUpdate;\n  const formioUpdateComponent = options.SCRIVA.formioUpdateComponent;\n\n  const onConfirm = () => {\n    return formioUpdateComponent(data, componenteConferma, 'no');\n  }\n  const onCancel = () => {\n    return formioUpdateComponent(data, componenteTrigger, 'si');\n  }\n  \n  const config = {\n    title: 'Conferma',\n    codeOrBody: 'A064',\n    callbacks: {\n      onConfirm: () => {\n        data = onConfirm();\n        formioUpdate(data);\n      },\n      onCancel: () => {\n        data = onCancel();\n        formioUpdate(data);\n      },\n      onClose: () => {\n        data = onCancel();\n        formioUpdate(data);\n      }\n    }\n  }\n  \n  formioModals.conferma(config);\n  \n} catch (e) {}"
                    }
                  ],
                  "trigger": {
                    "type": "javascript",
                    "simple": {},
                    "javascript": "try {\n  const check1 = data.USO_ENERGETICO.flgUsoEnergetico == 'no';\n  const check2 = data.JS_MODAL.USO_ENERGETICO.flgUsoEnergetico == 'si';\n  result = check1 && check2;\n} catch (e) {\n  return false;\n}"
                  }
                },
                {
                  "name": "JS_MODAL_SET_TRIGGER.USO_ENERGETICO.flgUsoEnergetico",
                  "actions": [
                    {
                      "name": "JS_MODAL_SET_TRIGGER_SI.USO_ENERGETICO.flgUsoEnergetico",
                      "type": "value",
                      "value": "value = 'si';"
                    }
                  ],
                  "trigger": {
                    "type": "simple",
                    "simple": {
                      "eq": "si",
                      "show": true,
                      "when": "USO_ENERGETICO.flgUsoEnergetico"
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
                  "key": "USO_ENERGETICO.uso_effettivo",
                  "type": "editgrid",
                  "input": true,
                  "label": "<strong>Uso effettivo</strong>",
                  "saveRow": "CONFERMA",
                  "validate": {
                    "custom": "// Recupero i dati\nlet listaElementi = data.USO_ENERGETICO.uso_effettivo;\n// Verifico che esistano elementi\nif (!listaElementi || listaElementi.length === 0) {\n  // Dati obbligatori\n  return false;\n}\n\n// Definisco la funzione di comparazione per la gestione dell'unicità degli elementi\nconst compare = (a, b) => { return a.tipo_uso.id_tipo_uso === b.tipo_uso.id_tipo_uso; };\n// Verifico che ci siano solo valori univoci, se la quantità di valori univoci è diversa dalla quantità di elementi, vuol dire che ci sono duplicati\nreturn  _.uniqWith(listaElementi, compare).length === listaElementi.length;",
                    "required": true,
                    "customMessage": "E' obbligatorio inserire almeno un elemento, e non devono essere presenti duplicati."
                  },
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
                      "key": "tipo_uso",
                      "data": {
                        "custom": "values = [\r\n  { des_tipo_uso: 'produzione energia', id_tipo_uso: 16, id_uso_legge: 1},\r\n  { des_tipo_uso: 'forza motrice', id_tipo_uso: 17, id_uso_legge: 1}\r\n  ]"
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
                  "key": "USO_ENERGETICO.qta_risorsa_utilizzata",
                  "type": "editgrid",
                  "input": true,
                  "label": "<strong>Quantità di risorsa utilizzata</strong>",
                  "saveRow": "CONFERMA",
                  "validate": {
                    "required": true
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
                              "label": "Portata massima utilizzata (l/s)",
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
                          "size": "md",
                          "width": 4,
                          "components": [
                            {
                              "label": "Portata media utilizzata (l/s)",
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
                            "custom": "try {\r\n// Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                          },
                          "validateWhenHidden": false,
                          "key": "USO_ENERGETICO.portata_media",
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
                          "key": "USO_ENERGETICO.volume_max_annuo",
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
              "customConditional": "try {\r\n  // Recupero il valore della input modale\r\n  const inputUtente = data.USO_ENERGETICO.flgUsoEnergetico;\r\n  const inputModale = data.JS_MODAL.USO_ENERGETICO.flgUsoEnergetico;\r\n  // Se l'input ha un valore specifico o quello di init devo nascondere l'elemento\r\n  const caseOnInit = inputUtente === 'si' && inputModale === 'init';\r\n  const caseOnUse = inputModale === 'si';\r\n  // Definisco le condizioni di visualizzazione\r\n  show = caseOnInit || caseOnUse;\r\n} catch(e) {\r\n  show = true;\r\n}\r\n"
            },
            {
              "key": "datiSpecifici",
              "type": "panel",
              "input": false,
              "label": "Panel",
              "title": "Dati specifici",
              "collapsed": true,
              "tableView": false,
              "components": [
                {
                  "key": "USO_ENERGETICO.collocazione",
                  "data": {
                    "custom": "values = [\r\n  { des_collocazione: 'In caverna', id_collocazione: 1},\r\n  { des_collocazione: 'In corpo traversa / DMV turbinato', id_collocazione: 2},\r\n  { des_collocazione: 'Su canale irriguo', id_collocazione: 3},\r\n  { des_collocazione: 'Su acquedotti', id_collocazione: 4}\r\n  ]\r\n"
                  },
                  "type": "select",
                  "input": true,
                  "label": "Collocazione",
                  "widget": "choicesjs",
                  "dataSrc": "custom",
                  "template": "<span>{{ item.des_collocazione }}</span>",
                  "tableView": true,
                  "validateWhenHidden": false
                },
                {
                  "key": "html",
                  "type": "htmlelement",
                  "attrs": [
                    {
                      "attr": "",
                      "value": ""
                    }
                  ],
                  "input": false,
                  "label": "HTML",
                  "content": "<strong>Potenza ed energia prodotta dalla centrale</strong>",
                  "tableView": false,
                  "refreshOnChange": false
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
                          "key": "USO_ENERGETICO.potenza_nma",
                          "type": "textfield",
                          "input": true,
                          "label": "Potenza nominale media annua (kW)",
                          "validate": {
                            "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                            "customMessage": "Indicare un massimo di 7 cifre intere e 4 cifre decimali"
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
                          "key": "USO_ENERGETICO.energia_prod_annua",
                          "type": "textfield",
                          "input": true,
                          "label": "Energia annualmente producibile (kWh)",
                          "validate": {
                            "pattern": "(^\\d{1,12}$)|(^\\d{1,12}[,]\\d{1,2}$)",
                            "customMessage": "Indicare un massimo di 12 cifre intere e 2 cifre decimali"
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
              "collapsible": true,
              "customConditional": "try {\r\n  // Recupero il valore della input modale\r\n  const inputUtente = data.USO_ENERGETICO.flgUsoEnergetico;\r\n  const inputModale = data.JS_MODAL.USO_ENERGETICO.flgUsoEnergetico;\r\n  // Se l'input ha un valore specifico o quello di init devo nascondere l'elemento\r\n  const caseOnInit = inputUtente === 'si' && inputModale === 'init';\r\n  const caseOnUse = inputModale === 'si';\r\n  // Definisco le condizioni di visualizzazione\r\n  show = caseOnInit || caseOnUse;\r\n} catch(e) {\r\n  show = true;\r\n}\r\n"
            }
          ],
          "collapsible": true
        },
        {
          "key": "usoRiqualificazioneDellenergia",
          "type": "panel",
          "input": false,
          "label": "Panel",
          "title": "Uso riqualificazione dell'energia",
          "collapsed": true,
          "tableView": false,
          "components": [
            {
              "key": "USO_RIQUALIFICAZIONE.flgUsoRiqualificazione",
              "type": "radio",
              "input": true,
              "label": "E' presente un uso riqualificazione dell'energia?",
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
              "key": "JS_MODAL.USO_RIQUALIFICAZIONE.flgUsoRiqualificazione",
              "type": "textfield",
              "input": true,
              "label": "Conferma gestione riqualificazione energia [NASCOSTO]",
              "logic": [
                {
                  "name": "JS_MODAL_TRIGGER.USO_RIQUALIFICAZIONE.flgUsoRiqualificazione",
                  "actions": [
                    {
                      "name": "JS_MODAL_CONFERMA.USO_RIQUALIFICAZIONE.flgUsoRiqualificazione",
                      "type": "customAction",
                      "customAction": "try {\n  const componenteTrigger = 'USO_RIQUALIFICAZIONE.flgUsoRiqualificazione';\n  const componenteConferma = 'JS_MODAL.USO_RIQUALIFICAZIONE.flgUsoRiqualificazione';\n  const formioModals = options.SCRIVA.formioModals;\n  const formioUpdate = options.SCRIVA.formioUpdate;\n  const formioUpdateComponent = options.SCRIVA.formioUpdateComponent;\n\n  const onConfirm = () => {\n    return formioUpdateComponent(data, componenteConferma, 'no');\n  }\n  const onCancel = () => {\n    return formioUpdateComponent(data, componenteTrigger, 'si');\n  }\n  \n  const config = {\n    title: 'Conferma',\n    codeOrBody: 'A064',\n    callbacks: {\n      onConfirm: () => {\n        data = onConfirm();\n        formioUpdate(data);\n      },\n      onCancel: () => {\n        data = onCancel();\n        formioUpdate(data);\n      },\n      onClose: () => {\n        data = onCancel();\n        formioUpdate(data);\n      }\n    }\n  }\n  \n  formioModals.conferma(config);\n  \n} catch (e) {}"
                    }
                  ],
                  "trigger": {
                    "type": "javascript",
                    "simple": {},
                    "javascript": "try {\n  const check1 = data.USO_RIQUALIFICAZIONE.flgUsoRiqualificazione == 'no';\n  const check2 = data.JS_MODAL.USO_RIQUALIFICAZIONE.flgUsoRiqualificazione == 'si';\n  result = check1 && check2;\n} catch (e) {\n  return false;\n}"
                  }
                },
                {
                  "name": "JS_MODAL_SET_TRIGGER.USO_RIQUALIFICAZIONE.flgUsoRiqualificazione",
                  "actions": [
                    {
                      "name": "JS_MODAL_SET_TRIGGER_SI.USO_RIQUALIFICAZIONE.flgUsoRiqualificazione",
                      "type": "value",
                      "value": "value = 'si';"
                    }
                  ],
                  "trigger": {
                    "type": "simple",
                    "simple": {
                      "eq": "si",
                      "show": true,
                      "when": "USO_RIQUALIFICAZIONE.flgUsoRiqualificazione"
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
              "key": "USO_RIQUALIFICAZIONE.tipo_uso",
              "data": {
                "custom": "values = [\r\n  { des_tipo_uso: \"riqualificazione dell'energia\", fk_uso_legge: 7, id_tipo_uso: 25}\r\n  ]"
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
                "id_tipo_uso": 25,
                "des_tipo_uso": "riqualificazione dell'energia",
                "fk_uso_legge": 7
              },
              "customConditional": "try {\r\n  // Recupero il valore della input modale\r\n  const inputUtente = data.USO_RIQUALIFICAZIONE.flgUsoRiqualificazione;\r\n  const inputModale = data.JS_MODAL.USO_RIQUALIFICAZIONE.flgUsoRiqualificazione;\r\n  // Se l'input ha un valore specifico o quello di init devo nascondere l'elemento\r\n  const caseOnInit = inputUtente === 'si' && inputModale === 'init';\r\n  const caseOnUse = inputModale === 'si';\r\n  // Definisco le condizioni di visualizzazione\r\n  show = caseOnInit || caseOnUse;\r\n} catch(e) {\r\n  show = true;\r\n}\r\n",
              "validateWhenHidden": false
            },
            {
              "key": "html2",
              "type": "htmlelement",
              "attrs": [
                {
                  "attr": "",
                  "value": ""
                }
              ],
              "input": false,
              "label": "HTML",
              "content": "<strong>Potenza ed energia di pompaggio</strong>",
              "tableView": false,
              "refreshOnChange": false,
              "customConditional": "try {\r\n  // Recupero il valore della input modale\r\n  const inputUtente = data.USO_RIQUALIFICAZIONE.flgUsoRiqualificazione;\r\n  const inputModale = data.JS_MODAL.USO_RIQUALIFICAZIONE.flgUsoRiqualificazione;\r\n  // Se l'input ha un valore specifico o quello di init devo nascondere l'elemento\r\n  const caseOnInit = inputUtente === 'si' && inputModale === 'init';\r\n  const caseOnUse = inputModale === 'si';\r\n  // Definisco le condizioni di visualizzazione\r\n  show = caseOnInit || caseOnUse;\r\n} catch(e) {\r\n  show = true;\r\n}\r\n"
            },
            {
              "key": "columns8",
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
                      "key": "USO_RIQUALIFICAZIONE.potenza_npomp",
                      "type": "textfield",
                      "input": true,
                      "label": "Potenza nominale di pompaggio (kW)",
                      "validate": {
                        "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,2}$)",
                        "customMessage": "Indicare un massimo di 7 cifre intere e 2 cifre decimali"
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
                      "key": "USO_RIQUALIFICAZIONE.energia_annua_pomp",
                      "type": "textfield",
                      "input": true,
                      "label": "Energia annua utilizzata per pompaggio (kWh)",
                      "validate": {
                        "pattern": "(^\\d{1,12}$)|(^\\d{1,12}[,]\\d{1,2}$)",
                        "customMessage": "Indicare un massimo di 12 cifre intere e 2 cifre decimali"
                      },
                      "tableView": true,
                      "applyMaskOn": "change",
                      "validateWhenHidden": false
                    }
                  ],
                  "currentWidth": 6
                }
              ],
              "tableView": false,
              "customConditional": "try {\r\n  // Recupero il valore della input modale\r\n  const inputUtente = data.USO_RIQUALIFICAZIONE.flgUsoRiqualificazione;\r\n  const inputModale = data.JS_MODAL.USO_RIQUALIFICAZIONE.flgUsoRiqualificazione;\r\n  // Se l'input ha un valore specifico o quello di init devo nascondere l'elemento\r\n  const caseOnInit = inputUtente === 'si' && inputModale === 'init';\r\n  const caseOnUse = inputModale === 'si';\r\n  // Definisco le condizioni di visualizzazione\r\n  show = caseOnInit || caseOnUse;\r\n} catch(e) {\r\n  show = true;\r\n}\r\n"
            }
          ],
          "collapsible": true
        },
        {
          "key": "usoProduzioneBeniEServizi",
          "type": "panel",
          "input": false,
          "label": "Panel",
          "title": "Uso produzione beni e servizi",
          "collapsed": true,
          "tableView": false,
          "components": [
            {
              "key": "USO_PRODUZIONE.flgUsoProduzione",
              "type": "radio",
              "input": true,
              "label": "E' presente un uso produzione beni e servizi?",
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
              "key": "JS_MODAL.USO_PRODUZIONE.flgUsoProduzione",
              "type": "textfield",
              "input": true,
              "label": "Conferma gestione produzione beni e servizi [NASCOSTO]",
              "logic": [
                {
                  "name": "JS_MODAL_TRIGGER.USO_PRODUZIONE.flgUsoProduzione",
                  "actions": [
                    {
                      "name": "JS_MODAL_CONFERMA.USO_PRODUZIONE.flgUsoProduzione",
                      "type": "customAction",
                      "customAction": "try {\n  const componenteTrigger = 'USO_PRODUZIONE.flgUsoProduzione';\n  const componenteConferma = 'JS_MODAL.USO_PRODUZIONE.flgUsoProduzione';\n  const formioModals = options.SCRIVA.formioModals;\n  const formioUpdate = options.SCRIVA.formioUpdate;\n  const formioUpdateComponent = options.SCRIVA.formioUpdateComponent;\n\n  const onConfirm = () => {\n    return formioUpdateComponent(data, componenteConferma, 'no');\n  }\n  const onCancel = () => {\n    return formioUpdateComponent(data, componenteTrigger, 'si');\n  }\n  \n  const config = {\n    title: 'Conferma',\n    codeOrBody: 'A064',\n    callbacks: {\n      onConfirm: () => {\n        data = onConfirm();\n        formioUpdate(data);\n      },\n      onCancel: () => {\n        data = onCancel();\n        formioUpdate(data);\n      },\n      onClose: () => {\n        data = onCancel();\n        formioUpdate(data);\n      }\n    }\n  }\n  \n  formioModals.conferma(config);\n  \n} catch (e) {}"
                    }
                  ],
                  "trigger": {
                    "type": "javascript",
                    "simple": {},
                    "javascript": "try {\n  const check1 = data.USO_PRODUZIONE.flgUsoProduzione == 'no';\n  const check2 = data.JS_MODAL.USO_PRODUZIONE.flgUsoProduzione == 'si';\n  result = check1 && check2;\n} catch (e) {\n  return false;\n}"
                  }
                },
                {
                  "name": "JS_MODAL_SET_TRIGGER.USO_PRODUZIONE.flgUsoProduzione",
                  "actions": [
                    {
                      "name": "JS_MODAL_SET_TRIGGER_SI.USO_PRODUZIONE.flgUsoProduzione",
                      "type": "value",
                      "value": "value = 'si';"
                    }
                  ],
                  "trigger": {
                    "type": "simple",
                    "simple": {
                      "eq": "si",
                      "show": true,
                      "when": "USO_PRODUZIONE.flgUsoProduzione"
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
                  "key": "USO_PRODUZIONE.qta_risorsa_utilizzata",
                  "type": "editgrid",
                  "input": true,
                  "label": "<strong>Quantità di risorsa utilizzata</strong>",
                  "saveRow": "CONFERMA",
                  "validate": {
                    "required": true
                  },
                  "removeRow": "ANNULLA",
                  "rowDrafts": false,
                  "tableView": false,
                  "templates": {
                    "row": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">\r\n        {{ isVisibleInRow(component) ? getView(component, row[component.key]) : ''}}\r\n      </div>\r\n    {% } %}\r\n  {% }) %}\r\n  {% if (!instance.options.readOnly && !instance.disabled) { %}\r\n    <div class=\"col\">\r\n      <div class=\"btn-group pull-left\">\r\n        <button class=\"btn btn-default btn-light btn-sm editRow\"><i class=\"{{ iconClass('edit') }}\"></i></button>\r\n        {% if (!instance.hasRemoveButtons || instance.hasRemoveButtons()) { %}\r\n          <button class=\"btn btn-danger btn-sm removeRow\"><i class=\"{{ iconClass('trash') }}\"></i></button>\r\n        {% } %}\r\n      </div>\r\n    </div>\r\n  {% } %}\r\n</div>",
                    "header": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">{{ t(component.label) }}</div>\r\n    {% } %}\r\n  {% }) %}\r\n  <div class=\"col\">Azioni</div>\r\n</div>"
                  },
                  "addAnother": "AGGIUNGI PERIODO",
                  "components": [
                    {
                      "key": "columns1",
                      "type": "columns",
                      "input": false,
                      "label": "Columns_1",
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
                                "custom": "values = [\r\n  { des_tipo_uso: 'alimentare', fk_uso_legge: 6, id_tipo_uso: 18},\r\n  { des_tipo_uso: 'attivita di prestazione del servizio', fk_uso_legge: 6, id_tipo_uso: 20},\r\n  { des_tipo_uso: 'di processo e assimilati', fk_uso_legge: 6, id_tipo_uso: 19},\r\n  { des_tipo_uso: 'impianti sportivi', fk_uso_legge: 6, id_tipo_uso: 22},\r\n  { des_tipo_uso: 'innevamento artificiale', fk_uso_legge: 6, id_tipo_uso: 23},\r\n  { des_tipo_uso: 'raffreddamento', fk_uso_legge: 6, id_tipo_uso: 21}\r\n  ]\r\n"
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
                          "currentWidth": 3
                        }
                      ],
                      "tableView": false
                    },
                    {
                      "key": "columns8",
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
                  "key": "USO_PRODUZIONE.tecnologie_ricircolo",
                  "type": "textarea",
                  "input": true,
                  "label": "Tecnologie impiegate per il ricircolo e riuso della risorsa",
                  "validate": {
                    "maxLength": 4000,
                    "customMessage": "Inserire al massimo 4000 caratteri."
                  },
                  "tableView": true,
                  "autoExpand": false,
                  "applyMaskOn": "change",
                  "validateWhenHidden": false
                },
                {
                  "key": "columns7",
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
                          "key": "USO_PRODUZIONE.portata_media",
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
                          "key": "USO_PRODUZIONE.volume_max_annuo",
                          "type": "textfield",
                          "input": true,
                          "hideOnChildrenHidden": false
                        }
                      ],
                      "currentWidth": 6
                    }
                  ],
                  "tableView": false
                }
              ],
              "collapsible": true,
              "customConditional": "try {\n  // Recupero il valore della input modale\n  const inputUtente = data.USO_PRODUZIONE.flgUsoProduzione;\n  const inputModale = data.JS_MODAL.USO_PRODUZIONE.flgUsoProduzione;\n  // Se l'input ha un valore specifico o quello di init devo nascondere l'elemento\n  const caseOnInit = inputUtente === 'si' && inputModale === 'init';\n  const caseOnUse = inputModale === 'si';\n  // Definisco le condizioni di visualizzazione\n  show = caseOnInit || caseOnUse;\n} catch(e) {\n  show = true;\n}\n"
            }
          ],
          "collapsible": true
        },
        {
          "key": "usoLavaggioInerti",
          "type": "panel",
          "input": false,
          "label": "Panel",
          "title": "Uso lavaggio inerti",
          "collapsed": true,
          "tableView": false,
          "components": [
            {
              "key": "USO_LAVAGGIO_INERTI.flgUsoLavaggioInerti",
              "type": "radio",
              "input": true,
              "label": "E' presente un uso lavaggio inerti?",
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
              "key": "JS_MODAL.USO_LAVAGGIO_INERTI.flgUsoLavaggioInerti",
              "type": "textfield",
              "input": true,
              "label": "Conferma gestione lavaggio inerti [NASCOSTO]",
              "logic": [
                {
                  "name": "JS_MODAL_TRIGGER.USO_LAVAGGIO_INERTI.flgUsoLavaggioInerti",
                  "actions": [
                    {
                      "name": "JS_MODAL_CONFERMA.USO_LAVAGGIO_INERTI.flgUsoLavaggioInerti",
                      "type": "customAction",
                      "customAction": "try {\n  const componenteTrigger = 'USO_LAVAGGIO_INERTI.flgUsoLavaggioInerti';\n  const componenteConferma = 'JS_MODAL.USO_LAVAGGIO_INERTI.flgUsoLavaggioInerti';\n  const formioModals = options.SCRIVA.formioModals;\n  const formioUpdate = options.SCRIVA.formioUpdate;\n  const formioUpdateComponent = options.SCRIVA.formioUpdateComponent;\n\n  const onConfirm = () => {\n    return formioUpdateComponent(data, componenteConferma, 'no');\n  }\n  const onCancel = () => {\n    return formioUpdateComponent(data, componenteTrigger, 'si');\n  }\n  \n  const config = {\n    title: 'Conferma',\n    codeOrBody: 'A064',\n    callbacks: {\n      onConfirm: () => {\n        data = onConfirm();\n        formioUpdate(data);\n      },\n      onCancel: () => {\n        data = onCancel();\n        formioUpdate(data);\n      },\n      onClose: () => {\n        data = onCancel();\n        formioUpdate(data);\n      }\n    }\n  }\n  \n  formioModals.conferma(config);\n  \n} catch (e) {}"
                    }
                  ],
                  "trigger": {
                    "type": "javascript",
                    "simple": {},
                    "javascript": "try {\n  const check1 = data.USO_LAVAGGIO_INERTI.flgUsoLavaggioInerti == 'no';\n  const check2 = data.JS_MODAL.USO_LAVAGGIO_INERTI.flgUsoLavaggioInerti == 'si';\n  result = check1 && check2;\n} catch (e) {\n  return false;\n}"
                  }
                },
                {
                  "name": "JS_MODAL_SET_TRIGGER.USO_LAVAGGIO_INERTI.flgUsoLavaggioInerti",
                  "actions": [
                    {
                      "name": "JS_MODAL_SET_TRIGGER_SI.USO_LAVAGGIO_INERTI.flgUsoLavaggioInerti",
                      "type": "value",
                      "value": "value = 'si';"
                    }
                  ],
                  "trigger": {
                    "type": "simple",
                    "simple": {
                      "eq": "si",
                      "show": true,
                      "when": "USO_LAVAGGIO_INERTI.flgUsoLavaggioInerti"
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
                  "key": "USO_LAVAGGIO_INERTI.tipo_uso",
                  "data": {
                    "custom": "values = [\r\n  { des_tipo_uso: 'lavaggio di inerti', fk_uso_legge: 7, id_tipo_uso: 24}\r\n  ]"
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
                    "id_tipo_uso": 24,
                    "des_tipo_uso": "lavaggio di inerti",
                    "fk_uso_legge": 7
                  },
                  "validateWhenHidden": false
                },
                {
                  "key": "USO_LAVAGGIO_INERTI.qta_risorsa_utilizzata2",
                  "type": "editgrid",
                  "input": true,
                  "label": "<strong>Quantità di risorsa utilizzata [NASCOSTO]</strong>",
                  "hidden": true,
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
                      "label": "columns_ec_2",
                      "columns": [
                        {
                          "pull": 0,
                          "push": 0,
                          "size": "md",
                          "width": 6,
                          "offset": 0,
                          "components": [
                            {
                              "key": "tecnica_lavaggio",
                              "type": "textfield",
                              "input": true,
                              "label": "Tecniche di lavaggio",
                              "validate": {
                                "required": true,
                                "maxLength": 30,
                                "customMessage": "Inserire un massimo di 30 caratteri"
                              },
                              "tableView": false,
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
                                "custom": "try {\n  // Uso la funzione di utility dalle options\n  const dateFormat = 'DD/MM';\n  const allow29thFeb = false;\n  const checkDateFormat = options.SCRIVA.utilities.checkDateFormat;\n  return checkDateFormat(input, dateFormat, allow29thFeb) ? true : 'invalidDate';\n} catch (e) {\n  // Errore, default\n  return true;\n}",
                                "pattern": "(^([0-2]{0,1}[0-9]|3[0,1])[/]([1-9]|0[1-9]|1[0-2])$)",
                                "required": true,
                                "customMessage": "Inserire nel formato giorno/mese"
                              },
                              "tableView": false,
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
                                "custom": "try {\n  // Uso la funzione di utility dalle options\n  const dateFormat = 'DD/MM';\n  const allow29thFeb = false;\n  const checkDateFormat = options.SCRIVA.utilities.checkDateFormat;\n  return checkDateFormat(input, dateFormat, allow29thFeb) ? true : 'invalidDate';\n} catch (e) {\n  // Errore, default\n  return true;\n}",
                                "pattern": "(^([0-2]{0,1}[0-9]|3[0,1])[/]([1-9]|0[1-9]|1[0-2])$)",
                                "required": true,
                                "customMessage": "Inserire nel formato giorno/mese"
                              },
                              "tableView": false,
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
                          "width": 4,
                          "offset": 0,
                          "components": [
                            {
                              "label": "Portata massima utilizzata (l/s)",
                              "applyMaskOn": "change",
                              "tableView": false,
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
                              "key": "portata_med_utilizz",
                              "type": "textfield",
                              "input": true,
                              "label": "Portata media utilizzata (l/s)",
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
                                "custom": "valid = (Number(input.replace(',','.')) <= Number(row.q_massima.replace(',','.'))) ? true : 'error'",
                                "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                                "required": true,
                                "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, inferiore o uguale alla portata massima, con un massimo di 7 cifre intere e 4 cifre decimali."
                              },
                              "tableView": false,
                              "applyMaskOn": "change",
                              "validateWhenHidden": false,
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
                              "key": "volume_max_utilizz",
                              "type": "textfield",
                              "input": true,
                              "label": "Volume massimo (m<sup>3</sup>)",
                              "validate": {
                                "pattern": "(^\\d{1,9}$)|(^\\d{1,9}[,]\\d{1,5}$)",
                                "required": true,
                                "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 9 cifre intere e 5 cifre decimali."
                              },
                              "tableView": false,
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
                  "displayAsTable": false,
                  "validateWhenHidden": false
                },
                {
                  "key": "USO_LAVAGGIO_INERTI.qta_risorsa_utilizzata",
                  "type": "editgrid",
                  "input": true,
                  "label": "<strong>Quantità di risorsa utilizzata</strong>",
                  "saveRow": "CONFERMA",
                  "validate": {
                    "required": true
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
                      "key": "columnsEc2",
                      "type": "columns",
                      "input": false,
                      "label": "columns_ec_2",
                      "columns": [
                        {
                          "pull": 0,
                          "push": 0,
                          "size": "md",
                          "width": 6,
                          "offset": 0,
                          "components": [
                            {
                              "key": "tecnica_lavaggio",
                              "data": {
                                "custom": "values = [\r\n  { des_tecnica_lavaggio: 'tecnica 1', id_tecnica_lavaggio: 1},\r\n  { des_tecnica_lavaggio: 'tecnica 2', id_tecnica_lavaggio: 2},\r\n  { des_tecnica_lavaggio: 'tecnica 3', id_tecnica_lavaggio: 3}\r\n  ]"
                              },
                              "type": "select",
                              "input": true,
                              "label": "Tecniche di lavaggio",
                              "widget": "choicesjs",
                              "dataSrc": "custom",
                              "template": "  <span>{{ item.des_tecnica_lavaggio }}</span>",
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
                          "width": 4,
                          "offset": 0,
                          "components": [
                            {
                              "label": "Portata massima utilizzata (l/s)",
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
                              "label": "Portata media utilizzata (l/s)",
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
                          "key": "USO_LAVAGGIO_INERTI.portata_media",
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
                          "key": "USO_LAVAGGIO_INERTI.volume_max_annuo",
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
              "customConditional": "try {\n  // Recupero il valore della input modale\n  const inputUtente = data.USO_LAVAGGIO_INERTI.flgUsoLavaggioInerti;\n  const inputModale = data.JS_MODAL.USO_LAVAGGIO_INERTI.flgUsoLavaggioInerti;\n  // Se l'input ha un valore specifico o quello di init devo nascondere l'elemento\n  const caseOnInit = inputUtente === 'si' && inputModale === 'init';\n  const caseOnUse = inputModale === 'si';\n  // Definisco le condizioni di visualizzazione\n  show = caseOnInit || caseOnUse;\n} catch(e) {\n  show = true;\n}\n"
            }
          ],
          "collapsible": true
        },
        {
          "key": "usoPiscicolo",
          "type": "panel",
          "input": false,
          "label": "Panel",
          "title": "Uso piscicolo",
          "collapsed": true,
          "tableView": false,
          "components": [
            {
              "key": "USO_PISCICOLO.flgUsoPiscicolo",
              "type": "radio",
              "input": true,
              "label": "E' presente un uso piscicolo?",
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
              "key": "JS_MODAL.USO_PISCICOLO.flgUsoPiscicolo",
              "type": "textfield",
              "input": true,
              "label": "Conferma gestione uso piscicolo [NASCOSTO]",
              "logic": [
                {
                  "name": "JS_MODAL_TRIGGER.USO_PISCICOLO.flgUsoPiscicolo",
                  "actions": [
                    {
                      "name": "JS_MODAL_CONFERMA.USO_PISCICOLO.flgUsoPiscicolo",
                      "type": "customAction",
                      "customAction": "try {\n  const componenteTrigger = 'USO_PISCICOLO.flgUsoPiscicolo';\n  const componenteConferma = 'JS_MODAL.USO_PISCICOLO.flgUsoPiscicolo';\n  const formioModals = options.SCRIVA.formioModals;\n  const formioUpdate = options.SCRIVA.formioUpdate;\n  const formioUpdateComponent = options.SCRIVA.formioUpdateComponent;\n\n  const onConfirm = () => {\n    return formioUpdateComponent(data, componenteConferma, 'no');\n  }\n  const onCancel = () => {\n    return formioUpdateComponent(data, componenteTrigger, 'si');\n  }\n  \n  const config = {\n    title: 'Conferma',\n    codeOrBody: 'A064',\n    callbacks: {\n      onConfirm: () => {\n        data = onConfirm();\n        formioUpdate(data);\n      },\n      onCancel: () => {\n        data = onCancel();\n        formioUpdate(data);\n      },\n      onClose: () => {\n        data = onCancel();\n        formioUpdate(data);\n      }\n    }\n  }\n  \n  formioModals.conferma(config);\n  \n} catch (e) {}"
                    }
                  ],
                  "trigger": {
                    "type": "javascript",
                    "simple": {},
                    "javascript": "try {\n  const check1 = data.USO_PISCICOLO.flgUsoPiscicolo == 'no';\n  const check2 = data.JS_MODAL.USO_PISCICOLO.flgUsoPiscicolo == 'si';\n  result = check1 && check2;\n} catch (e) {\n  return false;\n}"
                  }
                },
                {
                  "name": "JS_MODAL_SET_TRIGGER.USO_PISCICOLO.flgUsoPiscicolo",
                  "actions": [
                    {
                      "name": "JS_MODAL_SET_TRIGGER_SI.USO_PISCICOLO.flgUsoPiscicolo",
                      "type": "value",
                      "value": "value = 'si';"
                    }
                  ],
                  "trigger": {
                    "type": "simple",
                    "simple": {
                      "eq": "si",
                      "show": true,
                      "when": "USO_PISCICOLO.flgUsoPiscicolo"
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
              "key": "datiGenerali",
              "type": "panel",
              "input": false,
              "label": "Panel",
              "title": "* Dati generali",
              "collapsed": true,
              "tableView": false,
              "components": [
                {
                  "key": "columns9",
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
                          "label": "Vasche n.",
                          "applyMaskOn": "change",
                          "tableView": true,
                          "validate": {
                            "required": true,
                            "pattern": "(^\\d{1,2}$)",
                            "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 2 cifre intere.",
                            "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                          },
                          "validateWhenHidden": false,
                          "key": "USO_PISCICOLO.nr_vasche",
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
                          "label": "Volume massimo (m<sup>3</sup>)",
                          "applyMaskOn": "change",
                          "tableView": true,
                          "validate": {
                            "required": true,
                            "pattern": "(^\\d{1,11}$)|(^\\d{1,11}[,]\\d{1,2}$)",
                            "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 11 cifre intere e 2 cifre decimali.",
                            "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                          },
                          "validateWhenHidden": false,
                          "key": "USO_PISCICOLO.volume_max",
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
              "customConditional": "try {\n  // Recupero il valore della input modale\n  const inputUtente = data.USO_PISCICOLO.flgUsoPiscicolo;\n  const inputModale = data.JS_MODAL.USO_PISCICOLO.flgUsoPiscicolo;\n  // Se l'input ha un valore specifico o quello di init devo nascondere l'elemento\n  const caseOnInit = inputUtente === 'si' && inputModale === 'init';\n  const caseOnUse = inputModale === 'si';\n  // Definisco le condizioni di visualizzazione\n  show = caseOnInit || caseOnUse;\n} catch(e) {\n  show = true;\n}\n"
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
                  "key": "USO_PISCICOLO.tipo_uso",
                  "data": {
                    "custom": "values = [\r\n  { des_tipo_uso: \"piscicolo\", fk_uso_legge: 2, id_tipo_uso: 27}\r\n  ]"
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
                    "id_tipo_uso": 27,
                    "des_tipo_uso": "piscicolo",
                    "fk_uso_legge": 2
                  },
                  "validateWhenHidden": false
                },
                {
                  "key": "USO_PISCICOLO.qta_risorsa_utilizzata",
                  "type": "editgrid",
                  "input": true,
                  "label": "<strong>Quantità di risorsa utilizzata</strong>",
                  "saveRow": "CONFERMA",
                  "validate": {
                    "required": true
                  },
                  "removeRow": "ANNULLA",
                  "rowDrafts": false,
                  "tableView": false,
                  "templates": {
                    "row": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">\r\n        {{ isVisibleInRow(component) ? getView(component, row[component.key]) : ''}}\r\n      </div>\r\n    {% } %}\r\n  {% }) %}\r\n  {% if (!instance.options.readOnly && !instance.disabled) { %}\r\n    <div class=\"col\">\r\n      <div class=\"btn-group pull-left\">\r\n        <button class=\"btn btn-default btn-light btn-sm editRow\"><i class=\"{{ iconClass('edit') }}\"></i></button>\r\n        {% if (!instance.hasRemoveButtons || instance.hasRemoveButtons()) { %}\r\n          <button class=\"btn btn-danger btn-sm removeRow\"><i class=\"{{ iconClass('trash') }}\"></i></button>\r\n        {% } %}\r\n      </div>\r\n    </div>\r\n  {% } %}\r\n</div>",
                    "header": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">{{ t(component.label) }}</div>\r\n    {% } %}\r\n  {% }) %}\r\n  <div class=\"col\">Azioni</div>\r\n</div>"
                  },
                  "addAnother": "AGGIUNGI PERIODO",
                  "components": [
                    {
                      "key": "columns1",
                      "type": "columns",
                      "input": false,
                      "label": "Columns_1",
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
                          "currentWidth": 3
                        }
                      ],
                      "tableView": false
                    },
                    {
                      "key": "columns8",
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
                  "key": "columns7",
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
                          "key": "USO_PISCICOLO.portata_media",
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
                          "key": "USO_PISCICOLO.volume_max_annuo",
                          "type": "textfield",
                          "input": true,
                          "hideOnChildrenHidden": false
                        }
                      ],
                      "currentWidth": 6
                    }
                  ],
                  "tableView": false
                }
              ],
              "collapsible": true,
              "customConditional": "try {\n  // Recupero il valore della input modale\n  const inputUtente = data.USO_PISCICOLO.flgUsoPiscicolo;\n  const inputModale = data.JS_MODAL.USO_PISCICOLO.flgUsoPiscicolo;\n  // Se l'input ha un valore specifico o quello di init devo nascondere l'elemento\n  const caseOnInit = inputUtente === 'si' && inputModale === 'init';\n  const caseOnUse = inputModale === 'si';\n  // Definisco le condizioni di visualizzazione\n  show = caseOnInit || caseOnUse;\n} catch(e) {\n  show = true;\n}\n"
            }
          ],
          "collapsible": true
        },
        {
          "key": "usoZootecnico",
          "type": "panel",
          "input": false,
          "label": "Panel",
          "title": "Uso zootecnico",
          "collapsed": true,
          "tableView": false,
          "components": [
            {
              "key": "USO_ZOOTECNICO.flgUsoZootecnico",
              "type": "radio",
              "input": true,
              "label": "E' presente un uso zootecnico?",
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
              "tableView": true,
              "defaultValue": "no",
              "validateWhenHidden": false,
              "optionsLabelPosition": "right"
            },
            {
              "key": "JS_MODAL.USO_ZOOTECNICO.flgUsoZootecnico",
              "type": "textfield",
              "input": true,
              "label": "Conferma gestione zootecnico [NASCOSTO]",
              "logic": [
                {
                  "name": "JS_MODAL_TRIGGER.USO_ZOOTECNICO.flgUsoZootecnico",
                  "actions": [
                    {
                      "name": "JS_MODAL_CONFERMA.USO_ZOOTECNICO.flgUsoZootecnico",
                      "type": "customAction",
                      "customAction": "try {\n  const componenteTrigger = 'USO_ZOOTECNICO.flgUsoZootecnico';\n  const componenteConferma = 'JS_MODAL.USO_ZOOTECNICO.flgUsoZootecnico';\n  const formioModals = options.SCRIVA.formioModals;\n  const formioUpdate = options.SCRIVA.formioUpdate;\n  const formioUpdateComponent = options.SCRIVA.formioUpdateComponent;\n\n  const onConfirm = () => {\n    return formioUpdateComponent(data, componenteConferma, 'no');\n  }\n  const onCancel = () => {\n    return formioUpdateComponent(data, componenteTrigger, 'si');\n  }\n  \n  const config = {\n    title: 'Conferma',\n    codeOrBody: 'A064',\n    callbacks: {\n      onConfirm: () => {\n        data = onConfirm();\n        formioUpdate(data);\n      },\n      onCancel: () => {\n        data = onCancel();\n        formioUpdate(data);\n      },\n      onClose: () => {\n        data = onCancel();\n        formioUpdate(data);\n      }\n    }\n  }\n  \n  formioModals.conferma(config);\n  \n} catch (e) {}"
                    }
                  ],
                  "trigger": {
                    "type": "javascript",
                    "simple": {},
                    "javascript": "try {\n  const check1 = data.USO_ZOOTECNICO.flgUsoZootecnico == 'no';\n  const check2 = data.JS_MODAL.USO_ZOOTECNICO.flgUsoZootecnico == 'si';\n  result = check1 && check2;\n} catch (e) {\n  return false;\n}"
                  }
                },
                {
                  "name": "JS_MODAL_SET_TRIGGER.USO_ZOOTECNICO.flgUsoZootecnico",
                  "actions": [
                    {
                      "name": "JS_MODAL_SET_TRIGGER_SI.USO_ZOOTECNICO.flgUsoZootecnico",
                      "type": "value",
                      "value": "value = 'si';"
                    }
                  ],
                  "trigger": {
                    "type": "simple",
                    "simple": {
                      "eq": "si",
                      "show": true,
                      "when": "USO_ZOOTECNICO.flgUsoZootecnico"
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
              "key": "datiGenerali",
              "type": "panel",
              "input": false,
              "label": "Panel",
              "title": "* Dati generali",
              "collapsed": true,
              "tableView": false,
              "components": [
                {
                  "key": "columns9",
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
                          "label": "Numero capi",
                          "applyMaskOn": "change",
                          "tableView": true,
                          "validate": {
                            "required": true,
                            "pattern": "(^\\d{1,5}$)",
                            "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 5 cifre intere.",
                            "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                          },
                          "validateWhenHidden": false,
                          "key": "USO_ZOOTECNICO.nr_capi",
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
                          "key": "USO_ZOOTECNICO.tipo_allevamento",
                          "type": "textfield",
                          "input": true,
                          "label": "Tipo di allevamento",
                          "validate": {
                            "required": true,
                            "maxLength": 100,
                            "customMessage": "Inserire un numero massimo di 100 caratteri"
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
              "collapsible": true,
              "customConditional": "try {\n  // Recupero il valore della input modale\n  const inputUtente = data.USO_ZOOTECNICO.flgUsoZootecnico;\n  const inputModale = data.JS_MODAL.USO_ZOOTECNICO.flgUsoZootecnico;\n  // Se l'input ha un valore specifico o quello di init devo nascondere l'elemento\n  const caseOnInit = inputUtente === 'si' && inputModale === 'init';\n  const caseOnUse = inputModale === 'si';\n  // Definisco le condizioni di visualizzazione\n  show = caseOnInit || caseOnUse;\n} catch(e) {\n  show = true;\n}\n"
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
                  "key": "USO_ZOOTECNICO.tipo_uso",
                  "data": {
                    "custom": "values = [\r\n  { des_tipo_uso: \"zootecnico\", fk_uso_legge: 9, id_tipo_uso: 26}\r\n  ]"
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
                    "id_tipo_uso": 26,
                    "des_tipo_uso": "zootecnico",
                    "fk_uso_legge": 9
                  },
                  "validateWhenHidden": false
                },
                {
                  "key": "USO_ZOOTECNICO.qta_risorsa_utilizzata",
                  "type": "editgrid",
                  "input": true,
                  "label": "<strong>Quantità di risorsa utilizzata</strong>",
                  "saveRow": "CONFERMA",
                  "validate": {
                    "required": true
                  },
                  "removeRow": "ANNULLA",
                  "rowDrafts": false,
                  "tableView": false,
                  "templates": {
                    "row": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">\r\n        {{ isVisibleInRow(component) ? getView(component, row[component.key]) : ''}}\r\n      </div>\r\n    {% } %}\r\n  {% }) %}\r\n  {% if (!instance.options.readOnly && !instance.disabled) { %}\r\n    <div class=\"col\">\r\n      <div class=\"btn-group pull-left\">\r\n        <button class=\"btn btn-default btn-light btn-sm editRow\"><i class=\"{{ iconClass('edit') }}\"></i></button>\r\n        {% if (!instance.hasRemoveButtons || instance.hasRemoveButtons()) { %}\r\n          <button class=\"btn btn-danger btn-sm removeRow\"><i class=\"{{ iconClass('trash') }}\"></i></button>\r\n        {% } %}\r\n      </div>\r\n    </div>\r\n  {% } %}\r\n</div>",
                    "header": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">{{ t(component.label) }}</div>\r\n    {% } %}\r\n  {% }) %}\r\n  <div class=\"col\">Azioni</div>\r\n</div>"
                  },
                  "addAnother": "AGGIUNGI PERIODO",
                  "components": [
                    {
                      "key": "columns1",
                      "type": "columns",
                      "input": false,
                      "label": "Columns_1",
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
                          "currentWidth": 3
                        }
                      ],
                      "tableView": false
                    },
                    {
                      "key": "columns8",
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
                  "key": "columns7",
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
                          "key": "USO_ZOOTECNICO.portata_media",
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
                          "key": "USO_ZOOTECNICO.volume_max_annuo",
                          "type": "textfield",
                          "input": true,
                          "hideOnChildrenHidden": false
                        }
                      ],
                      "currentWidth": 6
                    }
                  ],
                  "tableView": false
                }
              ],
              "collapsible": true,
              "customConditional": "try {\n  // Recupero il valore della input modale\n  const inputUtente = data.USO_ZOOTECNICO.flgUsoZootecnico;\n  const inputModale = data.JS_MODAL.USO_ZOOTECNICO.flgUsoZootecnico;\n  // Se l'input ha un valore specifico o quello di init devo nascondere l'elemento\n  const caseOnInit = inputUtente === 'si' && inputModale === 'init';\n  const caseOnUse = inputModale === 'si';\n  // Definisco le condizioni di visualizzazione\n  show = caseOnInit || caseOnUse;\n} catch(e) {\n  show = true;\n}\n"
            }
          ],
          "collapsible": true
        }
      ]
    }
};

