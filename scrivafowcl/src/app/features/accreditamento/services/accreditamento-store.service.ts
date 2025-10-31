/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { AppConfigService } from 'src/app/shared/services';

@Injectable()
export class AccreditamentoStoreService {
  constructor(private http: HttpClient, private config: AppConfigService) {}
}
