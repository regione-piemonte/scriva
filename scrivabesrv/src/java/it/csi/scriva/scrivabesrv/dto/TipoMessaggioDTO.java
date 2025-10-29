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
 * The type Tipo messaggio dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoMessaggioDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_tipo_messaggio")
    private Long idTipoMessaggio;

    @JsonProperty("cod_tipo_messaggio")
    private String codTipoMessaggio;

    @JsonProperty("des_tipo_messaggio")
    private String desTipoMessaggio;

    /**
     * Gets id tipo messaggio.
     *
     * @return the id tipo messaggio
     */
    public Long getIdTipoMessaggio() {
        return idTipoMessaggio;
    }

    /**
     * Sets id tipo messaggio.
     *
     * @param idTipoMessaggio the id tipo messaggio
     */
    public void setIdTipoMessaggio(Long idTipoMessaggio) {
        this.idTipoMessaggio = idTipoMessaggio;
    }

    /**
     * Gets cod tipo messaggio.
     *
     * @return the cod tipo messaggio
     */
    public String getCodTipoMessaggio() {
        return codTipoMessaggio;
    }

    /**
     * Sets cod tipo messaggio.
     *
     * @param codTipoMessaggio the cod tipo messaggio
     */
    public void setCodTipoMessaggio(String codTipoMessaggio) {
        this.codTipoMessaggio = codTipoMessaggio;
    }

    /**
     * Gets des tipo messaggio.
     *
     * @return the des tipo messaggio
     */
    public String getDesTipoMessaggio() {
        return desTipoMessaggio;
    }

    /**
     * Sets des tipo messaggio.
     *
     * @param desTipoMessaggio the des tipo messaggio
     */
    public void setDesTipoMessaggio(String desTipoMessaggio) {
        this.desTipoMessaggio = desTipoMessaggio;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(codTipoMessaggio, desTipoMessaggio, idTipoMessaggio);
    }

    /**
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TipoMessaggioDTO other = (TipoMessaggioDTO) obj;
        return Objects.equals(codTipoMessaggio, other.codTipoMessaggio)
                && Objects.equals(desTipoMessaggio, other.desTipoMessaggio)
                && Objects.equals(idTipoMessaggio, other.idTipoMessaggio);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipoMessaggioDTO {");
        sb.append("         idTipoMessaggio:").append(idTipoMessaggio);
        sb.append(",         codTipoMessaggio:'").append(codTipoMessaggio).append("'");
        sb.append(",         desTipoMessaggio:'").append(desTipoMessaggio).append("'");
        sb.append("}");
        return sb.toString();
    }


}