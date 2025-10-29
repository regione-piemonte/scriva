/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.index.dto;

import java.util.Objects;

/**
 * The type Index generic result.
 */
public class IndexGenericResult {

    /**
     * The Output.
     */
    Object output;

    /**
     * Gets output.
     *
     * @return the output
     */
    public Object getOutput() {
        return output;
    }

    /**
     * Sets output.
     *
     * @param output the output
     */
    public void setOutput(Object output) {
        this.output = output;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexGenericResult that = (IndexGenericResult) o;
        return Objects.equals(output, that.output);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(output);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "IndexGenericResult {\n" +
                "         output:" + output +
                "}\n";
    }
}