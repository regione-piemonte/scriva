/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be;

import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.ws.rs.NotAllowedException;
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

    private static Logger LOGGER = Logger.getLogger(Constants.LOGGER_NAME + ".business");

    public ExceptionProvider() {
        LOGGER.info("Inizializzazione provider exception");
    }

    @Override
    public Response toResponse(Throwable exception) {
        Response response = null;
        if (exception instanceof GenericException) {
            GenericException e = (GenericException) exception;
            response = Response.serverError().entity(e.getError())
                    .status(e.getError() != null && e.getError().getStatus() != null ? Integer.parseInt(e.getError().getStatus()) : 500)
                    .build();
        } else if (exception instanceof NotAllowedException) {
            response = Response.serverError().status(403).build();
        } else {
            LOGGER.error("ExceptionProvider::toResponse", exception);
            ErrorDTO error = new ErrorDTO("500", "E100", "Errore inaspettato nella gestione della richiesta. Riprova a breve.", null, null);
            response = Response.serverError().entity(error).status(500).build();
        }
        return response;
    }

}