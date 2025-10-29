/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author CSI PIEMONTE
 */
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

    public Long getIdCategoriaAllegato() {
        return idCategoriaAllegato;
    }

    public void setIdCategoriaAllegato(Long idCategoriaAllegato) {
        this.idCategoriaAllegato = idCategoriaAllegato;
    }

    public String getCodCategoriaAllegato() {
        return codCategoriaAllegato;
    }

    public void setCodCategoriaAllegato(String codCategoriaAllegato) {
        this.codCategoriaAllegato = codCategoriaAllegato;
    }

    public String getDesCategoriaAllegato() {
        return desCategoriaAllegato;
    }

    public void setDesCategoriaAllegato(String desCategoriaAllegato) {
        this.desCategoriaAllegato = desCategoriaAllegato;
    }

    public Integer getOrdinamentoCategoriaAllegato() {
        return ordinamentoCategoriaAllegato;
    }

    public void setOrdinamentoCategoriaAllegato(Integer ordinamentoCategoriaAllegato) {
        this.ordinamentoCategoriaAllegato = ordinamentoCategoriaAllegato;
    }

    public Boolean getFlgAttivo() {
        return flgAttivo;
    }

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