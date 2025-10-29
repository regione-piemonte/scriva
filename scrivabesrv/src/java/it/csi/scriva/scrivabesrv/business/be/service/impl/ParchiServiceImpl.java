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

import it.csi.scriva.scrivabesrv.business.SpringApplicationContextHelper;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.ParkApiServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.areeprotette.AreaProtettaFiltriEstesi;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.rn2000.Rn2000;
import it.csi.scriva.scrivabesrv.business.be.impl.BaseApiServiceImpl;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.CompetenzaTerritorioDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoAreaProtettaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoNatura2000DAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.UbicazioneOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.ParchiService;
import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioDTO;
import it.csi.scriva.scrivabesrv.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ConfigurazioneDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaAreaProtettaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaNatura2000ExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAreaProtettaDTO;
import it.csi.scriva.scrivabesrv.dto.TipoNatura2000DTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoIstanzaExtendedDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Parchi service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class ParchiServiceImpl extends BaseApiServiceImpl implements ParchiService {

    private final String className = this.getClass().getSimpleName();

    private List<OggettoIstanzaAreaProtettaExtendedDTO> oggettoIstanzaAreaProtettaList = Collections.emptyList();
    private List<OggettoIstanzaNatura2000ExtendedDTO> oggettoIstanzaNatura2000List = Collections.emptyList();

    /**
     * The Competenza territorio dao.
     */
    @Autowired
    CompetenzaTerritorioDAO competenzaTerritorioDAO;

    /**
     * The Configurazione dao.
     */
    @Autowired
    ConfigurazioneDAO configurazioneDAO;

    /**
     * The Geo oggetto istanza dao.
     */
    @Autowired
    GeoOggettoIstanzaDAO geoOggettoIstanzaDAO;

    /**
     * The Istanza dao.
     */
    @Autowired
    IstanzaDAO istanzaDAO;

    /**
     * The Tipo area protetta dao.
     */
    @Autowired
    TipoAreaProtettaDAO tipoAreaProtettaDAO;

    /**
     * The Tipo natura 2000 dao.
     */
    @Autowired
    TipoNatura2000DAO tipoNatura2000DAO;

    /**
     * The Ubicazione oggetto istanza dao.
     */
    @Autowired
    UbicazioneOggettoIstanzaDAO ubicazioneOggettoIstanzaDAO;

    /**
     * The Park api service helper.
     */
    @Autowired
    ParkApiServiceHelper parkApiServiceHelper;


    private ParchiServiceImpl() {
        initializeBeans();
        try {
            this.getAreeProtette();
            this.getSitiNatura2000();
        } catch (GenericException e) {
            logError(className, e);
        }
    }

    /**
     * Initialize beans.
     */
    private void initializeBeans() {
        this.competenzaTerritorioDAO = (CompetenzaTerritorioDAO) SpringApplicationContextHelper.getBean("competenzaTerritorioDAO");
        this.tipoAreaProtettaDAO = (TipoAreaProtettaDAO) SpringApplicationContextHelper.getBean("tipoAreaProtettaDAO");
        this.tipoNatura2000DAO = (TipoNatura2000DAO) SpringApplicationContextHelper.getBean("tipoNatura2000DAO");
        this.parkApiServiceHelper = (ParkApiServiceHelper) SpringApplicationContextHelper.getBean("parkApiServiceHelper"); //chiamata duplicata. Il parkApiServiceHelper è gia stato inizializzato al bootstrap tramite costruttore esplicito nella classe
    }

    /**
     * Gets aree protette.
     *
     * @return the aree protette
     * @throws GenericException the generic exception
     */
    @Override
    public List<OggettoIstanzaAreaProtettaExtendedDTO> getAreeProtette() throws GenericException {
        logBegin(className);
        try {
            if (CollectionUtils.isEmpty(oggettoIstanzaAreaProtettaList)) {
                List<AreaProtettaFiltriEstesi> areaProtettaFiltriEstesiList = parkApiServiceHelper.getAreaProtetta();
                oggettoIstanzaAreaProtettaList = getOggettoIstanzaAreaProtettaFromAreaProtettaResult(null, areaProtettaFiltriEstesiList);
            }
        } catch (GenericException e) {
            logError(className, e.getError());
             throw e;
        } catch (Exception e) {
            // CHIAMATA CORRETTA AL MANAGER PER OTTENERE UN ErrorDTO
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            GenericException ge = new GenericException(error);
            ge.initCause(e);
            throw ge;
        } finally {
            logEnd(className);
        }
        return oggettoIstanzaAreaProtettaList;
    }

    /**
     * Gets aree protette by cod istat comuni.
     *
     * @param codIstatComuni the cod istat comuni
     * @return the aree protette by cod istat comuni
     * @throws GenericException the generic exception
     */
    @Override
    public List<OggettoIstanzaAreaProtettaExtendedDTO> getAreeProtetteByCodIstatComuni(String codIstatComuni) throws GenericException {
        logBeginInfo(className, "codIstatComuni : [" + codIstatComuni + "]");
        List<OggettoIstanzaAreaProtettaExtendedDTO> result = null;
        try {
            if (StringUtils.isNotBlank(codIstatComuni)) {
                List<AreaProtettaFiltriEstesi> areaProtettaFiltriEstesiList = parkApiServiceHelper.getAreaProtetta(Arrays.asList(codIstatComuni.split("\\s*,\\s*")));
                result = getOggettoIstanzaAreaProtettaFromAreaProtettaResult(null, areaProtettaFiltriEstesiList);
            }
        } catch (GenericException e) {
            logError(className, e.getError());
            throw new GenericException(e.getError());
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets aree protette.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param checkComuni      the check comuni
     * @return the aree protette
     * @throws GenericException the generic exception
     */
    @Override
    public List<OggettoIstanzaAreaProtettaExtendedDTO> getAreeProtette(Long idOggettoIstanza, Boolean checkComuni) throws GenericException {
        logBeginInfo(className, idOggettoIstanza);
        List<OggettoIstanzaAreaProtettaExtendedDTO> result = null;
        try {
            result = getAreeProtetteByGeometry(idOggettoIstanza);
            if (CollectionUtils.isEmpty(result) && Boolean.TRUE.equals(checkComuni)) {
                result = getAreeProtetteByOggIstCodIstatComuni(idOggettoIstanza);
            } else {
                result = CollectionUtils.isEmpty(result) ? new ArrayList<>() : result;
            }
        } catch (GenericException e) {
            logError(className, e.getError());
            throw new GenericException(e.getError());
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets aree protette by geometry.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the aree protette by geometry
     */
    @Override
    public List<OggettoIstanzaAreaProtettaExtendedDTO> getAreeProtetteByGeometry(Long idOggettoIstanza) throws GenericException {
        logBeginInfo(className, idOggettoIstanza);
        List<OggettoIstanzaAreaProtettaExtendedDTO> result = null;
        try {
            String geometryGML = geoOggettoIstanzaDAO.loadGMLGeometryByIdOggettoIstanza(idOggettoIstanza);
            if (StringUtils.isNotBlank(geometryGML)) {
                List<AreaProtettaFiltriEstesi> areaProtettaFiltriEstesiList = parkApiServiceHelper.getAreaProtetta(geometryGML);
                result = getOggettoIstanzaAreaProtettaFromAreaProtettaResult(idOggettoIstanza, areaProtettaFiltriEstesiList);
            }
        } catch (GenericException e) {
            logError(className, e.getError());
            throw new GenericException(e.getError());
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets aree protette by ogg ist cod istat comuni.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the aree protette by cod istat comuni
     * @throws GenericException the generic exception
     */
    @Override
    public List<OggettoIstanzaAreaProtettaExtendedDTO> getAreeProtetteByOggIstCodIstatComuni(Long idOggettoIstanza) throws GenericException {
        logBeginInfo(className, idOggettoIstanza);
        List<OggettoIstanzaAreaProtettaExtendedDTO> result = null;
        try {
            List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaList = ubicazioneOggettoIstanzaDAO.loadUbicazioneOggettoIstanzaByIdOggettoIstanza(idOggettoIstanza);
            if (CollectionUtils.isNotEmpty(ubicazioneOggettoIstanzaList)) {
                List<String> codiciIstatComuniUbicazione = ubicazioneOggettoIstanzaList.stream()
                        .map(UbicazioneOggettoIstanzaExtendedDTO::getComune)
                        .map(ComuneExtendedDTO::getCodIstatComune)
                        .collect(Collectors.toList());
                List<AreaProtettaFiltriEstesi> areaProtettaFiltriEstesiList = parkApiServiceHelper.getAreaProtetta(codiciIstatComuniUbicazione);
                result = getOggettoIstanzaAreaProtettaFromAreaProtettaResult(idOggettoIstanza, areaProtettaFiltriEstesiList);
            }
        } catch (GenericException e) {
            logError(className, e.getError());
            throw new GenericException(e.getError());
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets siti natura 2000.
     *
     * @return the siti natura 2000
     * @throws GenericException the generic exception
     */
    @Override
    public List<OggettoIstanzaNatura2000ExtendedDTO> getSitiNatura2000() throws GenericException {
        logBegin(className);
        try {
            if (CollectionUtils.isEmpty(oggettoIstanzaNatura2000List)) {
                List<Rn2000> rn2000List = parkApiServiceHelper.getRn2000();
                oggettoIstanzaNatura2000List = getOggettoIstanzaRn2000FromRn2000Result(null, rn2000List);
            }
        } catch (GenericException e) {
            logError(className, e.getError());
            throw e;
        } catch (Exception e) {
            logError(className, e);
            // CHIAMATA CORRETTA AL MANAGER PER OTTENERE UN ErrorDTO
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            GenericException ge = new GenericException(error);
            ge.initCause(e);
            throw ge;
        } finally {
            logEnd(className);
        }
        return oggettoIstanzaNatura2000List;
    }

    /**
     * Gets siti natura 2000 by cod istat comuni.
     *
     * @param codIstatComuni the cod istat comuni
     * @return the siti natura 2000 by cod istat comuni
     * @throws GenericException the generic exception
     */
    @Override
    public List<OggettoIstanzaNatura2000ExtendedDTO> getSitiNatura2000ByCodIstatComuni(String codIstatComuni) throws GenericException {
        logBeginInfo(className, "codIstatComuni : [" + codIstatComuni + "]");
        List<OggettoIstanzaNatura2000ExtendedDTO> result = null;
        try {
            if (StringUtils.isNotBlank(codIstatComuni)) {
                List<Rn2000> rn2000List = parkApiServiceHelper.getRn2000(Arrays.asList(codIstatComuni.split("\\s*,\\s*")));
                result = getOggettoIstanzaRn2000FromRn2000Result(null, rn2000List);
            }
        } catch (GenericException e) {
            logError(className, e.getError());
            throw new GenericException(e.getError());
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets siti natura 2000.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param checkComuni      the check comuni
     * @return the siti natura 2000
     * @throws GenericException the generic exception
     */
    @Override
    public List<OggettoIstanzaNatura2000ExtendedDTO> getSitiNatura2000(Long idOggettoIstanza, Boolean checkComuni) throws GenericException {
        logBeginInfo(className, idOggettoIstanza);
        List<OggettoIstanzaNatura2000ExtendedDTO> result = null;
        try {
            result = getSitiNatura2000ByGeometry(idOggettoIstanza);
            if (CollectionUtils.isEmpty(result) && Boolean.TRUE.equals(checkComuni)) {
                result = getSitiNatura2000ByCodIstatComuni(idOggettoIstanza);
            } else {
                result = CollectionUtils.isEmpty(result) ? new ArrayList<>() : result;
            }
        } catch (GenericException e) {
            logError(className, e.getError());
            throw new GenericException(e.getError());
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets siti natura 2000 by cod istat comuni.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the siti natura 2000 by cod istat comuni
     */
    @Override
    public List<OggettoIstanzaNatura2000ExtendedDTO> getSitiNatura2000ByCodIstatComuni(Long idOggettoIstanza) throws GenericException {
        logBeginInfo(className, idOggettoIstanza);
        List<OggettoIstanzaNatura2000ExtendedDTO> result = null;
        try {
            List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaList = ubicazioneOggettoIstanzaDAO.loadUbicazioneOggettoIstanzaByIdOggettoIstanza(idOggettoIstanza);
            if (CollectionUtils.isNotEmpty(ubicazioneOggettoIstanzaList)) {
                List<String> codiciIstatComuniUbicazione = ubicazioneOggettoIstanzaList.stream()
                        .map(UbicazioneOggettoIstanzaExtendedDTO::getComune)
                        .map(ComuneExtendedDTO::getCodIstatComune)
                        .collect(Collectors.toList());
                List<Rn2000> rn2000List = parkApiServiceHelper.getRn2000(codiciIstatComuniUbicazione);
                result = getOggettoIstanzaRn2000FromRn2000Result(idOggettoIstanza, rn2000List);
            }
        } catch (GenericException e) {
            logError(className, e.getError());
            throw new GenericException(e.getError());
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }

        return result;
    }

    /**
     * Gets siti natura 2000 by geometry.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the siti natura 2000 by geometry
     */
    @Override
    public List<OggettoIstanzaNatura2000ExtendedDTO> getSitiNatura2000ByGeometry(Long idOggettoIstanza) throws GenericException {
        logBeginInfo(className, idOggettoIstanza);
        List<OggettoIstanzaNatura2000ExtendedDTO> result = null;
        try {
            String geometryGML = geoOggettoIstanzaDAO.loadGMLGeometryByIdOggettoIstanza(idOggettoIstanza);
            if (StringUtils.isNotBlank(geometryGML)) {
                List<Rn2000> rn2000List = parkApiServiceHelper.getRn2000(geometryGML);
                result = getOggettoIstanzaRn2000FromRn2000Result(idOggettoIstanza, rn2000List);
            }
        } catch (GenericException e) {
            logError(className, e.getError());
            throw new GenericException(e.getError());
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }

        return result;
    }

    /**
     * Gets oggetto istanza area protetta from area protetta result.
     *
     * @param idOggettoIstanza             the id oggetto istanza
     * @param areaProtettaFiltriEstesiList the area protetta filtri estesi list
     * @return the oggetto istanza area protetta from area protetta result
     * @throws GenericException the generic exception
     */
    public List<OggettoIstanzaAreaProtettaExtendedDTO> getOggettoIstanzaAreaProtettaFromAreaProtettaResult(Long idOggettoIstanza, List<AreaProtettaFiltriEstesi> areaProtettaFiltriEstesiList) throws GenericException {
        logBegin(className);
        List<OggettoIstanzaAreaProtettaExtendedDTO> result = new ArrayList<>();
        try {
            if (CollectionUtils.isNotEmpty(areaProtettaFiltriEstesiList)) {
                for (AreaProtettaFiltriEstesi areaProtettaFiltriEstesi : areaProtettaFiltriEstesiList) {
                    if (!"999".equalsIgnoreCase(areaProtettaFiltriEstesi.getCodiceEnteGestore())) {// && !"413".equalsIgnoreCase(areaProtettaFiltriEstesi.getCodiceEnteGestore())) {
                        OggettoIstanzaAreaProtettaExtendedDTO oggettoIstanzaAreaProtetta = new OggettoIstanzaAreaProtettaExtendedDTO();
                        oggettoIstanzaAreaProtetta.setIdOggettoIstanza(idOggettoIstanza);
                        oggettoIstanzaAreaProtetta.setCompetenzaTerritorio(getCompetenzaTerritorio(areaProtettaFiltriEstesi.getCodiceEnteGestore(), idOggettoIstanza, areaProtettaFiltriEstesi.getEnteGestore(), areaProtettaFiltriEstesi.getDenominazione()));
                        oggettoIstanzaAreaProtetta.setTipoAreaProtetta(getTipoAreaProtetta(areaProtettaFiltriEstesi.getCodiceTipoAreaProtetta(), idOggettoIstanza, null, areaProtettaFiltriEstesi.getDenominazione()));
                        oggettoIstanzaAreaProtetta.setCodAmministrativo(areaProtettaFiltriEstesi.getCodiceAmministrativo());
                        oggettoIstanzaAreaProtetta.setCodGestoreFonte(areaProtettaFiltriEstesi.getCodiceEnteGestore());
                        oggettoIstanzaAreaProtetta.setDesAreaProtetta(areaProtettaFiltriEstesi.getDenominazione());
                        oggettoIstanzaAreaProtetta.setFlgRicade(areaProtettaFiltriEstesi.getPercentualeRicadenzaGeometriaInAp() > 0 ? Boolean.TRUE : Boolean.FALSE);
                        result.add(oggettoIstanzaAreaProtetta);
                    }
                }
            }
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets oggetto istanza rete natura 2000 from rete natura 2000 result.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param rn2000List       the rn 2000 list
     * @return the oggetto istanza area protetta from area protetta result
     * @throws GenericException the generic exception
     */
    public List<OggettoIstanzaNatura2000ExtendedDTO> getOggettoIstanzaRn2000FromRn2000Result(Long idOggettoIstanza, List<Rn2000> rn2000List) throws GenericException {
        logBegin(className);
        List<OggettoIstanzaNatura2000ExtendedDTO> result = new ArrayList<>();
        try {
            if (CollectionUtils.isNotEmpty(rn2000List)) {
                for (Rn2000 rn2000 : rn2000List) {
                    if (!"999".equalsIgnoreCase(rn2000.getCodiceEnteGestore())) {
                        OggettoIstanzaNatura2000ExtendedDTO oggettoIstanzaNatura2000 = new OggettoIstanzaNatura2000ExtendedDTO();
                        oggettoIstanzaNatura2000.setIdOggettoIstanza(idOggettoIstanza);
                        oggettoIstanzaNatura2000.setCompetenzaTerritorio(getCompetenzaTerritorio(rn2000.getCodiceEnteGestore(), idOggettoIstanza, rn2000.getEnteGestore(), rn2000.getDenominazione()));
                        oggettoIstanzaNatura2000.setTipoNatura2000(getTipoRn2000(rn2000.getCodiceTipoRn2000(), idOggettoIstanza, null, rn2000.getDenominazione()));
                        oggettoIstanzaNatura2000.setCodAmministrativo(rn2000.getCodiceAmministrativo());
                        oggettoIstanzaNatura2000.setCodGestoreFonte(rn2000.getCodiceEnteGestore());
                        oggettoIstanzaNatura2000.setDesSitoNatura2000(rn2000.getDenominazione());
                        oggettoIstanzaNatura2000.setFlgRicade(rn2000.getPercentualeRicadenzaGeometriaInRn2000() > 0 ? Boolean.TRUE : Boolean.FALSE);
                        result.add(oggettoIstanzaNatura2000);
                    }
                }
            }
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets competenza territorio object.
     *
     * @param codiceEnteGestore the codice ente gestore
     * @return the id competenza territorio
     * @throws GenericException the generic exception
     */
    private CompetenzaTerritorioDTO getCompetenzaTerritorio(String codiceEnteGestore, Long idOggettoIstanza, String desCompetenzaTerritorioFonte, String desSitoArea) throws GenericException {
        logBegin(className);
        List<CompetenzaTerritorioDTO> competenzaTerritorioList = null;
        if (StringUtils.isNotBlank(codiceEnteGestore)) {
            competenzaTerritorioList = competenzaTerritorioDAO.loadCompetenzaTerritorioByCodiceGestoreParchi(codiceEnteGestore);
        }
        if (CollectionUtils.isEmpty(competenzaTerritorioList)) {
            ErrorDTO error = getErrorManager().getError("500", "E072", null, null, null);
            setPlaceHolderValues(error, idOggettoIstanza, desCompetenzaTerritorioFonte, desSitoArea);
            throw new GenericException(error);
        }
        return competenzaTerritorioList.get(0);
    }

    /**
     * Gets tipo area protetta.
     *
     * @param codiceTipoAreaProtetta the codice tipo area protetta
     * @return the id tipo area protetta
     * @throws GenericException the generic exception
     */
    private TipoAreaProtettaDTO getTipoAreaProtetta(String codiceTipoAreaProtetta, Long idOggettoIstanza, String desCompetenzaTerritorioFonte, String desSitoArea) throws GenericException {
        logBegin(className);
        List<TipoAreaProtettaDTO> tipoAreaProtettaList = null;
        if (StringUtils.isNotBlank(codiceTipoAreaProtetta)) {
            tipoAreaProtettaList = tipoAreaProtettaDAO.loadTipoAreaProtettaByCode(codiceTipoAreaProtetta);
        }
        if (CollectionUtils.isEmpty(tipoAreaProtettaList)) {
            ErrorDTO error = getErrorManager().getError("500", "E073", null, null, null);
            setPlaceHolderValues(error, idOggettoIstanza, desCompetenzaTerritorioFonte, desSitoArea);
            throw new GenericException(error);
        }
        return tipoAreaProtettaList.get(0);
    }

    /**
     * Gets tipo natura 2000.
     *
     * @param codiceTipoRn2000 the codice tipo rete natura 2000
     * @return the id tipo rete natura 2000
     * @throws GenericException the generic exception
     */
    private TipoNatura2000DTO getTipoRn2000(String codiceTipoRn2000, Long idOggettoIstanza, String desCompetenzaTerritorioFonte, String desSitoArea) throws GenericException {
        logBegin(className);
        List<TipoNatura2000DTO> tipoNatura2000List = null;
        if (StringUtils.isNotBlank(codiceTipoRn2000)) {
            tipoNatura2000List = tipoNatura2000DAO.loadTipoNatura2000ByCode(codiceTipoRn2000);
        }
        if (CollectionUtils.isEmpty(tipoNatura2000List)) {
            ErrorDTO error = getErrorManager().getError("500", "E073", null, null, null);
            setPlaceHolderValues(error, idOggettoIstanza, desCompetenzaTerritorioFonte, desSitoArea);
            throw new GenericException(error);
        }
        return tipoNatura2000List.get(0);
    }

    /**
     * Sets place holder values.
     *
     * @param error                        the error
     * @param idOggettoIstanza             the id oggetto istanza
     * @param desCompetenzaTerritorioFonte the des competenza territorio fonte
     * @param desSitoArea                  the des sito area
     */
    public void setPlaceHolderValues(ErrorDTO error, Long idOggettoIstanza, String desCompetenzaTerritorioFonte, String desSitoArea) {
        logBegin(className);
        List<IstanzaExtendedDTO> istanzaList = Collections.emptyList();

        if (error != null) {
            String errorTitle = error.getTitle();

            if (StringUtils.isNotBlank(errorTitle)) {
                if (idOggettoIstanza != null && idOggettoIstanza > 0) 
                {
                     istanzaList = istanzaDAO.loadIstanzaByIdOggettoIstanza(idOggettoIstanza);
                }
                
                errorTitle = errorTitle.replace("{PH_DES_SITO_AREA}",  desSitoArea != null ? desSitoArea : "");
                errorTitle = errorTitle.replace("{PH_DES_COMPETENZA_TERRITORIO_FONTE}",  desCompetenzaTerritorioFonte != null ? desCompetenzaTerritorioFonte : "");
                
                if (CollectionUtils.isNotEmpty(istanzaList)) {
                    List<ConfigurazioneDTO> configurazioneList = configurazioneDAO.loadConfigByKey("SCRIVA_EMAIL_ASSISTENZA_" + istanzaList.get(0).getAdempimento().getTipoAdempimento().getCodTipoAdempimento());
                    if (CollectionUtils.isNotEmpty(configurazioneList)) {
                        errorTitle = errorTitle.replace("{PH_SCRIVA_EMAIL_ASSISTENZA_<COD_TIPO_ADEMPIMENTO>}", configurazioneList.get(0).getValore());
                    }
                }
                errorTitle = errorTitle.replace("{PH_SCRIVA_EMAIL_ASSISTENZA_<COD_TIPO_ADEMPIMENTO>}", "");
                error.setTitle(errorTitle);
            }
        }
    }

}