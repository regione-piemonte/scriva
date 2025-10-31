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
export const QDR_DER_OPERETRAS_RIEPILOGO_DEBUG = {
  "display": "form",
  "components": [{
    "key": "title",
    "type": "htmlelement",
    "input": false,
    "label": "HTML",
    "content": "<h2><strong>OPERE DI TRASPORTO</strong></h2>",
    "tableView": false,
    "refreshOnChange": false
  }, {
    "key": "html_descrizione_generica",
    "type": "htmlelement",
    "input": false,
    "label": "html_descrizione_generica",
    "content": "Le opere di trasporto sono state inserite.",
    "tableView": false,
    "refreshOnChange": false,
  }]
}

/**
 * @author Ismaele Bottelli
 * @date 11/12/2024
 * @jira SCRIVA-1576
 * @notes Con #Alessandro_Verner abbiamo affrontato la questione dei dati tecnici che possono non essere definiti sulle opere.
 *        Il problema alla base è che il quadro riepilogo lavora solo sul jsondata, per cui mancando i dati tecnici non si vedranno mai le opere che di natura non ne fanno uso.
 *        Lascio un backup nel momento in cui sistemeremo la situazione.
 */
// {
//   "display": "form",
//   "components": [{
//     "key": "title",
//     "type": "htmlelement",
//     "input": false,
//     "label": "HTML",
//     "content": "<h2><strong>OPERE DI TRASPORTO</strong></h2>",
//     "tableView": false,
//     "refreshOnChange": false
//   }, {
//     "key": "html_condotta_forzata",
//     "type": "htmlelement",
//     "input": false,
//     "label": "html_condotta_forzata",
//     "content": "Sono state inserite {{data.JS_DATI_CONDOTTA_FORZATA?.length}} opere di tipo condotta forzata.",
//     "tableView": false,
//     "refreshOnChange": false,
//     "customConditional": "show = data.JS_DATI_CONDOTTA_FORZATA?.length > 0;"
//   }]
// }