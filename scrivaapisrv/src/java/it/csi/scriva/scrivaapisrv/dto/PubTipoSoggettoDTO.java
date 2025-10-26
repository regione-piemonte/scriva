/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Pub tipo soggetto dto.
 *
 * @author CSI PIEMONTE
 */
public class PubTipoSoggettoDTO implements Serializable {

    @JsonProperty("cod_tipo_soggetto")
    private String codTipoSoggetto;

    @JsonProperty("Des_tipo_soggetto")
    private String desTipoSoggetto;

    /**
     * Gets cod tipo soggetto.
     *
     * @return the cod tipo soggetto
     */
    public String getCodTipoSoggetto() {
        return codTipoSoggetto;
    }

    /**
     * Sets cod tipo soggetto.
     *
     * @param codTipoSoggetto the cod tipo soggetto
     */
    public void setCodTipoSoggetto(String codTipoSoggetto) {
        this.codTipoSoggetto = codTipoSoggetto;
    }

    /**
     * Gets des tipo soggetto.
     *
     * @return the des tipo soggetto
     */
    public String getDesTipoSoggetto() {
        return desTipoSoggetto;
    }

    /**
     * Sets des tipo soggetto.
     *
     * @param desTipoSoggetto the des tipo soggetto
     */
    public void setDesTipoSoggetto(String desTipoSoggetto) {
        this.desTipoSoggetto = desTipoSoggetto;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PubTipoSoggettoDTO that = (PubTipoSoggettoDTO) o;
        return Objects.equals(codTipoSoggetto, that.codTipoSoggetto) && Objects.equals(desTipoSoggetto, that.desTipoSoggetto);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(codTipoSoggetto, desTipoSoggetto);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PubTipoSoggettoDTO {\n");
        sb.append("         codTipoSoggetto:'").append(codTipoSoggetto).append("'");
        sb.append(",\n         desTipoSoggetto:'").append(desTipoSoggetto).append("'");
        sb.append("}\n");
        return sb.toString();
    }
}