/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import {
  IScrivaServerError,
  IScrivaServerErrorInfo,
} from '../interfaces/scriva.interfaces';
import { HttpHeaders } from '@angular/common/http';

/**
 * Class che definisce la struttura di gestione degli errori da server.
 * Questa classe di base è stata pensata come estensione dell'oggetto: HttpErrorResponse; ma per comodità non viene dichiarata esplicamente l'extends della classe.
 */
export class ScrivaServerError implements IScrivaServerError {
  /** ScrivaServerErrorDetail con il dettaglio degli errori */
  error?: IScrivaServerErrorInfo;
  headers?: HttpHeaders;
  message?: string;
  name?: string;
  ok?: boolean;
  status?: number;
  statusText?: string;
  url?: string;
  [key: string]: any;

  constructor(c?: IScrivaServerError) {
    this.error = c?.error;
    this.headers = c?.headers;
    this.message = c?.message;
    this.name = c?.name;
    this.ok = c?.ok;
    this.status = c?.status;
    this.statusText = c?.statusText;
    this.url = c?.url;
  }
}
