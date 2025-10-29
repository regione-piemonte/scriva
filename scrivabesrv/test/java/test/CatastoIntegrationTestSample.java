/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package test;

import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.CatastoServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.Section;
import it.csi.scriva.scrivabesrv.util.oauth2.OauthHelper;

import java.io.IOException;
import java.util.List;

public class CatastoIntegrationTestSample {

    static final String endpoint  = "http://";
    static final String serviceUrl  = "/gis/giscataapi/v1";
    static final String consumerKey  = "ConsumerKeyExample1234567890";
    static final String consumerSecret  = "ConsumerSecretExample1234567890";


    public static void main(String[] args) throws IOException {
        String token = getToken();
        System.out.println(token);
        String codiceIstatComune = "A117"; // TORINO 001272 -
        //getSezioni(codiceIstatComune);
    }

    private static void getSezioni(String codiceIstatComune) {
        try {
            CatastoServiceHelper catastoServiceHelper = new CatastoServiceHelper(endpoint, serviceUrl);
            catastoServiceHelper.setConsumerKey("ConsumerKeyExample1234567890");
            catastoServiceHelper.setConsumerSecret("ConsumerSecretExample1234567890");
            List<Section> sections = catastoServiceHelper.getSezioniPerComune(null, null, codiceIstatComune, null, null, null);
            System.out.println(sections.size());
        } catch (GenericException e) {
            e.printStackTrace();
        }
    }

    private static String getToken() throws IOException {
        String tokenUrl = endpoint + "/token";
        OauthHelper oauthHelper = new OauthHelper(
                tokenUrl,
                "ConsumerKeyExample1234567890",
                "ConsumerSecretExample1234567890",
                10000);
        return oauthHelper.getToken();
    }

}