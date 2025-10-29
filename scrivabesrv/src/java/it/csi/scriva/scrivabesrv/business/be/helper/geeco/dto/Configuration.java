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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.ApiInfo;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.Layer;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.QuitInfo;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.StartupInfo;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * The type Configuration.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(Include.NON_NULL)
public class Configuration implements Serializable
{
    private static final long serialVersionUID = -6942275371303943138L;
    @JsonProperty("ApiInfo")
    private ApiInfo apiInfo;
    @JsonProperty("StartupInfo")
    private StartupInfo startupInfo;
    @JsonProperty("EditingLayers")
    private Map<String, Layer> editingLayers;
    @JsonProperty("QuitInfo")
    private QuitInfo quitInfo;


    /**
     * Gets api info.
     *
     * @return the api info
     */
    public ApiInfo getApiInfo() {
        return this.apiInfo;
    }

    /**
     * Sets api info.
     *
     * @param apiInfo the api info
     */
    public void setApiInfo(final ApiInfo apiInfo) {
        this.apiInfo = apiInfo;
    }

    /**
     * Gets startup info.
     *
     * @return the startup info
     */
    public StartupInfo getStartupInfo() {
        return this.startupInfo;
    }

    /**
     * Sets startup info.
     *
     * @param startupInfo the startup info
     */
    public void setStartupInfo(final StartupInfo startupInfo) {
        this.startupInfo = startupInfo;
    }

    /**
     * Gets quit info.
     *
     * @return the quit info
     */
    public QuitInfo getQuitInfo() {
        return this.quitInfo;
    }

    /**
     * Sets quit info.
     *
     * @param quitInfo the quit info
     */
    public void setQuitInfo(final QuitInfo quitInfo) {
        this.quitInfo = quitInfo;
    }

    /**
     * Gets editing layers.
     *
     * @return the editing layers
     */
    public Map<String, Layer> getEditingLayers() {
        return editingLayers;
    }

    /**
     * Sets editing layers.
     *
     * @param editingLayers the editing layers
     */
    public void setEditingLayers(Map<String, Layer> editingLayers) {
        this.editingLayers = editingLayers;
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
        Configuration that = (Configuration) o;
        return Objects.equals(apiInfo, that.apiInfo) && Objects.equals(startupInfo, that.startupInfo) && Objects.equals(editingLayers, that.editingLayers) && Objects.equals(quitInfo, that.quitInfo);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(apiInfo, startupInfo, editingLayers, quitInfo);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Configuration {");
        sb.append("         apiInfo:").append(apiInfo);
        sb.append(",         startupInfo:").append(startupInfo);
        sb.append(",         editingLayers:").append(editingLayers);
        sb.append(",         quitInfo:").append(quitInfo);
        sb.append("}");
        return sb.toString();
    }
}