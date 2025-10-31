/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export class ObjectHelper {
  constructor() {}

  static cloneObject(item: any) {
    if (item) {
      const clone = {};
      const keys = Object.keys(item);
      keys.forEach((key) => {
        switch (typeof item[key]) {
          case 'object':
            if (Array.isArray(item[key])) {
              const clonedArray = item[key].map((el) => this.cloneObject(el));
              clone[key] = [...clonedArray];
            } else {
              clone[key] = this.cloneObject(item[key]);
            }
            break;
          default:
            clone[key] = item[key];
            break;
        }
      });
      return clone;
    }
  }

  static flattenArray(multiDimArray, flatArray) {
    for (let i = 0; i < multiDimArray.length; i++) {
      const value = multiDimArray[i];
      if (Array.isArray(value)) {
        this.flattenArray(value, flatArray);
      } else {
        flatArray.push(value);
      }
    }
    return flatArray;
  }
}
