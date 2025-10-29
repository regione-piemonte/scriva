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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Tipo vincolo aut dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TipoVincoloAutDTO implements Serializable {

    @JsonProperty("id_tipo_vincolo_aut")
    private Long idTipoVincoloAut;

    @JsonProperty("cod_tipo_vincolo_aut")
    private String codTipoVincoloAut;

    @JsonProperty("des_tipo_vincolo_aut")
    private String desTipoVincoloAut;

    /**
     * Gets id tipo vincolo aut.
     *
     * @return the id tipo vincolo aut
     */
    public Long getIdTipoVincoloAut() {
        return idTipoVincoloAut;
    }

    /**
     * Sets id tipo vincolo aut.
     *
     * @param idTipoVincoloAut the id tipo vincolo aut
     */
    public void setIdTipoVincoloAut(Long idTipoVincoloAut) {
        this.idTipoVincoloAut = idTipoVincoloAut;
    }

    /**
     * Gets cod tipo vincolo aut.
     *
     * @return the cod tipo vincolo aut
     */
    public String getCodTipoVincoloAut() {
        return codTipoVincoloAut;
    }

    /**
     * Sets cod tipo vincolo aut.
     *
     * @param codTipoVincoloAut the cod tipo vincolo aut
     */
    public void setCodTipoVincoloAut(String codTipoVincoloAut) {
        this.codTipoVincoloAut = codTipoVincoloAut;
    }

    /**
     * Gets des tipo vincolo aut.
     *
     * @return the des tipo vincolo aut
     */
    public String getDesTipoVincoloAut() {
        return desTipoVincoloAut;
    }

    /**
     * Sets des tipo vincolo aut.
     *
     * @param desTipoVincoloAut the des tipo vincolo aut
     */
    public void setDesTipoVincoloAut(String desTipoVincoloAut) {
        this.desTipoVincoloAut = desTipoVincoloAut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoVincoloAutDTO that = (TipoVincoloAutDTO) o;
        return Objects.equals(idTipoVincoloAut, that.idTipoVincoloAut) && Objects.equals(codTipoVincoloAut, that.codTipoVincoloAut) && Objects.equals(desTipoVincoloAut, that.desTipoVincoloAut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTipoVincoloAut, codTipoVincoloAut, desTipoVincoloAut);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipoVincoloAutDTO {");
        sb.append("         idTipoVincoloAut:").append(idTipoVincoloAut);
        sb.append(",         codTipoVincoloAut:'").append(codTipoVincoloAut).append("'");
        sb.append(",         desTipoVincoloAut:'").append(desTipoVincoloAut).append("'");
        sb.append("}");
        return sb.toString();
    }
}