/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.exception;

import it.csi.scriva.scrivabesrv.dto.ErrorDTO;

/**
 * The type Generic exception.
 *
 * @author CSI PIEMONTE
 */
public class GenericException extends Exception{
    private static final long serialVersionUID = 1L;

    private ErrorDTO error;

    /**
     * Gets error.
     *
     * @return the error
     */
    public ErrorDTO getError() {
        return error;
    }

    /**
     * Sets error.
     *
     * @param error the error
     */
    public void setError(ErrorDTO error) {
        this.error = error;
    }

    /**
     * Instantiates a new Generic exception.
     *
     * @param msg error message
     */
    public GenericException(String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Generic exception.
     *
     * @param errore ErrorDTO
     */
    public GenericException(ErrorDTO errore) { this.setError(errore); }

    /**
     * Instantiates a new Generic exception.
     *
     * @param arg0 arg
     */
    public GenericException(Throwable arg0) { super(arg0); }

}