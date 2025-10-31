/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Comune } from 'src/app/shared/models';

export interface CatastoSezione {
  comune: Comune;
  sezione: string;
  nome_sezione: string;
}
