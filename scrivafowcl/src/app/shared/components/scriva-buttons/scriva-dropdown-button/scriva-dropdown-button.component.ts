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
  Component,
  EventEmitter,
  Input,
  Output,
  ViewChild,
} from '@angular/core';
import { NgbDropdown } from '@ng-bootstrap/ng-bootstrap';
import {
  ScrivaDDButtonConfig,
  ScrivaDDButtonCss,
  ScrivaDDItemConfig,
} from '../../../classes/scriva-utilities/scriva-utilities.classes';
import { NgBoostrapPlacements } from '../../../enums/ng-bootstrap/ng-bootstrap.enums';
import { SanitizerTypes } from '../../../enums/scriva-utilities/scriva-utilities.enums';
import {
  NgbDDAutoClose,
  NgbDDContainer,
  NgbDDDisplay,
} from '../../../types/ng-bootstrap/ng-bootstrap.types';
import { ScrivaCss } from '../../../types/scriva-utilities/scriva-utilities.types';
import { ScrivaButtonComponent } from '../scriva-button/scriva-button.component';

@Component({
  selector: 'scriva-dropdown-button',
  templateUrl: './scriva-dropdown-button.component.html',
  styleUrls: ['./scriva-dropdown-button.component.scss'],
})
export class ScrivaDropdownButtonComponent extends ScrivaButtonComponent {
  /** SanitizerTypes che definisce i tipi di sanitizzazione dell'HTML possibili. */
  ST = SanitizerTypes;

  /**
   * #########################################################################################################
   * CONFIGURAZIONI PER NgbDropdown. Ref: https://ng-bootstrap.github.io/#/components/dropdown/api#NgbDropdown
   * #########################################################################################################
   */
  /** Input che gestisce la configurazione: autoClose. */
  @Input('autoClose') ngbDDAutoClose: NgbDDAutoClose = true;
  /** Input che gestisce la configurazione: container. */
  @Input('container') ngbDDContainer: NgbDDContainer = 'body';
  /** Input che gestisce la configurazione: display. */
  @Input('display') ngbDDDisplay: NgbDDDisplay = 'dynamic';
  /** Input che gestisce la configurazione: open. */
  @Input('open') ngbDDOpen: boolean = false;
  /** Input che gestisce la configurazione: placement. */
  @Input('placement') ngbDDPlacement: NgBoostrapPlacements =
    NgBoostrapPlacements.bottom;
  /** Input che gestisce la configurazione: dropdownCss. */
  @Input('dropdownCss') ngbDDCss: ScrivaCss;

  /** Output che emette un evento quando viene cambiato lo stato di apertura della dropdown. */
  @Output('openChange') onOpenChanged = new EventEmitter<boolean>();

  /**
   * #########################
   * CONFIGURAZIONI COMPONENTE
   * #########################
   */
  /** Input che definisce le configurazioni per gli stili dropdown button. */
  @Input() cssConfig: ScrivaDDButtonCss;
  /** Input che definisce le configurazioni per i dati dropdown button. */
  @Input() dataConfig: ScrivaDDButtonConfig;

  /** Output che emette un evento quando viene premuto su un item della dropdown. */
  @Output() onItemClick = new EventEmitter<ScrivaDDItemConfig>();

  /** ViewChild collegato alla struttura del dropdown. */
  @ViewChild('scrivaBtnDD') scrivaBtnDD: NgbDropdown;

  /**
   * Costruttore
   */
  constructor() {
    super();
  }

  /**
   * #######################
   * FUNZIONI DELLA DROPDOWN
   * #######################
   */

  /**
   * Funzione agganciata all'evento openChange della direttiva ngbDropdown.
   * @param isOpen boolean che specifica se la dropdown è stata aperta (true) o chiusa (false).
   */
  onOpenChange(isOpen: boolean) {
    // Emetto l'evento
    this.onOpenChanged.emit(isOpen);
  }

  /**
   * Funzione che apre la dropdown programmativamente.
   */
  open() {
    // Richiamo la funzione del componente
    this.scrivaBtnDD.open();
  }

  /**
   * Funzione che chiude la dropdown programmativamente.
   */
  close() {
    // Richiamo la funzione del componente
    this.scrivaBtnDD.close();
  }

  /**
   * Funzione che toggla la dropdown programmativamente.
   */
  toggle() {
    // Richiamo la funzione del componente
    this.scrivaBtnDD.toggle();
  }

  /**
   * ##########################
   * FUNZIONALITA ITEM DROPDOWN
   * ##########################
   */

  /**
   * Funzione agganciata al click su un item della dropdown.
   * @param item ScrivaDDItemConfig contenente la configurazione dati dell'oggetto dell'item.
   */
  itemClick(item: ScrivaDDItemConfig) {
    // Emetto l'evento di click per l'item
    this.onItemClick.emit(item);
  }

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  /**
   * Getter che verifica se la dropdown è aperta.
   * @returns boolean che definisce se la dropdown è aperta (true) o chiusa (false).
   */
  get isOpen(): boolean {
    // Richiamo la funzione del componente
    return this.scrivaBtnDD?.isOpen() ?? false;
  }

  /**
   * Getter per la lista di configurazioni per gli items della dropdown.
   * @returns Array di ScrivaDDItemConfig che contiene le configurazioni per gli item della dropdown.
   */
  get dropdownItems(): ScrivaDDItemConfig[] {
    // Recupero dalla configurazione gli items
    return this.dataConfig?.dropdownItems ?? [];
  }
}
