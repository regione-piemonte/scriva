/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.context.annotation.Scope;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.Feature;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.CatastoServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.FeatureCollection;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.FeatureMember;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.Geometry;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.FunzionarioDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoAreaOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoLineaOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoPointConverterDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoPuntoOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaAttoreDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaFiglioDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.UbicazioneOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.GeecoFeatureService;
import it.csi.scriva.scrivabesrv.business.be.service.IstanzaEventoService;
import it.csi.scriva.scrivabesrv.business.be.service.IstanzaService;
import it.csi.scriva.scrivabesrv.business.be.service.LimitiAmministrativiService;
import it.csi.scriva.scrivabesrv.business.be.service.OggettiIstanzaService;
import it.csi.scriva.scrivabesrv.business.be.service.OggettiService;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.CatastoComuneDTO;
import it.csi.scriva.scrivabesrv.dto.CatastoParticellaDTO;
import it.csi.scriva.scrivabesrv.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.FunzionarioDTO;
import it.csi.scriva.scrivabesrv.dto.GeecoFeature;
import it.csi.scriva.scrivabesrv.dto.GeoAreaOggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.GeoBaseOggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.GeoLineaOggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.GeoPuntoOggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaAttoreDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaUbicazioneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoUbicazioneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipologiaOggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.CRUDEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.GestioneOggettoIstanzaEnum;
import it.csi.scriva.scrivabesrv.util.manager.CatastoManager;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
/**
 * The type Geeco feature service.
 * TEST MERGE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GeecoFeatureServiceImpl extends BaseServiceImpl implements GeecoFeatureService {

    public static final String GEO_AREA_LIST_SIZE = "GeoAreaList size : ";
    private final String className = this.getClass().getSimpleName();

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String GEECO = "Geeco";
    private static final String IDENTIFICATIVO = "Identificativo";
    private static final String CODICE_UNIVOCO = "Codice univoco";
    private static final String DESCRIZIONE_GEOMETRIA = "Descrizione geometria";
    private static final String REPLACE_DES_GEOMETRIA = "[[" + DESCRIZIONE_GEOMETRIA + "]]";
    private static final String POLYGON = "POLYGON";
    private static final String MULTIPOINT = "MULTIPOINT";
    private static final String LINESTRING = "LINESTRING";

    private static final Long MASTER_DATA = 1L;
    private static final String MAP_ID_TIPOLOGIA = "map_id_tipologia";
    private static final String MAP_DEN_OGGETTO = "map_den_oggetto";
    private static final String MAP_DES_OGGETTO = "map_des_oggetto";
    private static final String ATTORE = "Attore";
    private static final String COMP_APP = "Componente applicativa";
    private static final String ID_OGG_PADRE = "Identificativo oggetto padre";
    private static final String ID_ISTANZA = "Id istanza";
    private static final String AGG_OGGETTO = "Aggiorna oggetto";
    private static final String GEST_OGGETTO = "Gestione oggetto istanza";
    private static final String GEO_STATO_TRUE = "G";
    private static final String GEO_PROVENIENZA = "GEO";
    private static final String GEOD_PROVENIENZA = "GEOD";

    private AttoreScriva attoreScriva;
    private FunzionarioDTO funzionario;
    private IstanzaAttoreDTO istanzaAttore;

    /**
     * The Funzionario dao.
     */
    @Autowired
    FunzionarioDAO funzionarioDAO;

    @Autowired
    GeoPointConverterDAO geoPointConverterDAO;

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
     * The Istanza attore dao.
     */
    @Autowired
    IstanzaAttoreDAO istanzaAttoreDAO;

    /**
     * The Oggetto istanza dao.
     */
    @Autowired
    OggettoIstanzaDAO oggettoIstanzaDAO;

    /**
     * The Oggetto istanza figlio dao.
     */
    @Autowired
    OggettoIstanzaFiglioDAO oggettoIstanzaFiglioDAO;
    
    /**
     * The Ubicazione oggetto istanza dao
     */
    @Autowired
    UbicazioneOggettoIstanzaDAO ubicazioneOggettoIstanzaDAO; 

    /**
     * The Istanza service.
     */
    @Autowired
    IstanzaService istanzaService;

    /**
     * The Istanza evento service.
     */
    @Autowired
    IstanzaEventoService istanzaEventoService;

    /**
     * The Limiti amministrativi service.
     */
    @Autowired
    LimitiAmministrativiService limitiAmministrativiService;

    /**
     * The Oggetti service.
     */
    @Autowired
    OggettiService oggettiService;

    /**
     * The Oggetti istanza service.
     */
    @Autowired
    OggettiIstanzaService oggettiIstanzaService;

    /**
     * The Catasto manager.
     */
    @Autowired
    CatastoManager catastoManager;
    
    @Autowired
    CatastoServiceHelper catastoServiceHelper;

    /**
     * Sets attore scriva.
     *
     * @param properties       the properties
     * @param idEditingSession the id editing session
     */
    public void setAttoreScriva(Map<String, Object> properties, String idEditingSession) {
        if (this.attoreScriva == null) {
            if (this.funzionario == null) {
                this.funzionario = getFunzionario(properties);
            }
            if (this.istanzaAttore == null) {
                this.istanzaAttore = getIstanzaAttore(properties);
            }
            String cfAttore = getCFAttore(this.istanzaAttore, this.funzionario);
            this.attoreScriva = new AttoreScriva();
            this.attoreScriva.setComponente((String) properties.get(COMP_APP));
            this.attoreScriva.setCodiceFiscale(StringUtils.isNotBlank(cfAttore) ? cfAttore : GEECO);
        }
    }

    /**
     * Insert feature for editing layer feature.
     *
     * @param geecoFeature the geeco feature
     * @return the feature
     */
    @Override
    public Feature insertFeatureForEditingLayerOLD(GeecoFeature geecoFeature) {
        logBeginInfo(className, geecoFeature);
        Map<String, Object> properties = geecoFeature.getFeature().getProperties();
        Long idOggettoIstanza = getLongValue(properties.get(CODICE_UNIVOCO));
        insertGeometry(geecoFeature, idOggettoIstanza);
        logEnd(className);
        return geecoFeature.getFeature();
    }

    /**
     * Update feature for editing layer feature.
     *
     * @param geecoFeature the geeco feature
     * @return the feature
     */
    @Override
    public Feature updateFeatureForEditingLayerOLD(GeecoFeature geecoFeature) {
        Map<String, Object> properties = geecoFeature.getFeature().getProperties();
        Long idOggettoIstanza = getLongValue(properties.get(CODICE_UNIVOCO));
        updateGeometry(geecoFeature, idOggettoIstanza);
        logEnd(className);
        return geecoFeature.getFeature();
    }

    /**
     * Delete feature for editing layer feature.
     *
     * @param geecoFeature the geeco feature
     */
    @Override
    public void deleteFeatureForEditingLayer(GeecoFeature geecoFeature) {
        logBeginInfo(className, geecoFeature);

        Long idOggettoIstanza = this.getIdOggettoIstanzaByIdGeometrico(geecoFeature.getIdGeometrico());
        geoOggettoIstanzaDAO.deleteOggettoIstanza(geecoFeature.getIdGeometrico());
        this.updateIndGeoStatoOggettoIstanza(idOggettoIstanza);

        logDebug(className, "Update flgGeoModificato");
        oggettoIstanzaDAO.updateFlgGeoModificatoByIdOggIst(idOggettoIstanza, Boolean.TRUE);
        

        logEnd(className);
    }

    /**
     * Gets long value.
     *
     * @param obj the obj
     * @return the long value
     */
    private Long getLongValue(Object obj) {
        if (obj != null) {
            if (obj instanceof Integer) {
                Integer integerObj = (Integer) obj;
                return integerObj.longValue();
            }
            if (obj instanceof Long) {
                return (Long) obj;
            }
            if (obj instanceof String && !"null".equalsIgnoreCase((String) obj)) {
                return Long.parseLong((String) obj);
            }
            logDebug(className, "Tipo di campo non gestito");
        }
        return 0L;
    }

    /**
     * Sanitize properties.
     *
     * @param properties the properties
     */
    private void sanitizeProperties(Map<String, Object> properties) {
        properties.remove("n");
        properties.remove("b");
    }

    /**
     * Gets id oggetto istanza by id geometrico.
     *
     * @param idGeometrico the id geometrico
     * @return the id oggetto istanza by id geometrico
     */
    private Long getIdOggettoIstanzaByIdGeometrico(Long idGeometrico) {
        logBeginInfo(className, idGeometrico);
        Long idOggettoIstanza = 0L;

        logDebug(className, "Ricerca id geometrico in GeoArea");
        List<GeoAreaOggettoIstanzaDTO> geoAreaList = geoAreaOggettoIstanzaDAO.loadGeoAreaOggettoIstanzaByIdGeometrico(idGeometrico);
        logDebug(className, GEO_AREA_LIST_SIZE + geoAreaList.size());
        if (CollectionUtils.isNotEmpty(geoAreaList)) {
            idOggettoIstanza = geoAreaList.get(0).getIdOggettoIstanza();
        }

        if (idOggettoIstanza == 0) {
            logDebug(className, "Ricerca id geometrico in GeoLinea");
            List<GeoLineaOggettoIstanzaDTO> geoLineaList = geoLineaOggettoIstanzaDAO.loadGeoLineaOggettoIstanzaByIdGeometrico(idGeometrico);
            logDebug(className, GEO_AREA_LIST_SIZE + geoLineaList.size());
            if (CollectionUtils.isNotEmpty(geoLineaList)) {
                idOggettoIstanza = geoLineaList.get(0).getIdOggettoIstanza();
            }
        }

        if (idOggettoIstanza == 0) {
            logDebug(className, "Ricerca id geometrico in GeoPunto");
            List<GeoPuntoOggettoIstanzaDTO> geoPuntoList = geoPuntoOggettoIstanzaDAO.loadGeoPuntoOggettoIstanzaByIdGeometrico(idGeometrico);
            logDebug(className, GEO_AREA_LIST_SIZE + geoPuntoList.size());
            if (CollectionUtils.isNotEmpty(geoPuntoList)) {
                idOggettoIstanza = geoPuntoList.get(0).getIdOggettoIstanza();
            }
        }

        logEnd(className);
        return idOggettoIstanza;
    }

    /**
     * Update ind geo stato oggetto istanza.
     *
     * @param idOggettoIstanza the id oggetto istanza
     */
    private void updateIndGeoStatoOggettoIstanza(Long idOggettoIstanza) {
        logBeginInfo(className, idOggettoIstanza);
        if (idOggettoIstanza != 0) {
            List<GeoAreaOggettoIstanzaDTO> geoAreaList = geoAreaOggettoIstanzaDAO.loadGeoAreaOggettoIstanzaByIdOggettoIstanza(idOggettoIstanza);
            if (null != geoAreaList && !geoAreaList.isEmpty()) {
                return;
            }

            List<GeoLineaOggettoIstanzaDTO> geoLineaList = geoLineaOggettoIstanzaDAO.loadGeoLineaOggettoIstanzaByIdOggettoIstanza(idOggettoIstanza);
            if (null != geoLineaList && !geoLineaList.isEmpty()) {
                return;
            }

            List<GeoPuntoOggettoIstanzaDTO> geoPuntoList = geoPuntoOggettoIstanzaDAO.loadGeoPuntoOggettoIstanzaByIdOggettoIstanza(idOggettoIstanza);
            if (null != geoPuntoList && !geoPuntoList.isEmpty()) {
                return;
            }
            oggettoIstanzaDAO.updateIndGeoStato(idOggettoIstanza, null);
        }
    }

    /**
     * Translate object to json string string.
     *
     * @param obj the obj
     * @return the string
     */
    private String translateObjectToJsonString(Object obj) {
        logBeginInfo(className, obj);
        String result = null;
        try {
            result = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /************************************************
     *
     * NUOVE FUNZIONI
     *
     ************************************************/

    /**
     * Insert feature for editing layer new feature.
     *
     * @param geecoFeature the geeco feature
     * @return the feature
     */
    @Override
    //public Feature insertFeatureForEditingLayer(GeecoFeature geecoFeature) {
    public Map<String, Object> insertFeatureForEditingLayer(GeecoFeature geecoFeature) {    
        logBeginInfo(className, geecoFeature);
        Map<String, Object> properties = geecoFeature.getFeature().getProperties();
        setAttoreScriva(properties, geecoFeature.getIdEditingSession());
        Long idOggettoIstanza = gestioneInserimentoAggiornamento(geecoFeature);
        insertGeometry(geecoFeature, idOggettoIstanza);
        
        //return geecoFeature.getFeature();
        Map<String, Object> result = new HashMap<>();
        result.put("feature", geecoFeature.getFeature());
        result.put("idOggettoIstanza", idOggettoIstanza);
        result.put("attore", this.attoreScriva);
        logEnd(className);
        return result;
    }

    /**
     * Update feature for editing layer feature.
     *
     * @param geecoFeature the geeco feature
     * @return the feature
     * 
     * metodo modificato per integrare l'update dell'istanza jira 1448
     */
    @Override
    public Map<String, Object> updateFeatureForEditingLayer(GeecoFeature geecoFeature) throws GenericException {
        logBeginInfo(className, geecoFeature);
        Map<String, Object> properties = geecoFeature.getFeature().getProperties();
        setAttoreScriva(properties, geecoFeature.getIdEditingSession());
        Long idOggettoIstanza = getLongValue(properties.get(CODICE_UNIVOCO));

         if(idOggettoIstanza == -999 ){
                Long generatedIdGeometrico = geecoFeature.getIdGeometrico();
                idOggettoIstanza = geoPuntoOggettoIstanzaDAO.loadGeoPuntoOggettoIstanzaByIdGeometrico(generatedIdGeometrico).get(0).getIdOggettoIstanza();
                properties.put(CODICE_UNIVOCO, idOggettoIstanza);
                geecoFeature.getFeature().setProperties((HashMap<String, Object>) properties);
                
        } 

        Long idOggettoIstanzaCalcolato = gestioneInserimentoAggiornamento(geecoFeature);
        if(idOggettoIstanza == null || idOggettoIstanzaCalcolato == null || idOggettoIstanzaCalcolato.longValue() != idOggettoIstanza.longValue()){
             ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
                        logError(className, "idOggettoIstanza [" + idOggettoIstanza + "]\n" + error);
                        logEnd(className);
                        throw new GenericException(error);
        }
        updateGeometry(geecoFeature, idOggettoIstanza);

        Map<String, Object> result = new HashMap<>();
        result.put("feature", geecoFeature.getFeature());
        result.put("idOggettoIstanza", idOggettoIstanza);
        result.put("attore", this.attoreScriva);
        logEnd(className);

        return result;
    }

    /**
     * Gestione inserimento aggiornamento.
     *
     * @param geecoFeature the geeco feature
     * @return the long
     */
    public Long gestioneInserimentoAggiornamento(GeecoFeature geecoFeature) {
        //WP3-3.1-USR-V010.3-US005_PresentazioneIstanza-OperaGIS_FO-BO 4.18	Algoritmo 12GEO – GEO-Salva Geometrie su SCRIVA 
        logBegin(className);
        try {
            Map<String, Object> properties = geecoFeature.getFeature().getProperties();
            Long idIstanza = getLongValue(properties.get(ID_ISTANZA));
            Long idOggettoIstanza = getLongValue(properties.get(CODICE_UNIVOCO));
            //idOggettoIstanza = (long) -999; // DA RIMUOVERE, SOLO PER TEST
            boolean aggiornaOggetto = Boolean.parseBoolean((String) properties.get(AGG_OGGETTO));
            String gestOggettoIstanza = (String) properties.get(GEST_OGGETTO);

            if (idOggettoIstanza > 0) 
            { //id oggetto istanza definito
                List<OggettoIstanzaUbicazioneExtendedDTO> oggettoIstanzaUbicazioneList = oggettiIstanzaService.loadOggettoIstanzaById(idOggettoIstanza);

                if (oggettoIstanzaUbicazioneList != null && !oggettoIstanzaUbicazioneList.isEmpty()) 
                {
                    gestioneAggiornamento(aggiornaOggetto, geecoFeature, oggettoIstanzaUbicazioneList.get(0));
                }
            } 
            else if (idOggettoIstanza == -999) 
            {
                if (GestioneOggettoIstanzaEnum.CHECK_OGG_IST.name().equalsIgnoreCase(gestOggettoIstanza)) 
                {
                    // il sistema deve controllare se per l’istanza di interesse sia già presente un oggetto istanza
                    List<OggettoIstanzaUbicazioneExtendedDTO> oggettoIstanzaUbicazioneList = oggettiIstanzaService.loadOggettoIstanzaByIdIstanza(idIstanza);

                    if (oggettoIstanzaUbicazioneList != null && !oggettoIstanzaUbicazioneList.isEmpty()) 
                    {
                        gestioneAggiornamento(aggiornaOggetto, geecoFeature, oggettoIstanzaUbicazioneList.get(0));
                        idOggettoIstanza = oggettoIstanzaUbicazioneList.get(0).getIdOggettoIstanza();
                    } 
                    else 
                    {
                        OggettoIstanzaUbicazioneExtendedDTO oggettoIstanzaUbicazione = gestioneInserimento(geecoFeature);
                        idOggettoIstanza = oggettoIstanzaUbicazione.getIdOggettoIstanza();
                    }
                } 
                else 
                {
                    OggettoIstanzaUbicazioneExtendedDTO oggettoIstanzaUbicazione = gestioneInserimento(geecoFeature);

                    idOggettoIstanza = oggettoIstanzaUbicazione.getIdOggettoIstanza();
                    Long idOggettoIstanzaPadre = getLongValue(properties.get(ID_OGG_PADRE));
                    
                    if (idOggettoIstanzaPadre != null && idOggettoIstanzaPadre > 0) 
                    {
                        oggettiIstanzaService.saveOggettoIstanzaFiglio(oggettoIstanzaUbicazione, idOggettoIstanzaPadre, this.attoreScriva.getCodiceFiscale());
                    }
                }
            }
            return idOggettoIstanza;
        } 
        finally 
        {
            logEnd(className);
        }
    }

    /**
     * Insert geometry.
     *
     * @param geecoFeature     the geeco feature
     * @param idOggettoIstanza the id oggetto istanza
     */
    @Override
    public void insertGeometry(GeecoFeature geecoFeature, Long idOggettoIstanza) {
        logBegin(className);
        manageGeometry(geecoFeature, idOggettoIstanza, CRUDEnum.CREATE);
        logEnd(className);
    }

    /**
     * Update geometry.
     *
     * @param geecoFeature     the geeco feature
     * @param idOggettoIstanza the id oggetto istanza
     */
    @Override
    public void updateGeometry(GeecoFeature geecoFeature, Long idOggettoIstanza) {
        logBegin(className);
        manageGeometry(geecoFeature, idOggettoIstanza, CRUDEnum.UPDATE);
        logEnd(className);
    }

    /**
     * Manage geometry.
     *
     * @param geecoFeature     the geeco feature
     * @param idOggettoIstanza the id oggetto istanza
     * @param action           the action
     */
    private void manageGeometry(GeecoFeature geecoFeature, Long idOggettoIstanza, CRUDEnum action) {
        logBegin(className);
        Map<String, Object> properties = geecoFeature.getFeature().getProperties();
        String desGeeco = getPropertyValue(properties, DESCRIZIONE_GEOMETRIA); // per VIA
        Long generatedIdGeometrico = geecoFeature.getIdGeometrico();
        Long idOggettoIstanzaDer; 
        setAttoreScriva(properties, geecoFeature.getIdEditingSession());

        if (StringUtils.isNotEmpty(desGeeco)) {
            properties.put(IDENTIFICATIVO, generatedIdGeometrico + "_" + desGeeco);
        } else {
            properties.put(IDENTIFICATIVO, generatedIdGeometrico);
        }
        sanitizeProperties(properties);
        geecoFeature.getFeature().setId(generatedIdGeometrico);


        // sostituisco il -999 che arriva dal template con l'idOggettoIstanza assegnato alla geometria (introdotto per Derivazioni) scriva 1634
        Object codiceUnivoco = properties.get(CODICE_UNIVOCO);
        if (codiceUnivoco != null) 
        {
            if (
                (codiceUnivoco instanceof String && "-999".equals(codiceUnivoco)) ||
                (codiceUnivoco instanceof Integer && ((Integer) codiceUnivoco) == -999) ||
                (codiceUnivoco instanceof Long && ((Long) codiceUnivoco) == -999L)
            ) {
                // intercettato il caso "-999" in qualsiasi formato
                logInfo(className, "idOggettoIstanza nel json: " + properties.get(CODICE_UNIVOCO));
                properties.put(CODICE_UNIVOCO, idOggettoIstanza);
                logInfo(className, "new idOggettoIstanza nel json: " + idOggettoIstanza);
            }
        }
        
   

        String jsonGeoFeature = translateObjectToJsonString(geecoFeature.getFeature());
        String jsonGeometry = translateObjectToJsonString(geecoFeature.getFeature().getGeometry());

        logDebug(className, "idVirtuale : " + geecoFeature.getIdVirtuale());
        logDebug(className, "idOggettoIstanza : " + idOggettoIstanza);
        logDebug(className, "desGeeco : " + (StringUtils.isEmpty(desGeeco) ? "null" : desGeeco));
        logDebug(className, "jsonGeoFeature : " + jsonGeoFeature);
        logDebug(className, "jsonGeometry : " + jsonGeometry);
        logDebug(className, "generatedIdGeometrico : " + generatedIdGeometrico);

        GeoBaseOggettoIstanzaDTO geoBaseOggettoIstanza = getGeoBaseOggettoIstanza(geecoFeature, desGeeco, jsonGeometry, generatedIdGeometrico, idOggettoIstanza, jsonGeoFeature);
        if (geoBaseOggettoIstanza != null) {
            switch (geecoFeature.getFeature().getGeometry().getType().toUpperCase()) {
                case MULTIPOINT:
                    if (CRUDEnum.CREATE.equals(action)) {
                        geoPuntoOggettoIstanzaDAO.saveGeoPuntoOggettoIstanza(new GeoPuntoOggettoIstanzaDTO(geoBaseOggettoIstanza));
                    } else {
                        geoPuntoOggettoIstanzaDAO.updateGeoPuntoOggettoIstanza(new GeoPuntoOggettoIstanzaDTO(geoBaseOggettoIstanza));
                    
                        
                    }
                    break;
                case POLYGON:
                    if (CRUDEnum.CREATE.equals(action)) {
                        geoAreaOggettoIstanzaDAO.saveGeoAreaOggettoIstanza(new GeoAreaOggettoIstanzaDTO(geoBaseOggettoIstanza));
                    } else {
                        geoAreaOggettoIstanzaDAO.updateGeoAreaOggettoIstanza(new GeoAreaOggettoIstanzaDTO(geoBaseOggettoIstanza));
                    }
                    break;
                case LINESTRING:
                    if (CRUDEnum.CREATE.equals(action)) {
                        geoLineaOggettoIstanzaDAO.saveGeoLineaOggettoIstanza(new GeoLineaOggettoIstanzaDTO(geoBaseOggettoIstanza));
                    } else {
                        geoLineaOggettoIstanzaDAO.updateGeoLineaOggettoIstanza(new GeoLineaOggettoIstanzaDTO(geoBaseOggettoIstanza));
                    }
                    break;
                default:
                    logDebug(className, "Tipo di geometria non gestito");
                    break;
            }

            logDebug(className, "Update indGeoStato");

            oggettoIstanzaDAO.updateIndGeoStato(idOggettoIstanza, GEO_STATO_TRUE);

            logDebug(className, "Update flgGeoModificato");
            oggettoIstanzaDAO.updateFlgGeoModificatoByIdOggIst(idOggettoIstanza, Boolean.TRUE);
        }
        logEnd(className);
    }

    /************************************************
     * FUNZIONI AGGIORNAMENTO
     ************************************************/

    /**
     * Gestione aggiornamento.
     *
     * @param aggiornaOggetto          the aggiorna oggetto
     * @param geecoFeature             the geeco feature
     * @param oggettoIstanzaUbicazione the oggetto istanza ubicazione
     */
    private void gestioneAggiornamento(boolean aggiornaOggetto, GeecoFeature geecoFeature, OggettoIstanzaUbicazioneExtendedDTO oggettoIstanzaUbicazione) {
        logBegin(className);
        Map<String, Object> properties = geecoFeature.getFeature().getProperties();
        if (Boolean.TRUE.equals(aggiornaOggetto)) {
        	/*
        	 * Parte nuova
        	 * */
        	
            Long idIstanza = getLongValue(properties.get(ID_ISTANZA));
            if(idIstanza == null || idIstanza == 0) {
	        	Long idOggettoIstanza = getLongValue(properties.get(CODICE_UNIVOCO));
	        	List<OggettoIstanzaExtendedDTO> oggettoIstanza = oggettoIstanzaDAO.loadOggettoIstanzaById(idOggettoIstanza);
	        	if(!oggettoIstanza.isEmpty() && oggettoIstanza != null)
	        		idIstanza = oggettoIstanza.get(0).getIdIstanza();
            }

        	/*
        	 * Fine parte nuova
        	 * */
        	
//            IstanzaExtendedDTO istanza = istanzaService.getIstanza(getLongValue(properties.get(ID_ISTANZA)));
        	IstanzaExtendedDTO istanza = istanzaService.getIstanza(idIstanza);
            String codStatoIstanza = istanza != null ? istanza.getStatoIstanza().getCodiceStatoIstanza() : null;
            if (istanza != null && istanzaEventoService.mustUpdateAnagraficaOggetto(codStatoIstanza, oggettoIstanzaUbicazione.getIdOggetto(), istanza.getIdIstanza())) {
                // aggiornare oggetto istanza e oggetto
                OggettoUbicazioneExtendedDTO oggettoUbicazione = this.updateOggetto(geecoFeature, oggettoIstanzaUbicazione);
                this.updateOggettoIstanza(geecoFeature, oggettoUbicazione);
            }
        } 
//        else {
//            // aggiornare solo ubicazione
//            this.updateUbicazione(geecoFeature, oggettoIstanzaUbicazione);
//        }
        logEnd(className);
    }

    /**
     * Update oggetto oggetto ubicazione extended dto.
     *
     * @param geecoFeature             the geeco feature
     * @param oggettoIstanzaUbicazione the oggetto istanza ubicazione
     * @return the oggetto ubicazione extended dto
     */
    private OggettoUbicazioneExtendedDTO updateOggetto(GeecoFeature geecoFeature, OggettoIstanzaUbicazioneExtendedDTO oggettoIstanzaUbicazione) {
        logBegin(className);
        OggettoUbicazioneExtendedDTO oggettoUbicazione = null;
        try {
            if (geecoFeature != null && oggettoIstanzaUbicazione != null) {
                oggettoUbicazione = getOggettoUbicazioneByIdOggetto(geecoFeature, oggettoIstanzaUbicazione.getIdOggetto());
                if (oggettoUbicazione != null) {
                    /*int result =*/ oggettiService.updateOggetto(oggettoUbicazione, this.attoreScriva);
                }
            }
        } finally {
            logEnd(className);
        }
        return oggettoUbicazione;
    }

    /**
     * Update ubicazione.
     *
     * @param geecoFeature             the geeco feature
     * @param oggettoIstanzaUbicazione the oggetto istanza ubicazione
     */
    public void updateUbicazione(GeecoFeature geecoFeature, OggettoIstanzaUbicazioneExtendedDTO oggettoIstanzaUbicazione) {
        logBegin(className);
        OggettoUbicazioneExtendedDTO oggettoUbicazione = null;
        try {
            oggettoUbicazione = getOggettoUbicazioneByIdOggetto(geecoFeature, oggettoIstanzaUbicazione.getIdOggetto());
            if (oggettoUbicazione != null && CollectionUtils.isNotEmpty(oggettoUbicazione.getUbicazioneOggetto())) {
                oggettiService.updateUbicazioniOggetto(oggettoIstanzaUbicazione.getIdOggetto(), oggettoUbicazione.getUbicazioneOggetto(), null);
                oggettoIstanzaUbicazione.setUbicazioneOggettoIstanza(getUbicazioniOggettoIstanza(oggettoUbicazione));
                //oggettiIstanzaService.updateUbicazioniOggettoIstanza(oggettoIstanzaUbicazione, attoreScriva, null);
                oggettiIstanzaService.updateUbicazioniOggettoIstanzaNEW(oggettoIstanzaUbicazione, attoreScriva, null);
            }
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update oggetto istanza.
     *
     * @param geecoFeature      the geeco feature
     * @param oggettoUbicazione the oggetto ubicazione
     * @return the oggetto istanza ubicazione extended dto
     */
    public OggettoIstanzaUbicazioneExtendedDTO updateOggettoIstanza(GeecoFeature geecoFeature, OggettoUbicazioneExtendedDTO oggettoUbicazione) {
        logBegin(className);
        OggettoIstanzaUbicazioneExtendedDTO oggettoIstanzaUbicazione = null;
        List<ComuneExtendedDTO> comuniList = new ArrayList<ComuneExtendedDTO>();
        try {
            if (geecoFeature != null && oggettoUbicazione != null) {
                Map<String, Object> properties = geecoFeature.getFeature().getProperties();
                Long idOggettoIstanzaPadre = getLongValue(properties.get(ID_OGG_PADRE));
                Long idOggettoIstanza = getLongValue(properties.get(CODICE_UNIVOCO));
                List<OggettoIstanzaUbicazioneExtendedDTO> oggettoIstanzaUbicazioneListOld = oggettiIstanzaService.loadOggettoIstanzaById(idOggettoIstanza);
                if (oggettoIstanzaUbicazioneListOld != null && !oggettoIstanzaUbicazioneListOld.isEmpty()) {
                    oggettoIstanzaUbicazione = getOggettoIstanzaUbicazione(oggettoUbicazione, oggettoIstanzaUbicazioneListOld.get(0), null, idOggettoIstanzaPadre);
                    
                    try {
                        comuniList = getComuneListFromGeometry(geecoFeature.getFeature());
                    	} catch (IOException e) {
                    	} catch (GenericException e) {
                    }

                    oggettiIstanzaService.updateOggettoIstanzaWithComuni(oggettoIstanzaUbicazione, this.attoreScriva, comuniList);
                    //oggettiIstanzaService.updateOggettoIstanza(oggettoIstanzaUbicazione, this.attoreScriva);
                }
            }
        } finally {
            logEnd(className);
        }
        return oggettoIstanzaUbicazione;
    }


    /************************************************
     * FUNZIONI INSERIMENTO
     ************************************************/

    /**
     * Gestione inserimento.
     *
     * @param geecoFeature the geeco feature
     * @return the oggetto istanza ubicazione extended dto
     */
    public OggettoIstanzaUbicazioneExtendedDTO gestioneInserimento(GeecoFeature geecoFeature) {
        logBegin(className);
        try {
            OggettoUbicazioneExtendedDTO oggettoUbicazione = saveOggetto(geecoFeature);
            return saveOggettoIstanza(oggettoUbicazione, geecoFeature);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets geo base oggetto istanza.
     *
     * @param geecoFeature          the geeco feature
     * @param desGeeco              the des geeco
     * @param jsonGeometry          the json geometry
     * @param generatedIdGeometrico the generated id geometrico
     * @param idOggettoIstanza      the id oggetto istanza
     * @param jsonGeoFeature        the json geo feature
     * @return the geo base oggetto istanza
     */
    private GeoBaseOggettoIstanzaDTO getGeoBaseOggettoIstanza(GeecoFeature geecoFeature, String desGeeco, String jsonGeometry, Long generatedIdGeometrico, Long idOggettoIstanza, String jsonGeoFeature) {
        GeoBaseOggettoIstanzaDTO geoBaseOggettoIstanza = null;
        Timestamp now = Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime());
        switch (geecoFeature.getFeature().getGeometry().getType().toUpperCase(Locale.ROOT)) {
            case MULTIPOINT:
            case LINESTRING:
            case POLYGON:
                geoBaseOggettoIstanza = new GeoBaseOggettoIstanzaDTO();
                geoBaseOggettoIstanza.setDesGeeco(desGeeco);
                geoBaseOggettoIstanza.setGeometry(jsonGeometry);
                geoBaseOggettoIstanza.setIdGeometrico(generatedIdGeometrico);
                geoBaseOggettoIstanza.setIdOggettoIstanza(idOggettoIstanza);
                geoBaseOggettoIstanza.setIdVirtuale(geecoFeature.getIdVirtuale());
                geoBaseOggettoIstanza.setJsonGeoFeature(jsonGeoFeature);
                geoBaseOggettoIstanza.setGestDataIns(now);
                geoBaseOggettoIstanza.setGestAttoreIns(this.attoreScriva.getCodiceFiscale());
                geoBaseOggettoIstanza.setGestDataUpd(now);
                geoBaseOggettoIstanza.setGestAttoreUpd(this.attoreScriva.getCodiceFiscale());
                break;
            default:
                logDebug(className, "Tipo di geometria non gestito");
                break;
        }
        return geoBaseOggettoIstanza;
    }

    /**
     * Save oggetto.
     *
     * @param geecoFeature the geeco feature
     * @return the oggetto ubicazione extended dto
     */
    private OggettoUbicazioneExtendedDTO saveOggetto(GeecoFeature geecoFeature) {
        logBegin(className);
        OggettoUbicazioneExtendedDTO oggettoUbicazione = getOggettoUbicazione(geecoFeature, null);
        if (oggettoUbicazione != null) {
            Long idOggetto = oggettiService.saveOggetto(oggettoUbicazione, this.attoreScriva);
            oggettoUbicazione.setIdOggetto(idOggetto);
        }
        logEnd(className);
        return oggettoUbicazione;
    }

    /**
     * Save oggetto istanza oggetto istanza ubicazione extended dto.
     *
     * @param oggettoUbicazione the oggetto ubicazione
     * @return the oggetto istanza ubicazione extended dto
     */
    private OggettoIstanzaUbicazioneExtendedDTO saveOggettoIstanza(OggettoUbicazioneExtendedDTO oggettoUbicazione, GeecoFeature geecoFeature) {
        logBegin(className);
        OggettoIstanzaUbicazioneExtendedDTO oggettoIstanzaUbicazione = getOggettoIstanzaUbicazione(oggettoUbicazione, null, null, null);
        if (oggettoIstanzaUbicazione != null) {
            Map<String, Object> properties = geecoFeature.getFeature().getProperties();
            Long idOggettoIstanzaPadre = getLongValue(properties.get(ID_OGG_PADRE));
            if (idOggettoIstanzaPadre != null) {
                int indLivello = oggettiIstanzaService.getIndLivelloByIdPadre(idOggettoIstanzaPadre);
                oggettoIstanzaUbicazione.setIndLivello(indLivello + 1);
            }
            Long idOggettoIstanza = oggettiIstanzaService.saveOggettoIstanza(oggettoIstanzaUbicazione, this.attoreScriva);
            oggettoIstanzaUbicazione.setIdOggettoIstanza(idOggettoIstanza);
        }
        logEnd(className);
        return oggettoIstanzaUbicazione;
    }

    /**
     * Gets oggetto ubicazione.
     *
     * @param geecoFeature the geeco feature
     * @param idOggetto    the id oggetto
     * @return the oggetto ubicazione
     */
    private OggettoUbicazioneExtendedDTO getOggettoUbicazioneByIdOggetto(GeecoFeature geecoFeature, Long idOggetto) {
        logBegin(className);
        OggettoUbicazioneExtendedDTO oggettoUbicazione = null;
        List<OggettoUbicazioneExtendedDTO> oggettoUbicazioneList = idOggetto != null ? oggettiService.loadOggettoById(idOggetto) : null;
        oggettoUbicazione = getOggettoUbicazione(geecoFeature, oggettoUbicazioneList != null && !oggettoUbicazioneList.isEmpty() ? oggettoUbicazioneList.get(0) : null);
        logEnd(className);
        return oggettoUbicazione;
    }

    /**
     * Gets oggetto ubicazione.
     *
     * @param geecoFeature         the geeco feature
     * @param oggettoUbicazioneOld the oggetto ubicazione old
     * @return the oggetto istanza
     */
    private OggettoUbicazioneExtendedDTO getOggettoUbicazione(GeecoFeature geecoFeature, OggettoUbicazioneExtendedDTO oggettoUbicazioneOld) {
        logBegin(className);
        OggettoUbicazioneExtendedDTO oggettoUbicazione = null;
        if (geecoFeature != null && geecoFeature.getFeature() != null && geecoFeature.getFeature().getProperties() != null && !geecoFeature.getFeature().getProperties().isEmpty()) {
            Map<String, Object> properties = geecoFeature.getFeature().getProperties();
            String cfAttore = this.attoreScriva.getCodiceFiscale();

            oggettoUbicazione = new OggettoUbicazioneExtendedDTO();
            TipologiaOggettoExtendedDTO tipologiaOggetto = new TipologiaOggettoExtendedDTO();
            tipologiaOggetto.setIdTipologiaOggetto(getLongValue(properties.get(MAP_ID_TIPOLOGIA)));
            oggettoUbicazione.setTipologiaOggetto(tipologiaOggetto);
            oggettoUbicazione.setIdMasterdata(MASTER_DATA);
            oggettoUbicazione.setIdMasterdataOrigine(MASTER_DATA);
            oggettoUbicazione.setDenOggetto(getPropertyValue(properties, MAP_DEN_OGGETTO));
            oggettoUbicazione.setDesOggetto(getPropertyValue(properties, MAP_DES_OGGETTO));
        	/*
        	 * Parte nuova
        	 * */
            Long idIstanza = getLongValue(properties.get(ID_ISTANZA));
            if(idIstanza == null || idIstanza == 0) {
	        	Long idOggettoIstanza = getLongValue(properties.get(CODICE_UNIVOCO));
	        	List<OggettoIstanzaExtendedDTO> oggettoIstanza = oggettoIstanzaDAO.loadOggettoIstanzaById(idOggettoIstanza);
	        	if(!oggettoIstanza.isEmpty() && oggettoIstanza != null)
	        		idIstanza = oggettoIstanza.get(0).getIdIstanza();
            }
        		
        	/*
        	 * Fine parte nuova
        	 * */
            
            //oggettoUbicazione.setIdIstanzaAggiornamento(getLongValue(properties.get(ID_ISTANZA)));
        	oggettoUbicazione.setIdIstanzaAggiornamento(idIstanza);
            oggettoUbicazione.setGestAttoreIns(cfAttore);
            oggettoUbicazione.setGestAttoreUpd(cfAttore);
            oggettoUbicazione.setIdFunzionario(this.funzionario != null ? this.funzionario.getIdFunzionario() : null);
            if (oggettoUbicazioneOld != null) {
                oggettoUbicazione.setTipologiaOggetto(oggettoUbicazione.getTipologiaOggetto().getIdTipologiaOggetto() != null ? oggettoUbicazione.getTipologiaOggetto() : oggettoUbicazioneOld.getTipologiaOggetto());
                oggettoUbicazione.setDenOggetto(oggettoUbicazione.getDenOggetto() != null ? oggettoUbicazione.getDenOggetto() : oggettoUbicazioneOld.getDenOggetto());
                oggettoUbicazione.setDesOggetto(oggettoUbicazione.getDesOggetto() != null ? oggettoUbicazione.getDesOggetto() : oggettoUbicazioneOld.getDesOggetto());
                oggettoUbicazione.setIdIstanzaAggiornamento(oggettoUbicazione.getIdIstanzaAggiornamento() != null ? oggettoUbicazione.getIdIstanzaAggiornamento() : oggettoUbicazioneOld.getIdIstanzaAggiornamento());
                oggettoUbicazione.setIdFunzionario(oggettoUbicazione.getIdFunzionario() != null ? oggettoUbicazione.getIdFunzionario() : oggettoUbicazioneOld.getIdFunzionario());

                oggettoUbicazione.setIdOggetto(oggettoUbicazioneOld.getIdOggetto());
                oggettoUbicazione.setStatoOggetto(oggettoUbicazioneOld.getStatoOggetto());
                oggettoUbicazione.setCodScriva(oggettoUbicazioneOld.getCodScriva());
                oggettoUbicazione.setCoordinataX(oggettoUbicazioneOld.getCoordinataX());
                oggettoUbicazione.setCoordinataY(oggettoUbicazioneOld.getCoordinataY());
                oggettoUbicazione.setCodOggettoFonte(oggettoUbicazioneOld.getCodOggettoFonte());
                oggettoUbicazione.setGestDataIns(oggettoUbicazioneOld.getGestDataIns());
                oggettoUbicazione.setUbicazioneOggetto(this.getUbicazioniOggetto(oggettoUbicazioneOld.getIdOggetto(), geecoFeature.getFeature(), oggettoUbicazioneOld.getUbicazioneOggetto(), cfAttore));
                
            } else {
                oggettoUbicazione.setUbicazioneOggetto(this.getUbicazioniOggetto(null, geecoFeature.getFeature(), null, cfAttore));               
            }
        }
        logEnd(className);
        return oggettoUbicazione;
    }

    /**
     * Gets ubicazioni.
     *
     * @param feature  the feature
     * @param cfAttore the cf attore
     * @return the ubicazioni
     */
    private List<UbicazioneOggettoExtendedDTO> getUbicazioniOggetto(Long idOggetto, Feature feature, List<UbicazioneOggettoExtendedDTO> ubicazioneOggettoOldList, String cfAttore) {
        logBegin(className);
        List<UbicazioneOggettoExtendedDTO> ubicazioneOggettoList = new ArrayList<>();
        try {
            if (feature != null) {
               //List<ComuneExtendedDTO> comuneList = getComuneListFromFeature(feature);
                              
                // Non uso il metodo di sopra ma il nuovo getComuneListFromGeometry(feature) dove da feature estraggo la geometria 
                List<ComuneExtendedDTO> comuneList = getComuneListFromGeometry(feature);
                List<ComuneExtendedDTO> comuniToDeleteList = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(comuneList)) {
                    if (CollectionUtils.isNotEmpty(ubicazioneOggettoOldList)) {
                        // Lista codici istat delle ubicazioni inserite in precedenza
                        List<String> codIstatComuneOldList = ubicazioneOggettoOldList.stream()
                                .map(UbicazioneOggettoExtendedDTO::getComune)
                                .map(ComuneExtendedDTO::getCodIstatComune)
                                .collect(Collectors.toList());
                        
                        comuniToDeleteList = getComuniToDeleteList(comuneList, ubicazioneOggettoOldList);
                        
                        
                        // Ridefinizione dei comuni ricavati dalle feature eliminando quelli inseriti in precedenza
                        comuneList = comuneList.stream()
                                .filter(comune -> !codIstatComuneOldList.contains(comune.getCodIstatComune()))
                                .collect(Collectors.toList());
                        //ubicazioneOggettoList.addAll(ubicazioneOggettoOldList);
                        
                    }
                    
                    }
                for (ComuneExtendedDTO comune : comuneList) {
                    UbicazioneOggettoExtendedDTO ubicazioneOggetto = new UbicazioneOggettoExtendedDTO();
                    ubicazioneOggetto.setIdOggetto(idOggetto);
                    ubicazioneOggetto.setComune(comune);
                    ubicazioneOggetto.setGestAttoreIns(cfAttore);
                    ubicazioneOggetto.setGestAttoreUpd(cfAttore);
                    ubicazioneOggetto.setIndGeoProvenienza(GEO_PROVENIENZA);
                    ubicazioneOggettoList.add(ubicazioneOggetto);
            }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return ubicazioneOggettoList;
    }

    
    /**
     * Updates ubicazioni oggetto istanza.
     *
     * @param feature  the feature
     * @param cfAttore the cf attore
     * @param idIstanza the id istanza
     * @param idOggetto the id oggetto
     */
    private List<ComuneExtendedDTO> getComuniToDeleteList(List<ComuneExtendedDTO> comuneList, List<UbicazioneOggettoExtendedDTO> ubicazioneOggettoOldList) {
        logBegin(className);
        List<ComuneExtendedDTO> comuniToDeleteList = new ArrayList<>();
        try {
                if (CollectionUtils.isNotEmpty(comuneList)) {
                    if (CollectionUtils.isNotEmpty(ubicazioneOggettoOldList)) {

                    	 
                    	 List<String> codIstatComuneNewList = comuneList.stream()
                                 .map(ComuneExtendedDTO::getCodIstatComune)
                                 .collect(Collectors.toList());
                        
                    	 comuniToDeleteList = ubicazioneOggettoOldList.stream()
                    			 .filter(c -> !codIstatComuneNewList.contains(c.getComune().getCodIstatComune()))
                    			 .map(UbicazioneOggettoExtendedDTO::getComune)
                    			 .collect(Collectors.toList());
                    	 

                      
                    }                 
                }

        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        
        return comuniToDeleteList;
        
    }
    
    /**
     * Gets oggetto istanza ubicazione.
     *
     * @param oggetto               the oggetto
     * @param oggettoIstanzaOld     the oggetto istanza old
     * @param idIstanzaAttore       the id istanza attore
     * @param idOggettoIstanzaPadre the id oggetto istanza padre
     * @return the oggetto istanza
     */
    private OggettoIstanzaUbicazioneExtendedDTO getOggettoIstanzaUbicazione(OggettoUbicazioneExtendedDTO oggetto, OggettoIstanzaUbicazioneExtendedDTO oggettoIstanzaOld, Long idIstanzaAttore, Long idOggettoIstanzaPadre) {
        logBegin(className);
        OggettoIstanzaUbicazioneExtendedDTO oggettoIstanzaUbicazione = null;
        if (oggetto != null) {
            oggettoIstanzaUbicazione = new OggettoIstanzaUbicazioneExtendedDTO();
            oggettoIstanzaUbicazione.setIdOggetto(oggetto.getIdOggetto());
            oggettoIstanzaUbicazione.setIdIstanza(oggetto.getIdIstanzaAggiornamento());
            oggettoIstanzaUbicazione.setTipologiaOggetto(oggetto.getTipologiaOggetto());
            oggettoIstanzaUbicazione.setIndGeoStato(GEO_STATO_TRUE);
            oggettoIstanzaUbicazione.setDenOggetto(oggetto.getDenOggetto());
            oggettoIstanzaUbicazione.setDesOggetto(oggetto.getDesOggetto());
            oggettoIstanzaUbicazione.setIdMasterdataOrigine(oggetto.getIdMasterdataOrigine());
            oggettoIstanzaUbicazione.setIdMasterdata(oggetto.getIdMasterdata());
            oggettoIstanzaUbicazione.setFlgGeoModificato(Boolean.TRUE);
            oggettoIstanzaUbicazione.setGestAttoreIns(oggetto.getGestAttoreIns());
            oggettoIstanzaUbicazione.setGestAttoreUpd(oggetto.getGestAttoreUpd());
            oggettoIstanzaUbicazione.setIdFunzionario(oggetto.getIdFunzionario());
            oggettoIstanzaUbicazione.setIdIstanzaAttore(idIstanzaAttore);
            oggettoIstanzaUbicazione.setIndLivello(oggettiIstanzaService.getIndLivelloByIdPadre(idOggettoIstanzaPadre)); // perche non viene preso il livello oggettoIstanzaOld?
            oggettoIstanzaUbicazione.setUbicazioneOggettoIstanza(getUbicazioniOggettoIstanza(oggetto));
            if (oggettoIstanzaOld != null) {
                oggettoIstanzaUbicazione.setIdOggettoIstanza(oggettoIstanzaOld.getIdOggettoIstanza());
                oggettoIstanzaUbicazione.setCoordinataX(oggettoIstanzaOld.getCoordinataX());
                oggettoIstanzaUbicazione.setCoordinataY(oggettoIstanzaOld.getCoordinataY());
                oggettoIstanzaUbicazione.setCodOggettoFonte(oggettoIstanzaOld.getCodOggettoFonte());
                oggettoIstanzaUbicazione.setCodUtenza(oggettoIstanzaOld.getCodUtenza());
                oggettoIstanzaUbicazione.setNoteAttoPrecedente(oggettoIstanzaOld.getNoteAttoPrecedente());
                oggettoIstanzaUbicazione.setOggettoIstanzaNatura2000List(oggettoIstanzaOld.getOggettoIstanzaNatura2000List());
                oggettoIstanzaUbicazione.setOggettoIstanzaAreaProtettaList(oggettoIstanzaOld.getOggettoIstanzaAreaProtettaList());
                oggettoIstanzaUbicazione.setOggettoIstanzaVincoloAutorizzaList(oggettoIstanzaOld.getOggettoIstanzaVincoloAutorizzaList());
            }
        }
        logEnd(className);
        return oggettoIstanzaUbicazione;
    }

    /**
     * Gets ubicazioni oggetto istanza.
     *
     * @param oggetto the oggetto
     * @return the ubicazioni oggetto istanza
     */
    private List<UbicazioneOggettoIstanzaExtendedDTO> getUbicazioniOggettoIstanza(OggettoUbicazioneExtendedDTO oggetto) {
        logBegin(className);
        List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaList = new ArrayList<>();
        if (oggetto != null && CollectionUtils.isNotEmpty(oggetto.getUbicazioneOggetto())) {
            UbicazioneOggettoIstanzaExtendedDTO ubicazioneOggettoIstanza;
            for (UbicazioneOggettoExtendedDTO ubicazioneOggetto : oggetto.getUbicazioneOggetto()) {
                ubicazioneOggettoIstanza = new UbicazioneOggettoIstanzaExtendedDTO();
                ubicazioneOggettoIstanza.setComune(ubicazioneOggetto.getComune());
                ubicazioneOggettoIstanza.setIndGeoProvenienza(ubicazioneOggetto.getIndGeoProvenienza());
                ubicazioneOggettoIstanza.setGestAttoreIns(ubicazioneOggetto.getGestAttoreIns());
                ubicazioneOggettoIstanza.setGestAttoreUpd(ubicazioneOggetto.getGestAttoreUpd());
                ubicazioneOggettoIstanzaList.add(ubicazioneOggettoIstanza);
            }
        }
        logEnd(className);
        return ubicazioneOggettoIstanzaList;
    }

    /**
     * Get funzionario funzionario dto.
     *
     * @param properties the properties
     * @return the funzionario dto
     */
    private FunzionarioDTO getFunzionario(Map<String, Object> properties) {
        logBegin(className);
        FunzionarioDTO funz = null;
        String gestUidAttore = getPropertyValue(properties, ATTORE);
        String componenteApp = getPropertyValue(properties, COMP_APP);
        if (StringUtils.isNotBlank(gestUidAttore) && ComponenteAppEnum.BO.name().equalsIgnoreCase(componenteApp)) {
            funz = funzionarioDAO.loadFunzionarioByUid(gestUidAttore);
        }
        logEnd(className);
        return funz;
    }

    /**
     * Get istanza attore istanza attore dto.
     *
     * @param properties the properties
     * @return the istanza attore dto
     */
    private IstanzaAttoreDTO getIstanzaAttore(Map<String, Object> properties) {
        logBegin(className);
        IstanzaAttoreDTO iAttore = null;
        String gestUidAttore = (String) properties.get(ATTORE);
        String componenteApp = (String) properties.get(COMP_APP);
        if (StringUtils.isNotBlank(gestUidAttore) && ComponenteAppEnum.FO.name().equalsIgnoreCase(componenteApp)) {
            iAttore = istanzaAttoreDAO.getIstanzaAttoreByUid(gestUidAttore);
        }
        logEnd(className);
        return iAttore;
    }

    /**
     * Get cf attore string.
     *
     * @param istanzaAttore the istanza attore
     * @param funzionario   the funzionario
     * @return the string
     */
    private String getCFAttore(IstanzaAttoreDTO istanzaAttore, FunzionarioDTO funzionario) {
        logBegin(className);
        String cfAttore = istanzaAttore != null ? istanzaAttore.getCfAttore() : null;
        cfAttore = StringUtils.isBlank(cfAttore) && funzionario != null ? funzionario.getCfFunzionario() : cfAttore;
        logEnd(className);
        return cfAttore;
    }

    /**
     * Gets den des oggetto.
     *
     * @param properties the properties
     * @param property   the property
     * @return the den des oggetto
     */
    private String getPropertyValue(Map<String, Object> properties, String property) {
        logBegin(className);
        String propertyValue = (String) properties.get(property);
        logEnd(className);
        return REPLACE_DES_GEOMETRIA.equalsIgnoreCase(propertyValue) ? (String) properties.get(DESCRIZIONE_GEOMETRIA) : propertyValue;
    }

    /**
     * Gets comune list from feature.
     *
     * @param feature the feature
     * @return the comune list from feature
     * @throws IOException      the io exception
     * @throws GenericException the generic exception
     */
    private List<ComuneExtendedDTO> getComuneListFromFeature(Feature feature) throws IOException, GenericException {
        logBegin(className);
        List<ComuneExtendedDTO> comuneList = new ArrayList<>();
        Geometry oldGeometry = feature.getGeometry();
        // convert multipoint in polygon
        if (feature.getGeometry() != null &&
                (MULTIPOINT.equalsIgnoreCase(feature.getGeometry().getType()) || LINESTRING.equalsIgnoreCase(feature.getGeometry().getType()))
        ) {
            ObjectMapper objectMapper = new ObjectMapper();
            String multiPointGeoJSON = objectMapper.writeValueAsString(feature.getGeometry());
            String polygonGeoJSON = geoPointConverterDAO.convertGeometryToPolygon(multiPointGeoJSON);
            feature.setGeometry(objectMapper.readValue(polygonGeoJSON, Geometry.class));
        }
        List<CatastoParticellaDTO> catastoParticellaList = catastoManager.loadParticelleIntersecantiGeometria(null, null, feature);
        if (catastoParticellaList != null && !catastoParticellaList.isEmpty()) {
            List<String> codiciIstatList = catastoParticellaList.stream()
                    .map(CatastoParticellaDTO::getComune)
                    .map(CatastoComuneDTO::getCodComIstat)
                    .collect(Collectors.toList());
            comuneList = limitiAmministrativiService.loadComuni(codiciIstatList);
        }
        feature.setGeometry(oldGeometry);
        logEnd(className);
        return comuneList;
    }
    
    /**
     * Gets comune list from feature.
     *
     * @param feature the feature
     * @return the comune list from feature
     * @throws IOException      the io exception
     * @throws GenericException the generic exception
     */
    private List<ComuneExtendedDTO> getComuneListFromGeometry(Feature feature) throws IOException, GenericException {
        logBegin(className);
        List<ComuneExtendedDTO> comuneList = new ArrayList<>();
        List<String> codiciIstatList = new ArrayList<>();
        Geometry oldGeometry = feature.getGeometry();
        String polygonGeoJSON = null;
        String multiPointGeoJSON = null;
        String polygon = null;
        String combinedJson = null;
        ObjectMapper objectMapper = new ObjectMapper();
        
        Feature feats = new Feature();
        
        //Cerco le precedenti geometrie su db
        List<String> geometrie = null;
        String updatedJsonString = null;
        Map<String, Object> properties = feature.getProperties();
        Long idOggettoIstanza = getLongValue(properties.get(CODICE_UNIVOCO));
        if(idOggettoIstanza > 0) {
        	geometrie = geoAreaOggettoIstanzaDAO.loadCoordinateGeoAreeByOggettoIstanzaForComuni(idOggettoIstanza);       	
        }
        
        // convert multipoint in polygon
        if (feature.getGeometry() != null && (MULTIPOINT.equalsIgnoreCase(feature.getGeometry().getType()) || LINESTRING.equalsIgnoreCase(feature.getGeometry().getType()))) {
            
            multiPointGeoJSON = objectMapper.writeValueAsString(feature.getGeometry());
            polygonGeoJSON = geoPointConverterDAO.convertGeometryToPolygon(multiPointGeoJSON);
            
            if(geometrie != null && !geometrie.isEmpty()) {
	            combinedJson = combinePolygons(polygonGeoJSON, geometrie);
	            feats.setGeometry(objectMapper.readValue(combinedJson, Geometry.class));
            } else {
            	feats.setGeometry(objectMapper.readValue(polygonGeoJSON, Geometry.class));
            }            	

        }
        

        if(feats.getGeometry() == null) {          
             if(geometrie != null && !geometrie.isEmpty()) {
            	polygon = objectMapper.writeValueAsString(feature.getGeometry()); 
 	            combinedJson = combinePolygons(polygon, geometrie);
 	            feats.setGeometry(objectMapper.readValue(combinedJson, Geometry.class));
             } else {
            	 feats = feature;
             }
        	
        }
        	
                
        //NUOVA CHIAMATA
        FeatureCollection resp = new FeatureCollection();
        try {
            resp = catastoServiceHelper.getComuniFromGeometria(null, null, feats.getGeometry());
        } catch (JAXBException e) {
            logError(className, e);
        }
        //OTTENGO UNA LISTA DI COMUNE E CODICI ISTAT
        //CON UN CICLO FOR POPOLO I DUE CAMPI DELL'OGGETTO COMUNEEXTENDEDDTO E LI SALVO IN UNA LIST
        
        for(FeatureMember member : resp.getFeatureMembers()) {
                String comuneNom = member.getComune().getComune_nom();
                String comuneIst = member.getComune().getComune_ist();
                ComuneExtendedDTO singoloComune = new ComuneExtendedDTO();
                singoloComune.setDenomComune(comuneNom);
                singoloComune.setCodIstatComune(comuneIst);
                codiciIstatList.add(comuneIst);
                comuneList.add(singoloComune);
        }
        
        comuneList = limitiAmministrativiService.loadComuni(codiciIstatList);
        
        
        //FINE NUOVA CHIAMATA
        feature.setGeometry(oldGeometry);
        logEnd(className);
        return comuneList;
    }

    private String convertGeometry(String xmlString) {
    	
    	String jsonString = null;
    	try {
    		
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new ByteArrayInputStream(xmlString.getBytes()));
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("gml:coordinates");
            ObjectMapper mapper = new ObjectMapper();

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Element element = (Element) nList.item(temp);
                String coordinates = element.getTextContent().trim();
                String[] coordinatePairs = coordinates.split(" ");
                
                ArrayNode coordinatesArray = mapper.createArrayNode();
                for (String pair : coordinatePairs) {
                    String[] coords = pair.split(",");
                    ArrayNode point = mapper.createArrayNode();
                    point.add(Double.parseDouble(coords[0]));
                    point.add(Double.parseDouble(coords[1]));
                    coordinatesArray.add(point);
                }

                ObjectNode geoJson = mapper.createObjectNode();
                geoJson.put("type", "MultiPoint");
                geoJson.set("coordinates", coordinatesArray);

                jsonString = mapper.writeValueAsString(geoJson);
                
            }
        } catch (Exception e) {
            
        }
    	return jsonString;
    }
    
    private static String combinePolygons(String existingPolygonJson, List<String> jsonList) {

        try {
        	ObjectMapper mapper = new ObjectMapper();
            JsonNode existingRootNode = mapper.readTree(existingPolygonJson);
            ArrayNode existingCoordinatesArray = (ArrayNode) existingRootNode.path("coordinates");

            for (String jsonString : jsonList) {
                JsonNode rootNode = mapper.readTree(jsonString);
                ArrayNode coordinatesArray = (ArrayNode) rootNode.path("coordinates");

                for (JsonNode polygon : coordinatesArray) {
                    existingCoordinatesArray.add(polygon);
                }
            }

            ((ObjectNode) existingRootNode).set("coordinates", existingCoordinatesArray);
            return mapper.writeValueAsString(existingRootNode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static <T> T convertInstanceOfObject(Object o) {
        try {
            return (T) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    //TODO Funzione cancellazione ubicazione oggetto e oggetto-istanza dati gli id dei comuni estratti da tutte le geometrie che siano inseriti con ind_geo_provenienza = 'GEO'


}