/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { TipoIntegrazione } from "src/app/features/ambito/models/allegati/tipo-integrazione";
import { AllegatoIntegrazione } from "./allegato-integrazione.model";



export interface IntegrazioneIstanza {
    allegato_integrazione: AllegatoIntegrazione[];
    tipo_integrazione: TipoIntegrazione;
    id_integrazione_istanza: number;
    id_istanza: number;
    data_inserimento: string;
    data_invio: string;
    data_protocollo: string;
    num_protocollo: string;
    gestAttoreIns: string;
    gestAttoreUpd: string;
    gestUID: string;
    uuid_index: string;
}
  

