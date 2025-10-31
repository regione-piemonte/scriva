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
export const QDR_DETT_OGGETTO_VINCA_APP_DEBUG = {
  "label": "Dettaglio Progetto",
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
        "key": "VINCA_APP",
        "type": "hidden",
        "input": true,
        "label": "VINCA_APP",
        "tableView": true,
        "defaultValue": "VINCA_APP"
      },
      {
        "key": "title",
        "type": "htmlelement",
        "input": false,
        "label": "title",
        "content": "Compila le parti di interesse per il progetto/intervento/attività dell'istanza.",
        "tableView": false,
        "refreshOnChange": false
      },
      {
        "key": "string_campo_obbligatorio",
        "tag": "small",
        "type": "htmlelement",
        "input": false,
        "label": "string_campo_obbligatorio",
        "content": "*Campo obbligatorio",
        "className": "text-muted",
        "tableView": false,
        "customClass": "form-text",
        "refreshOnChange": false
      },
      {
        "key": "JS_CATEGORIE_PROGETTUALI.elenco_categorie",
        "data": {
          "url": "{{form.puntamento.value}}/categorie-progettuali?id_adempimento=14",
          "headers": [
            {
              "key": "Attore-Scriva",
              "value": "FO/FO"
            }
          ]
        },
        "type": "select",
        "input": true,
        "label": "Seleziona la tipologia di P/I/A",
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
          },
          {
            "name": "handlePlaceholder",
            "actions": [
              {
                "name": "blankPlaceholder",
                "text": "               ",
                "type": "property",
                "property": {
                  "type": "string",
                  "label": "Placeholder",
                  "value": "placeholder"
                }
              }
            ],
            "trigger": {
              "type": "javascript",
              "javascript": "// #### NOTA BENE: Per gestire il flusso bisgona assegnare alla variabile \"result\" un valore booleano ####\n// Recupero, dall'oggetto \"data\" tutte le informazioni presenti nel form\nvar formData = data;\n// Verifico che esista l'oggetto\nif (!formData) {\n  result = false;\n  return result;\n}\n\n// Estraggo tramite chiave definita per il controller, le informazioni per il controllo\nvar categorieProg = formData.JS_CATEGORIE_PROGETTUALI; // Oggetto\n// Verifico che esista l'oggetto\nif (!categorieProg) {\n  result = false;\n  return result;\n}\n\n// Estraggo il valore effettivo dal dato per il controller\nvar elencoCat = categorieProg.elenco_categorie; // Array di oggetti CategoriaOggetto\n// Verifico che esista l'oggetto\nif (elencoCat && elencoCat.length > 0) {\n  result = true;\n  return result;\n} else {\n  result = false;\n  return result;\n}"
            }
          }
        ],
        "widget": "choicesjs",
        "dataSrc": "url",
        "lazyLoad": false,
        "multiple": true,
        "template": "<span>{{ item.des_categoria_oggetto }}</span>",
        "validate": {
          "multiple": true,
          "required": true
        },
        "refreshOn": "data",
        "tableView": true,
        "persistent": false,
        "customClass": "question-grey",
        "placeholder": "Aggiungi",
        "disableLimit": false
      },
      {
        "key": "JS_CATEGORIE_PROGETTUALI.des_altra_categoria_oggetto",
        "type": "textarea",
        "input": true,
        "label": "Indica la tipologia del Progetto, Intervento o Attività",
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
          "maxLength": 500,
          "customMessage": "Campo obbligatorio"
        },
        "tableView": true,
        "autoExpand": false,
        "customClass": "sub-question",
        "placeholder": "Indica la tipologia",
        "customConditional": "// Recupero, dall'oggetto \"data\" tutte le informazioni presenti nel form\nvar formData = data;\n// Verifico che esista l'oggetto\nif (!formData) { return false; }\n\n// Estraggo tramite chiave definita per il controller, le informazioni per il controllo\nvar categorieProg = formData.JS_CATEGORIE_PROGETTUALI; // Oggetto\n// Verifico che esista l'oggetto\nif (!categorieProg) { return false; }\n\n// Estraggo il valore effettivo dal dato per il controller\nvar elencoCat = categorieProg.elenco_categorie; // Array di oggetti CategoriaOggetto\n// Verifico che esista l'oggetto\nif (!elencoCat || elencoCat.length == 0) { return false; }\n\n// Ho una lista di elementi, vado a verificare se esiste l'elemento \"Altro\"\nvar codCatOgg = 'ALTRO';\n// Cerco all'interno dell'array per cod_categoria_oggetto\nvar categoriaAltro = elencoCat.find((categoria) => {\n  // Effettuo un controllo per codici categoria oggetto\n  return categoria.cod_categoria_oggetto == codCatOgg;\n  // #\n});\n// Definisco un flag di comodo\nvar hasCatAltro = categoriaAltro != undefined;\n\n// La visibilità è dettata dal fatto che la categoria \"Altro\" sia presente\nreturn hasCatAltro;"
      },
      {
        "label": "Contesto localizzativo",
        "optionsLabelPosition": "right",
        "customClass": "question-grey",
        "tableView": false,
        "values": [
          {
            "label": "Centro urbano",
            "value": "centro_urbano",
            "shortcut": ""
          },
          {
            "label": "Zona periurbana",
            "value": "zona_periurbana",
            "shortcut": ""
          },
          {
            "label": "Aree agricole",
            "value": "aree_agricole",
            "shortcut": ""
          },
          {
            "label": "Aree industriali",
            "value": "aree_industriali",
            "shortcut": ""
          },
          {
            "label": "Aree naturali",
            "value": "aree_naturali",
            "shortcut": ""
          },
          {
            "label": "Altro",
            "value": "altro_contesto",
            "shortcut": ""
          }
        ],
        "persistent": false,
        "validate": {
          "required": true,
          "customMessage": "Seleziona almeno una voce dall'elenco proposto"
        },
        "validateWhenHidden": false,
        "key": "JS_LOCALIZZAZIONE.contesto_oggetto",
        "logic": [
          {
            "name": "enableLogic",
            "actions": [
              {
                "name": "enableAction",
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
                "eq": "DISABLED",
                "show": true,
                "when": "disableFlag"
              }
            }
          }
        ],
        "type": "selectboxes",
        "input": true,
        "inputType": "checkbox"
      },
      {
        "label": "Indica il contesto localizzativo",
        "applyMaskOn": "change",
        "autoExpand": false,
        "tableView": true,
        "validate": {
          "customMessage": "Campo obbligatorio"
        },
        "validateWhenHidden": false,
        "key": "JS_LOCALIZZAZIONE.des_altro_contesto",
        "customConditional": "show = false;\ntry {\n  const contestoOggetto = data.JS_LOCALIZZAZIONE.contesto_oggetto;\n  const altroContesto = contestoOggetto.altro_contesto;\n  const isAltroContesto = altroContesto === true;\n  show = isAltroContesto;\n} catch(e) {}",
        "logic": [
          {
            "name": "enableLogic",
            "actions": [
              {
                "name": "enableAction",
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
                "eq": "DISABLED",
                "show": true,
                "when": "disableFlag"
              }
            }
          },
          {
            "name": "checkAltroContesto",
            "trigger": {
              "type": "javascript",
              "javascript": "result = false;\ntry {\n  const contestoOggetto = data.JS_LOCALIZZAZIONE.contesto_oggetto;\n  const altroContesto = contestoOggetto.altro_contesto;\n  const isAltroContesto = altroContesto === true;\n  result = isAltroContesto;\n} catch(e) {}"
            },
            "actions": [
              {
                "name": "setRequired",
                "type": "property",
                "property": {
                  "label": "Required",
                  "value": "validate.required",
                  "type": "boolean"
                },
                "state": true
              }
            ]
          }
        ],
        "type": "textarea",
        "input": true
      },
      {
        "key": "JS_DESCRIZIONE_PROGETTO.flg_progetto_collegato",
        "type": "radio",
        "input": true,
        "label": "Il progetto/intervento/attività è parte di o è connesso ad un altro P/I/A?",
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
            "label": "Sì",
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
        "customClass": "question-grey",
        "optionsLabelPosition": "right"
      },
      {
        "key": "JS_DESCRIZIONE_PROGETTO.des_progetto_collegato",
        "type": "textarea",
        "input": true,
        "label": "Indica il progetto collegato",
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
          "maxLength": 500,
          "customMessage": "Campo obbligatorio"
        },
        "tableView": true,
        "persistent": false,
        "conditional": {
          "eq": "true",
          "show": true,
          "when": "JS_DESCRIZIONE_PROGETTO.flg_progetto_collegato"
        },
        "customClass": "sub-question"
      },
      {
        "key": "questionWell",
        "type": "well",
        "input": false,
        "label": "well-label",
        "tableView": false,
        "components": [
          {
            "key": "JS_LOCALIZZAZIONE.flg_presa_visione_misure_conservazione",
            "type": "radio",
            "input": true,
            "label": "È conforme e rispetta i divieti e gli obblighi delle “Misure di conservazione per la tutela della Rete Natura 2000 del Piemonte” (approvate con DGR n. 55-7222/2023/XI del 12 luglio 2023) e/o delle Misure sito specifiche o del Piano di Gestione eventualmente definite del Sito/i Natura 2000? ",
            "logic": [
              {
                "name": "enableLogic",
                "actions": [
                  {
                    "name": "enableAction",
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
                "label": "Sì",
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
            "customClass": "question-grey",
            "optionsLabelPosition": "right"
          },
          {
            "key": "JS_HELP.misure_conservazione",
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
        "key": "JS_LOCALIZZAZIONE.des_atto_consultato_misure_conservazione",
        "type": "textarea",
        "input": true,
        "label": "Indica l'atto consultato nel caso di misure sito specifiche o piano di gestione",
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
          "maxLength": 500,
          "customMessage": "Campo obbligatorio"
        },
        "tableView": true,
        "autoExpand": false,
        "persistent": false,
        "conditional": {
          "eq": "true",
          "show": true,
          "when": "JS_LOCALIZZAZIONE.flg_presa_visione_misure_conservazione"
        },
        "customClass": "sub-question"
      }
    ]
  }
}

