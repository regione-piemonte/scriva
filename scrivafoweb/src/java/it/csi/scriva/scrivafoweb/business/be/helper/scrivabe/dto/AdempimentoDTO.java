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
 * AdempimentoDTO
 *
 * @author CSI PIEMONTE
 */
public class AdempimentoDTO implements Serializable {

    @JsonProperty("id_adempimento")
    private Long idAdempimento;

    @JsonProperty("id_tipo_adempimento")
    private Long idTipoAdempimento;

    @JsonProperty("cod_adempimento")
    private String codAdempimento;

    @JsonProperty("des_adempimento")
    private String desAdempimento;

    @JsonProperty("des_estesa_adempimento")
    private String desEstesaAdempimento;

    @JsonProperty("ordinamento_adempimento")
    private Integer ordinamentoAdempimento;

    @JsonIgnore
    private Boolean flgAttivo;

    /**
     * @return idAdempimento
     */
    public Long getIdAdempimento() {
        return idAdempimento;
    }

    /**
     * @param idAdempimento idAdempimento
     */
    public void setIdAdempimento(Long idAdempimento) {
        this.idAdempimento = idAdempimento;
    }

    /**
     * @return idTipoAdempimento
     */
    public Long getIdTipoAdempimento() {
        return idTipoAdempimento;
    }

    /**
     * @param idTipoAdempimento idTipoAdempimento
     */
    public void setIdTipoAdempimento(Long idTipoAdempimento) {
        this.idTipoAdempimento = idTipoAdempimento;
    }

    /**
     * @return codAdempimento
     */
    public String getCodAdempimento() {
        return codAdempimento;
    }

    /**
     * @param codAdempimento codAdempimento
     */
    public void setCodAdempimento(String codAdempimento) {
        this.codAdempimento = codAdempimento;
    }

    /**
     * @return desAdempimento
     */
    public String getDesAdempimento() {
        return desAdempimento;
    }

    /**
     * @param desAdempimento desAdempimento
     */
    public void setDesAdempimento(String desAdempimento) {
        this.desAdempimento = desAdempimento;
    }

    /**
     * @return desEstesaAdempimento
     */
    public String getDesEstesaAdempimento() {
        return desEstesaAdempimento;
    }

    /**
     * @param desEstesaAdempimento desEstesaAdempimento
     */
    public void setDesEstesaAdempimento(String desEstesaAdempimento) {
        this.desEstesaAdempimento = desEstesaAdempimento;
    }

    /**
     * getOrdinamentoAdempimento
     *
     * @return ordinamentoAdempimento
     */
    public Integer getOrdinamentoAdempimento() {
        return ordinamentoAdempimento;
    }

    /**
     * setOrdinamentoAdempimento
     *
     * @param ordinamentoAdempimento ordinamentoAdempimento
     */
    public void setOrdinamentoAdempimento(Integer ordinamentoAdempimento) {
        this.ordinamentoAdempimento = ordinamentoAdempimento;
    }

    /**
     * getFlgAttivo
     *
     * @return flgAttivo
     */
    public Boolean getFlgAttivo() {
        return flgAttivo;
    }

    /**
     * setFlgAttivo
     *
     * @param flgAttivo flgAttivo
     */
    public void setFlgAttivo(Boolean flgAttivo) {
        this.flgAttivo = flgAttivo;
    }

    /**
     * hashCode
     *
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AdempimentoDTO that = (AdempimentoDTO) o;
        return Objects.equals(idAdempimento, that.idAdempimento) && Objects.equals(idTipoAdempimento, that.idTipoAdempimento) && Objects.equals(codAdempimento, that.codAdempimento) && Objects.equals(desAdempimento, that.desAdempimento) && Objects.equals(desEstesaAdempimento, that.desEstesaAdempimento) && Objects.equals(ordinamentoAdempimento, that.ordinamentoAdempimento) && Objects.equals(flgAttivo, that.flgAttivo);
    }

    /**
     * hashCode
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idAdempimento, idTipoAdempimento, codAdempimento, desAdempimento, desEstesaAdempimento, ordinamentoAdempimento, flgAttivo);
    }

    /**
     * toString
     *
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdempimentoDTO {");
        sb.append("         idAdempimento:").append(idAdempimento);
        sb.append(",         idTipoAdempimento:").append(idTipoAdempimento);
        sb.append(",         codAdempimento:'").append(codAdempimento).append("'");
        sb.append(",         desAdempimento:'").append(desAdempimento).append("'");
        sb.append(",         desEstesaAdempimento:'").append(desEstesaAdempimento).append("'");
        sb.append(",         ordinamentoAdempimento:").append(ordinamentoAdempimento);
        sb.append(",         flgAttivo:").append(flgAttivo);
        sb.append("}");
        return sb.toString();
    }
}