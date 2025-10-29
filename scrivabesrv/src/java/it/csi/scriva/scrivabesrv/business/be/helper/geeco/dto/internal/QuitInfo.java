/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.Objects;

/**
 * QuitInfo class
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(Include.NON_NULL)
public class QuitInfo implements Serializable {
    private static final long serialVersionUID = -8777246017013960230L;

    private String url;


    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Sets url.
     *
     * @param url the url
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * equals
     *
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        QuitInfo quitInfo = (QuitInfo) o;
        return Objects.equals(url, quitInfo.url);
    }

    /**
     * hashCode
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    /**
     * toString
     *
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("QuitInfo {");
        sb.append("         url:'").append(url).append("'");
        sb.append("}");
        return sb.toString();
    }
}