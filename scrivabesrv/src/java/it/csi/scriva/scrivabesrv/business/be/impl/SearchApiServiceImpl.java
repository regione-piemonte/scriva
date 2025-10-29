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

import it.csi.scriva.scrivabesrv.business.be.SearchApi;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.SearchDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.StatoIstanzaDAO;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.FunzionarioAutorizzatoDTO;
import it.csi.scriva.scrivabesrv.dto.FunzionarioProfiloExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaSearchResultDTO;
import it.csi.scriva.scrivabesrv.dto.PaginationHeaderDTO;
import it.csi.scriva.scrivabesrv.dto.ProfiloOggettoAppExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.SearchCountIstanzeDTO;
import it.csi.scriva.scrivabesrv.dto.SearchDTO;
import it.csi.scriva.scrivabesrv.dto.SearchHeaderDTO;
import it.csi.scriva.scrivabesrv.dto.StatoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoOggettoAppExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.AzioniBaseEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.ProfiloAppEnum;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.manager.ActionRoleManager;
import it.csi.scriva.scrivabesrv.util.manager.FunzionarioManager;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Search api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SearchApiServiceImpl extends BaseApiServiceImpl implements SearchApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Search dao.
     */
    @Autowired
    SearchDAO searchDAO;
    
    @Autowired
    IstanzaDAO istanzaDao;

    @Autowired
    private StatoIstanzaDAO statoIstanzaDAO;

    @Autowired
    private ActionRoleManager actionRoleManager;

    @Autowired
    private FunzionarioManager funzionarioManager;

    /**
     * @param searchCriteria  SearchDTO
     * @param offset          offset
     * @param limit           limit
     * @param sort            sort
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response searchIstanze(SearchDTO searchCriteria, Integer offset, Integer limit, String sort,
                                  Boolean oggApp,
                                  String compare,
                                  SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "searchCriteria :\n" + searchCriteria + "\n offset : " + offset + "\n limit : " + limit);
        SearchCountIstanzeDTO searchCount = new SearchCountIstanzeDTO();
        PaginationHeaderDTO paginationHeader = new PaginationHeaderDTO();
        paginationHeader.setPage(offset);
        paginationHeader.setPageSize(limit);

        try {
            attoreScriva = getAttoreScriva(httpHeaders);
            /*
            Response response = verifyAccreditamento(attoreScriva);
            if (response != null) {
                return response;
            }
            */
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }

        ProfiloAppEnum profiloApp = setAttoreSearch(attoreScriva, searchCriteria);

        long startTime;
        long endTime;
        long timeElapsed;
        long totalExecutionTime = 0;
        List<IstanzaSearchResultDTO> istanze;
        List<IstanzaSearchResultDTO> istanzeWithoutLimit;

        /*
        startTime = System.currentTimeMillis();
        istanze = searchDAO.searchIstanzeOLD(searchCriteria, String.valueOf(offset), String.valueOf(limit), sort, profiloApp);
        endTime = System.currentTimeMillis();
        timeElapsed = endTime - startTime;
        totalExecutionTime += timeElapsed;
        logDebug(className,"Query with search criteria OLD - Execution time in milliseconds OLD: " + timeElapsed);
        */

        startTime = System.currentTimeMillis();
        istanze = searchDAO.searchIstanze(searchCriteria, String.valueOf(offset), String.valueOf(limit), sort, profiloApp);
        endTime = System.currentTimeMillis();
        timeElapsed = endTime - startTime;
        totalExecutionTime += timeElapsed;
        logDebug(className, "Query with search criteria NEW - Execution time in milliseconds NEW: " + timeElapsed);


        if (istanze == null) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        }

        /*
        startTime = System.currentTimeMillis();
        istanzeWithoutLimit = searchDAO.searchIstanzeOLD(searchCriteria, null, null, null, profiloApp);
        endTime = System.currentTimeMillis();
        timeElapsed = endTime - startTime;
        totalExecutionTime += timeElapsed;
        logDebug(className,"Query without search criteria OLD - Execution time in milliseconds: " + timeElapsed);
        */

        startTime = System.currentTimeMillis();
        istanzeWithoutLimit = searchDAO.searchIstanze(searchCriteria, null, null, null, profiloApp);
        endTime = System.currentTimeMillis();
        timeElapsed = endTime - startTime;
        totalExecutionTime += timeElapsed;
        logDebug(className, "Query without search criteria NEW - Execution time in milliseconds: " + timeElapsed);

        paginationHeader.setTotalElements(istanzeWithoutLimit.size());
        paginationHeader.setTotalPages((istanzeWithoutLimit.size() / limit) + ((istanzeWithoutLimit.size() % limit) == 0 ? 0 : 1));

        if (StringUtils.isNotBlank(searchCriteria.getStatoIstanza())) {
            searchCriteria.setStatoIstanza(null);
            startTime = System.currentTimeMillis();
            istanzeWithoutLimit = searchDAO.searchIstanze(searchCriteria, null, null, null);
            endTime = System.currentTimeMillis();
            timeElapsed = endTime - startTime;
            totalExecutionTime += timeElapsed;
            logDebug(className, "Query without search criteria STATO NEW - Execution time in milliseconds: " + timeElapsed);
        }
        searchCount.setTutte(istanzeWithoutLimit.size());

        List<StatoIstanzaDTO> statiIStanza = statoIstanzaDAO.loadStatiIstanzaByComponenteApp(attoreScriva.getComponente());
        List<SearchHeaderDTO> searchHeaderList = new ArrayList<>();

        searchHeaderList.add(getSearchHeader("TUTTE", "Tutte", istanzeWithoutLimit.size(), 0L));
        for (StatoIstanzaDTO si : statiIStanza) {
            List<IstanzaSearchResultDTO> iwl = istanzeWithoutLimit.stream().filter(istanza -> si.getCodiceStatoIstanza().equalsIgnoreCase(istanza.getCodiceStatoIstanza())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(iwl)) {
                searchHeaderList.add(getSearchHeader(si .getCodiceStatoIstanza(), si.getDescrizioneStatoIstanza(), iwl.size(), si.getIdStatoIstanza()));
            }
        }

        if (Boolean.TRUE.equals(oggApp)) {
            /*
            startTime = System.currentTimeMillis();
            setOggettoAppIstanza(istanze, attoreScriva);
            endTime = System.currentTimeMillis();
            timeElapsed = endTime - startTime;
            totalExecutionTime += timeElapsed;
            logDebug(className,"Query setOggettoAppIstanza OLD - Execution time in milliseconds: " + timeElapsed);
            */

            startTime = System.currentTimeMillis();
            setOggettoAppIstanzaNew(istanze, attoreScriva);
            endTime = System.currentTimeMillis();
            timeElapsed = endTime - startTime;
            totalExecutionTime += timeElapsed;
            logDebug(className, "Query setOggettoAppIstanza NEW - Execution time in milliseconds: " + timeElapsed);

/*
            startTime = System.currentTimeMillis();
            setProfiloOggettoAppIstanza(istanze, attoreScriva);
            endTime = System.currentTimeMillis();
            timeElapsed = endTime - startTime;
            totalExecutionTime += timeElapsed;
            logDebug(className,"Query setProfiloOggettoAppIstanza OLD - Execution time in milliseconds: " + timeElapsed);
*/

            startTime = System.currentTimeMillis();
            setProfiloOggettoAppIstanzaNew(istanze, attoreScriva); //Associazione ProfiloOggettoApp alle relative istanze
            endTime = System.currentTimeMillis();
            timeElapsed = endTime - startTime;
            totalExecutionTime += timeElapsed;
            logDebug(className, "Query setProfiloOggettoAppIstanza NEW - Execution time in milliseconds: " + timeElapsed);

        }

        if (StringUtils.isNotBlank(compare)) {
            List<IstanzaSearchResultDTO> istanzeWithoutLimitOLD = searchDAO.searchIstanzeOLD(searchCriteria, null, null, null, profiloApp);
            searchHeaderList.add(getSearchHeader("TUTTE-OLD", "Tutte Old", istanzeWithoutLimitOLD.size(), 99999L));
        }

        logDebug(className, "Query searchIstanze - Total execution time in milliseconds: " + totalExecutionTime);
        logEnd(className);
        return Response.ok(istanze).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header("PaginationInfo", paginationHeader.getMap()).header("CountIstanze", new JSONArray(searchHeaderList)).build();
    }

    /**
     * Search istanze old response.
     *
     * @param searchCriteria  the search criteria
     * @param offset          the offset
     * @param limit           the limit
     * @param sort            the sort
     * @param oggApp          the ogg app
     * @param compare         the compare
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    public Response searchIstanzeOLD(SearchDTO searchCriteria, Integer offset, Integer limit, String sort,
                                     Boolean oggApp,
                                     String compare,
                                     SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "searchCriteria :\n" + searchCriteria + "\n offset : " + offset + "\n limit : " + limit);
        SearchCountIstanzeDTO searchCount = new SearchCountIstanzeDTO();
        PaginationHeaderDTO paginationHeader = new PaginationHeaderDTO();
        paginationHeader.setPage(offset);
        paginationHeader.setPageSize(limit);

        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }

        ProfiloAppEnum profiloApp = setAttoreSearch(attoreScriva, searchCriteria);

        long startTime;
        long endTime;
        long timeElapsed;
        long totalExecutionTime = 0;
        List<IstanzaSearchResultDTO> istanze;
        List<IstanzaSearchResultDTO> istanzeWithoutLimit;


        startTime = System.currentTimeMillis();
        istanze = searchDAO.searchIstanzeOLD(searchCriteria, String.valueOf(offset), String.valueOf(limit), sort, profiloApp);
        endTime = System.currentTimeMillis();
        timeElapsed = endTime - startTime;
        totalExecutionTime += timeElapsed;
        logDebug(className, "Query with search criteria OLD - Execution time in milliseconds OLD: " + timeElapsed);

        if (istanze == null) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        }

        startTime = System.currentTimeMillis();
        istanzeWithoutLimit = searchDAO.searchIstanzeOLD(searchCriteria, null, null, null, profiloApp);
        endTime = System.currentTimeMillis();
        timeElapsed = endTime - startTime;
        totalExecutionTime += timeElapsed;
        logDebug(className, "Query without search criteria OLD - Execution time in milliseconds: " + timeElapsed);

        paginationHeader.setTotalElements(istanzeWithoutLimit.size());
        paginationHeader.setTotalPages((istanzeWithoutLimit.size() / limit) + ((istanzeWithoutLimit.size() % limit) == 0 ? 0 : 1));

        if (StringUtils.isNotBlank(searchCriteria.getStatoIstanza())) {
            searchCriteria.setStatoIstanza(null);
            startTime = System.currentTimeMillis();
            istanzeWithoutLimit = searchDAO.searchIstanzeOLD(searchCriteria, null, null, null);
            endTime = System.currentTimeMillis();
            timeElapsed = endTime - startTime;
            totalExecutionTime += timeElapsed;
            logDebug(className, "Query without search criteria STATO NEW - Execution time in milliseconds: " + timeElapsed);
        }
        searchCount.setTutte(istanzeWithoutLimit.size());

        List<StatoIstanzaDTO> statiIStanza = statoIstanzaDAO.loadStatiIstanzaByComponenteApp(attoreScriva.getComponente());
        List<SearchHeaderDTO> searchHeaderList = new ArrayList<>();

        searchHeaderList.add(getSearchHeader("TUTTE", "Tutte", istanzeWithoutLimit.size(), 0L));
        for (StatoIstanzaDTO si : statiIStanza) {
            List<IstanzaSearchResultDTO> iwl = istanzeWithoutLimit.stream().filter(istanza -> si.getCodiceStatoIstanza().equalsIgnoreCase(istanza.getCodiceStatoIstanza())).collect(Collectors.toList());
            searchHeaderList.add(getSearchHeader(si.getCodiceStatoIstanza(), si.getDescrizioneStatoIstanza(), iwl.size(), si.getIdStatoIstanza()));
        }

        if (Boolean.TRUE.equals(oggApp)) {
            startTime = System.currentTimeMillis();
            setOggettoAppIstanza(istanze, attoreScriva);
            endTime = System.currentTimeMillis();
            timeElapsed = endTime - startTime;
            totalExecutionTime += timeElapsed;
            logDebug(className, "Query setOggettoAppIstanza OLD - Execution time in milliseconds: " + timeElapsed);

            startTime = System.currentTimeMillis();
            setProfiloOggettoAppIstanza(istanze, attoreScriva);
            endTime = System.currentTimeMillis();
            timeElapsed = endTime - startTime;
            totalExecutionTime += timeElapsed;
            logDebug(className, "Query setProfiloOggettoAppIstanza OLD - Execution time in milliseconds: " + timeElapsed);
        }

        if (StringUtils.isNotBlank(compare)) {
            List<IstanzaSearchResultDTO> istanzeWithoutLimitOLD = searchDAO.searchIstanzeOLD(searchCriteria, null, null, null, profiloApp);
            searchHeaderList.add(getSearchHeader("TUTTE-OLD", "Tutte Old", istanzeWithoutLimitOLD.size(), 99999L));
        }

        logDebug(className, "Query searchIstanze - Total execution time in milliseconds: " + totalExecutionTime);
        logEnd(className);
        return Response.ok(istanze).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header("PaginationInfo", paginationHeader.getMap()).header("CountIstanze", new JSONArray(searchHeaderList)).build();
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
     * Sets attore search.
     *
     * @param attoreScriva   the attore scriva
     * @param searchCriteria the search criteria
     * @return the attore search
     */
    private ProfiloAppEnum setAttoreSearch(AttoreScriva attoreScriva, SearchDTO searchCriteria) {
        logBegin(className);
        ProfiloAppEnum profiloApp = null;
        if (attoreScriva != null) {
            searchCriteria.setComponenteApp(attoreScriva.getComponente());
            if (ComponenteAppEnum.FO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
                searchCriteria.setCfCompilante(attoreScriva.getCodiceFiscale()); // a cosa serve???? 
            }
            if (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
                searchCriteria.setCfFunzionario(attoreScriva.getCodiceFiscale()); // a cosa serve???? 
                try {
                    FunzionarioAutorizzatoDTO funzionarioAutorizzato = funzionarioManager.getFunzionarioAutorizzatoByCF(attoreScriva.getCodiceFiscale());
                    if (funzionarioAutorizzato != null && !funzionarioAutorizzato.getFunzionarioProfilo().isEmpty()) {
                        List<FunzionarioProfiloExtendedDTO> funzionarioProfiloAdmin = funzionarioAutorizzato.getFunzionarioProfilo().stream()
                                .filter(funzProfilo -> funzProfilo.getProfiloApp().getCodProfiloApp().equalsIgnoreCase(ProfiloAppEnum.ADMIN.name()))
                                .collect(Collectors.toList());
                        if (!funzionarioProfiloAdmin.isEmpty()) {
                            profiloApp = ProfiloAppEnum.ADMIN;
                        } else {
                            List<FunzionarioProfiloExtendedDTO> funzionarioProfiloReadAll = funzionarioAutorizzato.getFunzionarioProfilo().stream()
                                    .filter(funzProfilo -> funzProfilo.getProfiloApp().getCodProfiloApp().equalsIgnoreCase(ProfiloAppEnum.READ_ALL.name()))
                                    .collect(Collectors.toList());
                            profiloApp = !funzionarioProfiloReadAll.isEmpty() ? ProfiloAppEnum.READ_ALL : null;
                        }

                    }
                } catch (Exception e) {
                    logError(className, e);
                }
            }
            attoreScriva.setProfiloAppIstanza(profiloApp != null ? profiloApp.name() : null);
        }
        logEnd(className);
        return profiloApp; // se FO sarà null
    }

    /**
     * Sets oggetto app istanza.
     *
     * @param istanzaSearchResultList the istanza search result list
     * @param attoreScriva            the attore scriva
     */
    private void setOggettoAppIstanza(List<IstanzaSearchResultDTO> istanzaSearchResultList, AttoreScriva attoreScriva) {
        logBegin(className);
        try {
            if (CollectionUtils.isNotEmpty(istanzaSearchResultList)) {
                if (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
                    for (IstanzaSearchResultDTO istanza : istanzaSearchResultList) {
                        if (ProfiloAppEnum.READ_ALL.name().equalsIgnoreCase(attoreScriva.getProfiloAppIstanza())) {
                            istanza.setAttoreGestioneIstanza(AzioniBaseEnum.READ_ONLY.getDescrizione());
                            istanza.setTipiAdempimentoOggettoApp(null);
                        } else {
                            List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList = actionRoleManager.getTipoAdempimentoOggettoApp(istanza.getIdIstanza(), null, attoreScriva, Boolean.TRUE);
                            istanza.setTipiAdempimentoOggettoApp(tipoAdempimentoOggettoAppList);
                            // Se il funzionario ha diritti di amministrazione può effettuare modifiche in qualsiasi istante
                            if (StringUtils.isNotBlank(attoreScriva.getProfiloAppIstanza()) && attoreScriva.getProfiloAppIstanza().equalsIgnoreCase(ProfiloAppEnum.ADMIN.name())) {
                                istanza.setAttoreGestioneIstanza(AzioniBaseEnum.WRITE.name());
                            } else {
                                AzioniBaseEnum azioniBaseEnum = tipoAdempimentoOggettoAppList != null && !tipoAdempimentoOggettoAppList.isEmpty() ? actionRoleManager.getProfiloApp(tipoAdempimentoOggettoAppList) : null;
                                istanza.setAttoreGestioneIstanza(azioniBaseEnum != null ? azioniBaseEnum.getDescrizione() : null);
                            }
                        }
                    }
                } else {
                    for (IstanzaSearchResultDTO istanza : istanzaSearchResultList) {
                        //List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList = actionRoleManager.getTipoAdempimentoOggettoApp(istanza.getIdIstanza(), attoreScriva.getCodiceFiscale(), attoreScriva.getComponente(), "AZ_AVANZATA");
                        List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList = actionRoleManager.getTipoAdempimentoOggettoApp(istanza.getIdIstanza(), null, attoreScriva, Boolean.TRUE);

                        AzioniBaseEnum azioniBaseEnum = actionRoleManager.getProfiloApp(tipoAdempimentoOggettoAppList);
                        istanza.setAttoreGestioneIstanza(azioniBaseEnum != null ? azioniBaseEnum.getDescrizione() : AzioniBaseEnum.READ_ONLY.getDescrizione());
                        istanza.setTipiAdempimentoOggettoApp(tipoAdempimentoOggettoAppList);
                    }
                }
            }
        } finally {
            logEnd(className);
        }
    }

    /**
     * Sets oggetto app istanza new.
     *
     * @param istanzaSearchResultList the istanza search result list
     * @param attoreScriva            the attore scriva
     */
    private void setOggettoAppIstanzaNew(List<IstanzaSearchResultDTO> istanzaSearchResultList, AttoreScriva attoreScriva) {
        logBegin(className);
        try {
            if (CollectionUtils.isNotEmpty(istanzaSearchResultList)) {
                List<Long> idIstanzaList = istanzaSearchResultList.stream()
                        .map(IstanzaSearchResultDTO::getIdIstanza)
                        .collect(Collectors.toList());
                if (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
                    setOggettoAppBO(istanzaSearchResultList, attoreScriva, idIstanzaList);
                } else {
                    setOggettoAppFO(istanzaSearchResultList, attoreScriva, idIstanzaList);
                }
            }
        } finally {
            logEnd(className);

        }
    }


    /**
     * Sets oggetto app bo.
     * vecchia versione
     *
     * @param istanzaSearchResultList la lista delle istanze (IstanzaSearchResultDTO)
     * @param attoreScriva            the attore scriva
     * @param idIstanzaList           la lista degli id istanza
     */
    /* 
    private void setOggettoAppBO(List<IstanzaSearchResultDTO> istanzaSearchResultList, AttoreScriva attoreScriva, List<Long> idIstanzaList) {
        logBegin(className);
        // verifica che la lista delle istanze e degli id non siano vuote
        if (CollectionUtils.isNotEmpty(istanzaSearchResultList) && CollectionUtils.isNotEmpty(idIstanzaList)) 
        {
            // se il funzionario ha il profilo READ_ALL assegna glielo assegna per tutte le istanze e si ferma
            if (ProfiloAppEnum.READ_ALL.name().equalsIgnoreCase(attoreScriva.getProfiloAppIstanza())) 
            {
                for (IstanzaSearchResultDTO istanza : istanzaSearchResultList) 
                {
                    istanza.setAttoreGestioneIstanza(AzioniBaseEnum.READ_ONLY.getDescrizione()); // assegna il profilo READ_ONLY come Gestione attore
                    istanza.setTipiAdempimentoOggettoApp(null); // assegna gli oggetti applicativi null (non vengono calcolati perche il profilo è READ_ONLY)?!?!?
                }
            } 
            else 
            {
                List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList = actionRoleManager.getTipoAdempimentoOggettoApp(idIstanzaList, null, attoreScriva, Boolean.TRUE);
                // mappa le istanze ricavate con il relativo stato di editabilità (scriva_r_stato_istanza_adempi.ind_modificabile)
                Map<Long, String> istanzeIndModificabile = istanzaDao.loadIstanzaIndModificabile(idIstanzaList);
                
                for (IstanzaSearchResultDTO istanza : istanzaSearchResultList) 
                {
                    List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppListFiltered = tipoAdempimentoOggettoAppList.stream()
                            .filter(t -> t.getIdIstanza().equals(istanza.getIdIstanza()))
                            .collect(Collectors.toList());
                    istanza.setTipiAdempimentoOggettoApp(tipoAdempimentoOggettoAppListFiltered);
                    // Se il funzionario ha diritti di amministrazione può effettuare modifiche in qualsiasi istante
                    if (StringUtils.isNotBlank(attoreScriva.getProfiloAppIstanza()) && attoreScriva.getProfiloAppIstanza().equalsIgnoreCase(ProfiloAppEnum.ADMIN.name())) 
                    {
                        istanza.setAttoreGestioneIstanza(AzioniBaseEnum.WRITE.name());
                    } 
                    else 
                    {
                        String statoEditabilita = istanzeIndModificabile.get(istanza.getIdIstanza());
                    	AzioniBaseEnum azioniBaseEnum = CollectionUtils.isNotEmpty(tipoAdempimentoOggettoAppListFiltered) ? actionRoleManager.getProfiloApp(tipoAdempimentoOggettoAppListFiltered) : null;
                        //istanza.setAttoreGestioneIstanza(azioniBaseEnum != null ? azioniBaseEnum.getDescrizione() : null);
                        
                        //anto
                        //istanza.setAttoreGestioneIstanza(CollectionUtils.isNotEmpty(tipoAdempimentoOggettoAppListFiltered) ? actionRoleManager.getProfiloAppNew(tipoAdempimentoOggettoAppListFiltered) : null);
                    	
                    	if(azioniBaseEnum != null)
                    	{	
                    		if(statoEditabilita != null && !statoEditabilita.equals("NA")) // se ind_modificabile è diverso da NA (non editabile) prende il valore calcolato (se stato editabilità è null c'è un errore di configurazione e nel dubbio inibisco la scrittura
                    			istanza.setAttoreGestioneIstanza(azioniBaseEnum.getDescrizione()); 
                    		else // altrimenti prende il WRITE_LOCK per bloccarne la modifica
                    			istanza.setAttoreGestioneIstanza(AzioniBaseEnum.findByDescr("WRITE_LOCK").getDescrizione());
                    	}
                		else 
                		{
                			istanza.setAttoreGestioneIstanza(null);
                		}
                    	
                    }
                }
            }

        }
        logEnd(className);
    }
    */

    /**
     * Sets oggetto app bo.
     * come modificata in seguito alla jira 1571
     *
     * @param istanzaSearchResultList la lista delle istanze (IstanzaSearchResultDTO)
     * @param attoreScriva            the attore scriva
     * @param idIstanzaList           la lista degli id istanza
     */
    private void setOggettoAppBO(List<IstanzaSearchResultDTO> istanzaSearchResultList, AttoreScriva attoreScriva, List<Long> idIstanzaList) {
        logBegin(className);
        // verifica che la lista delle istanze e degli id non siano vuote
        if (CollectionUtils.isNotEmpty(istanzaSearchResultList) && CollectionUtils.isNotEmpty(idIstanzaList)) 
        {
            // se il funzionario ha il profilo READ_ALL assegna glielo assegna per tutte le istanze e si ferma
            if (ProfiloAppEnum.READ_ALL.name().equalsIgnoreCase(attoreScriva.getProfiloAppIstanza())) 
            {
                for (IstanzaSearchResultDTO istanza : istanzaSearchResultList) 
                {
                    istanza.setAttoreGestioneIstanza(AzioniBaseEnum.READ_ONLY.getDescrizione()); // assegna il profilo READ_ONLY come Gestione attore
                    istanza.setTipiAdempimentoOggettoApp(null); // assegna gli oggetti applicativi null (non vengono calcolati perche il profilo è READ_ONLY)?!?!?
                }
            } 
            else 
            {
                // calcola gli oggetti applicativi per tutte le istanze
                List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList = actionRoleManager.getTipoAdempimentoOggettoApp(idIstanzaList, null, attoreScriva, Boolean.TRUE);

                // NUOVA QUERY PER ESTRARRE LA GESTIONE ATTORE DI TUTTE LE ISTANZE: restituisce coppie idIstanza, gestioneAttore SCRIVA 1378-1571
                // TODO eliminare il parametro codComponenteApp e renderlo statico visto che forse la query si userà solo per FO
                List<Pair<Long, String>> idIstanzaGestioneAttores = istanzaDao.loadIstanzaGestioneAttoresBO(idIstanzaList,attoreScriva.getCodiceFiscale(),"BO");

                
                //cicla tutte le istanze
                for (IstanzaSearchResultDTO istanza : istanzaSearchResultList) 
                {
                    // estrae gli oggetti applicativi relativi all'istanza corrente
                    List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppListFiltered = tipoAdempimentoOggettoAppList.stream()
                            .filter(t -> t.getIdIstanza().equals(istanza.getIdIstanza()))
                            .collect(Collectors.toList());

                    // associa gli aggetti applicativi corrispondenti all'istanza corrente        
                    istanza.setTipiAdempimentoOggettoApp(tipoAdempimentoOggettoAppListFiltered);
                    // Se il funzionario ha diritti di amministrazione può effettuare modifiche in qualsiasi istante
                    if (StringUtils.isNotBlank(attoreScriva.getProfiloAppIstanza()) && attoreScriva.getProfiloAppIstanza().equalsIgnoreCase(ProfiloAppEnum.ADMIN.name())) 
                    {
                        istanza.setAttoreGestioneIstanza(AzioniBaseEnum.WRITE.name()); // se è admin cli associa il profilo WRITE a tutte le istanze 
                    } 
                    else 
                    {
                        // NUOVO  FILTRO PER GESTIONE ATTORE SCRIVA 1378-1571
                        String gestioneAttore = idIstanzaGestioneAttores.stream()
                        .filter(pair -> pair.getLeft().equals(istanza.getIdIstanza())) // Filtra per Id_istanza
                        .map(Pair::getRight) // Estrai GestioneAttore (String)
                        .findFirst() // Trova il primo elemento corrispondente
                        .orElse(null); // Restituisci null se non esiste una corrispondenza

                        // associa la gestione attore all'istanza se ci fossero problemi sul riscontro della gestione attore recuperata da DB restituisce READ_ONLY
                        // utile quando per esempio il funzionario attiva la visualizzazione delle istanze di tutti
                        //rimosso enum
                        istanza.setAttoreGestioneIstanza(gestioneAttore != null ? gestioneAttore : AzioniBaseEnum.READ_ONLY.getDescrizione());

                        istanza.setTipiAdempimentoOggettoApp(tipoAdempimentoOggettoAppListFiltered);
                                            
                    }
                }
            }

        }
        logEnd(className);
    }

    /**
     * Sets oggetto app fo.
     *
     * @param istanzaSearchResultList la lista delle istanze (IstanzaSearchResultDTO)
     * @param attoreScriva            the attore scriva
     * @param idIstanzaList           la lista degli id istanza
     */
    private void setOggettoAppFO(List<IstanzaSearchResultDTO> istanzaSearchResultList, AttoreScriva attoreScriva, List<Long> idIstanzaList) {
        logBegin(className);
        // verifica che la lista delle istanze e degli id non siano vuote
        if (CollectionUtils.isNotEmpty(istanzaSearchResultList) && CollectionUtils.isNotEmpty(idIstanzaList)) {
           // recupera gli oggetti applicativi di tutte le istanze
            List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList = actionRoleManager.getTipoAdempimentoOggettoApp(idIstanzaList, null, attoreScriva, Boolean.TRUE);

            // NUOVA QUERY PER ESTRARRE LA GESTIONE ATTORE DI TUTTE LE ISTANZE: restituisce coppie idIstanza, gestioneAttore SCRIVA 1378-1571
            // TODO eliminare il parametro codComponenteApp e renderlo statico visto che forse la query si userà solo per FO
            List<Pair<Long, String>> idIstanzaGestioneAttores = istanzaDao.loadIstanzaGestioneAttoresFO(idIstanzaList,attoreScriva.getCodiceFiscale(),"FO");


            // per ogni istanza recupera gli oggetti applicativi 
            for (IstanzaSearchResultDTO istanza : istanzaSearchResultList) {
                
                // Filtra gli elementi di tipoAdempimentoOggettoAppList mantenendo solo quelli il cui idIstanza corrisponde a quello dell'oggetto istanza.
                List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppListFiltered = tipoAdempimentoOggettoAppList.stream()
                        .filter(t -> t.getIdIstanza().equals(istanza.getIdIstanza()))
                        .collect(Collectors.toList());
                
                // ricava la gestione attore in base a quella associata all'oggetto applicativo con priorità più bassa
                // AzioniBaseEnum azioniBaseEnum = actionRoleManager.getProfiloApp(tipoAdempimentoOggettoAppListFiltered);
                
                // NUOVO  FILTRO PER GESTIONE ATTORE SCRIVA 1378-1571
               String gestioneAttore = idIstanzaGestioneAttores.stream()
                        .filter(pair -> pair.getLeft().equals(istanza.getIdIstanza())) // Filtra per Id_istanza
                        .map(Pair::getRight) // Estrai GestioneAttore (String)
                        .findFirst() // Trova il primo elemento corrispondente
                        .orElse(null); // Restituisci null se non esiste una corrispondenza

                // riconduce la gestione attore al valore previsto
                // SCRIVA 1378-1571 rimosso enum
                // AzioniBaseEnum azioniBaseEnum = AzioniBaseEnum.findByDescr(gestioneAttore);
               
                // associa la gestione attore all'istanza se ci fossero problemi sul riscontro della gestione attore recuperata da DB restituisce READ_ONLY
                istanza.setAttoreGestioneIstanza(gestioneAttore != null ? gestioneAttore : AzioniBaseEnum.READ_ONLY.getDescrizione());
                
                istanza.setTipiAdempimentoOggettoApp(tipoAdempimentoOggettoAppListFiltered);
            }
        }
        logEnd(className);
    }

    /**
     * Sets profilo oggetto app istanza.
     *
     * @param istanze      the istanze
     * @param attoreScriva the attore scriva
     */
    public void setProfiloOggettoAppIstanza(List<IstanzaSearchResultDTO> istanze, AttoreScriva attoreScriva) {
        logBegin(className);
        if (istanze != null && attoreScriva != null) {
            for (IstanzaSearchResultDTO istanza : istanze) {
                try {
                    istanza.setProfiloOggettoAppList(getProfiloAppExtended(istanza.getIdIstanza(), attoreScriva));
                } catch (Exception e) {
                    logError(className, e);
                }
            }
        }
        logEnd(className);
    }

    /**
     * Sets profilo oggetto app istanza new.
     *
     * @param istanzaSearchResultList the istanza search result list
     * @param attoreScriva            the attore scriva
     */
    public void setProfiloOggettoAppIstanzaNew(List<IstanzaSearchResultDTO> istanzaSearchResultList, AttoreScriva attoreScriva) {
        logBegin(className);
        if (CollectionUtils.isNotEmpty(istanzaSearchResultList) && attoreScriva != null) {
            // Recupero id delle istanze ottenute dalla ricerca
            List<Long> idIstanzaList = istanzaSearchResultList.stream()
                    .map(IstanzaSearchResultDTO::getIdIstanza)
                    .collect(Collectors.toList());

            // Recupero ProfiloOggettoApp per le istanze ottenute e per l'attore in linea
            List<ProfiloOggettoAppExtendedDTO> profiloOggettoAppList = getProfiloAppExtendedNew(idIstanzaList, attoreScriva);

            List<ProfiloOggettoAppExtendedDTO> profiloOggettoAppNoIstanza = profiloOggettoAppList.stream()
                    .filter(p -> p.getIdIstanza()==null)
                    .collect(Collectors.toList());

            // Associazione ProfiloOggettoApp alle relative istanze
            for (IstanzaSearchResultDTO istanza : istanzaSearchResultList) {
                try {
                    /*
                    List<ProfiloOggettoAppExtendedDTO> profiloOggettoAppFilteredList = ComponenteAppEnum.FO.name().equalsIgnoreCase(attoreScriva.getComponente()) ?
                            profiloOggettoAppList.stream()
                                    .filter(p -> p.getIdIstanza()!=null && istanza.getIdIstanza()!=null && p.getIdIstanza().equals(istanza.getIdIstanza()))
                                    .collect(Collectors.toList()) :
                            profiloOggettoAppList;
                    */
                    List<ProfiloOggettoAppExtendedDTO> profiloOggettoAppFilteredList = profiloOggettoAppList.stream()
                                    .filter(p -> p.getIdIstanza()!=null && istanza.getIdIstanza()!=null && p.getIdIstanza().equals(istanza.getIdIstanza()))
                                    .collect(Collectors.toList());
                    List<ProfiloOggettoAppExtendedDTO> profiloOggettoAppCompleteList = Stream.concat(profiloOggettoAppNoIstanza.stream(), profiloOggettoAppFilteredList.stream())
                            .distinct()
                            .collect(Collectors.toList());
                    istanza.setProfiloOggettoAppList(profiloOggettoAppCompleteList);
                } catch (Exception e) {
                    logError(className, e);
                }
            }
        }
        logEnd(className);
    }

}