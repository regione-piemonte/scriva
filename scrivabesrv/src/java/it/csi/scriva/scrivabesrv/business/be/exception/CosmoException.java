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

import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.Esito;

/**
 * The type Cosmo exception.
 *
 * @author CSI PIEMONTE
 */
public class CosmoException extends Exception{
    private static final long serialVersionUID = 1L;

    private Esito esito;

    /**
     * Gets esito.
     *
     * @return the esito
     */
    public Esito getEsito() {
        return esito;
    }

    /**
     * Sets esito.
     *
     * @param esito the esito
     */
    public void setEsito(Esito esito) {
        this.esito = esito;
    }

    /**
     * Instantiates a new Cosmo exception.
     *
     * @param msg the msg
     */
    public CosmoException(String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Cosmo exception.
     *
     * @param esito the esito
     */
    public CosmoException(Esito esito) { this.setEsito(esito); }

    /**
     * Instantiates a new Cosmo exception.
     *
     * @param arg0 arg
     */
    public CosmoException(Throwable arg0) { super(arg0); }

}