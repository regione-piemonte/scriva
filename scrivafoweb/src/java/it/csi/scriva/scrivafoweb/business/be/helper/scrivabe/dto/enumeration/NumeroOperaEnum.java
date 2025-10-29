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

public enum NumeroOperaEnum {
    S ("Singola"),
    M ("Multipla"),
    N ("Nessuna"),
    O ("Singola opzionale");

    private String descrizione;

    NumeroOperaEnum(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public static NumeroOperaEnum findByDescr(final String descrizione){
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}