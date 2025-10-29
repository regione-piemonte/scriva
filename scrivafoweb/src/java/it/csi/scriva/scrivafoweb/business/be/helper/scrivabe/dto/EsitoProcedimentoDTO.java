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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Esito procedimento dto.
 *
 * @author CSI PIEMONTE
 */
public class EsitoProcedimentoDTO implements Serializable {

    @JsonProperty("id_esito_procedimento")
    private Long idEsitoProcedimento;

    @JsonProperty("cod_esito_procedimento")
    private String codEsitoProcedimento;

    @JsonProperty("des_esito_procedimento")
    private String desEsitoProcedimento;

    @JsonIgnore
    private boolean flgPositivo;

    /**
     * Gets id esito procedimento.
     *
     * @return the id esito procedimento
     */
    public Long getIdEsitoProcedimento() {
        return this.idEsitoProcedimento;
    }

    /**
     * Sets id esito procedimento.
     *
     * @param idEsitoProcedimento the id esito procedimento
     */
    public void setIdEsitoProcedimento(Long idEsitoProcedimento) {
        this.idEsitoProcedimento = idEsitoProcedimento;
    }

    /**
     * Gets cod esito procedimento.
     *
     * @return the cod esito procedimento
     */
    public String getCodEsitoProcedimento() {
        return this.codEsitoProcedimento;
    }

    /**
     * Sets cod esito procedimento.
     *
     * @param codEsitoProcedimento the cod esito procedimento
     */
    public void setCodEsitoProcedimento(String codEsitoProcedimento) {
        this.codEsitoProcedimento = codEsitoProcedimento;
    }

    /**
     * Gets des esito procedimento.
     *
     * @return the des esito procedimento
     */
    public String getDesEsitoProcedimento() {
        return this.desEsitoProcedimento;
    }

    /**
     * Sets des esito procedimento.
     *
     * @param desEsitoProcedimento the des esito procedimento
     */
    public void setDesEsitoProcedimento(String desEsitoProcedimento) {
        this.desEsitoProcedimento = desEsitoProcedimento;
    }

    /**
     * Gets flg positivo.
     *
     * @return the flg positivo
     */
    public boolean getFlgPositivo() {
        return this.flgPositivo;
    }

    /**
     * Sets flg positivo.
     *
     * @param flgPositivo the flg positivo
     */
    public void setFlgPositivo(boolean flgPositivo) {
        this.flgPositivo = flgPositivo;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EsitoProcedimentoDTO that = (EsitoProcedimentoDTO) o;
        return Objects.equals(idEsitoProcedimento, that.idEsitoProcedimento) && Objects.equals(codEsitoProcedimento, that.codEsitoProcedimento) && Objects.equals(desEsitoProcedimento, that.desEsitoProcedimento) && Objects.equals(flgPositivo, that.flgPositivo);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idEsitoProcedimento, codEsitoProcedimento, desEsitoProcedimento, flgPositivo);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "EsitoProcedimentoDTO {\n" +
                "         idEsitoProcedimento:" + idEsitoProcedimento +
                ",\n         codEsitoProcedimento:'" + codEsitoProcedimento + "'" +
                ",\n         desEsitoProcedimento:'" + desEsitoProcedimento + "'" +
                ",\n         flgPositivo:" + flgPositivo +
                "}\n";
    }
}