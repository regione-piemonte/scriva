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

/**
 * The enum Gruppo pagamento enum.
 */
public enum GruppoPagamentoEnum {
    /**
     * The Marca bollo.
     */
    MARCA_BOLLO("Marca da Bollo"),
    /**
     * Onere gruppo pagamento enum.
     */
    ONERE("Onere"),
    /**
     * Interesse gruppo pagamento enum.
     */
    INTERESSE("Interesse"),
    /**
     * Sanzione gruppo pagamento enum.
     */
    SANZIONE("Sanzione");

    private final String descrizione;

    GruppoPagamentoEnum(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Gets descrizione.
     *
     * @return the descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Find by descr gruppo pagamento enum.
     *
     * @param descrizione the descrizione
     * @return the gruppo pagamento enum
     */
    public static GruppoPagamentoEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}