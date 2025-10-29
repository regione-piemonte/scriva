/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Tipo Pagamento dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TipoPagamentoDTO implements Serializable {

    @JsonProperty("id_tipo_pagamento")
    private Long idTipoPagamento;

    @JsonProperty("id_gruppo_pagamento")
    private Long idGruppoPagamento;

    @JsonProperty("cod_tipo_pagamento")
    private String codiceTipoPagamento;

    @JsonProperty("des_tipo_pagamento")
    private String descrizioneTipoPagamento;

    /**
     * Gets id tipo pagamento.
     *
     * @return the id tipo pagamento
     */
    public Long getIdTipoPagamento() {
        return idTipoPagamento;
    }

    /**
     * Sets id tipo pagamento.
     *
     * @param idTipoPagamento the id tipo pagamento
     */
    public void setIdTipoPagamento(Long idTipoPagamento) {
        this.idTipoPagamento = idTipoPagamento;
    }

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
     * Gets codice tipo pagamento.
     *
     * @return the codice tipo pagamento
     */
    public String getCodiceTipoPagamento() {
        return codiceTipoPagamento;
    }

    /**
     * Sets codice tipo pagamento.
     *
     * @param codiceTipoPagamento the codice tipo pagamento
     */
    public void setCodiceTipoPagamento(String codiceTipoPagamento) {
        this.codiceTipoPagamento = codiceTipoPagamento;
    }

    /**
     * Gets descrizione tipo pagamento.
     *
     * @return the descrizione tipo pagamento
     */
    public String getDescrizioneTipoPagamento() {
        return descrizioneTipoPagamento;
    }

    /**
     * Sets descrizione tipo pagamento.
     *
     * @param descrizioneTipoPagamento the descrizione tipo pagamento
     */
    public void setDescrizioneTipoPagamento(String descrizioneTipoPagamento) {
        this.descrizioneTipoPagamento = descrizioneTipoPagamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoPagamentoDTO that = (TipoPagamentoDTO) o;
        return Objects.equals(idTipoPagamento, that.idTipoPagamento) && Objects.equals(idGruppoPagamento, that.idGruppoPagamento) && Objects.equals(codiceTipoPagamento, that.codiceTipoPagamento) && Objects.equals(descrizioneTipoPagamento, that.descrizioneTipoPagamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTipoPagamento, idGruppoPagamento, codiceTipoPagamento, descrizioneTipoPagamento);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipoPagamentoDTO {");
        sb.append("         idTipoPagamento:").append(idTipoPagamento);
        sb.append(",         idGruppoPagamento:").append(idGruppoPagamento);
        sb.append(",         codiceTipoPagamento:'").append(codiceTipoPagamento).append("'");
        sb.append(",         descrizioneTipoPagamento:'").append(descrizioneTipoPagamento).append("'");
        sb.append("}");
        return sb.toString();
    }
}