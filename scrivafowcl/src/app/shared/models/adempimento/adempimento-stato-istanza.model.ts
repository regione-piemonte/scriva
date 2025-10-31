/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ClasseAllegato } from "src/app/features/ambito/models";
import { StatoIstanza } from "../istanza/stato-istanza.model";

export interface AdempimentoStatoIstanza {
    stato_istanza: StatoIstanza,
    classe_allegato: ClasseAllegato
}