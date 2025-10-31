/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { AdvancedActionsChiavi } from "../enums/advanced-actions.enums";

/**
 * Interfaccia che descrize una aziona avanzata concreta
 * che deve implementare i metodi e le proprietà di questa interfaccia
 */
export interface AbstractAdvancedActionInterface {
    /**
     * chiave che identifica azione avanzata BO 
     */
    chiave: AdvancedActionsChiavi;
    /**
     * Codice maschera azione avanzata
     */
    codMaschera: string;
    /**
     * Metodo che viene invocato per caricare i dati della componenente corrente
     */
    loadData(): void;
    /**
     * Metodo che viene invocato in caso di successo nel caricamento dei dati
     */
    loadDataSuccess(res:any[]): void;
    /**
     * Metodo che viene invocato in caso di conferma dei dati da salvare
     */
    onConferma(): void;
}
