/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { GruppoPagamento } from './gruppo-pagamento.model';

export interface TipoPagamento {
  id_tipo_pagamento: number;
  cod_tipo_pagamento: string;
  des_tipo_pagamento: string;
  gruppo_pagamento: GruppoPagamento;
}
