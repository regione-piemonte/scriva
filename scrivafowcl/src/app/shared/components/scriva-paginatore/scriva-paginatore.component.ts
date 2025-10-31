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
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';
import { ScrivaPagination } from '../../services/helpers/utilities/http-helper.classes';
import { ScrivaIcons } from '../scriva-icon/utilities/scriva-icon.classes';

@Component({
  selector: 'scriva-paginatore',
  templateUrl: './scriva-paginatore.component.html',
  styleUrls: ['./scriva-paginatore.component.scss'],
})
export class ScrivaPaginatoreComponent implements OnInit, OnChanges {
  /** ScrivaIconsMap con le icone dell'applicazione. */
  private icons = ScrivaIcons;

  /**
   * Questo oggetto contiene i dati necessari alla paginazione di una tabella.
   * Se non forniti, la paginazione non viene fatta.
   */
  @Input() paginazione: ScrivaPagination;

  /**
   * Evento che comunica che è avvenuto un cambio pagina.
   * Il numero della nuova pagina sarà emesso.
   * Le pagine sono enumerate a partire da 1.
   */
  @Output('onCambioPagina') onCambioPagina$ =
    new EventEmitter<ScrivaPagination>();

  // Array delle pagine. Indica che pagine ci sono.
  public pagine: number[] = [];

  constructor() {}

  ngOnInit(): void {}

  ngOnChanges(changes: SimpleChanges): void {
    this.update();
  }

  /**
   * Aggiorna la paginazione se cambia la ricerca
   */
  private update() {
    if (this.paginazioneVisibile) {
      // Calcolo il numero di pagine, arrotondando per eccesso
      if (!this.overflowPagine) {
        // Creo le pagine
        this.pagine = [...Array(this.numPagine).keys()];
        // Le enumero correttamente partendo da 1
        this.pagine.forEach((i) => (this.pagine[i] = i + 1));
        // #
      } else {
        let distanzaSX = 0;
        let distanzaDX = 0;

        const metaSxPresunta = Math.floor((this.paginazione.maxPages - 1) / 2);
        const metaDxPresunta = this.paginazione.maxPages - 1 - metaSxPresunta;

        if (this.paginaCorrente <= metaSxPresunta) {
          distanzaSX = this.paginaCorrente - 1;
          distanzaDX = this.paginazione.maxPages - distanzaSX - 1;
        } else if (this.paginaCorrente >= this.numPagine - metaDxPresunta) {
          distanzaDX = this.numPagine - this.paginaCorrente;
          distanzaSX = this.paginazione.maxPages - distanzaDX - 1;
        } else {
          distanzaDX = metaDxPresunta;
          distanzaSX = metaSxPresunta;
        }

        const pagineSX = [...Array(distanzaSX).keys()];
        const indiceMinimo = this.paginaCorrente - distanzaSX;
        pagineSX.forEach((i) => (pagineSX[i] = i + indiceMinimo));

        const pagineDX = [...Array(distanzaDX).keys()];
        pagineDX.forEach((i) => (pagineDX[i] = i + this.paginaCorrente + 1));

        this.pagine = [...pagineSX, this.paginaCorrente, ...pagineDX];
      }
    }
  }

  /**
   * Istruzione per andare avanti o indietro sulle pagine
   * @param pagina pagina su cui andare
   */
  vaiAPagina(pagina: number) {
    // se sono sul primo elemento, sull'ultimo o sullo stesso non devo cambiare pagina
    if (
      pagina >= 1 &&
      pagina <= this.numPagine &&
      this.paginaCorrente != pagina
    ) {
      // Creo una copia dell'oggetto della paginazione da emettere
      const paginazione = new ScrivaPagination(this.paginazione);
      // Aggiorno la paginazione
      paginazione.currentPage = pagina;
      // Emetto l'oggetto della paginazione
      this.onCambioPagina$.emit(paginazione);
    }
  }

  /**
   * ###############
   * GETTER & SETTER
   * ###############
   */

  /**
   * Getter che recupera l'icona specifica per la funzione: start.
   * @returns string con l'icona per il pulsante.
   */
  get assets(): string {
    // Ritorno il path per gli assets
    return this.icons.assets;
  }

  /**
   * Getter che recupera l'icona specifica per la funzione: start.
   * @returns string con l'icona per il pulsante.
   */
  get start(): string {
    // Richiamo la costante e compongo l'icon
    const icon = `${this.assets}${this.icons.paginatorStart}`;
    // Ritorno l'icona generata
    return icon;
  }

  /**
   * Getter che recupera l'icona specifica per la funzione: backward.
   * @returns string con l'icona per il pulsante.
   */
  get backward(): string {
    // Richiamo la costante e compongo l'icon
    const icon = `${this.assets}${this.icons.paginatorBackward}`;
    // Ritorno l'icona generata
    return icon;
  }

  /**
   * Getter che recupera l'icona specifica per la funzione: end.
   * @returns string con l'icona per il pulsante.
   */
  get end(): string {
    // Richiamo la costante e compongo l'icon
    const icon = `${this.assets}${this.icons.paginatorEnd}`;
    // Ritorno l'icona generata
    return icon;
  }

  /**
   * Getter che recupera l'icona specifica per la funzione: forward.
   * @returns string con l'icona per il pulsante.
   */
  get forward(): string {
    // Richiamo la costante e compongo l'icon
    const icon = `${this.assets}${this.icons.paginatorForward}`;
    // Ritorno l'icona generata
    return icon;
  }

  /**
   * Pilota la visibilità della paginazione. è visibile per più di 10 elementi e se è definita correttamente una paginazione
   */
  get paginazioneVisibile() {
    return (
      this.paginazione &&
      this.paginazione.elementsForPage > 0 &&
      this.paginazione.total > this.paginazione.elementsForPage
    );
  }

  /**
   * Dice a che pagina siamo
   */
  get paginaCorrente() {
    return this.paginazione?.currentPage;
  }

  /**
   * Calcola numero di pagine per la ricerca corrente
   */
  get numPagine(): number {
    return Math.ceil(this.paginazione.total / this.paginazione.elementsForPage);
  }

  /**
   * Controlla se il numero di pagine è superiore alle pagine massime da mostrare
   */
  get overflowPagine() {
    return this.paginazione.maxPages < this.numPagine;
  }

  /**
   * Verifica se a destra della pagina corrente ci sono più pagine di quante ne mostro
   */
  get showDotsRight() {
    return (
      this.overflowPagine &&
      this.paginaCorrente + Math.floor((this.paginazione.maxPages - 1) / 2) <
        this.numPagine
    );
  }

  /**
   * Verifica se a destra della pagina corrente ci sono più pagine di quante ne mostro
   */
  get showDotsLeft() {
    return (
      this.overflowPagine &&
      this.paginaCorrente - Math.floor((this.paginazione.maxPages - 1) / 2) > 1
    );
  }
}
