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
 * Interfaccia di supporto che definisce le configurazioni per la gestione dell'icona.
 */
export interface IScrivaIcon {
  icon: string;
  alt: string;
  label?: string;
  asset?: boolean;
  customCss?: string | any;
}
