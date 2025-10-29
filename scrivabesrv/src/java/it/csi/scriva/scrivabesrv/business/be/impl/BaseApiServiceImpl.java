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

import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.scriva.scrivabesrv.business.SpringApplicationContextHelper;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.helper.iride.dto.Identita;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.CompilanteDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.FunzionarioDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaAttoreDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ProfiloAppDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.impl.BaseServiceImpl;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.CompilanteDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.FunzionarioDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaAttoreExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaCompetenzaGestore;
import it.csi.scriva.scrivabesrv.dto.IstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoAppExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.PaginationHeaderDTO;
import it.csi.scriva.scrivabesrv.dto.ProfiloAppExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ProfiloOggettoAppDTO;
import it.csi.scriva.scrivabesrv.dto.ProfiloOggettoAppExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.XRequestAuth;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.TipoOggettoAppEnum;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.manager.ActionRoleManager;
import it.csi.scriva.scrivabesrv.util.manager.ErrorManager;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The type Base api service.
 *
 * @author CSI PIEMONTE
 */
public abstract class BaseApiServiceImpl extends BaseServiceImpl {

    private static final String HEADER_ATTORE_SCRIVA = "Attore-Scriva";

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private ActionRoleManager actionRoleManager;

    @Autowired
    private CompilanteDAO compilanteDAO;

    @Autowired
    private FunzionarioDAO funzionarioDAO;

    @Autowired
    private IstanzaAttoreDAO istanzaAttoreDAO;

    @Autowired
    private IstanzaDAO IstanzaDao;

    @Autowired
    private ProfiloAppDAO profiloAppDAO;

    @Autowired
    private ErrorManager errorManager;

    /**
     * The Attore scriva.
     */
    protected AttoreScriva attoreScriva;

    /**
     * Gets action role manager.
     *
     * @return the action role manager
     */
    public ActionRoleManager getActionRoleManager() {
        if (actionRoleManager == null) {
            actionRoleManager = (ActionRoleManager) SpringApplicationContextHelper.getBean("actionRoleManager");
        }
        return actionRoleManager;
    }

    /**
     * Gets error manager.
     *
     * @return the error manager
     */
    public ErrorManager getErrorManager() {
        if (errorManager == null) {
            errorManager = (ErrorManager) SpringApplicationContextHelper.getBean("errorManager");
        }
        return errorManager;
    }

    /**
     * Gets response list.
     *
     * @param list       the list
     * @param className  the class name
     * @param methodName the method name
     * @return the response list
     */
    protected Response getResponseList(List<?> list, String className, String methodName) {
        if (list == null) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            logError(className, methodName, error);
            logEnd(className, methodName);
            return Response.serverError().entity(error).status(500).build();
        } else if (list.isEmpty()) {
            ErrorDTO error = getErrorManager().getError("404", "", "Elemento non trovato", null, null);
            logError(className, methodName, error);
            logEnd(className, methodName);
            return Response.serverError().entity(error).status(404).build();
        }
        logEnd(className, methodName);
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header(Constants.HEADER_ATTORE_GESTIONE, attoreScriva != null ? attoreScriva.getProfiloAppIstanza() : null).build();
    }

    /**
     * Gets response.
     *
     * @param obj        the obj
     * @param className  the class name
     * @param methodName the method name
     * @return the response
     */
    protected Response getResponse(Object obj, String className, String methodName) {
        if (obj == null) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            logError(className, methodName, error);
            logEnd(className, methodName);
            return Response.serverError().entity(error).status(500).build();
        }
        logEnd(className, methodName);
        return Response.ok(obj).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header(Constants.HEADER_ATTORE_GESTIONE, attoreScriva != null ? attoreScriva.getProfiloAppIstanza() : null).build();
    }

    /**
     * Gets response.
     *
     * @param obj       the obj
     * @param className the class name
     * @return the response
     */
    protected Response getResponse(Object obj, String className) {
        return getResponse(obj, className, getMethodName(3));
    }

    /**
     * Gets response list.
     *
     * @param list      the list
     * @param className the class name
     * @return the response list
     */
    protected Response getResponseList(List<?> list, String className) {
        return getResponseList(list, className, getMethodName(3));
    }

    /**
     * Gets response list.
     *
     * @param obj       the obj
     * @param className the class name
     * @return the response list
     */
    protected Response getResponseList(Object obj, String className) {
        return getResponse(obj, className, getMethodName(3));
    }

    /**
     * Gets response json.
     *
     * @param json       the json
     * @param className  the class name
     * @param methodName the method name
     * @return the response json
     */
    protected Response getResponseJson(String json, String className, String methodName) {
        if (StringUtils.isBlank(json)) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            logError(className, error);
            logEnd(className);
            return Response.serverError().entity(error).status(500).build();
        }
        logEnd(className);
        return Response.ok(json).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header(Constants.HEADER_ATTORE_GESTIONE, attoreScriva != null ? attoreScriva.getProfiloAppIstanza() : null).build();
    }

    /**
     * Gets response json.
     *
     * @param json      the json
     * @param className the class name
     * @return the response json
     */
    protected Response getResponseJson(String json, String className) {
        return getResponseJson(json, className, getMethodName(3));
    }

    /**
     * Gets response save update.
     *
     * @param obj        the obj
     * @param className  the class name
     * @param methodName the method name
     * @return the response save update
     */
    protected Response getResponseSaveUpdate(Object obj, String className, String methodName) {
        return this.getResponseSaveUpdate(obj, className, methodName, null);
    }

    /**
     * Gets response save update.
     *
     * @param obj       the obj
     * @param className the class name
     * @return the response save update
     */
    protected Response getResponseSaveUpdate(Object obj, String className) {
        return this.getResponseSaveUpdate(obj, className, getMethodName(3), null);
    }

    /**
     * Gets response save.
     *
     * @param obj       the obj
     * @param className the class name
     * @param url       the url
     * @return the response save
     */
    protected Response getResponseSave(Object obj, String className, String url) {
        return this.getResponseSaveUpdate(obj, className, getMethodName(3), url);
    }

    /**
     * Gets response save update.
     *
     * @param obj        the obj
     * @param className  the class name
     * @param methodName the method name
     * @param url        the url
     * @return the response save update
     */
    protected Response getResponseSaveUpdate(Object obj, String className, String methodName, String url) {
        if (obj == null) {
            return getResponseError(className, methodName, 500, "E100", null, null);
        }
        if ((obj instanceof Integer) && (Integer) obj == -409) {
            return getResponseError(className, methodName, 409, "", "Errore nell'inserimento dell'elemento; causa: elemento duplicato", null);
        }
        if ((obj instanceof Integer) && (Integer) obj < 1) {
            return getResponseError(className, methodName, 404, "", "Errore nell'aggiornamento dell'elemento; causa: elemento non trovato", null);
        }
        URI uri = null;
        if (StringUtils.isNotBlank(url)) {
            try {
                uri = new URI(url);
            } catch (URISyntaxException e) {
                logError(className, e);
            }
        }
        logEnd(className, methodName);
        return Response.ok(obj).status(StringUtils.isNotBlank(url) ? 201 : 200).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header(Constants.HEADER_ATTORE_GESTIONE, attoreScriva != null ? attoreScriva.getProfiloAppIstanza() : null).location(uri).build();
    }

    /**
     * Gets response delete.
     *
     * @param obj        the obj
     * @param className  the class name
     * @param methodName the method name
     * @return the response delete
     */
    protected Response getResponseDelete(Object obj, String className, String methodName) {
        if (obj == null) {
            return getResponseError(className, methodName, 500, "E100", null, null);
        }
        if ((obj instanceof Integer) && (Integer) obj < 1) {
            return getResponseError(className, methodName, 404, "", "Errore nella cancellazione dell'elemento;  causa: elemento non trovato", null);
        }
        logEnd(className, methodName);
        return Response.noContent().header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header(Constants.HEADER_ATTORE_GESTIONE, attoreScriva != null ? attoreScriva.getProfiloAppIstanza() : null).build();
    }

    /**
     * Gets response delete.
     *
     * @param obj       the obj
     * @param className the class name
     * @return the response delete
     */
    protected Response getResponseDelete(Object obj, String className) {
        return getResponseDelete(obj, className, getMethodName(3));
    }

    /**
     * Get response error response.
     *
     * @param className    the class name
     * @param methodName   the method name
     * @param status       the status
     * @param codeError    the code error
     * @param titleError   the title error
     * @param detailsError the details error
     * @return the response
     */
    protected Response getResponseError(String className, String methodName, Integer status, String codeError, String titleError, Map<String, String> detailsError) {
        ErrorDTO error = getErrorManager().getError(String.valueOf(status), codeError, titleError, detailsError, null);
        logError(className, methodName, error);
        logEnd(className, methodName);
        return Response.serverError().entity(error).status(status).build();
    }

    /**
     * Gets response error.
     *
     * @param className    the class name
     * @param status       the status
     * @param codeError    the code error
     * @param titleError   the title error
     * @param detailsError the details error
     * @return the response error
     */
    protected Response getResponseError(String className, Integer status, String codeError, String titleError, Map<String, String> detailsError) {
        ErrorDTO error = getErrorManager().getError(String.valueOf(status), codeError, titleError, detailsError, null);
        logError(className, error);
        logEnd(className);
        return Response.serverError().entity(error).status(status).build();
    }

    /**
     * Gets response error.
     *
     * @param className the class name
     * @param error     the error
     * @return the response error
     */
    protected Response getResponseError(String className, ErrorDTO error) {
        logError(className, error);
        logEnd(className);
        return Response.serverError().entity(error).status(error.getStatus() != null ? Integer.parseInt(error.getStatus()) : 500).build();
    }

    /**
     * Gets pagination header.
     *
     * @param list   the list
     * @param offset the offset
     * @param limit  the limit
     * @return the pagination header
     */
    protected PaginationHeaderDTO getPaginationHeader(@NotNull List<?> list, @NotNull Integer offset, @NotNull Integer limit) {
        PaginationHeaderDTO paginationHeader = new PaginationHeaderDTO();
        paginationHeader.setPage(offset);
        paginationHeader.setPageSize(limit);
        paginationHeader.setTotalElements(list.size());
        paginationHeader.setTotalPages((list.size() / limit) + ((list.size() % limit) == 0 ? 0 : 1));
        return paginationHeader;
    }

    /**
     * Gets pagination header new.
     *
     * @param list   the list
     * @param offset the offset
     * @param limit  the limit
     * @return the pagination header new
     */
    protected PaginationHeaderDTO getPaginationHeaderNew(@NotNull List<?> list, @NotNull Integer offset, @NotNull Integer limit) {
        PaginationHeaderDTO paginationHeader = new PaginationHeaderDTO();
        paginationHeader.setPage(offset);
        paginationHeader.setPageSize(limit);
        paginationHeader.setTotalElements(list.size());
        paginationHeader.setTotalPages(list.size() % limit == 0 ? (list.size() / limit) : (list.size() / limit) + 1);
        return paginationHeader;
    }


    /**
     * Gets pagination header detail.
     *
     * @param totalElement the total element
     * @param offset       the offset
     * @param limit        the limit
     * @return the pagination header new
     */
    protected PaginationHeaderDTO getPaginationHeaderDetail(@NotNull Integer totalElement, @NotNull Integer offset, @NotNull Integer limit) {
        PaginationHeaderDTO paginationHeader = new PaginationHeaderDTO();
        paginationHeader.setPage(offset);
        paginationHeader.setPageSize(limit);
        paginationHeader.setTotalElements(totalElement);
        paginationHeader.setTotalPages(totalElement % limit == 0 ? (totalElement / limit) : (totalElement / limit) + 1);
        return paginationHeader;
    }

    /**
     * Gets multivalued map from http headers.
     *
     * @param httpHeaders the http headers
     * @param httpRequest the http request
     * @return the multivalued map from http headers
     */
    protected MultivaluedMap<String, Object> getMultivaluedMapFromHttpHeaders(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        if (httpHeaders == null) {
            return null;
        } else {
            MultivaluedMap<String, String> requestHeaders = httpHeaders.getRequestHeaders();
            MultivaluedMap<String, Object> map = new MultivaluedHashMap<>();
            requestHeaders.forEach((name, values) -> {
                if (!"Content-Type".equals(name) && !"Content-Length".equals(name) && CollectionUtils.isNotEmpty(values)) {
                    map.put(name, (values.size() != 1) ? Collections.singletonList(values) : Collections.singletonList(values.get(0)));
                }
            });
            return map;
        }
    }

    /**
     * Gets attore scriva.
     *
     * @param httpHeaders the http headers
     * @return the attore scriva
     * @throws GenericException the generic exception
     */
    protected AttoreScriva getAttoreScriva(HttpHeaders httpHeaders) throws GenericException {
        AttoreScriva attoreScriva = null;
        if (httpHeaders != null) {
            MultivaluedMap<String, String> requestHeaders = httpHeaders.getRequestHeaders();
            List<String> headerXRequestAuthList = requestHeaders.get(Constants.HEADER_X_REQUEST_AUTH);
            List<String> headerAttoreIstanzaList = requestHeaders.get(HEADER_ATTORE_SCRIVA);
            // usato nelle chiamate che arrivano da applicazioni esterne tramite la componente scrivaapisrv 
            String headerXRequestAuth = headerXRequestAuthList != null && !headerXRequestAuthList.isEmpty() ? headerXRequestAuthList.get(0) : null;
            // usato dalle chiamate che arrivano da FE scriva (consweb incluso)
            String headerAttoreIstanza = headerAttoreIstanzaList != null && !headerAttoreIstanzaList.isEmpty() ? headerAttoreIstanzaList.get(0) : null;

            if (StringUtils.isNotBlank(headerXRequestAuth)) {
                attoreScriva = getAttoreScrivaFromXRequestAuth(headerXRequestAuth);
            } else if (headerAttoreIstanza != null) {
                String[] headerAttoreIstanzaSplitted = headerAttoreIstanza.split("/");
                if (headerAttoreIstanzaSplitted.length > 1) {
                    attoreScriva = buildAttoreScriva(null, headerAttoreIstanzaSplitted[0], headerAttoreIstanzaSplitted[1], null, null, null);
                }
            }
        }
        if (attoreScriva == null) {
            ErrorDTO err = getErrorManager() != null ? getErrorManager().getError("400", null, "Attore non identificato.", null, null) : new ErrorDTO("400", null, "Attore non identificato.", null, null);
            logError(className, err);
            throw new GenericException(err);
        }

        return attoreScriva;
    }

    /**
     * Can call method boolean.
     *
     * @param methodName  the method name
     * @param httpHeaders the http headers
     * @return the boolean
     */
    protected Boolean canCallMethod(String methodName, HttpHeaders httpHeaders) {
        Boolean result = Boolean.FALSE;
        if (httpHeaders != null) {
            MultivaluedMap<String, String> requestHeaders = httpHeaders.getRequestHeaders();
            List<String> headerXRequestAuthList = requestHeaders.get(Constants.HEADER_X_REQUEST_AUTH);
            if (headerXRequestAuthList != null && !headerXRequestAuthList.isEmpty() && StringUtils.isNotBlank(headerXRequestAuthList.get(0))) {
                XRequestAuth requestAuth = getXRequestAuth(headerXRequestAuthList.get(0));
                List<ProfiloAppExtendedDTO> profiloAppList = requestAuth != null ? profiloAppDAO.loadProfiloAppByCodeProfiloAndComponenteApp(requestAuth.getRuolo(), requestAuth.getComponenteApplicativa(), requestAuth.getPassword(), methodName, TipoOggettoAppEnum.SERVIZIO.name()) : null;
                result = profiloAppList != null && !profiloAppList.isEmpty() ? Boolean.TRUE : Boolean.FALSE;
            }
        }
        return result;
    }

    /**
     * Build attore scriva attore scriva.
     *
     * @param idAttore      the id attore
     * @param componente    the componente
     * @param codiceFiscale the codice fiscale
     * @param flgRead       the flg read
     * @param flgWrite      the flg write
     * @param profiloApp    the profilo app
     * @return the attore scriva
     */
    protected AttoreScriva buildAttoreScriva(Long idAttore, String componente, String codiceFiscale, Boolean flgRead, Boolean flgWrite, String profiloApp) {
        return buildAttoreScriva(idAttore, componente, codiceFiscale, flgRead, flgWrite, profiloApp, null, null);
    }

    /**
     * Build attore scriva attore scriva.
     *
     * @param idAttore      the id attore
     * @param componente    the componente
     * @param codiceFiscale the codice fiscale
     * @param flgRead       the flg read
     * @param flgWrite      the flg write
     * @param profiloApp    the profilo app
     * @param ruolo         the ruolo
     * @param identita      the identita
     * @return the attore scriva
     */
    protected AttoreScriva buildAttoreScriva(Long idAttore, String componente, String codiceFiscale, Boolean flgRead, Boolean flgWrite, String profiloApp, String ruolo, Identita identita) {
        AttoreScriva result = new AttoreScriva();
        result.setIdAttore(idAttore);
        result.setComponente(componente);
        result.setCodiceFiscale(codiceFiscale);
        result.setCanReadIstanza(flgRead);
        result.setCanWriteIstanza(flgWrite);
        result.setProfiloAppIstanza(profiloApp);
        result.setRuolo(ruolo);
        result.setIdentita(identita);
        return result;
    }

    /**
     * Gets attore scriva from x request auth.
     *
     * @param headerXRequestAuth the header x request auth
     * @return the attore scriva from x request auth
     * @throws GenericException the generic exception
     */
    protected AttoreScriva getAttoreScrivaFromXRequestAuth(String headerXRequestAuth) throws GenericException {
        XRequestAuth requestAuth = getXRequestAuth(headerXRequestAuth);
        if (requestAuth != null) {
            List<ProfiloAppExtendedDTO> profiloAppList = profiloAppDAO.loadProfiloAppByCodeProfiloAndComponenteApp(requestAuth.getRuolo(), requestAuth.getComponenteApplicativa(), requestAuth.getPassword());
            if (profiloAppList != null && !profiloAppList.isEmpty()) {
                return buildAttoreScriva(null, requestAuth.getComponenteApplicativa(), requestAuth.getUsername(), null, null, null, requestAuth.getRuolo(), requestAuth.getIdentita());
            } else {
                ErrorDTO error = errorManager.getError("403", null, "Accesso negato", null, null);
                throw new GenericException(error);
            }
        }
        return null;
    }

    /**
     * Gets compilante.
     *
     * @return the compilante
     */
    protected CompilanteDTO getCompilante() {
        CompilanteDTO compilante = null;
        if (attoreScriva != null) {
            List<CompilanteDTO> compilanteList = compilanteDAO.loadCompilanteByCodiceFiscale(attoreScriva.getCodiceFiscale());
            compilante = compilanteList != null && !compilanteList.isEmpty() ? compilanteList.get(0) : null;
        }
        return compilante;
    }

    /**
     * Gets funzionario.
     *
     * @return the funzionario
     */
    protected FunzionarioDTO getFunzionario() {
        FunzionarioDTO funzionario = null;
        if (attoreScriva != null) {
            List<FunzionarioDTO> funzionarioList = funzionarioDAO.loadFunzionarioByCf(attoreScriva.getCodiceFiscale());
            funzionario = funzionarioList != null && !funzionarioList.isEmpty() ? funzionarioList.get(0) : null;
        }
        return funzionario;
    }

    /**
     * Gets istanza attore.
     *
     * @param idIstanza the id istanza
     * @return the istanza attore
     */
    protected IstanzaAttoreExtendedDTO getIstanzaAttore(Long idIstanza) {
        List<IstanzaAttoreExtendedDTO> istanzaAttoreList = istanzaAttoreDAO.loadIstanzaAttoreByIdIstanzaAndCFAttore(idIstanza, attoreScriva.getCodiceFiscale());
        return istanzaAttoreList != null && !istanzaAttoreList.isEmpty() ? istanzaAttoreList.get(0) : null;
    }

        /**
     * Gets istanza attore.
     *
     * @param idIstanza the id istanza
     * @return the istanza attore
     */
    protected IstanzaAttoreExtendedDTO getIstanzaAttoreAgnostico(Long idIstanza) {
        List<IstanzaAttoreExtendedDTO> istanzaAttoreList = istanzaAttoreDAO.loadIstanzaAttoreByIdIstanzaCFAttoreCodCompAppCodProfApp(idIstanza, attoreScriva.getCodiceFiscale(),attoreScriva.getComponente());
        return istanzaAttoreList != null && !istanzaAttoreList.isEmpty() ? istanzaAttoreList.get(0) : null;
    }

    /**
     * Sets attore right.
     *
     * @param httpHeaders the http headers
     * @param idIstanza   the id istanza
     * @return the attore right
     */
    protected Response setAttoreRight(HttpHeaders httpHeaders, Long idIstanza) {
        return setAttoreRight(httpHeaders, idIstanza, Boolean.FALSE);
    }
    
    /****METODO SET ATTORE ALTERNATIVO PER ERRORE RANDOM*****/
    protected Response setAttoreRight2(HttpHeaders httpHeaders, Long idIstanza) {
        return setAttoreRight2(httpHeaders, idIstanza, Boolean.FALSE);
    }
    /*********/

    /**
     * popola l'oggetto AttoreScriva e verifica i permessi rispetto all'istanza
     * 
     * @param httpHeaders     the http headers
     * @param idIstanza       the id istanza
     * @param checkWriteRight verifica  il  diritto di scrittura e restituisce errore 403 se non è presente
     * @return the attore right
     */
    protected Response setAttoreRight(HttpHeaders httpHeaders, Long idIstanza, Boolean checkWriteRight) {
        try {
            attoreScriva = getAttoreScriva(httpHeaders); //popola i dati attore desumibili dagli header della chiamata (componete e CF)
            logInfo(className, "attoreScriva idIstanza [" + idIstanza + "]:\n" + attoreScriva);
        } catch (GenericException e) {
            logError(className, e);
            return Response.serverError().entity(e.getError()).status(400).build();
        }
        /*
        Response response = verifyAccreditamento(attoreScriva);
        if (response != null) {
            return response;
        }
        */
        // se la componente è BO o FO
        if (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) || ComponenteAppEnum.FO.name().equalsIgnoreCase(attoreScriva.getComponente())) {

            //Se la componente è BO
            if (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
                FunzionarioDTO funzionario = getFunzionario();
                attoreScriva.setIdAttore(funzionario != null ? funzionario.getIdFunzionario() : null);
                attoreScriva.setGestUidAttore(funzionario != null ? funzionario.getGestUID() : null);
            // Se la componente è FO    
            } else {
                //CompilanteDTO compilante = getCompilante();
                IstanzaAttoreExtendedDTO istanzaAttore = getIstanzaAttore(idIstanza); //recupera i dati dall'istanzaAttore in base all'idIstanza. TODO verificare query
                attoreScriva.setIdAttore(istanzaAttore != null ? istanzaAttore.getIdIstanzaAttore() : null);
                attoreScriva.setGestUidAttore(istanzaAttore != null ? istanzaAttore.getGestUID() : null);
            }
            
            /* 
            parte successiva pre jira 1350 FIX errore random null pointer attore scriva
            if (idIstanza != null) {
                getActionRoleManager().getRightIstanza(idIstanza, attoreScriva);
                logInfo(className, "attoreScriva after actionRoleManager idIstanza [" + idIstanza + "]:\n" + attoreScriva);
                if (Boolean.TRUE.equals(!attoreScriva.getCanReadIstanza()) || (checkWriteRight && !attoreScriva.getCanWriteIstanza())) {
                    return Response.serverError().status(403).build();
                }
            }
            */
            
            if (idIstanza != null) {
            	logInfo(className, "CanReadIstanza e CanWriteIstanza non ancora impostati");
                // faccio arrivare il valore come oggetto anziche per riferimento per verificare se era questo che creava problemi diventando null senza motivo apparente
                // poi si è scoperto che il problema poteva essere dovuto al fatto che questa classe era singleton e l'attore poteva essere sovrascritto e quindi diventare null
                // lasciamo cmq tutto cosi per cautela anche se forse si potrebbe ripristinare come era prima
                
                // setta la Gestione Attore
                attoreScriva = getActionRoleManager().getRightIstanza(idIstanza, attoreScriva);
                logInfo(className, "attoreScriva after actionRoleManager idIstanza [" + idIstanza + "]:\n" + attoreScriva);
                
                // evita il nullpointer in caso di attore scriva non correttamente impostato
                
                // Se il diritto di lettura non è specificato
                if (attoreScriva.getCanReadIstanza() == null || 
                // O Se il diritto di scrittura non è specificato e il controllo è attivo
                (checkWriteRight && attoreScriva.getCanWriteIstanza()== null) ||
                // O Se il diritto di lettura è esplicitamente false. Se il valore è null, questa condizione non causerà eccezioni.
                Boolean.TRUE.equals(!attoreScriva.getCanReadIstanza()) || 
                // O Se il diritto di scrittura è esplicitamente false e il controllo è attivo.
                (checkWriteRight && !attoreScriva.getCanWriteIstanza())) {
                    // restituisce errore 403: Forbidden
                    return Response.serverError().status(403).build();
                }
            }
        }
        return null;
    }
    
    
    /****METODO SET ATTORE RIGHT ALTERNATIVO PER ERRORE RANDOM****/
    protected Response setAttoreRight2(HttpHeaders httpHeaders, Long idIstanza, Boolean checkWriteRight) {
             try {
             attoreScriva = getAttoreScriva(httpHeaders);
             logInfo(className, "attoreScriva idIstanza [" + idIstanza + "]:\n" + attoreScriva);
         } catch (GenericException e) {
             logError(className, e);
             return Response.serverError().entity(e.getError()).status(400).build();
         }
         /*
         Response response = verifyAccreditamento(attoreScriva);
         if (response != null) {
             return response;
         }
         */
         if (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) || ComponenteAppEnum.FO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
             if (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
                 FunzionarioDTO funzionario = getFunzionario();
                 attoreScriva.setIdAttore(funzionario != null ? funzionario.getIdFunzionario() : null);
                 attoreScriva.setGestUidAttore(funzionario != null ? funzionario.getGestUID() : null);
             } else {
                 //CompilanteDTO compilante = getCompilante();
                 IstanzaAttoreExtendedDTO istanzaAttore = getIstanzaAttore(idIstanza);
                 attoreScriva.setIdAttore(istanzaAttore != null ? istanzaAttore.getIdIstanzaAttore() : null);
                 attoreScriva.setGestUidAttore(istanzaAttore != null ? istanzaAttore.getGestUID() : null);
             }
             if (idIstanza != null) {
                     logInfo(className, "CanReadIstanza e CanWriteIstanza non ancora impostati");
                 getActionRoleManager().getRightIstanza2(idIstanza, attoreScriva);
                 logInfo(className, "attoreScriva after actionRoleManager idIstanza [" + idIstanza + "]:\n" + attoreScriva);
                 if (Boolean.TRUE.equals(!attoreScriva.getCanReadIstanza()) || (checkWriteRight && !attoreScriva.getCanWriteIstanza())) {
                     return Response.serverError().status(403).build();
                 }
             }
         }
         return null;
    }
    /********/
    

    /**
     * Verify accreditamento response.
     *
     * @param attoreScriva the attore scriva
     * @return the response
     */
    protected Response verifyAccreditamento(AttoreScriva attoreScriva) {
        if (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) || ComponenteAppEnum.FO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
            if (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
                FunzionarioDTO funzionario = getFunzionario();
                if (funzionario == null) {
                    return Response.serverError().status(403).build();
                }
            } else {
                CompilanteDTO compilante = getCompilante();
                if (compilante == null)
                    return Response.serverError().status(403).build();
            }
        }
        return null;
    }


    /**
     * Gets x request auth.
     *
     * @param headerXRequestAuth the header x request auth
     * @return the x request auth
     */
    protected XRequestAuth getXRequestAuth(String headerXRequestAuth) {
        try {
            String xRequestAuth = new String(Base64.decode(headerXRequestAuth));
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(xRequestAuth, XRequestAuth.class);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets x request auth.
     *
     * @param httpHeaders the http headers
     * @return the x request auth
     */
    protected XRequestAuth getXRequestAuth(HttpHeaders httpHeaders) {
        try {
            if (httpHeaders != null) {
                MultivaluedMap<String, String> requestHeaders = httpHeaders.getRequestHeaders();
                List<String> headerXRequestAuthList = requestHeaders.get(Constants.HEADER_X_REQUEST_AUTH);
                if (headerXRequestAuthList != null && !headerXRequestAuthList.isEmpty() && StringUtils.isNotBlank(headerXRequestAuthList.get(0))) {
                    return getXRequestAuth(headerXRequestAuthList.get(0));
                }
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
     * Gets profilo app.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the profilo app
     */
    protected List<ProfiloOggettoAppDTO> getProfiloApp(Long idIstanza, AttoreScriva attoreScriva) {
        return profiloAppDAO.loadProfiloAppByIdIstanzaAndAttore(idIstanza, attoreScriva);
    }

    /**
     * Gets profilo app extended.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the profilo app extended
     */
    protected List<ProfiloOggettoAppExtendedDTO> getProfiloAppExtended(Long idIstanza, AttoreScriva attoreScriva) {
        logBegin(className);
        List<ProfiloOggettoAppDTO> profiloOggettoAppList = profiloAppDAO.loadProfiloAppByIdIstanzaAndAttore(idIstanza, attoreScriva);
        List<ProfiloOggettoAppDTO> profiloOggettoAppUniqueList = profiloOggettoAppList.stream()
                .filter(distinctByKey(ProfiloAppExtendedDTO::getCodProfiloApp))
                .collect(Collectors.toList());
        logEnd(className);
        return extractProfiloOggettoAppExtended(idIstanza, profiloOggettoAppList, profiloOggettoAppUniqueList);
    }

    /**
     * Gets profilo app extended new.
     *
     * @param idIstanzaList the id istanza list
     * @param attoreScriva  the attore scriva
     * @return the profilo app extended new
     */
    protected List<ProfiloOggettoAppExtendedDTO> getProfiloAppExtendedNew(List<Long> idIstanzaList, AttoreScriva attoreScriva) {
        logBegin(className);
        List<ProfiloOggettoAppExtendedDTO> result = null;
        List<ProfiloOggettoAppDTO> profiloOggettoAppList;
        if (CollectionUtils.isNotEmpty(idIstanzaList) && attoreScriva != null) {
            if (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
                profiloOggettoAppList = profiloAppDAO.loadProfiloAppByAttoreBO(attoreScriva);
            } else {
                profiloOggettoAppList = profiloAppDAO.loadProfiloAppByIdIstanzaListAndAttore(idIstanzaList, attoreScriva);
            }
            if (CollectionUtils.isNotEmpty(profiloOggettoAppList)) {
                result = extractProfiloOggettoAppExtendedNew(idIstanzaList, profiloOggettoAppList);
            }
        }
        logEnd(className);
        return result;
    }

    /**
     * Gets profilo app extended json.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the profilo app extended json
     */
    protected String getProfiloAppExtendedJson(Long idIstanza, AttoreScriva attoreScriva) {
        logBegin(className);
        StringWriter stringWriter = new StringWriter();
        try {
            List<ProfiloOggettoAppExtendedDTO> profiloOggettoAppExtendedList = getProfiloAppExtended(idIstanza, attoreScriva);
            if (profiloOggettoAppExtendedList != null && !profiloOggettoAppExtendedList.isEmpty()) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(stringWriter, profiloOggettoAppExtendedList);
            }
        } catch (IOException e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return stringWriter.toString();
    }

    /**
     * Extract profilo oggetto app extended list.
     *
     * @param idIstanza                   the id istanza
     * @param profiloOggettoAppList       the profilo oggetto app list
     * @param profiloOggettoAppUniqueList the profilo oggetto app unique list
     * @return the list
     */
    private List<ProfiloOggettoAppExtendedDTO> extractProfiloOggettoAppExtended(Long idIstanza, List<ProfiloOggettoAppDTO> profiloOggettoAppList, List<ProfiloOggettoAppDTO> profiloOggettoAppUniqueList) {
        List<ProfiloOggettoAppExtendedDTO> result = new ArrayList<>();
        if (profiloOggettoAppList != null && profiloOggettoAppUniqueList != null) {
            String gestoreProcesso = actionRoleManager.getGestoreProcesso(idIstanza);
            for (ProfiloOggettoAppDTO profiloOggettoApp : profiloOggettoAppUniqueList) {
                ProfiloOggettoAppExtendedDTO profiloOggettoAppNew = new ProfiloOggettoAppExtendedDTO();
                profiloOggettoAppNew.setIdProfiloApp(profiloOggettoApp.getIdProfiloApp());
                profiloOggettoAppNew.setCodProfiloApp(profiloOggettoApp.getCodProfiloApp());
                profiloOggettoAppNew.setComponenteApp(profiloOggettoApp.getComponenteApp());
                profiloOggettoAppNew.setDesProfiloApp(profiloOggettoApp.getDesProfiloApp());
                profiloOggettoAppNew.setFlgProfiloIride(profiloOggettoApp.getFlgProfiloIride());
                profiloOggettoAppNew.setFlgCompetenza(profiloOggettoApp.getFlgCompetenza());
                List<OggettoAppExtendedDTO> oggettoAppList = profiloOggettoAppList.stream()
                        .filter(p -> Objects.equals(p.getIdProfiloApp(), profiloOggettoApp.getIdProfiloApp()))
                        .map(ProfiloOggettoAppDTO::getOggettoApp)
                        .collect(Collectors.toList());
                // Se previsto un gestore processo per l'istanza, filtrare gli oggetti applicativi
                oggettoAppList = StringUtils.isBlank(gestoreProcesso) ?
                        oggettoAppList :
                        oggettoAppList.stream()
                                .filter(ogg -> Boolean.FALSE.equals(ogg.getFlgPrevistoDaGestoreProcesso()))
                                .collect(Collectors.toList());
                if (!oggettoAppList.isEmpty() && oggettoAppList.get(0) != null) {
                    profiloOggettoAppNew.setOggettoAppList(oggettoAppList);
                }
                result.add(profiloOggettoAppNew);
            }
        }
        return result;
    }

    /**
     * Extract profilo oggetto app extended new list.
     *
     * @param idIstanzaList         the id istanza list
     * @param profiloOggettoAppList the profilo oggetto app list
     * @return the list
     */
    private List<ProfiloOggettoAppExtendedDTO> extractProfiloOggettoAppExtendedNew(List<Long> idIstanzaList, List<ProfiloOggettoAppDTO> profiloOggettoAppList) {
        List<ProfiloOggettoAppExtendedDTO> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(profiloOggettoAppList)) {
            List<IstanzaCompetenzaGestore> istanzaCompetenzaGestoreList = actionRoleManager.getIstanzaCompetenzaGestoreList(idIstanzaList);
            for (long idIstanza : idIstanzaList) {
                List<ProfiloOggettoAppDTO> profiloOggettoAppUniqueList = ComponenteAppEnum.FO.name().equalsIgnoreCase(attoreScriva.getComponente()) ?
                        profiloOggettoAppList.stream()
                                .filter(p -> p.getIdIstanza() == idIstanza)
                                .filter(distinctByKey(ProfiloAppExtendedDTO::getCodProfiloApp))
                                .collect(Collectors.toList()) :
                        profiloOggettoAppList.stream()
                                .filter(distinctByKey(ProfiloAppExtendedDTO::getCodProfiloApp))
                                .collect(Collectors.toList());
                for (ProfiloOggettoAppDTO profiloOggettoApp : profiloOggettoAppUniqueList) {
                    ProfiloOggettoAppExtendedDTO profiloOggettoAppNew = new ProfiloOggettoAppExtendedDTO();
                    profiloOggettoAppNew.setIdIstanza(idIstanza);
                    profiloOggettoAppNew.setIdProfiloApp(profiloOggettoApp.getIdProfiloApp());
                    profiloOggettoAppNew.setCodProfiloApp(profiloOggettoApp.getCodProfiloApp());
                    profiloOggettoAppNew.setComponenteApp(profiloOggettoApp.getComponenteApp());
                    profiloOggettoAppNew.setDesProfiloApp(profiloOggettoApp.getDesProfiloApp());
                    profiloOggettoAppNew.setFlgProfiloIride(profiloOggettoApp.getFlgProfiloIride());
                    profiloOggettoAppNew.setFlgCompetenza(profiloOggettoApp.getFlgCompetenza());
                    List<OggettoAppExtendedDTO> oggettoAppList = new ArrayList<>();
                    if (profiloOggettoApp.getOggettoApp() != null) {
                        oggettoAppList = ComponenteAppEnum.FO.name().equalsIgnoreCase(attoreScriva.getComponente()) ?
                                profiloOggettoAppList.stream()
                                        .filter(p -> p.getIdIstanza() == idIstanza && Objects.equals(p.getIdProfiloApp(), profiloOggettoApp.getIdProfiloApp()))
                                        .map(ProfiloOggettoAppDTO::getOggettoApp)
                                        .filter(distinctByKey(OggettoAppExtendedDTO::getIdOggettoApp))
                                        .sorted(Comparator.comparingLong(OggettoAppExtendedDTO::getIdOggettoApp))
                                        .collect(Collectors.toList()) :
                                profiloOggettoAppList.stream()
                                        .filter(p -> Objects.equals(p.getIdProfiloApp(), profiloOggettoApp.getIdProfiloApp()))
                                        .map(ProfiloOggettoAppDTO::getOggettoApp)
                                        .filter(distinctByKey(OggettoAppExtendedDTO::getIdOggettoApp))
                                        .sorted(Comparator.comparingLong(OggettoAppExtendedDTO::getIdOggettoApp))
                                        .collect(Collectors.toList());
                    }
                    // Se previsto un gestore processo per l'istanza, filtrare gli oggetti applicativi
                    List<IstanzaCompetenzaGestore> istanzaCompetenzaGestoreFilteredList = istanzaCompetenzaGestoreList.stream()
                            .filter(i -> i.getIdIstanza() == idIstanza)
                            .filter(g -> g.getIdComponenteGestoreProcesso() != null)
                            .collect(Collectors.toList());

                    oggettoAppList = CollectionUtils.isEmpty(istanzaCompetenzaGestoreFilteredList) ?
                            oggettoAppList :
                            oggettoAppList.stream()
                                    .filter(ogg -> Boolean.FALSE.equals(ogg.getFlgPrevistoDaGestoreProcesso()))
                                    .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(oggettoAppList)) {
                        profiloOggettoAppNew.setOggettoAppList(oggettoAppList);
                    }
                    result.add(profiloOggettoAppNew);
                }
            }
        }
        return result;
    }

    /**
     * Distinct by key predicate.
     *
     * @param keyExtractor the key extractor
     * @param <T>          the type parameter
     * @return the predicate
     */

    protected Boolean updateIstanzaPraticaTimestampAttore(Long idIstanza, AttoreScriva attoreScriva)
    {   
        logBegin(className);
        try{
            IstanzaDTO istanza = IstanzaDao.findByPK(idIstanza);
            
            if (ComponenteAppEnum.FO.name().equalsIgnoreCase(attoreScriva.getComponente())) 
            {
                IstanzaAttoreExtendedDTO istanzaAttore = getIstanzaAttoreAgnostico(idIstanza);

                if(istanzaAttore != null)
                {
                    Long idIstanzaAttore =  istanzaAttore.getIdIstanzaAttore();
                    IstanzaDao.updateIstanzaPraticaTimestampAttore( idIstanza,  attoreScriva.getComponente(),  idIstanzaAttore, null, true);
                    return true;
                }
                else
                {
                    return false;
                }            
            }
            else if (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente())) 
            {
                Long idFunzionario =  funzionarioDAO.loadFunzionarioByCf(attoreScriva.getCodiceFiscale()).get(0).getIdFunzionario();
                IstanzaDao.updateIstanzaPraticaTimestampAttore( idIstanza,  attoreScriva.getComponente(),  null, idFunzionario, true);               
                return true;
            }
            else
            {
                return false;
            }
        }
        catch(Exception e)
        {
            logError(className, e);
            return false;
        }
        finally
        {
            logEnd(className);
        }
        
    }
}