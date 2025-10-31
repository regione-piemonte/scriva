/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { CheckFn, InputStatus } from './input-status';
import { StatusMessage } from './status-message';
import { ValidationErrorStatus } from './validation-error-status';

/* tslint:disable:semicolon */
export class ValidationStatus {

  public static ERROR = ValidationErrorStatus;
  public static BASIC = (checkFn: CheckFn, statusMessage?: StatusMessage) =>
    InputStatus.create(checkFn, 'basic', statusMessage);

  public static PRIMARY = (checkFn: CheckFn, statusMessage?: StatusMessage) =>
    InputStatus.create(checkFn, 'primary', statusMessage);

  public static INFO = (checkFn: CheckFn, statusMessage?: StatusMessage) =>
    InputStatus.create(checkFn, 'info', statusMessage);

  public static SUCCESS = (checkFn: CheckFn, statusMessage?: StatusMessage) =>
    InputStatus.create(checkFn, 'success', statusMessage);

  public static CONTROL = (checkFn: CheckFn, statusMessage?: StatusMessage) =>
    InputStatus.create(checkFn, 'control', statusMessage);

  public static WARNING = (checkFn: CheckFn, statusMessage?: StatusMessage) =>
    InputStatus.create(checkFn, 'warning', statusMessage);
}
