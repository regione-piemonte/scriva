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


import it.csi.scriva.scrivabesrv.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;

@ControllerAdvice
public class RestResponseExceptionHandler {
    protected static Logger LOGGER = Logger.getLogger(Constants.LOGGER_NAME + ".business");

    @ExceptionHandler(ConstraintViolationException.class)
    public Response handleConstraintViolationException(ConstraintViolationException error) {
        LOGGER.info("[RestResponseExceptionHandler::handleConstraintViolationException] CALLED");

        return Response.serverError().entity(error).status(500).build();
//       return error.getBindingResult();
    }

    @ExceptionHandler(NullPointerException.class)
    public Response handleNullPointerException(NullPointerException error) {
        LOGGER.info("[RestResponseExceptionHandler::handleNullPointerException] CALLED");
        return Response.serverError().entity(error).status(500).build();
    }

    @ExceptionHandler(Throwable.class)
    public Response handleThrowableException(Throwable error) {
        LOGGER.info("[RestResponseExceptionHandler::handleThrowableException] CALLED");

        return Response.serverError().entity(error).status(500).build();
//       return error.getBindingResult();
    }

    @ExceptionHandler(Exception.class)
    public Response handleException(Exception error) {
        LOGGER.info("[RestResponseExceptionHandler::handleException] CALLED");
        return Response.serverError().entity(error).status(500).build();
    }
}