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
 * The type Tipo integra allegato dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoIntegraAllegatoDTO implements Serializable {

    @JsonProperty("id_tipo_integra_allegato")
    private Long idTipoIntegraAllegato;

    @JsonProperty("cod_tipo_integra_allegato")
    private String codTipoIntegraAllegato;

    @JsonProperty("des_tipo_integra_allegato")
    private String desTipoIntegraAllegato;

    /**
     * Gets id tipo integra allegato.
     *
     * @return the id tipo integra allegato
     */
    public Long getIdTipoIntegraAllegato() {
        return idTipoIntegraAllegato;
    }

    /**
     * Sets id tipo integra allegato.
     *
     * @param idTipoIntegraAllegato the id tipo integra allegato
     */
    public void setIdTipoIntegraAllegato(Long idTipoIntegraAllegato) {
        this.idTipoIntegraAllegato = idTipoIntegraAllegato;
    }

    /**
     * Gets cod tipo integra allegato.
     *
     * @return the cod tipo integra allegato
     */
    public String getCodTipoIntegraAllegato() {
        return codTipoIntegraAllegato;
    }

    /**
     * Sets cod tipo integra allegato.
     *
     * @param codTipoIntegraAllegato the cod tipo integra allegato
     */
    public void setCodTipoIntegraAllegato(String codTipoIntegraAllegato) {
        this.codTipoIntegraAllegato = codTipoIntegraAllegato;
    }

    /**
     * Gets des tipo integra allegato.
     *
     * @return the des tipo integra allegato
     */
    public String getDesTipoIntegraAllegato() {
        return desTipoIntegraAllegato;
    }

    /**
     * Sets des tipo integra allegato.
     *
     * @param desTipoIntegraAllegato the des tipo integra allegato
     */
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