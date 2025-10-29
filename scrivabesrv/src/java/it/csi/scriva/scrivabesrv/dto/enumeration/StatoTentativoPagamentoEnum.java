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

public enum StatoTentativoPagamentoEnum {


    INVIATO("Inviato", 1L),
    FALLITO("Fallito", 2L),
    SUCCESSO("Successo", 3L);

    private final String descrizione;
    private final Long idStatoTentativoPagamento;


    StatoTentativoPagamentoEnum(String descrizione, Long idStatoTentativoPagamento) {
        this.descrizione = descrizione;
        this.idStatoTentativoPagamento = idStatoTentativoPagamento;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Long getIdStatoTentativoPagamento() {
        return idStatoTentativoPagamento;
    }

    public static StatoTentativoPagamentoEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescrizione().equals(descrizione)).findFirst().orElse(null);
    }
}