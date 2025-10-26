/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.IstanzaVincoloAutExtendedDTO;

import java.util.ArrayList;
import java.util.List;

public class IstanzaVincoliAutorizzazioniApiServiceHelper extends AbstractServiceHelper {

    private final String CLASSNAME = this.getClass().getSimpleName();

    public IstanzaVincoliAutorizzazioniApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<IstanzaVincoloAutExtendedDTO> getIstanzaVincoloAutorizzazioneByIstanza(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException{
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<IstanzaVincoloAutExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/istanza-vincoli-aut/id-istanza/" + idIstanza;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, "SERVER EXCEPTION : " + err));
                throw new GenericException(err);
            }
            result = resp.readEntity(new GenericType<List<IstanzaVincoloAutExtendedDTO>>() {
            });
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return result;
    }

    public IstanzaVincoloAutExtendedDTO saveIstanzaVincoloAutorizzazione(MultivaluedMap<String, Object> requestHeaders, IstanzaVincoloAutExtendedDTO istanzaVincoloAut) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        IstanzaVincoloAutExtendedDTO result = new IstanzaVincoloAutExtendedDTO();
        String targetUrl = this.endpointBase + "/istanza-vincoli-aut";
        try {
            Entity<IstanzaVincoloAutExtendedDTO> entity = Entity.json(istanzaVincoloAut);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, "SERVER EXCEPTION : " + err));
                throw new GenericException(err);
            }
            result = resp.readEntity(new GenericType<IstanzaVincoloAutExtendedDTO>() {
            });
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return result;
    }

    public IstanzaVincoloAutExtendedDTO updateIstanzaVincoloAutorizzazione(MultivaluedMap<String, Object> requestHeaders, IstanzaVincoloAutExtendedDTO istanzaVincoloAut) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        IstanzaVincoloAutExtendedDTO result = new IstanzaVincoloAutExtendedDTO();
        String targetUrl = this.endpointBase + "/istanza-vincoli-aut";
        try {
            Entity<IstanzaVincoloAutExtendedDTO> entity = Entity.json(istanzaVincoloAut);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).put(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, "SERVER EXCEPTION : " + err));
                throw new GenericException(err);
            }
            result = resp.readEntity(new GenericType<>() {
            });
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return result;
    }

    public Boolean deleteIstanzaVincoloAutorizzazione(MultivaluedMap<String, Object> requestHeaders, String uid) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        String targetUrl = this.endpointBase + "/istanza-vincoli-aut/" + uid;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).delete();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, "SERVER EXCEPTION : " + err));
                throw new GenericException(err);
            } else {
                return Boolean.TRUE;
            }
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
    }

}