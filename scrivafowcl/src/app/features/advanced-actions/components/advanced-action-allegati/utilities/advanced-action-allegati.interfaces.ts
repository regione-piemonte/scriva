/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { IAllegatoAzioneRegistro, IFileAzioneRegistro, IPubblicazioneAllegatiAzioneRegistro } from '../../../pages/inserisci-doc-istruttoria/utilities/inserisci-doc-istruttoria.interfaces';

/**
 * Interfaccia che definisce le informazioni di emissione dell'evento di file salvato.
 */
export interface IACAFileAggiunto extends IFileAzioneRegistro {}

/**
 * Interfaccia che definisce le informazioni di emissione dell'evento di allegato modificato.
 */
export interface IACAAllegatoModificato extends IAllegatoAzioneRegistro {}

/**
 * Interfaccia che definisce le informazioni di emissione dell'evento di allegato modificato.
 */
export interface IACAAllegatoCancellato extends IAllegatoAzioneRegistro {}

/**
 * Interfaccia che definisce le informazioni di pubblicazione/annullamento allegati.
 */
export interface IACAAllegatiPubblicati extends IPubblicazioneAllegatiAzioneRegistro {};
