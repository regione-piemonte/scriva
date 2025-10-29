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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type P pay result dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PPayResultDTO implements Serializable {

    private String idPagamento;

    private String descEsito;

    private String codEsito;

    private String source;

    /**
     * Gets id pagamento.
     *
     * @return the id pagamento
     */
    public String getIdPagamento() {
        return idPagamento;
    }

    /**
     * Sets id pagamento.
     *
     * @param idPagamento the id pagamento
     */
    public void setIdPagamento(String idPagamento) {
        this.idPagamento = idPagamento;
    }

    /**
     * Gets desc esito.
     *
     * @return the desc esito
     */
    public String getDescEsito() {
        return descEsito;
    }

    /**
     * Sets desc esito.
     *
     * @param descEsito the desc esito
     */
    public void setDescEsito(String descEsito) {
        this.descEsito = descEsito;
    }

    /**
     * Gets cod esito.
     *
     * @return the cod esito
     */
    public String getCodEsito() {
        return codEsito;
    }

    /**
     * Sets cod esito.
     *
     * @param codEsito the cod esito
     */
    public void setCodEsito(String codEsito) {
        this.codEsito = codEsito;
    }

    /**
     * Gets source.
     *
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets source.
     *
     * @param source the source
     */
    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PPayResultDTO that = (PPayResultDTO) o;
        return Objects.equals(idPagamento, that.idPagamento) && Objects.equals(descEsito, that.descEsito) && Objects.equals(codEsito, that.codEsito) && Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPagamento, descEsito, codEsito, source);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PPayResultDTO {");
        sb.append("         idPagamento:'").append(idPagamento).append("'");
        sb.append(",         descEsito:'").append(descEsito).append("'");
        sb.append(",         codEsito:'").append(codEsito).append("'");
        sb.append(",         source:'").append(source).append("'");
        sb.append("}");
        return sb.toString();
    }

}