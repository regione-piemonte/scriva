/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
export class CommonError {
  code: number;
  message: string;
  longMessage: string;
  friendlyMessage: string;
  url: string;
  body?: any;

  constructor(
      {
          code = null,
          message = null,
          longMessage = null,
          friendlyMessage = null,
          url = null,
          body = null
      } = {}
  ) {
      this.code = code;
      this.message = message;
      this.longMessage = longMessage;
      this.friendlyMessage = friendlyMessage;
      this.url = url;
      this.body = body;
  }
}
