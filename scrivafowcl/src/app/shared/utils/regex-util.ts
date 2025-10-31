/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export enum RegexUtil {
  CF = '^[A-Za-z]{6}[0-9]{2}[A-Za-z][0-9]{2}[A-Za-z][0-9]{3}[A-Za-z]$',
  Number = '^[0-9]*$',
  Phone = '^([+|0][0-9]{1,3})?[0-9]{8,25}$',
  PIVA = '^[0-9|]{11}$',
  Email = '^[A-Za-z0-9._%-]+@[A-Za-z0-9._%-]+\\.[A-Za-z]{2,4}$',
  CF_OR_PIVA = '^([A-Za-z]{6}[0-9]{2}[A-Za-z][0-9]{2}[A-Za-z][0-9]{3}[A-Za-z]|[0-9|]{11})$',
  Price = '^\\d*(?:[.,]\\d{1,2})?$',
  Name_Surname = '^[A-Za-z ]{2,30}$'
}
