/*-
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaconsweb.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class Config {

    @Value("${scrivaconsweb-proxy.proxy.client.http.connection.max}")
    private int proxyHttpClientMaxConnection;
    @Value("${scrivaconsweb-proxy.proxy.client.http.connection.route.max}")
    private int proxyHttpClientMaxConnectionPerRoute;

    public int getProxyHttpClientMaxConnection() {
        return this.proxyHttpClientMaxConnection;
    }

    public int getProxyHttpClientMaxConnectionPerRoute() {
        return this.proxyHttpClientMaxConnectionPerRoute;
    }

}
