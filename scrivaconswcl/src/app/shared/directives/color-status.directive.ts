/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Directive, HostBinding, Input } from '@angular/core';
export declare type NbComponentStatus =
  | 'basic'
  | 'primary'
  | 'success'
  | 'warning'
  | 'danger'
  | 'info'
  | 'control';

@Directive({
  selector: '[appColorStatus]',
})
export class ColorStatusDirective {
  @HostBinding('class.color-status-primary')
  get primary(): boolean {
    return this.appColorStatus === 'primary';
  }

  @HostBinding('class.color-status-info')
  get info(): boolean {
    return this.appColorStatus === 'info';
  }

  @HostBinding('class.color-status-success')
  get success(): boolean {
    return this.appColorStatus === 'success';
  }

  @HostBinding('class.color-status-warning')
  get warning(): boolean {
    return this.appColorStatus === 'warning';
  }

  @HostBinding('class.color-status-danger')
  get danger(): boolean {
    return this.appColorStatus === 'danger';
  }

  @HostBinding('class.color-status-basic')
  get basic(): boolean {
    return this.appColorStatus === 'basic';
  }

  @HostBinding('class.color-status-control')
  get control(): boolean {
    return this.appColorStatus === 'control';
  }

  @Input() appColorStatus: NbComponentStatus;
}
