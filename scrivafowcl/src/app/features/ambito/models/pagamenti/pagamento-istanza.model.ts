/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { AllegatoLight } from '../allegati/allegato-light.model';
import { RiscossioneEnte } from './riscossione-ente.model';
import { StatoPagamento } from './stato-pagamento.model';
import { TentativoDettaglio } from './tentativo-dettaglio.model';

export interface PagamentoIstanza {
  gestUID?: string;
  id_pagamento_istanza?: number;
  id_istanza: number;
  id_onere_padre?: number;
  data_inserimento_pagamento: Date;
  flg_non_dovuto?: boolean;
  importo_dovuto?: number;
  importo_pagato?: number;
  data_effettivo_pagamento?: Date;
  iuv?: string;
  iubd?: string;
  riscossione_ente: RiscossioneEnte;
  stato_pagamento: StatoPagamento;
  tentativi_dettaglio?: TentativoDettaglio[];
  rt_xml?: string;
  allegato_istanza?: AllegatoLight;
  ind_tipo_inserimento?: string;
}
