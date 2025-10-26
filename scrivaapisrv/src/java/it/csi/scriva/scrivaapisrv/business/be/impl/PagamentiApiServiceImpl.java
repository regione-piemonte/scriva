/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.scriva.scrivaapisrv.business.be.PagamentiApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.PagamentiApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.PPayResultDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.PagamentoIstanzaExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.TentativoDettaglioExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.UrlDTO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Pagamenti api service.
 */
@Component
public class PagamentiApiServiceImpl  extends AbstractApiServiceImpl implements PagamentiApi {

    private final String CLASSNAME = this.getClass().getSimpleName();

    /**
     * The Pagamenti api service helper.
     */
    @Autowired
    PagamentiApiServiceHelper pagamentiApiServiceHelper;


    @Override
    public Response getPagamentiIstanzaByIdIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<PagamentoIstanzaExtendedDTO> pagamentoIstanzaList = new ArrayList<PagamentoIstanzaExtendedDTO>();
        try {
            pagamentoIstanzaList = pagamentiApiServiceHelper.getPagamentiIstanzaByIdIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return Response.ok(pagamentoIstanzaList).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    /**
     * Save tentativo pagamento response.
     *
     * @param pagamentoIstanza the pagamento istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the response
     */
    @Override
    public Response saveTentativoPagamento(PagamentoIstanzaExtendedDTO pagamentoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        UrlDTO url;
        try {
            url = pagamentiApiServiceHelper.saveTentativoPagamento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), pagamentoIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return Response.ok(url).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    /**
     * Save result tentativo pagamento response.
     *
     * @param pPayResult      the p pay result
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response saveResultTentativoPagamento(PPayResultDTO pPayResult, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        IstanzaExtendedDTO istanza = null;
        try {
            istanza = pagamentiApiServiceHelper.saveResultTentativoPagamento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), pPayResult);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return Response.ok(istanza).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    /**
     * Save tentativo pagamento altri canali response.
     *
     * @param pagamentoIstanza the pagamento istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the response
     */
    @Override
    public Response saveTentativoPagamentoAltriCanali(PagamentoIstanzaExtendedDTO pagamentoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));

        try {
            pagamentoIstanza = pagamentiApiServiceHelper.saveTentativoPagamentoAltriCanali(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), pagamentoIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return Response.ok(pagamentoIstanza).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    /**
     * Gets rt by id istanza.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the rt by id istanza
     */
    @Override
    public Response getRTByIdIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        try {
            return pagamentiApiServiceHelper.getRTByIdIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        //return null;
    }
}