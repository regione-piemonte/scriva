/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.scriva.scrivabesrv.business.be.SaveFeatureApi;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.Feature;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoAreaOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoLineaOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoPuntoOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.GeecoFeatureService;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.GeecoFeature;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
// Add import for GenericException if it exists in your project
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
/**
 * The type Save feature api.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SaveFeatureApiImpl extends BaseApiServiceImpl implements SaveFeatureApi {

    private final String className = this.getClass().getSimpleName();

    private static final String GEECO = "Geeco";

    private static final String IDENTIFICATIVO = "Identificativo";
    private static final String CODICE_UNIVOCO = "Codice univoco";
    private static final String DESCRIZIONE_GEOMETRIA = "Descrizione geometria";
    private static final String POLYGON = "Polygon";
    private static final String MULTIPOINT = "MultiPoint";
    private static final String LINESTRING = "LineString";

    private static final String GEO_STATO_TRUE = "G";

    /**
     * The Geo punto oggetto istanza dao.
     */
    @Autowired
    GeoPuntoOggettoIstanzaDAO geoPuntoOggettoIstanzaDAO;

    /**
     * The Geo linea oggetto istanza dao.
     */
    @Autowired
    GeoLineaOggettoIstanzaDAO geoLineaOggettoIstanzaDAO;

    /**
     * The Geo area oggetto istanza dao.
     */
    @Autowired
    GeoAreaOggettoIstanzaDAO geoAreaOggettoIstanzaDAO;

    /**
     * The Geo oggetto istanza dao.
     */
    @Autowired
    GeoOggettoIstanzaDAO geoOggettoIstanzaDAO;

    /**
     * The Oggetto istanza dao.
     */
    @Autowired
    OggettoIstanzaDAO oggettoIstanzaDAO;

    /**
     * The Geeco feature service.
     */
    @Autowired
    GeecoFeatureService geecoFeatureService;

    /**
     * @param environment      environment
     * @param layerId          layerId
     * @param idEditingSession idEditingSession
     * @param feature          feature
     * @param securityContext  securityContext
     * @param httpHeaders      httpHeaders
     * @return Response
     */
    @Override
    public Response insertFeatureForEditingLayer(String environment, String layerId, String idEditingSession,
                                                 Feature feature, SecurityContext securityContext, HttpHeaders httpHeaders) {
        //log parametri in ingresso
        String inputParam = "environment [" + environment + "] - layerId [" + layerId + "] - idEditingSession [" + idEditingSession + "] - feature " + feature;

        logBeginInfo(className, (Object) inputParam);

        ObjectMapper mapper = new ObjectMapper();
        try {
            logInfo(className, inputParam);
            String jsonFeat = mapper.writeValueAsString(feature);
            logInfo(className, jsonFeat);
        } catch (Exception e) {
            logError(className, e);
        }

        // Recupero id geometrico dalla sequence
        Long generatedIdGeometrico = geoOggettoIstanzaDAO.getNextIdGeometrico();
        //geecoFeatureService.insertFeatureForEditingLayerOLD(getGeecoFeature(layerId, generatedIdGeometrico, idEditingSession, feature));
        
        // nuova gestione GEECO
        Map<String, Object> idOggettoIstanzaAndFeature = geecoFeatureService.insertFeatureForEditingLayer(getGeecoFeature(layerId, generatedIdGeometrico, idEditingSession, feature));     
       
        // aggiornamento timestamp istanza e attore
        Long idOggettoIstanza = (Long) idOggettoIstanzaAndFeature.get("idOggettoIstanza");
        AttoreScriva attoreScriva = (AttoreScriva) idOggettoIstanzaAndFeature.get("attore");
        this.attoreScriva = attoreScriva; // setta l'attorescriva della superclasse (perche risulta definito un attoreScriva anche a livello della classe geecoFeatureService :(  TODO: da sistemare!!!!)
        Long idOggettoIstanzaFinal = idOggettoIstanza;
        OggettoIstanzaDTO OggettoIstanza = oggettoIstanzaDAO.findByPK(idOggettoIstanzaFinal);
        updateIstanzaPraticaTimestampAttore(OggettoIstanza.getIdIstanza(), this.attoreScriva);    

        logEnd(className);
        //
        return Response.ok(feature).build();
    }

    /**
     * @param environment      environment
     * @param layerId          layerId
     * @param featureId        featureId
     * @param idEditingSession idEditingSession
     * @param feature          feature
     * @param securityContext  securityContext
     * @param httpHeaders      httpHeaders
     * @return Response
     */
    @Override
    public Response updateFeatureForEditingLayer(String environment, String layerId, String featureId,
                                                 String idEditingSession, Feature feature, SecurityContext securityContext, HttpHeaders httpHeaders) {
        String inputParam = "Parametro in input environment [" + environment + "] - layerId [" + layerId + "] - idEditingSession [" + idEditingSession + "] - feature " + feature;
        logBeginInfo(className, inputParam);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> idOggettoIstanzaAndFeature = null;
        try {
            logInfo(className, inputParam);
            String jsonFeat = mapper.writeValueAsString(feature);
            logInfo(className, jsonFeat);
        } catch (Exception e) {
            logError(className, e);
        }
        
        try
        {
            idOggettoIstanzaAndFeature = geecoFeatureService.updateFeatureForEditingLayer(getGeecoFeature(layerId, Long.parseLong(featureId), idEditingSession, feature));
        }
      
        catch (GenericException e) 
        {
            logError(className, e);
            return Response.serverError().entity(e.getError()).status(Integer.parseInt(e.getError().getStatus())).build();
        }
        catch (Exception e) 
        {
            logError(className, e);
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
       
            return Response.serverError().entity(error).status(500).build();
        }


        Long idOggettoIstanza = (Long) idOggettoIstanzaAndFeature.get("idOggettoIstanza");

        AttoreScriva attoreScriva = (AttoreScriva) idOggettoIstanzaAndFeature.get("attore");

        this.attoreScriva = attoreScriva; // setta l'attorescriva della superclasse (perche risulta definito un attoreScriva anche a livello della classe geecoFeatureService :(  TODO: da sistemare!!!!)
        Long idOggettoIstanzaFinal = idOggettoIstanza;
        OggettoIstanzaDTO OggettoIstanza = oggettoIstanzaDAO.findByPK(idOggettoIstanzaFinal);
        updateIstanzaPraticaTimestampAttore(OggettoIstanza.getIdIstanza(), this.attoreScriva);
        logEnd(className);
        return Response.ok(feature).build();
    }

    /**
     * @param environment      environment
     * @param layerId          layerId
     * @param featureId        featureId
     * @param idEditingSession idEditingSession
     * @param securityContext  securityContext
     * @param httpHeaders      httpHeaders
     * @return Response
     */
    @Override
    public Response deleteFeatureForEditingLayer(String environment, String layerId, String featureId, String idEditingSession,
                                                 SecurityContext securityContext, HttpHeaders httpHeaders) {

        String inputParam = "Parametro in input environment [" + environment + "] - layerId [" + layerId + "] - idEditingSession [" + idEditingSession + "]";
        logBeginInfo(className, inputParam);
        geecoFeatureService.deleteFeatureForEditingLayer(getGeecoFeature(layerId, Long.parseLong(featureId), idEditingSession, null));
        logEnd(className);
        return Response.ok().build();
    }

    /**
     * Gets geeco feature.
     *
     * @param layerId          the layer id
     * @param idGeometrico     the id geometrico
     * @param idEditingSession the id editing session
     * @param feature          the feature
     * @return the geeco feature
     */
    private GeecoFeature getGeecoFeature(String layerId, Long idGeometrico, String idEditingSession, Feature feature) {
        logBegin(className);
        GeecoFeature geecoFeature = new GeecoFeature();
        geecoFeature.setIdEditingSession(idEditingSession);
        geecoFeature.setIdVirtuale(Long.parseLong(layerId));
        geecoFeature.setIdGeometrico(idGeometrico);
        geecoFeature.setFeature(feature);
        logEnd(className);
        return geecoFeature;
    }

}