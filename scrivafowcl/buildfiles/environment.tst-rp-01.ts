/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
export const environment = {
  production: true,
  beServerPrefix: 'https://tst-....it',
  beBasePath: '/scrivafoweb/api/v1',
  mockServer: 'http://localhost:3000',
  apiGWServerPrefix: 'https://tst-api-pmanager.dominio.it:443/api',
  apiAccessToken: 'tokentoken-token-token-token-tokentoken',
  logoutUrlFO: 'https://tst-.....it/tst_scriva_443s_liv3_sispliv2spid_gasprp_ambiente/logout.do',
  logoutUrlBO: 'https://tst-.....it/tst_scriva_bo_443sliv3ireg/Shibboleth.sso/Logout',
  annullaUrlFO: 'https://tst-....it/srv/scrivania-richiedente/',
  annullaUrlBO: 'https://tst-....it/srv/scrivania-funzionario/',
  serviziRegionePiemonteUrl: 'https://tst-....it/scrivaconsweb/service-home',
  local: false,
  GDPR_docUrl:'https://tst-....it/scriva/',
  localEnv: {
    beHost: 'http://localhost:8080',
    fakeAuth: {
      enabled: false,
      headerName: 'Shib-Iride-IdentitaDigitale',
      // al cambio effettuare la logout al BE prima della modifica
      //typeProfile: 'FO', // 'FO' | 'BO'
      typeProfile: 'BO',
      headerValue: 'CF/Nome/cognome/SPID//4//'
      //
      // copiare su headerValue uno dei seguenti token e aggiornare typeUser(riga 17)
      // BO: 
      // FO: 
    }
  }
};
