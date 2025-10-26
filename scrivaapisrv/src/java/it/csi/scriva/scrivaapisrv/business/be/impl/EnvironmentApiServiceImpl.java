/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import it.csi.scriva.scrivaapisrv.business.be.EnvironmentApi;
import it.csi.scriva.scrivaapisrv.dto.Property;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class EnvironmentApiServiceImpl implements EnvironmentApi {

    @Override
    public Response getEnvironment(SecurityContext securityContext, HttpHeaders httpHeaders,
                                   HttpServletRequest httpRequest) {
        ArrayList<Property> ris = new ArrayList<Property>();
        Map<String, String> env = System.getenv();
        Iterator<String> id_env_k = env.keySet().iterator();
        while (id_env_k.hasNext()) {
            String currK = id_env_k.next();
            Property currProp = new Property();
            currProp.setName(currK);
            currProp.setValue(env.get(currK));
            currProp.setSource("env");
            ris.add(currProp);
        }
        Properties props = System.getProperties();
        Enumeration en_prop_k = props.keys();
        while (en_prop_k.hasMoreElements()) {
            String currK = (String) en_prop_k.nextElement();
            Property currProp = new Property();
            currProp.setName(currK);
            currProp.setValue((String) props.get(currK));
            currProp.setSource("props");
            ris.add(currProp);
        }
        return Response.ok(ris).build();
    }

}