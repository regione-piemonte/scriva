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
 * TipoAdempimentoDTO
 *
 * @author CSI PIEMONTE
 */
public class TipoAdempimentoDTO implements Serializable {

    @JsonProperty("id_tipo_adempimento")
    private Long idTipoAdempimento;

    @JsonProperty("id_ambito")
    private Long idAmbito;

    @JsonProperty("des_tipo_adempimento")
    private String desTipoAdempimento;

    @JsonProperty("des_estesa_tipo_adempimento")
    private String desEstesaTipoAdempimento;

    @JsonProperty("cod_tipo_adempimento")
    private String codTipoAdempimento;

    @JsonProperty("ordinamento_tipo_adempimento")
    private Integer ordinamentoTipoAdempimento;

    @JsonIgnore
    private Boolean flgAttivo;

    @JsonProperty("uuid_index")
    private String uuidIndex;

    /**
     * @return desEstesaTipoAdempimento
     */
    public String getDesEstesaTipoAdempimento() {
        return desEstesaTipoAdempimento;
    }

    /**
     * @param desEstesaTipoAdempimento desEstesaTipoAdempimento
     */
    public void setDesEstesaTipoAdempimento(String desEstesaTipoAdempimento) {
        this.desEstesaTipoAdempimento = desEstesaTipoAdempimento;
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
     * @return idAmbito
     */
    public Long getIdAmbito() {
        return idAmbito;
    }

    /**
     * @param idAmbito idAmbito
     */
    public void setIdAmbito(Long idAmbito) {
        this.idAmbito = idAmbito;
    }

    /**
     * @return desTipoAdempimento
     */
    public String getDesTipoAdempimento() {
        return desTipoAdempimento;
    }

    /**
     * @param desTipoAdempimento desTipoAdempimento
     */
    public void setDesTipoAdempimento(String desTipoAdempimento) {
        this.desTipoAdempimento = desTipoAdempimento;
    }

    /**
     * @return codTipoAdempimento
     */
    public String getCodTipoAdempimento() {
        return codTipoAdempimento;
    }

    /**
     * @param codTipoAdempimento codTipoAdempimento
     */
    public void setCodTipoAdempimento(String codTipoAdempimento) {
        this.codTipoAdempimento = codTipoAdempimento;
    }

    /**
     * @return ordinamentoTipoAdempimento
     */
    public Integer getOrdinamentoTipoAdempimento() {
        return ordinamentoTipoAdempimento;
    }

    /**
     * @param ordinamentoTipoAdempimento ordinamentoTipoAdempimento
     */
    public void setOrdinamentoTipoAdempimento(Integer ordinamentoTipoAdempimento) {
        this.ordinamentoTipoAdempimento = ordinamentoTipoAdempimento;
    }

    /**
     * @return flgAttivo
     */
    public Boolean getFlgAttivo() {
        return flgAttivo;
    }

    /**
     * @param flgAttivo flgAttivo
     */
    public void setFlgAttivo(Boolean flgAttivo) {
        this.flgAttivo = flgAttivo;
    }

    /**
     * @return uuidIndex
     */
    public String getUuidIndex() {
        return uuidIndex;
    }

    /**
     * @param uuidIndex uuidIndex
     */
    public void setUuidIndex(String uuidIndex) {
        this.uuidIndex = uuidIndex;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoAdempimentoDTO that = (TipoAdempimentoDTO) o;
        return Objects.equals(idTipoAdempimento, that.idTipoAdempimento) && Objects.equals(idAmbito, that.idAmbito) && Objects.equals(desTipoAdempimento, that.desTipoAdempimento) && Objects.equals(desEstesaTipoAdempimento, that.desEstesaTipoAdempimento) && Objects.equals(codTipoAdempimento, that.codTipoAdempimento) && Objects.equals(ordinamentoTipoAdempimento, that.ordinamentoTipoAdempimento) && Objects.equals(flgAttivo, that.flgAttivo) && Objects.equals(uuidIndex, that.uuidIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTipoAdempimento, idAmbito, desTipoAdempimento, desEstesaTipoAdempimento, codTipoAdempimento, ordinamentoTipoAdempimento, flgAttivo, uuidIndex);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipoAdempimentoDTO {");
        sb.append("         idTipoAdempimento:").append(idTipoAdempimento);
        sb.append(",         idAmbito:").append(idAmbito);
        sb.append(",         desTipoAdempimento:'").append(desTipoAdempimento).append("'");
        sb.append(",         desEstesaTipoAdempimento:'").append(desEstesaTipoAdempimento).append("'");
        sb.append(",         codTipoAdempimento:'").append(codTipoAdempimento).append("'");
        sb.append(",         ordinamentoTipoAdempimento:").append(ordinamentoTipoAdempimento);
        sb.append(",         flgAttivo:").append(flgAttivo);
        sb.append(",         uuidIndex:").append(uuidIndex);
        sb.append("}");
        return sb.toString();
    }
}