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

@Pipe({
 name: 'truncate'
})

export class TruncateTextPipe implements PipeTransform {

transform(value: string, limit: number = 50): string {
    // const limit = 50;
    const trail = '[...]';
    return value?.length > limit ? value.substring(0, limit) + trail : value;
   }
}
