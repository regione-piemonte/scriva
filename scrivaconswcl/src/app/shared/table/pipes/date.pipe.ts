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
import it from '@angular/common/locales/it';

@Pipe({
  name: 'date',
  pure: true,
})
export class DatePipe implements PipeTransform {
  transform(value: any): any {
    // for this type I assume always the same input type for date ---> yyyy-mm-ddThh:mm:ss.mssZ
    let tmp;
    // detect language ---> format date

    // detect type ---> make another method for Date
    if (typeof value === 'string') {
      const timestamp = value.split('T');
      const dateArray = timestamp[0].split('-');
      const dd = dateArray[2];
      const mm = dateArray[1];
      const yyyy = dateArray[0];
      const timeArray = timestamp[1].split('.')[0];
      tmp = dd + '/' + mm + '/' + yyyy + ' - ' + timeArray;
    }
    return tmp;
  }
}
