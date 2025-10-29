/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Catasto post request.
 */
@JsonInclude(Include.NON_NULL)
public class CatastoPostRequest implements Serializable {

    @JsonProperty("limit")
    private BigDecimal limit = null;

    @JsonProperty("offset")
    private BigDecimal offset = null;

    @JsonProperty("coordinates")
    private List<List<List<BigDecimal>>> coordinates = new ArrayList<List<List<BigDecimal>>>();

    /**
     * Gets limit.
     *
     * @return the limit
     */
    public BigDecimal getLimit() {
        return limit;
    }

    /**
     * Sets limit.
     *
     * @param limit the limit
     */
    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }

    /**
     * Gets offset.
     *
     * @return the offset
     */
    public BigDecimal getOffset() {
        return offset;
    }

    /**
     * Sets offset.
     *
     * @param offset the offset
     */
    public void setOffset(BigDecimal offset) {
        this.offset = offset;
    }

    /**
     * Gets coordinates.
     *
     * @return the coordinates
     */
    public List<List<List<BigDecimal>>> getCoordinates() {
        return coordinates;
    }

    /**
     * Sets coordinates.
     *
     * @param coordinates the coordinates
     */
    public void setCoordinates(List<List<List<BigDecimal>>> coordinates) {
        this.coordinates = coordinates;
    }
}