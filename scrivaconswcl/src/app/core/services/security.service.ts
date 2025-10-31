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
import { ApiClient } from '@eng-ds/api-client';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { UserInfo } from '@app/core';

@Injectable()
export class SecurityService {
  private _user$: BehaviorSubject<UserInfo> = new BehaviorSubject<UserInfo>(
    null
  );

  constructor(private apiClient: ApiClient) {}

  getUserProfile(): Observable<UserInfo> {
    return this.apiClient.request('getUserProfile').pipe(
      catchError((err) => {
        return throwError(err);
      }),
      map((response: any) => {
        return response;
      })
    );
  }

  initUserInfo() {
    return this.resolvePromise();
  }

  resolvePromise() {
    let promiseResolve: any;
    const userPromise = this.getUserProfile().toPromise();
    const promise = new Promise((resolve) => {
      promiseResolve = resolve;
    });
    userPromise
      .then((response) => this._user$.next(response))
      .catch()
      .finally(() => promiseResolve());
    return promise;
  }

  getUser(): Observable<UserInfo> {
    return this._user$.asObservable();
  }

  onLogout() {
    this._user$.next(null);
  }
}
