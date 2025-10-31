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
 * Enum che definisce le tipologie di modifiche che l'utente può effettuare e devono essere registrate.
 */
export enum IDIModificheUtente {
  documentoAggiunto = 'DOCUMENTO_AGGIUNTO',
  documentoSingoloAggiunto = 'DOCUMENTO_AGGIUNTO_SINGOLO',
  documentoMultiploAggiunto = 'DOCUMENTO_AGGIUNTO_MULTIPLO',
  allegatoModificato = 'ALLEGATO_MODIFICATO',
  allegatoCancellato = 'ALLEGATO_CANCELLATO',
  pubblicazioneAllegati = 'PUBBLICAZIONE_ANNULLAMENTO_ALLEGATI',
  notaAggiunta = 'NOTA_AGGIUNTA',
  sconosciuta = 'SCONOSCIUTA'
}