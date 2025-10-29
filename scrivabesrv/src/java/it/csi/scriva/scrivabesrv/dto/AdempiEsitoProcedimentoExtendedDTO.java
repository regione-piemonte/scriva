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
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Adempi esito procedimento dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdempiEsitoProcedimentoExtendedDTO extends AdempiEsitoProcedimentoDTO implements Serializable {

    @JsonProperty("esito_procedimento")
    private EsitoProcedimentoDTO esitoProcedimento;

    /**
     * Gets esito procedimento.
     *
     * @return the esito procedimento
     */
    public EsitoProcedimentoDTO getEsitoProcedimento() {
        return esitoProcedimento;
    }

    /**
     * Sets esito procedimento.
     *
     * @param esitoProcedimento the esito procedimento
     */
    public void setEsitoProcedimento(EsitoProcedimentoDTO esitoProcedimento) {
        this.esitoProcedimento = esitoProcedimento;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AdempiEsitoProcedimentoExtendedDTO that = (AdempiEsitoProcedimentoExtendedDTO) o;
        return Objects.equals(esitoProcedimento, that.esitoProcedimento);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), esitoProcedimento);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "AdempiEsitoProcedimentoExtendedDTO {\n" +
                "         esitoProcedimento:" + esitoProcedimento +
                super.toString() +
                "}\n";
    }
}