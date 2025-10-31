/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  RouterStateSnapshot,
} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthStoreService } from '../../services';

@Injectable()
export class ProfiloGuard implements CanActivate {
  /**
   * Costruttore.
   */
  constructor(private _authStore: AuthStoreService) {}

  /**
   * Interfaccia per la gestione del routing.
   * @param route ActivatedRouteSnapshot.
   * @param state RouterStateSnapshot.
   */
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean | Promise<boolean> | Observable<boolean> {
    // Lancio la funzione di verifica per il componente applicativo
    return this.isFrontOffice();
  }

  /**
   * Funzione di check di supporto per il tipo di componente attivo per scriva, che abilita solo per il front office..
   * @returns boolean che specifica se il componente è di front office.
   */
  private isFrontOffice() {
    // Solo il componente di front office può accedere al profilo
    return this._authStore.isFrontOffice;
  }
}
