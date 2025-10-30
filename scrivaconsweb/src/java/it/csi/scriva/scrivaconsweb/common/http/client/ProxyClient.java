/*-
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaconsweb.common.http.client;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import it.csi.scriva.scrivaconsweb.common.config.Config;

@Component
public class ProxyClient {

    @Autowired
    private Config config;

    @Bean(name = "proxyHttpsClient")
    @Scope("singleton")
    public CloseableHttpClient createProxyHttpClient() throws Exception {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(config.getProxyHttpClientMaxConnection());
        connectionManager.setDefaultMaxPerRoute(config.getProxyHttpClientMaxConnectionPerRoute());
        CloseableHttpClient httpsClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        return httpsClient;
    }

}
