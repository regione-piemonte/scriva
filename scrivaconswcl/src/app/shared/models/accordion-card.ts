/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
export interface AccordionCard {
    name: string;
    header: {
        title: string,
        cssClass?: string,
        showLeft?: boolean,
        showActive?: boolean,
        template?: any,
    };
    open?: boolean;
    template?: any;
    templateData?: any;
}
