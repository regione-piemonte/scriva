/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

import { NominatimLocation } from '../models';

@Injectable({ providedIn: 'root' })
export class NominatimService {
  private baseUrl =
    'https://nominatim.openstreetmap.org/search?email=assistenza.scriva@csi.it&format=json&addressdetails=1&limit=25';

  constructor(private http: HttpClient) {}

  searchAddress(
    country: string,
    city: string,
    street: string
  ): Observable<NominatimLocation[]> {
    if (street.length < 5) {
      return of([]);
    }
    const endpoint = `${this.baseUrl}&country=${country}&city=${city}&street=${street}`;
    return this.http.get<NominatimLocation[]>(endpoint);
  }
}
