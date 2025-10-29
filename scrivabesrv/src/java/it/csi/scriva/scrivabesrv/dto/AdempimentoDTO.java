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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * AdempimentoDTO
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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

    @JsonIgnore
    private String indVisibile;

    /**
     * Gets id adempimento.
     *
     * @return idAdempimento id adempimento
     */
    public Long getIdAdempimento() {
        return idAdempimento;
    }

    /**
     * Sets id adempimento.
     *
     * @param idAdempimento idAdempimento
     */
    public void setIdAdempimento(Long idAdempimento) {
        this.idAdempimento = idAdempimento;
    }

    /**
     * Gets id tipo adempimento.
     *
     * @return idTipoAdempimento id tipo adempimento
     */
    public Long getIdTipoAdempimento() {
        return idTipoAdempimento;
    }

    /**
     * Sets id tipo adempimento.
     *
     * @param idTipoAdempimento idTipoAdempimento
     */
    public void setIdTipoAdempimento(Long idTipoAdempimento) {
        this.idTipoAdempimento = idTipoAdempimento;
    }

    /**
     * Gets cod adempimento.
     *
     * @return codAdempimento cod adempimento
     */
    public String getCodAdempimento() {
        return codAdempimento;
    }

    /**
     * Sets cod adempimento.
     *
     * @param codAdempimento codAdempimento
     */
    public void setCodAdempimento(String codAdempimento) {
        this.codAdempimento = codAdempimento;
    }

    /**
     * Gets des adempimento.
     *
     * @return desAdempimento des adempimento
     */
    public String getDesAdempimento() {
        return desAdempimento;
    }

    /**
     * Sets des adempimento.
     *
     * @param desAdempimento desAdempimento
     */
    public void setDesAdempimento(String desAdempimento) {
        this.desAdempimento = desAdempimento;
    }

    /**
     * Gets des estesa adempimento.
     *
     * @return desEstesaAdempimento des estesa adempimento
     */
    public String getDesEstesaAdempimento() {
        return desEstesaAdempimento;
    }

    /**
     * Sets des estesa adempimento.
     *
     * @param desEstesaAdempimento desEstesaAdempimento
     */
    public void setDesEstesaAdempimento(String desEstesaAdempimento) {
        this.desEstesaAdempimento = desEstesaAdempimento;
    }

    /**
     * getOrdinamentoAdempimento
     *
     * @return ordinamentoAdempimento ordinamento adempimento
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
     * @return flgAttivo flg attivo
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
     * Gets ind visibile.
     *
     * @return the ind visibile
     */
    public String getIndVisibile() {
        return indVisibile;
    }

    /**
     * Sets ind visibile.
     *
     * @param indVisibile the ind visibile
     */
    public void setIndVisibile(String indVisibile) {
        this.indVisibile = indVisibile;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdempimentoDTO that = (AdempimentoDTO) o;
        return Objects.equals(idAdempimento, that.idAdempimento) && Objects.equals(idTipoAdempimento, that.idTipoAdempimento) && Objects.equals(codAdempimento, that.codAdempimento) && Objects.equals(desAdempimento, that.desAdempimento) && Objects.equals(desEstesaAdempimento, that.desEstesaAdempimento) && Objects.equals(ordinamentoAdempimento, that.ordinamentoAdempimento) && Objects.equals(flgAttivo, that.flgAttivo) && Objects.equals(indVisibile, that.indVisibile);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idAdempimento, idTipoAdempimento, codAdempimento, desAdempimento, desEstesaAdempimento, ordinamentoAdempimento, flgAttivo, indVisibile);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "AdempimentoDTO {\n" +
                "         idAdempimento:" + idAdempimento +
                ",\n         idTipoAdempimento:" + idTipoAdempimento +
                ",\n         codAdempimento:'" + codAdempimento + "'" +
                ",\n         desAdempimento:'" + desAdempimento + "'" +
                ",\n         desEstesaAdempimento:'" + desEstesaAdempimento + "'" +
                ",\n         ordinamentoAdempimento:" + ordinamentoAdempimento +
                ",\n         flgAttivo:" + flgAttivo +
                ",\n         indVisibile:'" + indVisibile + "'" +
                "}\n";
    }
}