/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { NotaIstanza } from '../../../../../shared/models';
import { IDIModificheUtente } from './inserisci-doc-istruttoria.enums';
import {
  IAllegatoAzioneRegistro,
  IFileAzioneRegistro,
  IIDIRegistroModifica,
  IPubblicazioneAllegatiAzioneRegistro,
  INotaAzioneRegistro,
} from './inserisci-doc-istruttoria.interfaces';

/**
 * Classe che definisce le informazioni per tenere traccia dei dati collegati ad un'azione utente.
 */
export class IDIRegistroAzione {
  /** IDIModificheUtente con il tipo di modifica fatta dall'utente. */
  private readonly _azione: IDIModificheUtente;

  /** IFileAzioneRegistro con le informazioni del file gestito. */
  datiFile?: IFileAzioneRegistro;
  /** IFileAzioneRegistro[] con le informazioni dei file multipli gestiti. */
  datiFiles?: IFileAzioneRegistro[];
  /** IAllegatoAzioneRegistro con le informazioni dell'allegato aggiornato dall'utente. */
  datiAllegato?: IAllegatoAzioneRegistro;
  /** IPubblicazioneAllegatiAzioneRegistro con le informazioni che tracciano la pubblicazione/annullamento allegati. */
  datiPubblicazioneAllegati?: IPubblicazioneAllegatiAzioneRegistro;
  /** NotaIstanza con le informazioni delle nota inserita dall'utente. */
  datiNota?: INotaAzioneRegistro;

  /**
   * Costruttore.
   */
  constructor(configs: IIDIRegistroModifica) {
    // Verifico i dati e assegno le informazioni
    this._azione = configs?.azione ?? IDIModificheUtente.sconosciuta;
    this.datiFile = configs?.datiFile;
    this.datiFiles = configs?.datiFiles;
    this.datiAllegato = configs?.datiAllegato;
    this.datiPubblicazioneAllegati = configs?.datiPubblicazioneAllegati;
    this.datiNota = configs?.datiNota;

    // #
  }

  /**
   * ##################
   * FUNZIONI DI COMODO
   * ##################
   */

  // #region "FILE MULTIPLI"

  /**
   * Funzione che verifica se l'azione gestisce file multipli ed il dato in input fa parte dello stesso blocco dati.
   * @param datiFile IFileAzioneRegistro con le informazioni del file da verificato.
   * @returns boolean con il risultato del check.
   */
  isFileInBloccoFiles(datiFile: IFileAzioneRegistro): boolean {
    // Verifico l'input
    if (!datiFile || !datiFile.id || !this.isAzioneFileMultipli) {
      // Mancano i dati minimi o l'azione non è a file multipli
      return false;
    }

    // Estraggo le informazioni dall'input
    const idCheck: string = datiFile.id;
    // Recupero la lista lista di files per la verifica
    const files: IFileAzioneRegistro[] = this.datiFiles ?? [];
    // Verifico se all'interno dei files c'è lo stesso id
    return files.some((f: IFileAzioneRegistro) => f.id === idCheck);
  }

  /**
   * Funzione che aggiunge alla lista dei fils multipli la nuova azione.
   * @param datiFile IFileAzioneRegistro d'aggiungere alla lista.
   * @returns IFileAzioneRegistro[] con la lista aggiornata. Se torna undefined, vuol dire che l'azione non gestisce file multipli.
   */
  aggiungiFileMultiplo(datiFile: IFileAzioneRegistro): IFileAzioneRegistro[] {
    // Verifico l'input
    if (!datiFile || !datiFile.id || !this.isAzioneFileMultipli) {
      // Mancano i dati minimi o l'azione non è del tipo necessario
      return undefined;
    }

    // Verifico che esista la lista di file
    if (!this.datiFiles) {
      // Lista non creata, la creo
      this.datiFiles = [];
    }

    // Aggiungo l'elemento alla lista
    this.datiFiles.push(datiFile);
    // Ritorno la lista aggiornata
    return this.datiFiles;
  }

  // #endregion "FILE MULTIPLI"

  // #region "NOTA"
  
  /**
   * Funzione che verifica se l'azione gestisce una nota specifica aggiunta dall'utente.
   * @notes La comparazione avviene come nel componente "scriva-note.component.ts", per la funzione "onRimuoviNota", ossia tramite comparazione dei gestUID.
   * @param nota NotaIstanza con le informazioni della nota da cercare.
   * @returns boolean con il risultato del check.
   */
  isNotaInserita(nota: NotaIstanza): boolean {
    // Verifico l'input
    if (!nota || !this.isAzioneNotaAggiunta || !this.datiNota) {
      // Mancano i dati minimi o l'azione non è del tipo necessario
      return false;
    }

    // Recupero la nota inserita
    const notaInserita: NotaIstanza = this.datiNota.notaIstanza;
    // Verifico la nota tramite uuid
    return notaInserita?.gestUID === nota.gestUID;
  }

  // #endregion "NOTA"

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  /**
   * Getter per la proprietà del componente.
   * @returns IDIModificheUtente con le informazioni della variabile.
   */
  get azione(): IDIModificheUtente {
    // Ritorno il valore del componente
    return this._azione;
  }

  /**
   * Getter che verifica il tipo azione definito.
   * @returns boolean con il risultato del check.
   */
  get isAzioneFileMultipli(): boolean {
    // Recupero l'azione
    const azione = this._azione;
    // Verifico se l'azione è di tipo aggiunta file multiplo
    return azione === IDIModificheUtente.documentoMultiploAggiunto;
  }

  /**
   * Getter che verifica il tipo azione definito.
   * @returns boolean con il risultato del check.
   */
  get isAzioneNotaAggiunta(): boolean {
    // Recupero l'azione
    const azione = this._azione;
    // Verifico se l'azione è di tipo aggiunta file multiplo
    return azione === IDIModificheUtente.notaAggiunta;
  }
}
