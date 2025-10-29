/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.cosmo;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.csi.scriva.scrivabesrv.business.be.exception.CosmoException;
import it.csi.scriva.scrivabesrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.AvviaProcessoFruitoreRequest;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.AvviaProcessoFruitoreResponse;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.CreaDocumentiFruitoreRequest;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.CreaNotificaFruitoreRequest;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.CreaNotificaFruitoreResponse;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.CreaPraticaFruitoreRequest;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.CreaPraticaFruitoreResponse;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.Esito;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.EsitoCreazioneDocumentiFruitore;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.FileUploadResult;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.GetPraticaFruitoreResponse;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.InviaSegnaleFruitoreRequest;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.InviaSegnaleFruitoreResponse;
import it.csi.scriva.scrivabesrv.util.Constants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;

import javax.validation.constraints.NotNull;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

/**
 * The type Cosmo service helper.
 *
 * @author CSI PIEMONTE
 */
public class CosmoServiceHelper extends AbstractServiceHelper {

    public static final String URL = "\nurl: [";
    public static final String ERROR_RESPONSE_STATUS_CODE = "\nError response : \nStatus code:\n";
    public static final String ESITO = "\nEsito:\n";
    private final String className = this.getClass().getSimpleName();
    public static final String PRATICHE = "/pratiche/";
    private static final List<String> CONF_KEYS_COSMO = Arrays.asList(Constants.CONF_KEY_APIMAN_CONSUMER_KEY, Constants.CONF_KEY_APIMAN_CONSUMER_SECRET);


    /**
     * Instantiates a new Cosmo service helper.
     *
     * @param endPoint   the end point
     * @param serviceUrl the service url
     * @throws JsonProcessingException the json processing exception
     */
    public CosmoServiceHelper(String endPoint, String serviceUrl) throws JsonProcessingException {
        tokenUrl = endPoint + "/token";
        apiEndpoint = endPoint + serviceUrl;
    }

    /**
     * Sets conf keys.
     */
    public void setConfKeys() {
        this.configurazioneList = this.configurazioneList == null || this.configurazioneList.isEmpty() ?
                configurazioneDAO.loadConfigByKeyList(CONF_KEYS_COSMO) :
                this.configurazioneList;
        if (this.configurazioneList != null && !this.configurazioneList.isEmpty()) {
            this.consumerKey = configurazioneList.getOrDefault(Constants.CONF_KEY_APIMAN_CONSUMER_KEY, null);
            this.consumerSecret = configurazioneList.getOrDefault(Constants.CONF_KEY_APIMAN_CONSUMER_SECRET, null);
        }
    }

    /**
     * Gets consumer key.
     *
     * @return the consumer key
     */
    public String getConsumerKey() {
        if (this.configurazioneList == null || this.configurazioneList.isEmpty() || StringUtils.isBlank(this.consumerKey)) {
            setConfKeys();
        }
        return consumerKey;
    }

    /**
     * Gets consumer secret.
     *
     * @return the consumer secret
     */
    public String getConsumerSecret() {
        if (this.configurazioneList == null || this.configurazioneList.isEmpty() || StringUtils.isBlank(this.consumerSecret)) {
            setConfKeys();
        }
        return consumerSecret;
    }


    /**** PRATICHE ****/

    /**
     * Il servizio esposto permette di creare su COSMO una nuova pratica e predisporre i dati principali che permettono
     * di avviare il processo e rendere in seguito disponibili attività correlate per gli utenti.
     *
     * @param creaPraticaFruitoreRequest the crea pratica fruitore request
     * @return the creazione pratica
     * @throws CosmoException the cosmo exception
     */
    public CreaPraticaFruitoreResponse creaPratica(@NotNull CreaPraticaFruitoreRequest creaPraticaFruitoreRequest) throws CosmoException {
        logBeginInfo(className, creaPraticaFruitoreRequest);
        CreaPraticaFruitoreResponse result = null;
        String api = "/pratiche";
        try {
            String url = apiEndpoint + api;
            Entity<CreaPraticaFruitoreRequest> entity = Entity.entity(creaPraticaFruitoreRequest, MediaType.APPLICATION_JSON);
            String debugInfo = URL + url + "]\ncreaPraticaFruitoreRequest: \n" + creaPraticaFruitoreRequest.toString() + "\n";
            logDebug(className, debugInfo);
            this.setConfKeys(CONF_KEYS_COSMO);
            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);
            if (resp.getStatus() >= 400) {
                String esito = resp.readEntity(String.class);
                logError(className, debugInfo + ERROR_RESPONSE_STATUS_CODE + resp.getStatus() + ESITO + esito + "\n");
                throw new CosmoException(esito);
            } else {
                result = resp.readEntity(CreaPraticaFruitoreResponse.class);
                logDebug(className, debugInfo + ERROR_RESPONSE_STATUS_CODE + resp.getStatus() + ESITO + result + "\n");
            }
        } catch (CosmoException e) {
            throw new CosmoException(e);
        } catch (Exception e) {
            logError(className, e);
            throw new CosmoException(e.getMessage());
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Ottiene lo stato della pratica.
     *
     * @param idPratica the id pratica
     * @return the pratica
     * @throws CosmoException the cosmo exception
     */
    public GetPraticaFruitoreResponse getPratica(@NotNull String idPratica) throws CosmoException {
        logBeginInfo(className, idPratica);
        GetPraticaFruitoreResponse result = null;
        String api = PRATICHE + idPratica;
        try {
            String url = apiEndpoint + api;
            String debugInfo = URL + url + "]\nidPratica: [" + idPratica + "]\n";
            this.setConfKeys(CONF_KEYS_COSMO);
            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .get();
            if (resp.getStatus() >= 400) {
                Esito esito = resp.readEntity(Esito.class);
                logError(className, debugInfo + ERROR_RESPONSE_STATUS_CODE + resp.getStatus() + ESITO + esito + "\n");
                throw new CosmoException(esito);
            } else {
                result = resp.readEntity(GetPraticaFruitoreResponse.class);
                logDebug(className, debugInfo + ERROR_RESPONSE_STATUS_CODE + resp.getStatus() + ESITO + result + "\n");
            }
        } catch (Exception e) {
            logError(className, e);
            throw new CosmoException(e);
        } finally {
            logEnd(className);
        }
        return result;
    }


    /**** PROCESSI ****/

    /**
     * Il servizio esposto permette di avviare il processo legato alla pratica creata in fase di Crea Pratica COSMO.
     * Questo comporta l’assegnazione di una prima attività, disegnata nel processo, ad un utente di COSMO e l’avvio dell’iter.
     *
     * @param avviaProcessoFruitoreRequest the avvia processo fruitore request
     * @return the avvio pratica cosmo resp
     * @throws CosmoException the cosmo exception
     */
    public AvviaProcessoFruitoreResponse avviaPratica(@NotNull AvviaProcessoFruitoreRequest avviaProcessoFruitoreRequest) throws CosmoException {
        logBeginInfo(className, avviaProcessoFruitoreRequest);
        AvviaProcessoFruitoreResponse result = null;
        String api = "/processo";
        try {
            String url = apiEndpoint + api;
            Entity<AvviaProcessoFruitoreRequest> entity = Entity.entity(avviaProcessoFruitoreRequest, MediaType.APPLICATION_JSON);
            String debugInfo = URL + url + "]\navviaProcessoFruitoreRequest: \n" + avviaProcessoFruitoreRequest.toString() + "\n";
            this.setConfKeys(CONF_KEYS_COSMO);
            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);
            if (resp.getStatus() >= 400) {
                Esito esito = resp.readEntity(Esito.class);
                logError(className, debugInfo + ERROR_RESPONSE_STATUS_CODE + resp.getStatus() + ESITO + esito + "\n");
                throw new CosmoException(esito);
            } else {
                result = resp.readEntity(AvviaProcessoFruitoreResponse.class);
                logDebug(className, debugInfo + ERROR_RESPONSE_STATUS_CODE + resp.getStatus() + ESITO + result + "\n");
            }
        } catch (Exception e) {
            logError(className, e);
            throw new CosmoException(e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * L’utilizzo classico di questo servizio si concretizza quando una pratica dev’essere lavorata su un sistema esterno
     * e solo in un momento successivo a tale lavorazione deve riprendere il suo iter su COSMO.
     * Per soddisfare questa esigenza COSMO mette a disposizione il servizio di avanzamento processo da evento.
     *
     * @param idPratica                   the id pratica
     * @param inviaSegnaleFruitoreRequest the invia segnale fruitore request
     * @return the invia segnale fruitore response
     * @throws CosmoException the cosmo exception
     */
    public InviaSegnaleFruitoreResponse avanzaProcesso(@NotNull String idPratica, @NotNull InviaSegnaleFruitoreRequest inviaSegnaleFruitoreRequest) throws CosmoException {
        logBeginInfo(className, "idPratica [" + idPratica + "] - inviaSegnaleFruitoreRequest : \n" + inviaSegnaleFruitoreRequest + "\n");
        InviaSegnaleFruitoreResponse result = null;
        String api = PRATICHE + idPratica + "/segnala";
        try {
            String url = apiEndpoint + api;
            Entity<InviaSegnaleFruitoreRequest> entity = Entity.entity(inviaSegnaleFruitoreRequest, MediaType.APPLICATION_JSON);
            String debugInfo = URL + url + "]\ninviaSegnaleFruitoreRequest: \n" + inviaSegnaleFruitoreRequest.toString() + "\n";
            this.setConfKeys(CONF_KEYS_COSMO);
            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);
            if (resp.getStatus() >= 400) {
                Esito esito = resp.readEntity(Esito.class);
                logError(className, debugInfo + ERROR_RESPONSE_STATUS_CODE + resp.getStatus() + ESITO + esito + "\n");
                throw new CosmoException(esito);
            } else {
                result = resp.readEntity(InviaSegnaleFruitoreResponse.class);
                logDebug(className, debugInfo + ERROR_RESPONSE_STATUS_CODE + resp.getStatus() + ESITO + result + "\n");
            }
        } catch (Exception e) {
            logError(className, e);
            throw new CosmoException(e);
        } finally {
            logEnd(className);
        }
        return result;
    }


    /**** FILE ****/

    /**
     * Il servizio esposto permette di caricare sul filesystem di COSMO dei file e ricevere in risposta un UUID del documento caricato in risposta.
     *
     * @param file     the file
     * @param fileName the file name
     * @return the file upload result
     * @throws CosmoException the cosmo exception
     */
    public FileUploadResult uploadDocumento(File file, String fileName) throws CosmoException {
        logBegin(className);
        FileUploadResult result = null;
        String api = "/file/upload";
        try {
            String url = apiEndpoint + api;
            MultipartFormDataOutput multipartForm = new MultipartFormDataOutput();
            if (file != null) {
                multipartForm.addFormData("payload", file, MediaType.APPLICATION_OCTET_STREAM_TYPE, fileName);
            }
            Entity<MultipartFormDataOutput> entity = Entity.entity(multipartForm, MediaType.MULTIPART_FORM_DATA);
            String debugInfo = URL + url + "]\nfile: \n" + fileName + "\n";
            this.setConfKeys(CONF_KEYS_COSMO);
            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);
            if (resp.getStatus() >= 400) {
                Esito esito = resp.readEntity(Esito.class);
                logError(className, debugInfo + ERROR_RESPONSE_STATUS_CODE + resp.getStatus() + ESITO + esito + "\n");
                throw new CosmoException(esito);
            } else {
                result = resp.readEntity(FileUploadResult.class);
                logDebug(className, debugInfo + ERROR_RESPONSE_STATUS_CODE + resp.getStatus() + ESITO + result + "\n");
            }
        } catch (Exception e) {
            logError(className, e);
            throw new CosmoException(e);
        } finally {
            logEnd(className);
        }
        return result;
    }


    /**** DOCUMENTI ****/

    /**
     * Il servizio esposto permette di inserire i metadati di uno o più documenti ed associarli ad una pratica in COSMO.
     *
     * @param idPraticaExt                 the id pratica ext
     * @param creaDocumentiFruitoreRequest the crea documenti fruitore request
     * @return the esito creazione documenti fruitore
     * @throws CosmoException the cosmo exception
     */
    public EsitoCreazioneDocumentiFruitore caricaDocumento(@NotNull String idPraticaExt, @NotNull CreaDocumentiFruitoreRequest creaDocumentiFruitoreRequest) throws CosmoException {
        logBeginInfo(className, idPraticaExt + "\n" + creaDocumentiFruitoreRequest);
        EsitoCreazioneDocumentiFruitore result = null;
        String api = "/documenti";
        try {
            String url = apiEndpoint + api;
            Entity<CreaDocumentiFruitoreRequest> entity = Entity.entity(creaDocumentiFruitoreRequest, MediaType.APPLICATION_JSON);
            String debugInfo = URL + url + "]\ncreaDocumentiFruitoreRequest: \n" + creaDocumentiFruitoreRequest.toString() + "\n";
            this.setConfKeys(CONF_KEYS_COSMO);
            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);
            if (resp.getStatus() >= 400) {
                Esito esito = resp.readEntity(Esito.class);
                logError(className, debugInfo + ERROR_RESPONSE_STATUS_CODE + resp.getStatus() + ESITO + esito + "\n");
                throw new CosmoException(esito);
            } else {
                result = resp.readEntity(EsitoCreazioneDocumentiFruitore.class);
                logDebug(className, debugInfo + ERROR_RESPONSE_STATUS_CODE + resp.getStatus() + ESITO + result + "\n");
            }
        } catch (Exception e) {
            logError(className, e);
            throw new CosmoException(e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets documento.
     *
     * @param idDocumento the id documento
     * @return the documento
     * @throws CosmoException the cosmo exception
     */
    public File getDocumento(@NotNull Long idDocumento) throws CosmoException {
        logBeginInfo(className, idDocumento);
        File result = null;
        String api = "/documenti/" + idDocumento + "/contenuto";
        try {
            String url = apiEndpoint + api;
            String debugInfo = URL + url + "]\nidDocumento: [" + idDocumento + "]\n";
            this.setConfKeys(CONF_KEYS_COSMO);
            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_OCTET_STREAM_TYPE)
                    .get();
            if (resp.getStatus() >= 400) {
                Esito esito = resp.readEntity(Esito.class);
                logError(className, debugInfo + ERROR_RESPONSE_STATUS_CODE + resp.getStatus() + ESITO + esito + "\n");
                throw new CosmoException(esito);
            } else {
                GenericType<File> fileType = new GenericType<File>() {
                };
                result = resp.readEntity(fileType);
                if (null != result) {
                    logDebug(className, "Recuperato file  : [" + result.getName() + "]");
                }
                logDebug(className, debugInfo + ERROR_RESPONSE_STATUS_CODE + resp.getStatus() + ESITO + result + "\n");
            }
        } catch (Exception e) {
            logError(className, e);
            throw new CosmoException(e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets documento.
     *
     * @param urlDocumento the url documento
     * @return the documento
     * @throws CosmoException the cosmo exception
     */
    public File getDocumento(@NotNull String urlDocumento) throws CosmoException {
        logBeginInfo(className, urlDocumento);
        File result = null;
        try {
            String url = apiEndpoint + urlDocumento;
            String debugInfo = URL + url + "]\nurlDocumento: [" + urlDocumento + "]\n";
            this.setConfKeys(CONF_KEYS_COSMO);
            Response resp = getBuilder(url)
                    //.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    //.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_OCTET_STREAM_TYPE)
                    .get();
            if (resp.getStatus() >= 400) {
                Esito esito = resp.readEntity(Esito.class);
                logError(className, debugInfo + ERROR_RESPONSE_STATUS_CODE + resp.getStatus() + ESITO + esito + "\n");
                throw new CosmoException(esito);
            } else {
                String fileName = getFilenameFromDisposition(resp.getHeaderString(HttpHeaders.CONTENT_DISPOSITION));

                if (StringUtils.isNotBlank(fileName) && !"noName".equals(fileName)) {
                    InputStream in = resp.readEntity(InputStream.class);
                    result = new File(fileName);
                    FileUtils.copyInputStreamToFile(in, result);
                } else {
                    String location = resp.getHeaderString(HttpHeaders.LOCATION);
                    result = getDocumentoFromLocation(location);
                }
                if (null != result) {
                    logDebug(className, "Recuperato file  : [" + result.getName() + "]");
                }
                logDebug(className, debugInfo + ERROR_RESPONSE_STATUS_CODE + resp.getStatus() + ESITO + result + "\n");
            }
        } catch (Exception e) {
            logError(className, e);
            throw new CosmoException(e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets documento from location.
     *
     * @param location the location
     * @return the documento from location
     * @throws CosmoException the cosmo exception
     */
    public File getDocumentoFromLocation(@NotNull String location) throws CosmoException {
        logBeginInfo(className, location);
        File result = null;
        try {
            String debugInfo = "location: [" + location + "]\n";
            this.setConfKeys(CONF_KEYS_COSMO);
            Response resp = getBuilder(location)
                    .get();
            if (resp.getStatus() >= 400) {
                String esito = resp.readEntity(String.class);
                logError(className, debugInfo + ERROR_RESPONSE_STATUS_CODE + resp.getStatus() + ESITO + esito + "\n");
                throw new CosmoException(esito);
            } else {
                String fileName = getFilenameFromDisposition(resp.getHeaderString(HttpHeaders.CONTENT_DISPOSITION));
                InputStream in = resp.readEntity(InputStream.class);
                result = new File(fileName);
                FileUtils.copyInputStreamToFile(in, result);
            }
        } catch (Exception e) {
            logError(className, e);
            throw new CosmoException(e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets filename.
     *
     * @param contentDisposition the content disposition
     * @return the filename
     */
    private String getFilenameFromDisposition(String contentDisposition) {
        logBegin(className);
        String fileName = null;
        if (StringUtils.isNotBlank(contentDisposition)) {
            fileName = contentDisposition.replaceFirst("(?i)^.*filename=\"?([^\"]+)\"?.*$", "$1");
        }
        logEnd(className);
        return StringUtils.isBlank(fileName) ? "noName" : fileName;
    }

    /**
     * Gets filename from header.
     *
     * @param url the url
     * @return the filename from header
     * @throws IOException the io exception
     */
    public String getFilenameFromHeader(URL url) throws IOException {
        logBegin(className);
        URLConnection urlConnection = url.openConnection();
        String fieldValue = urlConnection.getHeaderField(HttpHeaders.CONTENT_DISPOSITION);
        logEnd(className);
        return StringUtils.isNotBlank(fieldValue) && fieldValue.contains("filename=\"") ? fieldValue.substring(fieldValue.indexOf("filename=\"") + 10, fieldValue.length() - 1) : null;
    }


    /**** NOTIFICHE ****/

    /**
     * Il servizio permette a sistemi fruitori esterni di inserire notifiche per gli utenti di COSMO appartenenti ad un determinato ente.
     *
     * @param creaNotificaFruitoreRequest the crea notifica fruitore request
     * @return the crea notifica fruitore response
     * @throws CosmoException the cosmo exception
     */
    public CreaNotificaFruitoreResponse creaNotifica(@NotNull CreaNotificaFruitoreRequest creaNotificaFruitoreRequest) throws CosmoException {
        logBeginInfo(className, creaNotificaFruitoreRequest);
        CreaNotificaFruitoreResponse result = null;
        String api = "/notifiche";
        try {
            String url = apiEndpoint + api;
            Entity<CreaNotificaFruitoreRequest> entity = Entity.entity(creaNotificaFruitoreRequest, MediaType.APPLICATION_JSON);
            String debugInfo = URL + url + "]\ncreaNotificaFruitoreRequest: \n" + creaNotificaFruitoreRequest.toString() + "\n";
            this.setConfKeys(CONF_KEYS_COSMO);
            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);
            if (resp.getStatus() >= 400) {
                Esito esito = resp.readEntity(Esito.class);
                logError(className, debugInfo + ERROR_RESPONSE_STATUS_CODE + resp.getStatus() + ESITO + esito + "\n");
                throw new CosmoException(esito);
            } else {
                result = resp.readEntity(CreaNotificaFruitoreResponse.class);
                logDebug(className, debugInfo + ERROR_RESPONSE_STATUS_CODE + resp.getStatus() + ESITO + result + "\n");
            }
        } catch (Exception e) {
            logError(className, e);
            throw new CosmoException(e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**** PROCESSI ****/

    /**
     * Segnala invia segnale fruitore response.
     *
     * @param codPratica                  the cod pratica
     * @param inviaSegnaleFruitoreRequest the invia segnale fruitore request
     * @return the invia segnale fruitore response
     * @throws CosmoException the cosmo exception
     */
    public InviaSegnaleFruitoreResponse segnala(@NotNull String codPratica, @NotNull InviaSegnaleFruitoreRequest inviaSegnaleFruitoreRequest) throws CosmoException {
        logBeginInfo(className, inviaSegnaleFruitoreRequest);
        InviaSegnaleFruitoreResponse result = null;
        String api = PRATICHE + codPratica + "/segnala";
        try {
            String url = apiEndpoint + api;
            Entity<InviaSegnaleFruitoreRequest> entity = Entity.entity(inviaSegnaleFruitoreRequest, MediaType.APPLICATION_JSON);
            String debugInfo = URL + url + "]\ninviaSegnaleFruitoreRequest: \n" + inviaSegnaleFruitoreRequest.toString() + "\n";
            this.setConfKeys(CONF_KEYS_COSMO);
            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);
            if (resp.getStatus() >= 400) {
                String esito = resp.readEntity(String.class);
                logError(className, debugInfo + ERROR_RESPONSE_STATUS_CODE + resp.getStatus() + ESITO + esito + "\n");
                throw new CosmoException(esito);
            } else {
                result = resp.readEntity(InviaSegnaleFruitoreResponse.class);
                logDebug(className, debugInfo + ERROR_RESPONSE_STATUS_CODE + resp.getStatus() + ESITO + result + "\n");
            }
        } catch (Exception e) {
            logError(className, e);
            throw new CosmoException(e);
        } finally {
            logEnd(className);
        }
        return result;
    }


}