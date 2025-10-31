/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
export const environment = {
  production: false,
  beServerPrefix: 'http://localhost:8080',
  beBasePath: '/scrivafoweb/api/v1',
  mockServer: 'http://localhost:3000',
  apiGWServerPrefix: 'https://api-manager.dominio.it:443/api',
  apiAccessToken: 'tokektoken-token-3456-ypkrn-tooooken',
  logoutUrlFO:
    'https://dev-....it/dev_scriva_443s_liv2_sispliv2spid_gasprp_ambiente/logout.do',
  logoutUrlBO:
    'https://dev-....it/dev_scriva_443s_liv2_sispliv2spid_gasprp_ambiente/logout.do',
  annullaUrlFO:
    'https://dev-....it/srv/scrivania-richiedente/',
  annullaUrlBO:
    'https://dev-.....it/srv/scrivania-funzionario/',
  serviziRegionePiemonteUrl:
    'https://dev-......it/scrivaconsweb/service-home',
  local: true,
  GDPR_docUrl:'https://dev-....it/scriva/',
  localEnv: {
    beHost: 'http://localhost:17000',
    fakeAuth: {
      enabled: true,
      headerName: 'Shib-Iride-IdentitaDigitale',
      // al cambio effettuare la logout al BE prima della modifica
      // typeProfile: 'BO', // // Richiedente 'FO' | 'BO'
      
      // typeProfile: 'FO', // // Richiedente 'FO' | 'BO'
      
      // typeProfile: 'BO', // Funzionario
      

      typeProfile: 'FO',
      headerValue:'CF/nome/cognome/SPID//4//',
      
      // typeProfile: 'BO', // Funzionario
          
      // typeProfile: 'FO',
      
      // copiare su headerValue uno dei seguenti token e aggiornare typeUser(riga 17)
      // typeProfile: 'BO', // Funzionario
            
      // typeProfile: 'BO', // Funzionario
      
    },
  },
};
