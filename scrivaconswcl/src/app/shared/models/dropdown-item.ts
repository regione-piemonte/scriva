/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Icon } from './';

export interface DropDownItem {
  icon?: Icon;
  label: string;
  detail?: string;
  isHidden?: (row) => boolean;
  onClick?: ($event) => void;
}
