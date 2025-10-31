/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Component, Input } from '@angular/core';
import { AutoUnsubscribe } from '../../../../../core';
import { BaseInput } from '../../../models';

@Component(
  {
    template: ``
  }
)
export class BaseInputComponent extends AutoUnsubscribe {
  @Input() control?: BaseInput<any>;
}
