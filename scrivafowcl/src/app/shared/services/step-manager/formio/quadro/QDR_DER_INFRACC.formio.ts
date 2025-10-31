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
export const QDR_DER_INFRACC_DEBUG = {
    "label": "Infrastrutture Accessorie",
    "componentName": "InfrastruttureAccessorieComponent",
    "MISURATORE_DI_PORTATA": {
        "display": "form",
        "components": [{
                "key": "id_oggetto_istanza",
                "type": "hidden",
                "input": true,
                "label": "idOggettoIstanza - oggettoIstanza.id_oggetto_istanza",
                "tableView": true,
                "customDefaultValue": "value = options.SCRIVA.oggettoIstanza.id_oggetto_istanza;"
            }, {
                "key": "id_oggetto",
                "type": "hidden",
                "input": true,
                "label": "idOpera/idOggetto - oggetto.id_oggetto",
                "tableView": true,
                "customDefaultValue": "value = options.SCRIVA.oggetto.id_oggetto;"
            }, {
                "key": "title",
                "type": "htmlelement",
                "attrs": [{
                        "attr": "",
                        "value": ""
                    }
                ],
                "input": false,
                "label": "title",
                "content": "<strong>Misuratore {{options.SCRIVA.oggetto.cod_scriva}}</strong>",
                "tableView": false,
                "refreshOnChange": false
            }, {
                "key": "string_campo_obbligatorio",
                "tag": "small",
                "type": "htmlelement",
                "attrs": [{
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
            }, {
                "key": "DATI_IDENTIFICATIVI",
                "type": "panel",
                "input": false,
                "label": "Dati identificativi",
                "title": "<strong>Dati identificativi</strong>",
                "tableView": false,
                "components": [{
                        "key": "title_di_1",
                        "type": "htmlelement",
                        "input": false,
                        "label": "title_di_1",
                        "content": "<strong>Dati identificativi</strong>",
                        "tableView": false,
                        "refreshOnChange": false
                    }, {
                        "key": "columns1",
                        "type": "columns",
                        "input": false,
                        "label": "Columns",
                        "columns": [{
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 4,
                                "offset": 0,
                                "components": [{
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
                            }, {
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 4,
                                "offset": 0,
                                "components": [],
                                "currentWidth": 4
                            }, {
                                "size": "md",
                                "width": 4,
                                "components": [],
                                "currentWidth": 4
                            }
                        ],
                        "tableView": false
                    }, {
                        "key": "columns_di_1",
                        "type": "columns",
                        "input": false,
                        "label": "Columns_di_1",
                        "columns": [{
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 6,
                                "offset": 0,
                                "components": [{
                                        "key": "DATI_IDENTIFICATIVI.dati_identificativi.tipo_strumento",
                                        "data": {
                                            "custom": "values = [\r\n  { des_tipo_strumento: 'CONTATORE TOTALIZZATORE WOLTMAN', id_tipo_strumento: 1 },\r\n  { des_tipo_strumento: 'CONTATORE TOTALIZZATORE ELETTROMAGNETICO', id_tipo_strumento: 2 },\r\n  { des_tipo_strumento: 'MISURATORE A RISALTO', id_tipo_strumento: 3 },\r\n  { des_tipo_strumento: 'SENSORE AD ULTRASUONI', id_tipo_strumento: 4 },\r\n  { des_tipo_strumento: 'SENSORE MAGNETICO', id_tipo_strumento: 5 },\r\n  { des_tipo_strumento: 'STRAMAZZO CON SONDA DI LIVELLO', id_tipo_strumento: 6 },\r\n  { des_tipo_strumento: 'STRAMAZZO CON SONDA A PRESSIONE', id_tipo_strumento: 7 },\r\n  { des_tipo_strumento: 'VENTURIMETRO', id_tipo_strumento: 8 },\r\n  { des_tipo_strumento: 'ALTRA TECNOLOGIA', id_tipo_strumento: 9 }\r\n]"
                                        },
                                        "type": "select",
                                        "input": true,
                                        "label": "Tipo di strumento",
                                        "widget": "choicesjs",
                                        "dataSrc": "custom",
                                        "template": "<span>{{ item.des_tipo_strumento }}</span>",
                                        "validate": {
                                            "required": true
                                        },
                                        "tableView": true,
                                        "validateWhenHidden": false
                                    }
                                ],
                                "currentWidth": 6
                            }, {
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 6,
                                "offset": 0,
                                "components": [{
                                        "key": "DATI_IDENTIFICATIVI.dati_identificativi.matricola",
                                        "type": "textfield",
                                        "input": true,
                                        "label": "Matricola",
                                        "logic": [{
                                                "name": "verificaComponenteAppFO",
                                                "actions": [{
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
                                                    "javascript": "result = options.SCRIVA.isFrontOffice === true;"
                                                }
                                            }
                                        ],
                                        "validate": {
                                            "maxLength": 25,
                                            "customMessage": "Il campo può contenere un massimo di 25 caratteri"
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
                    }, {
                        "key": "columns_di_2",
                        "type": "columns",
                        "input": false,
                        "label": "columns_di_2",
                        "columns": [{
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 6,
                                "offset": 0,
                                "components": [{
                                        "key": "DATI_IDENTIFICATIVI.dati_identificativi.asta_idr",
                                        "type": "radio",
                                        "input": true,
                                        "label": "Asta idrometrica",
                                        "inline": true,
                                        "values": [{
                                                "label": "Si",
                                                "value": "si",
                                                "shortcut": ""
                                            }, {
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
                                "currentWidth": 6
                            }, {
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 6,
                                "offset": 0,
                                "components": [{
                                        "key": "DATI_IDENTIFICATIVI.dati_identificativi.data_installazione",
                                        "type": "datetime",
                                        "input": true,
                                        "label": "Data di installazione",
                                        "logic": [{
                                                "name": "verificaComponenteAppFO",
                                                "actions": [{
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
                                                    "javascript": "result = options.SCRIVA.isFrontOffice === true;"
                                                }
                                            }
                                        ],
                                        "format": "dd/MM/yyyy",
                                        "widget": {
                                            "mode": "single",
                                            "type": "calendar",
                                            "format": "dd/MM/yyyy",
                                            "locale": "en",
                                            "maxDate": null,
                                            "minDate": null,
                                            "time_24hr": true,
                                            "allowInput": true,
                                            "enableTime": false,
                                            "noCalendar": false,
                                            "hourIncrement": 0,
                                            "disableWeekdays": false,
                                            "disableWeekends": false,
                                            "minuteIncrement": 0,
                                            "displayInTimezone": "viewer",
                                            "useLocaleSettings": false
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
                                "currentWidth": 6
                            }
                        ],
                        "tableView": false
                    }, {
                        "key": "DATI_IDENTIFICATIVI.dati_identificativi.note_strumento",
                        "type": "textarea",
                        "input": true,
                        "label": "Note relative al tipo di strumento",
                        "validate": {
                            "maxLength": 4000,
                            "customMessage": "Il campo può contenere un massimo di 4000 caratteri"
                        },
                        "tableView": true,
                        "autoExpand": false,
                        "applyMaskOn": "change",
                        "validateWhenHidden": false
                    }, {
                        "key": "title_di_2",
                        "type": "htmlelement",
                        "attrs": [{
                                "attr": "",
                                "value": ""
                            }
                        ],
                        "input": false,
                        "label": "title_di_2",
                        "content": "<strong>Localizzazione</strong>",
                        "tableView": false,
                        "refreshOnChange": false
                    }, {
                        "key": "columnsDi2",
                        "type": "columns",
                        "input": false,
                        "label": "Columns_di_2",
                        "columns": [{
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 4,
                                "offset": 0,
                                "components": [{
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
                                        "validateWhenHidden": false
                                    }
                                ],
                                "currentWidth": 4
                            }, {
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 4,
                                "offset": 0,
                                "components": [{
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
                                        "validateWhenHidden": false
                                    }
                                ],
                                "currentWidth": 4
                            }
                        ],
                        "tableView": false
                    }, {
                        "key": "title_di_3",
                        "type": "htmlelement",
                        "input": false,
                        "label": "title_di_3",
                        "content": "Dati catastali:",
                        "tableView": false,
                        "refreshOnChange": false
                    }, {
                        "key": "columns_di_3",
                        "type": "columns",
                        "input": false,
                        "label": "columns_di_3",
                        "columns": [{
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 3,
                                "offset": 0,
                                "components": [{
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
                            }, {
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 3,
                                "offset": 0,
                                "components": [{
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
                            }, {
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 3,
                                "offset": 0,
                                "components": [{
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
                            }, {
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 3,
                                "offset": 0,
                                "components": [{
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
                    }, {
                        "key": "DATI_IDENTIFICATIVI.localizzazione.ubicazione",
                        "data": {
                            "custom": "values = [\r\n  { des_ubicazione: 'SU SORGENTE', id_ubicazione: 1 },\r\n  { des_ubicazione: 'SU PRESA', id_ubicazione: 2 },\r\n  { des_ubicazione: 'SU POZZO', id_ubicazione: 3 },\r\n  { des_ubicazione: 'SU CANALE', id_ubicazione: 4 },\r\n  { des_ubicazione: 'SU CONDOTTA', id_ubicazione: 5 },\r\n  { des_ubicazione: 'SU TRINCEA DRENANTE', id_ubicazione: 6 },\r\n  { des_ubicazione: 'SU RESTITUZIONE', id_ubicazione: 7 },\r\n  { des_ubicazione: 'SU SCARICO INDUSTRIALE', id_ubicazione: 8 },\r\n  { des_ubicazione: 'MISURATORE DI III LIVELLO', id_ubicazione: 9 },\r\n  { des_ubicazione: 'RILASCIO DMV', id_ubicazione: 10 }\r\n]"
                        },
                        "type": "select",
                        "input": true,
                        "label": "Ubicazione specifica",
                        "widget": "choicesjs",
                        "dataSrc": "custom",
                        "template": "<span>{{ item.des_ubicazione }}</span>",
                        "validate": {
                            "required": true
                        },
                        "tableView": true,
                        "validateWhenHidden": false
                    }, {
                        "key": "DATI_IDENTIFICATIVI.localizzazione.posizione",
                        "type": "radio",
                        "input": true,
                        "label": "Posizione corretta",
                        "logic": [{
                                "name": "verificaComponenteAppFO",
                                "actions": [{
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
                                    "javascript": "result = options.SCRIVA.isFrontOffice === true;"
                                }
                            }
                        ],
                        "inline": true,
                        "values": [{
                                "label": "Si",
                                "value": "si",
                                "shortcut": ""
                            }, {
                                "label": "No",
                                "value": "no",
                                "shortcut": ""
                            }
                        ],
                        "tableView": false,
                        "validateWhenHidden": false,
                        "optionsLabelPosition": "right"
                    }, {
                        "key": "title_di_4",
                        "type": "htmlelement",
                        "attrs": [{
                                "attr": "",
                                "value": ""
                            }
                        ],
                        "input": false,
                        "label": "title_di_4",
                        "content": "<strong>Note relative al censimento</strong>",
                        "tableView": false,
                        "refreshOnChange": false
                    }, {
                        "key": "DATI_IDENTIFICATIVI.note_censimento.note_censimento",
                        "type": "textarea",
                        "input": true,
                        "label": " Note",
                        "logic": [{
                                "name": "verificaComponenteAppFO",
                                "actions": [{
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
                                    "javascript": "result = options.SCRIVA.isFrontOffice === true;"
                                }
                            }
                        ],
                        "validate": {
                            "maxLength": 4000,
                            "customMessage": "Il campo può contenere un massimo di 4000 caratteri"
                        },
                        "tableView": true,
                        "autoExpand": false,
                        "applyMaskOn": "change",
                        "validateWhenHidden": false
                    }
                ],
                "collapsible": false
            }, {
                "key": "ESERCIZIO_DELLA_CAPTAZIONE",
                "type": "panel",
                "input": false,
                "label": "Esercizio della captazione",
                "title": "<strong>Esercizio</strong>",
                "tableView": false,
                "components": [{
                        "key": "ESERCIZIO.esercizio.stato_esercizio",
                        "data": {
                            "custom": "values = [\r\n  { des_stato_esercizio: 'IN USO', id_stato_esercizio: 1 },\r\n  { des_stato_esercizio: 'DISMESSO', id_stato_esercizio: 2 }\r\n]"
                        },
                        "type": "select",
                        "input": true,
                        "label": "Stato di esercizio",
                        "logic": [{
                                "name": "verificaComponenteAppFO",
                                "actions": [{
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
                                    "javascript": "result = options.SCRIVA.isFrontOffice === true;"
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
                "collapsible": false
            }
        ]
    },
    "SERBATOIO_DI_ACCUMULO": {
        "display": "form",
        "components": [{
                "key": "id_oggetto_istanza",
                "type": "hidden",
                "input": true,
                "label": "idOggettoIstanza - oggettoIstanza.id_oggetto_istanza",
                "tableView": true,
                "customDefaultValue": "value = options.SCRIVA.oggettoIstanza.id_oggetto_istanza;"
            }, {
                "key": "id_oggetto",
                "type": "hidden",
                "input": true,
                "label": "idOpera/idOggetto - oggetto.id_oggetto",
                "tableView": true,
                "customDefaultValue": "value = options.SCRIVA.oggetto.id_oggetto;"
            }, {
                "key": "title",
                "type": "htmlelement",
                "attrs": [{
                        "attr": "",
                        "value": ""
                    }
                ],
                "input": false,
                "label": "title",
                "content": "<strong>Serbatoio di accumulo {{options.SCRIVA.oggetto.cod_scriva}}</strong>",
                "tableView": false,
                "refreshOnChange": false
            }, {
                "key": "string_campo_obbligatorio",
                "tag": "small",
                "type": "htmlelement",
                "attrs": [{
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
            }, {
                "key": "CARATTERISTICHE",
                "type": "panel",
                "input": false,
                "label": "Caratteristiche",
                "title": "<strong>Caratteristiche</strong>",
                "tableView": false,
                "components": [{
                        "key": "columns1",
                        "type": "columns",
                        "input": false,
                        "label": "Columns",
                        "columns": [{
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 4,
                                "offset": 0,
                                "components": [{
                                        "key": "CARATTERISTICHE.caratteristiche.codice_rilievo",
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
                            }, {
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 4,
                                "offset": 0,
                                "components": [],
                                "currentWidth": 4
                            }, {
                                "size": "md",
                                "width": 4,
                                "components": [],
                                "currentWidth": 4
                            }
                        ],
                        "tableView": false
                    }, {
                        "key": "CARATTERISTICHE.caratteristiche.denominazione",
                        "type": "textfield",
                        "input": true,
                        "label": "Denominazione",
                        "validate": {
                            "maxLength": 50,
                            "customMessage": "Il campo può contenere un massimo di 50 caratteri"
                        },
                        "tableView": true,
                        "applyMaskOn": "change",
                        "customDefaultValue": "value = options.SCRIVA.oggettoIstanza.den_oggetto;",
                        "validateWhenHidden": false,
                        "hideOnChildrenHidden": false
                    }, {
                        "key": "CARATTERISTICHE.caratteristiche.tipo_serb",
                        "data": {
                            "custom": "values = [\r\n  { des_tipo_serb: 'Serbatoio di impianto di acquedotto', id_tipo_serb: 1 },\r\n  { des_tipo_serb: 'Serbatoio di rete di distribuzione', id_tipo_serb: 2 },\r\n  { des_tipo_serb: 'Vasca di carico', id_tipo_serb: 3 },\r\n  { des_tipo_serb: 'Demodulatore', id_tipo_serb: 4 },\r\n  { des_tipo_serb: 'Serbatoio ad uso irriguo', id_tipo_serb: 5 }\r\n]"
                        },
                        "type": "select",
                        "input": true,
                        "label": "Tipologia dell'opera",
                        "widget": "choicesjs",
                        "dataSrc": "custom",
                        "template": "<span>{{ item.des_tipo_serb }}</span>",
                        "validate": {
                            "required": true
                        },
                        "tableView": true,
                        "validateWhenHidden": false
                    }, {
                        "key": "title_di_2",
                        "type": "htmlelement",
                        "attrs": [{
                                "attr": "",
                                "value": ""
                            }
                        ],
                        "input": false,
                        "label": "title_di_2",
                        "content": "<strong>Localizzazione</strong>",
                        "tableView": false,
                        "refreshOnChange": false
                    }, {
                        "key": "columns_di_2",
                        "type": "columns",
                        "input": false,
                        "label": "columns_di_2",
                        "columns": [{
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 4,
                                "offset": 0,
                                "components": [{
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
                            }, {
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 4,
                                "offset": 0,
                                "components": [{
                                        "key": "CARATTERISTICHE.localizzazione.localita",
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
                    }, {
                        "key": "title_di_3",
                        "type": "htmlelement",
                        "input": false,
                        "label": "title_di_3",
                        "content": "Dati catastali:",
                        "tableView": false,
                        "refreshOnChange": false
                    }, {
                        "key": "columns_di_3",
                        "type": "columns",
                        "input": false,
                        "label": "columns_di_3",
                        "columns": [{
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 3,
                                "offset": 0,
                                "components": [{
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
                            }, {
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 3,
                                "offset": 0,
                                "components": [{
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
                            }, {
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 3,
                                "offset": 0,
                                "components": [{
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
                            }, {
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 3,
                                "offset": 0,
                                "components": [{
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
                    }
                ],
                "collapsible": false
            }, {
                "key": "DATI_TECNICI",
                "type": "panel",
                "input": false,
                "label": "Dati tecnici",
                "title": "<strong>Dati tecnici</strong>",
                "tableView": false,
                "components": [{
                        "key": "DATI_TECNICI.capacita_accumulo",
                        "type": "textfield",
                        "input": true,
                        "label": "Capacità di accumulo (m<sup>3</sup>)",
                        "validate": {
                            "pattern": "(^\\d{1,9}$)",
                            "customMessage": "Campo numerico. Indicare un massimo di 9 cifre intere."
                        },
                        "tableView": true,
                        "applyMaskOn": "change",
                        "validateWhenHidden": false
                    }
                ],
                "collapsible": false
            }
        ]
    },
    "STAZIONE_DI_POMPAGGIO": {
        "display": "form",
        "components": [{
                "key": "id_oggetto_istanza",
                "type": "hidden",
                "input": true,
                "label": "idOggettoIstanza - oggettoIstanza.id_oggetto_istanza",
                "tableView": true,
                "customDefaultValue": "value = options.SCRIVA.oggettoIstanza.id_oggetto_istanza;"
            }, {
                "key": "id_oggetto",
                "type": "hidden",
                "input": true,
                "label": "idOpera/idOggetto - oggetto.id_oggetto",
                "tableView": true,
                "customDefaultValue": "value = options.SCRIVA.oggetto.id_oggetto;"
            }, {
                "key": "title",
                "type": "htmlelement",
                "attrs": [{
                        "attr": "",
                        "value": ""
                    }
                ],
                "input": false,
                "label": "title",
                "content": "<strong>Stazione di pompaggio {{options.SCRIVA.oggetto.cod_scriva}}</strong>",
                "tableView": false,
                "refreshOnChange": false
            }, {
                "key": "string_campo_obbligatorio",
                "tag": "small",
                "type": "htmlelement",
                "attrs": [{
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
            }, {
                "key": "CARATTERISTICHE",
                "type": "panel",
                "input": false,
                "label": "Caratteristiche",
                "title": "<strong>Caratteristiche</strong>",
                "tableView": false,
                "components": [{
                        "key": "columns1",
                        "type": "columns",
                        "input": false,
                        "label": "Columns",
                        "columns": [{
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 4,
                                "offset": 0,
                                "components": [{
                                        "key": "CARATTERISTICHE.caratteristiche.codice_rilievo",
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
                            }, {
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 4,
                                "offset": 0,
                                "components": [],
                                "currentWidth": 4
                            }, {
                                "size": "md",
                                "width": 4,
                                "components": [],
                                "currentWidth": 4
                            }
                        ],
                        "tableView": false
                    }, {
                        "key": "CARATTERISTICHE.caratteristiche.tipo_stazione",
                        "data": {
                            "custom": "values = [\r\n { des_tipo_stazione: 'Stazione di pompaggio di rete di distribuzione', id_tipo_stazione: 1 },\r\n  { des_tipo_stazione: 'Stazione di Pompaggio di impianto di acquedotto', id_tipo_stazione: 2 },\r\n  { des_tipo_stazione: 'Stazione di pompaggio ad uso irriguo', id_tipo_stazione: 3 },\r\n  { des_tipo_stazione: 'Stazione di pompaggio ad uso diverso', id_tipo_stazione: 4 }\r\n]"
                        },
                        "type": "select",
                        "input": true,
                        "label": "Tipologia dell'opera",
                        "widget": "choicesjs",
                        "dataSrc": "custom",
                        "template": "<span>{{ item.des_tipo_stazione }}</span>",
                        "validate": {
                            "required": true
                        },
                        "tableView": true,
                        "validateWhenHidden": false
                    }, {
                        "key": "CARATTERISTICHE.caratteristiche.denominazione",
                        "type": "textfield",
                        "input": true,
                        "label": "Denominazione",
                        "validate": {
                            "maxLength": 50,
                            "customMessage": "Il campo può contenere un massimo di 50 caratteri"
                        },
                        "tableView": true,
                        "applyMaskOn": "change",
                        "customDefaultValue": "value = options.SCRIVA.oggettoIstanza.den_oggetto;",
                        "validateWhenHidden": false,
                        "hideOnChildrenHidden": false
                    }, {
                        "key": "title_di_2",
                        "type": "htmlelement",
                        "attrs": [{
                                "attr": "",
                                "value": ""
                            }
                        ],
                        "input": false,
                        "label": "title_di_2",
                        "content": "<strong>Localizzazione</strong>",
                        "tableView": false,
                        "refreshOnChange": false
                    }, {
                        "key": "columns_di_2",
                        "type": "columns",
                        "input": false,
                        "label": "columns_di_2",
                        "columns": [{
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 4,
                                "offset": 0,
                                "components": [{
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
                            }, {
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 4,
                                "offset": 0,
                                "components": [{
                                        "key": "CARATTERISTICHE.localizzazione.localita",
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
                    }, {
                        "key": "title_di_3",
                        "type": "htmlelement",
                        "input": false,
                        "label": "title_di_3",
                        "content": "Dati catastali:",
                        "tableView": false,
                        "refreshOnChange": false
                    }, {
                        "key": "columns_di_3",
                        "type": "columns",
                        "input": false,
                        "label": "columns_di_3",
                        "columns": [{
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 3,
                                "offset": 0,
                                "components": [{
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
                            }, {
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 3,
                                "offset": 0,
                                "components": [{
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
                            }, {
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 3,
                                "offset": 0,
                                "components": [{
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
                            }, {
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 3,
                                "offset": 0,
                                "components": [{
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
                    }
                ],
                "collapsible": false
            }, {
                "key": "DATI_TECNICI",
                "type": "panel",
                "input": false,
                "label": "Dati tecnici",
                "title": "<strong>Dati tecnici</strong>",
                "tableView": false,
                "components": [{
                        "key": "datiTecniciCol1",
                        "type": "columns",
                        "input": false,
                        "label": "dati_tecnici_col1",
                        "columns": [{
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 6,
                                "offset": 0,
                                "components": [{
                                        "key": "DATI_TECNICI.portata_complessiva",
                                        "type": "textfield",
                                        "input": true,
                                        "label": "Portata complessiva (l/s)",
                                        "validate": {
                                            "pattern": "(^\\d{1,6}$)|(^\\d{1,6}[,]\\d{1,4}$)",
                                            "customMessage": "Campo numerico. Indicare un massimo di 6 cifre intere e  un massimo di 4 cifre decimali."
                                        },
                                        "tableView": true,
                                        "applyMaskOn": "change",
                                        "validateWhenHidden": false
                                    }
                                ],
                                "currentWidth": 6
                            }, {
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 6,
                                "offset": 0,
                                "components": [{
                                        "key": "DATI_TECNICI.prevalenza",
                                        "type": "textfield",
                                        "input": true,
                                        "label": "Prevalenza (m)",
                                        "validate": {
                                            "pattern": "(^\\d{1,4}$)|(^\\d{1,4}[,]\\d{1,2}$)",
                                            "customMessage": "Campo numerico. Indicare un massimo di 4 cifre intere e  un massimo di 2 cifre decimali."
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
                    }, {
                        "key": "datiTecniciCol2",
                        "type": "columns",
                        "input": false,
                        "label": "dati_tecnici_col2",
                        "columns": [{
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 6,
                                "offset": 0,
                                "components": [{
                                        "key": "DATI_TECNICI.potenza_installata",
                                        "type": "textfield",
                                        "input": true,
                                        "label": "Potenza installata (kW)",
                                        "validate": {
                                            "pattern": "(^\\d{1,7}$)|(^\\d{1,7}[,]\\d{1,2}$)",
                                            "customMessage": "Campo numerico. Indicare un massimo di 7 cifre intere e  un massimo di 2 cifre decimali."
                                        },
                                        "tableView": true,
                                        "applyMaskOn": "change",
                                        "validateWhenHidden": false
                                    }
                                ],
                                "currentWidth": 6
                            }, {
                                "pull": 0,
                                "push": 0,
                                "size": "md",
                                "width": 6,
                                "offset": 0,
                                "components": [{
                                        "key": "DATI_TECNICI.volume_annuo",
                                        "type": "textfield",
                                        "input": true,
                                        "label": "Volume annuo (m<sup>3</sup>)",
                                        "validate": {
                                            "pattern": "(^\\d{1,9}$)|(^\\d{1,9}[,]\\d{1,2}$)",
                                            "customMessage": "Campo numerico. Indicare un massimo di 9 cifre intere e  un massimo di 2 cifre decimali."
                                        },
                                        "tableView": true,
                                        "errorLabel": "è",
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
            }
        ]
    }
}

