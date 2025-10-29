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

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Map json data dto.
 *
 * @author CSI PIEMONTE
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MapJsonDataDTO implements Serializable {

    private Long idMapJson;

    private String tabella;

    private String tipoQuadro;

    private String tag;

    private String qWriteJsondata;

    private Boolean flgVisibile;

    /**
     * Gets id map json.
     *
     * @return the id map json
     */
    public Long getIdMapJson() {
        return idMapJson;
    }

    /**
     * Sets id map json.
     *
     * @param idMapJson the id map json
     */
    public void setIdMapJson(Long idMapJson) {
        this.idMapJson = idMapJson;
    }

    /**
     * Gets tabella.
     *
     * @return the tabella
     */
    public String getTabella() {
        return tabella;
    }

    /**
     * Sets tabella.
     *
     * @param tabella the tabella
     */
    public void setTabella(String tabella) {
        this.tabella = tabella;
    }

    /**
     * Gets tipo quadro.
     *
     * @return the tipo quadro
     */
    public String getTipoQuadro() {
        return tipoQuadro;
    }

    /**
     * Sets tipo quadro.
     *
     * @param tipoQuadro the tipo quadro
     */
    public void setTipoQuadro(String tipoQuadro) {
        this.tipoQuadro = tipoQuadro;
    }

    /**
     * Gets tag.
     *
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * Sets tag.
     *
     * @param tag the tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * Gets write jsondata.
     *
     * @return the write jsondata
     */
    public String getqWriteJsondata() {
        return qWriteJsondata;
    }

    /**
     * Sets write jsondata.
     *
     * @param qWriteJsondata the q write jsondata
     */
    public void setqWriteJsondata(String qWriteJsondata) {
        this.qWriteJsondata = qWriteJsondata;
    }

    /**
     * Gets flg visibile.
     *
     * @return the flg visibile
     */
    public Boolean getFlgVisibile() {
        return flgVisibile;
    }

    /**
     * Sets flg visibile.
     *
     * @param flgVisibile the flg visibile
     */
    public void setFlgVisibile(Boolean flgVisibile) {
        this.flgVisibile = flgVisibile;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapJsonDataDTO that = (MapJsonDataDTO) o;
        return Objects.equals(idMapJson, that.idMapJson) && Objects.equals(tabella, that.tabella) && Objects.equals(tipoQuadro, that.tipoQuadro) && Objects.equals(tag, that.tag) && Objects.equals(qWriteJsondata, that.qWriteJsondata) && Objects.equals(flgVisibile, that.flgVisibile);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idMapJson, tabella, tipoQuadro, tag, qWriteJsondata, flgVisibile);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "MapJsonDataDTO {\n" +
                "         idMapJson:" + idMapJson +
                ",\n         tabella:'" + tabella + "'" +
                ",\n         tipoQuadro:'" + tipoQuadro + "'" +
                ",\n         tag:'" + tag + "'" +
                ",\n         qWriteJsondata:'" + qWriteJsondata + "'" +
                ",\n         flgVisibile:" + flgVisibile +
                "}\n";
    }
}