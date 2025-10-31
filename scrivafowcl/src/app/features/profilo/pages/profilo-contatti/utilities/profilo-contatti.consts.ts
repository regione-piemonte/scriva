/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ScrivaIcons } from '../../../../../shared/components/scriva-icon/utilities/scriva-icon.classes';
import { IPDUAction } from '../../../components/profilo-dato-utente/utilities/profilo-dato-utente.interfaces';
import { PCActions } from './profilo-contatti.enums';

/**
 * Definizioni delle azioni per il dato utente.
 */
const actionModificaEmail: IPDUAction = {
  id: PCActions.modificaEmail,
  title: 'Modifica',
  icon: {
    icon: ScrivaIcons.modifica,
    alt: 'Modifica',
    asset: true,
  },
  disabled: false,
  style: {},
};
const actionModificaTelefono: IPDUAction = {
  id: PCActions.modificaTel,
  title: 'Modifica',
  icon: {
    icon: ScrivaIcons.modificaDisabled,
    alt: 'Modifica',
    asset: true,
  },
  style: {},
  disabled: true,
};
const actionVediTerminiCondizioni: IPDUAction = {
  id: PCActions.terminiCodizioniUso,
  title: `Termini e condizioni d'uso`,
  icon: {
    icon: 'fa-chevron-right fa-lg scriva-blue',
    alt: 'Vedi di più',
  },
  style: {},
  disabled: true,
};

/**
 * Interfaccia di supporto per l'oggetto di costanti del componente profilo-contatti.component.ts
 */
export interface IProfiloContattiConsts {
  ALERT_ANCHOR: string;

  LABEL_EMAIL: string;
  LABEL_TELEFONO: string;
  LABEL_TERMINI_CONDIZIONI_USO: string;

  ACTIONS_EMAIL: IPDUAction[];
  ACTIONS_TELEFONO: IPDUAction[];
  ACTIONS_TERMINI_CONDIZIONI_USO: IPDUAction[];
}

/**
 * Costanti di utilities per il componente profilo-contatti.components.ts.
 */
export const PROFILO_CONTATTI_CONSTS: IProfiloContattiConsts = {
  ALERT_ANCHOR: 'ALERT_PROFILO_CONTATTI',

  LABEL_EMAIL: 'E-mail',
  LABEL_TELEFONO: 'Numero di telefono',
  LABEL_TERMINI_CONDIZIONI_USO: 'Termini e condizioni uso',

  ACTIONS_EMAIL: [actionModificaEmail],
  ACTIONS_TELEFONO: [actionModificaTelefono],
  ACTIONS_TERMINI_CONDIZIONI_USO: [actionVediTerminiCondizioni],
};
