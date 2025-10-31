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
  production: false,
  iconBaseUrl: '',
  auth: {
    ssoLogout: 'https://tst-xxx.spid.ente.it/gasprp_ambiente/logout.do'
  },
  fakeAuth: {
    enabled: false,
    header: {
      key: 'Shib-Iride-IdentitaDigitale',
      value: 'CF/Nome/Cognome/SPID//4//'
    }
  },
  backend: {
    environment: 'DEV',
    baseUrl: 'https://dev-scrivaconsweb.ente.it/scrivaconsweb',
    api: backendApi
  },
  logger: {
    level: 0, // all log
    hasRemote: false,
    remoteLogUrl: null,
    cache: false
  }
};
