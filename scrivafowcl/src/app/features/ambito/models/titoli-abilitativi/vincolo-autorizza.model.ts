/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export interface VincoloAutorizza {
  id_vincolo_autorizza: number;
  tipo_vincolo_aut: TipoVincoloAut;
  cod_vincolo_autorizza: string;
  des_vincolo_autorizza: string;
  des_rif_normativo: string;
  data_inizio_validita: string;
  flg_modifica: boolean;
  ind_visibile: string;
  ordinamento_vincolo_aut: number;
}

interface TipoVincoloAut {
  id_tipo_vincolo_aut: number;
  cod_tipo_vincolo_aut: string;
  des_tipo_vincolo_aut: string;
}
