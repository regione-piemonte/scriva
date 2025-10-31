/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
export class BootstrapSize {
  is: string;
  sm: string;
  md: string;
  lg: string;
  xl: string;

  constructor(
    is: string = '12',
    sm: string = '12',
    md: string = '12',
    lg: string = '12',
    xl: string = '12'
  ) {
    this.is = is;
    this.sm = sm;
    this.md = md;
    this.lg = lg;
    this.xl = xl;
  }

  static formPipe(pipe: string): BootstrapSize {
    const sizes: string[] = pipe.split('|');

    return new BootstrapSize(
      sizes[0] || '12',
      sizes[1] || '12',
      sizes[2] || '12',
      sizes[3] || '12',
      sizes[4] || '12'
    );
  }

  toClass(): string {
    return `col-is-${this.is} col-sm-${this.sm} col-md-${this.md} col-lg-${this.lg} col-xl-${this.xl}`;
  }
}
