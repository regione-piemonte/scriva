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

import it.csi.scriva.scrivabesrv.business.be.CompetenzeTerritorioApi;

import it.csi.scriva.scrivabesrv.business.be.exception.DuplicateRecordException;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.CompetenzaTerritorioDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaCompetenzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoCompetenzaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.CompetenzeTerritorioService;
import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioResponsabileDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaCompetenzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.SearchHeaderDTO;
import it.csi.scriva.scrivabesrv.dto.TipoCompetenzaDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.manager.JsonDataManager;
import org.apache.commons.lang3.StringUtils;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Competenze territorio api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CompetenzeTerritorioApiServiceImpl extends BaseApiServiceImpl implements CompetenzeTerritorioApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Competenza territorio dao.
     */
    @Autowired
    CompetenzaTerritorioDAO competenzaTerritorioDAO;

    /**
     * The Competenze territorio service.
     */
    @Autowired
    CompetenzeTerritorioService competenzeTerritorioService;

    /**
     * The Istanza dao.
     */
    @Autowired
    IstanzaDAO istanzaDAO;

    /**
     * The Istanza competenza dao.
     */
    @Autowired
    IstanzaCompetenzaDAO istanzaCompetenzaDAO;

    /**
     * The Tipo competenza dao.
     */
    @Autowired
    TipoCompetenzaDAO tipoCompetenzaDAO;

    /**
     * The JsonData manager.
     */
    @Autowired
    private JsonDataManager jsonDataManager;


    /**
     * Load competenze territorio response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idAdempimento   the id adempimento
     * @param idIstanza       the id istanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @Override
    public Response loadCompetenzeTerritorio(String xRequestAuth, String xRequestId, Long idAdempimento, Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idAdempimento [" + idAdempimento + "] - idIstanza [" + idIstanza + "]");
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
            logEnd(className);
            return Response.serverError().entity(e.getError()).status(Integer.parseInt(e.getError().getStatus())).build();
        }
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList = null;
        List<CompetenzaTerritorioExtendedDTO> competenzaTerritorioList = null;
        List<SearchHeaderDTO> searchHeaderList = new ArrayList<>();
        if (idAdempimento == null && idIstanza == null) {
            competenzaTerritorioList = competenzeTerritorioService.loadCompetenzeTerritorio(attoreScriva);
        } else {
            competenzaTerritorioList = idIstanza == null ? competenzeTerritorioService.loadCompetenzeTerritorio(idAdempimento, attoreScriva) : null;
            istanzaCompetenzaList = idIstanza != null ? competenzeTerritorioService.loadIstanzaCompetenzeTerritorio(idIstanza, attoreScriva) : null;
            searchHeaderList = getSearchHeaderList(competenzaTerritorioList);
        }
        logEnd(className);
        return Response.ok(idIstanza != null ? istanzaCompetenzaList : competenzaTerritorioList).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header("CountCompetenze", new JSONArray(searchHeaderList)).build();
    }

    /**
     * Gets search header list.
     *
     * @param competenzaTerritorioList the competenza territorio list
     * @return the search header list
     */
    private List<SearchHeaderDTO> getSearchHeaderList(List<CompetenzaTerritorioExtendedDTO> competenzaTerritorioList) {
        logBegin(className);
        List<SearchHeaderDTO> searchHeaderList = new ArrayList<>();
        if (competenzaTerritorioList != null) {
            List<TipoCompetenzaDTO> tipoCompetenzaList = tipoCompetenzaDAO.loadTipiCompetenza();
            searchHeaderList.add(getSearchHeader("TUTTE", "Tutte", competenzaTerritorioList.size(), 0L));
            for (TipoCompetenzaDTO tipoCompetenza : tipoCompetenzaList) {
                List<CompetenzaTerritorioExtendedDTO> competenzaTerritorioFilterByTipoList = competenzaTerritorioList.stream().filter(competenzaTerritorio -> tipoCompetenza.getCodTipoCompetenza().equalsIgnoreCase(competenzaTerritorio.getTipoCompetenza().getCodTipoCompetenza())).collect(Collectors.toList());
                searchHeaderList.add(getSearchHeader(tipoCompetenza.getCodTipoCompetenza(), tipoCompetenza.getDesTipoCompetenza(), competenzaTerritorioFilterByTipoList.size(), tipoCompetenza.getOrdinamentoTipoCompetenza()));
            }
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
     * @param idCompetenzaTerritorio idCompetenzaTerritorio
     * @param securityContext        SecurityContext
     * @param httpHeaders            HttpHeaders
     * @param httpRequest            HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadCompetenzeTerritorioById(String xRequestAuth, String xRequestId, Long idCompetenzaTerritorio, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idCompetenzaTerritorio);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
            logEnd(className);
            return Response.serverError().entity(e.getError()).status(Integer.parseInt(e.getError().getStatus())).build();
        }
        return getResponseList(competenzeTerritorioService.loadCompetenzeTerritorioById(idCompetenzaTerritorio, attoreScriva), className);
    }

    /**
     * Verifica competenze territorio by id istanza adempimento response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    //@SuppressWarnings("unused")
    /*
    @Override
	public Response verificaCompetenzeTerritorioByIdIstanza(String xRequestAuth, String xRequestId, Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList = null;
        // Verifica permessi su istanza
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
        try {
            istanzaCompetenzaList = competenzeTerritorioService.verificaCompetenzeTerritorioByIdIstanza(idIstanza, attoreScriva);
        } catch (GenericException e) {
            logError(className, e);
            logEnd(className);
            return Response.serverError().entity(e.getError()).status(e.getError() != null && StringUtils.isNotBlank(e.getError().getStatus()) ? Integer.parseInt(e.getError().getStatus()) : 500).build();
        } catch (Exception e) {
            logError(className, e);
        }
        return getResponseList(istanzaCompetenzaList, className);
    }
    */

    @Override
    public Response gestioneAC(String xRequestAuth, String xRequestId, Long idIstanza,Boolean ac3,String tipoSelezione, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
    	logBeginInfo(className, "idIstanza: [" + idIstanza + "]"+"ac3: [" + ac3 + "]"+"tipoSelezione: [" + tipoSelezione + "]");
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList = null;
        // Verifica permessi su istanza
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
        try {

        	if (ac3 != null && tipoSelezione != null)

        		istanzaCompetenzaList = competenzeTerritorioService.gestioneAC(idIstanza,ac3,tipoSelezione, attoreScriva);

        	else

        		istanzaCompetenzaList = competenzeTerritorioService.verificaCompetenzeTerritorioByIdIstanza(idIstanza, attoreScriva);


        } catch (GenericException e) {
            logError(className, e);
            logEnd(className);
            return Response.serverError().entity(e.getError()).status(e.getError() != null && StringUtils.isNotBlank(e.getError().getStatus()) ? Integer.parseInt(e.getError().getStatus()) : 500).build();
        } catch (Exception e) {
            logError(className, e);
        }
        return getResponseList(istanzaCompetenzaList, className);
    }

    /**
     * Calculate istanza competenza territorio secondarie response.
     *
     * @param idIstanza         the id istanza
     * @param livelliCompetenza the livelli competenza
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @Override
    public Response calculateIstanzaCompetenzaTerritorioSecondarie(String xRequestAuth, String xRequestId, Long idIstanza, String livelliCompetenza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idIstanza: [" + idIstanza + "] - livelliCompetenza [" + livelliCompetenza + "]");
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList = null;
        // Verifica permessi di scrittura su istanza
        Response response = setAttoreRight(httpHeaders, idIstanza, Boolean.TRUE);
        if (response != null) {
            return response;
        }
        try {
            istanzaCompetenzaList = competenzeTerritorioService.calculateIstanzaCompetenzaTerritorioSecondarie(idIstanza, livelliCompetenza, attoreScriva);
        } catch (GenericException e) {
            logError(className, e);
            logEnd(className);
            return Response.serverError().entity(e.getError()).status(e.getError() != null && StringUtils.isNotBlank(e.getError().getStatus()) ? Integer.parseInt(e.getError().getStatus()) : 500).build();
        } catch (Exception e) {
            logError(className, e);
        }
        return getResponseList(istanzaCompetenzaList, className);
    }

    /**
     * Delete istanza competenza territorio secondarie response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    @Transactional
    public Response deleteIstanzaCompetenzaTerritorioSecondarie(String xRequestAuth, String xRequestId, Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        try {
            // Verifica permessi di scrittura su istanza
            Response response = setAttoreRight(httpHeaders, idIstanza, Boolean.TRUE);
            if (response != null) {
                return response;
            }
            return getResponseDelete(competenzeTerritorioService.deleteIstanzaCompetenzaTerritorioCategorieProgettuali(idIstanza), className);
        } catch (Exception e) {
            logError(className, e);
            return getResponseDelete(null, className);
        } finally {
            logEnd(className);
        }
    }
    


    /**
     * @param istanzaCompetenza istanzaCompetenza
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response
     */
    @Override
    @Transactional
    public Response saveIstanzaCompetenzaTerritorio(String xRequestAuth, String xRequestId, IstanzaCompetenzaExtendedDTO istanzaCompetenza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, istanzaCompetenza);
        try {
            // Verifica permessi di scrittura su istanza
            Response response = setAttoreRight(httpHeaders, istanzaCompetenza.getIdIstanza(), Boolean.TRUE);
            if (response != null && !ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
                return response;
            }
            return getResponseSave(competenzeTerritorioService.saveIstanzaCompetenzaTerritorio(istanzaCompetenza, attoreScriva), className, "/competenze-territorio/id-istanza/" + istanzaCompetenza.getIdIstanza());
        } catch (DuplicateRecordException e) {
            logError(className, "idIstanza [" + istanzaCompetenza.getIdIstanza() + "] - idCompetenzaTerritorio [" + istanzaCompetenza.getCompetenzaTerritorio().getIdCompetenzaTerritorio() + "] : DUPLICATO");
            return getResponseSaveUpdate(-409, className);
        } catch (Exception e) {
            logError(className, e);
            return getResponseSaveUpdate(null, className);
        } finally {
            logEnd(className);
        }
    }

    /**
     * @param istanzaCompetenza istanzaCompetenza
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response
     */
    @Override
    @Transactional
    public Response updateIstanzaCompetenzaTerritorio(String xRequestAuth, String xRequestId, IstanzaCompetenzaExtendedDTO istanzaCompetenza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, istanzaCompetenza);
        try {
            // Verifica permessi di scrittura su istanza
            Response response = setAttoreRight(httpHeaders, istanzaCompetenza.getIdIstanza(), Boolean.TRUE);
            if (response != null) {
                return response;
            }
            return getResponseSaveUpdate(competenzeTerritorioService.updateIstanzaCompetenzaTerritorio(istanzaCompetenza, attoreScriva), className);
        } catch (Exception e) {
            logError(className, e);
            return getResponseSaveUpdate(null, className);
        } finally {
            logEnd(className);
        }
    }
    
    /**
     * Load competenze territorio response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idAdempimento   the id adempimento
     * @param idIstanza       the id istanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @Override
    public Response loadCompetenzeTerritorioByIdCompResp(String xRequestAuth, String xRequestId, Long idCompetenzaTerritorioResponsabile, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idCompetenzaTerritorioResponsabile);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
            logEnd(className);
            return Response.serverError().entity(e.getError()).status(Integer.parseInt(e.getError().getStatus())).build();
        }
        CompetenzaTerritorioResponsabileDTO competenzaTerritorioResp = null;

        competenzaTerritorioResp = competenzeTerritorioService.loadCompetenzeTerritorioByIdCompResp(idCompetenzaTerritorioResponsabile, attoreScriva);
        return Response.ok(competenzaTerritorioResp).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).build();
    }


}