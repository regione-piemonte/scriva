/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.dto;

import java.util.Objects;

/**
 * Status
 */
public class Status {
    private String code = null;

    private String description = null;

    private OneOfStatusResult result = null;

    public Status code(String code) {
        this.code = code;
        return this;
    }

    /**
     * Get code
     *
     * @return code
     **/
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Status description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get description
     *
     * @return description
     **/
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status result(OneOfStatusResult result) {
        this.result = result;
        return this;
    }

    /**
     * Get result
     *
     * @return result
     **/
    public OneOfStatusResult getResult() {
        return result;
    }

    public void setResult(OneOfStatusResult result) {
        this.result = result;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Status status = (Status) o;
        return Objects.equals(this.code, status.code) &&
                Objects.equals(this.description, status.description) &&
                Objects.equals(this.result, status.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, description, result);
    }


    @Override
    public String toString() {
        return "class Status {\n" +
                "    code: " + toIndentedString(code) + "\n" +
                "    description: " + toIndentedString(description) + "\n" +
                "    result: " + toIndentedString(result) + "\n" +
                "}";
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}