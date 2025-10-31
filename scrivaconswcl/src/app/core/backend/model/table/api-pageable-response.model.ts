/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
export interface ApiResponse<T> extends Partial<ApiResponseError> {
  content: T;
}

export interface ApiListResponse<T> extends ApiResponse<T[]> {
}

export interface ApiPageableResponse<T> extends Partial<ApiResponseError> {
  content: T[];
  totalElements: number;
  totalPages: number;
}

export interface ApiResponseError {
  keyErrorMessage: string;
  response: {
    errorDescription: string;
  };
}
