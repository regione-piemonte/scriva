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

@Pipe({ name: 'fileDimension' })
export class FileDimensionPipe implements PipeTransform {
  transform(input: number): string {
/*     // from byte to Mb with 3 decimals
    return input
      ? (Math.round(input / 1000) / 1000).toString() + ' ' + 'Mb'
      : input.toString(); */
      let mb = input / 1048576;
      return mb.toFixed(2) + 'Mb';
  }
}
