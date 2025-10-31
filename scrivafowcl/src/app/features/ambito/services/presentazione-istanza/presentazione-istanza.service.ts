
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
import { Subject } from 'rxjs';
import { StepCompleted } from './utilities/presentazione-istanza.interfaces';
import { IClickOnStepper } from 'src/app/shared/services/step-manager/utilities/step-manager.interfaces';
import { MessageService } from 'src/app/shared/services';
import { ICambiaStep } from 'src/app/shared/components/stepper-istanza/utilities/stepper-istanza.interfaces';

/**
 * Servizio di supporto al componente presentazione-istanza.component.ts.
 */
@Injectable({ providedIn: 'root' })
export class PresentazioneIstanzaService {
  // variabile per indicare cambio di valori
  private _changed: boolean;

  /*
   *Subject per la gestione dello stepper
   */
  selectStep$ = new Subject<number>();
  stepCompleted$ = new Subject<StepCompleted>();
  avantiClicked$ = new Subject();
  indietroClicked$ = new Subject();
  istanzaSubmitted$ = new Subject();
  checkSelectedStep$ = new Subject();

  showMostraAvanti$ = new Subject();
  showMostraIndietro$ = new Subject();

  /**
   * Costruttore
   */
  constructor(private messageService: MessageService) {}

  emitShowMostraAvantiSub(isShowMostraAvanti: boolean) {
    return this.showMostraAvanti$.next(isShowMostraAvanti);
  }

  emitShowMostraIndietroSub(isShowMostraIndietro: boolean) {
    return this.showMostraIndietro$.next(isShowMostraIndietro);
  }

  /**
   * Funzione per per emettere valore di step selezionato
   * @param stepIndex
   * @returns
   */
  emitSelectStepSub(stepIndex: number) {
    return this.selectStep$.next(stepIndex);
  }

  emitCheckSelectedStep(step: IClickOnStepper) {
    return this.checkSelectedStep$.next(step);
  }

  /**
   * Funzione per emettere step completato
   * @param stepCompleted
   * @returns
   */
  emitStepCompletedSub(stepCompleted: StepCompleted) {
    return this.stepCompleted$.next(stepCompleted);
  }

  /**
   * Funzione per emettere di andare avanti
   * @returns void
   */
  emitAvantiClickedSub(actualStep: number) {
    return this.avantiClicked$.next(actualStep);
  }
  /**
   * Funzione per emettere di andare indietro
   * @returns void
   */
  emitIndietroClickedSub(actualStep: IClickOnStepper) {
    return this.indietroClicked$.next(actualStep);
  }

  /**
   * Funzione per emettere istanza inviata
   * @returns void
   */
  emitIstanzaSubmittedSub() {
    return this.istanzaSubmitted$.next();
  }

  /**
   * Funzione che gestisce il flusso per il cambio dello step.
   * @param data ICambiaStep con le informazioni per gestire il cambio step.
   */
  clickOnStepper(data: ICambiaStep) {
    // Estraggo le informazioni id quadro
   const idQuadro = data.idQuadro;

    // Lancio la funzione per la gestione della conferma del cambio step
    this._confirmIfChanged(() => {
      // Lancio l'evento per la next dello step
      this.emitSelectStepSub(idQuadro);
    });
  }

  /**
   * Funzione per mostrare messaggio di conferma
   */
  private _confirmIfChanged(confirmFunction: any) {
    if (this.changed) {
      this.messageService.showConfirmation({
        title: 'Conferma annullamento modifiche',
        codMess: 'A050',
        buttons: [
          {
            label: 'ANNULLA',
            type: 'btn-link',
            callback: () => {},
          },
          {
            label: 'CONFERMA',
            type: 'btn-primary',
            callback: () => {
              confirmFunction();
            },
          },
        ],
      });
      return;
    }
    confirmFunction();
  }

  /**
   * ###############
   * GETTER & SETTER
   * ###############
   */

  set changed(v: boolean) {
    this._changed = v;
  }

  get changed(): boolean {
    return this._changed;
  }
}
