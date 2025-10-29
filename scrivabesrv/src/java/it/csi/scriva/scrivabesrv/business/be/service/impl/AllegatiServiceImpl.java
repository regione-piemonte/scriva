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

import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.Node;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.SharingInfo;
import it.csi.scriva.scrivabesrv.business.be.impl.BaseApiServiceImpl;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoConfigDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoTipoAllegatoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AllegatoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ClasseAllegatoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.DynamicSqlDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OsservazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoIntegraAllegatoDAO;
import it.csi.scriva.scrivabesrv.business.be.service.AllegatiService;
import it.csi.scriva.scrivabesrv.business.be.service.OsservazioneService;
import it.csi.scriva.scrivabesrv.dto.AdempimentoConfigDTO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoTipoAllegatoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AllegatoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.ClasseAllegatoDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.GenericInputParDTO;
import it.csi.scriva.scrivabesrv.dto.IndexMetadataPropertyDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoOsservazioneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoIntegraAllegatoDTO;
import it.csi.scriva.scrivabesrv.dto.UrlDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.InformazioniScrivaEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.TipologiaAllegatoEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.ValidationResultEnum;
import it.csi.scriva.scrivabesrv.util.CalendarUtils;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.manager.AllegatiManager;
import it.csi.scriva.scrivabesrv.util.manager.FunzionarioManager;
import it.csi.scriva.scrivabesrv.util.manager.IstanzaAttoreManager;
import it.csi.scriva.scrivabesrv.util.templating.TemplateUtil;
import it.csi.scriva.scrivabesrv.util.updownfile.FileUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Tipi evento service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class AllegatiServiceImpl extends BaseApiServiceImpl implements AllegatiService {

    public static final String JAVA_IO_TMPDIR = "java.io.tmpdir";
    private final String className = this.getClass().getSimpleName();
    private static final String ZIP = "ZIP";
    private static final String CSV = "CSV";
    private static final String DOC = "DOC";
    private static final String PDF = "PDF";
    private static final String SCRIVA_DOWNLOAD_MAX_PATH = "SCRIVA_DOWNLOAD_MAX_PATH";
    private static final String SCRIVA_DOWNLOAD_MAX_PATH_TESTO = "SCRIVA_DOWNLOAD_MAX_PATH_TESTO";

    /**
     * The Template util.
     */
    @Autowired
    TemplateUtil templateUtil;

    /**
     * The Adempimento tipo allegato dao.
     */
    @Autowired
    AdempimentoConfigDAO adempimentoConfigDAO;

    /**
     * The Adempimento tipo allegato dao.
     */
    @Autowired
    AdempimentoTipoAllegatoDAO adempimentoTipoAllegatoDAO;

    /**
     * The Allegato istanza dao.
     */
    @Autowired
    AllegatoIstanzaDAO allegatoIstanzaDAO;

    /**
     * The Classe allegato dao.
     */
    @Autowired
    ClasseAllegatoDAO classeAllegatoDAO;

    /**
     * The Dynamic sql dao.
     */
    @Autowired
    DynamicSqlDAO dynamicSqlDAO;

    /**
     * The Istanza dao.
     */
    @Autowired
    IstanzaDAO istanzaDAO;

    /**
     * The Osservazione dao.
     */
    @Autowired
    OsservazioneDAO osservazioneDAO;

    /**
     * The Tipo integra allegato dao.
     */
    @Autowired
    TipoIntegraAllegatoDAO tipoIntegraAllegatoDAO;

    /**
     * The Allegati manager.
     */
    @Autowired
    AllegatiManager allegatiManager;

    /**
     * The Funzionario manager.
     */
    @Autowired
    FunzionarioManager funzionarioManager;

    /**
     * The Istanza attore manager.
     */
    @Autowired
    IstanzaAttoreManager istanzaAttoreManager;

    /**
     * The Osservazione service.
     */
    @Autowired
    OsservazioneService osservazioneService;

    @Autowired
    private ConfigurazioneDAO configurazioneDAO;

    /**
     * Load allegati list.
     *
     * @return the list
     */
    @Override
    public List<AllegatoIstanzaExtendedDTO> loadAllegati() {
        logBegin(className);
        List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList = null;
        try {
            allegatoIstanzaList = allegatoIstanzaDAO.loadAllegatiIstanza(null, null, Boolean.FALSE, null);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return allegatoIstanzaList;
    }

    /**
     * Load allegati by id allegato istanza extended dto.
     *
     * @param idAllegatoIstanza the id allegato istanza
     * @return the allegato istanza extended dto
     */
    @Override
    public AllegatoIstanzaExtendedDTO loadAllegatiById(Long idAllegatoIstanza) {
        logBeginInfo(className, idAllegatoIstanza);
        List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList = null;
        try {
            allegatoIstanzaList = allegatoIstanzaDAO.loadAllegatoIstanzaById(idAllegatoIstanza);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return allegatoIstanzaList != null ? allegatoIstanzaList.get(0) : null;
    }

    /**
     * Load allegati by uuid index allegato istanza extended dto.
     *
     * @param uuidIndex the uuid index
     * @return the allegato istanza extended dto
     */
    @Override
    public AllegatoIstanzaExtendedDTO loadAllegatiByUuidIndex(@NotNull String uuidIndex) {
        logBeginInfo(className, uuidIndex);
        List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList = null;
        try {
            allegatoIstanzaList = allegatoIstanzaDAO.loadAllegatoIstanzaByUuidIndex(uuidIndex);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return allegatoIstanzaList != null ? allegatoIstanzaList.get(0) : null;
    }

    /**
     * Load allegati by id istanza list.
     *
     * @param idIstanza             the id istanza
     * @param codAllegato           the cod allegato
     * @param codClasseAllegato     the cod classe allegato
     * @param codCategoriaAllegato  the cod categoria allegato
     * @param codTipologiaAllegato  the cod tipologia allegato
     * @param idIstanzaOsservazione the id istanza osservazione
     * @param flgCancLogica         the flg canc logica
     * @param flgLinkDocumento      the flg link documento
     * @param offset                the offset
     * @param limit                 the limit
     * @param sort                  the sort
     * @param system                the system
     * @param attoreScriva          the attore scriva
     * @return the list
     */
    @Override
    public List<AllegatoIstanzaExtendedDTO> loadAllegatiByIdIstanza(Long idIstanza, String codAllegato, String codClasseAllegato, String codCategoriaAllegato, String codTipologiaAllegato, Long idIstanzaOsservazione, String flgCancLogica, String flgLinkDocumento, Integer offset, Integer limit, String sort, Boolean system, AttoreScriva attoreScriva) {
        logBeginInfo(className, "idIstanza [" + idIstanza + "] - codAllegato [" + codAllegato + "] - codCategoriaAllegato [" + codCategoriaAllegato + "] - flgCancLogica [" + flgCancLogica + "]  - flgLinkDocumento [" + flgLinkDocumento + "] - offset [" + offset + "] - limit [" + limit + "] - sort [" + sort + "]");
        List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList = null;
        try {
            /*
            Solo PUBBLICATI :
             - Utente PUBWEB (visibili alla componente)
             - Utente non autenticato (Identita non presente su Attore-Scriva)
             - Utente autenticato (Identita presente e ruolo non contenente "_RW" su Attore-Scriva)

            boolean flgOnlyPubblicati = (ComponenteAppEnum.FO.name().equals(attoreScriva.getComponente()) || ComponenteAppEnum.BO.name().equals(attoreScriva.getComponente()))
                    || (ComponenteAppEnum.PUBWEB.name().equals(attoreScriva.getComponente()) && idIstanzaOsservazione != null) //SCRIVA-847
                    || (!ComponenteAppEnum.PUBWEB.name().equals(attoreScriva.getComponente()) && attoreScriva.getIdentita() != null && StringUtils.isNotBlank(attoreScriva.getIdentita().getTimestamp()) && (StringUtils.isNotBlank(attoreScriva.getRuolo()) && attoreScriva.getRuolo().contains("_RW"))) ?
                    Boolean.FALSE : Boolean.TRUE;

            TUTTI e visibili dalla componente applicativa :
             - Utente autenticato (FO o BO)
             - Utente autenticato (Identita e ruolo contenente "_RW" su xRequestAuth)

            String componenteApp = (ComponenteAppEnum.FO.name().equals(attoreScriva.getComponente()) || ComponenteAppEnum.BO.name().equals(attoreScriva.getComponente()) || ComponenteAppEnum.PUBWEB.name().equals(attoreScriva.getComponente())) ||
                    (attoreScriva.getIdentita() != null && StringUtils.isNotBlank(attoreScriva.getRuolo())) ?
                    attoreScriva.getComponente() : null;
            */

            /*
            SOLO PUBBLICATI:
                - Utente PUBWEB (id_osservazione non valorizzata)
                - Utente NO SCRIVA (No FO/BO/PUBWEB/COSMO) e non autenticato
                - Utente NO SCRIVA (No FO/BO/PUBWEB/COSMO), autenticato ma con ruolo diverso da "_RW"
            */
            boolean flgOnlyPubblicati = (ComponenteAppEnum.PUBWEB.name().equals(attoreScriva.getComponente()) && idIstanzaOsservazione == null) //SCRIVA-847
                    || (!Constants.COMPONENT_SCRIVA_LIST.contains(attoreScriva.getComponente()) // Utente NON SCRIVA
                    && (attoreScriva.getIdentita() == null || StringUtils.isBlank(attoreScriva.getIdentita().getTimestamp()))) // non autenticato
                    || (!Constants.COMPONENT_SCRIVA_LIST.contains(attoreScriva.getComponente())
                    && (attoreScriva.getIdentita() != null && StringUtils.isNotBlank(attoreScriva.getIdentita().getTimestamp())) // autenticato
                    && (StringUtils.isNotBlank(attoreScriva.getRuolo()) && !attoreScriva.getRuolo().contains("_RW"))) // ruolo diverso da "_RW"
                    ? Boolean.TRUE : Boolean.FALSE;

            /*
            Filtro data pubblicazione = TRUE
                Componente PUBWEB (se idOsservazione non valorizzata)
                Componente NOT (PUBWEB/COSMO/FO/BO) e non autenticato

            Filtro ind_visibile = COMPONENTE APP
                Componente PUBWEB/FO/BO/COSMO

            VISIBILITA`COMPONENTE APPLICATIVA:
                - componente chiamante NON SCRIVA (diversa da PUBWEB/FO/BO/COSMO)

            */
            String componenteApp = Constants.COMPONENT_SCRIVA_LIST.contains(attoreScriva.getComponente()) ?
                    attoreScriva.getComponente() : null;

            if (idIstanza == null && StringUtils.isBlank(codAllegato) && idIstanzaOsservazione == null) {
                allegatoIstanzaList = allegatoIstanzaDAO.loadAllegatiIstanza(idIstanzaOsservazione, attoreScriva, flgOnlyPubblicati, componenteApp);
            } else {
                allegatoIstanzaList = allegatoIstanzaDAO.loadAllegatoIstanzaByIdIstanza(idIstanza, codAllegato, codClasseAllegato, codCategoriaAllegato, codTipologiaAllegato, idIstanzaOsservazione, flgCancLogica, flgOnlyPubblicati, offset != null ? String.valueOf(offset) : null, limit != null ? String.valueOf(limit) : null, sort, system, componenteApp);
                if (CollectionUtils.isNotEmpty(allegatoIstanzaList) &&
                        (
                                "TRUE".equalsIgnoreCase(flgLinkDocumento) ||
                                        (!Constants.COMPONENT_SCRIVA_LIST.contains(attoreScriva.getComponente()) && // utente non SCRIVA
                                                (attoreScriva.getIdentita() != null && StringUtils.isNotBlank(attoreScriva.getIdentita().getTimestamp())) && // autenticato
                                                (StringUtils.isNotBlank(attoreScriva.getRuolo()) && attoreScriva.getRuolo().contains("_RW")) // con ruolo "_RW"
                                        )
                        )
                ) {
                    addUrlTemp(allegatoIstanzaList, 15, flgLinkDocumento);
                }
            }

            // SCRIVA-996 : Documenti legati ad osservazioni in BOZZA non devono essere visibili dalle componenti diverse da PUBWEB
            if (!"PUBWEB".equalsIgnoreCase(attoreScriva.getComponente())) {
                allegatoIstanzaList = removeAllegatiOsservazioniBozza(allegatoIstanzaList);
            }

        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return allegatoIstanzaList;
    }

    /**
     * Add url temp.
     *
     * @param allegatoIstanzaList the allegato istanza list
     * @param addMinuteTime       the add minute time
     * @param flgLinkDocumento    the flg link documento
     */
    private void addUrlTemp(List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList, int addMinuteTime, String flgLinkDocumento) {
        logBegin(className);
        try {
            if (allegatoIstanzaList != null && !allegatoIstanzaList.isEmpty()) {
                Date now = Calendar.getInstance().getTime();
                String fromDate = CalendarUtils.convertDateToString(CalendarUtils.DATE_FORMAT_YYYY_MM_DDHHMM, now);
                Date afterAddMin = DateUtils.addMinutes(now, addMinuteTime);
                String toDate = CalendarUtils.convertDateToString(CalendarUtils.DATE_FORMAT_YYYY_MM_DDHHMM, afterAddMin);
                for (AllegatoIstanzaExtendedDTO allegatoIstanza : allegatoIstanzaList) {
                    UrlDTO url = null;
                    if ("TRUE".equalsIgnoreCase(flgLinkDocumento)) {
                        //Se flgLinkDocumento è impostato a TRUE riporta tra gli attributi anche il link del documento, se quest’ultimo non è un documento pubblicato/non riservato, il sistema fornisce un link temporaneo di 15 minuti;
                        url = allegatoIstanza.getDataPubblicazione() == null ? getLinkIndexFile(allegatoIstanza.getUuidIndex(), fromDate, toDate) : getLinkIndexFile(allegatoIstanza.getUuidIndex(), fromDate, null);
                    } else {
                        //Se componente chiamante diversa da PUBWEB e diversa da SCRIVA
                        //Se autenticato e con ruolo <componente chiamante>_RW
                        //restituisce tutti gli allegati dell’istanza passata in input fornendo per i documenti non pubblicati una URL TEMPORANEA
                        url = allegatoIstanza.getDataPubblicazione() == null ? getLinkIndexFile(allegatoIstanza.getUuidIndex(), fromDate, toDate) : null;
                    }
                    if (url == null && StringUtils.isBlank(allegatoIstanza.getUrlDoc())) {
                        url = getLinkIndexFile(allegatoIstanza.getUuidIndex(), fromDate, null);
                    }
                    if (url != null) {
                        allegatoIstanza.setUrlDoc(url.getUrl());
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
     * Remove allegati osservazioni bozza list.
     *
     * @param allegatoIstanzaList the allegato istanza list
     * @return the list
     */
    private List<AllegatoIstanzaExtendedDTO> removeAllegatiOsservazioniBozza(List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList) {
        logBegin(className);
        List<AllegatoIstanzaExtendedDTO> result = new ArrayList<>();
        try {
            if (CollectionUtils.isNotEmpty(allegatoIstanzaList)) {
                result = allegatoIstanzaList.stream()
                        .filter(allegato -> !"BOZZA".equalsIgnoreCase(allegato.getCodStatoOsservazione()))
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Load allegato istanza non inviato by id istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    @Override
    public List<AllegatoIstanzaExtendedDTO> loadAllegatoIstanzaNonInviatoByIdIstanza(Long idIstanza) {
        logBeginInfo(className, idIstanza);
        return allegatoIstanzaDAO.loadAllegatoIstanzaNonInviatoByIdIstanza(idIstanza);
    }

    /**
     * Update allegato istanza integer.
     *
     * @param allegatoIstanza         the allegato istanza
     * @param adempimentoTipoAllegato the adempimento tipo allegato
     * @param attoreScriva            the attore scriva
     * @return the integer
     */
    @Override
    public Integer updateAllegatoIstanza(AllegatoIstanzaExtendedDTO allegatoIstanza, AdempimentoTipoAllegatoExtendedDTO adempimentoTipoAllegato, AttoreScriva attoreScriva) {
        logBeginInfo(className, "allegatoIstanza [" + allegatoIstanza + "] - adempimentoTipoAllegato [" + adempimentoTipoAllegato + "] - attoreScriva [" + attoreScriva + "]");
        try {

            List<IstanzaExtendedDTO> istanzaList = istanzaDAO.loadIstanza(allegatoIstanza.getIdIstanza());
            IstanzaExtendedDTO istanza = istanzaList != null && !istanzaList.isEmpty() ? istanzaList.get(0) : null;

            setIdAttoreFunzionario(allegatoIstanza, attoreScriva);
            setDatiPubblicazione(allegatoIstanza, istanza, attoreScriva);
            setDatiProtocollazioneIntegrazione(allegatoIstanza, istanza, attoreScriva);
            allegatoIstanza.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
            //rollback 1492
            IndexMetadataPropertyDTO indexMetadataProperty = allegatiManager.getindexMetadataProperty(istanza, allegatoIstanza, adempimentoTipoAllegato.getFlgObbligo(), null, null, allegatoIstanza.getFlgCancellato(), allegatoIstanza.getDataCancellazione());
            //IndexMetadataPropertyDTO indexMetadataProperty = allegatiManager.getindexMetadataProperty(istanza, allegatoIstanza, adempimentoTipoAllegato.getFlgObbligo(), null, null, null, null);
            ErrorDTO error = allegatiManager.updateMetadataIndexByUuid(allegatoIstanza.getUuidIndex(), indexMetadataProperty);
            if (error != null) {
                logError(className, error);
                logEnd(className);
                return null;
            }
            return allegatoIstanzaDAO.updateAllegatoIstanza(allegatoIstanza.getDTO());
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets link index file.
     *
     * @param uuidIndex the uuid index
     * @param fromDate  the from date
     * @param toDate    the to date
     * @return the link index file
     */
    @Override
    public UrlDTO getLinkIndexFile(String uuidIndex, String fromDate, String toDate) {
        logBeginInfo(className, "Parametro input uuidIndex [" + uuidIndex + "]" + (StringUtils.isNotBlank(fromDate) ? " - fromDate [" + fromDate + "]" : "") + (StringUtils.isNotBlank(toDate) ? " - toDate [" + toDate + "]" : ""));
        try {
            if (StringUtils.isBlank(uuidIndex)) {
                logError(className, "ERROR creating link : uuidIndex NULL");
                throw new GenericException(getErrorManager().getError("400", null, "ERROR creating link : uuidIndex NULL", null, null));
            }
            AllegatoIstanzaExtendedDTO allegatoIstanza = this.loadAllegatiByUuidIndex(uuidIndex);
            if (allegatoIstanza == null) {
                logError(className, "ERROR : uuidIndex [" + uuidIndex + "] allegatoIstanza not found");
                throw new GenericException(getErrorManager().getError("404", null, "ERROR : uuidIndex [" + uuidIndex + "] allegatoIstanza not found", null, null));
            }

            SharingInfo sharingInfo = allegatiManager.getSharingInfos(uuidIndex);
            sharingInfo = sharingInfo != null ? allegatiManager.setSharingInfo(allegatoIstanza.getNomeAllegato(), fromDate, toDate, sharingInfo) : null;
            String urlShareLink = sharingInfo != null ? allegatiManager.updateSharedLink(uuidIndex, sharingInfo) : allegatiManager.shareDocument(uuidIndex, allegatoIstanza.getNomeAllegato(), fromDate, toDate);
            //String urlShareLink = sharingInfo != null ? allegatiManager.updateSharedLink(uuidIndex, allegatiManager.setSharingInfo("test_link_allegato.pdf", fromDate, toDate, sharingInfo)) : allegatiManager.shareDocument(uuidIndex, "test_link_allegato.pdf", fromDate, toDate);

            if (StringUtils.isBlank(urlShareLink)) {
                logError(className, "ERROR : uuidIndex [" + uuidIndex + "] urlShareLink not found");
                throw new GenericException(getErrorManager().getError("404", null, "ERROR : uuidIndex [" + uuidIndex + "] urlShareLink not found", null, null));
            }
            UrlDTO url = new UrlDTO();
            url.setUrl(urlShareLink);
            logEnd(className);
            return url;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Verifica validitÃ  dell'oggetto allegatoIstanza
     *
     * @param allegatoIstanza allegatoIstanza
     * @param attoreScriva    the attore scriva
     * @return Map<String, Object>  map
     */
    public Map<String, Object> validateAllegato(AllegatoIstanzaExtendedDTO allegatoIstanza, AttoreScriva attoreScriva) {
        ErrorDTO error = null;
        AdempimentoTipoAllegatoExtendedDTO adempimentoTipoAllegato = null;
        IstanzaExtendedDTO istanza = null;
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, String> details = new HashMap<>();

        if (allegatoIstanza == null) {
            details.put("allegatoIstanza", ValidationResultEnum.INVALID.getDescription());
        } else {
            //SCRIVA-892 - non si possono inviare osservazioni oltre scadenza
            if (allegatoIstanza.getIdIstanzaOsservazione() != null && allegatoIstanza.getIdIstanzaOsservazione() > 0) {
                if (osservazioneService.isDataOsservazioneExpired(allegatoIstanza.getIdIstanzaOsservazione(), attoreScriva)) {
                    details.put("allegatoIstanza", ValidationResultEnum.EXPIRED_OBSERVATION_DATE.getDescription());
                }
            }
            Long idIstanza = allegatoIstanza.getIdIstanza();
            Long idTipologiaAllegato = allegatoIstanza.getTipologiaAllegato() != null ? allegatoIstanza.getTipologiaAllegato().getIdTipologiaAllegato() : null;
            String codTipoIntegraAllegato = allegatoIstanza.getTipoIntegraAllegato() != null && allegatoIstanza.getTipoIntegraAllegato().getCodTipoIntegraAllegato() != null ? allegatoIstanza.getTipoIntegraAllegato().getCodTipoIntegraAllegato() : null;

            if (StringUtils.isNotBlank(codTipoIntegraAllegato)) {
                List<TipoIntegraAllegatoDTO> tipoIntegraAllegatoList = tipoIntegraAllegatoDAO.loadTipoIntegraAllegatoByCode(codTipoIntegraAllegato);
                allegatoIstanza.setTipoIntegraAllegato(tipoIntegraAllegatoList != null && !tipoIntegraAllegatoList.isEmpty() ? tipoIntegraAllegatoList.get(0) : null);
            }

            if (idIstanza == null) {
                details.put("idIstanza", ValidationResultEnum.MANDATORY.getDescription());
            }

            if (idIstanza != null) {
                List<IstanzaExtendedDTO> istanzaList = istanzaDAO.loadIstanza(idIstanza);
                if (istanzaList != null && !istanzaList.isEmpty()) {
                    istanza = istanzaList.get(0);
                }

                if (istanza == null) {
                    details.put("idIstanza", ValidationResultEnum.INVALID.getDescription());
                } else {
                    Long idAdempimento = istanza.getAdempimento() != null ? istanza.getAdempimento().getIdAdempimento() : null;
                    String codAdempimento = istanza.getAdempimento() != null ? istanza.getAdempimento().getCodAdempimento() : null;
                    if (idAdempimento == null) {
                        details.put("idAdempimento", ValidationResultEnum.INVALID.getDescription());
                    }

                    String codTipologiaAllegato = allegatoIstanza.getTipologiaAllegato().getCodTipologiaAllegato();
                    List<AdempimentoTipoAllegatoExtendedDTO> adempimentoTipoAllegatoList = null;
                    if (idAdempimento != null) {
                        if (idTipologiaAllegato != null) {
                            adempimentoTipoAllegatoList = adempimentoTipoAllegatoDAO.loadTipologieAllegatoByIdAdempimentoAndIdTipologiaAllegato(idAdempimento, idTipologiaAllegato, attoreScriva.getComponente());
                        } else if (StringUtils.isNotBlank(codTipologiaAllegato) &&
                                StringUtils.isNotBlank(codAdempimento)) {
                            adempimentoTipoAllegatoList = TipologiaAllegatoEnum.LETT_TRASM.name().equalsIgnoreCase(codTipologiaAllegato) ? adempimentoTipoAllegatoDAO.loadTipologieAllegatoByCodeAdempimento(codAdempimento, "na") : adempimentoTipoAllegatoDAO.loadTipologieAllegatoByCodeAdempimento(codAdempimento, attoreScriva.getComponente());
                            adempimentoTipoAllegatoList = adempimentoTipoAllegatoList != null && !adempimentoTipoAllegatoList.isEmpty() ?
                                    adempimentoTipoAllegatoList.stream()
                                            .filter(t -> codTipologiaAllegato.equalsIgnoreCase(t.getTipologiaAllegato().getCodTipologiaAllegato()))
                                            .collect(Collectors.toList()) :
                                    adempimentoTipoAllegatoList;
                        }
                    }
                    if (adempimentoTipoAllegatoList != null && !adempimentoTipoAllegatoList.isEmpty()) {
                        adempimentoTipoAllegato = adempimentoTipoAllegatoList.get(0);
                        allegatoIstanza.setTipologiaAllegato(adempimentoTipoAllegato.getTipologiaAllegato());
                        if (Boolean.TRUE.equals(adempimentoTipoAllegato.getFlgNota()) && StringUtils.isBlank(allegatoIstanza.getNote())) {
                            details.put("note", ValidationResultEnum.MANDATORY.getDescription());
                        }
                    } else {
                        details.put("adempimentoTipoAllegato", ValidationResultEnum.INVALID.getDescription());
                    }
                }
            }
        }

        if (!details.isEmpty()) {
            error = getErrorManager().getError("400", "E004", "I dati inseriti non sono corretti.", details, null);
        }

        resultMap.put("error", error);
        resultMap.put("adempimentoTipoAllegato", adempimentoTipoAllegato);
        resultMap.put("istanza", istanza);

        return resultMap;
    }

    /**
     * Gets allegato file.
     *
     * @param codAllegato  the cod allegato
     * @param attoreScriva the attore scriva
     * @return the allegato file
     */
    @Override
    public File getAllegatoFile(String codAllegato, AttoreScriva attoreScriva) {
        logBeginInfo(className, "codAllegato [" + codAllegato + "] - attoreScriva : \n" + attoreScriva + "\n");
        try {
            List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList = allegatoIstanzaDAO.loadAllegatoIstanzaByIdIstanza(null, codAllegato, null, null, null, null, "FALSE", Boolean.FALSE, null, null, null, Boolean.TRUE, attoreScriva.getComponente());
            if (allegatoIstanzaList != null && !allegatoIstanzaList.isEmpty()) {
                return allegatiManager.getFileFromIndexByUuid(allegatoIstanzaList.get(0).getUuidIndex());
            }
            ErrorDTO error = getErrorManager().getError("404", "", "Elemento non trovato", null, null);
            logError(className, error);
            logEnd(className);
            throw new GenericException(error);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets allegati zip file.
     *
     * @param idIstanza    the id istanza
     * @param codAllegato  the cod allegato
     * @param attoreScriva the attore scriva
     * @return the allegati zip file
     */
    @Override
    public File getAllegatiZipFile(Long idIstanza, String codAllegato, AttoreScriva attoreScriva) {
        logBeginInfo(className, "idIstanza [" + idIstanza + "] - attoreScriva [" + attoreScriva + "]");
        long millis = Instant.now().toEpochMilli();
        String rootPath = idIstanza + "_" + millis;
        String filename = null;
        try {
            IstanzaDTO istanza = istanzaDAO.findByPK(idIstanza);
            List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList = allegatoIstanzaDAO.loadAllegatoIstanzaByIdIstanza(idIstanza, codAllegato, null, null, null, null, null, Boolean.FALSE, null, null, null, Boolean.TRUE, attoreScriva.getComponente());
            if (istanza != null && allegatoIstanzaList != null && !allegatoIstanzaList.isEmpty()) {
                filename = getZIPFilename(istanza);
                for (AllegatoIstanzaExtendedDTO allegatoIstanza : allegatoIstanzaList) {
                    // recupero file da index
                    String uuidIndex = allegatoIstanza.getUuidIndex();
                    String desClasseAllegato = allegatoIstanza.getClasseAllegato() != null ? allegatoIstanza.getClasseAllegato().getDesClasseAllegato() : null;
                    String desCategoriaAllegato = allegatoIstanza.getTipologiaAllegato().getCategoriaAllegato().getDesCategoriaAllegato();
                    String desTipologiaAllegato = allegatoIstanza.getTipologiaAllegato().getDesTipologiaAllegato();
                    String nomeAllegato = allegatoIstanza.getNomeAllegato();
                    String savePath = filename + File.separator + (StringUtils.isNotBlank(desClasseAllegato) ? desClasseAllegato + File.separator : "") + desCategoriaAllegato + File.separator + desTipologiaAllegato + File.separator + nomeAllegato;
                    File fileFromIndex = allegatiManager.getFileFromIndexByUuid(uuidIndex);
                    if (fileFromIndex == null) {
                        ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
                        logError(className, "idIstanza [" + idIstanza + "]\n" + error);
                        logEnd(className);
                        throw new GenericException(error);
                    }
                    File fileDestination = new File(savePath);
                    FileUtils.copyFile(fileFromIndex, fileDestination);
                }

                String tmpFileZIP = System.getProperty(JAVA_IO_TMPDIR) + File.separator + filename + ".zip";
                FileOutputStream fileOutputStream = FileUtils.zipFolder(filename, tmpFileZIP);
                if (fileOutputStream == null) {
                    logError(className, "idIstanza [" + idIstanza + "]\n Errore durante la creazione del file ZIP");
                    logEnd(className);
                    return null;
                }
                fileOutputStream.close();
                FileUtils.deleteFolder(filename);
                return new File(tmpFileZIP);
            }
            return null;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    @Override
    public File getAllegatiZipFileByIdOsservazione(Long idOsservazione, String codAllegato, AttoreScriva attoreScriva) {
        logBeginInfo(className, "idOsservazione [" + idOsservazione + "] - attoreScriva [" + attoreScriva + "]");
        long millis = Instant.now().toEpochMilli();
        String rootPath = idOsservazione + "_" + millis;
        String filename = null;
        try {
            List<OggettoOsservazioneExtendedDTO> oggettoOsservazioneExtendedDTOList
                    = osservazioneDAO.loadOsservazioneById(idOsservazione, attoreScriva);
            List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList = allegatoIstanzaDAO.loadAllegatoIstanzaByIdIstanza(
                    null, codAllegato, null, null, null, idOsservazione, null, Boolean.FALSE, null, null, null, Boolean.TRUE, attoreScriva.getComponente());
            if (oggettoOsservazioneExtendedDTOList != null && allegatoIstanzaList != null && !allegatoIstanzaList.isEmpty()) {
                filename = getZIPFilename(oggettoOsservazioneExtendedDTOList.get(0));
                for (AllegatoIstanzaExtendedDTO allegatoIstanza : allegatoIstanzaList) {
                    // recupero file da index
                    String uuidIndex = allegatoIstanza.getUuidIndex();
                    String desClasseAllegato = allegatoIstanza.getClasseAllegato() != null ? allegatoIstanza.getClasseAllegato().getDesClasseAllegato() : null;
                    String desCategoriaAllegato = allegatoIstanza.getTipologiaAllegato().getCategoriaAllegato().getDesCategoriaAllegato();
                    String desTipologiaAllegato = allegatoIstanza.getTipologiaAllegato().getDesTipologiaAllegato();
                    String nomeAllegato = allegatoIstanza.getNomeAllegato();
                    String savePath = filename + File.separator + (StringUtils.isNotBlank(desClasseAllegato) ? desClasseAllegato + File.separator : "") + desCategoriaAllegato + File.separator + desTipologiaAllegato + File.separator + nomeAllegato;
                    File fileFromIndex = allegatiManager.getFileFromIndexByUuid(uuidIndex);
                    if (fileFromIndex == null) {
                        ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
                        logError(className, "idOsservazione [" + idOsservazione + "]\n" + error);
                        logEnd(className);
                        throw new GenericException(error);
                    }
                    File fileDestination = new File(savePath);
                    FileUtils.copyFile(fileFromIndex, fileDestination);
                }
                String tmpFileZIP = System.getProperty(JAVA_IO_TMPDIR) + File.separator + filename + ".zip";
                FileOutputStream fileOutputStream = FileUtils.zipFolder(filename, tmpFileZIP);
                if (fileOutputStream == null) {
                    logError(className, "idOsservazione [" + idOsservazione + "]\n Errore durante la creazione del file ZIP");
                    logEnd(className);
                    return null;
                }
                fileOutputStream.close();
                FileUtils.deleteFolder(filename);
                return new File(tmpFileZIP);
            }
            return null;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }
/* SCRIVA-1452
    @Override
    public File getAllegatiListZipFile(Long idIstanza, GenericInputParDTO codiciAllegato, AttoreScriva attoreScriva) {
        logBeginInfo(className, "idIstanza [" + idIstanza + "] - attoreScriva [" + attoreScriva + "]");
        long millis = Instant.now().toEpochMilli();
        String rootPath = idIstanza + "_" + millis;
        String filename = null;
        try {
            if (!codiciAllegato.getList().isEmpty()) {
                IstanzaDTO istanza = istanzaDAO.findByPK(idIstanza);
                filename = getZIPFilename(istanza);
                for (String codiceAllegato : codiciAllegato.getList()) {
                    List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList = allegatoIstanzaDAO.loadAllegatoIstanzaByIdIstanza(idIstanza, codiceAllegato, null, null, null, null, null, Boolean.FALSE, null, null, null, Boolean.TRUE, attoreScriva.getComponente());
                    // recupero file da index
                    String uuidIndex = allegatoIstanzaList.get(0).getUuidIndex();
                    String desClasseAllegato = FileUtils.sanitizeDirName(allegatoIstanzaList.get(0).getClasseAllegato() != null ? allegatoIstanzaList.get(0).getClasseAllegato().getDesClasseAllegato() : null);
                    String desCategoriaAllegato = FileUtils.sanitizeDirName(allegatoIstanzaList.get(0).getTipologiaAllegato().getCategoriaAllegato().getDesCategoriaAllegato());
                    String desTipologiaAllegato = FileUtils.sanitizeDirName(allegatoIstanzaList.get(0).getTipologiaAllegato().getDesTipologiaAllegato());

                    String nomeAllegato = allegatoIstanzaList.get(0).getNomeAllegato();
                    String savePath = filename + File.separator + (StringUtils.isNotBlank(desClasseAllegato) ? desClasseAllegato + File.separator : "") + desCategoriaAllegato + File.separator + desTipologiaAllegato + File.separator + nomeAllegato;
                    File fileFromIndex = allegatiManager.getFileFromIndexByUuid(uuidIndex);
                    if (fileFromIndex == null) {
                        ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
                        logError(className, "idIstanza [" + idIstanza + "]\n" + error);
                        logEnd(className);
                        throw new GenericException(error);
                    }
                    File fileDestination = new File(savePath);
                    FileUtils.copyFile(fileFromIndex, fileDestination);
                }
                String tmpFileZIP = System.getProperty(JAVA_IO_TMPDIR) + File.separator + filename + ".zip";
                FileOutputStream fileOutputStream = FileUtils.zipFolder(filename, tmpFileZIP);
                if (fileOutputStream == null) {
                    logError(className, "idIstanza [" + idIstanza + "]\n Errore durante la creazione del file ZIP");
                    logEnd(className);
                    return null;
                }
                fileOutputStream.close();
                FileUtils.deleteFolder(filename);
                return new File(tmpFileZIP);
            }
            return null;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }
*/

    @Override
    public File getAllegatiListZipFile(Long idIstanza, GenericInputParDTO codiciAllegato, AttoreScriva attoreScriva) {
        logBeginInfo(className, "idIstanza [" + idIstanza + "] - attoreScriva [" + attoreScriva + "]");
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        
        Boolean flgLongPath = Boolean.FALSE; // semaforo per i path lunghi
        
        String filename = null;
        String limitePath = configurazioneDAO.loadConfigByKey(SCRIVA_DOWNLOAD_MAX_PATH).get(0).getValore();
        String maxPathTesto = configurazioneDAO.loadConfigByKey(SCRIVA_DOWNLOAD_MAX_PATH_TESTO).get(0).getValore();
        int limitePathInt;
     
       
        try {

            //inizializzo il file di testo per l'elenco dei file troppo lunghi
            BufferedWriter writer = new BufferedWriter(new FileWriter("LEGGIMI.txt")); 
            writer.write(maxPathTesto);
            writer.newLine();
            writer.close();
           
            //inizializzo il file di testo per l'elenco dei file troppo lunghi
            limitePathInt = Integer.parseInt(limitePath.trim());       
           


            if (!codiciAllegato.getList().isEmpty()) {
                IstanzaDTO istanza = istanzaDAO.findByPK(idIstanza);
                filename = istanza.getCodIstanza();

                 // ciclo su ogni allegato richiesto
                for (String codiceAllegato : codiciAllegato.getList()) {
                    //recupero metadati allegato da db
                    List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList = allegatoIstanzaDAO.loadAllegatoIstanzaByIdIstanza(idIstanza, codiceAllegato, null, null, null, null, null, Boolean.FALSE, null, null, null, Boolean.TRUE, attoreScriva.getComponente());
                    
                    String uuidIndex = allegatoIstanzaList.get(0).getUuidIndex();
                    String desClasseAllegato = FileUtils.sanitizeDirName(allegatoIstanzaList.get(0).getClasseAllegato() != null ? allegatoIstanzaList.get(0).getClasseAllegato().getDesClasseAllegato() : null);
                    String desCategoriaAllegato = FileUtils.sanitizeDirName(allegatoIstanzaList.get(0).getTipologiaAllegato().getCategoriaAllegato().getDesCategoriaAllegato());
                    String nomeAllegato = allegatoIstanzaList.get(0).getNomeAllegato();
                                        
                    // recupero file fisico da index
                    File fileFromIndex = allegatiManager.getFileFromIndexByUuid(uuidIndex);
                    if (fileFromIndex == null) {
                        ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
                        logError(className, "idIstanza [" + idIstanza + "]\n" + error);
                        logEnd(className);
                        throw new GenericException(error);
                    }

                    //dettermina il path di salvataggio
                    String savePath = filename + File.separator + (StringUtils.isNotBlank(desClasseAllegato) ? desClasseAllegato + File.separator : "") + desCategoriaAllegato + File.separator + nomeAllegato;

                    //controllo lunghezza path
                    if(savePath.length() > limitePathInt)
                    {
                        // il file eccede il limite lo inserosco nella cartella principale e appendo il suo nome nel file txt
                        logInfo(className, "Il path per il file "+nomeAllegato+" supera il limite di "+limitePathInt+" caratteri");
                        flgLongPath = Boolean.TRUE; // indica che c'è almeno un file che eccede il limite
                        String savePathOriginal = savePath; // mi salvo il path originale del file
                        // metto il file nella cartella principale
                        savePath =  filename + File.separator + nomeAllegato;
                        // aggiungo il path del file nel file txt
                        BufferedWriter writer2 = new BufferedWriter(new FileWriter("LEGGIMI.txt", true));
                        writer2.write("\n"+savePathOriginal+";");
                        writer2.close();
    
                    }

                    File fileDestination = new File(savePath);
                    FileUtils.copyFile(fileFromIndex, fileDestination);
                }
                
                // se c'è almeno un file che eccede il limite inserisco il file txt nello zip
                if(flgLongPath)
                {
                    String savePath =  filename + File.separator + "LEGGIMI.txt";
                    File fileDestination = new File(savePath);
                    FileUtils.copyFile(new File("LEGGIMI.txt"), fileDestination);

                }

                // genero file zip
                String tmpFileZIP = System.getProperty(JAVA_IO_TMPDIR) + File.separator + filename + ".zip";
                FileOutputStream fileOutputStream = FileUtils.zipFolder(filename, tmpFileZIP);
                if (fileOutputStream == null) {
                    logError(className, "idIstanza [" + idIstanza + "]\n Errore durante la creazione del file ZIP");
                    logEnd(className);
                    return null;
                }
                fileOutputStream.close();
                FileUtils.deleteFolder(filename);
                return new File(tmpFileZIP);
            }
            return null;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Load allegati csv by id istanza file.
     *
     * @param idIstanza    the id istanza
     * @param codAllegato  the cod allegato
     * @param attoreScriva the attore scriva
     * @return the file
     */
    @Override
    public File getAllegatiCSVFile(Long idIstanza, String codAllegato, AttoreScriva attoreScriva) {
        logBeginInfo(className, "idIstanza [" + idIstanza + "] - attoreScriva [" + attoreScriva + "]");
        byte[] bytes = new byte[0];
        String filename = String.valueOf(idIstanza);
        List<IstanzaExtendedDTO> istanzaList = istanzaDAO.loadIstanza(idIstanza);
        if (istanzaList != null && !istanzaList.isEmpty()) {
            IstanzaExtendedDTO istanza = istanzaList.get(0);
            filename = getCSVFilename(istanza, Constants.CSV_ALLEGATI_COD_INFORMAZIONE_SCRIVA, Constants.CSV_NOME_FILE) + ".csv";
            List<AdempimentoConfigDTO> adempimentoConfigList = adempimentoConfigDAO.loadAdempimentoConfigByAdempimentoInformazioneChiave(istanza.getAdempimento().getCodAdempimento(), InformazioniScrivaEnum.CSV_ALLEGATI.name(), Constants.CSV_ALLEGATI_KEY);
            if (adempimentoConfigList != null && !adempimentoConfigList.isEmpty()) {
                String query = adempimentoConfigList.get(0).getValore();
                Map<String, Object> map = new HashMap<>();
                map.put("idIstanza", idIstanza);
                bytes = StringUtils.isNotBlank(query) ? dynamicSqlDAO.getCSVFromQuery(query, Constants.CSV_ALLEGATI_DELIMITER, map, null, null) : new byte[0];
            }
        }
        logEnd(className);
        String tmpFileCSV = System.getProperty(JAVA_IO_TMPDIR) + File.separator + filename;
        return FileUtils.byteArrayToFile(bytes, tmpFileCSV);
    }

    /**
     * Gets allegati csv file by id osservazione.
     *
     * @param idOsservazione the id osservazione
     * @param attoreScriva   the attore scriva
     * @return the allegati csv file by id osservazione
     */
    public File getAllegatiCSVFileByIdOsservazione(Long idOsservazione, AttoreScriva attoreScriva) {
        logBeginInfo(className, "idOsservazione [" + idOsservazione + "] - attoreScriva [" + attoreScriva + "]");
        byte[] bytes = new byte[0];
        List<OggettoOsservazioneExtendedDTO> listaOggettoOsservazioneExtendedDTO = osservazioneDAO.loadOsservazioneById(idOsservazione, null);
        Long idIstanza = listaOggettoOsservazioneExtendedDTO.get(0).getDTO().getIdIstanza();
        String filename = String.valueOf(idIstanza);
        List<IstanzaExtendedDTO> istanzaList = istanzaDAO.loadIstanza(idIstanza);
        if (istanzaList != null && !istanzaList.isEmpty()) {
            IstanzaExtendedDTO istanza = istanzaList.get(0);
            filename = getCSVFilename(istanza, Constants.CSV_OSSERVAZIONI_COD_INFORMAZIONE_SCRIVA, Constants.CSV_NOME_FILE) + ".csv";
            List<AdempimentoConfigDTO> adempimentoConfigList = adempimentoConfigDAO.loadAdempimentoConfigByAdempimentoInformazioneChiave(istanza.getAdempimento().getCodAdempimento(), InformazioniScrivaEnum.CSV_OSSERVAZIONI.getDescrizione(), Constants.CSV_ALLEGATI_KEY);
            if (adempimentoConfigList != null && !adempimentoConfigList.isEmpty()) {
                String query = adempimentoConfigList.get(0).getValore();
                Map<String, Object> map = new HashMap<>();
                map.put("idIstanzaOsservazione", idOsservazione);
                bytes = StringUtils.isNotBlank(query) ? dynamicSqlDAO.getCSVFromQuery(query, Constants.CSV_ALLEGATI_DELIMITER, map, null, null) : new byte[0];
            }
        }
        logEnd(className);
        String tmpFileCSV = System.getProperty(JAVA_IO_TMPDIR) + File.separator + filename;
        return FileUtils.byteArrayToFile(bytes, tmpFileCSV);
    }

    /**
     * Gets allegati pdf file.
     *
     * @param idIstanza    the id istanza
     * @param codAllegato  the cod allegato
     * @param attoreScriva the attore scriva
     * @return the allegati pdf file
     * @throws GenericException the generic exception
     */
    @Override
    public File getAllegatiPDFFile(Long idIstanza, String codAllegato, AttoreScriva attoreScriva) throws Exception {
        logBeginInfo(className, "idIstanza [" + idIstanza + "] - codAllegato [" + codAllegato + "] - attoreScriva :\n" + attoreScriva + "\n");
        byte[] bytes = new byte[0];
        String filename = String.valueOf(idIstanza);
        List<IstanzaExtendedDTO> istanzaList = istanzaDAO.loadIstanza(idIstanza);
        if (istanzaList != null && !istanzaList.isEmpty()) {
            IstanzaExtendedDTO istanza = istanzaList.get(0);
            //String json = istanza.getJsonData();
            //String pathTemplate = istanzaTemplateQuadroDAO.loadPathAllegatiTemplateByIdIstanza(istanza.getIdIstanza());
            String pathTemplate = "templates/Template_Modulo_PDF_VIA_ELENCO_ALLEGATI_V1.docx";
            if (StringUtils.isNotBlank(pathTemplate)) {
                //byte[] out = TemplateUtil.getCompiledTemplatePDF(json, pathTemplate);
                byte[] out = templateUtil.getCompiledTemplatePDF(idIstanza, pathTemplate);
                filename = getCSVFilename(istanza, Constants.CSV_ALLEGATI_COD_INFORMAZIONE_SCRIVA, Constants.CSV_NOME_FILE) + ".pdf";

                File outputFile = FileUtils.byteArrayToFile(out, filename);

                List<AllegatoIstanzaExtendedDTO> allegatiIstanzaList = allegatoIstanzaDAO.loadAllegatoIstanzaByIdIstanzaAndCodTipologia(istanza.getIdIstanza(), Constants.COD_TIPOLOGIA_ALLEGATI_ELENCO);
                // verifica presenza file e, in caso positivo cancellazione da index del vecchio file
                if (allegatiIstanzaList != null && !allegatiIstanzaList.isEmpty()) {
                    allegatiManager.deleteContenutoByUuid(allegatiIstanzaList.get(0).getUuidIndex());
                }

                // recupero tipo allegato per adempimento
                List<AdempimentoTipoAllegatoExtendedDTO> adempimentoTipoAllegatoList = adempimentoTipoAllegatoDAO.loadTipologieAllegatoByCodeAdempimentoAndCodeCategoria(istanza.getAdempimento().getCodAdempimento(), Constants.COD_CATEGORIA_ALLEGATI_SISTEMA, attoreScriva.getComponente());
                List<AdempimentoTipoAllegatoExtendedDTO> adempimentoTipoAllegatoFilteredList = adempimentoTipoAllegatoList != null && !adempimentoTipoAllegatoList.isEmpty() ? adempimentoTipoAllegatoList.stream()
                        .filter(tipo -> tipo.getTipologiaAllegato().getCodTipologiaAllegato().equalsIgnoreCase(Constants.COD_TIPOLOGIA_ALLEGATI_ELENCO))
                        .collect(Collectors.toList()) : null;
                AdempimentoTipoAllegatoExtendedDTO adempimentoTipoAllegato = adempimentoTipoAllegatoFilteredList != null && !adempimentoTipoAllegatoFilteredList.isEmpty() ? adempimentoTipoAllegatoFilteredList.get(0) : null;
                if (adempimentoTipoAllegato == null) {
                    ErrorDTO error = getErrorManager().getError("400", "E100", "Errore durante il recupero delle tipologie allegati.", null, null);
                    logError(className, "idIstanza [" + idIstanza + "]\n" + error);
                    logEnd(className);
                    throw new GenericException(error);
                }

                // upload file su index
                String uuidIndex = allegatiManager.uploadFileOnIndex(istanza, outputFile, filename);

                if (uuidIndex == null || StringUtils.isBlank(uuidIndex)) {
                    ErrorDTO error = getErrorManager().getError("400", "E100", "Errore durante il caricamento dei file su index.", null, null);
                    logError(className, "idIstanza [" + idIstanza + "]\n" + error);
                    logEnd(className);
                    throw new GenericException(error);
                }

                // recupero metadata da index
                Node indexNode = allegatiManager.getMetadataIndexByUuid(uuidIndex);

                // populate allegato istanza
                AllegatoIstanzaExtendedDTO allegatoIstanza = allegatiIstanzaList != null && !allegatiIstanzaList.isEmpty() ? allegatiIstanzaList.get(0) : new AllegatoIstanzaExtendedDTO();
                allegatiManager.populateAllegatoIstanza(allegatoIstanza, indexNode, uuidIndex, filename, outputFile.length(), 0, false, istanza, adempimentoTipoAllegato);
                Long idIstanzaAttoreCompilante = istanzaAttoreManager.getIdIstanzaAttoreCompilante(attoreScriva.getCodiceFiscale(), allegatoIstanza.getIdIstanza(), ComponenteAppEnum.findByDescr(attoreScriva.getComponente()), attoreScriva.getCodiceFiscale());
                allegatoIstanza.setIdIstanzaAttore(idIstanzaAttoreCompilante);
                if (allegatoIstanza.getClasseAllegato() == null) {
                    allegatoIstanza.setClasseAllegato(getClasseAllegato(istanza.getDTO(), allegatoIstanza.getTipologiaAllegato().getCodTipologiaAllegato(), null));
                }
                if (allegatiIstanzaList != null && !allegatiIstanzaList.isEmpty()) {
                    Integer recordUpdated = allegatoIstanzaDAO.updateAllegatoIstanza(allegatoIstanza.getDTO());
                } else {
                    // save allegato istanza
                    Long idAllegatoIstanza = allegatoIstanzaDAO.saveAllegatoIstanza(allegatoIstanza.getDTO(), allegatoIstanza.getTipologiaAllegato().getCategoriaAllegato().getCodCategoriaAllegato());
                }

                allegatiIstanzaList = allegatoIstanzaDAO.loadAllegatoIstanzaByIdIstanzaAndCodTipologia(istanza.getIdIstanza(), Constants.COD_TIPOLOGIA_ALLEGATI_ELENCO);
                if (allegatiIstanzaList != null && !allegatiIstanzaList.isEmpty()) {
                    // update metadata index
                    IndexMetadataPropertyDTO indexMetadataProperty = allegatiManager.getindexMetadataProperty(istanza, allegatiIstanzaList.get(0), null, null, null, null, null);
                    allegatiManager.updateMetadataIndexByUuid(uuidIndex, indexMetadataProperty);
                }
                return outputFile;
            }
        }
        logEnd(className);
        return FileUtils.byteArrayToFile(bytes, filename);
    }

    /**
     * Gets file allegati.
     *
     * @param idIstanza    the id istanza
     * @param outputFormat the output format
     * @param attoreScriva the attore scriva
     * @return the file allegati
     */
    @Override
    public File getFileAllegati(Long idIstanza, String codAllegato, Long idOsservazione, String outputFormat, AttoreScriva attoreScriva) throws Exception {
        logBeginInfo(className, "idIstanza [" + idIstanza + "] - codAllegato [" + codAllegato + "] - outputFormat [" + outputFormat + "]");
        File result = null;
        try {
            if (StringUtils.isNotBlank(outputFormat) && StringUtils.isBlank(codAllegato)) {
                switch (StringUtils.upperCase(outputFormat)) {
                    case ZIP:
                        if (idOsservazione != null) {
                            result = this.getAllegatiZipFileByIdOsservazione(idOsservazione, codAllegato, attoreScriva);
                        } else {
                            result = this.getAllegatiZipFile(idIstanza, codAllegato, attoreScriva);
                        }
                        break;
                    case PDF:
                        result = this.getAllegatiPDFFile(idIstanza, codAllegato, attoreScriva);
                        break;
                    case CSV:
                        if (idOsservazione != null) {
                            result = this.getAllegatiCSVFileByIdOsservazione(idOsservazione, attoreScriva);
                        } else {
                            result = this.getAllegatiCSVFile(idIstanza, codAllegato, attoreScriva);
                        }
                        break;
                    default:
                        result = this.getAllegatoFile(codAllegato, attoreScriva);
                        break;
                }
            } else {
                result = this.getAllegatoFile(codAllegato, attoreScriva);
            }
        } finally {
            logEnd(className);
        }
        return result;
    }


    @Override
    public File getFileAllegatiList(Long idIstanza, GenericInputParDTO codiciAllegato, Long idOsservazione, String outputFormat, AttoreScriva attoreScriva) throws Exception {
        logBeginInfo(className, "idIstanza [" + idIstanza + "] - codAllegato [" + codiciAllegato + "] - outputFormat [" + outputFormat + "]");
        File result = null;
        try {
            if (StringUtils.isNotBlank(outputFormat) && codiciAllegato.getList().size() > 0) {
                switch (StringUtils.upperCase(outputFormat)) {
                    case ZIP:
                        result = this.getAllegatiListZipFile(idIstanza, codiciAllegato, attoreScriva);
                        break;
                }
            }
        } finally {
            logEnd(className);
        }
        return result;
    }


    /**
     * Sets id attore funzionario.
     *
     * @param allegatoIstanza the allegato istanza
     * @param attoreScriva    the attore scriva
     */
    private void setIdAttoreFunzionario(AllegatoIstanzaExtendedDTO allegatoIstanza, AttoreScriva attoreScriva) throws Exception {
        logBeginInfo(className, "allegatoIstanza [" + allegatoIstanza + "] - attoreScriva [" + attoreScriva + "]");
        try {
            if (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) || ComponenteAppEnum.FO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
                if (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
                    allegatoIstanza.setIdFunzionario(funzionarioManager.getIdFunzionarioByCf(attoreScriva.getCodiceFiscale()));
                } else {
                    allegatoIstanza.setIdIstanzaAttore(istanzaAttoreManager.getIdIstanzaAttoreCompilante(attoreScriva.getCodiceFiscale(), allegatoIstanza.getIdIstanza(), ComponenteAppEnum.findByDescr(attoreScriva.getComponente()), attoreScriva.getCodiceFiscale()));
                }
            }
        } catch (Exception e) {
            logError(className, e);
            throw new GenericException(e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Sets dati pubblicazione.
     *
     * @param allegatoIstanza the allegato istanza
     * @param istanza         the istanza
     * @param attoreScriva    the attore scriva
     * @throws GenericException the generic exception
     */
    @Override
    public void setDatiPubblicazione(AllegatoIstanzaExtendedDTO allegatoIstanza, IstanzaDTO istanza, AttoreScriva attoreScriva) throws GenericException {
        logBeginInfo(className, "allegatoIstanza :\n" + allegatoIstanza + "\nistanza :\n" + istanza + "\nattoreScriva :\n" + attoreScriva + "\n");
        try {
            AllegatoIstanzaExtendedDTO allegatoIstanzaOld = allegatoIstanza.getIdAllegatoIstanza() != null ? this.loadAllegatiById(allegatoIstanza.getIdAllegatoIstanza()) : null;
            if (Boolean.TRUE.equals(allegatoIstanza.getFlgDaPubblicare())) {
                Timestamp dataPubblicazione = new Timestamp(System.currentTimeMillis());
                // se precedentemente l'allegato non era pubblicato o
                // se precedentemente l'allegato era stato pubblicato ma non è stata popolata la data di pubblicazione
                boolean allegatoDaPubblicare = allegatoIstanzaOld != null ?
                        (Boolean.FALSE.equals(allegatoIstanzaOld.getFlgDaPubblicare()) || (Boolean.TRUE.equals(allegatoIstanzaOld.getFlgDaPubblicare()) && allegatoIstanzaOld.getDataPubblicazione() == null)) :
                        Boolean.TRUE.equals(allegatoIstanza.getFlgDaPubblicare());
                if (allegatoDaPubblicare) {
                    // recupero istanza e verifica data pubblicazione
                    if (istanza.getDataPubblicazione() != null) {
                        // se l'istanza ha una data pubblicazione, setto anche la data pubblicazione dell'allegato
                        allegatoIstanza.setDataPubblicazione(dataPubblicazione);
                        // SCRIVA-835
                        // Se il documento è associato ad una osservazione che non è stata pubblicata:
                        // - impostare data_pubblicazione dell'osservazione
                        // - impostare stato_osservazione a 'PUBBLICATA' --> vedi query
                        osservazioneDAO.updateDataPubbOsservazioneByIdAllegato(allegatoIstanza.getIdAllegatoIstanza(), dataPubblicazione, null, attoreScriva);
                    } else {
                        allegatoIstanza.setDataPubblicazione(null);
                        // SCRIVA-835
                        // Se il documento è associato ad una osservazione che è stata pubblicata, e per la stessa osservazione non sono presenti altri documenti pubblicati:
                        // - impostare data_pubblicazione dell'osservazione a NULL
                        // - impostare stato_osservazione a 'INVIATA' -> vedi query
                        osservazioneDAO.updateDataPubbOsservazioneByIdAllegato(allegatoIstanza.getIdAllegatoIstanza(), null, null, attoreScriva);
                    }
                    // Aggiorna su index il metadato denominato riservato impostandolo a false.
                    allegatoIstanza.setFlgRiservato(false);


                    // Crea l'url di download pubblico
                    if (allegatoIstanzaOld == null || StringUtils.isBlank(allegatoIstanzaOld.getUrlDoc())) {
                        String fromDate = CalendarUtils.convertDateToString(CalendarUtils.DATE_FORMAT_YYYY_MM_DDHHMM, Calendar.getInstance().getTime());
                        UrlDTO url = this.getLinkIndexFile(allegatoIstanza.getUuidIndex(), fromDate, null);
                        allegatoIstanza.setUrlDoc(url != null ? url.getUrl() : null);
                    }
                }
            } else if (Boolean.FALSE.equals(allegatoIstanza.getFlgDaPubblicare())) {
                allegatoIstanza.setDataPubblicazione(null);
                allegatoIstanza.setUrlDoc(null);
                // SCRIVA-835
                // Se il documento è associato ad una osservazione che è stata pubblicata, e per la stessa osservazione non sono presenti altri documenti pubblicati:
                // - impostare data_pubblicazione dell'osservazione a NULL
                // - impostare stato_osservazione a 'INVIATA' -> vedi query
                osservazioneDAO.updateDataPubbOsservazioneByIdAllegato(allegatoIstanza.getIdAllegatoIstanza(), null, null, attoreScriva);
            }
        } catch (Exception e) {
            logError(className, e);
            throw new GenericException(e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Sets dati protocollazione integrazione.
     *
     * @param allegatoIstanza the allegato istanza
     * @param istanza         the istanza
     * @param attoreScriva    the attore scriva
     * @throws GenericException the generic exception
     */
    public void setDatiProtocollazioneIntegrazione(AllegatoIstanzaExtendedDTO allegatoIstanza, IstanzaDTO istanza, AttoreScriva attoreScriva) throws GenericException {
        logBeginInfo(className, "allegatoIstanza :\n" + allegatoIstanza + "\nistanza :\n" + istanza + "\nattoreScriva :\n" + attoreScriva + "\n");
        try {
            if (allegatoIstanza.getDataProtocolloAllegato() != null && StringUtils.isNotBlank(allegatoIstanza.getNumProtocolloAllegato())) {
                AllegatoIstanzaExtendedDTO allegatoIstanzaOld = allegatoIstanza.getIdAllegatoIstanza() != null ? this.loadAllegatiById(allegatoIstanza.getIdAllegatoIstanza()) : null;
                if (allegatoIstanzaOld == null && StringUtils.isNotBlank(allegatoIstanza.getNomeAllegato())) {
                    allegatoIstanzaOld = this.loadAllegatiByIdIstanzaNome(istanza.getIdIstanza(), allegatoIstanza.getNomeAllegato());
                }
                if (allegatoIstanzaOld != null &&
                        (allegatoIstanzaOld.getDataProtocolloAllegato() != allegatoIstanza.getDataProtocolloAllegato() ||
                                !allegatoIstanzaOld.getNumProtocolloAllegato().equals(allegatoIstanza.getNumProtocolloAllegato())
                        )
                ) {
                    // Nel caso in cui l’attore inserisca o modifichi i dati di protocollazione integrazioni
                    // occorre propagare la modifica a tutti i documenti associati all’integrazione (scriva_t_allegato_istanza),
                    // ossia il documento con nome_file corrispondente a quello dell’integrazione e a tutti i documenti figli (record con id_allegato_padre = id_allegato del file di tipo ELENCO_INTEGR)
                    // e a tutti i documenti aventi stessa DATA_INTEGRAZIONE del documento padre.
                    // I documenti da aggiornare devono avere cod_classe_allegato = INTEGRAZIONI e data_cancellazione non valorizzata
                    allegatoIstanzaDAO.updateDatiIntegrazione(allegatoIstanza.getIdAllegatoIstanza(), allegatoIstanza.getNumProtocolloAllegato(), allegatoIstanza.getDataProtocolloAllegato(), attoreScriva);
                }
            }
        } catch (Exception e) {
            logError(className, e);
            throw new GenericException(e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update data padre from figlio.
     *
     * @param idAllegatoIstanzaPadre the id allegato istanza padre
     * @return the integer
     */
    @Override
    public Integer updateDataPadreFromFiglio(Long idAllegatoIstanzaPadre) {
        logBegin(className);
        try {
            return allegatoIstanzaDAO.updateDataPadreFromFiglio(idAllegatoIstanzaPadre);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update id padre osservazioni.
     *
     * @param allegatoIstanza the allegato istanza
     * @param attoreScriva    the attore scriva
     * @return integer
     */
    @Override
    public Integer updateIdPadreOsservazioni(AllegatoIstanzaExtendedDTO allegatoIstanza, AttoreScriva attoreScriva) {
        logBegin(className);
        try {
            if (allegatoIstanza.getIdIstanzaOsservazione() != null && allegatoIstanza.getIdIstanzaOsservazione() > 0) {
                return allegatoIstanzaDAO.updateIdPadreOsservazioni(allegatoIstanza.getIdIstanzaOsservazione(), attoreScriva);
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return null;
    }

    /**
     * Update data protocollo mod istanza elenco allegati integer.
     *
     * @param idIstanza      the id istanza
     * @param numProtocollo  the num protocollo
     * @param dataProtocollo the data protocollo
     * @param attoreScriva   the attore scriva
     * @return the integer
     */
    @Override
    public Integer updateDataProtocolloModIstanzaElencoAllegati(Long idIstanza, String numProtocollo, Timestamp dataProtocollo, AttoreScriva attoreScriva) {
        logBegin(className);
        int result = 0;

        /*
          TODO: sostituire in futuro la chiamata a questi metodi con una chiamata unica che aggiorna tutti i record 
          senza discriminare sulla tipologia allegato puntuale:
          richiamando il etodo già creato updateProtocolloModIstanzaElencoAllegatiAll
         */
        try {
            if (idIstanza != null && StringUtils.isNotBlank(numProtocollo) && dataProtocollo != null && attoreScriva != null) {
                result = allegatoIstanzaDAO.updateProtocolloModIstanzaElencoAllegati(idIstanza, numProtocollo, dataProtocollo, attoreScriva);
                result += allegatoIstanzaDAO.updateProtocolloFigliElencoAllegati(idIstanza, numProtocollo, dataProtocollo, attoreScriva);
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets classe allegato.
     *
     * @param istanza              the istanza
     * @param codTipologiaAllegato the cod tipologia allegato
     * @param attoreScriva         the attore scriva
     * @return the classe allegato
     */
    public ClasseAllegatoDTO getClasseAllegato(IstanzaDTO istanza, String codTipologiaAllegato, AttoreScriva attoreScriva) {
        logBeginInfo(className, "istanza [" + istanza + "] - attoreScriva [" + attoreScriva + "]");
        /* SCRIVA-727
            Assegnazione della CLASSE DEL DOCUMENTO:
                1) se passata in input usare quella
                2) se non passata in input prima verificare se per l'adempimento e la tipologia allegato è stata assegnata una classe specifica (  con informazione scriva 19 (Classe documento associata)
                3) se non passata in input e non configurata per l'adempimento e tipologia allegato dedurla dal ciclo di vita dell'istanza, scriva_r_stato_istanza_adempi
        */
        List<ClasseAllegatoDTO> classeAllegatoList = classeAllegatoDAO.loadClasseAllegatoByIdIstanzaConfig(istanza.getIdIstanza(), InformazioniScrivaEnum.CLASSE_DOC.name(), codTipologiaAllegato);
        classeAllegatoList = CollectionUtils.isNotEmpty(classeAllegatoList) ? classeAllegatoList : classeAllegatoDAO.loadClasseAllegatoByIdIstanza(istanza.getIdIstanza(), attoreScriva != null ? attoreScriva.getComponente() : null);
        return CollectionUtils.isNotEmpty(classeAllegatoList) ? classeAllegatoList.get(0) : null;
    }

    /**
     * Gets classe allegato by code.
     *
     * @param codClasseAllegato the cod classe allegato
     * @return the classe allegato by code
     */
    @Override
    public ClasseAllegatoDTO getClasseAllegatoByCode(String codClasseAllegato) {
        logBeginInfo(className, (Object) codClasseAllegato);
        List<ClasseAllegatoDTO> classeAllegatoList = classeAllegatoDAO.loadClasseAllegatoByCode(codClasseAllegato);
        return classeAllegatoList != null && !classeAllegatoList.isEmpty() ? classeAllegatoList.get(0) : null;
    }

    /**
     * Gets file from index.
     *
     * @param uuidIndex the uuid index
     * @return the file from index
     */
    @Override
    public File getFileFromIndex(String uuidIndex) {
        logBeginInfo(className, uuidIndex);
        File result = null;
        try {
            if (StringUtils.isNotBlank(uuidIndex)) {
                result = allegatiManager.getFileByUuid(uuidIndex);
            }
        } catch (Exception e) {
            logError(className, e);
            //throw new GenericException(e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Update data invio esterno integer.
     *
     * @param idAllegatoIstanzaList the id allegato istanza list
     * @return the integer
     */
    @Override
    public Integer updateDataInvioEsterno(List<Long> idAllegatoIstanzaList) {
        logBeginInfo(className, "idAllegatoIstanzaList [" + idAllegatoIstanzaList + "]");
        return allegatoIstanzaDAO.updateDataInvioEsterno(idAllegatoIstanzaList);
    }

    /**
     * Gets csv filename.
     *
     * @param istanza       the istanza
     * @param codInfoConfig the cod info config
     * @param keyConfig     the key config
     * @return the csv filename
     */
    private String getCSVFilename(IstanzaExtendedDTO istanza, String codInfoConfig, String keyConfig) {
        String data = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());
        String filename = "Elenco_Allegati_<tipoAdempimento>-<id_istanza>-<data_upload>";
        filename = filename.trim().replace("<tipoAdempimento>", String.valueOf(istanza.getAdempimento().getCodAdempimento())).replace("<id_istanza>", String.valueOf(istanza.getIdIstanza())).replace("<data_upload>", data);
        List<AdempimentoConfigDTO> adempimentoConfigList = adempimentoConfigDAO.loadAdempimentoConfigByAdempimentoInformazioneChiave(istanza.getAdempimento().getCodAdempimento(), codInfoConfig, keyConfig);
        if (adempimentoConfigList != null && !adempimentoConfigList.isEmpty()) {
            filename = adempimentoConfigList.get(0).getValore();
            filename = filename.trim().replace("<tipoAdempimento>", String.valueOf(istanza.getAdempimento().getCodAdempimento())).replace("<id_istanza>", String.valueOf(istanza.getIdIstanza())).replace("<data_upload>", data);
        }
        return filename;
    }

    /**
     * Gets zip filename.
     *
     * @param istanza the istanza
     * @return the zip filename
     */
    private String getZIPFilename(IstanzaDTO istanza) {
        return "Documenti_Istanza_" + istanza.getCodIstanza() + "_" + CalendarUtils.convertDateToString(CalendarUtils.DATE_FORMAT_YYYMMDD__HHMMSS, Calendar.getInstance().getTime());
    }

    /**
     * Gets zip filename.
     *
     * @param oggettoOsservazioneExtendedDTO the oggetto osservazione extended dto
     * @return the zip filename
     */
    private String getZIPFilename(OggettoOsservazioneExtendedDTO oggettoOsservazioneExtendedDTO) {
        return "Documenti_Osservazione_" + oggettoOsservazioneExtendedDTO.getIdIstanzaOsservazione() + "_" + CalendarUtils.convertDateToString(CalendarUtils.DATE_FORMAT_YYYMMDD__HHMMSS, Calendar.getInstance().getTime());
    }

    /**
     * Update id padre figlio.
     *
     * @param idIstanza            the id istanza
     * @param idAllegatoIstanza    the id allegato istanza
     * @param codTipologiaAllegato the cod tipologia allegato
     */
    @Override
    public void updateIdPadreFiglio(Long idIstanza, Long idAllegatoIstanza, String codTipologiaAllegato) {
        if (idIstanza != null && idAllegatoIstanza != null) {
            if (StringUtils.isNotEmpty(codTipologiaAllegato) && TipologiaAllegatoEnum.ELENCO_ALLEGATI.name().equalsIgnoreCase(codTipologiaAllegato)) {
                allegatoIstanzaDAO.updateIdPadreFiglioElencoAllegati(idIstanza, idAllegatoIstanza);
            } else if (StringUtils.isNotEmpty(codTipologiaAllegato) && TipologiaAllegatoEnum.ELENCO_INTEGR.name().equalsIgnoreCase(codTipologiaAllegato)) {
                allegatoIstanzaDAO.updateIdPadreFiglioElencoIntegrazioni(idIstanza, idAllegatoIstanza);
            } else {
                allegatoIstanzaDAO.updateIdPadreFiglio(idIstanza, idAllegatoIstanza);
            }
        }
    }

    /**
     * Load allegati by id istanza nome allegato istanza extended dto.
     *
     * @param idIstanza    the id istanza
     * @param nomeAllegato the nome allegato
     * @return the allegato istanza extended dto
     */
    public AllegatoIstanzaExtendedDTO loadAllegatiByIdIstanzaNome(Long idIstanza, String nomeAllegato) {
        logBeginInfo(className, "idIstanza : [" + idIstanza + "] - nomeAllegato : [" + nomeAllegato + "]");
        List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList = null;
        try {
            allegatoIstanzaList = allegatoIstanzaDAO.loadAllegatoIstanzaByIdIstanzaAndNome(idIstanza, nomeAllegato);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return allegatoIstanzaList != null ? allegatoIstanzaList.get(0) : null;
    }


}