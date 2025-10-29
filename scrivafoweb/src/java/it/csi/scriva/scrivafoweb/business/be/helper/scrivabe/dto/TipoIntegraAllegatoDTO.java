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
 *
 * @author CSI PIEMONTE
 */
public class TipoIntegraAllegatoDTO implements Serializable {

    @JsonProperty("id_tipo_integra_allegato")
    private Long idTipoIntegraAllegato;

    @JsonProperty("cod_tipo_integra_allegato")
    private String codTipoIntegraAllegato;

    @JsonProperty("des_tipo_integra_allegato")
    private String desTipoIntegraAllegato;

    public Long getIdTipoIntegraAllegato() {
        return idTipoIntegraAllegato;
    }

    public void setIdTipoIntegraAllegato(Long idTipoIntegraAllegato) {
        this.idTipoIntegraAllegato = idTipoIntegraAllegato;
    }

    public String getCodTipoIntegraAllegato() {
        return codTipoIntegraAllegato;
    }

    public void setCodTipoIntegraAllegato(String codTipoIntegraAllegato) {
        this.codTipoIntegraAllegato = codTipoIntegraAllegato;
    }

    public String getDesTipoIntegraAllegato() {
        return desTipoIntegraAllegato;
    }

    public void setDesTipoIntegraAllegato(String desTipoIntegraAllegato) {
        this.desTipoIntegraAllegato = desTipoIntegraAllegato;
    }

    /**
     * @param o object
     * @return booloean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TipoIntegraAllegatoDTO that = (TipoIntegraAllegatoDTO) o;
        return Objects.equals(idTipoIntegraAllegato, that.idTipoIntegraAllegato) && Objects.equals(codTipoIntegraAllegato, that.codTipoIntegraAllegato) && Objects.equals(desTipoIntegraAllegato, that.desTipoIntegraAllegato);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTipoIntegraAllegato, codTipoIntegraAllegato, desTipoIntegraAllegato);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipoIntegraAllegatoDTO {");
        sb.append("         idTipoIntegraAllegato:").append(idTipoIntegraAllegato);
        sb.append(",         codTipoIntegraAllegato:'").append(codTipoIntegraAllegato).append("'");
        sb.append(",         desTipoIntegraAllegato:'").append(desTipoIntegraAllegato).append("'");
        sb.append("}");
        return sb.toString();
    }
}