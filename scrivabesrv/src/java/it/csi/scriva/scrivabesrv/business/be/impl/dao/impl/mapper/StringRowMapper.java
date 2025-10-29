/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl.dao.impl.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Long row mapper.
 */
public class StringRowMapper implements RowMapper<String> {
    /**
     * The Field name.
     */
    String fieldName;

    /**
     * Instantiates a new Long row mapper.
     *
     * @param fieldName the field name
     */
    public StringRowMapper(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Implementations must implement this method to map each row of data
     * in the ResultSet. This method should not call {@code next()} on
     * the ResultSet; it is only supposed to map values of the current row.
     *
     * @param rs     the ResultSet to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     * @return the result object for the current row (may be {@code null})
     * @throws SQLException if a SQLException is encountered getting
     *                      column values (that is, there's no need to catch SQLException)
     */
    @Override
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getString(fieldName);
    }
}