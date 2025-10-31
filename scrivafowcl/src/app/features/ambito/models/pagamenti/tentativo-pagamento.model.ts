/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { StatoTentativoPag } from './stato-tentativo-pag.model';

export interface TentativoPagamento {
  gestUID: string;
  id_tentativo_pagamento: number;
  stato_tentativo_pag: StatoTentativoPag;
  identificativo_pagamento_ppay?: string;
  hash_pagamento?: string;
  tipo_bollo?: string;
  flg_solo_marca?: boolean;
}
