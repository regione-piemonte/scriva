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
 * The type User not allowed exception.
 */
public class UserNotAllowedException extends GenericException {

    /**
     * Instantiates a new User not allowed exception.
     *
     * @param msg the msg
     */
    public UserNotAllowedException(String msg) {
        super(msg);
    }

    /**
     * Instantiates a new User not allowed exception.
     *
     * @param arg0 the arg 0
     */
    public UserNotAllowedException(Throwable arg0) {
        super(arg0);

    }

    /**
     * Instantiates a new User not allowed exception.
     *
     * @param errore the errore
     */
    public UserNotAllowedException(ErrorDTO errore) {
        super(errore);
    }
}