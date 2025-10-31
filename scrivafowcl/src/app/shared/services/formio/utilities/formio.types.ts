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
  IFormioFormBuilderContainer,
  IFormioFormBuilderHidden,
  IFormioFormBuilderHTMLElement,
  IFormioFormBuilderPanel,
} from './formio.interfaces';

/**
 * Tipizzazione dei tipi di oggetti che possono rappresentare dei componenti FormIo, intesi come oggetti che il builder traduce.
 */
export type FormioFormBuilderTypes =
  | IFormioFormBuilderPanel
  | IFormioFormBuilderHidden
  | IFormioFormBuilderContainer
  | IFormioFormBuilderHTMLElement
  | any;
