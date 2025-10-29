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

/**
 * The type Incorrect scrivabesrv request exception.
 *
 * @author CSI PIEMONTE
 */
public class IncorrectScrivabesrvRequestException extends GenericException{
    /**
     * Instantiates a new Incorrect scrivabesrv request exception.
     *
     * @param msg the msg
     */
    public IncorrectScrivabesrvRequestException(String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Incorrect scrivabesrv request exception.
     *
     * @param arg0 the arg 0
     */
    public IncorrectScrivabesrvRequestException(Throwable arg0) {
        super(arg0);

    }
}