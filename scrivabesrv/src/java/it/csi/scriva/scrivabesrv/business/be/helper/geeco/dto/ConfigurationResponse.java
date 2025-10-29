/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Configuration response.
 *
 * @author CSI PIEMONTE
 */
public class ConfigurationResponse implements Serializable{

    private static final long serialVersionUID = -2451612165239945295L;

    private String geecourl;

    /**
     * Gets geecourl.
     *
     * @return the geecourl
     */
    public String getGeecourl() {
        return geecourl;
    }

    /**
     * Sets geecourl.
     *
     * @param geecourl the geecourl
     */
    public void setGeecourl(String geecourl) {
        this.geecourl = geecourl;
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
        ConfigurationResponse that = (ConfigurationResponse) o;
        return Objects.equals(geecourl, that.geecourl);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(geecourl);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConfigurationResponse {");
        sb.append("         geecourl:'").append(geecourl).append("'");
        sb.append("}");
        return sb.toString();
    }
}