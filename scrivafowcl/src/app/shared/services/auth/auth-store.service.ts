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
import { cloneDeep } from 'lodash';
import { environment } from 'src/environments/environment';
import { ScrivaComponenteApp } from '../../enums/scriva-utilities/scriva-utilities.enums';
import { Compilante, IFunzionario, IFunzionarioAutorizzato, TipoAdempimento, FunzionarioProfilo } from '../../models';

@Injectable({
  providedIn: 'root',
})
export class AuthStoreService {
  private compilante: Compilante;
  private componente: ScrivaComponenteApp;
  private funzionario: IFunzionarioAutorizzato;
  private loggedUserCf: string;

  constructor() {
    this.compilante = null;
    this.funzionario = null;
  }

  getCompilante(): Compilante {
    return cloneDeep(this.compilante);
  }

  setCompilante(compilante: Compilante): void {
    this.compilante = cloneDeep(compilante);
  }

  hasCompilante(): boolean {
    return !!this.compilante;
  }

  getComponente(): ScrivaComponenteApp {
    return this.componente;
  }

  setComponente(componente: ScrivaComponenteApp): ScrivaComponenteApp {
    this.componente = componente;
    sessionStorage.setItem('app', componente);
    return this.componente;
  }

  setComponenteByHostOrSession() {
    const component: ScrivaComponenteApp = sessionStorage.getItem(
      'app'
    ) as ScrivaComponenteApp;
    if (component) {
      return this.setComponente(component);
    }

    if (window.location.hostname.includes('scrivabo')) {
      return this.setComponente(ScrivaComponenteApp.backOffice);
    }

    if (environment.local && environment.localEnv.fakeAuth.enabled) {
      return this.setComponente(
        environment.localEnv.fakeAuth.typeProfile as ScrivaComponenteApp
      );
    }

    return this.setComponente(ScrivaComponenteApp.frontOffice);
  }

  isBoComponent(): boolean {
    return this.componente === ScrivaComponenteApp.backOffice;
  }

  getFunzionarioAutorizzato(): IFunzionarioAutorizzato {
    return cloneDeep(this.funzionario);
  }

  getFunzionario(): IFunzionario {
    return this.funzionario?.funzionario;
  }

  setFunzionario(funzionario: IFunzionarioAutorizzato) {
    this.funzionario = cloneDeep(funzionario);
  }

  hasFunzionario(): boolean {
    return !!this.funzionario;
  }

  getLoggedUserCf(): string {
    return this.loggedUserCf;
  }

  setLoggedUserCf(cf: string) {
    this.loggedUserCf = cf;
  }

  /**
   * ###############
   * GETTER & SETTER
   * ###############
   */

  /**
   * Getter per il componente dell'applicativo.
   * @returns ScrivaComponenteApp con il componente attivo.
   */
  get scrivaComponente(): ScrivaComponenteApp {
    // Recupero il componente dal servizio
    return this.getComponente();
  }

  /**
   * Getter per sapere se l'applicazione è impostata su "front office".
   * @returns boolean che definisce se l'applicazione è impostata su "front office".
   */
  get isFrontOffice(): boolean {
    // Recupero il tipo "componente" dal servizio di auth
    const componente: ScrivaComponenteApp = this.scrivaComponente;
    // Verifico se il componente è di front office
    return componente === ScrivaComponenteApp.frontOffice;
  }

  /**
   * Getter per sapere se l'applicazione è impostata su "back office".
   * @returns boolean che definisce se l'applicazione è impostata su "back office".
   */
  get isBackOffice(): boolean {
    // Recupero il tipo "componente" dal servizio di auth
    const componente: ScrivaComponenteApp = this.scrivaComponente;
    // Verifico se il componente è di back office
    return componente === ScrivaComponenteApp.backOffice;
  }

  /**
   * recupero il nome utente corrente
   */
  get loggedUserNome(): string {
    if (this.isFrontOffice) return this.getCompilante().nome_compilante;
    return this.getFunzionario().nome_funzionario;
  }

  /**
   * Recupero il cognome utente corrente
   */
  get loggedUserCognome(): string {
    if (this.isFrontOffice) return this.getCompilante().cognome_compilante;
    return this.getFunzionario().cognome_funzionario;
  }
}
