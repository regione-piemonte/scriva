/*
* ========================LICENSE_START=================================
* 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
package it.csi.scriva.scrivabesrv.util.notification.service.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import it.csi.scriva.scrivabesrv.business.be.service.impl.BaseServiceImpl;
import it.csi.scriva.scrivabesrv.util.notification.service.ExternalServiceCaller;

@Service
public class ExternalServiceCallerImpl extends BaseServiceImpl implements ExternalServiceCaller {
    
    private final String className = this.getClass().getSimpleName();

    private final RestTemplate restTemplate;

   public ExternalServiceCallerImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> callExternalService(String url, String token, String uuid, String id, String userId, String subject, String markdown) {
        // Set up headers
        logBegin(className);
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-authentication", token);
        headers.set("Content-Type", "application/json");

        // Costruzione del body
        String requestBody = "{\n" +
        "    \"uuid\": \"" + uuid + "\",\n" +
        "    \"payload\": {\n" +
        "        \"id\": \"" + id + "\",\n" +
        "        \"user_id\": \"" + userId + "\",\n" +
        "        \"io\": {\n" +
        "            \"content\": {\n" +
        "                \"subject\": \"" + subject + "\",\n" +
        "                \"markdown\": \"" + markdown + "\"\n" +
        "            }\n" +
        "        }\n" +
        "    }\n" +
        "}";

        logInfo(className,requestBody);

        // Create the HTTP entity
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = null;

        try {
                response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
                );
        } catch (HttpClientErrorException e) {
            logError(className, "Errore nella richiesta: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw e;
        }  finally {
            logEnd(className);
        }
    return response;
    }
}