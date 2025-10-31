/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import * as moment from 'moment';
import { Allegato } from '../../../../features/ambito/models';
import { CommonConsts } from '../../../consts/common-consts.consts';
import { CRATableConfigs } from './card-riepilogo-allegati.classes';
import { ICRATableColumn } from './card-riepilogo-allegati.interfaces';

/**
 * String con la definizione del prefisso INDEX che definisce un uuid di un allegato come non associato correttamente al proprio file binario.
 */
export const UUID_INDEX_BIN_PREFIX = 'UUID-INDEX-';

/**
 * Classe contenente le costanti per il componente omonimo.
 */
export class CardRiepilogoAllegatiConsts {
  /** CommonConsts con le costanti comuni all'applicazione. */
  C_C = new CommonConsts();

  /** boolean che definisce se le configurazioni devono essere modificate sulla base della modalità applicazione: FO. */
  private isFrontOffice: boolean = false;

  /** CRATableConfigs con la configurazione per la colonna omonima. */
  CLASSIFICAZIONE_DOC: CRATableConfigs;
  /** CRATableConfigs con la configurazione per la colonna omonima. */
  DOCUMENTO: CRATableConfigs;
  /** CRATableConfigs con la configurazione per la colonna omonima. */
  DOCUMENTO_CORRELATO: CRATableConfigs;
  /** CRATableConfigs con la configurazione per la colonna omonima. */
  IN_PUBBLICAZIONE: CRATableConfigs;
  /** CRATableConfigs con la configurazione per la colonna omonima. */
  CATEGORIA_ALLEGATO: CRATableConfigs;
  /** CRATableConfigs con la configurazione per la colonna omonima. */
  TIPOLOGIA_ALLEGATO: CRATableConfigs;
  /** CRATableConfigs con la configurazione per la colonna omonima. */
  CODICE_ELABORATO: CRATableConfigs;
  /** CRATableConfigs con la configurazione per la colonna omonima. */
  DATA_CARICAMENTO: CRATableConfigs;
  /** CRATableConfigs con la configurazione per la colonna omonima. */
  DATA_PUBBLICAZIONE: CRATableConfigs;
  /** CRATableConfigs con la configurazione per la colonna omonima. */
  DIMENSIONE: CRATableConfigs;
  /** CRATableConfigs con la configurazione per la colonna omonima. */
  ACCESSO: CRATableConfigs;
  /** CRATableConfigs con la configurazione per la colonna omonima. */
  AZIONI: CRATableConfigs;

  /** ICRATableColumn con la configurazione per la gestione della colonna del csv degli allegati. */
  CSV_CLASSIFICAZIONE_DOC: ICRATableColumn;
  /** ICRATableColumn con la configurazione per la gestione della colonna del csv degli allegati. */
  CSV_DOCUMENTO: ICRATableColumn;
  /** ICRATableColumn con la configurazione per la gestione della colonna del csv degli allegati. */
  CSV_DOCUMENTO_CORRELATO: ICRATableColumn;
  /** ICRATableColumn con la configurazione per la gestione della colonna del csv degli allegati. */
  CSV_CATEGORIA_ALLEGATO: ICRATableColumn;
  /** ICRATableColumn con la configurazione per la gestione della colonna del csv degli allegati. */
  CSV_TIPOLOGIA_ALLEGATO: ICRATableColumn;
  /** ICRATableColumn con la configurazione per la gestione della colonna del csv degli allegati. */
  CSV_CODICE_ELABORATO: ICRATableColumn;
  /** ICRATableColumn con la configurazione per la gestione della colonna del csv degli allegati. */
  CSV_DATA_CARICAMENTO: ICRATableColumn;
  /** ICRATableColumn con la configurazione per la gestione della colonna del csv degli allegati. */
  CSV_DATA_PUBBLICAZIONE: ICRATableColumn;
  /** ICRATableColumn con la configurazione per la gestione della colonna del csv degli allegati. */
  CSV_DIMENSIONE_UPLOAD: ICRATableColumn;
  /** ICRATableColumn con la configurazione per la gestione della colonna del csv degli allegati. */
  CSV_FLG_RISERVATO: ICRATableColumn;
  /** ICRATableColumn con la configurazione per la gestione della colonna del csv degli allegati. */
  CSV_IN_PUBBLICAZIONE: ICRATableColumn;
  /** ICRATableColumn con la configurazione per la gestione della colonna del csv degli allegati. */
  CSV_DATA_PROTOCOLLO: ICRATableColumn;
  /** ICRATableColumn con la configurazione per la gestione della colonna del csv degli allegati. */
  CSV_NUM_PROTOCOLLLO: ICRATableColumn;
  /** ICRATableColumn con la configurazione per la gestione della colonna del csv degli allegati. */
  CSV_DATA_ATTO: ICRATableColumn;
  /** ICRATableColumn con la configurazione per la gestione della colonna del csv degli allegati. */
  CSV_NUMERO_ATTO: ICRATableColumn;
  /** ICRATableColumn con la configurazione per la gestione della colonna del csv degli allegati. */
  CSV_TITOLO_ALLEGATO: ICRATableColumn;
  /** ICRATableColumn con la configurazione per la gestione della colonna del csv degli allegati. */
  CSV_AUTORE_ALLEGATO: ICRATableColumn;
  /** ICRATableColumn con la configurazione per la gestione della colonna del csv degli allegati. */
  CSV_NOTE: ICRATableColumn;
  /** ICRATableColumn con la configurazione per la gestione della colonna del csv degli allegati. */
  CSV_DATA_INTEGRAZIONE: ICRATableColumn;
  /** ICRATableColumn con la configurazione per la gestione della colonna del csv degli allegati. */
  CSV_TIPO_INTEGRAZIONE: ICRATableColumn;
  /** ICRATableColumn con la configurazione per la gestione della colonna del csv degli allegati. */
  CSV_DATA_CANCELLAZIONE: ICRATableColumn;

  /** string con la label identificativa. */
  LABEL_CLASSIFICAZIONE_DOC: string = 'Classificazione documento';
  /** string con la label identificativa. */
  LABEL_DOCUMENTO: string = 'Documento';
  /** string con la label identificativa. */
  LABEL_NOME_DOCUMENTO: string = 'Nome documento';
  /** string con la label identificativa. */
  LABEL_CATEGORIATIPOLOGIA: string = 'Categoria/Tipologia';
  /** string con la label identificativa. */
  LABEL_ACCESSO: string = 'Accesso';
  /** string con la label identificativa. */
  LABEL_CODICE_ELABORATO: string = 'Codice elaborato';
  /** string con la label identificativa. */
  LABEL_DATA_CARICAMENTO: string = 'Data caricamento';
  /** string con la label identificativa. */
  LABEL_DATA_PUBBLICAZIONE: string = 'Data pubblicazione';
  /** string con la label identificativa. */
  LABEL_DIMENSIONE: string = 'Dimensione';
  /** string con la label identificativa. */
  LABEL_DIMENSIONE_MB: string = 'Dimensione in MB';
  /** string con la label identificativa. */
  LABEL_AZIONI: string = 'Azioni';
  /** string con la label identificativa. */
  LABEL_TIPOLOGIA_ALLEGATO: string = 'Tipologia documento';
  /** string con la label identificativa. */
  LABEL_CATEGORIA_ALLEGATO: string = 'Categoria documento';
  /** string con la label identificativa. */
  LABEL_DOCUMENTO_CORRELATO: string = 'Documento correlato';
  /** string con la label identificativa. */
  LABEL_IN_PUBBLICAZIONE: string = 'In pubblicazione';
  /** string con la label identificativa. */
  LABEL_DATA_PROTOCOLLO: string = 'Data protocollo';
  /** string con la label identificativa. */
  LABEL_NUM_PROTOCOLLLO: string = 'Numero protocollo';
  /** string con la label identificativa. */
  LABEL_DATA_ATTO: string = 'Data atto';
  /** string con la label identificativa. */
  LABEL_NUMERO_ATTO: string = 'Numero atto';
  /** string con la label identificativa. */
  LABEL_TITOLO_ALLEGATO: string = 'Titolo';
  /** string con la label identificativa. */
  LABEL_AUTORE_ALLEGATO: string = 'Autore';
  /** string con la label identificativa. */
  LABEL_NOTE: string = 'Note';
  /** string con la label identificativa. */
  LABEL_DATA_INTEGRAZIONE: string = 'Data perfezionamento/integrazione';
  /** string con la label identificativa. */
  LABEL_TIPO_INTEGRAZIONE: string = 'Tipo integrazione';
  /** string con la label identificativa. */
  LABEL_DATA_CANCCELLAZIONE: string = 'Data cancellazione';

  /** string con il formato per la gestione della data. */
  DATE_COMPLETE_VIEW_FORMAT = 'dd/MM/yyyy HH:mm:ss';
  /** string con il formato per la gestione della data. */
  DATE_VIEW_FORMAT = 'dd/MM/yyyy';
  /** string con il formato per la gestione dell'ora'. */
  HOURS_VIEW_FORMAT = 'HH:mm:ss';
  /** string con il farmato della data per la gestione del csv. */
  FULL_DATE_CSV_FORMAT = 'DD/MM/YYYY - HH:mm:ss';

  /**
   * Costruttore.
   */
  constructor(isFrontOffice: boolean = false) {
    // Definisco le variabili all'interno del componente
    this.isFrontOffice = isFrontOffice;

    // Lancio la funzione di setup per le configurazioni della tabella allegati
    this.setupConfigurazioniTabellaAllegati(isFrontOffice);
    // Lancio la funzione di setup per la configurazione della tabella allegati csv
    this.setupConfigurazioniTabellaAellagtiCsv();
  }

  /**
   * Funzione di setup per le varie configurazioni per la tabella degli allegati.
   * @param isFrontOffice boolean che definisce se le configurazioni devono tenere conto della modalità FO dell'applicazione.
   */
  private setupConfigurazioniTabellaAllegati(isFrontOffice: boolean = false) {
    // Inizializzo le configurazioni
    this.CLASSIFICAZIONE_DOC = new CRATableConfigs({
      columnLabel: this.LABEL_CLASSIFICAZIONE_DOC,
      colWidth: 200,
    });

    this.DOCUMENTO = new CRATableConfigs({
      columnLabel: this.LABEL_DOCUMENTO,
      colWidth: 175,
    });

    this.DOCUMENTO_CORRELATO = new CRATableConfigs({
      columnLabel: this.LABEL_DOCUMENTO_CORRELATO,
      colWidth: isFrontOffice ? 300 : 175,
    });

    this.IN_PUBBLICAZIONE = new CRATableConfigs({
      columnLabel: this.LABEL_IN_PUBBLICAZIONE,
      colWidth: 135,
    });

    this.CATEGORIA_ALLEGATO = new CRATableConfigs({
      columnLabel: this.LABEL_CATEGORIA_ALLEGATO,
      colWidth: 200,
    });

    this.TIPOLOGIA_ALLEGATO = new CRATableConfigs({
      columnLabel: this.LABEL_TIPOLOGIA_ALLEGATO,
      colWidth: 200,
    });

    this.CODICE_ELABORATO = new CRATableConfigs({
      columnLabel: this.LABEL_CODICE_ELABORATO,
      colWidth: 200,
    });

    this.DATA_CARICAMENTO = new CRATableConfigs({
      columnLabel: this.LABEL_DATA_CARICAMENTO,
      colWidth: 200,
    });

    this.DATA_PUBBLICAZIONE = new CRATableConfigs({
      columnLabel: this.LABEL_DATA_PUBBLICAZIONE,
      colWidth: 120,
    });

    this.DIMENSIONE = new CRATableConfigs({
      columnLabel: this.LABEL_DIMENSIONE,
      colWidth: 200,
    });

    this.ACCESSO = new CRATableConfigs({
      columnLabel: this.LABEL_ACCESSO,
      colWidth: 95,
    });

    this.AZIONI = new CRATableConfigs({
      columnLabel: this.LABEL_AZIONI,
      colWidth: 160,
    });
  }

  /**
   * Funzione di setup per le varie configurazioni per la tabella degli allegati con stampa csv.
   */
  private setupConfigurazioniTabellaAellagtiCsv() {
    // Definisco le configurazioni per la gestione delle colonne
    this.CSV_CLASSIFICAZIONE_DOC = {
      prop: 'classe_allegato.des_classe_allegato',
      name: this.LABEL_CLASSIFICAZIONE_DOC,
      getter: {
        transform: (value: Allegato) =>
          value.classe_allegato?.des_classe_allegato
            ? value.classe_allegato.des_classe_allegato
            : '',
      },
    };

    this.CSV_DOCUMENTO = {
      prop: 'nome_doc',
      name: this.LABEL_DOCUMENTO,
      // getter: {
      //   transform: (value: Allegato) =>
      //     !value?.id_allegato_istanza_padre
      //       ? value.nome_allegato
      //       : this.getNomeAllegato(value.id_allegato_istanza_padre),
      // },
    };

    this.CSV_DOCUMENTO_CORRELATO = {
      prop: 'nome_allegato',
      name: this.LABEL_DOCUMENTO_CORRELATO,
      getter: {
        transform: (value: Allegato) =>
          !value?.id_allegato_istanza_padre ? '' : value.nome_allegato,
      },
    };

    this.CSV_CATEGORIA_ALLEGATO = {
      prop: 'tipologia_allegato.categoria_allegato.des_categoria_allegato',
      name: this.LABEL_CATEGORIA_ALLEGATO,
      getter: {
        transform: (value: Allegato) =>
          value?.tipologia_allegato?.categoria_allegato?.des_categoria_allegato
            ? value.tipologia_allegato.categoria_allegato.des_categoria_allegato
            : '',
      },
    };

    this.CSV_TIPOLOGIA_ALLEGATO = {
      prop: 'tipologia_allegato.des_tipologia_allegato',
      name: this.LABEL_TIPOLOGIA_ALLEGATO,
      getter: {
        transform: (value: Allegato) =>
          value.tipologia_allegato?.des_tipologia_allegato
            ? value.tipologia_allegato.des_tipologia_allegato
            : '',
      },
    };

    this.CSV_CODICE_ELABORATO = {
      prop: 'cod_allegato',
      name: this.LABEL_CODICE_ELABORATO,
    };

    this.CSV_DATA_CARICAMENTO = {
      prop: 'data_upload',
      name: this.LABEL_DATA_CARICAMENTO,
      formatDate: {
        transform: (value: Allegato) => this.csvMoment(value?.data_upload),
      },
    };

    this.CSV_DATA_PUBBLICAZIONE = {
      prop: 'data_pubblicazione',
      name: this.LABEL_DATA_PUBBLICAZIONE,
      formatDate: {
        transform: (value: Allegato) =>
          this.csvMoment(value?.data_pubblicazione),
      },
    };

    this.CSV_DIMENSIONE_UPLOAD = {
      prop: 'dimensione_upload',
      name: this.LABEL_DIMENSIONE_MB,
      getter: {
        transform: (value: Allegato) =>
          value?.dimensione_upload
            ? this.byteToMegabyte(value.dimensione_upload)
            : '',
      },
    };

    this.CSV_FLG_RISERVATO = {
      prop: 'flg_riservato',
      name: this.LABEL_ACCESSO,
      pipe: {
        transform: (value: any) => (value ? 'Riservato' : 'Pubblico'),
      },
    };

    this.CSV_IN_PUBBLICAZIONE = {
      prop: 'flg_da_pubblicare',
      name: this.LABEL_IN_PUBBLICAZIONE,
      pipe: {
        transform: (value: any) => (value ? 'X' : ''),
      },
    };

    this.CSV_DATA_PROTOCOLLO = {
      prop: 'data_protocollo_allegato',
      name: this.LABEL_DATA_PROTOCOLLO,
      formatDate: {
        transform: (value: Allegato) =>
          this.csvMoment(value?.data_protocollo_allegato),
      },
    };

    this.CSV_NUM_PROTOCOLLLO = {
      prop: 'num_protocollo_allegato',
      name: this.LABEL_NUM_PROTOCOLLLO,
    };

    this.CSV_DATA_ATTO = {
      prop: 'data_atto',
      name: this.LABEL_DATA_ATTO,
      formatDate: {
        transform: (value: Allegato) => this.csvMoment(value?.data_atto),
      },
    };

    this.CSV_NUMERO_ATTO = {
      prop: 'num_atto',
      name: this.LABEL_NUMERO_ATTO,
    };

    this.CSV_TITOLO_ALLEGATO = {
      prop: 'titolo_allegato',
      name: this.LABEL_TITOLO_ALLEGATO,
    };

    this.CSV_AUTORE_ALLEGATO = {
      prop: 'autore_allegato',
      name: this.LABEL_AUTORE_ALLEGATO,
    };

    this.CSV_NOTE = {
      prop: 'note',
      name: this.LABEL_NOTE,
      getter: {
        transform: (value: Allegato) =>
          value?.note ? value.note.replace(/\n/g, ' ') : '',
      },
    };

    this.CSV_DATA_INTEGRAZIONE = {
      prop: 'data_integrazione',
      name: this.LABEL_DATA_INTEGRAZIONE,
      formatDate: {
        transform: (value: Allegato) =>
          this.csvMoment(value?.data_integrazione),
      },
    };

    this.CSV_TIPO_INTEGRAZIONE = {
      prop: 'tipo_integra_allegato.des_tipo_integra_allegato',
      name: this.LABEL_TIPO_INTEGRAZIONE,
      getter: {
        transform: (value: Allegato) =>
          value.tipo_integra_allegato?.des_tipo_integra_allegato
            ? value.tipo_integra_allegato.des_tipo_integra_allegato
            : '',
      },
    };

    this.CSV_DATA_CANCELLAZIONE = {
      prop: 'data_cancellazione',
      name: this.LABEL_DATA_CANCCELLAZIONE,
      formatDate: {
        transform: (value: Allegato) =>
          this.csvMoment(value?.data_cancellazione),
      },
    };
  }

  /**
   * Funzione di comodo che aggiorna le configurazioni dipendenti dallo stato applicativo: FO.
   * @param isFrontOffice boolean con il flag che indica se l'applicazione è in modalità FO.
   */
  updateIsFOConfigs(isFrontOffice: boolean = false) {
    // Aggiorno localmente la variabile
    this.isFrontOffice = isFrontOffice;

    // Rilancio il setup delle configurazioni per la gestione della pagina
    this.setupConfigurazioniTabellaAllegati(isFrontOffice);
  }

  /**
   * ####################
   * FUNZIONI DI SUPPORTO
   * ####################
   */

  /**
   * Funzione che va a calcolare la dimensione di un file.
   * @param byte dimensione file in byte
   * @returns Questa funzione converte il numero di byte in megabyte dividendo per 1024^2 (il numero di byte in un megabyte).
   */
  private byteToMegabyte(byte: number): string {
    if (byte) {
      let mb = byte / this.C_C.MEGAYTE_SIZE;
      // toFixed(3) viene utilizzato per arrotondare il risultato a tre decimali
      return mb.toFixed(2);
    }
    return '';
  }

  /**
   * Funzione che converte una data con la formattazione pensata per il CSV.
   * @param date any con le informazioni da convertire.
   */
  private csvMoment(date: any): string {
    // Gestisco possibili errori
    try {
      // Cerco di convertire la data
      return date ? moment(date).format(this.FULL_DATE_CSV_FORMAT) : '';
    } catch (e) {
      // Ritorno stringa vuota
      return '';
    }
  }

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  /**
   * Getter che ritorna la configurazione utilizzata per le colonne della tabella allegati csv.
   * La struttura è dedicata alla gestione di FO.
   * @returns ICRATableColumn[] con la configurazione specifica.
   */
  get allegatiCsvColumnFO(): ICRATableColumn[] {
    // Definisco l'array con le configurazioni per il FO
    const columnFO: ICRATableColumn[] = [
      this.CSV_CLASSIFICAZIONE_DOC,
      this.CSV_DOCUMENTO,
      this.CSV_DOCUMENTO_CORRELATO,
      this.CSV_CATEGORIA_ALLEGATO,
      this.CSV_TIPOLOGIA_ALLEGATO,
      this.CSV_CODICE_ELABORATO,
      this.CSV_DATA_CARICAMENTO,
      this.CSV_DIMENSIONE_UPLOAD,
    ];
    // Ritorno la configurazione
    return columnFO;
  }

  /**
   * Getter che ritorna la configurazione utilizzata per le colonne della tabella allegati csv.
   * La struttura è dedicata alla gestione di BO.
   * @returns ICRATableColumn[] con la configurazione specifica.
   */
  get allegatiCsvColumnBO(): ICRATableColumn[] {
    // Definisco l'array con le configurazioni per il BO
    const columnBO: ICRATableColumn[] = [
      this.CSV_CLASSIFICAZIONE_DOC,
      this.CSV_DOCUMENTO,
      this.CSV_DOCUMENTO_CORRELATO,
      this.CSV_IN_PUBBLICAZIONE,
      this.CSV_CATEGORIA_ALLEGATO,
      this.CSV_TIPOLOGIA_ALLEGATO,
      this.CSV_CODICE_ELABORATO,
      this.CSV_DATA_CARICAMENTO,
      this.CSV_DATA_PUBBLICAZIONE,
      this.CSV_DIMENSIONE_UPLOAD,
      this.CSV_FLG_RISERVATO, // ACCESSO
      this.CSV_DATA_PROTOCOLLO,
      this.CSV_NUM_PROTOCOLLLO,
      this.CSV_DATA_ATTO,
      this.CSV_NUMERO_ATTO,
      this.CSV_TITOLO_ALLEGATO,
      this.CSV_AUTORE_ALLEGATO,
      this.CSV_NOTE,
      this.CSV_DATA_INTEGRAZIONE, // INTEGRAZIONE O PERFEZIONAMENTO
      this.CSV_TIPO_INTEGRAZIONE,
      this.CSV_DATA_CANCELLAZIONE,
    ];
    // Ritorno la configurazione
    return columnBO;
  }
}
