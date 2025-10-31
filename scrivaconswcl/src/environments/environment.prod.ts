/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { backendApi } from '../config/backend-api.conf';

export const environment = {
  production: true,
  iconBaseUrl: '/scrivaconsweb',
  auth: {
    ssoLogout: 'https://tst-xxx.spid.ente.it/gasprp_ambiente/logout.do'
  },
  fakeAuth: {
    enabled: false,
    header: {
      key: 'Shib-Iride-IdentitaDigitale',
      value: 'CF/nome/cognome/SPID//4//'
    }
  },
  backend: {
    environment: 'PROD',
    baseUrl: 'https://scriva-....it/scrivaconsweb',
    api: backendApi
  },
  logger: {
    level: 0, // all log
    hasRemote: false,
    remoteLogUrl: null,
    cache: false
  }
};
