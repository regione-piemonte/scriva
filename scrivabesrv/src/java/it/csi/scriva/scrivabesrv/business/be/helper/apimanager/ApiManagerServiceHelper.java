/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.apimanager;

import it.csi.scriva.scrivabesrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivabesrv.util.oauth2.OauthHelper;

/**
 * The type Api manager service helper.
 */
public class ApiManagerServiceHelper extends AbstractServiceHelper {

    /**
     * The constant X_AUTH.
     */
    public static final String X_AUTH = "X-Authorization";

    /**
     * Instantiates a new Api manager service helper.
     *
     * @param tokenUrl the token url
     */
    public ApiManagerServiceHelper(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    /**
     * Gets oauth helper.
     *
     * @return the oauth helper
     */
    public final OauthHelper getOauthHelper() {
        LOGGER.debug("[ApiManagerServiceHelper::getOauthHelper] BEGIN");

        OauthHelper oauthHelper = new OauthHelper(
                tokenUrl,
                consumerKey,
                consumerSecret,
                10000);

        LOGGER.debug("[ApiManagerServiceHelper::getOauthHelper] END");
        return oauthHelper;
    }
}