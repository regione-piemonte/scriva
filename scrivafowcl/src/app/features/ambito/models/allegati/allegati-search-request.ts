/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */

/**
    * Oggetto di richiesta per la Ricerca degli allegati
*/
export interface AllegatiSearchRequest {
    id_istanza?: number;
    id_osservazione?: number;
	cod_istanza?: string;
	cod_tipologia_allegato?: string;
    cod_categoria_allegato?: string;
    cod_classe_allegato?: string;
    flg_canc_logica?: boolean;
    flg_link_documento?: boolean;
    cod_allegato?: string;
    sys?: boolean;
}

