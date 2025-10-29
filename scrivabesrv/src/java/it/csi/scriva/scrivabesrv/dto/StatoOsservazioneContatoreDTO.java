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

/**
 * The type Stato osservazione dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatoOsservazioneContatoreDTO implements Serializable {

    @JsonProperty("stato_osservazione")
    private String statoOsservazione;

    @JsonProperty("numero_osservazione")
    private Long numeroOsservazione;


    /**
     * Gets stato osservazione.
     *
     * @return the stato osservazione
     */
    public String getStatoOsservazione() {
        return statoOsservazione;
    }


    /**
     * Sets stato osservazione.
     *
     * @param statoOsservazione the stato osservazione
     */
    public void setStatoOsservazione(String statoOsservazione) {
        this.statoOsservazione = statoOsservazione;
    }


    /**
     * Gets numero osservazione.
     *
     * @return the numero osservazione
     */
    public Long getNumeroOsservazione() {
        return numeroOsservazione;
    }


    /**
     * Sets numero osservazione.
     *
     * @param numeroOsservazione the numero osservazione
     */
    public void setNumeroOsservazione(Long numeroOsservazione) {
        this.numeroOsservazione = numeroOsservazione;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((numeroOsservazione == null) ? 0 : numeroOsservazione.hashCode());
        result = prime * result + ((statoOsservazione == null) ? 0 : statoOsservazione.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StatoOsservazioneContatoreDTO other = (StatoOsservazioneContatoreDTO) obj;
        if (numeroOsservazione == null) {
            if (other.numeroOsservazione != null)
                return false;
        } else if (!numeroOsservazione.equals(other.numeroOsservazione))
            return false;
        if (statoOsservazione == null) {
            if (other.statoOsservazione != null)
                return false;
        } else if (!statoOsservazione.equals(other.statoOsservazione))
            return false;
        return true;
    }


    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatoOsservazioneContatoreDTO {\n");
        sb.append("         statoOsservazione:").append(statoOsservazione);
        sb.append(",\n         numeroOsservazione:'").append(numeroOsservazione).append("'");
        sb.append("}\n");
        return sb.toString();
    }
}