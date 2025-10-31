/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { IntegrazioneIstanza } from "./integrazione-istanza.model";
import { Istanza } from "./istanza.model";
import { IntegraAllegato } from "src/app/features/ambito/models";



export interface IntegrazioneIstanzaStatus {
    idIstanza: number,
    integrazioneIstanza: IntegrazioneIstanza,
    tipoIntegrazione: IntegraAllegato,
    // da verificare tipoAdempimentoOggApp o OggettoApp
    tipoAdempimentoOggApp: any[],
    readonlyAllegati: boolean,
    disabled: any,
    istanza?: Istanza, 
    jsIntegrazioni?: any
}
  

