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

import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.CatastoServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.BaseFeatureGeoJSON;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.Crs;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.Crs.TypeEnum;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.CrsProperties;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.CrsProperties.NameEnum;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.Municipality;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.Parcel;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.ParcelFeatureGeoJSON;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.ParcelsFeatureCollectionGeoJSON;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.PolygonGeoJSON;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.PostRequestFeatureGeoJSON;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.PostRequestJSON;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.Section;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.Sheet;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.SheetFeatureGeoJSON;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.SheetsFeatureCollectionGeoJSON;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.Feature;
import it.csi.scriva.scrivabesrv.business.be.impl.BaseApiServiceImpl;
import it.csi.scriva.scrivabesrv.business.be.service.GeoService;
import it.csi.scriva.scrivabesrv.dto.CatastoComuneDTO;
import it.csi.scriva.scrivabesrv.dto.CatastoFoglioDTO;
import it.csi.scriva.scrivabesrv.dto.CatastoParticellaDTO;
import it.csi.scriva.scrivabesrv.dto.CatastoPostRequest;
import it.csi.scriva.scrivabesrv.dto.CatastoSezioneDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.compress.utils.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * The type Catasto manager.
 *
 * @author CSI PIEMONTE
 */
@Component
public class CatastoManager extends BaseApiServiceImpl {

    private final String classname = this.getClass().getSimpleName();

    @Autowired
    private CatastoServiceHelper catastoServiceHelper;

    @Autowired
    private GeoService geoService;

    /**
     * Load comuni list.
     *
     * @param xRequestId    the x request id
     * @param xForwardedFor the x forwarded for
     * @param candidateName the candidate name
     * @param expansionMode the expansion mode
     * @param epsg          the epsg
     * @param limit         the limit
     * @param offset        the offset
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<CatastoComuneDTO> loadComuni(String xRequestId, String xForwardedFor, String candidateName, String expansionMode, Integer epsg, Integer limit, Integer offset) throws GenericException {
        logBegin(classname);
        List<Municipality> municipalities = catastoServiceHelper.getComuni(xRequestId, xForwardedFor, candidateName, expansionMode, epsg, limit, offset);
        List<CatastoComuneDTO> comuniCatastoList = Lists.newArrayList();
        for (Municipality municipality : municipalities) {
            CatastoComuneDTO dto = new CatastoComuneDTO();
            dto.setComune(municipality.getComune());
            dto.setCodComIstat(municipality.getCodComIstat());
            dto.setCodComBelfiore(municipality.getCodComBelfiore());
            dto.setSiglaProvincia(municipality.getSiglaProvincia());
            dto.setIstatProvincia(municipality.getIstatProvincia());
            comuniCatastoList.add(dto);
        }
        logEnd(classname);
        return comuniCatastoList;
    }

    /**
     * Load sezioni per comune list.
     *
     * @param xRequestId    the x request id
     * @param xForwardedFor the x forwarded for
     * @param codiceComune  the codice comune
     * @param epsg          the epsg
     * @param limit         the limit
     * @param offset        the offset
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<CatastoSezioneDTO> loadSezioniPerComune(String xRequestId, String xForwardedFor, String codiceComune, Integer epsg, Integer limit, Integer offset) throws GenericException {
        logBegin(classname);
        List<Section> sectionList = catastoServiceHelper.getSezioniPerComune(xRequestId, xForwardedFor, codiceComune, epsg, limit, offset);
        List<CatastoSezioneDTO> sezioniCatastoList = Lists.newArrayList();
        for (Section section : sectionList) {
            CatastoSezioneDTO dto = getCatastoSezioneDTO(section);
            sezioniCatastoList.add(dto);
        }
        LOGGER.debug("[CatastoManager::loadSezioniPerComune] BEGIN");
        return sezioniCatastoList;
    }

    /**
     * Load sezione list.
     *
     * @param xRequestId    the x request id
     * @param xForwardedFor the x forwarded for
     * @param codiceComune  the codice comune
     * @param codiceSezione the codice sezione
     * @param epsg          the epsg
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<CatastoSezioneDTO> loadSezione(String xRequestId, String xForwardedFor, String codiceComune, String codiceSezione, Integer epsg) throws GenericException {
        logBegin(classname);
        Section section = catastoServiceHelper.getSezione(xRequestId, xForwardedFor, codiceComune, codiceSezione, epsg);
        CatastoSezioneDTO dto = getCatastoSezioneDTO(section);
        List<CatastoSezioneDTO> sezioniList = Lists.newArrayList();
        sezioniList.add(dto);
        LOGGER.debug("[CatastoApiServiceImpl::loadSezione] END");
        return sezioniList;
    }

    private CatastoSezioneDTO getCatastoSezioneDTO(Section section) {
        CatastoComuneDTO comuneDTO = new CatastoComuneDTO();
        comuneDTO.setComune(section.getComune());
        comuneDTO.setCodComIstat(section.getCodComIstat());
        comuneDTO.setCodComBelfiore(section.getCodComBelfiore());
        CatastoSezioneDTO dto = new CatastoSezioneDTO();
        dto.setComune(comuneDTO);
        dto.setSezione(section.getSezione());
        dto.setNomeSezione(section.getNomeSezione());
        return dto;
    }

    /**
     * Load fogli per comune per sezione list.
     *
     * @param xRequestId    the x request id
     * @param xForwardedFor the x forwarded for
     * @param codiceComune  the codice comune
     * @param codiceSezione the codice sezione
     * @param epsg          the epsg
     * @param limit         the limit
     * @param offset        the offset
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<CatastoFoglioDTO> loadFogliPerComunePerSezione(String xRequestId, String xForwardedFor, String codiceComune, String codiceSezione, Integer epsg, Integer limit, Integer offset) throws GenericException {
        logBegin(classname);
        SheetsFeatureCollectionGeoJSON sheetsFeatureCollectionGeoJSON = catastoServiceHelper.getFogliPerComunePerSezione(xRequestId, xForwardedFor, codiceComune, codiceSezione, epsg, limit, offset);
        List<CatastoFoglioDTO> fogliList = fillCatastoFoglioList(sheetsFeatureCollectionGeoJSON);
        LOGGER.debug("[CatastoApiServiceImpl::loadFogliPerComunePerSezione] END");
        return fogliList;
    }

    /**
     * Load foglio list.
     *
     * @param xRequestId    the x request id
     * @param xForwardedFor the x forwarded for
     * @param codiceComune  the codice comune
     * @param codiceSezione the codice sezione
     * @param numeroFoglio  the numero foglio
     * @param epsg          the epsg
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<CatastoFoglioDTO> loadFoglio(String xRequestId, String xForwardedFor, String codiceComune, String codiceSezione, String numeroFoglio, Integer epsg) throws GenericException {
        logBegin(classname);
        SheetFeatureGeoJSON feature = catastoServiceHelper.getFoglio(xRequestId, xForwardedFor, codiceComune, codiceSezione, numeroFoglio, epsg);
        List<CatastoFoglioDTO> fogliList = Lists.newArrayList();
        CatastoFoglioDTO dto = getCatastoFoglioDTO(feature);
        fogliList.add(dto);
        LOGGER.debug("[CatastoApiServiceImpl::loadFoglio] END");
        return fogliList;
    }

    /**
     * Load fogli intersecanti geometria list.
     *
     * @param xRequestId       the x request id
     * @param xForwardedFor    the x forwarded for
     * @param idOggettoIstanza the id oggetto istanza
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<CatastoFoglioDTO> loadFogliIntersecantiGeometria(String xRequestId, String xForwardedFor, Long idOggettoIstanza) throws GenericException {
        logBegin(classname);
        List<String> coords = geoService.loadCoordinateGeoAreeByOggettoIstanza(idOggettoIstanza);
        Set<CatastoFoglioDTO> fogliSet = Sets.newHashSet();
        for (String coord : coords) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<List<List<BigDecimal>>> coordinates;
            try {
                coordinates = objectMapper.readValue(coord, List.class);
            } catch (IOException e) {
                throw new GenericException("Error converting geojson : " + e.getMessage());
            }
            CatastoPostRequest catastoPostRequest = new CatastoPostRequest();
            catastoPostRequest.setCoordinates(coordinates);
            PostRequestJSON postRequestJSON = fillPostRequest(catastoPostRequest);
            SheetsFeatureCollectionGeoJSON sheetsFeatureCollectionGeoJSON = catastoServiceHelper.getFogliIntersecantiGeometria(xRequestId, xForwardedFor, postRequestJSON);
            List<CatastoFoglioDTO> fogliList = fillCatastoFoglioList(sheetsFeatureCollectionGeoJSON);
            fogliSet.addAll(fogliList);
        }
        logEnd(classname);
        return new ArrayList<>(fogliSet);
    }

    /**
     * Load fogli contenuti geometria list.
     *
     * @param xRequestId       the x request id
     * @param xForwardedFor    the x forwarded for
     * @param idOggettoIstanza the id oggetto istanza
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<CatastoFoglioDTO> loadFogliContenutiGeometria(String xRequestId, String xForwardedFor, Long idOggettoIstanza) throws GenericException {
        logBegin(classname);
        List<String> coords = geoService.loadCoordinateGeoAreeByOggettoIstanza(idOggettoIstanza);
        Set<CatastoFoglioDTO> fogliSet = Sets.newHashSet();
        for (String coord : coords) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<List<List<BigDecimal>>> coordinates = null;
            try {
                coordinates = objectMapper.readValue(coord, List.class);
            } catch (IOException e) {
                throw new GenericException("Error converting geojson : " + e.getMessage());
            }
            CatastoPostRequest catastoPostRequest = new CatastoPostRequest();
            catastoPostRequest.setCoordinates(coordinates);
            PostRequestJSON postRequestJSON = fillPostRequest(catastoPostRequest);
            SheetsFeatureCollectionGeoJSON sheetsFeatureCollectionGeoJSON = catastoServiceHelper.getFogliContenutiGeometria(xRequestId, xForwardedFor, postRequestJSON);
            List<CatastoFoglioDTO> fogliList = fillCatastoFoglioList(sheetsFeatureCollectionGeoJSON);
            fogliSet.addAll(fogliList);
        }
        logEnd(classname);
        return new ArrayList<>(fogliSet);
    }

    /**
     * Load particelle per foglio per sezione per comune list.
     *
     * @param xRequestId    the x request id
     * @param xForwardedFor the x forwarded for
     * @param codiceComune  the codice comune
     * @param codiceSezione the codice sezione
     * @param numeroFoglio  the numero foglio
     * @param epsg          the epsg
     * @param limit         the limit
     * @param offset        the offset
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<CatastoParticellaDTO> loadParticellePerFoglioPerSezionePerComune(String xRequestId, String xForwardedFor, String codiceComune, String codiceSezione, String numeroFoglio, Integer epsg, Integer limit, Integer offset) throws GenericException {
        logBegin(classname);
        ParcelsFeatureCollectionGeoJSON parcelsFeatureCollectionGeoJSON = catastoServiceHelper.getParticellePerFoglioPerSezionePerComune(xRequestId, xForwardedFor, codiceComune, codiceSezione, numeroFoglio, epsg, limit, offset);
        List<CatastoParticellaDTO> particelleList = fillCatastoParticellaList(parcelsFeatureCollectionGeoJSON);
        logEnd(classname);
        return particelleList;
    }


    /**
     * Load particella list.
     *
     * @param xRequestId       the x request id
     * @param xForwardedFor    the x forwarded for
     * @param codiceComune     the codice comune
     * @param codiceSezione    the codice sezione
     * @param numeroFoglio     the numero foglio
     * @param numeroParticella the numero particella
     * @param epsg             the epsg
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<CatastoParticellaDTO> loadParticella(String xRequestId, String xForwardedFor, String codiceComune, String codiceSezione, String numeroFoglio, String numeroParticella, Integer epsg) throws GenericException {
        logBegin(classname);
        ParcelFeatureGeoJSON feature = catastoServiceHelper.getParticella(xRequestId, xForwardedFor, codiceComune, codiceSezione, numeroFoglio, numeroParticella, epsg);
        List<CatastoParticellaDTO> particelleList = Lists.newArrayList();
        CatastoParticellaDTO dto = getCatastoParticella(feature);
        particelleList.add(dto);
        logEnd(classname);
        return particelleList;
    }

    /**
     * Load particelle intersecanti geometria list.
     *
     * @param xRequestId       the x request id
     * @param xForwardedFor    the x forwarded for
     * @param idOggettoIstanza the id oggetto istanza
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<CatastoParticellaDTO> loadParticelleIntersecantiGeometria(String xRequestId, String xForwardedFor, Long idOggettoIstanza) throws GenericException, IOException {
        logBegin(classname);
        List<String> coordsList = geoService.loadCoordinateGeoAreeByOggettoIstanza(idOggettoIstanza);

        Set<CatastoParticellaDTO> particelleSet = getCatastoParticellaSet(xRequestId, xForwardedFor, coordsList, null);
        return new ArrayList<>(particelleSet);
    }

    /**
     * Gets catasto particella set.
     *
     * @param xRequestId    the x request id
     * @param xForwardedFor the x forwarded for
     * @param coordsList    the coords list
     * @param geecoFeature  the geeco feature
     * @return the catasto particella set
     * @throws GenericException the generic exception
     * @throws IOException      the io exception
     */
    private Set<CatastoParticellaDTO> getCatastoParticellaSet(String xRequestId, String xForwardedFor, List<String> coordsList, Feature geecoFeature) throws GenericException, IOException {
        logBegin(classname);
        Set<CatastoParticellaDTO> particelleSet = Sets.newHashSet();
        PostRequestJSON postRequestJSON = null;
        if (CollectionUtils.isNotEmpty(coordsList)) {
        	
	        for(int n=0; n<coordsList.size(); n++) {

	            ObjectMapper objectMapper = new ObjectMapper();
	            List<List<List<BigDecimal>>> coordinates = null;
	            try {
	            	if(coordsList.get(n) == null)
	            		return particelleSet;
	            	else 
	            		coordinates = objectMapper.readValue(coordsList.get(n), List.class);
	            } catch (IOException e) {
	                throw new GenericException("Error converting geojson : " + e.getMessage());
	            }
	            CatastoPostRequest catastoPostRequest = new CatastoPostRequest();
	            catastoPostRequest.setCoordinates(coordinates);
	            postRequestJSON = fillPostRequest(catastoPostRequest);
	
		        ParcelsFeatureCollectionGeoJSON parcelsFeatureCollectionGeoJSON = catastoServiceHelper.getParticelleIntersecantiGeometria(xRequestId, xForwardedFor, postRequestJSON);
		        List<CatastoParticellaDTO> particelleList = fillCatastoParticellaList(parcelsFeatureCollectionGeoJSON);
		        particelleSet.addAll(particelleList);
	        }

        }
        else {
	       	 postRequestJSON = fillPostRequest(geecoFeature);
	    	 
	         ParcelsFeatureCollectionGeoJSON parcelsFeatureCollectionGeoJSON = catastoServiceHelper.getParticelleIntersecantiGeometria(xRequestId, xForwardedFor, postRequestJSON);
	         List<CatastoParticellaDTO> particelleList = fillCatastoParticellaList(parcelsFeatureCollectionGeoJSON);
	         particelleSet.addAll(particelleList);
        }
        logEnd(classname);
        return particelleSet;
    }

    /**
     * Load particelle intersecanti geometria list.
     *
     * @param xRequestId    the x request id
     * @param xForwardedFor the x forwarded for
     * @param geecoFeature  the geeco feature
     * @return the list
     * @throws GenericException the generic exception
     * @throws IOException      the io exception
     */
    public List<CatastoParticellaDTO> loadParticelleIntersecantiGeometria(String xRequestId, String xForwardedFor, Feature geecoFeature) throws GenericException, IOException {
        logBegin(classname);
        Set<CatastoParticellaDTO> particelleSet = getCatastoParticellaSet(xRequestId, xForwardedFor, null, geecoFeature);
        logEnd(classname);
        return new ArrayList<>(particelleSet);
    }

    /**
     * Load particelle contenute geometria list.
     *
     * @param xRequestId       the x request id
     * @param xForwardedFor    the x forwarded for
     * @param idOggettoIstanza the id oggetto istanza
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<CatastoParticellaDTO> loadParticelleContenuteGeometria(String xRequestId, String xForwardedFor, Long idOggettoIstanza) throws GenericException {
        logBegin(classname);
        List<String> coords = geoService.loadCoordinateGeoAreeByOggettoIstanza(idOggettoIstanza);
        Set<CatastoParticellaDTO> particelleSet = Sets.newHashSet();
        for (String coord : coords) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<List<List<BigDecimal>>> coordinates = null;
            try {
                coordinates = objectMapper.readValue(coord, List.class);
            } catch (IOException e) {
                throw new GenericException("Error converting geojson : " + e.getMessage());
            }
            CatastoPostRequest catastoPostRequest = new CatastoPostRequest();
            catastoPostRequest.setCoordinates(coordinates);
            PostRequestJSON postRequestJSON = fillPostRequest(catastoPostRequest);
            ParcelsFeatureCollectionGeoJSON parcelsFeatureCollectionGeoJSON = catastoServiceHelper.getParticelleContenuteGeometria(xRequestId, xForwardedFor, postRequestJSON);
            List<CatastoParticellaDTO> particelleList = fillCatastoParticellaList(parcelsFeatureCollectionGeoJSON);
            particelleSet.addAll(particelleList);
        }
        logEnd(classname);
        return new ArrayList<>(particelleSet);
    }

    /**
     * Fill catasto particella dto list list.
     *
     * @param parcelsFeatureCollectionGeoJSON the parcels feature collection geo json
     * @return the list
     */
    private List<CatastoParticellaDTO> fillCatastoParticellaList(ParcelsFeatureCollectionGeoJSON parcelsFeatureCollectionGeoJSON) {
        List<ParcelFeatureGeoJSON> features = parcelsFeatureCollectionGeoJSON.getFeatures();
        List<CatastoParticellaDTO> particelleList = Lists.newArrayList();
        if (features != null) {
            for (ParcelFeatureGeoJSON feature : features) {
                CatastoParticellaDTO dto = getCatastoParticella(feature);
                particelleList.add(dto);
            }
        }
        return particelleList;
    }

    /**
     * Gets catasto particella dto.
     *
     * @param feature the feature
     * @return the catasto particella dto
     */
    private CatastoParticellaDTO getCatastoParticella(ParcelFeatureGeoJSON feature) {
        logBegin(classname);
        CatastoParticellaDTO dto = new CatastoParticellaDTO();
        CatastoComuneDTO comuneDTO = new CatastoComuneDTO();
        Parcel parcel = feature.getProperties();
        comuneDTO.setComune(parcel.getComune());
        comuneDTO.setCodComIstat(parcel.getCodComIstat());
        comuneDTO.setCodComBelfiore(parcel.getCodComBelfiore());
        dto.setComune(comuneDTO);

        dto.setSezione(parcel.getSezione());
        dto.setFoglio(parcel.getFoglio());
        dto.setParticella(parcel.getParticella());
        dto.setIdGeoParticella(parcel.getIdGeoParticella());
        dto.setAggiornatoAl(parcel.getAggiornatoAl());
        dto.setAllegato(parcel.getAllegato());
        dto.setSviluppo(parcel.getSviluppo());

        /*
        PolygonGeoJSON polygonGeoJSON = feature.getGeometry();
        if (null != polygonGeoJSON){
            String geometryType = polygonGeoJSON.getType().getValue();
            dto.setTipoGeometria(geometryType);
            List<List<List<BigDecimal>>> coordinates = polygonGeoJSON.getCoordinates().get(0);
            dto.setCoordinates(coordinates);
        }
        */
        logEnd(classname);
        return dto;
    }

    /**
     * Fill post request post request json.
     *
     * @param catastoPostRequest the catasto post request
     * @return the post request json
     */
    private PostRequestJSON fillPostRequest(CatastoPostRequest catastoPostRequest) {
        logBegin(classname);
        PostRequestFeatureGeoJSON postRequestFeatureGeoJSON = new PostRequestFeatureGeoJSON();
        postRequestFeatureGeoJSON.setType(BaseFeatureGeoJSON.TypeEnum.Feature);
        postRequestFeatureGeoJSON.setProperties(new HashMap<>());

        PolygonGeoJSON polygonGeoJSON = new PolygonGeoJSON();
        polygonGeoJSON.setType(PolygonGeoJSON.TypeEnum.Polygon);
        polygonGeoJSON.setCoordinates(catastoPostRequest.getCoordinates());
        postRequestFeatureGeoJSON.setGeometry(polygonGeoJSON);

        postRequestFeatureGeoJSON.setCrs(getCrs());

        PostRequestJSON postRequestJSON = new PostRequestJSON();
        postRequestJSON.setFeature(postRequestFeatureGeoJSON);
        postRequestJSON.setLimit(catastoPostRequest.getLimit());
        postRequestJSON.setOffset(catastoPostRequest.getOffset());
        logEnd(classname);
        return postRequestJSON;
    }

    /**
     * Fill post request post request json.
     *
     * @param feature the feature
     * @return the post request json
     * @throws IOException the io exception
     */
    private PostRequestJSON fillPostRequest(Feature feature) throws IOException {
        logBegin(classname);
        ObjectMapper mapper = new ObjectMapper();
        String jsonFeature = mapper.writer().writeValueAsString(feature);
        PostRequestFeatureGeoJSON postRequestFeatureGeoJSON = mapper.readValue(jsonFeature, PostRequestFeatureGeoJSON.class);
        postRequestFeatureGeoJSON.setCrs(getCrs());
        PostRequestJSON postRequestJSON = new PostRequestJSON();
        postRequestJSON.setFeature(postRequestFeatureGeoJSON);
        postRequestJSON.setLimit(BigDecimal.ZERO);
        postRequestJSON.setOffset(BigDecimal.ZERO);
        logEnd(classname);
        return postRequestJSON;
    }

    /**
     * Gets crs.
     *
     * @return the crs
     */
    private Crs getCrs() {
        logBegin(classname);
        Crs crs = new Crs();
        crs.setType(TypeEnum.name);
        CrsProperties crsProperties = new CrsProperties();
        crsProperties.setName(NameEnum.EPSG_32632.getValue());
        crs.setProperties(crsProperties);
        logEnd(classname);
        return crs;
    }

    /**
     * Gets catasto foglio dto.
     *
     * @param feature the feature
     * @return the catasto foglio dto
     */
    private CatastoFoglioDTO getCatastoFoglioDTO(SheetFeatureGeoJSON feature) {
        logBegin(classname);
        CatastoFoglioDTO dto = new CatastoFoglioDTO();
        CatastoComuneDTO comuneDTO = new CatastoComuneDTO();
        Sheet sheet = feature.getProperties();
        comuneDTO.setComune(sheet.getComune());
        comuneDTO.setCodComIstat(sheet.getCodComIstat());
        comuneDTO.setCodComBelfiore(sheet.getCodComBelfiore());
        dto.setComune(comuneDTO);

        dto.setSezione(sheet.getSezione());
        dto.setFoglio(sheet.getFoglio());
        dto.setIdGeoFoglio(sheet.getIdGeoFoglio());
        dto.setAreagisHa(sheet.getAreagisHa());
        dto.setAggiornatoAl(sheet.getAggiornatoAl());
        dto.setAllegato(sheet.getAllegato());
        dto.setSviluppo(sheet.getSviluppo());

        /*
        PolygonGeoJSON polygonGeoJSON = feature.getGeometry();
        if (null != polygonGeoJSON) {
            String geometryType = polygonGeoJSON.getType().getValue();
            dto.setTipoGeometria(geometryType);
            List<List<List<List<BigDecimal>>>> coordinates = polygonGeoJSON.getCoordinates();
            dto.setCoordinates(coordinates);
        }
         */
        logEnd(classname);
        return dto;
    }

    /**
     * Fill catasto foglio dto list list.
     *
     * @param sheetsFeatureCollectionGeoJSON the sheets feature collection geo json
     * @return the list
     */
    private List<CatastoFoglioDTO> fillCatastoFoglioList(SheetsFeatureCollectionGeoJSON sheetsFeatureCollectionGeoJSON) {
        logBegin(classname);
        List<CatastoFoglioDTO> fogliList = Lists.newArrayList();
        List<SheetFeatureGeoJSON> features = sheetsFeatureCollectionGeoJSON.getFeatures();
        for (SheetFeatureGeoJSON feature : features) {
            CatastoFoglioDTO dto = getCatastoFoglioDTO(feature);
            fogliList.add(dto);
        }
        logEnd(classname);
        return fogliList;
    }
}