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
export const QDR_AUT_DATI_TECNICI_DEBUG = {
  "label": "Dettaglio Progetto",
  "jsonForm": {
    "display": "form",
    "components": [
      {
        "key": "JS_DATI_GENERALI",
        "type": "container",
        "input": true,
        "label": "Container",
        "tableView": false,
        "components": [
          {
            "key": "panel",
            "type": "panel",
            "input": false,
            "label": "Panel",
            "title": "Inquadramento Generale",
            "collapsed": false,
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
                    "width": 5,
                    "offset": 0,
                    "components": [
                      {
                        "key": "tipologiaAttivitaEstrattiva",
                        "data": {
                          "json": [
                            "Cava in parco o in aree contigue",
                            "Cave in area di salvaguardia",
                            "Cave per OOPP > 500.000 mc",
                            "Altre cave"
                          ]
                        },
                        "type": "select",
                        "input": true,
                        "label": "Tipologia attività estrattiva",
                        "widget": "choicesjs",
                        "dataSrc": "json",
                        "validate": {
                          "required": true,
                          "customMessage": "Campo obbligatorio"
                        },
                        "tableView": true,
                        "errorLabel": "Campo obbligatorio",
                        "validateWhenHidden": false
                      }
                    ],
                    "currentWidth": 5
                  },
                  {
                    "pull": 0,
                    "push": 0,
                    "size": "md",
                    "width": 4,
                    "offset": 0,
                    "components": [
                      {
                        "key": "competenzaAttivitaEstrattiva",
                        "data": {
                          "json": [
                            "Regione Piemonte",
                            "Provincia",
                            "Città Metropolitana"
                          ]
                        },
                        "type": "select",
                        "input": true,
                        "label": "Competenza attività estrattiva",
                        "widget": "choicesjs",
                        "dataSrc": "json",
                        "tableView": true,
                        "validateWhenHidden": false
                      }
                    ],
                    "currentWidth": 4
                  },
                  {
                    "size": "md",
                    "width": 3,
                    "components": [
                      {
                        "key": "litotipo",
                        "data": {
                          "json": [
                            "ARGILLA",
                            "CALCARE",
                            "GESSO",
                            "CALCESCISTI",
                            "QUARZITE",
                            "GRANITO",
                            "DIORITE",
                            "SIENITE",
                            "MARMO",
                            "QUARZO",
                            "SERPENTINA",
                            "MATERIALE ALLUVIONALE",
                            "MATERIALE MORENICO",
                            "TORBA",
                            "SABBIE SILICEE",
                            "SABBIE PER RIEMPIMENTO",
                            "DETRITO DI FALDA",
                            "GNEISS",
                            "SERIZZO",
                            "BEOLA",
                            "PIETRA DI LUSERNA",
                            "FELDSPATI",
                            "GRANATI",
                            "METALLI",
                            "RIOLITI DI ALTERAZIONE",
                            "FELDSPATI E ASSOCIATI",
                            "ARGILLE REFRATTARIE",
                            "OLIVINA",
                            "CAOLINO",
                            "TALCO",
                            "MINERALI AURIFERI E ASSOCIATI",
                            "MARNA DA CEMENTO",
                            "GRANATI E ASSOCIATI",
                            "PERIDOTITE FLOGOPITICA",
                            "IDROCARBURI"
                          ]
                        },
                        "type": "select",
                        "input": true,
                        "label": "Litotipo",
                        "widget": "choicesjs",
                        "dataSrc": "json",
                        "validate": {
                          "required": true,
                          "customMessage": "Campo obbligatorio"
                        },
                        "tableView": true,
                        "validateWhenHidden": false
                      }
                    ],
                    "currentWidth": 3
                  }
                ],
                "tableView": false
              },
              {
                "key": "compartoPrae",
                "type": "radio",
                "input": true,
                "label": "<b>Comparto PRAE</b>",
                "inline": false,
                "values": [
                  {
                    "label": "Comparto I - Aggregati per le costruzioni e le infrastrutture",
                    "value": "1",
                    "shortcut": ""
                  },
                  {
                    "label": "Comparto II - pietre ornamentali",
                    "value": "2",
                    "shortcut": ""
                  },
                  {
                    "label": "Comparto III -  materiali industriali",
                    "value": "3",
                    "shortcut": ""
                  }
                ],
                "validate": {
                  "required": true,
                  "customMessage": "Campo obbligatorio"
                },
                "tableView": false,
                "errorLabel": "Campo obbligatorio",
                "validateWhenHidden": false,
                "optionsLabelPosition": "right"
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
                    "width": 6,
                    "offset": 0,
                    "components": [
                      {
                        "key": "poloEstrattivo",
                        "data": {
                          "json": [
                            {
                              "POLO": "Verbania",
                              "COMUNE": "Mergozzo",
                              "COD_POL": "O01090",
                              "COMPARTO": "1",
                              "DES_POLO": "O01090 - Verbania",
                              "PROVINCIA": "VERBANO-CUSIO-OSSOLA"
                            },
                            {
                              "POLO": "Caprile",
                              "COMUNE": "Caprile",
                              "COD_POL": "B01028",
                              "COMPARTO": "1",
                              "DES_POLO": "B01028 - Caprile",
                              "PROVINCIA": "BIELLA"
                            },
                            {
                              "POLO": "La Loggia-2",
                              "COMUNE": "La Loggia",
                              "COD_POL": "T01083",
                              "COMPARTO": "1",
                              "DES_POLO": "T01083 - La Loggia-2",
                              "PROVINCIA": "TORINO"
                            },
                            {
                              "POLO": "Bellinzago Novarese",
                              "COMUNE": "Bellinzago Novarese",
                              "COD_POL": "N01062",
                              "COMPARTO": "1",
                              "DES_POLO": "N01062 - Bellinzago Novarese",
                              "PROVINCIA": "NOVARA"
                            },
                            {
                              "POLO": "Valdieri",
                              "COMUNE": "Valdieri",
                              "COD_POL": "C03055",
                              "COMPARTO": "3",
                              "DES_POLO": "C03055 - Valdieri",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Pianezza",
                              "COMUNE": "Pianezza",
                              "COD_POL": "T01089",
                              "COMPARTO": "1",
                              "DES_POLO": "T01089 - Pianezza",
                              "PROVINCIA": "TORINO"
                            },
                            {
                              "POLO": "Bagnasco-1",
                              "COMUNE": "Bagnasco",
                              "COD_POL": "C03065",
                              "COMPARTO": "3",
                              "DES_POLO": "C03065 - Bagnasco-1",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Cisterna Asti",
                              "COMUNE": "Cisterna d'Asti",
                              "COD_POL": "S03023",
                              "COMPARTO": "3",
                              "DES_POLO": "S03023 - Cisterna Asti",
                              "PROVINCIA": "ASTI"
                            },
                            {
                              "POLO": "Casalgrasso",
                              "COMUNE": "Casalgrasso",
                              "COD_POL": "C01029",
                              "COMPARTO": "1",
                              "DES_POLO": "C01029 - Casalgrasso",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Maggiora",
                              "COMUNE": "Maggiora",
                              "COD_POL": "N01063",
                              "COMPARTO": "1",
                              "DES_POLO": "N01063 - Maggiora",
                              "PROVINCIA": "NOVARA"
                            },
                            {
                              "POLO": "Momo",
                              "COMUNE": "Momo",
                              "COD_POL": "N01071",
                              "COMPARTO": "1",
                              "DES_POLO": "N01071 - Momo",
                              "PROVINCIA": "NOVARA"
                            },
                            {
                              "POLO": "Trecate-Cerano",
                              "COMUNE": "Cerano",
                              "COD_POL": "N01067",
                              "COMPARTO": "1",
                              "DES_POLO": "N01067 - Trecate-Cerano",
                              "PROVINCIA": "NOVARA"
                            },
                            {
                              "POLO": "Pontestura",
                              "COMUNE": "Pontestura",
                              "COD_POL": "A03018",
                              "COMPARTO": "3",
                              "DES_POLO": "A03018 - Pontestura",
                              "PROVINCIA": "ALESSANDRIA"
                            },
                            {
                              "POLO": "Malvicino",
                              "COMUNE": "Malvicino",
                              "COD_POL": "A01011",
                              "COMPARTO": "1",
                              "DES_POLO": "A01011 - Malvicino",
                              "PROVINCIA": "ALESSANDRIA"
                            },
                            {
                              "POLO": "Casale Monferrato",
                              "COMUNE": "Casale Monferrato",
                              "COD_POL": "A01005",
                              "COMPARTO": "1",
                              "DES_POLO": "A01005 - Casale Monferrato",
                              "PROVINCIA": "ALESSANDRIA"
                            },
                            {
                              "POLO": "Frassineto Po",
                              "COMUNE": "Frassineto Po",
                              "COD_POL": "A01006",
                              "COMPARTO": "1",
                              "DES_POLO": "A01006 - Frassineto Po",
                              "PROVINCIA": "ALESSANDRIA"
                            },
                            {
                              "POLO": "Cherasco-Bra",
                              "COMUNE": "Cherasco",
                              "COD_POL": "C01031",
                              "COMPARTO": "1",
                              "DES_POLO": "C01031 - Cherasco-Bra",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Cherasco",
                              "COMUNE": "Cherasco",
                              "COD_POL": "C01039",
                              "COMPARTO": "1",
                              "DES_POLO": "C01039 - Cherasco",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "San Michele Mondovi'",
                              "COMUNE": "San Michele Mondovi'",
                              "COD_POL": "C01042",
                              "COMPARTO": "1",
                              "DES_POLO": "C01042 - San Michele Mondovi'",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Castellazzo Bormida",
                              "COMUNE": "Castellazzo Bormida",
                              "COD_POL": "A01001",
                              "COMPARTO": "1",
                              "DES_POLO": "A01001 - Castellazzo Bormida",
                              "PROVINCIA": "ALESSANDRIA"
                            },
                            {
                              "POLO": "Brusasco",
                              "COMUNE": "Brusasco",
                              "COD_POL": "T01100",
                              "COMPARTO": "1",
                              "DES_POLO": "T01100 - Brusasco",
                              "PROVINCIA": "TORINO"
                            },
                            {
                              "POLO": "Fossano-2",
                              "COMUNE": "Fossano",
                              "COD_POL": "C01038",
                              "COMPARTO": "1",
                              "DES_POLO": "C01038 - Fossano-2",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Scarnafigi-Ruffia-Villanova",
                              "COMUNE": "Scarnafigi",
                              "COD_POL": "C01037",
                              "COMPARTO": "1",
                              "DES_POLO": "C01037 - Scarnafigi-Ruffia-Villanova",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Villanova Mondovi-2",
                              "COMUNE": "Villanova MondovÃ¬",
                              "COD_POL": "C03063",
                              "COMPARTO": "3",
                              "DES_POLO": "C03063 - Villanova Mondovi-2",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Isola SantAntonio",
                              "COMUNE": "Isola Sant'Antonio",
                              "COD_POL": "A01009",
                              "COMPARTO": "1",
                              "DES_POLO": "A01009 - Isola SantAntonio",
                              "PROVINCIA": "ALESSANDRIA"
                            },
                            {
                              "POLO": "Carignano-Carmagnola-2",
                              "COMUNE": "Carignano",
                              "COD_POL": "T01092",
                              "COMPARTO": "1",
                              "DES_POLO": "T01092 - Carignano-Carmagnola-2",
                              "PROVINCIA": "TORINO"
                            },
                            {
                              "POLO": "Rondissone-Montanaro",
                              "COMUNE": "Rondissone",
                              "COD_POL": "T01070",
                              "COMPARTO": "1",
                              "DES_POLO": "T01070 - Rondissone-Montanaro",
                              "PROVINCIA": "TORINO"
                            },
                            {
                              "POLO": "Moncalieri",
                              "COMUNE": "Moncalieri",
                              "COD_POL": "T01091",
                              "COMPARTO": "1",
                              "DES_POLO": "T01091 - Moncalieri",
                              "PROVINCIA": "TORINO"
                            },
                            {
                              "POLO": "La Loggia-1",
                              "COMUNE": "La Loggia",
                              "COD_POL": "T01071",
                              "COMPARTO": "1",
                              "DES_POLO": "T01071 - La Loggia-1",
                              "PROVINCIA": "TORINO"
                            },
                            {
                              "POLO": "Ottiglio",
                              "COMUNE": "Ottiglio",
                              "COD_POL": "A03019",
                              "COMPARTO": "3",
                              "DES_POLO": "A03019 - Ottiglio",
                              "PROVINCIA": "ALESSANDRIA"
                            },
                            {
                              "POLO": "Bagnasco-2",
                              "COMUNE": "Bagnasco",
                              "COD_POL": "C01035",
                              "COMPARTO": "1",
                              "DES_POLO": "C01035 - Bagnasco-2",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Moncalvo",
                              "COMUNE": "Moncalvo",
                              "COD_POL": "S03024",
                              "COMPARTO": "3",
                              "DES_POLO": "S03024 - Moncalvo",
                              "PROVINCIA": "ASTI"
                            },
                            {
                              "POLO": "Calliano",
                              "COMUNE": "Calliano",
                              "COD_POL": "S03027",
                              "COMPARTO": "3",
                              "DES_POLO": "S03027 - Calliano",
                              "PROVINCIA": "ASTI"
                            },
                            {
                              "POLO": "Cavour-2",
                              "COMUNE": "Cavour",
                              "COD_POL": "T01088",
                              "COMPARTO": "1",
                              "DES_POLO": "T01088 - Cavour-2",
                              "PROVINCIA": "TORINO"
                            },
                            {
                              "POLO": "Niella tanaro",
                              "COMUNE": "NIELLA TANARO",
                              "COD_POL": "C03066",
                              "COMPARTO": "3",
                              "DES_POLO": "C03066 - Niella tanaro",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Boschetto",
                              "COMUNE": "Chivasso",
                              "COD_POL": "T01101",
                              "COMPARTO": "1",
                              "DES_POLO": "T01101 - Boschetto",
                              "PROVINCIA": "TORINO"
                            },
                            {
                              "POLO": "Romentino",
                              "COMUNE": "Romentino",
                              "COD_POL": "N01065",
                              "COMPARTO": "1",
                              "DES_POLO": "N01065 - Romentino",
                              "PROVINCIA": "NOVARA"
                            },
                            {
                              "POLO": "Bassignana",
                              "COMUNE": "Bassignana",
                              "COD_POL": "A03017",
                              "COMPARTO": "3",
                              "DES_POLO": "A03017 - Bassignana",
                              "PROVINCIA": "ALESSANDRIA"
                            },
                            {
                              "POLO": "Castelnuovo-Bormida-Cassine-1",
                              "COMUNE": "Castelnuovo Bormida",
                              "COD_POL": "A01008",
                              "COMPARTO": "1",
                              "DES_POLO": "A01008 - Castelnuovo-Bormida-Cassine-1",
                              "PROVINCIA": "ALESSANDRIA"
                            },
                            {
                              "POLO": "Sezzadio",
                              "COMUNE": "Sezzadio",
                              "COD_POL": "A01002",
                              "COMPARTO": "1",
                              "DES_POLO": "A01002 - Sezzadio",
                              "PROVINCIA": "ALESSANDRIA"
                            },
                            {
                              "POLO": "Asti-2",
                              "COMUNE": "Asti",
                              "COD_POL": "S01020",
                              "COMPARTO": "1",
                              "DES_POLO": "S01020 - Asti-2",
                              "PROVINCIA": "ASTI"
                            },
                            {
                              "POLO": "BorgoSanMartino",
                              "COMUNE": "Borgo San Martino",
                              "COD_POL": "A01003",
                              "COMPARTO": "1",
                              "DES_POLO": "A01003 - BorgoSanMartino",
                              "PROVINCIA": "ALESSANDRIA"
                            },
                            {
                              "POLO": "Alessandria-2",
                              "COMUNE": "ALESSANDRIA",
                              "COD_POL": "A01004",
                              "COMPARTO": "1",
                              "DES_POLO": "A01004 - Alessandria-2",
                              "PROVINCIA": "ALESSANDRIA"
                            },
                            {
                              "POLO": "Alessandria-1",
                              "COMUNE": "ALESSANDRIA",
                              "COD_POL": "A01007",
                              "COMPARTO": "1",
                              "DES_POLO": "A01007 - Alessandria-1",
                              "PROVINCIA": "ALESSANDRIA"
                            },
                            {
                              "POLO": "Capriata Orba",
                              "COMUNE": "Capriata d'Orba",
                              "COD_POL": "A01018",
                              "COMPARTO": "1",
                              "DES_POLO": "A01018 - Capriata Orba",
                              "PROVINCIA": "ALESSANDRIA"
                            },
                            {
                              "POLO": "Castelnuovo-Bormida-Cassine-2",
                              "COMUNE": "Cassine",
                              "COD_POL": "A01019",
                              "COMPARTO": "1",
                              "DES_POLO": "A01019 - Castelnuovo-Bormida-Cassine-2",
                              "PROVINCIA": "ALESSANDRIA"
                            },
                            {
                              "POLO": "Castellazzo Bormida 2",
                              "COMUNE": "Castellazzo Bormida",
                              "COD_POL": "A01022",
                              "COMPARTO": "1",
                              "DES_POLO": "A01022 - Castellazzo Bormida 2",
                              "PROVINCIA": "ALESSANDRIA"
                            },
                            {
                              "POLO": "Tortona",
                              "COMUNE": "Tortona",
                              "COD_POL": "A01026",
                              "COMPARTO": "1",
                              "DES_POLO": "A01026 - Tortona",
                              "PROVINCIA": "ALESSANDRIA"
                            },
                            {
                              "POLO": "Murisengo",
                              "COMUNE": "Murisengo",
                              "COD_POL": "A03014",
                              "COMPARTO": "3",
                              "DES_POLO": "A03014 - Murisengo",
                              "PROVINCIA": "ALESSANDRIA"
                            },
                            {
                              "POLO": "Govone",
                              "COMUNE": "Govone",
                              "COD_POL": "C01030",
                              "COMPARTO": "1",
                              "DES_POLO": "C01030 - Govone",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Fossano-1",
                              "COMUNE": "Fossano",
                              "COD_POL": "C01041",
                              "COMPARTO": "1",
                              "DES_POLO": "C01041 - Fossano-1",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Roccavione",
                              "COMUNE": "Roccavione",
                              "COD_POL": "C01043",
                              "COMPARTO": "1",
                              "DES_POLO": "C01043 - Roccavione",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Mergozzo-Gravellona",
                              "COMUNE": "VERBANIA",
                              "COD_POL": "O01091",
                              "COMPARTO": "1",
                              "DES_POLO": "O01091 - Mergozzo-Gravellona",
                              "PROVINCIA": "VERBANO-CUSIO-OSSOLA"
                            },
                            {
                              "POLO": "Rovasenda-Murisengo-Brusnengo",
                              "COMUNE": "Rovasenda",
                              "COD_POL": "V03001",
                              "COMPARTO": "3",
                              "DES_POLO": "V03001 - Rovasenda-Murisengo-Brusnengo",
                              "PROVINCIA": "VERCELLI"
                            },
                            {
                              "POLO": "Santhia",
                              "COMUNE": "Santhia'",
                              "COD_POL": "V01110",
                              "COMPARTO": "1",
                              "DES_POLO": "V01110 - Santhia",
                              "PROVINCIA": "VERCELLI"
                            },
                            {
                              "POLO": "Tronzano Vercellese",
                              "COMUNE": "Tronzano Vercellese",
                              "COD_POL": "V01109",
                              "COMPARTO": "1",
                              "DES_POLO": "V01109 - Tronzano Vercellese",
                              "PROVINCIA": "VERCELLI"
                            },
                            {
                              "POLO": "Quarona",
                              "COMUNE": "Quarona",
                              "COD_POL": "V01008",
                              "COMPARTO": "1",
                              "DES_POLO": "V01008 - Quarona",
                              "PROVINCIA": "VERCELLI"
                            },
                            {
                              "POLO": "Villastellone_Carignano_Carmagnola",
                              "COMUNE": "Carmagnola",
                              "COD_POL": "T01072",
                              "COMPARTO": "1",
                              "DES_POLO": "T01072 - Villastellone_Carignano_Carmagnola",
                              "PROVINCIA": "TORINO"
                            },
                            {
                              "POLO": "La Loggia-Carignano",
                              "COMUNE": "La Loggia",
                              "COD_POL": "T01075",
                              "COMPARTO": "1",
                              "DES_POLO": "T01075 - La Loggia-Carignano",
                              "PROVINCIA": "TORINO"
                            },
                            {
                              "POLO": "Carignano-Carmagnola-1",
                              "COMUNE": "Carignano",
                              "COD_POL": "T01076",
                              "COMPARTO": "1",
                              "DES_POLO": "T01076 - Carignano-Carmagnola-1",
                              "PROVINCIA": "TORINO"
                            },
                            {
                              "POLO": "Rivalta",
                              "COMUNE": "Rivalta di Torino",
                              "COD_POL": "T01081",
                              "COMPARTO": "1",
                              "DES_POLO": "T01081 - Rivalta",
                              "PROVINCIA": "TORINO"
                            },
                            {
                              "POLO": "Collegno-Druento",
                              "COMUNE": "Druento",
                              "COD_POL": "T01085",
                              "COMPARTO": "1",
                              "DES_POLO": "T01085 - Collegno-Druento",
                              "PROVINCIA": "TORINO"
                            },
                            {
                              "POLO": "Castiglione",
                              "COMUNE": "Castiglione torinese",
                              "COD_POL": "T01102",
                              "COMPARTO": "1",
                              "DES_POLO": "T01102 - Castiglione",
                              "PROVINCIA": "TORINO"
                            },
                            {
                              "POLO": "Cambiano",
                              "COMUNE": "Cambiano",
                              "COD_POL": "T03089",
                              "COMPARTO": "3",
                              "DES_POLO": "T03089 - Cambiano",
                              "PROVINCIA": "TORINO"
                            },
                            {
                              "POLO": "Vicoforte-2",
                              "COMUNE": "Vicoforte",
                              "COD_POL": "C01046",
                              "COMPARTO": "1",
                              "DES_POLO": "C01046 - Vicoforte-2",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Scarnafigi",
                              "COMUNE": "Scarnafigi",
                              "COD_POL": "C01115",
                              "COMPARTO": "1",
                              "DES_POLO": "C01115 - Scarnafigi",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Rossana",
                              "COMUNE": "Rossana",
                              "COD_POL": "C03051",
                              "COMPARTO": "3",
                              "DES_POLO": "C03051 - Rossana",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Barnezzo",
                              "COMUNE": "Bernezzo",
                              "COD_POL": "C03052",
                              "COMPARTO": "3",
                              "DES_POLO": "C03052 - Barnezzo",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Frabosa Sottana",
                              "COMUNE": "Frabosa Sottana",
                              "COD_POL": "C03056",
                              "COMPARTO": "3",
                              "DES_POLO": "C03056 - Frabosa Sottana",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Dogliani",
                              "COMUNE": "Dogliani",
                              "COD_POL": "C03057",
                              "COMPARTO": "3",
                              "DES_POLO": "C03057 - Dogliani",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Pianfei",
                              "COMUNE": "Pianfei",
                              "COD_POL": "C03059",
                              "COMPARTO": "3",
                              "DES_POLO": "C03059 - Pianfei",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Vernante",
                              "COMUNE": "Vernante",
                              "COD_POL": "C03060",
                              "COMPARTO": "3",
                              "DES_POLO": "C03060 - Vernante",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Roccavione-Robilante",
                              "COMUNE": "Roccavione",
                              "COD_POL": "C03064",
                              "COMPARTO": "3",
                              "DES_POLO": "C03064 - Roccavione-Robilante",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Momo-Briona",
                              "COMUNE": "Briona",
                              "COD_POL": "N01064",
                              "COMPARTO": "1",
                              "DES_POLO": "N01064 - Momo-Briona",
                              "PROVINCIA": "NOVARA"
                            },
                            {
                              "POLO": "Cameri",
                              "COMUNE": "Cameri",
                              "COD_POL": "N01066",
                              "COMPARTO": "1",
                              "DES_POLO": "N01066 - Cameri",
                              "PROVINCIA": "NOVARA"
                            },
                            {
                              "POLO": "Varallo Pombia",
                              "COMUNE": "Varallo Pombia",
                              "COD_POL": "N01069",
                              "COMPARTO": "1",
                              "DES_POLO": "N01069 - Varallo Pombia",
                              "PROVINCIA": "NOVARA"
                            },
                            {
                              "POLO": "Oleggio",
                              "COMUNE": "Oleggio",
                              "COD_POL": "N01070",
                              "COMPARTO": "1",
                              "DES_POLO": "N01070 - Oleggio",
                              "PROVINCIA": "NOVARA"
                            },
                            {
                              "POLO": "Castagnole-Magliano-Neive",
                              "COMUNE": "Castagnole delle Lanze",
                              "COD_POL": "S01021",
                              "COMPARTO": "1",
                              "DES_POLO": "S01021 - Castagnole-Magliano-Neive",
                              "PROVINCIA": "ASTI"
                            },
                            {
                              "POLO": "Asti-3",
                              "COMUNE": "Asti",
                              "COD_POL": "S01022",
                              "COMPARTO": "1",
                              "DES_POLO": "S01022 - Asti-3",
                              "PROVINCIA": "ASTI"
                            },
                            {
                              "POLO": "Cocconato",
                              "COMUNE": "Cocconato",
                              "COD_POL": "S03025",
                              "COMPARTO": "3",
                              "DES_POLO": "S03025 - Cocconato",
                              "PROVINCIA": "ASTI"
                            },
                            {
                              "POLO": "Roccavione-Robilante-Roaschia",
                              "COMUNE": "Roccavione",
                              "COD_POL": "C03053",
                              "COMPARTO": "3",
                              "DES_POLO": "C03053 - Roccavione-Robilante-Roaschia",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Roaschia",
                              "COMUNE": "Roaschia",
                              "COD_POL": "C03054",
                              "COMPARTO": "3",
                              "DES_POLO": "C03054 - Roaschia",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Guazzora",
                              "COMUNE": "Guazzora",
                              "COD_POL": "A01025",
                              "COMPARTO": "1",
                              "DES_POLO": "A01025 - Guazzora",
                              "PROVINCIA": "ALESSANDRIA"
                            },
                            {
                              "POLO": "Alba-Santavittoria_roddi",
                              "COMUNE": "Alba",
                              "COD_POL": "C01033",
                              "COMPARTO": "1",
                              "DES_POLO": "C01033 - Alba-Santavittoria_roddi",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Alba-Barbaresco-Megliano",
                              "COMUNE": "Alba",
                              "COD_POL": "C01032",
                              "COMPARTO": "1",
                              "DES_POLO": "C01032 - Alba-Barbaresco-Megliano",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Casal Cermelli - Frugarolo",
                              "COMUNE": "Casal Cermelli",
                              "COD_POL": "A01012",
                              "COMPARTO": "1",
                              "DES_POLO": "A01012 - Casal Cermelli - Frugarolo",
                              "PROVINCIA": "ALESSANDRIA"
                            },
                            {
                              "POLO": "Cavaglia-Alice Castello",
                              "COMUNE": "Alice Castello",
                              "COD_POL": "V01111",
                              "COMPARTO": "1",
                              "DES_POLO": "V01111 - Cavaglia-Alice Castello",
                              "PROVINCIA": "VERCELLI"
                            },
                            {
                              "POLO": "Torrette",
                              "COMUNE": "Frassineto Po",
                              "COD_POL": "A01021",
                              "COMPARTO": "1",
                              "DES_POLO": "A01021 - Torrette",
                              "PROVINCIA": "ALESSANDRIA"
                            },
                            {
                              "POLO": "Vicoforte-1",
                              "COMUNE": "Vicoforte",
                              "COD_POL": "C01045",
                              "COMPARTO": "1",
                              "DES_POLO": "C01045 - Vicoforte-1",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Caraglio",
                              "COMUNE": "Caraglio",
                              "COD_POL": "C01040",
                              "COMPARTO": "1",
                              "DES_POLO": "C01040 - Caraglio",
                              "PROVINCIA": "CUNEO"
                            },
                            {
                              "POLO": "Poirino",
                              "COMUNE": "Poirino",
                              "COD_POL": "T03088",
                              "COMPARTO": "3",
                              "DES_POLO": "T03088 - Poirino",
                              "PROVINCIA": "TORINO"
                            }
                          ]
                        },
                        "type": "select",
                        "input": true,
                        "label": "Polo Estrattivo PRAE",
                        "widget": "choicesjs",
                        "dataSrc": "json",
                        "template": "<span>{{ item.DES_POLO }}</span>",
                        "tableView": true,
                        "conditional": {
                          "eq": "2",
                          "show": false,
                          "when": "JS_DATI_GENERALI.compartoPrae"
                        },
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
              },
              {
                "key": "utilizzo",
                "type": "container",
                "input": true,
                "label": "<b>Utilizzo</b>",
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
                            "key": "usoPrimario",
                            "data": {
                              "values": [
                                {
                                  "label": "Industriale",
                                  "value": "industiale"
                                },
                                {
                                  "label": "Energetico",
                                  "value": "energetico"
                                },
                                {
                                  "label": "Altro",
                                  "value": "altro"
                                }
                              ]
                            },
                            "type": "select",
                            "input": true,
                            "label": "Uso Primario",
                            "widget": "choicesjs",
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
                        "components": [],
                        "currentWidth": 6
                      }
                    ],
                    "tableView": false
                  },
                  {
                    "key": "descrizioneUso",
                    "type": "textarea",
                    "input": true,
                    "label": "Specifica altro uso",
                    "tableView": true,
                    "autoExpand": false,
                    "applyMaskOn": "change",
                    "conditional": {
                      "eq": "altro",
                      "show": true,
                      "when": "JS_DATI_GENERALI.utilizzo.usoPrimario"
                    },
                    "validateWhenHidden": false
                  }
                ],
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
                    "width": 8,
                    "offset": 0,
                    "components": [
                      {
                        "key": "collocazioneGeologica",
                        "data": {
                          "json": [
                            {
                              "codice": "2FL1",
                              "descrizione": " ALL.SABBIOSE-ARGILL. VILLAFRANCHIANO"
                            },
                            {
                              "codice": "A",
                              "descrizione": " ALLUVIONI"
                            },
                            {
                              "codice": "A1",
                              "descrizione": " ALLUVIONI ATTUALI"
                            },
                            {
                              "codice": "Q1",
                              "descrizione": " ALLUVIONI FERRETTIZZATE"
                            },
                            {
                              "codice": "FGWR",
                              "descrizione": " ALLUVIONI FLUVIOGLACIALI RISS-WURM"
                            },
                            {
                              "codice": "FGW",
                              "descrizione": " ALLUVIONI FLUVIOGLACIALI WURM"
                            },
                            {
                              "codice": "FL1",
                              "descrizione": " ALLUVIONI FORTEMENTE ALTERATE"
                            },
                            {
                              "codice": "A2",
                              "descrizione": " ALLUVIONI MEDIE"
                            },
                            {
                              "codice": "A21",
                              "descrizione": " ALLUVIONI POST-GLACIALI"
                            },
                            {
                              "codice": "A1FL3",
                              "descrizione": " ALLUVIONI PREVALENTEMENTE ARGILLOSE"
                            },
                            {
                              "codice": "A3",
                              "descrizione": " ALLUVIONI RECENTI"
                            },
                            {
                              "codice": "FL3",
                              "descrizione": " ALLUVIONI SABB. GHIAIOSE OLOCENE"
                            },
                            {
                              "codice": "FL2",
                              "descrizione": " ALLUVIONI SABBIOSO-SILTOSO-ARGILLOSE"
                            },
                            {
                              "codice": "A1L",
                              "descrizione": " ALLUVIONI TERRAZZATE"
                            },
                            {
                              "codice": "AT",
                              "descrizione": " ALLUVIONI TORBOSE"
                            },
                            {
                              "codice": "L1",
                              "descrizione": " ALTERNANZE SABBIOSO-ARGILLOSE"
                            },
                            {
                              "codice": "AB",
                              "descrizione": " ANFIBOLITI PREERCINICHE"
                            },
                            {
                              "codice": "AMS",
                              "descrizione": " APLITI A MICHE CICLO ALPINO"
                            },
                            {
                              "codice": "MQ",
                              "descrizione": " APLITI DIORITO-KINZIGITICHE"
                            },
                            {
                              "codice": "O3E3",
                              "descrizione": " ARENARIE RANZANO-MARNE ANTOGNOLA"
                            },
                            {
                              "codice": "03E3A",
                              "descrizione": " ARENARIE SAB.-MARN. OLIGOCENE"
                            },
                            {
                              "codice": "EM",
                              "descrizione": " ARGILLA LOESSICA"
                            },
                            {
                              "codice": "C",
                              "descrizione": " ARGILLE CAOLINICHE DA VULCANITI"
                            },
                            {
                              "codice": "PC",
                              "descrizione": " ARGILLE CAOLINICHE PLIOCENE"
                            },
                            {
                              "codice": "PA",
                              "descrizione": " ARGILLE DI LUGAGNANO"
                            },
                            {
                              "codice": "P2-1",
                              "descrizione": " ARGILLE DI LUGAGNANO PLEISTOCENE"
                            },
                            {
                              "codice": "M5",
                              "descrizione": " ARGILLE FORMAZIONE GESSOSO-SOLFIFERA"
                            },
                            {
                              "codice": "P1",
                              "descrizione": " ARGILLE PIACENZIANE"
                            },
                            {
                              "codice": "SGR",
                              "descrizione": " ARGILLE SABBIOSE LACUSTRI"
                            },
                            {
                              "codice": "L2",
                              "descrizione": " ARGILLE SABBIOSE VILLAFRANCHIANO"
                            },
                            {
                              "codice": "E3C6",
                              "descrizione": " CALCARI ARENACEI EOCENE"
                            },
                            {
                              "codice": "G11-5",
                              "descrizione": " CALCARI CEMENTIFERI DOGGER-MALM"
                            },
                            {
                              "codice": "CX",
                              "descrizione": " CALCARI CIPOLLINI"
                            },
                            {
                              "codice": "T2",
                              "descrizione": " CALCARI CRISTALLINI GIURA"
                            },
                            {
                              "codice": "C2G1",
                              "descrizione": " CALCARI DI VOLTAGGIO"
                            },
                            {
                              "codice": "C21",
                              "descrizione": " CALCARI DI VOLTAGGIO GIURA"
                            },
                            {
                              "codice": "PCC7",
                              "descrizione": " CALCARI DI ZEBEDASSI PALEOCENE"
                            },
                            {
                              "codice": "T42",
                              "descrizione": " CALCARI DOLOMITICI"
                            },
                            {
                              "codice": "T",
                              "descrizione": " CALCARI DOLOMITICI TRIAS"
                            },
                            {
                              "codice": "TC",
                              "descrizione": " CALCARI GRIGI BRECCIATI TRIAS"
                            },
                            {
                              "codice": "GL",
                              "descrizione": " CALCARI MARMOREI GIURA"
                            },
                            {
                              "codice": "C5",
                              "descrizione": " CALCARI MICROCRISTALLINI"
                            },
                            {
                              "codice": "CS",
                              "descrizione": " CALCESCISTI CRETACEO-GIURA"
                            },
                            {
                              "codice": "C1",
                              "descrizione": " CALCESCISTI FILLADICI"
                            },
                            {
                              "codice": "O21",
                              "descrizione": " CONGLOMERATI DI RANZANO"
                            },
                            {
                              "codice": "AC",
                              "descrizione": " CONO DI DEIEZIONE"
                            },
                            {
                              "codice": "AD",
                              "descrizione": " CONOIDE"
                            },
                            {
                              "codice": "MM",
                              "descrizione": " DEPOSITI MORENICI ARGILLOSI RISS"
                            },
                            {
                              "codice": "FGR2",
                              "descrizione": " DEPOSITI SABBIOSI ARGILLIFICATI"
                            },
                            {
                              "codice": "DG",
                              "descrizione": " DETRITI DI FALDA GNEISSICI"
                            },
                            {
                              "codice": "DGR",
                              "descrizione": " DETRITI GRANITOIDI"
                            },
                            {
                              "codice": "DP",
                              "descrizione": " DETRITI PERIDOTITICI"
                            },
                            {
                              "codice": "DQ",
                              "descrizione": " DETRITI QUARZOSI"
                            },
                            {
                              "codice": "T10",
                              "descrizione": " DETRITO"
                            },
                            {
                              "codice": "DT",
                              "descrizione": " DETRITO DI FALDA"
                            },
                            {
                              "codice": "Q",
                              "descrizione": " DILUVIALE ANTICO"
                            },
                            {
                              "codice": "Q2",
                              "descrizione": " DILUVIALE FERRETIZZATO"
                            },
                            {
                              "codice": "ALF",
                              "descrizione": " DIORITE QUARZIFERA DI BROSSO"
                            },
                            {
                              "codice": "DK",
                              "descrizione": " DIORITE-KINZIGITI"
                            },
                            {
                              "codice": "BETA",
                              "descrizione": " DIORITI MELANOCRATICHE"
                            },
                            {
                              "codice": "ALFA",
                              "descrizione": " DIORITI QUARZIFERE"
                            },
                            {
                              "codice": "TM",
                              "descrizione": " DOLOMIE CALCAREE GRIGIE TRIAS"
                            },
                            {
                              "codice": "T3-2",
                              "descrizione": " DOLOMIE CALCAREE TRIAS"
                            },
                            {
                              "codice": "FLM",
                              "descrizione": " FLUVIALE MINDELIANO"
                            },
                            {
                              "codice": "FLR",
                              "descrizione": " FLUVIALE RISSIANO"
                            },
                            {
                              "codice": "FGM",
                              "descrizione": " FLUVIOGLACIALE MINDELIANO"
                            },
                            {
                              "codice": "FGR",
                              "descrizione": " FLUVIOGLACIALE RISS WURM"
                            },
                            {
                              "codice": "E21",
                              "descrizione": " FLYSCH CALCAREO MARNOSO"
                            },
                            {
                              "codice": "GES",
                              "descrizione": " GESSO MESSINIANO"
                            },
                            {
                              "codice": "MG5",
                              "descrizione": " GESSOSO-SOLFIFERA"
                            },
                            {
                              "codice": "GGA1",
                              "descrizione": " GNEISS DIORITO-KINZIGITICI"
                            },
                            {
                              "codice": "GG",
                              "descrizione": " GNEISS GRANITOIDI"
                            },
                            {
                              "codice": "GAM",
                              "descrizione": " GNEISS GRANITOIDI PREERCINICI"
                            },
                            {
                              "codice": "GNL",
                              "descrizione": " GNEISS GRANULARI DI LUSERNA DORA-MAIRA"
                            },
                            {
                              "codice": "GGU",
                              "descrizione": " GNEISS LISTATI"
                            },
                            {
                              "codice": "GSM",
                              "descrizione": " GNEISS MINUTI"
                            },
                            {
                              "codice": "GMB",
                              "descrizione": " GNEISS MINUTI DI BROSSASCO"
                            },
                            {
                              "codice": "GS1",
                              "descrizione": " GNEISS MINUTI OCCH. SESIA LANZO"
                            },
                            {
                              "codice": "GN1",
                              "descrizione": " GNEISS MINUTI PRETRIASSICO"
                            },
                            {
                              "codice": "G",
                              "descrizione": " GNEISS MINUTI SESIA -LANZO"
                            },
                            {
                              "codice": "C1A",
                              "descrizione": " GNEISS PSAMMITICI SERIE GRAFITICA"
                            },
                            {
                              "codice": "GN",
                              "descrizione": " GNEISS SERIZZI"
                            },
                            {
                              "codice": "GK ",
                              "descrizione": "GNEISS-KINZIGITI"
                            },
                            {
                              "codice": "GAMMA",
                              "descrizione": " GRANITI BIOTITICI"
                            },
                            {
                              "codice": "GAMRO",
                              "descrizione": " GRANITI ROSSI"
                            },
                            {
                              "codice": "GAMRP",
                              "descrizione": " GRANITO ROSSO PANTHEON"
                            },
                            {
                              "codice": "GV",
                              "descrizione": " GRUPPO DI VOLTRI"
                            },
                            {
                              "codice": "LF",
                              "descrizione": " LIMI FERRETIZZATI"
                            },
                            {
                              "codice": "CT",
                              "descrizione": " LOESS ARGILLIFICATO RISS-WURM"
                            },
                            {
                              "codice": "E_G",
                              "descrizione": " MARMI CLORITO-MUSCOVITICI CRETACEI"
                            },
                            {
                              "codice": "CA",
                              "descrizione": " MARMI PREERCINICI"
                            },
                            {
                              "codice": "M",
                              "descrizione": " MARMO ROSA DI CANDOGLIA"
                            },
                            {
                              "codice": "M3-2M",
                              "descrizione": " MARNA SCAGLIOSA LANGHIANO"
                            },
                            {
                              "codice": "M1O3M",
                              "descrizione": " MARNA SILTOSO-SABBIOSA AQUITANIANO"
                            },
                            {
                              "codice": "MM32",
                              "descrizione": " MARNE DI CAMINO"
                            },
                            {
                              "codice": "M5-4",
                              "descrizione": " MARNE DI S. AGATA FOSSILI"
                            },
                            {
                              "codice": "E3",
                              "descrizione": " MARNE FORMAZ.GASSINO EOCENE"
                            },
                            {
                              "codice": "M3",
                              "descrizione": " MARNE GRIGIE TORTONIANO"
                            },
                            {
                              "codice": "M2",
                              "descrizione": " MARNE GRIGIO CALCAREE MIOCENE"
                            },
                            {
                              "codice": "M3-2",
                              "descrizione": " MARNE GRIGIO-CALCAREE SERRAVALLIANO"
                            },
                            {
                              "codice": "M10A",
                              "descrizione": " MARNE GRIGIO-VERDASTRE AQUITANIANO"
                            },
                            {
                              "codice": "M4",
                              "descrizione": " MARNE S.AGATA FOSSILI"
                            },
                            {
                              "codice": "P",
                              "descrizione": " MARNE SABBIOSE LUGAGNANO"
                            },
                            {
                              "codice": "PLZ",
                              "descrizione": " MARNE SABBIOSE PLIOCENICHE"
                            },
                            {
                              "codice": "TGS",
                              "descrizione": " MICASCISTI DI ANTIGORIO"
                            },
                            {
                              "codice": "GM ",
                              "descrizione": "MICASCISTI E GNEISS MINUTI DORA-MAIRA"
                            },
                            {
                              "codice": "GS",
                              "descrizione": " MICASCISTI E GNEISS OCCHIADINI"
                            },
                            {
                              "codice": "GMS",
                              "descrizione": " MICASCISTI GNEISS DORA-MAIRA"
                            },
                            {
                              "codice": "MO",
                              "descrizione": " MORENICO"
                            },
                            {
                              "codice": "GNT",
                              "descrizione": " ORTOGNEISS TABULARI BEOLE"
                            },
                            {
                              "codice": "AP",
                              "descrizione": " PALEOSUOLI ARGILLOSI PLEISTOCENE"
                            },
                            {
                              "codice": "LAMD",
                              "descrizione": " PERIDOTITI E ROCCE ULTRABASICHE"
                            },
                            {
                              "codice": "M3-1",
                              "descrizione": " PIETRA DA CANTONI MIOCENE"
                            },
                            {
                              "codice": "PIGRE",
                              "descrizione": " PORFIDI FERRETIZZATI"
                            },
                            {
                              "codice": "OMEGA",
                              "descrizione": " PORFIDI QUARZIFERI"
                            },
                            {
                              "codice": "QQ",
                              "descrizione": " QUARZITI E QUARZO LENT. PRETRIAS"
                            },
                            {
                              "codice": "TI",
                              "descrizione": " QUARZITI E SCISTI QUARZITICI TRIAS"
                            },
                            {
                              "codice": "TX1",
                              "descrizione": " QUARZITI TABULARI"
                            },
                            {
                              "codice": "QT",
                              "descrizione": " QUARZITI TEGULARI BARGIOLINA"
                            },
                            {
                              "codice": "M1O3",
                              "descrizione": " SABBIA E CONGLOM.POLIGENICO AQUITANIANO"
                            },
                            {
                              "codice": "P3",
                              "descrizione": " SABBIE GIALLE PLIOCENICHE ASTIANE"
                            },
                            {
                              "codice": "PS",
                              "descrizione": " SABBIE GIALLE STRATIF. VILLAFRANCHIANO"
                            },
                            {
                              "codice": "T5-4",
                              "descrizione": " SCISTI ARGILL.-CALCAREI PRETRIAS"
                            },
                            {
                              "codice": "PT",
                              "descrizione": " SCISTI FILLADICI PRETRIAS"
                            },
                            {
                              "codice": "PE1",
                              "descrizione": " SCISTI QUARZ.-SERICITICI PERM-CARBON."
                            },
                            {
                              "codice": "SIGMA",
                              "descrizione": " SERPENTINA TRIAS-GIURA"
                            },
                            {
                              "codice": "ST",
                              "descrizione": " SERPENTINITI ULTRABASICHE LANZO"
                            },
                            {
                              "codice": "SIENI",
                              "descrizione": " SIENITI ANFIBOLICHE"
                            },
                            {
                              "codice": "SIG",
                              "descrizione": " SIENITI ANFIBOLICHE CICLO ALPINO"
                            },
                            {
                              "codice": "P11",
                              "descrizione": " SILTS ASTIANI"
                            },
                            {
                              "codice": "A1FLW",
                              "descrizione": " TERRAZZI SAB.-ARGIL.PLEISTOCENE"
                            },
                            {
                              "codice": "TO",
                              "descrizione": " TORBA"
                            }
                          ]
                        },
                        "type": "select",
                        "input": true,
                        "label": "Collocazione Geologica",
                        "widget": "choicesjs",
                        "dataSrc": "json",
                        "template": "<span>{{ item.descrizione }}</span>",
                        "tableView": true
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
                "key": "columns3",
                "type": "columns",
                "input": false,
                "label": "Columns",
                "columns": [
                  {
                    "pull": 0,
                    "push": 0,
                    "size": "md",
                    "width": 5,
                    "offset": 0,
                    "components": [
                      {
                        "key": "volumeDaAutorizzare",
                        "mask": false,
                        "type": "number",
                        "input": true,
                        "label": "Volume totale da autorizzare",
                        "suffix": "m3",
                        "validate": {
                          "max": 999999999999999,
                          "required": true,
                          "customMessage": "Campo obbligatorio"
                        },
                        "delimiter": false,
                        "tableView": false,
                        "applyMaskOn": "change",
                        "inputFormat": "plain",
                        "requireDecimal": false,
                        "validateWhenHidden": false,
                        "truncateMultipleSpaces": false
                      }
                    ],
                    "currentWidth": 5
                  },
                  {
                    "pull": 0,
                    "push": 0,
                    "size": "md",
                    "width": 5,
                    "offset": 0,
                    "components": [
                      {
                        "key": "superficieDaAutorizzare",
                        "mask": false,
                        "type": "number",
                        "input": true,
                        "label": "Superficie totale da autorizzare",
                        "suffix": "m2",
                        "validate": {
                          "max": 999999999999999,
                          "required": true,
                          "customMessage": "Campo obbligatorio"
                        },
                        "delimiter": false,
                        "tableView": false,
                        "applyMaskOn": "change",
                        "inputFormat": "plain",
                        "requireDecimal": false,
                        "validateWhenHidden": false,
                        "truncateMultipleSpaces": false
                      }
                    ],
                    "currentWidth": 5
                  }
                ],
                "tableView": false
              },
              {
                "key": "destinazioneUrbanisitca",
                "type": "textarea",
                "input": true,
                "label": "Destinazione urbanisitca da P.R.G.C. vigente",
                "tableView": true,
                "autoExpand": false,
                "applyMaskOn": "change"
              },
              {
                "key": "datiVisibilita",
                "type": "container",
                "input": true,
                "label": "<b>Visibilità</b>",
                "hideLabel": false,
                "tableView": false,
                "components": [
                  {
                    "key": "strade",
                    "type": "checkbox",
                    "input": true,
                    "label": "Visibilità da strade",
                    "tableView": false,
                    "defaultValue": false,
                    "validateWhenHidden": false
                  },
                  {
                    "key": "centriAbitati",
                    "type": "checkbox",
                    "input": true,
                    "label": "Visibilità da centri abitati",
                    "tableView": false,
                    "defaultValue": false,
                    "validateWhenHidden": false
                  },
                  {
                    "key": "elementiValoreStoricoArtistico",
                    "type": "checkbox",
                    "input": true,
                    "label": "Visibilità da elementi di valore storico artistico",
                    "tableView": false,
                    "defaultValue": false,
                    "validateWhenHidden": false
                  }
                ],
                "validateWhenHidden": false
              }
            ],
            "collapsible": true
          }
        ]
      },
      {
        "key": "JS_DATI_TECNICI",
        "type": "container",
        "input": true,
        "label": "Container",
        "tableView": false,
        "components": [
          {
            "key": "panel2",
            "type": "panel",
            "input": false,
            "label": "Panel",
            "title": "Dati Tecnici",
            "collapsed": true,
            "tableView": false,
            "components": [
              {
                "label": "Tipologia",
                "tableView": false,
                "validateWhenHidden": false,
                "key": "tipologia",
                "type": "container",
                "input": true,
                "components": [
                  {
                    "label": "Columns",
                    "columns": [
                      {
                        "components": [
                          {
                            "label": "Tipologia di cava",
                            "widget": "choicesjs",
                            "tableView": true,
                            "data": {
                              "values": [
                                {
                                  "label": "In sotterraneo",
                                  "value": "inSotterraneo"
                                },
                                {
                                  "label": "A cielo aperto",
                                  "value": "aCieloAperto"
                                }
                              ]
                            },
                            "validateWhenHidden": false,
                            "key": "tipologiaDiCava1",
                            "type": "select",
                            "input": true
                          }
                        ],
                        "width": 6,
                        "offset": 0,
                        "push": 0,
                        "pull": 0,
                        "size": "md",
                        "currentWidth": 6
                      },
                      {
                        "components": [],
                        "width": 6,
                        "offset": 0,
                        "push": 0,
                        "pull": 0,
                        "size": "md",
                        "currentWidth": 6
                      }
                    ],
                    "key": "columns",
                    "type": "columns",
                    "input": false,
                    "tableView": false
                  },
                  {
                    "label": "Container",
                    "tableView": false,
                    "validateWhenHidden": false,
                    "key": "container",
                    "type": "container",
                    "input": true,
                    "components": [
                      {
                        "label": "Metodo di coltivazione",
                        "optionsLabelPosition": "right",
                        "inline": true,
                        "tableView": false,
                        "defaultValue": {
                          "conVuoti": false,
                          "conFranaDelMinerale": false,
                          "conRipiena": false
                        },
                        "values": [
                          {
                            "label": "A gradone unico",
                            "value": "conVuoti",
                            "shortcut": ""
                          },
                          {
                            "label": "Per splateamenti",
                            "value": "conFranaDelMinerale",
                            "shortcut": ""
                          },
                          {
                            "label": "A gradoni multipli",
                            "value": "conRipiena",
                            "shortcut": ""
                          }
                        ],
                        "validateWhenHidden": false,
                        "key": "CieloApertometodoDiColtivazione",
                        "conditional": {
                          "show": true,
                          "when": "JS_DATI_TECNICI.tipologia.1tipologiaDiCava",
                          "eq": "aCieloAperto"
                        },
                        "type": "selectboxes",
                        "input": true,
                        "inputType": "checkbox"
                      },
                      {
                        "label": "Metodo di coltivazione",
                        "optionsLabelPosition": "right",
                        "inline": true,
                        "tableView": false,
                        "defaultValue": {
                          "conVuoti": false,
                          "conFranaDelMinerale": false,
                          "conRipiena": false
                        },
                        "values": [
                          {
                            "label": "Con vuoti",
                            "value": "conVuoti",
                            "shortcut": ""
                          },
                          {
                            "label": "Con frana del minerale",
                            "value": "conFranaDelMinerale",
                            "shortcut": ""
                          },
                          {
                            "label": "Con ripiena",
                            "value": "conRipiena",
                            "shortcut": ""
                          }
                        ],
                        "validateWhenHidden": false,
                        "key": "metodoDiColtivazione",
                        "conditional": {
                          "show": true,
                          "when": "JS_DATI_TECNICI.tipologia.1tipologiaDiCava",
                          "eq": "inSotterraneo"
                        },
                        "type": "selectboxes",
                        "input": true,
                        "inputType": "checkbox"
                      }
                    ]
                  },
                  {
                    "label": "Container",
                    "tableView": false,
                    "validateWhenHidden": false,
                    "key": "container2",
                    "conditional": {
                      "show": true,
                      "when": "JS_DATI_TECNICI.tipologia.1tipologiaDiCava",
                      "eq": "inSotterraneo"
                    },
                    "type": "container",
                    "input": true,
                    "components": [
                      {
                        "label": "Columns",
                        "columns": [
                          {
                            "components": [
                              {
                                "label": "Altezza dei vuoti",
                                "suffix": "m",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validateWhenHidden": false,
                                "key": "altezzaDeiVuoti",
                                "type": "number",
                                "input": true
                              }
                            ],
                            "width": 4,
                            "offset": 0,
                            "push": 0,
                            "pull": 0,
                            "size": "md",
                            "currentWidth": 4
                          },
                          {
                            "components": [
                              {
                                "label": "Altezza della soletta",
                                "suffix": "m",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validateWhenHidden": false,
                                "key": "altezzaDellaSoletta",
                                "type": "number",
                                "input": true
                              }
                            ],
                            "width": 4,
                            "offset": 0,
                            "push": 0,
                            "pull": 0,
                            "size": "md",
                            "currentWidth": 4
                          },
                          {
                            "components": [
                              {
                                "label": "Indice di recupero",
                                "tooltip": "Sempre minore di 1",
                                "suffix": "%",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validateWhenHidden": false,
                                "key": "indiceDiRecupero",
                                "type": "number",
                                "input": true
                              }
                            ],
                            "size": "md",
                            "width": 4,
                            "currentWidth": 4
                          }
                        ],
                        "key": "columns",
                        "type": "columns",
                        "input": false,
                        "tableView": false
                      }
                    ]
                  },
                  {
                    "label": "Container",
                    "tableView": false,
                    "validateWhenHidden": false,
                    "key": "container1",
                    "type": "container",
                    "input": true,
                    "components": [
                      {
                        "label": "Uso di esplosivo",
                        "optionsLabelPosition": "right",
                        "inline": true,
                        "tableView": false,
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
                        "validateWhenHidden": false,
                        "key": "usoDiEsplosivo",
                        "type": "radio",
                        "input": true
                      },
                      {
                        "label": "Specificare il metodo di coltivazione con esplosivo",
                        "optionsLabelPosition": "right",
                        "tableView": false,
                        "defaultValue": {
                          "abbattimentoMassivo": false,
                          "preminaggio": false,
                          "frantumazioneDiBlocchiIsolati": false
                        },
                        "values": [
                          {
                            "label": "Abbattimento massivo",
                            "value": "abbattimentoMassivo",
                            "shortcut": ""
                          },
                          {
                            "label": "Preminaggio",
                            "value": "preminaggio",
                            "shortcut": ""
                          },
                          {
                            "label": "Frantumazione di blocchi isolati",
                            "value": "frantumazioneDiBlocchiIsolati",
                            "shortcut": ""
                          }
                        ],
                        "validateWhenHidden": false,
                        "key": "specificareIlMetodoDiColtivazioneConEsplosivo",
                        "customConditional": "const valore1 = data.JS_DATI_TECNICI.tipologia.tipologiaDiCava1;\r\nconst valore2 = data.usoDiEsplosivo;\r\n\r\nconst condizione1 = valore1 == 'aCieloAperto';\r\nconst condizione2 = valore2 == 'si';\r\n\r\nshow = condizione1 && condizione2;",
                        "type": "selectboxes",
                        "input": true,
                        "inputType": "checkbox"
                      }
                    ]
                  }
                ]
              },
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
                    "width": 4,
                    "offset": 0,
                    "components": [
                      {
                        "key": "potenzaAutorizzare",
                        "mask": false,
                        "type": "number",
                        "input": true,
                        "label": "Potenza da autorizzare",
                        "suffix": "m",
                        "validate": {
                          "max": 9999999,
                          "customMessage": "Campo obbligatorio"
                        },
                        "delimiter": false,
                        "tableView": false,
                        "applyMaskOn": "change",
                        "inputFormat": "plain",
                        "decimalLimit": 3,
                        "requireDecimal": true,
                        "validateWhenHidden": false,
                        "truncateMultipleSpaces": false
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
                        "label": "Potenza copertura",
                        "tooltip": "Terreno vegetale + cappellaccio",
                        "suffix": "m",
                        "applyMaskOn": "change",
                        "mask": false,
                        "tableView": false,
                        "delimiter": false,
                        "requireDecimal": true,
                        "inputFormat": "plain",
                        "truncateMultipleSpaces": false,
                        "validate": {
                          "required": true,
                          "customMessage": "Campo obbligatorio",
                          "max": 999
                        },
                        "validateWhenHidden": false,
                        "key": "potenzaCopertura",
                        "type": "number",
                        "decimalLimit": 3,
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
                        "key": "superficieColtivabile",
                        "mask": false,
                        "type": "number",
                        "input": true,
                        "label": "Superficie coltivabile",
                        "suffix": "m2",
                        "validate": {
                          "max": 9999999,
                          "required": true,
                          "customMessage": "Campo obbligatorio"
                        },
                        "delimiter": false,
                        "tableView": false,
                        "applyMaskOn": "change",
                        "inputFormat": "plain",
                        "decimalLimit": 3,
                        "requireDecimal": true,
                        "validateWhenHidden": false,
                        "truncateMultipleSpaces": false
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
                        "label": "Percentuale sterile",
                        "suffix": "%",
                        "applyMaskOn": "change",
                        "mask": false,
                        "tableView": false,
                        "delimiter": false,
                        "requireDecimal": true,
                        "inputFormat": "plain",
                        "truncateMultipleSpaces": false,
                        "validate": {
                          "customMessage": "Campo obbligatorio",
                          "max": 99
                        },
                        "validateWhenHidden": false,
                        "key": "percentualeSterile",
                        "type": "number",
                        "decimalLimit": 1,
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
              },
              {
                "key": "riferimentiScavo",
                "type": "container",
                "input": true,
                "label": "Container",
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
                            "key": "quotaCiglioScavo",
                            "mask": false,
                            "type": "number",
                            "input": true,
                            "label": "Quota ciglio di scavo",
                            "suffix": "m s.l.m.",
                            "validate": {
                              "max": 9999,
                              "customMessage": "Campo obbligatorio"
                            },
                            "delimiter": false,
                            "tableView": false,
                            "applyMaskOn": "change",
                            "inputFormat": "plain",
                            "decimalLimit": 2,
                            "requireDecimal": true,
                            "validateWhenHidden": false,
                            "truncateMultipleSpaces": false
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
                    "label": "Coordinate Est-Nord del punto di scavo",
                    "placeholder": "430076,168; 4958240,432",
                    "tooltip": "Inserire le coordinate separate da ; nel sistema di riferimento WGS84 UTM32N (EPSG:32632)",
                    "suffix": "WGS84 UTM32N",
                    "applyMaskOn": "change",
                    "tableView": true,
                    "validate": {
                      "required": true
                    },
                    "validateWhenHidden": false,
                    "key": "coordinatePuntoScavo",
                    "type": "textfield",
                    "input": true
                  }
                ],
                "validateWhenHidden": false
              },
              {
                "key": "columns10",
                "type": "columns",
                "input": false,
                "label": "Columns",
                "columns": [
                  {
                    "pull": 0,
                    "push": 0,
                    "size": "sm",
                    "width": 5,
                    "offset": 0,
                    "components": [
                      {
                        "key": "inclinazioneAlFronteMassimaDaAutorizzare",
                        "mask": false,
                        "type": "number",
                        "input": true,
                        "label": "Inclinazione al fronte massima da autorizzare",
                        "suffix": "°",
                        "validate": {
                          "required": true,
                          "customMessage": "Campo obbligatorio"
                        },
                        "delimiter": false,
                        "tableView": false,
                        "errorLabel": "Campo obbligatorio",
                        "applyMaskOn": "change",
                        "inputFormat": "plain",
                        "requireDecimal": false,
                        "validateWhenHidden": false,
                        "truncateMultipleSpaces": false
                      }
                    ],
                    "currentWidth": 5
                  },
                  {
                    "pull": 0,
                    "push": 0,
                    "size": "md",
                    "width": 3,
                    "offset": 0,
                    "components": [
                      {
                        "key": "inclinazioneSopraFalda",
                        "mask": false,
                        "type": "number",
                        "input": true,
                        "label": "Inclinazione sopra falda",
                        "suffix": "°",
                        "validate": {
                          "required": true,
                          "customMessage": "Campo obbligatorio"
                        },
                        "delimiter": false,
                        "tableView": false,
                        "applyMaskOn": "change",
                        "inputFormat": "plain",
                        "requireDecimal": false,
                        "validateWhenHidden": false,
                        "truncateMultipleSpaces": false
                      }
                    ],
                    "currentWidth": 3
                  },
                  {
                    "size": "md",
                    "width": 3,
                    "components": [
                      {
                        "key": "inclinazioneSottoFalda",
                        "mask": false,
                        "type": "number",
                        "input": true,
                        "label": "Inclinazione sotto falda ",
                        "suffix": "°",
                        "validate": {
                          "required": true,
                          "customMessage": "Campo obbligatorio"
                        },
                        "delimiter": false,
                        "tableView": false,
                        "applyMaskOn": "change",
                        "inputFormat": "plain",
                        "requireDecimal": false,
                        "validateWhenHidden": false,
                        "truncateMultipleSpaces": false
                      }
                    ],
                    "currentWidth": 3
                  }
                ],
                "tableView": false,
                "autoAdjust": true
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
                        "label": "Quota minima di scavo",
                        "suffix": "m s.l.m.",
                        "applyMaskOn": "change",
                        "mask": false,
                        "tableView": false,
                        "delimiter": false,
                        "requireDecimal": true,
                        "inputFormat": "plain",
                        "truncateMultipleSpaces": false,
                        "validate": {
                          "required": true,
                          "customMessage": "Campo obbligatorio",
                          "max": 9999
                        },
                        "validateWhenHidden": false,
                        "key": "quotaMinimaScavo",
                        "type": "number",
                        "decimalLimit": 2,
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
                        "label": "Quota di morbida",
                        "suffix": "m s.l.m.",
                        "applyMaskOn": "change",
                        "mask": false,
                        "tableView": false,
                        "delimiter": false,
                        "requireDecimal": true,
                        "inputFormat": "plain",
                        "truncateMultipleSpaces": false,
                        "validate": {
                          "required": true,
                          "customMessage": "campo obbligatorio",
                          "max": 9999
                        },
                        "validateWhenHidden": false,
                        "key": "quotaMorbida",
                        "type": "number",
                        "decimalLimit": 2,
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
                        "label": "Quota di magra",
                        "suffix": "m s.l.m.",
                        "applyMaskOn": "change",
                        "mask": false,
                        "tableView": false,
                        "delimiter": false,
                        "requireDecimal": true,
                        "inputFormat": "plain",
                        "truncateMultipleSpaces": false,
                        "validate": {
                          "required": true,
                          "customMessage": "campo obbligatorio",
                          "max": 9999
                        },
                        "validateWhenHidden": false,
                        "key": "quotaMagra",
                        "type": "number",
                        "decimalLimit": 2,
                        "input": true
                      }
                    ],
                    "currentWidth": 4
                  }
                ],
                "key": "columns7",
                "type": "columns",
                "input": false,
                "tableView": false
              },
              {
                "key": "gradoni",
                "type": "container",
                "input": true,
                "label": "<b>Gradoni</b>",
                "tableView": false,
                "components": [
                  {
                    "key": "presenzaDiGradoni",
                    "type": "radio",
                    "input": true,
                    "label": "<b>Presenza di gradoni</b>",
                    "inline": true,
                    "values": [
                      {
                        "label": "SI",
                        "value": "si",
                        "shortcut": ""
                      },
                      {
                        "label": "NO",
                        "value": "no",
                        "shortcut": ""
                      }
                    ],
                    "tableView": false,
                    "validateWhenHidden": false,
                    "optionsLabelPosition": "right"
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
                            "key": "numero",
                            "mask": false,
                            "type": "number",
                            "input": true,
                            "label": "Numero",
                            "validate": {
                              "max": 99,
                              "required": true,
                              "customMessage": "Campo obbligatorio"
                            },
                            "delimiter": false,
                            "tableView": false,
                            "applyMaskOn": "change",
                            "conditional": {
                              "eq": "si",
                              "show": true,
                              "when": "JS_DATI_TECNICI.gradoni.presenzaDiGradoni"
                            },
                            "inputFormat": "plain",
                            "requireDecimal": false,
                            "validateWhenHidden": false,
                            "truncateMultipleSpaces": false
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
                            "label": "Alzata",
                            "suffix": "m",
                            "applyMaskOn": "change",
                            "mask": false,
                            "tableView": false,
                            "delimiter": false,
                            "requireDecimal": false,
                            "inputFormat": "plain",
                            "truncateMultipleSpaces": false,
                            "validate": {
                              "required": true,
                              "customMessage": "Campo obbligatorio",
                              "max": 99
                            },
                            "validateWhenHidden": false,
                            "key": "altezza",
                            "conditional": {
                              "show": true,
                              "when": "JS_DATI_TECNICI.gradoni.presenzaDiGradoni",
                              "eq": "si"
                            },
                            "type": "number",
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
                            "label": "Pedata",
                            "suffix": "m",
                            "applyMaskOn": "change",
                            "mask": false,
                            "tableView": false,
                            "delimiter": false,
                            "requireDecimal": false,
                            "inputFormat": "plain",
                            "truncateMultipleSpaces": false,
                            "validate": {
                              "required": true,
                              "customMessage": "Campo obbligatorio",
                              "max": 99
                            },
                            "validateWhenHidden": false,
                            "key": "larghezza",
                            "conditional": {
                              "show": true,
                              "when": "JS_DATI_TECNICI.gradoni.presenzaDiGradoni",
                              "eq": "si"
                            },
                            "type": "number",
                            "input": true
                          }
                        ],
                        "currentWidth": 3
                      },
                      {
                        "size": "md",
                        "width": 3,
                        "components": [
                          {
                            "key": "inclinazione",
                            "mask": false,
                            "type": "number",
                            "input": true,
                            "label": "Inclinazione",
                            "suffix": "°",
                            "validate": {
                              "required": true,
                              "customMessage": "Campo obbligatorio"
                            },
                            "delimiter": false,
                            "tableView": false,
                            "applyMaskOn": "change",
                            "conditional": {
                              "eq": "si",
                              "show": true,
                              "when": "JS_DATI_TECNICI.gradoni.presenzaDiGradoni"
                            },
                            "inputFormat": "plain",
                            "requireDecimal": false,
                            "validateWhenHidden": false,
                            "truncateMultipleSpaces": false
                          }
                        ],
                        "currentWidth": 3
                      }
                    ],
                    "tableView": false
                  }
                ],
                "validateWhenHidden": false
              },
              {
                "key": "columns12",
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
                        "key": "usoSuoloPrevalente",
                        "data": {
                          "json": [
                            "RISAIE",
                            "SEMINATIVI",
                            "SEMINATIVI DA FORAGGIO",
                            "COLTURE SPECIALIZZATE",
                            "PRATI AVVICENDATI",
                            "PRATI PERMANENTI",
                            "PRATI PASCOLI",
                            "PASCOLI",
                            "INCOLTI",
                            "FRUTTETI",
                            "VIGNETI",
                            "NOCCIOLETI",
                            "PIOPPETI",
                            "ALTRE COLTIVAZIONI LEGNOSE AGRARIE",
                            "CASTAGNETI DA FRUTTO",
                            "FUSTAIE DI CONIFERE",
                            "FUSTAIE DI LATIFOGLIE",
                            "CEDUI",
                            "ACQUE"
                          ]
                        },
                        "type": "select",
                        "input": true,
                        "label": "Uso del suolo prevalente per la parte esterna",
                        "widget": "choicesjs",
                        "dataSrc": "json",
                        "validate": {
                          "required": true,
                          "customMessage": "Campo obbligatorio"
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
                        "key": "classiCapacita",
                        "data": {
                          "json": [
                            {
                              "codice": "I",
                              "descrizione": "SUOLO PRIVO DI LIMITAZIONI ALLA COLTIVAZIONE"
                            },
                            {
                              "codice": "II",
                              "descrizione": "SUOLO CON LIMITAZIONI MODERATE ALLA COLTIVAZIONE"
                            },
                            {
                              "codice": "III",
                              "descrizione": "SUOLO CON LIMITAZIONI SEVERE ALLA COLTIVAZIONE"
                            },
                            {
                              "codice": "IV",
                              "descrizione": "SUOLO CON LIMITAZIONI MOLTO SEVERE ALLA COLTIVAZIONE"
                            },
                            {
                              "codice": "V",
                              "descrizione": "SUOLO PRINCIPALMENTE ADATTO A PASCOLO E BOSCO"
                            },
                            {
                              "codice": "VI",
                              "descrizione": "SUOLO ADATTO SOLO A PASCOLO E BOSCO"
                            },
                            {
                              "codice": "VII",
                              "descrizione": "SUOLO CON LIMITAZIONI FORTI AD USI PRODUTTIVI"
                            },
                            {
                              "codice": "VIII",
                              "descrizione": "SUOLO CON LIMITAZIONI TALI DA PRECLUDERE USI PRODUTTIVI"
                            }
                          ]
                        },
                        "type": "select",
                        "input": true,
                        "label": "Classe di capacità prevalente",
                        "widget": "choicesjs",
                        "dataSrc": "json",
                        "template": "<span>{{ item.descrizione }}</span>",
                        "validate": {
                          "required": true,
                          "customMessage": "Campo obbligatorio"
                        },
                        "tableView": true,
                        "validateWhenHidden": false
                      }
                    ],
                    "currentWidth": 6
                  }
                ],
                "tableView": false
              },
              {
                "key": "pertinenze",
                "type": "container",
                "input": true,
                "label": "Container",
                "tableView": false,
                "components": [
                  {
                    "key": "presenzaPertinenzeMinerarie",
                    "type": "radio",
                    "input": true,
                    "label": "<b>Presenza pertinenze minerarie</b>",
                    "inline": true,
                    "values": [
                      {
                        "label": "SI",
                        "value": "si",
                        "shortcut": ""
                      },
                      {
                        "label": "NO",
                        "value": "no",
                        "shortcut": ""
                      }
                    ],
                    "validate": {
                      "required": true,
                      "customMessage": "Campo obbligatorio"
                    },
                    "tableView": false,
                    "validateWhenHidden": false,
                    "optionsLabelPosition": "right"
                  },
                  {
                    "key": "tipoPertinenzeMinerarie",
                    "type": "container",
                    "input": true,
                    "label": "<b>Tipo di pertinenze</b>",
                    "validate": {
                      "required": true,
                      "customMessage": "Campo obbligatorio"
                    },
                    "hideLabel": false,
                    "tableView": false,
                    "components": [
                      {
                        "key": "depositoSfridi",
                        "type": "checkbox",
                        "input": true,
                        "label": "Strutture di deposito per sfridi derivanti dall'attività",
                        "tableView": false,
                        "defaultValue": false,
                        "validateWhenHidden": false
                      },
                      {
                        "key": "vascheStoccaggio",
                        "type": "checkbox",
                        "input": true,
                        "label": "Vasche di stoccaggio fini",
                        "tableView": false,
                        "defaultValue": false,
                        "validateWhenHidden": false
                      },
                      {
                        "key": "impiantoTrattamento",
                        "type": "checkbox",
                        "input": true,
                        "label": "Impianto di trattamento fisso",
                        "tableView": false,
                        "defaultValue": false,
                        "validateWhenHidden": false
                      },
                      {
                        "label": "Altro",
                        "tableView": false,
                        "defaultValue": false,
                        "validateWhenHidden": false,
                        "key": "altro",
                        "type": "checkbox",
                        "input": true
                      }
                    ],
                    "conditional": {
                      "eq": "si",
                      "show": true,
                      "when": "JS_DATI_TECNICI.pertinenze.presenzaPertinenzeMinerarie"
                    },
                    "validateWhenHidden": false
                  }
                ],
                "validateWhenHidden": false
              }
            ],
            "collapsible": true
          }
        ]
      },
      {
        "key": "JS_RECUPERO_AMBIENTALE",
        "type": "container",
        "input": true,
        "label": "Container",
        "tableView": false,
        "components": [
          {
            "key": "recuperoAmbientale",
            "type": "panel",
            "input": false,
            "label": "Panel",
            "title": "Recupero Ambientale",
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
                        "key": "destinazioneFinale1",
                        "data": {
                          "json": [
                            "AGRICOLA",
                            "FORESTALE",
                            "NATURALISTICA",
                            "TURISTICO-RICREATIVA",
                            "BACINO PER UTILIZZO ACQUA",
                            "RIUTILIZZO VUOTI SOTTERRANEI"
                          ]
                        },
                        "type": "select",
                        "input": true,
                        "label": "Destinazione finale 1",
                        "widget": "choicesjs",
                        "dataSrc": "json",
                        "validate": {
                          "required": true,
                          "customMessage": "Campo obbligatorio"
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
                        "key": "destinazioneFinale2",
                        "data": {
                          "json": [
                            "AGRICOLA",
                            "FORESTALE",
                            "NATURALISTICA",
                            "TURISTICO-RICREATIVA",
                            "BACINO PER UTILIZZO ACQUA",
                            "RIUTILIZZO VUOTI SOTTERRANEI"
                          ]
                        },
                        "type": "select",
                        "input": true,
                        "label": "Destinazione finale 2",
                        "widget": "choicesjs",
                        "dataSrc": "json",
                        "tableView": true
                      }
                    ],
                    "currentWidth": 6
                  }
                ],
                "tableView": false
              },
              {
                "key": "destinazioneFinale3",
                "type": "textarea",
                "input": true,
                "label": "Destinazione finale 3",
                "tableView": true,
                "autoExpand": false,
                "applyMaskOn": "change",
                "validateWhenHidden": false
              },
              {
                "key": "ripristionoMorfologico",
                "type": "container",
                "input": true,
                "label": "<b>Tipologia di ripristino morfologico</b>",
                "validate": {
                  "required": true,
                  "customMessage": "Campo obbligatorio"
                },
                "hideLabel": false,
                "tableView": false,
                "components": [
                  {
                    "key": "riportoTerrenoVegetale1",
                    "type": "container",
                    "input": true,
                    "label": "Container",
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
                            "width": 5,
                            "offset": 0,
                            "components": [
                              {
                                "key": "riempimentoParziale",
                                "type": "checkbox",
                                "input": true,
                                "label": "Riempimento Parziale",
                                "tableView": false,
                                "defaultValue": false
                              }
                            ],
                            "currentWidth": 5
                          },
                          {
                            "pull": 0,
                            "push": 0,
                            "size": "md",
                            "width": 5,
                            "offset": 0,
                            "components": [
                              {
                                "label": "Cubatura",
                                "labelPosition": "left-left",
                                "suffix": "m3",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "required": true,
                                  "customMessage": "Campo obbligatorio",
                                  "max": 9999999999
                                },
                                "validateWhenHidden": false,
                                "key": "cubatura",
                                "conditional": {
                                  "show": true,
                                  "when": "JS_RECUPERO_AMBIENTALE.ripristionoMorfologico.riportoTerrenoVegetale1.riempimentoParziale",
                                  "eq": "true"
                                },
                                "type": "number",
                                "input": true
                              }
                            ],
                            "currentWidth": 5
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
                      }
                    ]
                  },
                  {
                    "key": "riportoTerrenoVegetale",
                    "type": "container",
                    "input": true,
                    "label": "Container",
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
                            "width": 5,
                            "offset": 0,
                            "components": [
                              {
                                "key": "riempimentoTotale",
                                "type": "checkbox",
                                "input": true,
                                "label": "Riempimento totale",
                                "tableView": false,
                                "defaultValue": false
                              }
                            ],
                            "currentWidth": 5
                          },
                          {
                            "pull": 0,
                            "push": 0,
                            "size": "md",
                            "width": 5,
                            "offset": 0,
                            "components": [
                              {
                                "label": "Cubatura",
                                "labelPosition": "left-left",
                                "suffix": "m3",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "required": true,
                                  "customMessage": "Campo obbligatorio",
                                  "max": 9999999999
                                },
                                "validateWhenHidden": false,
                                "key": "cubatura",
                                "conditional": {
                                  "show": true,
                                  "when": "JS_RECUPERO_AMBIENTALE.ripristionoMorfologico.riportoTerrenoVegetale.riempimentoTotale",
                                  "eq": "true"
                                },
                                "type": "number",
                                "input": true
                              }
                            ],
                            "currentWidth": 5
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
                      }
                    ]
                  },
                  {
                    "key": "rimodellamentoDelleSuperfici",
                    "type": "checkbox",
                    "input": true,
                    "label": "Rimodellamento delle superfici",
                    "tableView": false,
                    "defaultValue": false
                  },
                  {
                    "key": "riportoTerrenoVegetale2",
                    "type": "container",
                    "input": true,
                    "label": "Container",
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
                            "width": 5,
                            "offset": 0,
                            "components": [
                              {
                                "key": "riportoTerreno",
                                "type": "checkbox",
                                "input": true,
                                "label": "Riporto terreno vegetale",
                                "tableView": false,
                                "defaultValue": false
                              }
                            ],
                            "currentWidth": 5
                          },
                          {
                            "pull": 0,
                            "push": 0,
                            "size": "md",
                            "width": 5,
                            "offset": 0,
                            "components": [
                              {
                                "label": "Cubatura",
                                "labelPosition": "left-left",
                                "suffix": "m3",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "required": true,
                                  "customMessage": "Campo obbligatorio",
                                  "max": 9999999999
                                },
                                "validateWhenHidden": false,
                                "key": "cubatura",
                                "conditional": {
                                  "show": true,
                                  "when": "JS_RECUPERO_AMBIENTALE.ripristionoMorfologico.riportoTerrenoVegetale2.riportoTerreno",
                                  "eq": "true"
                                },
                                "type": "number",
                                "input": true
                              }
                            ],
                            "currentWidth": 5
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
                      }
                    ]
                  }
                ],
                "validateWhenHidden": false
              },
              {
                "key": "materialeRiempimento",
                "type": "container",
                "input": true,
                "label": "<b>Materiale di riempimento (ex art 5 RR 3/R del 2022) </b>",
                "validate": {
                  "required": true,
                  "customMessage": "Campo obbligatorio"
                },
                "hideLabel": false,
                "tableView": false,
                "components": [
                  {
                    "key": "rifiutiEstrazione",
                    "type": "checkbox",
                    "input": true,
                    "label": "a) rifiuti di estrazione",
                    "tableView": false,
                    "defaultValue": false,
                    "validateWhenHidden": false
                  },
                  {
                    "key": "terreRocceScavo",
                    "type": "checkbox",
                    "input": true,
                    "label": "b) terre e rocce da scavo",
                    "tableView": false,
                    "defaultValue": false,
                    "validateWhenHidden": false
                  },
                  {
                    "key": "rifiutiAttivitaLavorazioneCave",
                    "type": "checkbox",
                    "input": true,
                    "label": "c) rifiuti provenienti da attività di lavorazione di materiali estratti da cave",
                    "tableView": false,
                    "defaultValue": false,
                    "validateWhenHidden": false
                  },
                  {
                    "key": "materialiSottoprodotto",
                    "type": "checkbox",
                    "input": true,
                    "label": "d) materiali aventi la qualifica di sottoprodotto",
                    "tableView": false,
                    "defaultValue": false,
                    "validateWhenHidden": false
                  },
                  {
                    "key": "materialiNoQualificaUsoSpecifico",
                    "type": "checkbox",
                    "input": true,
                    "label": "e) materiali che abbiano cessato la qualifica di rifiuto destinati all'uso specifico",
                    "tableView": false,
                    "defaultValue": false,
                    "validateWhenHidden": false
                  },
                  {
                    "key": "materialiIdoneiRecuperoAmbientale",
                    "type": "checkbox",
                    "input": true,
                    "label": "f) rifiuti individuati al punto 7.31 bis dal d.m. 5 febbraio 1998 idonei ai fini del recupero ambientale",
                    "tableView": false,
                    "defaultValue": false,
                    "validateWhenHidden": false
                  },
                  {
                    "key": "altriRifiuti",
                    "type": "container",
                    "input": true,
                    "label": "Container",
                    "tableView": false,
                    "components": [
                      {
                        "label": "g) altri rifiuti di origine minerale diversi da quelli di cui alla lettere c) ed f) individuati ai punti 7.1, 7.4, 7.14, dal d.m. 5 febbraio 1998 ai fini del recupero ambientale (R10), anche se autorizzati secondo le procedure previste dall’articolo 208, d.lgs. 152/2006, nel rispetto dei disposti di cui all’articolo 5 del citato d.m.. Potrà essere valutato anche l'utilizzo di ulteriori tipologie di rifiuti, individuati esclusivamente nei capitoli 7 e 12 dell'Allegato 1, Suballegato 1 al d.m. 5 febbraio 1998 ai fini del recupero ambientale (R10), solo se supportato dai risultati di uno studio specifico, presentato dal proponente, che dovrà essere preliminarmente validato da ARPA Piemonte.",
                        "tableView": false,
                        "defaultValue": false,
                        "validateWhenHidden": false,
                        "key": "altriRifiutiOrigineMinerale",
                        "type": "checkbox",
                        "input": true
                      },
                      {
                        "key": "altriRifiutiCodiceCer",
                        "type": "textfield",
                        "input": true,
                        "label": "Codice CER per altri rifiuti di origine minerale diversi",
                        "tableView": true,
                        "applyMaskOn": "change",
                        "conditional": {
                          "eq": "true",
                          "show": true,
                          "when": "JS_RECUPERO_AMBIENTALE.materialeRiempimento.altriRifiuti.altriRifiutiOrigineMinerale"
                        },
                        "validateWhenHidden": false
                      }
                    ],
                    "validateWhenHidden": false
                  }
                ],
                "validateWhenHidden": false
              },
              {
                "key": "interventiRegimazione",
                "type": "container",
                "input": true,
                "label": "<b>Interventi di regimazione </b>",
                "validate": {
                  "required": true,
                  "customMessage": "Campo obbligatorio"
                },
                "hideLabel": false,
                "tableView": false,
                "components": [
                  {
                    "key": "canalette",
                    "type": "checkbox",
                    "input": true,
                    "label": "Canalette",
                    "tableView": false,
                    "defaultValue": false
                  },
                  {
                    "key": "caditoie",
                    "type": "checkbox",
                    "input": true,
                    "label": "Caditoie",
                    "tableView": false,
                    "defaultValue": false,
                    "validateWhenHidden": false
                  },
                  {
                    "key": "vascheRaccolta",
                    "type": "checkbox",
                    "input": true,
                    "label": "Vasche di raccolta",
                    "tableView": false,
                    "defaultValue": false
                  },
                  {
                    "label": "Trincee drenanti",
                    "tableView": false,
                    "defaultValue": false,
                    "validateWhenHidden": false,
                    "key": "trincee",
                    "type": "checkbox",
                    "input": true
                  },
                  {
                    "label": "Container",
                    "tableView": false,
                    "validateWhenHidden": false,
                    "key": "interventiRegimazione",
                    "type": "container",
                    "input": true,
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
                            "width": 5,
                            "offset": 0,
                            "components": [
                              {
                                "label": "Altro",
                                "tableView": false,
                                "defaultValue": false,
                                "validateWhenHidden": false,
                                "key": "altro",
                                "type": "checkbox",
                                "input": true
                              }
                            ],
                            "currentWidth": 5
                          },
                          {
                            "pull": 0,
                            "push": 0,
                            "size": "md",
                            "width": 5,
                            "offset": 0,
                            "components": [],
                            "currentWidth": 5
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
                        "label": "Specificare altro",
                        "applyMaskOn": "change",
                        "autoExpand": false,
                        "tableView": true,
                        "validate": {
                          "required": true
                        },
                        "validateWhenHidden": false,
                        "key": "descrizioneAltro",
                        "conditional": {
                          "show": true,
                          "when": "JS_RECUPERO_AMBIENTALE.interventiRegimazione.interventiRegimazione.altro",
                          "eq": "true"
                        },
                        "type": "textarea",
                        "input": true
                      }
                    ]
                  }
                ],
                "validateWhenHidden": false
              },
              {
                "key": "interventiPiazzale",
                "type": "container",
                "input": true,
                "label": "<b>Interventi di recupero ambientale </b>",
                "validate": {
                  "required": true,
                  "customMessage": "Campo obbligatorio"
                },
                "hideLabel": false,
                "tableView": false,
                "components": [
                  {
                    "key": "inerbimento",
                    "type": "checkbox",
                    "input": true,
                    "label": "Inerbimento",
                    "tableView": false,
                    "defaultValue": false
                  },
                  {
                    "key": "impiantoSpecieArbustive",
                    "type": "checkbox",
                    "input": true,
                    "label": "Impianto specie arbustive",
                    "tableView": false,
                    "defaultValue": false
                  },
                  {
                    "key": "impiantoSpecieArboree",
                    "type": "checkbox",
                    "input": true,
                    "label": "Impianto specie arboree",
                    "tableView": false,
                    "defaultValue": false
                  }
                ],
                "validateWhenHidden": false
              }
            ],
            "collapsible": true
          }
        ]
      },
      {
        "label": "Container",
        "tableView": false,
        "validateWhenHidden": false,
        "key": "JS_PRAE",
        "type": "container",
        "input": true,
        "components": [
          {
            "title": "Set di indicatori di monitoraggio ambientale del PRAE ",
            "collapsible": true,
            "key": "setDiIndicatoriDiMonitoraggioAmbientaleDelPrae",
            "type": "panel",
            "label": "Panel",
            "input": false,
            "tableView": false,
            "components": [
              {
                "label": "Indicatori",
                "components": [
                  {
                    "label": "Attività estrattive",
                    "key": "tab2",
                    "components": [
                      {
                        "label": "indicatori attività estrattiva",
                        "columns": [
                          {
                            "components": [
                              {
                                "label": "Superficie autorizzata ",
                                "tooltip": "L'indicatore misura i metri quadrati di superficie impegnata da attività di scavo nel sito di cava",
                                "suffix": "m2",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "min": 0,
                                  "max": 1000000000
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Numero massimo di caratteri raggiunti per la superficie autorizzata ",
                                "key": "superficieAutorizzata1",
                                "type": "number",
                                "decimalLimit": 2,
                                "input": true
                              }
                            ],
                            "width": 6,
                            "offset": 0,
                            "push": 0,
                            "pull": 0,
                            "size": "md",
                            "currentWidth": 6
                          },
                          {
                            "components": [
                              {
                                "label": "Superficie da autorizzare",
                                "tooltip": "L'indicatore misura i metri quadrati di superficie impegnata da attività di scavo in aree di ampliamento\r\n",
                                "suffix": "m2",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "min": 0,
                                  "max": 1000000000
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Numero massimo di caratteri raggiunti per la superficie da autorizzare",
                                "key": "superficieAutorizzare",
                                "type": "number",
                                "decimalLimit": 2,
                                "input": true
                              }
                            ],
                            "width": 6,
                            "offset": 0,
                            "push": 0,
                            "pull": 0,
                            "size": "md",
                            "currentWidth": 6
                          },
                          {
                            "components": [
                              {
                                "label": "Volumetria autorizzata                                                                                     ",
                                "tooltip": "L’indicatore misura la volumetria autorizzata e non ancora estratta\r\n",
                                "suffix": "m3",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "min": 0,
                                  "max": 1000000000
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Numero massimo di caratteri raggiunti per il volume autorizzato ",
                                "key": "volumetriaAutorizzata",
                                "type": "number",
                                "decimalLimit": 2,
                                "input": true
                              }
                            ],
                            "size": "md",
                            "width": 6,
                            "currentWidth": 6
                          },
                          {
                            "components": [
                              {
                                "label": "Volumetria autorizzata in ampliamenti pianificati",
                                "tooltip": "L’indicatore misura la volumetria autorizzata in ampliamenti pianificati",
                                "suffix": "m3",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "min": 0,
                                  "max": 1000000000
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Numero massimo di caratteri raggiunti per il volume autorizzato  in ampliamenti pianificati",
                                "key": "volumetriaAutorizzataAmpliamenti",
                                "type": "number",
                                "decimalLimit": 2,
                                "input": true
                              }
                            ],
                            "size": "md",
                            "width": 6,
                            "currentWidth": 6
                          },
                          {
                            "components": [
                              {
                                "label": "Superficie impegnata dal cantiere su autorizzazione in vigore",
                                "tooltip": "L’indicatore misura la superficie impegnata dal cantiere estrattivo sul totale della superficie prevista da autorizzazione in vigore",
                                "suffix": "%",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "min": 0,
                                  "max": 100
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Numero massimo di caratteri raggiunti per la superficie impegnata dal cantiere",
                                "key": "superficieImpegnataDalCantiereSuAutorizzazioneInVigore",
                                "type": "number",
                                "input": true
                              }
                            ],
                            "size": "md",
                            "width": 6,
                            "currentWidth": 6
                          },
                          {
                            "components": [
                              {
                                "label": "Superficie impegnata dal cantiere su superfici di ampliamento pianificati nel PRAE",
                                "suffix": "%",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "min": 0,
                                  "max": 100
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Numero massimo di caratteri raggiunti per la superficie impegnata dal cantiere su superfici di ampliamento pianificati nel PRAE",
                                "key": "superficieImpegnataDalCantiereSuperficiAmpliamentoPianificati",
                                "type": "number",
                                "input": true
                              }
                            ],
                            "size": "md",
                            "width": 6,
                            "currentWidth": 6
                          },
                          {
                            "components": [
                              {
                                "label": "Superficie in fase di recupero su autorizzazione in vigore",
                                "tooltip": "L’indicatore misura la superficie impegnata dal cantiere estrattivo sul totale della superficie prevista da autorizzazione in vigore",
                                "suffix": "%",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "min": 0,
                                  "max": 100
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Numero massimo di caratteri raggiunti per la superficie in fase di recupero su autorizzazione in vigore",
                                "key": "superficieFaseRecuperoAutorizzazioneVigore",
                                "type": "number",
                                "input": true
                              }
                            ],
                            "size": "md",
                            "width": 6,
                            "currentWidth": 6
                          },
                          {
                            "components": [
                              {
                                "label": "Aree oggetto di interventi compensativi in corso e ultimati",
                                "tooltip": "L’indicatore conta la superficie oggetto di interventi compensativi in corso e ultimati",
                                "suffix": "m2",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "min": 0,
                                  "max": 1000000000
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Numero massimo di caratteri raggiunti per aree oggetto di interventi",
                                "key": "AreeOggettoInterventiCompensativi",
                                "type": "number",
                                "decimalLimit": 2,
                                "input": true
                              }
                            ],
                            "size": "md",
                            "width": 6,
                            "currentWidth": 6
                          },
                          {
                            "components": [
                              {
                                "label": "Certificazioni ambientali ottenute dalla singola cava",
                                "optionsLabelPosition": "right",
                                "tooltip": "L'indicatore considera le certificazioni ambientali ottenute dalle attività estrattive come misura indiretta del livello di qualità al fine di raggiungere soglie di certificazione ambientale europea di riferimento (ISO 14000 o EMAS)",
                                "inline": true,
                                "tableView": false,
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
                                "validateWhenHidden": false,
                                "key": "certificazioniAmbientaliOttenuteDallaSingolaCava",
                                "type": "radio",
                                "input": true
                              }
                            ],
                            "size": "md",
                            "width": 6,
                            "currentWidth": 6
                          },
                          {
                            "components": [],
                            "size": "md",
                            "width": 6,
                            "currentWidth": 6
                          },
                          {
                            "components": [
                              {
                                "label": "Rapporto tra volume estratto e volume residuo da autorizzazioni vigenti",
                                "tooltip": "L’indicatore misura il rapporto in percentuale tra il volume estratto e volume residuo previsto da autorizzazione in vigore ",
                                "suffix": "%",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "min": 0,
                                  "max": 100
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Numero massimo di caratteri raggiunti per il rapporto tra volume estratto e volume residuo da autorizzazioni vigenti",
                                "key": "rapportoVolumeEstrattoVolumeResiduoAutVigenti",
                                "type": "number",
                                "input": true
                              }
                            ],
                            "size": "md",
                            "width": 6,
                            "currentWidth": 6
                          },
                          {
                            "components": [
                              {
                                "label": "Area estrattiva già autorizzata ricadente in fascia A e B del PAI",
                                "description": "Valore da inserire anche se pari a 0",
                                "tooltip": "L’indicatore monitora l’eventuale presenza di criticità nelle attività estrattive sui processi fluvio-torrentizi e di versante, attraverso il calcolo della superficie di ampliamenti già autorizzati ricadenti in fascia A e B del PAI",
                                "suffix": "m2",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "required": true,
                                  "min": 0,
                                  "max": 1000000000
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Valore massimo raggiunto per l'area estrattiva già autorizzata ricadente in fascia A e B del PAI",
                                "key": "areaEstrattivaAutorizzataFasciaABPai1",
                                "type": "number",
                                "decimalLimit": 2,
                                "input": true
                              }
                            ],
                            "size": "md",
                            "width": 6,
                            "currentWidth": 6
                          },
                          {
                            "components": [
                              {
                                "label": "Area estrattiva già autorizzata ricadente in Frane attive  Fa",
                                "description": "Valore da inserire anche se pari a 0",
                                "tooltip": "L’indicatore monitora l’eventuale presenza di criticità nelle attività estrattive sui processi fluvio-torrentizi e di versante, attraverso il calcolo della superficie di ampliamenti già autorizzati ricadenti in Frane attive Fa",
                                "suffix": "m2",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "required": true,
                                  "min": 0,
                                  "max": 1000000000
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Valore massimo raggiunto per l'area estrattiva già autorizzata ricadente in Frane attive Fa",
                                "key": "areaEstrattivaAutorizzataRicadenteFranaAttiva",
                                "type": "number",
                                "decimalLimit": 2,
                                "input": true
                              }
                            ],
                            "size": "md",
                            "width": 6,
                            "currentWidth": 6
                          },
                          {
                            "components": [
                              {
                                "label": "Area estrattiva già autorizzata ricadente in Aree a rischio molto elevato RME ",
                                "description": "Valore da inserire anche se pari a 0",
                                "tooltip": "L’indicatore monitora l’eventuale presenza di criticità nelle attività estrattive sui processi fluvio-torrentizi e di versante, attraverso il calcolo della superficie di ampliamenti già autorizzati ricadenti in Aree a rischio molto elevato RME",
                                "suffix": "m2",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "required": true,
                                  "min": 0,
                                  "max": 1000000000
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Valore massimo raggiunto per l'area estrattiva già autorizzata ricadente in Aree a rischio molto elevato RME",
                                "key": "areaEstrattivaAutorizzataAreeRischioMElevatoRME",
                                "type": "number",
                                "decimalLimit": 2,
                                "input": true
                              }
                            ],
                            "size": "md",
                            "width": 6,
                            "currentWidth": 6
                          },
                          {
                            "components": [
                              {
                                "label": "Area estrattive già autorizzate ricadente in aree PGRA scenari H e M dei reticoli RP, RSP e RSCM",
                                "description": "Valore da inserire anche se pari a 0",
                                "tooltip": "L’indicatore monitora l’eventuale presenza di criticità nelle attività estrattive sui processi fluvio-torrentizi e di versante, attraverso il calcolo della superficie",
                                "suffix": "m2",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "required": true,
                                  "min": 0,
                                  "max": 1000000000
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Valore massimo raggiunto per l'area estrattiva già autorizzate ricadente in aree PGRA scenari H e M dei reticoli RP, RSP e RSCM",
                                "key": "areaEstrattivaAutorizzataAreePGRAScenariHMReticoli",
                                "type": "number",
                                "decimalLimit": 2,
                                "input": true
                              }
                            ],
                            "size": "md",
                            "width": 6,
                            "currentWidth": 6
                          },
                          {
                            "components": [
                              {
                                "label": "Area estrattiva già autorizzata ricadente in Conoidi parz. protetti Cp ",
                                "description": "Valore da inserire anche se pari a 0",
                                "tooltip": "L’indicatore monitora l’eventuale presenza di criticità nelle attività estrattive sui processi fluvio-torrentizi e di versante, attraverso il calcolo della superficie di ampliamenti già autorizzati ricadenti in Conoidi parz. protetti Cp",
                                "suffix": "m2",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "required": true,
                                  "min": 0,
                                  "max": 1000000000
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Valore massimo raggiunto per l'area estrattiva già autorizzata ricadente in Conoidi parz. protetti Cp",
                                "key": "areaEstrattivaAutorizzataConoidiParzProtettiCp",
                                "type": "number",
                                "decimalLimit": 2,
                                "input": true
                              }
                            ],
                            "size": "md",
                            "width": 6,
                            "currentWidth": 6
                          },
                          {
                            "components": [
                              {
                                "label": "Area estrattiva già autorizzata ricadente in Esondazioni Eb",
                                "placeholder": "m2",
                                "description": "Valore da inserire anche se pari a 0",
                                "tooltip": "L’indicatore monitora l’eventuale presenza di criticità nelle attività estrattive sui processi fluvio-torrentizi e di versante, attraverso il calcolo della superficie di ampliamenti già autorizzati ricadenti in Esondazioni Eb",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "required": true,
                                  "min": 0,
                                  "max": 1000000000
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Valore massimo raggiunto per l'area estrattivagià autorizzata ricadente in Esondazioni Eb",
                                "key": "areaEstrattivaAutorizzataRicadenteEsondazioneEb",
                                "type": "number",
                                "decimalLimit": 2,
                                "input": true
                              }
                            ],
                            "size": "md",
                            "width": 6,
                            "currentWidth": 6
                          },
                          {
                            "components": [],
                            "size": "md",
                            "width": 6,
                            "currentWidth": 6
                          },
                          {
                            "components": [],
                            "size": "md",
                            "width": 6,
                            "currentWidth": 6
                          },
                          {
                            "components": [],
                            "size": "md",
                            "width": 6,
                            "currentWidth": 6
                          }
                        ],
                        "key": "indicatori",
                        "type": "columns",
                        "input": false,
                        "tableView": false
                      }
                    ]
                  },
                  {
                    "label": "Acqua",
                    "key": "acqua",
                    "components": [
                      {
                        "label": "Columns",
                        "columns": [
                          {
                            "components": [
                              {
                                "label": "Numero di fontanili",
                                "tooltip": "Numero di fontanili presenti nel sito estrattivo e nel raggio di 500m dall’attività estrattiva / Numero totale di fontanili rilevati\r\n\r\n",
                                "suffix": "n°/n°totale",
                                "applyMaskOn": "change",
                                "tableView": true,
                                "validateWhenHidden": false,
                                "key": "numeroDiFontanili",
                                "type": "textfield",
                                "input": true
                              }
                            ],
                            "width": 6,
                            "offset": 0,
                            "push": 0,
                            "pull": 0,
                            "size": "md",
                            "currentWidth": 6
                          },
                          {
                            "components": [
                              {
                                "label": "Quota di massimo scavo da progetto",
                                "tooltip": "L’indicatore monitora le profondità di scavo massima raggiunta dall'attività estrattiva ",
                                "suffix": "m s.l.m.",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": true,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "max": 9999
                                },
                                "validateWhenHidden": false,
                                "key": "quotaMinimaScavo",
                                "type": "number",
                                "decimalLimit": 2,
                                "input": true
                              }
                            ],
                            "width": 6,
                            "offset": 0,
                            "push": 0,
                            "pull": 0,
                            "size": "md",
                            "currentWidth": 6
                          },
                          {
                            "components": [
                              {
                                "label": "Franco di sicurezza tra il massimo scavo e il livello batimetrico (BAS)",
                                "tooltip": "L’indicatore monitora il rispetto del franco di sicurezza tra il massimo scavo e il livello batimetrico (BAS)",
                                "suffix": "m s.l.m.",
                                "applyMaskOn": "change",
                                "tableView": true,
                                "validateWhenHidden": false,
                                "key": "bas",
                                "type": "textfield",
                                "input": true
                              }
                            ],
                            "size": "md",
                            "width": 6,
                            "currentWidth": 6
                          }
                        ],
                        "key": "columns",
                        "type": "columns",
                        "input": false,
                        "tableView": false
                      }
                    ]
                  },
                  {
                    "label": "Mobilità",
                    "key": "mobilit",
                    "components": [
                      {
                        "label": "mobilità",
                        "columns": [
                          {
                            "components": [
                              {
                                "label": "Accessibilità ai nuovi siti estrattivi",
                                "tooltip": "L'indicatore misura l'estensione lineare di nuovi assi di trasporto, su gomma e su ferro, che collegano i siti di cava alla rete infrastrutturale esistente",
                                "suffix": "Km",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "required": true,
                                  "min": 0,
                                  "max": 1000000000
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Valore massimo raggiunto",
                                "key": "accessibilitaNuoviSitiEstrattivi",
                                "type": "number",
                                "decimalLimit": 2,
                                "input": true
                              }
                            ],
                            "width": 6,
                            "offset": 0,
                            "push": 0,
                            "pull": 0,
                            "size": "md",
                            "currentWidth": 6
                          },
                          {
                            "components": [
                              {
                                "label": "Totale delle distanze percorse dai mezzi operativi",
                                "tooltip": "L’indicatore misura la distanza totale in km percorsi dai mezzi operativi per ogni sito di cava",
                                "suffix": "Km",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "min": 0,
                                  "max": 1000000000
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Valore massimo raggiunto",
                                "key": "totaleDistanzePercorseMezziOperativi",
                                "type": "number",
                                "decimalLimit": 2,
                                "input": true
                              }
                            ],
                            "width": 6,
                            "offset": 0,
                            "push": 0,
                            "pull": 0,
                            "size": "md",
                            "currentWidth": 6
                          },
                          {
                            "components": [
                              {
                                "label": "Percorrenza media del materiale prodotto",
                                "tooltip": "L’indicatore misura percorrenza media del materiale prodotto dal luogo di estrazione agli impianti di lavorazione",
                                "suffix": "Km",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "min": 0,
                                  "max": 1000000000
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Valore massimo raggiunto",
                                "key": "percorrenzaMediaMaterialeProdotto",
                                "type": "number",
                                "decimalLimit": 2,
                                "input": true
                              }
                            ],
                            "size": "md",
                            "width": 6,
                            "currentWidth": 6
                          }
                        ],
                        "key": "mobilita",
                        "type": "columns",
                        "input": false,
                        "tableView": false
                      }
                    ]
                  },
                  {
                    "label": "Natura biodiversità e ambiente",
                    "key": "naturaBiodiversitaEAmbiente",
                    "components": [
                      {
                        "label": "natura ambiente e biodiversità",
                        "columns": [
                          {
                            "components": [
                              {
                                "label": "Area sottoposta a vincolo paesaggistico ex art. 142 D.Lgs. 42/04",
                                "tooltip": "L’indicatore misura il rapporto tra l’area sottoposta a vincolo paesaggistico ex art. 142 D.Lgs. 42/04 interessata da attività estrattiva rispetto alla totale area estrattiva in quel sito. ",
                                "suffix": "%",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "min": 0,
                                  "max": 100
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Valore massimo raggiunto",
                                "key": "areaVincoloPaesaggistico142",
                                "type": "number",
                                "input": true
                              }
                            ],
                            "width": 6,
                            "offset": 0,
                            "push": 0,
                            "pull": 0,
                            "size": "md",
                            "currentWidth": 6
                          },
                          {
                            "components": [
                              {
                                "label": "Aree protette",
                                "tooltip": "L’indicatore misura il rapporto tra l’area protetta interessata da attività estrattiva rispetto alla totale area estrattiva in quel sito. ",
                                "suffix": "%",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "min": 0,
                                  "max": 100
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Valore massimo raggiunto",
                                "key": "areeProtette",
                                "type": "number",
                                "input": true
                              }
                            ],
                            "width": 6,
                            "offset": 0,
                            "push": 0,
                            "pull": 0,
                            "size": "md",
                            "currentWidth": 6
                          },
                          {
                            "components": [
                              {
                                "label": "Aree contigue",
                                "tooltip": "L’indicatore misura il rapporto tra l’area contigua interessata da attività estrattiva rispetto alla totale area estrattiva in quel sito. ",
                                "suffix": "%",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "min": 0,
                                  "max": 100
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Valore massimo raggiunto",
                                "key": "areeContigue",
                                "type": "number",
                                "input": true
                              }
                            ],
                            "size": "md",
                            "width": 6,
                            "currentWidth": 6
                          },
                          {
                            "components": [
                              {
                                "label": "Area Rete Natura 2000  ",
                                "tooltip": "L’indicatore misura il rapporto tra l’area di Rete Natura 2000 interessata da attività estrattiva rispetto alla totale area estrattiva in quel sito. ",
                                "suffix": "%",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "min": 0,
                                  "max": 100
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Valore massimo raggiunto",
                                "key": "areeReteNatura2000",
                                "type": "number",
                                "input": true
                              }
                            ],
                            "size": "md",
                            "width": 6,
                            "currentWidth": 6
                          }
                        ],
                        "key": "naturaAmbienteBio",
                        "type": "columns",
                        "input": false,
                        "tableView": false
                      },
                      {
                        "label": "Container",
                        "tableView": false,
                        "validateWhenHidden": false,
                        "key": "container",
                        "type": "container",
                        "input": true,
                        "components": [
                          {
                            "label": "Altra area",
                            "tableView": false,
                            "addAnother": "Aggiungi",
                            "validateWhenHidden": false,
                            "rowDrafts": false,
                            "key": "altraArea",
                            "type": "editgrid",
                            "displayAsTable": false,
                            "input": true,
                            "components": [
                              {
                                "label": "Columns",
                                "columns": [
                                  {
                                    "components": [
                                      {
                                        "label": "Descrizione",
                                        "applyMaskOn": "change",
                                        "tableView": true,
                                        "validateWhenHidden": false,
                                        "key": "descrizione",
                                        "type": "textfield",
                                        "input": true
                                      }
                                    ],
                                    "width": 6,
                                    "offset": 0,
                                    "push": 0,
                                    "pull": 0,
                                    "size": "md",
                                    "currentWidth": 6
                                  },
                                  {
                                    "components": [
                                      {
                                        "label": "Percentuale",
                                        "suffix": "%",
                                        "applyMaskOn": "change",
                                        "mask": false,
                                        "tableView": false,
                                        "delimiter": false,
                                        "requireDecimal": false,
                                        "inputFormat": "plain",
                                        "truncateMultipleSpaces": false,
                                        "validateWhenHidden": false,
                                        "key": "percentuale",
                                        "type": "number",
                                        "input": true
                                      }
                                    ],
                                    "width": 6,
                                    "offset": 0,
                                    "push": 0,
                                    "pull": 0,
                                    "size": "md",
                                    "currentWidth": 6
                                  }
                                ],
                                "key": "columns",
                                "type": "columns",
                                "input": false,
                                "tableView": false
                              }
                            ]
                          }
                        ]
                      }
                    ]
                  },
                  {
                    "label": "Società",
                    "key": "societa",
                    "components": [
                      {
                        "label": "Società",
                        "columns": [
                          {
                            "components": [
                              {
                                "label": "Superficie Agricola impegnata da attività estrattiva",
                                "tooltip": "L'indicatore misura l'incidenza dell’ampliamento della cava sulla superficie agricola",
                                "suffix": "%",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "min": 0,
                                  "max": 100
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Valore massimo raggiunto",
                                "key": "superficieAgricolaImpegnataAttivitEstrattiva",
                                "type": "number",
                                "input": true
                              }
                            ],
                            "width": 6,
                            "offset": 0,
                            "push": 0,
                            "pull": 0,
                            "size": "md",
                            "currentWidth": 6
                          },
                          {
                            "components": [],
                            "width": 6,
                            "offset": 0,
                            "push": 0,
                            "pull": 0,
                            "size": "md",
                            "currentWidth": 6
                          }
                        ],
                        "key": "societa1",
                        "type": "columns",
                        "input": false,
                        "tableView": false
                      }
                    ]
                  },
                  {
                    "label": "Rumore",
                    "key": "rumore",
                    "components": [
                      {
                        "label": "rumore",
                        "columns": [
                          {
                            "components": [
                              {
                                "label": "Valori di immissione sonora",
                                "tooltip": "L’indicatore misura grado di inquinamento acustico in ambiente esterno è, così come indicato dalla Legge 447/95, il livello assoluto di immissione sonora (LAeq). Tale parametro rappresenta il livello medio di rumore rilevabile",
                                "suffix": "decibel",
                                "applyMaskOn": "change",
                                "mask": false,
                                "tableView": false,
                                "delimiter": false,
                                "requireDecimal": false,
                                "inputFormat": "plain",
                                "truncateMultipleSpaces": false,
                                "validate": {
                                  "min": 0,
                                  "max": 1111111111
                                },
                                "validateWhenHidden": false,
                                "errorLabel": "Valore massimo raggiunto",
                                "key": "valoriEmissioneSonora1",
                                "type": "number",
                                "decimalLimit": 2,
                                "input": true
                              }
                            ],
                            "width": 6,
                            "offset": 0,
                            "push": 0,
                            "pull": 0,
                            "size": "md",
                            "currentWidth": 6
                          },
                          {
                            "components": [],
                            "width": 6,
                            "offset": 0,
                            "push": 0,
                            "pull": 0,
                            "size": "md",
                            "currentWidth": 6
                          }
                        ],
                        "key": "rumore1",
                        "type": "columns",
                        "input": false,
                        "tableView": false
                      }
                    ]
                  },
                  {
                    "label": "",
                    "key": "",
                    "components": []
                  }
                ],
                "key": "indicatori1",
                "type": "tabs",
                "input": false,
                "tableView": false
              }
            ],
            "collapsed": true
          }
        ]
      }
    ]
  }
}
