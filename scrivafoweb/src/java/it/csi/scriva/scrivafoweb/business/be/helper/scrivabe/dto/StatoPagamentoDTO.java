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
 * The type Tipo soggetto dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StatoPagamentoDTO implements Serializable {

    @JsonProperty("id_stato_pagamento")
    private Long idStatoPagamento;

    @JsonProperty("cod_stato_pagamento")
    private String codiceStatoPagamento;

    @JsonProperty("des_stato_pagamento")
    private String descrizioneStatoPagamento;

    /**
     * Gets id stato pagamento.
     *
     * @return the id stato pagamento
     */
    public Long getIdStatoPagamento() {
        return idStatoPagamento;
    }

    /**
     * Sets id stato pagamento.
     *
     * @param idStatoPagamento the id stato pagamento
     */
    public void setIdStatoPagamento(Long idStatoPagamento) {
        this.idStatoPagamento = idStatoPagamento;
    }

    /**
     * Gets codice stato pagamento.
     *
     * @return the codice stato pagamento
     */
    public String getCodiceStatoPagamento() {
        return codiceStatoPagamento;
    }

    /**
     * Sets codice stato pagamento.
     *
     * @param codiceStatoPagamento the codice stato pagamento
     */
    public void setCodiceStatoPagamento(String codiceStatoPagamento) {
        this.codiceStatoPagamento = codiceStatoPagamento;
    }

    /**
     * Gets descrizione stato pagamento.
     *
     * @return the descrizione stato pagamento
     */
    public String getDescrizioneStatoPagamento() {
        return descrizioneStatoPagamento;
    }

    /**
     * Sets descrizione stato pagamento.
     *
     * @param descrizioneStatoPagamento the descrizione stato pagamento
     */
    public void setDescrizioneStatoPagamento(String descrizioneStatoPagamento) {
        this.descrizioneStatoPagamento = descrizioneStatoPagamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatoPagamentoDTO that = (StatoPagamentoDTO) o;
        return Objects.equals(idStatoPagamento, that.idStatoPagamento) && Objects.equals(codiceStatoPagamento, that.codiceStatoPagamento) && Objects.equals(descrizioneStatoPagamento, that.descrizioneStatoPagamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStatoPagamento, codiceStatoPagamento, descrizioneStatoPagamento);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatoPagamentoDTO {");
        sb.append("         idStatoPagamento:").append(idStatoPagamento);
        sb.append(",         codiceStatoPagamento:'").append(codiceStatoPagamento).append("'");
        sb.append(",         descrizioneStatoPagamento:'").append(descrizioneStatoPagamento).append("'");
        sb.append("}");
        return sb.toString();
    }
}