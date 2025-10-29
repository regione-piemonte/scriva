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
 * The type Config geeco dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigGeecoDTO implements Serializable {

    @JsonProperty("uuid_geeco")
    private String uuidGeeco;

    @JsonProperty("env_geeco")
    private String envGeeco;

    @JsonProperty("version_geeco")
    private String versionGeeco;

    @JsonProperty("json_startupinfo")
    private String jsonStartupinfo;

    @JsonProperty("json_editinglayers")
    private String jsonEditinglayers;

    @JsonProperty("json_quitinfo")
    private String jsonQuitinfo;

    /**
     * Gets uuid geeco.
     *
     * @return the uuid geeco
     */
    public String getUuidGeeco() {
        return uuidGeeco;
    }

    /**
     * Sets uuid geeco.
     *
     * @param uuidGeeco the uuid geeco
     */
    public void setUuidGeeco(String uuidGeeco) {
        this.uuidGeeco = uuidGeeco;
    }

    /**
     * Gets env geeco.
     *
     * @return the env geeco
     */
    public String getEnvGeeco() {
        return envGeeco;
    }

    /**
     * Sets env geeco.
     *
     * @param envGeeco the env geeco
     */
    public void setEnvGeeco(String envGeeco) {
        this.envGeeco = envGeeco;
    }

    /**
     * Gets version geeco.
     *
     * @return the version geeco
     */
    public String getVersionGeeco() {
        return versionGeeco;
    }

    /**
     * Sets version geeco.
     *
     * @param versionGeeco the version geeco
     */
    public void setVersionGeeco(String versionGeeco) {
        this.versionGeeco = versionGeeco;
    }

    /**
     * Gets json startupinfo.
     *
     * @return the json startupinfo
     */
    public String getJsonStartupinfo() {
        return jsonStartupinfo;
    }

    /**
     * Sets json startupinfo.
     *
     * @param jsonStartupinfo the json startupinfo
     */
    public void setJsonStartupinfo(String jsonStartupinfo) {
        this.jsonStartupinfo = jsonStartupinfo;
    }

    /**
     * Gets json editinglayers.
     *
     * @return the json editinglayers
     */
    public String getJsonEditinglayers() {
        return jsonEditinglayers;
    }

    /**
     * Sets json editinglayers.
     *
     * @param jsonEditinglayers the json editinglayers
     */
    public void setJsonEditinglayers(String jsonEditinglayers) {
        this.jsonEditinglayers = jsonEditinglayers;
    }

    /**
     * Gets json quitinfo.
     *
     * @return the json quitinfo
     */
    public String getJsonQuitinfo() {
        return jsonQuitinfo;
    }

    /**
     * Sets json quitinfo.
     *
     * @param jsonQuitinfo the json quitinfo
     */
    public void setJsonQuitinfo(String jsonQuitinfo) {
        this.jsonQuitinfo = jsonQuitinfo;
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
        ConfigGeecoDTO that = (ConfigGeecoDTO) o;
        return Objects.equals(uuidGeeco, that.uuidGeeco) && Objects.equals(envGeeco, that.envGeeco) && Objects.equals(versionGeeco, that.versionGeeco) && Objects.equals(jsonStartupinfo, that.jsonStartupinfo) && Objects.equals(jsonEditinglayers, that.jsonEditinglayers) && Objects.equals(jsonQuitinfo, that.jsonQuitinfo);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(uuidGeeco, envGeeco, versionGeeco, jsonStartupinfo, jsonEditinglayers, jsonQuitinfo);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConfigGeecoDTO {");
        sb.append("         uuidGeeco:'").append(uuidGeeco).append("'");
        sb.append(",         envGeeco:'").append(envGeeco).append("'");
        sb.append(",         versionGeeco:'").append(versionGeeco).append("'");
        sb.append(",         jsonStartupinfo:'").append(jsonStartupinfo).append("'");
        sb.append(",         jsonEditinglayers:'").append(jsonEditinglayers).append("'");
        sb.append(",         jsonQuitinfo:'").append(jsonQuitinfo).append("'");
        sb.append("}");
        return sb.toString();
    }
}