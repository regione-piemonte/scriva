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

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class TipoPagamentoExtendedDTO extends TipoPagamentoDTO implements Serializable {

    @JsonProperty("gruppo_pagamento")
    private GruppoPagamentoDTO gruppoPagamento;

    /**
     * Gets gruppo pagamento.
     *
     * @return the gruppo pagamento
     */
    public GruppoPagamentoDTO getGruppoPagamento() {
        return gruppoPagamento;
    }

    /**
     * Sets gruppo pagamento.
     *
     * @param gruppoPagamento the gruppo pagamento
     */
    public void setGruppoPagamento(GruppoPagamentoDTO gruppoPagamento) {
        this.gruppoPagamento = gruppoPagamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TipoPagamentoExtendedDTO that = (TipoPagamentoExtendedDTO) o;
        return Objects.equals(gruppoPagamento, that.gruppoPagamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), gruppoPagamento);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipoPagamentoExtendedDTO {");
        sb.append(super.toString());
        sb.append("         gruppoPagamento:").append(gruppoPagamento);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public TipoPagamentoDTO getDTO() {
        TipoPagamentoDTO dto = new TipoPagamentoDTO();
        dto.setIdTipoPagamento(this.getIdTipoPagamento());
        if (this.gruppoPagamento != null) {
            dto.setIdGruppoPagamento(this.gruppoPagamento.getIdGruppoPagamento());
        }
        dto.setCodiceTipoPagamento(this.getCodiceTipoPagamento());
        dto.setDescrizioneTipoPagamento(this.getDescrizioneTipoPagamento());
        return dto;
    }
}