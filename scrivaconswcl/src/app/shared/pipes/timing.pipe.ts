/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'timing' })
export class TimingPipe implements PipeTransform {
  transform(time: number): string {
    if (time) {
      const minutes = Math.floor(time / 60);
      const seconds = Math.floor(time % 60);
      return `${this.initZero(minutes)}${minutes}:${this.initZero(seconds)}${seconds}`;
    }

    return '00:00';
  }

  private initZero(time: number): string {
    return time < 10 ? '0' : '';
  }
}
