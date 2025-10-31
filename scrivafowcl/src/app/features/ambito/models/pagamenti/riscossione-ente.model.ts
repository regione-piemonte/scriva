/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { AdempiTipoPagamento } from './adempi-tipo-pagamento.model';
import { GruppoPagamento } from './gruppo-pagamento.model';

export interface RiscossioneEnte {
  id_riscossione_ente: number;
  dati_specifici_riscossione: string;
  accertamento_anno: number;
  numero_accertamento: number;
  des_pagamento_verso_cittadino: string;
  ordinamento_riscossione_ente: number;
  flg_attiva_pagamento: boolean;
  adempi_tipo_pagamento: AdempiTipoPagamento;
  gruppo_pagamento: GruppoPagamento;
  url_oneri_previsti?: string; // todo: to be removed (moved to CompetenzaTerritorio model)
}
