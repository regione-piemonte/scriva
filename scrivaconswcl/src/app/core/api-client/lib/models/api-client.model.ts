/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { BackendConfig } from './backend-config.model';
import { ApiConfig } from './api-config.model';

export class ApiClientConfig {
  backend: Partial<BackendConfig>;

  constructor(config: Partial<ApiClientConfig>) {
    this.backend = new BackendConfig(config.backend);
  }

  /**
   * Get api configuration from the environment file
   * @param apiName string Attribute name of requested api
   * @returns api config
   */
  getApiConfig(apiName: string): ApiConfig | null {
    return this.backend.getApiConfig(apiName);
  }

  /**
   * Get api configuration from the environment file
   * @returns backend config
   */
  getBackendConfig(): Partial<BackendConfig> {
    return this.backend;
  }
}
