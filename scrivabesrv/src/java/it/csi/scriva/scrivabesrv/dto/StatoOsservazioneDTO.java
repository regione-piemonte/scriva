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
 * The type Stato osservazione dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatoOsservazioneDTO implements Serializable {

    @JsonProperty("id_stato_osservazione")
    private Long idStatoOsservazione;

    @JsonProperty("cod_stato_osservazione")
    private String codStatoOsservazione;

    @JsonProperty("des_stato_osservazione")
    private String desStatoOsservazione;

    /**
     * Gets id stato osservazione.
     *
     * @return the id stato osservazione
     */
    public Long getIdStatoOsservazione() {
        return idStatoOsservazione;
    }

    /**
     * Sets id stato osservazione.
     *
     * @param idStatoOsservazione the id stato osservazione
     */
    public void setIdStatoOsservazione(Long idStatoOsservazione) {
        this.idStatoOsservazione = idStatoOsservazione;
    }

    /**
     * Gets cod stato osservazione.
     *
     * @return the cod stato osservazione
     */
    public String getCodStatoOsservazione() {
        return codStatoOsservazione;
    }

    /**
     * Sets cod stato osservazione.
     *
     * @param codStatoOsservazione the cod stato osservazione
     */
    public void setCodStatoOsservazione(String codStatoOsservazione) {
        this.codStatoOsservazione = codStatoOsservazione;
    }

    /**
     * Gets des stato osservazione.
     *
     * @return the des stato osservazione
     */
    public String getDesStatoOsservazione() {
        return desStatoOsservazione;
    }

    /**
     * Sets des stato osservazione.
     *
     * @param desStatoOsservazione the des stato osservazione
     */
    public void setDesStatoOsservazione(String desStatoOsservazione) {
        this.desStatoOsservazione = desStatoOsservazione;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatoOsservazioneDTO that = (StatoOsservazioneDTO) o;
        return Objects.equals(idStatoOsservazione, that.idStatoOsservazione) && Objects.equals(codStatoOsservazione, that.codStatoOsservazione) && Objects.equals(desStatoOsservazione, that.desStatoOsservazione);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idStatoOsservazione, codStatoOsservazione, desStatoOsservazione);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatoOsservazioneDTO {\n");
        sb.append("         idStatoOsservazione:").append(idStatoOsservazione);
        sb.append(",\n         codStatoOsservazione:'").append(codStatoOsservazione).append("'");
        sb.append(",\n         desStatoOsservazione:'").append(desStatoOsservazione).append("'");
        sb.append("}\n");
        return sb.toString();
    }
}