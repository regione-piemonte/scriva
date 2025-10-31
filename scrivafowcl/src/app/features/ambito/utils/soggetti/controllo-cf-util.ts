/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export class ControlloCf {
  constructor() {}

  static normalize(cf: string = '') {
    // Verifico l'input
    cf = cf ?? '';

    return cf.replace(/\s/g, '').toUpperCase();
  }

  static PRIVATE_validate_regular(cf: string = '') {
    // Verifico l'input
    cf = cf ?? '';

    const charRegex = new RegExp(/^[0-9A-Z]{16}$/);
    if (!charRegex.test(cf)) {
      return 'Invalid characters.';
    }
    let s = 0;
    const even_map = 'BAFHJNPRTVCESULDGIMOQKWZYX';
    for (let i = 0; i < 15; i++) {
      let c = cf[i];
      let n = 0;
      if ('0' <= c && c <= '9') {
        n = c.charCodeAt(0) - '0'.charCodeAt(0);
      } else {
        n = c.charCodeAt(0) - 'A'.charCodeAt(0);
      }
      if ((i & 1) === 0) {
        n = even_map.charCodeAt(n) - 'A'.charCodeAt(0);
      }
      s += n;
    }
    if ((s % 26) + 'A'.charCodeAt(0) !== cf.charCodeAt(15)) {
      return 'Invalid checksum.';
    }
    return null;
  }

  static PRIVATE_validate_temporary(cf: string = '') {
    // Verifico l'input
    cf = cf ?? '';

    const charRegex = new RegExp(/^[0-9]{11}$/);
    if (!charRegex.test(cf)) {
      return 'Invalid characters.';
    }
    let s = 0;
    for (let i = 0; i < 11; i++) {
      let n = cf.charCodeAt(i) - '0'.charCodeAt(0);
      if ((i & 1) === 1) {
        n *= 2;
        if (n > 9) {
          n -= 9;
        }
      }
      s += n;
    }
    if (s % 10 !== 0) {
      return 'Invalid checksum.';
    }
    return null;
  }

  /* Returns string Null if valid, or string describing why this CF must be rejected. */
  static validateCF(cf: string = '') {
    // Verifico l'input
    cf = cf ?? '';

    cf = this.normalize(cf);
    if (cf.length === 0) {
      return 'Empty.';
    } else if (cf.length === 16) {
      return this.PRIVATE_validate_regular(cf);
    } else if (cf.length === 11) {
      return this.PRIVATE_validate_temporary(cf);
    } else {
      return 'Invalid length.';
    }
  }

  /* Returns string Null if valid, or string describing why this CF must be rejected. */
  static validatePI(pi: string = '') {
    // Verifico l'input
    pi = pi ?? '';

    pi = this.normalize(pi);
    if (pi.length === 0) {
      return 'Empty.';
    } else if (pi.length !== 11) {
      return 'Invalid length.';
    }
    const charRegex = new RegExp(/^[0-9]{11}$/);
    if (!charRegex.test(pi)) {
      return 'Invalid characters.';
    }
    let s = 0;
    for (let i = 0; i < 11; i++) {
      let n = pi.charCodeAt(i) - '0'.charCodeAt(0);
      if ((i & 1) === 1) {
        n *= 2;
        if (n > 9) {
          n -= 9;
        }
      }
      s += n;
    }
    if (s % 10 !== 0) {
      return 'Invalid checksum.';
    }
    return null;
  }
}
