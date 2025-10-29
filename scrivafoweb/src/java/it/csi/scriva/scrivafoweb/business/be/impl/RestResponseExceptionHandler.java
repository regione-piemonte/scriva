/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.impl;

import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

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

    @ExceptionHandler(Throwable.class)
    public Response handleThrowableException(Throwable error) {
        LOGGER.info("[RestResponseExceptionHandler::handleThrowableException] CALLED");

        return Response.serverError().entity(error).status(500).build();
//       return error.getBindingResult();
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(GenericException.class)
    public Response handleGenericException(GenericException error) {
        LOGGER.info("[RestResponseExceptionHandler::handleGenericException] CALLED");
        return Response.serverError().entity(error).status(500).build();
    }

}