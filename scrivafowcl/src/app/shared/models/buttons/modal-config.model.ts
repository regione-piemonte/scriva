/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export interface ModalButton {
    label?: string;
    type?: string;
    callback?: Function;
}

export interface ModalConfig {
    title: string;
    codMess: string;
    content?: string;
    buttons: ModalButton[];
    testo?: string
}
