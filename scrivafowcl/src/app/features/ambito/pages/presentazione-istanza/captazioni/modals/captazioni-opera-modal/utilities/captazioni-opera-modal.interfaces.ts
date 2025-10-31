/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import {
  IDTOperaModalCallbacks,
  IDTOperaSalvataggio,
  IFormIoDTOperaParams,
} from '../../../../opere/modals/dati-tecnici-opera-modal/utilities/dati-tecnici-opera-modal.interfaces';
import { FontanileUtilities } from '../fontanile/fontanile.utilities';
import { PozzoUtilities } from '../pozzo/pozzo.utilities';
import { PresaUtilities } from '../presa/presa.utilities';
import { SorgenteUtilities } from '../sorgente/sorgente.utilities';
import { TrinceaDrenanteUtilities } from '../trincea-drenante/trincea-drenante.utilities';

/**
 * Interfaccia che definisce le informazioni come parametri extra di gestione del formio per i dati tecnici dell'opera.
 */
export interface IFormIoCaptazioniOperaParams extends IFormIoDTOperaParams {
  pozzoUtils?: PozzoUtilities;
  presaUtils?: PresaUtilities;
  sorgenteUtils?: SorgenteUtilities;
  fontanileUtils?: FontanileUtilities;
  trinceaDrenanteUtils?: TrinceaDrenanteUtilities;
}

/**
 * Interfaccia che definisce le informazioni per il salvataggio dei dati tecnici di una sezione.
 */
export interface IDTOperaSalvataggioSezione extends IDTOperaSalvataggio {
  sezione: string;
  datiSezione: any;
}

/**
 * Interfaccia che definisce le callback da utilizzare per la gestione dei dati tecnici delle opere.
 */
export interface IDTCaptazioniOperaModalCallbacks
  extends IDTOperaModalCallbacks {
  /** (datiTecniciDaSalvare: IDTOperaSalvataggio) => any; con la funzione che gestisce il flusso logico per la gestione del salvataggio parziale dei dati tecnici di un'opera. */
  saveDatiTecniciOperaPartial: (
    datiTecniciDaSalvare: IDTOperaSalvataggio
  ) => any;
}
