/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */



/**Interfaccia per gli step completati */
export interface StepCompleted {
    stepComponent: string;
    completed: boolean;
    idQuadro: number | null;
  }
  