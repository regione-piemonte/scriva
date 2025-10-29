/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.dto;

import java.util.List;
import java.util.Objects;

/**
 * The type Istanza competenza result fo dto.
 */
public class ListObjectWithHeaderResultFoDTO {

    private List<Object> objectList;

    private String countHeader;

    /**
     * Gets istanza competenza list.
     *
     * @return the istanza competenza list
     */
    public List<Object> getObjectList() {
        return objectList;
    }

    /**
     * Sets istanza competenza list.
     *
     * @param objectList the istanza competenza list
     */
    public void setObjectList(List<Object> objectList) {
        this.objectList = objectList;
    }

    /**
     * Gets count competenze header.
     *
     * @return the count competenze header
     */
    public String getCountHeader() {
        return countHeader;
    }

    /**
     * Sets count competenze header.
     *
     * @param countHeader the count competenze header
     */
    public void setCountHeader(String countHeader) {
        this.countHeader = countHeader;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListObjectWithHeaderResultFoDTO that = (ListObjectWithHeaderResultFoDTO) o;
        return Objects.equals(objectList, that.objectList) && Objects.equals(countHeader, that.countHeader);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(objectList, countHeader);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ListObjectWithHeaderResultFoDTO {\n");
        sb.append("         istanzaCompetenzaList:").append(objectList);
        sb.append(",\n         countCompetenzeHeader:'").append(countHeader).append("'");
        sb.append("}\n");
        return sb.toString();
    }
}