/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.catasto;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.Municipality;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.ParcelFeatureGeoJSON;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.ParcelsFeatureCollectionGeoJSON;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.PostRequestJSON;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.Section;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.SheetFeatureGeoJSON;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.SheetsFeatureCollectionGeoJSON;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.Error;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.FeatureCollection;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.Geometry;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.LineStringGeometry;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.MultiPointGeometry;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.PolygonGeometry;
import it.csi.scriva.scrivabesrv.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.util.Constants;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

// import di prova
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.w3c.dom.Node;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * The type Catasto service helper.
 *
 * @author CSI PIEMONTE
 */
public class CatastoServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    /**
     * The constant OFFSET.
     */
    public static final String OFFSET = "offset";
    /**
     * The constant LOGGER.
     */
    private static final String APPLICATION_GEO_JSON = "application/geo+json";
    /**
     * The constant LIMIT.
     */
    public static final String LIMIT = "limit";
    /**
     * The constant SECTIONS.
     */
    public static final String SECTIONS = "/sections/";
    /**
     * The constant OMIT_GEOMETRY.
     */
    public static final String OMIT_GEOMETRY = "omit_geometry";
    /**
     * The constant SHEETS.
     */
    public static final String SHEETS = "/sheets/";
    /**
     * The constant ERRORE_NELLA_CHIAMATA_AL_SERVIZIO.
     */
    public static final String ERRORE_NELLA_CHIAMATA_AL_SERVIZIO = "Errore nella chiamata al servizio [ ";
    /**
     * The constant COLLECTIONS_MUNICIPALITIES.
     */
    public static final String COLLECTIONS_MUNICIPALITIES = "/collections/municipalities/";

    private final String endPoint;
    private static final List<String> CONF_KEYS_APIMAN = Arrays.asList(Constants.CONF_KEY_APIMAN_CONSUMER_KEY,
            Constants.CONF_KEY_APIMAN_CONSUMER_SECRET);

    private final String serviceUrlGeoServ;

    /**
     * Instantiates a new Catasto service helper.
     *
     * @param endPoint   the end point
     * @param serviceUrl the service url
     */
    public CatastoServiceHelper(String endPoint, String serviceUrl, String serviceUrlGeoServ) {
        super();
        this.tokenUrl = endPoint + "/token";
        this.endPoint = endPoint + serviceUrl;
        this.serviceUrlGeoServ = serviceUrlGeoServ;
    }

    /**
     * Sets conf keys.
     *
     * @throws JsonProcessingException the json processing exception
     */
    public void setConfKeys() throws JsonProcessingException {
        this.configurazioneList = this.configurazioneList == null || this.configurazioneList.isEmpty()
                ? configurazioneDAO.loadConfigByKeyList(CONF_KEYS_APIMAN)
                : this.configurazioneList;
        if (this.configurazioneList != null && !this.configurazioneList.isEmpty()) {
            this.consumerKey = configurazioneList.getOrDefault(Constants.CONF_KEY_APIMAN_CONSUMER_KEY, null);
            this.consumerSecret = configurazioneList.getOrDefault(Constants.CONF_KEY_APIMAN_CONSUMER_SECRET, null);
        }
    }

    /**
     * Gets end point.
     *
     * @return the end point
     */
    public String getEndPoint() {
        return endPoint;
    }

    /**
     * Gets service url geo serv.
     *
     * @return service url geo serv
     */
    public String getServiceUrlGeoServ() {
        return serviceUrlGeoServ;
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
     * Sets consumer key.
     *
     * @param consumerKey the consumer key
     */
    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
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
     * Sets consumer secret.
     *
     * @param consumerSecret the consumer secret
     */
    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    /*
     * BLOCCO COMUNI
     */

    /**
     * Gets the paginated municipalities list for the ones identified by the given
     * candidate name.
     *
     * @param xRequestID    the x request id
     * @param xForwardedFor the x forwarded for
     * @param candidateName the candidate name
     * @param expansionMode the expansion mode
     * @param epsg          the epsg
     * @param limit         the limit
     * @param offset        the offset
     * @return the paginated municipalities list
     * @throws GenericException the generic exception
     */
    public List<Municipality> getComuni(String xRequestID, String xForwardedFor, String candidateName,
            String expansionMode, Integer epsg, Integer limit, Integer offset) throws GenericException {
        logBegin(className);
        List<Municipality> result = null;
        String api = "/collections/municipalities";
        StringBuilder url = new StringBuilder(this.endPoint).append(api);

        Map<String, Object> queryParams = new HashMap<>();
        if (StringUtils.isNotBlank(candidateName)) {
            queryParams.put("candidateName", candidateName);
        }
        if (StringUtils.isNotBlank(expansionMode)) {
            queryParams.put("expansionMode", expansionMode);
        }
        if (null != epsg) {
            queryParams.put("epsg", epsg);
        }
        if (null != limit) {
            queryParams.put(LIMIT, limit);
        }
        if (null != offset) {
            queryParams.put(OFFSET, offset);

        }

        logDebug(className, "url : [" + url + "]");
        try {
            Response resp = getBuilder(url.toString(), xRequestID, xForwardedFor, queryParams)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON).get();

            if (resp.getStatus() >= 400) {
                GenericType<Error> errGenericType = new GenericType<Error>() {
                };
                Error err = resp.readEntity(errGenericType);
                logError(className, err.getMessage());
                ErrorDTO error = new ErrorDTO(String.valueOf(resp.getStatus()), null, err.getMessage(), null, null);
                throw new GenericException(error);
            } else {
                GenericType<List<Municipality>> municipalities = new GenericType<List<Municipality>>() {
                };
                result = resp.readEntity(municipalities);
                logDebug(className, "Lista Comuni : " + result);
            }
        } catch (JsonProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + url + " ]");
        }
        logEnd(className);
        return result;
    }

    /*
     * BLOCCO SEZIONI
     */

    /**
     * Gets the paginated sections list for the municipality identified by its code
     * (either the ISTAT code or the Belfiore code). The list will have at least one
     * section.
     *
     * @param xRequestID    the x request id
     * @param xForwardedFor the x forwarded for
     * @param codiceComune  the codice comune
     * @param epsg          the epsg
     * @param limit         the limit
     * @param offset        the offset
     * @return the paginated sections list
     * @throws GenericException the generic exception
     */
    // PRIMA MODIFICA
    public List<Section> getSezioniPerComune(String xRequestID, String xForwardedFor, String codiceComune, Integer epsg,
            Integer limit, Integer offset) throws GenericException {
        logBegin(className);
        List<Section> result = new ArrayList<>();
        String api = COLLECTIONS_MUNICIPALITIES + codiceComune + "/sections";
        StringBuilder url = new StringBuilder(this.endPoint).append(api);

        boolean omitGeo = true;
        limit = (limit != null) ? limit : 100; // Inizializza limit se è nullo
        offset = (offset != null) ? offset : 0; // Inizializza offset se è nullo
        Map<String, Object> queryParams = new HashMap<>();
        if (null != epsg) {
            queryParams.put("epsg", epsg);
        }
        if (null != limit) {
            queryParams.put(LIMIT, limit);
        }
        if (null != offset) {
            queryParams.put(OFFSET, offset);
        }
        queryParams.put(OMIT_GEOMETRY, omitGeo);

        logDebug(className, "url : [" + url + "]");
        // try {
        // Response resp = getBuilder(url.toString(), xRequestID, xForwardedFor,
        // queryParams).header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON).get();
        //
        // if (resp.getStatus() >= 400) {
        // String errorMessage = resp.readEntity(String.class);
        // logError(className, errorMessage);
        // ErrorDTO error = new ErrorDTO(String.valueOf(resp.getStatus()), null,
        // errorMessage, null, null);
        // throw new GenericException(error);
        // } else {
        // result = resp.readEntity(new GenericType<List<Section>>() {
        // });
        // logDebug(className, "Lista Sezioni : " + result);
        // }
        // } catch (JsonProcessingException e) {
        // logError(className, e);
        // throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + url + "
        // ]");
        // }
        // logEnd(className);
        // return result;

        try {
            int totalElements = 0;
            int fetchedElements = 0;

            do {
                Response resp = getBuilder(url.toString(), xRequestID, xForwardedFor, queryParams)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON).get();

                if (resp.getStatus() >= 400) {
                    String errorMessage = resp.readEntity(String.class);
                    logError(className, errorMessage);
                    ErrorDTO error = new ErrorDTO(String.valueOf(resp.getStatus()), null, errorMessage, null, null);
                    throw new GenericException(error);
                } else {
                    String totalElementsString = resp.getHeaderString("X-Total-Elements");
                    totalElements = Integer.valueOf(totalElementsString);

                    List<Section> partialResult = resp.readEntity(new GenericType<List<Section>>() {
                    });
                    if (partialResult != null) {
                        result.addAll(partialResult);
                        fetchedElements += partialResult.size();
                    }

                    logDebug(className, "Fetched " + fetchedElements + " of " + totalElements + " elements.");

                    offset += limit;
                    queryParams.put(OFFSET, offset);
                }
            } while (fetchedElements < totalElements);

        } catch (JsonProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + url + " ]");
        }
        logEnd(className);
        return result;
    }

    /**
     * Gets Section detail.
     *
     * @param xRequestID    the x request id
     * @param xForwardedFor the x forwarded for
     * @param codiceComune  the codice comune
     * @param sectionCode   the section code
     * @param epsg          the epsg
     * @return the section detail
     * @throws GenericException the generic exception
     */
    public Section getSezione(String xRequestID, String xForwardedFor, String codiceComune, String sectionCode,
            Integer epsg) throws GenericException {
        logBegin(className);
        Section result = null;
        String api = COLLECTIONS_MUNICIPALITIES + codiceComune + SECTIONS + sectionCode;
        StringBuilder url = new StringBuilder(this.endPoint).append(api);

        Map<String, Object> queryParams = new HashMap<>();
        if (null != epsg) {
            queryParams.put("epsg", epsg);
        }

        logDebug(className, "url : [" + url + "]");
        try {
            Response resp = getBuilder(url.toString(), xRequestID, xForwardedFor, queryParams)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON).get();

            if (resp.getStatus() >= 400) {
                String errorMessage = resp.readEntity(String.class);
                logError(className, "SERVER EXCEPTION : " + errorMessage);
                ErrorDTO error = new ErrorDTO(String.valueOf(resp.getStatus()), null, errorMessage, null, null);
                throw new GenericException(error);
            } else {
                GenericType<Section> section = new GenericType<Section>() {
                };
                result = resp.readEntity(section);
                logDebug(className, "Sezione : " + result);
            }
        } catch (JsonProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + url + " ]");
        }
        logEnd(className);
        return result;
    }

    /*
     * BLOCCO FOGLI
     */

    /**
     * Gets the paginated sheets list for the municipality identified by its code
     * (either the ISTAT code or the Belfiore code) and its section code.
     *
     * @param xRequestID    the x request id
     * @param xForwardedFor the x forwarded for
     * @param codiceComune  the codice comune
     * @param codiceSezione the codice sezione
     * @param epsg          the epsg
     * @param limit         the limit
     * @param offset        the offset
     * @return the paginated sheets list
     * @throws GenericException the generic exception
     */
    // SECONDA MODIFICA
    // public SheetsFeatureCollectionGeoJSON getFogliPerComunePerSezione(String
    // xRequestID, String xForwardedFor, String codiceComune, String codiceSezione,
    // Integer epsg, Integer limit, Integer offset) throws GenericException {
    // logBegin(className);
    // SheetsFeatureCollectionGeoJSON result = null;
    // String api = COLLECTIONS_MUNICIPALITIES + codiceComune + SECTIONS +
    // codiceSezione + "/sheets";
    // StringBuilder url = new StringBuilder(this.endPoint).append(api);
    // boolean omitGeo = true;
    // limit = 100;
    // Map<String, Object> queryParams = new HashMap<>();
    // if (null != epsg) {
    // queryParams.put("epsg", epsg);
    // }
    // if (null != limit) {
    // queryParams.put(LIMIT, limit);
    // }
    // if (null != offset) {
    // queryParams.put(OFFSET, offset);
    // }
    //
    // queryParams.put(OMIT_GEOMETRY, omitGeo);
    //
    // logDebug(className, "url : [" + url + "]");
    // try {
    // Response resp = getBuilder(url.toString(), xRequestID, xForwardedFor,
    // queryParams).header(HttpHeaders.ACCEPT, APPLICATION_GEO_JSON).get();
    //
    // if (resp.getStatus() >= 400) {
    // String errorMessage = resp.readEntity(String.class);
    // logError(className, errorMessage);
    // ErrorDTO error = new ErrorDTO(String.valueOf(resp.getStatus()), null,
    // errorMessage, null, null);
    // throw new GenericException(error);
    // } else {
    //
    // String totalElementsString = resp.getHeaderString("X-Total-Elements");
    // int totalElements = Integer.valueOf(totalElementsString);
    //
    // if(totalElements <= limit) {
    // GenericType<SheetsFeatureCollectionGeoJSON> sheets = new
    // GenericType<SheetsFeatureCollectionGeoJSON>() {
    // };
    // result = resp.readEntity(sheets);
    // logDebug(className, "Lista fogli : " + result);
    // }
    // else {
    // offset = 100;
    // queryParams.put(OFFSET, offset);
    // resp = getBuilder(url.toString(), xRequestID, xForwardedFor,
    // queryParams).header(HttpHeaders.ACCEPT, APPLICATION_GEO_JSON).get();
    //
    // if (resp.getStatus() >= 400) {
    // String errorMessage = resp.readEntity(String.class);
    // logError(className, errorMessage);
    // ErrorDTO error = new ErrorDTO(String.valueOf(resp.getStatus()), null,
    // errorMessage, null, null);
    // throw new GenericException(error);
    // }
    // else {
    // GenericType<SheetsFeatureCollectionGeoJSON> sheets = new
    // GenericType<SheetsFeatureCollectionGeoJSON>() {
    // };
    // result = resp.readEntity(sheets);
    // logDebug(className, "Lista fogli : " + result);
    // }
    // }
    // }
    // } catch (JsonProcessingException e) {
    // logError(className, e);
    // throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + url + "
    // ]");
    // }
    // logEnd(className);
    // return result;
    // }

    public SheetsFeatureCollectionGeoJSON getFogliPerComunePerSezione(String xRequestID, String xForwardedFor,
            String codiceComune, String codiceSezione, Integer epsg, Integer limit, Integer offset)
            throws GenericException {
        logBegin(className);
        SheetsFeatureCollectionGeoJSON result = new SheetsFeatureCollectionGeoJSON();
        String api = COLLECTIONS_MUNICIPALITIES + codiceComune + SECTIONS + codiceSezione + "/sheets";
        StringBuilder url = new StringBuilder(this.endPoint).append(api);
        boolean omitGeo = true;
        limit = (limit != null) ? limit : 100; // Inizializza limit se è nullo
        offset = (offset != null) ? offset : 0; // Inizializza offset se è nullo
        Map<String, Object> queryParams = new HashMap<>();
        if (null != epsg) {
            queryParams.put("epsg", epsg);
        }
        if (null != limit) {
            queryParams.put(LIMIT, limit);
        }
        if (null != offset) {
            queryParams.put(OFFSET, offset);
        }

        queryParams.put(OMIT_GEOMETRY, omitGeo);

        logDebug(className, "url : [" + url + "]");
        try {
            int totalElements = 0;
            int fetchedElements = 0;

            do {
                Response resp = getBuilder(url.toString(), xRequestID, xForwardedFor, queryParams)
                        .header(HttpHeaders.ACCEPT, APPLICATION_GEO_JSON).get();

                if (resp.getStatus() >= 400) {
                    String errorMessage = resp.readEntity(String.class);
                    logError(className, errorMessage);
                    ErrorDTO error = new ErrorDTO(String.valueOf(resp.getStatus()), null, errorMessage, null, null);
                    throw new GenericException(error);
                } else {
                    String totalElementsString = resp.getHeaderString("X-Total-Elements");
                    totalElements = Integer.valueOf(totalElementsString);

                    GenericType<SheetsFeatureCollectionGeoJSON> sheets = new GenericType<SheetsFeatureCollectionGeoJSON>() {
                    };
                    SheetsFeatureCollectionGeoJSON partialResult = resp.readEntity(sheets);

                    if (partialResult != null && partialResult.getFeatures() != null) {
                        if (result.getFeatures() == null) {
                            result.setFeatures(new ArrayList<>());
                        }
                        result.getFeatures().addAll(partialResult.getFeatures());
                        fetchedElements += partialResult.getFeatures().size();
                    }

                    logDebug(className, "Fetched " + fetchedElements + " of " + totalElements + " elements.");

                    offset += limit;
                    queryParams.put(OFFSET, offset);
                }
            } while (fetchedElements < totalElements);

        } catch (JsonProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + url + " ]");
        }
        logEnd(className);
        return result;
    }

    /**
     * Gets Sheet detail by its number.
     *
     * @param xRequestID    the x request id
     * @param xForwardedFor the x forwarded for
     * @param codiceComune  the codice comune
     * @param codiceSezione the codice sezione
     * @param numeroFoglio  the numero foglio
     * @param epsg          the epsg
     * @return the sheet
     * @throws GenericException the generic exception
     */
    public SheetFeatureGeoJSON getFoglio(String xRequestID, String xForwardedFor, String codiceComune,
            String codiceSezione, String numeroFoglio, Integer epsg) throws GenericException {
        logBegin(className);
        SheetFeatureGeoJSON result = null;
        String api = COLLECTIONS_MUNICIPALITIES + codiceComune + SECTIONS + codiceSezione + SHEETS + numeroFoglio;
        StringBuilder url = new StringBuilder(this.endPoint).append(api);

        Map<String, Object> queryParams = new HashMap<>();
        if (null != epsg) {
            queryParams.put("epsg", epsg);
        }

        logDebug(className, "url : [" + url + "]");
        try {
            Response resp = getBuilder(url.toString(), xRequestID, xForwardedFor, queryParams)
                    .header(HttpHeaders.ACCEPT, APPLICATION_GEO_JSON).get();
            if (resp.getStatus() >= 400) {
                String errorMessage = resp.readEntity(String.class);
                logError(className, errorMessage);
                ErrorDTO error = new ErrorDTO(String.valueOf(resp.getStatus()), null, errorMessage, null, null);
                throw new GenericException(error);
            } else {
                GenericType<SheetFeatureGeoJSON> sheet = new GenericType<SheetFeatureGeoJSON>() {
                };
                result = resp.readEntity(sheet);
                logDebug(className, "Foglio : " + result);
            }
        } catch (JsonProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + url + " ]");
        }
        logEnd(className);
        return result;
    }

    /**
     * Gets the paginated sheets list intersecting the given geometry.
     *
     * @param xRequestID      the x request id
     * @param xForwardedFor   the x forwarded for
     * @param postRequestJSON the post request json
     * @return the paginated sheets list
     * @throws GenericException the generic exception
     */
    public SheetsFeatureCollectionGeoJSON getFogliIntersecantiGeometria(String xRequestID, String xForwardedFor,
            PostRequestJSON postRequestJSON) throws GenericException {
        logBegin(className);
        SheetsFeatureCollectionGeoJSON result = null;
        String api = "/collections/sheets/_intersects";
        StringBuilder url = new StringBuilder(this.endPoint).append(api);

        logDebug(className, "url : [" + url + "]");
        try {
            Entity<PostRequestJSON> entity = Entity.entity(postRequestJSON, MediaType.APPLICATION_JSON);

            Response resp = getBuilder(url.toString(), xRequestID, xForwardedFor, null)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON).post(entity);

            if (resp.getStatus() >= 400) {
                GenericType<Error> errGenericType = new GenericType<Error>() {
                };
                Error err = resp.readEntity(errGenericType);
                logError(className, err);
                ErrorDTO error = new ErrorDTO(String.valueOf(resp.getStatus()), null, err.getMessage(), null, null);
                throw new GenericException(error);
            } else {
                GenericType<SheetsFeatureCollectionGeoJSON> sheets = new GenericType<SheetsFeatureCollectionGeoJSON>() {
                };
                result = resp.readEntity(sheets);
                logDebug(className, "Fogli che intersecano la geometria data : " + result);
            }
        } catch (JsonProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + url + " ]");
        }
        logEnd(className);
        return result;
    }

    /**
     * Gets the paginated sheets list contained in the given geometry.
     *
     * @param xRequestID      the x request id
     * @param xForwardedFor   the x forwarded for
     * @param postRequestJSON the post request json
     * @return the paginated sheets list
     * @throws GenericException the generic exception
     */
    public SheetsFeatureCollectionGeoJSON getFogliContenutiGeometria(String xRequestID, String xForwardedFor,
            PostRequestJSON postRequestJSON) throws GenericException {
        logBegin(className);
        SheetsFeatureCollectionGeoJSON result = null;
        String api = "/collections/sheets/_contains";
        StringBuilder url = new StringBuilder(this.endPoint).append(api);

        logDebug(className, "url : [" + url + "]");
        try {
            Entity<PostRequestJSON> entity = Entity.entity(postRequestJSON, MediaType.APPLICATION_JSON);

            Response resp = getBuilder(url.toString(), xRequestID, xForwardedFor, null)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON).post(entity);

            if (resp.getStatus() >= 400) {
                GenericType<Error> errGenericType = new GenericType<Error>() {
                };
                Error err = resp.readEntity(errGenericType);
                logError(className, err.getMessage());
                ErrorDTO error = new ErrorDTO(String.valueOf(resp.getStatus()), null, err.getMessage(), null, null);
                throw new GenericException(error);
            } else {
                GenericType<SheetsFeatureCollectionGeoJSON> sheets = new GenericType<SheetsFeatureCollectionGeoJSON>() {
                };
                result = resp.readEntity(sheets);
                logDebug(className, "Fogli contenuti nella geometria data : " + result);
            }
        } catch (JsonProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + url + " ]");
        }
        logEnd(className);
        return result;
    }

    /*
     * BLOCCO PARTICELLE
     */

    /**
     * Gets the paginated parcels list for the municipality identified by its code
     * (either the ISTAT code or the Belfiore code), its section code and the sheet
     * number.
     *
     * @param xRequestID    the x request id
     * @param xForwardedFor the x forwarded for
     * @param codiceComune  the codice comune
     * @param codiceSezione the codice sezione
     * @param numeroFoglio  the numero foglio
     * @param epsg          the epsg
     * @param limit         the limit
     * @param offset        the offset
     * @return the paginated parcels list
     * @throws GenericException the generic exception
     */
    // TERZA MODIFICA
    // public ParcelsFeatureCollectionGeoJSON
    // getParticellePerFoglioPerSezionePerComune(String xRequestID, String
    // xForwardedFor, String codiceComune, String codiceSezione, String
    // numeroFoglio, Integer epsg, Integer limit, Integer offset) throws
    // GenericException {
    // logBegin(className);
    // ParcelsFeatureCollectionGeoJSON result = null;
    // String api = COLLECTIONS_MUNICIPALITIES + codiceComune + SECTIONS +
    // codiceSezione + SHEETS + numeroFoglio + "/parcels";
    // StringBuilder url = new StringBuilder(this.endPoint).append(api);
    // boolean omitGeo = true;
    // Map<String, Object> queryParams = new HashMap<>();
    // if (null != epsg) {
    // queryParams.put("epsg", epsg);
    // }
    // if (null != limit) {
    // queryParams.put(LIMIT, limit);
    // }
    // if (null != offset) {
    // queryParams.put(OFFSET, offset);
    // }
    //
    // queryParams.put(OMIT_GEOMETRY, omitGeo);
    //
    // logDebug(className, "url : [" + url + "]");
    // try {
    // Response resp = getBuilder(url.toString(), xRequestID, xForwardedFor,
    // queryParams).header(HttpHeaders.ACCEPT, APPLICATION_GEO_JSON).get();
    //
    // if (resp.getStatus() >= 400) {
    // String errorMessage = resp.readEntity(String.class);
    // logError(className, errorMessage);
    // ErrorDTO error = new ErrorDTO(String.valueOf(resp.getStatus()), null,
    // errorMessage, null, null);
    // throw new GenericException(error);
    // } else {
    // GenericType<ParcelsFeatureCollectionGeoJSON> parcels = new
    // GenericType<ParcelsFeatureCollectionGeoJSON>() {
    // };
    // result = resp.readEntity(parcels);
    // logDebug(className, "Lista Sezioni : " + result);
    // }
    // } catch (JsonProcessingException e) {
    // logError(className, e);
    // throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + url + "
    // ]");
    // }
    // logEnd(className);
    // return result;
    // }
    public ParcelsFeatureCollectionGeoJSON getParticellePerFoglioPerSezionePerComune(String xRequestID,
            String xForwardedFor, String codiceComune, String codiceSezione, String numeroFoglio, Integer epsg,
            Integer limit, Integer offset) throws GenericException {
        logBegin(className);
        ParcelsFeatureCollectionGeoJSON result = new ParcelsFeatureCollectionGeoJSON();
        String api = COLLECTIONS_MUNICIPALITIES + codiceComune + SECTIONS + codiceSezione + SHEETS + numeroFoglio
                + "/parcels";
        StringBuilder url = new StringBuilder(this.endPoint).append(api);
        boolean omitGeo = true;
        limit = (limit != null) ? limit : 100; // Inizializza limit se è nullo
        offset = (offset != null) ? offset : 0; // Inizializza offset se è nullo
        Map<String, Object> queryParams = new HashMap<>();
        if (null != epsg) {
            queryParams.put("epsg", epsg);
        }
        queryParams.put(LIMIT, limit);
        queryParams.put(OFFSET, offset);
        queryParams.put(OMIT_GEOMETRY, omitGeo);

        logDebug(className, "url : [" + url + "]");
        try {
            int totalElements = 0;
            int fetchedElements = 0;

            do {
                Response resp = getBuilder(url.toString(), xRequestID, xForwardedFor, queryParams)
                        .header(HttpHeaders.ACCEPT, APPLICATION_GEO_JSON).get();

                if (resp.getStatus() >= 400) {
                    String errorMessage = resp.readEntity(String.class);
                    logError(className, errorMessage);
                    ErrorDTO error = new ErrorDTO(String.valueOf(resp.getStatus()), null, errorMessage, null, null);
                    throw new GenericException(error);
                } else {
                    String totalElementsString = resp.getHeaderString("X-Total-Elements");
                    totalElements = Integer.valueOf(totalElementsString);

                    GenericType<ParcelsFeatureCollectionGeoJSON> parcels = new GenericType<ParcelsFeatureCollectionGeoJSON>() {
                    };
                    ParcelsFeatureCollectionGeoJSON partialResult = resp.readEntity(parcels);

                    if (partialResult != null && partialResult.getFeatures() != null) {
                        if (result.getFeatures() == null) {
                            result.setFeatures(new ArrayList<>());
                        }
                        result.getFeatures().addAll(partialResult.getFeatures());
                        fetchedElements += partialResult.getFeatures().size();
                    }

                    logDebug(className, "Fetched " + fetchedElements + " of " + totalElements + " elements.");

                    offset += limit;
                    queryParams.put(OFFSET, offset);
                }
            } while (fetchedElements < totalElements);

        } catch (JsonProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + url + " ]");
        }
        logEnd(className);
        return result;
    }

    /**
     * Gets Parcel by number.
     *
     * @param xRequestID       the x request id
     * @param xForwardedFor    the x forwarded for
     * @param codiceComune     the codice comune
     * @param codiceSezione    the codice sezione
     * @param numeroFoglio     the numero foglio
     * @param numeroParticella the numero particella
     * @param epsg             the epsg
     * @return the Parcel
     * @throws GenericException the generic exception
     */
    public ParcelFeatureGeoJSON getParticella(String xRequestID, String xForwardedFor, String codiceComune,
            String codiceSezione, String numeroFoglio, String numeroParticella, Integer epsg) throws GenericException {
        logBegin(className);
        ParcelFeatureGeoJSON result = null;
        String api = COLLECTIONS_MUNICIPALITIES + codiceComune + SECTIONS + codiceSezione + SHEETS + numeroFoglio
                + "parcels/" + numeroParticella;
        StringBuilder url = new StringBuilder(this.endPoint).append(api);

        Map<String, Object> queryParams = new HashMap<>();
        if (null != epsg) {
            queryParams.put("epsg", epsg);
        }
        logDebug(className, "url : [" + url + "]");
        try {
            Response resp = getBuilder(url.toString(), xRequestID, xForwardedFor, queryParams)
                    .header(HttpHeaders.ACCEPT, APPLICATION_GEO_JSON).get();

            if (resp.getStatus() >= 400) {
                String errorMessage = resp.readEntity(String.class);
                logError(className, errorMessage);
                ErrorDTO error = new ErrorDTO(String.valueOf(resp.getStatus()), null, errorMessage, null, null);
                throw new GenericException(error);
            } else {
                GenericType<ParcelFeatureGeoJSON> parcel = new GenericType<ParcelFeatureGeoJSON>() {
                };
                result = resp.readEntity(parcel);
                logDebug(className, "Lista Sezioni : " + result);
            }
        } catch (JsonProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + url + " ]");
        }
        logEnd(className);
        return result;
    }

    /**
     * Gets the paginated parcels list intersecting the given geometry.
     *
     * @param xRequestID      the x request id
     * @param xForwardedFor   the x forwarded for
     * @param postRequestJSON the post request json
     * @return the paginated parcels list
     * @throws GenericException the generic exception
     */
    public ParcelsFeatureCollectionGeoJSON getParticelleIntersecantiGeometria(String xRequestID, String xForwardedFor,
            PostRequestJSON postRequestJSON) throws GenericException {
        logBegin(className);
        ParcelsFeatureCollectionGeoJSON result = null;
        String api = "/collections/parcels/_intersects";
        StringBuilder url = new StringBuilder(this.endPoint).append(api);

        logDebug(className, "url : [" + url + "]");
        logDebug(className, "INPUT :\n" + postRequestJSON + "\n");
        try {
            Entity<PostRequestJSON> entity = Entity.entity(postRequestJSON, MediaType.APPLICATION_JSON);

            Response resp = getBuilder(url.toString(), xRequestID, xForwardedFor, null)
                    .header(HttpHeaders.ACCEPT, APPLICATION_GEO_JSON)
                    .post(entity);

            if (resp.getStatus() >= 400) {
                String errorMessage = resp.readEntity(String.class);
                logError(className, errorMessage);
                // force return 404
                ErrorDTO error = new ErrorDTO(String.valueOf("404"), null,
                        "Non sono state trovate particelle all'interno dell'area", null, null);
                throw new GenericException(error);
            } else {
                GenericType<ParcelsFeatureCollectionGeoJSON> sheets = new GenericType<ParcelsFeatureCollectionGeoJSON>() {
                };
                result = resp.readEntity(sheets);
                logDebug(className, "Particelle che intersecano la geometria data : " + result);
            }
        } catch (JsonProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + url + " ]");
        }
        logEnd(className);
        return result;
    }

    /**
     * Gets he paginated parcels list contained in the given geometry.
     *
     * @param xRequestID      the x request id
     * @param xForwardedFor   the x forwarded for
     * @param postRequestJSON the post request json
     * @return the paginated parcels list
     * @throws GenericException the generic exception
     */
    public ParcelsFeatureCollectionGeoJSON getParticelleContenuteGeometria(String xRequestID, String xForwardedFor,
            PostRequestJSON postRequestJSON) throws GenericException {
        logBegin(className);
        ParcelsFeatureCollectionGeoJSON result = null;
        String api = "/collections/parcels/_contains";
        StringBuilder url = new StringBuilder(this.endPoint).append(api);

        logDebug(className, "url : [" + url + "]");
        try {
            Entity<PostRequestJSON> entity = Entity.entity(postRequestJSON, MediaType.APPLICATION_JSON);

            Response resp = getBuilder(url.toString(), xRequestID, xForwardedFor, null)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON).post(entity);

            if (resp.getStatus() >= 400) {
                GenericType<Error> errGenericType = new GenericType<Error>() {
                };
                Error err = resp.readEntity(errGenericType);
                logError(className, err.getMessage());
                ErrorDTO error = new ErrorDTO(String.valueOf(resp.getStatus()), null, err.getMessage(), null, null);
                throw new GenericException(error);
            } else {
                GenericType<ParcelsFeatureCollectionGeoJSON> sheets = new GenericType<ParcelsFeatureCollectionGeoJSON>() {
                };
                result = resp.readEntity(sheets);
                logDebug(className, "Particelle contenute nella geometria data : " + result);
            }
        } catch (JsonProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + url + " ]");
        }
        logEnd(className);
        return result;
    }

    public FeatureCollection getComuniFromGeometria(String xRequestID, String xForwardedFor, Geometry geometry)
            throws GenericException, JAXBException {
        logBegin(className);

        FeatureCollection featureCollection = new FeatureCollection();
        String geometryType = geometry.getType();

        try {
            if ("MultiPoint".equals(geometryType)) {
                MultiPointGeometry geo = (MultiPointGeometry) geometry;
                List<List<BigDecimal>> coordinates = geo.getCoordinates();

                for (List<BigDecimal> point : coordinates) {
                    String spatialFilter = buildSpatialFilterPoint(Collections.singletonList(point));
                    FeatureCollection partial = callGeoService(spatialFilter, xRequestID, xForwardedFor);
                    addFeatureMembers(featureCollection, partial);
                }

            } else if ("LineString".equals(geometryType)) {
                LineStringGeometry geo = (LineStringGeometry) geometry;
                List<List<BigDecimal>> coordinates = geo.getCoordinates();

                for (List<BigDecimal> coord : coordinates) {
                    String spatialFilter = buildSpatialFilterLine(Collections.singletonList(coord));
                    FeatureCollection partial = callGeoService(spatialFilter, xRequestID, xForwardedFor);
                    addFeatureMembers(featureCollection, partial);
                }

            } else {
                PolygonGeometry geo = (PolygonGeometry) geometry;
                List<List<List<BigDecimal>>> polygons = geo.getCoordinates();

                for (List<List<BigDecimal>> polygon : polygons) {
                    String spatialFilter = buildSpatialFilterPolygon(Collections.singletonList(polygon));
                    FeatureCollection partial = callGeoService(spatialFilter, xRequestID, xForwardedFor);
                    addFeatureMembers(featureCollection, partial);
                }
            }

        } catch (Exception e) {
            logError(className, e);
        }
        logEnd(className);
        return featureCollection;
    }

    private static String buildSpatialFilterPoint(List<List<BigDecimal>> coordinates) {
        StringBuilder filterBuilder = new StringBuilder();
        filterBuilder.append("<ogc:Filter>");

        for (List<BigDecimal> coordinate : coordinates) {
            filterBuilder.append("<ogc:Contains>")
                    .append("<ogc:PropertyName>geometry</ogc:PropertyName>")
                    .append("<gml:Point srsName=\"urn:ogc:def:crs:EPSG::32632\">")
                    .append("<gml:coordinates>").append(coordinate.get(0)).append(" ").append(coordinate.get(1))
                    .append("</gml:coordinates>")
                    .append("</gml:Point>")
                    .append("</ogc:Contains>");
        }

        filterBuilder.append("</ogc:Filter>");
        return filterBuilder.toString();
    }

    private static String buildSpatialFilterLine(List<List<BigDecimal>> coordinates) {
        StringBuilder filterBuilder = new StringBuilder();

        filterBuilder.append("<ogc:Filter>");
        filterBuilder.append("<ogc:Or>");
        for (List<BigDecimal> coordinate : coordinates) {

            filterBuilder.append("<ogc:Intersects>")
                    .append("<ogc:PropertyName>geometry</ogc:PropertyName>")
                    .append("<gml:LineString srsName=\"urn:ogc:def:crs:EPSG::32632\">")
                    .append("<gml:posList>").append(coordinate.get(0)).append(" ").append(coordinate.get(1))
                    .append("</gml:posList>")
                    .append("</gml:posList>")
                    .append("</ogc:Intersects>");
        }
        filterBuilder.append("</ogc:Or>");
        filterBuilder.append("</ogc:Filter>");
        return filterBuilder.toString();
    }

    private static String buildSpatialFilterPolygon(List<List<List<BigDecimal>>> coordinates) {
        StringBuilder filterBuilder = new StringBuilder();

        filterBuilder.append("<ogc:Filter>");

        // Aggiungi il tag <ogc:Or> solo se ci sono più poligoni
        if (coordinates.size() > 1) {
            filterBuilder.append("<ogc:Or>");
        }

        for (List<List<BigDecimal>> polygon : coordinates) {
            filterBuilder.append("<ogc:Intersects>")
                    .append("<ogc:PropertyName>geometry</ogc:PropertyName>")
                    .append("<gml:Polygon srsName=\"urn:ogc:def:crs:EPSG::32632\">")
                    .append("<gml:exterior>")
                    .append("<gml:LinearRing>")
                    .append("<gml:posList>");

            for (List<BigDecimal> coordinate : polygon) {
                filterBuilder.append(coordinate.get(0)).append(" ").append(coordinate.get(1)).append(" ");
            }

            // Rimuove l'ultimo spazio aggiunto
            filterBuilder.setLength(filterBuilder.length() - 1);

            filterBuilder.append("</gml:posList>")
                    .append("</gml:LinearRing>")
                    .append("</gml:exterior>")
                    .append("</gml:Polygon>")
                    .append("</ogc:Intersects>");
        }

        // Chiudi il tag <ogc:Or> solo se è stato aperto
        if (coordinates.size() > 1) {
            filterBuilder.append("</ogc:Or>");
        }

        filterBuilder.append("</ogc:Filter>");
        return filterBuilder.toString();
    }

    /**
     * Gets builder.
     *
     * @param url           the url
     * @param xRequestID    the x request id
     * @param xForwardedFor the x forwarded for
     * @param queryParams   the query params
     * @return the builder
     * @throws JsonProcessingException the json processing exception
     */
    private Invocation.Builder getBuilder(String url, String xRequestID, String xForwardedFor,
            Map<String, Object> queryParams) throws JsonProcessingException {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(url);
        if (null != queryParams) {
            for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
                target = target.queryParam(entry.getKey(), entry.getValue());
            }
        }
        if (StringUtils.isBlank(this.consumerKey)) {
            this.getConsumerKey();
        }
        if (StringUtils.isBlank(this.consumerSecret)) {
            this.getConsumerSecret();
        }
        return target.request()
                .header(HttpHeaders.AUTHORIZATION,
                        "Bearer " + this.getToken(this.tokenUrl, this.consumerKey, this.consumerSecret))
                .header("X-Request-ID", xRequestID).header("X-Forwarded-For", xForwardedFor);
    }

    private FeatureCollection callGeoService(String spatialFilter, String xRequestID, String xForwardedFor)
            throws GenericException, JAXBException {

        StringBuilder url = new StringBuilder(this.serviceUrlGeoServ).append(spatialFilter);
        logDebug(className, "Chiamata URL: [" + url + "]");
        String resp;

        try {
            resp = getBuilder(url.toString(), xRequestID, xForwardedFor, null).get(String.class);
        } catch (ProcessingException | JsonProcessingException e) {
            logError(className, e);
            throw new GenericException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + url + " ]");
        }

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(FeatureCollection.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (FeatureCollection) unmarshaller.unmarshal(new StringReader(resp));
        } catch (JAXBException e) {
            logError(className, e);
            throw new GenericException("Errore nel parsing XML dalla risposta del servizio.");
        }
    }

    private void addFeatureMembers(FeatureCollection target, FeatureCollection source) {
        if (source != null && source.getFeatureMembers() != null) {
            if (target.getFeatureMembers() == null) {
                target.setFeatureMembers(new ArrayList<>());
            }
            target.getFeatureMembers().addAll(source.getFeatureMembers());
        }
    }

}