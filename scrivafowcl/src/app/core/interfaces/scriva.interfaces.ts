/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { HttpHeaders } from "@angular/common/http";

/**
 * Interfaccia che definisce un oggetto dinamico formato da chiave: string, e valore: any.
 */
export interface DynamicObjAny {
  [key: string]: any;
}

/**
 * Interfaccia che definisce un oggetto dinamico formato da chiave: string, e valore di tipo: T.
 */
export interface DynamicObjType<T> {
  [key: string]: T;
}

/**
 * Interfaccia che definisce un oggetto dinamico formato da chiave: string, e valore: string.
 */
export interface DynamicObjString {
  [key: string]: string;
}

/**
 * Interfaccia che definisce un oggetto dinamico formato da chiave: string, e valore: boolean.
 */
export interface DynamicObjBoolean {
  [key: string]: boolean;
}

/**
 * Interfaccia che definisce la tipologia di errore che viene ritornata dal server per gli errori sulle chiamate alle API.
 * L'interfaccia va a gestire una HttpRequest in errore (HttpErrorResponse), definendo al suo interno un eventuale errore generato dal server.
 */
export interface IScrivaServerError extends DynamicObjAny {
  error?: IScrivaServerErrorInfo;
  // Le proprietà sotto sono ridefinite partendo dall'oggetto "HttpErrorResponse"
  headers?: HttpHeaders;
  message?: string;
  name?: string;
  ok?: boolean;
  status?: number;
  statusText?: string;
  url?: string;
}

/**
 * Interfaccia che definisce le informazioni dell'errore che viene ritornata dal server per gli errori sulle chiamate alle API.
 */
export interface IScrivaServerErrorInfo {
  status?: string;
  code?: string;
  title?: string;
  detail?: IScrivaServerErrorDetail;
  links?: string[];
}

/**
 * Interfaccia che definisce il dettaglio delle info dell'errore che viene ritornata dal server per gli errori sulle chiamate alle API.
 */
export interface IScrivaServerErrorDetail extends DynamicObjAny {}
