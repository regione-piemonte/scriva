/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import {
  HttpClient,
  HttpErrorResponse,
  HttpResponse
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject, throwError } from 'rxjs';
import { catchError, map, timeout } from 'rxjs/operators';
import { ApiClientConfig } from './models/api-client.model';
import { ApiConfig } from './models/api-config.model';
import { PathParams } from './models/path-params.model';

/**
 * ApiClient service help to invoke backend api
 */
@Injectable()
export class ApiClient {
  private config: ApiClientConfig;
  onError$: Subject<HttpErrorResponse> = new Subject();

  constructor(config: ApiClientConfig, private httpClient: HttpClient) {
    this.config = config;
  }

  private get _timeout(): number {
    return this.config.getBackendConfig().timeout;
  }

  /**
   * Globally handle response
   * @param response response of the requst
   * @returns response
   */
  private _handleSuccess<T>(response: T): T {
    return response;
  }

  /**
   * Globally handle response
   * @param response response of the requst
   * @returns response
   */
  private _handleSuccessWithHeader<T>(
    response: HttpResponse<T>
  ): HttpResponse<T> {
    return response;
  }

  /**
   * Globally handle error response
   * @param response response of the requst
   * @returns error to return
   */
  private _handleError(
    response: HttpErrorResponse
  ): Observable<HttpErrorResponse> {
    console.error(response);
    this.onError$.next(response);
    return throwError(response);
  }

  /**
   * Make a request
   * @param apiName the request's api name to invoke
   * @param body the request's body
   * @param queryParams the request's qury string params
   * @param pathParams the request's path params
   *
   * @returns the request's observable
   */
  request<T>(
    apiName: string,
    body?: any,
    queryParams?: any,
    pathParams?: PathParams
  ): Observable<T> {
    const apiConfig = this.config.getApiConfig(apiName);

    return this._request<T>(apiConfig, body, queryParams, pathParams).pipe(
      timeout(apiConfig.timeout || this._timeout),
      catchError((res: HttpErrorResponse) => this._handleError(res)),
      map((data: T) => this._handleSuccess<T>(data))
    );
  }

  private _request<T>(
    apiConfig: ApiConfig,
    body?: any,
    queryParams?: any,
    pathParams?: PathParams
  ): Observable<T> {
    return this.httpClient.request<T>(
      apiConfig.method,
      this._bindPathParams(apiConfig.url, pathParams),
      {
        headers: apiConfig.headers,
        observe: 'body',
        body: body || {},
        params: queryParams || null,
        responseType: 'json'
      }
    );
  }

  /**
   * Make a request
   * @param apiName the request's api name to invoke
   * @param body the request's body
   * @param queryParams the request's qury string params
   * @param pathParams the request's path params
   *
   * @returns the request's observable
   */
  requestWithHeader<T>(
    apiName: string,
    body?: any,
    queryParams?: any,
    pathParams?: PathParams
  ): Observable<HttpResponse<T>> {
    const apiConfig = this.config.getApiConfig(apiName);

    return this._requestWithHeader<T>(
      apiConfig,
      body,
      queryParams,
      pathParams
    ).pipe(
      timeout(apiConfig.timeout || this._timeout),
      catchError((res: HttpErrorResponse) => this._handleError(res)),
      map((data: HttpResponse<T>) => this._handleSuccessWithHeader<T>(data))
    );
  }

  private _requestWithHeader<T>(
    apiConfig: ApiConfig,
    body?: any,
    queryParams?: any,
    pathParams?: PathParams
  ): Observable<HttpResponse<T>> {
    return this.httpClient.request<T>(
      apiConfig.method,
      this._bindPathParams(apiConfig.url, pathParams),
      {
        headers: apiConfig.headers,
        observe: 'response',
        body: body || {},
        params: queryParams || null,
        responseType: 'json'
      }
    );
  }

  private _bindPathParams(url: string, params: PathParams = {}): string {
    let urlInternal = url;
    for (const key in params) {
      if (params[key]) {
        urlInternal = urlInternal.replace(
          new RegExp(`{${key}}`, 'g'),
          params[key]
        );
      }
    }
    return urlInternal;
  }

  /**
   * Return the path builded
   * @param apiName the api's name
   * @param pathParams the api's pathParams
   * @returns th path compiled
   */
  getUrlByApiName(apiName: string, pathParams?: PathParams): string {
    return this._bindPathParams(
      this.config.getApiConfig(apiName).url,
      pathParams
    );
  }
}
