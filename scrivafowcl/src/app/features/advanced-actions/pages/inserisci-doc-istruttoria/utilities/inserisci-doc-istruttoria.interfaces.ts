/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ScrivaServerError } from '../../../../../core/classes/scriva.classes';
import { NotaIstanza } from '../../../../../shared/models';
import { Allegato } from '../../../../ambito/models';
import { IDIModificheUtente } from './inserisci-doc-istruttoria.enums';

/**
 * Interfaccia che definisce le informazioni per tenere traccia dei dati collegati ad un'azione utente.
 */
export interface IIDIRegistroModifica {
  azione: IDIModificheUtente;
  datiFile?: IFileAzioneRegistro;
  datiFiles?: IFileAzioneRegistro[];
  datiAllegato?: IAllegatoAzioneRegistro;
  datiPubblicazioneAllegati?: IPubblicazioneAllegatiAzioneRegistro;
  datiNota?: INotaAzioneRegistro;
}

/**
 * Interfaccia che definisce le informazioni di emissione dell'evento di file gestito.
 */
export interface IFileAzioneRegistro {
  /** string con l'identificativo per gestire il salvataggio di un file. */
  id: string;
  /** any con le informazioni del file gestito. */
  data: any;
}

/**
 * Interfaccia che definisce le informazioni di emissione dell'evento di allegato gestito.
 */
export interface IAllegatoAzioneRegistro {
  /** string con l'identificativo per gestire il salvataggio di un file. */
  id: string;
  /** Allegato con le informazioni dell'allegato gestito. */
  allegato: Allegato;
}

/**
 * Interfaccia che definisce le informazioni di emissione dell'evento di allegati pubblicati/annullati.
 * L'oggetto dovrà essere valorizzato solo se almeno un allegato è stato correttamente pubblicato o annullato.
 */
export interface IPubblicazioneAllegatiAzioneRegistro {
  /** string con l'identificativo per gestire il salvataggio di un file. */
  id: string;
  /** Partial<Allegato>[] con la lista di allegati che si è cercato di pubblicare/annullare. */
  allegati: Partial<Allegato>[];
  /** (Partial<Allegato> | ScrivaServerError)[] con le informazioni del risultato per la pubblicazione/annullamento. */
  risultati: (Partial<Allegato> | ScrivaServerError)[];
}

/**
 * Interfaccia che definisce le informazioni di emissione dell'evento di nota gestita.
 */
export interface INotaAzioneRegistro {
  /** string con l'identificativo per gestire il salvataggio di un file. */
  id: string;
  /** NotaIstanza con le informazioni della nota gestita. */
  notaIstanza: NotaIstanza;
}
