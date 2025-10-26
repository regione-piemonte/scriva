/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/*
 *  SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */

package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

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