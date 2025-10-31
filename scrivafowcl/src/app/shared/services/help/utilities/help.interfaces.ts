/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ScrivaComponenteApp } from '../../../enums/scriva-utilities/scriva-utilities.enums';
import { Adempimento, Quadro } from '../../../models';

/**
 * Interfaccia che definisce le configurazioni per lo scarico degli helper per una maschera.
 */
export interface IChiaveHelpPrimitiveParams {
  componente: ScrivaComponenteApp;
  codTipoAdempimento: string;
  codAdempimento: string;
  codQuadro: string;
  chiave?: string;
}

/**
 * Interfaccia che definisce le configurazioni per lo scarico degli helper per una maschera.
 */
export interface IChiaveHelpParams {
  componente: ScrivaComponenteApp;
  adempimento: Adempimento;
  quadro: Quadro;
  chiave?: string;
}
