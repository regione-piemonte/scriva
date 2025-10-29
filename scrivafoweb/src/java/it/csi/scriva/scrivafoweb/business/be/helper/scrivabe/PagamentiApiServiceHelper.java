/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe;

import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.PPayResultDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.PagamentoIstanzaExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.UrlDTO;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Categorie oggetto api service helper.
 */
public class PagamentiApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    /**
     * Instantiates a new Categorie oggetto api service helper.
     *
     * @param hostname     the hostname
     * @param endpointBase the endpoint base
     */
    public PagamentiApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    /**
     * Gets pagamenti istanza by id istanza.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @return the pagamenti istanza by id istanza
     * @throws GenericException the generic exception
     */
    public List<PagamentoIstanzaExtendedDTO> getPagamentiIstanzaByIdIstanza(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        List<PagamentoIstanzaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/pagamenti/id-istanza/" + idIstanza;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error(getClassFunctionErrorInfo(className, methodName, SERVER_EXCEPTION + err));
                throw new GenericException(err);
            }
            result = resp.readEntity(new GenericType<List<PagamentoIstanzaExtendedDTO>>() {
            });
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
        return result;
    }

    /**
     * Save tentativo pagamento list.
     *
     * @param requestHeaders   the request headers
     * @param pagamentoIstanza the pagamento istanza
     * @return the list
     * @throws GenericException the generic exception
     */
    public UrlDTO saveTentativoPagamento(MultivaluedMap<String, Object> requestHeaders, PagamentoIstanzaExtendedDTO pagamentoIstanza) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        UrlDTO result;
        String targetUrl = this.endpointBase + "/pagamenti";
        try {
            Entity<PagamentoIstanzaExtendedDTO> entity = Entity.json(pagamentoIstanza);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error(getClassFunctionErrorInfo(className, methodName, SERVER_EXCEPTION + err));
                throw new GenericException(err);
            }
            result = resp.readEntity(new GenericType<UrlDTO>() {
            });
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
        return result;
    }

    public IstanzaExtendedDTO saveResultTentativoPagamento(MultivaluedMap<String, Object> requestHeaders, PPayResultDTO pPayResult) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        IstanzaExtendedDTO result = null;
        String targetUrl = this.endpointBase + "/pagamenti/ppay-result";
        try {
            Entity<PPayResultDTO> entity = Entity.json(pPayResult);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error(getClassFunctionErrorInfo(className, methodName, SERVER_EXCEPTION + err));
                throw new GenericException(err);
            }
            result = resp.readEntity(new GenericType<IstanzaExtendedDTO>() {
            });
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
        return result;
    }

    public PagamentoIstanzaExtendedDTO saveTentativoPagamentoAltriCanali(MultivaluedMap<String, Object> requestHeaders, PagamentoIstanzaExtendedDTO pagamentoIstanza) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        PagamentoIstanzaExtendedDTO result;
        String targetUrl = this.endpointBase + "/pagamenti/altri-canali";
        try {
            Entity<PagamentoIstanzaExtendedDTO> entity = Entity.json(pagamentoIstanza);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).put(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error(getClassFunctionErrorInfo(className, methodName, SERVER_EXCEPTION + err));
                throw new GenericException(err);
            }
            result = resp.readEntity(new GenericType<PagamentoIstanzaExtendedDTO>() {
            });
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
        return result;
    }

    public Response getRTByIdIstanza(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        Response result = null;
        String targetUrl = this.endpointBase + "/pagamenti/rt/id-istanza/" + idIstanza;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error(getClassFunctionErrorInfo(className, methodName, SERVER_EXCEPTION + err));
                throw new GenericException(err);
            }
            result = resp;
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
        return result;
    }

}