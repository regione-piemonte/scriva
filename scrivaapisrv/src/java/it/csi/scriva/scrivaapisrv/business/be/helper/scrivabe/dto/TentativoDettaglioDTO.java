/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Tentativo dettaglio dto.
 *
 * @author CSI PIEMONTE
 */
public class TentativoDettaglioDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_pagamento_istanza")
    private Long idPagamentoIstanza;

    @JsonProperty("id_tentativo_pagamento")
    private Long idTentativoPagamento;

    @JsonProperty("iuv_tentativo_pagamento")
    private String iuvTentativoPagamento;

    /**
     * Gets id pagamento istanza.
     *
     * @return the id pagamento istanza
     */
    public Long getIdPagamentoIstanza() {
        return idPagamentoIstanza;
    }

    /**
     * Sets id pagamento istanza.
     *
     * @param idPagamentoIstanza the id pagamento istanza
     */
    public void setIdPagamentoIstanza(Long idPagamentoIstanza) {
        this.idPagamentoIstanza = idPagamentoIstanza;
    }

    /**
     * Gets id tentativo pagamento.
     *
     * @return the id tentativo pagamento
     */
    public Long getIdTentativoPagamento() {
        return idTentativoPagamento;
    }

    /**
     * Sets id tentativo pagamento.
     *
     * @param idTentativoPagamento the id tentativo pagamento
     */
    public void setIdTentativoPagamento(Long idTentativoPagamento) {
        this.idTentativoPagamento = idTentativoPagamento;
    }

    /**
     * Gets iuv tentativo pagamento.
     *
     * @return the iuv tentativo pagamento
     */
    public String getIuvTentativoPagamento() {
        return iuvTentativoPagamento;
    }

    /**
     * Sets iuv tentativo pagamento.
     *
     * @param iuvTentativoPagamento the iuv tentativo pagamento
     */
    public void setIuvTentativoPagamento(String iuvTentativoPagamento) {
        this.iuvTentativoPagamento = iuvTentativoPagamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TentativoDettaglioDTO that = (TentativoDettaglioDTO) o;
        return Objects.equals(idPagamentoIstanza, that.idPagamentoIstanza) && Objects.equals(idTentativoPagamento, that.idTentativoPagamento) && Objects.equals(iuvTentativoPagamento, that.iuvTentativoPagamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idPagamentoIstanza, idTentativoPagamento, iuvTentativoPagamento);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TentativoDettaglioDTO {");
        sb.append("         idPagamentoIstanza:").append(idPagamentoIstanza);
        sb.append(",         idTentativoPagamento:").append(idTentativoPagamento);
        sb.append(",         iuvTentativoPagamento:").append(iuvTentativoPagamento);
        sb.append(",         gestDataIns:").append(gestDataIns);
        sb.append(",         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",         gestDataUpd:").append(gestDataUpd);
        sb.append(",         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",         gestUID:'").append(gestUID).append("'");
        sb.append("}");
        return sb.toString();
    }
}