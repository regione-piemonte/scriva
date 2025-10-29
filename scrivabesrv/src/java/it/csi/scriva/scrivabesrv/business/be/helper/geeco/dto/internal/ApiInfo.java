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

/**
 * The type Api info.
 *
 * @author CSI PIEMONTE
 */
@JsonInclude(Include.NON_NULL)
public class ApiInfo implements Serializable
{
    private static final long serialVersionUID = 6059331299893662599L;
    private String uuid;
    private String env;
    private String version;

    /**
     * Gets uuid.
     *
     * @return the uuid
     */
    public String getUuid() {
        return this.uuid;
    }

    /**
     * Sets uuid.
     *
     * @param uuid the uuid
     */
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets env.
     *
     * @return the env
     */
    public String getEnv() {
        return this.env;
    }

    /**
     * Sets env.
     *
     * @param env the env
     */
    public void setEnv(final String env) {
        this.env = env;
    }

    /**
     * Gets version.
     *
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets version.
     *
     * @param version the version
     */
    public void setVersion(String version) {
        this.version = version;
    }


}