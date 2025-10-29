/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be;

import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * The type Jackson config.
 *
 * @author CSI PIEMONTE
 */
@Provider
@Component
@Produces(MediaType.APPLICATION_JSON)
public class ExceptionProvider implements ExceptionMapper<Throwable> {

    private static final Logger LOGGER = Logger.getLogger(Constants.LOGGER_NAME + ".business");

    public ExceptionProvider() {
        LOGGER.info("ExceptionProvider::ExceptionProvider construct");
    }

    @Override
    public Response toResponse(Throwable exception) {
        Response response = null;
        LOGGER.error("ExceptionProvider::toResponse", exception);
        if (exception instanceof NotAllowedException) {
            response = Response.serverError().status(403).build();
        } else if (exception instanceof GenericException) {
            GenericException e = (GenericException) exception;
            ErrorDTO err = e.getError();
            response = Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } else if (exception instanceof ProcessingException) {
            ProcessingException e = (ProcessingException) exception;
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            response = Response.serverError().entity(err).status(500).build();
        } else {
            ErrorDTO error = new ErrorDTO("500", "E100", "Errore inaspettato nella gestione della richiesta. Riprova a breve.", null, null);
            response = Response.serverError().entity(error).status(500).build();
        }
        return response;
    }

}