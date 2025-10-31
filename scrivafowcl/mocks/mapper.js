// Copyright Regione Piemonte - 2025
// SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
// SPDX-License-Identifier: EUPL-1.2
// ============LICENSE_END===================
module.exports = {
  get: {
    '/api/ambiti/ambiti': '/api/ambiti',

    '/api/procedimenti/procedimenti': '/api/procedimenti/',
    '/api/procedimenti/procedimenti-ambito': '/api/procedimenti/id-ambito/:id',

    '/api/tipi-soggetto/tipi-soggetto': '/api/tipi-soggetto',

    '/api/tipi-natura-giuridica/tipi-natura-giuridica': '/api/tipi-natura-giuridica',

    '/api/ruoli-compilante/ruoli-compilante': '/api/ruoli-compilante',

    '/api/ruoli-soggetto/ruoli-soggetto': '/api/ruoli-soggetto',

    '/api/soggetti/soggetto': '/api/soggetti/cf/:cf/tipo-soggetto/:idtiposoggetto/tipo-procedimento/:idprocedimento',

    '/api/form/testform': '/api/form/testform',
    '/api/form/searchForm': '/api/form/searchForm',
    '/api/form/simpleform': '/api/form/simpleform',
    '/api/form/wizardform': '/api/form/wizardform',
    '/api/form/dataGridForm': '/api/form/dataGridForm',
    '/api/form/wizardNuovaVia': '/api/form/wizardNuovaVia',

    '/api/form/formNuovaViaSoggetti': '/api/form/formNuovaViaSoggetti',
    '/api/form/operaForm': '/api/form/operaForm',

    '/api/location/comuni-pg': '/api/location/comuni/perugia',
    '/api/location/comuni-tr': '/api/location/comuni/terni',
    '/api/location/province': '/api/location/province',

    '/api/ping/ping': '/api/ping',

    '/api/users/users': '/api/users/users',

    '/api/opere/opere': '/api/opere',
  },
  put: {},
  post: {},
  patch: {},
  delete: {},
};
