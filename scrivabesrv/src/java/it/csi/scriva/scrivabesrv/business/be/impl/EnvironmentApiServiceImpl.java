/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl;

import it.csi.scriva.scrivabesrv.business.be.EnvironmentApi;
import it.csi.scriva.scrivabesrv.dto.Property;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * The type Environment api service.
 *
 * @author CSI PIEMONTE
 */
public class EnvironmentApiServiceImpl implements EnvironmentApi{

    /**
     * {@inheritDoc}
     */
    @Override
    public Response getEnvironment(SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        ArrayList<Property> ris = new ArrayList<>();
        Map<String, String> env = System.getenv();
        for (String currK : env.keySet()) {
            Property currProp = new Property();
            currProp.setName(currK);
            currProp.setValue(env.get(currK));
            currProp.setSource("env");
            ris.add(currProp);
        }
        Properties props = System.getProperties();
        Enumeration enPropK = props.keys();
        while (enPropK.hasMoreElements()) {
            String currK = (String) enPropK.nextElement();
            Property currProp = new Property();
            currProp.setName(currK);
            currProp.setValue((String)props.get(currK));
            currProp.setSource("props");
            ris.add(currProp);
        }
        return Response.ok(ris).build();
    }

}