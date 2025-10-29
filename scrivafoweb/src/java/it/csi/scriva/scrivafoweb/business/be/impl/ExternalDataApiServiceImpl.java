/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.impl;


import it.csi.scriva.scrivafoweb.business.be.ExternalDataApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
//import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.ExternalDataApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.GenericInputParDTO;
import it.csi.scriva.scrivafoweb.dto.FileFoDTO;
import it.csi.scriva.scrivafoweb.dto.ObjectListFoDTO;
import it.csi.scriva.scrivafoweb.util.Constants;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
/**
 * The type ExternalData api service.
 */
@Component
public class ExternalDataApiServiceImpl extends AbstractApiServiceImpl implements ExternalDataApi {

    private final String className = this.getClass().getSimpleName();


    /**
     * Generazione csv con elenco allegati associati all’istanza.
     * Caricamento su index del pdf con l'elenco degli allegati.
     * Generazione zip con file allegati associati all’istanza.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idIstanza       the id istanza
     * @param outputFormat    the output format
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
//    @Override
//    public Response getExternalDataByDataSet(UriInfo uriInfo, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
////        logBeginInfo(className, "Parametro in input dataSet [" + dataSet + "] - codAllegato [" + codiceRifiuto + "]");
//    	
//    	String dataSetValue = new String();
//    	String searchParameterKey = null;
//    	String searchParameterValue = null;
//    	String filteredJsonString = null;
//    	
//    	MultivaluedMap<String, String> queryMap = uriInfo.getQueryParameters();	
//
//    	Map<String,String> parameters = new HashMap<String,String>();
//
//    	   for(String str : queryMap.keySet()){
//    	     parameters.put(str, queryMap.getFirst(str));
//    	   }
//    	
//    	   for (Map.Entry<String, String> entry : parameters.entrySet()) {
//    			String k = entry.getKey();
//    			String v = entry.getValue();
//
//    			if(k.equals("data_set"))
//    				dataSetValue = v;
//    			else {
//    				searchParameterKey = k;
//    				searchParameterValue = v;
//    			}
//						
//    		}
//    	
//
//    	Object obj = new Object();
//    	
//        JSONParser jsonParser = new JSONParser();
//        
//        InputStream inputStream = ExternalDataApiServiceImpl.class.getResourceAsStream("/externaldata/data/" + dataSetValue + ".json");
//        String result = new BufferedReader(new InputStreamReader(inputStream))
//        		   .lines().collect(Collectors.joining("\n"));
//        try 
//        {
//        	        	
//            //Read JSON file
//        	//obj = jsonParser.parse(reader);
//        	
//        	if(searchParameterKey != null && searchParameterValue != null) {
//	        	String jsonString = result.toString();//obj.toString();
//	        	
//	            // Parse the JSON document
//	            ObjectMapper objectMapper = new ObjectMapper();
//	            JsonNode jsonNode = objectMapper.readTree(jsonString);
//	            
//	            // Convert the JSON document to a stream of objects
//	            Stream<JsonNode> stream = StreamSupport.stream(jsonNode.spliterator(), false);
//	            
//	            final String searchKey = searchParameterKey;
//	            final String searchValue = searchParameterValue;
//	            
//	            
//	            // Filter the stream of objects
//	            Stream<JsonNode> filteredStream = stream.filter(node -> node.get(searchKey).asText().equalsIgnoreCase(searchValue));
//	            
//	            // Convert the filtered stream back to a JSON document
//	            JsonNode filteredJsonNode = objectMapper.valueToTree(filteredStream.collect(Collectors.toList()));
//	            filteredJsonString = objectMapper.writeValueAsString(filteredJsonNode);
//        	}
//        	else
//        		filteredJsonString = result.toString();// obj.toString();
//        } catch (FileNotFoundException e) {
//            logError(className, e);
//            return Response.serverError().status(500).build();
//        } catch (IOException e) {
//        	logError(className, e);
//        	return Response.serverError().build();
//        	} //catch (ParseException e) {
////        	logError(className, e);
////        	return Response.serverError().build();
////        }
//
//    	return Response.ok(filteredJsonString).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
//
//    }

    @Override
    public Response getExternalDataByDataSet(UriInfo uriInfo, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String dataSetValue = new String();
        String searchParameterKey = null;
        String searchParameterValue = null;
        final String[] sortParameterKey = {null}; // Utilizzo di un array per rendere la variabile effettivamente finale
        String filteredJsonString = null;

        MultivaluedMap<String, String> queryMap = uriInfo.getQueryParameters();    
        Map<String,String> parameters = new HashMap<String,String>();

        for(String str : queryMap.keySet()){
            parameters.put(str, queryMap.getFirst(str));
        }

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();

            if(k.equals("data_set"))
                dataSetValue = v;
            else if(k.equals("sort_by"))
                sortParameterKey[0] = v;
            else {
                searchParameterKey = k;
                searchParameterValue = v;
            }
        }

        JSONParser jsonParser = new JSONParser();
        InputStream inputStream = ExternalDataApiServiceImpl.class.getResourceAsStream("/externaldata/data/" + dataSetValue + ".json");
        String result = new BufferedReader(new InputStreamReader(inputStream))
                       .lines().collect(Collectors.joining("\n"));
        try {
            String jsonString = result.toString();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            Stream<JsonNode> stream = StreamSupport.stream(jsonNode.spliterator(), false);

            if(searchParameterKey != null && searchParameterValue != null) {
                final String searchKey = searchParameterKey;
                final String searchValue = searchParameterValue;

                stream = stream.filter(node -> node.get(searchKey).asText().equalsIgnoreCase(searchValue));
            }

            if (sortParameterKey[0] != null) {
                stream = stream.sorted((node1, node2) -> node1.get(sortParameterKey[0]).asText().compareTo(node2.get(sortParameterKey[0]).asText()));
            }

            JsonNode filteredJsonNode = objectMapper.valueToTree(stream.collect(Collectors.toList()));
            filteredJsonString = objectMapper.writeValueAsString(filteredJsonNode);
        } catch (FileNotFoundException e) {
            logError(className, e);
            return Response.serverError().status(500).build();
        } catch (IOException e) {
            logError(className, e);
            return Response.serverError().build();
        }

        return Response.ok(filteredJsonString).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

}