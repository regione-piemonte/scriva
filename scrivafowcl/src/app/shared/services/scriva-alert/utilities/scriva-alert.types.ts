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
 * Type che definisce la tipologia delle informazioni per il replace nei messaggi.
 */
export type TScrivaDataPlaceholder =
  | string
  | number
  | string[]
  | number[]
  | (string | number)[];

/**
 * Type che definisce la tipologia delle informazioni per il replace nei messaggi, come array.
 */
export type TScrivaDataPlaceholders = TScrivaDataPlaceholder[];