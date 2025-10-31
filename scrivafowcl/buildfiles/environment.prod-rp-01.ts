/*
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
export const environment = {
  production: true,
  beServerPrefix: 'https://scriva.portale.ente.it',
  beBasePath: '/scrivafoweb/api/v1',
  logoutUrlFO: 'https://scriva-portale.ente.it/scriva_443s_liv3_sispliv2spid_gasprp_ambiente/logout.do',
  logoutUrlBO: 'https://scriva-portale.ente.it/scriva_443s_liv3_sispliv2spid_gasprp_ambiente/logout.do',
  annullaUrlFO: 'https://www.portale.ente.it/srv/scrivania-richiedente/',
  annullaUrlBO: 'https://www.portale.ente.it/srv/scrivania-funzionario/',
  serviziRegionePiemonteUrl: 'https://scriva-portaledev.ente.it/scrivaconsweb/service-home',
  local: false,
  GDPR_docUrl:'https://scriva-portaledev.ente.it/scriva/',
  localEnv: {
    beHost: 'http://localhost:8080',
    fakeAuth: {
      enabled: false,
      headerName: 'Shib-Iride-IdentitaDigitale',
      // al cambio effettuare la logout al BE prima della modifica
      //typeProfile: 'FO', // 'FO' | 'BO'
      typeProfile: 'BO',
      headerValue: 'CF/nome/cognome/SPID//4//'
      // copiare su headerValue uno dei seguenti token e aggiornare typeUser(riga 17)
      // BO: 
      // FO: 
    }
  }
};
