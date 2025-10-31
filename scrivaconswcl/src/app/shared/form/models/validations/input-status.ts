/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { FormControl } from '@angular/forms';
import { InternalStatusMessage, StatusMessage } from './status-message';
declare type NbComponentStatus =
  | 'basic'
  | 'primary'
  | 'success'
  | 'warning'
  | 'danger'
  | 'info'
  | 'control';

export type CheckFn = (control?: FormControl) => boolean;

export class InputStatus {
  /**
   * Function to check if there is the specific status
   */
  checkFn: CheckFn;

  /**
   * The status/color of the input
   */
  status: NbComponentStatus;

  /**
   * The text message for the status
   */
  message: InternalStatusMessage;

  constructor(
    checkFn: CheckFn,
    status?: NbComponentStatus,
    message?: StatusMessage
  ) {
    this.checkFn = checkFn;
    this.status = status || 'danger';
    this.message = { ...message, status: this.status };
  }

  static create(
    checkFn: CheckFn,
    status: NbComponentStatus,
    message?: StatusMessage
  ): InputStatus {
    return new InputStatus(checkFn, status, message);
  }
}
