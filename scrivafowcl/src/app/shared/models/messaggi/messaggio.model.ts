/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export interface MessaggioUtente {
  id_messaggio: number;
  tipo_messaggio: TipoMessaggio;
  cod_messaggio: string;
  des_testo_messaggio: string;
  des_titolo_messaggio?: string;
}

interface TipoMessaggio {
  id_tipo_messaggio: number;
  cod_tipo_messaggio: CodTipoMessEnum;
  des_tipo_messaggio: string;
}

export enum CodTipoMessEnum {
  P = 'P',
  E = 'E',
  I = 'I',
  A = 'A'
}
