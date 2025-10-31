/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ScrivaInfoLevels } from "../../../enums/scriva-utilities/scriva-utilities.enums";

/**
 * Tipizzazione che definisce il template gestito dal componente "risca-alert"
 */
export type TScrivaAlertType =
  | ScrivaInfoLevels.none
  | ScrivaInfoLevels.success
  | ScrivaInfoLevels.danger
  | ScrivaInfoLevels.info
  | ScrivaInfoLevels.warning;
