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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Tentativo pagamento dto.
 *
 *  @author CSI PIEMONTE
 */
public class TentativoPagamentoDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_tentativo_pagamento")
    private Long idTentativoPagamento;

    @JsonProperty("id_stato_tentativo_pag")
    private Long idStatoTentativoPagamento;

    @JsonProperty("identificativo_pagamento_ppay")
    private String identificativoPagamentoPpay;

    @JsonProperty("hash_pagamento")
    private String hashPagamento;

    @JsonProperty("tipo_bollo")
    private String tipoBollo;

    @JsonProperty("flg_solo_marca")
    private Boolean flgSoloMarca;

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
     * Gets id stato tentativo pagamento.
     *
     * @return the id stato tentativo pagamento
     */
    public Long getIdStatoTentativoPagamento() {
        return idStatoTentativoPagamento;
    }

    /**
     * Sets id stato tentativo pagamento.
     *
     * @param idStatoTentativoPagamento the id stato tentativo pagamento
     */
    public void setIdStatoTentativoPagamento(Long idStatoTentativoPagamento) {
        this.idStatoTentativoPagamento = idStatoTentativoPagamento;
    }

    /**
     * Gets identificativo pagamento ppay.
     *
     * @return the identificativo pagamento ppay
     */
    public String getIdentificativoPagamentoPpay() {
        return identificativoPagamentoPpay;
    }

    /**
     * Sets identificativo pagamento ppay.
     *
     * @param identificativoPagamentoPpay the identificativo pagamento ppay
     */
    public void setIdentificativoPagamentoPpay(String identificativoPagamentoPpay) {
        this.identificativoPagamentoPpay = identificativoPagamentoPpay;
    }

    /**
     * Gets hash pagamento.
     *
     * @return the hash pagamento
     */
    public String getHashPagamento() {
        return hashPagamento;
    }

    /**
     * Sets hash pagamento.
     *
     * @param hashPagamento the hash pagamento
     */
    public void setHashPagamento(String hashPagamento) {
        this.hashPagamento = hashPagamento;
    }

    /**
     * Gets tipo bollo.
     *
     * @return the tipo bollo
     */
    public String getTipoBollo() {
        return tipoBollo;
    }

    /**
     * Sets tipo bollo.
     *
     * @param tipoBollo the tipo bollo
     */
    public void setTipoBollo(String tipoBollo) {
        this.tipoBollo = tipoBollo;
    }

    /**
     * Gets flg solo marca.
     *
     * @return the flg solo marca
     */
    public Boolean getFlgSoloMarca() {
        return flgSoloMarca;
    }

    /**
     * Sets flg solo marca.
     *
     * @param flgSoloMarca the flg solo marca
     */
    public void setFlgSoloMarca(Boolean flgSoloMarca) {
        this.flgSoloMarca = flgSoloMarca;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TentativoPagamentoDTO that = (TentativoPagamentoDTO) o;
        return Objects.equals(idTentativoPagamento, that.idTentativoPagamento) && Objects.equals(idStatoTentativoPagamento, that.idStatoTentativoPagamento) && Objects.equals(identificativoPagamentoPpay, that.identificativoPagamentoPpay) && Objects.equals(hashPagamento, that.hashPagamento) && Objects.equals(tipoBollo, that.tipoBollo) && Objects.equals(flgSoloMarca, that.flgSoloMarca);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idTentativoPagamento, idStatoTentativoPagamento, identificativoPagamentoPpay, hashPagamento, tipoBollo, flgSoloMarca);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TentativoPagamentoDTO {");
        sb.append("         idTentativoPagamento:").append(idTentativoPagamento);
        sb.append(",         idStatoTentativoPagamento:").append(idStatoTentativoPagamento);
        sb.append(",         identificativoPagamentoPpay:'").append(identificativoPagamentoPpay).append("'");
        sb.append(",         hashPagamento:'").append(hashPagamento).append("'");
        sb.append(",         tipoBollo:'").append(tipoBollo).append("'");
        sb.append(",         flgSoloMarca:").append(flgSoloMarca);
        sb.append(",         gestDataIns:").append(gestDataIns);
        sb.append(",         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",         gestDataUpd:").append(gestDataUpd);
        sb.append(",         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",         gestUID:'").append(gestUID).append("'");
        sb.append("}");
        return sb.toString();
    }
}