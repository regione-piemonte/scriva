/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Pipe, PipeTransform } from '@angular/core';
import { Quadro } from '../../../../shared/models';

/**
 * Pipe dedicata alla sezione presentazione-istanza.
 */
@Pipe({ name: 'loadStepperComponent' })
export class LoadStepperComponentPipe implements PipeTransform {
  /**
   * Costruttore del Pipe.
   */
  constructor() {}

  /**
   * Funzione recupera il componente specifico di uno step dello stepper.
   * @param componentsForStepper any che definisce tutti i componenti caribili per lo stepper.
   * @param step Quadro con le informazioni per il caricamento del componente per il Quadro.
   * @returns any con la referenza al componente da caricare per lo step.
   */
  transform(componentsForStepper: any, step: Quadro): any {
    // # NOTA: Considero le strutture stabili, non faccio controlli
    return componentsForStepper[step.json_configura_quadro.componentName];
  }
}
