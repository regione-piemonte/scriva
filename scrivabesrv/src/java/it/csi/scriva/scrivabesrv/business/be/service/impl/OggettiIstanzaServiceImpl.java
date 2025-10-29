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

import it.csi.scriva.scrivabesrv.business.be.impl.BaseApiServiceImpl;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.CatastoUbicazioneOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoAreaOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoLineaOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoPuntoOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaAreaProtettaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaFiglioDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaNatura2000DAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaVincoloAutorizzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.UbicazioneOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.OggettiIstanzaService;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.CatastoUbicazioneOggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.GeecoFeature;
import it.csi.scriva.scrivabesrv.dto.GeoOggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaAreaProtettaDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaAreaProtettaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaFiglioDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaGeecoDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaNatura2000DTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaNatura2000ExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaUbicazioneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaUbicazioneGeometrieExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaVincoloAutorizzaDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaVincoloAutorizzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.ValidationResultEnum;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.manager.IstanzaAttoreManager;
import it.csi.scriva.scrivabesrv.util.manager.JsonDataManager;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The type Oggetti istanza service.
 */
@Component
public class OggettiIstanzaServiceImpl extends BaseApiServiceImpl implements OggettiIstanzaService {

    private final String className = this.getClass().getSimpleName();

    private static final String VISIBILE = "visibile";

    @Autowired
    private CatastoUbicazioneOggettoIstanzaDAO catastoUbicazioneOggettoIstanzaDAO;

    @Autowired
    private GeoAreaOggettoIstanzaDAO geoAreaOggettoIstanzaDAO;

    @Autowired
    private GeoPuntoOggettoIstanzaDAO geoPuntoOggettoIstanzaDAO;

    @Autowired
    private GeoLineaOggettoIstanzaDAO geoLineaOggettoIstanzaDAO;

    @Autowired
    private GeoOggettoIstanzaDAO GeoOggettoIstanzaDAO;


    @Autowired
    private IstanzaDAO istanzaDAO;

    @Autowired
    private OggettoDAO oggettoDAO;

    @Autowired
    private OggettoIstanzaDAO oggettoIstanzaDAO;

    @Autowired
    private OggettoIstanzaFiglioDAO oggettoIstanzaFiglioDAO;

    @Autowired
    private OggettoIstanzaNatura2000DAO oggettoIstanzaNatura2000DAO;

    @Autowired
    private OggettoIstanzaAreaProtettaDAO oggettoIstanzaAreaProtettaDAO;

    @Autowired
    private OggettoIstanzaVincoloAutorizzaDAO oggettoIstanzaVincoloAutorizzaDAO;

    @Autowired
    private UbicazioneOggettoIstanzaDAO ubicazioneOggettoIstanzaDAO;

    @Autowired
    private IstanzaAttoreManager istanzaAttoreManager;

    @Autowired
    private JsonDataManager jsonDataManager;

    /**
     * Load oggetti istanza list.
     *
     * @return the list
     */
    @Override
    public List<OggettoIstanzaUbicazioneExtendedDTO> loadOggettiIstanza() {
        logBegin(className);
        List<OggettoIstanzaUbicazioneExtendedDTO> oggettoIstanzaUbicazioneList = null;
        try {
            List<OggettoIstanzaExtendedDTO> oggettiIstanzaList = oggettoIstanzaDAO.loadOggettiIstanza();
            oggettoIstanzaUbicazioneList = getOggettoUbicazioneList(oggettiIstanzaList);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return oggettoIstanzaUbicazioneList;
    }

    /**
     * Load oggetto istanza by id istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    @Override
    public List<OggettoIstanzaUbicazioneExtendedDTO> loadOggettoIstanzaByIdIstanza(Long idIstanza) {
        logBegin(className);
        List<OggettoIstanzaUbicazioneExtendedDTO> oggettoIstanzaUbicazioneList = null;
        try {
            List<OggettoIstanzaExtendedDTO> oggettiIstanzaList = oggettoIstanzaDAO.loadOggettoIstanzaByIdIstanza(idIstanza);
            oggettoIstanzaUbicazioneList = getOggettoUbicazioneList(oggettiIstanzaList);
            setOggettoIstanzaNatura2000(oggettoIstanzaUbicazioneList);
            setOggettoIstanzaAreaProtetta(oggettoIstanzaUbicazioneList);
            setOggettoIstanzaVincoloAutorizza(oggettoIstanzaUbicazioneList);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return oggettoIstanzaUbicazioneList;
    }

    /**
     * Load oggetto istanza by id list.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the list
     */
    @Override
    public List<OggettoIstanzaUbicazioneExtendedDTO> loadOggettoIstanzaById(Long idOggettoIstanza) {
        logBegin(className);
        List<OggettoIstanzaUbicazioneExtendedDTO> oggettoIstanzaUbicazioneList = null;
        try {
            List<OggettoIstanzaExtendedDTO> oggettiIstanzaList = oggettoIstanzaDAO.loadOggettoIstanzaById(idOggettoIstanza);
            oggettoIstanzaUbicazioneList = getOggettoUbicazioneList(oggettiIstanzaList);
            setOggettoIstanzaNatura2000(oggettoIstanzaUbicazioneList);
            setOggettoIstanzaAreaProtetta(oggettoIstanzaUbicazioneList);
            setOggettoIstanzaVincoloAutorizza(oggettoIstanzaUbicazioneList);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return oggettoIstanzaUbicazioneList;
    }

    /**
     * Load oggetto istanza by id con geometrie.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the list
     */

    @Override
    public List<OggettoIstanzaUbicazioneGeometrieExtendedDTO> loadOggettoIstanzaWithGeometriesById(Long idOggettoIstanza) {
        logBegin(className);
        List<OggettoIstanzaUbicazioneGeometrieExtendedDTO> OggettoIstanzaUbicazioneGeometrieList = new ArrayList<>();

        try {
            // Carica la lista originale
            List<OggettoIstanzaExtendedDTO> oggettiIstanzaList = oggettoIstanzaDAO.loadOggettoIstanzaById(idOggettoIstanza);
            List<OggettoIstanzaUbicazioneExtendedDTO> oggettoIstanzaUbicazioneList = getOggettoUbicazioneList(oggettiIstanzaList);

            // Completa le informazioni
            setOggettoIstanzaNatura2000(oggettoIstanzaUbicazioneList);
            setOggettoIstanzaAreaProtetta(oggettoIstanzaUbicazioneList);
            setOggettoIstanzaVincoloAutorizza(oggettoIstanzaUbicazioneList);

            // Recupera le geometrie
            List<GeoOggettoIstanzaDTO> geometrieOggettoIstanzaList = GeoOggettoIstanzaDAO.getOggetti(idOggettoIstanza);

            // Trasforma ogni oggetto in OggettoIstanzaUbicazioneGeometrieExtendedDTO
            for (OggettoIstanzaUbicazioneExtendedDTO item : oggettoIstanzaUbicazioneList) {
                OggettoIstanzaUbicazioneGeometrieExtendedDTO OggettoIstanzaUbicazioneGeometrie = new OggettoIstanzaUbicazioneGeometrieExtendedDTO();

                // Copia i dati dall'oggetto originale
                OggettoIstanzaUbicazioneGeometrie.setIdOggettoIstanza(item.getIdOggettoIstanza());
                OggettoIstanzaUbicazioneGeometrie.setIdOggetto(item.getIdOggetto());
                OggettoIstanzaUbicazioneGeometrie.setIdIstanza(item.getIdIstanza());
                OggettoIstanzaUbicazioneGeometrie.setTipologiaOggetto(item.getTipologiaOggetto());
                OggettoIstanzaUbicazioneGeometrie.setIndGeoStato(item.getIndGeoStato());
                OggettoIstanzaUbicazioneGeometrie.setDenOggetto(item.getDenOggetto());
                OggettoIstanzaUbicazioneGeometrie.setDesOggetto(item.getDesOggetto());
                OggettoIstanzaUbicazioneGeometrie.setCoordinataX(item.getCoordinataX());
                OggettoIstanzaUbicazioneGeometrie.setCoordinataY(item.getCoordinataY());
                OggettoIstanzaUbicazioneGeometrie.setIdMasterdata(item.getIdMasterdata());
                OggettoIstanzaUbicazioneGeometrie.setIdMasterdataOrigine(item.getIdMasterdataOrigine());
                OggettoIstanzaUbicazioneGeometrie.setGestUID(item.getGestUID());
                OggettoIstanzaUbicazioneGeometrie.setCodOggettoFonte(item.getCodOggettoFonte());
                OggettoIstanzaUbicazioneGeometrie.setCodUtenza(item.getCodUtenza());
                OggettoIstanzaUbicazioneGeometrie.setNoteAttoPrecedente(item.getNoteAttoPrecedente());
                OggettoIstanzaUbicazioneGeometrie.setFlgGeoModificato(item.getFlgGeoModificato());
                OggettoIstanzaUbicazioneGeometrie.setIndLivello(item.getIndLivello());
                OggettoIstanzaUbicazioneGeometrie.setTipologiaOggetto(item.getTipologiaOggetto());
                OggettoIstanzaUbicazioneGeometrie.setUbicazioneOggettoIstanza(item.getUbicazioneOggettoIstanza());
                OggettoIstanzaUbicazioneGeometrie.setOggettoIstanzaNatura2000List(item.getOggettoIstanzaNatura2000List());
                OggettoIstanzaUbicazioneGeometrie.setOggettoIstanzaAreaProtettaList(item.getOggettoIstanzaAreaProtettaList());
                OggettoIstanzaUbicazioneGeometrie.setOggettoIstanzaVincoloAutorizzaList(item.getOggettoIstanzaVincoloAutorizzaList());
                OggettoIstanzaUbicazioneGeometrie.setIdOggettoIstanzaPadre(item.getIdOggettoIstanzaPadre());

                // Aggiungi le geometrie
                OggettoIstanzaUbicazioneGeometrie.setGeometrieOggettoIstanzaList(geometrieOggettoIstanzaList);

                // Aggiungi alla lista finale
                OggettoIstanzaUbicazioneGeometrieList.add(OggettoIstanzaUbicazioneGeometrie);
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return OggettoIstanzaUbicazioneGeometrieList;
    }

    /**
     * Load oggetto by uid list.
     *
     * @param uidOggettoIstanza the uid oggetto istanza
     * @return the list
     */
    @Override
    public List<OggettoIstanzaUbicazioneExtendedDTO> loadOggettoIstanzaByUID(String uidOggettoIstanza) {
        logBegin(className);
        List<OggettoIstanzaUbicazioneExtendedDTO> oggettoIstanzaUbicazioneList = null;
        try {
            List<OggettoIstanzaExtendedDTO> oggettiIstanzaList = oggettoIstanzaDAO.loadOggettoIstanzaByUID(uidOggettoIstanza);
            oggettoIstanzaUbicazioneList = getOggettoUbicazioneList(oggettiIstanzaList);
            setOggettoIstanzaNatura2000(oggettoIstanzaUbicazioneList);
            setOggettoIstanzaAreaProtetta(oggettoIstanzaUbicazioneList);
            setOggettoIstanzaVincoloAutorizza(oggettoIstanzaUbicazioneList);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return oggettoIstanzaUbicazioneList;
    }

    /**
     * Save oggetto istanza long.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param attoreScriva   the attore scriva
     * @return the long
     */
    @Override
    public Long saveOggettoIstanza(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva) {
        logBegin(className);
        Long idOggettoIstanza = null;
        try {
            if (oggettoIstanza != null) {
                if (StringUtils.isBlank(oggettoIstanza.getGestAttoreIns())) {
                    oggettoIstanza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                }
                Long idIstanzaAttoreCompilante = ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ?
                        istanzaAttoreManager.getIdIstanzaAttoreCompilante(Constants.CF_COMPILANTE_FITTIZIO_BO, oggettoIstanza.getIdIstanza(), ComponenteAppEnum.FO, attoreScriva.getCodiceFiscale()) :
                        istanzaAttoreManager.getIdIstanzaAttoreCompilante(attoreScriva.getCodiceFiscale(), oggettoIstanza.getIdIstanza(), ComponenteAppEnum.findByDescr(attoreScriva.getComponente()), attoreScriva.getCodiceFiscale());
                oggettoIstanza.setIdIstanzaAttore(idIstanzaAttoreCompilante);

                idOggettoIstanza = oggettoIstanzaDAO.saveOggettoIstanza(oggettoIstanza.getDTO());
                // Inserimento delle ubicazioni dell'oggetto
                if (idOggettoIstanza != null && CollectionUtils.isNotEmpty(oggettoIstanza.getUbicazioneOggettoIstanza())) {
                    Date now = Calendar.getInstance().getTime();
                    oggettoIstanza.setIdOggettoIstanza(idOggettoIstanza);
                    Long idUbicazione = saveUbicazioniOggettoIstanza(idOggettoIstanza, oggettoIstanza.getUbicazioneOggettoIstanza(), attoreScriva);
                    //saveCatastoUbicazione(oggettoIstanza, attoreScriva, now);
                    saveOggNatura2000(oggettoIstanza, attoreScriva, now);
                    saveOggAreaProtetta(oggettoIstanza, attoreScriva, now);
                    saveOggVincoloAut(oggettoIstanza, attoreScriva, now);
                    if (oggettoIstanza.getIdOggettoIstanzaPadre() != null) {
                        saveOggettoIstanzaFiglio(oggettoIstanza, oggettoIstanza.getIdOggettoIstanzaPadre(), attoreScriva.getCodiceFiscale());
                    }
                    idOggettoIstanza = idUbicazione != null ? idOggettoIstanza : null;
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return idOggettoIstanza;
    }

    /**
     * Update oggetto istanza integer.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param attoreScriva   the attore scriva
     * @return the integer
     */
    @Override
    public Integer updateOggettoIstanza(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva) {
        String inputParam = "Parametro in input oggettoIstanza \n" + oggettoIstanza + "\n";
        logBeginInfo(className, inputParam);
        Integer resUpdateOggettoIstanza = null;
        try {
            if (oggettoIstanza != null) {
                if (StringUtils.isBlank(oggettoIstanza.getGestAttoreUpd())) {
                    oggettoIstanza.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
                }
                Long idIstanzaAttoreCompilante = istanzaAttoreManager.getIdIstanzaAttoreCompilante(attoreScriva.getCodiceFiscale(), oggettoIstanza.getIdIstanza(), ComponenteAppEnum.findByDescr(attoreScriva.getComponente()), attoreScriva.getCodiceFiscale());
                oggettoIstanza.setIdIstanzaAttore(idIstanzaAttoreCompilante);
                resUpdateOggettoIstanza = oggettoIstanzaDAO.updateOggettoIstanza(oggettoIstanza.getDTO());
                Date now = Calendar.getInstance().getTime();
                updateUbicazioniOggettoIstanza(oggettoIstanza, attoreScriva, now);
                updateOggNatura2000(oggettoIstanza, attoreScriva, now);
                updateOggAreaProtetta(oggettoIstanza, attoreScriva, now);
                updateOggVincoloAut(oggettoIstanza, attoreScriva, now);
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return resUpdateOggettoIstanza;
    }
    
    
    /**
     * Update oggetto istanza integer.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param attoreScriva   the attore scriva
     * @return the integer
     */
    @Override
    public Integer updateOggettoIstanzaWithComuni(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva, List<ComuneExtendedDTO> comuniList) {
        String inputParam = "Parametro in input oggettoIstanza \n" + oggettoIstanza + "\n";
        logBeginInfo(className, inputParam);
        Integer resUpdateOggettoIstanza = null;
        try {
            if (oggettoIstanza != null) {
                if (StringUtils.isBlank(oggettoIstanza.getGestAttoreUpd())) {
                    oggettoIstanza.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
                }
                Long idIstanzaAttoreCompilante = istanzaAttoreManager.getIdIstanzaAttoreCompilante(attoreScriva.getCodiceFiscale(), oggettoIstanza.getIdIstanza(), ComponenteAppEnum.findByDescr(attoreScriva.getComponente()), attoreScriva.getCodiceFiscale());
                oggettoIstanza.setIdIstanzaAttore(idIstanzaAttoreCompilante);
                resUpdateOggettoIstanza = oggettoIstanzaDAO.updateOggettoIstanza(oggettoIstanza.getDTO());
                Date now = Calendar.getInstance().getTime();
                updateUbicazioniOggettoIstanzaWithComuni(oggettoIstanza, attoreScriva, now, comuniList);
                updateOggNatura2000(oggettoIstanza, attoreScriva, now);
                updateOggAreaProtetta(oggettoIstanza, attoreScriva, now);
                updateOggVincoloAut(oggettoIstanza, attoreScriva, now);
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return resUpdateOggettoIstanza;
    }

    /**
     * Delete oggetto istanza integer.
     *
     * @param uidOggettoIstanza the uid oggetto istanza
     * @return the integer
     */
    @Override
    public Integer deleteOggettoIstanza(String uidOggettoIstanza) {
        return null;
    }

    /**
     * Validate dto error dto.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param isUpdate       the is update
     * @return the error dto
     */
    @Override
    public ErrorDTO validateDTO(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, Boolean isUpdate) {
        ErrorDTO error = null;
        Map<String, String> details = new HashMap<>();

        if (null == oggettoIstanza.getIdOggetto()) {
            details.put("id_oggetto", ValidationResultEnum.MANDATORY.getDescription());
        }

        if (null == oggettoIstanza.getIdIstanza() || oggettoIstanza.getIdIstanza() < 1) {
            details.put("id_istanza", ValidationResultEnum.MANDATORY.getDescription());
        }

        if (oggettoIstanza.getTipologiaOggetto() == null || (oggettoIstanza.getTipologiaOggetto() != null && oggettoIstanza.getTipologiaOggetto().getIdTipologiaOggetto() == null)) {
            if (oggettoIstanza.getIdOggetto() != null) {
                List<OggettoExtendedDTO> oggettoList = oggettoDAO.loadOggettoById(oggettoIstanza.getIdOggetto());
                if (oggettoList != null && !oggettoList.isEmpty()) {
                    oggettoIstanza.setTipologiaOggetto(oggettoList.get(0).getTipologiaOggetto());
                } else {
                    details.put("tipologia_oggetto", ValidationResultEnum.MANDATORY.getDescription());
                }
            } else {
                details.put("tipologia_oggetto", ValidationResultEnum.MANDATORY.getDescription());
            }
        }

        if (StringUtils.isBlank(oggettoIstanza.getDenOggetto())) {
            details.put("den_oggetto", ValidationResultEnum.MANDATORY.getDescription());
        } else if (oggettoIstanza.getDenOggetto().length() > 500) {
            details.put("den_oggetto", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (StringUtils.isBlank(oggettoIstanza.getDesOggetto())) {
            details.put("des_oggetto", ValidationResultEnum.MANDATORY.getDescription());
        } else if (oggettoIstanza.getDesOggetto().length() > 2000) {
            details.put("des_oggetto", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (null == oggettoIstanza.getIdMasterdata()) {
            details.put("id_masterdata", ValidationResultEnum.MANDATORY.getDescription());
        }

        if (null == oggettoIstanza.getIdMasterdataOrigine()) {
            details.put("id_masterdata_origine", ValidationResultEnum.MANDATORY.getDescription());
        }

        if (StringUtils.isNotBlank(oggettoIstanza.getIndGeoStato()) && oggettoIstanza.getIndGeoStato().length() > 1) {
            details.put("ind_geo_stato", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (!details.isEmpty()) {
            error = getErrorManager().getError("500", "E004", "I dati inseriti non sono corretti.", details, null);
        } else {
            List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioni = oggettoIstanza.getUbicazioneOggettoIstanza();
            if (CollectionUtils.isNotEmpty(ubicazioni)) {
                for (UbicazioneOggettoIstanzaExtendedDTO ubica : ubicazioni) {

                    if (null == ubica.getComune() || (null != ubica.getComune() && null == ubica.getComune().getIdComune())) {
                        details.put("comune", ValidationResultEnum.MANDATORY.getDescription());
                    }

                    if (StringUtils.isNotBlank(ubica.getDenIndirizzo()) && ubica.getDenIndirizzo().length() > 100) {
                        details.put("den_indirizzo", ValidationResultEnum.INVALID_LENGTH.getDescription());
                    }

                    if (StringUtils.isNotBlank(ubica.getNumCivico()) && ubica.getNumCivico().length() > 30) {
                        details.put("num_civico", ValidationResultEnum.INVALID_LENGTH.getDescription());
                    }

                    if (StringUtils.isNotBlank(ubica.getDesLocalita()) && ubica.getDesLocalita().length() > 250) {
                        details.put("des_localita", ValidationResultEnum.INVALID_LENGTH.getDescription());
                    }

                    if (StringUtils.isNotBlank(ubica.getIndGeoProvenienza()) && ubica.getIndGeoProvenienza().length() > 4) {
                        details.put("ind_geo_provenienza", ValidationResultEnum.INVALID_LENGTH.getDescription());
                    }

                    if (!details.isEmpty()) {
                        error = getErrorManager().getError("500", "E004", "I dati inseriti non sono corretti.", details, null);
                        break;
                    }

                }
            }
        }
        return error;
    }

    /**
     * Save ubicazioni oggetto istanza long.
     *
     * @param idOggettoIstanza             the id oggetto istanza
     * @param ubicazioneOggettoIstanzaList the ubicazione oggetto istanza list
     * @param attoreScriva                 the attore scriva
     * @return the long
     */
    @Override
    public Long saveUbicazioniOggettoIstanza(Long idOggettoIstanza, List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaList, AttoreScriva attoreScriva) {
        logBegin(className);
        Long idUbicazione = null;
        try {
            if (idOggettoIstanza != null && ubicazioneOggettoIstanzaList != null && !ubicazioneOggettoIstanzaList.isEmpty()) {
                for (UbicazioneOggettoIstanzaExtendedDTO ubicazioneOggettoIstanza : ubicazioneOggettoIstanzaList) {
                    ubicazioneOggettoIstanza.setIdOggettoIstanza(idOggettoIstanza);
                    if (StringUtils.isBlank(ubicazioneOggettoIstanza.getGestAttoreIns())) {
                        ubicazioneOggettoIstanza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                    }
                    idUbicazione = ubicazioneOggettoIstanzaDAO.saveUbicazioneOggettoIstanza(ubicazioneOggettoIstanza.getDTO());
                    if (idUbicazione == null) {
                        // Cancellazione di tutti i record creati
                        ubicazioneOggettoIstanzaDAO.deleteUbicazioneOggettoIstanzaById(idOggettoIstanza);
                        oggettoIstanzaDAO.deleteOggettoById(idOggettoIstanza);
                        break;
                    }
                    ubicazioneOggettoIstanza.setIdUbicazioneOggettoIstanza(idUbicazione);
                    saveCatastoUbicazione(ubicazioneOggettoIstanza, attoreScriva, null);
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return idUbicazione;
    }

    /**
     * Save ogg natura 2000 long.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param attoreScriva   the attore scriva
     * @param now            the now
     * @return the long
     */
    @Override
    public Long saveOggNatura2000(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva, Date now) {
        logBegin(className);
        Long idOggettoIstanzaNatura2000 = null;
        if (oggettoIstanza != null && oggettoIstanza.getOggettoIstanzaNatura2000List() != null && !oggettoIstanza.getOggettoIstanzaNatura2000List().isEmpty() && attoreScriva != null) {
            for (OggettoIstanzaNatura2000ExtendedDTO oggettoIstanzaNatura2000Ext : oggettoIstanza.getOggettoIstanzaNatura2000List()) {
                OggettoIstanzaNatura2000DTO oggettoIstanzaNatura2000 = oggettoIstanzaNatura2000Ext.getDTO();
                oggettoIstanzaNatura2000.setIdOggettoIstanza(oggettoIstanza.getIdOggettoIstanza());
                if (StringUtils.isBlank(oggettoIstanzaNatura2000.getGestAttoreIns())) {
                    oggettoIstanzaNatura2000.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                }
                idOggettoIstanzaNatura2000 = oggettoIstanzaNatura2000DAO.saveOggettoIstanzaNatura2000(oggettoIstanzaNatura2000);
            }
        }
        logEnd(className);
        return idOggettoIstanzaNatura2000;
    }

    /**
     * Save ogg area protetta long.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param attoreScriva   the attore scriva
     * @param now            the now
     * @return the long
     */
    @Override
    public Long saveOggAreaProtetta(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva, Date now) {
        logBegin(className);
        Long idOggettoAreaProtetta = null;
        if (oggettoIstanza != null && oggettoIstanza.getOggettoIstanzaAreaProtettaList() != null && !oggettoIstanza.getOggettoIstanzaAreaProtettaList().isEmpty() && attoreScriva != null) {
            for (OggettoIstanzaAreaProtettaExtendedDTO oggettoIstanzaAreaProtettaExt : oggettoIstanza.getOggettoIstanzaAreaProtettaList()) {
                OggettoIstanzaAreaProtettaDTO oggettoIstanzaAreaProtetta = oggettoIstanzaAreaProtettaExt.getDTO();
                oggettoIstanzaAreaProtetta.setIdOggettoIstanza(oggettoIstanza.getIdOggettoIstanza());
                if (StringUtils.isBlank(oggettoIstanzaAreaProtetta.getGestAttoreIns())) {
                    oggettoIstanzaAreaProtetta.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                }
                idOggettoAreaProtetta = oggettoIstanzaAreaProtettaDAO.saveOggettoIstanzaAreaProtetta(oggettoIstanzaAreaProtetta);
            }
        }
        logEnd(className);
        return idOggettoAreaProtetta;
    }

    /**
     * Save ogg vincolo aut long.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param attoreScriva   the attore scriva
     * @param now            the now
     * @return the long
     */
    @Override
    public Long saveOggVincoloAut(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva, Date now) {
        logBegin(className);
        Long idOggettoVincoloAut = null;
        if (oggettoIstanza != null && CollectionUtils.isNotEmpty(oggettoIstanza.getOggettoIstanzaVincoloAutorizzaList()) && attoreScriva != null) {
            for (OggettoIstanzaVincoloAutorizzaExtendedDTO oggettoIstanzaVincoloAutorizzaExt : oggettoIstanza.getOggettoIstanzaVincoloAutorizzaList()) {
                OggettoIstanzaVincoloAutorizzaDTO oggettoIstanzaVincoloAutorizza = oggettoIstanzaVincoloAutorizzaExt.getDTO();
                oggettoIstanzaVincoloAutorizza.setIdOggettoIstanza(oggettoIstanza.getIdOggettoIstanza());
                if (StringUtils.isBlank(oggettoIstanzaVincoloAutorizza.getGestAttoreIns())) {
                    oggettoIstanzaVincoloAutorizza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                }
                idOggettoVincoloAut = oggettoIstanzaVincoloAutorizzaDAO.saveOggettoIstanzaVincoloAutorizza(oggettoIstanzaVincoloAutorizza);
            }
        }
        logEnd(className);
        return idOggettoVincoloAut;
    }

    /**
     * Save catasto ubicazione long.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param attoreScriva   the attore scriva
     * @param now            the now
     * @return the long
     */
    @Override
    public Long saveCatastoUbicazione(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva, Date now) {
        logBegin(className);
        Long idCatastoUbicazione = null;
        try {
            if (oggettoIstanza != null && oggettoIstanza.getUbicazioneOggettoIstanza() != null && !oggettoIstanza.getUbicazioneOggettoIstanza().isEmpty() && attoreScriva != null) {
                for (UbicazioneOggettoIstanzaExtendedDTO ubicazioneOggettoIstanza : oggettoIstanza.getUbicazioneOggettoIstanza()) {
                    if (ubicazioneOggettoIstanza.getDatiCatastali() != null && !ubicazioneOggettoIstanza.getDatiCatastali().isEmpty()) {
                        for (CatastoUbicazioneOggettoIstanzaDTO catastoUbicazioneOggettoIstanza : ubicazioneOggettoIstanza.getDatiCatastali()) {
                            catastoUbicazioneOggettoIstanza.setIdUbicaOggettoIstanza(ubicazioneOggettoIstanza.getIdUbicazioneOggettoIstanza());
                            catastoUbicazioneOggettoIstanza.setGestAttoreIns(StringUtils.isBlank(catastoUbicazioneOggettoIstanza.getGestAttoreIns()) ? attoreScriva.getCodiceFiscale() : catastoUbicazioneOggettoIstanza.getGestAttoreIns());
                            idCatastoUbicazione = catastoUbicazioneOggettoIstanzaDAO.saveCatastoUbicazioneOggettoIstanza(catastoUbicazioneOggettoIstanza);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return idCatastoUbicazione;
    }

    /**
     * Save catasto ubicazione long.
     *
     * @param ubicazioneOggettoIstanza the ubicazione oggetto istanza
     * @param attoreScriva             the attore scriva
     * @param now                      the now
     * @return the long
     */
    public Long saveCatastoUbicazione(UbicazioneOggettoIstanzaExtendedDTO ubicazioneOggettoIstanza, AttoreScriva attoreScriva, Date now) {
        logBegin(className);
        Long idCatastoUbicazione = null;
        try {
            if (ubicazioneOggettoIstanza != null && ubicazioneOggettoIstanza.getDatiCatastali() != null && !ubicazioneOggettoIstanza.getDatiCatastali().isEmpty() && attoreScriva != null) {
                for (CatastoUbicazioneOggettoIstanzaDTO catastoUbicazioneOggettoIstanza : ubicazioneOggettoIstanza.getDatiCatastali()) {
                    catastoUbicazioneOggettoIstanza.setIdUbicaOggettoIstanza(ubicazioneOggettoIstanza.getIdUbicazioneOggettoIstanza());
                    catastoUbicazioneOggettoIstanza.setGestAttoreIns(StringUtils.isBlank(catastoUbicazioneOggettoIstanza.getGestAttoreIns()) ? attoreScriva.getCodiceFiscale() : catastoUbicazioneOggettoIstanza.getGestAttoreIns());
                    idCatastoUbicazione = catastoUbicazioneOggettoIstanzaDAO.saveCatastoUbicazioneOggettoIstanza(catastoUbicazioneOggettoIstanza);
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return idCatastoUbicazione;
    }

    
    /*ROLLBACK per cancellazione comuni segnalazione di Ismaele
//    /**
//     * Update ubicazioni oggetto istanza new.
//     *
//     * @param oggettoIstanza the oggetto istanza
//     * @param attoreScriva   the attore scriva
//     * @param now            the now
//     */
//    @Override
//    public void updateUbicazioniOggettoIstanza(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva, Date now) {
//        logBegin(className);
//        if (oggettoIstanza != null && attoreScriva != null) {
//            Long idOggettoIstanza = oggettoIstanza.getIdOggettoIstanza();
//            List<UbicazioneOggettoIstanzaExtendedDTO> newUbicazioneOggettoIstanzaList = oggettoIstanza.getUbicazioneOggettoIstanza();
//            List<UbicazioneOggettoIstanzaExtendedDTO> oldUbicazioneOggettoIstanzaList = ubicazioneOggettoIstanzaDAO.loadUbicazioneOggettoIstanzaByIdOggettoIstanza(idOggettoIstanza);
//            if (newUbicazioneOggettoIstanzaList != null && !newUbicazioneOggettoIstanzaList.isEmpty()) {
//                updateInsertUbicaOggIstanza(idOggettoIstanza, newUbicazioneOggettoIstanzaList, oldUbicazioneOggettoIstanzaList, attoreScriva);
//            }
//            if (oldUbicazioneOggettoIstanzaList != null && !oldUbicazioneOggettoIstanzaList.isEmpty()) {
//            	for(UbicazioneOggettoIstanzaExtendedDTO oldUbicazioneOggettoIstanza : oldUbicazioneOggettoIstanzaList){
//                	if(oldUbicazioneOggettoIstanza.getIndGeoProvenienza().equals("GEO"))
//                        deleteUbicaOggIstanza(newUbicazioneOggettoIstanzaList, oldUbicazioneOggettoIstanzaList);
//            	}
//
//                }
//        }
//        logEnd(className);
//    }
//    
    
    public void updateUbicazioniOggettoIstanza(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva, Date now) {
        this.logBegin(this.className);
        if (oggettoIstanza != null && attoreScriva != null) {
           Long idOggettoIstanza = oggettoIstanza.getIdOggettoIstanza();
           List<UbicazioneOggettoIstanzaExtendedDTO> newUbicazioneOggettoIstanzaList = oggettoIstanza.getUbicazioneOggettoIstanza();
           List<UbicazioneOggettoIstanzaExtendedDTO> oldUbicazioneOggettoIstanzaList = this.ubicazioneOggettoIstanzaDAO.loadUbicazioneOggettoIstanzaByIdOggettoIstanza(idOggettoIstanza);
           if (newUbicazioneOggettoIstanzaList != null && !newUbicazioneOggettoIstanzaList.isEmpty()) {
              this.updateInsertUbicaOggIstanza(idOggettoIstanza, newUbicazioneOggettoIstanzaList, oldUbicazioneOggettoIstanzaList, attoreScriva);
           }

           if (oldUbicazioneOggettoIstanzaList != null && !oldUbicazioneOggettoIstanzaList.isEmpty()) {
              this.deleteUbicaOggIstanza(newUbicazioneOggettoIstanzaList, oldUbicazioneOggettoIstanzaList);
           }
        }

        this.logEnd(this.className);
     }
    
    /**
     * Update ubicazioni oggetto istanza new.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param attoreScriva   the attore scriva
     * @param now            the now
     */
    @Override
    public void updateUbicazioniOggettoIstanzaWithComuni(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva, Date now, List<ComuneExtendedDTO> comuniList) {
        logBegin(className);
        if (oggettoIstanza != null && attoreScriva != null) {
            Long idOggettoIstanza = oggettoIstanza.getIdOggettoIstanza();
            List<UbicazioneOggettoIstanzaExtendedDTO> newUbicazioneOggettoIstanzaList = oggettoIstanza.getUbicazioneOggettoIstanza();
            List<UbicazioneOggettoIstanzaExtendedDTO> oldUbicazioneOggettoIstanzaList = ubicazioneOggettoIstanzaDAO.loadUbicazioneOggettoIstanzaByIdOggettoIstanza(idOggettoIstanza);
            //if (newUbicazioneOggettoIstanzaList != null && !newUbicazioneOggettoIstanzaList.isEmpty()) {
                updateInsertUbicaOggIstanzaWithComuni(idOggettoIstanza, newUbicazioneOggettoIstanzaList, oldUbicazioneOggettoIstanzaList, attoreScriva, comuniList);
            //}
            if (oldUbicazioneOggettoIstanzaList != null && !oldUbicazioneOggettoIstanzaList.isEmpty()) {
            	if(newUbicazioneOggettoIstanzaList != null && !newUbicazioneOggettoIstanzaList.isEmpty()) {
            	for(UbicazioneOggettoIstanzaExtendedDTO oldUbicazioneOggettoIstanza : oldUbicazioneOggettoIstanzaList){
                	if(oldUbicazioneOggettoIstanza.getIndGeoProvenienza().equals("GEO"))
                        deleteUbicaOggIstanza(newUbicazioneOggettoIstanzaList, oldUbicazioneOggettoIstanzaList);
            	}
            	}
                }
        }
        logEnd(className);
    }
    
    
    
    /**
     * Update ubicazioni oggetto istanza new.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param attoreScriva   the attore scriva
     * @param now            the now
     */
    @Override
    public void updateUbicazioniOggettoIstanzaNEW(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva, Date now) {
        logBegin(className);
        if (oggettoIstanza != null && attoreScriva != null) {
            Long idOggettoIstanza = oggettoIstanza.getIdOggettoIstanza();
            List<UbicazioneOggettoIstanzaExtendedDTO> newUbicazioneOggettoIstanzaList = oggettoIstanza.getUbicazioneOggettoIstanza();
            List<UbicazioneOggettoIstanzaExtendedDTO> oldUbicazioneOggettoIstanzaList = ubicazioneOggettoIstanzaDAO.loadUbicazioneOggettoIstanzaByIdOggettoIstanza(idOggettoIstanza);
            if (newUbicazioneOggettoIstanzaList != null && !newUbicazioneOggettoIstanzaList.isEmpty()) {
                updateInsertUbicaOggIstanza(idOggettoIstanza, newUbicazioneOggettoIstanzaList, oldUbicazioneOggettoIstanzaList, attoreScriva);
            }

        }
        logEnd(className);
    }

    /**
     * Update insert ubica ogg istanza.
     *
     * @param idOggettoIstanza                the id oggetto istanza
     * @param newUbicazioneOggettoIstanzaList the new ubicazione oggetto istanza list
     * @param oldUbicazioneOggettoIstanzaList the old ubicazione oggetto istanza list
     * @param attoreScriva                    the attore scriva
     */
    private void updateInsertUbicaOggIstanza(Long idOggettoIstanza, List<UbicazioneOggettoIstanzaExtendedDTO> newUbicazioneOggettoIstanzaList, List<UbicazioneOggettoIstanzaExtendedDTO> oldUbicazioneOggettoIstanzaList, AttoreScriva attoreScriva) {
        logBegin(className);
        for (UbicazioneOggettoIstanzaExtendedDTO newUbicazioneOggettoIstanzaExt : newUbicazioneOggettoIstanzaList) {
            UbicazioneOggettoIstanzaDTO newUbicazioneOggettoIstanza = newUbicazioneOggettoIstanzaExt.getDTO();
            newUbicazioneOggettoIstanza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
            newUbicazioneOggettoIstanza.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
            newUbicazioneOggettoIstanza.setIdOggettoIstanza(idOggettoIstanza);
            
            List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaToUpdateList = oldUbicazioneOggettoIstanzaList.stream()
                    .filter(ubicaoggIst -> Objects.equals(ubicaoggIst.getDTO().getIdOggettoIstanza(), newUbicazioneOggettoIstanza.getIdOggettoIstanza()) && Objects.equals(ubicaoggIst.getDTO().getIdComune(), newUbicazioneOggettoIstanza.getIdComune()))
                    .collect(Collectors.toList());
            
            List<UbicazioneOggettoIstanzaExtendedDTO> elementsNotInNewList = new ArrayList<UbicazioneOggettoIstanzaExtendedDTO>();
            /****LOGICA NUOVA CHE NON DEVE ESSERE FATTA SEMPRE - DA CAPIRE QUANDO*****/
            elementsNotInNewList= 	oldUbicazioneOggettoIstanzaList.stream()
                    .filter(oldItem -> newUbicazioneOggettoIstanzaList.stream()
                        .noneMatch(newItem -> Objects.equals(oldItem.getDTO().getIdOggettoIstanza(), newItem.getDTO().getIdOggettoIstanza()) &&
                                              Objects.equals(oldItem.getDTO().getIdComune(), newItem.getDTO().getIdComune())))
                    .collect(Collectors.toList());
            
            for(UbicazioneOggettoIstanzaExtendedDTO element: elementsNotInNewList) {
            	if(element.getIndGeoProvenienza().equals("FO")) {
            		
            		UbicazioneOggettoIstanzaDTO oldUbicazioneOggettoIstanza = element.getDTO();
            		oldUbicazioneOggettoIstanza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
            		oldUbicazioneOggettoIstanza.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
            		oldUbicazioneOggettoIstanza.setIdOggettoIstanza(idOggettoIstanza);
            		oldUbicazioneOggettoIstanza.setIndGeoProvenienza("GEOD");
            		oldUbicazioneOggettoIstanza.setIdUbicazioneOggettoIstanza(element.getIdUbicazioneOggettoIstanza());
            		ubicazioneOggettoIstanzaDAO.updateUbicazioneOggettoIstanza(oldUbicazioneOggettoIstanza);
            	}
            }
            
            /*****FINE LOGICA NUOVA****/
            
            if (!ubicazioneOggettoIstanzaToUpdateList.isEmpty()) {
            	String indGeoProv = newUbicazioneOggettoIstanza.getIndGeoProvenienza();

            	newUbicazioneOggettoIstanza.setIndGeoProvenienza(indGeoProv);
                newUbicazioneOggettoIstanza.setIdUbicazioneOggettoIstanza(ubicazioneOggettoIstanzaToUpdateList.get(0).getIdUbicazioneOggettoIstanza());
                newUbicazioneOggettoIstanzaExt.setIdUbicazioneOggettoIstanza(ubicazioneOggettoIstanzaToUpdateList.get(0).getIdUbicazioneOggettoIstanza());
                ubicazioneOggettoIstanzaDAO.updateUbicazioneOggettoIstanza(newUbicazioneOggettoIstanza);
            } else {
                Long idUbicazioneOggettoIstanza = ubicazioneOggettoIstanzaDAO.saveUbicazioneOggettoIstanza(newUbicazioneOggettoIstanza); 
                newUbicazioneOggettoIstanzaExt.setIdUbicazioneOggettoIstanza(idUbicazioneOggettoIstanza);
            }
            updateCatastoUbicazione(newUbicazioneOggettoIstanzaExt, attoreScriva, Calendar.getInstance().getTime());
        }
        logEnd(className);
    }

    
    /**
     * Update insert ubica ogg istanza.
     *
     * @param idOggettoIstanza                the id oggetto istanza
     * @param newUbicazioneOggettoIstanzaList the new ubicazione oggetto istanza list
     * @param oldUbicazioneOggettoIstanzaList the old ubicazione oggetto istanza list
     * @param attoreScriva                    the attore scriva
     */
    private void updateInsertUbicaOggIstanzaWithComuni(Long idOggettoIstanza, List<UbicazioneOggettoIstanzaExtendedDTO> newUbicazioneOggettoIstanzaList, List<UbicazioneOggettoIstanzaExtendedDTO> oldUbicazioneOggettoIstanzaList, AttoreScriva attoreScriva, List<ComuneExtendedDTO> comuniListFromGeoService) {
        logBegin(className);
        
        if(newUbicazioneOggettoIstanzaList != null && !newUbicazioneOggettoIstanzaList.isEmpty()) {
        //newUbicazioneOggettoIstanzaList SONO I NUOVI COMUNI CHE ARRIVANO DA GEECO, MENO QUELLI GIA' PRESENTI SU DB
        //oldUbicazioneOggettoIstanzaList SONO I COMUNI GIA' PRESENTI SU DB
        for (UbicazioneOggettoIstanzaExtendedDTO newUbicazioneOggettoIstanzaExt : newUbicazioneOggettoIstanzaList) {
            UbicazioneOggettoIstanzaDTO newUbicazioneOggettoIstanza = newUbicazioneOggettoIstanzaExt.getDTO();
            newUbicazioneOggettoIstanza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
            newUbicazioneOggettoIstanza.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
            newUbicazioneOggettoIstanza.setIdOggettoIstanza(idOggettoIstanza);
            //crea una nuova lista ubicazioneOggettoIstanzaToUpdateList contenente solo gli oggetti di oldUbicazioneOggettoIstanzaList che hanno lo stesso IdOggettoIstanza e IdComune di newUbicazioneOggettoIstanza
            List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaToUpdateList = oldUbicazioneOggettoIstanzaList.stream()
                    .filter(ubicaoggIst -> Objects.equals(ubicaoggIst.getDTO().getIdOggettoIstanza(), newUbicazioneOggettoIstanza.getIdOggettoIstanza()) && Objects.equals(ubicaoggIst.getDTO().getIdComune(), newUbicazioneOggettoIstanza.getIdComune()))
                    .collect(Collectors.toList());

            
            List<ComuneExtendedDTO> comuneList = new ArrayList<ComuneExtendedDTO>();
            List<ComuneExtendedDTO> comuniToDeleteList = new ArrayList<ComuneExtendedDTO>();
            List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaFromDB = ubicazioneOggettoIstanzaDAO.loadUbicazioneOggettoIstanzaByIdOggettoIstanza(idOggettoIstanza);
            
            List<ComuneExtendedDTO> comuniFromDB = new ArrayList<ComuneExtendedDTO>();
            
            for(UbicazioneOggettoIstanzaExtendedDTO ubicazioneOggettoIstanza : ubicazioneOggettoIstanzaFromDB) {
            	comuniFromDB.add(ubicazioneOggettoIstanza.getComune());
            }
            
            

            
            //comuniFromDB --> Comuni attualmente presenti su db
            //comuniListFromGeoService --> Comuni restituiti dal servizio di georeferenziazione
            //comuneList --> comuni ricavati dalle feature eliminando quelli inseriti in precedenza
            

            
            boolean trovato = false;
            for (UbicazioneOggettoIstanzaExtendedDTO ubicazione : oldUbicazioneOggettoIstanzaList) {
                
                for (ComuneExtendedDTO comune : comuniListFromGeoService) {
                    if (ubicazione.getComune().getIdComune().equals(comune.getIdComune())) {
                        trovato = true;
                        break;
                    }
                }
            }
            
            
            
            if(!trovato) {
	            for(UbicazioneOggettoIstanzaExtendedDTO element: oldUbicazioneOggettoIstanzaList) {
	            	if(element.getIndGeoProvenienza().equals("FO")|| element.getIndGeoProvenienza().equals("BO")) {
	            		
	            		UbicazioneOggettoIstanzaDTO oldUbicazioneOggettoIstanza = element.getDTO();
	            		oldUbicazioneOggettoIstanza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
	            		oldUbicazioneOggettoIstanza.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
	            		oldUbicazioneOggettoIstanza.setIdOggettoIstanza(idOggettoIstanza);
	            		oldUbicazioneOggettoIstanza.setIndGeoProvenienza("GEOD");
	            		oldUbicazioneOggettoIstanza.setIdUbicazioneOggettoIstanza(element.getIdUbicazioneOggettoIstanza());
	            		ubicazioneOggettoIstanzaDAO.updateUbicazioneOggettoIstanza(oldUbicazioneOggettoIstanza);
	            	}
	            }
            }
            
            //In newUbicazioneOggettoIstanzaList ho sempre gli elementi nuovi da inserire a db
            //Li filtro con comuniFromDb e mi faccio restituire la lista di quelli assenti.
            // if (lista vuota) allora faccio l'update e imposto i geod
            // else faccio l'insert

            
            if(newUbicazioneOggettoIstanzaList.isEmpty() ) {//if (!ubicazioneOggettoIstanzaToUpdateList.isEmpty()) {
            	String indGeoProv = newUbicazioneOggettoIstanza.getIndGeoProvenienza();

            	newUbicazioneOggettoIstanza.setIndGeoProvenienza(indGeoProv);
                newUbicazioneOggettoIstanza.setIdUbicazioneOggettoIstanza(ubicazioneOggettoIstanzaToUpdateList.get(0).getIdUbicazioneOggettoIstanza());
                newUbicazioneOggettoIstanzaExt.setIdUbicazioneOggettoIstanza(ubicazioneOggettoIstanzaToUpdateList.get(0).getIdUbicazioneOggettoIstanza());
                ubicazioneOggettoIstanzaDAO.updateUbicazioneOggettoIstanza(newUbicazioneOggettoIstanza);
            } else {
                Long idUbicazioneOggettoIstanza = ubicazioneOggettoIstanzaDAO.saveUbicazioneOggettoIstanza(newUbicazioneOggettoIstanza); 
                newUbicazioneOggettoIstanzaExt.setIdUbicazioneOggettoIstanza(idUbicazioneOggettoIstanza);
            }
            updateCatastoUbicazione(newUbicazioneOggettoIstanzaExt, attoreScriva, Calendar.getInstance().getTime());
        }
    } else {
    	
    	List<UbicazioneOggettoIstanzaExtendedDTO> nonPresentiInComuniList = new ArrayList<>();
    	for (UbicazioneOggettoIstanzaExtendedDTO ubicazione : oldUbicazioneOggettoIstanzaList) {
    		boolean trovato = false;
    	    for (ComuneExtendedDTO comune : comuniListFromGeoService) {
    	        if (ubicazione.getComune().getIdComune().equals(comune.getIdComune())) {
    	            trovato = true;
    	            break;
    	        }
    	    }
    	    if (!trovato) {
    	        nonPresentiInComuniList.add(ubicazione);
    	    }
    	}
        
        
        
        if(nonPresentiInComuniList != null && !nonPresentiInComuniList.isEmpty()) {
            for(UbicazioneOggettoIstanzaExtendedDTO element: nonPresentiInComuniList) {
            	if(element.getIndGeoProvenienza().equals("FO")|| element.getIndGeoProvenienza().equals("BO")) {
            		
            		UbicazioneOggettoIstanzaDTO oldUbicazioneOggettoIstanza = element.getDTO();
            		oldUbicazioneOggettoIstanza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
            		oldUbicazioneOggettoIstanza.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
            		oldUbicazioneOggettoIstanza.setIdOggettoIstanza(idOggettoIstanza);
            		oldUbicazioneOggettoIstanza.setIndGeoProvenienza("GEOD");
            		oldUbicazioneOggettoIstanza.setIdUbicazioneOggettoIstanza(element.getIdUbicazioneOggettoIstanza());
            		ubicazioneOggettoIstanzaDAO.updateUbicazioneOggettoIstanza(oldUbicazioneOggettoIstanza);
            	}
            }
        }
        
        
    	List<UbicazioneOggettoIstanzaExtendedDTO> presentiInComuniList = new ArrayList<>();
    	for (UbicazioneOggettoIstanzaExtendedDTO ubicazione : oldUbicazioneOggettoIstanzaList) {
    		boolean presente = false;
    	    for (ComuneExtendedDTO comune : comuniListFromGeoService) {
    	        if (ubicazione.getComune().getIdComune().equals(comune.getIdComune())) {
    	            presente = true;
    	            break;
    	        }
    	    }
    	    if (presente) {
    	    	presentiInComuniList.add(ubicazione);
    	    }
    	}
        
        
        
        if(presentiInComuniList != null && !presentiInComuniList.isEmpty()) {
            for(UbicazioneOggettoIstanzaExtendedDTO element: presentiInComuniList) {
            	if(element.getIndGeoProvenienza().equals("GEOD")) {
            		
            		UbicazioneOggettoIstanzaDTO oldUbicazioneOggettoIstanza = element.getDTO();
            		oldUbicazioneOggettoIstanza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
            		oldUbicazioneOggettoIstanza.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
            		oldUbicazioneOggettoIstanza.setIdOggettoIstanza(idOggettoIstanza);
            		oldUbicazioneOggettoIstanza.setIndGeoProvenienza(attoreScriva.getComponente());
            		oldUbicazioneOggettoIstanza.setIdUbicazioneOggettoIstanza(element.getIdUbicazioneOggettoIstanza());
            		ubicazioneOggettoIstanzaDAO.updateUbicazioneOggettoIstanza(oldUbicazioneOggettoIstanza);
            	}
            }
        }
    }
        logEnd(className);
    }
    
    
    
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
   
//ROLLBACK per cancellazione comuni segnalazione di Ismaele    
//    /**
//     * Delete ubica ogg istanza.
//     *
//     * @param newUbicazioneOggettoIstanzaList the new ubicazione oggetto istanza list
//     * @param oldUbicazioneOggettoIstanzaList the old ubicazione oggetto istanza list
//     */
//    private void deleteUbicaOggIstanza(List<UbicazioneOggettoIstanzaExtendedDTO> newUbicazioneOggettoIstanzaList, List<UbicazioneOggettoIstanzaExtendedDTO> oldUbicazioneOggettoIstanzaList) {
//        logBegin(className);
//        for (UbicazioneOggettoIstanzaExtendedDTO oldUbicazioneOggettoIstanzaExt : oldUbicazioneOggettoIstanzaList) {
//            UbicazioneOggettoIstanzaDTO oldUbicazioneOggettoIstanza = oldUbicazioneOggettoIstanzaExt.getDTO();
//            List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaNoDeleteList = newUbicazioneOggettoIstanzaList != null ? 
//                    newUbicazioneOggettoIstanzaList.stream()
//                            .filter(ubicaoggIst -> Objects.equals(ubicaoggIst.getDTO().getIdComune(), oldUbicazioneOggettoIstanza.getIdComune()))
//                            .collect(Collectors.toList()) : new ArrayList<>(); //Il filtro mantiene solo gli oggetti per cui getIdComune() del DTO è uguale a getIdComune() di oldUbicazioneOggettoIstanza
//           
//            
//         // Creiamo una nuova lista per gli elementi non presenti in newUbicazioneOggettoIstanzaList
//            List<UbicazioneOggettoIstanzaExtendedDTO> nonPresentiInNewList = new ArrayList<>();
//
////            for (UbicazioneOggettoIstanzaExtendedDTO oldItem : oldUbicazioneOggettoIstanzaList) {
////                boolean found = false;
////                for (UbicazioneOggettoIstanzaExtendedDTO newItem : newUbicazioneOggettoIstanzaList) {
////                    if (Objects.equals(oldItem.getDTO().getIdComune(), newItem.getDTO().getIdComune())) {
////                        found = true;
////                        break;
////                    }
////                }
////                if (!found) {
////                    nonPresentiInNewList.add(oldItem);
////                }
////            }
//
//            // Ora nonPresentiInNewList contiene gli elementi di oldUbicazioneOggettoIstanzaList non presenti in newUbicazioneOggettoIstanzaList
//                            
//           //if(oldUbicazioneOggettoIstanzaExt.getIndGeoProvenienza().equals("GEO")) {//if (!ubicazioneOggettoIstanzaNoDeleteList.isEmpty()) { LOGICA VECCHIA
//                            //if (!nonPresentiInNewList.isEmpty()) {
//            if (!ubicazioneOggettoIstanzaNoDeleteList.isEmpty()) {
//                            catastoUbicazioneOggettoIstanzaDAO.deleteCatastoUbicazioneOggettoIstanzaByIdUbicazioneOggettoIstanza(oldUbicazioneOggettoIstanza.getIdUbicazioneOggettoIstanza());
//                ubicazioneOggettoIstanzaDAO.deleteUbicazioneOggettoIstanza(oldUbicazioneOggettoIstanza.getGestUID());
//            }
//        }
//        logEnd(className);
//    }
//    
    
    
    private void deleteUbicaOggIstanza(List<UbicazioneOggettoIstanzaExtendedDTO> newUbicazioneOggettoIstanzaList, List<UbicazioneOggettoIstanzaExtendedDTO> oldUbicazioneOggettoIstanzaList) {
        this.logBegin(this.className);
        Iterator var3 = oldUbicazioneOggettoIstanzaList.iterator();

        while(var3.hasNext()) {
           UbicazioneOggettoIstanzaExtendedDTO oldUbicazioneOggettoIstanzaExt = (UbicazioneOggettoIstanzaExtendedDTO)var3.next();
           UbicazioneOggettoIstanzaDTO oldUbicazioneOggettoIstanza = oldUbicazioneOggettoIstanzaExt.getDTO();
           List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaNoDeleteList = newUbicazioneOggettoIstanzaList != null ? (List)newUbicazioneOggettoIstanzaList.stream().filter((ubicaoggIst) -> {
              return Objects.equals(ubicaoggIst.getDTO().getIdComune(), oldUbicazioneOggettoIstanza.getIdComune());
           }).collect(Collectors.toList()) : new ArrayList();
           if (((List)ubicazioneOggettoIstanzaNoDeleteList).isEmpty()) {
              this.catastoUbicazioneOggettoIstanzaDAO.deleteCatastoUbicazioneOggettoIstanzaByIdUbicazioneOggettoIstanza(oldUbicazioneOggettoIstanza.getIdUbicazioneOggettoIstanza());
              this.ubicazioneOggettoIstanzaDAO.deleteUbicazioneOggettoIstanza(oldUbicazioneOggettoIstanza.getGestUID());
           }
        }

        this.logEnd(this.className);
     }
    

    /**
     * Update ogg natura 2000 integer.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param attoreScriva   the attore scriva
     * @param now            the now
     */
    @Override
    public void updateOggNatura2000(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva, Date now) {
        logBegin(className);
        if (oggettoIstanza != null && attoreScriva != null) {
            deleteOggNatura2000(oggettoIstanza.getIdOggettoIstanza());
        }

        Long idOggettoIstanzaNatura2000 = null;
        if (oggettoIstanza != null && oggettoIstanza.getOggettoIstanzaNatura2000List() != null && !oggettoIstanza.getOggettoIstanzaNatura2000List().isEmpty() && attoreScriva != null) {
            for (OggettoIstanzaNatura2000ExtendedDTO oggettoIstanzaNatura2000Ext : oggettoIstanza.getOggettoIstanzaNatura2000List()) {
                OggettoIstanzaNatura2000DTO oggettoIstanzaNatura2000 = oggettoIstanzaNatura2000Ext.getDTO();
                oggettoIstanzaNatura2000.setIdOggettoIstanza(oggettoIstanza.getIdOggettoIstanza());
                if (StringUtils.isBlank(oggettoIstanzaNatura2000.getGestAttoreIns())) {
                    oggettoIstanzaNatura2000.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                }
                idOggettoIstanzaNatura2000 = oggettoIstanzaNatura2000DAO.saveOggettoIstanzaNatura2000(oggettoIstanzaNatura2000);
            }
        }
        logEnd(className);
    }

    /**
     * Update insert ogg natura 2000.
     *
     * @param idOggettoIstanza                the id oggetto istanza
     * @param newOggettoIstanzaNatura2000List the new oggetto istanza natura 2000 list
     * @param oldOggettoIstanzaNatura2000List the old oggetto istanza natura 2000 list
     * @param attoreScriva                    the attore scriva
     */
    private void updateInsertOggNatura2000(Long idOggettoIstanza, List<OggettoIstanzaNatura2000ExtendedDTO> newOggettoIstanzaNatura2000List, List<OggettoIstanzaNatura2000ExtendedDTO> oldOggettoIstanzaNatura2000List, AttoreScriva attoreScriva) {
        logBegin(className);
        for (OggettoIstanzaNatura2000ExtendedDTO newOggettoIstanzaNatura2000Ext : newOggettoIstanzaNatura2000List) {
            OggettoIstanzaNatura2000DTO newOggettoIstanzaNatura2000 = newOggettoIstanzaNatura2000Ext.getDTO();
            newOggettoIstanzaNatura2000.setGestAttoreIns(attoreScriva.getCodiceFiscale());
            newOggettoIstanzaNatura2000.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
            newOggettoIstanzaNatura2000.setIdOggettoIstanza(idOggettoIstanza);
            List<OggettoIstanzaNatura2000ExtendedDTO> oggettoIstanzaNatura2000ToUpdateList = oldOggettoIstanzaNatura2000List.stream()
                    .filter(oggIstNatura2000 -> Objects.equals(oggIstNatura2000.getDTO().getIdOggettoIstanza(), newOggettoIstanzaNatura2000.getIdOggettoIstanza()) && Objects.equals(oggIstNatura2000.getDTO().getIdTipoNatura2000(), newOggettoIstanzaNatura2000.getIdTipoNatura2000()) && Objects.equals(oggIstNatura2000.getDTO().getCodAmministrativo(), newOggettoIstanzaNatura2000.getCodAmministrativo()))
                    .collect(Collectors.toList());
            if (!oggettoIstanzaNatura2000ToUpdateList.isEmpty()) {
                newOggettoIstanzaNatura2000.setIdOggettoNatura2000(oggettoIstanzaNatura2000ToUpdateList.get(0).getIdOggettoNatura2000());
                oggettoIstanzaNatura2000DAO.updateOggettoIstanzaNatura2000(newOggettoIstanzaNatura2000);
            } else {
                oggettoIstanzaNatura2000DAO.saveOggettoIstanzaNatura2000(newOggettoIstanzaNatura2000);
            }
        }
        logEnd(className);
    }

    /**
     * Delete ogg natura 2000.
     *
     * @param idOggettoIstanza the id oggetto istanza
     */
    private void deleteOggNatura2000(Long idOggettoIstanza) { //List<OggettoIstanzaNatura2000ExtendedDTO> newOggettoIstanzaNatura2000List, List<OggettoIstanzaNatura2000ExtendedDTO> oldOggettoIstanzaNatura2000List) {
        logBegin(className);
        logEnd(className);
        oggettoIstanzaNatura2000DAO.deleteOggettoIstanzaNatura2000ByIdOggettoIstanza(idOggettoIstanza);
    }

    /**
     * Update ogg area protetta integer.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param attoreScriva   the attore scriva
     * @param now            the now
     */
    @Override
    public void updateOggAreaProtetta(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva, Date now) {
        logBegin(className);
        if (oggettoIstanza != null && attoreScriva != null) {
            Long idOggettoIstanza = oggettoIstanza.getIdOggettoIstanza();
            deleteOggAreaProtetta(oggettoIstanza.getIdOggettoIstanza());
        }
        Long idOggettoAreaProtetta = null;
        if (oggettoIstanza != null && oggettoIstanza.getOggettoIstanzaAreaProtettaList() != null && !oggettoIstanza.getOggettoIstanzaAreaProtettaList().isEmpty() && attoreScriva != null) {
            for (OggettoIstanzaAreaProtettaExtendedDTO oggettoIstanzaAreaProtettaExt : oggettoIstanza.getOggettoIstanzaAreaProtettaList()) {
                OggettoIstanzaAreaProtettaDTO oggettoIstanzaAreaProtetta = oggettoIstanzaAreaProtettaExt.getDTO();
                oggettoIstanzaAreaProtetta.setIdOggettoIstanza(oggettoIstanza.getIdOggettoIstanza());
                if (StringUtils.isBlank(oggettoIstanzaAreaProtetta.getGestAttoreIns())) {
                    oggettoIstanzaAreaProtetta.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                }
                idOggettoAreaProtetta = oggettoIstanzaAreaProtettaDAO.saveOggettoIstanzaAreaProtetta(oggettoIstanzaAreaProtetta);
            }
        }
        logEnd(className);
    }

    /**
     * Update insert ogg area protetta.
     *
     * @param idOggettoIstanza                  the id oggetto istanza
     * @param newOggettoIstanzaAreaProtettaList the new oggetto istanza area protetta list
     * @param oldOggettoIstanzaAreaProtettaList the old oggetto istanza area protetta list
     * @param attoreScriva                      the attore scriva
     */
    private void updateInsertOggAreaProtetta(Long idOggettoIstanza, List<OggettoIstanzaAreaProtettaExtendedDTO> newOggettoIstanzaAreaProtettaList, List<OggettoIstanzaAreaProtettaExtendedDTO> oldOggettoIstanzaAreaProtettaList, AttoreScriva attoreScriva) {
        logBegin(className);
        for (OggettoIstanzaAreaProtettaExtendedDTO newOggettoIstanzaAreaProtettaExt : newOggettoIstanzaAreaProtettaList) {
            OggettoIstanzaAreaProtettaDTO newOggettoIstanzaAreaProtetta = newOggettoIstanzaAreaProtettaExt.getDTO();
            newOggettoIstanzaAreaProtetta.setGestAttoreIns(attoreScriva.getCodiceFiscale());
            newOggettoIstanzaAreaProtetta.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
            newOggettoIstanzaAreaProtetta.setIdOggettoIstanza(idOggettoIstanza);
            List<OggettoIstanzaAreaProtettaExtendedDTO> oggettoIstanzaAreaProtettaToUpdateList = oldOggettoIstanzaAreaProtettaList.stream()
                    .filter(oggIstAreaProtetta -> Objects.equals(oggIstAreaProtetta.getDTO().getIdOggettoIstanza(), newOggettoIstanzaAreaProtetta.getIdOggettoIstanza()) && Objects.equals(oggIstAreaProtetta.getDTO().getIdTipoAreaProtetta(), newOggettoIstanzaAreaProtetta.getIdTipoAreaProtetta()) && Objects.equals(oggIstAreaProtetta.getDTO().getCodAmministrativo(), newOggettoIstanzaAreaProtetta.getCodAmministrativo()))
                    .collect(Collectors.toList());
            if (!oggettoIstanzaAreaProtettaToUpdateList.isEmpty()) {
                newOggettoIstanzaAreaProtetta.setIdOggettoAreaProtetta(oggettoIstanzaAreaProtettaToUpdateList.get(0).getIdOggettoAreaProtetta());
                oggettoIstanzaAreaProtettaDAO.updateOggettoIstanzaAreaProtetta(newOggettoIstanzaAreaProtetta);
            } else {
                oggettoIstanzaAreaProtettaDAO.saveOggettoIstanzaAreaProtetta(newOggettoIstanzaAreaProtetta);
            }
        }
        logEnd(className);
    }

    /**
     * Delete ogg area protetta.
     *
     * @param idOggettoIstanza the id oggetto istanza
     */
    private void deleteOggAreaProtetta(Long idOggettoIstanza) { //List<OggettoIstanzaAreaProtettaExtendedDTO> newOggettoIstanzaAreaProtettaList, List<OggettoIstanzaAreaProtettaExtendedDTO> oldOggettoIstanzaAreaProtettaList) {
        logBegin(className);
        logEnd(className);

        oggettoIstanzaAreaProtettaDAO.deleteOggettoIstanzaAreaProtettaByIdOggettoIstanza(idOggettoIstanza);

    }

    /**
     * Update ogg vincolo aut integer.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param attoreScriva   the attore scriva
     * @param now            the now
     */
    @Override
    public void updateOggVincoloAut(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva, Date now) {
        logBegin(className);
        if (oggettoIstanza != null && attoreScriva != null) {
            Long idOggettoIstanza = oggettoIstanza.getIdOggettoIstanza();
            List<OggettoIstanzaVincoloAutorizzaExtendedDTO> newOggettoIstanzaVincoloAutorizzaList = oggettoIstanza.getOggettoIstanzaVincoloAutorizzaList();
            List<OggettoIstanzaVincoloAutorizzaExtendedDTO> oldOggettoIstanzaVincoloAutorizzaList = oggettoIstanzaVincoloAutorizzaDAO.loadOggettiIstanzaVincoloAutorizza(null, idOggettoIstanza);
            if (CollectionUtils.isNotEmpty(oldOggettoIstanzaVincoloAutorizzaList)) {
                deleteOggVincoloAutorizza(newOggettoIstanzaVincoloAutorizzaList, oldOggettoIstanzaVincoloAutorizzaList);
            }
            if (CollectionUtils.isNotEmpty(newOggettoIstanzaVincoloAutorizzaList)) {
              updateInsertOggVincoloAutorizza(idOggettoIstanza, newOggettoIstanzaVincoloAutorizzaList, oldOggettoIstanzaVincoloAutorizzaList, attoreScriva);
            }

        }
        logEnd(className);
    }

    /**
     * Update insert ogg vincolo autorizza.
     *
     * @param idOggettoIstanza                      the id oggetto istanza
     * @param newOggettoIstanzaVincoloAutorizzaList the new oggetto istanza vincolo autorizza list
     * @param oldOggettoIstanzaVincoloAutorizzaList the old oggetto istanza vincolo autorizza list
     * @param attoreScriva                          the attore scriva
     */
    private void updateInsertOggVincoloAutorizza(Long idOggettoIstanza, List<OggettoIstanzaVincoloAutorizzaExtendedDTO> newOggettoIstanzaVincoloAutorizzaList, List<OggettoIstanzaVincoloAutorizzaExtendedDTO> oldOggettoIstanzaVincoloAutorizzaList, AttoreScriva attoreScriva) {
        logBegin(className);
        if (CollectionUtils.isNotEmpty(newOggettoIstanzaVincoloAutorizzaList)) {
          for (OggettoIstanzaVincoloAutorizzaExtendedDTO newOggettoIstanzaVincoloAutorizzaExt : newOggettoIstanzaVincoloAutorizzaList) {
              OggettoIstanzaVincoloAutorizzaDTO newOggettoIstanzaVincoloAutorizza = newOggettoIstanzaVincoloAutorizzaExt.getDTO();
              newOggettoIstanzaVincoloAutorizza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
              newOggettoIstanzaVincoloAutorizza.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
              newOggettoIstanzaVincoloAutorizza.setIdOggettoIstanza(idOggettoIstanza);
              List<OggettoIstanzaVincoloAutorizzaExtendedDTO> oggettoIstanzaVincoloAutorizzaToUpdateList = oldOggettoIstanzaVincoloAutorizzaList.stream()
                      .filter(oggIstVincoloAut -> Objects.equals(oggIstVincoloAut.getDTO().getGestUID(), newOggettoIstanzaVincoloAutorizza.getGestUID()))
                      .collect(Collectors.toList());
              if (CollectionUtils.isNotEmpty(oggettoIstanzaVincoloAutorizzaToUpdateList)) {
                  newOggettoIstanzaVincoloAutorizza.setIdOggettoVincoloAut(oggettoIstanzaVincoloAutorizzaToUpdateList.get(0).getIdOggettoVincoloAut());
                  oggettoIstanzaVincoloAutorizzaDAO.updateOggettoIstanzaVincoloAutorizza(newOggettoIstanzaVincoloAutorizza);
              } else {
                  oggettoIstanzaVincoloAutorizzaDAO.saveOggettoIstanzaVincoloAutorizza(newOggettoIstanzaVincoloAutorizza);
              }
          }
        }
        logEnd(className);
    }

    /**
     * Delete ogg vincolo autorizza.
     *
     * @param newOggettoIstanzaVincoloAutorizzaList the new oggetto istanza vincolo autorizza list
     * @param oldOggettoIstanzaVincoloAutorizzaList the old oggetto istanza vincolo autorizza list
     */
    public void deleteOggVincoloAutorizza(List<OggettoIstanzaVincoloAutorizzaExtendedDTO> newOggettoIstanzaVincoloAutorizzaList, List<OggettoIstanzaVincoloAutorizzaExtendedDTO> oldOggettoIstanzaVincoloAutorizzaList) {
        logBegin(className);
        logEnd(className);
        if (CollectionUtils.isNotEmpty(oldOggettoIstanzaVincoloAutorizzaList)) {
            for (OggettoIstanzaVincoloAutorizzaExtendedDTO oldOggettoIstanzaVincoloAutorizzaExt : oldOggettoIstanzaVincoloAutorizzaList) {
                OggettoIstanzaVincoloAutorizzaDTO oldOggettoIstanzaVincoloAutorizza = oldOggettoIstanzaVincoloAutorizzaExt.getDTO();
                List<OggettoIstanzaVincoloAutorizzaExtendedDTO> oggettoIstanzaVincoloAutorizzaNoDeleteList = CollectionUtils.isNotEmpty(newOggettoIstanzaVincoloAutorizzaList) ?
                        newOggettoIstanzaVincoloAutorizzaList.stream()
                                .filter(oggIstVincoloAut -> Objects.equals(oggIstVincoloAut.getDTO().getGestUID(), oldOggettoIstanzaVincoloAutorizza.getGestUID()))
                                .collect(Collectors.toList()) : new ArrayList<>();
                if (CollectionUtils.isEmpty(oggettoIstanzaVincoloAutorizzaNoDeleteList)) {
                    oggettoIstanzaVincoloAutorizzaDAO.deleteOggettoIstanzaVincoloAutorizza(oldOggettoIstanzaVincoloAutorizza.getGestUID());
                }
            }
        }
    }

    /**
     * Update catasto ubicazione.
     *
     * @param ubicazioneOggettoIstanza the ubicazione oggetto istanza
     * @param attoreScriva             the attore scriva
     * @param now                      the now
     */
    @Override
    public void updateCatastoUbicazione(UbicazioneOggettoIstanzaExtendedDTO ubicazioneOggettoIstanza, AttoreScriva attoreScriva, Date now) {
        logBegin(className);
        if (ubicazioneOggettoIstanza != null && attoreScriva != null) {
            Long idUbicazioneOggettoIstanza = ubicazioneOggettoIstanza.getIdUbicazioneOggettoIstanza();
            catastoUbicazioneOggettoIstanzaDAO.deleteCatastoUbicazioneOggettoIstanzaByIdUbicazioneOggettoIstanza(idUbicazioneOggettoIstanza);

            List<CatastoUbicazioneOggettoIstanzaDTO> newCatastoUbicazioneOggettoIstanzaList = ubicazioneOggettoIstanza.getDatiCatastali();
            //List<CatastoUbicazioneOggettoIstanzaDTO> oldCatastoUbicazioneOggettoIstanzaList = catastoUbicazioneOggettoIstanzaDAO.loadCatastoUbicazioneOggettoIstanza(null, idUbicazioneOggettoIstanza, null);
            if (CollectionUtils.isNotEmpty(newCatastoUbicazioneOggettoIstanzaList)) {
                updateInsertCatastoUbicazione(idUbicazioneOggettoIstanza, newCatastoUbicazioneOggettoIstanzaList, attoreScriva);
            }
            /*
            if (oldCatastoUbicazioneOggettoIstanzaList != null && !oldCatastoUbicazioneOggettoIstanzaList.isEmpty()) {
                deleteCatastoUbicazione(newCatastoUbicazioneOggettoIstanzaList, oldCatastoUbicazioneOggettoIstanzaList);
            }
             */
        }
        logEnd(className);
    }

    /**
     * Update insert catasto ubicazione.
     *
     * @param idUbicazioneOggettoIstanza             the id ubicazione oggetto istanza
     * @param newCatastoUbicazioneOggettoIstanzaList the new catasto ubicazione oggetto istanza list
     * @param attoreScriva                           the attore scriva
     */
    private void updateInsertCatastoUbicazione(Long idUbicazioneOggettoIstanza, List<CatastoUbicazioneOggettoIstanzaDTO> newCatastoUbicazioneOggettoIstanzaList, AttoreScriva attoreScriva) {
        logBegin(className);
        for (CatastoUbicazioneOggettoIstanzaDTO newCatastoUbicazioneOggettoIstanza : newCatastoUbicazioneOggettoIstanzaList) {
            newCatastoUbicazioneOggettoIstanza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
            newCatastoUbicazioneOggettoIstanza.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
            newCatastoUbicazioneOggettoIstanza.setIdUbicaOggettoIstanza(idUbicazioneOggettoIstanza);
            catastoUbicazioneOggettoIstanzaDAO.saveCatastoUbicazioneOggettoIstanza(newCatastoUbicazioneOggettoIstanza);
        }
        logEnd(className);
    }

    /**
     * Delete catasto ubicazione.
     *
     * @param newCatastoUbicazioneOggettoIstanzaList the new catasto ubicazione oggetto istanza list
     * @param oldCatastoUbicazioneOggettoIstanzaList the old catasto ubicazione oggetto istanza list
     */
    private void deleteCatastoUbicazione(List<CatastoUbicazioneOggettoIstanzaDTO> newCatastoUbicazioneOggettoIstanzaList, List<CatastoUbicazioneOggettoIstanzaDTO> oldCatastoUbicazioneOggettoIstanzaList) {
        logBegin(className);
        logEnd(className);
        for (CatastoUbicazioneOggettoIstanzaDTO oldCatastoUbicazioneOggettoIstanza : oldCatastoUbicazioneOggettoIstanzaList) {
            List<CatastoUbicazioneOggettoIstanzaDTO> catastoUbicazioneOggettoIstanzaNoDeleteList = newCatastoUbicazioneOggettoIstanzaList != null ?
                    newCatastoUbicazioneOggettoIstanzaList.stream()
                            .filter(catastoUbiOggIst -> Objects.equals(catastoUbiOggIst.getGestUID(), oldCatastoUbicazioneOggettoIstanza.getGestUID()))
                            .collect(Collectors.toList()) : new ArrayList<>();
            if (catastoUbicazioneOggettoIstanzaNoDeleteList.isEmpty()) {
                catastoUbicazioneOggettoIstanzaDAO.deleteCatastoUbicazioneOggettoIstanza(oldCatastoUbicazioneOggettoIstanza.getGestUID());
            }
        }
    }

    /**
     * Copy last info from oggetto.
     *
     * @param idOggettoIstanzaNew the id oggetto istanza new
     * @param idOggetto           the id oggetto
     * @param gestAttoreIns       the gest attore ins
     * @param now                 the now
     */
    @Override
    public void copyLastInfoFromOggetto(Long idOggettoIstanzaNew, Long idOggetto, String gestAttoreIns, Date now) {
        logBegin(className);
        try {
            OggettoIstanzaDTO oggettoIstanzaNew = idOggettoIstanzaNew != null ? oggettoIstanzaDAO.findByPK(idOggettoIstanzaNew) : null;
            IstanzaDTO istanza = oggettoIstanzaNew != null ? istanzaDAO.findByPK(oggettoIstanzaNew.getIdIstanza()) : null;
            IstanzaDTO istanzaOld = istanzaDAO.findByIdOggetto(idOggetto);
            Long idOggettoIstanzaToCopy = idOggetto != null && istanza != null ? oggettoIstanzaDAO.getLastIdOggettoIstanzaRefOggetto(idOggetto) : null;
            if (istanza != null && istanzaOld != null) {
                //if (Objects.equals(istanza.getIdTemplate(), istanzaOld.getIdTemplate())) {
                if (null != idOggettoIstanzaToCopy && idOggettoIstanzaToCopy > 0) {
                    if (!("N".equalsIgnoreCase(jsonDataManager.getValueFromJsonData(istanza.getJsonData(), Constants.PATH_JSONDATA_QDR_CONFIG, "IndGeoMode")))) {
                        copyLastGeoRefFromOggetto(idOggettoIstanzaNew, idOggettoIstanzaToCopy, now, gestAttoreIns);
                    }
                    if ("TRUE".equalsIgnoreCase(jsonDataManager.getValueFromJsonData(istanza.getJsonData(), Constants.PATH_JSONDATA_QDR_CONFIG_SITI_NATURA, VISIBILE))) {
                        copyLastOggNatura2000FromOggetto(idOggettoIstanzaNew, idOggettoIstanzaToCopy, now, gestAttoreIns);
                    }
                    if ("TRUE".equalsIgnoreCase(jsonDataManager.getValueFromJsonData(istanza.getJsonData(), Constants.PATH_JSONDATA_QDR_CONFIG_AREE_PROTETTE, VISIBILE))) {
                        copyLastOggAreaProtettaFromOggetto(idOggettoIstanzaNew, idOggettoIstanzaToCopy, now, gestAttoreIns);
                    }
                    if ("TRUE".equalsIgnoreCase(jsonDataManager.getValueFromJsonData(istanza.getJsonData(), Constants.PATH_JSONDATA_QDR_CONFIG_VINCOLI, VISIBILE))) {
                        copyLastOggVincoloAutFromOggetto(istanza.getIdAdempimento(), idOggettoIstanzaNew, idOggettoIstanzaToCopy, now, gestAttoreIns);
                    }
                    if ("TRUE".equalsIgnoreCase(jsonDataManager.getValueFromJsonData(istanza.getJsonData(), Constants.PATH_JSONDATA_QDR_CONFIG_DATI_CATASTO, VISIBILE))) {
                        copyLastCatastoUbiOggIstanzaFromOggetto(idOggettoIstanzaNew, idOggettoIstanzaToCopy, now, gestAttoreIns);
                    }
                }
                //} else {
                // TODO: Mapping layer virtuali per geometrie quando i procedimenti sono differenti
                //}
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Copy last geo ref from oggetto.
     *
     * @param idOggettoIstanzaNew    the id oggetto istanza new
     * @param idOggettoIstanzaToCopy the id oggetto istanza to copy
     * @param now                    the now
     * @param gestAttoreIns          the gest attore ins
     */
    @Override
    public void copyLastGeoRefFromOggetto(Long idOggettoIstanzaNew, Long idOggettoIstanzaToCopy, Date now, String gestAttoreIns) {
        logBegin(className);
        try {
            if (idOggettoIstanzaNew != null && idOggettoIstanzaToCopy != null && idOggettoIstanzaNew > 0 && idOggettoIstanzaToCopy > 0) {
                geoAreaOggettoIstanzaDAO.saveCopyGeoAreaOggettoIstanza(idOggettoIstanzaNew, idOggettoIstanzaToCopy, now, gestAttoreIns);
                geoPuntoOggettoIstanzaDAO.saveCopyGeoPuntoOggettoIstanza(idOggettoIstanzaNew, idOggettoIstanzaToCopy, now, gestAttoreIns);
                geoLineaOggettoIstanzaDAO.saveCopyGeoLineaOggettoIstanza(idOggettoIstanzaNew, idOggettoIstanzaToCopy, now, gestAttoreIns);
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Copy last ogg natura 2000 from oggetto.
     *
     * @param idOggettoIstanzaNew    the id oggetto istanza new
     * @param idOggettoIstanzaToCopy the id oggetto istanza to copy
     * @param gestAttoreIns          the gest attore ins
     */
    @Override
    public void copyLastOggNatura2000FromOggetto(Long idOggettoIstanzaNew, Long idOggettoIstanzaToCopy, Date now, String gestAttoreIns) {
        logBegin(className);
        try {
            if (idOggettoIstanzaNew != null && idOggettoIstanzaToCopy != null && idOggettoIstanzaNew > 0 && idOggettoIstanzaToCopy > 0) {
                oggettoIstanzaNatura2000DAO.saveCopyOggettoIstanzaNatura2000(idOggettoIstanzaNew, idOggettoIstanzaToCopy, now, gestAttoreIns);
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Copy last ogg area protetta from oggetto.
     *
     * @param idOggettoIstanzaNew    the id oggetto istanza new
     * @param idOggettoIstanzaToCopy the id oggetto istanza to copy
     * @param gestAttoreIns          the gest attore ins
     */
    @Override
    public void copyLastOggAreaProtettaFromOggetto(Long idOggettoIstanzaNew, Long idOggettoIstanzaToCopy, Date now, String gestAttoreIns) {
        logBegin(className);
        try {
            if (idOggettoIstanzaNew != null && idOggettoIstanzaToCopy != null && idOggettoIstanzaNew > 0 && idOggettoIstanzaToCopy > 0) {
                oggettoIstanzaAreaProtettaDAO.saveCopyOggettoIstanzaAreaProtetta(idOggettoIstanzaNew, idOggettoIstanzaToCopy, now, gestAttoreIns);
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Copy last ogg vincolo aut from oggetto.
     *
     * @param idAdempimento          the id adempimento
     * @param idOggettoIstanzaNew    the id oggetto istanza new
     * @param idOggettoIstanzaToCopy the id oggetto istanza to copy
     * @param now                    the now
     * @param gestAttoreIns          the gest attore ins
     */
    @Override
    public void copyLastOggVincoloAutFromOggetto(Long idAdempimento, Long idOggettoIstanzaNew, Long idOggettoIstanzaToCopy, Date now, String gestAttoreIns) {
        logBegin(className);
        try {
            if (idOggettoIstanzaNew != null && idOggettoIstanzaToCopy != null && idOggettoIstanzaNew > 0 && idOggettoIstanzaToCopy > 0) {
                oggettoIstanzaVincoloAutorizzaDAO.saveCopyOggettoIstanzaVincoloAutorizza(idAdempimento, idOggettoIstanzaNew, idOggettoIstanzaToCopy, now, gestAttoreIns);
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Copy last catasto ubi ogg istanza from oggetto.
     *
     * @param idOggettoIstanzaNew    the id oggetto istanza new
     * @param idOggettoIstanzaToCopy the id oggetto istanza to copy
     * @param now                    the now
     * @param gestAttoreIns          the gest attore ins
     */
    @Override
    public void copyLastCatastoUbiOggIstanzaFromOggetto(Long idOggettoIstanzaNew, Long idOggettoIstanzaToCopy, Date now, String gestAttoreIns) {
        logBegin(className);
        try {
            if (idOggettoIstanzaNew != null && idOggettoIstanzaToCopy != null && idOggettoIstanzaNew > 0 && idOggettoIstanzaToCopy > 0) {
                List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaNewList = ubicazioneOggettoIstanzaDAO.loadUbicazioneOggettoIstanzaByIdOggettoIstanza(idOggettoIstanzaNew);
                List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaToCopyList = ubicazioneOggettoIstanzaDAO.loadUbicazioneOggettoIstanzaByIdOggettoIstanza(idOggettoIstanzaToCopy);
                copyCatasto(ubicazioneOggettoIstanzaNewList, ubicazioneOggettoIstanzaToCopyList, now, gestAttoreIns);
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets oggetto ubicazione list.
     *
     * @param oggettiIstanzaList the oggetti istanza list
     * @return the oggetto ubicazione list
     */
    @Override
    public List<OggettoIstanzaUbicazioneExtendedDTO> getOggettoUbicazioneList(List<OggettoIstanzaExtendedDTO> oggettiIstanzaList) {
        logBegin(className);
        List<OggettoIstanzaUbicazioneExtendedDTO> oggettoIstanzaUbicazioneList = new ArrayList<>();
        try {
            if (CollectionUtils.isNotEmpty(oggettiIstanzaList)) {
                for (OggettoIstanzaExtendedDTO oggettoIstanza : oggettiIstanzaList) {
                    OggettoIstanzaUbicazioneExtendedDTO oggettoIstanzaUbicazione = new OggettoIstanzaUbicazioneExtendedDTO();
                    oggettoIstanzaUbicazione.setIdOggettoIstanza(oggettoIstanza.getIdOggettoIstanza());
                    oggettoIstanzaUbicazione.setIdOggetto(oggettoIstanza.getOggetto().getIdOggetto());
                    oggettoIstanzaUbicazione.setIdIstanza(oggettoIstanza.getIdIstanza());
                    oggettoIstanzaUbicazione.setTipologiaOggetto(oggettoIstanza.getTipologiaOggetto());
                    oggettoIstanzaUbicazione.setIndGeoStato(oggettoIstanza.getIndGeoStato());
                    oggettoIstanzaUbicazione.setDenOggetto(oggettoIstanza.getDenOggetto());
                    oggettoIstanzaUbicazione.setDesOggetto(oggettoIstanza.getDesOggetto());
                    oggettoIstanzaUbicazione.setCoordinataX(oggettoIstanza.getCoordinataX());
                    oggettoIstanzaUbicazione.setCoordinataY(oggettoIstanza.getCoordinataY());
                    oggettoIstanzaUbicazione.setIdMasterdata(oggettoIstanza.getIdMasterdata());
                    oggettoIstanzaUbicazione.setIdMasterdataOrigine(oggettoIstanza.getIdMasterdataOrigine());
                    oggettoIstanzaUbicazione.setGestUID(oggettoIstanza.getGestUID());
                    oggettoIstanzaUbicazione.setCodOggettoFonte(oggettoIstanza.getCodOggettoFonte());
                    oggettoIstanzaUbicazione.setCodUtenza(oggettoIstanza.getCodUtenza());
                    oggettoIstanzaUbicazione.setNoteAttoPrecedente(oggettoIstanza.getNoteAttoPrecedente());
                    oggettoIstanzaUbicazione.setFlgGeoModificato(oggettoIstanza.getFlgGeoModificato());
                    oggettoIstanzaUbicazione.setIndLivello(oggettoIstanza.getIndLivello());
                    List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioniOggettoIstanzaList = ubicazioneOggettoIstanzaDAO.loadUbicazioneOggettoIstanzaByIdOggettoIstanza(oggettoIstanza.getIdOggettoIstanza());
                    setCatastoUbicazioneOggettoIstanza(ubicazioniOggettoIstanzaList);
                    oggettoIstanzaUbicazione.setUbicazioneOggettoIstanza(ubicazioniOggettoIstanzaList);
                    oggettoIstanzaUbicazioneList.add(oggettoIstanzaUbicazione);
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return oggettoIstanzaUbicazioneList;
    }

    /**
     * Gets oggetto istanza geeco.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the oggetto istanza geeco
     */
    @Override
    public OggettoIstanzaGeecoDTO getOggettoIstanzaGeeco(Long idOggettoIstanza) {
        logBegin(className, "idOggettoIstanza : [" + idOggettoIstanza + "]");
        return oggettoIstanzaDAO.getOggettoIstanzaGeeco(idOggettoIstanza);
    }

    /**
     * Get oggetto istanza geeco list.
     *
     * @param idIstanza            the id istanza
     * @param idOggettoIstanzaList the id oggetto istanza list
     * @param idLayerList          the id layer list
     * @param indLivelloEstrazione the ind livello estrazione
     * @return the list
     */
    @Override
    public List<OggettoIstanzaGeecoDTO> getOggettoIstanzaGeeco(Long idIstanza, List<Long> idOggettoIstanzaList, List<Long> idLayerList, Long indLivelloEstrazione) {
        logBegin(className, "idIstanza : [" + idIstanza + "]\nidOggettoIstanzaList : " + idOggettoIstanzaList + "\nidLayerList : " + idLayerList + "\nindLivelloEstrazione : [" + indLivelloEstrazione + "]");
        return oggettoIstanzaDAO.getOggettoIstanzaGeeco(idIstanza, idOggettoIstanzaList, idLayerList, indLivelloEstrazione);
    }

    /**
     * Update flg geo modificato integer.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param value            the value
     * @return the integer
     */
    @Override
    public Integer updateFlgGeoModificatoByIdOggIst(Long idOggettoIstanza, boolean value) {
        logBegin(className, "idOggettoIstanza : [" + idOggettoIstanza + "] - value : [" + value + "]");
        return oggettoIstanzaDAO.updateFlgGeoModificatoByIdOggIst(idOggettoIstanza, value);
    }

    /**
     * Update flg geo modificato by id ist integer.
     *
     * @param idIstanza the id istanza
     * @param value     the value
     * @return the integer
     */
    @Override
    public Integer updateFlgGeoModificatoByIdIst(Long idIstanza, boolean value) {
        logBegin(className, "idIstanza : [" + idIstanza + "] - value : [" + value + "]");
        return oggettoIstanzaDAO.updateFlgGeoModificatoByIdIst(idIstanza, value);
    }

    /**
     * Gets ind livello by id padre.
     *
     * @param idOggettoIstanzaPadre the id oggetto istanza padre
     * @return the ind livello by id padre
     */
    @Override
    public Integer getIndLivelloByIdPadre(Long idOggettoIstanzaPadre) {
        logBeginInfo(className, idOggettoIstanzaPadre);
        OggettoIstanzaDTO oggettoIstanza = oggettoIstanzaDAO.findByPK(idOggettoIstanzaPadre);
        return oggettoIstanza != null ? oggettoIstanza.getIndLivello() : 0;
    }

    /**
     * Save oggetto istanza figlio oggetto istanza figlio dto.
     *
     * @param oggettoIstanzaUbicazione the oggetto istanza ubicazione
     * @param idOggettoIstanzaPadre    the id oggetto istanza padre
     * @param cfAttore                 the cf attore
     * @return the oggetto istanza figlio dto
     */
    @Override
    public OggettoIstanzaFiglioDTO saveOggettoIstanzaFiglio(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanzaUbicazione, Long idOggettoIstanzaPadre, String cfAttore) {
        logBegin(className);
        OggettoIstanzaFiglioDTO oggettoIstanzaFiglio = getOggettoIstanzaFiglio(oggettoIstanzaUbicazione.getIdOggettoIstanza(), idOggettoIstanzaPadre, cfAttore);
        Long idOggettoIstanzaFiglio = oggettoIstanzaFiglioDAO.saveOggettoIstanzaFiglio(oggettoIstanzaFiglio);
        oggettoIstanzaFiglio.setIdOggettoIstanzaFiglio(idOggettoIstanzaFiglio);
        logEnd(className);
        return oggettoIstanzaFiglio;
    }

    /**
     * Sets catasto ubicazione oggetto istanza.
     *
     * @param ubicazioniOggettoIstanzaList the ubicazioni oggetto istanza list
     */
    public void setCatastoUbicazioneOggettoIstanza(List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioniOggettoIstanzaList) {
        logBegin(className);
        try {
            if (CollectionUtils.isNotEmpty(ubicazioniOggettoIstanzaList)) {
                for (UbicazioneOggettoIstanzaExtendedDTO ubicazioneOggettoIstanza : ubicazioniOggettoIstanzaList) {
                    List<CatastoUbicazioneOggettoIstanzaDTO> catastoUbicazioneOggettoIstanzaList = catastoUbicazioneOggettoIstanzaDAO.loadCatastoUbicazioneOggettoIstanza(null, ubicazioneOggettoIstanza.getIdUbicazioneOggettoIstanza(), null, null);
                    ubicazioneOggettoIstanza.setDatiCatastali(catastoUbicazioneOggettoIstanzaList);
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Sets oggetto istanza natura 2000.
     *
     * @param oggettoIstanzaUbicazioneList the oggetto istanza ubicazione list
     */
    private void setOggettoIstanzaNatura2000(List<OggettoIstanzaUbicazioneExtendedDTO> oggettoIstanzaUbicazioneList) {
        logBegin(className);
        try {
            if (oggettoIstanzaUbicazioneList != null && !oggettoIstanzaUbicazioneList.isEmpty()) {
                for (OggettoIstanzaUbicazioneExtendedDTO oggettoIstanzaUbicazione : oggettoIstanzaUbicazioneList) {
                    List<OggettoIstanzaNatura2000ExtendedDTO> oggettoIstanzaNatura2000List = oggettoIstanzaNatura2000DAO.loadOggettiIstanzaNatura2000(null, oggettoIstanzaUbicazione.getIdOggettoIstanza());
                    if (oggettoIstanzaNatura2000List != null && !oggettoIstanzaNatura2000List.isEmpty()) {
                        // eliminazione oggetto-istanza annidato
                        for (OggettoIstanzaNatura2000ExtendedDTO oggettoIstanzaNatura2000 : oggettoIstanzaNatura2000List) {
                            Long idOggettoIstanza = oggettoIstanzaNatura2000.getOggettoIstanza().getIdOggettoIstanza();
                            oggettoIstanzaNatura2000.setIdOggettoIstanza(idOggettoIstanza);
                            oggettoIstanzaNatura2000.setOggettoIstanza(null);
                        }
                        oggettoIstanzaUbicazione.setOggettoIstanzaNatura2000List(oggettoIstanzaNatura2000List);
                    }
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Sets oggetto istanza area protetta.
     *
     * @param oggettoIstanzaUbicazioneList the oggetto istanza ubicazione list
     */
    private void setOggettoIstanzaAreaProtetta(List<OggettoIstanzaUbicazioneExtendedDTO> oggettoIstanzaUbicazioneList) {
        logBegin(className);
        try {
            if (oggettoIstanzaUbicazioneList != null && !oggettoIstanzaUbicazioneList.isEmpty()) {
                for (OggettoIstanzaUbicazioneExtendedDTO oggettoIstanzaUbicazione : oggettoIstanzaUbicazioneList) {
                    List<OggettoIstanzaAreaProtettaExtendedDTO> oggettoIstanzaAreaProtettaList = oggettoIstanzaAreaProtettaDAO.loadOggettiIstanzaAreaProtetta(null, oggettoIstanzaUbicazione.getIdOggettoIstanza());
                    if (CollectionUtils.isNotEmpty(oggettoIstanzaAreaProtettaList)) {
                        // eliminazione oggetto-istanza annidato
                        for (OggettoIstanzaAreaProtettaExtendedDTO oggettoIstanzaAreaProtetta : oggettoIstanzaAreaProtettaList) {
                            Long idOggettoIstanza = oggettoIstanzaAreaProtetta.getOggettoIstanza().getIdOggettoIstanza();
                            oggettoIstanzaAreaProtetta.setIdOggettoIstanza(idOggettoIstanza);
                            oggettoIstanzaAreaProtetta.setOggettoIstanza(null);
                        }
                        oggettoIstanzaUbicazione.setOggettoIstanzaAreaProtettaList(oggettoIstanzaAreaProtettaList);
                    }
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Sets oggetto istanza vincolo autorizza.
     *
     * @param oggettoIstanzaUbicazioneList the oggetto istanza ubicazione list
     */
    private void setOggettoIstanzaVincoloAutorizza(List<OggettoIstanzaUbicazioneExtendedDTO> oggettoIstanzaUbicazioneList) {
        logBegin(className);
        try {
            if (oggettoIstanzaUbicazioneList != null && !oggettoIstanzaUbicazioneList.isEmpty()) {
                for (OggettoIstanzaUbicazioneExtendedDTO oggettoIstanzaUbicazione : oggettoIstanzaUbicazioneList) {
                    List<OggettoIstanzaVincoloAutorizzaExtendedDTO> oggettoIstanzaVincoloAutorizzaList = oggettoIstanzaVincoloAutorizzaDAO.loadOggettiIstanzaVincoloAutorizza(null, oggettoIstanzaUbicazione.getIdOggettoIstanza());
                    if (CollectionUtils.isNotEmpty(oggettoIstanzaVincoloAutorizzaList)) {
                        // eliminazione oggetto-istanza annidato
                        for (OggettoIstanzaVincoloAutorizzaExtendedDTO oggettoIstanzaVincoloAutorizza : oggettoIstanzaVincoloAutorizzaList) {
                            Long idOggettoIstanza = oggettoIstanzaVincoloAutorizza.getOggettoIstanza().getIdOggettoIstanza();
                            oggettoIstanzaVincoloAutorizza.setIdOggettoIstanza(idOggettoIstanza);
                            oggettoIstanzaVincoloAutorizza.setOggettoIstanza(null);
                        }
                        oggettoIstanzaUbicazione.setOggettoIstanzaVincoloAutorizzaList(oggettoIstanzaVincoloAutorizzaList);
                    }
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Copy catasto.
     *
     * @param ubicazioneOggettoIstanzaNewList    the ubicazione oggetto istanza new list
     * @param ubicazioneOggettoIstanzaToCopyList the ubicazione oggetto istanza to copy list
     * @param now                                the now
     * @param gestAttoreIns                      the gest attore ins
     */
    private void copyCatasto(List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaNewList, List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaToCopyList, Date now, String gestAttoreIns) {
        logBegin(className);
        try {
            if (CollectionUtils.isNotEmpty(ubicazioneOggettoIstanzaToCopyList) && CollectionUtils.isNotEmpty(ubicazioneOggettoIstanzaNewList)) {
                for (UbicazioneOggettoIstanzaExtendedDTO ubicazioneOggettoIstanzaToCopy : ubicazioneOggettoIstanzaToCopyList) {
                    for (UbicazioneOggettoIstanzaExtendedDTO ubicazioneOggettoIstanzaNew : ubicazioneOggettoIstanzaNewList) {
                        if (Objects.equals(ubicazioneOggettoIstanzaNew.getComune().getIdComune(), ubicazioneOggettoIstanzaToCopy.getComune().getIdComune()) &&
                                Objects.equals(ubicazioneOggettoIstanzaNew.getDenIndirizzo(), ubicazioneOggettoIstanzaToCopy.getDenIndirizzo()) &&
                                Objects.equals(ubicazioneOggettoIstanzaNew.getNumCivico(), ubicazioneOggettoIstanzaToCopy.getNumCivico()) &&
                                Objects.equals(ubicazioneOggettoIstanzaNew.getDesLocalita(), ubicazioneOggettoIstanzaToCopy.getDesLocalita())) {
                            catastoUbicazioneOggettoIstanzaDAO.saveCopyCatastoUbicazioneOggettoIstanza(ubicazioneOggettoIstanzaNew.getIdUbicazioneOggettoIstanza(), ubicazioneOggettoIstanzaToCopy.getIdUbicazioneOggettoIstanza(), now, gestAttoreIns);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Get oggetto istanza figlio oggetto istanza figlio dto.
     *
     * @param idOggettoIstanza      the id oggetto istanza
     * @param idOggettoIstanzaPadre the id oggetto istanza padre
     * @param cfAttore              the cf attore
     * @return the oggetto istanza figlio dto
     */
    private OggettoIstanzaFiglioDTO getOggettoIstanzaFiglio(Long idOggettoIstanza, Long idOggettoIstanzaPadre, String cfAttore) {
        logBegin(className);
        OggettoIstanzaFiglioDTO oggettoIstanzaFiglio = new OggettoIstanzaFiglioDTO();
        oggettoIstanzaFiglio.setIdOggettoIstanzaFiglio(idOggettoIstanza);
        oggettoIstanzaFiglio.setIdOggettoIstanzaPadre(idOggettoIstanzaPadre);
        oggettoIstanzaFiglio.setGestAttoreIns(cfAttore);
        oggettoIstanzaFiglio.setGestAttoreUpd(cfAttore);
        return oggettoIstanzaFiglio;
    }
}