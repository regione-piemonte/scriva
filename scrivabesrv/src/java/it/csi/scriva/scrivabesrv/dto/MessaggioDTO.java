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
 * The type Messaggio dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessaggioDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_messaggio")
    private Long idMessaggio;

    @JsonProperty("id_tipo_messaggio")
    private Long idTipoMessaggio;

    @JsonProperty("cod_messaggio")
    private String codMessaggio;

    @JsonProperty("des_testo_messaggio")
    private String desTestoMessaggio;

    /**
     * Gets id messaggio.
     *
     * @return the id messaggio
     */
    public Long getIdMessaggio() {
        return idMessaggio;
    }

    /**
     * Sets id messaggio.
     *
     * @param idMessaggio the id messaggio
     */
    public void setIdMessaggio(Long idMessaggio) {
        this.idMessaggio = idMessaggio;
    }

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
     * Gets cod messaggio.
     *
     * @return the cod messaggio
     */
    public String getCodMessaggio() {
        return codMessaggio;
    }

    /**
     * Sets cod messaggio.
     *
     * @param codMessaggio the cod messaggio
     */
    public void setCodMessaggio(String codMessaggio) {
        this.codMessaggio = codMessaggio;
    }

    /**
     * Gets des testo messaggio.
     *
     * @return the des testo messaggio
     */
    public String getDesTestoMessaggio() {
        return desTestoMessaggio;
    }

    /**
     * Sets des testo messaggio.
     *
     * @param desTestoMessaggio the des testo messaggio
     */
    public void setDesTestoMessaggio(String desTestoMessaggio) {
        this.desTestoMessaggio = desTestoMessaggio;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MessaggioDTO that = (MessaggioDTO) o;
        return Objects.equals(idMessaggio, that.idMessaggio) && Objects.equals(idTipoMessaggio, that.idTipoMessaggio) && Objects.equals(codMessaggio, that.codMessaggio) && Objects.equals(desTestoMessaggio, that.desTestoMessaggio);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idMessaggio, idTipoMessaggio, codMessaggio, desTestoMessaggio);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MessaggioDTO {");
        sb.append("         idMessaggio:").append(idMessaggio);
        sb.append(",         idTipoMessaggio:").append(idTipoMessaggio);
        sb.append(",         codMessaggio:'").append(codMessaggio).append("'");
        sb.append(",         desTestoMessaggio:'").append(desTestoMessaggio).append("'");
        sb.append("}");
        return sb.toString();
    }
}