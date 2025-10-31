/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { AllegatoAdditionalInfo } from "./allegato-additional-info.model";
import { TipoAllegato } from "./tipologia-allegato.model";

/**
* Interfaccia che descrive le opzioni per gli allegati
*/
export interface AllegatiUploadOptions {
    idIstanza: number;
    maxFileSize: number;
    tipologiaSelezionata: TipoAllegato;
    flgRiservato: boolean;
    numProtocolloAllegato: string;
    dataProtocolloAllegato: string;
    nota: string;
    allegatoAdditionalInfo?: AllegatoAdditionalInfo;
    tipoIntegrazione?: string;
    fileExtensionsArray: string[];
    targetMessage: string;
    // tutte le proprietà da qui in poi hanno dei default _allegatiUploadOptionsDefault nel servizio che lavora
    // con questa interfaccia
    enableProgress?: boolean;
    enableProgressMultipleUpload?: boolean;
    maxSizeFileSmall?: number;
    parallelFileSmall?: number;
    minProgressMega?: number; // limite minimo somma file per attivare la progress
    secondsProgressForMega?: number; // secondi per file > di maxProgressMegaSmall
    secondsProgressForMegaSmall?: number; // secondi per file < di maxProgressMegaSmall
    maxProgressMegaSmall?: number; // a seconda del peso del file si usa un numero di secondi diverso per inventare la stima
    timerMilliseconds?: number; // tempo di aggiornamento del progress
    maxPercentProgressPending?: number; // percentuale massima progress in pending
}

