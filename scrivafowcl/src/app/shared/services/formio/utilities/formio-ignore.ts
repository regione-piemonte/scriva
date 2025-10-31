/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export interface FormioIgnore {
    type: FormioIgnoreType,
    key: string
}

export enum FormioIgnoreType {
    STARTWITH = 'STARTWITH',
    EXACTKEY = 'EXACTKEY',
}

export const formioIgnoreList: FormioIgnore[] = [
    {
        type: FormioIgnoreType.EXACTKEY,
        key: 'imports'
    }
];