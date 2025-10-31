/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { AutoUnsubscribe } from 'src/app/core/components';
import { TipoAdempimento } from '../../models';
import { AuthStoreService } from '../../services';
import { FunzionarioService } from '../../services/auth/funzionario.service';

@Component({
  selector: 'app-tipo-adempimento-card',
  templateUrl: './tipo-adempimento-card.component.html',
  styleUrls: ['./tipo-adempimento-card.component.scss'],
})
export class TipoAdempimentoCardComponent
  extends AutoUnsubscribe
  implements OnInit
{
  @Input() tipoAdempimento: TipoAdempimento;

  @Output() toggleFavourite = new EventEmitter();
  @Output() creaNuova = new EventEmitter();
  @Output() info = new EventEmitter();
  @Output() cerca = new EventEmitter();

  /** boolean che definisce se il ruolo utente è di consultazione rispetto al tipo adempimento in input. */
  isConsultatoreByTipoAdempimento: boolean = false;

  /**
   * Costruttore.
   */
  constructor(
    private _authStore: AuthStoreService,
    private _funzionario: FunzionarioService
  ) {
    super();
  }

  /**
   * ################
   * FUNZIONI DI INIT
   * ################
   */

  // #region "INIT"

  /**
   * ngOnInit.
   */
  ngOnInit() {
    // Lancio la funzione di init del componente
    this.initComponente();
    // #
  }

  /**
   * Funzione di init per le logiche del componente.
   */
  private initComponente() {
    // Lancio la funzione di init per la gestione del ruolo consultatore rispetto al tipo adempimento
    this.initRuoloPerTipoAdempimento();
    // #
  }

  /**
   * Funzione di init per le logiche del componente.
   */
  private initRuoloPerTipoAdempimento() {
    // Vado a verificare se sono nel contesto di BO
    if (this.isBackOffice) {
      // Recupero dal contesto del componente il tipo adempimento in input
      const tipoAdempimento: TipoAdempimento = this.tipoAdempimento;
      // Sono nel contesto di backoffice, lancio la funzione di verifica per il tipo adempimento
      let flagTA: boolean;
      flagTA = this.verificaConsultatoreByTipoAdempimento(tipoAdempimento);

      // Assegno il risultato localmente
      this.isConsultatoreByTipoAdempimento = flagTA;
      // #
    }
  }

  // #endregion "INIT"

  /**
   * ####################
   * FUNZIONI DI SUPPORTO
   * ####################
   */

  // #region "SUPPORTO"

  /**
   * Funzione che verifica rispetto alle configurazioni del funzionario autorizzato se questo è un CONSULTATORE rispetto al tipo adempimento passato in input.
   * @param tipoAdempimento TipoAdempimento da verificare se risulta in sola consultazione.
   * @returns boolean che indica se l'utente è CONSULTATORE [true] o può inserire istanze [false].
   */
  private verificaConsultatoreByTipoAdempimento(
    tipoAdempimento: TipoAdempimento
  ): boolean {
    // Richiamo la funzione del servizio e ritorno il risultato
    return this._funzionario.verificaConsultatoreByTipoAdempimento(
      tipoAdempimento
    );
    // #
  }

  // #endregion "SUPPORTO"

  onToggleFavourite() {
    this.toggleFavourite.emit();
  }

  onCreaNuova() {
    this.creaNuova.emit();
  }

  onInfo() {
    this.info.emit();
  }

  onCerca() {
    this.cerca.emit();
  }

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  // #region "GETTER E SETTER"

  /**
   * Getter per sapere se l'applicazione è impostata su "back office".
   * @returns boolean che definisce se l'applicazione è impostata su "back office".
   */
  get isBackOffice(): boolean {
    // Verifico se il componente è di back office tramite servizio
    return this._authStore.isBackOffice;
    // #
  }

  // #endregion "GETTER E SETTER"
}
