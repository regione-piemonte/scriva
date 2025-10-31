/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { NbAclOptions } from '@nebular/security';

export const acl: NbAclOptions = {
  accessControl: {
    USER: {
      view: '*',
      create: '*',
      edit: '*',
      remove: '*',
      home: ['home'],
      viewUser: ['info']
    },
    GUEST: {
      view: ['procedimenti/ricerca', 'menu-item'],
      create: [],
      edit: '*',
      remove: '*',
      home: ['service-home'],
      viewUser: ['login']
    }
  }
};
