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
import org.apache.commons.lang3.tuple.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Long and String Pair row mapper.
 */
public class LongStringRowMapper implements RowMapper<Pair<Long, String>> {

    private final String longFieldName;
    private final String stringFieldName;

    /**
     * Instantiates a new Long and String Pair row mapper.
     *
     * @param longFieldName   the name of the column for the Long value
     * @param stringFieldName the name of the column for the String value
     */
    public LongStringRowMapper(String longFieldName, String stringFieldName) {
        this.longFieldName = longFieldName;
        this.stringFieldName = stringFieldName;
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
    public Pair<Long, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long longValue = rs.getLong(longFieldName);
        String stringValue = rs.getString(stringFieldName);
        return Pair.of(longValue, stringValue);
    }
}