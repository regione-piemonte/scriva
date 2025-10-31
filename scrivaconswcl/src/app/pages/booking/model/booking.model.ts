/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { SelectOption } from '@shared/form';

export interface Booking {
  startDate: string;
  endDate: string;
  bookingDate: string;
  id: number;
  address: string;
  city: SelectOption;
  user: User;
  amount: number;
  dicount: number;
  dicountCode: string;
  notes: string;
}

interface User {
  firstName: string;
  lastName: string;
  company: string;
}
