/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'deepObject' })
export class DeepObjectPipe implements PipeTransform {

  transform(input: any): string {
    return input.denominazione;
  }
}
