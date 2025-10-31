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
export const QDR_DER_CAPTAZIONE_DEBUG = {
  "label": "Opere di captazione",
  "componentName": "CaptazioniComponent",
  "POZZO": {
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
        "content": "<strong>Pozzo {{options.SCRIVA.oggetto.cod_scriva}}</strong>",
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
            "key": "SECTION_REQUIRED.DATI_IDENTIFICATIVI",
            "type": "hidden",
            "input": true,
            "label": "Sezione obbligatoria",
            "tableView": true,
            "defaultValue": true
          },
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
                    "label": "Codice ROC l.r. 22/99",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "customDefaultValue": "value = options.SCRIVA.oggetto.cod_scriva;",
                    "validate": {
                      "maxLength": 10
                    },
                    "validateWhenHidden": false,
                    "key": "DATI_IDENTIFICATIVI.dati_identificativi.codice_roc",
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
                        "name": "verificaCodiceROCProvvisorio",
                        "trigger": {
                          "type": "javascript",
                          "javascript": "// Recupero il codice ROC dai dati del campo\nconst codiceRoc = data.DATI_IDENTIFICATIVI.dati_identificativi.codice_roc;\n// Verifico se il campo inizia con il codice provvisorio generato\nresult = codiceRoc.startsWith(\"SCRV\");"
                        },
                        "actions": [
                          {
                            "name": "nascondiCodiceROCProvvisorio",
                            "type": "property",
                            "property": {
                              "label": "Hidden",
                              "value": "hidden",
                              "type": "boolean"
                            },
                            "state": true
                          }
                        ]
                      }
                    ],
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
                "size": "md",
                "width": 4,
                "components": [
                  {
                    "key": "DATI_IDENTIFICATIVI.dati_identificativi.codice_sii",
                    "type": "textfield",
                    "input": true,
                    "label": "Codice SII",
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
                      "maxLength": 10
                    },
                    "tableView": true,
                    "applyMaskOn": "change",
                    "validateWhenHidden": false
                  }
                ],
                "currentWidth": 4
              }
            ],
            "autoAdjust": true,
            "key": "columns",
            "type": "columns",
            "input": false,
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
                "width": 8,
                "offset": 0,
                "components": [
                  {
                    "key": "DATI_IDENTIFICATIVI.denominazione",
                    "type": "textfield",
                    "input": true,
                    "label": "Denominazione",
                    "disabled": true,
                    "validate": {
                      "maxLength": 500,
                      "customMessage": "Il campo può contenere un massimo di 500 caratteri"
                    },
                    "tableView": true,
                    "applyMaskOn": "change",
                    "customDefaultValue": "value = options.SCRIVA.oggettoIstanza.den_oggetto;",
                    "validateWhenHidden": false,
                    "hideOnChildrenHidden": false
                  }
                ],
                "currentWidth": 8
              },
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 4,
                "offset": 0,
                "components": [],
                "currentWidth": 4
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
                    "key": "DATI_IDENTIFICATIVI.altri_elementi_identificativi.profondita",
                    "type": "textfield",
                    "input": true,
                    "label": "Profondità (m)",
                    "validate": {
                      "pattern": "(^\\d{1,4}$)|(^\\d{1,4}[,]\\d{1,2}$)",
                      "customMessage": "Indicare un massimo di 4 cifre intere e 2 cifre decimali"
                    },
                    "tableView": true,
                    "applyMaskOn": "change",
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
                    "key": "DATI_IDENTIFICATIVI.altri_elementi_identificativi.quota_piano_c",
                    "type": "textfield",
                    "input": true,
                    "label": "Quota del Piano Campagna s.l.m. (m)",
                    "validate": {
                      "pattern": "(^\\d{1,4}$)",
                      "customMessage": "Inserire un numero intero di massimo 4 cifre"
                    },
                    "tableView": true,
                    "applyMaskOn": "change",
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
                    "key": "DATI_IDENTIFICATIVI.altri_elementi_identificativi.quota_base_acq",
                    "type": "textfield",
                    "input": true,
                    "label": "Quota della base dell’acquifero s.l.m. (m)",
                    "validate": {
                      "pattern": "(^\\d{1,4}$)",
                      "customMessage": "Inserire un numero intero di massimo 4 cifre"
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
          },
          {
            "key": "columns_di_5",
            "type": "columns",
            "input": false,
            "label": "columns_di_5",
            "columns": [
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 4,
                "offset": 0,
                "components": [
                  {
                    "key": "DATI_IDENTIFICATIVI.altri_elementi_identificativi.flg_campo_pozzo",
                    "data": {
                      "values": [
                        {
                          "label": "Sì",
                          "value": "si"
                        },
                        {
                          "label": "No",
                          "value": "no"
                        }
                      ]
                    },
                    "type": "select",
                    "input": true,
                    "label": "Appartenenza ad un campo pozzi",
                    "widget": "choicesjs",
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
                    "key": "DATI_IDENTIFICATIVI.altri_elementi_identificativi.flg_pozzo_art",
                    "data": {
                      "values": [
                        {
                          "label": "Sì",
                          "value": "si"
                        },
                        {
                          "label": "No",
                          "value": "no"
                        }
                      ]
                    },
                    "type": "select",
                    "input": true,
                    "label": "Pozzo artesiano",
                    "widget": "choicesjs",
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
                    "key": "DATI_IDENTIFICATIVI.altri_elementi_identificativi.codice_roc_bck",
                    "type": "textfield",
                    "input": true,
                    "label": "Presa alternativa codice ROC l.r. 22/99",
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
                      "maxLength": 10,
                      "customMessage": "Il campo può contenere un massimo di 50 caratteri"
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
          },
          {
            "key": "JS_SAVE_SECTION.DATI_IDENTIFICATIVI",
            "type": "button",
            "event": "saveSectionData",
            "input": true,
            "label": "SALVA MODIFICHE",
            "logic": [
              {
                "name": "JS_SAVE.DATI_IDENTIFICATIVI",
                "actions": [
                  {
                    "name": "disabilitaSalvaModifiche",
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
                  "javascript": "try {\r\n  const codice_roc = data.DATI_IDENTIFICATIVI.dati_identificativi.codice_roc;\r\n  const codice_rilievo = data.DATI_IDENTIFICATIVI.dati_identificativi.codice_rilievo;\r\n  const codice_sii = data.DATI_IDENTIFICATIVI.dati_identificativi.codice_sii;\r\n  const denominazione = data.DATI_IDENTIFICATIVI.denominazione;\r\n  const comune = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.comune;\r\n  const localizzazione = data.DATI_IDENTIFICATIVI.localizzazione;\r\n  const localita = localizzazione ? localizzazione.localita : '';\r\n  const catas_comune = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.cod_catastale;\r\n  const catas_sezione = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.sezione;\r\n  const catas_foglio = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.foglio;\r\n  const catas_particella = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.particella;\r\n  const profondita = data.DATI_IDENTIFICATIVI.altri_elementi_identificativi.profondita;\r\n  const quota_piano_c = data.DATI_IDENTIFICATIVI.altri_elementi_identificativi.quota_piano_c;\r\n  const quota_base_acq = data.DATI_IDENTIFICATIVI.altri_elementi_identificativi.quota_base_acq;\r\n  const flg_campo_pozzo = data.DATI_IDENTIFICATIVI.altri_elementi_identificativi.flg_campo_pozzo;\r\n  const flg_pozzo_art = data.DATI_IDENTIFICATIVI.altri_elementi_identificativi.flg_pozzo_art;\r\n  const codice_roc_bck = data.DATI_IDENTIFICATIVI.altri_elementi_identificativi.codice_roc_bck;\r\n\r\n  const datiSezione = {\r\n    codice_roc,\r\n    codice_rilievo,\r\n    codice_sii,\r\n    denominazione,\r\n    comune,\r\n    localita,\r\n    catas_comune,\r\n    catas_sezione,\r\n    catas_foglio,\r\n    catas_particella,\r\n    profondita,\r\n    quota_piano_c,\r\n    quota_base_acq,\r\n    flg_campo_pozzo,\r\n    flg_pozzo_art,\r\n    codice_roc_bck,\r\n  };\r\n  const valid = options.SCRIVA.pozzoUtils.validDatiIdentificativi(datiSezione);\r\n  return !valid;\r\n\r\n} catch (e) {\r\n  return true;\r\n}"
                }
              }
            ],
            "theme": "default",
            "action": "event",
            "tableView": false,
            "customClass": "btn-outline-primary float-right",
            "showValidations": false
          }
        ],
        "collapsible": true
      },
      {
        "key": "ESERCIZIO_DELLA_CAPTAZIONE",
        "type": "panel",
        "input": false,
        "label": "Esercizio della captazione",
        "title": "<strong>* Esercizio della captazione</strong>",
        "collapsed": true,
        "tableView": false,
        "components": [
          {
            "key": "SECTION_REQUIRED.ESERCIZIO_DELLA_CAPTAZIONE",
            "type": "hidden",
            "input": true,
            "label": "Sezione obbligatoria",
            "tableView": true,
            "defaultValue": true
          },
          {
            "key": "title_ec_1",
            "type": "htmlelement",
            "input": false,
            "label": "title_ec_1",
            "content": "<strong>Esercizio della captazione</strong>",
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
                    "key": "ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.stato_esercizio",
                    "data": {
                      "custom": "values = [\n  { id_stato_esercizio: 1, des_stato_esercizio: 'In uso' },\n  { id_stato_esercizio: 2, des_stato_esercizio: 'Dismesso' }\n]"
                    },
                    "type": "select",
                    "input": true,
                    "label": "Stato di esercizio",
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
                    "widget": "choicesjs",
                    "dataSrc": "custom",
                    "template": "<span>{{ item.des_stato_esercizio }}</span>",
                    "tableView": true,
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
                    "label": "Portata massima derivabile (l/s)",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "validate": {
                      "required": true,
                      "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                      "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                      "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                    },
                    "validateWhenHidden": false,
                    "key": "ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_max_deriv",
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
                    "label": "Portata media annua derivabile (l/s)",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "validate": {
                      "required": true,
                      "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                      "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, inferiore o uguale alla portata massima, con un massimo di 7 cifre intere e 4 cifre decimali.",
                      "custom": "try {\r\n  // Definisco un oggetto con le informazioni da passare per la verifica\r\n  const params = {\r\n    portataMedia: input,\r\n    portataMassima: data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_max_deriv,\r\n    checkZero: true,\r\n  };\r\n  // Recupero la funzione di comodo per la verifica\r\n  const qdrDERCheckPortataMedia = options.SCRIVA.formioQuadri.qdrDERCheckPortataMedia;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const check = qdrDERCheckPortataMedia(params);\r\n  // Verifico il risultato dell'elaborazione sul check\r\n  if (check) {\r\n    // Validazione completa\r\n    return true;\r\n    // #\r\n  } else {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"portataMediaInvalida\";\r\n  }\r\n} catch (e) {}"
                    },
                    "validateWhenHidden": false,
                    "key": "ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_med_deriv",
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
                    "label": "Volume massimo di concessione (m<sup>3</sup>)",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "validate": {
                      "required": true,
                      "pattern": "(^\\d{1,9}$)|(^\\d{1,9}[,]\\d{1,5}$)",
                      "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 9 cifre intere e 5 cifre decimali.",
                      "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                    },
                    "validateWhenHidden": false,
                    "key": "ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.volume_max",
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
            "key": "ESERCIZIO_DELLA_CAPTAZIONE.qta_risorsa_captata",
            "type": "editgrid",
            "input": true,
            "label": "<strong>Quantità di risorsa prelevata dall'opera</strong>",
            "saveRow": "CONFERMA",
            "validate": {
              "required": true,
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
                        "label": "Portata massima istantanea (l/s)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                          "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "portata_max_derivata",
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
                          "custom": "try {\r\n  // Definisco un oggetto con le informazioni da passare per la verifica\r\n  const params = {\r\n    portataMedia: input,\r\n    portataMassima: row.portata_max_derivata,\r\n    checkZero: true,\r\n  };\r\n  // Recupero la funzione di comodo per la verifica\r\n  const qdrDERCheckPortataMedia = options.SCRIVA.formioQuadri.qdrDERCheckPortataMedia;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const check = qdrDERCheckPortataMedia(params);\r\n  // Verifico il risultato dell'elaborazione sul check\r\n  if (check) {\r\n    // Validazione completa\r\n    return true;\r\n    // #\r\n  } else {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"portataMediaInvalida\";\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "portata_med_derivata",
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
                        "label": "Volume massimo annuo (m<sup>3</sup>)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,9}$)|(^\\d{1,9}[,]\\d{1,5}$)",
                          "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 9 cifre intere e 5 cifre decimali.",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "volume_max_concessione",
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
            "key": "JS_SAVE_SECTION.ESERCIZIO_DELLA_CAPTAZIONE",
            "type": "button",
            "event": "saveSectionData",
            "input": true,
            "label": "SALVA MODIFICHE",
            "logic": [
              {
                "name": "JS_SAVE.ESERCIZIO_DELLA_CAPTAZIONE",
                "actions": [
                  {
                    "name": "disabilitaSalvaModifiche",
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
                  "javascript": "try {\r\n  const stato_esercizio = data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.stato_esercizio;\r\n  const portata_max_deriv = data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_max_deriv;\r\n  const portata_med_deriv = data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_med_deriv;\r\n  const volume_max_concessione = data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.volume_max;\r\n  const qta_risorsa_captata = data.ESERCIZIO_DELLA_CAPTAZIONE.qta_risorsa_captata;\r\n\r\n  const datiSezione = {\r\n    stato_esercizio,\r\n    portata_max_deriv,\r\n    portata_med_deriv,\r\n    volume_max_concessione,\r\n    qta_risorsa_captata\r\n  };\r\n  const valid = options.SCRIVA.pozzoUtils.validEsercizioDellaCaptazione(datiSezione);\r\n  return !valid;\r\n\r\n} catch (e) {\r\n  return true;\r\n}"
                }
              }
            ],
            "theme": "default",
            "action": "event",
            "tableView": false,
            "customClass": "btn-outline-primary float-right",
            "showValidations": false
          }
        ],
        "collapsible": true
      },
      {
        "key": "DATI_STRUTTURALI",
        "type": "panel",
        "input": false,
        "label": "Dati strutturali",
        "title": "<strong>* Dati strutturali</strong>",
        "collapsed": true,
        "tableView": false,
        "components": [
          {
            "key": "SECTION_REQUIRED.DATI_STRUTTURALI",
            "type": "hidden",
            "input": true,
            "label": "Sezione obbligatoria",
            "tableView": true,
            "defaultValue": true
          },
          {
            "key": "title_ds_1",
            "type": "htmlelement",
            "input": false,
            "label": "title_ds_1",
            "content": "<strong>Dati strutturali</strong>",
            "tableView": false,
            "refreshOnChange": false
          },
          {
            "key": "columns2",
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
                    "key": "DATI_STRUTTURALI.dati_strutturali.data_costruzione",
                    "type": "datetime",
                    "input": true,
                    "label": "Data di costruzione",
                    "format": "dd/MM/yyyy",
                    "widget": {
                      "type": "calendar",
                      "displayInTimezone": "viewer",
                      "locale": "en",
                      "useLocaleSettings": false,
                      "allowInput": true,
                      "mode": "single",
                      "enableTime": false,
                      "noCalendar": false,
                      "format": "dd/MM/yyyy",
                      "hourIncrement": 1,
                      "minuteIncrement": 1,
                      "time_24hr": false,
                      "minDate": null,
                      "disableWeekends": false,
                      "disableWeekdays": false,
                      "maxDate": null
                    },
                    "tableView": false,
                    "datePicker": {
                      "disableWeekdays": false,
                      "disableWeekends": false
                    },
                    "enableTime": false,
                    "enableMaxDateInput": false,
                    "enableMinDateInput": false,
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
                    "key": "DATI_STRUTTURALI.dati_strutturali.tipo_falda",
                    "data": {
                      "custom": "values = [\n  { id_tipo_falda: 5, des_tipo_falda: 'Superficiale' },\n  { id_tipo_falda: 6, des_tipo_falda: 'Profonda' },\n]"
                    },
                    "type": "select",
                    "input": true,
                    "label": "Tipo falda",
                    "widget": "choicesjs",
                    "dataSrc": "custom",
                    "template": "<span>{{ item.des_tipo_falda }}</span>",
                    "validate": {
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
                "size": "md",
                "width": 4,
                "components": [
                  {
                    "key": "DATI_STRUTTURALI.dati_strutturali.flg_disp_strat",
                    "data": {
                      "values": [
                        {
                          "label": "Sì",
                          "value": "si"
                        },
                        {
                          "label": "No",
                          "value": "no"
                        }
                      ]
                    },
                    "type": "select",
                    "input": true,
                    "label": "Disponibilità di stratigrafia",
                    "widget": "choicesjs",
                    "validate": {
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
              }
            ],
            "tableView": false
          },
          {
            "key": "DATI_STRUTTURALI.diametro_colonna",
            "type": "editgrid",
            "input": true,
            "label": "<strong>Valori del diametro della colonna</strong>",
            "saveRow": "CONFERMA",
            "removeRow": "ANNULLA",
            "rowDrafts": false,
            "tableView": false,
            "templates": {
              "row": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col-sm-2\">\r\n        {{ isVisibleInRow(component) ? getView(component, row[component.key]) : ''}}\r\n      </div>\r\n    {% } %}\r\n  {% }) %}\r\n  {% if (!instance.options.readOnly && !instance.disabled) { %}\r\n    <div class=\"col-sm-2\">\r\n      <div class=\"btn-group pull-left\">\r\n        <button class=\"btn btn-default btn-light btn-sm editRow\"><i class=\"{{ iconClass('edit') }}\"></i></button>\r\n        {% if (!instance.hasRemoveButtons || instance.hasRemoveButtons()) { %}\r\n          <button class=\"btn btn-danger btn-sm removeRow\"><i class=\"{{ iconClass('trash') }}\"></i></button>\r\n        {% } %}\r\n      </div>\r\n    </div>\r\n  {% } %}\r\n</div>\r\n",
              "header": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col-sm-2\">{{ t(component.label) }}</div>\r\n    {% } %}\r\n  {% }) %}\r\n  <div class=\"col-sm-2\">Azioni</div>\r\n</div>\r\n"
            },
            "addAnother": "AGGIUNGI RIGA",
            "components": [
              {
                "key": "columns_ds_2",
                "type": "columns",
                "input": false,
                "label": "columns_ds_2",
                "columns": [
                  {
                    "pull": 0,
                    "push": 0,
                    "size": "md",
                    "width": 3,
                    "offset": 0,
                    "components": [
                      {
                        "label": "Progressivo tratto",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "defaultValue": "1",
                        "customDefaultValue": "value = 1;\r\ntry {\r\n  const nomeCampo = 'progressivo_tratto';\r\n  const listaTab = data.DATI_STRUTTURALI.diametro_colonna;\r\n  const elementiTab = listaTab.length;\r\n  if (elementiTab > 0) {\r\n    const ultimoElemento = listaTab[elementiTab - 1];\r\n    const ultimoValoreProg = ultimoElemento[nomeCampo];\r\n    const ultimoNumeroProg = Number(ultimoValoreProg);\r\n    if (!isNaN(ultimoNumeroProg)) {\r\n      value = ultimoNumeroProg + 1;\r\n    }\r\n  }\r\n} catch(e) {}",
                        "validate": {
                          "required": true,
                          "customMessage": "Il campo è obbligatorio, univoco e può contenere un massimo di 20 caratteri",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}",
                          "maxLength": 20
                        },
                        "validateWhenHidden": false,
                        "key": "progressivo_tratto",
                        "type": "textfield",
                        "input": true,
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
                        "label": "Diametro (mm)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,4}$)",
                          "customMessage": "Il campo è obbligatorio e può contenere un massimo di 4 cifre intere",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "diametro",
                        "type": "textfield",
                        "input": true,
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
                        "label": "Profondità da (m)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,3}$)|(^\\d{1,3}[,]\\d{1,2}$)",
                          "customMessage": "Il campo è obbligatorio e può contenere un massimo di 3 cifre intere e 2 cifre decimali",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "profondita_da",
                        "type": "textfield",
                        "input": true,
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
                        "label": "Profondità a (m)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,3}$)|(^\\d{1,3}[,]\\d{1,2}$)",
                          "customMessage": "Il campo è obbligatorio e può contenere un massimo di 3 cifre intere e 2 cifre decimali",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "profondita_a",
                        "type": "textfield",
                        "input": true,
                        "hideOnChildrenHidden": false
                      }
                    ],
                    "currentWidth": 3
                  }
                ],
                "tableView": false
              }
            ],
            "displayAsTable": false,
            "validateWhenHidden": false
          },
          {
            "key": "DATI_STRUTTURALI.presenza_cementazione",
            "type": "editgrid",
            "input": true,
            "label": "<strong>Tratti di colonna interessati dalla presenza di cementazione</strong>",
            "saveRow": "CONFERMA",
            "removeRow": "ANNULLA",
            "rowDrafts": false,
            "tableView": false,
            "templates": {
              "row": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col-sm-2\">\r\n        {{ isVisibleInRow(component) ? getView(component, row[component.key]) : ''}}\r\n      </div>\r\n    {% } %}\r\n  {% }) %}\r\n  {% if (!instance.options.readOnly && !instance.disabled) { %}\r\n    <div class=\"col-sm-2\">\r\n      <div class=\"btn-group pull-left\">\r\n        <button class=\"btn btn-default btn-light btn-sm editRow\"><i class=\"{{ iconClass('edit') }}\"></i></button>\r\n        {% if (!instance.hasRemoveButtons || instance.hasRemoveButtons()) { %}\r\n          <button class=\"btn btn-danger btn-sm removeRow\"><i class=\"{{ iconClass('trash') }}\"></i></button>\r\n        {% } %}\r\n      </div>\r\n    </div>\r\n  {% } %}\r\n</div>\r\n",
              "header": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col-sm-2\">{{ t(component.label) }}</div>\r\n    {% } %}\r\n  {% }) %}\r\n  <div class=\"col-sm-2\">Azioni</div>\r\n</div>\r\n"
            },
            "addAnother": "AGGIUNGI RIGA",
            "components": [
              {
                "key": "columns_ds_2",
                "type": "columns",
                "input": false,
                "label": "columns_ds_2",
                "columns": [
                  {
                    "pull": 0,
                    "push": 0,
                    "size": "md",
                    "width": 3,
                    "offset": 0,
                    "components": [
                      {
                        "label": "Progressivo tratto",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "customDefaultValue": "value = 1;\r\ntry {\r\n  const nomeCampo = 'progressivo_tratto';\r\n  const listaTab = data.DATI_STRUTTURALI.presenza_cementazione;\r\n  const elementiTab = listaTab.length;\r\n  if (elementiTab > 0) {\r\n    const ultimoElemento = listaTab[elementiTab - 1];\r\n    const ultimoValoreProg = ultimoElemento[nomeCampo];\r\n    const ultimoNumeroProg = Number(ultimoValoreProg);\r\n    if (!isNaN(ultimoNumeroProg)) {\r\n      value = ultimoNumeroProg + 1;\r\n    }\r\n  }\r\n} catch(e) {}",
                        "validate": {
                          "required": true,
                          "customMessage": "Il campo è obbligatorio, univoco e può contenere un massimo di 20 caratteri",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}",
                          "maxLength": 20
                        },
                        "validateWhenHidden": false,
                        "key": "progressivo_tratto",
                        "type": "textfield",
                        "input": true,
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
                        "label": "Materiale impiegato",
                        "widget": "choicesjs",
                        "tableView": true,
                        "dataSrc": "custom",
                        "data": {
                          "custom": "values = [\r\n  { id_mater: 1, des_mater: \"ACCIAIO\" },\r\n  { id_mater: 39, des_mater: \"ARGILLA A BLOCCHI\" },\r\n  { id_mater: 40, des_mater: \"ARGILLA A PALLINI\" },\r\n  { id_mater: 6, des_mater: \"CALCESTRUZZO\" },\r\n  { id_mater: 37, des_mater: \"CEMENTO\" },\r\n  { id_mater: 3, des_mater: \"CEMENTO ARMATO\" },\r\n  { id_mater: 52, des_mater: \"CEMENTO ARMATO – PRECOMPRESSO\" },\r\n  { id_mater: 4, des_mater: \"CEMENTO-AMIANTO\" },\r\n  { id_mater: 38, des_mater: \"CEMENTO-BENTONITE\" },\r\n  { id_mater: 46, des_mater: \"DISCONTINUO\" },\r\n  { id_mater: 23, des_mater: \"FERRO\" },\r\n  { id_mater: 25, des_mater: \"FIBROCEMENTO\" },\r\n  { id_mater: 42, des_mater: \"GABBIONATE METALLICHE\" },\r\n  { id_mater: 24, des_mater: \"GALLERIA TUFO\" },\r\n  { id_mater: 2, des_mater: \"GHISA\" },\r\n  { id_mater: 9, des_mater: \"GRES\" },\r\n  { id_mater: 50, des_mater: \"MATERIALI PLASTICI\" },\r\n  { id_mater: 44, des_mater: \"MATTONI\" },\r\n  { id_mater: 45, des_mater: \"MISTO\" },\r\n  { id_mater: 51, des_mater: \"MISTO/DISCONTINUO\" },\r\n  { id_mater: 10, des_mater: \"MURATURA\" },\r\n  { id_mater: 26, des_mater: \"NEOPRENE\" },\r\n  { id_mater: 33, des_mater: \"PIETRA\" },\r\n  { id_mater: 34, des_mater: \"PIOMBO\" },\r\n  { id_mater: 47, des_mater: \"POLIESTERE\" },\r\n  { id_mater: 5, des_mater: \"POLIETILENE\" },\r\n  { id_mater: 49, des_mater: \"POLIPROPILENE\" },\r\n  { id_mater: 7, des_mater: \"POLIVINILCLORURO\" },\r\n  { id_mater: 48, des_mater: \"PROLIPROPILENE A BASSA DENSITA'\" },\r\n  { id_mater: 35, des_mater: \"SCONOSCIUTO\" },\r\n  { id_mater: 43, des_mater: \"STRUTTURE PREFABBRICATE\" },\r\n  { id_mater: 41, des_mater: \"TERRA\" },\r\n  { id_mater: 36, des_mater: \"TERRA COTTA\" },\r\n  { id_mater: 8, des_mater: \"VETRORESINA\" }\r\n];"
                        },
                        "template": "<span>{{ item.des_mater }}</span>",
                        "validate": {
                          "required": true
                        },
                        "validateWhenHidden": false,
                        "key": "mater",
                        "type": "select",
                        "input": true
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
                        "label": "Profondità da (m)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,3}$)|(^\\d{1,3}[,]\\d{1,2}$)",
                          "customMessage": "Il campo è obbligatorio e può contenere un massimo di 3 cifre intere e 2 cifre decimali",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "profondita_da",
                        "type": "textfield",
                        "input": true,
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
                        "label": "Profondità a (m)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,3}$)|(^\\d{1,3}[,]\\d{1,2}$)",
                          "customMessage": "Il campo è obbligatorio e può contenere un massimo di 3 cifre intere e 2 cifre decimali",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "profondita_a",
                        "type": "textfield",
                        "input": true,
                        "hideOnChildrenHidden": false
                      }
                    ],
                    "currentWidth": 3
                  }
                ],
                "tableView": false
              }
            ],
            "displayAsTable": false,
            "validateWhenHidden": false
          },
          {
            "key": "DATI_STRUTTURALI.presenza_dreni",
            "type": "editgrid",
            "input": true,
            "label": "<strong>Tratti di colonna interessati dalla presenza di dreni</strong>",
            "saveRow": "CONFERMA",
            "removeRow": "ANNULLA",
            "rowDrafts": false,
            "tableView": false,
            "templates": {
              "row": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col-sm-2\">\r\n        {{ isVisibleInRow(component) ? getView(component, row[component.key]) : ''}}\r\n      </div>\r\n    {% } %}\r\n  {% }) %}\r\n  {% if (!instance.options.readOnly && !instance.disabled) { %}\r\n    <div class=\"col-sm-2\">\r\n      <div class=\"btn-group pull-left\">\r\n        <button class=\"btn btn-default btn-light btn-sm editRow\"><i class=\"{{ iconClass('edit') }}\"></i></button>\r\n        {% if (!instance.hasRemoveButtons || instance.hasRemoveButtons()) { %}\r\n          <button class=\"btn btn-danger btn-sm removeRow\"><i class=\"{{ iconClass('trash') }}\"></i></button>\r\n        {% } %}\r\n      </div>\r\n    </div>\r\n  {% } %}\r\n</div>\r\n",
              "header": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col-sm-2\">{{ t(component.label) }}</div>\r\n    {% } %}\r\n  {% }) %}\r\n  <div class=\"col-sm-2\">Azioni</div>\r\n</div>\r\n"
            },
            "addAnother": "AGGIUNGI RIGA",
            "components": [
              {
                "key": "columns_ds_2",
                "type": "columns",
                "input": false,
                "label": "columns_ds_2",
                "columns": [
                  {
                    "pull": 0,
                    "push": 0,
                    "size": "md",
                    "width": 3,
                    "offset": 0,
                    "components": [
                      {
                        "label": "Progressivo tratto",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "customDefaultValue": "value = 1;\r\ntry {\r\n  const nomeCampo = 'progressivo_tratto';\r\n  const listaTab = data.DATI_STRUTTURALI.presenza_dreni;\r\n  const elementiTab = listaTab.length;\r\n  if (elementiTab > 0) {\r\n    const ultimoElemento = listaTab[elementiTab - 1];\r\n    const ultimoValoreProg = ultimoElemento[nomeCampo];\r\n    const ultimoNumeroProg = Number(ultimoValoreProg);\r\n    if (!isNaN(ultimoNumeroProg)) {\r\n      value = ultimoNumeroProg + 1;\r\n    }\r\n  }\r\n} catch(e) {}",
                        "validate": {
                          "required": true,
                          "customMessage": "Il campo è obbligatorio, univoco e può contenere un massimo di 20 caratteri",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}",
                          "maxLength": 20
                        },
                        "validateWhenHidden": false,
                        "key": "progressivo_tratto",
                        "type": "textfield",
                        "input": true,
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
                        "label": "Profondità da (m)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,3}$)|(^\\d{1,3}[,]\\d{1,2}$)",
                          "customMessage": "Il campo è obbligatorio e può contenere un massimo di 3 cifre intere e 2 cifre decimali",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "profondita_da",
                        "type": "textfield",
                        "input": true,
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
                        "label": "Profondità a (m)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,3}$)|(^\\d{1,3}[,]\\d{1,2}$)",
                          "customMessage": "Il campo è obbligatorio e può contenere un massimo di 3 cifre intere e 2 cifre decimali",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "profondita_a",
                        "type": "textfield",
                        "input": true,
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
                    "components": [],
                    "currentWidth": 3
                  }
                ],
                "tableView": false
              }
            ],
            "displayAsTable": false,
            "validateWhenHidden": false
          },
          {
            "key": "DATI_STRUTTURALI.presenza_filtri",
            "type": "editgrid",
            "input": true,
            "label": "<strong>Tratti di colonna interessati dalla presenza di filtri</strong>",
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
                "key": "columns_ds_2",
                "type": "columns",
                "input": false,
                "label": "columns_ds_2",
                "columns": [
                  {
                    "pull": 0,
                    "push": 0,
                    "size": "md",
                    "width": 3,
                    "offset": 0,
                    "components": [
                      {
                        "label": "Progressivo tratto",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "customDefaultValue": "value = 1;\r\ntry {\r\n  const nomeCampo = 'progressivo_tratto';\r\n  const listaTab = data.DATI_STRUTTURALI.presenza_filtri;\r\n  const elementiTab = listaTab.length;\r\n  if (elementiTab > 0) {\r\n    const ultimoElemento = listaTab[elementiTab - 1];\r\n    const ultimoValoreProg = ultimoElemento[nomeCampo];\r\n    const ultimoNumeroProg = Number(ultimoValoreProg);\r\n    if (!isNaN(ultimoNumeroProg)) {\r\n      value = ultimoNumeroProg + 1;\r\n    }\r\n  }\r\n} catch(e) {}",
                        "validate": {
                          "required": true,
                          "customMessage": "Il campo è obbligatorio, univoco e può contenere un massimo di 20 caratteri",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}",
                          "maxLength": 20
                        },
                        "validateWhenHidden": false,
                        "key": "progressivo_tratto",
                        "type": "textfield",
                        "input": true,
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
                        "key": "tipo_filtro",
                        "data": {
                          "custom": "values = [\n  { id_tipo_filtro: 1, des_tipo_filtro: \"APERTURA CONTINUA\" },\n  { id_tipo_filtro: 2, des_tipo_filtro: \"A FESSURA VERTICALE\" },\n  { id_tipo_filtro: 3, des_tipo_filtro: \"A PONTE\" },\n  { id_tipo_filtro: 4, des_tipo_filtro: \"ALTRO\" }\n];"
                        },
                        "type": "select",
                        "input": true,
                        "label": "Tipologia di filtro",
                        "widget": "choicesjs",
                        "dataSrc": "custom",
                        "template": "<span>{{ item.des_tipo_filtro }}</span>",
                        "validate": {
                          "required": true
                        },
                        "tableView": true,
                        "validateWhenHidden": false
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
                        "label": "Apertura (mm)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,1}$)|(^\\d{1,1}[,]\\d{1,1}$)",
                          "customMessage": "Il campo è obbligatorio e può contenere un massimo di 1 cifra intera e 1 cifra decimale",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "apertura",
                        "type": "textfield",
                        "input": true,
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
                        "label": "Profondità da (m)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,3}$)|(^\\d{1,3}[,]\\d{1,2}$)",
                          "customMessage": "Il campo è obbligatorio e può contenere un massimo di 3 cifre intere e 2 cifre decimali",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "profondita_da",
                        "type": "textfield",
                        "input": true,
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
                    "width": 3,
                    "offset": 0,
                    "components": [
                      {
                        "label": "Profondità a (m)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,3}$)|(^\\d{1,3}[,]\\d{1,2}$)",
                          "customMessage": "Il campo è obbligatorio e può contenere un massimo di 3 cifre intere e 2 cifre decimali",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "profondita_a",
                        "type": "textfield",
                        "input": true,
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
                        "key": "origine_dato",
                        "data": {
                          "custom": "values = [\n  { id_origine_dato: 1, des_origine_dato: \"DICHIARATO\" },\n  { id_origine_dato: 2, des_origine_dato: \"MISURATO\" }\n];"
                        },
                        "type": "select",
                        "input": true,
                        "label": "Origine del dato",
                        "widget": "choicesjs",
                        "dataSrc": "custom",
                        "template": "<span>{{ item.des_origine_dato }}</span>",
                        "validate": {
                          "required": true
                        },
                        "tableView": true,
                        "validateWhenHidden": false
                      }
                    ],
                    "currentWidth": 3
                  },
                  {
                    "size": "md",
                    "width": 3,
                    "components": [],
                    "currentWidth": 3
                  },
                  {
                    "size": "md",
                    "width": 3,
                    "components": [],
                    "currentWidth": 3
                  }
                ],
                "tableView": false
              }
            ],
            "displayAsTable": false,
            "validateWhenHidden": false
          },
          {
            "key": "JS_SAVE_SECTION.DATI_STRUTTURALI",
            "type": "button",
            "event": "saveSectionData",
            "input": true,
            "label": "SALVA MODIFICHE",
            "logic": [
              {
                "name": "JS_SAVE.DATI_STRUTTURALI",
                "actions": [
                  {
                    "name": "disabilitaSalvaModifiche",
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
                  "javascript": "try {\r\n  const data_costruzione = data.DATI_STRUTTURALI.dati_strutturali.data_costruzione;\r\n  const tipo_falda = data.DATI_STRUTTURALI.dati_strutturali.tipo_falda;\r\n  const flg_disp_strat = data.DATI_STRUTTURALI.dati_strutturali.flg_disp_strat;\r\n  const diametro_colonna = data.DATI_STRUTTURALI.diametro_colonna;\r\n  const presenza_cementazione = data.DATI_STRUTTURALI.presenza_cementazione;\r\n  const presenza_dreni = data.DATI_STRUTTURALI.presenza_dreni;\r\n  const presenza_filtri = data.DATI_STRUTTURALI.presenza_filtri;\r\n  \r\n  const datiSezione = {\r\n    data_costruzione,\r\n    tipo_falda,\r\n    flg_disp_strat,\r\n    diametro_colonna,\r\n    presenza_cementazione,\r\n    presenza_dreni,\r\n    presenza_filtri,\r\n  };\r\n  const valid = options.SCRIVA.pozzoUtils.validDatiStrutturali(datiSezione);\r\n  return !valid;\r\n\r\n} catch (e) {\r\n  return true;\r\n}"
                }
              }
            ],
            "theme": "default",
            "action": "event",
            "tableView": false,
            "customClass": "btn-outline-primary float-right",
            "showValidations": false
          }
        ],
        "collapsible": true
      },
      {
        "key": "PROVE_EMUNGIMENTO",
        "type": "panel",
        "input": false,
        "label": "Prove di emungimento",
        "title": "<strong>Prove di emungimento</strong>",
        "collapsed": true,
        "tableView": false,
        "components": [
          {
            "key": "SECTION_REQUIRED.PROVE_EMUNGIMENTO",
            "type": "hidden",
            "input": true,
            "label": "Sezione obbligatoria",
            "tableView": true,
            "defaultValue": "false"
          },
          {
            "key": "title_ds_2",
            "type": "htmlelement",
            "attrs": [
              {
                "attr": "",
                "value": ""
              }
            ],
            "input": false,
            "label": "title_ds_1",
            "content": "<strong>Prove di emungimento</strong>",
            "tableView": false,
            "refreshOnChange": false
          },
          {
            "key": "columns3",
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
                    "key": "PROVE_EMUNGIMENTO.prove_emungimento.flg_disp_mis_piez",
                    "data": {
                      "values": [
                        {
                          "label": "Sì",
                          "value": "sì"
                        },
                        {
                          "label": "No",
                          "value": "no"
                        }
                      ]
                    },
                    "type": "select",
                    "input": true,
                    "label": "Disponibilità di misure piezometriche",
                    "widget": "choicesjs",
                    "validate": {
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
                    "key": "PROVE_EMUNGIMENTO.prove_emungimento.piez_assoc",
                    "type": "textfield",
                    "input": true,
                    "label": "Piezometri associati",
                    "validate": {
                      "pattern": "(^\\d{1,2}$)",
                      "customMessage": "Il campo è obbligatorio e può contenere un massimo di 2 cifre intere"
                    },
                    "tableView": true,
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
                "components": [],
                "currentWidth": 4
              }
            ],
            "tableView": false
          },
          {
            "key": "PROVE_EMUNGIMENTO.emungimento_gradini",
            "type": "editgrid",
            "input": true,
            "label": "<strong>Prove di emungimento a gradini</strong>",
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
                "key": "columns_ds_2",
                "type": "columns",
                "input": false,
                "label": "columns_ds_2",
                "columns": [
                  {
                    "pull": 0,
                    "push": 0,
                    "size": "md",
                    "width": 3,
                    "offset": 0,
                    "components": [
                      {
                        "label": "Progressivo",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "customDefaultValue": "value = 1;\r\ntry {\r\n  const nomeCampo = 'progressivo';\r\n  const listaTab = data.PROVE_EMUNGIMENTO.emungimento_gradini;\r\n  const elementiTab = listaTab.length;\r\n  if (elementiTab > 0) {\r\n    const ultimoElemento = listaTab[elementiTab - 1];\r\n    const ultimoValoreProg = ultimoElemento[nomeCampo];\r\n    const ultimoNumeroProg = Number(ultimoValoreProg);\r\n    if (!isNaN(ultimoNumeroProg)) {\r\n      value = ultimoNumeroProg + 1;\r\n    }\r\n  }\r\n} catch(e) {}",
                        "validate": {
                          "required": true,
                          "customMessage": "Il campo è obbligatorio, univoco e può contenere un massimo di 20 caratteri",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}",
                          "maxLength": 20
                        },
                        "validateWhenHidden": false,
                        "key": "progressivo",
                        "type": "textfield",
                        "input": true,
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
                        "key": "data_misura",
                        "type": "datetime",
                        "input": true,
                        "label": "Data della misura",
                        "format": "dd/MM/yyyy",
                        "widget": {
                          "type": "calendar",
                          "displayInTimezone": "viewer",
                          "locale": "en",
                          "useLocaleSettings": false,
                          "allowInput": true,
                          "mode": "single",
                          "enableTime": false,
                          "noCalendar": false,
                          "format": "dd/MM/yyyy",
                          "hourIncrement": 1,
                          "minuteIncrement": 1,
                          "time_24hr": true,
                          "minDate": null,
                          "disableWeekends": false,
                          "disableWeekdays": false,
                          "maxDate": "moment()"
                        },
                        "validate": {
                          "required": true
                        },
                        "tableView": true,
                        "datePicker": {
                          "maxDate": "moment()",
                          "disableWeekdays": false,
                          "disableWeekends": false
                        },
                        "enableTime": false,
                        "timePicker": {
                          "showMeridian": false
                        },
                        "enableMaxDateInput": true,
                        "enableMinDateInput": false,
                        "validateWhenHidden": false
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
                        "key": "soggetto_esecutore",
                        "type": "textfield",
                        "input": true,
                        "label": "Soggetto esecutore",
                        "validate": {
                          "required": true,
                          "maxLength": 100
                        },
                        "tableView": true,
                        "applyMaskOn": "change",
                        "validateWhenHidden": false
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
                        "label": "Portata critica (l/s)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,5}$)|(^\\d{1,5}[,]\\d{1,5}$)",
                          "customMessage": "Il campo è obbligatorio e può contenere un massimo di 5 cifre intere e 5 cifre decimali",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "portata_critica",
                        "type": "textfield",
                        "input": true,
                        "hideOnChildrenHidden": false
                      }
                    ],
                    "currentWidth": 3
                  }
                ],
                "tableView": false
              }
            ],
            "displayAsTable": false,
            "validateWhenHidden": false
          },
          {
            "key": "PROVE_EMUNGIMENTO.emungimento_lunga_durata",
            "type": "editgrid",
            "input": true,
            "label": "<strong>Prove di emungimento a lunga durata</strong>",
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
                "key": "columns_ds_2",
                "type": "columns",
                "input": false,
                "label": "columns_ds_2",
                "columns": [
                  {
                    "pull": 0,
                    "push": 0,
                    "size": "md",
                    "width": 3,
                    "offset": 0,
                    "components": [
                      {
                        "label": "Progressivo",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "customDefaultValue": "value = 1;\r\ntry {\r\n  const nomeCampo = 'progressivo';\r\n  const listaTab = data.PROVE_EMUNGIMENTO.emungimento_lunga_durata;\r\n  const elementiTab = listaTab.length;\r\n  if (elementiTab > 0) {\r\n    const ultimoElemento = listaTab[elementiTab - 1];\r\n    const ultimoValoreProg = ultimoElemento[nomeCampo];\r\n    const ultimoNumeroProg = Number(ultimoValoreProg);\r\n    if (!isNaN(ultimoNumeroProg)) {\r\n      value = ultimoNumeroProg + 1;\r\n    }\r\n  }\r\n} catch(e) {}",
                        "validate": {
                          "required": true,
                          "customMessage": "Il campo è obbligatorio, univoco e può contenere un massimo di 20 caratteri",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}",
                          "maxLength": 20
                        },
                        "validateWhenHidden": false,
                        "key": "progressivo",
                        "type": "textfield",
                        "input": true,
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
                        "key": "data_misura",
                        "type": "datetime",
                        "input": true,
                        "label": "Data della misura",
                        "format": "dd/MM/yyyy",
                        "widget": {
                          "type": "calendar",
                          "displayInTimezone": "viewer",
                          "locale": "en",
                          "useLocaleSettings": false,
                          "allowInput": true,
                          "mode": "single",
                          "enableTime": false,
                          "noCalendar": false,
                          "format": "dd/MM/yyyy",
                          "hourIncrement": 1,
                          "minuteIncrement": 1,
                          "time_24hr": false,
                          "minDate": null,
                          "disableWeekends": false,
                          "disableWeekdays": false,
                          "maxDate": "moment()"
                        },
                        "validate": {
                          "required": true
                        },
                        "tableView": true,
                        "datePicker": {
                          "maxDate": "moment()",
                          "disableWeekdays": false,
                          "disableWeekends": false
                        },
                        "enableTime": false,
                        "enableMaxDateInput": true,
                        "enableMinDateInput": false,
                        "validateWhenHidden": false
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
                        "key": "soggetto_esecutore",
                        "type": "textfield",
                        "input": true,
                        "label": "Soggetto esecutore",
                        "validate": {
                          "required": true,
                          "maxLength": 100
                        },
                        "tableView": true,
                        "applyMaskOn": "change",
                        "validateWhenHidden": false
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
                        "label": "Portata costante (l/s)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,5}$)|(^\\d{1,5}[,]\\d{1,5}$)",
                          "customMessage": "Il campo è obbligatorio e può contenere un massimo di 5 cifre intere e 5 cifre decimali",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "portata_costante",
                        "type": "textfield",
                        "input": true,
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
                    "width": 3,
                    "offset": 0,
                    "components": [
                      {
                        "label": "Durata (h)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,2}$)|(^\\d{1,2}[,]\\d{1,2}$)",
                          "customMessage": "Il campo è obbligatorio e può contenere un massimo di 2 cifre intere e 2 cifre decimali",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "durata",
                        "type": "textfield",
                        "input": true,
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
                        "label": "Trasmissività (m2/s)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,6}$)|(^\\d{1,6}[,]\\d{1,7}$)",
                          "customMessage": "Il campo è obbligatorio e può contenere un massimo di 6 cifre intere e 7 cifre decimali",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "trasmissivita",
                        "type": "textfield",
                        "input": true,
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
                        "key": "trasmissivita1",
                        "type": "textfield",
                        "input": true,
                        "label": "Coefficiente di immagazzinamento",
                        "validate": {
                          "pattern": "(^\\d{1,2}$)|(^\\d{1,2}[,]\\d{1,7}$)",
                          "customMessage": "Il campo è obbligatorio e può contenere un massimo di 2 cifre intere e 7 cifre decimali"
                        },
                        "tableView": true,
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
                    "components": [],
                    "currentWidth": 3
                  }
                ],
                "tableView": false
              }
            ],
            "displayAsTable": false,
            "validateWhenHidden": false
          },
          {
            "key": "JS_SAVE_SECTION.PROVE_EMUNGIMENTO",
            "type": "button",
            "event": "saveSectionData",
            "input": true,
            "label": "SALVA MODIFICHE",
            "logic": [
              {
                "name": "JS_SAVE.PROVE_EMUNGIMENTO",
                "actions": [
                  {
                    "name": "disabilitaSalvaModifiche",
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
                  "javascript": "try {\r\n  const flg_disp_mis_piez = data.PROVE_EMUNGIMENTO.prove_emungimento.flg_disp_mis_piez;\r\n  const piez_assoc = data.PROVE_EMUNGIMENTO.prove_emungimento.piez_assoc;\r\n  const emungimento_gradini = data.PROVE_EMUNGIMENTO.emungimento_gradini;\r\n  const emungimento_lunga_durata = data.PROVE_EMUNGIMENTO.emungimento_lunga_durata;\r\n  \r\n  const datiSezione = {\r\n    flg_disp_mis_piez,\r\n    piez_assoc,\r\n    emungimento_gradini,\r\n    emungimento_lunga_durata,\r\n  };\r\n  const valid = options.SCRIVA.pozzoUtils.validProveDiEmungimento(datiSezione);\r\n  return !valid;\r\n\r\n} catch (e) {\r\n  return true;\r\n}"
                }
              }
            ],
            "theme": "default",
            "action": "event",
            "tableView": false,
            "customClass": "btn-outline-primary float-right",
            "showValidations": false
          }
        ],
        "collapsible": true
      },
      {
        "key": "POMPE_IDRAULICHE",
        "type": "panel",
        "input": false,
        "label": "Pompe idrauliche",
        "title": "<strong>Pompe idrauliche</strong>",
        "collapsed": true,
        "tableView": false,
        "components": [
          {
            "key": "SECTION_REQUIRED.POMPE_IDRAULICHE",
            "type": "hidden",
            "input": true,
            "label": "Sezione obbligatoria",
            "tableView": true,
            "defaultValue": false
          },
          {
            "key": "POMPE_IDRAULICHE.pompe_idrauliche",
            "type": "editgrid",
            "input": true,
            "label": "<strong>Pompe idrauliche</strong>",
            "saveRow": "CONFERMA",
            "removeRow": "ANNULLA",
            "rowDrafts": false,
            "tableView": false,
            "templates": {
              "row": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col-sm-2\">\r\n        {{ isVisibleInRow(component) ? getView(component, row[component.key]) : ''}}\r\n      </div>\r\n    {% } %}\r\n  {% }) %}\r\n  {% if (!instance.options.readOnly && !instance.disabled) { %}\r\n    <div class=\"col-sm-2\">\r\n      <div class=\"btn-group pull-left\">\r\n        <button class=\"btn btn-default btn-light btn-sm editRow\"><i class=\"{{ iconClass('edit') }}\"></i></button>\r\n        {% if (!instance.hasRemoveButtons || instance.hasRemoveButtons()) { %}\r\n          <button class=\"btn btn-danger btn-sm removeRow\"><i class=\"{{ iconClass('trash') }}\"></i></button>\r\n        {% } %}\r\n      </div>\r\n    </div>\r\n  {% } %}\r\n</div>\r\n",
              "header": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col-sm-2\">{{ t(component.label) }}</div>\r\n    {% } %}\r\n  {% }) %}\r\n  <div class=\"col-sm-2\">Azioni</div>\r\n</div>\r\n"
            },
            "addAnother": "AGGIUNGI POMPA",
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
                    "width": 3,
                    "offset": 0,
                    "components": [
                      {
                        "key": "progressiva",
                        "type": "textfield",
                        "input": true,
                        "label": "Progressiva",
                        "validate": {
                          "maxLength": 20
                        },
                        "tableView": true,
                        "applyMaskOn": "change",
                        "custom": "try {\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (verificaGreatZero <= 0) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}",
                        "customDefaultValue": "value = 1;\r\ntry {\r\n  const nomeCampo = 'progressiva';\r\n  const listaTab = data.POMPE_IDRAULICHE.pompe_idrauliche;\r\n  const elementiTab = listaTab.length;\r\n  if (elementiTab > 0) {\r\n    const ultimoElemento = listaTab[elementiTab - 1];\r\n    const ultimoValoreProg = ultimoElemento[nomeCampo];\r\n    const ultimoNumeroProg = Number(ultimoValoreProg);\r\n    if (!isNaN(ultimoNumeroProg)) {\r\n      value = ultimoNumeroProg + 1;\r\n    }\r\n  }\r\n} catch(e) {}",
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
                        "key": "potenza_motore",
                        "type": "textfield",
                        "input": true,
                        "label": "Potenza del motore (kW)",
                        "validate": {
                          "pattern": "(^\\d{1,5}$)|(^\\d{1,5}[,]\\d{1,2}$)",
                          "customMessage": "Indicare un massimo di 5 cifre intere e 2 cifre decimali"
                        },
                        "tableView": true,
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
                        "key": "portata_max",
                        "type": "textfield",
                        "input": true,
                        "label": "Portata massima estraibile (l/s)",
                        "validate": {
                          "pattern": "(^\\d{1,4}$)|(^\\d{1,4}[,]\\d{1,3}$)",
                          "customMessage": "Indicare un massimo di 4 cifre intere e 3 cifre decimali"
                        },
                        "tableView": true,
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
                        "key": "prevalenza",
                        "type": "textfield",
                        "input": true,
                        "label": "Prevalenza (m)",
                        "validate": {
                          "pattern": "(^\\d{1,4}$)",
                          "customMessage": "Indicare un massimo di 4 cifre intere"
                        },
                        "tableView": true,
                        "applyMaskOn": "change",
                        "validateWhenHidden": false,
                        "hideOnChildrenHidden": false
                      }
                    ],
                    "currentWidth": 3
                  }
                ],
                "tableView": false
              }
            ],
            "displayAsTable": false,
            "validateWhenHidden": false
          },
          {
            "key": "JS_SAVE_SECTION.POMPE_IDRAULICHE",
            "type": "button",
            "event": "saveSectionData",
            "input": true,
            "label": "SALVA MODIFICHE",
            "theme": "default",
            "action": "event",
            "tableView": false,
            "customClass": "btn-outline-primary float-right",
            "showValidations": false
          }
        ],
        "collapsible": true
      }
    ]
  },
  "SORGENTE": {
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
        "content": "<strong>Sorgente {{options.SCRIVA.oggetto.cod_scriva}}</strong>",
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
            "key": "SECTION_REQUIRED.DATI_IDENTIFICATIVI",
            "type": "hidden",
            "input": true,
            "label": "Sezione obbligatoria",
            "tableView": true,
            "defaultValue": true
          },
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
                    "label": "Codice ROC l.r. 22/99",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "customDefaultValue": "value = options.SCRIVA.oggetto.cod_scriva;",
                    "validate": {
                      "maxLength": 10
                    },
                    "validateWhenHidden": false,
                    "key": "DATI_IDENTIFICATIVI.dati_identificativi.codice_roc",
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
                        "name": "verificaCodiceROCProvvisorio",
                        "trigger": {
                          "type": "javascript",
                          "javascript": "// Recupero il codice ROC dai dati del campo\nconst codiceRoc = data.DATI_IDENTIFICATIVI.dati_identificativi.codice_roc;\n// Verifico se il campo inizia con il codice provvisorio generato\nresult = codiceRoc.startsWith(\"SCRV\");"
                        },
                        "actions": [
                          {
                            "name": "nascondiCodiceROCProvvisorio",
                            "type": "property",
                            "property": {
                              "label": "Hidden",
                              "value": "hidden",
                              "type": "boolean"
                            },
                            "state": true
                          }
                        ]
                      }
                    ],
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
                "size": "md",
                "width": 4,
                "components": [
                  {
                    "key": "DATI_IDENTIFICATIVI.dati_identificativi.codice_sii",
                    "type": "textfield",
                    "input": true,
                    "label": "Codice SII",
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
                      "maxLength": 10
                    },
                    "tableView": true,
                    "applyMaskOn": "change",
                    "validateWhenHidden": false
                  }
                ],
                "currentWidth": 4
              }
            ],
            "autoAdjust": true,
            "key": "columns1",
            "type": "columns",
            "input": false,
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
                "width": 8,
                "offset": 0,
                "components": [
                  {
                    "key": "DATI_IDENTIFICATIVI.denominazione",
                    "type": "textfield",
                    "input": true,
                    "label": "Denominazione",
                    "disabled": true,
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
                "currentWidth": 8
              },
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 4,
                "offset": 0,
                "components": [],
                "currentWidth": 4
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
            "key": "columns_di_5",
            "type": "columns",
            "input": false,
            "label": "columns_di_5",
            "columns": [
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 4,
                "offset": 0,
                "components": [
                  {
                    "key": "DATI_IDENTIFICATIVI.altri_elementi_identificativi.quota_piano_c",
                    "type": "textfield",
                    "input": true,
                    "label": "Quota del Piano Campagna s.l.m. (m)",
                    "validate": {
                      "pattern": "(^\\d{1,4}$)",
                      "customMessage": "Inserire un numero intero di massimo 4 cifre"
                    },
                    "tableView": true,
                    "applyMaskOn": "change",
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
            "key": "JS_SAVE_SECTION.DATI_IDENTIFICATIVI",
            "type": "button",
            "event": "saveSectionData",
            "input": true,
            "label": "SALVA MODIFICHE",
            "logic": [
              {
                "name": "JS_SAVE.DATI_IDENTIFICATIVI",
                "actions": [
                  {
                    "name": "disabilitaSalvaModifiche",
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
                  "javascript": "try {\r\n  const codice_roc = data.DATI_IDENTIFICATIVI.dati_identificativi.codice_roc;\r\n  const codice_rilievo = data.DATI_IDENTIFICATIVI.dati_identificativi.codice_rilievo;\r\n  const codice_sii = data.DATI_IDENTIFICATIVI.dati_identificativi.codice_sii;\r\n  const denominazione = data.DATI_IDENTIFICATIVI.denominazione;\r\n  const comune = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.comune;\r\n  const localizzazione = data.DATI_IDENTIFICATIVI.localizzazione;\r\n  const localita = localizzazione ? localizzazione.localita : '';\r\n  const cod_catastale = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.cod_catastale;\r\n  const sezione = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.sezione;\r\n  const foglio = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.foglio;\r\n  const particella = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.particella;\r\n  const quota_piano_c = data.DATI_IDENTIFICATIVI.altri_elementi_identificativi.quota_piano_c;\r\n\r\n  const datiSezione = {\r\n    codice_roc,\r\n    codice_rilievo,\r\n    codice_sii,\r\n    denominazione,\r\n    comune,\r\n    localita,\r\n    cod_catastale,\r\n    sezione,\r\n    foglio,\r\n    particella,\r\n    quota_piano_c\r\n  };\r\n  const valid = options.SCRIVA.sorgenteUtils.validDatiIdentificativi(datiSezione);\r\n  return !valid;\r\n\r\n} catch (e) {\r\n  return true;\r\n}"
                }
              }
            ],
            "theme": "default",
            "action": "event",
            "tableView": false,
            "customClass": "btn-outline-primary float-right",
            "showValidations": false
          }
        ],
        "collapsible": true
      },
      {
        "key": "ESERCIZIO_DELLA_CAPTAZIONE",
        "type": "panel",
        "input": false,
        "label": "Esercizio della captazione",
        "title": "<strong>* Esercizio della captazione</strong>",
        "collapsed": true,
        "tableView": false,
        "components": [
          {
            "key": "SECTION_REQUIRED.ESERCIZIO_DELLA_CAPTAZIONE",
            "type": "hidden",
            "input": true,
            "label": "Sezione obbligatoria",
            "tableView": true,
            "defaultValue": true
          },
          {
            "key": "title_ec_1",
            "type": "htmlelement",
            "input": false,
            "label": "title_ec_1",
            "content": "<strong>Esercizio della captazione</strong>",
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
                    "key": "ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.stato_esercizio",
                    "data": {
                      "custom": "values = [\n { id_stato_esercizio: 1, des_stato_esercizio: 'In uso' },\n  { id_stato_esercizio: 2, des_stato_esercizio: 'Dismesso' }\n]"
                    },
                    "type": "select",
                    "input": true,
                    "label": "Stato di esercizio",
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
                    "widget": "choicesjs",
                    "dataSrc": "custom",
                    "template": "<span>{{ item.des_stato_esercizio }}</span>",
                    "tableView": true,
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
                    "label": "Portata massima derivabile (l/s)",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "validate": {
                      "required": true,
                      "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                      "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                      "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                    },
                    "validateWhenHidden": false,
                    "key": "ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_max_deriv",
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
                    "label": "Portata media annua derivabile (l/s)",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "validate": {
                      "required": true,
                      "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                      "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, inferiore o uguale alla portata massima, con un massimo di 7 cifre intere e 4 cifre decimali.",
                      "custom": "try {\r\n  // Definisco un oggetto con le informazioni da passare per la verifica\r\n  const params = {\r\n    portataMedia: input,\r\n    portataMassima: data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_max_deriv,\r\n    checkZero: true,\r\n  };\r\n  // Recupero la funzione di comodo per la verifica\r\n  const qdrDERCheckPortataMedia = options.SCRIVA.formioQuadri.qdrDERCheckPortataMedia;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const check = qdrDERCheckPortataMedia(params);\r\n  // Verifico il risultato dell'elaborazione sul check\r\n  if (check) {\r\n    // Validazione completa\r\n    return true;\r\n    // #\r\n  } else {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"portataMediaInvalida\";\r\n  }\r\n} catch (e) {}"
                    },
                    "validateWhenHidden": false,
                    "key": "ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_med_deriv",
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
                    "label": "Volume massimo di concessione (m<sup>3</sup>)",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "validate": {
                      "required": true,
                      "pattern": "(^\\d{1,9}$)|(^\\d{1,9}[,]\\d{1,5}$)",
                      "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 9 cifre intere e 5 cifre decimali.",
                      "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                    },
                    "validateWhenHidden": false,
                    "key": "ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.volume_max",
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
            "key": "ESERCIZIO_DELLA_CAPTAZIONE.qta_risorsa_captata",
            "type": "editgrid",
            "input": true,
            "label": "<strong>Quantità di risorsa prelevata dall'opera</strong>",
            "saveRow": "CONFERMA",
            "validate": {
              "required": true,
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
                        "label": "Portata massima istantanea (l/s)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                          "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "portata_max_derivata",
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
                          "custom": "try {\r\n  // Definisco un oggetto con le informazioni da passare per la verifica\r\n  const params = {\r\n    portataMedia: input,\r\n    portataMassima: row.portata_max_derivata,\r\n    checkZero: true,\r\n  };\r\n  // Recupero la funzione di comodo per la verifica\r\n  const qdrDERCheckPortataMedia = options.SCRIVA.formioQuadri.qdrDERCheckPortataMedia;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const check = qdrDERCheckPortataMedia(params);\r\n  // Verifico il risultato dell'elaborazione sul check\r\n  if (check) {\r\n    // Validazione completa\r\n    return true;\r\n    // #\r\n  } else {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"portataMediaInvalida\";\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "portata_med_derivata",
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
                        "label": "Volume massimo annuo (m<sup>3</sup>)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,9}$)|(^\\d{1,9}[,]\\d{1,5}$)",
                          "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 9 cifre intere e 5 cifre decimali.",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "volume_max_concessione",
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
            "key": "JS_SAVE_SECTION.ESERCIZIO_DELLA_CAPTAZIONE",
            "type": "button",
            "event": "saveSectionData",
            "input": true,
            "label": "SALVA MODIFICHE",
            "logic": [
              {
                "name": "JS_SAVE.ESERCIZIO_DELLA_CAPTAZIONE",
                "actions": [
                  {
                    "name": "disabilitaSalvaModifiche",
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
                  "javascript": "try {\r\n  const stato_esercizio = data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.stato_esercizio;\r\n  const portata_max_deriv = data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_max_deriv;\r\n  const portata_med_deriv = data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_med_deriv;\r\n  const volume_max_concessione = data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.volume_max;\r\n  const qta_risorsa_captata = data.ESERCIZIO_DELLA_CAPTAZIONE.qta_risorsa_captata;\r\n\r\n  const datiSezione = {\r\n    stato_esercizio,\r\n    portata_max_deriv,\r\n    portata_med_deriv,\r\n    volume_max_concessione,\r\n    qta_risorsa_captata\r\n  };\r\n  const valid = options.SCRIVA.sorgenteUtils.validEsercizioDellaCaptazione(datiSezione);\r\n  return !valid;\r\n\r\n} catch (e) {\r\n  return true;\r\n}"
                }
              }
            ],
            "theme": "default",
            "action": "event",
            "tableView": false,
            "customClass": "btn-outline-primary float-right",
            "showValidations": false
          }
        ],
        "collapsible": true
      },
      {
        "key": "RILASCIO_A_VALLE_DELLA_PRESA",
        "type": "panel",
        "input": false,
        "label": "Rilascio a valle della presa",
        "title": "<strong>* Rilascio a valle della presa</strong>",
        "collapsed": true,
        "tableView": false,
        "components": [
          {
            "key": "SECTION_REQUIRED.RILASCIO_A_VALLE_DELLA_PRESA",
            "type": "hidden",
            "input": true,
            "label": "Sezione obbligatoria",
            "tableView": true,
            "defaultValue": true
          },
          {
            "key": "title_rv_1",
            "type": "htmlelement",
            "input": false,
            "label": "title_rv_1",
            "content": "<strong>Rilascio a valle della presa</strong>",
            "tableView": false,
            "refreshOnChange": false
          },
          {
            "key": "columns_rv_1",
            "type": "columns",
            "input": false,
            "label": "columns_rv_1",
            "columns": [
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 4,
                "offset": 0,
                "components": [
                  {
                    "key": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_dispositivi",
                    "data": {
                      "values": [
                        {
                          "label": "Sì",
                          "value": "si"
                        },
                        {
                          "label": "No",
                          "value": "no"
                        }
                      ]
                    },
                    "type": "select",
                    "input": true,
                    "label": "Presenza dispositivi per il rilascio",
                    "widget": "choicesjs",
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
            "key": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_imposto",
            "type": "editgrid",
            "input": true,
            "label": "<strong>Rilascio imposto</strong>",
            "saveRow": "CONFERMA",
            "removeRow": "ANNULLA",
            "rowDrafts": false,
            "tableView": false,
            "templates": {
              "row": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col-sm-2\">\r\n        {{ isVisibleInRow(component) ? getView(component, row[component.key]) : ''}}\r\n      </div>\r\n    {% } %}\r\n  {% }) %}\r\n  {% if (!instance.options.readOnly && !instance.disabled) { %}\r\n    <div class=\"col-sm-2\">\r\n      <div class=\"btn-group pull-left\">\r\n        <button class=\"btn btn-default btn-light btn-sm editRow\"><i class=\"{{ iconClass('edit') }}\"></i></button>\r\n        {% if (!instance.hasRemoveButtons || instance.hasRemoveButtons()) { %}\r\n          <button class=\"btn btn-danger btn-sm removeRow\"><i class=\"{{ iconClass('trash') }}\"></i></button>\r\n        {% } %}\r\n      </div>\r\n    </div>\r\n  {% } %}\r\n</div>\r\n",
              "header": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col-sm-2\">{{ t(component.label) }}</div>\r\n    {% } %}\r\n  {% }) %}\r\n  <div class=\"col-sm-2\">Azioni</div>\r\n</div>\r\n"
            },
            "addAnother": "AGGIUNGI PERIODO",
            "components": [
              {
                "key": "columns_rv_2",
                "type": "columns",
                "input": false,
                "label": "columns_rv_2",
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
                    "pull": 0,
                    "push": 0,
                    "size": "md",
                    "width": 3,
                    "offset": 0,
                    "components": [
                      {
                        "label": "Portata (l/s)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                          "customMessage": "Il campo è obbligatorio e può contenere un massimo di 7 cifre intere e 4 cifre decimali",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "portata",
                        "type": "textfield",
                        "input": true,
                        "hideOnChildrenHidden": false
                      }
                    ],
                    "currentWidth": 3
                  }
                ],
                "tableView": false
              }
            ],
            "conditional": {
              "eq": "si",
              "show": true,
              "when": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_dispositivi"
            },
            "displayAsTable": false,
            "validateWhenHidden": false
          },
          {
            "key": "columns_rv_2",
            "type": "columns",
            "input": false,
            "label": "columns_rv_2",
            "columns": [
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 4,
                "offset": 0,
                "components": [
                  {
                    "key": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_de",
                    "data": {
                      "values": [
                        {
                          "label": "Sì",
                          "value": "si"
                        },
                        {
                          "label": "No",
                          "value": "no"
                        }
                      ]
                    },
                    "type": "select",
                    "input": true,
                    "label": "DE modulato",
                    "logic": [
                      {
                        "name": "isRequired",
                        "actions": [
                          {
                            "name": "setRequired",
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
                          "type": "simple",
                          "simple": {
                            "eq": "si",
                            "show": true,
                            "when": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_dispositivi"
                          }
                        }
                      },
                      {
                        "name": "isDisabled",
                        "actions": [
                          {
                            "name": "setDisabled",
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
                          "javascript": "result = false;\n\ntry {\n  const rilascioVallePresa = data.RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa;\n  const flg_dispositivi = rilascioVallePresa.flg_dispositivi;\n  const check1 = !flg_dispositivi || flg_dispositivi == 'no';\n  result = check1;\n} catch(e) {}"
                        }
                      }
                    ],
                    "widget": "choicesjs",
                    "refreshOn": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_dispositivi",
                    "tableView": true,
                    "conditional": {
                      "eq": "si",
                      "show": true,
                      "when": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_dispositivi"
                    },
                    "placeholder": "Seleziona",
                    "searchEnabled": false,
                    "clearOnRefresh": true,
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
                    "key": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.modulazione",
                    "data": {
                      "custom": "values = [\n { id_modulazione: 1, des_modulazione: 'Tipo A 10%' },\n  { id_modulazione: 2, des_modulazione: 'Tipo A 20%' },\n  { id_modulazione: 3, des_modulazione: 'Tipo B' },  \n]"
                    },
                    "type": "select",
                    "input": true,
                    "label": "Tipologia di modulazione",
                    "logic": [
                      {
                        "name": "isRequired",
                        "actions": [
                          {
                            "name": "setRequired",
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
                          "type": "simple",
                          "simple": {
                            "eq": "si",
                            "show": true,
                            "when": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_de"
                          }
                        }
                      },
                      {
                        "name": "isDisabled",
                        "actions": [
                          {
                            "name": "setDisabled",
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
                          "javascript": "result = false;\n\ntry {\n  const rilascioVallePresa = data.RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa;\n  const flg_dispositivi = rilascioVallePresa.flg_dispositivi;\n  const check1 = !flg_dispositivi || flg_dispositivi == 'no';\n  const flg_de = rilascioVallePresa.flg_de;\n  const check2 = !flg_de || flg_de == 'no';\n  result = check1 || check2;\n} catch(e) {}"
                        }
                      }
                    ],
                    "widget": "choicesjs",
                    "dataSrc": "custom",
                    "template": "<span>{{ item.des_modulazione }}</span>",
                    "refreshOn": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_de",
                    "tableView": true,
                    "conditional": {
                      "eq": "si",
                      "show": true,
                      "when": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_de"
                    },
                    "placeholder": "Seleziona",
                    "searchEnabled": false,
                    "clearOnRefresh": true,
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
                "components": [],
                "currentWidth": 4
              }
            ],
            "tableView": false
          },
          {
            "key": "columnsRv4",
            "type": "columns",
            "input": false,
            "label": "Columns_rv4",
            "columns": [
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 4,
                "offset": 0,
                "components": [
                  {
                    "key": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_deroga_de",
                    "data": {
                      "values": [
                        {
                          "label": "Sì",
                          "value": "si"
                        },
                        {
                          "label": "No",
                          "value": "no"
                        }
                      ]
                    },
                    "type": "select",
                    "input": true,
                    "label": "In deroga al DE",
                    "widget": "choicesjs",
                    "validate": {
                      "required": true
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
                "width": 6,
                "offset": 0,
                "components": [],
                "currentWidth": 6
              }
            ],
            "tableView": false
          },
          {
            "key": "columns_rv_3",
            "type": "columns",
            "input": false,
            "label": "columns_rv_3",
            "columns": [
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 8,
                "offset": 0,
                "components": [
                  {
                    "key": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.ulteriori_obblighi",
                    "rows": 5,
                    "type": "textarea",
                    "input": true,
                    "label": "Ulteriori obblighi",
                    "validate": {
                      "maxLength": 4000,
                      "customMessage": "Il campo può contenere un massimo di 4000 caratteri"
                    },
                    "tableView": true,
                    "autoExpand": true,
                    "applyMaskOn": "change",
                    "validateWhenHidden": false,
                    "hideOnChildrenHidden": false
                  }
                ],
                "currentWidth": 8
              },
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 4,
                "offset": 0,
                "components": [],
                "currentWidth": 4
              }
            ],
            "tableView": false
          },
          {
            "key": "JS_SAVE_SECTION.RILASCIO_A_VALLE_DELLA_PRESA",
            "type": "button",
            "event": "saveSectionData",
            "input": true,
            "label": "SALVA MODIFICHE",
            "logic": [
              {
                "name": "JS_SAVE.RILASCIO_A_VALLE_DELLA_PRESA",
                "actions": [
                  {
                    "name": "disabilitaSalvaModifiche",
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
                  "javascript": "try {\r\n  const flg_dispositivi = data.RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_dispositivi;\r\n  const rilascio_imposto = data.RILASCIO_A_VALLE_DELLA_PRESA.rilascio_imposto;\r\n  const flg_de = data.RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_de;\r\n  const modulazione = data.RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.modulazione;\r\n  const flg_deroga_de = data.RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_deroga_de;\r\n  const ulteriori_obblighi = data.RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.ulteriori_obblighi;\r\n\r\n  const datiSezione = {\r\n    flg_dispositivi,\r\n    rilascio_imposto,\r\n    flg_de,\r\n    modulazione,\r\n    flg_deroga_de,\r\n    ulteriori_obblighi\r\n  };\r\n  const valid = options.SCRIVA.sorgenteUtils.validRilascioAValle(datiSezione);\r\n  return !valid;\r\n\r\n} catch (e) {\r\n  return true;\r\n}"
                }
              }
            ],
            "theme": "default",
            "action": "event",
            "tableView": false,
            "customClass": "btn-outline-primary float-right",
            "showValidations": false
          }
        ],
        "collapsible": true
      },
      {
        "key": "DATI_SPECIFICI_DELLA_SORGENTE",
        "type": "panel",
        "input": false,
        "label": "Dati infrastrutturali",
        "title": "<strong>* Dati specifici della sorgente</strong>",
        "collapsed": true,
        "tableView": false,
        "components": [
          {
            "key": "SECTION_REQUIRED.DATI_SPECIFICI_DELLA_SORGENTE",
            "type": "hidden",
            "input": true,
            "label": "Sezione obbligatoria",
            "tableView": true,
            "defaultValue": "true"
          },
          {
            "key": "columns_inf_1",
            "type": "columns",
            "input": false,
            "label": "columns_inf_1",
            "columns": [
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 4,
                "offset": 0,
                "components": [
                  {
                    "key": "DATI_SPECIFICI_DELLA_SORGENTE.dati_specifici_della_sorgente.tipo_sorg",
                    "data": {
                      "custom": "values = [\n { id_tipo_sorg: 1, des_tipo_sorg: 'In rocce fratturate' },\n  { id_tipo_sorg: 2, des_tipo_sorg: 'Carsica' },\n  { id_tipo_sorg: 3, des_tipo_sorg: 'Da acquifero poroso' },\n  { id_tipo_sorg: 4, des_tipo_sorg: 'Termale' },\n];"
                    },
                    "type": "select",
                    "input": true,
                    "label": "Tipo di sorgente",
                    "widget": "choicesjs",
                    "dataSrc": "custom",
                    "template": "<span>{{ item.des_tipo_sorg }}</span>",
                    "validate": {
                      "required": true
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
                    "key": "DATI_SPECIFICI_DELLA_SORGENTE.dati_specifici_della_sorgente.flg_tributaria",
                    "data": {
                      "values": [
                        {
                          "label": "Sì",
                          "value": "si"
                        },
                        {
                          "label": "No",
                          "value": "no"
                        }
                      ]
                    },
                    "type": "select",
                    "input": true,
                    "label": "Sorgente tributaria di corso d’acqua",
                    "widget": "choicesjs",
                    "validate": {
                      "required": true
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
                "components": [],
                "currentWidth": 4
              }
            ],
            "tableView": false
          },
          {
            "key": "JS_SAVE_SECTION.DATI_SPECIFICI_DELLA_SORGENTE",
            "type": "button",
            "event": "saveSectionData",
            "input": true,
            "label": "SALVA MODIFICHE",
            "logic": [
              {
                "name": "JS_SAVE.DATI_SPECIFICI_DELLA_SORGENTE",
                "actions": [
                  {
                    "name": "disabilitaSalvaModifiche",
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
                  "javascript": "try {\r\n  const tipo_sorg = data.DATI_SPECIFICI_DELLA_SORGENTE.dati_specifici_della_sorgente.tipo_sorg;\r\n  const flg_tributaria = data.DATI_SPECIFICI_DELLA_SORGENTE.dati_specifici_della_sorgente.flg_tributaria;\r\n\r\n  const datiSezione = {\r\n    tipo_sorg,\r\n    flg_tributaria\r\n  };\r\n  const valid = options.SCRIVA.sorgenteUtils.validDatiSpecifici(datiSezione);\r\n  return !valid;\r\n\r\n} catch (e) {\r\n  return true;\r\n}"
                }
              }
            ],
            "theme": "default",
            "action": "event",
            "tableView": false,
            "customClass": "btn-outline-primary float-right",
            "showValidations": false
          }
        ],
        "collapsible": true
      }
    ]
  },
  "FONTANILE": {
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
        "content": "<strong>Fontanile {{options.SCRIVA.oggetto.cod_scriva}}</strong>",
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
            "key": "SECTION_REQUIRED.DATI_IDENTIFICATIVI",
            "type": "hidden",
            "input": true,
            "label": "Sezione obbligatoria",
            "tableView": true,
            "defaultValue": true
          },
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
                    "label": "Codice ROC l.r. 22/99",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "customDefaultValue": "value = options.SCRIVA.oggetto.cod_scriva;",
                    "validate": {
                      "maxLength": 10
                    },
                    "validateWhenHidden": false,
                    "key": "DATI_IDENTIFICATIVI.dati_identificativi.codice_roc",
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
                        "name": "verificaCodiceROCProvvisorio",
                        "trigger": {
                          "type": "javascript",
                          "javascript": "// Recupero il codice ROC dai dati del campo\nconst codiceRoc = data.DATI_IDENTIFICATIVI.dati_identificativi.codice_roc;\n// Verifico se il campo inizia con il codice provvisorio generato\nresult = codiceRoc.startsWith(\"SCRV\");"
                        },
                        "actions": [
                          {
                            "name": "nascondiCodiceROCProvvisorio",
                            "type": "property",
                            "property": {
                              "label": "Hidden",
                              "value": "hidden",
                              "type": "boolean"
                            },
                            "state": true
                          }
                        ]
                      }
                    ],
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
                "size": "md",
                "width": 4,
                "components": [],
                "currentWidth": 4
              }
            ],
            "autoAdjust": true,
            "key": "columns1",
            "type": "columns",
            "input": false,
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
                "width": 8,
                "offset": 0,
                "components": [
                  {
                    "key": "DATI_IDENTIFICATIVI.denominazione",
                    "type": "textfield",
                    "input": true,
                    "label": "Denominazione",
                    "disabled": true,
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
                "currentWidth": 8
              },
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 4,
                "offset": 0,
                "components": [],
                "currentWidth": 4
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
            "key": "JS_SAVE_SECTION.DATI_IDENTIFICATIVI",
            "type": "button",
            "event": "saveSectionData",
            "input": true,
            "label": "SALVA MODIFICHE",
            "logic": [
              {
                "name": "JS_SAVE.DATI_IDENTIFICATIVI",
                "actions": [
                  {
                    "name": "disabilitaSalvaModifiche",
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
                  "javascript": "try {\r\n  const codice_roc = data.DATI_IDENTIFICATIVI.dati_identificativi.codice_roc;\r\n  const codice_rilievo = data.DATI_IDENTIFICATIVI.dati_identificativi.codice_rilievo;\r\n  const denominazione = data.DATI_IDENTIFICATIVI.denominazione;\r\n  const comune = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.comune;\r\n  const localizzazione = data.DATI_IDENTIFICATIVI.localizzazione;\r\n  const localita = localizzazione ? localizzazione.localita : '';\r\n  const cod_catastale = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.cod_catastale;\r\n  const sezione = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.sezione;\r\n  const foglio = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.foglio;\r\n  const particella = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.particella;\r\n  \r\n  const datiSezione = {\r\n    codice_roc,\r\n    codice_rilievo,\r\n    denominazione,\r\n    comune,\r\n    localita,\r\n    cod_catastale,\r\n    sezione,\r\n    foglio,\r\n    particella\r\n  };\r\n  const valid = options.SCRIVA.fontanileUtils.validDatiIdentificativi(datiSezione);\r\n  return !valid;\r\n\r\n} catch (e) {\r\n  return true;\r\n}"
                }
              }
            ],
            "theme": "default",
            "action": "event",
            "tableView": false,
            "customClass": "btn-outline-primary float-right",
            "showValidations": false
          }
        ],
        "collapsible": true
      },
      {
        "key": "ESERCIZIO_DELLA_CAPTAZIONE",
        "type": "panel",
        "input": false,
        "label": "Esercizio della captazione",
        "title": "<strong>* Esercizio della captazione</strong>",
        "collapsed": true,
        "tableView": false,
        "components": [
          {
            "key": "SECTION_REQUIRED.ESERCIZIO_DELLA_CAPTAZIONE",
            "type": "hidden",
            "input": true,
            "label": "Sezione obbligatoria",
            "tableView": true,
            "defaultValue": true
          },
          {
            "key": "title_ec_1",
            "type": "htmlelement",
            "input": false,
            "label": "title_ec_1",
            "content": "<strong>Esercizio della captazione</strong>",
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
                    "key": "ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.stato_esercizio",
                    "data": {
                      "custom": "values = [\n { id_stato_esercizio: 1, des_stato_esercizio: 'In uso' },\n  { id_stato_esercizio: 2, des_stato_esercizio: 'Dismesso' }\n]"
                    },
                    "type": "select",
                    "input": true,
                    "label": "Stato di esercizio",
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
                    "widget": "choicesjs",
                    "dataSrc": "custom",
                    "template": "<span>{{ item.des_stato_esercizio }}</span>",
                    "tableView": true,
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
                    "label": "Portata massima derivabile (l/s)",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "validate": {
                      "required": true,
                      "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                      "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                      "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                    },
                    "validateWhenHidden": false,
                    "key": "ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_max_deriv",
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
                    "label": "Portata media annua derivabile (l/s)",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "validate": {
                      "required": true,
                      "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                      "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, inferiore o uguale alla portata massima, con un massimo di 7 cifre intere e 4 cifre decimali.",
                      "custom": "try {\r\n  // Definisco un oggetto con le informazioni da passare per la verifica\r\n  const params = {\r\n    portataMedia: input,\r\n    portataMassima: data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_max_deriv,\r\n    checkZero: true,\r\n  };\r\n  // Recupero la funzione di comodo per la verifica\r\n  const qdrDERCheckPortataMedia = options.SCRIVA.formioQuadri.qdrDERCheckPortataMedia;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const check = qdrDERCheckPortataMedia(params);\r\n  // Verifico il risultato dell'elaborazione sul check\r\n  if (check) {\r\n    // Validazione completa\r\n    return true;\r\n    // #\r\n  } else {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"portataMediaInvalida\";\r\n  }\r\n} catch (e) {}"
                    },
                    "validateWhenHidden": false,
                    "key": "ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_med_deriv",
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
                    "label": "Volume massimo di concessione (m<sup>3</sup>)",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "validate": {
                      "required": true,
                      "pattern": "(^\\d{1,9}$)|(^\\d{1,9}[,]\\d{1,5}$)",
                      "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 9 cifre intere e 5 cifre decimali.",
                      "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                    },
                    "validateWhenHidden": false,
                    "key": "ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.volume_max",
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
            "key": "ESERCIZIO_DELLA_CAPTAZIONE.qta_risorsa_captata",
            "type": "editgrid",
            "input": true,
            "label": "<strong>Quantità di risorsa prelevata dall'opera</strong>",
            "saveRow": "CONFERMA",
            "validate": {
              "required": true,
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
                        "label": "Portata massima istantanea (l/s)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                          "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "portata_max_derivata",
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
                          "custom": "try {\r\n  // Definisco un oggetto con le informazioni da passare per la verifica\r\n  const params = {\r\n    portataMedia: input,\r\n    portataMassima: row.portata_max_derivata,\r\n    checkZero: true,\r\n  };\r\n  // Recupero la funzione di comodo per la verifica\r\n  const qdrDERCheckPortataMedia = options.SCRIVA.formioQuadri.qdrDERCheckPortataMedia;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const check = qdrDERCheckPortataMedia(params);\r\n  // Verifico il risultato dell'elaborazione sul check\r\n  if (check) {\r\n    // Validazione completa\r\n    return true;\r\n    // #\r\n  } else {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"portataMediaInvalida\";\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "portata_med_derivata",
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
                        "label": "Volume massimo annuo (m<sup>3</sup>)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,9}$)|(^\\d{1,9}[,]\\d{1,5}$)",
                          "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 9 cifre intere e 5 cifre decimali.",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "volume_max_concessione",
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
            "key": "JS_SAVE_SECTION.ESERCIZIO_DELLA_CAPTAZIONE",
            "type": "button",
            "event": "saveSectionData",
            "input": true,
            "label": "SALVA MODIFICHE",
            "logic": [
              {
                "name": "JS_SAVE.ESERCIZIO_DELLA_CAPTAZIONE",
                "actions": [
                  {
                    "name": "disabilitaSalvaModifiche",
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
                  "javascript": "try {\r\n  const stato_esercizio = data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.stato_esercizio;\r\n  const portata_max_deriv = data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_max_deriv;\r\n  const portata_med_deriv = data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_med_deriv;\r\n  const volume_max_concessione = data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.volume_max;\r\n  const qta_risorsa_captata = data.ESERCIZIO_DELLA_CAPTAZIONE.qta_risorsa_captata;\r\n\r\n  const datiSezione = {\r\n    stato_esercizio,\r\n    portata_max_deriv,\r\n    portata_med_deriv,\r\n    volume_max_concessione,\r\n    qta_risorsa_captata\r\n  };\r\n  const valid = options.SCRIVA.fontanileUtils.validEsercizioDellaCaptazione(datiSezione);\r\n  return !valid;\r\n\r\n} catch (e) {\r\n  return true;\r\n}"
                }
              }
            ],
            "theme": "default",
            "action": "event",
            "tableView": false,
            "customClass": "btn-outline-primary float-right",
            "showValidations": false
          }
        ],
        "collapsible": true
      }
    ]
  },
  "TRINCEA_DRENANTE": {
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
        "content": "<strong>Trincea drenante {{options.SCRIVA.oggetto.cod_scriva}}</strong>",
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
            "key": "SECTION_REQUIRED.DATI_IDENTIFICATIVI",
            "type": "hidden",
            "input": true,
            "label": "Sezione obbligatoria",
            "tableView": true,
            "defaultValue": true
          },
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
                    "key": "DATI_IDENTIFICATIVI.dati_identificativi.codice_roc",
                    "type": "textfield",
                    "input": true,
                    "label": "Codice ROC l.r. 22/99",
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
                        "name": "verificaCodiceROCProvvisorio",
                        "trigger": {
                          "type": "javascript",
                          "javascript": "// Recupero il codice ROC dai dati del campo\nconst codiceRoc = data.DATI_IDENTIFICATIVI.dati_identificativi.codice_roc;\n// Verifico se il campo inizia con il codice provvisorio generato\nresult = codiceRoc.startsWith(\"SCRV\");"
                        },
                        "actions": [
                          {
                            "name": "nascondiCodiceROCProvvisorio",
                            "type": "property",
                            "property": {
                              "label": "Hidden",
                              "value": "hidden",
                              "type": "boolean"
                            },
                            "state": true
                          }
                        ]
                      }
                    ],
                    "validate": {
                      "maxLength": 10
                    },
                    "tableView": true,
                    "applyMaskOn": "change",
                    "customDefaultValue": "value = options.SCRIVA.oggetto.cod_scriva;",
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
                "size": "md",
                "width": 4,
                "components": [],
                "currentWidth": 4
              }
            ],
            "autoAdjust": true,
            "key": "columns1",
            "type": "columns",
            "input": false,
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
                "width": 8,
                "offset": 0,
                "components": [
                  {
                    "key": "DATI_IDENTIFICATIVI.denominazione",
                    "type": "textfield",
                    "input": true,
                    "label": "Denominazione",
                    "disabled": true,
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
                "currentWidth": 8
              },
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 4,
                "offset": 0,
                "components": [],
                "currentWidth": 4
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
            "key": "columns",
            "type": "columns",
            "input": false,
            "label": "Columns",
            "columns": [
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 3,
                "offset": 0,
                "components": [
                  {
                    "label": "Sviluppo longitudinale (m)",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "validate": {
                      "required": true,
                      "pattern": "(^\\d{1,4}$)",
                      "customMessage": "Il campo è obbligatorio e può contenere un massimo di 4 cifre intere",
                      "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                    },
                    "validateWhenHidden": false,
                    "key": "DATI_IDENTIFICATIVI.altri_elementi_identificativi.sviluppo_long",
                    "type": "textfield",
                    "input": true,
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
                    "label": "Inclinazione",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "validate": {
                      "required": true,
                      "pattern": "(^\\d{1,2}$)",
                      "customMessage": "Il campo è obbligatorio e può contenere un massimo di 2 cifre intere",
                      "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                    },
                    "validateWhenHidden": false,
                    "key": "DATI_IDENTIFICATIVI.altri_elementi_identificativi.inclinazione",
                    "type": "textfield",
                    "input": true,
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
                    "label": "Profondità max (m)",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "validate": {
                      "required": true,
                      "pattern": "(^\\d{1,2}$)|(^\\d{1,2}[,]\\d{1,2}$)",
                      "customMessage": "Il campo è obbligatorio e può contenere un massimo di 2 cifre intere e 2 cifre decimali",
                      "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                    },
                    "validateWhenHidden": false,
                    "key": "DATI_IDENTIFICATIVI.altri_elementi_identificativi.profondita_max",
                    "type": "textfield",
                    "input": true,
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
                    "label": "Profondità min (m)",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "validate": {
                      "required": true,
                      "pattern": "(^\\d{1,2}$)|(^\\d{1,2}[,]\\d{1,2}$)",
                      "customMessage": "Il campo è obbligatorio e può contenere un massimo di 2 cifre intere e 2 cifre decimali",
                      "custom": "try {\r\n  // Definisco un oggetto con le informazioni da passare per la verifica\r\n  const params = {\r\n    minimo: input,\r\n    massimo: data.DATI_IDENTIFICATIVI.altri_elementi_identificativi.profondita_max,\r\n    checkZero: true,\r\n\t  massimoNotZero: true,\r\n  };\r\n  // Recupero la funzione di comodo per la verifica\r\n  const qdrCheckMinimoMassimo = options.SCRIVA.formioQuadri.qdrCheckMinimoMassimo;\r\n  // Recupero le informazioni per minimo e massimo\r\n  const check = qdrCheckMinimoMassimo(params);\r\n  // Verifico il risultato dell'elaborazione sul check\r\n  if (check) {\r\n    // Validazione completa\r\n    return true;\r\n    // #\r\n  } else {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"minimoMassimoInvalido\";\r\n  }\r\n} catch (e) {}"
                    },
                    "validateWhenHidden": false,
                    "key": "DATI_IDENTIFICATIVI.altri_elementi_identificativi.profondita_min",
                    "type": "textfield",
                    "input": true,
                    "hideOnChildrenHidden": false
                  }
                ],
                "currentWidth": 3
              }
            ],
            "tableView": false
          },
          {
            "key": "JS_SAVE_SECTION.DATI_IDENTIFICATIVI",
            "type": "button",
            "event": "saveSectionData",
            "input": true,
            "label": "SALVA MODIFICHE",
            "logic": [
              {
                "name": "JS_SAVE.DATI_IDENTIFICATIVI",
                "actions": [
                  {
                    "name": "disabilitaSalvaModifiche",
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
                  "javascript": "try {\r\n  const codice_roc = data.DATI_IDENTIFICATIVI.dati_identificativi.codice_roc;\r\n  const codice_rilievo = data.DATI_IDENTIFICATIVI.dati_identificativi.codice_rilievo;\r\n  const denominazione = data.DATI_IDENTIFICATIVI.denominazione;\r\n  const comune = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.comune;\r\n  const localizzazione = data.DATI_IDENTIFICATIVI.localizzazione;\r\n  const localita = localizzazione ? localizzazione.localita : '';\r\n  const cod_catastale = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.cod_catastale;\r\n  const sezione = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.sezione;\r\n  const foglio = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.foglio;\r\n  const particella = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.particella;\r\n  const sviluppo_long = data.DATI_IDENTIFICATIVI.altri_elementi_identificativi.sviluppo_long;\r\n  const inclinazione = data.DATI_IDENTIFICATIVI.altri_elementi_identificativi.inclinazione;\r\n  const profondita_max = data.DATI_IDENTIFICATIVI.altri_elementi_identificativi.profondita_max;\r\n  const profondita_min = data.DATI_IDENTIFICATIVI.altri_elementi_identificativi.profondita_min;\r\n  \r\n  const datiSezione = {\r\n    codice_roc,\r\n    codice_rilievo,\r\n    denominazione,\r\n    comune,\r\n    localita,\r\n    cod_catastale,\r\n    sezione,\r\n    foglio,\r\n    particella,\r\n    sviluppo_long,\r\n    inclinazione,\r\n    profondita_max,\r\n    profondita_min,\r\n  };\r\n  const valid = options.SCRIVA.trinceaDrenanteUtils.validDatiIdentificativi(datiSezione);\r\n  return !valid;\r\n\r\n} catch (e) {\r\n  return true;\r\n}"
                }
              }
            ],
            "theme": "default",
            "action": "event",
            "tableView": false,
            "customClass": "btn-outline-primary float-right",
            "showValidations": false
          }
        ],
        "collapsible": true
      },
      {
        "key": "ESERCIZIO_DELLA_CAPTAZIONE",
        "type": "panel",
        "input": false,
        "label": "Esercizio della captazione",
        "title": "<strong>* Esercizio della captazione</strong>",
        "collapsed": true,
        "tableView": false,
        "components": [
          {
            "key": "SECTION_REQUIRED.ESERCIZIO_DELLA_CAPTAZIONE",
            "type": "hidden",
            "input": true,
            "label": "Sezione obbligatoria",
            "tableView": true,
            "defaultValue": true
          },
          {
            "key": "title_ec_1",
            "type": "htmlelement",
            "input": false,
            "label": "title_ec_1",
            "content": "<strong>Esercizio della captazione</strong>",
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
                    "key": "ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.stato_esercizio",
                    "data": {
                      "custom": "values = [\n { id_stato_esercizio: 1, des_stato_esercizio: 'In uso' },\n  { id_stato_esercizio: 2, des_stato_esercizio: 'Dismesso' }\n]"
                    },
                    "type": "select",
                    "input": true,
                    "label": "Stato di esercizio",
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
                    "widget": "choicesjs",
                    "dataSrc": "custom",
                    "template": "<span>{{ item.des_stato_esercizio }}</span>",
                    "tableView": true,
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
                    "label": "Portata massima derivabile (l/s)",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "validate": {
                      "required": true,
                      "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                      "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                      "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                    },
                    "validateWhenHidden": false,
                    "key": "ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_max_deriv",
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
                    "label": "Portata media annua derivabile (l/s)",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "validate": {
                      "required": true,
                      "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                      "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, inferiore o uguale alla portata massima, con un massimo di 7 cifre intere e 4 cifre decimali.",
                      "custom": "try {\r\n  // Definisco un oggetto con le informazioni da passare per la verifica\r\n  const params = {\r\n    portataMedia: input,\r\n    portataMassima: data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_max_deriv,\r\n    checkZero: true,\r\n  };\r\n  // Recupero la funzione di comodo per la verifica\r\n  const qdrDERCheckPortataMedia = options.SCRIVA.formioQuadri.qdrDERCheckPortataMedia;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const check = qdrDERCheckPortataMedia(params);\r\n  // Verifico il risultato dell'elaborazione sul check\r\n  if (check) {\r\n    // Validazione completa\r\n    return true;\r\n    // #\r\n  } else {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"portataMediaInvalida\";\r\n  }\r\n} catch (e) {}"
                    },
                    "validateWhenHidden": false,
                    "key": "ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_med_deriv",
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
                    "label": "Volume massimo di concessione (m<sup>3</sup>)",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "validate": {
                      "required": true,
                      "pattern": "(^\\d{1,9}$)|(^\\d{1,9}[,]\\d{1,5}$)",
                      "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 9 cifre intere e 5 cifre decimali.",
                      "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                    },
                    "validateWhenHidden": false,
                    "key": "ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.volume_max",
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
            "key": "ESERCIZIO_DELLA_CAPTAZIONE.qta_risorsa_captata",
            "type": "editgrid",
            "input": true,
            "label": "<strong>Quantità di risorsa prelevata dall'opera</strong>",
            "saveRow": "CONFERMA",
            "validate": {
              "required": true,
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
                        "label": "Portata massima istantanea (l/s)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                          "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "portata_max_derivata",
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
                          "custom": "try {\r\n  // Definisco un oggetto con le informazioni da passare per la verifica\r\n  const params = {\r\n    portataMedia: input,\r\n    portataMassima: row.portata_max_derivata,\r\n    checkZero: true,\r\n  };\r\n  // Recupero la funzione di comodo per la verifica\r\n  const qdrDERCheckPortataMedia = options.SCRIVA.formioQuadri.qdrDERCheckPortataMedia;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const check = qdrDERCheckPortataMedia(params);\r\n  // Verifico il risultato dell'elaborazione sul check\r\n  if (check) {\r\n    // Validazione completa\r\n    return true;\r\n    // #\r\n  } else {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"portataMediaInvalida\";\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "portata_med_derivata",
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
                        "label": "Volume massimo annuo (m<sup>3</sup>)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,9}$)|(^\\d{1,9}[,]\\d{1,5}$)",
                          "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 9 cifre intere e 5 cifre decimali.",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "volume_max_concessione",
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
            "key": "JS_SAVE_SECTION.ESERCIZIO_DELLA_CAPTAZIONE",
            "type": "button",
            "event": "saveSectionData",
            "input": true,
            "label": "SALVA MODIFICHE",
            "logic": [
              {
                "name": "JS_SAVE.ESERCIZIO_DELLA_CAPTAZIONE",
                "actions": [
                  {
                    "name": "disabilitaSalvaModifiche",
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
                  "javascript": "try {\r\n  const stato_esercizio = data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.stato_esercizio;\r\n  const portata_max_deriv = data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_max_deriv;\r\n  const portata_med_deriv = data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_med_deriv;\r\n  const volume_max_concessione = data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.volume_max;\r\n  const qta_risorsa_captata = data.ESERCIZIO_DELLA_CAPTAZIONE.qta_risorsa_captata;\r\n\r\n  const datiSezione = {\r\n    stato_esercizio,\r\n    portata_max_deriv,\r\n    portata_med_deriv,\r\n    volume_max_concessione,\r\n    qta_risorsa_captata\r\n  };\r\n  const valid = options.SCRIVA.trinceaDrenanteUtils.validEsercizioDellaCaptazione(datiSezione);\r\n  return !valid;\r\n\r\n} catch (e) {\r\n  return true;\r\n}"
                }
              }
            ],
            "theme": "default",
            "action": "event",
            "tableView": false,
            "customClass": "btn-outline-primary float-right",
            "showValidations": false
          }
        ],
        "collapsible": true
      }
    ]
  },
  "PRESA_DA_ACQUE_SUPERFICIALI": {
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
        "input": false,
        "label": "title",
        "content": "<strong>Presa {{options.SCRIVA.oggetto.cod_scriva}}</strong>",
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
            "key": "SECTION_REQUIRED.DATI_IDENTIFICATIVI",
            "type": "hidden",
            "input": true,
            "label": "Sezione obbligatoria",
            "tableView": true,
            "defaultValue": true
          },
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
                    "key": "DATI_IDENTIFICATIVI.dati_identificativi.codice_roc",
                    "type": "textfield",
                    "input": true,
                    "label": "Codice ROC l.r. 22/99",
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
                        "name": "verificaCodiceROCProvvisorio",
                        "trigger": {
                          "type": "javascript",
                          "javascript": "// Recupero il codice ROC dai dati del campo\nconst codiceRoc = data.DATI_IDENTIFICATIVI.dati_identificativi.codice_roc;\n// Verifico se il campo inizia con il codice provvisorio generato\nresult = codiceRoc.startsWith(\"SCRV\");"
                        },
                        "actions": [
                          {
                            "name": "nascondiCodiceROCProvvisorio",
                            "type": "property",
                            "property": {
                              "label": "Hidden",
                              "value": "hidden",
                              "type": "boolean"
                            },
                            "state": true
                          }
                        ]
                      }
                    ],
                    "validate": {
                      "maxLength": 10
                    },
                    "tableView": true,
                    "applyMaskOn": "change",
                    "customDefaultValue": "value = options.SCRIVA.oggetto.cod_scriva;",
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
                "size": "md",
                "width": 4,
                "components": [
                  {
                    "key": "DATI_IDENTIFICATIVI.dati_identificativi.codice_sii",
                    "type": "textfield",
                    "input": true,
                    "label": "Codice SII",
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
                      "maxLength": 10
                    },
                    "tableView": true,
                    "applyMaskOn": "change",
                    "validateWhenHidden": false
                  }
                ],
                "currentWidth": 4
              }
            ],
            "autoAdjust": true,
            "key": "columns1",
            "type": "columns",
            "input": false,
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
                    "key": "DATI_IDENTIFICATIVI.dati_identificativi.tipo_presa",
                    "data": {
                      "custom": "values = [\n  { id_tipo_presa: 1, des_tipo_presa: \"Da corpo idrico naturale\" },\n  { id_tipo_presa: 2, des_tipo_presa: \"Da corpo idrico artificiale\" },  \n]"
                    },
                    "type": "select",
                    "input": true,
                    "label": "Tipo presa",
                    "widget": "choicesjs",
                    "dataSrc": "custom",
                    "template": "<span>{{ item.des_tipo_presa }}</span>",
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
                    "key": "DATI_IDENTIFICATIVI.denominazione",
                    "type": "textfield",
                    "input": true,
                    "label": "Denominazione presa/del canale alimentato dalla presa",
                    "disabled": true,
                    "validate": {
                      "maxLength": 500,
                      "customMessage": "Il campo può contenere un massimo di 500 caratteri"
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
                    "key": "DATI_IDENTIFICATIVI.dati_identificativi.denomin_cidrsup",
                    "type": "textfield",
                    "input": true,
                    "label": "Denominazione corpo idrico/lago/invaso",
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
                    "key": "DATI_IDENTIFICATIVI.altri_elementi_identificativi.progressiva_asta",
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
                    "key": "DATI_IDENTIFICATIVI.altri_elementi_identificativi.flg_galleria_filtr",
                    "data": {
                      "values": [
                        {
                          "label": "Sì",
                          "value": "si"
                        },
                        {
                          "label": "No",
                          "value": "no"
                        }
                      ]
                    },
                    "type": "select",
                    "input": true,
                    "label": "Galleria filtrante o tubazione drenante",
                    "widget": "choicesjs",
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
              }
            ],
            "tableView": false
          },
          {
            "key": "columns_di_5",
            "type": "columns",
            "input": false,
            "label": "columns_di_5",
            "columns": [
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 4,
                "offset": 0,
                "components": [
                  {
                    "key": "DATI_IDENTIFICATIVI.altri_elementi_identificativi.flg_capt_subalveo",
                    "data": {
                      "values": [
                        {
                          "label": "Sì",
                          "value": "si"
                        },
                        {
                          "label": "No",
                          "value": "no"
                        }
                      ]
                    },
                    "type": "select",
                    "input": true,
                    "label": "Captazione d’acqua da subalveo",
                    "widget": "choicesjs",
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
                    "key": "DATI_IDENTIFICATIVI.altri_elementi_identificativi.codice_roc_bck",
                    "type": "textfield",
                    "input": true,
                    "label": "Presa alternativa codice ROC l.r. 22/99",
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
                      "maxLength": 10,
                      "customMessage": "Il campo può contenere un massimo di 10 caratteri"
                    },
                    "tableView": true,
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
                    "key": "DATI_IDENTIFICATIVI.altri_elementi_identificativi.flg_da_canale",
                    "data": {
                      "values": [
                        {
                          "label": "Sì",
                          "value": "si"
                        },
                        {
                          "label": "No",
                          "value": "no"
                        }
                      ]
                    },
                    "type": "select",
                    "input": true,
                    "label": "Presa da canale (sub-derivazione)",
                    "widget": "choicesjs",
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
              }
            ],
            "tableView": false
          },
          {
            "key": "JS_SAVE_SECTION.DATI_IDENTIFICATIVI",
            "type": "button",
            "event": "saveSectionData",
            "input": true,
            "label": "SALVA MODIFICHE",
            "logic": [
              {
                "name": "JS_SAVE.DATI_IDENTIFICATIVI",
                "actions": [
                  {
                    "name": "disabilitaSalvaModifiche",
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
                  "javascript": "try {\r\n  const codice_roc = data.DATI_IDENTIFICATIVI.dati_identificativi.codice_roc;\r\n  const codice_rilievo = data.DATI_IDENTIFICATIVI.dati_identificativi.codice_rilievo;\r\n  const codice_sii = data.DATI_IDENTIFICATIVI.dati_identificativi.codice_sii;\r\n  const tipo_presa = data.DATI_IDENTIFICATIVI.dati_identificativi.tipo_presa;\r\n  const denominazione = data.DATI_IDENTIFICATIVI.denominazione;\r\n  const denomin_cidrsup = data.DATI_IDENTIFICATIVI.dati_identificativi.denomin_cidrsup;\r\n  const comune = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.comune;\r\n  const localizzazione = data.DATI_IDENTIFICATIVI.localizzazione;\r\n  const localita = localizzazione ? localizzazione.localita : '';\r\n  const cod_catastale = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.cod_catastale;\r\n  const sezione = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.sezione;\r\n  const foglio = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.foglio;\r\n  const particella = data.JS_READ_ONLY.DATI_IDENTIFICATIVI.particella;\r\n  const sponda = data.DATI_IDENTIFICATIVI.altri_elementi_identificativi.sponda;\r\n  const progressiva_asta = data.DATI_IDENTIFICATIVI.altri_elementi_identificativi.progressiva_asta;\r\n  const flg_galleria_filtr = data.DATI_IDENTIFICATIVI.altri_elementi_identificativi.flg_galleria_filtr;\r\n  const flg_capt_subalveo = data.DATI_IDENTIFICATIVI.altri_elementi_identificativi.flg_capt_subalveo;\r\n  const codice_roc_bck = data.DATI_IDENTIFICATIVI.altri_elementi_identificativi.codice_roc_bck;\r\n  const flg_da_canale = data.DATI_IDENTIFICATIVI.altri_elementi_identificativi.flg_da_canale;\r\n\r\n  const datiSezione = {\r\n    codice_roc,\r\n    codice_rilievo,\r\n    codice_sii,\r\n    tipo_presa,\r\n    denominazione,\r\n    denomin_cidrsup,\r\n    comune,\r\n    localita,\r\n    cod_catastale,\r\n    sezione,\r\n    foglio,\r\n    particella,\r\n    sponda,\r\n    progressiva_asta,\r\n    flg_galleria_filtr,\r\n    flg_capt_subalveo,\r\n    codice_roc_bck,\r\n    flg_da_canale,\r\n  };\r\n  const valid = options.SCRIVA.presaUtils.validDatiIdentificativi(datiSezione);\r\n  return !valid;\r\n\r\n} catch (e) {\r\n  return true;\r\n}"
                }
              }
            ],
            "theme": "default",
            "action": "event",
            "tableView": false,
            "customClass": "btn-outline-primary float-right",
            "showValidations": false
          }
        ],
        "collapsible": true
      },
      {
        "key": "ESERCIZIO_DELLA_CAPTAZIONE",
        "type": "panel",
        "input": false,
        "label": "Esercizio della captazione",
        "title": "<strong>* Esercizio della captazione</strong>",
        "collapsed": true,
        "tableView": false,
        "components": [
          {
            "key": "SECTION_REQUIRED.ESERCIZIO_DELLA_CAPTAZIONE",
            "type": "hidden",
            "input": true,
            "label": "Sezione obbligatoria",
            "tableView": true,
            "defaultValue": true
          },
          {
            "key": "title_ec_1",
            "type": "htmlelement",
            "input": false,
            "label": "title_ec_1",
            "content": "<strong>Esercizio della captazione</strong>",
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
                    "key": "ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.stato_esercizio",
                    "data": {
                      "custom": "values = [\n { id_stato_esercizio: 1, des_stato_esercizio: 'In uso' },\n  { id_stato_esercizio: 2, des_stato_esercizio: 'Dismesso' }\n]"
                    },
                    "type": "select",
                    "input": true,
                    "label": "Stato di esercizio",
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
                    "widget": "choicesjs",
                    "dataSrc": "custom",
                    "template": "<span>{{ item.des_stato_esercizio }}</span>",
                    "tableView": true,
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
                    "label": "Portata massima derivabile (l/s)",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "validate": {
                      "required": true,
                      "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                      "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                      "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                    },
                    "validateWhenHidden": false,
                    "key": "ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_max_deriv",
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
                    "label": "Portata media annua derivabile (l/s)",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "validate": {
                      "required": true,
                      "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                      "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, inferiore o uguale alla portata massima, con un massimo di 7 cifre intere e 4 cifre decimali.",
                      "custom": "try {\r\n  // Definisco un oggetto con le informazioni da passare per la verifica\r\n  const params = {\r\n    portataMedia: input,\r\n    portataMassima: data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_max_deriv,\r\n    checkZero: true,\r\n  };\r\n  // Recupero la funzione di comodo per la verifica\r\n  const qdrDERCheckPortataMedia = options.SCRIVA.formioQuadri.qdrDERCheckPortataMedia;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const check = qdrDERCheckPortataMedia(params);\r\n  // Verifico il risultato dell'elaborazione sul check\r\n  if (check) {\r\n    // Validazione completa\r\n    return true;\r\n    // #\r\n  } else {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"portataMediaInvalida\";\r\n  }\r\n} catch (e) {}"
                    },
                    "validateWhenHidden": false,
                    "key": "ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_med_deriv",
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
                    "label": "Volume massimo di concessione (m<sup>3</sup>)",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "validate": {
                      "required": true,
                      "pattern": "(^\\d{1,9}$)|(^\\d{1,9}[,]\\d{1,5}$)",
                      "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 9 cifre intere e 5 cifre decimali.",
                      "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                    },
                    "validateWhenHidden": false,
                    "key": "ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.volume_max",
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
            "key": "ESERCIZIO_DELLA_CAPTAZIONE.qta_risorsa_captata",
            "type": "editgrid",
            "input": true,
            "label": "<strong>Quantità di risorsa prelevata dall'opera</strong>",
            "saveRow": "CONFERMA",
            "validate": {
              "required": true,
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
                        "label": "Portata massima istantanea (l/s)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                          "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "portata_max_derivata",
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
                          "custom": "try {\r\n  // Definisco un oggetto con le informazioni da passare per la verifica\r\n  const params = {\r\n    portataMedia: input,\r\n    portataMassima: row.portata_max_derivata,\r\n    checkZero: true,\r\n  };\r\n  // Recupero la funzione di comodo per la verifica\r\n  const qdrDERCheckPortataMedia = options.SCRIVA.formioQuadri.qdrDERCheckPortataMedia;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const check = qdrDERCheckPortataMedia(params);\r\n  // Verifico il risultato dell'elaborazione sul check\r\n  if (check) {\r\n    // Validazione completa\r\n    return true;\r\n    // #\r\n  } else {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"portataMediaInvalida\";\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "portata_med_derivata",
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
                        "label": "Volume massimo annuo (m<sup>3</sup>)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,9}$)|(^\\d{1,9}[,]\\d{1,5}$)",
                          "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 9 cifre intere e 5 cifre decimali.",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "volume_max_concessione",
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
            "key": "JS_SAVE_SECTION.ESERCIZIO_DELLA_CAPTAZIONE",
            "type": "button",
            "event": "saveSectionData",
            "input": true,
            "label": "SALVA MODIFICHE",
            "logic": [
              {
                "name": "JS_SAVE.ESERCIZIO_DELLA_CAPTAZIONE",
                "actions": [
                  {
                    "name": "disabilitaSalvaModifiche",
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
                  "javascript": "try {\r\n  const stato_esercizio = data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.stato_esercizio;\r\n  const portata_max_deriv = data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_max_deriv;\r\n  const portata_med_deriv = data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.portata_med_deriv;\r\n  const volume_max_concessione = data.ESERCIZIO_DELLA_CAPTAZIONE.esercizio_captazione.volume_max;\r\n  const qta_risorsa_captata = data.ESERCIZIO_DELLA_CAPTAZIONE.qta_risorsa_captata;\r\n\r\n  const datiSezione = {\r\n    stato_esercizio,\r\n    portata_max_deriv,\r\n    portata_med_deriv,\r\n    volume_max_concessione,\r\n    qta_risorsa_captata\r\n  };\r\n  const valid = options.SCRIVA.presaUtils.validEsercizioDellaCaptazione(datiSezione);\r\n  return !valid;\r\n\r\n} catch (e) {\r\n  return true;\r\n}"
                }
              }
            ],
            "theme": "default",
            "action": "event",
            "tableView": false,
            "customClass": "btn-outline-primary float-right",
            "showValidations": false
          }
        ],
        "collapsible": true
      },
      {
        "key": "RILASCIO_A_VALLE_DELLA_PRESA",
        "type": "panel",
        "input": false,
        "label": "Rilascio a valle della presa",
        "title": "<strong>* Rilascio a valle della presa</strong>",
        "collapsed": true,
        "tableView": false,
        "components": [
          {
            "key": "SECTION_REQUIRED.RILASCIO_A_VALLE_DELLA_PRESA",
            "type": "hidden",
            "input": true,
            "label": "Sezione obbligatoria",
            "tableView": true,
            "defaultValue": true
          },
          {
            "key": "title_rv_1",
            "type": "htmlelement",
            "input": false,
            "label": "title_rv_1",
            "content": "<strong>Rilascio a valle della presa</strong>",
            "tableView": false,
            "refreshOnChange": false
          },
          {
            "key": "columns_rv_1",
            "type": "columns",
            "input": false,
            "label": "columns_rv_1",
            "columns": [
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 4,
                "offset": 0,
                "components": [
                  {
                    "key": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_dispositivi",
                    "data": {
                      "values": [
                        {
                          "label": "Sì",
                          "value": "si"
                        },
                        {
                          "label": "No",
                          "value": "no"
                        }
                      ]
                    },
                    "type": "select",
                    "input": true,
                    "label": "Presenza dispositivi per il rilascio",
                    "widget": "choicesjs",
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
            "key": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_imposto",
            "type": "editgrid",
            "input": true,
            "label": "<strong>Rilascio imposto</strong>",
            "saveRow": "CONFERMA",
            "removeRow": "ANNULLA",
            "rowDrafts": false,
            "tableView": false,
            "templates": {
              "row": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col-sm-2\">\r\n        {{ isVisibleInRow(component) ? getView(component, row[component.key]) : ''}}\r\n      </div>\r\n    {% } %}\r\n  {% }) %}\r\n  {% if (!instance.options.readOnly && !instance.disabled) { %}\r\n    <div class=\"col-sm-2\">\r\n      <div class=\"btn-group pull-left\">\r\n        <button class=\"btn btn-default btn-light btn-sm editRow\"><i class=\"{{ iconClass('edit') }}\"></i></button>\r\n        {% if (!instance.hasRemoveButtons || instance.hasRemoveButtons()) { %}\r\n          <button class=\"btn btn-danger btn-sm removeRow\"><i class=\"{{ iconClass('trash') }}\"></i></button>\r\n        {% } %}\r\n      </div>\r\n    </div>\r\n  {% } %}\r\n</div>\r\n",
              "header": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col-sm-2\">{{ t(component.label) }}</div>\r\n    {% } %}\r\n  {% }) %}\r\n  <div class=\"col-sm-2\">Azioni</div>\r\n</div>\r\n"
            },
            "addAnother": "AGGIUNGI PERIODO",
            "components": [
              {
                "key": "columns_rv_2",
                "type": "columns",
                "input": false,
                "label": "columns_rv_2",
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
                    "pull": 0,
                    "push": 0,
                    "size": "md",
                    "width": 3,
                    "offset": 0,
                    "components": [
                      {
                        "label": "Portata (l/s)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                          "customMessage": "Il campo è obbligatorio e può contenere un massimo di 7 cifre intere e 4 cifre decimali",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "portata",
                        "type": "textfield",
                        "input": true,
                        "hideOnChildrenHidden": false
                      }
                    ],
                    "currentWidth": 3
                  }
                ],
                "tableView": false
              }
            ],
            "conditional": {
              "eq": "si",
              "show": true,
              "when": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_dispositivi"
            },
            "displayAsTable": false,
            "validateWhenHidden": false
          },
          {
            "key": "columns_rv_2",
            "type": "columns",
            "input": false,
            "label": "columns_rv_2",
            "columns": [
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 4,
                "offset": 0,
                "components": [
                  {
                    "key": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_de",
                    "data": {
                      "values": [
                        {
                          "label": "Sì",
                          "value": "si"
                        },
                        {
                          "label": "No",
                          "value": "no"
                        }
                      ]
                    },
                    "type": "select",
                    "input": true,
                    "label": "DE modulato",
                    "logic": [
                      {
                        "name": "isRequired",
                        "actions": [
                          {
                            "name": "setRequired",
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
                          "type": "simple",
                          "simple": {
                            "eq": "si",
                            "show": true,
                            "when": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_dispositivi"
                          }
                        }
                      },
                      {
                        "name": "isDisabled",
                        "actions": [
                          {
                            "name": "setDisabled",
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
                          "javascript": "result = false;\n\ntry {\n  const rilascioVallePresa = data.RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa;\n  const flg_dispositivi = rilascioVallePresa.flg_dispositivi;\n  const check1 = !flg_dispositivi || flg_dispositivi == 'no';\n  result = check1;\n} catch(e) {}"
                        }
                      }
                    ],
                    "widget": "choicesjs",
                    "refreshOn": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_dispositivi",
                    "tableView": true,
                    "conditional": {
                      "eq": "si",
                      "show": true,
                      "when": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_dispositivi"
                    },
                    "placeholder": "Seleziona",
                    "searchEnabled": false,
                    "clearOnRefresh": true,
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
                    "key": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.modulazione",
                    "data": {
                      "custom": "values = [\n { id_modulazione: 1, des_modulazione: 'Tipo A 10%' },\n  { id_modulazione: 2, des_modulazione: 'Tipo A 20%' },\n  { id_modulazione: 3, des_modulazione: 'Tipo B' },  \n]"
                    },
                    "type": "select",
                    "input": true,
                    "label": "Tipologia di modulazione",
                    "logic": [
                      {
                        "name": "isRequired",
                        "actions": [
                          {
                            "name": "setRequired",
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
                          "type": "simple",
                          "simple": {
                            "eq": "si",
                            "show": true,
                            "when": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_de"
                          }
                        }
                      },
                      {
                        "name": "isDisabled",
                        "actions": [
                          {
                            "name": "setDisabled",
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
                          "javascript": "result = false;\n\ntry {\n  const rilascioVallePresa = data.RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa;\n  const flg_dispositivi = rilascioVallePresa.flg_dispositivi;\n  const check1 = !flg_dispositivi || flg_dispositivi == 'no';\n  const flg_de = rilascioVallePresa.flg_de;\n  const check2 = !flg_de || flg_de == 'no';\n  result = check1 || check2;\n} catch(e) {}"
                        }
                      }
                    ],
                    "widget": "choicesjs",
                    "dataSrc": "custom",
                    "template": "<span>{{ item.des_modulazione }}</span>",
                    "refreshOn": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_de",
                    "tableView": true,
                    "conditional": {
                      "eq": "si",
                      "show": true,
                      "when": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_de"
                    },
                    "placeholder": "Seleziona",
                    "searchEnabled": false,
                    "clearOnRefresh": true,
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
                "components": [],
                "currentWidth": 4
              }
            ],
            "tableView": false,
            "autoAdjust": true
          },
          {
            "key": "columnsRv3",
            "type": "columns",
            "input": false,
            "label": "columns_rv_3",
            "columns": [
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 4,
                "offset": 0,
                "components": [
                  {
                    "key": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_deroga_de",
                    "data": {
                      "values": [
                        {
                          "label": "Sì",
                          "value": "si"
                        },
                        {
                          "label": "No",
                          "value": "no"
                        }
                      ]
                    },
                    "type": "select",
                    "input": true,
                    "label": "In deroga al DE",
                    "widget": "choicesjs",
                    "validate": {
                      "required": true
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
                "width": 6,
                "offset": 0,
                "components": [],
                "currentWidth": 6
              }
            ],
            "tableView": false
          },
          {
            "key": "columns_rv_3",
            "type": "columns",
            "input": false,
            "label": "columns_rv_3",
            "columns": [
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 8,
                "offset": 0,
                "components": [
                  {
                    "key": "RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.ulteriori_obblighi",
                    "rows": 5,
                    "type": "textarea",
                    "input": true,
                    "label": "Ulteriori obblighi",
                    "validate": {
                      "maxLength": 4000,
                      "customMessage": "Il campo può contenere un massimo di 4000 caratteri"
                    },
                    "tableView": true,
                    "autoExpand": true,
                    "applyMaskOn": "change",
                    "validateWhenHidden": false,
                    "hideOnChildrenHidden": false
                  }
                ],
                "currentWidth": 8
              },
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 4,
                "offset": 0,
                "components": [],
                "currentWidth": 4
              }
            ],
            "tableView": false
          },
          {
            "key": "JS_SAVE_SECTION.RILASCIO_A_VALLE_DELLA_PRESA",
            "type": "button",
            "event": "saveSectionData",
            "input": true,
            "label": "SALVA MODIFICHE",
            "logic": [
              {
                "name": "JS_SAVE.RILASCIO_A_VALLE_DELLA_PRESA",
                "actions": [
                  {
                    "name": "disabilitaSalvaModifiche",
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
                  "javascript": "try {\r\n  const flg_dispositivi = data.RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_dispositivi;\r\n  const rilascio_imposto = data.RILASCIO_A_VALLE_DELLA_PRESA.rilascio_imposto;\r\n  const flg_de = data.RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_de;\r\n  const modulazione = data.RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.modulazione;\r\n  const flg_deroga_de = data.RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.flg_deroga_de;\r\n  const ulteriori_obblighi = data.RILASCIO_A_VALLE_DELLA_PRESA.rilascio_a_valle_della_presa.ulteriori_obblighi;\r\n\r\n  const datiSezione = {\r\n    flg_dispositivi,\r\n    rilascio_imposto,\r\n    flg_de,\r\n    modulazione,\r\n    flg_deroga_de,\r\n    ulteriori_obblighi\r\n  };\r\n  const valid = options.SCRIVA.presaUtils.validRilascioAValle(datiSezione);\r\n  return !valid;\r\n\r\n} catch (e) {\r\n  return true;\r\n}"
                }
              }
            ],
            "theme": "default",
            "action": "event",
            "tableView": false,
            "customClass": "btn-outline-primary float-right",
            "showValidations": false
          }
        ],
        "collapsible": true
      },
      {
        "key": "DATI_INFRASTRUTTURALI",
        "type": "panel",
        "input": false,
        "label": "Dati infrastrutturali",
        "title": "<strong>* Dati infrastrutturali</strong>",
        "collapsed": true,
        "tableView": false,
        "components": [
          {
            "key": "SECTION_REQUIRED.DATI_INFRASTRUTTURALI",
            "type": "hidden",
            "input": true,
            "label": "Sezione obbligatoria",
            "tableView": true,
            "defaultValue": "true"
          },
          {
            "key": "title_inf_1",
            "type": "htmlelement",
            "input": false,
            "label": "title_inf_1",
            "content": "<strong>Sbarramento</strong>",
            "tableView": false,
            "refreshOnChange": false
          },
          {
            "label": "Presenza di sbarramento",
            "optionsLabelPosition": "right",
            "inline": true,
            "tableView": false,
            "defaultValue": "si",
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
            "validateWhenHidden": false,
            "key": "DATI_INFRASTRUTTURALI.sbarramento.flg_sbarramento",
            "type": "radio",
            "input": true
          },
          {
            "label": "columns_inf_1",
            "columns": [
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 4,
                "offset": 0,
                "components": [
                  {
                    "key": "DATI_INFRASTRUTTURALI.sbarramento.tipo_sbarr",
                    "data": {
                      "custom": "values = [\n { id_tipo_sbarr: 1, des_tipo_sbarr: 'SBARRAMENTO PRECARIO' },\n  { id_tipo_sbarr: 2, des_tipo_sbarr: 'TRAVERSE CON ORGANI DI REGOLAZIONE' },\n  { id_tipo_sbarr: 3, des_tipo_sbarr: 'TRAVERSE SENZA ORGANI DI REGOLAZIONE' },\n  { id_tipo_sbarr: 4, des_tipo_sbarr: 'ALTRO SBARRAMENTO' },\n  { id_tipo_sbarr: 5, des_tipo_sbarr: 'PICCOLA DIGA' },\n  { id_tipo_sbarr: 6, des_tipo_sbarr: 'GRANDE DIGA' },\n];"
                    },
                    "type": "select",
                    "input": true,
                    "label": "Tipologia",
                    "widget": "choicesjs",
                    "dataSrc": "custom",
                    "template": "<span>{{ item.des_tipo_sbarr }}</span>",
                    "validate": {
                      "required": true
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
                    "key": "DATI_INFRASTRUTTURALI.sbarramento.altezza",
                    "type": "textfield",
                    "input": true,
                    "label": "Altezza",
                    "validate": {
                      "pattern": "(^\\d{1,4}$)|(^\\d{1,4}[,]\\d{1,2}$)",
                      "customMessage": "Indicare un massimo di 4 cifre intere e 2 cifre decimali"
                    },
                    "tableView": true,
                    "applyMaskOn": "change",
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
                    "key": "DATI_INFRASTRUTTURALI.sbarramento.volume_max_invaso",
                    "type": "textfield",
                    "input": true,
                    "label": "Volume max d'invaso (m<sup>3</sup>)",
                    "validate": {
                      "pattern": "(^\\d{1,10}$)|(^\\d{1,10}[,]\\d{1,2}$)",
                      "customMessage": "Indicare un massimo di 10 cifre intere e 2 cifre decimali"
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
            "key": "columns_inf_1",
            "conditional": {
              "show": true,
              "when": "DATI_INFRASTRUTTURALI.sbarramento.flg_sbarramento",
              "eq": "si"
            },
            "type": "columns",
            "input": false,
            "tableView": false
          },
          {
            "key": "title_inf_2",
            "type": "htmlelement",
            "attrs": [
              {
                "attr": "",
                "value": ""
              }
            ],
            "input": false,
            "label": "title_inf_1",
            "content": "<strong>Tutela della fauna ittica</strong>",
            "tableView": false,
            "refreshOnChange": false,
            "conditional": {
              "show": true,
              "when": "DATI_INFRASTRUTTURALI.sbarramento.flg_sbarramento",
              "eq": "si"
            }
          },
          {
            "key": "columns2",
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
                    "key": "DATI_INFRASTRUTTURALI.tutela_della_fauna_ittica.flg_scala",
                    "data": {
                      "values": [
                        {
                          "label": "Sì",
                          "value": "si"
                        },
                        {
                          "label": "No",
                          "value": "no"
                        }
                      ]
                    },
                    "type": "select",
                    "input": true,
                    "label": "Scala di risalita per la fauna ittica",
                    "widget": "choicesjs",
                    "validate": {
                      "required": true
                    },
                    "tableView": true,
                    "defaultValue": "no",
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
                    "key": "DATI_INFRASTRUTTURALI.tutela_della_fauna_ittica.tipo_scala",
                    "data": {
                      "custom": "values = [\n { id_tipo_scala: 1, des_tipo_scala: 'Rustica' },\n  { id_tipo_scala: 2, des_tipo_scala: 'A rallentamento' },\n  { id_tipo_scala: 3, des_tipo_scala: 'A bacini successivi' },\n  { id_tipo_scala: 4, des_tipo_scala: 'Canale by-pass' },\n  { id_tipo_scala: 5, des_tipo_scala: 'Ascensore per pesci' },\n];"
                    },
                    "type": "select",
                    "input": true,
                    "label": "Tipologia della scala",
                    "logic": [
                      {
                        "name": "isRequired",
                        "actions": [
                          {
                            "name": "setRequired",
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
                          "type": "simple",
                          "simple": {
                            "eq": "si",
                            "show": true,
                            "when": "DATI_INFRASTRUTTURALI.tutela_della_fauna_ittica.flg_scala"
                          }
                        }
                      },
                      {
                        "name": "isDisabled",
                        "actions": [
                          {
                            "name": "setDisabled",
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
                          "type": "simple",
                          "simple": {
                            "eq": "no",
                            "show": true,
                            "when": "DATI_INFRASTRUTTURALI.tutela_della_fauna_ittica.flg_scala"
                          }
                        }
                      }
                    ],
                    "widget": "choicesjs",
                    "dataSrc": "custom",
                    "template": "<span>{{ item.des_tipo_scala }}</span>",
                    "refreshOn": "DATI_INFRASTRUTTURALI.tutela_della_fauna_ittica.flg_scala",
                    "tableView": true,
                    "conditional": {
                      "eq": "si",
                      "show": true,
                      "when": "DATI_INFRASTRUTTURALI.tutela_della_fauna_ittica.flg_scala"
                    },
                    "clearOnRefresh": true,
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
                    "key": "DATI_INFRASTRUTTURALI.tutela_della_fauna_ittica.sponda_scala",
                    "data": {
                      "custom": "values = [\n { id_sponda_scala: 1, des_sponda_scala: 'Destra' },\n  { id_sponda_scala: 2, des_sponda_scala: 'Sinistra' },\n];"
                    },
                    "type": "select",
                    "input": true,
                    "label": "Sponda di localizzazione",
                    "logic": [
                      {
                        "name": "isRequired",
                        "actions": [
                          {
                            "name": "setRequired",
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
                          "type": "simple",
                          "simple": {
                            "eq": "si",
                            "show": true,
                            "when": "DATI_INFRASTRUTTURALI.tutela_della_fauna_ittica.flg_scala"
                          }
                        }
                      },
                      {
                        "name": "isDisabled",
                        "actions": [
                          {
                            "name": "setDisabled",
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
                          "type": "simple",
                          "simple": {
                            "eq": "no",
                            "show": true,
                            "when": "DATI_INFRASTRUTTURALI.tutela_della_fauna_ittica.flg_scala"
                          }
                        }
                      }
                    ],
                    "widget": "choicesjs",
                    "dataSrc": "custom",
                    "template": "<span>{{ item.des_sponda_scala }}</span>",
                    "refreshOn": "DATI_INFRASTRUTTURALI.tutela_della_fauna_ittica.flg_scala",
                    "tableView": true,
                    "conditional": {
                      "eq": "si",
                      "show": true,
                      "when": "DATI_INFRASTRUTTURALI.tutela_della_fauna_ittica.flg_scala"
                    },
                    "clearOnRefresh": true,
                    "validateWhenHidden": false
                  }
                ],
                "currentWidth": 4
              }
            ],
            "tableView": false,
            "conditional": {
              "show": true,
              "when": "DATI_INFRASTRUTTURALI.sbarramento.flg_sbarramento",
              "eq": "si"
            }
          },
          {
            "key": "columns3",
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
                    "key": "DATI_INFRASTRUTTURALI.tutela_della_fauna_ittica.flg_obblighi_itt",
                    "data": {
                      "values": [
                        {
                          "label": "Si",
                          "value": "si"
                        },
                        {
                          "label": "No",
                          "value": "no"
                        }
                      ]
                    },
                    "type": "select",
                    "input": true,
                    "label": "Obblighi ittiogenici",
                    "widget": "choicesjs",
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
            "conditional": {
              "show": true,
              "when": "DATI_INFRASTRUTTURALI.sbarramento.flg_sbarramento",
              "eq": "si"
            },
            "tableView": false
          },
          {
            "key": "columns_rv_4",
            "type": "columns",
            "input": false,
            "label": "columns_rv_3",
            "columns": [
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 8,
                "offset": 0,
                "components": [
                  {
                    "key": "DATI_INFRASTRUTTURALI.tutela_della_fauna_ittica.specie_ittiche",
                    "type": "textarea",
                    "input": true,
                    "label": "Specie ittiche",
                    "validate": {
                      "maxLength": 200,
                      "customMessage": "Il campo può contenere un massimo di 200 caratteri"
                    },
                    "tableView": true,
                    "autoExpand": true,
                    "applyMaskOn": "change",
                    "validateWhenHidden": false,
                    "hideOnChildrenHidden": false
                  }
                ],
                "currentWidth": 8
              },
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 4,
                "offset": 0,
                "components": [],
                "currentWidth": 4
              }
            ],
            "conditional": {
              "show": true,
              "when": "DATI_INFRASTRUTTURALI.sbarramento.flg_sbarramento",
              "eq": "si"
            },
            "tableView": false
          },
          {
            "label": "SALVA MODIFICHE",
            "action": "event",
            "showValidations": false,
            "theme": "default",
            "customClass": "btn-outline-primary float-right",
            "tableView": false,
            "key": "JS_SAVE_SECTION.DATI_INFRASTRUTTURALI",
            "logic": [
              {
                "name": "JS_SAVE.DATI_INFRASTRUTTURALI",
                "actions": [
                  {
                    "name": "disabilitaSalvaModifiche",
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
                  "javascript": "try {\r\n  const tipo_sbarr = data.DATI_INFRASTRUTTURALI.sbarramento.tipo_sbarr;\r\n  const altezza = data.DATI_INFRASTRUTTURALI.sbarramento.altezza;\r\n  const volume_max_invaso = data.DATI_INFRASTRUTTURALI.sbarramento.volume_max_invaso;\r\n  const flg_scala = data.DATI_INFRASTRUTTURALI.tutela_della_fauna_ittica.flg_scala;\r\n  const tipo_scala = data.DATI_INFRASTRUTTURALI.tutela_della_fauna_ittica.tipo_scala;\r\n  const sponda_scala = data.DATI_INFRASTRUTTURALI.tutela_della_fauna_ittica.sponda_scala;\r\n  const flg_obblighi_itt = data.DATI_INFRASTRUTTURALI.tutela_della_fauna_ittica.flg_obblighi_itt;\r\n  const specie_ittiche = data.DATI_INFRASTRUTTURALI.tutela_della_fauna_ittica.specie_ittiche;\r\n  const flg_sbarramento = data.DATI_INFRASTRUTTURALI.sbarramento.flg_sbarramento;\r\n\r\n  const datiSezione = {\r\n    tipo_sbarr,\r\n    altezza,\r\n    volume_max_invaso,\r\n    flg_scala,\r\n    tipo_scala,\r\n    sponda_scala,\r\n    flg_obblighi_itt,\r\n    specie_ittiche,\r\n    flg_sbarramento\r\n  };\r\n  const valid = options.SCRIVA.presaUtils.validDatiInfrastrutturali(datiSezione);\r\n  return !valid;\r\n\r\n} catch (e) {\r\n  return true;\r\n}"
                }
              }
            ],
            "type": "button",
            "event": "saveSectionData",
            "input": true
          }
        ],
        "collapsible": true
      }
    ]
  }
};

