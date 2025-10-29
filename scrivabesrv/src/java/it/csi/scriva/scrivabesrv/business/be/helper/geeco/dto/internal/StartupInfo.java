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
import java.util.Arrays;
import java.util.Objects;

/**
 * StartupInfo class
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(Include.NON_NULL)
public class StartupInfo implements Serializable {
    private static final long serialVersionUID = -1077155039247589718L;
    private boolean readonly;
    private boolean zoomEnabled;
    private boolean showInputFeatures;
    private boolean showLabelOnFeatures;
    private int[] sessionEditableLayers;
//    private String authLevel;
//    private String authCommunity;
    private boolean https;
    private String activeToolOnStartup;

    /**
     * Instantiates a new Startup info.
     */
    public StartupInfo() {
        this.readonly = false;
        this.zoomEnabled = false;
        this.showInputFeatures = false;
        this.showLabelOnFeatures = false;
        this.https = true;
    }

    /**
     * Is https boolean.
     *
     * @return the boolean
     */
    public boolean isHttps() {
        return this.https;
    }

    /**
     * Sets https.
     *
     * @param https the https
     */
    public void setHttps(final boolean https) {
        this.https = https;
    }

    /**
     * Is readonly boolean.
     *
     * @return the boolean
     */
    public boolean isReadonly() {
        return this.readonly;
    }

    /**
     * Sets readonly.
     *
     * @param readonly the readonly
     */
    public void setReadonly(final boolean readonly) {
        this.readonly = readonly;
    }

    /**
     * Is zoom enabled boolean.
     *
     * @return the boolean
     */
    public boolean isZoomEnabled() {
        return this.zoomEnabled;
    }

    /**
     * Sets zoom enabled.
     *
     * @param zoomEnabled the zoom enabled
     */
    public void setZoomEnabled(final boolean zoomEnabled) {
        this.zoomEnabled = zoomEnabled;
    }

    /**
     * Is show input features boolean.
     *
     * @return the boolean
     */
    public boolean isShowInputFeatures() {
        return this.showInputFeatures;
    }

    /**
     * Sets show input features.
     *
     * @param showInputFeatures the show input features
     */
    public void setShowInputFeatures(final boolean showInputFeatures) {
        this.showInputFeatures = showInputFeatures;
    }

    /**
     * Get session editable layers int [ ].
     *
     * @return the int [ ]
     */
    public int[] getSessionEditableLayers() {
        return this.sessionEditableLayers;
    }

    /**
     * Sets session editable layers.
     *
     * @param sessionEditableLayers the session editable layers
     */
    public void setSessionEditableLayers(final int[] sessionEditableLayers) {
        this.sessionEditableLayers = sessionEditableLayers;
    }

//    public String getAuthLevel() {
//        return this.authLevel;
//    }
//
//    public void setAuthLevel(final String authLevel) {
//        if (authLevel != null && !authLevel.equalsIgnoreCase("liv1") && !authLevel.equalsIgnoreCase("liv2") && !authLevel.equalsIgnoreCase("liv3")) {
//            throw new UnsupportedOperationException("Livello di autenticazione non supportato. Per ora GEECO gestisce LIV1, LIV2 e LIV3");
//        }
//        this.authLevel = "liv1";
//    }
//
//    public String getAuthCommunity() {
//        return this.authCommunity;
//    }
//
//    public void setAuthCommunity(final String authCommunity) {
//        if (this.authLevel != null && !this.authLevel.equals("sisp") && !this.authLevel.equals("wrup")) {
//            throw new UnsupportedOperationException("Community di autenticazione non supportata. Per ora GEECO gestisce solo WRUP e SISP");
//        }
//        this.authCommunity = authCommunity;
//    }

    /**
     * Is show label on features boolean.
     *
     * @return the boolean
     */
    public boolean isShowLabelOnFeatures() {
        return this.showLabelOnFeatures;
    }

    /**
     * Sets show label on features.
     *
     * @param showLabelOnFeatures the show label on features
     */
    public void setShowLabelOnFeatures(final boolean showLabelOnFeatures) {
        this.showLabelOnFeatures = showLabelOnFeatures;
    }

    /**
     * Gets active tool on startup.
     *
     * @return the active tool on startup
     */
    public String getActiveToolOnStartup() {
        return this.activeToolOnStartup;
    }

    /**
     * Sets active tool on startup.
     *
     * @param activeToolOnStartup the active tool on startup
     */
    public void setActiveToolOnStartup(final String activeToolOnStartup) {
        this.activeToolOnStartup = activeToolOnStartup;
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
        StartupInfo that = (StartupInfo) o;
        return readonly == that.readonly && zoomEnabled == that.zoomEnabled && showInputFeatures == that.showInputFeatures && showLabelOnFeatures == that.showLabelOnFeatures && https == that.https && Arrays.equals(sessionEditableLayers, that.sessionEditableLayers) && Objects.equals(activeToolOnStartup, that.activeToolOnStartup);
    }

    /**
     * hashCode
     *
     * @return int
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(readonly, zoomEnabled, showInputFeatures, showLabelOnFeatures, https, activeToolOnStartup);
        result = 31 * result + Arrays.hashCode(sessionEditableLayers);
        return result;
    }

    /**
     * toString
     *
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StartupInfo {");
        sb.append("         readonly:").append(readonly);
        sb.append(",         zoomEnabled:").append(zoomEnabled);
        sb.append(",         showInputFeatures:").append(showInputFeatures);
        sb.append(",         showLabelOnFeatures:").append(showLabelOnFeatures);
        sb.append(",         sessionEditableLayers:").append(Arrays.toString(sessionEditableLayers));
        sb.append(",         https:").append(https);
        sb.append(",         activeToolOnStartup:'").append(activeToolOnStartup).append("'");
        sb.append("}");
        return sb.toString();
    }
}