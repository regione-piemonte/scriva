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

import java.util.Objects;

/**
 * The type Generic response dto.
 */
public class GenericResponseDTO {

    private String status;

    private String contentType;

    private String contentDisposition;

    private String paginationInfo;

    private Object entity;

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets content type.
     *
     * @return the content type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets content type.
     *
     * @param contentType the content type
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Gets content disposition.
     *
     * @return the content disposition
     */
    public String getContentDisposition() {
        return contentDisposition;
    }

    /**
     * Sets content disposition.
     *
     * @param contentDisposition the content disposition
     */
    public void setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
    }

    /**
     * Gets pagination info.
     *
     * @return the pagination info
     */
    public String getPaginationInfo() {
        return paginationInfo;
    }

    /**
     * Sets pagination info.
     *
     * @param paginationInfo the pagination info
     */
    public void setPaginationInfo(String paginationInfo) {
        this.paginationInfo = paginationInfo;
    }

    /**
     * Gets entity.
     *
     * @return the entity
     */
    public Object getEntity() {
        return entity;
    }

    /**
     * Sets entity.
     *
     * @param entity the entity
     */
    public void setEntity(Object entity) {
        this.entity = entity;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericResponseDTO that = (GenericResponseDTO) o;
        return Objects.equals(status, that.status) && Objects.equals(contentType, that.contentType) && Objects.equals(contentDisposition, that.contentDisposition) && Objects.equals(paginationInfo, that.paginationInfo) && Objects.equals(entity, that.entity);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(status, contentType, contentDisposition, paginationInfo, entity);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GenericResponseDTO {\n");
        sb.append("         status:'").append(status).append("'");
        sb.append(",\n         contentType:'").append(contentType).append("'");
        sb.append(",\n         contentDisposition:'").append(contentDisposition).append("'");
        sb.append(",\n         paginationInfo:'").append(paginationInfo).append("'");
        sb.append(",\n         entity:").append(entity);
        sb.append("}\n");
        return sb.toString();
    }
}