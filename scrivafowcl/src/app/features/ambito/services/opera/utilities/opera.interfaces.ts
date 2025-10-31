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
 * Interfaccia che definisce le informazioni per il check delle autorità competenti.
 */
export interface ICheckACParams {
  idIstanza: number;
  calcoloAC3: boolean;
  tipoSelezioneAC: any;
}