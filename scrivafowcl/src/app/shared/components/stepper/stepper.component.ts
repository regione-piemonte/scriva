/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { CdkStep, CdkStepper } from '@angular/cdk/stepper';
import {
  ChangeDetectorRef,
  Component, OnInit
} from '@angular/core';
import { PresentazioneIstanzaService } from 'src/app/features/ambito/services/presentazione-istanza/presentazione-istanza.service';
import { Directionality } from '@angular/cdk/bidi';
import { ICambiaStep } from '../stepper-istanza/utilities/stepper-istanza.interfaces';

@Component({
  selector: 'app-stepper',
  templateUrl: './stepper.component.html',
  styleUrls: ['./stepper.component.scss'],
  providers: [{ provide: CdkStepper, useExisting: StepperComponent }],
})
export class StepperComponent extends CdkStepper implements OnInit {
  /*
  componente usato solo in presentazione istanza per implementare passaggi tra i vari step
  */

  showNextButton: boolean = true;
  showBackButton: boolean = true;

  constructor(
    private _presentazioneIstanzaService: PresentazioneIstanzaService,
    _dir: Directionality,
    _changeDetectorRef: ChangeDetectorRef
  ) {
    super(_dir, _changeDetectorRef);
  }

  ngOnInit() {
    this.setVisibilityButton();
  }

  setVisibilityButton() {
    this._presentazioneIstanzaService.showMostraAvanti$.subscribe(
      (showMostraAvanti: boolean) => {
        this.showNextButton = showMostraAvanti;
      }
    );

    this._presentazioneIstanzaService.showMostraIndietro$.subscribe(
      (showMostraIndietro: boolean) => {
        this.showBackButton = showMostraIndietro;
      }
    );
  }

  scroll(element: HTMLElement, direction: number) {
    element.scrollLeft += 100 * direction;
  }

  canScrollStart(element: HTMLElement) {
    return element.scrollLeft > 0;
  }

  canScrollEnd(element: HTMLElement) {
    return element.scrollLeft + element.clientWidth !== element.scrollWidth;
  }

  /**
   *
   * @param step CdkStep con il campo state utilizzato in modo improprio per mappare dati che servono a noi
   * per questo castato anche ad any
   */
  click(step: CdkStep | any) {
    const data = this._createDataStep(step);
    if (data) {
      // Emetto che è stato cliccato uno step e invio i dati del quadro e l'evento per andare allo step
      this._presentazioneIstanzaService.emitCheckSelectedStep(data);
    }
  }
  private _createDataStep(step: CdkStep | any): ICambiaStep | null {
    const position: IPositionStepClicked | null =
      this.stepClickedPosition(step);
    if (position) {
      // Verifico se esiste l'id quadro dallo step
      if (step.state?.id_quadro) {
        // Definisco le informazioni per la funzione
        const data: ICambiaStep = {
          idQuadro: step.state.id_quadro,
          stepClickedPosition: position,
          stepArrayLength: this.steps.length,
          clickedIndex: this.steps.toArray().indexOf(step),
          currentIndex: this.steps.toArray().indexOf(this.selected),
        };
        return data;
      }
    }

    return null;
  }

  private stepClickedPosition(
    step: CdkStep | any
  ): IPositionStepClicked | null {
    const stepArray = this.steps.toArray();
    const clickedIndex = stepArray.indexOf(step);
    const currentIndex = this.steps.toArray().indexOf(this.selected);
    if (clickedIndex === this.steps.length - 1) {
      return IPositionStepClicked.LAST;
    }
    if (clickedIndex < currentIndex) {
      //Step cliccato è prima di quello attuale
      return IPositionStepClicked.BEFORE;
    }
    if (clickedIndex > currentIndex) {
      //Step cliccato è dopo di quello attuale
      return IPositionStepClicked.AFTER;
    }
    if (clickedIndex === currentIndex) {
      //Step cliccato è uguale di quello attuale
      return IPositionStepClicked.EQUAL;
    }
    return null;
  }

  // Trova uno step tramite indice
  private _getStepByIndex(index: number): CdkStep | undefined {
    const stepArray = this.steps.toArray();
    return stepArray[index]; // Ritorna lo step corrispondente
  }

  onAvanti() {
    const step = this._getStepByIndex(this.selectedIndex + 1);
    const dataStep = this._createDataStep(step);
    this._presentazioneIstanzaService.emitAvantiClickedSub(dataStep.idQuadro);
  }

  onIndietro() {
    const prevStep = this._getStepByIndex(this.selectedIndex - 1);
    const stepToSend = this._createDataStep(prevStep);
    this._presentazioneIstanzaService.emitIndietroClickedSub(stepToSend);
  }
}

export enum IPositionStepClicked {
  BEFORE = 'BEFORE',
  AFTER = 'AFTER',
  EQUAL = 'EQUAL',
  LAST = 'LAST',
}
