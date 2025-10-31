/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
// This file can be replaced during build by using the `fileReplacements` array.
// `ng build ---prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
// *******************************************************************
// Anche nel serve in locale si utilizza environment.local della cartella buildfiles
// *******************************************************************

export const environment = {
  production: false,
  beServerPrefix: 'http://localhost:17000', // local server
  beBasePath: '/scrivafoweb/api/v1',
  mockServer: 'http://localhost:3000',
  apiGWServerPrefix: 'https://api-pmanager.....it:443/api',
  apiAccessToken: '512345toke-token6789',
  logoutUrlFO: 'https://dev-........it/dev_scriva_443s_liv2_sispliv2spid_gasprp_ambiente/logout.do',
  logoutUrlBO: 'https://dev-.....it/dev_scriva_443s_liv2_sispliv2spid_gasprp_ambiente/logout.do',
  serviziRegionePiemonteUrl: 'https://dev-.....it/scrivaconsweb/service-home',
  local: true,
  GDPR_docUrl:'https://dev-.....it/scriva/',
  localEnv: {
    beHost: 'http://localhost:8080',
    fakeAuth: {
      enabled: true,
      headerName: 'Shib-Iride-IdentitaDigitale',
      // al cambio effettuare la logout al BE prima della modifica
      //typeProfile: 'FO', // 'FO' | 'BO'
      typeProfile: 'BO',
      headerValue: 'CF/Nome/cognome/SPID//4//'
      }
  }
};

