/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { NbRoleProvider } from '@nebular/security';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SecurityService } from '@core/services/security.service';
import { map } from 'rxjs/operators';
import { Role } from '@app/core/auth/role.enum';
import { UserInfo } from '@app/core';

@Injectable()
export class NbSimpleRoleProvider extends NbRoleProvider {

  roles: string[] = [Role.GUEST, Role.USER];

  constructor(private userService: SecurityService) {
    super();
  }

  getRole(): Observable<string> {
    return this.userService.getUser().pipe(
      map((user: UserInfo) => {
        if (!user) {
          return Role.GUEST;
        }
        return Role.USER;
      })
    );
  }
}
