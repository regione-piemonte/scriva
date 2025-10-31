/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
export interface MenuItem {
  title: string;
  icon: string;
  link: string;
  activeCheck: string;
  home: boolean;
  data: any;
  hidden?: boolean;
  private?: boolean;
  children?: MenuItem[];
  type?: string;
  id?: string;
}
