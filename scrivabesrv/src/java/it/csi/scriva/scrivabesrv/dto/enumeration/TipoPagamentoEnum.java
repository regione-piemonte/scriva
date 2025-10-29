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
 * The enum Tipo pagamento enum.
 */
public enum TipoPagamentoEnum {
    /**
     * The Marca bollo.
     */
    MARCA_BOLLO("Marca da Bollo"),
    /**
     * The Oneri istruttoria.
     */
    ONERI_ISTRUTTORIA("Oneri di istruttoria"),
    /**
     * The Oneri segreteria.
     */
    ONERI_SEGRETERIA("Onere di segreteria"),
    /**
     * The Interessi oneri ist.
     */
    INTERESSI_ONERI_IST("Interessi oneri istruttoria"),
    /**
     * The Sanzione oneri ist.
     */
    SANZIONE_ONERI_IST("Sanzione onere istruttoria");

    private final String descrizione;

    TipoPagamentoEnum(String descrizione) {
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
     * Find by descr tipo pagamento enum.
     *
     * @param descrizione the descrizione
     * @return the tipo pagamento enum
     */
    public static TipoPagamentoEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }

}