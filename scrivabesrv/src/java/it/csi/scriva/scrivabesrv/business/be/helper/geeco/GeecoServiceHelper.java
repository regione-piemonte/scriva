/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.geeco;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.CreaPraticaFruitoreRequest;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.Configuration;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.ConfigurationResponse;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.Error;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.GeecoDefaultProperty;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.ApiInfo;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.Feature;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.Features;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.Layer;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.QuitInfo;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.StartupInfo;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigGeecoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.dto.ConfigGeecoDTO;
import it.csi.scriva.scrivabesrv.dto.ConfigurazioneDTO;
import it.csi.scriva.scrivabesrv.dto.GeoOggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.TipologiaOggettoDTO;
import it.csi.scriva.scrivabesrv.util.Constants;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Geeco service helper.
 *
 * @author CSI PIEMONTE
 */
public class GeecoServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    private static final String PH_ID_OGGETTO_ISTANZA = "PH_ID_OGGETTO_ISTANZA";
    private static final String PH_DEN_OGGETTO = "PH_DEN_OGGETTO";
    private static final String PH_DES_OGGETTO = "PH_DES_OGGETTO";
    private static final String PH_ID_SOGGETTO = "PH_ID_SOGGETTO";
    private static final String PH_ISTANZA = "PH_ISTANZA";
    private static final String PH_ATTORE = "PH_ATTORE";
    private static final String PH_ID_OGG_IST_PADRE = "PH_ID_OGG_IST_PADRE";
    private static final String PH_COMP_APP = "PH_COMP_APP";
    private static final String PH_ID_TIPOLOGIA = "PH_ID_TIPOLOGIA";
    private static final String PH_TIPOLOGIA_LIST = "PH_TIPOLOGIA_LIST";
    private static final String PH_ID_OGG_IST_LIST = "PH_ID_OGG_IST_LIST";
    private static final String PH_QUIT_URL = "PH_URL_QUIT";
    private static final String CONF_KEY_GEECO_TOKEN = "SCRIVA_GEECO_TOKEN";

    @Autowired
    private ConfigGeecoDAO configGeecoDAO;

    @Autowired
    private GeoOggettoIstanzaDAO geoOggettoIstanzaDAO;

    @Autowired
    private OggettoIstanzaDAO oggettoIstanzaDAO;

    
    /**** PARTE NUOVA ****/
    
    public static final String URL = "\nurl: [";

    private static final List<String> CONF_KEYS_GEECO = Arrays.asList(Constants.CONF_KEY_APIMAN_CONSUMER_KEY, Constants.CONF_KEY_APIMAN_CONSUMER_SECRET);
    /**
     * Instantiates a new GEECO service helper.
     *
     * @param endPoint   the end point
     * @param serviceUrl the service url
     * @throws JsonProcessingException the json processing exception
     */
    public GeecoServiceHelper(String endPoint, String serviceUrl) throws JsonProcessingException {
    	super();
        this.tokenUrl = endPoint + "/token";
        this.apiEndpoint = endPoint + serviceUrl;
    }

//    /**
//     * Sets conf keys.
//     */
//    public void setConfKeys() {
//        this.configurazioneList = this.configurazioneList == null || this.configurazioneList.isEmpty() ?
//                configurazioneDAO.loadConfigByKeyList(CONF_KEYS_GEECO) :
//                this.configurazioneList;
//        if (this.configurazioneList != null && !this.configurazioneList.isEmpty()) {
//            this.consumerKey = configurazioneList.getOrDefault(Constants.CONF_KEY_APIMAN_CONSUMER_KEY, null);
//            this.consumerSecret = configurazioneList.getOrDefault(Constants.CONF_KEY_APIMAN_CONSUMER_SECRET, null);
//        }
//    }



    /**
     * Gets geeco url.
     *
     * @param configuration configuration
     * @return string geeco url
     * @throws GenericException GenericException
     */
    public String getGeecoUrl(Configuration configuration) throws GenericException {
        logBeginInfo(className, configuration);
        String geecoUrl = null;
        String targetUrl = this.apiEndpoint + "/" + configuration.getApiInfo().getEnv() + "/configuration";
        try {
            logDebug(className, "Configuration:\n" + new ObjectMapper().writer().writeValueAsString(configuration));
            Entity<Configuration> entity = Entity.json(configuration);

            this.setConfKeys(CONF_KEYS_GEECO);
            Response resp = getBuilder(targetUrl)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);

            if (resp.getStatus() >= 400) {
                GenericType<Error> errGenericType = new GenericType<Error>() {
                };
                Error err = resp.readEntity(errGenericType);
                logError(className, err);
                throw new GenericException(err.getMessage());
            }
            GenericType<ConfigurationResponse> configurationResponseType = new GenericType<ConfigurationResponse>() {
            };
            ConfigurationResponse response = resp.readEntity(configurationResponseType);
            geecoUrl = response != null ? response.getGeecourl() : null;
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } catch (JsonProcessingException e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return geecoUrl;
    }

    /**
     * Gets geeco configuration.
     *
     * @param idOggettoIstanza            idOggettoIstanza
     * @param denominazioneOggettoIstanza denominazioneOggettoIstanza
     * @param idAdempimento               idAdempimento
     * @param idRuoloApplicativo          idRuoloApplicativo
     * @return Configuration geeco configuration
     */
    public Configuration getGeecoConfiguration(Long idOggettoIstanza, String denominazioneOggettoIstanza, Long idAdempimento, int idRuoloApplicativo) {
        LOGGER.debug("[GeecoServiceHelper::getGeecoConfiguration] BEGIN");
        LOGGER.debug("[GeecoServiceHelper::getGeecoConfiguration] idOggettoIstanza : " + idOggettoIstanza);
        LOGGER.debug("[GeecoServiceHelper::getGeecoConfiguration] denominazioneOggettoIstanza : " + denominazioneOggettoIstanza);
        LOGGER.debug("[GeecoServiceHelper::getGeecoConfiguration] idAdempimento : " + idAdempimento);
        LOGGER.debug("[GeecoServiceHelper::getGeecoConfiguration] idRuoloApplicativo : " + idRuoloApplicativo);

        Configuration config = new Configuration();
        ConfigGeecoDTO template = configGeecoDAO.getConfig(idRuoloApplicativo, idAdempimento);

        ApiInfo apiInfo = new ApiInfo();
        apiInfo.setEnv(template.getEnvGeeco());
        apiInfo.setUuid(template.getUuidGeeco());
        apiInfo.setVersion(template.getVersionGeeco());
        config.setApiInfo(apiInfo);

        StartupInfo startupInfo = getStartupInfoFromJson(template.getJsonStartupinfo(), null);
        config.setStartupInfo(startupInfo);

        String quitUrl = getQuitUrl(null, idOggettoIstanza, "FO");
        QuitInfo quitInfo = getQuitInfoFromJson(template.getJsonQuitinfo(), quitUrl);
        config.setQuitInfo(quitInfo);

        String jsonEditingLayers = template.getJsonEditinglayers();
        // sostituisco i placeholders
        jsonEditingLayers = jsonEditingLayers.replace(PH_ID_OGGETTO_ISTANZA, String.valueOf(idOggettoIstanza));
        jsonEditingLayers = jsonEditingLayers.replace(PH_DEN_OGGETTO, String.valueOf(denominazioneOggettoIstanza));
        Map<String, Layer> editingLayers = getEditingLayersFromJson(jsonEditingLayers);

        // per ciascun layer, aggiungo le feature se sono già state salvate per oggetto_istanza
        // TODO aggiungere le ultime feature per l'oggetto anche se è referenziato da istanze diverse
        if (editingLayers != null) {
            for (Map.Entry<String, Layer> entry : editingLayers.entrySet()) {
                Long idVirtuale = Long.parseLong(entry.getKey());
                Layer layer = entry.getValue();
                Features features = new Features();
                layer.setFeatures(features);

                List<GeoOggettoIstanzaDTO> oggetti = geoOggettoIstanzaDAO.getOggetti(idVirtuale, idOggettoIstanza);
                if (oggetti != null) {
                    for (GeoOggettoIstanzaDTO oggetto : oggetti) {
                        Feature feature = getFeatureFromJson(oggetto.getJsonGeoFeature());
                        if (feature != null) {
                            feature.setId(oggetto.getIdGeometrico());
                            features.getFeatures().add(feature);
                        }
                    }
                }
            }
        }
        config.setEditingLayers(editingLayers);
        LOGGER.debug("[GeecoServiceHelper::getGeecoConfiguration] END");
        return config;
    }

    /**
     * Gets geeco configuration.
     *
     * @param idOggettoIstanza            the id oggetto istanza
     * @param denominazioneOggettoIstanza the denominazione oggetto istanza
     * @param idAdempimento               the id adempimento
     * @param codGestioneAttore           the cod gestione attore
     * @return the geeco configuration
     */
    public Configuration getGeecoConfiguration(Long idOggettoIstanza, String denominazioneOggettoIstanza, Long idAdempimento, String codGestioneAttore, String codComponenteApp) {
        logBegin(className);
        logInfo(className, "idOggettoIstanza : [" + idOggettoIstanza + "]\n" +
                "denominazioneOggettoIstanza : [" + denominazioneOggettoIstanza + "]\n" +
                "idAdempimento : [" + idAdempimento + "]\n" +
                "codGestioneAttore : [" + codGestioneAttore + "]");

        Configuration config = new Configuration();
        ConfigGeecoDTO configGeeco = configGeecoDAO.getConfig(codGestioneAttore, idAdempimento);

        ApiInfo apiInfo = new ApiInfo();
        apiInfo.setEnv(configGeeco.getEnvGeeco());
        apiInfo.setUuid(configGeeco.getUuidGeeco());
        apiInfo.setVersion(configGeeco.getVersionGeeco());
        config.setApiInfo(apiInfo);

        StartupInfo startupInfo = getStartupInfoFromJson(configGeeco.getJsonStartupinfo(), null);
        config.setStartupInfo(startupInfo);

        String quitUrl = getQuitUrl(null, idOggettoIstanza, codComponenteApp);
        QuitInfo quitInfo = getQuitInfoFromJson(configGeeco.getJsonQuitinfo(), quitUrl);
        config.setQuitInfo(quitInfo);

        String jsonEditingLayers = configGeeco.getJsonEditinglayers();
        // sostituisco i placeholders
        jsonEditingLayers = jsonEditingLayers.replace(PH_ID_OGGETTO_ISTANZA, String.valueOf(idOggettoIstanza));
        jsonEditingLayers = jsonEditingLayers.replace(PH_DEN_OGGETTO, String.valueOf(denominazioneOggettoIstanza));
        Map<String, Layer> editingLayers = getEditingLayersFromJson(jsonEditingLayers);

        // per ciascun layer, aggiungo le feature se sono già state salvate per oggetto_istanza
        // TODO aggiungere le ultime feature per l'oggetto anche se è referenziato da istanze diverse
        if (editingLayers != null) {
            for (Map.Entry<String, Layer> entry : editingLayers.entrySet()) {
                Long idVirtuale = Long.parseLong(entry.getKey());
                Layer layer = entry.getValue();
                Features features = new Features();
                layer.setFeatures(features);

                List<GeoOggettoIstanzaDTO> oggetti = geoOggettoIstanzaDAO.getOggetti(idVirtuale, idOggettoIstanza);
                if (oggetti != null) {
                    for (GeoOggettoIstanzaDTO oggetto : oggetti) {
                        Feature feature = getFeatureFromJson(oggetto.getJsonGeoFeature());
                        if (feature != null) {
                            feature.setId(oggetto.getIdGeometrico());
                            features.getFeatures().add(feature);
                        }
                    }
                }
            }
        }
        config.setEditingLayers(editingLayers);
        LOGGER.debug("[GeecoServiceHelper::getGeecoConfiguration] END");
        return config;
    }

    /**
     * Gets geeco configuration.
     *
     * @param geecoDefaultProperty the geeco default property
     * @param idAdempimento        the id adempimento
     * @return the geeco configuration
     */
    public Configuration getGeecoConfiguration(GeecoDefaultProperty geecoDefaultProperty, Long idAdempimento) {
        logBegin(className);
        logInfo(className, "geecoDefaultProperty : \n" + geecoDefaultProperty + "\n");

        Configuration config = new Configuration();
        ConfigGeecoDTO configGeeco = configGeecoDAO.getConfig(geecoDefaultProperty.getAttoreScriva().getProfiloAppIstanza(), idAdempimento);

        ApiInfo apiInfo = new ApiInfo();
        apiInfo.setEnv(configGeeco.getEnvGeeco());
        apiInfo.setUuid(configGeeco.getUuidGeeco());
        apiInfo.setVersion(configGeeco.getVersionGeeco());
        config.setApiInfo(apiInfo);

        StartupInfo startupInfo = getStartupInfoFromJson(configGeeco.getJsonStartupinfo(), geecoDefaultProperty);
        config.setStartupInfo(startupInfo);

        Long idOggettoIstanza = CollectionUtils.isNotEmpty(geecoDefaultProperty.getIdOggettoIstanzaList()) ? geecoDefaultProperty.getIdOggettoIstanzaList().get(0) : null;
        String quitUrl = StringUtils.isNotBlank(geecoDefaultProperty.getQuitUrl()) ? geecoDefaultProperty.getQuitUrl() : getQuitUrl(geecoDefaultProperty.getIdIstanza(), idOggettoIstanza, geecoDefaultProperty.getAttoreScriva().getComponente());
        QuitInfo quitInfo = getQuitInfoFromJson(configGeeco.getJsonQuitinfo(), quitUrl);
        config.setQuitInfo(quitInfo);

        String jsonEditingLayers = filterEditingLayers(configGeeco.getJsonEditinglayers(), geecoDefaultProperty.getIdLayerList());
        //{"schemas":[{"name":"Identificativo","type":"hidden","readonly":true,"required":false},{"name":"Codice univoco","type":"hidden","readonly":true,"required":false},{"name":"Id istanza","type":"hidden","readonly":true,"required":false},{"name":"Attore","type":"hidden","readonly":true,"required":false},{"name":"Identificativo oggetto padre","type":"hidden","readonly":true,"required":false},{"name":"Componente applicativa","type":"hidden","readonly":true,"required":false},{"name":"Aggiorna oggetto","type":"hidden","readonly":true,"required":false},{"name":"Gestione oggetto istanza","type":"hidden","readonly":true,"required":false},{"name":"map_id_tipologia","type":"hidden","readonly":true,"required":false},{"name":"Tipo opera","type":"text","readonly":true,"required":true},{"name":"Descrizione geometria","type":"text","readonly":false,"required":true,"maxLength":30},{"name":"map_den_oggetto","type":"hidden","readonly":true,"required":false},{"name":"map_des_oggetto","type":"hidden","readonly":true,"required":false},{"name":"geom","type":"geometry","geomType":"MultiPoint","required":true}],"defaultStyles":"","defaultValues":{"Attore":"PH_ATTORE","Id istanza":"PH_ISTANZA","Tipo opera":"Restituzione","Codice univoco":"-999","map_den_oggetto":"[[Descrizione geometria]]","map_des_oggetto":"[[Descrizione geometria]]","Aggiorna oggetto":"true","map_id_tipologia":"PH_ID_TIPOLOGIA","Descrizione geometria":"Restituzione xxx","Componente applicativa":"PH_COMP_APP","Gestione oggetto istanza":"NO_CHECK_OGG_IST","Identificativo oggetto padre":"PH_ID_OGG_IST_PADRE"},"canDeleteFeatures":false,"canInsertNewFeatures":true}
        Map<String, Layer> editingLayers = getEditingLayersFromJson(setPlaceHolder(geecoDefaultProperty, jsonEditingLayers));

        // per ciascun layer, aggiungo le feature se sono già state salvate per oggetto_istanza
        // TODO aggiungere le ultime feature per l'oggetto anche se è referenziato da istanze diverse
        setFeatures(geecoDefaultProperty, editingLayers);

        config.setEditingLayers(editingLayers);
        try {
            logDebug(className, new ObjectMapper().writer().writeValueAsString(config));
        } catch (JsonProcessingException e) {
            logError(className, e);
        }
        logEnd(className);
        return config;
    }

    /**
     * Filter editing layers string.
     *
     * @param jsonEditinglayers the json editinglayers
     * @param idLayerList       the id layer list
     * @return the string
     */
    private String filterEditingLayers(String jsonEditinglayers, List<Long> idLayerList) {
        logBegin(className);
        if (StringUtils.isNotBlank(jsonEditinglayers) && idLayerList != null && !idLayerList.isEmpty()) {
            try {
                List<String> idLayerStringList = idLayerList.stream().map(Object::toString).collect(Collectors.toUnmodifiableList());
                //trasformazione di una lista di String in una stringa di array in formato JSON
                String idLayerJsonStringList = JSONArray.toJSONString(idLayerStringList);
                //sostituzione dei doppi apici con singolo apice adatto ai filtri di JsonPath
                String filterJsonPath = idLayerJsonStringList.replace("\"", "'");
                //filterJsonPath = "['57']";
                Map<String, Object> mapJsonEditingLayers = JsonPath.read(jsonEditinglayers, "$" + filterJsonPath);
                
                // Contare le occorrenze di "schemas" nel JSON
                int schemasCount = countSchemasOccurrences(mapJsonEditingLayers);

                // Creare una nuova mappa per includere la chiave "57" solo se "schemas" appare una sola volta
                Map<String, Object> result = new HashMap<>();
                if (schemasCount == 1) {
                	String radix = filterJsonPath.replace("'", "").replace("[", "").replace("]", "");
                    result.put(radix, mapJsonEditingLayers);
                    jsonEditinglayers = mapJsonEditingLayers != null ? new ObjectMapper().writer().writeValueAsString(result) : null;
                    logEnd(className);
                    return jsonEditinglayers;
                }
                            
                jsonEditinglayers = mapJsonEditingLayers != null ? new ObjectMapper().writer().writeValueAsString(mapJsonEditingLayers) : null;
            } catch (Exception e) {
                logError(className, e);
            }
        }
        logEnd(className);
        return jsonEditinglayers;
    }

    private static int countSchemasOccurrences(Map<String, Object> map) {
        int count = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals("schemas")) {
                count++;
            }
            if (entry.getValue() instanceof Map) {
                count += countSchemasOccurrences((Map<String, Object>) entry.getValue());
            } else if (entry.getValue() instanceof List) {
                for (Object item : (List<?>) entry.getValue()) {
                    if (item instanceof Map) {
                        count += countSchemasOccurrences((Map<String, Object>) item);
                    }
                }
            }
        }
        return count;
    }
    
    /**
     * Gets quit info from json.
     *
     * @param json    json
     * @param quitUrl the quit url
     * @return QuitInfo quit info from json
     */
    private static QuitInfo getQuitInfoFromJson(String json, String quitUrl) {
        if (StringUtils.isNotBlank(json) && StringUtils.isNotBlank(quitUrl)) {
            json = json.replace(PH_QUIT_URL, quitUrl);
        }
        return getElementFromJson(json, new TypeReference<QuitInfo>() {
        });
    }

    /**
     * Gets startup info from json.
     *
     * @param json                 json
     * @param geecoDefaultProperty the geeco default property
     * @return StartupInfo startup info from json
     */
    private StartupInfo getStartupInfoFromJson(String json, GeecoDefaultProperty geecoDefaultProperty) {
        try {
            if (geecoDefaultProperty != null && CollectionUtils.isNotEmpty(geecoDefaultProperty.getIdLayerList())) {
                json = json.replace("\"[PH_EDITABLE_LAYERS]\"", new ObjectMapper().writer().writeValueAsString(geecoDefaultProperty.getIdLayerList()));
            } else {
                json = json.replace("\"[PH_EDITABLE_LAYERS]\"", "null");
            }
        } catch (Exception e) {
            logError(className, e);
        }
        return getElementFromJson(json, new TypeReference<StartupInfo>() {
        });
    }

    /**
     * @param json json
     * @return ApiInfo
     */
    private static ApiInfo getApiInfoFromJson(String json) {
        return StringUtils.isNotBlank(json) ? getElementFromJson(json, new TypeReference<ApiInfo>() {
        }) : null;
    }

    /**
     * @param json json
     * @return Map<String, Layer>
     */
    private static Map<String, Layer> getEditingLayersFromJson(String json) {
        return StringUtils.isNotBlank(json) ? getElementFromJson(json, new TypeReference<Map<String, Layer>>() {
        }) : null;
    }

    /**
     * @param json json
     * @return Feature
     */
    private static Feature getFeatureFromJson(String json) {
        return StringUtils.isNotBlank(json) ? getElementFromJson(json, new TypeReference<Feature>() {
        }) : null;
    }


    /**
     * Sets place holder.
     *
     * @param geecoDefaultProperty the geeco default property
     * @param jsonEditingLayers    the json editing layers
     * @return the place holder
     */
    private String setPlaceHolder(GeecoDefaultProperty geecoDefaultProperty, String jsonEditingLayers) {
        if (geecoDefaultProperty != null && StringUtils.isNotBlank(jsonEditingLayers)) {
            if (geecoDefaultProperty.getIdOggettoIstanzaList().size() == 1) {
                jsonEditingLayers = jsonEditingLayers.replace(PH_ID_OGGETTO_ISTANZA, String.valueOf(geecoDefaultProperty.getIdOggettoIstanzaList().get(0)));
            }
            jsonEditingLayers = jsonEditingLayers.replace(PH_DEN_OGGETTO, geecoDefaultProperty.getDenOggetto());
            jsonEditingLayers = jsonEditingLayers.replace(PH_DES_OGGETTO, geecoDefaultProperty.getDesOggetto());
            jsonEditingLayers = jsonEditingLayers.replace(PH_ID_SOGGETTO, String.valueOf(geecoDefaultProperty.getIdSoggetto()));
            jsonEditingLayers = jsonEditingLayers.replace(PH_ISTANZA, String.valueOf(geecoDefaultProperty.getIdIstanza()));
            jsonEditingLayers = jsonEditingLayers.replace(PH_ATTORE, geecoDefaultProperty.getAttoreScriva().getGestUidAttore());
            jsonEditingLayers = jsonEditingLayers.replace(PH_ID_OGG_IST_PADRE, String.valueOf(geecoDefaultProperty.getIdOggettoIstanzaPadre()));
            jsonEditingLayers = jsonEditingLayers.replace(PH_COMP_APP, geecoDefaultProperty.getAttoreScriva().getComponente());
            //jsonEditingLayers = jsonEditingLayers.replace(PH_ID_TIPOLOGIA, String.valueOf(geecoDefaultProperty.getIdTipologiaOggetto()));
            jsonEditingLayers = setMapIdTipologiaOggetto(geecoDefaultProperty, jsonEditingLayers);

            if (geecoDefaultProperty.getTipologiaOggettoList() != null && !geecoDefaultProperty.getTipologiaOggettoList().isEmpty()) {
                jsonEditingLayers = jsonEditingLayers.replace(PH_TIPOLOGIA_LIST, JSONArray.toJSONString(geecoDefaultProperty.getTipologiaOggettoList()));
            }

            //jsonEditingLayers = jsonEditingLayers.replace(PH_ID_OGG_IST_LIST, String.valueOf(geecoDefaultProperty.getTipologiaOggettoList()));
        }
        return jsonEditingLayers;
    }


    private String setMapIdTipologiaOggetto(GeecoDefaultProperty geecoDefaultProperty, String jsonEditingLayers) {
        logBegin(className);
        try {
            if (StringUtils.isNotBlank(jsonEditingLayers) && CollectionUtils.isNotEmpty(geecoDefaultProperty.getTipologiaOggettoObjList())) {
                JSONObject jsonObject = new JSONObject(jsonEditingLayers.trim());
                Iterator<String> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    Long keyL = Long.valueOf(key);
                    TipologiaOggettoDTO tipologiaOggetto = geecoDefaultProperty.getTipologiaOggettoObjList().stream()
                            .filter(t -> keyL.equals(t.getIdLayer()))
                            .findFirst()
                            .orElse(null);
                    if (tipologiaOggetto != null && jsonObject.get(key) instanceof JSONObject) {
                        String value = jsonObject.get(key).toString();
                        value = value.replace(PH_ID_TIPOLOGIA, tipologiaOggetto.getIdTipologiaOggetto().toString());
                        jsonObject.put(key, new JSONObject(value.trim()));
                    }
                }
                jsonEditingLayers = jsonObject.toString();
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return jsonEditingLayers;
    }

    /**
     * Sets features.
     *
     * @param geecoDefaultProperty the geeco default property
     * @param editingLayers        the editing layers
     */
    private void setFeatures(GeecoDefaultProperty geecoDefaultProperty, Map<String, Layer> editingLayers) {
        logBegin(className);
        if (editingLayers != null) {
            for (Map.Entry<String, Layer> entry : editingLayers.entrySet()) {
                Long idVirtuale = Long.parseLong(entry.getKey());
                Layer layer = entry.getValue();
                Features features = new Features();
                layer.setFeatures(features);

                if (geecoDefaultProperty != null && CollectionUtils.isNotEmpty(geecoDefaultProperty.getIdOggettoIstanzaList())) {
                    List<GeoOggettoIstanzaDTO> oggetti = geoOggettoIstanzaDAO.getOggetti(idVirtuale, geecoDefaultProperty.getIdOggettoIstanzaList());
                    if (oggetti != null) {
                        for (GeoOggettoIstanzaDTO oggetto : oggetti) {
                            Feature feature = getFeatureFromJson(oggetto.getJsonGeoFeature());
                            if (feature != null) {
                                feature.setId(oggetto.getIdGeometrico());
                                features.getFeatures().add(feature);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Gets quit url.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param codComponenteApp the cod componente app
     * @return the quit url
     */
    private String getQuitUrl(Long idIstanza, Long idOggettoIstanza, String codComponenteApp) {
        logBegin(className);
        List<ConfigurazioneDTO> configurazioneList = configurazioneDAO.loadConfigByKey("SCRIVA_" + StringUtils.upperCase(codComponenteApp) + "_URL");
        String urlBase = CollectionUtils.isNotEmpty(configurazioneList) ? configurazioneList.get(0).getValore() : "";
        if (idIstanza == null && idOggettoIstanza != null) {
            OggettoIstanzaDTO oggettoIstanza = oggettoIstanzaDAO.findByPK(idOggettoIstanza);
            idIstanza = oggettoIstanza != null ? oggettoIstanza.getIdIstanza() : null;
        }
        logEnd(className);
        return urlBase + "progetto-geeco-istanza?idIstanza=" + idIstanza;
    }


    /**
     * @param json json
     * @param type Feature
     * @return <T>
     */
    private static <T> T getElementFromJson(String json, final TypeReference<T> type) {
        LOGGER.debug("[GeecoServiceHelper::getElementFromJson] BEGIN");
        ObjectMapper objectMapper = new ObjectMapper();
        T obj = null;
        try {
            obj = objectMapper.readValue(json, type);
        } catch (IOException e) {
            LOGGER.error("[GeecoServiceHelper::getElementFromJson] EXCEPTION ", e);
        }
        LOGGER.debug("[GeecoServiceHelper::getElementFromJson] END");
        return obj;
    }
}