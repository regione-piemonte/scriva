/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.impl;

import it.csi.scriva.scrivaapisrv.business.be.SoggettiApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.SoggettiApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.SoggettoExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.XRequestAuth;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The type Soggetti api service.
 */
@Component
public class SoggettiApiServiceImpl extends AbstractApiServiceImpl implements SoggettiApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Soggetti api service helper.
     */
    @Autowired
    SoggettiApiServiceHelper soggettiApiServiceHelper;


    /**
     * Gets soggetti.
     *
     * @param xRequestAuth         the x request auth
     * @param xRequestId           the x request id
     * @param securityContext      the security context
     * @param httpHeaders          the http headers
     * @param httpRequest          the http request
     * @param idSoggetto           the id soggetto
     * @param codiceFiscale        the codice fiscale
     * @param tipoSoggetto         the tipo soggetto
     * @param codAdempimento       the cod adempimento
     * @param codiceFiscaleImpresa the codice fiscale impresa
     * @return the soggetti
     */
    @Override
    public Response getSoggetti(String xRequestAuth, String xRequestId, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, Long idSoggetto, String codiceFiscale, String tipoSoggetto, String codAdempimento, String codiceFiscaleImpresa) {
        logBegin(className);
        logDebug(className, "Parametri in input idSoggetto [" + idSoggetto + "] - codiceFiscale [" + codiceFiscale + "] - tipoSoggetto [" + tipoSoggetto + "] - codAdempimento [" + codAdempimento + "] - codiceFiscaleImpresa [" + codiceFiscaleImpresa + "]");
        List<SoggettoExtendedDTO> listSoggetti = new ArrayList<>();
        try {
            if (null != idSoggetto) {
                listSoggetti = soggettiApiServiceHelper.getSoggettoById(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idSoggetto);
            } else {
                listSoggetti = soggettiApiServiceHelper.getSoggetti(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codiceFiscale, tipoSoggetto, codAdempimento, codiceFiscaleImpresa);
            }
            if (listSoggetti.isEmpty()) {
                ErrorDTO err = new ErrorDTO("404", "E100", "Dato non trovato", null, null);
                logError(className, err);
                return Response.serverError().entity(err).status(404).build();
            }
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            ErrorDTO err = new ErrorDTO("500", "E100", e.getMessage(), null, null);
            logError(className, err);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
            LOGGER.debug("[SoggettiApiImpl::getSoggetti] END");
        }
        return Response.ok(listSoggetti).header(HttpHeaders.CONTENT_ENCODING, "identity").build();

    }

    /**
     * Save soggetto response.
     *
     * @param xRequestAuth the x request auth
     * @param xRequestId   the x request id
     * @param soggetto     the soggetto
     * @param httpHeaders  the http headers
     * @param httpRequest  the http request
     * @return the response
     */
    @Override
    public Response saveSoggetto(String xRequestAuth, String xRequestId, SoggettoExtendedDTO soggetto,
                                 SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[SoggettiApiImpl::saveSoggetto] BEGIN");
        LOGGER.debug("[SoggettiApiImpl::saveSoggetto] Parametro in input soggetto :\r\n " + soggetto.toString());
        SoggettoExtendedDTO dto = null;
        //UserInfo user = getSessionUser(httpRequest);
        XRequestAuth xRequAuth = getXRequestAuth(xRequestAuth);
        soggetto.setGestAttoreIns(xRequAuth.getUsername());
        try {
            String codiceFiscale = null;
            String codiceFiscaleImpresa = null;
            String tipoSoggetto = null;
            //verifico che il tipo soggetto ed i relativi parametri obbligatori siano forniti
            try {
                if (soggetto.getTipoSoggetto().getCodiceTipoSoggetto().equalsIgnoreCase("PF")) {
                    codiceFiscale = soggetto.getCfSoggetto();
                    if (StringUtils.isBlank(codiceFiscale)) {
                        String errorMessage = "Parametri in ingresso non validi : codice fiscale è obbligatorio.";
                        ErrorDTO err = new ErrorDTO("400", "E100", errorMessage, null, null);
                        LOGGER.error("[SoggettiApiImpl::saveSoggetto] ERROR : " + errorMessage);
                        return Response.serverError().entity(err).status(400).build();
                    }
                    tipoSoggetto = "PF";
                } else if (soggetto.getTipoSoggetto().getCodiceTipoSoggetto().equalsIgnoreCase("PG") || soggetto.getTipoSoggetto().getCodiceTipoSoggetto().equalsIgnoreCase("PB")) {
                    codiceFiscale = soggetto.getCfSoggetto();
                    if (StringUtils.isBlank(codiceFiscale)) {
                        String errorMessage = "Parametri in ingresso non validi : codice fiscale è obbligatorio.";
                        ErrorDTO err = new ErrorDTO("400", "E100", errorMessage, null, null);
                        LOGGER.error("[SoggettiApiImpl::saveSoggetto] ERROR : " + errorMessage);
                        return Response.serverError().entity(err).status(400).build();
                    }
                    codiceFiscaleImpresa = soggetto.getPivaSoggetto();
                    if (StringUtils.isBlank(codiceFiscaleImpresa)) {
                        String errorMessage = "Parametri in ingresso non validi : partita iva è obbligatorio.";
                        ErrorDTO err = new ErrorDTO("400", "E100", errorMessage, null, null);
                        LOGGER.error("[SoggettiApiImpl::saveSoggetto] ERROR : " + errorMessage);
                        return Response.serverError().entity(err).status(400).build();
                    }
                    tipoSoggetto = soggetto.getTipoSoggetto().getCodiceTipoSoggetto();
                }
            } catch (NullPointerException npe) {
                String errorMessage = "Parametri in ingresso non validi : codice tipo soggetto è obbligatorio.";
                ErrorDTO err = new ErrorDTO("400", "E100", errorMessage, null, null);
                LOGGER.error("[SoggettiApiImpl::saveSoggetto] ERROR : " + errorMessage);
                return Response.serverError().entity(err).status(400).build();
            }
            //se il tipo soggetto non è valido
            if (StringUtils.isBlank(tipoSoggetto)) {
                String errorMessage = "Parametri in ingresso non validi : codice tipo soggetto non valido.";
                ErrorDTO err = new ErrorDTO("400", "E100", errorMessage, null, null);
                LOGGER.error("[SoggettiApiImpl::saveSoggetto] ERROR : " + errorMessage);
                return Response.serverError().entity(err).status(400).build();
            }

            List<SoggettoExtendedDTO> soggettoList = soggettiApiServiceHelper.getSoggetti(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codiceFiscale, tipoSoggetto, null, codiceFiscaleImpresa);

            //se il soggetto non esiste già lo creo
            if (null == soggettoList || soggettoList.isEmpty()) {
                dto = soggettiApiServiceHelper.saveSoggetto(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), soggetto);
            } else {
                SoggettoExtendedDTO soggettoFromDB = soggettoList.get(0);

                Date d = soggettoFromDB.getDataAggiornamento();

                //se data aggiornamento soggetto preso dal db è più vecchia
                if (null == d || d.before(soggetto.getDataAggiornamento())) {
                    if (null == soggetto.getDataAggiornamento()) {
                        soggetto.setDataAggiornamento(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                    }
                    soggetto.setIdSoggetto(soggettoFromDB.getIdSoggetto());
                    dto = soggettiApiServiceHelper.updateSoggetto(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), soggetto);
                } else {
                    dto = soggettoFromDB;
                }
            }
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[SoggettiApiImpl::saveSoggetto] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[SoggettiApiImpl::saveSoggetto] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[SoggettiApiImpl::saveSoggetto] END");
        }
        return Response.ok(dto).header(HttpHeaders.CONTENT_ENCODING, "identity").status(201).build();
    }

}