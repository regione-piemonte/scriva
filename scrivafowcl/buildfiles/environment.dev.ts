/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
export const environment = {
  production: false,
  beServerPrefix: 'https://dev-portale.ente.it',
  beBasePath: '/scrivafoweb/api/v1',
  mockServer: 'http://localhost:3000',
  apiGWServerPrefix: 'https://api-manager.dominio.it:443/api',
  apiAccessToken: 'token-token-token-token-token',
  logoutUrlFO: 'https://portaledev.ente.it/dev_scriva_443s_liv3_sispliv2spid_gasprp_ambiente/logout.do',
  logoutUrlBO: 'https://dev-scrivabo-servizi.regione.piemonte.it/dev_scriva_bo_443sliv3ireg/Shibboleth.sso/Logout',
  annullaUrlFO: 'https://dev-www.portaledev.ente.it/srv/scrivania-richiedente/',
  annullaUrlBO: 'https://dev-www.portaledev.ente.it/srv/scrivania-funzionario/',
  serviziRegionePiemonteUrl: 'https://portaledev.ente.it/scrivaconsweb/service-home',
  local: false,
  GDPR_docUrl:'https://portaledev.ente.it/scriva/',
  localEnv: {
    beHost: 'http://localhost:8080',
    fakeAuth: {
      enabled: false,
      headerName: 'Shib-Iride-IdentitaDigitale',
      // al cambio effettuare la logout al BE prima della modifica
      //typeProfile: 'FO', // 'FO' | 'BO'
      typeProfile: 'BO',
      headerValue: 'CF/nome/cognome/SPID//4//'
      
    }
  },
};
