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
 * The type Verifica firma dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerificaFirmaDTO implements Serializable {

    private Integer indFirma;

    private Integer tipoFirma;

    private Integer errorCode;

    /**
     * Gets ind firma.
     *
     * @return indFirma ind firma
     */
    public Integer getIndFirma() {
        if (errorCode != null && tipoFirma != null) {
            if (errorCode > 0) {
                indFirma = 3;
            } else {
                if (tipoFirma > 0) {
                    indFirma = 2;
                } else {
                    indFirma = 1;
                }
            }
        }
        return indFirma;
    }

    /**
     * Sets ind firma.
     *
     * @param indFirma indFirma
     */
    public void setIndFirma(Integer indFirma) {
        this.indFirma = indFirma;
    }

    /**
     * Gets tipo firma.
     *
     * @return tipoFirma tipo firma
     */
    public Integer getTipoFirma() {
        return tipoFirma;
    }

    /**
     * Sets tipo firma.
     *
     * @param tipoFirma tipoFirma
     */
    public void setTipoFirma(Integer tipoFirma) {
        this.tipoFirma = tipoFirma;
    }

    /**
     * Gets error code.
     *
     * @return errorCode error code
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * Sets error code.
     *
     * @param errorCode errorCode
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificaFirmaDTO that = (VerificaFirmaDTO) o;
        return Objects.equals(indFirma, that.indFirma) && Objects.equals(tipoFirma, that.tipoFirma) && Objects.equals(errorCode, that.errorCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(indFirma, tipoFirma, errorCode);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VerificaFirmaDTO {");
        sb.append("         indFirma:").append(indFirma);
        sb.append(",         tipoFirma:").append(tipoFirma);
        sb.append(",         errorCode:").append(errorCode);
        sb.append("}");
        return sb.toString();
    }
}