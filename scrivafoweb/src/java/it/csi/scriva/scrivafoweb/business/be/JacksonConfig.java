/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.map.PropertyNamingStrategy;
//import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;



@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JacksonConfig implements ContextResolver<ObjectMapper> {

    private ObjectMapper objectMapper;

    public JacksonConfig() throws Exception {
        this.objectMapper = new ObjectMapper();

        this.objectMapper.setSerializationInclusion(Include.NON_NULL);

        // converte ogni DateTime in timestamp in formato ISO-8601
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // permette di serializzare automaticamente in snake_case le property che sono
        // in camelCase, senza dover specificare le annotation sulle singole property
        // this.objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        //this.objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        // permette di escludere dal JSON le proprieta' con valore nullo
        this.objectMapper.setSerializationInclusion(Include.NON_EMPTY);
    }

    public ObjectMapper getContext(Class<?> objectType) {
        return objectMapper;
    }
}