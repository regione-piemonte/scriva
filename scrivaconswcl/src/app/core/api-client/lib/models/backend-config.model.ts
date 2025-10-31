/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { ApiConfig } from './api-config.model';

export class BackendConfig {
  baseUrl: string;
  api: ApiConfig[];

  timeout?: number;

  constructor(backend: Partial<BackendConfig>) {
    this.baseUrl = backend.baseUrl;
    this.api = (backend.api as ApiConfig[]).map(
      (a: ApiConfig) => new ApiConfig(a)
    );
    this.timeout = backend.timeout || 3000;
  }

  getApiConfig(apiName: string): ApiConfig | null {
    let api;
    try {
      // tslint:disable-next-line:no-shadowed-variable
      api = this.api.find((api: ApiConfig) => api.name === apiName);
      api.url = this.prepareUrl(api.url);
    } catch (err) {
      api = null;
    }
    return api;
  }

  /**
   * Add baseUrl as prefix if the api url is relative
   * @param url Relative api url
   */
  prepareUrl(url: string): string {
    if (url.trim().indexOf('http') !== 0) {
      url = (this.baseUrl + url).trim();
    }
    return url;
  }
}
