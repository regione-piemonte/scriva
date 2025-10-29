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
 * The type Mappa fonte esterna dto.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MappaFonteEsternaDTO implements Serializable {

    @JsonProperty("id_mappa_fonte_esterna")
    private Long idMappaFonteEsterna;

    @JsonProperty("cod_masterdata")
    private String codMasterdata;

    @JsonProperty("info_fonte")
    private String infoFonte;

    @JsonProperty("cod_fonte")
    private String codFonte;

    @JsonProperty("cod_scriva")
    private String codScriva;

    /**
     * Gets id mappa fonte esterna.
     *
     * @return the id mappa fonte esterna
     */
    public Long getIdMappaFonteEsterna() {
        return idMappaFonteEsterna;
    }

    /**
     * Sets id mappa fonte esterna.
     *
     * @param idMappaFonteEsterna the id mappa fonte esterna
     */
    public void setIdMappaFonteEsterna(Long idMappaFonteEsterna) {
        this.idMappaFonteEsterna = idMappaFonteEsterna;
    }

    /**
     * Gets cod masterdata.
     *
     * @return the cod masterdata
     */
    public String getCodMasterdata() {
        return codMasterdata;
    }

    /**
     * Sets cod masterdata.
     *
     * @param codMasterdata the cod masterdata
     */
    public void setCodMasterdata(String codMasterdata) {
        this.codMasterdata = codMasterdata;
    }

    /**
     * Gets info fonte.
     *
     * @return the info fonte
     */
    public String getInfoFonte() {
        return infoFonte;
    }

    /**
     * Sets info fonte.
     *
     * @param infoFonte the info fonte
     */
    public void setInfoFonte(String infoFonte) {
        this.infoFonte = infoFonte;
    }

    /**
     * Gets cod gonte.
     *
     * @return the cod gonte
     */
    public String getCodFonte() {
        return codFonte;
    }

    /**
     * Sets cod gonte.
     *
     * @param codFonte the cod gonte
     */
    public void setCodFonte(String codFonte) {
        this.codFonte = codFonte;
    }

    /**
     * Gets cod scriva.
     *
     * @return the cod scriva
     */
    public String getCodScriva() {
        return codScriva;
    }

    /**
     * Sets cod scriva.
     *
     * @param codScriva the cod scriva
     */
    public void setCodScriva(String codScriva) {
        this.codScriva = codScriva;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MappaFonteEsternaDTO that = (MappaFonteEsternaDTO) o;
        return Objects.equals(idMappaFonteEsterna, that.idMappaFonteEsterna) && Objects.equals(codMasterdata, that.codMasterdata) && Objects.equals(infoFonte, that.infoFonte) && Objects.equals(codFonte, that.codFonte) && Objects.equals(codScriva, that.codScriva);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idMappaFonteEsterna, codMasterdata, infoFonte, codFonte, codScriva);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MappaFonteEsternaDTO {\n");
        sb.append("         idMappaFonteEsterna:").append(idMappaFonteEsterna);
        sb.append(",\n         codMasterdata:'").append(codMasterdata).append("'");
        sb.append(",\n         infoFonte:'").append(infoFonte).append("'");
        sb.append(",\n         codGonte:'").append(codFonte).append("'");
        sb.append(",\n         codScriva:'").append(codScriva).append("'");
        sb.append("}\n");
        return sb.toString();
    }
}