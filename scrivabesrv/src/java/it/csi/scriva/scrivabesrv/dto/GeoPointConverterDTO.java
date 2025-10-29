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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * The type GeoPointConverter dto.
 *
 * @author CSI PIEMONTE
 */
public class GeoPointConverterDTO implements Serializable {

    @JsonProperty("epsg_sorgente")
    private Integer sourceEPSG = 4326;

    @JsonProperty("latitudine_sorgente")
    private BigDecimal sourceLAT;

    @JsonProperty("longitudine_sorgente")
    private BigDecimal sourceLON;


    @JsonProperty("epsg_destinazione")
    private Integer targetEPSG = 32632;

    @JsonProperty("latitudine_destinazione")
    private BigDecimal targetLAT;

    @JsonProperty("longitudine_destinazione")
    private BigDecimal targetLON;

    public Integer getSourceEPSG() {
        return sourceEPSG;
    }

    public void setSourceEPSG(Integer sourceEPSG) {
        this.sourceEPSG = sourceEPSG;
    }

    public BigDecimal getSourceLAT() {
        return sourceLAT;
    }

    public void setSourceLAT(BigDecimal sourceLAT) {
        this.sourceLAT = sourceLAT;
    }

    public BigDecimal getSourceLON() {
        return sourceLON;
    }

    public void setSourceLON(BigDecimal sourceLON) {
        this.sourceLON = sourceLON;
    }

    public Integer getTargetEPSG() {
        return targetEPSG;
    }

    public void setTargetEPSG(Integer targetEPSG) {
        this.targetEPSG = targetEPSG;
    }

    public BigDecimal getTargetLAT() {
        return targetLAT;
    }

    public void setTargetLAT(BigDecimal targetLAT) {
        this.targetLAT = targetLAT;
    }

    public BigDecimal getTargetLON() {
        return targetLON;
    }

    public void setTargetLON(BigDecimal targetLON) {
        this.targetLON = targetLON;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeoPointConverterDTO)) return false;
        GeoPointConverterDTO that = (GeoPointConverterDTO) o;
        return Objects.equals(sourceEPSG, that.sourceEPSG) && Objects.equals(sourceLAT, that.sourceLAT) && Objects.equals(sourceLON, that.sourceLON) && Objects.equals(targetEPSG, that.targetEPSG) && Objects.equals(targetLAT, that.targetLAT) && Objects.equals(targetLON, that.targetLON);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceEPSG, sourceLAT, sourceLON, targetEPSG, targetLAT, targetLON);
    }

    @Override
    public String toString() {
        return new StringJoiner("\n ", GeoPointConverterDTO.class.getSimpleName() + "[", "]")
                .add("sourceEPSG=" + sourceEPSG)
                .add("sourceLAT=" + sourceLAT)
                .add("sourceLON=" + sourceLON)
                .add("targetEPSG=" + targetEPSG)
                .add("targetLAT=" + targetLAT)
                .add("targetLON=" + targetLON)
                .toString();
    }


}