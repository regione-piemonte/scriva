/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Gruppo pagamento dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GruppoPagamentoDTO implements Serializable {

    @JsonProperty("id_gruppo_pagamento")
    private Long idGruppoPagamento;

    @JsonProperty("cod_gruppo_pagamento")
    private String codiceGruppoPagamento;

    @JsonProperty("des_gruppo_pagamento")
    private String descrizioneGruppoPagamento;

    /**
     * Gets id gruppo pagamento.
     *
     * @return the id gruppo pagamento
     */
    public Long getIdGruppoPagamento() {
        return idGruppoPagamento;
    }

    /**
     * Sets id gruppo pagamento.
     *
     * @param idGruppoPagamento the id gruppo pagamento
     */
    public void setIdGruppoPagamento(Long idGruppoPagamento) {
        this.idGruppoPagamento = idGruppoPagamento;
    }

    /**
     * Gets codice gruppo pagamento.
     *
     * @return the codice gruppo pagamento
     */
    public String getCodiceGruppoPagamento() {
        return codiceGruppoPagamento;
    }

    /**
     * Sets codice gruppo pagamento.
     *
     * @param codiceGruppoPagamento the codice gruppo pagamento
     */
    public void setCodiceGruppoPagamento(String codiceGruppoPagamento) {
        this.codiceGruppoPagamento = codiceGruppoPagamento;
    }

    /**
     * Gets descrizione gruppo pagamento.
     *
     * @return the descrizione gruppo pagamento
     */
    public String getDescrizioneGruppoPagamento() {
        return descrizioneGruppoPagamento;
    }

    /**
     * Sets descrizione gruppo pagamento.
     *
     * @param descrizioneGruppoPagamento the descrizione gruppo pagamento
     */
    public void setDescrizioneGruppoPagamento(String descrizioneGruppoPagamento) {
        this.descrizioneGruppoPagamento = descrizioneGruppoPagamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GruppoPagamentoDTO that = (GruppoPagamentoDTO) o;
        return Objects.equals(idGruppoPagamento, that.idGruppoPagamento) && Objects.equals(codiceGruppoPagamento, that.codiceGruppoPagamento) && Objects.equals(descrizioneGruppoPagamento, that.descrizioneGruppoPagamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGruppoPagamento, codiceGruppoPagamento, descrizioneGruppoPagamento);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GruppoPagamentoDTO {");
        sb.append("         idGruppoPagamento:").append(idGruppoPagamento);
        sb.append(",         codiceTipoPagamento:'").append(codiceGruppoPagamento).append("'");
        sb.append(",         descrizioneTipoPagamento:'").append(descrizioneGruppoPagamento).append("'");
        sb.append("}");
        return sb.toString();
    }
}