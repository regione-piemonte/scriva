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

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.IOException;


public class ApiManagerTest {

    private static final String apiManagerUrl = "https://";
    private static final String tokenEndPoint = "/token";
    private static final String indexEndPoint = "/documentale/indexcsiexp-exp03/v1";
    private static final String modelsEndpoint = "/tenants/scriva/models";

    private static final String tokenUrl = apiManagerUrl + tokenEndPoint;
    private static final String indexUrl = apiManagerUrl + indexEndPoint + modelsEndpoint;

    public static void main(String[] args) throws IOException {
        try {
            // istanzia apiManager
            /*
            ApiManagerServiceHelper apiManager = new ApiManagerServiceHelper(tokenUrl);
            OauthHelper oauthHelper = apiManager.getOauthHelper();
            String token = oauthHelper.getToken();
            System.out.println("Token : " + token);
            */

            //chiamata servizio index
            String token="token_da_recuperare_con_la_chiamata_di_authentication";
            String urlPing = "https://";
            Client client = ClientBuilder.newBuilder().build();
            WebTarget target = client.target(urlPing);
            //System.out.println(indexUrl);
            Response resp = target.request().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).get();
            if (resp.getStatus() >= 400) {
                //GenericType<Error> errGenericType = new GenericType<Error>() {};
                //Error err = resp.readEntity(errGenericType);
                System.out.println("SERVER EXCEPTION : " + resp.toString());
                //throw new GenericException(err.getMessage());
            } else {
                System.out.println(resp.getHeaders());
            }
            //GenericType<ConfigurationResponse> configurationResponseType = new GenericType<ConfigurationResponse>() {            };
            //ConfigurationResponse response = resp.readEntity(configurationResponseType);
            //if (response != null) {
                // fai qualcosa
            //}


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}