/*

========================LICENSE_START=================================
Copyright (C) 2025 Regione Piemonte
SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
SPDX-License-Identifier: EUPL-1.2
=========================LICENSE_END==================================
*/
package it.csi.scriva.scrivaapisrv.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The type Object list fo dto.
 *
 * @author CSI PIEMONTE
 */
public class ObjectListFoDTO implements Serializable {

    /**
     * The Object list.
     */
    List<?> objectList;

    private String paginationInfoHeader;

    /**
     * Gets object list.
     *
     * @return the object list
     */
    public List<?> getObjectList() {
        return objectList;
    }

    /**
     * Sets object list.
     *
     * @param objectList the object list
     */
    public void setObjectList(List<?> objectList) {
        this.objectList = objectList;
    }

    /**
     * Gets pagination info header.
     *
     * @return the pagination info header
     */
    public String getPaginationInfoHeader() {
        return paginationInfoHeader;
    }

    /**
     * Sets pagination info header.
     *
     * @param paginationInfoHeader the pagination info header
     */
    public void setPaginationInfoHeader(String paginationInfoHeader) {
        this.paginationInfoHeader = paginationInfoHeader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectListFoDTO that = (ObjectListFoDTO) o;
        return Objects.equals(objectList, that.objectList) && Objects.equals(paginationInfoHeader, that.paginationInfoHeader);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectList, paginationInfoHeader);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ObjectListFoDTO {");
        sb.append("         objectList:").append(objectList);
        sb.append(",         paginationInfoHeader:'").append(paginationInfoHeader).append("'");
        sb.append("}");
        return sb.toString();
    }
}