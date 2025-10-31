/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**
 * Interfaccia che rappresenta le informazioni per gestire le logiche per: clickOnStepper.
 */
export interface IClickOnStepper {
  idQuadro: number;
  onNextStep?: (data?: any) => any;
}