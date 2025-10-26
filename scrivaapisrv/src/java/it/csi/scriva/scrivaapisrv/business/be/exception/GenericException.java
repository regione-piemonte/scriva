/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */

package it.csi.scriva.scrivaapisrv.business.be.exception;

import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;

public class GenericException extends Exception {

    private static final long serialVersionUID = 1L;

    private ErrorDTO error;

    public GenericException(String msg) {
        super(msg);
    }

    public GenericException(ErrorDTO errore) {
        this.setError(errore);
    }

    public GenericException(Throwable arg0) {
        super(arg0);
    }

    public ErrorDTO getError() {
        return error;
    }

    public void setError(ErrorDTO error) {
        this.error = error;
    }

}