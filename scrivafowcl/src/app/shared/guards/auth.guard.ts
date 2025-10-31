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
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanLoad,
  Route,
  Router,
  RouterStateSnapshot,
  UrlSegment,
} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthStoreService } from '../services';
import { AuthService } from '../services/auth/auth.service';

@Injectable()
export class AuthGuard implements CanLoad, CanActivate {
  constructor(
    private router: Router,
    private authService: AuthService,
    private authStoreService: AuthStoreService
  ) {}

  canLoad(
    route: Route,
    segments: UrlSegment[]
  ): boolean | Observable<boolean> | Promise<boolean> {
    if (
      this.authStoreService.hasCompilante() ||
      this.authStoreService.hasFunzionario()
    ) {
      return true;
    } else {
      return false;
    }
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (
      this.authStoreService.hasCompilante() ||
      this.authStoreService.hasFunzionario()
    ) {
      return true;
    } else {
      // todo: this else is not correct for BO
      // this.authService.getCompilanteBySession().subscribe(
      //   res => {
      //     this.authStoreService.setCompilante(res);
      //     return true;
      //   }, err => {
      //     console.log(err);
      //     return false;
      //   }
      // );
      return false;
    }
  }
}
