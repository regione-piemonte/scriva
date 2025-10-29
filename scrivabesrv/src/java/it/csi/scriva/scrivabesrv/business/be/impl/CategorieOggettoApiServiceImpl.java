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

import it.csi.scriva.scrivabesrv.business.be.CategorieOggettoApi;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoConfigDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.CategoriaOggettoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaCategoriaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoCompetenzaDAO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoConfigDTO;
import it.csi.scriva.scrivabesrv.dto.CategoriaOggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaCategoriaDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaCategoriaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.SearchHeaderDTO;
import it.csi.scriva.scrivabesrv.dto.TipoCompetenzaDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.InformazioniScrivaEnum;
import it.csi.scriva.scrivabesrv.util.Constants;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Categorie oggetto api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CategorieOggettoApiServiceImpl extends BaseApiServiceImpl implements CategorieOggettoApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private AdempimentoConfigDAO adempimentoConfigDAO;

    /**
     * The Categoria oggetto dao.
     */
    @Autowired
    CategoriaOggettoDAO categoriaOggettoDAO;

    /**
     * The Istanza dao.
     */
    @Autowired
    IstanzaDAO istanzaDAO;

    /**
     * The Oggetto istanza dao.
     */
    @Autowired
    OggettoIstanzaDAO oggettoIstanzaDAO;

    /**
     * The Oggetto istanza categoria dao.
     */
    @Autowired
    OggettoIstanzaCategoriaDAO oggettoIstanzaCategoriaDAO;

    /**
     * The Tipo competenza dao.
     */
    @Autowired
    TipoCompetenzaDAO tipoCompetenzaDAO;

    /**
     * Load categorie progettuali response.
     *
     * @param xRequestAuth     the x request auth
     * @param xRequestId       the x request id
     * @param idAdempimento    the id adempimento
     * @param idIstanza        the id istanza
     * @param idOggettoIstanza the id oggetto istanza
     * @param search           the search
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      HttpServletRequest
     * @return Response response
     */
    @Override
    public Response loadCategorieProgettuali(String xRequestAuth, String xRequestId,
                                             Long idAdempimento, Long idIstanza, Long idOggettoIstanza,
                                             String search, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String info = "idAdempimento [" + idAdempimento + "] - idIstanza [" + idIstanza + "] - idOggettoIstanza [" + idOggettoIstanza + "] - search [" + search + "]";
        logBeginInfo(className, (Object) info);
        if (idIstanza != null) {
            Response response = setAttoreRight(httpHeaders, idIstanza);
            if (response != null) {
                return response;
            }
        } else {
            try {
                attoreScriva = getAttoreScriva(httpHeaders);
            } catch (GenericException e) {
                logError(className, e);
                return getResponseError(className, Integer.valueOf(e.getError().getStatus()), "E100", e.getError().getTitle(), null);
            }
        }
        List<CategoriaOggettoExtendedDTO> categoriaOggettoList = new ArrayList<>();
        List<TipoCompetenzaDTO> tipoCompetenzaList = null;
        List<OggettoIstanzaCategoriaExtendedDTO> oggettoIstanzaCategoriaList = null;
        List<SearchHeaderDTO> searchHeaderList = null;
        if (idIstanza != null || idOggettoIstanza != null) {
            oggettoIstanzaCategoriaList = idIstanza != null ? oggettoIstanzaCategoriaDAO.loadOggettoIstanzaCategoriaByIdIstanza(idIstanza) : oggettoIstanzaCategoriaDAO.loadOggettoIstanzaCategoriaByIdOggettoIstanza(idOggettoIstanza, null);
        } else {
            categoriaOggettoList = idAdempimento != null ? categoriaOggettoDAO.loadCategoriaOggettoByIdAdempimentoComponenteSearchCriteria(idAdempimento, attoreScriva.getComponente(), search) : categoriaOggettoDAO.loadCategorieOggetto(attoreScriva.getComponente());
            tipoCompetenzaList = idAdempimento != null ? tipoCompetenzaDAO.loadTipoCompetenzaByIdAdempimento(idAdempimento) : tipoCompetenzaDAO.loadTipiCompetenza();
            populateTipiCompetenzaForCategoriaNew(categoriaOggettoList, tipoCompetenzaList);
            searchHeaderList = getSearchHeaderList(categoriaOggettoList);
        }
        logEnd(className);
        if ((oggettoIstanzaCategoriaList != null && oggettoIstanzaCategoriaList.isEmpty()) && (categoriaOggettoList.isEmpty())) {
            ErrorDTO error = getErrorManager().getError("404", "", "Elemento non trovato con id [" + idIstanza + "]", null, null);
            return Response.serverError().entity(error).status(404).build();
        }
        return Response.ok(oggettoIstanzaCategoriaList != null ? oggettoIstanzaCategoriaList : categoriaOggettoList).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header("CountCompetenze", new JSONArray(searchHeaderList)).build();
    }

    /**
     * @param idAdempimento   idAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadCategoriaProgettualeByIdAdempimento(Long idAdempimento, String search, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idAdempimento [" + idAdempimento + "] - search [" + search + "]");
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
            return getResponseError(className, 500, "E100", null, null);
        }
        List<CategoriaOggettoExtendedDTO> categoriaOggettoList = categoriaOggettoDAO.loadCategoriaOggettoByIdAdempimentoComponenteSearchCriteria(idAdempimento, attoreScriva.getComponente(), search);
        List<TipoCompetenzaDTO> tipoCompetenzaList = tipoCompetenzaDAO.loadTipoCompetenzaByIdAdempimento(idAdempimento);
        populateTipiCompetenzaForCategoriaNew(categoriaOggettoList, tipoCompetenzaList);
        List<SearchHeaderDTO> searchHeaderList = getSearchHeaderList(categoriaOggettoList);
        logEnd(className);
        return Response.ok(categoriaOggettoList).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header("CountCompetenze", new JSONArray(searchHeaderList)).build();
    }

    /**
     * Gets search header list.
     *
     * @param categoriaOggettoList the categoria oggetto list
     * @return the search header list
     */
    private List<SearchHeaderDTO> getSearchHeaderList(List<CategoriaOggettoExtendedDTO> categoriaOggettoList) {
        logBegin(className);
        List<SearchHeaderDTO> searchHeaderList = new ArrayList<>();
        if (categoriaOggettoList != null) {
            List<TipoCompetenzaDTO> tipoCompetenzaList = tipoCompetenzaDAO.loadTipiCompetenza();
            searchHeaderList.add(getSearchHeader("TUTTE", "Tutte", categoriaOggettoList.size(), 0L));
            int countCategorieTotali = 0;
            for (TipoCompetenzaDTO tipoCompetenza : tipoCompetenzaList) {
                List<CategoriaOggettoExtendedDTO> categoriaOggettoFilteredList = categoriaOggettoList.stream()
                        .filter(categoriaOggetto -> tipoCompetenza.getCodTipoCompetenza().equalsIgnoreCase(categoriaOggetto.getTipoCompetenzaList() != null && !categoriaOggetto.getTipoCompetenzaList().isEmpty() ? categoriaOggetto.getTipoCompetenzaList().get(0).getCodTipoCompetenza() : ""))
                        .collect(Collectors.toList());
                if (!categoriaOggettoFilteredList.isEmpty()) {
                    countCategorieTotali += categoriaOggettoFilteredList.size();
                    searchHeaderList.add(getSearchHeader(tipoCompetenza.getCodTipoCompetenza(), tipoCompetenza.getDesTipoCompetenza(), categoriaOggettoFilteredList.size(), tipoCompetenza.getOrdinamentoTipoCompetenza()));
                }
            }
            //searchHeaderList.add(getSearchHeader("TUTTE", "Tutte", countCategorieTotali, 0L));
        }
        logEnd(className);
        return searchHeaderList;
    }

    /**
     * Gets search header.
     *
     * @param codice      the codice
     * @param descrizione the descrizione
     * @param size        the size
     * @param ordinamento the ordinamento
     * @return the search header
     */
    private SearchHeaderDTO getSearchHeader(String codice, String descrizione, int size, long ordinamento) {
        SearchHeaderDTO searchHeader = new SearchHeaderDTO();
        searchHeader.setCodice(codice);
        searchHeader.setLabel(descrizione + " (" + size + ")");
        searchHeader.setOrdinamento(ordinamento);
        return searchHeader;
    }

    /**
     * @param idOggettoIstanza idOggettoIstanza
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadCategoriaProgettualeByIdOggettoIstanza(Long idOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idOggettoIstanza);

        try {
                 attoreScriva = getAttoreScriva(httpHeaders);
             } catch (GenericException e) {
                 logError(className, e);
                 return getResponseError(className, 500, "E100", null, null);
             }

        List<OggettoIstanzaCategoriaExtendedDTO> oggettoIstanzaCategoriaList = oggettoIstanzaCategoriaDAO.loadOggettoIstanzaCategoriaByIdOggettoIstanza(idOggettoIstanza,attoreScriva.getComponente());
        populateTipiCompetenzaForOggettoIstanza(oggettoIstanzaCategoriaList);
        return getResponseList(oggettoIstanzaCategoriaList, className);
    }

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    @Transactional
    public Response saveOggettoIstanzaCategoria(OggettoIstanzaCategoriaExtendedDTO oggettoIstanzaCategoria, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, oggettoIstanzaCategoria);
        // Recupero oggetto-istanza per recuperare istanza e relativi permessi di scrittura
        List<OggettoIstanzaExtendedDTO> oggettoIstanzaList = oggettoIstanzaDAO.loadOggettoIstanzaById(oggettoIstanzaCategoria.getIdOggettoIstanza());
        if (oggettoIstanzaList == null || oggettoIstanzaList.isEmpty()) {
            return getResponseSaveUpdate(null, className);
        }
        // Verifica permessi di scrittura su istanza
        Response response = setAttoreRight(httpHeaders, oggettoIstanzaList.get(0).getIdIstanza(), Boolean.TRUE);
        /*
        if (response != null) {
            return response;
        }
        */
        oggettoIstanzaCategoria.setGestAttoreIns(attoreScriva.getCodiceFiscale());
        Long idOggettoIstanza = oggettoIstanzaCategoriaDAO.saveOggettoIstanzaCategoria(oggettoIstanzaCategoria.getDTO());
        if (idOggettoIstanza == null) {
            return getResponseSaveUpdate(null, className);
        }
        List<OggettoIstanzaCategoriaExtendedDTO> oggettoIstanzaCategoriaList = oggettoIstanzaCategoriaDAO.loadOggettoIstanzaCategoriaByIdOggettoIstanzaIdCategoria(idOggettoIstanza, oggettoIstanzaCategoria.getCategoriaOggetto().getIdCategoriaOggetto());
        populateTipiCompetenzaForOggettoIstanza(oggettoIstanzaCategoriaList);
        updateIstanzaPraticaTimestampAttore(oggettoIstanzaList.get(0).getIdIstanza(), attoreScriva);
        return getResponseSave((oggettoIstanzaCategoriaList != null && !oggettoIstanzaCategoriaList.isEmpty() ? oggettoIstanzaCategoriaList.get(0) : oggettoIstanzaCategoriaList), className, "/categorie-progettuali/id-oggetto-istanza/" + idOggettoIstanza);
    }

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    @Transactional
    public Response updateOggettoIstanzaCategoria(OggettoIstanzaCategoriaExtendedDTO oggettoIstanzaCategoria, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, oggettoIstanzaCategoria);
        // Recupero oggetto-istanza per recuperare istanza e relativi permessi di scrittura
        List<OggettoIstanzaExtendedDTO> oggettoIstanzaList = oggettoIstanzaDAO.loadOggettoIstanzaById(oggettoIstanzaCategoria.getIdOggettoIstanza());
        if (oggettoIstanzaList == null || oggettoIstanzaList.isEmpty()) {
            return getResponseSaveUpdate(null, className);
        }
        // Verifica permessi di scrittura su istanza
        Response response = setAttoreRight(httpHeaders, oggettoIstanzaList.get(0).getIdIstanza(), Boolean.TRUE);
        /*
        if (response != null) {
            return response;
        }
         */
        oggettoIstanzaCategoria.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
        Integer res = oggettoIstanzaCategoriaDAO.updateOggettoIstanzaCategoria(oggettoIstanzaCategoria.getDTO());
        if (res == null || res < 1) {
            return getResponseSaveUpdate(null, className);
        }
        List<OggettoIstanzaCategoriaExtendedDTO> oggettoIstanzaCategoriaList = oggettoIstanzaCategoriaDAO.loadOggettoIstanzaCategoriaByIdOggettoIstanzaIdCategoria(oggettoIstanzaCategoria.getIdOggettoIstanza(), oggettoIstanzaCategoria.getCategoriaOggetto().getIdCategoriaOggetto());
        populateTipiCompetenzaForOggettoIstanza(oggettoIstanzaCategoriaList);
        updateIstanzaPraticaTimestampAttore(oggettoIstanzaList.get(0).getIdIstanza(), attoreScriva);
        return getResponseSaveUpdate((oggettoIstanzaCategoriaList != null && !oggettoIstanzaCategoriaList.isEmpty() ? oggettoIstanzaCategoriaList.get(0) : oggettoIstanzaCategoriaList), className);
    }

    /**
     * Add categorie modifica response.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the response
     */
    @Override
    public Response addCategorieModifica(Long idOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idOggettoIstanza);
        // Recupero oggetto-istanza per recuperare istanza e relativi permessi di scrittura
        List<OggettoIstanzaExtendedDTO> oggettoIstanzaList = oggettoIstanzaDAO.loadOggettoIstanzaById(idOggettoIstanza);
        if (oggettoIstanzaList == null || oggettoIstanzaList.isEmpty()) {
            return getResponseSaveUpdate(null, className);
        }
        // Verifica permessi di scrittura su istanza
        Response response = setAttoreRight(httpHeaders, oggettoIstanzaList.get(0).getIdIstanza(), Boolean.TRUE);
        if (response != null) {
            return response;
        }

        List<IstanzaExtendedDTO> istanzaList = istanzaDAO.loadIstanza(oggettoIstanzaList.get(0).getIdIstanza());
        String codeAdempimento = istanzaList.get(0).getAdempimento().getCodAdempimento();

        OggettoIstanzaCategoriaDTO oggettoIstanzaCategoria = new OggettoIstanzaCategoriaDTO();
        oggettoIstanzaCategoria.setIdOggettoIstanza(idOggettoIstanza);
        oggettoIstanzaCategoria.setFlgCatModificaOggetto(Boolean.TRUE);
        oggettoIstanzaCategoria.setGestAttoreIns(attoreScriva.getCodiceFiscale());

        List<AdempimentoConfigDTO> adempimentoConfigCatOggRegionaleList = adempimentoConfigDAO.loadAdempimentoConfigByAdempimentoInformazioneChiave(codeAdempimento, InformazioniScrivaEnum.CATEGORIA_OGGETTO.name(), Constants.CONFIG_KEY_CATEGORIA_MODIFICA_REGIONALE);
        saveCategorieOggettoList(oggettoIstanzaCategoria, adempimentoConfigCatOggRegionaleList);

        List<AdempimentoConfigDTO> adempimentoConfigCatOggProvincialeList = adempimentoConfigDAO.loadAdempimentoConfigByAdempimentoInformazioneChiave(codeAdempimento, InformazioniScrivaEnum.CATEGORIA_OGGETTO.name(), Constants.CONFIG_KEY_CATEGORIA_MODIFICA_PROVINCIALE);
        saveCategorieOggettoList(oggettoIstanzaCategoria, adempimentoConfigCatOggProvincialeList);

        List<AdempimentoConfigDTO> adempimentoConfigCatOggComunaleList = adempimentoConfigDAO.loadAdempimentoConfigByAdempimentoInformazioneChiave(codeAdempimento, InformazioniScrivaEnum.CATEGORIA_OGGETTO.name(), Constants.CONFIG_KEY_CATEGORIA_MODIFICA_COMUNALE);
        saveCategorieOggettoList(oggettoIstanzaCategoria, adempimentoConfigCatOggComunaleList);

        List<OggettoIstanzaCategoriaExtendedDTO> oggettoIstanzaCategoriaList = oggettoIstanzaCategoriaDAO.loadOggettoIstanzaCategoriaByIdOggettoIstanza(idOggettoIstanza,null);
        populateTipiCompetenzaForOggettoIstanza(oggettoIstanzaCategoriaList);
        updateIstanzaPraticaTimestampAttore(oggettoIstanzaList.get(0).getIdIstanza(), attoreScriva);
        return getResponseSaveUpdate(oggettoIstanzaCategoriaList, className, "/categorie-progettuali/id-oggetto-istanza/" + idOggettoIstanza);
    }

    /**
     * Delete soggetto istanza response.
     *
     * @param uid             the uid
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response deleteOggettoIstanzaCategoria(String uid, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, uid);

        //Recupero oggetto-istanza-categoria
        List<OggettoIstanzaCategoriaExtendedDTO> oggettoIstanzaCategoriaList = oggettoIstanzaCategoriaDAO.loadOggettoIstanzaCategoriaByUID(uid);
        if (oggettoIstanzaCategoriaList == null || oggettoIstanzaCategoriaList.isEmpty()) {
            return getResponseSaveUpdate(null, className);
        }
        Long idOggettoIstanza = oggettoIstanzaCategoriaList.get(0).getIdOggettoIstanza();

        // Recupero oggetto-istanza per recuperare istanza e relativi permessi di scrittura
        List<OggettoIstanzaExtendedDTO> oggettoIstanzaList = oggettoIstanzaDAO.loadOggettoIstanzaById(idOggettoIstanza);
        if (oggettoIstanzaList == null || oggettoIstanzaList.isEmpty()) {
            return getResponseSaveUpdate(null, className);
        }

        // Verifica permessi di scrittura su istanza
        Response response = setAttoreRight(httpHeaders, oggettoIstanzaList.get(0).getIdIstanza(), Boolean.TRUE);
        if (response != null) {
            return response;
        }
        updateIstanzaPraticaTimestampAttore(oggettoIstanzaList.get(0).getIdIstanza(), attoreScriva);
        return getResponseDelete(oggettoIstanzaCategoriaDAO.deleteOggettoIstanzaCategoria(uid), className);
    }

    /**
     * Populate tipi competenza for categoria new.
     *
     * @param categoriaOggettoList the categoria oggetto list
     * @param tipoCompetenzaList   the tipo competenza list
     */
    private void populateTipiCompetenzaForCategoriaNew(List<CategoriaOggettoExtendedDTO> categoriaOggettoList, List<TipoCompetenzaDTO> tipoCompetenzaList) {
        int i = 1;
        if (categoriaOggettoList != null && !categoriaOggettoList.isEmpty() && tipoCompetenzaList != null && !tipoCompetenzaList.isEmpty()) {
            for (CategoriaOggettoExtendedDTO categoriaOggetto : categoriaOggettoList) {
                List<TipoCompetenzaDTO> tipoCompetenzaListFilteredByCategoria = tipoCompetenzaList.stream()
                        .filter(tipoComp -> tipoComp.getIdCategoriaOggetto().equals(categoriaOggetto.getIdCategoriaOggetto()))
                        .collect(Collectors.toList());
                logDebug(className, "categoriaOggetto : " + categoriaOggetto.getIdCategoriaOggetto() + ". " + categoriaOggetto.getDesCategoriaOggetto() + " - tipoCompetenza: " + tipoCompetenzaListFilteredByCategoria.size());
                categoriaOggetto.setTipoCompetenzaList(tipoCompetenzaListFilteredByCategoria);
                i++;
            }
            logDebug(className, "Record totali : " + i);
        }
    }

    /**
     * Populate tipi competenza for oggetto istanza.
     *
     * @param oggettoIstanzaCategoriaList the oggetto istanza categoria list
     */
    private void populateTipiCompetenzaForOggettoIstanza(List<OggettoIstanzaCategoriaExtendedDTO> oggettoIstanzaCategoriaList) {
        if (oggettoIstanzaCategoriaList != null && !oggettoIstanzaCategoriaList.isEmpty()) {
            for (OggettoIstanzaCategoriaExtendedDTO oggettoIstanzaCategoria : oggettoIstanzaCategoriaList) {
                List<TipoCompetenzaDTO> tipoCompetenzaList = tipoCompetenzaDAO.loadTipoCompetenzaByIdCategoriaOggetto(oggettoIstanzaCategoria.getCategoriaOggetto().getIdCategoriaOggetto());
                oggettoIstanzaCategoria.getCategoriaOggetto().setTipoCompetenzaList(tipoCompetenzaList);
            }
        }
    }

    /**
     * Save categorie oggetto list.
     *
     * @param oggettoIstanzaCategoria     the oggetto istanza categoria
     * @param adempimentoConfigCatOggList the adempimento config cat ogg list
     */
    private void saveCategorieOggettoList(OggettoIstanzaCategoriaDTO oggettoIstanzaCategoria, List<AdempimentoConfigDTO> adempimentoConfigCatOggList) {
        logBeginInfo(className, "oggettoIstanzaCategoria :\n" + oggettoIstanzaCategoria + "\nadempimentoConfigCatOggList :\n" + adempimentoConfigCatOggList + "\n");
        if (adempimentoConfigCatOggList != null && !adempimentoConfigCatOggList.isEmpty()) {
            String categoriaOggetto = adempimentoConfigCatOggList.get(0).getValore();
            Long idAdempimento = adempimentoConfigCatOggList.get(0).getIdAdempimento();
            String[] categoriaOggettoArray = categoriaOggetto.split("\\|");
            logDebug(className, "categoriaOggettoArray : " + Arrays.toString(categoriaOggettoArray));
            for (String categoriaOggettoToAdd : categoriaOggettoArray) {
                List<CategoriaOggettoExtendedDTO> categoriaOggettoList = categoriaOggettoDAO.loadCategoriaOggettoByCode(categoriaOggettoToAdd, idAdempimento);
                if (categoriaOggettoList != null && !categoriaOggettoList.isEmpty()) {
                    oggettoIstanzaCategoria.setIdCategoriaOggetto(categoriaOggettoList.get(0).getIdCategoriaOggetto());
                    Long id = oggettoIstanzaCategoriaDAO.saveOggettoIstanzaCategoria(oggettoIstanzaCategoria);
                    if (id == null) {
                        logError(className, "Error saving [" + categoriaOggettoToAdd + "] on idOggettoIstanza: [" + oggettoIstanzaCategoria.getIdOggettoIstanza() + "]");
                    }
                } else {
                    logError(className, "Error saving [" + categoriaOggettoToAdd + "] on idOggettoIstanza: [" + oggettoIstanzaCategoria.getIdOggettoIstanza() + "] ERROR : NOT FOUND CATEGORIA " + categoriaOggettoToAdd);
                }
            }
        }
        logEnd(className);
    }
}