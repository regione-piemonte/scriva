/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { IFunzionario } from './../funzionario/funzionario.model';
export interface NotaIstanza {
  gestUID?: string;
  id_nota_istanza?: number;
  id_istanza: number;
  id_funzionario?: number;
  data_nota?: string;
  des_nota: string;
  ind_visibile?: string;
  funzionario?: Partial<IFunzionario>;
}
