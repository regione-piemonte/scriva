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

import it.csi.scriva.scrivabesrv.util.oauth2.OauthHelper;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;

public class ApiManagerTestSample {

    private static final String endPointUrl = "http://";
    private static final String endPointScrivaApi = endPointUrl + "/ambiente/scriva-scrivaapisrv-rp-01/v1";
    private static final String xRequestAuth = "YOUR_X_REQUEST_AUTH_HEADER_VALUE";

    private static final String consumerKey = "consumerKeyExample1234567890";
    private static final String consumerSecret = "consumerSecretExample1234567890";

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException, ParseException {
        String token = getToken();
        System.out.println("token : " + token);
        ping(token);
        pingNew(token);
        getDocumentiPubblicati(token, null, null, null, null);
    }

    private static String getToken() {
        String tokenUrl = endPointUrl + "/token";
        OauthHelper oauthHelper = new OauthHelper(
                tokenUrl,
                consumerKey,
                consumerSecret,
                10000);
        return oauthHelper.getToken();
    }

    private static void ping(String accessToken) {
        String url = endPointScrivaApi + "/ping";
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(url);
        Response resp = target.request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header("X-Request-Auth", xRequestAuth)
                .get();
        if (resp.getStatus() >= 400) {
            System.out.println("********************>>>> ERRORE chiamata PING :\n" + resp.getStatus() + " : " + resp.readEntity(String.class) + "\n");
        } else {
            System.out.println("\n********************>>>> Response chiamata PING :\n" + resp.readEntity(String.class) + "\n");
        }
    }

    // Java 11 version
    private static void pingNew(String accessToken) throws URISyntaxException, IOException, InterruptedException {
        String url = endPointScrivaApi + "/ping";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header("X-Request-Auth", xRequestAuth)
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            System.out.println("********************>>>> ERRORE chiamata PING :\n" + response.statusCode() + " : " + response.body() + "\n");
        } else {
            System.out.println("\n********************>>>> Response chiamata PING :\n" + response.body() + "\n");
        }
    }

    private static void getDocumentiPubblicati(String accessToken, String idIstanza, String codAllegato, String codClasseAllegato, String codCategoriaAllegato) throws URISyntaxException, IOException, InterruptedException {
        String url = endPointScrivaApi + "/documenti-pubblicati";
        url += StringUtils.isNotBlank(idIstanza) ? "?id_istanza=" + idIstanza : "";
        url += StringUtils.isNotBlank(codAllegato) ? "?cod_allegato=" + codAllegato : "";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header("X-Request-Auth", xRequestAuth)
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            System.out.println("********************>>>> ERRORE chiamata DOCUMENTI PUBBLICATI :\n" + response.statusCode() + " : " + response.body() + "\n");
        } else {
            System.out.println("\n********************>>>> Response chiamata DOCUMENTI PUBBLICATI :\n" + response.body() + "\n");
        }
    }

}