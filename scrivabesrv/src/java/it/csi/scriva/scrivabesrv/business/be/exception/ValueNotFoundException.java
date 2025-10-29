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
 * The type Value not found exception.
 *
 * @author CSI PIEMONTE
 */
public class ValueNotFoundException extends GenericException{

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Value not found exception.
     *
     * @param msg the msg
     */
    public ValueNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Value not found exception.
     *
     * @param arg0 the arg 0
     */
    public ValueNotFoundException(Throwable arg0) {
        super(arg0);

    }

    /**
     * Instantiates a new Value not found exception.
     *
     * @param error the error
     */
    public ValueNotFoundException(ErrorDTO error) {
        super(error);
    }
}