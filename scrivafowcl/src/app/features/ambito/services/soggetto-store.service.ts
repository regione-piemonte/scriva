/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Injectable } from '@angular/core';
import { Soggetto } from '../models';

@Injectable()
export class SoggettoStoreService {
  soggettoSnapshot: Partial<Soggetto>;
  richiedenteSnapshot: Partial<Soggetto>;
  constructor() {}
}
