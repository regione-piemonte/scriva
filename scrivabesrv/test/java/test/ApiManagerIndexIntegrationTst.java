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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpStatus;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ApiManagerIndexIntegrationTst {

    public static void main(String[] args) throws IOException {
        String filePath = "D:\\test.docx"; //binary
        //String filePath = "D:\\hello.py"; //text
        File file = new File(filePath);
        String token = "token_da_recuperare_con_la_chiamata_di_authentication";
        String uuidIndex = "uuid_da_recuperare_con_la_chiamata_di_authentication"; // folder root cm:scriva
        System.out.println("\n\nTOKEN : \n\n" + token + "\n\n");
        System.out.println(createContent(token, uuidIndex, file, filePath));
    }

    private static String createContent(String accessToken, String parentNodeUid, File file, String filePath) {
        // url chiamata creazione contenuto su api manager
        
        String url = "http://localhost:8080/api-manager-index-integration/indices/scriva/nodes/content";
        String result = null;
        try {
            String xRequestAuth = getXRequestAuth();

            // Costruzione parametro node
            Node node = new Node();
            node.setTypePrefixedName("cm:content");
            node.setPrefixedName("cm:" + file.getName());
            node.setModelPrefixedName("cm:scriva");
            node.setParentAssocTypePrefixedName("cm:contains");
            node.setContentPropertyPrefixedName("cm:content");
            node.setMimeType("application/octet-stream");
            node.setEncoding("UTF-8");

            // serializzazione parametro node in formato json
            ObjectMapper objectMapper = new ObjectMapper()
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).findAndRegisterModules();
            String jsonNode = objectMapper.writeValueAsString(node);
            System.out.println("parentNodeUid : \n" + parentNodeUid);
            System.out.println("node : \n" + jsonNode);

            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            byte[] bytes2 = FileUtils.readFileToByteArray(file);

            MultipartFormDataOutput multipartForm = new MultipartFormDataOutput();

            multipartForm.addFormData("parentNodeUid", parentNodeUid, MediaType.TEXT_PLAIN_TYPE);
            multipartForm.addFormData("binaryContent", file, MediaType.APPLICATION_OCTET_STREAM_TYPE, file.getName());
            multipartForm.addFormData("node", jsonNode, MediaType.TEXT_PLAIN_TYPE);

            Entity<MultipartFormDataOutput> entity = Entity.entity(multipartForm, MediaType.MULTIPART_FORM_DATA);

            // chiamata rest-easy
            Client client = ClientBuilder.newBuilder().build();
            WebTarget target = client.target(url);
            Response resp = target.request()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
                    .header("X-Request-Auth", xRequestAuth)
                    .header(HttpHeaders.ACCEPT, "text/plain")
                    .post(entity);

            if (resp.getStatus() >= 400) {
                System.out.println("********************>>>> ERRORE " + resp.getStatus() + " : " + resp.readEntity(String.class));
            } else {
                result = resp.readEntity(String.class);
                System.out.println("********************>>>> UID Nodo : " + result);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static String getXRequestAuth() throws JsonProcessingException {
        IndexOperationContext operationContext = new IndexOperationContext();
        operationContext.setUsername("user_scriva");
        operationContext.setPassword("password_scriva");
        operationContext.setTenant("scriva");
        operationContext.setRepository("primary");

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).findAndRegisterModules();
        String jsonOperationContext = objectMapper.writeValueAsString(operationContext);
        return new String(Base64.getEncoder().encode(jsonOperationContext.getBytes()));
    }

    /* INNER CLASSES */

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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class Node implements Serializable {

        @JsonProperty("uid")
        private String uid = null;

        @JsonProperty("typePrefixedName")
        private String typePrefixedName = null;

        @JsonProperty("prefixedName")
        private String prefixedName = null;

        @JsonProperty("modelPrefixedName")
        private String modelPrefixedName = null;

        @JsonProperty("parentAssocTypePrefixedName")
        private String parentAssocTypePrefixedName = null;

        @JsonProperty("contentPropertyPrefixedName")
        private String contentPropertyPrefixedName = null;

        @JsonProperty("mimeType")
        private String mimeType = null;

        @JsonProperty("encoding")
        private String encoding = null;

        @JsonProperty("encryptionInfo")
        private EncryptionInfo encryptionInfo = null;

        @JsonProperty("properties")
        private List<Property> properties = null;

        @JsonProperty("aspects")
        private List<Aspect> aspects = null;

        public Node uid(String uid) {
            this.uid = uid;
            return this;
        }

        /**
         * UID del nodo.
         *
         * @return uid
         **/
        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public Node typePrefixedName(String typePrefixedName) {
            this.typePrefixedName = typePrefixedName;
            return this;
        }

        /**
         * Prefixed name del tipo del nodo.
         *
         * @return typePrefixedName
         **/
        public String getTypePrefixedName() {
            return typePrefixedName;
        }

        public void setTypePrefixedName(String typePrefixedName) {
            this.typePrefixedName = typePrefixedName;
        }

        public Node prefixedName(String prefixedName) {
            this.prefixedName = prefixedName;
            return this;
        }

        /**
         * Rappresenta il prefixed name.
         *
         * @return prefixedName
         **/
        public String getPrefixedName() {
            return prefixedName;
        }

        public void setPrefixedName(String prefixedName) {
            this.prefixedName = prefixedName;
        }

        public Node modelPrefixedName(String modelPrefixedName) {
            this.modelPrefixedName = modelPrefixedName;
            return this;
        }

        /**
         * Prefixed name del modello del nodo.
         *
         * @return modelPrefixedName
         **/
        public String getModelPrefixedName() {
            return modelPrefixedName;
        }

        public void setModelPrefixedName(String modelPrefixedName) {
            this.modelPrefixedName = modelPrefixedName;
        }

        public Node parentAssocTypePrefixedName(String parentAssocTypePrefixedName) {
            this.parentAssocTypePrefixedName = parentAssocTypePrefixedName;
            return this;
        }

        /**
         * Prefixed name dell&#39;associazione padre/figlio del nodo.
         *
         * @return parentAssocTypePrefixedName
         **/
        public String getParentAssocTypePrefixedName() {
            return parentAssocTypePrefixedName;
        }

        public void setParentAssocTypePrefixedName(String parentAssocTypePrefixedName) {
            this.parentAssocTypePrefixedName = parentAssocTypePrefixedName;
        }

        public Node contentPropertyPrefixedName(String contentPropertyPrefixedName) {
            this.contentPropertyPrefixedName = contentPropertyPrefixedName;
            return this;
        }

        /**
         * Prefixed name della proprietà che rappresenta il contenuto binario del nodo.
         *
         * @return contentPropertyPrefixedName
         **/
        public String getContentPropertyPrefixedName() {
            return contentPropertyPrefixedName;
        }

        public void setContentPropertyPrefixedName(String contentPropertyPrefixedName) {
            this.contentPropertyPrefixedName = contentPropertyPrefixedName;
        }

        public Node mimeType(String mimeType) {
            this.mimeType = mimeType;
            return this;
        }

        /**
         * MIME-Type del contenuto binario del nodo.
         *
         * @return mimeType
         **/
        public String getMimeType() {
            return mimeType;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }

        public Node encoding(String encoding) {
            this.encoding = encoding;
            return this;
        }

        /**
         * Codifica del contenuto binario del nodo.
         *
         * @return encoding
         **/
        public String getEncoding() {
            return encoding;
        }

        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

        public Node encryptionInfo(EncryptionInfo encryptionInfo) {
            this.encryptionInfo = encryptionInfo;
            return this;
        }

        /**
         * Get encryptionInfo
         *
         * @return encryptionInfo
         **/
        public EncryptionInfo getEncryptionInfo() {
            return encryptionInfo;
        }

        public void setEncryptionInfo(EncryptionInfo encryptionInfo) {
            this.encryptionInfo = encryptionInfo;
        }

        public Node properties(List<Property> properties) {
            this.properties = properties;
            return this;
        }

        public Node addPropertiesItem(Property propertiesItem) {
            if (this.properties == null) {
                this.properties = new ArrayList<Property>();
            }
            this.properties.add(propertiesItem);
            return this;
        }

        /**
         * Elenco delle proprietà del nodo.
         *
         * @return properties
         **/
        public List<ApiManagerIndexIntegrationTst.Property> getProperties() {
            return properties;
        }

        public void setProperties(List<Property> properties) {
            this.properties = properties;
        }

        public Node aspects(List<Aspect> aspects) {
            this.aspects = aspects;
            return this;
        }

        public Node addAspectsItem(Aspect aspectsItem) {
            if (this.aspects == null) {
                this.aspects = new ArrayList<Aspect>();
            }
            this.aspects.add(aspectsItem);
            return this;
        }

        /**
         * Aspetti del nodo.
         *
         * @return aspects
         **/
        public List<Aspect> getAspects() {
            return aspects;
        }

        public void setAspects(List<Aspect> aspects) {
            this.aspects = aspects;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Node node = (Node) o;
            return Objects.equals(this.uid, node.uid) &&
                    Objects.equals(this.typePrefixedName, node.typePrefixedName) &&
                    Objects.equals(this.prefixedName, node.prefixedName) &&
                    Objects.equals(this.modelPrefixedName, node.modelPrefixedName) &&
                    Objects.equals(this.parentAssocTypePrefixedName, node.parentAssocTypePrefixedName) &&
                    Objects.equals(this.contentPropertyPrefixedName, node.contentPropertyPrefixedName) &&
                    Objects.equals(this.mimeType, node.mimeType) &&
                    Objects.equals(this.encoding, node.encoding) &&
                    Objects.equals(this.encryptionInfo, node.encryptionInfo) &&
                    Objects.equals(this.properties, node.properties) &&
                    Objects.equals(this.aspects, node.aspects);
        }

        @Override
        public int hashCode() {
            return Objects.hash(uid, typePrefixedName, prefixedName, modelPrefixedName, parentAssocTypePrefixedName, contentPropertyPrefixedName, mimeType, encoding, encryptionInfo, properties, aspects);
        }


        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class Node {\n");

            sb.append("    uid: ").append(toIndentedString(uid)).append("\n");
            sb.append("    typePrefixedName: ").append(toIndentedString(typePrefixedName)).append("\n");
            sb.append("    prefixedName: ").append(toIndentedString(prefixedName)).append("\n");
            sb.append("    modelPrefixedName: ").append(toIndentedString(modelPrefixedName)).append("\n");
            sb.append("    parentAssocTypePrefixedName: ").append(toIndentedString(parentAssocTypePrefixedName)).append("\n");
            sb.append("    contentPropertyPrefixedName: ").append(toIndentedString(contentPropertyPrefixedName)).append("\n");
            sb.append("    mimeType: ").append(toIndentedString(mimeType)).append("\n");
            sb.append("    encoding: ").append(toIndentedString(encoding)).append("\n");
            sb.append("    encryptionInfo: ").append(toIndentedString(encryptionInfo)).append("\n");
            sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
            sb.append("    aspects: ").append(toIndentedString(aspects)).append("\n");
            sb.append("}");
            return sb.toString();
        }

        /**
         * Convert the given object to string with each line indented by 4 spaces
         * (except the first line).
         */
        private String toIndentedString(Object o) {
            if (o == null) {
                return "null";
            }
            return o.toString().replace("\n", "\n    ");
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EncryptionInfo implements Serializable {
        @JsonProperty("key")
        private String key = null;

        @JsonProperty("algorithm")
        private String algorithm = null;

        @JsonProperty("padding")
        private String padding = null;

        @JsonProperty("mode")
        private String mode = null;

        @JsonProperty("keyId")
        private String keyId = null;

        @JsonProperty("sourceIV")
        private String sourceIV = null;

        @JsonProperty("sourceEncrypted")
        private Boolean sourceEncrypted = null;

        public EncryptionInfo key(String key) {
            this.key = key;
            return this;
        }

        /**
         * Chiave di cifratura.
         *
         * @return key
         **/
        @ApiModelProperty(example = "0123456789ABCDEF", value = "Chiave di cifratura.")
        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public EncryptionInfo algorithm(String algorithm) {
            this.algorithm = algorithm;
            return this;
        }

        /**
         * Algoritmo utilizzato per la cifratura.
         *
         * @return algorithm
         **/
        @ApiModelProperty(example = "AES", value = "Algoritmo utilizzato per la cifratura.")
        public String getAlgorithm() {
            return algorithm;
        }

        public void setAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }

        public EncryptionInfo padding(String padding) {
            this.padding = padding;
            return this;
        }

        /**
         * Tipo di padding utilizzato per la cifratura.
         *
         * @return padding
         **/
        @ApiModelProperty(example = "PKCS5Padding", value = "Tipo di padding utilizzato per la cifratura.")
        public String getPadding() {
            return padding;
        }

        public void setPadding(String padding) {
            this.padding = padding;
        }

        public EncryptionInfo mode(String mode) {
            this.mode = mode;
            return this;
        }

        /**
         * Modalità di cifratura.
         *
         * @return mode
         **/
        @ApiModelProperty(example = "CBC", value = "Modalità di cifratura.")
        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public EncryptionInfo keyId(String keyId) {
            this.keyId = keyId;
            return this;
        }

        /**
         * Identificativo della chiave crittografica utilizzata.
         *
         * @return keyId
         **/
        @ApiModelProperty(example = "0123456789ABCDEF", value = "Identificativo della chiave crittografica utilizzata.")
        public String getKeyId() {
            return keyId;
        }

        public void setKeyId(String keyId) {
            this.keyId = keyId;
        }

        public EncryptionInfo sourceIV(String sourceIV) {
            this.sourceIV = sourceIV;
            return this;
        }

        /**
         * Initialization Vector utilizzato per criptare il contenuto alla fonte.
         *
         * @return sourceIV
         **/
        @ApiModelProperty(value = "Initialization Vector utilizzato per criptare il contenuto alla fonte.")
        public String getSourceIV() {
            return sourceIV;
        }

        public void setSourceIV(String sourceIV) {
            this.sourceIV = sourceIV;
        }

        public EncryptionInfo sourceEncrypted(Boolean sourceEncrypted) {
            this.sourceEncrypted = sourceEncrypted;
            return this;
        }

        /**
         * Indica se il contenuto è criptato alla fonte.
         *
         * @return sourceEncrypted
         **/
        @ApiModelProperty(value = "Indica se il contenuto è criptato alla fonte.")
        public Boolean isSourceEncrypted() {
            return sourceEncrypted;
        }

        public void setSourceEncrypted(Boolean sourceEncrypted) {
            this.sourceEncrypted = sourceEncrypted;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            EncryptionInfo encryptionInfo = (EncryptionInfo) o;
            return Objects.equals(this.key, encryptionInfo.key) &&
                    Objects.equals(this.algorithm, encryptionInfo.algorithm) &&
                    Objects.equals(this.padding, encryptionInfo.padding) &&
                    Objects.equals(this.mode, encryptionInfo.mode) &&
                    Objects.equals(this.keyId, encryptionInfo.keyId) &&
                    Objects.equals(this.sourceIV, encryptionInfo.sourceIV) &&
                    Objects.equals(this.sourceEncrypted, encryptionInfo.sourceEncrypted);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, algorithm, padding, mode, keyId, sourceIV, sourceEncrypted);
        }


        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class EncryptionInfo {\n");

            sb.append("    key: ").append(toIndentedString(key)).append("\n");
            sb.append("    algorithm: ").append(toIndentedString(algorithm)).append("\n");
            sb.append("    padding: ").append(toIndentedString(padding)).append("\n");
            sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
            sb.append("    keyId: ").append(toIndentedString(keyId)).append("\n");
            sb.append("    sourceIV: ").append(toIndentedString(sourceIV)).append("\n");
            sb.append("    sourceEncrypted: ").append(toIndentedString(sourceEncrypted)).append("\n");
            sb.append("}");
            return sb.toString();
        }

        /**
         * Convert the given object to string with each line indented by 4 spaces
         * (except the first line).
         */
        private String toIndentedString(Object o) {
            if (o == null) {
                return "null";
            }
            return o.toString().replace("\n", "\n    ");
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class Aspect implements Serializable {
        @JsonProperty("prefixedName")
        private String prefixedName = null;

        public Aspect prefixedName(String prefixedName) {
            this.prefixedName = prefixedName;
            return this;
        }

        /**
         * Rappresenta il prefixed name.
         *
         * @return prefixedName
         **/
        public String getPrefixedName() {
            return prefixedName;
        }

        public void setPrefixedName(String prefixedName) {
            this.prefixedName = prefixedName;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Aspect aspect = (Aspect) o;
            return Objects.equals(this.prefixedName, aspect.prefixedName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(prefixedName);
        }


        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class Aspect {\n");

            sb.append("    prefixedName: ").append(toIndentedString(prefixedName)).append("\n");
            sb.append("}");
            return sb.toString();
        }

        /**
         * Convert the given object to string with each line indented by 4 spaces
         * (except the first line).
         */
        private String toIndentedString(Object o) {
            if (o == null) {
                return "null";
            }
            return o.toString().replace("\n", "\n    ");
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class Property implements Serializable {
        @JsonProperty("prefixedName")
        private String prefixedName = null;

        @JsonProperty("multivalue")
        private Boolean multivalue = null;

        @JsonProperty("dataType")
        private String dataType = null;

        @JsonProperty("values")
        private List<String> values = null;

        @JsonProperty("relativeAspectPrefixedName")
        private String relativeAspectPrefixedName = null;

        public Property prefixedName(String prefixedName) {
            this.prefixedName = prefixedName;
            return this;
        }

        /**
         * Prefixed name dell&#39;eventuale aspetto che contiene la proprietà.
         *
         * @return prefixedName
         **/
        public String getPrefixedName() {
            return prefixedName;
        }

        public void setPrefixedName(String prefixedName) {
            this.prefixedName = prefixedName;
        }

        public Property multivalue(Boolean multivalue) {
            this.multivalue = multivalue;
            return this;
        }

        /**
         * Indica se la proprietà è multivalore oppure no.
         *
         * @return multivalue
         **/
        public Boolean isMultivalue() {
            return multivalue;
        }

        public void setMultivalue(Boolean multivalue) {
            this.multivalue = multivalue;
        }

        public Property dataType(String dataType) {
            this.dataType = dataType;
            return this;
        }

        /**
         * Tipo della proprietà.
         *
         * @return dataType
         **/
        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public Property values(List<String> values) {
            this.values = values;
            return this;
        }

        public Property addValuesItem(String valuesItem) {
            if (this.values == null) {
                this.values = new ArrayList<String>();
            }
            this.values.add(valuesItem);
            return this;
        }

        /**
         * Valori della proprietà.
         *
         * @return values
         **/
        public List<String> getValues() {
            return values;
        }

        public void setValues(List<String> values) {
            this.values = values;
        }

        public Property relativeAspectPrefixedName(String relativeAspectPrefixedName) {
            this.relativeAspectPrefixedName = relativeAspectPrefixedName;
            return this;
        }

        /**
         * Prefixed name dell&#39;eventuale aspetto che contiene la proprietà.
         *
         * @return relativeAspectPrefixedName
         **/
        public String getRelativeAspectPrefixedName() {
            return relativeAspectPrefixedName;
        }

        public void setRelativeAspectPrefixedName(String relativeAspectPrefixedName) {
            this.relativeAspectPrefixedName = relativeAspectPrefixedName;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Property property = (Property) o;
            return Objects.equals(this.prefixedName, property.prefixedName) &&
                    Objects.equals(this.multivalue, property.multivalue) &&
                    Objects.equals(this.dataType, property.dataType) &&
                    Objects.equals(this.values, property.values) &&
                    Objects.equals(this.relativeAspectPrefixedName, property.relativeAspectPrefixedName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(prefixedName, multivalue, dataType, values, relativeAspectPrefixedName);
        }


        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class Property {\n");

            sb.append("    prefixedName: ").append(toIndentedString(prefixedName)).append("\n");
            sb.append("    multivalue: ").append(toIndentedString(multivalue)).append("\n");
            sb.append("    dataType: ").append(toIndentedString(dataType)).append("\n");
            sb.append("    values: ").append(toIndentedString(values)).append("\n");
            sb.append("    relativeAspectPrefixedName: ").append(toIndentedString(relativeAspectPrefixedName)).append("\n");
            sb.append("}");
            return sb.toString();
        }

        /**
         * Convert the given object to string with each line indented by 4 spaces
         * (except the first line).
         */
        private String toIndentedString(Object o) {
            if (o == null) {
                return "null";
            }
            return o.toString().replace("\n", "\n    ");
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class Signature {
        @JsonProperty("signature")
        private List<Signature> signature = null;

        @JsonProperty("annoFirma")
        private Integer annoFirma = null;

        @JsonProperty("ca")
        private String ca = null;

        @JsonProperty("cert")
        private byte[] cert = null;

        @JsonProperty("codiceFiscale")
        private String codiceFiscale = null;

        @JsonProperty("dataOra")
        private String dataOra = null;

        @JsonProperty("dataOraVerifica")
        private String dataOraVerifica = null;

        @JsonProperty("dipartimento")
        private String dipartimento = null;

        @JsonProperty("dnQualifier")
        private String dnQualifier = null;

        @JsonProperty("errorCode")
        private Integer errorCode = null;

        @JsonProperty("fineValidita")
        private String fineValidita = null;

        @JsonProperty("firmatario")
        private String firmatario = null;

        @JsonProperty("giornoFirma")
        private Integer giornoFirma = null;

        @JsonProperty("givenname")
        private String givenname = null;

        @JsonProperty("inizioValidita")
        private String inizioValidita = null;

        @JsonProperty("meseFirma")
        private Integer meseFirma = null;

        @JsonProperty("minutiFirma")
        private Integer minutiFirma = null;

        @JsonProperty("nominativoFirmatario")
        private String nominativoFirmatario = null;

        @JsonProperty("numeroControfirme")
        private Integer numeroControfirme = null;

        @JsonProperty("oraFirma")
        private Integer oraFirma = null;

        @JsonProperty("organizzazione")
        private String organizzazione = null;

        @JsonProperty("paese")
        private String paese = null;

        @JsonProperty("secondiFirma")
        private Integer secondiFirma = null;

        @JsonProperty("serialNumber")
        private String serialNumber = null;

        @JsonProperty("surname")
        private String surname = null;

        @JsonProperty("timestamped")
        private Boolean timestamped = null;

        @JsonProperty("tipoCertificato")
        private Integer tipoCertificato = null;

        @JsonProperty("tipoFirma")
        private Integer tipoFirma = null;

        public Signature signature(List<Signature> signature) {
            this.signature = signature;
            return this;
        }

        public Signature addSignatureItem(Signature signatureItem) {
            if (this.signature == null) {
                this.signature = new ArrayList<Signature>();
            }
            this.signature.add(signatureItem);
            return this;
        }

        /**
         * Elenco delle controfirme.
         *
         * @return signature
         **/
        @ApiModelProperty(value = "Elenco delle controfirme.")
        public List<Signature> getSignature() {
            return signature;
        }

        public void setSignature(List<Signature> signature) {
            this.signature = signature;
        }

        public Signature annoFirma(Integer annoFirma) {
            this.annoFirma = annoFirma;
            return this;
        }

        /**
         * Anno della firma.
         *
         * @return annoFirma
         **/
        @ApiModelProperty(example = "2020", value = "Anno della firma.")
        public Integer getAnnoFirma() {
            return annoFirma;
        }

        public void setAnnoFirma(Integer annoFirma) {
            this.annoFirma = annoFirma;
        }

        public Signature ca(String ca) {
            this.ca = ca;
            return this;
        }

        /**
         * Certification authority.
         *
         * @return ca
         **/
        @ApiModelProperty(example = "ArubaPEC EU Qualified Certificates CA G1", value = "Certification authority.")
        public String getCa() {
            return ca;
        }

        public void setCa(String ca) {
            this.ca = ca;
        }

        public Signature cert(byte[] cert) {
            this.cert = cert;
            return this;
        }

        /**
         * Certificato di firma codificato in base64.
         *
         * @return cert
         **/
        @ApiModelProperty(example = "Ynl0ZXM=", value = "Certificato di firma codificato in base64.")
        public byte[] getCert() {
            return cert;
        }

        public void setCert(byte[] cert) {
            this.cert = cert;
        }

        public Signature codiceFiscale(String codiceFiscale) {
            this.codiceFiscale = codiceFiscale;
            return this;
        }

        /**
         * Codice fiscale del firmatario.
         *
         * @return codiceFiscale
         **/
        @ApiModelProperty(example = "RSSMRA34839200039L220S", value = "Codice fiscale del firmatario.")
        public String getCodiceFiscale() {
            return codiceFiscale;
        }

        public void setCodiceFiscale(String codiceFiscale) {
            this.codiceFiscale = codiceFiscale;
        }

        public Signature dataOra(String dataOra) {
            this.dataOra = dataOra;
            return this;
        }

        /**
         * Data e l&#39;ora della firma.
         *
         * @return dataOra
         **/
        @ApiModelProperty(example = "2018-04-09T14:15:29.000+0000", value = "Data e l'ora della firma.")
        public String getDataOra() {
            return dataOra;
        }

        public void setDataOra(String dataOra) {
            this.dataOra = dataOra;
        }

        public Signature dataOraVerifica(String dataOraVerifica) {
            this.dataOraVerifica = dataOraVerifica;
            return this;
        }

        /**
         * Data e ora del rapporto di verifica nel formato gg/mm/aaaa hh:mm:ss.
         *
         * @return dataOraVerifica
         **/
        @ApiModelProperty(example = "25/02/2021 15:18:20", value = "Data e ora del rapporto di verifica nel formato gg/mm/aaaa hh:mm:ss.")
        public String getDataOraVerifica() {
            return dataOraVerifica;
        }

        public void setDataOraVerifica(String dataOraVerifica) {
            this.dataOraVerifica = dataOraVerifica;
        }

        public Signature dipartimento(String dipartimento) {
            this.dipartimento = dipartimento;
            return this;
        }

        /**
         * Dipartimento.
         *
         * @return dipartimento
         **/
        @ApiModelProperty(value = "Dipartimento.")
        public String getDipartimento() {
            return dipartimento;
        }

        public void setDipartimento(String dipartimento) {
            this.dipartimento = dipartimento;
        }

        public Signature dnQualifier(String dnQualifier) {
            this.dnQualifier = dnQualifier;
            return this;
        }

        /**
         * Dnqualifier del firmatario.
         *
         * @return dnQualifier
         **/
        @ApiModelProperty(example = "WSREF-69794445239940", value = "Dnqualifier del firmatario.")
        public String getDnQualifier() {
            return dnQualifier;
        }

        public void setDnQualifier(String dnQualifier) {
            this.dnQualifier = dnQualifier;
        }

        public Signature errorCode(Integer errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        /**
         * Eventuale codice di errore della verifica della firma.
         *
         * @return errorCode
         **/
        @ApiModelProperty(example = "4", value = "Eventuale codice di errore della verifica della firma.")
        public Integer getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(Integer errorCode) {
            this.errorCode = errorCode;
        }

        public Signature fineValidita(String fineValidita) {
            this.fineValidita = fineValidita;
            return this;
        }

        /**
         * Data di fine di validità della firma.
         *
         * @return fineValidita
         **/
        @ApiModelProperty(example = "Feb 19 17:06:55 2020", value = "Data di fine di validità della firma.")
        public String getFineValidita() {
            return fineValidita;
        }

        public void setFineValidita(String fineValidita) {
            this.fineValidita = fineValidita;
        }

        public Signature firmatario(String firmatario) {
            this.firmatario = firmatario;
            return this;
        }

        /**
         * Firmatario.
         *
         * @return firmatario
         **/
        @ApiModelProperty(example = "ROSSI MARIO", value = "Firmatario.")
        public String getFirmatario() {
            return firmatario;
        }

        public void setFirmatario(String firmatario) {
            this.firmatario = firmatario;
        }

        public Signature giornoFirma(Integer giornoFirma) {
            this.giornoFirma = giornoFirma;
            return this;
        }

        /**
         * Giorno della firma.
         *
         * @return giornoFirma
         **/
        @ApiModelProperty(example = "15", value = "Giorno della firma.")
        public Integer getGiornoFirma() {
            return giornoFirma;
        }

        public void setGiornoFirma(Integer giornoFirma) {
            this.giornoFirma = giornoFirma;
        }

        public Signature givenname(String givenname) {
            this.givenname = givenname;
            return this;
        }

        /**
         * Nome del firmatario.
         *
         * @return givenname
         **/
        @ApiModelProperty(example = "Mario", value = "Nome del firmatario.")
        public String getGivenname() {
            return givenname;
        }

        public void setGivenname(String givenname) {
            this.givenname = givenname;
        }

        public Signature inizioValidita(String inizioValidita) {
            this.inizioValidita = inizioValidita;
            return this;
        }

        /**
         * Data di inizio di validità della firma.
         *
         * @return inizioValidita
         **/
        @ApiModelProperty(example = "Feb 19 17:06:55 2018", value = "Data di inizio di validità della firma.")
        public String getInizioValidita() {
            return inizioValidita;
        }

        public void setInizioValidita(String inizioValidita) {
            this.inizioValidita = inizioValidita;
        }

        public Signature meseFirma(Integer meseFirma) {
            this.meseFirma = meseFirma;
            return this;
        }

        /**
         * Mese della firma.
         *
         * @return meseFirma
         **/
        @ApiModelProperty(example = "0", value = "Mese della firma.")
        public Integer getMeseFirma() {
            return meseFirma;
        }

        public void setMeseFirma(Integer meseFirma) {
            this.meseFirma = meseFirma;
        }

        public Signature minutiFirma(Integer minutiFirma) {
            this.minutiFirma = minutiFirma;
            return this;
        }

        /**
         * Minuti della firma.
         *
         * @return minutiFirma
         **/
        @ApiModelProperty(example = "4", value = "Minuti della firma.")
        public Integer getMinutiFirma() {
            return minutiFirma;
        }

        public void setMinutiFirma(Integer minutiFirma) {
            this.minutiFirma = minutiFirma;
        }

        public Signature nominativoFirmatario(String nominativoFirmatario) {
            this.nominativoFirmatario = nominativoFirmatario;
            return this;
        }

        /**
         * Nominativo del firmatario.
         *
         * @return nominativoFirmatario
         **/
        @ApiModelProperty(example = "MARIO ROSSI", value = "Nominativo del firmatario.")
        public String getNominativoFirmatario() {
            return nominativoFirmatario;
        }

        public void setNominativoFirmatario(String nominativoFirmatario) {
            this.nominativoFirmatario = nominativoFirmatario;
        }

        public Signature numeroControfirme(Integer numeroControfirme) {
            this.numeroControfirme = numeroControfirme;
            return this;
        }

        /**
         * Numero di controfirme del file.
         *
         * @return numeroControfirme
         **/
        @ApiModelProperty(example = "1", value = "Numero di controfirme del file.")
        public Integer getNumeroControfirme() {
            return numeroControfirme;
        }

        public void setNumeroControfirme(Integer numeroControfirme) {
            this.numeroControfirme = numeroControfirme;
        }

        public Signature oraFirma(Integer oraFirma) {
            this.oraFirma = oraFirma;
            return this;
        }

        /**
         * Ora della firma.
         *
         * @return oraFirma
         **/
        @ApiModelProperty(example = "23", value = "Ora della firma.")
        public Integer getOraFirma() {
            return oraFirma;
        }

        public void setOraFirma(Integer oraFirma) {
            this.oraFirma = oraFirma;
        }

        public Signature organizzazione(String organizzazione) {
            this.organizzazione = organizzazione;
            return this;
        }

        /**
         * Organizzazione del firmatario.
         *
         * @return organizzazione
         **/
        @ApiModelProperty(value = "Organizzazione del firmatario.")
        public String getOrganizzazione() {
            return organizzazione;
        }

        public void setOrganizzazione(String organizzazione) {
            this.organizzazione = organizzazione;
        }

        public Signature paese(String paese) {
            this.paese = paese;
            return this;
        }

        /**
         * Paese del firmatario.
         *
         * @return paese
         **/
        @ApiModelProperty(example = "IT", value = "Paese del firmatario.")
        public String getPaese() {
            return paese;
        }

        public void setPaese(String paese) {
            this.paese = paese;
        }

        public Signature secondiFirma(Integer secondiFirma) {
            this.secondiFirma = secondiFirma;
            return this;
        }

        /**
         * Secondi della firma.
         *
         * @return secondiFirma
         **/
        @ApiModelProperty(example = "34", value = "Secondi della firma.")
        public Integer getSecondiFirma() {
            return secondiFirma;
        }

        public void setSecondiFirma(Integer secondiFirma) {
            this.secondiFirma = secondiFirma;
        }

        public Signature serialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
            return this;
        }

        /**
         * Serial number del firmatario.
         *
         * @return serialNumber
         **/
        @ApiModelProperty(example = "TINIT-ROSSMA61D15L219L", value = "Serial number del firmatario.")
        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

        public Signature surname(String surname) {
            this.surname = surname;
            return this;
        }

        /**
         * Cognome del firmatario.
         *
         * @return surname
         **/
        @ApiModelProperty(example = "ROSSI", value = "Cognome del firmatario.")
        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public Signature timestamped(Boolean timestamped) {
            this.timestamped = timestamped;
            return this;
        }

        /**
         * Imposta se la firma è fornita di una marcatura temporale.
         *
         * @return timestamped
         **/
        @ApiModelProperty(value = "Imposta se la firma è fornita di una marcatura temporale.")
        public Boolean isTimestamped() {
            return timestamped;
        }

        public void setTimestamped(Boolean timestamped) {
            this.timestamped = timestamped;
        }

        public Signature tipoCertificato(Integer tipoCertificato) {
            this.tipoCertificato = tipoCertificato;
            return this;
        }

        /**
         * Imposta il tipo del certificato:  1 in caso di firma  2 in caso di marca temporale.
         *
         * @return tipoCertificato
         **/
        @ApiModelProperty(example = "1", value = "Imposta il tipo del certificato:  1 in caso di firma  2 in caso di marca temporale. ")
        public Integer getTipoCertificato() {
            return tipoCertificato;
        }

        public void setTipoCertificato(Integer tipoCertificato) {
            this.tipoCertificato = tipoCertificato;
        }

        public Signature tipoFirma(Integer tipoFirma) {
            this.tipoFirma = tipoFirma;
            return this;
        }

        /**
         * Imposta il tipo della firma.
         *
         * @return tipoFirma
         **/
        @ApiModelProperty(example = "0", value = "Imposta il tipo della firma.")
        public Integer getTipoFirma() {
            return tipoFirma;
        }

        public void setTipoFirma(Integer tipoFirma) {
            this.tipoFirma = tipoFirma;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Signature signature = (Signature) o;
            return Objects.equals(this.signature, signature.signature) &&
                    Objects.equals(this.annoFirma, signature.annoFirma) &&
                    Objects.equals(this.ca, signature.ca) &&
                    Arrays.equals(this.cert, signature.cert) &&
                    Objects.equals(this.codiceFiscale, signature.codiceFiscale) &&
                    Objects.equals(this.dataOra, signature.dataOra) &&
                    Objects.equals(this.dataOraVerifica, signature.dataOraVerifica) &&
                    Objects.equals(this.dipartimento, signature.dipartimento) &&
                    Objects.equals(this.dnQualifier, signature.dnQualifier) &&
                    Objects.equals(this.errorCode, signature.errorCode) &&
                    Objects.equals(this.fineValidita, signature.fineValidita) &&
                    Objects.equals(this.firmatario, signature.firmatario) &&
                    Objects.equals(this.giornoFirma, signature.giornoFirma) &&
                    Objects.equals(this.givenname, signature.givenname) &&
                    Objects.equals(this.inizioValidita, signature.inizioValidita) &&
                    Objects.equals(this.meseFirma, signature.meseFirma) &&
                    Objects.equals(this.minutiFirma, signature.minutiFirma) &&
                    Objects.equals(this.nominativoFirmatario, signature.nominativoFirmatario) &&
                    Objects.equals(this.numeroControfirme, signature.numeroControfirme) &&
                    Objects.equals(this.oraFirma, signature.oraFirma) &&
                    Objects.equals(this.organizzazione, signature.organizzazione) &&
                    Objects.equals(this.paese, signature.paese) &&
                    Objects.equals(this.secondiFirma, signature.secondiFirma) &&
                    Objects.equals(this.serialNumber, signature.serialNumber) &&
                    Objects.equals(this.surname, signature.surname) &&
                    Objects.equals(this.timestamped, signature.timestamped) &&
                    Objects.equals(this.tipoCertificato, signature.tipoCertificato) &&
                    Objects.equals(this.tipoFirma, signature.tipoFirma);
        }

        @Override
        public int hashCode() {
            return Objects.hash(signature, annoFirma, ca, Arrays.hashCode(cert), codiceFiscale, dataOra, dataOraVerifica, dipartimento, dnQualifier, errorCode, fineValidita, firmatario, giornoFirma, givenname, inizioValidita, meseFirma, minutiFirma, nominativoFirmatario, numeroControfirme, oraFirma, organizzazione, paese, secondiFirma, serialNumber, surname, timestamped, tipoCertificato, tipoFirma);
        }


        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class Signature {\n");

            sb.append("    signature: ").append(toIndentedString(signature)).append("\n");
            sb.append("    annoFirma: ").append(toIndentedString(annoFirma)).append("\n");
            sb.append("    ca: ").append(toIndentedString(ca)).append("\n");
            sb.append("    cert: ").append(toIndentedString(cert)).append("\n");
            sb.append("    codiceFiscale: ").append(toIndentedString(codiceFiscale)).append("\n");
            sb.append("    dataOra: ").append(toIndentedString(dataOra)).append("\n");
            sb.append("    dataOraVerifica: ").append(toIndentedString(dataOraVerifica)).append("\n");
            sb.append("    dipartimento: ").append(toIndentedString(dipartimento)).append("\n");
            sb.append("    dnQualifier: ").append(toIndentedString(dnQualifier)).append("\n");
            sb.append("    errorCode: ").append(toIndentedString(errorCode)).append("\n");
            sb.append("    fineValidita: ").append(toIndentedString(fineValidita)).append("\n");
            sb.append("    firmatario: ").append(toIndentedString(firmatario)).append("\n");
            sb.append("    giornoFirma: ").append(toIndentedString(giornoFirma)).append("\n");
            sb.append("    givenname: ").append(toIndentedString(givenname)).append("\n");
            sb.append("    inizioValidita: ").append(toIndentedString(inizioValidita)).append("\n");
            sb.append("    meseFirma: ").append(toIndentedString(meseFirma)).append("\n");
            sb.append("    minutiFirma: ").append(toIndentedString(minutiFirma)).append("\n");
            sb.append("    nominativoFirmatario: ").append(toIndentedString(nominativoFirmatario)).append("\n");
            sb.append("    numeroControfirme: ").append(toIndentedString(numeroControfirme)).append("\n");
            sb.append("    oraFirma: ").append(toIndentedString(oraFirma)).append("\n");
            sb.append("    organizzazione: ").append(toIndentedString(organizzazione)).append("\n");
            sb.append("    paese: ").append(toIndentedString(paese)).append("\n");
            sb.append("    secondiFirma: ").append(toIndentedString(secondiFirma)).append("\n");
            sb.append("    serialNumber: ").append(toIndentedString(serialNumber)).append("\n");
            sb.append("    surname: ").append(toIndentedString(surname)).append("\n");
            sb.append("    timestamped: ").append(toIndentedString(timestamped)).append("\n");
            sb.append("    tipoCertificato: ").append(toIndentedString(tipoCertificato)).append("\n");
            sb.append("    tipoFirma: ").append(toIndentedString(tipoFirma)).append("\n");
            sb.append("}");
            return sb.toString();
        }

        /**
         * Convert the given object to string with each line indented by 4 spaces
         * (except the first line).
         */
        private String toIndentedString(Object o) {
            if (o == null) {
                return "null";
            }
            return o.toString().replace("\n", "\n    ");
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class VerifyReport {
        @JsonProperty("child")
        private VerifyReport child = null;

        @JsonProperty("conformitaParametriInput")
        private Integer conformitaParametriInput = null;

        @JsonProperty("date")
        private String date = null;

        @JsonProperty("errorCode")
        private Integer errorCode = null;

        @JsonProperty("formatoFirma")
        private Integer formatoFirma = null;

        @JsonProperty("numCertificatiFirma")
        private Integer numCertificatiFirma = null;

        @JsonProperty("numCertificatiMarca")
        private Integer numCertificatiMarca = null;

        @JsonProperty("signature")
        private List<Signature> signature = null;

        @JsonProperty("tipoFirma")
        private Integer tipoFirma = null;

        @JsonProperty("uid")
        private List<String> uid = null;

        public VerifyReport child(VerifyReport child) {
            this.child = child;
            return this;
        }

        /**
         * Get child
         *
         * @return child
         **/
        @ApiModelProperty(value = "")
        public VerifyReport getChild() {
            return child;
        }

        public void setChild(VerifyReport child) {
            this.child = child;
        }

        public VerifyReport conformitaParametriInput(Integer conformitaParametriInput) {
            this.conformitaParametriInput = conformitaParametriInput;
            return this;
        }

        /**
         * Conformita dei parametri di input:  0 NON OK 1 OK
         *
         * @return conformitaParametriInput
         **/
        @ApiModelProperty(example = "0", value = "Conformita dei parametri di input:  0 NON OK 1 OK ")
        public Integer getConformitaParametriInput() {
            return conformitaParametriInput;
        }

        public void setConformitaParametriInput(Integer conformitaParametriInput) {
            this.conformitaParametriInput = conformitaParametriInput;
        }

        public VerifyReport date(String date) {
            this.date = date;
            return this;
        }

        /**
         * Data in cui è stato chiesto il rapporto di verifica.
         *
         * @return date
         **/
        @ApiModelProperty(example = "", value = "Data in cui è stato chiesto il rapporto di verifica.")
        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public VerifyReport errorCode(Integer errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        /**
         * Eventuale codice di errore : viene restituito  0 se il processo si è concluso correttamente  altrimenti un valore da 1 a 7 in caso di fallimento di uno dei sette passi.    &gt;      1: Verifica conformità e integrità busta crittografica.      2: Sbustamento busta crittografica.      3: Verifica consistenza firma.      4: Verifica validità certificato.      5: Verifica certification authority.      6: Verifica lista di revoca — CRL aggiornata non disponibile.      7: Verifica lista di revoca — certificato presente nella CRL.
         *
         * @return errorCode
         **/
        @ApiModelProperty(example = "1", value = "Eventuale codice di errore : viene restituito  0 se il processo si è concluso correttamente  altrimenti un valore da 1 a 7 in caso di fallimento di uno dei sette passi.    >      1: Verifica conformità e integrità busta crittografica.      2: Sbustamento busta crittografica.      3: Verifica consistenza firma.      4: Verifica validità certificato.      5: Verifica certification authority.      6: Verifica lista di revoca — CRL aggiornata non disponibile.      7: Verifica lista di revoca — certificato presente nella CRL. ")
        public Integer getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(Integer errorCode) {
            this.errorCode = errorCode;
        }

        public VerifyReport formatoFirma(Integer formatoFirma) {
            this.formatoFirma = formatoFirma;
            return this;
        }

        /**
         * Formato della firma:  1 in caso di firma enveloped 2 in caso di firma separata 3 in caso di marca temporale InfoCert  4 in caso di marca temporale separata
         *
         * @return formatoFirma
         **/
        @ApiModelProperty(example = "1", value = "Formato della firma:  1 in caso di firma enveloped 2 in caso di firma separata 3 in caso di marca temporale InfoCert  4 in caso di marca temporale separata ")
        public Integer getFormatoFirma() {
            return formatoFirma;
        }

        public void setFormatoFirma(Integer formatoFirma) {
            this.formatoFirma = formatoFirma;
        }

        public VerifyReport numCertificatiFirma(Integer numCertificatiFirma) {
            this.numCertificatiFirma = numCertificatiFirma;
            return this;
        }

        /**
         * Numero dei certificati della firma.
         *
         * @return numCertificatiFirma
         **/
        @ApiModelProperty(example = "5", value = "Numero dei certificati della firma.")
        public Integer getNumCertificatiFirma() {
            return numCertificatiFirma;
        }

        public void setNumCertificatiFirma(Integer numCertificatiFirma) {
            this.numCertificatiFirma = numCertificatiFirma;
        }

        public VerifyReport numCertificatiMarca(Integer numCertificatiMarca) {
            this.numCertificatiMarca = numCertificatiMarca;
            return this;
        }

        /**
         * Numero dei certificati della marca temporale.
         *
         * @return numCertificatiMarca
         **/
        @ApiModelProperty(example = "5", value = "Numero dei certificati della marca temporale.")
        public Integer getNumCertificatiMarca() {
            return numCertificatiMarca;
        }

        public void setNumCertificatiMarca(Integer numCertificatiMarca) {
            this.numCertificatiMarca = numCertificatiMarca;
        }

        public VerifyReport signature(List<Signature> signature) {
            this.signature = signature;
            return this;
        }

        public VerifyReport addSignatureItem(Signature signatureItem) {
            if (this.signature == null) {
                this.signature = new ArrayList<Signature>();
            }
            this.signature.add(signatureItem);
            return this;
        }

        /**
         * Firme del file.
         *
         * @return signature
         **/
        @ApiModelProperty(value = "Firme del file.")
        public List<Signature> getSignature() {
            return signature;
        }

        public void setSignature(List<Signature> signature) {
            this.signature = signature;
        }

        public VerifyReport tipoFirma(Integer tipoFirma) {
            this.tipoFirma = tipoFirma;
            return this;
        }

        /**
         * Tipologia di firma o marca temporale:  0 in caso di marca temporale 1 in caso di firma semplice 2 in caso di firma multipla parallela  3 in caso di firma multipla controfirma 4 in caso di firma multipla a catena
         *
         * @return tipoFirma
         **/
        @ApiModelProperty(example = "1", value = "Tipologia di firma o marca temporale:  0 in caso di marca temporale 1 in caso di firma semplice 2 in caso di firma multipla parallela  3 in caso di firma multipla controfirma 4 in caso di firma multipla a catena ")
        public Integer getTipoFirma() {
            return tipoFirma;
        }

        public void setTipoFirma(Integer tipoFirma) {
            this.tipoFirma = tipoFirma;
        }

        public VerifyReport uid(List<String> uid) {
            this.uid = uid;
            return this;
        }

        public VerifyReport addUidItem(String uidItem) {
            if (this.uid == null) {
                this.uid = new ArrayList<String>();
            }
            this.uid.add(uidItem);
            return this;
        }

        /**
         * Eventuale UID del file.
         *
         * @return uid
         **/
        @ApiModelProperty(value = "Eventuale UID del file.")
        public List<String> getUid() {
            return uid;
        }

        public void setUid(List<String> uid) {
            this.uid = uid;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            VerifyReport verifyReport = (VerifyReport) o;
            return Objects.equals(this.child, verifyReport.child) &&
                    Objects.equals(this.conformitaParametriInput, verifyReport.conformitaParametriInput) &&
                    Objects.equals(this.date, verifyReport.date) &&
                    Objects.equals(this.errorCode, verifyReport.errorCode) &&
                    Objects.equals(this.formatoFirma, verifyReport.formatoFirma) &&
                    Objects.equals(this.numCertificatiFirma, verifyReport.numCertificatiFirma) &&
                    Objects.equals(this.numCertificatiMarca, verifyReport.numCertificatiMarca) &&
                    Objects.equals(this.signature, verifyReport.signature) &&
                    Objects.equals(this.tipoFirma, verifyReport.tipoFirma) &&
                    Objects.equals(this.uid, verifyReport.uid);
        }

        @Override
        public int hashCode() {
            return Objects.hash(child, conformitaParametriInput, date, errorCode, formatoFirma, numCertificatiFirma, numCertificatiMarca, signature, tipoFirma, uid);
        }


        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("class VerifyReport {\n");

            sb.append("    child: ").append(toIndentedString(child)).append("\n");
            sb.append("    conformitaParametriInput: ").append(toIndentedString(conformitaParametriInput)).append("\n");
            sb.append("    date: ").append(toIndentedString(date)).append("\n");
            sb.append("    errorCode: ").append(toIndentedString(errorCode)).append("\n");
            sb.append("    formatoFirma: ").append(toIndentedString(formatoFirma)).append("\n");
            sb.append("    numCertificatiFirma: ").append(toIndentedString(numCertificatiFirma)).append("\n");
            sb.append("    numCertificatiMarca: ").append(toIndentedString(numCertificatiMarca)).append("\n");
            sb.append("    signature: ").append(toIndentedString(signature)).append("\n");
            sb.append("    tipoFirma: ").append(toIndentedString(tipoFirma)).append("\n");
            sb.append("    uid: ").append(toIndentedString(uid)).append("\n");
            sb.append("}");
            return sb.toString();
        }

        /**
         * Convert the given object to string with each line indented by 4 spaces
         * (except the first line).
         */
        private String toIndentedString(Object o) {
            if (o == null) {
                return "null";
            }
            return o.toString().replace("\n", "\n    ");
        }

    }


    private static class OauthHelperLocal {

        public static final String ACCESS_TOKEN = "access_token";
        public static final String OAUTH2_GRANT_TYPE = "grantType";
        public static final String OAUTH2_GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";

        /**
         * @param oauthURL       l'endpoint del servizio REST /api/token
         * @param consumerKey    consumerKey @see <a href="https://docs.wso2.com/display/AM191/Token+API">consumerKey</a>
         * @param consumerSecret consumerSecret @see <a href="https://docs.wso2.com/display/AM191/Token+API">consumerSecret</a>
         * @param timeout        timeout in millisecondi della connessione verso /api/token
         */
        public OauthHelperLocal(String oauthURL, String consumerKey, String consumerSecret, int timeout) {
            this.oauthURL = (oauthURL != null) ? oauthURL.trim() : oauthURL;
            this.consumerKey = (consumerKey != null) ? consumerKey.trim() : consumerKey;
            this.consumerSecret = (consumerSecret != null) ? consumerSecret.trim() : consumerSecret;
            this.timeout = timeout;
        }

        /**
         * @param oauthURL       l'endpoint del servizio REST /api/token
         * @param consumerKey    consumerKey @see <a href="https://docs.wso2.com/display/AM191/Token+API">consumerKey</a>
         * @param consumerSecret consumerSecret @see <a href="https://docs.wso2.com/display/AM191/Token+API">consumerSecret</a>
         */

        public OauthHelperLocal(String oauthURL, String consumerKey, String consumerSecret) {
            this(oauthURL, consumerKey, consumerSecret, 30000);
        }

        private final String oauthURL;
        private final String consumerKey;
        private final String consumerSecret;
        private final int timeout;

        private static volatile String token;

        private static volatile Long expires;

        private static volatile long hashCount; // # accessi token in cache
        private static volatile long counter;   // # richieste token


        /**
         * @return il tempo di expires del token in cache
         */
        public static long getExpires() {
            if (expires != null)
                return expires;
            return 0;
        }

        /**
         * @return il token da usare verso l'API Manager
         * <p>
         * Se esiste in cache viene tornato l'ultimo token ottenuto dall'API Manager
         * altrimenti si effettua la richiesta e si torna il valore ottenuto
         */
        public String getToken() {
            return getTokenInternal();
        }

        /**
         * @return il token da usare verso l'API Manager
         * <p>
         * Forza l'ottenimento del token dall'API Manager. Il valore ritornato viene tenuto
         * in cache locale per soddisfare successive <code>getToken</code>
         * Usare questo metodo solo se e' noto a priori che il valore in cache e' invalido
         */
        public String getNewToken() {
            token = null;                  // invalidate cache
            return getTokenInternal();
        }

        /**
         * Implementa la connessione verso l'API Manager
         *
         * @return il token ottenuto dall'API Manager
         */
        private String getTokenInternal() {

            ResteasyClient client = new ResteasyClientBuilder()
                    .connectTimeout(timeout, TimeUnit.MILLISECONDS)
                    .readTimeout(timeout, TimeUnit.MILLISECONDS).build();
            ResteasyWebTarget target = client.target(this.oauthURL);
            Form tokenForm = new Form();
            tokenForm
                    .param("grant_type", "client_credentials")
                    .param("client_id", consumerKey)
                    .param("client_secret", consumerSecret);

            Response tokenResponse = target.request()
                    .header("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2")
                    .header("Content-type", "application/x-www-form-urlencoded")
                    .post(Entity.form(tokenForm));
            int statusCode = tokenResponse.getStatus();
            if (statusCode != HttpStatus.SC_OK) {
                token = null;
                expires = null;
                return token;
            }

            // Read the response body.
            GetTokenResponse responseBody = tokenResponse.readEntity(GetTokenResponse.class);

            token = responseBody.getAccess_token();
            expires = responseBody.getExpires_in();

            if (token == null || expires == null) {
                token = null;
                expires = null;
                return token;
            }
            return token;
        }

    }

    private static class GetTokenResponse {
        private String scope;
        private String token_type;
        private String bearer;
        private Long expires_in;
        private String access_token;

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getToken_type() {
            return token_type;
        }

        public void setToken_type(String token_type) {
            this.token_type = token_type;
        }

        public String getBearer() {
            return bearer;
        }

        public void setBearer(String bearer) {
            this.bearer = bearer;
        }

        public Long getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(Long expires_in) {
            this.expires_in = expires_in;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }
    }

}