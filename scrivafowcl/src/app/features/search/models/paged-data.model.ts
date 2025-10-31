/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Page } from './page.model';

/**
 * An array of data with an associated page object used for paging
 */
export class PagedData<T> {
  data = new Array<T>();
  page = new Page();
}
