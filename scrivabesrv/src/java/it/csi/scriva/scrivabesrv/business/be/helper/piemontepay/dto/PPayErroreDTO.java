/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.dto;

import java.util.Objects;

/**
 * The type P pay errore dto.
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class PPayErroreDTO {

    private String codice;

    private Integer status;

    private String messaggio;

    private String dettagli;

    /**
     * Gets codice.
     *
     * @return codice codice
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Sets codice.
     *
     * @param codice codice
     */
    public void setCodice(String codice) {
        this.codice = codice;
    }

    /**
     * Gets status.
     *
     * @return status status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Gets messaggio.
     *
     * @return messaggio messaggio
     */
    public String getMessaggio() {
        return messaggio;
    }

    /**
     * Sets messaggio.
     *
     * @param messaggio messaggio
     */
    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

    /**
     * Gets dettagli.
     *
     * @return dettagli dettagli
     */
    public String getDettagli() {
        return dettagli;
    }

    /**
     * Sets dettagli.
     *
     * @param dettagli dettagli
     */
    public void setDettagli(String dettagli) {
        this.dettagli = dettagli;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PPayErroreDTO that = (PPayErroreDTO) o;
        return Objects.equals(codice, that.codice) && Objects.equals(status, that.status) && Objects.equals(messaggio, that.messaggio) && Objects.equals(dettagli, that.dettagli);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(codice, status, messaggio, dettagli);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ErrorePPayDTO {");
        sb.append("         codice:'").append(codice).append("'");
        sb.append(",         status:").append(status);
        sb.append(",         messaggio:'").append(messaggio).append("'");
        sb.append(",         dettagli:'").append(dettagli).append("'");
        sb.append("}");
        return sb.toString();
    }
}