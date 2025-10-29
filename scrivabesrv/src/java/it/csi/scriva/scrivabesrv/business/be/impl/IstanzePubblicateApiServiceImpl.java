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

import it.csi.scriva.scrivabesrv.business.be.IstanzePubblicateApi;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.SearchDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.StatoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoAdempimentoOggettoAppDAO;
import it.csi.scriva.scrivabesrv.business.be.service.IstanzePubblicateService;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.FunzionarioAutorizzatoDTO;
import it.csi.scriva.scrivabesrv.dto.FunzionarioProfiloExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaSearchResultDTO;
import it.csi.scriva.scrivabesrv.dto.PaginationHeaderDTO;
import it.csi.scriva.scrivabesrv.dto.PubIstanzaContatoreDTO;
import it.csi.scriva.scrivabesrv.dto.PubIstanzaContatoreExtDTO;
import it.csi.scriva.scrivabesrv.dto.PubIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.PubIstanzaSintesiDTO;
import it.csi.scriva.scrivabesrv.dto.PubSearchDTO;
import it.csi.scriva.scrivabesrv.dto.SearchCountIstanzeDTO;
import it.csi.scriva.scrivabesrv.dto.SearchDTO;
import it.csi.scriva.scrivabesrv.dto.SearchHeaderDTO;
import it.csi.scriva.scrivabesrv.dto.SintesiDTO;
import it.csi.scriva.scrivabesrv.dto.StatoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoOggettoAppExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.AzioniBaseEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.FasePubblicazioneEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.ProfiloAppEnum;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.manager.ActionRoleManager;
import it.csi.scriva.scrivabesrv.util.manager.FunzionarioManager;
import org.apache.commons.lang3.StringUtils;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Search api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class IstanzePubblicateApiServiceImpl extends BaseApiServiceImpl implements IstanzePubblicateApi {

    public static final String TUTTE = "TUTTE";
    public static final String PAGINATION_INFO = "PaginationInfo";
    private final String className = this.getClass().getSimpleName();

    /**
     * The Search dao.
     */
    @Autowired
    SearchDAO searchDAO;

    @Autowired
    private StatoIstanzaDAO statoIstanzaDAO;

    @Autowired
    private TipoAdempimentoOggettoAppDAO tipoAdempimentoOggettoAppDAO;

    @Autowired
    private ActionRoleManager actionRoleManager;

    @Autowired
    private FunzionarioManager funzionarioManager;

    @Autowired
    private IstanzePubblicateService istanzePubblicateService;

    /**
     * Search istanze response.
     *
     * @param searchCriteria  SearchDTO
     * @param offset          offset
     * @param limit           limit
     * @param sort            sort
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    public Response searchIstanze(SearchDTO searchCriteria, Integer offset, Integer limit, String sort,
                                  SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input searchCriteria :\n" + searchCriteria + "\n offset : " + offset
                + "\n limit : " + limit;
        LOGGER.debug(getClassFunctionBeginInfo(className, methodName));
        LOGGER.debug(getClassFunctionDebugString(className, methodName, inputParam));

        SearchCountIstanzeDTO searchCount = new SearchCountIstanzeDTO();
        PaginationHeaderDTO paginationHeader = new PaginationHeaderDTO();
        paginationHeader.setPage(offset);
        paginationHeader.setPageSize(limit);

        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            LOGGER.error(getClassFunctionErrorInfo(className, methodName, e.getError()));
            return Response.serverError().entity(e.getError()).status(400).build();
        }

        ProfiloAppEnum profiloApp = setAttoreSearch(attoreScriva, searchCriteria);
        List<IstanzaSearchResultDTO> istanze = searchDAO.searchIstanze(searchCriteria, String.valueOf(offset),
                String.valueOf(limit), sort, profiloApp);
        if (istanze == null) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            LOGGER.error(getClassFunctionErrorInfo(className, methodName, inputParam + "\n" + error));
            LOGGER.debug(getClassFunctionEndInfo(className, methodName));
            return Response.serverError().entity(error).status(500).build();
        }

        List<IstanzaSearchResultDTO> istanzeWithoutLimit = searchDAO.searchIstanze(searchCriteria, null, null, null,
                profiloApp);
        paginationHeader.setTotalElements(istanzeWithoutLimit.size());
        paginationHeader.setTotalPages(
                (istanzeWithoutLimit.size() / limit) + ((istanzeWithoutLimit.size() % limit) == 0 ? 0 : 1));

        if (StringUtils.isNotBlank(searchCriteria.getStatoIstanza())) {
            searchCriteria.setStatoIstanza(null);
            istanzeWithoutLimit = searchDAO.searchIstanze(searchCriteria, null, null, null);
        }
        searchCount.setTutte(istanzeWithoutLimit.size());

        List<StatoIstanzaDTO> statiIStanza = statoIstanzaDAO
                .loadStatiIstanzaByComponenteApp(attoreScriva.getComponente());
        List<SearchHeaderDTO> searchHeaderList = new ArrayList<>();

        searchHeaderList.add(getSearchHeader(TUTTE, "Tutte", istanzeWithoutLimit.size(), 0L));
        for (StatoIstanzaDTO si : statiIStanza) {
            List<IstanzaSearchResultDTO> iwl = istanzeWithoutLimit.stream()
                    .filter(istanza -> si.getCodiceStatoIstanza().equalsIgnoreCase(istanza.getCodiceStatoIstanza()))
                    .collect(Collectors.toList());
            searchHeaderList.add(getSearchHeader(si.getCodiceStatoIstanza(), si.getDescrizioneStatoIstanza(),
                    iwl.size(), si.getIdStatoIstanza()));
        }

        setOggettoAppIstanza(istanze, attoreScriva);


//        for (IstanzaSearchResultDTO istanza : istanze) {
//            //List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList = actionRoleManager.getTipoAdempimentoOggettoApp(istanza.getIdIstanza(), attoreScriva.getCodiceFiscale(), attoreScriva.getComponente(), "AZ_AVANZATA");
//            List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList = actionRoleManager.getTipoAdempimentoOggettoApp(istanza.getIdIstanza(), attoreScriva.getCodiceFiscale(), attoreScriva.getComponente(), null);
//
//            AzioniBaseEnum azioniBaseEnum = actionRoleManager.getProfiloApp(tipoAdempimentoOggettoAppList);
//            istanza.setAttoreGestioneIstanza(azioniBaseEnum != null ? azioniBaseEnum.getDescrizione() : AzioniBaseEnum.READ_ONLY.getDescrizione());
//            istanza.setTipiAdempimentoOggettoApp(tipoAdempimentoOggettoAppList);
//        }

        LOGGER.debug(getClassFunctionEndInfo(className, methodName));
        return Response.ok(istanze).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY)
                .header(PAGINATION_INFO, paginationHeader.getMap())
                .header("CountIstanze", new JSONArray(searchHeaderList)).build();
    }

    private SearchHeaderDTO getSearchHeader(String codice, String descrizione, int size, long ordinamento) {
        SearchHeaderDTO searchHeader = new SearchHeaderDTO();
        searchHeader.setCodice(codice);
        searchHeader.setLabel(descrizione + " (" + size + ")");
        searchHeader.setOrdinamento(ordinamento);
        return searchHeader;
    }

    private ProfiloAppEnum setAttoreSearch(AttoreScriva attoreScriva, SearchDTO searchCriteria) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(className, methodName));
        ProfiloAppEnum profiloApp = null;
        if (attoreScriva != null) {
            searchCriteria.setComponenteApp(attoreScriva.getComponente());
            if (ComponenteAppEnum.FO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
                searchCriteria.setCfCompilante(attoreScriva.getCodiceFiscale());
            }
            if (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
                searchCriteria.setCfFunzionario(attoreScriva.getCodiceFiscale());
                try {
                    FunzionarioAutorizzatoDTO funzionarioAutorizzato = funzionarioManager
                            .getFunzionarioAutorizzatoByCF(attoreScriva.getCodiceFiscale());
                    if (funzionarioAutorizzato != null && !funzionarioAutorizzato.getFunzionarioProfilo().isEmpty()) {
                        List<FunzionarioProfiloExtendedDTO> funzionarioProfiloAdmin = funzionarioAutorizzato
                                .getFunzionarioProfilo().stream().filter(funzProfilo -> funzProfilo.getProfiloApp()
                                        .getCodProfiloApp().equalsIgnoreCase(ProfiloAppEnum.ADMIN.name()))
                                .collect(Collectors.toList());
                        if (!funzionarioProfiloAdmin.isEmpty()) {
                            profiloApp = ProfiloAppEnum.ADMIN;
                        } else {
                            List<FunzionarioProfiloExtendedDTO> funzionarioProfiloReadAll = funzionarioAutorizzato
                                    .getFunzionarioProfilo().stream()
                                    .filter(funzProfilo -> funzProfilo.getProfiloApp().getCodProfiloApp()
                                            .equalsIgnoreCase(ProfiloAppEnum.READ_ALL.name()))
                                    .collect(Collectors.toList());
                            profiloApp = !funzionarioProfiloReadAll.isEmpty() ? ProfiloAppEnum.READ_ALL : null;
                        }

                    }
                } catch (Exception e) {
                    LOGGER.error(getClassFunctionErrorInfo(className, methodName, e));
                }
            }
            attoreScriva.setProfiloAppIstanza(profiloApp != null ? profiloApp.name() : null);
        }
        LOGGER.debug(getClassFunctionEndInfo(className, methodName));
        return profiloApp;
    }

    private void setOggettoAppIstanza(List<IstanzaSearchResultDTO> istanzaSearchResultList, AttoreScriva attoreScriva) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(className, methodName));
        if (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
            for (IstanzaSearchResultDTO istanza : istanzaSearchResultList) {
                if (StringUtils.isNotBlank(attoreScriva.getProfiloAppIstanza())
                        && attoreScriva.getProfiloAppIstanza().equalsIgnoreCase(ProfiloAppEnum.READ_ALL.name())) {
                    istanza.setAttoreGestioneIstanza(AzioniBaseEnum.READ_ONLY.getDescrizione());
                    istanza.setTipiAdempimentoOggettoApp(null);
                } else {
                    List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList = actionRoleManager.getTipoAdempimentoOggettoApp(istanza.getIdIstanza(), null, attoreScriva, Boolean.TRUE);
                    istanza.setTipiAdempimentoOggettoApp(tipoAdempimentoOggettoAppList);
                    // Se il funzionario ha diritti di amministrazione può effettuare modifiche in
                    // qualsiasi istante
                    if (StringUtils.isNotBlank(attoreScriva.getProfiloAppIstanza())
                            && attoreScriva.getProfiloAppIstanza().equalsIgnoreCase(ProfiloAppEnum.ADMIN.name())) {
                        istanza.setAttoreGestioneIstanza(AzioniBaseEnum.WRITE.name());
                    } else {
                        AzioniBaseEnum azioniBaseEnum = tipoAdempimentoOggettoAppList != null
                                && !tipoAdempimentoOggettoAppList.isEmpty()
                                ? actionRoleManager.getProfiloApp(tipoAdempimentoOggettoAppList)
                                : null;
                        istanza.setAttoreGestioneIstanza(
                                azioniBaseEnum != null ? azioniBaseEnum.getDescrizione() : null);
                    }
                }
            }
        } else {
            for (IstanzaSearchResultDTO istanza : istanzaSearchResultList) {
                // List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList =
                // actionRoleManager.getTipoAdempimentoOggettoApp(istanza.getIdIstanza(),
                // attoreScriva.getCodiceFiscale(), attoreScriva.getComponente(),
                // "AZ_AVANZATA");
                List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList = actionRoleManager
                        .getTipoAdempimentoOggettoApp(istanza.getIdIstanza(), null, attoreScriva, Boolean.TRUE);

                AzioniBaseEnum azioniBaseEnum = actionRoleManager.getProfiloApp(tipoAdempimentoOggettoAppList);
                istanza.setAttoreGestioneIstanza(azioniBaseEnum != null ? azioniBaseEnum.getDescrizione()
                        : AzioniBaseEnum.READ_ONLY.getDescrizione());
                istanza.setTipiAdempimentoOggettoApp(tipoAdempimentoOggettoAppList);
            }
        }
        LOGGER.debug(getClassFunctionEndInfo(className, methodName));
    }


    /**
     * Search istanze response.
     *
     * @param searchCriteria  SearchDTO
     * @param offset          offset
     * @param limit           limit
     * @param sort            sort
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @Override
    public Response searchSintesiIstanzePubblicate(PubSearchDTO searchCriteria, Integer offset, Integer limit, String sort,
                                                   SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest
    ) {
        logBeginInfo(className, "searchCriteria [" + searchCriteria + "] - offset [" + offset + "] - limit [" + limit + "] - sort [" + sort + "]");

        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
        }
        List<SintesiDTO> sintesiDTO = istanzePubblicateService.searchSintesiIstanzePubblicate(searchCriteria, attoreScriva, offset, limit, sort);

        if (sintesiDTO == null) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        } else if (sintesiDTO.isEmpty()) {
            ErrorDTO error = getErrorManager().getError("404", "", "Elemento non trovato", null, null);
            return getResponseError(className, error);
        }
        logEnd(className);

        List<PubIstanzaSintesiDTO> pubIstanzaSintesiList = valorizzaResponse(sintesiDTO);

        //RICERCA TOTALE PER VALORIZZARE IL COUNTER
        String salvaFase = searchCriteria.getFasePubblicazione();
        searchCriteria.setFasePubblicazione(FasePubblicazioneEnum.TUTTE.getDescrizione());
        List<SintesiDTO> sintesiConta = istanzePubblicateService.searchSintesiIstanzePubblicate(searchCriteria, attoreScriva, null, null, null);

        //PaginationHeaderDTO paginationHeader=getPaginationHeaderDetail(sintesiConta.get(0).getContatoreAll().intValue(), offset, limit);


        //per costruire IL PAGINATOR DEVO UTILIZZARE I CONTATORI TOTALI
        PaginationHeaderDTO paginationHeader = null;
        if (salvaFase.equals(FasePubblicazioneEnum.TUTTE.getDescrizione())) {
            paginationHeader = getPaginationHeaderDetail(sintesiConta.get(0).getContatoreAll().intValue(), offset, limit);
        } else if (salvaFase.equals(FasePubblicazioneEnum.CONCLUSA.getDescrizione())) {
            paginationHeader = getPaginationHeaderDetail(sintesiConta.get(0).getContatoreConclusa().intValue(), offset, limit);
        } else if (salvaFase.equals(FasePubblicazioneEnum.CONSULTA.getDescrizione())) {
            paginationHeader = getPaginationHeaderDetail(sintesiConta.get(0).getContatoreInConsultazione().intValue(), offset, limit);
        } else if (salvaFase.equals(FasePubblicazioneEnum.IN_CORSO.getDescrizione())) {
            paginationHeader = getPaginationHeaderDetail(sintesiConta.get(0).getContatoreInCorso().intValue(), offset, limit);
        }


        List<PubIstanzaContatoreDTO> PubIstanzaContatoreList = valorizzaContatore(sintesiConta);

        PubIstanzaContatoreExtDTO pubIstanzaContatoreExtDTO = new PubIstanzaContatoreExtDTO();
        pubIstanzaContatoreExtDTO.setContatore(PubIstanzaContatoreList);
        return Response.ok(pubIstanzaSintesiList).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header(
                Constants.HEADER_ATTORE_GESTIONE, attoreScriva != null ? attoreScriva.getProfiloAppIstanza() : null).header(
                PAGINATION_INFO, paginationHeader != null ? paginationHeader.getMap() : null).header(
                "Counter", pubIstanzaContatoreExtDTO.getMapTree()).build();
    }


    /**
     * Search dettaglio istanza response.
     *
     * @param searchCriteria  SearchDTO
     * @param offset          offset
     * @param limit           limit
     * @param sort            sort
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @Override
    public Response searchIstanzePubblicate(PubSearchDTO searchCriteria, Integer offset, Integer limit, String sort,
                                            SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest
    ) {
        logBeginInfo(className, "searchCriteria [" + searchCriteria + "] - offset [" + offset + "] - limit [" + limit + "] - sort [" + sort + "]");

        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
            // return getResponseError(className, e.getError());
        }
        //mi salvo la fase pubblicazione, se è null perchè sto chiedendo il dettaglio dell'istanza
        String fasePubblicazioneSearchCriteria = "";
        if (searchCriteria.getFasePubblicazione() == null) {
            fasePubblicazioneSearchCriteria = TUTTE;
        } else {
            fasePubblicazioneSearchCriteria = searchCriteria.getFasePubblicazione();
        }

        List<PubIstanzaDTO> pubListIstanza = istanzePubblicateService.searchIstanzePubblicate(searchCriteria, attoreScriva, offset, limit, sort);

        List<PubIstanzaDTO> pubListIstanzaConta = new ArrayList<>();
        PubSearchDTO searchCriteriaConta = new PubSearchDTO();
        searchCriteriaConta = searchCriteria;
        searchCriteriaConta.setFasePubblicazione(TUTTE);
        pubListIstanzaConta = istanzePubblicateService.searchIstanzePubblicate(searchCriteria, attoreScriva, null, null, sort);

        List<PubIstanzaContatoreDTO> listContatore = new ArrayList<>();
        long somma = 0;
        for (PubIstanzaDTO pubIstanzaDTO : pubListIstanzaConta) {
            PubIstanzaContatoreDTO pubIstanzaContatoreDTO = new PubIstanzaContatoreDTO();
            pubIstanzaContatoreDTO.setFase(pubIstanzaDTO.getFasePubblicazione());
            pubIstanzaContatoreDTO.setContatore(pubIstanzaDTO.getContatorePratiche());
            listContatore.add(pubIstanzaContatoreDTO);
        }

        Set<PubIstanzaContatoreDTO> distinctSla = new HashSet<>(listContatore);
        List<PubIstanzaContatoreDTO> sortedList = new ArrayList<>(distinctSla);
        for (PubIstanzaContatoreDTO pubIstanzaContatoreDTO : sortedList) {
            somma += pubIstanzaContatoreDTO.getContatore();
        }

        PubIstanzaContatoreDTO pubIstanzaContatoreDTO = new PubIstanzaContatoreDTO();
        pubIstanzaContatoreDTO.setFase(TUTTE);
        pubIstanzaContatoreDTO.setContatore(somma);
        sortedList.add(pubIstanzaContatoreDTO);

        PubIstanzaContatoreExtDTO pubIstanzaContatoreExtDTO = new PubIstanzaContatoreExtDTO();
        pubIstanzaContatoreExtDTO.setContatore(sortedList);

        if (pubListIstanza == null) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        } else if (pubListIstanza.isEmpty()) {
            ErrorDTO error = getErrorManager().getError("404", "", "Elemento non trovato", null, null);
            return getResponseError(className, error);
        }
        logEnd(className);

        // gestito ordinamento quando arriva il parametro sort da FE
        List<PubIstanzaDTO> pubListIstanzaSort = sortList(sort, pubListIstanza);

        // SCRIVA-709 : problema dei dati per la paginazione
        //PaginationHeaderDTO paginationHeader = paginationHeader = offset != null & limit != null ? getPaginationHeaderNew(pubListIstanzaConta, offset, limit) : null;
        PaginationHeaderDTO paginationHeader = null;
        for (PubIstanzaContatoreDTO pubIstanzaContatore : sortedList) {
            if (fasePubblicazioneSearchCriteria.equals(pubIstanzaContatore.getFase())) {
                paginationHeader = offset != null && limit != null ?
                        getPaginationHeaderDetail(pubIstanzaContatore.getContatore().intValue(), offset, limit) : null;
            }
        }

        return Response.ok(pubListIstanzaSort).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header(
                Constants.HEADER_ATTORE_GESTIONE, attoreScriva != null ? attoreScriva.getProfiloAppIstanza() : null).header(
                PAGINATION_INFO, paginationHeader != null ? paginationHeader.getMap() : null).header(
                "Counter", pubIstanzaContatoreExtDTO.getMapTree()).build();
    }

    private List<PubIstanzaDTO> sortList(String sort, List<PubIstanzaDTO> pubListIstanza) {

        String sortField;
        char order = '-';
        if (sort != null && !sort.isEmpty()) {
            sortField = sort.charAt(0) == order ? sort.substring(1) : sort;
            Comparator<PubIstanzaDTO> c = new Comparator<PubIstanzaDTO>() {
                public int compare(PubIstanzaDTO o1, PubIstanzaDTO o2) {
                    if (sortField.equals("cod_pratica")) {
                        return o1.getCodPratica().compareToIgnoreCase(o2.getCodPratica());
                    } else if (sortField.equals("den_oggetto")) {
                        return o1.getOggettoIstanza().get(0).getDenOggetto().compareToIgnoreCase(o2.getOggettoIstanza().get(0).getDenOggetto());
                    } else if (sortField.equals("des_adempimento")) {
                        return o1.getAdempimento().getDesAdempimento().compareToIgnoreCase(o2.getAdempimento().getDesAdempimento());
                    } else if (sortField.equals("des_competenza_territorio")) {
                        return o1.getCompetenzaTerritorio().get(0).getDesCompetenzaTerritorio().compareToIgnoreCase(o2.getCompetenzaTerritorio().get(0).getDesCompetenzaTerritorio());
                    } else if (sortField.equals("denom_comune")) {
                        return o1.getOggettoIstanza().get(0).getUbicazioneOoggetto().get(0).getComune().getDenomComune().compareToIgnoreCase(o2.getOggettoIstanza().get(0).getUbicazioneOoggetto().get(0).getComune().getDenomComune());
                    } else if (sortField.equals("label_stato")) {
                        return o1.getStatoIstanza().getLabelStato().compareToIgnoreCase(o2.getStatoIstanza().getLabelStato());
                    } else if (sortField.equals("data_fine_osservazione")) {
                        if (o1.getDataFineOsservazione() == null && o2.getDataFineOsservazione() == null) {
                            return 0;
                        } else if (o1.getDataFineOsservazione() == null) {
                            return 1;
                        } else if (o2.getDataFineOsservazione() == null) {
                            return -1;
                        } else {
                            return (o1.getDataFineOsservazione().compareTo(o2.getDataFineOsservazione()));
                        }
                    } else if (sortField.equals("data_conclusione_procedimento")) {
                        if (o1.getDataConclusioneProcedimento() == null && o2.getDataConclusioneProcedimento() == null) {
                            return 0;
                        } else if (o1.getDataConclusioneProcedimento() == null) {
                            return 1;
                        } else if (o2.getDataConclusioneProcedimento() == null) {
                            return -1;
                        } else {
                            return (o1.getDataConclusioneProcedimento().compareTo(o2.getDataConclusioneProcedimento()));
                        }
                    }
                    return 0;
                }
            };
            if (sort.charAt(0) == order) {
                Collections.sort(pubListIstanza, c.reversed());
            } else {
                Collections.sort(pubListIstanza, c);
            }
        }
        return pubListIstanza;
    }

    /**
     * Load mappe response.
     *
     * @param id              the id
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadMappe(Long id, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, id);
        String jsonGeoJson = istanzePubblicateService.loadMappe(id);

        if (jsonGeoJson == null) {
            ErrorDTO error = getErrorManager().getError("404", "", "Dati geometrici non trovati", null, null);
            LOGGER.debug(getClassFunctionEndInfo(className, "loadMappe()"));
            return Response.serverError().entity(error).status(404).build();
        }
        return getResponseJson(jsonGeoJson, className);
    }


    private List<PubIstanzaSintesiDTO> valorizzaResponse(List<SintesiDTO> sintesiDTO) {
        List<PubIstanzaSintesiDTO> pubIstanzaSintesiList = new ArrayList<>();
        for (SintesiDTO sintesi : sintesiDTO) {
            PubIstanzaSintesiDTO pubIstanzaSintesi = new PubIstanzaSintesiDTO();
            pubIstanzaSintesi.setCodPratica(sintesi.getCodPratica());
            pubIstanzaSintesi.setDataConclusioneProcedimento(sintesi.getDataConclusioneProcedimento());
            pubIstanzaSintesi.setDataFineOsservazione(sintesi.getDataFineOsservazione());
            pubIstanzaSintesi.setDataInserimentoPratica(sintesi.getDataInserimentoPratica());
            pubIstanzaSintesi.setDataProtocolloIstanza(sintesi.getDataProtocolloIstanza());
            pubIstanzaSintesi.setDataPubblicazione(sintesi.getDataPubblicazione());
            pubIstanzaSintesi.setDenOggetto(sintesi.getDenOggetto());
            pubIstanzaSintesi.setDenomComune(sintesi.getDenomComune());
            pubIstanzaSintesi.setDesAdempimento(sintesi.getDesAdempimento());
            pubIstanzaSintesi.setCodTipoAdempimento(sintesi.getCodTipoAdempimento());
            pubIstanzaSintesi.setCodAmbito(sintesi.getCodAmbito());
            pubIstanzaSintesi.setDesCompetenzaTerritorio(sintesi.getDesCompetenzaTerritorio());
            pubIstanzaSintesi.setDesOggetto(sintesi.getDesOggetto());
            pubIstanzaSintesi.setFasePubblicazione(sintesi.getFasePubblicazione());
            pubIstanzaSintesi.setFlagContaConclusa(sintesi.getFlagContaConclusa());
            pubIstanzaSintesi.setFlagContaInCorso(sintesi.getFlagContaInCorso());
            pubIstanzaSintesi.setFlagContaConsultazione(sintesi.getFlagContaConsultazione());
            pubIstanzaSintesi.setFlgOsservazione(sintesi.getFlgOsservazione());
            pubIstanzaSintesi.setIdIstanza(sintesi.getIdIstanza());
            pubIstanzaSintesi.setLabelStato(sintesi.getLabelStato());
            pubIstanzaSintesiList.add(pubIstanzaSintesi);

        }
        return pubIstanzaSintesiList;
    }

    private List<PubIstanzaContatoreDTO> valorizzaContatore(List<SintesiDTO> sintesiConta) {
        List<PubIstanzaContatoreDTO> pubIstanzaContatoreList = new ArrayList<>();
        pubIstanzaContatoreList.add(new PubIstanzaContatoreDTO(FasePubblicazioneEnum.IN_CORSO.getDescrizione(), sintesiConta.get(0).getContatoreInCorso()));
        pubIstanzaContatoreList.add(new PubIstanzaContatoreDTO(FasePubblicazioneEnum.CONSULTA.getDescrizione(), sintesiConta.get(0).getContatoreInConsultazione()));
        pubIstanzaContatoreList.add(new PubIstanzaContatoreDTO(FasePubblicazioneEnum.CONCLUSA.getDescrizione(), sintesiConta.get(0).getContatoreConclusa()));
        pubIstanzaContatoreList.add(new PubIstanzaContatoreDTO(FasePubblicazioneEnum.TUTTE.getDescrizione(), sintesiConta.get(0).getContatoreAll()));

        return pubIstanzaContatoreList;
    }


}