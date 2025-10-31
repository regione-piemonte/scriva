/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { ApiConfig } from '@eng-ds/api-client';

export const backendApi: ApiConfig[] = [
  {
    name: 'getBookings',
    method: 'GET',
    url: 'http://localhost:4200/assets/mocks/booking/bookings.json'
  },
  {
    name: 'getBookingDetail',
    method: 'GET',
    url: 'http://localhost:4200/assets/mocks/booking/booking-detail.json'
  },
  {
    name: 'createBooking',
    method: 'GET',
    // TODO change to POST method. Used GET only for example purpose
    url: 'http://localhost:4200/assets/mocks/booking/booking-create-response.json'
  },
  {
    name: 'updateBooking',
    method: 'GET',
    // TODO change to PUT method. Used GET only for example purpose
    url: 'http://localhost:4200/assets/mocks/booking/booking-edit-response.json'
  },
  {
    name: 'deleteBooking',
    method: 'GET',
    // TODO change to DELETE method. Used GET only for example purpose
    url: 'http://localhost:4200/assets/mocks/booking/booking-delete-response.json'
  },
  {
    name: 'getAllegatiMock',
    method: 'GET',
    url: 'http://localhost:4200/assets/mocks/practices/allegati-list.json'
  },

  // API SCRIVA
  {
    name: 'getNotes',
    method: 'GET',
    url: '/api/v1/note-pubblicate'
  },
  {
    name: 'getNotesMock',
    method: 'GET',
    url: 'http://localhost:4200/assets/mocks/note/note.json'
  },
  {
    name: 'getOsservazioni',
    method: 'GET',
    url: '/api/v1/secure/osservazioni'
  },
  {
    name: 'trackEv',
    method: 'POST',
    url: '/api/v1/eventi'
  },
  {
    name: 'trackEvent',
    method: 'POST',
    url: '/api/v1/secure/eventi'
  },
  {
    name: 'getUserProfile',
    method: 'GET',
    url: '/api/v1/secure/ping/spid'
  },
  {
    name: 'getAmbiti',
    method: 'GET',
    url: '/api/v1/ambiti'
  },
  {
    name: 'getAdempimenti',
    method: 'GET',
    url: '/api/v1/tipi-adempimento'
  },
  {
    name: 'getAdempimentiDetail',
    method: 'GET',
    url: 'http://localhost:4200/assets/mocks/adempimenti/adempimenti-detail.json'
  },
  {
    name: 'getAdempimentiByAmbito',
    method: 'GET',
    url: '/api/v1/tipi-adempimento'
  },
  {
    name: 'getAdempimentiConfig',
    method: 'GET',
    url: '/api/v1/adempimenti-config'
  },
  {
    name: 'getAdempimentiByCod',
    method: 'GET',
    url: '/api/v1/adempimenti'
  },
  {
    name: 'getPracticesList',
    method: 'POST',
    url: '/api/v1/istanze-pubblicate/_search'
  },
  {
    name: 'getPracticeDetail',
    method: 'POST',
    url: '/api/v1/istanze-pubblicate'
  },
  {
    name: 'getMapDetail',
    method: 'GET',
    url: '/api/v1/istanze-pubblicate/mappe/{id}'
  },
  {
    name: 'getMapDetailMock',
    method: 'GET',
    url: 'http://localhost:4200/assets/mocks/map/geojson.json'
  },
  {
    name: 'getCompetenzeTerritorio',
    method: 'GET',
    url: '/api/v1/competenze-territorio'
  },
  {
    name: 'limitiAmministrativiProvince',
    method: 'GET',
    url: '/api/v1/limiti-amministrativi/province'
  },
  {
    name: 'limitiAmministrativiComuni',
    method: 'GET',
    url: '/api/v1/limiti-amministrativi/comuni'
  },
  {
    name: 'stati-istanza',
    method: 'GET',
    url: '/api/v1/stati-istanza'
  },
  {
    name: 'getFilteredTipoAdempimento',
    method: 'GET',
    url: '/api/v1/tipi-adempimento/{id_tipo_adempimento}'
  },
  {
    name: 'getFilteredAmbiti',
    method: 'GET',
    url: '/api/v1/ambiti/{id_ambito}'
  },
  {
    name: 'getCategorieProgettualiByIstanza',
    method: 'GET',
    url: '/api/v1/categorie-progettuali/id-oggetto-istanza/{id_oggetto_istanza}'
  },
  {
    name: 'getCategorieProgettualiByAdempimento',
    method: 'GET',
    url: '/api/v1/categorie-progettuali/id-adempimento/{id_adempimento}'
  },
  {
    name: 'getVincoliIstanza',
    method: 'GET',
    url: '/api/v1/istanza-vincoli-aut'
  },
  {
    name: 'adempimenti',
    method: 'GET',
    // TODO create mock for get tipi_adempimento by cod_ambito
    url: 'http://localhost:4200/assets/mocks/competenze-territorio/competenze-territorio.json'
  },
  // {
  //   name: 'getAllegatiList',
  //   method: 'GET',
  //   url: 'http://localhost:4200/assets/mocks/practices/allegati-list.json'
  // },
  {
    name: 'getHelp',
    method: 'GET',
    url: '/api/v1/help'
  },
  {
    name: 'getCategoriaAllegato',
    method: 'GET',
    url: '/api/v1/categorie-allegato'
  },
  {
    name: 'getTipologieAllegatoByCodAdCodCat',
    method: 'GET',
    url: '/api/v1/tipologie-allegato/code-adempimento/{codAdempimento}/code-categoria/{codCategoria}'
  },
  {
    name: 'getRiepilogoAllegati',
    method: 'POST',
    url: '/api/v1/allegati/_search'
  },
  {
    name: 'postOsservazioni',
    method: 'POST',
    url: '/api/v1/secure/osservazioni'
  },
  {
    name: 'deleteOsservazioni',
    method: 'DELETE',
    url: '/api/v1/secure/osservazioni/{id}'
  },
  {
    name: 'postAllegati',
    method: 'POST',
    url: '/api/v1/secure/multipart/allegati'
  },
  {
    name: 'deleteAllegato',
    method: 'DELETE',
    url: '/api/v1/secure/allegati'
  },
  {
    name: 'putOsservazioni',
    method: 'PUT',
    url: '/api/v1/secure/osservazioni'
  },
  {
    name: 'downloadModuloOsservazioni',
    method: 'GET',
    url: '/api/v1/secure/istanze/{id_istanza}/_download'
  },
  {
    name: 'getAllegatiList',
    method: 'POST',
    url: '/api/v1/allegati/_search'
  },
  {
    name: 'downloadAllegati',
    method: 'GET',
    url: '/api/v1/allegati/_download'
  },
  {
    name: 'downloadAllegatiSelezionati',
    method: 'POST',
    url: '/api/v1/allegati/_download'
  },
  {
    name: 'getAttiFinali',
    method: 'POST',
    url: '/api/v1/allegati/_search'
  },
  {
    name: 'getTipiAllegatiByAdempimento',
    method: 'GET',
    url: '/api/v1/estensioni-allegato/code-adempimento/{codeAdempimento}'
  },
  {
    name: 'getStatiProcedimentoStatale',
    method: 'GET',
    url: '/api/v1/stati-procedimento-statale'
  },
  {
    name: 'localLogout',
    method: 'GET',
    url: '/api/v1/compilanti/logout'
  },
  {
    name: 'getUserBySession',
    method: 'GET',
    url: '/api/v1/compilanti/user'
  },
  {
    name: 'getCaptcha',
    method: 'GET',
    url: '/api/v1/captcha'
  },
  {
    name: 'validateCaptcha',
    method: 'POST',
    url: '/api/v1/captcha/_validate'
  },
  {
    name: 'messaggi',
    method: 'GET',
    url: '/api/v1/messaggi'
  }
];
