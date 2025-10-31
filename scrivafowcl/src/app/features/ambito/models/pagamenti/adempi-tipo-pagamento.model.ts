/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Adempimento, CompetenzaTerritorio, EnteCreditore } from 'src/app/shared/models';
import { TipoPagamento } from './tipo-pagamento.model';

export interface AdempiTipoPagamento {
  id_adempi_tipo_pagamento: number;
  codice_versamento: string;
  importo_previsto: number;
  giorni_max_attesa_rt: number;
  ind_importo_pagamento: string;
  adempimento: Adempimento;
  tipo_pagamento: TipoPagamento;
  ente_creditore?: EnteCreditore;
  competenza_territorio?: CompetenzaTerritorio;
}
