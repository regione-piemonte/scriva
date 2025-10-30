/*-
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaconsweb.util;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.csi.scriva.scrivaconsweb.common.Constants;

public class NoProxyHostPetternUtil {

	private static final Logger logger = LoggerFactory.getLogger(Constants.UTIL);
	
	public static Pattern createNonProxyPattern(String nonProxyHosts) {
        if (nonProxyHosts == null || nonProxyHosts.equals("")) return null;

        // "*.fedora-commons.org" -> ".*?\.fedora-commons\.org" 
        nonProxyHosts = nonProxyHosts.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*?");

        // a|b|*.c -> (a)|(b)|(.*?\.c)
        //nonProxyHosts = "(" + nonProxyHosts.replaceAll("\\|", ")|(") + ")";
        nonProxyHosts = "(" + nonProxyHosts.replaceAll("\\|", ".*)|(") + ")";

        try {
            return Pattern.compile(nonProxyHosts);

            //we don't want to bring down the whole server by misusing the nonProxy pattern
            //therefore the error is logged and the web client moves on.
        } catch (Exception e) {
        	logger.error("Creating the nonProxyHosts pattern failed for http.nonProxyHosts="
                            + nonProxyHosts
                            + " with the following exception: "
                            + e);
            return null;
        }
    }
	
	
}
