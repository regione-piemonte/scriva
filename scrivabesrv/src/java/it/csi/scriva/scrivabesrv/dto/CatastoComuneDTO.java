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
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * The type Catasto comune dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(Include.NON_NULL)
public class CatastoComuneDTO implements Serializable {

    @JsonProperty("denom_comune")
    private String comune = null;

    @JsonProperty("cod_istat_comune")
    private String codComIstat = null;

    @JsonProperty("cod_belfiore_comune")
    private String codComBelfiore = null;

    @JsonProperty("sigla_provincia")
    private String siglaProvincia = null;

    @JsonProperty("cod_provincia")
    private String istatProvincia = null;

    /**
     * Gets comune.
     *
     * @return the comune
     */
    public String getComune() {
        return comune;
    }

    /**
     * Sets comune.
     *
     * @param comune the comune
     */
    public void setComune(String comune) {
        this.comune = comune;
    }

    /**
     * Gets cod com istat.
     *
     * @return the cod com istat
     */
    public String getCodComIstat() {
        return codComIstat;
    }

    /**
     * Sets cod com istat.
     *
     * @param codComIstat the cod com istat
     */
    public void setCodComIstat(String codComIstat) {
        this.codComIstat = codComIstat;
    }

    /**
     * Gets cod com belfiore.
     *
     * @return the cod com belfiore
     */
    public String getCodComBelfiore() {
        return codComBelfiore;
    }

    /**
     * Sets cod com belfiore.
     *
     * @param codComBelfiore the cod com belfiore
     */
    public void setCodComBelfiore(String codComBelfiore) {
        this.codComBelfiore = codComBelfiore;
    }

    /**
     * Gets sigla provincia.
     *
     * @return the sigla provincia
     */
    public String getSiglaProvincia() {
        return siglaProvincia;
    }

    /**
     * Sets sigla provincia.
     *
     * @param siglaProvincia the sigla provincia
     */
    public void setSiglaProvincia(String siglaProvincia) {
        this.siglaProvincia = siglaProvincia;
    }

    /**
     * Gets istat provincia.
     *
     * @return the istat provincia
     */
    public String getIstatProvincia() {
        return istatProvincia;
    }

    /**
     * Sets istat provincia.
     *
     * @param istatProvincia the istat provincia
     */
    public void setIstatProvincia(String istatProvincia) {
        this.istatProvincia = istatProvincia;
    }
}