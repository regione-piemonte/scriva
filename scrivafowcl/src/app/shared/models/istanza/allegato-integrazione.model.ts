/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */



export interface AllegatoIntegrazione {
    id_integra_istanza_allegato?: number;
    id_integrazione_istanza?: number;
    id_allegato_istanza: number;
    flg_allegato_rif_protocollo: boolean;
    nome_allegato?: string;
    cod_allegato?: string;
    uuid_index?: string;
}