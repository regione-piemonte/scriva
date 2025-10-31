/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export interface NominatimLocation {
  place_id: number;
  display_name: string;
  address: NominatimAddress;
}

interface NominatimAddress {
  country: string;
  country_code: string;
  state: string; // regione
  city?: string;
  house_number?: string;
  road: string;
  postcode: string;
}
