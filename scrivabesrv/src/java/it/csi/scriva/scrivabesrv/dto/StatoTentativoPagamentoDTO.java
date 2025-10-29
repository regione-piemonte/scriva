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
 * The type Tipo soggetto dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StatoTentativoPagamentoDTO implements Serializable {

    @JsonProperty("id_stato_tentativo_pag")
    private Long idStatoTentativoPagamento;

    @JsonProperty("cod_stato_tentativo_pag")
    private String codiceStatoTentativoPagamento;

    @JsonProperty("des_stato_tentativo_pag")
    private String descrizioneStatoTentativoPagamento;

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
     * Gets codice stato tentativo pagamento.
     *
     * @return the codice stato tentativo pagamento
     */
    public String getCodiceStatoTentativoPagamento() {
        return codiceStatoTentativoPagamento;
    }

    /**
     * Sets codice stato tentativo pagamento.
     *
     * @param codiceStatoTentativoPagamento the codice stato tentativo pagamento
     */
    public void setCodiceStatoTentativoPagamento(String codiceStatoTentativoPagamento) {
        this.codiceStatoTentativoPagamento = codiceStatoTentativoPagamento;
    }

    /**
     * Gets descrizione stato tentativo pagamento.
     *
     * @return the descrizione stato tentativo pagamento
     */
    public String getDescrizioneStatoTentativoPagamento() {
        return descrizioneStatoTentativoPagamento;
    }

    /**
     * Sets descrizione stato tentativo pagamento.
     *
     * @param descrizioneStatoTentativoPagamento the descrizione stato tentativo pagamento
     */
    public void setDescrizioneStatoTentativoPagamento(String descrizioneStatoTentativoPagamento) {
        this.descrizioneStatoTentativoPagamento = descrizioneStatoTentativoPagamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatoTentativoPagamentoDTO that = (StatoTentativoPagamentoDTO) o;
        return Objects.equals(idStatoTentativoPagamento, that.idStatoTentativoPagamento) && Objects.equals(codiceStatoTentativoPagamento, that.codiceStatoTentativoPagamento) && Objects.equals(descrizioneStatoTentativoPagamento, that.descrizioneStatoTentativoPagamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStatoTentativoPagamento, codiceStatoTentativoPagamento, descrizioneStatoTentativoPagamento);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatoTentativoPagamentoDTO {");
        sb.append("         idStatoTentativoPagamento:").append(idStatoTentativoPagamento);
        sb.append(",         codiceStatoTentativoPagamento:'").append(codiceStatoTentativoPagamento).append("'");
        sb.append(",         descrizioneStatoTentativoPagamento:'").append(descrizioneStatoTentativoPagamento).append("'");
        sb.append("}");
        return sb.toString();
    }
}