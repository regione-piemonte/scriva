/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.index;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.ErrorMessage;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.IndexGenericResult;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.LuceneSearchResponse;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.Node;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.SearchParams;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.SharingInfo;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.VerifyReport;
import it.csi.scriva.scrivabesrv.business.be.helper.index.factory.IndexFactory;
import it.csi.scriva.scrivabesrv.dto.IndexMetadataPropertyDTO;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * The type Index service helper.
 *
 * @author CSI PIEMONTE
 */
public class IndexServiceHelper extends AbstractServiceHelper {

    public static final String URL = "url : [";
    public static final String TENANTS_TENANT_NAME_NODES_UID = "/tenants/{tenantName}/nodes/{uid}";
    private final String className = this.getClass().getSimpleName();

    private static final String CONF_KEY_INDEX_USERNAME = "SCRIVA_INDEX_USERNAME";
    private static final String CONF_KEY_INDEX_PASSWORD = "SCRIVA_INDEX_PASSWORD";
    private static final String CONF_KEY_INDEX_TENANT = "SCRIVA_INDEX_TENANT_NAME";
    private static final String CONF_KEY_INDEX_REPOSITORY = "SCRIVA_INDEX_REPOSITORY";
    private static final String CONF_KEY_INDEX_FRUITORE = "SCRIVA_INDEX_FRUITORE";
    private static final String CONF_KEY_INDEX_NOME_FISICO = "SCRIVA_INDEX_NOME_FISICO";
    private static final String CONF_KEY_INDEX_CONSUMER_KEY = "SCRIVA_APIMAN_CONSUMERKEY";
    private static final String CONF_KEY_INDEX_CONSUMER_SECRET = "SCRIVA_APIMAN_CONSUMERSECRET";
    private static final List<String> CONF_KEYS_INDEX = Arrays.asList(CONF_KEY_INDEX_USERNAME, CONF_KEY_INDEX_PASSWORD, CONF_KEY_INDEX_TENANT, CONF_KEY_INDEX_REPOSITORY, CONF_KEY_INDEX_CONSUMER_KEY, CONF_KEY_INDEX_CONSUMER_SECRET, CONF_KEY_INDEX_FRUITORE, CONF_KEY_INDEX_NOME_FISICO);

    private String tenant;
    private String repository;
    private String xRequestAuth;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).findAndRegisterModules();

    /**
     * Instantiates a new Index service helper.
     *
     * @param endPoint   the end point
     * @param serviceUrl the service url
     */
    public IndexServiceHelper(String endPoint, String serviceUrl) {
        this.tokenUrl = endPoint + "/token";
        this.apiEndpoint = endPoint + serviceUrl;
    }

    /**
     * Sets conf keys.
     *
     * @throws JsonProcessingException the json processing exception
     */
    public void setConfKeys() throws JsonProcessingException {
        this.configurazioneList = this.configurazioneList == null || this.configurazioneList.isEmpty() ? configurazioneDAO.loadConfigByKeyList(CONF_KEYS_INDEX) : this.configurazioneList;
        if (this.configurazioneList != null && !this.configurazioneList.isEmpty()) {
            String username = configurazioneList.getOrDefault(CONF_KEY_INDEX_USERNAME, null);
            String password = configurazioneList.getOrDefault(CONF_KEY_INDEX_PASSWORD, null);
            this.repository = configurazioneList.getOrDefault(CONF_KEY_INDEX_REPOSITORY, null);
            this.consumerKey = configurazioneList.getOrDefault(CONF_KEY_INDEX_CONSUMER_KEY, null);
            this.consumerSecret = configurazioneList.getOrDefault(CONF_KEY_INDEX_CONSUMER_SECRET, null);
            this.tenant = configurazioneList.getOrDefault(CONF_KEY_INDEX_TENANT, null);
            String fruitore = configurazioneList.getOrDefault(CONF_KEY_INDEX_FRUITORE, null);
            String nomeFisico = configurazioneList.getOrDefault(CONF_KEY_INDEX_NOME_FISICO, null);
            this.xRequestAuth = IndexFactory.getXRequestAuth(username, password, this.tenant, fruitore, this.repository, nomeFisico);
        }
    }

    /**
     * Gets consumer key.
     *
     * @return the consumer key
     * @throws JsonProcessingException the json processing exception
     */
    public String getConsumerKey() throws JsonProcessingException {
        if (this.configurazioneList == null || this.configurazioneList.isEmpty()) {
            setConfKeys();
        }
        return consumerKey;
    }

    /**
     * Gets consumer secret.
     *
     * @return the consumer secret
     * @throws JsonProcessingException the json processing exception
     */
    public String getConsumerSecret() throws JsonProcessingException {
        if (this.configurazioneList == null || this.configurazioneList.isEmpty()) {
            setConfKeys();
        }
        return consumerSecret;
    }

    /**
     * Gets tenant.
     *
     * @return the tenant
     * @throws JsonProcessingException the json processing exception
     */
    public String getTenant() throws JsonProcessingException {
        if (this.configurazioneList == null || this.configurazioneList.isEmpty()) {
            setConfKeys();
        }
        return tenant;
    }

    /**
     * Gets repository.
     *
     * @return the repository
     * @throws JsonProcessingException the json processing exception
     */
    public String getRepository() throws JsonProcessingException {
        if (this.configurazioneList == null || this.configurazioneList.isEmpty()) {
            setConfKeys();
        }
        return repository;
    }

    /**
     * Gets request auth.
     *
     * @return the request auth
     * @throws JsonProcessingException the json processing exception
     */
    public String getxRequestAuth() throws JsonProcessingException {
        if (this.configurazioneList == null || this.configurazioneList.isEmpty()) {
            setConfKeys();
        }
        return xRequestAuth;
    }

    /**
     * Gets configurazione list.
     *
     * @return the configurazione list
     * @throws JsonProcessingException the json processing exception
     */
    public Map<String, String> getConfigurazioneList() throws JsonProcessingException {
        if (this.configurazioneList == null || this.configurazioneList.isEmpty()) {
            setConfKeys();
        }
        return configurazioneList;
    }

    /**
     * Crea un nuovo nodo.
     *
     * @param name                    Nome della cartella o del file
     * @param parentNodeUid           Uid della cartella padre
     * @param file                    File che si intende inserire (non sarà valorizzato se si sta creando una cartella)
     * @param indexMetadataProperties Proprietà che si intendono inserire
     * @return Uid del nodo creato
     */
    public String createContent(String name, String parentNodeUid, File file, IndexMetadataPropertyDTO indexMetadataProperties) {
        logBegin(className);
        logDebug(className, "Parametri in input :\n name [" + name + "]\n parentNodeUid [" + parentNodeUid + "]\n file");
        logInfo(className, "createContent", "TIMER BEGIN");
        String result = null;
        String api = "/tenants/{tenantName}/nodes";
        try {
            String url = IndexFactory.getUrl(apiEndpoint + api, this.getTenant(), null);
            logDebug(className, URL + url + "]");
            Node indexNode = file == null ? IndexFactory.getCreateFolderNode(name) : IndexFactory.getCreateContentNode(name, indexMetadataProperties);
            String jsonNode = objectMapper.writeValueAsString(indexNode);
            logDebug(className, "POST :\nurl: [" + url + "]\ntenantName: [" + this.getTenant() + "]\nparentNodeUid : [" + parentNodeUid + "]\nnode: [" + jsonNode + "]\n");

            MultipartFormDataOutput multipartForm = new MultipartFormDataOutput();
            multipartForm.addFormData("parentNodeUid", parentNodeUid, MediaType.TEXT_PLAIN_TYPE);
            multipartForm.addFormData("node", jsonNode, MediaType.TEXT_PLAIN_TYPE);
            if (file != null) {
                multipartForm.addFormData("binaryContent", file, MediaType.APPLICATION_OCTET_STREAM_TYPE, file.getName());
            }
            Entity<MultipartFormDataOutput> entity = Entity.entity(multipartForm, MediaType.MULTIPART_FORM_DATA);

            logInfo(className, "createContent", "TIMER: iniziocaricamento su index");

            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
                    //.header(HttpHeaders.ACCEPT, "text/plain")
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)  // Refactor INDEX
                    .post(entity);
            logInfo(className, "createcontent", "TIMER: fine caricamento su index");        
            if (resp.getStatus() >= 400) {
                String error = resp.readEntity(String.class);
                logError(className, (StringUtils.isNotBlank(error) ? error : resp.getStatus()));
            } else {
                //result = resp.readEntity(String.class);
                GenericType<IndexGenericResult> indexResultGenericType = new GenericType<IndexGenericResult>() {
                };
                IndexGenericResult indexGenericResult = resp.readEntity(indexResultGenericType);
                result = indexGenericResult != null ? (String) indexGenericResult.getOutput() : null;
                logDebug(className, "Creato nodo : " + result);
            }
        } catch (JsonProcessingException e) {
            logError(className, e);
        }
        logInfo(className, "createContent", "TIMER END");
        return result;
    }

    /**
     * Verifica la presenza della firma digitale (embedded, busta o detached) di un documento, lo salva opzionalmente su temp,
     * restituisce la verifica ed eventualmente l'UID.
     *
     * @param uid  uid
     * @param file File di cui si vuole verificare la firma
     * @return Report della verifica
     */
    public VerifyReport verifySignedDocument(String uid, File file) {
        logBegin(className);
        String api = "/utils/_verify/document/signed";
        String url = apiEndpoint + api;
        VerifyReport verifyReport = null;
        logDebug(className, URL + url + "]");
        try {
            MultipartFormDataOutput multipartForm = new MultipartFormDataOutput();
            if (uid != null && StringUtils.isNotBlank(uid)) {
                multipartForm.addFormData("documentUid", uid, MediaType.TEXT_PLAIN_TYPE);
                multipartForm.addFormData("documentContentPropertyName", "cm:content", MediaType.TEXT_PLAIN_TYPE);
            }
            if (file != null) {
                multipartForm.addFormData("documentBinaryContent", file, MediaType.APPLICATION_OCTET_STREAM_TYPE, file.getName());
            }
            multipartForm.addFormData("documentStore", "false", MediaType.TEXT_PLAIN_TYPE);
            Entity<MultipartFormDataOutput> entity = Entity.entity(multipartForm, MediaType.MULTIPART_FORM_DATA);
            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);
            if (resp.getStatus() >= 400) {
                String errorMessage = resp.readEntity(String.class);
                logError(className, (StringUtils.isNotBlank(errorMessage) ? errorMessage : resp.getStatus()));
            } else {
                GenericType<VerifyReport> verifyReportGenericType = new GenericType<VerifyReport>() {
                };
                verifyReport = resp.readEntity(verifyReportGenericType);
                logDebug(className, "verifyReport" + (file != null ? " file [" + file.getName() + "]" : "") + (StringUtils.isNotBlank(uid) ? " uid [" + uid + "]" : "") + " : \n\n" + verifyReport + "\n\n");
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return verifyReport;
    }

    /**
     * Verify signed document verify report.
     *
     * @param file the file
     * @return the verify report
     * @throws GenericException the generic exception
     */
    public VerifyReport verifySignedDocument(InputStream file) throws GenericException {
        logBegin(className);
        String api = "/utils/_verify/document/signed";
        String url = apiEndpoint + api;
        VerifyReport verifyReport = null;
        logDebug(className, URL + url + "]");
        try {
            MultipartFormDataOutput multipartForm = new MultipartFormDataOutput();
            multipartForm.addFormData("documentBinaryContent", file, MediaType.APPLICATION_OCTET_STREAM_TYPE, "filename");
            multipartForm.addFormData("documentStore", "false", MediaType.TEXT_PLAIN_TYPE);
            Entity<MultipartFormDataOutput> entity = Entity.entity(multipartForm, MediaType.MULTIPART_FORM_DATA);
            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);
            if (resp.getStatus() >= 400) {
                String errorMessage = resp.readEntity(String.class);
                logError(className, StringUtils.isNotBlank(errorMessage) ? errorMessage : resp.getStatus());
            } else {
                GenericType<VerifyReport> verifyReportGenericType = new GenericType<VerifyReport>() {
                };
                verifyReport = resp.readEntity(verifyReportGenericType);
                logDebug(className, "verifyReport file : \n\n" + verifyReport + "\n\n");
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return verifyReport;

    }


    /**
     * Restituisce il contenuto binario di un nodo (download).
     *
     * @param uid Uid del file da recuperare
     * @return File recuperato
     */
    public File retrieveContentData(String uid) {
        logBegin(className);
        File result = null;
        String api = TENANTS_TENANT_NAME_NODES_UID;
        try {
            String url = IndexFactory.getUrl(apiEndpoint + api, this.getTenant(), uid) + "/contents?contentPropertyName=cm:content";
            logDebug(className, URL + url + "]");
            Response resp = getBuilder(url)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .get();
            if (resp.getStatus() >= 400) {
                String errorMessage = resp.readEntity(String.class);
                logError(className, StringUtils.isNotBlank(errorMessage) ? errorMessage : resp.getStatus());
            } else {
                GenericType<File> fileType = new GenericType<File>() {
                };
                result = resp.readEntity(fileType);
                if (null != result) {
                    logDebug(className, "Recuperato file  : [" + result.getName() + "]");
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Modifica il contenuto binario di un nodo. (Attenzione!!! ricordarsi di aggiornare i metadata con la chiamata al metodo updateMetadata)
     *
     * @param uid  Uid del file che si vuole aggiornare
     * @param file the file
     * @return the string
     */
    public String updateContentData(String uid, File file) {
        logBegin(className);
        String api = TENANTS_TENANT_NAME_NODES_UID;
        String errorMessage = null;
        try {
            String url = IndexFactory.getUrl(apiEndpoint + api, this.getTenant(), uid) + "/contents?contentPropertyName=cm:content";
            logDebug(className, URL + url + "]");
            MultipartFormDataOutput multipartForm = new MultipartFormDataOutput();
            multipartForm.addFormData("encoding", "UTF-8", MediaType.TEXT_PLAIN_TYPE);
            multipartForm.addFormData("mimeType", file.toURI().toURL().openConnection().getContentType(), MediaType.TEXT_PLAIN_TYPE);
            multipartForm.addFormData("binaryContent", file, MediaType.APPLICATION_OCTET_STREAM_TYPE, file.getName());
            Entity<MultipartFormDataOutput> entity = Entity.entity(multipartForm, MediaType.MULTIPART_FORM_DATA);

            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .put(entity);
            if (resp.getStatus() >= 400) {
                errorMessage = resp.readEntity(String.class);
                logError(className, StringUtils.isNotBlank(errorMessage) ? errorMessage : resp.getStatus());
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return errorMessage;
    }

    /**
     * Modifica i metadati (proprietà e aspetti) di un nodo.
     *
     * @param uid                     uuidIndex
     * @param indexMetadataProperties IndexMetadataPropertiesDTO
     * @return the error message
     */
    public ErrorMessage updateMetadata(String uid, IndexMetadataPropertyDTO indexMetadataProperties) {
        logBegin(className);
        String api = TENANTS_TENANT_NAME_NODES_UID;
        ErrorMessage errorMessage = null;
        try {
            String url = IndexFactory.getUrl(apiEndpoint + api, this.getTenant(), uid);
            logDebug(className, URL + url + "]");

            // Costruzione parametro node
            Node indexNode = IndexFactory.getCreateContentNode(uid, indexMetadataProperties);
            ObjectMapper mapper = new ObjectMapper();
            logInfo(className, "JSON inviato: " + mapper.writeValueAsString(indexNode));
            Entity<Node> entity = Entity.json(indexNode);

            Response resp = getBuilder(url)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .put(entity);

            if (resp.getStatus() >= 400) {
    /*              Jira 1626. In caso di errore aggiornamento metadati l'errore non risaliva al livello superiore e la chiamata principale es. POST allegati restituiva 200 come se fosse andato tutto bene 
                    String errMsg = resp.readEntity(String.class);
                    logError(className, StringUtils.isNotBlank(errMsg) ? errMsg : resp.getStatus());
                    GenericType<ErrorMessage> errorMessageGenericType = new GenericType<ErrorMessage>() {
                    };
                    errorMessage = resp.readEntity(errorMessageGenericType);
    */ 
                String errMsg = resp.readEntity(String.class);
                logError(className, StringUtils.isNotBlank(errMsg) ? errMsg : String.valueOf(resp.getStatus()));

                // Log di alcuni header selezionati
                List<String> headersToLog = Arrays.asList("Content-Type", "X-Error", "X-Request-ID");
                for (String headerName : headersToLog) {
                    List<String> values = resp.getStringHeaders().get(headerName);
                    if (values != null && !values.isEmpty()) {
                        logDebug(className, "Header: " + headerName + " = " + String.join(", ", values));
                    }
                }

                if (StringUtils.isNotBlank(errMsg)) {
                    try {
                        errorMessage = mapper.readValue(errMsg, ErrorMessage.class);
                    } catch (Exception ex) {
                        logError(className, "Errore durante la deserializzazione di ErrorMessage: " + ex.getMessage());
                    }
                } else {
                    logError(className, "Corpo della risposta vuoto: impossibile deserializzare ErrorMessage");
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return errorMessage;
    }

    /**
     * Cancella un nodo o elimina un nodo cancellato.
     *
     * @param uid UID del nodo
     * @return ErrorMessage in caso di errore
     */
    public ErrorMessage deleteContent(String uid) {
        logBegin(className);
        ErrorMessage errorMessage = null;
        String api = TENANTS_TENANT_NAME_NODES_UID;
        try {
            String url = IndexFactory.getUrl(apiEndpoint + api, this.getTenant(), uid);
            logDebug(className, URL + url + "]");
            Entity<String> entity = Entity.json(objectMapper.writeValueAsString("DELETE_AND_PURGE"));
            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .build("DELETE", entity)
                    .invoke();
            if (resp.getStatus() >= 400) {
                String errMsg = resp.readEntity(String.class);
                logError(className, StringUtils.isNotBlank(errMsg) ? errMsg : resp.getStatus());
                GenericType<ErrorMessage> errorMessageGenericType = new GenericType<ErrorMessage>() {
                };
                errorMessage = resp.readEntity(errorMessageGenericType);
            }
        } catch (Exception e) {
            logError(className, e);
            errorMessage = new ErrorMessage();
            errorMessage.setStatus(500);
            errorMessage.setTitle(e.getMessage());
        } finally {
            logEnd(className);
        }

        return errorMessage;
    }

    /**
     * Restituisce i metadati (tra cui proprietà e aspetti) di un nodo.
     *
     * @param uid UID del nodo
     * @return Node metadati del nodo
     */
    public Node getContentMetadata(String uid) {
        logBegin(className);
        String api = TENANTS_TENANT_NAME_NODES_UID;
        Node indexNode = null;
        try {
            String url = IndexFactory.getUrl(apiEndpoint + api, this.getTenant(), uid);
            logDebug(className, URL + url + "]");
            Response resp = getBuilder(url)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .get();
            if (resp.getStatus() >= 400) {
                String errorMessage = resp.readEntity(String.class);
                logError(className, StringUtils.isNotBlank(errorMessage) ? errorMessage : resp.getStatus());
            } else {
                GenericType<Node> nodo = new GenericType<Node>() {
                };
                indexNode = resp.readEntity(nodo);
                logDebug(className, resp.getStatus() + " : \n" + nodo.toString() + "\n");
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return indexNode;
    }

    /**
     * Restituisce l'elenco dei nodi che rispecchiano i parametri di ricerca
     *
     * @param metadata     boolean
     * @param searchParams parametri di ricerca
     * @return ListNode list
     */
    public List<Node> luceneSearch(Boolean metadata, SearchParams searchParams) {
        logBegin(className);
        String api = "/tenants/{tenantName}/nodes/_search";
        List<Node> indexNodeList = null;
        try {
            String url = IndexFactory.getUrl(apiEndpoint + api, this.getTenant(), null) + (Boolean.TRUE.equals(metadata) ? "?metadata=true" : "");
            logDebug(className, URL + url + "]");
            Entity<SearchParams> entity = Entity.json(searchParams);
            Response resp = getBuilder(url)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);
            if (resp.getStatus() >= 400) {
                String errorMessage = resp.readEntity(String.class);
                logError(className, StringUtils.isNotBlank(errorMessage) ? errorMessage : resp.getStatus());
            } else {
                GenericType<LuceneSearchResponse> luceneSearchResponseGenericType = new GenericType<LuceneSearchResponse>() {
                };
                LuceneSearchResponse luceneSearchResponse = resp.readEntity(luceneSearchResponseGenericType);
                indexNodeList = luceneSearchResponse != null && luceneSearchResponse.getNodes() != null ? luceneSearchResponse.getNodes() : new ArrayList<>();
                logDebug(className, resp.getStatus() + " : \n" + luceneSearchResponseGenericType.toString() + "\n");
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return indexNodeList;
    }


    /**
     * Share document string.
     *
     * @param uid         the uid
     * @param sharingInfo the sharing info
     * @return the string
     */
    public String shareDocument(String uid, SharingInfo sharingInfo) {
        logBegin(className);
        logDebug(className, "Parametro input uid [" + uid + "] - sharingInfo:\n" + sharingInfo + "\n");
        String api = "/tenants/{tenantName}/nodes/{uid}/sharedLinks";
        String result = null;
        try {
            String url = IndexFactory.getUrl(apiEndpoint + api, this.getTenant(), uid);
            logDebug(className, URL + url + "]");
            Entity<SharingInfo> entity = Entity.json(sharingInfo);
            String jsonSharingInfo = objectMapper.writeValueAsString(sharingInfo);
            logDebug(className, "POST :\nurl: [" + url + "]\ntenantName: [" + this.getTenant() + "]\nuid : [" + uid + "]\nsharingInfo: [" + jsonSharingInfo + "]\n");
            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    //.header(HttpHeaders.ACCEPT, "text/plain")
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON) //Refactor INDEX
                    .post(entity);
            if (resp.getStatus() >= 400) {
                String errorMessage = resp.readEntity(String.class);
                logError(className, StringUtils.isNotBlank(errorMessage) ? errorMessage : resp.getStatus());
            } else {
                //result = resp.readEntity(new GenericType<>() {});
                GenericType<IndexGenericResult> indexResultGenericType = new GenericType<IndexGenericResult>() {
                };
                IndexGenericResult indexGenericResult = resp.readEntity(indexResultGenericType);
                result = indexGenericResult != null ? (String) indexGenericResult.getOutput() : null;
                logDebug(className, resp.getStatus() + " : \n" + result + "\n");
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Update shared link string.
     *
     * @param uid         the uid
     * @param shareLinkId the share link id
     * @param sharingInfo the sharing info
     * @return the string
     */
    public String updateSharedLink(String uid, String shareLinkId, SharingInfo sharingInfo) {
        logBegin(className);
        logDebug(className, "Parametro input uid [" + uid + "] - sharingInfo:\n" + sharingInfo + "\n");
        String api = "/tenants/{tenantName}/nodes/{uid}/sharedLinks/" + shareLinkId;
        String result = null;
        try {
            String url = IndexFactory.getUrl(apiEndpoint + api, this.getTenant(), uid);
            logDebug(className, URL + url + "]");
            Entity<SharingInfo> entity = Entity.json(sharingInfo);
            String jsonSharingInfo = objectMapper.writeValueAsString(sharingInfo);
            logDebug(className, "PUT :\nurl: [" + url + "]\ntenantName: [" + this.getTenant() + "]\nuid : [" + uid + "]\nsharingInfo: [" + jsonSharingInfo + "]\n");
            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .put(entity);
            if (resp.getStatus() >= 400) {
                String errorMessage = resp.readEntity(String.class);
                logError(className, StringUtils.isNotBlank(errorMessage) ? errorMessage : resp.getStatus());
            } else {
                result = resp.readEntity(new GenericType<String>() {
                });
                logDebug(className, resp.getStatus() + " : \n" + result + "\n");
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets sharing infos.
     *
     * @param uid the uid
     * @return the sharing infos
     */
    public SharingInfo getSharingInfos(String uid) {
        logBeginInfo(className, (Object) uid);
        String api = "/tenants/{tenantName}/nodes/{uid}/sharedLinks";
        List<SharingInfo> result = null;
        try {
            String url = IndexFactory.getUrl(apiEndpoint + api, this.getTenant(), uid);
            logDebug(className, URL + url + "]");
            Response resp = getBuilder(url)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .get();
            if (resp.getStatus() >= 400) {
                String errorMessage = resp.readEntity(String.class);
                logError(className, StringUtils.isNotBlank(errorMessage) ? errorMessage : resp.getStatus());
            } else {
                resp.bufferEntity();
                result = resp.readEntity(new GenericType<List<SharingInfo>>() {
                });
            
                logDebug(className, uid + " : " + resp.getStatus() + " : \n" + resp.readEntity(String.class) + "\n");

            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result != null && !result.isEmpty() ? result.get(0) : null;
    }

    /**
     * Copy content between tenant.
     *
     * @param uid the uid
     */
    public void copyContentBetweenTenant(String uid) {
        // POST /tenants/{tenantName}/nodes/{uid}/_copyBetweenTenant
    }

    @Override
    public Invocation.Builder getBuilder(String url) throws JsonProcessingException {
        Client client = ClientBuilder.newBuilder().build();
        Invocation.Builder cl = client.target(url).request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + IndexFactory.getTokenIndex(tokenUrl, this.getConsumerKey(), this.getConsumerSecret()))
                .header("X-Request-Auth", xRequestAuth);
        LOGGER.info("IndexServiceHelper::getBuilder : fine recupero token");
        return cl;        
    }

    

}