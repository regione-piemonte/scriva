/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { SoggettoIstanza } from "src/app/features/ambito/models";
import { Istanza } from "../istanza/istanza.model";
import { IstanzaCompetenza } from "../aut-competente/istanza-competenza.model";

export interface FormioImports {
    JSONDATA: any,
    SOGGETTI: SoggettoIstanza[],
    ISTANZA: Istanza,
    ISTANZACOMPETENZAACP: IstanzaCompetenza[]
}
  