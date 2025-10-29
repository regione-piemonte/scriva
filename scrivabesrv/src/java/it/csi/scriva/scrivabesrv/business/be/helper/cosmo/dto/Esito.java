/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto;

import java.util.Objects;

/**
 * Esito dei servizi.
 *
 * @author CSI PIEMONTE
 */
public class Esito {

    private Integer status;

    private String code;

    private String title;

    /**
     * Gets status.
     *
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Esito that = (Esito) o;
        return Objects.equals(status, that.status) && Objects.equals(code, that.code) && Objects.equals(title, that.title);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(status, code, title);
    }

    /**
     * @return string
     */
    @Override
    public String
    toString() {
        final StringBuilder sb = new StringBuilder("Esito {\n");
        sb.append("         status:").append(status);
        sb.append(",\n         code:'").append(code).append("'");
        sb.append(",\n         title:'").append(title).append("'");
        sb.append("}\n");
        return sb.toString();
    }
}