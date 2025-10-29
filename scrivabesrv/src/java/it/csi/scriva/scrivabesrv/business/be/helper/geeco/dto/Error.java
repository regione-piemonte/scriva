/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * The type Error.
 *
 * @author CSI PIEMONTE
 */
public class Error
{
    @JsonProperty("error_type")
    private String errorType;
    @JsonProperty("message")
    private String message;

    /**
     * Gets error type.
     *
     * @return the error type
     */
    public String getErrorType() {
        return this.errorType;
    }

    /**
     * Sets error type.
     *
     * @param error_type the error type
     */
    public void setErrorType(final String error_type) {
        this.errorType = error_type;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Error error = (Error) o;
        return Objects.equals(errorType, error.errorType) && Objects.equals(message, error.message);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(errorType, message);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Error {");
        sb.append("         errorType:'").append(errorType).append("'");
        sb.append(",         message:'").append(message).append("'");
        sb.append("}");
        return sb.toString();
    }
}