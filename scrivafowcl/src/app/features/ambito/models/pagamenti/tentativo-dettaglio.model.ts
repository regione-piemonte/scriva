/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { TentativoPagamento } from './tentativo-pagamento.model';

export interface TentativoDettaglio {
  gestUID: string;
  tentativo_pagamento: TentativoPagamento;
  IUV_tentativo_pagamento: string;
}
