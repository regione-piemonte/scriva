/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.manager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigJsonDataDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoAreaOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoLineaOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoPuntoOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.QuadroDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ReferenteIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.SoggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.UbicazioneOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.impl.BaseServiceImpl;
import it.csi.scriva.scrivabesrv.dto.ConfigJsonDataDTO;
import it.csi.scriva.scrivabesrv.dto.GeoAreaOggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.GeoLineaOggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.GeoPuntoOggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaCompetenzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaUbicazioneGeoRefExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.QuadroExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ReferenteIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoQuadroDTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoIstanzaExtendedDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Json data manager.
 *
 * @author CSI PIEMONTE
 */
@Component
public class JsonDataManager extends BaseServiceImpl {

    private final String className = this.getClass().getSimpleName();

    private final String PARAM_ID_ISTANZA = "Parametro in input idIstanza [__idIstanza__]";

    private final String ERR_INASPETTATO = "Errore inaspettato nella gestione della richiesta. Riprova a breve.";

    private final String ELEMENTO_NON_TROVATO = "Elemento non trovato con idIstanza [__idIstanza__]";

    /**
     * The constant CONFIG_PATH.
     */
    protected static final String CONFIG_PATH = "$.QDR_CONFIG";

    @Autowired
    private ConfigJsonDataDAO configJsonDataDAO;

    @Autowired
    private GeoAreaOggettoIstanzaDAO geoAreaOggettoIstanzaDAO;

    @Autowired
    private GeoPuntoOggettoIstanzaDAO geoPuntoOggettoIstanzaDAO;

    @Autowired
    private GeoLineaOggettoIstanzaDAO geoLineaOggettoIstanzaDAO;

    @Autowired
    private IstanzaDAO istanzaDAO;

    @Autowired
    private OggettoIstanzaDAO oggettoIstanzaDAO;

    @Autowired
    private QuadroDAO quadroDAO;

    @Autowired
    private ReferenteIstanzaDAO referenteIstanzaDAO;

    @Autowired
    private SoggettoIstanzaDAO soggettoIstanzaDAO;

    @Autowired
    private UbicazioneOggettoIstanzaDAO ubicazioneOggettoIstanzaDAO;

    /**
     * Get value from json data object.
     *
     * @param idIstanza the id istanza
     * @param path      the path
     * @param key       the key
     * @return the object
     */
    public String getValueFromJsonData(Long idIstanza, String path, String key) {
        logBeginInfo(className, "idIstanza [" + idIstanza + "] - path [" + path + "] - key [" + key + "]");
        Object result = null;
        try {
            List<IstanzaExtendedDTO> list = istanzaDAO.loadIstanza(idIstanza);
            IstanzaExtendedDTO istanza = list != null && !list.isEmpty() ? list.get(0) : null;
            String jsonData = istanza != null ? istanza.getJsonData() : null;
            result = getValueFromJsonData(jsonData, path, key);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result != null ? result.toString() : null;
    }

    /**
     * Gets value from json data.
     *
     * @param jsonData the json data
     * @param path     the path
     * @param key      the key
     * @return the value from json data
     */
    public String getValueFromJsonData(String jsonData, String path, String key) {
        logBeginInfo(className, "jsonData :\n" + jsonData + "\npath [" + path + "] - key [" + key + "]");
        Object result = null;
        try {
            Map<String, Object> config = StringUtils.isNotBlank(jsonData) ? JsonPath.read(jsonData, path) : null;
            result = config != null && !config.isEmpty() ? config.getOrDefault(key, null) : null;
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result != null ? result.toString() : null;
    }

    /**
     * Search value from json data string.
     *
     * @param idIstanza the id istanza
     * @param key       the key
     * @return the string
     */
    public String searchValueFromJsonData(Long idIstanza, String key) {
        logBeginInfo(className, "idIstanza [" + idIstanza + "] - key [" + key + "]");
        Object result = null;
        try {
            List<IstanzaExtendedDTO> list = istanzaDAO.loadIstanza(idIstanza);
            IstanzaExtendedDTO istanza = list != null && !list.isEmpty() ? list.get(0) : null;
            String jsonData = istanza != null ? istanza.getJsonData() : null;
            result = searchValueFromJsonData(jsonData, key);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result != null ? result.toString() : null;
    }

    /**
     * Search value from json data new string.
     *
     * @param jsonData the json data
     * @param key      the key
     * @return the string
     */
    public String searchValueFromJsonData(String jsonData, String key) {
        logBeginInfo(className, "jsonData :\n" + jsonData + "\nkey [" + key + "]");
        try {
            JsonNode jsonNode = StringUtils.isNotBlank(jsonData) && StringUtils.isNotBlank(key) ? new ObjectMapper().readTree(jsonData) : null;
            JsonNode jsonNodeFound = jsonNode != null ? research(jsonNode, key) : null;
            return jsonNodeFound != null ? jsonNodeFound.toString() : null;
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return null;
    }

    /**
     * Research json node.
     *
     * @param jsonNode the json node
     * @param key      the key
     * @return the json node
     */
    private JsonNode research(JsonNode jsonNode, String key) {
        JsonNode result = null;
        Iterator<Map.Entry<String, JsonNode>> nodes = jsonNode.fields();
        while (nodes.hasNext()) {
            Map.Entry<String, JsonNode> entry = nodes.next();
            if (key.equalsIgnoreCase(entry.getKey())) {
                logDebug(className, "key --> " + entry.getKey() + " value-->" + entry.getValue());
                return entry.getValue();
            }

            if (entry.getValue().getNodeType().equals(JsonNodeType.ARRAY)) {
                for (final JsonNode node : entry.getValue()) {
                    result = research(node, key);
                }
            } else {
                result = research(entry.getValue(), key);
            }
            if (result != null) {
                System.out.println(entry.getKey());
                return result;
            }
        }
        return null;
    }

    /**
     * Remove object from json string.
     *
     * @param jsonData the json data
     * @param key      the key
     * @param value    the value
     * @return the string
     */
    public String removeObjectFromJson(String jsonData, String key, String value) {
        logBeginInfo(className, "jsonData :\n" + jsonData + "\nkey [" + key + "]\nvalue [" + value + "]");
        List<String> path = new ArrayList<>();
        List<String> allPath = new ArrayList<>();
        try {
            DocumentContext jsonContext = StringUtils.isNotBlank(jsonData) ? JsonPath.parse(jsonData) : null;
            JsonNode jsonNode = StringUtils.isNotBlank(jsonData) && StringUtils.isNotBlank(key) ? new ObjectMapper().readTree(jsonData) : null;
            getPathFromJson(jsonNode, key, value, path, allPath, 0);
            if (jsonContext != null && CollectionUtils.isNotEmpty(allPath)) {
                allPath.sort(Collections.reverseOrder());
                for (String p : allPath) {
                    logDebug(className, p);
                    jsonContext.delete("$." + p);
                }
            }

            return jsonContext != null ? new ObjectMapper().writeValueAsString(jsonContext.json()) : null;
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return null;
    }

    private JsonNode searchAndPath(JsonNode jsonNode, String key, List<String> path, List<String> allPath, int pathPosition) {
        JsonNode result = null;
        Iterator<Map.Entry<String, JsonNode>> nodes = jsonNode.fields();
        while (nodes.hasNext()) {
            Map.Entry<String, JsonNode> entry = nodes.next();
            setPath(path, pathPosition, entry);
            if (key.equalsIgnoreCase(entry.getKey())) {
                logDebug(className, "key --> " + entry.getKey() + " value-->" + entry.getValue());
                allPath.add(StringUtils.join(path.subList(0, pathPosition + 1), "."));
                return entry.getValue();
            }

            if (entry.getValue().getNodeType().equals(JsonNodeType.ARRAY)) {
                int idxArray = 0;
                for (final JsonNode node : entry.getValue()) {
                    logDebug(className, " ".repeat(pathPosition * 2) + entry.getKey() + " -> level " + pathPosition);
                    path.set(pathPosition, entry.getKey() + "[" + idxArray + "]");
                    result = searchAndPath(node, key, path, allPath, pathPosition + 1);
                    if (result != null) {
                        //path.add(entry.getKey() + "[" + idxArray + "]");
                        /*
                        if (pathPosition == 0) {
                            List<String> tmpPath = new ArrayList<>(path);
                            Collections.reverse(tmpPath);
                            allPath.add(StringUtils.join(tmpPath, "."));
                        }*/
                        return result;
                    }
                    idxArray++;
                }
            } else {
                logDebug(className, " ".repeat(pathPosition * 2) + entry.getKey() + " -> level " + pathPosition);
                result = searchAndPath(entry.getValue(), key, path, allPath, pathPosition + 1);
            }
            if (result != null) {
                //path.add(entry.getKey());
                /*
                if (pathPosition == 0) {
                    List<String> tmpPath = new ArrayList<>(path);
                    Collections.reverse(tmpPath);
                    allPath.add(StringUtils.join(tmpPath, "."));
                }
                */
                return result;
            }
        }
        return null;
    }

    private JsonNode searchAndPathXp(JsonNode jsonNode, String key, List<String> path, List<String> allPath, int pathPosition) {
        Iterator<Map.Entry<String, JsonNode>> nodes = jsonNode.fields();
        while (nodes.hasNext()) {
            Map.Entry<String, JsonNode> entry = nodes.next();
            setPath(path, pathPosition, entry);
            if (key.equalsIgnoreCase(entry.getKey())) {
                logDebug(className, "key --> " + entry.getKey() + " value-->" + entry.getValue());
                allPath.add(StringUtils.join(path.subList(0, pathPosition + 1), "."));
                System.out.println(path.subList(pathPosition - 1, pathPosition));
                return entry.getValue();
            }

            if (entry.getValue().getNodeType().equals(JsonNodeType.ARRAY)) {
                int idxArray = 0;
                for (final JsonNode node : entry.getValue()) {
                    logDebug(className, " ".repeat(pathPosition * 4) + entry.getKey() + " -> level " + pathPosition);
                    path.set(pathPosition, entry.getKey() + "[" + idxArray + "]");
                    searchAndPathXp(node, key, path, allPath, pathPosition + 1);
                    idxArray++;
                }
            } else {
                logDebug(className, " ".repeat(pathPosition * 4) + entry.getKey() + " -> level " + pathPosition);
                searchAndPathXp(entry.getValue(), key, path, allPath, pathPosition + 1);
            }
        }
        return null;
    }

    private void getPathFromJson(JsonNode jsonNode, String key, String value, List<String> path, List<String> allPath, int pathPosition) {
        if (jsonNode != null) {
            Iterator<Map.Entry<String, JsonNode>> nodes = jsonNode.fields();
            while (nodes.hasNext()) {
                Map.Entry<String, JsonNode> entry = nodes.next();
                setPath(path, pathPosition, entry);
                if (key.equalsIgnoreCase(entry.getKey())) {
                    logDebug(className, "key --> " + entry.getKey() + " value-->" + entry.getValue());
                    if (StringUtils.isBlank(value) || (StringUtils.isNotBlank(value) && value.equalsIgnoreCase(String.valueOf(entry.getValue())))) {
                        allPath.add(StringUtils.join(path.subList(0, pathPosition), "."));
                    }
                }
                if (entry.getValue().getNodeType().equals(JsonNodeType.ARRAY)) {
                    getPathFromJsonArray(key, value, path, allPath, pathPosition, entry);
                } else {
                    logDebug(className, " ".repeat(pathPosition * 4) + entry.getKey() + " -> level " + pathPosition);
                    getPathFromJson(entry.getValue(), key, value, path, allPath, pathPosition + 1);
                }
            }
        }
    }

    private void getPathFromJsonArray(String key, String value, List<String> path, List<String> allPath, int pathPosition, Map.Entry<String, JsonNode> entry) {
        int idxArray = 0;
        for (final JsonNode node : entry.getValue()) {
            logDebug(className, " ".repeat(pathPosition * 4) + entry.getKey() + " -> level " + pathPosition);
            path.set(pathPosition, entry.getKey() + "[" + idxArray + "]");
            getPathFromJson(node, key, value, path, allPath, pathPosition + 1);
            idxArray++;
        }
    }

    private void setPath(List<String> path, int pathPosition, Map.Entry<String, JsonNode> entry) {
        if (path.size() > pathPosition) {
            path.set(pathPosition, entry.getKey());
        } else {
            path.add(entry.getKey());
        }
    }

    /**
     * Find on json node by key.
     *
     * @param rootNode the root node
     * @param key      the key
     */
    public void findOnJsonNodeByKey(JsonNode rootNode, String key) {
        Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            if (field.getKey().equalsIgnoreCase(key)) {
                logInfo(className, field.getValue().asText());
            } else {
                this.findOnJsonNodeByKey(field.getValue(), key);
            }
        }
    }

    /**
     * Update json data.
     *
     * @param idIstanza idIstanza
     */
    public void updateJsonData(Long idIstanza) {
        logBeginInfo(className, idIstanza);
        List<IstanzaExtendedDTO> list = istanzaDAO.loadIstanza(idIstanza);
        if (null == list) {
            logError(className, ERR_INASPETTATO);
            logEnd(className);
        } else if (list.isEmpty()) {
            logError(className, getErrElementoNonTrovato(idIstanza));
            logEnd(className);
        } else {
            // update json_data istanza
            this.updateJsonDataIstanza(idIstanza, list.get(0));

            // update json_data soggetto
            List<SoggettoIstanzaExtendedDTO> soggettiIstanza = soggettoIstanzaDAO.loadSoggettoIstanzaByIdIstanza(idIstanza);
            if (soggettiIstanza != null && !soggettiIstanza.isEmpty()) {
                this.updateJsonDataSoggettoIstanza(list, soggettiIstanza);
            }

            // update json_data oggetto
            List<OggettoIstanzaUbicazioneGeoRefExtendedDTO> oggettiIstanza = null;
            List<OggettoIstanzaExtendedDTO> oggettiIstanzaList = oggettoIstanzaDAO.loadOggettoIstanzaByIdIstanza(idIstanza);
            if (oggettiIstanzaList != null && !oggettiIstanzaList.isEmpty()) {
                oggettiIstanza = this.getOggettoUbicazioneGeoRefList(oggettiIstanzaList);
                if (!oggettiIstanza.isEmpty()) {
                    this.updateJsonDataOggettoIstanza(list, oggettiIstanza);
                }
            }

            // update json_data referenti
            List<ReferenteIstanzaDTO> referentiIstanza = referenteIstanzaDAO.loadReferentiIstanzaByIdIstanza(idIstanza);
            if (referentiIstanza != null && !referentiIstanza.isEmpty()) {
                this.updateJsonDataReferenteIstanza(list, referentiIstanza);
            }
            logEnd(className);
        }
    }

    /**
     * Update json data istanza.
     *
     * @param idIstanza idIstanza
     * @param istanza   istanza
     */
    public void updateJsonDataIstanza(Long idIstanza, IstanzaExtendedDTO istanza) {
        logBeginInfo(className, "idIstanza [" + idIstanza + "]\nistanza :\n" + istanza);
        IstanzaExtendedDTO istanzaExt = null;
        if (istanza == null) {
            logDebug(className, getParamIdIstanza(idIstanza));
            List<IstanzaExtendedDTO> list = istanzaDAO.loadIstanza(idIstanza);
            if (null == list) {
                logError(className, ERR_INASPETTATO);
                logEnd(className);
                return;
            }
            if (list.isEmpty()) {
                logError(className, getErrElementoNonTrovato(idIstanza));
                logEnd(className);
                return;
            }
            istanzaExt = list.get(0);
        } else {
            istanzaExt = istanza;
        }

        try {
            String jsonData = istanzaExt.getJsonData();
            JSONObject jsonDataIstanza = StringUtils.isNotBlank(jsonData) ? new JSONObject(jsonData) : new JSONObject();
            JSONObject jsonIstanza = null;

            JSONArray jsonCompetenzeTerritorioArray;
            try {
                jsonCompetenzeTerritorioArray = jsonDataIstanza.getJSONObject("QDR_ISTANZA").getJSONArray("competenze_territorio");
            } catch (Exception e) {
                jsonCompetenzeTerritorioArray = null;
            }

            if (jsonCompetenzeTerritorioArray != null) {
                jsonIstanza = istanzaExt.toJsonObj().put("competenze_territorio", jsonCompetenzeTerritorioArray);
            }

            // SCRIVA-265 : ORIENTAMENTO - salvataggio dichiarazione selezionata
            JSONArray jsonQdrConfigDchrArray;
            try {
                jsonQdrConfigDchrArray = jsonDataIstanza.getJSONObject("QDR_ISTANZA").getJSONArray("dichiarazioni");
            } catch (Exception e) {
                jsonQdrConfigDchrArray = null;
            }

            if (jsonQdrConfigDchrArray != null) {
                jsonIstanza = jsonIstanza == null ? istanzaExt.toJsonObj().put("dichiarazioni", jsonQdrConfigDchrArray) : jsonIstanza.put("dichiarazioni", jsonQdrConfigDchrArray);
            }

            JSONObject newJson = jsonDataIstanza.put("QDR_ISTANZA", jsonIstanza);
            if (null != newJson) {
                istanzaExt.setJsonData(newJson.toString());
            }
            istanzaDAO.updateJsonDataIstanza(istanzaExt.getDTO());
        } catch (Exception e) {
            logError(className, e);
        }

    }

    /**
     * Update json data soggetto istanza.
     *
     * @param idIstanza       idIstanza
     * @param soggettiIstanza soggettiIstanza
     */
    public void updateJsonDataSoggettoIstanza(Long idIstanza, List<SoggettoIstanzaExtendedDTO> soggettiIstanza) {
        logBeginInfo(className, "idIstanza [" + idIstanza + "]\nsoggettiIstanza :\n" + soggettiIstanza);
        SoggettoIstanzaExtendedDTO soggettoIstanzaExt = null;
        logDebug(className, getParamIdIstanza(idIstanza));
        List<IstanzaExtendedDTO> list = istanzaDAO.loadIstanza(idIstanza);
        if (null == list) {
            logError(className, ERR_INASPETTATO);
            logEnd(className);
        } else if (list.isEmpty()) {
            logError(className, getErrElementoNonTrovato(idIstanza));
            logEnd(className);
        } else {
            IstanzaExtendedDTO istanzaExt = list.get(0);
            String jsonData = istanzaExt.getJsonData();
            try {
                JSONObject jsonDataObject = new JSONObject(jsonData);
                List<JSONObject> jsonObjectList = new ArrayList<>();

                for (SoggettoIstanzaExtendedDTO soggettoIstanza : soggettiIstanza) {
                    jsonObjectList.add(soggettoIstanza.toJsonObj());
                }
                if (jsonDataObject.getJSONObject("QDR_SOGGETTO") != null) {
                    jsonDataObject.getJSONObject("QDR_SOGGETTO").put("soggettiIstanza", jsonObjectList);
                } else {
                    JSONObject jsonDataObjectVuoto = new JSONObject("{}");
                    jsonDataObject.put("QDR_SOGGETTO", jsonDataObjectVuoto);
                    jsonDataObject.getJSONObject("QDR_SOGGETTO").put("soggettiIstanza", jsonObjectList);
                }
                istanzaExt.setJsonData(jsonDataObject.toString());
                istanzaDAO.updateJsonDataIstanza(istanzaExt.getDTO());
            } catch (Exception e) {
                logError(className, e.toString());
            }
        }
    }

    /**
     * Update json data soggetto istanza.
     *
     * @param istanzaExtendedDTOList istanzaExtendedDTOList
     * @param soggettiIstanza        soggettiIstanza
     */
    public void updateJsonDataSoggettoIstanza(List<IstanzaExtendedDTO> istanzaExtendedDTOList, List<SoggettoIstanzaExtendedDTO> soggettiIstanza) {
        logBeginInfo(className, "istanzaExtendedDTOList\n" + istanzaExtendedDTOList + "\nsoggettiIstanza :\n" + soggettiIstanza);
        SoggettoIstanzaExtendedDTO soggettoIstanzaExt = null;
        if (null == istanzaExtendedDTOList) {
            logError(className, ERR_INASPETTATO);
            logEnd(className);
        } else if (istanzaExtendedDTOList.isEmpty()) {
            logEnd(className);
        } else {
            IstanzaExtendedDTO istanzaExt = istanzaExtendedDTOList.get(0);
            String jsonData = istanzaExt.getJsonData();

            try {
                JSONObject jsonDataObject = new JSONObject(jsonData);
                List<JSONObject> jsonObjectList = new ArrayList<>();

                for (SoggettoIstanzaExtendedDTO soggettoIstanza : soggettiIstanza) {
                    jsonObjectList.add(soggettoIstanza.toJsonObj());
                }
                if (jsonDataObject.has("QDR_SOGGETTO")) {
                    jsonDataObject.getJSONObject("QDR_SOGGETTO").put("soggettiIstanza", jsonObjectList);
                } else {
                    JSONObject jsonDataObjectVuoto = new JSONObject("{}");
                    jsonDataObject.put("QDR_SOGGETTO", jsonDataObjectVuoto);
                    jsonDataObject.getJSONObject("QDR_SOGGETTO").put("soggettiIstanza", jsonObjectList);
                }
                istanzaExt.setJsonData(jsonDataObject.toString());
                istanzaDAO.updateJsonDataIstanza(istanzaExt.getDTO());
            } catch (Exception e) {
                logError(className, e);
            }
        }
    }

    /**
     * Update json data oggetto istanza.
     *
     * @param idIstanza                                     idIstanza
     * @param oggettoIstanzaUbicazioneGeoRefExtendedDTOList oggettoIstanzaUbicazioneGeoRefExtendedDTOList
     */
    public void updateJsonDataOggettoIstanza(Long idIstanza, List<OggettoIstanzaUbicazioneGeoRefExtendedDTO> oggettoIstanzaUbicazioneGeoRefExtendedDTOList) {
        logBeginInfo(className, "idIstanza [" + idIstanza + "]\noggettoIstanzaUbicazioneGeoRefExtendedDTOList :\n" + oggettoIstanzaUbicazioneGeoRefExtendedDTOList);
        List<IstanzaExtendedDTO> list = istanzaDAO.loadIstanza(idIstanza);
        if (null == list) {
            logError(className, ERR_INASPETTATO);
            logEnd(className);
        } else if (list.isEmpty()) {
            logError(className, getErrElementoNonTrovato(idIstanza));
            logEnd(className);
            return;
        } else {
            IstanzaExtendedDTO istanzaExt = list.get(0);
            String jsonData = istanzaExt.getJsonData();
            try {
                JSONObject jsonDataObject = new JSONObject(jsonData);
                List<JSONObject> jsonObjectList = new ArrayList<>();
                for (OggettoIstanzaUbicazioneGeoRefExtendedDTO oggettoIstanzaUbicazione : oggettoIstanzaUbicazioneGeoRefExtendedDTOList) {
                    jsonObjectList.add(oggettoIstanzaUbicazione.toJsonObj());
                }
                if (jsonDataObject.has("QDR_OGGETTO")) {
                    jsonDataObject.getJSONObject("QDR_OGGETTO").put("oggettiIstanza", jsonObjectList);
                } else {
                    JSONObject jsonDataObjectVuoto = new JSONObject("{}");
                    jsonDataObject.put("QDR_OGGETTO", jsonDataObjectVuoto);
                    jsonDataObject.getJSONObject("QDR_OGGETTO").put("oggettiIstanza", jsonObjectList);
                }
                istanzaExt.setJsonData(jsonDataObject.toString());
                istanzaDAO.updateJsonDataIstanza(istanzaExt.getDTO());
            } catch (Exception e) {
                logError(className, e);
                logEnd(className);
            }
        }
    }

    /**
     * Update json data oggetto istanza.
     *
     * @param istanzaExtendedDTOList                        istanzaExtendedDTOList
     * @param oggettoIstanzaUbicazioneGeoRefExtendedDTOList oggettoIstanzaUbicazioneGeoRefExtendedDTOList
     */
    public void updateJsonDataOggettoIstanza(List<IstanzaExtendedDTO> istanzaExtendedDTOList, List<OggettoIstanzaUbicazioneGeoRefExtendedDTO> oggettoIstanzaUbicazioneGeoRefExtendedDTOList) {
        logBeginInfo(className, "istanzaExtendedDTOList\n" + istanzaExtendedDTOList + "\noggettoIstanzaUbicazioneGeoRefExtendedDTOList :\n" + oggettoIstanzaUbicazioneGeoRefExtendedDTOList);
        OggettoIstanzaUbicazioneGeoRefExtendedDTO oggettoIstanzaExt = null;
        if (null == istanzaExtendedDTOList) {
            String errorMessage = "Errore inaspettato nella gestione della richiesta. Riprova a breve";
            logError(className, errorMessage);
            logEnd(className);
        } else if (istanzaExtendedDTOList.isEmpty()) {
            logEnd(className);
        } else {
            IstanzaExtendedDTO istanzaExt = istanzaExtendedDTOList.get(0);
            String jsonData = istanzaExt.getJsonData();
            try {
                JSONObject jsonDataObject = new JSONObject(jsonData);
                List<JSONObject> jsonObjectList = new ArrayList<>();
                for (OggettoIstanzaUbicazioneGeoRefExtendedDTO oggettoIstanzaUbicazione : oggettoIstanzaUbicazioneGeoRefExtendedDTOList) {
                    jsonObjectList.add(oggettoIstanzaUbicazione.toJsonObj());
                }
                if (jsonDataObject.has("QDR_OGGETTO")) {
                    jsonDataObject.getJSONObject("QDR_OGGETTO").put("oggettiIstanza", jsonObjectList);
                } else {
                    JSONObject jsonDataObjectVuoto = new JSONObject("{}");
                    jsonDataObject.put("QDR_OGGETTO", jsonDataObjectVuoto);
                    jsonDataObject.getJSONObject("QDR_OGGETTO").put("oggettiIstanza", jsonObjectList);
                }
                istanzaExt.setJsonData(jsonDataObject.toString());
                istanzaDAO.updateJsonDataIstanza(istanzaExt.getDTO());
            } catch (Exception e) {
                logError(className, e);
                logEnd(className);
            }
        }
    }

    /**
     * Update json data referente istanza.
     *
     * @param idIstanza        idIstanza
     * @param referentiIstanza referentiIstanza
     */
    public void updateJsonDataReferenteIstanza(Long idIstanza, List<ReferenteIstanzaDTO> referentiIstanza) {
        logBeginInfo(className, "idIstanza [" + idIstanza + "]\nreferentiIstanza :\n" + referentiIstanza);
        List<IstanzaExtendedDTO> list = istanzaDAO.loadIstanza(idIstanza);
        if (null == list) {
            logError(className, ERR_INASPETTATO);
            logEnd(className);
        } else if (list.isEmpty()) {
            logError(className, getErrElementoNonTrovato(idIstanza));
            logEnd(className);
        } else {
            IstanzaExtendedDTO istanzaExt = list.get(0);
            String jsonData = istanzaExt.getJsonData();
            try {
                JSONObject jsonDataObject = new JSONObject(jsonData);
                List<JSONObject> jsonObjectList = new ArrayList<>();
                for (ReferenteIstanzaDTO referenteIstanzaExtendedDTO : referentiIstanza) {
                    jsonObjectList.add(referenteIstanzaExtendedDTO.toJsonObj());
                }

                if (jsonDataObject.has("QDR_OGGETTO")) {
                    jsonDataObject.getJSONObject("QDR_OGGETTO").put("referenti", jsonObjectList);
                } else {
                    JSONObject jsonDataObjectVuoto = new JSONObject("{}");
                    jsonDataObject.put("QDR_OGGETTO", jsonDataObjectVuoto);
                    jsonDataObject.getJSONObject("QDR_OGGETTO").put("referenti", jsonObjectList);
                }
                istanzaExt.setJsonData(jsonDataObject.toString());
                istanzaDAO.updateJsonDataIstanza(istanzaExt.getDTO());
            } catch (Exception e) {
                logError(className, e);
                logEnd(className);
            }
        }
    }

    /**
     * Update json data referente istanza.
     *
     * @param istanzaExtendedDTOList istanzaExtendedDTOList
     * @param referentiIstanza       referentiIstanza
     */
    public void updateJsonDataReferenteIstanza(List<IstanzaExtendedDTO> istanzaExtendedDTOList, List<ReferenteIstanzaDTO> referentiIstanza) {
        logBeginInfo(className, "istanzaExtendedDTOList\n" + istanzaExtendedDTOList + "\nreferentiIstanza :\n" + referentiIstanza);
        if (null == istanzaExtendedDTOList) {
            logError(className, ERR_INASPETTATO);
            logEnd(className);
        } else if (istanzaExtendedDTOList.isEmpty()) {
            logEnd(className);
        } else {
            IstanzaExtendedDTO istanzaExt = istanzaExtendedDTOList.get(0);
            String jsonData = istanzaExt.getJsonData();
            try {
                JSONObject jsonDataObject = new JSONObject(jsonData);
                List<JSONObject> jsonObjectList = new ArrayList<>();
                for (ReferenteIstanzaDTO referenteIstanzaExtendedDTO : referentiIstanza) {
                    jsonObjectList.add(referenteIstanzaExtendedDTO.toJsonObj());
                }

                if (jsonDataObject.has("QDR_OGGETTO")) {
                    jsonDataObject.getJSONObject("QDR_OGGETTO").put("referenti", jsonObjectList);
                } else {
                    JSONObject jsonDataObjectVuoto = new JSONObject("{}");
                    jsonDataObject.put("QDR_OGGETTO", jsonDataObjectVuoto);
                    jsonDataObject.getJSONObject("QDR_OGGETTO").put("referenti", jsonObjectList);
                }
                istanzaExt.setJsonData(jsonDataObject.toString());
                istanzaDAO.updateJsonDataIstanza(istanzaExt.getDTO());
            } catch (Exception e) {
                logError(className, e);
                logEnd(className);
            }
        }
    }

    /**
     * Update json data istanza competenza territorio.
     *
     * @param idIstanza             the id istanza
     * @param istanzaCompetenzaList the istanza competenza list
     */
    public void updateJsonDataIstanzaCompetenzaTerritorio(Long idIstanza, List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList) {
        logBeginInfo(className, "idIstanza [" + idIstanza + "]\nistanzaCompetenzaList :\n" + istanzaCompetenzaList);
        List<IstanzaExtendedDTO> list = istanzaDAO.loadIstanza(idIstanza);
        if (null == list) {
            logError(className, ERR_INASPETTATO);
            logEnd(className);
        } else if (list.isEmpty()) {
            logError(className, getErrElementoNonTrovato(idIstanza));
            logEnd(className);
        } else {
            IstanzaExtendedDTO istanzaExt = list.get(0);
            String jsonData = istanzaExt.getJsonData();
            try {
                JSONObject jsonDataObject = new JSONObject(jsonData);
                List<JSONObject> jsonObjectList = new ArrayList<>();
                if (istanzaCompetenzaList != null && !istanzaCompetenzaList.isEmpty()) {
                    for (IstanzaCompetenzaExtendedDTO istanzaCompetenza : istanzaCompetenzaList) {
                        JSONObject jsonIstanzaCompetenza = istanzaCompetenza.toJsonObj();
                        jsonObjectList.add(jsonIstanzaCompetenza);
                    }
                    if (jsonDataObject.has("QDR_ISTANZA")) {
                        jsonDataObject.getJSONObject("QDR_ISTANZA").put("competenze_territorio", jsonObjectList);
                    } else {
                        JSONObject jsonDataObjectVuoto = new JSONObject("{}");
                        jsonDataObject.put("QDR_ISTANZA", jsonDataObjectVuoto);
                        jsonDataObject.getJSONObject("QDR_ISTANZA").put("competenze_territorio", jsonObjectList);
                    }
                }
                istanzaExt.setJsonData(jsonDataObject.toString());
                istanzaDAO.updateJsonDataIstanza(istanzaExt.getDTO());
            } catch (Exception e) {
                logError(className, e);
                logEnd(className);
            }
        }
    }

    /**
     * Gets class from json data.
     *
     * @param <T>             the type parameter
     * @param jsonDataIstanza the json data istanza
     * @param jsonTag         the json tag
     * @param type            the type
     * @return the class from json data
     */
    public <T> T getClassFromJsonData(String jsonDataIstanza, String jsonTag, TypeReference<T> type) {
        logBegin(className);
        try {
            String jsonFragment = searchValueFromJsonData(jsonDataIstanza, jsonTag);
            return StringUtils.isNotBlank(jsonFragment) ? new ObjectMapper().readValue(jsonFragment, type) : null;
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return null;
    }

    /**
     * Generate json data from map json object.
     *
     * @param idIstanza the id istanza
     * @return the json object
     * @throws ParseException the parse exception
     */
    public org.json.simple.JSONObject generateJsonDataFromConfig(Long idIstanza) throws ParseException {
        logBeginInfo(className, idIstanza);
        return generateJsonDataFromConfig(idIstanza, null);
    }

    /**
     * Generate json data from config org . json . simple . json object.
     *
     * @param idIstanza     the id istanza
     * @param codTipoQuadro the cod tipo quadro
     * @return the org . json . simple . json object
     * @throws ParseException the parse exception
     */
    public org.json.simple.JSONObject generateJsonDataFromConfig(Long idIstanza, String codTipoQuadro) throws ParseException {
        logBeginInfo(className, idIstanza);
        // recupero istanza
        IstanzaDTO istanza = istanzaDAO.findByPK(idIstanza);
        // recupero quadri legati al template utilizzato per l'istanza
        List<QuadroExtendedDTO> quadroList = istanza != null ? quadroDAO.loadQuadroByIdTemplate(istanza.getIdTemplate(), null) : null;
        // recupero dalla tabella di mapping le jsonQuery associate ai tipi quadri recuperati
        List<String> tipiQuadroCodes = getTipiQuadroCodesFromQuadri(quadroList);
        boolean ignoreFlgObbligo = Boolean.FALSE;
        if (StringUtils.isNotBlank(codTipoQuadro)) {
            //tipiQuadroCodes = tipiQuadroCodes.stream().filter(codTipoQuadro::equalsIgnoreCase).collect(Collectors.toList());
            tipiQuadroCodes = Collections.singletonList(codTipoQuadro);
            ignoreFlgObbligo = Boolean.TRUE;
        }
        JSONObject jsonDataIstanza = istanza != null ? new JSONObject(istanza.getJsonData()) : new JSONObject("{}");
        if (StringUtils.isNotBlank(codTipoQuadro)) {
            String jsonDataFrag = istanza != null ? searchValueFromJsonData(istanza.getJsonData(), codTipoQuadro) : "{}";
            //jsonDataIstanza = StringUtils.isNotBlank(jsonDataFrag) ? new JSONObject().put(codTipoQuadro, new JSONObject(jsonDataFrag)) : jsonDataIstanza;
            jsonDataIstanza = StringUtils.isNotBlank(jsonDataFrag) ? new JSONObject().put(codTipoQuadro, new JSONObject(jsonDataFrag)) : new JSONObject();
        }
        JSONParser parser = new JSONParser();
        if (CollectionUtils.isNotEmpty(tipiQuadroCodes)) {
            List<ConfigJsonDataDTO> configJsonDataList = configJsonDataDAO.loadConfigJsonDataByTipiQuadro(tipiQuadroCodes, ignoreFlgObbligo);
            if (configJsonDataList != null) {
                for (ConfigJsonDataDTO configJsonData : configJsonDataList) {
                    String json = getJsonFromQuery(idIstanza, configJsonData);
                    if (StringUtils.isNotBlank(json)) {
                        Object jsonObject = null; //StringUtils.isBlank(codTipoQuadro) ? new JSONTokener(json).nextValue() : new JSONObject(json);
                        if (StringUtils.isBlank(codTipoQuadro)) {
                            jsonObject = new JSONTokener(json).nextValue();
                        } else {
                            jsonObject = json.startsWith("[") ? new JSONArray(json) : new JSONObject(json);
                        }
                        //JSONObject jsonFragment = StringUtils.isBlank(codTipoQuadro) ? getJsonFragment(configJsonData, jsonObject) : new JSONObject().put(codTipoQuadro, new JSONObject(json));
                        JSONObject jsonFragment = getJsonFragment(configJsonData, jsonObject);
                        getJsonDataUpdated(jsonDataIstanza, configJsonData, json, jsonObject, jsonFragment);
                    }
                }
                cleanJsonData(jsonDataIstanza, configJsonDataList);
            }
        }

        return (org.json.simple.JSONObject) parser.parse(jsonDataIstanza.toString());
    }

    /**
     * Clean json data.
     *
     * @param jsonDataIstanza    the json data istanza
     * @param configJsonDataList the config json data list
     */
    private void cleanJsonData(JSONObject jsonDataIstanza, List<ConfigJsonDataDTO> configJsonDataList) {
        List<String> removeKeys = configJsonDataList != null ?
                configJsonDataList.stream()
                        .filter(config -> StringUtils.isBlank(config.getQueryEstrazione()))
                        .map(ConfigJsonDataDTO::getCodTipoQuadro)
                        .collect(Collectors.toList()) :
                new ArrayList<>();

        for (String key : removeKeys) {
            removeKeyFromJsonObject(jsonDataIstanza, key);
        }
    }

    /**
     * Remove key from json object.
     *
     * @param jsonDataIstanza the json data istanza
     * @param key             the key
     */
    private void removeKeyFromJsonObject(JSONObject jsonDataIstanza, String key) {
        jsonDataIstanza.remove(key);
    }

    /**
     * Gets json data updated.
     *
     * @param jsonDataIstanza the json data istanza
     * @param configJsonData  the config json data
     * @param json            the json
     * @param jsonObject      the json object
     * @param jsonFragment    the json fragment
     */
    public void getJsonDataUpdated(JSONObject jsonDataIstanza, ConfigJsonDataDTO configJsonData, String json, Object jsonObject, JSONObject jsonFragment) {
        JSONObject jsonTipoQuadro = jsonDataIstanza.optJSONObject(configJsonData.getCodTipoQuadro());
        if (jsonTipoQuadro != null) {
            if (StringUtils.isNotBlank(configJsonData.getDesTag())) {
                JSONObject jsonTagDef = jsonTipoQuadro.optJSONObject(configJsonData.getDesTag());
                if (jsonTagDef != null) {
                    jsonTagDef.put(configJsonData.getDesTag(), jsonObject);
                    jsonDataIstanza.put(configJsonData.getCodTipoQuadro(), jsonTagDef);
                } else {
                    jsonTipoQuadro.put(configJsonData.getDesTag(), jsonObject);
                    jsonDataIstanza.put(configJsonData.getCodTipoQuadro(), jsonTipoQuadro);
                }
            } else {
                JSONObject jp = new JSONObject(json);
                if (JSONObject.getNames(jp) != null) {
                    for (String key : JSONObject.getNames(jp)) {
                        jsonTipoQuadro.put(key, jp.get(key));
                    }
                }
                if (jsonTipoQuadro.toString().equals("{}")) {
                    jsonDataIstanza.put(configJsonData.getCodTipoQuadro(), jsonTipoQuadro);
                }
            }
        } else {
            if (jsonFragment != null && jsonObject != null && !jsonObject.toString().equals("{}")) {
                jsonDataIstanza.put(configJsonData.getCodTipoQuadro(), StringUtils.isNotBlank(configJsonData.getDesTag()) ? jsonFragment : jsonObject);
            }
        }
    }

    /**
     * Gets json fragment.
     *
     * @param configJsonData the config json data
     * @param jsonObject     the json object
     * @return the json fragment
     */
    public JSONObject getJsonFragment(ConfigJsonDataDTO configJsonData, Object jsonObject) {
        JSONObject jsonTag = null;
        JSONObject jsonWriteble;
        JSONObject jsonRoot = new JSONObject();
        if (StringUtils.isNotBlank(configJsonData.getDesTag())) {
            jsonTag = new JSONObject().put(configJsonData.getDesTag(), jsonObject);
            jsonWriteble = jsonRoot.put(configJsonData.getCodTipoQuadro(), jsonTag);
        } else {
            jsonTag = new JSONObject().put(configJsonData.getCodTipoQuadro(), jsonObject);
            jsonWriteble = jsonRoot.put(configJsonData.getCodTipoQuadro(), jsonObject);
        }
        configJsonDataDAO.updateJsonData(configJsonData.getIdConfigJsonData(), jsonWriteble.toString());
        return jsonTag;
    }

    /**
     * Gets json from query.
     *
     * @param idIstanza      the id istanza
     * @param configJsonData the config json data
     * @return the json from query
     */
    public String getJsonFromQuery(Long idIstanza, ConfigJsonDataDTO configJsonData) {
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        return StringUtils.isNotBlank(configJsonData.getQueryEstrazione()) ? configJsonDataDAO.loadJsonByQuery(configJsonData.getQueryEstrazione(), map) : null;
    }

    /**
     * Gets tipi quadro codes from quadri.
     *
     * @param quadroList the quadro list
     * @return the tipi quadro codes from quadri
     */
    private List<String> getTipiQuadroCodesFromQuadri(List<QuadroExtendedDTO> quadroList) {
        List<TipoQuadroDTO> tipoQuadroList = quadroList != null && !quadroList.isEmpty() ?
                quadroList.stream()
                        .map(QuadroExtendedDTO::getTipoQuadro)
                        .collect(Collectors.toList()) :
                new ArrayList<>();

        return !tipoQuadroList.isEmpty() ?
                tipoQuadroList.stream()
                        .map(TipoQuadroDTO::getCodiceTipoQuadro)
                        .collect(Collectors.toList()) :
                new ArrayList<>();
    }

    /**
     * @param oggettiIstanzaList oggettiIstanzaList
     * @return List<OggettoIstanzaUbicazioneGeoRefExtendedDTO>
     */
    private List<OggettoIstanzaUbicazioneGeoRefExtendedDTO> getOggettoUbicazioneGeoRefList(List<OggettoIstanzaExtendedDTO> oggettiIstanzaList) {
        List<OggettoIstanzaUbicazioneGeoRefExtendedDTO> oggettoIstanzaUbicazioneGeoRefList = new ArrayList<>();
        for (OggettoIstanzaExtendedDTO oggettoIstanza : oggettiIstanzaList) {
            OggettoIstanzaUbicazioneGeoRefExtendedDTO oggettoIstanzaUbicazioneGeoRef = new OggettoIstanzaUbicazioneGeoRefExtendedDTO();
            oggettoIstanzaUbicazioneGeoRef.setIdOggettoIstanza(oggettoIstanza.getIdOggettoIstanza());
            oggettoIstanzaUbicazioneGeoRef.setIdOggetto(oggettoIstanza.getOggetto().getIdOggetto());
            oggettoIstanzaUbicazioneGeoRef.setIdIstanza(oggettoIstanza.getIdIstanza());
            oggettoIstanzaUbicazioneGeoRef.setTipologiaOggetto(oggettoIstanza.getTipologiaOggetto());
            oggettoIstanzaUbicazioneGeoRef.setIndGeoStato(oggettoIstanza.getIndGeoStato());
            oggettoIstanzaUbicazioneGeoRef.setCodOggettoFonte(oggettoIstanza.getCodOggettoFonte());
            oggettoIstanzaUbicazioneGeoRef.setDenOggetto(oggettoIstanza.getDenOggetto());
            oggettoIstanzaUbicazioneGeoRef.setDesOggetto(oggettoIstanza.getDesOggetto());
            oggettoIstanzaUbicazioneGeoRef.setCoordinataX(oggettoIstanza.getCoordinataX());
            oggettoIstanzaUbicazioneGeoRef.setCoordinataY(oggettoIstanza.getCoordinataY());
            oggettoIstanzaUbicazioneGeoRef.setIdMasterdata(oggettoIstanza.getIdMasterdata());
            oggettoIstanzaUbicazioneGeoRef.setIdMasterdataOrigine(oggettoIstanza.getIdMasterdataOrigine());
            oggettoIstanzaUbicazioneGeoRef.setGestUID(oggettoIstanza.getGestUID());

            List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioniOggettoIstanzaList = ubicazioneOggettoIstanzaDAO.loadUbicazioneOggettoIstanzaByIdOggettoIstanza(oggettoIstanza.getIdOggettoIstanza());
            oggettoIstanzaUbicazioneGeoRef.setUbicazioneOggettoIstanza(ubicazioniOggettoIstanzaList);

            List<GeoAreaOggettoIstanzaDTO> geoAreaOggettoIstanza = geoAreaOggettoIstanzaDAO.loadGeoAreaOggettoIstanzaByIdOggettoIstanza(oggettoIstanza.getIdOggettoIstanza());
            oggettoIstanzaUbicazioneGeoRef.setGeoAreaOggettoIstanza(geoAreaOggettoIstanza);

            List<GeoLineaOggettoIstanzaDTO> geoLineaOggettoIstanza = geoLineaOggettoIstanzaDAO.loadGeoLineaOggettoIstanzaByIdOggettoIstanza(oggettoIstanza.getIdOggettoIstanza());
            oggettoIstanzaUbicazioneGeoRef.setGeoLineaOggettoIstanza(geoLineaOggettoIstanza);

            List<GeoPuntoOggettoIstanzaDTO> geoPuntoOggettoIstanza = geoPuntoOggettoIstanzaDAO.loadGeoPuntoOggettoIstanzaByIdOggettoIstanza(oggettoIstanza.getIdOggettoIstanza());
            oggettoIstanzaUbicazioneGeoRef.setGeoPuntoOggettoIstanza(geoPuntoOggettoIstanza);

            oggettoIstanzaUbicazioneGeoRefList.add(oggettoIstanzaUbicazioneGeoRef);
        }
        return oggettoIstanzaUbicazioneGeoRefList;
    }

    private String getParamIdIstanza(Long idIstanza) {
        if (idIstanza != null) {
            return PARAM_ID_ISTANZA.replace("__idIstanza__", idIstanza.toString());
        }
        return PARAM_ID_ISTANZA;
    }

    private String getErrElementoNonTrovato(Long idIstanza) {
        if (idIstanza != null) {
            return ELEMENTO_NON_TROVATO.replace("__idIstanza__", idIstanza.toString());
        }
        return ELEMENTO_NON_TROVATO;
    }

}