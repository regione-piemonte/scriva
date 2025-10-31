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
export const QDR_DER_DATIGEN_DEBUG = {
  "label": "Dati generali",
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
        "content": "Compila i dati generali della richiesta di concessione.",
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
        "key": "panelDatiSanatoria",
        "type": "panel",
        "input": false,
        "label": "Panel",
        "title": "Dati sanatoria",
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
                "width": 4,
                "offset": 0,
                "components": [
                  {
                    "key": "JS_SANATORIA.data_inizio_uso_acqua",
                    "type": "datetime",
                    "input": true,
                    "label": "Data di inizio uso dell'acqua",
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
                      "hourIncrement": 0,
                      "minuteIncrement": 0,
                      "time_24hr": true,
                      "minDate": null,
                      "disableWeekends": false,
                      "disableWeekdays": false,
                      "maxDate": null
                    },
                    "validate": {
                      "required": true,
                      "customMessage": "Inserire una data valida."
                    },
                    "tableView": false,
                    "datePicker": {
                      "disableWeekdays": false,
                      "disableWeekends": false
                    },
                    "enableTime": false,
                    "timePicker": {
                      "hourStep": 0,
                      "minuteStep": 0,
                      "showMeridian": false
                    },
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
            "key": "JS_SANATORIA.titolo_autor_preesistente",
            "type": "textarea",
            "input": true,
            "label": "Eventuale titolo autorizzativo preesistente",
            "validate": {
              "maxLength": 500,
              "customMessage": "Inserire al massimo 500 caratteri."
            },
            "tableView": true,
            "autoExpand": false,
            "applyMaskOn": "change",
            "validateWhenHidden": false
          },
          {
            "key": "JS_SANATORIA.flg_autor_provv_uso",
            "type": "radio",
            "input": true,
            "label": "Viene richiesto il rilascio dell’autorizzazione provvisoria alla continuazione dell’utilizzo",
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
              "required": true,
              "customMessage": "Selezionare un'opzione.",
              "onlyAvailableItems": true
            },
            "tableView": false,
            "defaultValue": "no",
            "validateWhenHidden": false,
            "optionsLabelPosition": "right"
          },
          {
            "key": "JS_MODAL.JS_SANATORIA.flg_autor_provv_uso",
            "type": "textfield",
            "input": true,
            "label": "Conferma rilascio autorizzazione provvisoria [NASCOSTO]",
            "logic": [
              {
                "name": "JS_MODAL_TRIGGER.JS_SANATORIA.flg_autor_provv_uso",
                "actions": [
                  {
                    "name": "JS_MODAL_CONFERMA.JS_SANATORIA.flg_autor_provv_uso",
                    "type": "customAction",
                    "customAction": "try {\n  const componenteTrigger = 'JS_SANATORIA.flg_autor_provv_uso';\n  const componenteConferma = 'JS_MODAL.JS_SANATORIA.flg_autor_provv_uso';\n  const formioModals = options.SCRIVA.formioModals;\n  const formioUpdate = options.SCRIVA.formioUpdate;\n  const formioUpdateComponent = options.SCRIVA.formioUpdateComponent;\n\n  const onConfirm = () => {\n    return formioUpdateComponent(data, componenteConferma, 'no');\n  }\n  const onCancel = () => {\n    return formioUpdateComponent(data, componenteTrigger, 'si');\n  }\n  \n  const config = {\n    title: 'Conferma',\n    codeOrBody: 'A056',\n    callbacks: {\n      onConfirm: () => {\n        data = onConfirm();\n        formioUpdate(data);\n      },\n      onCancel: () => {\n        data = onCancel();\n        formioUpdate(data);\n      },\n      onClose: () => {\n        data = onCancel();\n        formioUpdate(data);\n      }\n    }\n  }\n  \n  formioModals.conferma(config);\n  \n} catch (e) {}"
                  }
                ],
                "trigger": {
                  "type": "javascript",
                  "simple": {},
                  "javascript": "try {\n  const check1 = data.JS_SANATORIA.flg_autor_provv_uso == 'no';\n  const check2 = data.JS_MODAL.JS_SANATORIA.flg_autor_provv_uso == 'si';\n  result = check1 && check2;\n} catch (e) {\n  return false;\n}"
                }
              },
              {
                "name": "JS_MODAL_SET_TRIGGER.JS_SANATORIA.flg_autor_provv_uso",
                "actions": [
                  {
                    "name": "JS_MODAL_SET_TRIGGER_SI.JS_SANATORIA.flg_autor_provv_uso",
                    "type": "value",
                    "value": "value = 'si';"
                  }
                ],
                "trigger": {
                  "type": "simple",
                  "simple": {
                    "eq": "si",
                    "show": true,
                    "when": "JS_SANATORIA.flg_autor_provv_uso"
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
            "key": "JS_SANATORIA.motivaz_autor_provv_uso",
            "type": "textarea",
            "input": true,
            "label": "per le seguenti motivazioni",
            "validate": {
              "maxLength": 4000,
              "customMessage": "Inserire al massimo 4000 caratteri."
            },
            "tableView": true,
            "autoExpand": false,
            "applyMaskOn": "change",
            "customConditional": "try {\n  // Recupero il valore della input modale\n  const inputUtente = data.JS_SANATORIA.flg_autor_provv_uso;\n  const inputModale = data.JS_MODAL.JS_SANATORIA.flg_autor_provv_uso;\n  // Se l'input ha un valore specifico o quello di init devo nascondere l'elemento\n  const caseOnInit = inputUtente === 'si' && inputModale === 'init';\n  const caseOnUse = inputModale === 'si';\n  // Definisco le condizioni di visualizzazione\n  show = caseOnInit || caseOnUse;\n} catch(e) {\n  show = true;\n}",
            "validateWhenHidden": false
          }
        ],
        "collapsible": false,
        "customConditional": "show = false;\r\n\r\ntry {\r\n  const tipoConcessione = form.json_istanza.QDR_CONFIG.QDR_DER_DATIGEN.tipo_concessione;\r\n  show = tipoConcessione.toLowerCase() === 'sanatoria';\r\n} catch(e) {}"
      },
      {
        "key": "panelDatiIdentificativi",
        "type": "panel",
        "input": false,
        "label": "Panel",
        "title": "Dati identificativi",
        "tableView": false,
        "components": [
          {
            "key": "columnsRiga1",
            "type": "columns",
            "input": false,
            "label": "ColumnsRiga1",
            "columns": [
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 4,
                "offset": 0,
                "components": [
                  {
                    "key": "JS_DATI_IDENTIFICATIVI.codice_utenza_gerica",
                    "type": "textfield",
                    "input": true,
                    "label": "Codice utenza",
                    "validate": {
                      "maxLength": 10,
                      "customMessage": "Lunghezza massima 10 caratteri."
                    },
                    "tableView": true,
                    "applyMaskOn": "change",
                    "customConditional": "show = options.SCRIVA.isBackOffice;",
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
                    "key": "JS_DATI_IDENTIFICATIVI.codice_rilievo",
                    "type": "textfield",
                    "input": true,
                    "label": "Codice rilievo",
                    "disabled": true,
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
            "tableView": false,
            "autoAdjust": true
          },
          {
            "key": "columnsRiga2",
            "type": "columns",
            "input": false,
            "label": "ColumnsRiga2",
            "columns": [
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 4,
                "offset": 0,
                "components": [
                  {
                    "key": "JS_DATI_IDENTIFICATIVI.tipo_deriv",
                    "data": {
                      "custom": "values = [\r\n  { des_tipo_deriv: 'Grande', id_tipo_deriv: 1 },\r\n  { des_tipo_deriv: 'Piccola', id_tipo_deriv: 2 }\r\n]"
                    },
                    "type": "select",
                    "input": true,
                    "label": "Specie della derivazione",
                    "widget": "choicesjs",
                    "dataSrc": "custom",
                    "template": "<span>{{ item.des_tipo_deriv }}</span>",
                    "validate": {
                      "required": true,
                      "customMessage": "Inserire la Specie della derivazione."
                    },
                    "tableView": true,
                    "placeholder": "Seleziona",
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
          }
        ],
        "collapsible": false
      },
      {
        "key": "panelEsercizioDerivazione",
        "type": "panel",
        "input": false,
        "label": "Panel",
        "title": "Esercizio della derivazione",
        "tableView": false,
        "components": [
          {
            "key": "esercizioColumns",
            "type": "columns",
            "input": false,
            "label": "EsercizioColumns",
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
                    "key": "JS_ESERCIZIO_DERIVAZIONE.portata_max",
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
                    "label": "Portata media di prelievo (l/s)",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "validate": {
                      "required": true,
                      "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                      "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, inferiore o uguale alla portata massima, con un massimo di 7 cifre intere e 4 cifre decimali.",
                      "custom": "try {\n  // Definisco un oggetto con le informazioni da passare per la verifica\n  const params = {\n    portataMedia: input,\n    portataMassima: data.JS_ESERCIZIO_DERIVAZIONE.portata_max,\n    checkZero: true,\n  };\n  // Recupero la funzione di comodo per la verifica\n  const qdrDERCheckPortataMedia = options.SCRIVA.formioQuadri.qdrDERCheckPortataMedia;\n  // Recupero le informazioni per portata media e portata massima\n  const check = qdrDERCheckPortataMedia(params);\n  // Verifico il risultato dell'elaborazione sul check\n  if (check) {\n    // Validazione completa\n    return true;\n    // #\n  } else {\n    // Validazione fallita, ritorno un \"codice errore\"\n    return \"portataMediaInvalida\";\n  }\n} catch (e) {}"
                    },
                    "validateWhenHidden": false,
                    "key": "JS_ESERCIZIO_DERIVAZIONE.portata_med",
                    "type": "textfield",
                    "input": true
                  }
                ],
                "currentWidth": 4
              },
              {
                "size": "md",
                "width": 4,
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
                    "key": "JS_ESERCIZIO_DERIVAZIONE.volume_max",
                    "type": "textfield",
                    "input": true
                  }
                ],
                "currentWidth": 4
              }
            ],
            "tableView": false
          }
        ],
        "collapsible": false
      },
      {
        "key": "sovracanone",
        "type": "panel",
        "input": false,
        "label": "Panel",
        "title": "Sovracanone",
        "tableView": false,
        "components": [
          {
            "key": "sovracanoneHeaderHelper",
            "type": "columns",
            "input": false,
            "label": "sovracanone_header_helper",
            "columns": [
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": "auto",
                "offset": 0,
                "components": [
                  {
                    "key": "headerSovracanone",
                    "type": "htmlelement",
                    "attrs": [
                      {
                        "attr": "",
                        "value": ""
                      }
                    ],
                    "input": false,
                    "label": "HeaderSovracanone",
                    "content": "Indicare se è presente un sovracanone, di quale tipo e l'elenco dei bacini e/o dei comuni interessati",
                    "tableView": false,
                    "refreshOnChange": false
                  }
                ],
                "currentWidth": "auto"
              },
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": "auto",
                "offset": 0,
                "components": [
                  {
                    "key": "JS_HELP.sovracanone_testo_iniz",
                    "type": "button",
                    "event": "helpBtnClick",
                    "input": true,
                    "label": "<i class='fa fa-question-circle fa-lg' aria-hidden='true'></i>",
                    "action": "event",
                    "tableView": false,
                    "customClass": "help-btn",
                    "showValidations": false,
                    "hideOnChildrenHidden": false
                  }
                ],
                "currentWidth": "auto"
              }
            ],
            "tableView": false
          },
          {
            "key": "JS_SOVRACANONE.flg_sovracanone",
            "type": "radio",
            "input": true,
            "label": "Presenza di sovracanone per uso energetico con potenza nominale media annua > 220 kW",
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
              "required": true,
              "customMessage": "Selezionare un'opzione."
            },
            "tableView": false,
            "defaultValue": "no",
            "validateWhenHidden": false,
            "optionsLabelPosition": "right"
          },
          {
            "key": "JS_MODAL.JS_SOVRACANONE.flg_sovracanone",
            "type": "textfield",
            "input": true,
            "label": "Presenza di sovracanone per uso energetico [NASCOSTO]",
            "logic": [
              {
                "name": "JS_MODAL_TRIGGER.JS_SOVRACANONE.flg_sovracanone",
                "actions": [
                  {
                    "name": "JS_MODAL_CONFERMA.JS_SOVRACANONE.flg_sovracanone",
                    "type": "customAction",
                    "customAction": "try {\n  const componenteTrigger = 'JS_SOVRACANONE.flg_sovracanone';\n  const componenteConferma = 'JS_MODAL.' + componenteTrigger;\n  const formioModals = options.SCRIVA.formioModals;\n  const formioUpdate = options.SCRIVA.formioUpdate;\n  const formioUpdateComponent = options.SCRIVA.formioUpdateComponent;\n\n  const onConfirm = () => {\n    return formioUpdateComponent(data, componenteConferma, 'no');\n  }\n  const onCancel = () => {\n    return formioUpdateComponent(data, componenteTrigger, 'si');\n  }\n  \n  const config = {\n    title: 'Conferma',\n    codeOrBody: 'A057',\n    callbacks: {\n      onConfirm: () => {\n        data = onConfirm();\n        formioUpdate(data);\n      },\n      onCancel: () => {\n        data = onCancel();\n        formioUpdate(data);\n      },\n      onClose: () => {\n        data = onCancel();\n        formioUpdate(data);\n      }\n    }\n  }\n  \n  formioModals.conferma(config);\n  \n} catch (e) {}"
                  }
                ],
                "trigger": {
                  "type": "javascript",
                  "simple": {},
                  "javascript": "const check1 = data.JS_SOVRACANONE.flg_sovracanone == 'no';\nconst check2 = data.JS_MODAL.JS_SOVRACANONE.flg_sovracanone == 'si';\nresult = check1 && check2;"
                }
              },
              {
                "name": "JS_MODAL_SET_TRIGGER.JS_SOVRACANONE.flg_sovracanone",
                "actions": [
                  {
                    "name": "JS_MODAL_SET_TRIGGER_SI.JS_SOVRACANONE.flg_sovracanone",
                    "type": "value",
                    "value": "value = 'si';"
                  }
                ],
                "trigger": {
                  "type": "simple",
                  "simple": {
                    "eq": "si",
                    "show": true,
                    "when": "JS_SOVRACANONE.flg_sovracanone"
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
            "key": "JS_FIELD_SET.JS_SOVRACANONE.flg_bim",
            "type": "fieldset",
            "input": false,
            "label": "Field Set",
            "tableView": false,
            "components": [
              {
                "key": "JS_SOVRACANONE.flg_bim",
                "type": "radio",
                "input": true,
                "label": "Per opere di captazione che ricadono in Bacino Imbrifero Montano",
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
                  "required": true,
                  "customMessage": "Selezionare un'opzione."
                },
                "tableView": false,
                "defaultValue": "no",
                "validateWhenHidden": false,
                "optionsLabelPosition": "right"
              },
              {
                "key": "JS_MODAL.JS_SOVRACANONE.flg_bim",
                "type": "textfield",
                "input": true,
                "label": "Per opere di captazione che ricadono in Bacino Imbrifero Montano [NASCOSTO]",
                "logic": [
                  {
                    "name": "JS_MODAL_TRIGGER.JS_SOVRACANONE.flg_bim",
                    "actions": [
                      {
                        "name": "JS_MODAL_CONFERMA.JS_SOVRACANONE.flg_bim",
                        "type": "customAction",
                        "customAction": "try {\n  const componenteTrigger = 'JS_SOVRACANONE.flg_bim';\n  const componenteConferma = 'JS_MODAL.' + componenteTrigger;\n  const formioModals = options.SCRIVA.formioModals;\n  const formioUpdate = options.SCRIVA.formioUpdate;\n  const formioUpdateComponent = options.SCRIVA.formioUpdateComponent;\n\n  const onConfirm = () => {\n    return formioUpdateComponent(data, componenteConferma, 'no');\n  }\n  const onCancel = () => {\n    return formioUpdateComponent(data, componenteTrigger, 'si');\n  }\n  \n  const config = {\n    title: 'Conferma',\n    codeOrBody: 'A062',\n    callbacks: {\n      onConfirm: () => {\n        data = onConfirm();\n        formioUpdate(data);\n      },\n      onCancel: () => {\n        data = onCancel();\n        formioUpdate(data);\n      },\n      onClose: () => {\n        data = onCancel();\n        formioUpdate(data);\n      }\n    }\n  }\n  \n  formioModals.conferma(config);\n  \n} catch (e) {}"
                      }
                    ],
                    "trigger": {
                      "type": "javascript",
                      "simple": {},
                      "javascript": "const check1 = data.JS_SOVRACANONE.flg_bim == 'no';\nconst check2 = data.JS_MODAL.JS_SOVRACANONE.flg_bim == 'si';\nresult = check1 && check2;"
                    }
                  },
                  {
                    "name": "JS_MODAL_SET_TRIGGER.JS_SOVRACANONE.flg_bim",
                    "actions": [
                      {
                        "name": "JS_MODAL_SET_TRIGGER_SI.JS_SOVRACANONE.flg_bim",
                        "type": "value",
                        "value": "value = 'si';"
                      }
                    ],
                    "trigger": {
                      "type": "simple",
                      "simple": {
                        "eq": "si",
                        "show": true,
                        "when": "JS_SOVRACANONE.flg_bim"
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
                "key": "JS_SOVRACANONE.elenco_bim",
                "type": "editgrid",
                "input": true,
                "label": "<strong>Elenco Bacini Imbriferi Montani</strong>",
                "saveRow": "CONFERMA",
                "validate": {
                  "custom": "try {\n  const lista = input;\n  const listaUnivoca = _.uniqBy(input, (e) => { return e.bim.id_BIM; });\n  // Liste con stessi elementi? se si, non ci sono duplicati\n  return lista.length === listaUnivoca.length;\n} catch (e) {\n  return true;\n}",
                  "customMessage": "Bacini imbriferi duplicati."
                },
                "removeRow": "ANNULLA",
                "rowDrafts": false,
                "tableView": false,
                "templates": {
                  "row": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col-sm-2\">\r\n        {{ isVisibleInRow(component) ? getView(component, row[component.key]) : ''}}\r\n      </div>\r\n    {% } %}\r\n  {% }) %}\r\n  {% if (!instance.options.readOnly && !instance.disabled) { %}\r\n    <div class=\"col-sm-2\">\r\n      <div class=\"btn-group pull-left\">\r\n        <button class=\"btn btn-default btn-light btn-sm editRow\"><i class=\"{{ iconClass('edit') }}\"></i></button>\r\n        {% if (!instance.hasRemoveButtons || instance.hasRemoveButtons()) { %}\r\n          <button class=\"btn btn-danger btn-sm removeRow\"><i class=\"{{ iconClass('trash') }}\"></i></button>\r\n        {% } %}\r\n      </div>\r\n    </div>\r\n  {% } %}\r\n</div>",
                  "header": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col-sm-2\">{{ t(component.label) }}</div>\r\n    {% } %}\r\n  {% }) %}\r\n  <div class=\"col-sm-2\">Azioni</div>\r\n</div>"
                },
                "addAnother": "AGGIUNGI BACINO",
                "components": [
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
                            "key": "bim",
                            "data": {
                              "custom": "values = [\r\n  { denom_BIM: 'Ticino', id_BIM: '01' },\r\n  { denom_BIM: 'Sesia', id_BIM: '02' },\r\n  { denom_BIM: 'Dora Baltea', id_BIM: '03' },\r\n  { denom_BIM: 'Orco', id_BIM: '04' },\r\n  { denom_BIM: 'Stura di Lanzo', id_BIM: '05' },\r\n  { denom_BIM: 'Dora Riparia', id_BIM: '06' },\r\n  { denom_BIM: 'Sangone', id_BIM: '07' },\r\n  { denom_BIM: 'Pellice', id_BIM: '08' },\r\n  { denom_BIM: 'Po', id_BIM: '09' },\r\n  { denom_BIM: 'Varaita', id_BIM: '10' },\r\n  { denom_BIM: 'Maira', id_BIM: '11' },\r\n  { denom_BIM: 'Tanaro', id_BIM: \"12\" },\r\n  { denom_BIM: 'Bormida', id_BIM: \"13\" },\r\n  { denom_BIM: 'Scrivia', id_BIM: \"14\" }\r\n]"
                            },
                            "type": "select",
                            "input": true,
                            "label": "Bacini Imbriferi Montani",
                            "unique": true,
                            "widget": "choicesjs",
                            "dataSrc": "custom",
                            "template": "<span>{{ item.denom_BIM }}</span>",
                            "validate": {
                              "required": true
                            },
                            "tableView": true,
                            "errorLabel": "Inserire il bacino.",
                            "validateWhenHidden": false
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
                  }
                ],
                "conditional": {
                  "eq": "no",
                  "show": false,
                  "when": "JS_MODAL.JS_SOVRACANONE.flg_bim"
                },
                "displayAsTable": false,
                "customConditional": "try {\n  // Recupero il valore della input modale\n  const inputUtente = data.JS_SOVRACANONE.flg_bim;\n  const inputModale = data.JS_MODAL.JS_SOVRACANONE.flg_bim;\n  // Se l'input ha un valore specifico o quello di init devo nascondere l'elemento\n  const caseOnInit = inputUtente === 'si' && inputModale === 'init';\n  const caseOnUse = inputModale === 'si';\n  // Definisco le condizioni di visualizzazione\n  show = caseOnInit || caseOnUse;\n} catch(e) {\n  show = true;\n}",
                "validateWhenHidden": false
              },
              {
                "key": "JS_SOVRACANONE.flg_rivieraschi",
                "type": "radio",
                "input": true,
                "label": "Presenza di comuni rivieraschi",
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
                  "required": true,
                  "customMessage": "Selezionare un'opzione."
                },
                "tableView": false,
                "defaultValue": "no",
                "validateWhenHidden": false,
                "optionsLabelPosition": "right"
              },
              {
                "key": "JS_MODAL.JS_SOVRACANONE.flg_rivieraschi",
                "type": "textfield",
                "input": true,
                "label": "Presenza di comuni rivieraschi [NASCOSTO]",
                "logic": [
                  {
                    "name": "JS_MODAL_TRIGGER.JS_SOVRACANONE.flg_rivieraschi",
                    "actions": [
                      {
                        "name": "JS_MODAL_CONFERMA.JS_SOVRACANONE.flg_rivieraschi",
                        "type": "customAction",
                        "customAction": "try {\n  const componenteTrigger = 'JS_SOVRACANONE.flg_rivieraschi';\n  const componenteConferma = 'JS_MODAL.' + componenteTrigger;\n  const formioModals = options.SCRIVA.formioModals;\n  const formioUpdate = options.SCRIVA.formioUpdate;\n  const formioUpdateComponent = options.SCRIVA.formioUpdateComponent;\n\n  const onConfirm = () => {\n    return formioUpdateComponent(data, componenteConferma, 'no');\n  }\n  const onCancel = () => {\n    return formioUpdateComponent(data, componenteTrigger, 'si');\n  }\n  \n  const config = {\n    title: 'Conferma',\n    codeOrBody: 'A061',\n    callbacks: {\n      onConfirm: () => {\n        data = onConfirm();\n        formioUpdate(data);\n      },\n      onCancel: () => {\n        data = onCancel();\n        formioUpdate(data);\n      },\n      onClose: () => {\n        data = onCancel();\n        formioUpdate(data);\n      }\n    }\n  }\n  \n  formioModals.conferma(config);\n  \n} catch (e) {}"
                      }
                    ],
                    "trigger": {
                      "type": "javascript",
                      "simple": {},
                      "javascript": "const check1 = data.JS_SOVRACANONE.flg_rivieraschi == 'no';\nconst check2 = data.JS_MODAL.JS_SOVRACANONE.flg_rivieraschi == 'si';\nresult = check1 && check2;"
                    }
                  },
                  {
                    "name": "JS_MODAL_SET_TRIGGER.JS_SOVRACANONE.flg_rivieraschi",
                    "actions": [
                      {
                        "name": "JS_MODAL_SET_TRIGGER_SI.JS_SOVRACANONE.flg_rivieraschi",
                        "type": "value",
                        "value": "value = 'si';"
                      }
                    ],
                    "trigger": {
                      "type": "simple",
                      "simple": {
                        "eq": "si",
                        "show": true,
                        "when": "JS_SOVRACANONE.flg_rivieraschi"
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
                "key": "JS_SOVRACANONE.elenco_comuni_rivieraschi",
                "type": "editgrid",
                "input": true,
                "label": "<strong>Elenco Comuni Rivieraschi</strong>",
                "saveRow": "CONFERMA",
                "validate": {
                  "custom": "try {\n  const lista = input;\n  const listaUnivoca = _.uniqBy(input, (e) => { return e.comune.cod_istat_comune; });\n  // Liste con stessi elementi? se si, non ci sono duplicati\n  return lista.length === listaUnivoca.length;\n} catch (e) {\n  return true;\n}",
                  "customMessage": "I comuni sono duplicati"
                },
                "removeRow": "ANNULLA",
                "rowDrafts": false,
                "tableView": false,
                "templates": {
                  "row": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col-sm-2\">\r\n        {{ isVisibleInRow(component) ? getView(component, row[component.key]) : ''}}\r\n      </div>\r\n    {% } %}\r\n  {% }) %}\r\n  {% if (!instance.options.readOnly && !instance.disabled) { %}\r\n    <div class=\"col-sm-2\">\r\n      <div class=\"btn-group pull-left\">\r\n        <button class=\"btn btn-default btn-light btn-sm editRow\"><i class=\"{{ iconClass('edit') }}\"></i></button>\r\n        {% if (!instance.hasRemoveButtons || instance.hasRemoveButtons()) { %}\r\n          <button class=\"btn btn-danger btn-sm removeRow\"><i class=\"{{ iconClass('trash') }}\"></i></button>\r\n        {% } %}\r\n      </div>\r\n    </div>\r\n  {% } %}\r\n</div>",
                  "header": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col-sm-2\">{{ t(component.label) }}</div>\r\n    {% } %}\r\n  {% }) %}\r\n  <div class=\"col-sm-2\">Azioni</div>\r\n</div>\r\n"
                },
                "addAnother": "AGGIUNGI COMUNE",
                "components": [
                  {
                    "key": "columns_ec_4",
                    "type": "columns",
                    "input": false,
                    "label": "columns_ec_4",
                    "columns": [
                      {
                        "pull": 0,
                        "push": 0,
                        "size": "md",
                        "width": 4,
                        "offset": 0,
                        "components": [
                          {
                            "key": "provincia",
                            "data": {
                              "url": "{{form.puntamento.value}}/limiti-amministrativi/province?id_adempimento=17",
                              "headers": [
                                {
                                  "key": "",
                                  "value": ""
                                }
                              ]
                            },
                            "type": "select",
                            "input": true,
                            "label": "Provincia",
                            "widget": "choicesjs",
                            "dataSrc": "url",
                            "template": "<span>{{ item.denom_provincia }}</span>",
                            "validate": {
                              "required": true,
                              "customMessage": "Inserire la provincia."
                            },
                            "tableView": true,
                            "disableLimit": true,
                            "clearOnRefresh": true,
                            "noRefreshOnScroll": false,
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
                            "key": "comune",
                            "data": {
                              "url": "{{form.puntamento.value}}/limiti-amministrativi/comuni?cod_istat_provincia={{ row.provincia.cod_provincia }}&attiva=true",
                              "headers": [
                                {
                                  "key": "",
                                  "value": ""
                                }
                              ]
                            },
                            "type": "select",
                            "input": true,
                            "label": "Comune",
                            "logic": [
                              {
                                "name": "disabilitaComunePerProvincia",
                                "actions": [
                                  {
                                    "name": "disabilitaComune",
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
                                  "simple": {},
                                  "javascript": "result = _.isEmpty(row.provincia);"
                                }
                              }
                            ],
                            "widget": "choicesjs",
                            "dataSrc": "url",
                            "template": "<span>{{ item.denom_comune }}</span>",
                            "validate": {
                              "required": true,
                              "customMessage": "Inserire il comune."
                            },
                            "refreshOn": "JS_SOVRACANONE.elenco_comuni_rivieraschi.provincia",
                            "tableView": true,
                            "disableLimit": true,
                            "clearOnRefresh": true,
                            "noRefreshOnScroll": false,
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
                    "tableView": false
                  }
                ],
                "conditional": {
                  "eq": "no",
                  "show": false,
                  "when": "JS_MODAL.JS_SOVRACANONE.flg_rivieraschi"
                },
                "displayAsTable": false,
                "customConditional": "try {\n  // Recupero il valore della input modale\n  const inputUtente = data.JS_SOVRACANONE.flg_rivieraschi;\n  const inputModale = data.JS_MODAL.JS_SOVRACANONE.flg_rivieraschi;\n  // Se l'input ha un valore specifico o quello di init devo nascondere l'elemento\n  const caseOnInit = inputUtente === 'si' && inputModale === 'init';\n  const caseOnUse = inputModale === 'si';\n  // Definisco le condizioni di visualizzazione\n  show = caseOnInit || caseOnUse;\n} catch(e) {\n  show = true;\n}",
                "validateWhenHidden": false
              }
            ],
            "customConditional": "try {\n  // Recupero il valore della input modale\n  const inputUtente = data.JS_SOVRACANONE.flg_sovracanone;\n  const inputModale = data.JS_MODAL.JS_SOVRACANONE.flg_sovracanone;\n  // Se l'input ha un valore specifico o quello di init devo nascondere l'elemento\n  const caseOnInit = inputUtente === 'si' && inputModale === 'init';\n  const caseOnUse = inputModale === 'si';\n  // Definisco le condizioni di visualizzazione\n  show = caseOnInit || caseOnUse;\n} catch(e) {\n  show = true;\n}"
          }
        ],
        "collapsible": false
      },
      {
        "key": "panelUsi",
        "type": "panel",
        "input": false,
        "label": "Panel",
        "title": "Usi",
        "tableView": false,
        "components": [
          {
            "key": "JS_USI.flg_uso_promiscuo",
            "type": "radio",
            "input": true,
            "label": "Presenza di uso promiscuo",
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
              "required": true,
              "customMessage": "Selezionare un'opzione."
            },
            "tableView": false,
            "defaultValue": "no",
            "validateWhenHidden": false,
            "optionsLabelPosition": "right"
          },
          {
            "key": "labelQtaIdriciComplessivi",
            "tag": "strong",
            "type": "htmlelement",
            "attrs": [
              {
                "attr": "",
                "value": ""
              }
            ],
            "input": false,
            "label": "labelQtaIdriciComplessivi",
            "content": "<strong>Quantitativi idrici complessivi</strong>",
            "tableView": false,
            "refreshOnChange": false
          },
          {
            "key": "columnsHeaderUsi",
            "type": "columns",
            "input": false,
            "label": "ColumnsHeaderUsi",
            "columns": [
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": "auto",
                "offset": 0,
                "components": [
                  {
                    "key": "headerUsi",
                    "type": "htmlelement",
                    "attrs": [
                      {
                        "attr": "",
                        "value": ""
                      }
                    ],
                    "input": false,
                    "label": "HeaderUsi",
                    "content": "Indicare i periodi in cui il prelievo verrà effettuato, l’uso ed i quantitativi di portata e volume per ciascun periodo",
                    "tableView": false,
                    "refreshOnChange": false
                  }
                ],
                "currentWidth": "auto"
              },
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": "auto",
                "offset": 0,
                "components": [
                  {
                    "key": "JS_HELP.usi_periodi_prelievo",
                    "type": "button",
                    "event": "helpBtnClick",
                    "input": true,
                    "label": "<i class='fa fa-question-circle fa-lg' aria-hidden='true'></i>",
                    "action": "event",
                    "tableView": false,
                    "customClass": "help-btn",
                    "showValidations": false,
                    "hideOnChildrenHidden": false
                  }
                ],
                "currentWidth": "auto"
              }
            ],
            "tableView": false
          },
          {
            "key": "JS_USI.quantitativi_idrici_complessivi",
            "type": "editgrid",
            "input": true,
            "label": "Quantitativi idrici complessivi",
            "saveRow": "CONFERMA",
            "validate": {
              "required": true,
              "customMessage": "Inserire almeno un periodo."
            },
            "removeRow": "ANNULLA",
            "rowDrafts": false,
            "tableView": false,
            "templates": {
              "row": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">\r\n        {{ isVisibleInRow(component) ? getView(component, row[component.key]) : ''}}\r\n      </div>\r\n    {% } %}\r\n  {% }) %}\r\n  {% if (!instance.options.readOnly && !instance.disabled) { %}\r\n    <div class=\"col\">\r\n      <div class=\"btn-group pull-left\">\r\n        <button class=\"btn btn-default btn-light btn-sm editRow\"><i class=\"{{ iconClass('edit') }}\"></i></button>\r\n        {% if (!instance.hasRemoveButtons || instance.hasRemoveButtons()) { %}\r\n          <button class=\"btn btn-danger btn-sm removeRow\"><i class=\"{{ iconClass('trash') }}\"></i></button>\r\n        {% } %}\r\n      </div>\r\n    </div>\r\n  {% } %}\r\n</div>\r\n",
              "header": "<div class=\"row\">\r\n  {% util.eachComponent(components, function(component) { %}\r\n    {% if (displayValue(component)) { %}\r\n      <div class=\"col\">{{ t(component.label) }}</div>\r\n    {% } %}\r\n  {% }) %}\r\n  <div class=\"col\">Azioni</div>\r\n</div>\r\n"
            },
            "addAnother": "AGGIUNGI PERIODO",
            "components": [
              {
                "key": "columnsRiga1",
                "type": "columns",
                "input": false,
                "label": "columns_riga1",
                "columns": [
                  {
                    "pull": 0,
                    "push": 0,
                    "size": "md",
                    "width": 4,
                    "offset": 0,
                    "components": [
                      {
                        "label": "portata max istantanea (l/s)",
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
                        "label": "portata media prelievo (l/s)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                          "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, inferiore o uguale alla portata massima, con un massimo di 7 cifre intere e 4 cifre decimali.",
                          "custom": "try {\n  // Definisco un oggetto con le informazioni da passare per la verifica\n  const params = {\n    portataMedia: input,\n    portataMassima: row.portata_max,\n    checkZero: true,\n  };\n  // Recupero la funzione di comodo per la verifica\n  const qdrDERCheckPortataMedia = options.SCRIVA.formioQuadri.qdrDERCheckPortataMedia;\n  // Recupero le informazioni per portata media e portata massima\n  const check = qdrDERCheckPortataMedia(params);\n  // Verifico il risultato dell'elaborazione sul check\n  if (check) {\n    // Validazione completa\n    return true;\n    // #\n  } else {\n    // Validazione fallita, ritorno un \"codice errore\"\n    return \"portataMediaInvalida\";\n  }\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "portata_med",
                        "type": "textfield",
                        "input": true
                      }
                    ],
                    "currentWidth": 4
                  },
                  {
                    "size": "md",
                    "width": 4,
                    "components": [
                      {
                        "label": "volume max annuo (m<sup>3</sup>)",
                        "applyMaskOn": "change",
                        "tableView": true,
                        "validate": {
                          "required": true,
                          "pattern": "(^\\d{1,9}$)|(^\\d{1,9}[,]\\d{1,5}$)",
                          "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 9 cifre intere e 5 cifre decimali.",
                          "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                        },
                        "validateWhenHidden": false,
                        "key": "volume_max",
                        "type": "textfield",
                        "input": true
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
                "label": "columns_riga2",
                "columns": [
                  {
                    "pull": 0,
                    "push": 0,
                    "size": "md",
                    "width": 4,
                    "offset": 0,
                    "components": [
                      {
                        "key": "uso_legge",
                        "data": {
                          "custom": "values = [\r\n  { denom_uso_legge: 'energetico', id_uso_legge: 1 },\r\n  { denom_uso_legge: 'piscicolo', id_uso_legge: 2 },\r\n  { denom_uso_legge: 'agricolo', id_uso_legge: 3 },\r\n  { denom_uso_legge: 'potabile', id_uso_legge: 4 },\r\n  { denom_uso_legge: 'civile', id_uso_legge: 5 },\r\n  { denom_uso_legge: 'produzione beni e servizi', id_uso_legge: 6 },\r\n  { denom_uso_legge: 'riqualificazione dell\\'energia', id_uso_legge: 7 },\r\n  { denom_uso_legge: 'lavaggio di inerti', id_uso_legge: 8 },\r\n  { denom_uso_legge: 'zootecnico', id_uso_legge: 9 },\r\n  { denom_uso_legge: 'domestico di acque superficiali', id_uso_legge: 10 }\r\n]"
                        },
                        "type": "select",
                        "input": true,
                        "label": "uso di legge",
                        "widget": "choicesjs",
                        "dataSrc": "custom",
                        "template": "<span>{{ item.denom_uso_legge }}</span>",
                        "validate": {
                          "required": true,
                          "customMessage": "Inserire l'uso di legge."
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
                    "components": [
                      {
                        "key": "inizio_periodo",
                        "type": "textfield",
                        "input": true,
                        "label": "dal (gg/mm)",
                        "validate": {
                          "custom": "try {\n  // Uso la funzione di utility dalle options\n  const dateFormat = 'DD/MM';\n  const allow29thFeb = false;\n  const checkDateFormat = options.SCRIVA.utilities.checkDateFormat;\n  return checkDateFormat(input, dateFormat, allow29thFeb) ? true : 'invalidDate';\n} catch (e) {\n  // Errore, default\n  return true;\n}",
                          "pattern": "(^([0-2]{0,1}[0-9]|3[0,1])[/]([1-9]|0[1-9]|1[0-2])$)",
                          "required": true,
                          "customMessage": "Inserire nel formato giorno/mese."
                        },
                        "tableView": true,
                        "validateOn": "blur",
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
                        "key": "fine_periodo",
                        "type": "textfield",
                        "input": true,
                        "label": "al (gg/mm)",
                        "validate": {
                          "custom": "try {\n  // Uso la funzione di utility dalle options\n  const dateFormat = 'DD/MM';\n  const allow29thFeb = false;\n  const checkDateFormat = options.SCRIVA.utilities.checkDateFormat;\n  return checkDateFormat(input, dateFormat, allow29thFeb) ? true : 'invalidDate';\n} catch (e) {\n  // Errore, default\n  return true;\n}",
                          "pattern": "(^([0-2]{0,1}[0-9]|3[0,1])[/]([1-9]|0[1-9]|1[0-2])$)",
                          "required": true,
                          "customMessage": "Inserire nel formato giorno/mese."
                        },
                        "tableView": true,
                        "validateOn": "blur",
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
          },
          {
            "key": "JS_USI.capacita_accumulo",
            "type": "textarea",
            "input": true,
            "label": "Indicare eventuali capacità di accumulo, se previste",
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
        "collapsible": false
      },
      {
        "key": "panelRestituzione",
        "type": "panel",
        "input": false,
        "label": "Panel",
        "title": "Restituzione",
        "tableView": false,
        "components": [
          {
            "key": "columnsHeaderRestituzione",
            "type": "columns",
            "input": false,
            "label": "ColumnsHeaderRestituzione",
            "columns": [
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": "auto",
                "offset": 0,
                "components": [
                  {
                    "key": "headerRestituzione",
                    "type": "htmlelement",
                    "attrs": [
                      {
                        "attr": "",
                        "value": ""
                      }
                    ],
                    "input": false,
                    "label": "HeaderRestituzione",
                    "content": "Indicare i valori complessivi di portata e volume restituiti",
                    "tableView": false,
                    "refreshOnChange": false
                  }
                ],
                "currentWidth": "auto"
              },
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": "auto",
                "offset": 0,
                "components": [
                  {
                    "key": "JS_HELP.restituzione_testo_iniz",
                    "type": "button",
                    "event": "helpBtnClick",
                    "input": true,
                    "label": "<i class='fa fa-question-circle fa-lg' aria-hidden='true'></i>",
                    "action": "event",
                    "tableView": false,
                    "customClass": "help-btn",
                    "showValidations": false,
                    "hideOnChildrenHidden": false
                  }
                ],
                "currentWidth": "auto"
              }
            ],
            "tableView": false
          },
          {
            "key": "columnsRestituzione",
            "type": "columns",
            "input": false,
            "label": "ColumnsRestituzione",
            "columns": [
              {
                "pull": 0,
                "push": 0,
                "size": "md",
                "width": 4,
                "offset": 0,
                "components": [
                  {
                    "label": "Portata complessivamente restituita (l/s)",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "validate": {
                      "required": true,
                      "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,4}$)",
                      "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 7 cifre intere e 4 cifre decimali.",
                      "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                    },
                    "validateWhenHidden": true,
                    "key": "JS_RESTITUZIONE.portata_tot_rest",
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
                    "label": "volume annuo complessivamente restituito (m<sup>3</sup>)",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "validate": {
                      "required": true,
                      "pattern": "(^\\d{1,9}$)|(^\\d{1,9}[,]\\d{1,5}$)",
                      "customMessage": "Campo obbligatorio e numerico. Indicare un valore maggiore di 0, con un massimo di 9 cifre intere e 5 cifre decimali.",
                      "custom": "try {\r\n  // Recupero la funzione di comodo per la verifica del numero maggiore di zero\r\n  const formioStringNumberGreaterThanZero = options.SCRIVA.utilities.formioStringNumberGreaterThanZero;\r\n  // Recupero le informazioni per portata media e portata massima\r\n  const campoVerifica = input;\r\n  // Lancio la verifica sul campo\r\n  const verificaGreatZero = formioStringNumberGreaterThanZero(campoVerifica);\r\n  // La portata media deve essere minore/uguale alla massima\r\n  if (!verificaGreatZero) {\r\n    // Validazione fallita, ritorno un \"codice errore\"\r\n    return \"campoMinoreUgualeZero\";\r\n    // #\r\n  } else {\r\n    // Validazione completa\r\n    return true;\r\n  }\r\n} catch (e) {}"
                    },
                    "validateWhenHidden": false,
                    "key": "JS_RESTITUZIONE.vol_annuo_tot_rest",
                    "type": "textfield",
                    "input": true
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
          }
        ],
        "collapsible": false
      },
      {
        "key": "panelNote",
        "type": "panel",
        "input": false,
        "label": "Panel",
        "title": "Note",
        "tableView": false,
        "components": [
          {
            "key": "JS_NOTE.note_derivazione",
            "type": "textarea",
            "input": true,
            "label": "Indicare eventuali note, se necessarie (max 500 ch)",
            "validate": {
              "maxLength": 500,
              "customMessage": "Inserire al massimo 500 caratteri."
            },
            "tableView": true,
            "autoExpand": false,
            "applyMaskOn": "change",
            "validateWhenHidden": false
          }
        ],
        "collapsible": false
      }
    ]
  },
  "componentName": "FormioDatiGeneraliComponent"
};
