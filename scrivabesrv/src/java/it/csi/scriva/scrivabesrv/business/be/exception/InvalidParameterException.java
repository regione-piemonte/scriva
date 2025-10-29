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
 * The type Invalid parameter exception.
 *
 * @author CSI PIEMONTE
 */
public class InvalidParameterException extends GenericException{


    /**
     * Instantiates a new Invalid parameter exception.
     *
     * @param msg the msg
     */
    public InvalidParameterException(String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Invalid parameter exception.
     *
     * @param arg0 the arg 0
     */
    public InvalidParameterException(Throwable arg0) {
        super(arg0);

    }

}