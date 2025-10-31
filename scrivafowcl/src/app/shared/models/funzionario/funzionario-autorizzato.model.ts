/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { FunzionarioCompetenza } from './funzionario-competenza.model';
import { FunzionarioProfilo } from './funzionario-profilo.model';
import { IFunzionario } from './funzionario.model';

export interface IFunzionarioAutorizzato {
  funzionario: IFunzionario;
  funzionario_competenza?: FunzionarioCompetenza[];
  funzionario_profilo?: FunzionarioProfilo[];
}
