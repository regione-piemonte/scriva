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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Tentativo dettaglio dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TentativoDettaglioExtendedDTO extends TentativoDettaglioDTO implements Serializable {

    @JsonProperty("pagamento_istanza")
    private PagamentoIstanzaExtendedDTO pagamentoIstanza;

    @JsonProperty("tentativo_pagamento")
    private TentativoPagamentoExtendedDTO tentativoPagamento;

    /**
     * Gets pagamento istanza.
     *
     * @return the pagamento istanza
     */
    public PagamentoIstanzaExtendedDTO getPagamentoIstanza() {
        return pagamentoIstanza;
    }

    /**
     * Sets pagamento istanza.
     *
     * @param pagamentoIstanza the pagamento istanza
     */
    public void setPagamentoIstanza(PagamentoIstanzaExtendedDTO pagamentoIstanza) {
        this.pagamentoIstanza = pagamentoIstanza;
    }

    /**
     * Gets tentativo pagamento.
     *
     * @return the tentativo pagamento
     */
    public TentativoPagamentoExtendedDTO getTentativoPagamento() {
        return tentativoPagamento;
    }

    /**
     * Sets tentativo pagamento.
     *
     * @param tentativoPagamento the tentativo pagamento
     */
    public void setTentativoPagamento(TentativoPagamentoExtendedDTO tentativoPagamento) {
        this.tentativoPagamento = tentativoPagamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TentativoDettaglioExtendedDTO that = (TentativoDettaglioExtendedDTO) o;
        return Objects.equals(pagamentoIstanza, that.pagamentoIstanza) && Objects.equals(tentativoPagamento, that.tentativoPagamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pagamentoIstanza, tentativoPagamento);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TentativoDettaglioExtendedDTO {");
        sb.append(super.toString());
        sb.append("         pagamentoIstanza:").append(pagamentoIstanza);
        sb.append(",         tentativoPagamento:").append(tentativoPagamento);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public TentativoDettaglioDTO getDTO() {
        TentativoDettaglioDTO dto = new TentativoDettaglioDTO();

        if (this.getPagamentoIstanza() != null) {
            dto.setIdPagamentoIstanza(this.getPagamentoIstanza().getIdPagamentoIstanza());
        }

        if (this.getTentativoPagamento() != null) {
            dto.setIdTentativoPagamento(this.getTentativoPagamento().getIdTentativoPagamento());
        }

        dto.setIuvTentativoPagamento(this.getIuvTentativoPagamento());
        dto.setGestAttoreIns(this.gestAttoreIns);
        dto.setGestAttoreUpd(this.gestAttoreUpd);
        dto.setGestDataIns(this.gestDataIns);
        dto.setGestDataUpd(this.gestDataUpd);
        dto.setGestUID(this.gestUID);
        return dto;
    }
}