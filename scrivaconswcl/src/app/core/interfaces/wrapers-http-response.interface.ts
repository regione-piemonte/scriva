/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
interface WrapperResponse<T> {
  timestamp: number;
  status: number;
  exceptionCode: number;
  exceptionMessage: string;
  path: string;
  result: T & WrapperResult;
}

interface WrapperResult {
  className: string;
}

export { WrapperResponse };
