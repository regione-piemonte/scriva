/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, Input, OnInit } from '@angular/core';
import { Compilante, IFunzionarioAutorizzato } from 'src/app/shared/models';
import { AuthStoreService } from 'src/app/shared/services';
import { CommonConsts } from '../../consts/common-consts.consts';

@Component({
  selector: 'scriva-page-title',
  templateUrl: './scriva-page-title.component.html',
  styleUrls: ['./scriva-page-title.component.scss'],
})
export class ScrivaPageTitleComponent implements OnInit {
  /** Oggetto di costanti comune per l'applicativo scriva. */
  C_C = new CommonConsts();
  
  /** Input string che definisce il nome della pagina come titolo. */
  @Input() title: string = 'Pagina';
  /** Input boolean che definisce se il titolo va concatenato con le informazioni dell'utente connesso. Per default è: false. */
  @Input() showUserInfo: boolean = false;
  /** Input string | boolean che permette di definire delle regole css custom per il container del titolo. L'input deve essere compatibile con le direttive ngClass o ngStyle. */
  @Input() customContainer: string | any;

  /** Compilante contenente le informazioni del compilante per la generazione del title di pagina. */
  compilante: Compilante;
  /** FunzionarioAutorizzato contenente le informazioni del compilante per la generazione del title di pagina. */
  funzionario: IFunzionarioAutorizzato;

  /**
   * Costruttore.
   */
  constructor(private _authStore: AuthStoreService) {}

  /**
   * NgOnInit.
   */
  ngOnInit() {
    // Lancio la funzione di init del componente
    this.initComponente();
  }

  /**
   * ###############################
   * FUNZIONI DI INIT DEL COMPONENTE
   * ###############################
   */

  /**
   * Funzione di comodo per le funzioni di init del componente.
   */
  private initComponente() {
    // Recupero le informazioni utente
    this.initInfoUtente();
  }

  /**
   * Funzione di init specifica per i dati dell'utente loggato.
   * In base alle configurazioni, verranno popolati vaariabili specifiche per i dati utente.
   */
  private initInfoUtente() {
    // Verifico il tipo di componente applicativo
    if (this.isFrontOffice) {
      // Scarico i dati del compilante
      this.compilante = this._authStore.getCompilante();
      // #
    } else {
      // Scarico i dati del funzionario
      this.funzionario = this._authStore.getFunzionarioAutorizzato();
      // #
    }
  }

  /**
   * ###############
   * GETTER & SETTER
   * ###############
   */

  /**
   * Getter per sapere se l'applicazione è impostata su "front office".
   * @returns boolean che definisce se l'applicazione è impostata su "front office".
   */
  get isFrontOffice(): boolean {
    // Verifico se il componente è di front office
    return this._authStore.isFrontOffice;
  }

  /**
   * Getter di comodo per il titolo composto.
   * @returns string con la composizione del titolo.
   */
  get pageTitle(): string {
    // Verifico se ci sono da mostrare le informazioni dell'utente
    if (this.showUserInfo) {
      // Verifico se esiste l'oggetto compilante o funzionario
      if (this.compilante) {
        // Ritorno il title con i dati del compilante
        return `${this.title} di ${this.compilante.nome_compilante} ${this.compilante.cognome_compilante}`;
        // #
      } else if (this.funzionario) {
        // Ritorno il title con i dati del funzionario
        return `${this.title} di ${this.funzionario.funzionario.nome_funzionario} ${this.funzionario.funzionario.cognome_funzionario}`;
        // #
      } else {
        // Manca qualche informazione, ritorno il title
        return this.title;
      }
    } else {
      // Il titolo è semplicemente quello passato
      return this.title;
    }
  }
}
