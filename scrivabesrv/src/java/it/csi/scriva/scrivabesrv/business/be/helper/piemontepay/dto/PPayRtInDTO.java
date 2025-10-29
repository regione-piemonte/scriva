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
 * The type P pay rt in dto.
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class PPayRtInDTO {

    private String iuv;

    private String codiceFiscale;

    private String codiceFiscaleEnte;

    private String identificativoPagamento;

    private String formatoRT = "XML";

    /**
     * Gets iuv.
     *
     * @return the iuv
     */
    public String getIuv() {
        return iuv;
    }

    /**
     * Sets iuv.
     *
     * @param iuv the iuv
     */
    public void setIuv(String iuv) {
        this.iuv = iuv;
    }

    /**
     * Gets codice fiscale.
     *
     * @return the codice fiscale
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Sets codice fiscale.
     *
     * @param codiceFiscale the codice fiscale
     */
    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    /**
     * Gets codice fiscale ente.
     *
     * @return the codice fiscale ente
     */
    public String getCodiceFiscaleEnte() {
        return codiceFiscaleEnte;
    }

    /**
     * Sets codice fiscale ente.
     *
     * @param codiceFiscaleEnte the codice fiscale ente
     */
    public void setCodiceFiscaleEnte(String codiceFiscaleEnte) {
        this.codiceFiscaleEnte = codiceFiscaleEnte;
    }

    /**
     * Gets identificativo pagamento.
     *
     * @return the identificativo pagamento
     */
    public String getIdentificativoPagamento() {
        return identificativoPagamento;
    }

    /**
     * Sets identificativo pagamento.
     *
     * @param identificativoPagamento the identificativo pagamento
     */
    public void setIdentificativoPagamento(String identificativoPagamento) {
        this.identificativoPagamento = identificativoPagamento;
    }

    /**
     * Gets formato rt.
     *
     * @return the formato rt
     */
    public String getFormatoRT() {
        return formatoRT;
    }

    /**
     * Sets formato rt.
     *
     * @param formatoRT the formato rt
     */
    public void setFormatoRT(String formatoRT) {
        this.formatoRT = formatoRT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PPayRtInDTO that = (PPayRtInDTO) o;
        return Objects.equals(iuv, that.iuv) && Objects.equals(codiceFiscale, that.codiceFiscale) && Objects.equals(identificativoPagamento, that.identificativoPagamento) && Objects.equals(formatoRT, that.formatoRT);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iuv, codiceFiscale, identificativoPagamento, formatoRT);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PPayRtInDTO {");
        sb.append("         iuv:'").append(iuv).append("'");
        sb.append(",         codiceFiscale:'").append(codiceFiscale).append("'");
        sb.append(",         identificativoPagamento:'").append(identificativoPagamento).append("'");
        sb.append(",         formatoRT:'").append(formatoRT).append("'");
        sb.append("}");
        return sb.toString();
    }
}