/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */

interface ProgressFile {
    name: string,
    size: number,
    status: 'wait'|'started'|'done'|'error',  
    typeMessage?: 'success'|'info'|'error'|'warning',
    error?: string
    progress?:number,
    startDate?: number,
}