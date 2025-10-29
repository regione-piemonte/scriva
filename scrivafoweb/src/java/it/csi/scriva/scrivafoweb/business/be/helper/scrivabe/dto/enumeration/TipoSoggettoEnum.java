/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.enumeration;

import java.util.Arrays;

public enum TipoSoggettoEnum {
    PF ("Persona Fisica"),
    PG ("Persona Giuridica"),
    GF ("Entrambe Fisica/Giuridica");

    private String descrizione;

    TipoSoggettoEnum(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public static TipoSoggettoEnum findByDescr(final String descrizione){
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }
}