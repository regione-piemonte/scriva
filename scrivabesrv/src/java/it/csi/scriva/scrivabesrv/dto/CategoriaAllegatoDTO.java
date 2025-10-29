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
 * The type Categoria allegato dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoriaAllegatoDTO implements Serializable {

    @JsonProperty("id_categoria_allegato")
    private Long idCategoriaAllegato;

    @JsonProperty("cod_categoria_allegato")
    private String codCategoriaAllegato;

    @JsonProperty("des_categoria_allegato")
    private String desCategoriaAllegato;

    @JsonProperty("ordinamento_categoria_allegato")
    private Integer ordinamentoCategoriaAllegato;

    @JsonProperty("flg_attivo")
    private Boolean flgAttivo;

    /**
     * Gets id categoria allegato.
     *
     * @return the id categoria allegato
     */
    public Long getIdCategoriaAllegato() {
        return idCategoriaAllegato;
    }

    /**
     * Sets id categoria allegato.
     *
     * @param idCategoriaAllegato the id categoria allegato
     */
    public void setIdCategoriaAllegato(Long idCategoriaAllegato) {
        this.idCategoriaAllegato = idCategoriaAllegato;
    }

    /**
     * Gets cod categoria allegato.
     *
     * @return the cod categoria allegato
     */
    public String getCodCategoriaAllegato() {
        return codCategoriaAllegato;
    }

    /**
     * Sets cod categoria allegato.
     *
     * @param codCategoriaAllegato the cod categoria allegato
     */
    public void setCodCategoriaAllegato(String codCategoriaAllegato) {
        this.codCategoriaAllegato = codCategoriaAllegato;
    }

    /**
     * Gets des categoria allegato.
     *
     * @return the des categoria allegato
     */
    public String getDesCategoriaAllegato() {
        return desCategoriaAllegato;
    }

    /**
     * Sets des categoria allegato.
     *
     * @param desCategoriaAllegato the des categoria allegato
     */
    public void setDesCategoriaAllegato(String desCategoriaAllegato) {
        this.desCategoriaAllegato = desCategoriaAllegato;
    }

    /**
     * Gets ordinamento categoria allegato.
     *
     * @return the ordinamento categoria allegato
     */
    public Integer getOrdinamentoCategoriaAllegato() {
        return ordinamentoCategoriaAllegato;
    }

    /**
     * Sets ordinamento categoria allegato.
     *
     * @param ordinamentoCategoriaAllegato the ordinamento categoria allegato
     */
    public void setOrdinamentoCategoriaAllegato(Integer ordinamentoCategoriaAllegato) {
        this.ordinamentoCategoriaAllegato = ordinamentoCategoriaAllegato;
    }

    /**
     * Gets flg attivo.
     *
     * @return the flg attivo
     */
    public Boolean getFlgAttivo() {
        return flgAttivo;
    }

    /**
     * Sets flg attivo.
     *
     * @param flgAttivo the flg attivo
     */
    public void setFlgAttivo(Boolean flgAttivo) {
        this.flgAttivo = flgAttivo;
    }

    /**
     * @param o object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CategoriaAllegatoDTO that = (CategoriaAllegatoDTO) o;
        return Objects.equals(idCategoriaAllegato, that.idCategoriaAllegato) && Objects.equals(codCategoriaAllegato, that.codCategoriaAllegato) && Objects.equals(desCategoriaAllegato, that.desCategoriaAllegato) && Objects.equals(ordinamentoCategoriaAllegato, that.ordinamentoCategoriaAllegato) && Objects.equals(flgAttivo, that.flgAttivo);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idCategoriaAllegato, codCategoriaAllegato, desCategoriaAllegato, ordinamentoCategoriaAllegato, flgAttivo);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CategoriaAllegatoDTO {");
        sb.append("         idCategoriaAllegato:").append(idCategoriaAllegato);
        sb.append(",         codCategoriaAllegato:'").append(codCategoriaAllegato).append("'");
        sb.append(",         desCategoriaAllegato:'").append(desCategoriaAllegato).append("'");
        sb.append(",         ordinamentoCategoriaAllegato:'").append(ordinamentoCategoriaAllegato).append("'");
        sb.append(",         flgAttivo:").append(flgAttivo);
        sb.append("}");
        return sb.toString();
    }
}