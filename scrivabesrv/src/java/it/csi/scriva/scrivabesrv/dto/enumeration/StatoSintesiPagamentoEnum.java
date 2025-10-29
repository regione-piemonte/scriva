/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.dto.enumeration;

import java.util.Arrays;

public enum StatoSintesiPagamentoEnum {
    NON_PREVISTO("NON PREVISTO"),
    NON_DOVUTO("NON DOVUTO"),
    PAGATO("PAGATO"),
    DA_EFFETTUARE("DA EFFETTUARE");

    private final String descrizione;

    StatoSintesiPagamentoEnum(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public static StatoSintesiPagamentoEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}