/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { TestBed, inject } from '@angular/core/testing';

import { I18nService } from './i18n.service';

describe('I18nService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [I18nService]
    });
  });

  it('should be created', inject([I18nService], (service: I18nService) => {
    expect(service).toBeTruthy();
  }));
});
