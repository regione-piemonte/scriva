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

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class UploadFileIntegrationTestSample {

    public static void main(String[] args) throws IOException {
        String token = getToken();
        createContent(token);
    }

    private static String getToken() throws IOException {
        return "1bb2db57-794e-3846-975b-5629475b782d";
    }


    private static void createContent(String accessToken){
        // url chiamata creazione contenuto su api manager tst-api-piemonte
        String url = "http://..../parot/v1";
        try {
            // chiamata rest-easy
            File file = new File("D:\\test.pdf");
            Entity<File> entity = Entity.entity(file, MediaType.APPLICATION_OCTET_STREAM_TYPE);
            Client client = ClientBuilder.newBuilder().build();
            WebTarget target = client.target(url);
            Response resp = target.request()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .post(entity);
            if (resp.getStatus() >= 400) {
                System.out.println("********************>>>> ERRORE " + resp.getStatus() + " : "+ resp.readEntity(String.class));
            } else {
                System.out.println("********************>>>> UID Nodo : " + resp.readEntity(String.class));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static class IndexOperationContext {

        private String username;

        private String password;

        private String tenant;

        private String repository;

        private String fruitore;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getTenant() {
            return tenant;
        }

        public void setTenant(String tenant) {
            this.tenant = tenant;
        }

        public String getRepository() {
            return repository;
        }

        public void setRepository(String repository) {
            this.repository = repository;
        }

        public String getFruitore() {
            return fruitore;
        }

        public void setFruitore(String fruitore) {
            this.fruitore = fruitore;
        }

    }

    private static class IndexForm {

        @JsonProperty("parentNodeUid")
        private String parentNodeUid;

        @JsonProperty("node")
        private String node;

        public String getParentNodeUid() {
            return parentNodeUid;
        }

        public void setParentNodeUid(String parentNodeUid) {
            this.parentNodeUid = parentNodeUid;
        }

        public String getNode() {
            return node;
        }

        public void setNode(String node) {
            this.node = node;
        }

    }

    private static class IndexNode {

        @JsonProperty("typePrefixedName")
        private String typePrefixedName;

        @JsonProperty("prefixedName")
        private String prefixedName;

        @JsonProperty("modelPrefixedName")
        private String modelPrefixedName;

        @JsonProperty("parentAssocTypePrefixedName")
        private String parentAssocTypePrefixedName;

        @JsonProperty("contentPropertyPrefixedName")
        private String contentPropertyPrefixedName;

        @JsonProperty("mimeType")
        private String mimeType;

        @JsonProperty("encoding")
        private String encoding;

        private List<IndexProperty> properties;

        public String getTypePrefixedName() {
            return typePrefixedName;
        }

        public void setTypePrefixedName(String typePrefixedName) {
            this.typePrefixedName = typePrefixedName;
        }

        public String getPrefixedName() {
            return prefixedName;
        }

        public void setPrefixedName(String prefixedName) {
            this.prefixedName = prefixedName;
        }

        public String getModelPrefixedName() {
            return modelPrefixedName;
        }

        public void setModelPrefixedName(String modelPrefixedName) {
            this.modelPrefixedName = modelPrefixedName;
        }

        public String getParentAssocTypePrefixedName() {
            return parentAssocTypePrefixedName;
        }

        public void setParentAssocTypePrefixedName(String parentAssocTypePrefixedName) {
            this.parentAssocTypePrefixedName = parentAssocTypePrefixedName;
        }

        public String getContentPropertyPrefixedName() {
            return contentPropertyPrefixedName;
        }

        public void setContentPropertyPrefixedName(String contentPropertyPrefixedName) {
            this.contentPropertyPrefixedName = contentPropertyPrefixedName;
        }

        public String getMimeType() {
            return mimeType;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }

        public String getEncoding() {
            return encoding;
        }

        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

        public List<IndexProperty> getProperties() {
            return properties;
        }

        public void setProperties(List<IndexProperty> properties) {
            this.properties = properties;
        }
    }

    private static class IndexProperty {

        private String prefixedName;

        private boolean multivalue;

        private String dataType;

        private String[] values;

        public String getPrefixedName() {
            return prefixedName;
        }

        public void setPrefixedName(String prefixedName) {
            this.prefixedName = prefixedName;
        }

        public boolean getMultivalue() {
            return multivalue;
        }

        public void setMultivalue(boolean multivalue) {
            this.multivalue = multivalue;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public String[] getValues() {
            return values;
        }

        public void setValues(String[] values) {
            this.values = values;
        }
    }

}