import { TypeSiNo } from '../../../../../../shared/types/formio/scriva-formio.types';
import { ConfigElement } from '../../../../models';
import { IDatiTecniciOggettoIstanza } from '../../../../pages/presentazione-istanza/opere/utilities/opere.interfaces';

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
 * Interfaccia che definisce le configurazioni per la gestione del cf azienda del componente.
 */
export interface IConfigsElementCfAzienda {
  cfAziendaConfig: ConfigElement;
  denAziendaConfig: ConfigElement;
}

/**
 * Interfaccia che rappresenta le informazioni generate dal formio per gli usi/ulo.
 * L'interfaccia deriva dalla struttura del formio, quindi ad ogni variazione strutturale del formio bisogna aggiornare questa interfaccia.
 */
export interface IDTFormioUsiUloDER extends IDatiTecniciOggettoIstanza {
  id_oggetto?: string;
  identificativo: string;
  id_oggetto_istanza: number;
  DATI_ULO?: IDTFormioUsiUloDER_DATI_ULO;
  USO_PISCICOLO?: IDTFormioUsiUloDER_USO_PISCICOLO;
  USO_ENERGETICO?: IDTFormioUsiUloDER_USO_ENERGETICO;
  USO_PRODUZIONE?: IDTFormioUsiUloDER_USO_PRODUZIONE;
  USO_ZOOTECNICO?: IDTFormioUsiUloDER_USO_ZOOTECNICO;
  USO_LAVAGGIO_INERTI?: IDTFormioUsiUloDER_USO_LAVAGGIO_INERTI;
  USO_RIQUALIFICAZIONE?: IDTFormioUsiUloDER_USO_RIQUALIFICAZIONE;
}

/**
 * Interfaccia che rappresenta le informazioni generate dal formio per gli usi/ulo.
 * L'interfaccia deriva dalla struttura del formio, quindi ad ogni variazione strutturale del formio bisogna aggiornare questa interfaccia.
 * L'interfaccia rappresenta una sezione specifica del formio.
 * La dichiarazione dell'interfaccia viene gestita con informazioni generiche e con la definizione delle proprietà essenziali per il funzionamento delle logiche di FE, vista la natura dinamica del FormIo.
 */
export interface IDTFormioUsiUloDER_DATI_ULO {
  [key: string]: any;
}

/**
 * Interfaccia che rappresenta le informazioni generate dal formio per gli usi/ulo.
 * L'interfaccia deriva dalla struttura del formio, quindi ad ogni variazione strutturale del formio bisogna aggiornare questa interfaccia.
 * L'interfaccia rappresenta una sezione specifica del formio.
 * La dichiarazione dell'interfaccia viene gestita con informazioni generiche e con la definizione delle proprietà essenziali per il funzionamento delle logiche di FE, vista la natura dinamica del FormIo.
 */
export interface IDTFormioUsiUloDER_USO_PISCICOLO {
  [key: string]: any;
  flgUsoPiscicolo: TypeSiNo;
}

/**
 * Interfaccia che rappresenta le informazioni generate dal formio per gli usi/ulo.
 * L'interfaccia deriva dalla struttura del formio, quindi ad ogni variazione strutturale del formio bisogna aggiornare questa interfaccia.
 * L'interfaccia rappresenta una sezione specifica del formio.
 * La dichiarazione dell'interfaccia viene gestita con informazioni generiche e con la definizione delle proprietà essenziali per il funzionamento delle logiche di FE, vista la natura dinamica del FormIo.
 */
export interface IDTFormioUsiUloDER_USO_ENERGETICO {
  [key: string]: any;
  flgUsoEnergetico: TypeSiNo;
}

/**
 * Interfaccia che rappresenta le informazioni generate dal formio per gli usi/ulo.
 * L'interfaccia deriva dalla struttura del formio, quindi ad ogni variazione strutturale del formio bisogna aggiornare questa interfaccia.
 * L'interfaccia rappresenta una sezione specifica del formio.
 * La dichiarazione dell'interfaccia viene gestita con informazioni generiche e con la definizione delle proprietà essenziali per il funzionamento delle logiche di FE, vista la natura dinamica del FormIo.
 */
export interface IDTFormioUsiUloDER_USO_PRODUZIONE {
  [key: string]: any;
  flgUsoProduzione: TypeSiNo;
}

/**
 * Interfaccia che rappresenta le informazioni generate dal formio per gli usi/ulo.
 * L'interfaccia deriva dalla struttura del formio, quindi ad ogni variazione strutturale del formio bisogna aggiornare questa interfaccia.
 * L'interfaccia rappresenta una sezione specifica del formio.
 * La dichiarazione dell'interfaccia viene gestita con informazioni generiche e con la definizione delle proprietà essenziali per il funzionamento delle logiche di FE, vista la natura dinamica del FormIo.
 */
export interface IDTFormioUsiUloDER_USO_ZOOTECNICO {
  [key: string]: any;
  flgUsoZootecnico: TypeSiNo;
}

/**
 * Interfaccia che rappresenta le informazioni generate dal formio per gli usi/ulo.
 * L'interfaccia deriva dalla struttura del formio, quindi ad ogni variazione strutturale del formio bisogna aggiornare questa interfaccia.
 * L'interfaccia rappresenta una sezione specifica del formio.
 * La dichiarazione dell'interfaccia viene gestita con informazioni generiche e con la definizione delle proprietà essenziali per il funzionamento delle logiche di FE, vista la natura dinamica del FormIo.
 */
export interface IDTFormioUsiUloDER_USO_LAVAGGIO_INERTI {
  [key: string]: any;
  flgUsoLavaggioInerti: TypeSiNo;
}

/**
 * Interfaccia che rappresenta le informazioni generate dal formio per gli usi/ulo.
 * L'interfaccia deriva dalla struttura del formio, quindi ad ogni variazione strutturale del formio bisogna aggiornare questa interfaccia.
 * L'interfaccia rappresenta una sezione specifica del formio.
 * La dichiarazione dell'interfaccia viene gestita con informazioni generiche e con la definizione delle proprietà essenziali per il funzionamento delle logiche di FE, vista la natura dinamica del FormIo.
 */
export interface IDTFormioUsiUloDER_USO_RIQUALIFICAZIONE {
  [key: string]: any;
  flgUsoRiqualificazione: TypeSiNo;
}
