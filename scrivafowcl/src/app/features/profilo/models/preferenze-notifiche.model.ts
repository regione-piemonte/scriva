/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Compilante } from '../../../shared/models';
import { StringMap } from '@angular/compiler/src/compiler_facade_interface';

/**
 * Interfaccia che rappresenta il dato ritornato dal server per: IPreferenzaNotifica.
 */
export interface IPreferenzaNotifica {
  idCompilantePreferenzaCanale: number;
  flgAbilitato: boolean;
  compilante?: Compilante;
  canaleNotifica: ICanaleNotifica;
}

/**
 * Interfaccia che rappresenta il dato ritornato dal server per: ICanaleNotifica.
 */
export interface ICanaleNotifica {
  idCanaleNotifica: number;
  idComponenteApplPush: number;
  codCanaleNotifica: string;
  desCanaleNotifica: string;
  flgCampoCc: boolean;
  dataInizio: string;
  indTipoCanale: string;
}
