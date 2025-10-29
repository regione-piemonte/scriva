/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl.dao.impl;

import it.csi.scriva.scrivabesrv.business.be.impl.dao.DynamicSqlDAO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Dynamic sql dao.
 *
 * @author CSI PIEMONTE
 */
public class DynamicSqlDAOImpl extends ScrivaBeSrvGenericDAO<List<String>> implements DynamicSqlDAO {

    private final String className = this.getClass().getSimpleName();

    /**
     * Esegue la query passata in input e restituisce i relativi record in una lista di stringhe.
     * La prima stringa rappresenta le intestazioni di colonna.
     *
     * @param query     Query da eseguire
     * @param delimiter Separatore tra field
     * @return Lista dei record in formato stringa
     */
    @Override
    public List<String> getListFromQuery(String query, String delimiter, Map<String, Object> mapParam, String offset, String limit) {
        logBegin(className);
        try {
            Map<String, Object> map = mapParam == null ? new HashMap<>() : mapParam;
            MapSqlParameterSource params = getParameterValue(map);
            List<Map<String, Object>> rows = template.queryForList(getQuery(query, offset, limit), params);
            return getListFromRowsMap(rows, delimiter);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Esegue la query passata in input e restituisce i relativi dati in un array di byte.
     *
     * @param query     Query da eseguire
     * @param delimiter Separatore tra field
     * @return Array di byte dei record in formato CSV
     */
    @Override
    public byte[] getCSVFromQuery(String query, String delimiter, Map<String, Object> mapParam, String offset, String limit) {
        logBegin(className);
        byte[] bytes = new byte[0];
        List<String> stringList = this.getListFromQuery(query, delimiter, mapParam, offset, limit);
        if (stringList != null) {
            StringBuilder csv = new StringBuilder();
            for (String record : stringList) {
                csv.append(record).append(System.lineSeparator());
            }
            bytes = csv.toString().getBytes(StandardCharsets.UTF_8);
        }
        logEnd(className);
        return bytes;
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String
     */
    @Override
    public String getPrimaryKeySelect() {
        return null;
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<List < String>>
     */
    @Override
    public RowMapper<List<String>> getRowMapper() throws SQLException {
        return null;
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>
     */
    @Override
    public RowMapper<List<String>> getExtendedRowMapper() throws SQLException {
        return null;
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @param delimiter the delimiter
     * @return RowMapper<AdempimentoTipoAllegatoDTO> row mapper
     * @throws SQLException the sql exception
     */
    public RowMapper<List<String>> getRowMapper(String delimiter) throws SQLException {
        return new StringListRowMapper(delimiter);
    }

    private List<String> getListFromRowsMap(List<Map<String, Object>> rows, String delimiter) {
        List<String> keyList = new ArrayList<>();
        List<String> result = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            if (keyList.isEmpty()) {
                for (Map.Entry<String, Object> entry : row.entrySet()) {
                    keyList.add(entry.getKey());
                }
                String[] keyArray = keyList.toArray(new String[0]);
                List<String> strKeyList = Arrays.asList(keyArray);
                String joinedKeyString = String.join(StringUtils.isNotBlank(delimiter) ? delimiter : ";", strKeyList);
                result.add(joinedKeyString);
            }

            List<String> valueList = new ArrayList<>();
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                if (entry.getValue() != null) {
                    valueList.add(entry.getValue().getClass() == String.class ? "\"" + entry.getValue() + "\"" : entry.getValue().toString());
                } else {
                    valueList.add("");
                }
            }
            String[] valueArray = valueList.toArray(new String[0]);
            List<String> strValueList = Arrays.asList(valueArray);
            String joinedValueString = String.join(StringUtils.isNotBlank(delimiter) ? delimiter : ";", strValueList);
            result.add(joinedValueString);
        }

        return result;
    }

    private static class StringListRowMapper implements RowMapper<List<String>> {

        private String delimiter;

        /**
         * Costruttore
         *
         * @param delimiter the delimiter
         * @throws SQLException SQLException
         */
        public StringListRowMapper(String delimiter) throws SQLException {
            this.delimiter = delimiter;
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
        public List<String> mapRow(ResultSet rs, int rowNum) throws SQLException {

            ResultSetMetaData metaData = rs.getMetaData();
            int columns = metaData.getColumnCount();
            List<String> fieldsName = new ArrayList<>();
            List<String> result = new ArrayList<>();
            int riga = 1;
            while (rs.next()) {
                Map<String, Object> row = new HashMap<String, Object>(columns);
                List<String> valori = new ArrayList<>();
                for (int i = 1; i <= columns; ++i) {
                    if (riga == 1) {
                        fieldsName.add(metaData.getColumnName(i));
                    }
                    if (rs.getObject(i) != null) {
                        valori.add("varchar".equalsIgnoreCase(metaData.getColumnTypeName(i)) ? "\"" + rs.getObject(i).toString() + "\"" : rs.getObject(i).toString());
                    } else {
                        valori.add("varchar".equalsIgnoreCase(metaData.getColumnTypeName(i)) ? "\"\"" : "");
                    }
                }
                //rows.add(row);
                if (riga == 1) {
                    String[] intestazioni = fieldsName.toArray(new String[0]);
                    List<String> strIntestazioniList = Arrays.asList(intestazioni);
                    String joinedIntestazioniString = String.join(this.delimiter, strIntestazioniList);
                    result.add(joinedIntestazioniString);
                }
                String[] arrayValori = valori.toArray(new String[0]);
                List<String> strValoriList = Arrays.asList(arrayValori);
                String joinedValoriString = String.join(this.delimiter, strValoriList);
                result.add(joinedValoriString);
                riga++;
            }

            // Stampa result
            String[] arrayResult = result.toArray(new String[0]);

            return Arrays.asList(arrayResult);
        }


    }

}