/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
export const IT = {
  APP: {
    NAME: 'Pubblicazione procedimenti e Osservazioni'
  },
  HEADER: {
    LOGIN: 'Accedi',
    DROPDOWN: {
      LOGOUT: 'Esci'
    }
  },
  FOOTER: {
    REGIONE: {
      SERVICE: `Un servizio a cura della regione Piemonte<br>P. Iva 123456789 - CF 1234567489`
    },
    LINK: {
      ACCESSIBILITY: 'Accessibilità',
      PRIVACY: 'Privacy',
      COOKIE: 'Cookie policy'
    }
  },
  MENU: {
    TITLE: 'Pubblicazione procedimenti e Osservazioni',
    HOME: 'Home',
    AMBIENTE: 'Ambiente',
    FORESTALE: 'Forestale',
    BOOKING: 'Prenotazioni',
    SEARCH_PRACTCE: 'Ricerca avanzata',
    OBSERVATIONS: 'Mie osservazioni',
    PRIVATE: 'Accedi per usare il servizio',
    CONTATTI: 'Contatti'
  },
  NOT_AUTHORIZED: {
    NOTIFICATION: {
      TITLE: 'Utente non autorizzato',
      TEXT: "L'utente loggato non possiede i permessi per visualizzare questa pagina"
    },
    TITLE: '401 Accesso non consentito',
    SUB_TITLE: 'Non si hanno i permessi per accedere alla pagina',
    GO_TO_HOME: 'Portami alla Home'
  },
  NOT_FOUND: {
    TITLE: '404 Pagina non trovata',
    SUB_TITLE: 'La pagina non sembra esistere',
    GO_TO_HOME: 'Portami alla Home'
  },
  UTILS: {
    NOTIFICATIONS: {
      CLOSE: 'Chiudi notifica: {{titleNotification}}'
    },
    TABLE: {
      TOTAL_ROWS: 'risultati',
      NUMBER_OF_ELEMENTS: 'elementi trovati',
      EMPTY: 'Non presenti',
      NO_ITEMS: 'Info',
      INFO_PAGE: '{{page}} di {{total}} pagine',
      SIZE_PAGE_SELECT: 'righe per pagina',
      ACTIONS: 'Azioni',
      DOWNLOAD_EXCEL: 'ESPORTA ELENCO IN FORMATO CSV'
    },
    AUTOCOMPLETE: {
      TYPE_TO_SEARCH: 'Search...',
      ITEMS_NOT_FOUND: 'Items not found',
      INSERT_MIN_CHARACTERS: 'Insert almost one character'
    },
    SELECT: {
      CLEAR_CHOICE: 'Clear'
    },
    FORM: {
      RESET: 'Pulisci',
      FILTER: 'Filtra',
      LABEL_NOT_MANDATORY: '(facoltativo)',
      MANDATORY: '*'
    },
    SEARCH: {
      NOT_EMPTY: {
        TITLE: 'Errore',
        TEXT: 'La ricerca non può essere vuota'
      }
    },
    CLEAR: 'Clear',
    SUBMIT: 'Save'
  },
  HOME: {
    TITLE: 'Home',
    MORE_BUTTON: 'Avvisi al pubblico',
    SEARCH_BUTTON: 'Procedimenti pubblicati'
  },
  SERVICE_HOME: {
    GO: 'Vai al servizio',
    TEXT: 'Puoi accedere ai singoli servizi per una <b>consultazione libera</b> oppure, <b>accedere con le tue credenziali</b> per inserire, le tue <b>osservazioni</b>.<br>Seleziona l’icona di scrittura ',
    TEXT2:
      ' visibile sui procedimenti che lo prevedono con periodo di consultazione non ancora scaduto.'
  },
  ADEMPIMENTI: {
    TITLE: 'Adempimenti'
  },
  BOOKING: {
    NEW: {
      TITLE: 'Aggiungi prenotazione',
      NOTIFICATION: {
        TITLE: 'Successo',
        TEXT: 'Prenotazione creata con successo'
      }
    },
    DETAIL: {
      NOTIFICATION: {
        ERROR: {
          TITLE: 'Prenotazione non trovate',
          TEXT: 'Non è stato possibile trovare la prenotazione cercata'
        }
      }
    },
    EDIT: {
      TITLE: 'Modifica prenotazione',
      HEADER: {
        START_DATE: 'Dal',
        END_DATE: 'Al',
        BOOKING_DATE: 'Data prenotazione',
        ID: 'Id'
      },
      NOTIFICATION: {
        TITLE: 'Successo',
        TEXT: 'Prenotazione modificata con successo'
      }
    },
    FORM: {
      CANCEL: 'Annulla',
      SAVE: 'Salva',
      FIELDS: {
        CITY: {
          LABEL: 'Città',
          PLACEHOLDER: `Inserisci città`
        },
        START_DATE: {
          LABEL: 'Data inizio',
          PLACEHOLDER: `Inserisci data inizio`
        },
        END_DATE: {
          LABEL: 'Data fine',
          PLACEHOLDER: `Inserisci data fine`
        },
        ADDRESS: {
          LABEL: 'Indirizzo',
          PLACEHOLDER: `Inserisci indirizzo`
        },
        AMOUNT: {
          LABEL: 'Totale',
          PLACEHOLDER: `Inserisci totale`
        },
        DISCOUNT_CODE: {
          LABEL: 'Codice sconto',
          PLACEHOLDER: `Inserisci codice sconto`
        },
        NOTES: {
          LABEL: 'Note',
          PLACEHOLDER: `Inserisci note`
        }
      }
    },
    LIST: {
      TAB_TITLE: 'Lista',
      TITLE: 'Lista prenotazioni',
      TOTAL_ELEMENTS: ' prenotazioni',
      EXPORT: 'Esporta',
      ADD_BUTTON: 'Aggiungi Prenotazione',
      FIELDS: {
        START_DATE: 'Dal',
        END_DATE: 'Al',
        BOOKING_DATE: 'Data prenotazione',
        ID: 'Id',
        TYPE: 'Tipo',
        ADDRESS: 'Indirizzo',
        CITY: 'Città',
        PRICE: 'Prezzo',
        USER_FULLNAME: 'Utente',
        COMPANY: 'Azienda',
        AMOUNT: 'Totale',
        DISCOUNT: 'Sconto',
        DISCOUNT_CODE: 'Codice sconto',
        NOTES: 'Note'
      },
      FILTERS: {
        DATE_INTERVALL: {
          LABEL: 'Periodo',
          PLACEHOLDER: 'Seleziona intervallo date'
        },
        USER: {
          LABEL: 'Utente',
          PLACEHOLDER: `Inserisci utente`
        },
        COMPANY: {
          LABEL: 'Azienda',
          PLACEHOLDER: `Inserisci azienda`
        },
        TYPE: {
          LABEL: 'Tipo',
          PLACEHOLDER: `Inserisci tipo`
        },
        CITY: {
          LABEL: 'Città',
          PLACEHOLDER: `Inserisci città`
        },
        PRICE: {
          LABEL: 'Prezzo',
          PLACEHOLDER: `Inserisci prezzo`
        }
      },
      MODAL_DETAIL_TITLE: 'Dettaglio prenotazione',
      DROPDOWN: {
        EDIT: 'Modifica',
        DELETE: 'Elimina'
      }
    },
    DELETE: {
      TITLE: 'Elimina prenotazione',
      BODY: 'Sei sicuro di voler eliminare la prenotazione con id = {{id}}',
      FOOTER: {
        CANCEL: 'No',
        CONFIRM: 'Si, cancella prenotazione'
      },
      NOTIFICATION: {
        TITLE: 'Prenotazione eliminata',
        TEXT: 'Prenotazione eliminata con successo. Grazie'
      }
    }
  },
  PRACTICE: {
    LIST: {
      TITLE: 'Procedimenti pubblicati',
      INFO_OSSERVAZIONI:
        'Per inserire o modificare le proprie Osservazioni è necessario ',
      LOGIN: 'accedere al servizio',
      DROPDOWN: {
        VIEW: 'Dettaglio procedimento',
        ADD_OSSERVAZIONE: 'Inserisci osservazione'
      },
      FIELDS: {
        ADEMPIMENTO: 'Adempimento',
        COD_ADEMPIMENTO: 'Codice Adempimento',
        AUTORITA_COMP: 'Autorità Competente',
        CODICE_PRATICA: 'Codice Procedimento',
        DENOMINAZIONE: 'Denominazione',
        LOCALIZZAZIONE: 'Localizzazione',
        STATO_PRATICA: 'Stato Procedimento',
        SCAD_OSSERVAZIONI: 'Scadenza Osservazioni',
        DATA_PROVV_CONCLUSO: 'Data Conclusione Provvedimento'
      },
      BUTTONS: {
        IN_CORSO: 'Procedimenti IN CORSO',
        IN_CONSULTAZIONE: 'Procedimenti IN CONSULTAZIONE',
        CONCLUSI: 'Procedimenti CONCLUSI',
        TUTTE: 'Tutti'
      },
      ICONS: {
        PAPER: 'Dettaglio Procedimento',
        NEW_REMARK: 'Inserisci Osservazione',
        VIEW_REMARK: 'Dettaglio Osservazione',
        EDIT_REMARK: 'Modifica Osservazione',
        DELETE_REMARK: 'Elimina Osservazione'
      }
    },
    DETAIL: {
      TITLE: 'Dettaglio procedimento',
      RECAP: { TITLE: 'Informazioni Generali' },
      MAP: {
        ERROR: {
          TITLE: 'Mappa geografica progetto',
          TEXT: 'Si è verificato un errore lato server, riprova più tardi'
        },
        BREADCRUMB: 'Mappa',
        TITLE: 'Mappa geografica progetto',
        TEXT: 'Visualizza mappa progetto',
        OVERLAY_TREE: {
          TITLE: 'Servizi'
        }
      },
      HELP: {
        HEADER: 'HELP',
        HOME: 'Portale di pubblicazione procedimenti e osservazioni'
      },
      CAPTCHA: {
        HEADER: 'Download File',
        ERROR: {
          TITLE: 'Errore',
          TEXT: 'Attenzione il codice inserito non corrisponde al codice visualizzato'
        }
      },
      DOCUMENTI: {
        TITLE: 'Sezione documenti',
        BUTTON: {
          EXPORT_CSV: 'Esporta elenco in formato CSV',
          DOWNLOAD: 'Download',
          DOWNLOAD_ALL: 'Scarico elenco completo',
          DOWNLOAD_SELECTED: 'Scarica documenti selezionati',
          ZIP: 'Download ZIP',
          HELP: 'Aiuto'
        },
        EXPORT: {
          CSV: {
            ERROR: {
              TITLE: 'Errore',
              TEXT: "Non è stato possibile eseguire l'export dei file in formato CSV"
            },
            NO_SELECTION: {
              TITLE: 'Errore',
              TEXT: 'Seleziona almeno un file prima di procedere al download'
            }
          },
          FILE: {
            ERROR: {
              TITLE: 'Errore',
              TEXT: "Non è stato possibile eseguire l'export del file scelto"
            }
          },
          ALL: {
            ERROR: {
              TITLE: 'Errore',
              TEXT: "Non è stato possibile eseguire l'export di tutti i file"
            }
          }
        },
        FIELDS: {
          CLASSIFICAZIONE_DOC: 'Classificazione documento',
          NOME_FILE: 'Documento',
          NOME_FILE_CORRELATO: 'Documento correlato',
          CATEGORIA_ALLEGATO: 'Categoria documento',
          TIPOLOGIA_ALLEGATO: 'Tipologia documento',
          COD_ELABORATO: 'Codice elaborato',
          DATA_UPLOAD: 'Data caricamento',
          DATA_PUB: 'Data pubblicazione',
          DIMENSIONE: 'Dimensione',
          SELEZIONA_TUTTI: 'Seleziona Tutti',
          DIMENSIONE_MB: 'Dimensione in MB'
        }
      },
      FIELDS: {
        COD_PRATICA: 'Codice Procedimento',
        IDENTIFICATIVO: 'Identificativo istanza',
        AUTORITA_COMP: 'Autorità competente',
        OTHER_AUTORITA_COMP: 'Altra Autorità competente',
        AUTORITA_STATALE_COMP: 'Autorità competente procedimento statale',
        OTHER_AUTORITA_STATALE_COMP: 'Altra Autorità competente procedimento statale',
        INF_STRATEGICA: 'Infrastruttura strategica (ex legge 443/2001)',
        ORARI: 'Orari',
        SITO_WEB: 'Sito WEB',
        PEC: 'Posta Elettronica Certificata',
        PROGETTO: 'Progetto',
        STATO: 'Stato',
        CATEGORIA_PROG: 'Categoria progettuale',
        VALUTAZIONE_INC: 'Valutazione incidenza',
        LOCALIZZAZIONE: 'Localizzazione',
        DESCR: 'Descrizione',
        PROPONENTE: 'Proponente',
        DATA_PRES_PROG: 'Data presentazione progetto',
        DATA_PUBBL_AVVISO: 'Data avvio consultazione pubblica',
        UNITA_ORGANIZZATIVA:
          "Unità organizzativa responsabile dell'istruttoria",
        RESP_PROCEDIMENTO: 'Responsabile del procedimento',
        DATA_SCAD_OSS: 'Data scadenza invio osservazioni',
        NO_DATA: {
          ORARI: 'Nessun orario disponibile',
          SITO_WEB: 'Nessun sito web disponibile',
          PEC: 'Nessun indirizzo PEC disponibile'
        }
      },
      ERROR: {
        TITLE: 'Procedimento non trovato',
        TEXT: 'Si è verificato un errore lato server, riprova più tardi'
      }
    },
    SEARCH: {
      LIST_TITLE: 'Elenco procedimenti',
      TITLE: 'Ricerca avanzata',
      RESULT: 'risultati di ricerca',
      FIELDS: {
        AMBITO: 'Argomento',
        TIPO_PROCEDIMENTO: 'Ambito Procedimento',
        TIPO_PROCEDURA: 'Tipo procedimento',
        AUTORITA_COMP: 'Autorità competente',
        ANNO_PRES: 'Anno di presentazione del progetto',
        COD_PRATICA: 'Codice procedimento',
        TITOLO_PROG: 'Titolo progetto (Inserire la parola chiave)',
        LOCALIZZAZIONE: 'Locallizzazione dell opera Intervento',
        PROVINCIA: 'Provincia',
        COMUNE: 'Comune',
        STATO_PROC: 'Stato del procedimento',
        INFRASTRUTTURA: 'Infrastruttura strategica (ex legge 443/2001)',
        VAL_INCIDENZA: 'Valutazione incidenza',
        PROGETTO_PNRR:
          'Progetto PNRR (Piano Nazionale di Ripresa e Resilienza)',
        CAT_OPERA: "Categoria d'opera",
        ANNO: 'Anno',
        MAGGIORE: 'Maggiore di',
        MINORE: 'Minore di',
        DENOMINAZIONE: 'Denominazione',
        STATO_PROCEDIMENTO_STATALE: 'Stato procedimento statale',
        DES_ESITO_PROCEDIMENTO: 'Stato procedimento statale',
        PLACEHOLDER: {
          AMBITO: 'Argomento',
          TIPO_PROCEDIMENTO: 'Ambito Procedimento',
          TIPO_PROCEDURA: 'Tipo procedimento',
          AUTORITA_COMP: 'Autorità competente',
          COD_PRATICA: 'Codice procedimento',
          TITOLO_PROG: 'Titolo progetto (Inserire la parola chiave)',
          LOCALIZZAZIONE: 'Locallizzazione dell opera Intervento',
          PROVINCIA: 'Provincia',
          COMUNE: 'Comune',
          STATO_PROC: 'Stato del procedimento',
          INFRASTRUTTURA: 'Infrastruttura strategica (ex legge 443/2001)',
          VAL_INCIDENZA: 'Valutazione incidenza',
          PROGETTO_PNRR:
            'Progetto PNRR(Plesso nazionale di ripresa e resilienza)',
          CAT_OPERA: "Categoria d'opera",
          ANNO: 'Anno di presentazione del progetto',
          DENOMINAZIONE: 'Denominazione',
          STATO_PROCEDIMENTO_STATALE: 'Stato procedimento statale',
          DES_ESITO_PROCEDIMENTO: 'Stato procedimento statale'
        },
        NO_VALUE: {
          AMBITO: 'Seleziona ambito',
          TIPO_PROCEDIMENTO: 'Seleziona tipo Procedimento',
          TIPO_PROCEDURA: 'Seleziona tipo procedimento',
          AUTORITA_COMP: 'Seleziona autorità competente',
          COD_PRATICA: 'Seleziona codice procedimento',
          ARGOMENTO: 'Seleziona Argomento',
          TITOLO_PROG: 'Titolo progetto (Inserire la parola chiave)',
          LOCALIZZAZIONE: "Localizzazione dell'opera Intervento",
          PROVINCIA: 'Seleziona provincia',
          COMUNE: 'Seleziona comune',
          STATO_PROC: 'Seleziona stato del procedimento',
          INFRASTRUTTURA: 'Infrastruttura strategica (ex legge 443/2001)',
          VAL_INCIDENZA: 'Valutazione incidenza',
          PROGETTO_PNRR:
            'Progetto PNRR(Plesso nazionale di ripresa e resilienza)',
          STATO_PROCEDIMENTO_STATALE: 'Seleziona Stato procedimento statale',
          CAT_OPERA: "Seleziona categoria d'opera",
          ANNO: 'Anno di presentazione del progetto',
          DENOMINAZIONE: 'Denominazione'
        }
      },
      BUTTONS: {
        SEARCH: 'Ricerca Procedimento',
        CLEAR: 'Annulla'
      }
    },
    PROCEDURE: {
      TITLE: 'Sezione istruttoria',
      STATUS_COUNTRY: 'Stato del procedimento statale',
      RESULT_COUNTRY: 'Esito del procedimento statale',
      DOC: 'Documentazione',
      DOC_TEXT:
        'La documentazione del procedimento (elaborati progettuali, osservazioni..) è consultabile presso il sito del Ministero',
      ENDING: 'Termine del procedimento',
      STATUS: 'Stato del procedimento',
      RESULT: 'Esito',
      PROVV_CONCLUSO: 'Data conclusione provvedimento'
    },
    NOTES: {
      TITLE: 'Riepilogo Note',
      TEXT: 'Note',
      DATE: 'Data nota'
    },
    FINAL_ACTS: {
      NAME: 'Provvedimento conclusivo',
      TITLE: 'Titolo atto',
      NUMBER: 'Numero atto',
      DATE: 'Data atto'
    },
    RIF_PROCEDIMENTO: {
      TITLE: 'Riferimenti Procedimento',
      TIPOLOGIA: 'Tipologia',
      DENOMINAZIONE: 'Denominazione',
      RECAPITO: 'Recapito'
    },
    RETE_NATURA_2000: {
      TITLE: 'Siti Rete Natura 2000',
      COD: 'Codice',
      DEN: 'Denominazione',
      TIPO: 'Tipo Sito',
      ENTE: 'Ente Gestore'
    },
    AREE_PROTETTE: {
      TITLE: 'Aree naturali protette',
      COD: 'Codice',
      DEN: 'Denominazione',
      TIPO: 'Tipo area protetta',
      ENTE: 'Ente Gestore'
    }
  },
  OBSERVATIONS: {
    TITLE: 'Mie Osservazioni',
    TABLE: {
      ADEMP: 'Adempimento',
      CODE: 'Codice Procedimento',
      NAME: 'Denominazione',
      STATUS: 'Stato',
      INSERT_DATE: 'Data inserimento',
      PUBLISH_DATE: 'Data pubblicazione'
    },
    BUTTONS: {
      TUTTE: 'Tutti',
      BOZZA: 'Bozza',
      INVIATE: 'Inviate',
      PUBBLICATE: 'Pubblicate'
    }
  },
  AVVISI: {
    LIST: {
      TITLE: 'Avvisi al pubblico',
      FIELDS: {
        ADEMPIMENTO: 'Adempimento',
        DESCRIZIONE: 'Descrizione progetto',
        DATA_PUBBLICAZIONE: 'Data Pubblicazione',
        SCAD_OSSERVAZIONI: 'Scadenza Osservazioni'
      }
    }
  },
  DOCUMENTI: {
    BUTTON: {
      EXPORT_CSV: 'Esporta elenco in formato CSV',
      DOWNLOAD: 'Download PDF',
      DOWNLOAD_ALL: 'Scarica documenti'
    }
  },
  OSSERVAZIONI: {
    BREADCRUMB: 'Osservazioni',
    TITLE: 'Mie Osservazioni',
    TITLE_INSERT: 'Inserisci osservazioni con relativi allegati',
    FIELDS: {
      TITLE: 'Presentazione di osservazioni relative al procedimento di :',
      AUTORITA_COMP_PRINC: 'Autorità competente Principale',
      STATO_PRATICA: 'Stato procedimento',
      SCAD_OSS: 'Scadenza osservazioni',
      DENOM_PROG: 'Progetto denominato',
      LOCALIZZAZIONE: 'Localizzato nei comuni',
      CATEGORIA_ALLEGATO: 'Categoria allegato',
      TIPOLOGIA_ALLEGATO: 'Tipologia allegato',
      FLG_PUBBLICO: 'Questo documento non sarà pubblicato',
      SELECT_PLACEHOLDER: 'Seleziona',
      NOTE: 'Note (max 500 caratteri)',
      MB: 'Mb'
    },
    ICONS: {
      DELETE: 'Elimina allegato'
    },
    BUTTONS: {
      DOWNLOAD_OSS: 'Scarica Modulo',
      DOWNLOAD_PRIVACY: 'Scarica privacy',
      CARICA_OSS: 'Carica Documento',
      ANNULLA: 'Annulla',
      BACK: 'Indietro',
      INVIA_OSS: 'Invia Osservazione'
    },
    RIEPILOGO: {
      TITLE: 'Riepilogo',
      DICHIARAZIONE:
        'Il/La Sottoscritto/a dichiara di essere consapevole che,' +
        'ai sensi del d.lgs. 152/2006, le presenti osservazioni e gli eventuali allegati tecnici' +
        'saranno pubblicati sul sito WEB dell’Autorità Competente all’indirizzo ' +
        'www.regione.piemonte / scrivaconsweb.... ',
      FIELDS: {
        NOME_DOC: 'Documento',
        DATA_DOC: 'Data',
        DIMENSIONE: 'Dimensione'
      }
    },
    NOTIFICATION: {
      SUCCESS: {
        EDIT: {
          TITLE: 'Invio osservazione',
          TEXT: 'Osservazione inviata con successo'
        },
        DELETE: {
          TITLE: 'Elimina osservazione',
          TEXT: 'L’osservazione è stata eliminata correttamente'
        }
      },
      ERROR: {
        TRACK: {
          TITLE: 'Tracciamento osservazione',
          TEXT: 'Si è verificato un errore lato server, riprova più tardi'
        },
        CREATE: {
          TITLE: 'Inserimento osservazione',
          TEXT: 'Si è verificato un errore lato server, riprova più tardi'
        },
        EDIT: {
          TITLE: 'Invio osservazione',
          TEXT: 'Si è verificato un errore lato server, riprova più tardi'
        },
        DELETE: {
          TITLE: 'Elimina osservazione',
          TEXT: 'Si è verificato un errore lato server, riprova più tardi'
        },
        ALLEGATI: {
          TITLE: 'Inserimento allegati',
          TEXT: "La tipologia allegato di tipo 'Modulo osservazioni del cittadino' è obbligatoria",
          REQUIRED:
            'Attenzione! Per il corretto invio dell’osservazione devono essere allegati i documenti appartenenti alle tipologie obbligatorie',
          FILENAMING:
            'Attenzione: è stato riscontrato un problema nella nomenclatura del documento. Ti chiediamo gentilmente di rinominarlo e di ricaricarlo'
        }
      }
    },
    ALLEGATI: {
      NOTIFICATION: {
        ERROR: {
          DIMENSIONE: {
            TITLE: 'Caricamento allegato',
            TEXT: 'La dimensione o il formato del file allegato non sono supportati'
          },
          UPLOAD: {
            TITLE: 'Caricamento allegato',
            TEXT: 'Si è verificato un errore lato server, riprova più tardi'
          },
          DELETE: {
            TITLE: 'Elimina allegato',
            TEXT: 'Si è verificato un errore lato server, riprova più tardi'
          },
          DOWNLOAD: {
            TITLE: 'Download documento',
            TEXT: 'Si è verificato un errore lato server, riprova più tardi'
          }
        }
      }
    }
  }
};
