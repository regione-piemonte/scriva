/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Injectable, Optional } from '@angular/core';
import { LogLevel } from './models/log-level.model';
import { LoggerConfig } from './models/logger.model';

@Injectable()
export class Logger {
  level: LogLevel;

  constructor(@Optional() config: LoggerConfig) {
    this.setConfig(config);
  }

  setConfig(config?: LoggerConfig): void {
    this.level = (config && config.level) || LogLevel.all;
  }

  log(caller: any, ...message: any[]): void {
    if (LogLevel.debug >= this.level) {
      // tslint:disable-next-line:no-console
      console.log(this.formatLogMessage(caller), ...message);
    }
  }

  info(caller: any, ...message: any[]): void {
    if (LogLevel.info >= this.level) {
      // tslint:disable-next-line:no-console
      console.info(this.formatLogMessage(caller), ...message);
    }
  }

  warn(caller: any, ...message: any[]): void {
    if (LogLevel.warning >= this.level) {
      // tslint:disable-next-line:no-console
      console.warn(this.formatLogMessage(caller), ...message);
    }
  }

  error(caller: any, ...message: any[]): void {
    if (LogLevel.error >= this.level) {
      // tslint:disable-next-line:no-console
      console.error(this.formatLogMessage(caller), ...message);
    }
  }

  formatLogMessage(caller: any, type?: string): string {
    const cDate: Date = new Date();
    const line: string = this._getLine();
    const callerName: string =
      caller.constructor.name === 'String' ? caller : caller.constructor.name;
    const logType = type ? `type: ${type}: ` : '';
    // tslint:disable-next-line:max-line-length
    return `[${this._fillZero(cDate.getHours())}:${this._fillZero(
      cDate.getMinutes()
    )}:${this._fillZero(
      cDate.getSeconds()
    )} | ${callerName}: ${line}] ${logType} `;
  }

  private _fillZero(input: number): string {
    return input < 10 ? `0${input}` : String(input);
  }

  private _getLine(): string {
    let line = '';
    try {
      line = new Error().stack.split('\n')[4];
      line =
        line.indexOf(' (') >= 0
          ? line.split(' (')[1].substring(0, line.length - 1)
          : line.split('at ')[1];
      line =
        line.lastIndexOf(')') !== -1
          ? line.substring(0, line.lastIndexOf(')'))
          : line;
    } catch (e) {
      try {
        line = new Error().stack.split('\n')[4].split('@')[1];
      } catch (ignore) {
      } // for IE
    }

    return line;
  }
}
